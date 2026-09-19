package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;


public class AnnoyingVillagersModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AnnoyingVillagers.MODID);
    public static final DeferredHolder<MobEffect, MobEffect> ENCHANT_BED_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("enchant_bed_effect", EnchantBedEffectMobEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIFY = AnnoyingVillagersModMobEffects.REGISTRY.register("electrify", ElectrifyMobEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> GROUND_STUCK = AnnoyingVillagersModMobEffects.REGISTRY.register("ground_stuck", GroundStuckMobEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> CAPTIVE = AnnoyingVillagersModMobEffects.REGISTRY.register("captive", CaptiveMobEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> HEROBRINE = AnnoyingVillagersModMobEffects.REGISTRY.register("herobrine", HerobrineMobEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> OBEDIENCE = AnnoyingVillagersModMobEffects.REGISTRY.register("obedience", ObedienceMobEffect::new);
}
