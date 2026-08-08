package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightACG;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.item.WoopieTheSwordItem;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.network.ClientboundWoopieSwordWindFx;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.DealDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Objects;
import java.util.UUID;

public class WoopieTheSwordSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("5a6ceb12-eacb-49c6-8030-37942b192e1d");
    public WoopieTheSwordSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            skillContainer.getExecutor().playAnimationSynchronized(AnimsEpicFightACG.SAO_DUAL_SWORD_AUTO12, 0.0F);
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        skillContainer.deactivate();
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID,
                (DealDamageEvent.Damage event) -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;

                    final PlayerPatch<?> playerPatch = event.getPlayerPatch();
                    AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                    if (dynamicAnimation == null) return;

                    if (dynamicAnimation == AnimsPugilistSteve.RUSH_SWORD && container.getStack() < 1) {
                        WoopieTheSwordSkill woopieTheSwordSkill = (WoopieTheSwordSkill) container.getSkill();
                        float currentResource = container.getResource();
                        float neededResource = container.getNeededResource();
                        woopieTheSwordSkill.setConsumptionSynchronize(container, currentResource + neededResource);
                    }
                },
                10
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
        super.onRemoved(container);
    }
}
