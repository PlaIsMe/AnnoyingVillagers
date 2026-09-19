package com.pla.annoyingvillagers.world;

import com.mojang.serialization.MapCodec;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public final class AVMobSpawnBiomeModifier implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biomeHolder, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD) {
            return;
        }
        if (!biomeHolder.is(BiomeTags.IS_OVERWORLD)) return;
        AVWorldSpawns.addBiomeSpawns(builder);
    }

    @Override public MapCodec<? extends BiomeModifier> codec() {
        return makeCodec();
    }
    public static MapCodec<AVMobSpawnBiomeModifier> makeCodec() {
        return MapCodec.unit(AVMobSpawnBiomeModifier::new);
    }
}
