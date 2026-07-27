package com.pla.annoyingvillagers.event;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.skill.weaponinnate.DemonicAscensionSkill;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mod.EventBusSubscriber
public class WomFixEvent {
    @SubscribeEvent
    public static void fixAntitheusCrash(TickEvent.PlayerTickEvent playerTickEvent) {
        if (!playerTickEvent.side.isServer()) return;

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(playerTickEvent.player, PlayerPatch.class);
        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;

        SkillContainer skillContainer = serverPlayerPatch.getSkill(WOMSkills.DEMONIC_ASCENSION);
        if (skillContainer == null || !(skillContainer.getSkill() instanceof DemonicAscensionSkill demonicAscensionSkill)) return;

        if (!skillContainer.isActivated()) return;

        boolean holdingAntitheus = playerTickEvent.player.getMainHandItem().is(WOMItems.ANTITHEUS.get());
        if (holdingAntitheus) return;
        demonicAscensionSkill.cancelOnServer(skillContainer, null);
        skillContainer.deactivate();
        if (serverPlayerPatch.getAnimator() != null) {
            serverPlayerPatch.getAnimator().stopPlaying(null);
        }
    }
}
