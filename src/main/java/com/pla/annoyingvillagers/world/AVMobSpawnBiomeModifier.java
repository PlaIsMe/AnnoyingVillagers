package com.pla.annoyingvillagers.world;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public final class AVMobSpawnBiomeModifier implements BiomeModifier {
    // Registry dispatch must receive the same codec instance that was registered.
    private static final MapCodec<AVMobSpawnBiomeModifier> CODEC = MapCodec.unit(AVMobSpawnBiomeModifier::new);

    @Override
    public void modify(Holder<Biome> biomeHolder, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD) {
            return;
        }
        if (!biomeHolder.is(BiomeTags.IS_OVERWORLD)) return;
        AVWorldSpawns.addBiomeSpawns(builder);
    }

    @Override public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
    public static MapCodec<AVMobSpawnBiomeModifier> makeCodec() {
        return CODEC;
    }
}
