package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.particle.*;
import com.pla.annoyingvillagers.client.particle.ground_slam.AwakenGroundSlamOptions;
import com.pla.annoyingvillagers.client.particle.smoke_wave.AwakenSmokeWaveOptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class AnnoyingVillagersModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AnnoyingVillagers.MODID);
    public static final RegistryObject<SimpleParticleType> RED_SPARK =
            REGISTRY.register("red_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DRAGON_SPARK =
            REGISTRY.register("dragon_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ELECTRIC_SPARK =
            REGISTRY.register("electric_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ELECTRIC_SPARK_2 =
            REGISTRY.register("electric_spark_2", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPARK =
            REGISTRY.register("spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PE =
            REGISTRY.register("pe", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GLOWINGEYES =
            REGISTRY.register("glowing_eyes", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LIGHT =
            REGISTRY.register("light", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BLUESPARK =
            REGISTRY.register("blue_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GREENSPARK =
            REGISTRY.register("green_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ENDER =
            REGISTRY.register("ender", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> NULL =
            REGISTRY.register("null", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FULL_COWL =
            REGISTRY.register("full_cowl", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> METEORITE_TRAIL =
            REGISTRY.register("meteorite_trail", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BIG_SPLASH =
            REGISTRY.register("big_splash", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FIREBALL =
        REGISTRY.register("fireball", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ELECTRIC_LITE =
            REGISTRY.register("electric_lite", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<AwakenSmokeWaveOptions>> AWAKEN_SMOKE_WAVE =
            REGISTRY.register("awaken_smoke_wave", () -> new ParticleType<AwakenSmokeWaveOptions>(false, AwakenSmokeWaveOptions.DESERIALIZER) {
                @Override
                public @NotNull Codec<AwakenSmokeWaveOptions> codec() {
                    return AwakenSmokeWaveOptions.codec();
                }
            });
    public static final RegistryObject<ParticleType<AwakenGroundSlamOptions>> AWAKEN_GROUND_SLAM =
            REGISTRY.register("awaken_ground_slam", () -> new ParticleType<AwakenGroundSlamOptions>(false, AwakenGroundSlamOptions.DESERIALIZER) {
                @Override
                public @NotNull Codec<AwakenGroundSlamOptions> codec() {
                    return AwakenGroundSlamOptions.codec();
                }
            });
    public static final RegistryObject<ParticleType<AwakenJointFollowParticleOptions>> AWAKEN_AIRFLOW =
            REGISTRY.register("awaken_airflow", () -> new ParticleType<AwakenJointFollowParticleOptions>(false, AwakenJointFollowParticleOptions.DESERIALIZER) {
                @Override
                public @NotNull Codec<AwakenJointFollowParticleOptions> codec() {
                    return AwakenJointFollowParticleOptions.codec(this);
                }
            });
}
