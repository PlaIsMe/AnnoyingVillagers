package com.pla.annoyingvillagers.skill;

import com.google.common.collect.Lists;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import reascer.wom.gameasset.animations.weapons.AnimsEnderblaster;
import reascer.wom.gameasset.animations.weapons.AnimsMoonless;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.UUID;

public class BedrockWeaponSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("64062d4e-095e-468b-a25a-12811e92fd73");

    public BedrockWeaponSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public static void onParry(ServerPlayerPatch serverPlayerPatch) {
        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.BEDROCK_WEAPON);
        if (skillContainer == null) return;
        BedrockWeaponSkill bedrockWeaponSkill = (BedrockWeaponSkill) skillContainer.getSkill();

        if (!skillContainer.isActivated() && skillContainer.getStack() < 1) {
            float currentResource = skillContainer.getResource();
            float neededResource = skillContainer.getNeededResource();
            float addResource = Math.min(2.0F, neededResource);
            bedrockWeaponSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
        } else if (skillContainer.isActivated()) {
            bedrockWeaponSkill.setDurationSynchronize(skillContainer, skillContainer.getRemainDuration() + 40);
        }
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!skillContainer.isActivated()) {
            skillContainer.getExecutor().playAnimationSynchronized(AnimsEnderblaster.ENDERBLASTER_ONEHAND_RELOAD, 0.0F);
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
        }
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
        container.getExecutor().getEventListener().addEventListener(EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID, (pre) -> {
            PlayerPatch<?> playerPatch = pre.getPlayerPatch();
            ServerPlayer serverPlayer = pre.getPlayerPatch().getOriginal();
            DamageSource damageSource = pre.getDamageSource();
            SkillContainer skillContainer = pre.getPlayerPatch().getSkill(this);
            if (skillContainer == null) return;

            if (!damageSource.is(DamageTypes.MAGIC) && !damageSource.is(DamageTypes.EXPLOSION)
                    && !damageSource.is(DamageTypes.ON_FIRE) && !damageSource.is(DamageTypes.IN_FIRE) && !damageSource.is(DamageTypes.FALL)
                    && skillContainer.isActivated()) {
                Entity entity = damageSource.getEntity();
                if (entity != null) {
                    pre.setCanceled(true);
                    pre.setResult(AttackResult.ResultType.BLOCKED);
                    playerPatch.playAnimationSynchronized(AnimsMoonless.MOONLESS_GUARD_HIT_1, 0.0F);
                    entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.2, 0.0F, entity.getLookAngle().z * -0.2));
                    serverPlayer.setDeltaMovement(new Vec3(serverPlayer.getLookAngle().x * -0.2, 0.0F, serverPlayer.getLookAngle().z * -0.2));
                    serverPlayer.heal(2.0F);
                    if (serverPlayer.level() instanceof ServerLevel serverLevel) {
                        EpicfightUtil.damageBlocked(damageSource, serverPlayer, serverLevel);
                    }
                }
            }

        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
    }
}
