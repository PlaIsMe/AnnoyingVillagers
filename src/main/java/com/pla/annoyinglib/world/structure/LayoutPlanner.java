package com.pla.annoyinglib.world.structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

/** Bounded, deterministic planning; never reads or writes partially generated neighbor chunks. */
public final class LayoutPlanner {
    public static final int MAX_RADIUS = 127;

    public interface Terrain {
        int surface(int x, int z);
        int floor(int x, int z);
        int minY();
        int maxY();
    }

    public record Placed(LayoutSpec.Part part, BlockPos origin, Rotation rotation,
                         BoundingBox box, int floorY) {
        public boolean wall() { return part.role().equals("wall"); }
        public boolean gate() { return part.role().equals("gate") || part.role().equals("passage"); }
        public BlockPos transform(BlockPos local) { return origin.offset(local.rotate(rotation)); }
    }

    public record Entrance(BlockPos pos, Direction facing, int width) {}
    public record Plan(List<Placed> pieces, List<BlockPos> paths, List<Entrance> gates) {}

    private final LayoutSpec spec;
    private final BlockPos origin;
    private final Rotation rotation;
    private final RandomSource random;
    private final Terrain terrain;
    private final List<Placed> placed = new ArrayList<>();
    private final List<Entrance> gates = new ArrayList<>();
    private final Map<String, Integer> clusterFloors = new HashMap<>();
    private BoundingBox enclosure;

    public LayoutPlanner(LayoutSpec spec, BlockPos origin, Rotation rotation, RandomSource random, Terrain terrain) {
        this.spec = spec;
        this.origin = origin;
        this.rotation = rotation;
        this.random = random;
        this.terrain = terrain;
        for (LayoutSpec.Gate gate : spec.gates()) {
            BlockPos p = origin.offset(gate.pos().rotate(rotation));
            gates.add(new Entrance(p.atY(terrain.surface(p.getX(), p.getZ())), rotation.rotate(direction(gate.facing())), gate.width()));
        }
    }

    public Optional<Plan> plan() {
        if (spec.parts().isEmpty() || spec.maxPieces() < spec.minPieces()) return Optional.empty();
        if (spec.mode().equals("camp")) {
            if (!select(new LayoutSpec.Selection("nearby", spec.parts(), spec.minPieces(), spec.maxPieces(), 82))) return Optional.empty();
        } else {
            // Shared cluster anchors keep tiled castles and connected rooms together vertically.
            Map<String, BoundingBox> clusters = new HashMap<>();
            for (LayoutSpec.Part part : spec.parts()) {
                if (!part.cluster().isEmpty()) {
                    BoundingBox b = horizontalBox(part);
                    clusters.merge(part.cluster(), b, LayoutPlanner::union);
                }
            }
            for (Map.Entry<String, BoundingBox> entry : clusters.entrySet()) {
                int[] heights = sample(entry.getValue(), 8);
                if (heights[1] - heights[0] > spec.maxSlope()) return Optional.empty();
                clusterFloors.put(entry.getKey(), heights[2]);
            }
            for (LayoutSpec.Part part : spec.parts()) {
                Optional<Placed> candidate = anchor(part);
                if (candidate.isEmpty()) return Optional.empty();
                placed.add(candidate.get());
            }
        }
        for (int i = 0; i < gates.size(); i++) {
            Entrance gate = gates.get(i);
            for (Placed piece : placed) if (piece.part().role().equals("passage") && contains(piece.box(), gate.pos(), 0)) {
                gates.set(i, new Entrance(gate.pos().atY(piece.origin().getY() + 1), gate.facing(), gate.width()));
                break;
            }
        }
        for (Placed p : placed) if (p.wall()) enclosure = enclosure == null ? p.box() : union(enclosure, p.box());
        // Adjacent wall spans can step, but cannot develop large vertical discontinuities.
        for (Placed a : placed) if (a.wall()) for (Placed b : placed) if (b.wall() && a != b
                && overlaps(a.box(), b.box(), 1) && Math.abs(a.floorY() - b.floorY()) > 4) return Optional.empty();

        for (LayoutSpec.Selection selection : spec.selections()) if (!select(selection)) return Optional.empty();
        List<BlockPos> paths = spec.paths() ? new SettlementPaths(placed, gates, terrain, origin, enclosure).build() : List.of();
        if (spec.paths() && placed.stream().anyMatch(p -> !p.part().doors().isEmpty()) && paths.isEmpty()) return Optional.empty();
        // Placement order is stable across chunk generation order; gates clear their authored openings last.
        placed.sort(Comparator.comparingInt(p -> p.wall() ? 0 : p.part().role().equals("passage") ? 3 : p.gate() ? 2 : 1));
        return Optional.of(new Plan(List.copyOf(placed), paths, List.copyOf(gates)));
    }

    private boolean select(LayoutSpec.Selection selection) {
        if (selection.pool().isEmpty()) return selection.min() == 0;
        List<LayoutSpec.Part> pool = new ArrayList<>(selection.pool());
        for (int i = pool.size() - 1; i > 0; i--) {
            int other = random.nextInt(i + 1);
            LayoutSpec.Part p = pool.get(i); pool.set(i, pool.get(other)); pool.set(other, p);
        }
        int count = Math.min(pool.size(), selection.min() + random.nextInt(Math.max(1, selection.max() - selection.min() + 1)));
        int added = 0;
        int attempts = 0;
        for (LayoutSpec.Part base : pool) {
            if (added >= count) break;
            for (int attempt = 0; attempt < 32 && attempts++ < 192; attempt++) {
                int turn = random.nextInt(4);
                BlockPos center;
                if (selection.zone().equals("outside")) {
                    if (gates.isEmpty()) break;
                    Entrance gate = gates.get(random.nextInt(gates.size()));
                    int side = (random.nextBoolean() ? 1 : -1) * (12 + random.nextInt(12));
                    BlockPos world = gate.pos().relative(gate.facing(), 18 + random.nextInt(14)).relative(gate.facing().getClockWise(), side);
                    center = world.subtract(origin).atY(0).rotate(inverse(rotation));
                } else if (selection.zone().equals("interior") && enclosure != null) {
                    int x = between(enclosure.minX() + 10, enclosure.maxX() - 10);
                    int z = between(enclosure.minZ() + 10, enclosure.maxZ() - 10);
                    center = new BlockPos(x - origin.getX(), 0, z - origin.getZ()).rotate(inverse(rotation));
                } else {
                    int radius = selection.radius();
                    center = new BlockPos(between(-radius, radius), 0, between(-radius, radius));
                }
                BlockPos half = new BlockPos(base.size().getX() / 2, 0, base.size().getZ() / 2).rotate(Rotation.values()[turn]);
                LayoutSpec.Part part = base.at(center.subtract(half), turn);
                BoundingBox horizontal = horizontalBox(part);
                if (selection.zone().equals("interior") && enclosure != null && !inside(horizontal, enclosure, 8)) continue;
                if (selection.zone().equals("outside") && enclosure != null && overlaps(horizontal, enclosure, 3)) continue;
                if (placed.stream().anyMatch(old -> overlaps(old.box(), horizontal, 4)) || blocksApproach(horizontal)) continue;
                Optional<Placed> candidate = anchor(part);
                if (candidate.isEmpty()) continue;
                Placed p = candidate.get();
                if (selection.zone().equals("interior") && enclosure != null && !inside(p.box(), enclosure, 8)) continue;
                if (selection.zone().equals("outside") && enclosure != null && overlaps(p.box(), enclosure, 3)) continue;
                if (placed.stream().anyMatch(old -> overlaps(old.box(), p.box(), 4))) continue;
                if (blocksApproach(p.box())) continue;
                placed.add(p);
                added++;
                break;
            }
        }
        return added >= selection.min();
    }

    private boolean blocksApproach(BoundingBox b) {
        for (Entrance gate : gates) {
            // Reserve a through-corridor between each genuine entrance and the center.
            int x = gate.pos().getX(), z = gate.pos().getZ();
            int endX = origin.getX(), endZ = origin.getZ();
            if (gate.facing().getAxis() == Direction.Axis.X) {
                if (b.minZ() <= z + 3 && b.maxZ() >= z - 3 && b.minX() <= Math.max(x, endX) + 5 && b.maxX() >= Math.min(x, endX) - 5) return true;
            } else if (b.minX() <= x + 3 && b.maxX() >= x - 3 && b.minZ() <= Math.max(z, endZ) + 5 && b.maxZ() >= Math.min(z, endZ) - 5) return true;
        }
        for (Placed old : placed) for (LayoutSpec.Door door : old.part().doors()) {
            BlockPos p = old.transform(door.pos());
            Direction facing = old.rotation().rotate(direction(door.facing()));
            for (int i = 1; i <= 5; i++) if (contains(b, p.relative(facing, i), 2)) return true;
        }
        return false;
    }

    private Optional<Placed> anchor(LayoutSpec.Part part) {
        Rotation turn = rotation.getRotated(Rotation.values()[part.turn()]);
        BlockPos horizontal = origin.atY(0).offset(part.offset().atY(0).rotate(rotation));
        BoundingBox b = box(horizontal, part.size(), turn);
        if (Math.max(Math.max(Math.abs(b.minX() - origin.getX()), Math.abs(b.maxX() - origin.getX())),
                Math.max(Math.abs(b.minZ() - origin.getZ()), Math.abs(b.maxZ() - origin.getZ()))) > MAX_RADIUS) return Optional.empty();
        if (!List.of("airship", "ship", "submerged", "bridge").contains(spec.mode())) {
            int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;
            for (int x : new int[]{b.minX(), b.maxX()}) for (int z : new int[]{b.minZ(), b.maxZ()}) {
                int y = terrain.floor(x, z); low = Math.min(low, y); high = Math.max(high, y);
            }
            if (high - low > Math.min(spec.maxSlope(), part.role().equals("wall") ? 4 : spec.maxSlope())) return Optional.empty();
        }
        int[] heights = sample(b, 6);
        boolean bridge = part.role().equals("bridge");
        boolean water = spec.mode().equals("ship") || spec.mode().equals("submerged");
        int floor;
        if (bridge) {
            Integer deck = bridgeHeight(part, horizontal, turn);
            if (deck == null) return Optional.empty();
            floor = deck;
        } else if (water) {
            int requiredDepth = Math.max(spec.minWaterDepth(), spec.mode().equals("submerged") ? part.size().getY() - part.anchor() + 2 : part.anchor() + 2);
            int waterline = terrain.surface(b.getCenter().getX(), b.getCenter().getZ());
            for (int x = b.minX(); ; x = Math.min(b.maxX(), x + 6)) {
                for (int z = b.minZ(); ; z = Math.min(b.maxZ(), z + 6)) {
                    if (terrain.surface(x, z) - terrain.floor(x, z) < requiredDepth || terrain.surface(x, z) != waterline) return Optional.empty();
                    if (z == b.maxZ()) break;
                }
                if (x == b.maxX()) break;
            }
            if (spec.mode().equals("submerged") && heights[1] - heights[0] > spec.foundationDepth()) return Optional.empty();
            floor = spec.mode().equals("ship") ? waterline : heights[1];
        } else {
            if (!spec.mode().equals("airship") && heights[1] - heights[0] > Math.min(spec.maxSlope(), part.role().equals("wall") ? 4 : spec.maxSlope())) return Optional.empty();
            if (!spec.mode().equals("airship")) {
                for (int x : new int[]{b.minX(), b.getCenter().getX(), b.maxX()})
                    for (int z : new int[]{b.minZ(), b.getCenter().getZ(), b.maxZ()})
                        if (terrain.surface(x, z) - terrain.floor(x, z) > 1) return Optional.empty();
            }
            floor = clusterFloors.getOrDefault(part.cluster(), part.role().equals("wall") ? heights[1] : heights[2]);
            if (!part.doors().isEmpty() && part.cluster().isEmpty() && !part.role().equals("wall")) {
                BlockPos door = horizontal.offset(part.doors().get(0).pos().rotate(turn));
                floor = terrain.floor(door.getX(), door.getZ());
            }
            if (spec.mode().equals("airship")) floor = heights[1] + 28;
        }
        if (!water && !bridge && !spec.mode().equals("airship") && floor - heights[0] > spec.foundationDepth()) return Optional.empty();
        BlockPos position = horizontal.atY(floor + part.offset().getY() - part.anchor());
        BoundingBox placedBox = box(position, part.size(), turn);
        if (placedBox.minY() < terrain.minY() + 4 || placedBox.maxY() >= terrain.maxY() - 2) return Optional.empty();
        return Optional.of(new Placed(part, position, turn, placedBox, floor));
    }

    private Integer bridgeHeight(LayoutSpec.Part part, BlockPos position, Rotation turn) {
        int length = part.size().getZ();
        if (length < 48) return null;
        int x = part.size().getX() / 2;
        BlockPos a = position.offset(new BlockPos(x, 0, 1).rotate(turn));
        BlockPos b = position.offset(new BlockPos(x, 0, length - 2).rotate(turn));
        if (terrain.surface(a.getX(), a.getZ()) != terrain.floor(a.getX(), a.getZ())
                || terrain.surface(b.getX(), b.getZ()) != terrain.floor(b.getX(), b.getZ())) return null;
        int ya = terrain.floor(a.getX(), a.getZ()), yb = terrain.floor(b.getX(), b.getZ());
        if (Math.abs(ya - yb) > 4) return null;
        int wet = 0;
        for (int z = 8; z < length - 8; z += 4) {
            BlockPos p = position.offset(new BlockPos(x, 0, z).rotate(turn));
            if (terrain.surface(p.getX(), p.getZ()) - terrain.floor(p.getX(), p.getZ()) >= 4) wet += 4;
        }
        return wet >= 32 ? Math.max(ya, yb) + 1 : null;
    }

    private int[] sample(BoundingBox b, int step) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        List<Integer> heights = new ArrayList<>();
        for (int x = b.minX(); ; x = Math.min(b.maxX(), x + step)) {
            for (int z = b.minZ(); ; z = Math.min(b.maxZ(), z + step)) {
                int y = terrain.floor(x, z); min = Math.min(min, y); max = Math.max(max, y); heights.add(y);
                if (z == b.maxZ()) break;
            }
            if (x == b.maxX()) break;
        }
        heights.sort(Integer::compareTo);
        return new int[]{min, max, heights.get(heights.size() / 2)};
    }

    private BoundingBox horizontalBox(LayoutSpec.Part part) {
        return box(origin.atY(0).offset(part.offset().atY(0).rotate(rotation)), part.size(), rotation.getRotated(Rotation.values()[part.turn()]));
    }

    public static BoundingBox box(BlockPos origin, BlockPos size, Rotation rotation) {
        BlockPos far = new BlockPos(size.getX() - 1, size.getY() - 1, size.getZ() - 1).rotate(rotation).offset(origin);
        return BoundingBox.fromCorners(origin, far);
    }

    static BoundingBox union(BoundingBox a, BoundingBox b) {
        return new BoundingBox(Math.min(a.minX(), b.minX()), Math.min(a.minY(), b.minY()), Math.min(a.minZ(), b.minZ()),
                Math.max(a.maxX(), b.maxX()), Math.max(a.maxY(), b.maxY()), Math.max(a.maxZ(), b.maxZ()));
    }

    static boolean overlaps(BoundingBox a, BoundingBox b, int margin) {
        return a.minX() - margin <= b.maxX() && a.maxX() + margin >= b.minX() && a.minZ() - margin <= b.maxZ() && a.maxZ() + margin >= b.minZ();
    }

    static boolean inside(BoundingBox a, BoundingBox b, int margin) {
        return a.minX() >= b.minX() + margin && a.maxX() <= b.maxX() - margin && a.minZ() >= b.minZ() + margin && a.maxZ() <= b.maxZ() - margin;
    }

    static boolean contains(BoundingBox box, BlockPos p, int margin) {
        return p.getX() >= box.minX() - margin && p.getX() <= box.maxX() + margin && p.getZ() >= box.minZ() - margin && p.getZ() <= box.maxZ() + margin;
    }

    static Direction direction(String name) {
        Direction result = Direction.byName(name);
        return result != null && result.getAxis().isHorizontal() ? result : Direction.NORTH;
    }

    private int between(int min, int max) { return min + random.nextInt(Math.max(1, max - min + 1)); }
    static Rotation inverse(Rotation rotation) { return Rotation.values()[(4 - rotation.ordinal()) & 3]; }
}
