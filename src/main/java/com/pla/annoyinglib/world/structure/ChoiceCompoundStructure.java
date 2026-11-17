package com.pla.annoyinglib.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

/** One village-family entry, selecting a complete owner-defined compound rather than a loose building. */
public final class ChoiceCompoundStructure extends Structure {
    public static final Codec<ChoiceCompoundStructure> CODEC=RecordCodecBuilder.create(i->i.group(
            settingsCodec(i),ResourceLocation.CODEC.listOf().fieldOf("layouts").forGetter(s->s.layouts)
    ).apply(i,ChoiceCompoundStructure::new));
    private final List<ResourceLocation> layouts;
    public ChoiceCompoundStructure(StructureSettings settings,List<ResourceLocation> layouts){super(settings);this.layouts=List.copyOf(layouts);}
    public List<ResourceLocation> layouts(){return layouts;}
    @Override public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        var registry=context.registryAccess().registryOrThrow(Registries.STRUCTURE);
        var biome=context.biomeSource().getNoiseBiome(QuartPos.fromBlock(context.chunkPos().getMiddleBlockX()),
                QuartPos.fromBlock(context.chunkGenerator().getSeaLevel()+1),QuartPos.fromBlock(context.chunkPos().getMiddleBlockZ()),context.randomState().sampler());
        if(!context.validBiome().test(biome))return Optional.empty();
        List<TerrainCompoundStructure> candidates=new ArrayList<>();
        for(var id:layouts)if(registry.get(id) instanceof TerrainCompoundStructure value&&value.biomes().contains(biome))candidates.add(value);
        if(candidates.isEmpty())for(var id:layouts)if(registry.get(id) instanceof TerrainCompoundStructure value)candidates.add(value);
        if(candidates.isEmpty())return Optional.empty();
        return candidates.get(context.random().nextInt(candidates.size())).findGenerationPoint(context);
    }
    @Override public StructureType<?> type(){return StructureWorldgen.CHOICE_COMPOUND.get();}
}
