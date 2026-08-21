package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.particle.HitParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AnnoyingVillagers.MODID);
    public static final RegistryObject<SimpleParticleType> ELECTRIC_SPARK =
            REGISTRY.register("electric_spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPARK =
            REGISTRY.register("spark", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PE =
            REGISTRY.register("pe", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GLOWINGEYES =
            REGISTRY.register("glowing_eyes", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LIGHT =
            REGISTRY.register("light", () -> new SimpleParticleType(true));
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
    public static final RegistryObject<SimpleParticleType> GROUND_SLAM =
            REGISTRY.register("ground_slam", () -> new SimpleParticleType(true));
    public static final RegistryObject<HitParticleType> HIT_BLUNT =
            REGISTRY.register("hit_blunt", () -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, HitParticleType.ZERO));
}
