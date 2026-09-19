package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.particle.HitParticleType;
import com.pla.annoyingvillagers.client.particle.smoke_wave.SmokeWaveOptions;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class AnnoyingVillagersModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, AnnoyingVillagers.MODID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ELECTRIC_SPARK =
            REGISTRY.register("electric_spark", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARK =
            REGISTRY.register("spark", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PE =
            REGISTRY.register("pe", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GLOWINGEYES =
            REGISTRY.register("glowing_eyes", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHT =
            REGISTRY.register("light", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ENDER =
            REGISTRY.register("ender", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> NULL =
            REGISTRY.register("null", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FULL_COWL =
            REGISTRY.register("full_cowl", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> METEORITE_TRAIL =
            REGISTRY.register("meteorite_trail", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BIG_SPLASH =
            REGISTRY.register("big_splash", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GROUND_SLAM =
            REGISTRY.register("ground_slam", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WHITE_AFTERIMAGE =
            REGISTRY.register("white_afterimage", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, ParticleType<SmokeWaveOptions>> SMOKE_WAVE =
            REGISTRY.register("smoke_wave", () -> new ParticleType<SmokeWaveOptions>(false) {
                @Override
                public @NotNull MapCodec<SmokeWaveOptions> codec() {
                    return SmokeWaveOptions.codec();
                }

                @Override
                public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, SmokeWaveOptions> streamCodec() {
                    return SmokeWaveOptions.STREAM_CODEC;
                }
            });
    public static final DeferredHolder<ParticleType<?>, HitParticleType> HIT_BLUNT =
            REGISTRY.register("hit_blunt", () -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, HitParticleType.ZERO));
}
