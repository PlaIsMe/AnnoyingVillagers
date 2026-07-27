package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Objects;
import java.util.UUID;

public class EnderAegisSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("348aa19d-7c78-4959-9639-00c467ed258d");

    public EnderAegisSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public static void onParry(ServerPlayerPatch serverPlayerPatch) {
        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_AEGIS);
        if (skillContainer == null) return;
        EnderAegisSkill enderAegisSkill = (EnderAegisSkill) skillContainer.getSkill();

        if (!skillContainer.isActivated() && skillContainer.getStack() < 1) {
            float currentResource = skillContainer.getResource();
            float neededResource = skillContainer.getNeededResource();
            float addResource = Math.min(5.0F, neededResource);
            enderAegisSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
        } else if (skillContainer.isActivated()) {
            enderAegisSkill.setDurationSynchronize(skillContainer, skillContainer.getRemainDuration() + 40);
        }
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!skillContainer.isActivated()) {
            skillContainer.getExecutor().playAnimationSynchronized(AnimsWom.ENDER_AEGIS_NAPOLEON_RELOAD_1, 0.0F);
            skillContainer.getExecutor().getOriginal().playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(), 0.5F, 1.0F);
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
        container.getExecutor().getEventListener().addEventListener(
                EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    ItemStack itemStack = event.getPlayerPatch().getOriginal().getMainHandItem();
                    if (skillContainer == null) return;

                    if (skillContainer.isActivated()
                            && itemStack.getTag() != null) {
                        event.setCanceled(true);
                        if (event.getPlayerPatch().getOriginal().getCooldowns().getCooldownPercent(itemStack.getItem(), 0) == 0) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsEpicFight.AEGIS_SHIELD_SHOOT, 0.0F);
                        }
                    }
                }
        );

        container.getExecutor().getEventListener().addEventListener(EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID, (pre) -> {
            PlayerPatch<?> playerPatch = pre.getPlayerPatch();
            ServerPlayer serverPlayer = pre.getPlayerPatch().getOriginal();
            DamageSource damageSource = pre.getDamageSource();
            SkillContainer skillContainer = pre.getPlayerPatch().getSkill(this);
            if (skillContainer == null) return;
            EnderAegisSkill enderAegisSkill = (EnderAegisSkill) skillContainer.getSkill();
            AnimationPlayer animationPlayer = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null));
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
            float elapsedTimeFloat = animationPlayer.getElapsedTime();
            EntityState entityState = dynamicAnimation.get().getState(playerPatch, elapsedTimeFloat);

            if (!damageSource.is(DamageTypes.MAGIC) && !damageSource.is(DamageTypes.EXPLOSION)
                    && !damageSource.is(DamageTypes.ON_FIRE) && !damageSource.is(DamageTypes.IN_FIRE) && !damageSource.is(DamageTypes.FALL)
                    && skillContainer.isActivated() && dynamicAnimation == AnimsEpicFight.AEGIS_SHIELD_SHOOT && entityState.getLevel() < 3) {
                Entity entity = damageSource.getEntity();
                if (entity != null) {
                    Vec3 entityPosition = entity.position();
                    Vec3 entityViewVector = pre.getPlayerPatch().getOriginal().getViewVector(1.0F);
                    Vec3 entitySubtract = entityPosition.subtract(pre.getPlayerPatch().getOriginal().getEyePosition()).normalize();
                    if (entitySubtract.dot(entityViewVector) > (double)0.0F) {
                        pre.setCanceled(true);
                        pre.setResult(AttackResult.ResultType.BLOCKED);
                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.2, 0.0F, entity.getLookAngle().z * -0.2));
                        serverPlayer.setDeltaMovement(new Vec3(serverPlayer.getLookAngle().x * -0.2, 0.0F, serverPlayer.getLookAngle().z * -0.2));
                        if (serverPlayer.level() instanceof ServerLevel serverLevel) {
                            EpicfightUtil.damageBlocked(damageSource, serverPlayer, serverLevel);
                        }
                        enderAegisSkill.setDurationSynchronize(skillContainer, skillContainer.getRemainDuration() + 40);
                    }
                }
            }

        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
    }
}
