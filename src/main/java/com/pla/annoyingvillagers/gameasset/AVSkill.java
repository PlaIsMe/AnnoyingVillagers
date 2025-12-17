package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.dual_greatsword.skill.DualGreatswordSkill;
import com.pla.annoyingvillagers.compat.dual_greatsword.skill.EarthquakeSkill;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.item.EpicFightCreativeTabs;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AVSkill {
    public static Skill EARTHQUAKE;
    public static Skill DUALGREATSWORD;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        SkillBuildEvent.ModRegistryWorker modRegistry = skillbuildevent.createRegistryWorker(AnnoyingVillagers.MODID);
        AVSkill.EARTHQUAKE = modRegistry.build("earthquake", EarthquakeSkill::new, EarthquakeSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));
        AVSkill.DUALGREATSWORD = modRegistry.build("dualgreatsword", DualGreatswordSkill::new, DualGreatswordSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));
    }
}
