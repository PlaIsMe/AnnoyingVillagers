package com.pla.annoyinglib.world;

import com.mojang.serialization.Codec;
import com.pla.annoyinglib.AnnoyingLib;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AVMobSpawnBiomeModifier implements BiomeModifier {

    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject
            .create(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "av_mob_spawns"),
                    ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, AnnoyingLib.MODID);

    @Override
    public void modify(Holder<Biome> biomeHolder, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD) {
            return;
        }
        if (!biomeHolder.is(BiomeTags.IS_OVERWORLD)) return;
        AVWorldSpawns.addBiomeSpawns(builder);
    }

    @Override public Codec<? extends BiomeModifier> codec() {
        return SERIALIZER.get();
    }
    public static Codec<AVMobSpawnBiomeModifier> makeCodec() {
        return Codec.unit(AVMobSpawnBiomeModifier::new);
    }
}
