package com.pla.annoyingvillagers.specialanimation;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.GolemArms;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DestructionEyeItem;
import com.pla.annoyingvillagers.network.ClientboundSpecialAnimation;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class SpecialAnimationController {
    private static final Map<UUID, ActiveAnimationState> ACTIVE = new HashMap<>();

    private SpecialAnimationController() {
    }

    public static boolean play(Mob mob, SpecialAnimationId animationId, LivingEntity target) {
        return play(mob, SpecialAnimationSpecs.get(animationId), target);
    }

    public static boolean play(Mob mob, SpecialAnimationSpec spec, LivingEntity target) {
        if (mob.level().isClientSide || !mob.isAlive() || mob.isRemoved() || hasActiveAnimation(mob)) return false;
        if (target != null && target.isAlive()) faceTarget(mob, target);
        if (spec.attackWindows().length > 0) {
            mob.swing(InteractionHand.MAIN_HAND, true);
            if (!(mob instanceof GolemArms)) playSwingSound(mob);
        }
        ActiveAnimationState state = new ActiveAnimationState(mob, spec, mob.tickCount);
        ACTIVE.put(mob.getUUID(), state);
        sendAnimation(mob, spec.animationId(), spec.durationTicks());
        scheduleEnd(mob, state);
        scheduleHooks(mob, state);
        scheduleCollisions(mob, state);
        return true;
    }

    public static boolean hasActiveAnimation(Mob mob) {
        return getState(mob) != null;
    }

    public static SpecialAnimationId getActiveAnimationId(Mob mob) {
        ActiveAnimationState state = getState(mob);
        return state == null ? null : state.spec().animationId();
    }

    public static int remainingTicks(Mob mob) {
        ActiveAnimationState state = getState(mob);
        if (state == null) return 0;
        return Math.max(0, state.spec().durationTicks() - state.elapsedTicks(mob));
    }

    public static void clear(Mob mob) {
        ActiveAnimationState state = ACTIVE.remove(mob.getUUID());
        if (state != null && state.mob() == mob && !mob.level().isClientSide) sendAnimation(mob, state.spec().animationId(), 0);
    }

    public static void clearActiveAnimations() {
        ACTIVE.clear();
    }

    private static ActiveAnimationState getState(Mob mob) {
        ActiveAnimationState state = ACTIVE.get(mob.getUUID());
        if (state == null) return null;
        if (state.mob() != mob || state.elapsedTicks(mob) < 0 || state.elapsedTicks(mob) > state.spec().durationTicks()) {
            ACTIVE.remove(mob.getUUID(), state);
            return null;
        }
        return state;
    }

    private static void scheduleEnd(Mob mob, ActiveAnimationState state) {
        if (state.spec().animationId() == SpecialAnimationId.ARMS_GUARD) return;
        new DelayedTask(state.spec().durationTicks()) {
            @Override
            public void run() {
                ACTIVE.remove(mob.getUUID(), state);
            }
        };
    }

    private static void scheduleHooks(Mob mob, ActiveAnimationState state) {
        for (SpecialAnimationSpec.TimedHook hook : state.spec().timedHooks()) {
            new DelayedTask(hook.tick()) {
                @Override
                public void run() {
                    if (!isCurrent(mob, state) || !mob.isAlive() || mob.isRemoved()) return;
                    hook.hook().run(mob);
                }
            };
        }
    }

    private static void scheduleCollisions(Mob mob, ActiveAnimationState state) {
        boolean multiWindow = state.spec().attackWindows().length > 1;
        for (SpecialAttackWindow window : state.spec().attackWindows()) scheduleWindow(mob, state, window, multiWindow);
    }

    private static void scheduleWindow(Mob mob, ActiveAnimationState state, SpecialAttackWindow window, boolean multiWindow) {
        if (mob instanceof GolemArms) {
            new DelayedTask(window.startTickInclusive()) {
                @Override
                public void run() {
                    if (!isCurrent(mob, state) || !mob.isAlive() || mob.isRemoved()) return;
                    playSwingSound(mob);
                }
            };
        }
        Set<UUID> hit = new HashSet<>();
        for (int elapsed = window.startTickInclusive(); elapsed < window.endTickExclusive(); elapsed++) {
            int tick = elapsed;
            new DelayedTask(tick) {
                @Override
                public void run() {
                    if (!isCurrent(mob, state) || !mob.isAlive() || mob.isRemoved()) return;
                    for (LivingEntity target : SpecialColliderSystem.findHits(mob, state.spec().animationId(), window, tick)) {
                        if (!canDamage(mob, target) || !hit.add(target.getUUID())) continue;
                        hurt(mob, target, state.spec().damageMultiplier(), multiWindow);
                    }
                }
            };
        }
    }

    private static void hurt(Mob mob, LivingEntity target, float multiplier, boolean resetHurtCooldown) {
        LivingEntity owner = mob instanceof GolemArms arms ? arms.getOwnerLiving() : mob;
        if (owner == null) owner = mob;
        LivingEntity damageOwner = mob instanceof GolemArms && owner != null ? owner : mob;
        double baseDamage = damageOwner.getAttribute(Attributes.ATTACK_DAMAGE) == null ? 1.0D : damageOwner.getAttributeValue(Attributes.ATTACK_DAMAGE);
        DamageSource source = owner instanceof ServerPlayer player ? mob.level().damageSources().playerAttack(player) : mob.level().damageSources().mobAttack(mob);
        int previousInvulnerableTime = target.invulnerableTime;
        if (resetHurtCooldown) target.invulnerableTime = 0;
        boolean hurt = target.hurt(source, (float)(baseDamage * multiplier));
        if (resetHurtCooldown) target.invulnerableTime = previousInvulnerableTime;
        if (!hurt) return;
        if (mob instanceof GolemArms arms) DestructionEyeItem.damageForArmsHit(arms);
        mob.setLastHurtMob(target);
        if (owner instanceof Mob ownerMob) {
            ownerMob.setLastHurtMob(target);
            target.setLastHurtByMob(ownerMob);
        }
        Vec3 away = target.position().subtract(mob.position());
        if (away.horizontalDistanceSqr() > 1.0E-6D) target.knockback(0.35D + 0.1D * multiplier, -away.x, -away.z);
        mob.level().playSound(null, target.getX(), target.getY(), target.getZ(), AnnoyingVillagersModSounds.BLUNT_HIT.get(), SoundSource.HOSTILE, 0.9F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
    }

    private static void playSwingSound(Mob mob) {
        SoundSource source = mob instanceof GolemArms ? SoundSource.PLAYERS : SoundSource.HOSTILE;
        float volume = mob instanceof GolemArms ? 1.2F : 1.0F;
        float pitch = 0.85F + mob.getRandom().nextFloat() * 0.15F;
        mob.level().playSound(null, mob.getX(), mob.getY(), mob.getZ(), AnnoyingVillagersModSounds.WHOOSH.get(), source, volume, pitch);
    }

    private static boolean canDamage(Mob mob, LivingEntity target) {
        if (!target.isAlive() || target.isRemoved() || target.isDeadOrDying()) return false;
        LivingEntity owner = mob instanceof GolemArms arms ? arms.getOwnerLiving() : mob;
        if (target == owner || target == mob || target == mob.getVehicle()) return false;
        if (owner != null && areAllied(owner, target)) return false;
        return owner == null || owner.canAttack(target);
    }

    private static boolean areAllied(LivingEntity source, LivingEntity target) {
        Team sourceTeam = source.getTeam();
        Team targetTeam = target.getTeam();
        if (sourceTeam != null && targetTeam != null && (sourceTeam == targetTeam || sourceTeam.isAlliedTo(targetTeam) || targetTeam.isAlliedTo(sourceTeam))) return true;
        return source.isAlliedTo(target) || target.isAlliedTo(source);
    }

    private static boolean isCurrent(Mob mob, ActiveAnimationState state) {
        return ACTIVE.get(mob.getUUID()) == state;
    }

    private static void faceTarget(Mob mob, LivingEntity target) {
        mob.getLookControl().setLookAt(target, 60.0F, 60.0F);
        mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
    }

    private static void sendAnimation(Mob mob, SpecialAnimationId animationId, int durationTicks) {
        AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundSpecialAnimation(mob.getId(), animationId, durationTicks));
    }

    private record ActiveAnimationState(Mob mob, SpecialAnimationSpec spec, int startTick) {
        private int elapsedTicks(Mob mob) {
            return mob.tickCount - this.startTick;
        }
    }
}
