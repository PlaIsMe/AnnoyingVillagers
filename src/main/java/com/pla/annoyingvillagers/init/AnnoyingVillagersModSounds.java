package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AnnoyingVillagers.MODID);

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
    public static final RegistryObject<SoundEvent> ARMORED_HEROBRINE_SAY_ON_DEATH = register("armored_herobrine_say_on_death");
    public static final RegistryObject<SoundEvent> ARMORED_HEROBRINE_SAY = register("armored_herobrine_say");
    public static final RegistryObject<SoundEvent> INFECTED_THE_MOSTMOISTBURRIT0_SAY_ON_SPAWN = register("infected_the_most_moisburrit0_say_on_spawn");
    public static final RegistryObject<SoundEvent> ELITE_HEROBRINE_SAY = register("elite_herobrine_say");
    public static final RegistryObject<SoundEvent> ELITE_HEROBRINE_WEAPON_SCREAMING = register("elite_herobrine_weapon_screaming");
    public static final RegistryObject<SoundEvent> ELITE_HEROBRINE_SAY_SECOND_FORM_RELEASE = register("elite_herobrine_say_second_form_release");
    public static final RegistryObject<SoundEvent> KNOCKED_ELITE_HEROBRINE_SAY_ON_SPAWN = register("knocked_elite_herobrine_say_on_spawn");
    public static final RegistryObject<SoundEvent> KNOCKED_ELITE_HEROBRINE_SAY_ON_BEING_EATEN = register("knocked_elite_herobrine_say_on_being_eaten");
    public static final RegistryObject<SoundEvent> HEROBRINE_CLONE_SAY_ON_SPAWN = register("herobrine_clone_say_on_spawn");
    public static final RegistryObject<SoundEvent> HEROBRINE_CLONE_SAY_ON_HURT = register("herobrine_clone_say_on_hurt");
    public static final RegistryObject<SoundEvent> HEROBRINE_CLONE_SAY_ON_DEATH = register("herobrine_clone_say_on_death");
    public static final RegistryObject<SoundEvent> HEROBRINE_CLONE_SAY = register("herobrine_clone_say");
    public static final RegistryObject<SoundEvent> NULL_SAY = register("null_say");
    public static final RegistryObject<SoundEvent> SHADOW_HEROBRINE_SAY_OBSIDIAN_MACHINE_GUN = register("shadow_herobrine_say_obsidian_machine_gun");
    public static final RegistryObject<SoundEvent> SHADOW_HEROBRINE_SAY_ON_PHASE_2 = register("shadow_herobrine_say_on_phase_2");
    public static final RegistryObject<SoundEvent> SHADOW_HEROBRINE_SAY_ON_SPAWN = register("shadow_herobrine_say_on_spawn");
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
    public static final RegistryObject<SoundEvent> BEAM_BREATH = register("beam_breath");
    public static final RegistryObject<SoundEvent> PORTAL_SUMMON = register("portal_summon");
    public static final RegistryObject<SoundEvent> PORTAL_NATURAL = register("portal_natural");
    public static final RegistryObject<SoundEvent> PORTAL_ENTER = register("portal_enter");
    public static final RegistryObject<SoundEvent> PORTAL_EXIT = register("portal_exit");
    public static final RegistryObject<SoundEvent> PORTAL_OPEN = register("portal_open");
    public static final RegistryObject<SoundEvent> PORTAL_FIZZLE = register("portal_fizzle");
    public static final RegistryObject<SoundEvent> PORTAL_AMBIENT = register("portal_ambient");
    public static final RegistryObject<SoundEvent> SELF_REQUESTING_ASSISTANCE = register("self_requesting_assistance");
    public static final RegistryObject<SoundEvent> GREG_REQUESTING_ASSISTANCE = register("greg_requesting_assistance");
    public static final RegistryObject<SoundEvent> HEROBRINE_UNDERSTOOD = register("herobrine_understood");
    public static final RegistryObject<SoundEvent> WOOPIE_WIND = register("woopie_wind");
    public static final RegistryObject<SoundEvent> DRAGON_AMBIENT_SOUND = register("entity.dragon.ambient");
    public static final RegistryObject<SoundEvent> DRAGON_STEP_SOUND = register("entity.dragon.step");
    public static final RegistryObject<SoundEvent> DRAGON_DEATH_SOUND = register("entity.dragon.death");
    public static final RegistryObject<SoundEvent> DRAGON_THUNDER_SHOOT_SOUND = register("entity.dragon.thunder_shoot");
    public static final RegistryObject<SoundEvent> MUFFLED_BOOM = register("muffled_boom");
    public static final RegistryObject<SoundEvent> BLACK_FIRE = register("black_fire");
    public static final RegistryObject<SoundEvent> DIAMOND_ATTRACTOR = register("diamond_attractor");
    public static final RegistryObject<SoundEvent> REAPER_FIRE = register("reaper_fire");
    public static final RegistryObject<SoundEvent> GROUND_SLAM = register("ground_slam");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, name)));
    }

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
