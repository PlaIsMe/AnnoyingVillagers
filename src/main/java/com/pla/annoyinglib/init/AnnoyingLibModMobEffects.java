package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class AnnoyingLibModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AnnoyingLib.MODID);

    public static final RegistryObject<MobEffect> CAPTIVE = AnnoyingLibModMobEffects.REGISTRY.register("captive", CaptiveMobEffect::new);

    public static final RegistryObject<MobEffect> OBEDIENCE = AnnoyingLibModMobEffects.REGISTRY.register("obedience", ObedienceMobEffect::new);
}
