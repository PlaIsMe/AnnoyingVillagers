package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsAVSword;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.TakeDamageEvent;

import java.util.Objects;
import java.util.UUID;

public class GreatSwordSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("5a6ceb12-eacb-49c6-8030-37942b192e1d");

    private static final float FRONT_DOT_THRESHOLD = 0.25f;
    private static final float COUNTER_DAMAGE = 6.0f;
    private static final double KNOCKBACK_STRENGTH = 1.0;

    public GreatSwordSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        final LivingEntity livingEntity = skillContainer.getExecutor().getOriginal();
        final ServerLevel serverLevel = (ServerLevel) livingEntity.level();
        if (skillContainer.isActivated()) {
            this.cancelOnServer(skillContainer, friendlyByteBuf);
        } else {
            skillContainer.getExecutor().playAnimationSynchronized(AnimsAVSword.GREAT_SWORD_INNATE, 0.0F);
            livingEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2));

            new DelayedTask(4) {
                @Override public void run() {
                    serverLevel.sendParticles(
                            AnnoyingVillagersModParticleTypes.RED_SPARK.get(),
                            livingEntity.getX(), livingEntity.getY() + 1.5, livingEntity.getZ() + 1,
                            35, 0.0, 0.0, 0.0, 0.6
                    );
                    serverLevel.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                            AnnoyingVillagersModSounds.HARD_GREATSWORD_SKILL.get(),
                            SoundSource.NEUTRAL, 1.0F, 1.0F);
                }
            };
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
        }
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        super.cancelOnServer(container, args);
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        if (container.isActivated()) return true;
        return super.canExecute(container);
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
                PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID,
                (TakeDamageEvent.Attack event) -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;

                    final PlayerPatch<?> playerPatch = event.getPlayerPatch();
                    final Player defender = playerPatch.getOriginal();

                    AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                    if (dynamicAnimation == null || dynamicAnimation != AnimsAVSword.GREAT_SWORD_INNATE) return;

                    Entity attacker = event.getDamageSource().getEntity();
                    if (!(attacker instanceof LivingEntity livingEntity) || !attacker.isAlive()) return;

                    Vec3 fwd = defender.getViewVector(1.0F).normalize();
                    Vec3 toAttacker = attacker.position().subtract(defender.position()).normalize();
                    if (fwd.dot(toAttacker) <= FRONT_DOT_THRESHOLD) return;

                    LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
                    if (livingEntityPatch != null) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.STUN_BACK, 0.0F);
                    }
                    livingEntity.knockback(KNOCKBACK_STRENGTH, defender.getX() - attacker.getX(), defender.getZ() - attacker.getZ());

                    if (container.getStack() < 1) {
                        GreatSwordSkill greatSwordSkill = (GreatSwordSkill) container.getSkill();
                        float currentResource = container.getResource();
                        float neededResource = container.getNeededResource();
                        float addResource = Math.min(20.0F, neededResource);
                        greatSwordSkill.setConsumptionSynchronize(container, currentResource + addResource);
                    }

                    attacker.hurtMarked = true;
                    attacker.hurt(defender.damageSources().playerAttack(defender), COUNTER_DAMAGE);
                    event.setCanceled(true);
                    event.setResult(AttackResult.ResultType.BLOCKED);
                },
                10
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
        super.onRemoved(container);
    }
}
