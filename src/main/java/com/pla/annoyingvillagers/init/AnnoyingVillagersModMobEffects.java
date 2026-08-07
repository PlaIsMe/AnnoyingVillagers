package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class AnnoyingVillagersModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AnnoyingVillagers.MODID);
    public static final RegistryObject<MobEffect> ENCHANT_BED_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("enchant_bed_effect", EnchantBedEffectMobEffect::new);
    public static final RegistryObject<MobEffect> ELECTRIFY = AnnoyingVillagersModMobEffects.REGISTRY.register("electrify", ElectrifyMobEffect::new);
    public static final RegistryObject<MobEffect> CAPTIVE = AnnoyingVillagersModMobEffects.REGISTRY.register("captive", CaptiveMobEffect::new);
    public static final RegistryObject<MobEffect> HEROBRINE = AnnoyingVillagersModMobEffects.REGISTRY.register("herobrine", HerobrineMobEffect::new);
    public static final RegistryObject<MobEffect> OBEDIENCE = AnnoyingVillagersModMobEffects.REGISTRY.register("obedience", ObedienceMobEffect::new);
    public static final RegistryObject<MobEffect> GROUND_STUCK = AnnoyingVillagersModMobEffects.REGISTRY.register("ground_stuck", GroundStuckMobEffect::new);
}
