package com.pla.annoyinglib.world.structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

/** Routes around reserved footprints, with one-block grades and bounded search. */
final class SettlementPaths {
    private final List<LayoutPlanner.Placed> pieces;
    private final List<LayoutPlanner.Entrance> gates;
    private final LayoutPlanner.Terrain terrain;
    private final BlockPos origin;
    private final BoundingBox enclosure;
    private final Map<Long, BlockPos> paths = new LinkedHashMap<>();

    SettlementPaths(List<LayoutPlanner.Placed> pieces, List<LayoutPlanner.Entrance> gates,
                    LayoutPlanner.Terrain terrain, BlockPos origin, BoundingBox enclosure) {
        this.pieces = pieces; this.gates = gates; this.terrain = terrain; this.origin = origin; this.enclosure = enclosure;
    }

    List<BlockPos> build() {
        BlockPos hub = null;
        BlockPos hubOrigin = enclosure == null ? origin : enclosure.getCenter().atY(0);
        for (int radius = 0; radius <= 40 && hub == null; radius++) {
            for (int x = -radius; x <= radius && hub == null; x++) for (int z : new int[]{-radius, radius}) {
                BlockPos p = hubOrigin.offset(x, 0, z);
                if (!blocked(p) && dry(p) && (enclosure == null || LayoutPlanner.contains(enclosure, p, -8))) { hub = ground(p); break; }
            }
        }
        if (hub == null) return List.of();
        paths.put(key(hub), hub);
        for (LayoutPlanner.Entrance gate : gates) {
            BlockPos outside = gate.pos().relative(gate.facing(), 12);
            List<BlockPos> route = route(ground(outside), hub);
            if (route.isEmpty()) return List.of();
            add(route);
        }
        for (LayoutPlanner.Placed piece : pieces) if (piece.part().role().equals("bridge")) {
            BlockPos end = piece.transform(new BlockPos(piece.part().size().getX() / 2, 0, 1));
            List<BlockPos> route = route(ground(end), hub);
            if (route.isEmpty()) return List.of();
            add(route);
        }
        for (LayoutPlanner.Placed piece : pieces) {
            if (piece.part().doors().isEmpty() || piece.wall() || piece.gate()) continue;
            LayoutSpec.Door door = piece.part().doors().get(0);
            Direction facing = piece.rotation().rotate(LayoutPlanner.direction(door.facing()));
            BlockPos doorway = piece.transform(door.pos());
            int y = doorway.getY() - 1;
            List<BlockPos> approach = new ArrayList<>();
            BlockPos start = null;
            for (int distance = 1; distance <= 10; distance++) {
                BlockPos p = doorway.relative(facing, distance);
                boolean occupied = false;
                for (LayoutPlanner.Placed other : pieces) if (other != piece && LayoutPlanner.contains(other.box(), p, 0) && !gatePass(p)) { occupied = true; break; }
                if (occupied || !dry(p)) break;
                int desired = ground(p).getY();
                if (distance > 1) y += Integer.compare(desired, y);
                approach.add(p.atY(y));
                if (distance >= 2 && y == desired && !blocked(p)) { start = p.atY(y); break; }
            }
            if (start == null) return List.of();
            BlockPos from = start;
            BlockPos target = paths.values().stream().min(Comparator.comparingInt(p -> Math.abs(p.getX() - from.getX()) + Math.abs(p.getZ() - from.getZ()))).orElse(hub);
            List<BlockPos> route = route(start, target);
            if (route.isEmpty()) return List.of();
            add(approach); add(route);
        }
        return List.copyOf(paths.values());
    }

    private void add(List<BlockPos> route) { for (BlockPos p : route) paths.putIfAbsent(key(p), p); }

    private List<BlockPos> route(BlockPos from, BlockPos to) {
        record Node(BlockPos pos, int cost, int score) {}
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::score).thenComparingInt(n -> n.pos().getX()).thenComparingInt(n -> n.pos().getZ()));
        Map<Long, Integer> costs = new HashMap<>();
        Map<Long, BlockPos> parents = new HashMap<>();
        Set<Long> closed = new HashSet<>();
        costs.put(key(from), 0); open.add(new Node(from, 0, distance(from, to)));
        int expanded = 0;
        while (!open.isEmpty() && expanded++ < 16000) {
            Node current = open.poll();
            if (!closed.add(key(current.pos()))) continue;
            if (key(current.pos()) == key(to)) {
                List<BlockPos> route = new ArrayList<>(); BlockPos p = current.pos();
                while (p != null) { route.add(p); p = parents.get(key(p)); }
                return route;
            }
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos next = ground(current.pos().relative(direction));
                if (Math.abs(next.getX() - origin.getX()) > LayoutPlanner.MAX_RADIUS || Math.abs(next.getZ() - origin.getZ()) > LayoutPlanner.MAX_RADIUS) continue;
                if ((blocked(next) && key(next) != key(to)) || !dry(next) || Math.abs(next.getY() - current.pos().getY()) > 1) continue;
                int cost = current.cost() + 10 + Math.abs(next.getY() - current.pos().getY()) * 4;
                if (cost >= costs.getOrDefault(key(next), Integer.MAX_VALUE)) continue;
                costs.put(key(next), cost); parents.put(key(next), current.pos());
                open.add(new Node(next, cost, cost + distance(next, to) * 10));
            }
        }
        return List.of();
    }

    private BlockPos ground(BlockPos p) {
        BlockPos existing = paths.get(key(p));
        if (existing != null) return existing;
        LayoutPlanner.Placed bridge = bridgeAt(p);
        if (bridge != null) return p.atY(bridge.floorY() - 1);
        for (LayoutPlanner.Placed piece : pieces) if (piece.part().role().equals("passage") && LayoutPlanner.contains(piece.box(), p, 0)) return p.atY(piece.origin().getY());
        for (LayoutPlanner.Entrance gate : gates) if (inGate(p, gate)) return p.atY(gate.pos().getY() - 1);
        return p.atY(terrain.floor(p.getX(), p.getZ()) - 1);
    }

    private boolean dry(BlockPos p) { return bridgeAt(p) != null || terrain.surface(p.getX(), p.getZ()) - terrain.floor(p.getX(), p.getZ()) <= 1; }

    private boolean blocked(BlockPos p) {
        if (gatePass(p) || bridgeAt(p) != null) return false;
        for (LayoutPlanner.Placed piece : pieces) if (LayoutPlanner.contains(piece.box(), p, 1)) return true;
        return false;
    }

    private boolean gatePass(BlockPos p) {
        for (LayoutPlanner.Entrance gate : gates) if (inGate(p, gate)) return true;
        for (LayoutPlanner.Placed piece : pieces) if (piece.part().role().equals("passage") && LayoutPlanner.contains(piece.box(), p, 0)) return true;
        return false;
    }

    private LayoutPlanner.Placed bridgeAt(BlockPos p) {
        for (LayoutPlanner.Placed piece : pieces) if (piece.part().role().equals("bridge")) {
            BlockPos local = p.subtract(piece.origin()).rotate(LayoutPlanner.inverse(piece.rotation()));
            if (Math.abs(local.getX() - piece.part().size().getX() / 2) <= 2 && local.getZ() >= -1 && local.getZ() <= piece.part().size().getZ()) return piece;
        }
        return null;
    }

    private boolean inGate(BlockPos p, LayoutPlanner.Entrance gate) {
        int dx = Math.abs(p.getX() - gate.pos().getX()), dz = Math.abs(p.getZ() - gate.pos().getZ());
        return gate.facing().getAxis() == Direction.Axis.X ? dx <= 12 && dz <= Math.max(1, gate.width() / 2 - 1) : dz <= 12 && dx <= Math.max(1, gate.width() / 2 - 1);
    }

    private static int distance(BlockPos a, BlockPos b) { return Math.abs(a.getX() - b.getX()) + Math.abs(a.getZ() - b.getZ()); }
    private static long key(BlockPos p) { return BlockPos.asLong(p.getX(), 0, p.getZ()); }
}
