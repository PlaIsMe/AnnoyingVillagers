package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingLibModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AnnoyingLib.MODID);

    public static final RegistryObject<SoundEvent> ZOMBIE_SAY = register("zombie_say");

    
    public static final RegistryObject<SoundEvent> ELECTRIC_SHOOT = register("electric_shoot");
    public static final RegistryObject<SoundEvent> OB_PLACE = register("ob_place");
    public static final RegistryObject<SoundEvent> CLASH = register("clash");
    public static final RegistryObject<SoundEvent> SWORD_WHOOSH = register("sword_whoosh");
    public static final RegistryObject<SoundEvent> WHOOSH_SHARP = register("whoosh_sharp");
    public static final RegistryObject<SoundEvent> WHOOSH = register("whoosh");
    public static final RegistryObject<SoundEvent> BLADE_HIT = register("blade_hit");
    public static final RegistryObject<SoundEvent> BLUNT_HIT = register("blunt_hit");
    public static final RegistryObject<SoundEvent> ROLL = register("roll");
    
    
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_START = register("heavy_attack_start");
    public static final RegistryObject<SoundEvent> HARD_GREATSWORD_SKILL = register("hard_greatsword_skill");;
    public static final RegistryObject<SoundEvent> OBSIDIAN_PLACE = register("obsidian_place");
    public static final RegistryObject<SoundEvent> POP = register("pop");
    public static final RegistryObject<SoundEvent> OBSIDIAN_HIT = register("obsidian_hit");
    public static final RegistryObject<SoundEvent> HEAVY_HIT = register("heavy_hit");
    public static final RegistryObject<SoundEvent> ENDER_SHOT = register("ender_shot");

    public static final RegistryObject<SoundEvent> MUFFLED_BOOM = register("muffled_boom");

    public static final RegistryObject<SoundEvent> GROUND_SLAM = register("ground_slam");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, name)));
    }

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }

public static final RegistryObject<SoundEvent> DIAMOND_ATTRACTOR = register("diamond_attractor");
}
