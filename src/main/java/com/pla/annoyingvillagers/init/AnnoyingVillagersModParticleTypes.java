package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.particle.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
}
