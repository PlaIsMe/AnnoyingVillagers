package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public class ObedienceMobEffect extends MobEffect {
    private static final String OWNER_UUID_KEY = "AVObedienceOwner";
    private static final String TEAM_CAPTURED_KEY = "AVObedienceTeamCaptured";
    private static final String ORIGINAL_TEAM_KEY = "AVObedienceOriginalTeam";
    private static final String REFRESHING_KEY = "AVObedienceRefreshing";
    private static final double SEARCH_RANGE = 24.0D;

    public ObedienceMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x28FF28);
    }
    public @NotNull String getDescriptionId() {
        return "effect.annoyingvillagers.obedience";
    }
    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (entity instanceof Mob mob) {
            tickObedience(mob);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        if (!event.getEffect().is(AnnoyingVillagersModMobEffects.OBEDIENCE.getKey())) return;
        if (!entity.level().isClientSide() && entity instanceof Mob mob) {
            syncObedienceRemoval(mob);
            CompoundTag tag = mob.getPersistentData();
            if (tag.getBooleanOr(REFRESHING_KEY, false)) {
                return;
            }
            restoreOriginalTeamAndClear(mob);
        }
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance effect = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        if (effect == null
                || !effect.is(AnnoyingVillagersModMobEffects.OBEDIENCE)
                || entity.level().isClientSide()
                || !(entity instanceof Mob mob)) {
            return;
        }

        syncObedienceRemoval(mob);
        restoreOriginalTeamAndClear(mob);
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || !(event.getTarget() instanceof Mob mob)) {
            return;
        }

        MobEffectInstance effect = mob.getEffect(AnnoyingVillagersModMobEffects.OBEDIENCE);
        if (effect != null) {
            player.connection.send(new ClientboundUpdateMobEffectPacket(mob.getId(), effect, true));
        }
    }

    public static boolean canBeObedientMob(Entity entity) {
        return entity instanceof Zombie
                || entity instanceof AbstractSkeleton
                || entity instanceof Creeper
                || entity instanceof Spider
                || entity instanceof AbstractPiglin
                || entity instanceof AbstractIllager
                || entity instanceof Vex;
    }

    public static void applyObedience(Mob targetMob, LivingEntity owner, int durationTicks) {
        if (targetMob.level().isClientSide()) {
            return;
        }

        if (!canBeObedientMob(targetMob)) {
            return;
        }

        if (!targetMob.isAlive() || !owner.isAlive()) {
            return;
        }

        if (targetMob.getUUID().equals(owner.getUUID())) {
            return;
        }

        CompoundTag tag = targetMob.getPersistentData();

        boolean alreadyHasObedience =
                targetMob.hasEffect(AnnoyingVillagersModMobEffects.OBEDIENCE);

        if (alreadyHasObedience) {
            tag.putBoolean(REFRESHING_KEY, true);
        }

        try {
            captureOriginalTeamAndLeave(targetMob);

            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, OWNER_UUID_KEY, owner.getUUID());

            boolean added = targetMob.addEffect(
                    new MobEffectInstance(
                            AnnoyingVillagersModMobEffects.OBEDIENCE,
                            durationTicks,
                            0,
                            false,
                            true,
                            true
                    ),
                    owner
            );
            MobEffectInstance appliedEffect = targetMob.getEffect(AnnoyingVillagersModMobEffects.OBEDIENCE);
            if (added && appliedEffect != null) {
                syncObedienceUpdate(targetMob, appliedEffect, !alreadyHasObedience);
            }
            captureOriginalTeamAndLeave(targetMob);
            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, OWNER_UUID_KEY, owner.getUUID());

            if (targetMob.getTarget() == owner) {
                targetMob.setTarget(null);
            }
        } finally {
            tag.remove(REFRESHING_KEY);
        }
    }

    public static void tickObedience(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!canBeObedientMob(mob)) {
            mob.removeEffect(AnnoyingVillagersModMobEffects.OBEDIENCE);
            return;
        }

        captureOriginalTeamAndLeave(mob);
        TeamUtil.leaveCurrentTeam(mob);

        UUID ownerUuid = getOwnerUUID(mob);
        if (ownerUuid == null) {
            mob.removeEffect(AnnoyingVillagersModMobEffects.OBEDIENCE);
            return;
        }

        Entity ownerEntity = serverLevel.getEntity(ownerUuid);
        if (!(ownerEntity instanceof LivingEntity owner) || !owner.isAlive()) {
            mob.removeEffect(AnnoyingVillagersModMobEffects.OBEDIENCE);
            return;
        }

        LivingEntity currentTarget = mob.getTarget();

        if (currentTarget != null && shouldBlockTarget(mob, currentTarget)) {
            mob.setTarget(null);
        }

        LivingEntity bestTarget = findBestTarget(mob, owner);

        if (bestTarget != null && mob.getTarget() != bestTarget) {
            mob.setTarget(bestTarget);
        }
    }

    private static void syncObedienceUpdate(Mob mob, MobEffectInstance effect, boolean newEffect) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            serverLevel.getChunkSource().sendToTrackingPlayersAndSelf(
                    mob,
                    new ClientboundUpdateMobEffectPacket(mob.getId(), effect, newEffect)
            );
        }
    }

    private static void syncObedienceRemoval(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            serverLevel.getChunkSource().sendToTrackingPlayersAndSelf(
                    mob,
                    new ClientboundRemoveMobEffectPacket(mob.getId(), AnnoyingVillagersModMobEffects.OBEDIENCE)
            );
        }
    }

    private static void captureOriginalTeamAndLeave(Mob mob) {
        CompoundTag tag = mob.getPersistentData();

        if (!tag.getBooleanOr(TEAM_CAPTURED_KEY, false)) {
            tag.putBoolean(TEAM_CAPTURED_KEY, true);

            String originalTeamName = TeamUtil.getTeamName(mob);
            if (originalTeamName != null) {
                tag.putString(ORIGINAL_TEAM_KEY, originalTeamName);
            }
        }

        TeamUtil.leaveCurrentTeam(mob);
    }

    public static void restoreOriginalTeamAndClear(Mob mob) {
        CompoundTag tag = mob.getPersistentData();

        if (tag.getBooleanOr(TEAM_CAPTURED_KEY, false)
                && tag.contains(ORIGINAL_TEAM_KEY)) {

            String originalTeamName = tag.getStringOr(ORIGINAL_TEAM_KEY, "");

            if (!originalTeamName.isEmpty()) {
                TeamUtil.addOrJoinTeam(mob, originalTeamName);
            }
        }

        tag.remove(OWNER_UUID_KEY);
        tag.remove(TEAM_CAPTURED_KEY);
        tag.remove(ORIGINAL_TEAM_KEY);
        tag.remove(REFRESHING_KEY);

        if (mob.getTarget() != null) {
            mob.setTarget(null);
        }
    }

    @Nullable
    private static LivingEntity findBestTarget(Mob mob, LivingEntity owner) {
        LivingEntity ownerLastHurtMob = owner.getLastHurtMob();
        if (isValidObedienceTarget(mob, owner, ownerLastHurtMob)) {
            return ownerLastHurtMob;
        }

        LivingEntity ownerLastHurtByMob = owner.getLastHurtByMob();
        if (isValidObedienceTarget(mob, owner, ownerLastHurtByMob)) {
            return ownerLastHurtByMob;
        }

        LivingEntity mobLastHurtByMob = mob.getLastHurtByMob();
        if (isValidObedienceTarget(mob, owner, mobLastHurtByMob)) {
            return mobLastHurtByMob;
        }

        List<Monster> nearbyMonsters = mob.level().getEntitiesOfClass(
                Monster.class,
                mob.getBoundingBox().inflate(SEARCH_RANGE),
                monster -> isValidObedienceTarget(mob, owner, monster)
        );

        return nearbyMonsters.stream()
                .min(Comparator.comparingDouble(mob::distanceToSqr))
                .orElse(null);
    }

    private static boolean isValidObedienceTarget(
            Mob controlledMob,
            LivingEntity owner,
            @Nullable LivingEntity target
    ) {
        if (!(target instanceof Monster targetMob)) {
            return false;
        }

        if (!target.isAlive()) {
            return false;
        }

        if (target == controlledMob || target == owner) {
            return false;
        }

        UUID ownerUuid = getOwnerUUID(controlledMob);

        if (ownerUuid != null) {
            if (target.getUUID().equals(ownerUuid)) {
                return false;
            }

            return !isControlledBy(targetMob, ownerUuid);
        }

        return true;
    }

    public static boolean shouldBlockTarget(Mob mob, LivingEntity target) {
        UUID ownerUuid = getOwnerUUID(mob);

        if (ownerUuid == null) {
            return false;
        }

        if (target.getUUID().equals(ownerUuid)) {
            return true;
        }

        return target instanceof Mob targetMob && isControlledBy(targetMob, ownerUuid);
    }

    public static boolean isObedientMob(Entity entity) {
        return entity instanceof Mob mob
                && mob.hasEffect(AnnoyingVillagersModMobEffects.OBEDIENCE)
                && getOwnerUUID(mob) != null;
    }

    public static boolean isControlledBy(Mob mob, UUID ownerUuid) {
        UUID storedOwnerUuid = getOwnerUUID(mob);
        return storedOwnerUuid != null && storedOwnerUuid.equals(ownerUuid);
    }

    @Nullable
    public static UUID getOwnerUUID(Entity entity) {
        CompoundTag tag = entity.getPersistentData();
        return com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(tag, OWNER_UUID_KEY) ? com.pla.annoyingvillagers.util.LegacyNbt.getUUID(tag, OWNER_UUID_KEY) : null;
    }
}
