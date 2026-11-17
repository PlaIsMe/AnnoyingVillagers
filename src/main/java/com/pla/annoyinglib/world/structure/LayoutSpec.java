package com.pla.annoyinglib.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

/** Authored content stays in its owning mod's worldgen JSON; this is shared placement data. */
public record LayoutSpec(String mode, List<Part> parts, List<Selection> selections,
                         int minPieces, int maxPieces, int maxSlope, int foundationDepth,
                         int minWaterDepth, boolean paths, List<Gate> gates) {
    public static final Codec<LayoutSpec> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.optionalFieldOf("mode", "surface").forGetter(LayoutSpec::mode),
            Part.CODEC.listOf().fieldOf("parts").forGetter(LayoutSpec::parts),
            Selection.CODEC.listOf().optionalFieldOf("selections", List.of()).forGetter(LayoutSpec::selections),
            Codec.intRange(0, 128).optionalFieldOf("min_pieces", 0).forGetter(LayoutSpec::minPieces),
            Codec.intRange(0, 128).optionalFieldOf("max_pieces", 0).forGetter(LayoutSpec::maxPieces),
            Codec.intRange(0, 32).optionalFieldOf("max_slope", 8).forGetter(LayoutSpec::maxSlope),
            Codec.intRange(0, 16).optionalFieldOf("foundation_depth", 12).forGetter(LayoutSpec::foundationDepth),
            Codec.intRange(0, 64).optionalFieldOf("min_water_depth", 0).forGetter(LayoutSpec::minWaterDepth),
            Codec.BOOL.optionalFieldOf("paths", false).forGetter(LayoutSpec::paths),
            Gate.CODEC.listOf().optionalFieldOf("gates", List.of()).forGetter(LayoutSpec::gates)
    ).apply(i, LayoutSpec::new));

    public record Door(BlockPos pos, String facing) {
        public static final Codec<Door> CODEC = RecordCodecBuilder.create(i -> i.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(Door::pos),
                Codec.STRING.fieldOf("facing").forGetter(Door::facing)
        ).apply(i, Door::new));
    }

    /** Offset is the unrotated template origin, relative to the complete layout's center. */
    public record Part(ResourceLocation template, BlockPos offset, BlockPos size, int anchor,
                       String role, String cluster, int turn, List<Door> doors) {
        public static final Codec<Part> CODEC = RecordCodecBuilder.create(i -> i.group(
                ResourceLocation.CODEC.fieldOf("template").forGetter(Part::template),
                BlockPos.CODEC.optionalFieldOf("offset", BlockPos.ZERO).forGetter(Part::offset),
                BlockPos.CODEC.fieldOf("size").forGetter(Part::size),
                Codec.intRange(0, 384).optionalFieldOf("anchor", 0).forGetter(Part::anchor),
                Codec.STRING.optionalFieldOf("role", "building").forGetter(Part::role),
                Codec.STRING.optionalFieldOf("cluster", "").forGetter(Part::cluster),
                Codec.intRange(0, 3).optionalFieldOf("turn", 0).forGetter(Part::turn),
                Door.CODEC.listOf().optionalFieldOf("doors", List.of()).forGetter(Part::doors)
        ).apply(i, Part::new));

        public Part at(BlockPos position, int rotation) {
            return new Part(template, position, size, anchor, role, cluster, rotation, doors);
        }
    }

    /** Pools are selected without replacement. Zones reserve buildings before props/vegetation. */
    public record Selection(String zone, List<Part> pool, int min, int max, int radius) {
        public static final Codec<Selection> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.STRING.fieldOf("zone").forGetter(Selection::zone),
                Part.CODEC.listOf().fieldOf("pool").forGetter(Selection::pool),
                Codec.intRange(0, 32).fieldOf("min").forGetter(Selection::min),
                Codec.intRange(0, 32).fieldOf("max").forGetter(Selection::max),
                Codec.intRange(8, 120).optionalFieldOf("radius", 48).forGetter(Selection::radius)
        ).apply(i, Selection::new));
    }

    public record Gate(BlockPos pos, String facing, int width) {
        public static final Codec<Gate> CODEC = RecordCodecBuilder.create(i -> i.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(Gate::pos),
                Codec.STRING.fieldOf("facing").forGetter(Gate::facing),
                Codec.intRange(3, 32).fieldOf("width").forGetter(Gate::width)
        ).apply(i, Gate::new));
    }
}
