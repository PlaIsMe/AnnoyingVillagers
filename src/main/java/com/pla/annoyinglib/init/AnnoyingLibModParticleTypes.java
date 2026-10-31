package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.client.particle.HitParticleType;
import com.pla.annoyinglib.client.particle.smoke_wave.SmokeWaveOptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class AnnoyingLibModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AnnoyingLib.MODID);
    public static final RegistryObject<SimpleParticleType> ELECTRIC_SPARK =
            REGISTRY.register("electric_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPARK =
            REGISTRY.register("spark", () -> new SimpleParticleType(true));







    public static final RegistryObject<SimpleParticleType> BIG_SPLASH =
            REGISTRY.register("big_splash", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GROUND_SLAM =
            REGISTRY.register("ground_slam", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WHITE_AFTERIMAGE =
            REGISTRY.register("white_afterimage", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SmokeWaveOptions>> SMOKE_WAVE =
            REGISTRY.register("smoke_wave", () -> new ParticleType<SmokeWaveOptions>(false, SmokeWaveOptions.DESERIALIZER) {
                @Override
                public @NotNull Codec<SmokeWaveOptions> codec() {
                    return SmokeWaveOptions.codec();
                }
            });
    public static final RegistryObject<HitParticleType> HIT_BLUNT =
            REGISTRY.register("hit_blunt", () -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, HitParticleType.ZERO));
}