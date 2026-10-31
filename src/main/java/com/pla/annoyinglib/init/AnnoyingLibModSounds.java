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


    public static final RegistryObject<SoundEvent> DIAMOND_ATTRACTOR = register("diamond_attractor");
    public static final RegistryObject<SoundEvent> ALEX_SAY = register("alex_say");
    public static final RegistryObject<SoundEvent> BBQ_SAY = register("bbq_say");
    public static final RegistryObject<SoundEvent> BLUE_DEMON_SAY = register("blue_demon_say");
    public static final RegistryObject<SoundEvent> BLUE_DEMON_SAY_TRIDENT_FESTIVAL = register("blue_demon_say_trident_festival");
    public static final RegistryObject<SoundEvent> BLUE_DEMON_SAY_PHASE_2_RELEASE = register("blue_demon_say_phase_2_release");
    public static final RegistryObject<SoundEvent> BLUE_DEMON_SAY_WHEN_RETREAT = register("blue_demon_say_retreat");
    public static final RegistryObject<SoundEvent> CHRIS_SAY_ON_SPAWN = register("chris_say_on_spawn");
    public static final RegistryObject<SoundEvent> JEV_SAY_ON_SPAWN = register("jev_say_on_spawn");
    public static final RegistryObject<SoundEvent> JEV_SAY_WHEN_SUPPORT_ALEX = register("jev_say_when_support_alex");
    public static final RegistryObject<SoundEvent> JEV_SAY_WHEN_ALEX_SECOND_PHASE = register("jev_say_when_alex_second_phase");
















    public static final RegistryObject<SoundEvent> STEVE_SAY_ON_SPAWN = register("steve_say_on_spawn");
    public static final RegistryObject<SoundEvent> ANGRY_STEVE_SAY_ON_SPAWN = register("angry_steve_say_on_spawn");
    public static final RegistryObject<SoundEvent> STEVE_SAY_ON_DEATH = register("steve_say_on_death");
    public static final RegistryObject<SoundEvent> STEVE_SAY_WHAT = register("steve_say_what");
    public static final RegistryObject<SoundEvent> STEVE_SAY_I_NOT_BELIEVE = register("steve_say_i_not_believe");
    public static final RegistryObject<SoundEvent> STEVE_SAY_ON_ATTACK = register("steve_say_on_attack");
    public static final RegistryObject<SoundEvent> STEVE_SAY = register("steve_say");
    public static final RegistryObject<SoundEvent> ANGRY_STEVE_SAY = register("angry_steve_say");
    public static final RegistryObject<SoundEvent> VILLAGER_KNIGHTS_SAY_ON_FIRE = register("villager_knights_say_on_fire");
    public static final RegistryObject<SoundEvent> VILLAGER_KNIGHTS_SAY = register("villager_knights_say");
    public static final RegistryObject<SoundEvent> VILLAGER_SCOUTS_SAY_ON_FIRE = register("villager_scouts_say_on_fire");
    public static final RegistryObject<SoundEvent> VILLAGER_SCOUTS_SAY = register("villager_scouts_say");
    public static final RegistryObject<SoundEvent> ZOMBIE_SAY = register("zombie_say");

    public static final RegistryObject<SoundEvent> ELECTRIFY = register("electrify");
    public static final RegistryObject<SoundEvent> ELECTRIC_SHOOT = register("electric_shoot");
    public static final RegistryObject<SoundEvent> OB_PLACE = register("ob_place");
    public static final RegistryObject<SoundEvent> CLASH = register("clash");
    public static final RegistryObject<SoundEvent> SWORD_WHOOSH = register("sword_whoosh");
    public static final RegistryObject<SoundEvent> WHOOSH_SHARP = register("whoosh_sharp");
    public static final RegistryObject<SoundEvent> WHOOSH = register("whoosh");
    public static final RegistryObject<SoundEvent> BLADE_HIT = register("blade_hit");
    public static final RegistryObject<SoundEvent> BLUNT_HIT = register("blunt_hit");
    public static final RegistryObject<SoundEvent> ROLL = register("roll");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_LEGENDARY_SWORD = register("heavy_attack_legendary_sword");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_LEGENDARY_SWORD_2 = register("heavy_attack_legendary_sword_2");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_START = register("heavy_attack_start");
    public static final RegistryObject<SoundEvent> HARD_GREATSWORD_SKILL = register("hard_greatsword_skill");;
    public static final RegistryObject<SoundEvent> OBSIDIAN_PLACE = register("obsidian_place");
    public static final RegistryObject<SoundEvent> POP = register("pop");
    public static final RegistryObject<SoundEvent> OBSIDIAN_HIT = register("obsidian_hit");
    public static final RegistryObject<SoundEvent> HEAVY_HIT = register("heavy_hit");
    public static final RegistryObject<SoundEvent> ENDER_SHOT = register("ender_shot");











    public static final RegistryObject<SoundEvent> WOOPIE_WIND = register("woopie_wind");




    public static final RegistryObject<SoundEvent> MUFFLED_BOOM = register("muffled_boom");
    public static final RegistryObject<SoundEvent> BLACK_FIRE = register("black_fire");




    public static final RegistryObject<SoundEvent> GROUND_SLAM = register("ground_slam");




    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, name)));
    }

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
