package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.entity.LowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.SwordsmanHerobrineEntity;
import com.pla.annoyingvillagers.entity.TransporterHerobrineCloneEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.HerobrinePortalCombatUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class HerobrinePortalCombatGoal extends Goal {
    private static final double SWORDSMAN_SIX_PORTAL_RADIUS = 48.0D;

    private final Mob caster;
    private Action action;

    public HerobrinePortalCombatGoal(Mob caster) {
        this.caster = caster;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(this.caster.level() instanceof ServerLevel) || !this.caster.isAlive() || this.caster.isPassenger()) {
            return false;
        }

        Action selected = this.pickAction();
        if (selected == null) {
            return false;
        }

        this.action = selected;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        Action selected = this.action;
        this.action = null;
        if (selected == null) {
            return;
        }

        switch (selected) {
            case PORTAL_ESCAPE_STEP_BACK -> this.performPortalEscapeStepBack();
            case SUPPORT_ESCAPE_PORTAL -> this.summonSupportingEscapePortal();
            case RANGED_COUNTER_PORTAL -> this.summonSupportCounterPortal();
            case SIX_PORTAL_SUPPORT -> this.doSixPortalSupport();
            case LOW_CLONE_SUPPORT -> this.summonLowCloneSupport();
            case TWO_PORTAL_SUPPORT -> this.summonTwoPortalSupport();
        }
    }

    @Nullable
    private Action pickAction() {
        if (this.canPerformPortalEscapeStepBack()) {
            return Action.PORTAL_ESCAPE_STEP_BACK;
        }

        List<WeightedAction> candidates = new ArrayList<>();
        addIf(candidates, Action.SUPPORT_ESCAPE_PORTAL, 45, this.canSupportEscapingHerobrine());
        addIf(candidates, Action.RANGED_COUNTER_PORTAL, 35, this.canSummonSupportCounterPortal());
        addIf(candidates, Action.SIX_PORTAL_SUPPORT, 30, this.canDoSixPortalSupport());
        addIf(candidates, Action.LOW_CLONE_SUPPORT, 30, this.canSummonLowCloneSupport());
        addIf(candidates, Action.TWO_PORTAL_SUPPORT, 25, this.canSummonTwoPortalSupport());

        if (candidates.isEmpty()) {
            return null;
        }

        int totalWeight = candidates.stream().mapToInt(WeightedAction::weight).sum();
        int roll = this.caster.getRandom().nextInt(totalWeight);
        for (WeightedAction candidate : candidates) {
            roll -= candidate.weight();
            if (roll < 0) {
                return candidate.action();
            }
        }

        return candidates.get(0).action();
    }

    private static void addIf(List<WeightedAction> candidates, Action action, int weight, boolean condition) {
        if (condition) {
            candidates.add(new WeightedAction(action, weight));
        }
    }

    private boolean canPerformPortalEscapeStepBack() {
        return this.canUseSupportPortalAction()
                && this.canUsePortalEscapeStepBack()
                && this.caster.getTarget() != null
                && HerobrinePortalCombatUtil.isVanillaEscapePressure(this.caster)
                && this.caster.level() instanceof ServerLevel serverLevel
                && TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, this.caster, 2);
    }

    private void performPortalEscapeStepBack() {
        if (!(this.caster.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, this.caster, 2)
                || spawnEscapePortalPair(this.caster, this.caster) <= 0) {
            return;
        }

        this.setPortalEscapeStepBackCooldown();
        this.caster.getNavigation().stop();
        this.caster.setTarget(null);
        this.caster.setSprinting(false);
        HerobrinePortalCombatUtil.playPortalPairSummon(this.caster);

        LivingEntity entity = this.caster;
        new DelayedTask(10) {
            @Override
            public void run() {
                if (!entity.isAlive()) {
                    return;
                }
                pushStepBackIntoPortal(entity, 0.65D);
                new DelayedTask(2) {
                    @Override
                    public void run() {
                        if (entity.isAlive()) {
                            pushStepBackIntoPortal(entity, 0.35D);
                        }
                    }
                };
            }
        };
    }

    private boolean canSupportEscapingHerobrine() {
        return this.canUseSupportPortalAction()
                && this.canUseSupportEscapePortal()
                && this.caster.level() instanceof ServerLevel serverLevel
                && TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, this.caster, 2)
                && this.findEscapingSupportHerobrine() != null;
    }

    private void summonSupportingEscapePortal() {
        LivingEntity support = this.findEscapingSupportHerobrine();
        if (support != null && spawnEscapePortalPair(this.caster, support) > 0) {
            this.markPortalSupportCaster();
            this.setSupportEscapePortalCooldown();
            HerobrinePortalCombatUtil.playSixPortalSummon(this.caster);
        }
    }

    private boolean canSummonSupportCounterPortal() {
        return this.canUseSupportPortalAction()
                && this.canUseRangedCounterPortal()
                && HerobrinePortalCombatUtil.canBowCounterPortalSupport(this.caster);
    }

    private void summonSupportCounterPortal() {
        if (HerobrinePortalCombatUtil.tryBowCounterPortalSupport(this.caster)) {
            this.markPortalSupportCaster();
            this.setRangedCounterPortalCooldown();
        }
    }

    private boolean canSummonTwoPortalSupport() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            return greg.canUseSupportPortalAction()
                    && greg.getPortalPairCooldown() <= 0
                    && HerobrinePortalCombatUtil.canGregPortalSupport(greg);
        }
        return this.caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.canUseSupportPortalAction()
                && transporter.getPortalPairCooldown() <= 0
                && HerobrinePortalCombatUtil.canTransporterPortalSupport(transporter);
    }

    private void summonTwoPortalSupport() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            if (HerobrinePortalCombatUtil.tryGregPortalSupport(greg)) {
                greg.setPortalPairCooldown();
            }
        } else if (this.caster instanceof TransporterHerobrineCloneEntity transporter
                && HerobrinePortalCombatUtil.tryTransporterPortalSupport(transporter)) {
            transporter.setPortalPairCooldown();
        }
    }

    private boolean canDoSixPortalSupport() {
        return this.caster instanceof HerobrineGregEntity greg
                && greg.canAnswerSixPortalSupportRequest()
                && this.findGregSixPortalSupportTarget(greg) != null;
    }

    private void doSixPortalSupport() {
        if (!(this.caster instanceof HerobrineGregEntity greg)) {
            return;
        }

        SwordsmanHerobrineEntity swordsman = this.findGregSixPortalSupportTarget(greg);
        if (swordsman == null) {
            return;
        }

        TransporterFragmentItem.PortalSpawnBatch portalBatch = TransporterFragmentItem.spawnPortalPairsBatch(
                greg.level(),
                greg,
                swordsman
        );
        if (portalBatch.spawned() <= 0) {
            return;
        }

        if (portalBatch.portalGroup() != null) {
            DemoniacVoltageReaverItem.setPreferredPortalTarget(
                    swordsman.getMainHandItem(),
                    portalBatch.portalGroup(),
                    greg.getUUID()
            );
        }

        greg.markSupportingHerobrine();
        greg.getLookControl().setLookAt(swordsman, 30.0F, 30.0F);
        greg.setSixPortalSupportCooldown();
        HerobrinePortalCombatUtil.playSixPortalSummon(greg);
    }

    private boolean canSummonLowCloneSupport() {
        if (!this.caster.onGround()) {
            return false;
        }
        if (this.caster instanceof HerobrineGregEntity greg) {
            return greg.canSummonLowCloneSupport() && this.findGregLowCloneSupportEnemy(greg) != null;
        }
        return this.caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.canSummonLowCloneSupport()
                && this.canFindTransporterLowCloneSupportSpawn(transporter);
    }

    private void summonLowCloneSupport() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            this.summonGregLowCloneSupport(greg);
        } else if (this.caster instanceof TransporterHerobrineCloneEntity transporter) {
            this.summonTransporterLowCloneSupport(transporter);
        }
    }

    private void summonGregLowCloneSupport(HerobrineGregEntity greg) {
        if (!(greg.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        LivingEntity support = greg.findGregFollowSupportHerobrine();
        LivingEntity enemy = this.findGregLowCloneSupportEnemy(greg);
        if (support == null || enemy == null) {
            return;
        }

        int count = Math.min(1 + greg.getRandom().nextInt(3), greg.getAvailableCombatLowCloneSupportSlotCount());
        int spawned = 0;
        for (int i = 0; i < count && greg.hasAvailableCombatLowCloneSupportSlot(); i++) {
            if (this.spawnGregCombatLowCloneNear(serverLevel, greg, support, enemy)) {
                spawned++;
            }
        }

        if (spawned > 0) {
            greg.markSupportingHerobrine();
            greg.setLowCloneSupportCooldown();
            HerobrinePortalCombatUtil.playSixPortalSummon(greg);
        }
    }

    private void summonTransporterLowCloneSupport(TransporterHerobrineCloneEntity transporter) {
        if (!(transporter.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        int count = Math.min(1 + transporter.getRandom().nextInt(3), transporter.getAvailableCombatLowCloneSupportSlotCount());
        int spawned = 0;
        for (int i = 0; i < count && transporter.hasAvailableCombatLowCloneSupportSlot(); i++) {
            if (this.spawnTransporterLowClone(serverLevel, transporter)) {
                spawned++;
            }
        }

        if (spawned > 0) {
            transporter.setLowCloneSupportCooldown();
            HerobrinePortalCombatUtil.playSixPortalSummon(transporter);
        }
    }

    private boolean canUseSupportPortalAction() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            return greg.canUseSupportPortalAction();
        }
        return this.caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.canUseSupportPortalAction();
    }

    private boolean canUseSupportEscapePortal() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            return greg.getSupportEscapePortalCooldown() <= 0;
        }
        return this.caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.getSupportEscapePortalCooldown() <= 0;
    }

    private void setSupportEscapePortalCooldown() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            greg.setSupportEscapePortalCooldown();
        } else if (this.caster instanceof TransporterHerobrineCloneEntity transporter) {
            transporter.setSupportEscapePortalCooldown();
        }
    }

    private boolean canUseRangedCounterPortal() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            return greg.getRangedCounterPortalCooldown() <= 0;
        }
        return this.caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.getRangedCounterPortalCooldown() <= 0;
    }

    private void setRangedCounterPortalCooldown() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            greg.setRangedCounterPortalCooldown();
        } else if (this.caster instanceof TransporterHerobrineCloneEntity transporter) {
            transporter.setRangedCounterPortalCooldown();
        }
    }

    private boolean canUsePortalEscapeStepBack() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            return greg.getPortalEscapeStepBackCooldown() <= 0;
        }
        return this.caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.getPortalEscapeStepBackCooldown() <= 0;
    }

    private void setPortalEscapeStepBackCooldown() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            greg.setPortalEscapeStepBackCooldown();
        } else if (this.caster instanceof TransporterHerobrineCloneEntity transporter) {
            transporter.setPortalEscapeStepBackCooldown();
        }
    }

    private void markPortalSupportCaster() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            greg.markSupportingHerobrine();
        }
    }

    @Nullable
    private LivingEntity findEscapingSupportHerobrine() {
        if (this.caster instanceof HerobrineGregEntity greg) {
            return greg.findEscapingSupportHerobrine();
        }
        for (LivingEntity support : HerobrinePortalCombatUtil.findSupportHerobrines(this.caster, 40.0D)) {
            if (support instanceof Mob mob
                    && support.isAlive()
                    && !(support.isPassenger() && support.getVehicle() instanceof HerobrineDragonEntity)
                    && HerobrinePortalCombatUtil.isVanillaEscapePressure(mob)) {
                return support;
            }
        }
        return null;
    }

    private static int spawnEscapePortalPair(LivingEntity caster, LivingEntity portalUser) {
        Vec3 entrance = getPortalBehind(portalUser, 1.75D);
        Vec3 exit = getRandomPortalEscapeExit(caster.level() instanceof ServerLevel serverLevel ? serverLevel : null, portalUser);
        return TransporterFragmentItem.spawnLinkedPortalPair(caster.level(), caster, entrance, exit);
    }

    private static Vec3 getPortalBehind(LivingEntity livingEntity, double distance) {
        double yawRad = Math.toRadians(livingEntity.getYRot());
        double x = livingEntity.getX() + Math.sin(yawRad) * distance;
        double z = livingEntity.getZ() - Math.cos(yawRad) * distance;
        return new Vec3(x, livingEntity.getY(), z);
    }

    private static Vec3 getRandomPortalEscapeExit(@Nullable ServerLevel serverLevel, LivingEntity anchor) {
        Random random = new Random(anchor.getRandom().nextLong());
        if (serverLevel != null) {
            for (int attempt = 0; attempt < 16; attempt++) {
                double angle = random.nextDouble() * Math.PI * 2.0D;
                double distance = 9.0D + random.nextDouble() * 2.0D;
                double x = anchor.getX() + Math.cos(angle) * distance;
                double z = anchor.getZ() + Math.sin(angle) * distance;
                BlockPos surface = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(x, anchor.getY(), z));
                BlockPos portalPos = surface.above(random.nextInt(3));
                if (!serverLevel.isLoaded(portalPos)
                        || !serverLevel.getWorldBorder().isWithinBounds(portalPos)
                        || !serverLevel.isEmptyBlock(portalPos)
                        || !serverLevel.isEmptyBlock(portalPos.above())) {
                    continue;
                }
                return new Vec3(portalPos.getX() + 0.5D, portalPos.getY(), portalPos.getZ() + 0.5D);
            }
        }

        double angle = random.nextDouble() * Math.PI * 2.0D;
        return anchor.position().add(Math.cos(angle) * 10.0D, random.nextInt(3), Math.sin(angle) * 10.0D);
    }

    private static void pushStepBackIntoPortal(LivingEntity livingEntity, double strength) {
        double yawRad = Math.toRadians(livingEntity.getYRot());
        Vec3 backward = new Vec3(Math.sin(yawRad), 0.0D, -Math.cos(yawRad)).scale(strength);
        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(backward.x, 0.0D, backward.z));
        livingEntity.hasImpulse = true;
    }

    @Nullable
    private LivingEntity findGregLowCloneSupportEnemy(HerobrineGregEntity greg) {
        LivingEntity support = greg.findGregFollowSupportHerobrine();
        if (support == null || !support.isAlive()
                || (support.isPassenger() && support.getVehicle() instanceof HerobrineDragonEntity)) {
            return null;
        }

        LivingEntity enemy = HerobrinePortalCombatUtil.findThreateningEnemy(greg, support, 48.0D);
        return enemy != null ? enemy : HerobrinePortalCombatUtil.findEnemyForSupport(support, greg.getTarget(), 48.0D);
    }

    private boolean spawnGregCombatLowCloneNear(ServerLevel serverLevel, HerobrineGregEntity greg, Entity anchor, LivingEntity enemy) {
        if (!greg.hasAvailableCombatLowCloneSupportSlot()) {
            return false;
        }

        RandomSource random = greg.getRandom();
        for (int attempt = 0; attempt < 24; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double radius = 2.5D + random.nextDouble() * 5.5D;
            double x = anchor.getX() + Math.cos(angle) * radius;
            double z = anchor.getZ() + Math.sin(angle) * radius;
            int y = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mth.floor(x), Mth.floor(z));
            BlockPos spawnPos = BlockPos.containing(x, y, z);
            if (!isValidCombatLowCloneSpawn(serverLevel, spawnPos)) {
                continue;
            }

            Mob clone = createCombatLowClone(serverLevel, random.nextBoolean());
            clone.moveTo(x, y, z, greg.getYRot(), greg.getXRot());
            if (!serverLevel.noCollision(clone)) {
                continue;
            }

            configureCombatLowClone(clone);
            equipLowCloneGear(clone, random);
            clone.setTarget(enemy);
            clone.lookAt(EntityAnchorArgument.Anchor.EYES, enemy.getEyePosition());
            clone.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(spawnPos), MobSpawnType.MOB_SUMMONED, null, null);
            if (!serverLevel.addFreshEntity(clone)) {
                return false;
            }
            if (!greg.claimCombatLowCloneSupportSlot(clone)) {
                clone.discard();
                return false;
            }
            TeamUtil.addOrJoinTeam(clone, "herobrine");
            AnnoyingVillagers.PACKET_HANDLER.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> clone),
                    new ClientboundHerobrinePortalFx(new Vec3(x, y, z))
            );
            return true;
        }
        return false;
    }

    private boolean canFindTransporterLowCloneSupportSpawn(TransporterHerobrineCloneEntity transporter) {
        if (!(transporter.level() instanceof ServerLevel serverLevel)) {
            return false;
        }
        return this.findTransporterLowCloneEnemy(transporter) != null
                && this.findTransporterLowCloneSpawnPosition(transporter, serverLevel) != null;
    }

    private boolean spawnTransporterLowClone(ServerLevel serverLevel, TransporterHerobrineCloneEntity transporter) {
        if (!transporter.hasAvailableCombatLowCloneSupportSlot()) {
            return false;
        }

        Vec3 spawnPos = this.findTransporterLowCloneSpawnPosition(transporter, serverLevel);
        if (spawnPos == null) {
            return false;
        }

        Mob clone = createCombatLowClone(serverLevel, transporter.getRandom().nextBoolean());
        clone.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, transporter.getYRot(), transporter.getXRot());
        if (!serverLevel.noCollision(clone)) {
            return false;
        }

        configureCombatLowClone(clone);
        equipLowCloneGear(clone, transporter.getRandom());

        LivingEntity enemy = this.findTransporterLowCloneEnemy(transporter);
        if (enemy != null && enemy.isAlive()) {
            clone.setTarget(enemy);
            clone.lookAt(EntityAnchorArgument.Anchor.EYES, enemy.getEyePosition());
        } else {
            clone.lookAt(EntityAnchorArgument.Anchor.EYES, transporter.getEyePosition());
        }

        clone.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(clone.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        if (!serverLevel.addFreshEntity(clone)) {
            return false;
        }
        if (!transporter.claimCombatLowCloneSupportSlot(clone)) {
            clone.discard();
            return false;
        }
        TeamUtil.addOrJoinTeam(clone, "herobrine");
        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY.with(() -> clone),
                new ClientboundHerobrinePortalFx(spawnPos)
        );
        return true;
    }

    @Nullable
    private LivingEntity findTransporterLowCloneEnemy(TransporterHerobrineCloneEntity transporter) {
        LivingEntity enemy = HerobrinePortalCombatUtil.findThreateningEnemy(
                transporter,
                null,
                TransporterHerobrineCloneEntity.SUPPORT_AVOID_SEARCH_RADIUS
        );
        return enemy != null
                ? enemy
                : HerobrinePortalCombatUtil.findEnemyForSupport(
                transporter,
                null,
                TransporterHerobrineCloneEntity.SUPPORT_AVOID_SEARCH_RADIUS
        );
    }

    @Nullable
    private Vec3 findTransporterLowCloneSpawnPosition(TransporterHerobrineCloneEntity transporter, ServerLevel serverLevel) {
        RandomSource random = transporter.getRandom();
        for (int attempt = 0; attempt < 32; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double distance = 3.0D + random.nextDouble() * 7.0D;
            double x = transporter.getX() + Math.cos(angle) * distance;
            double z = transporter.getZ() + Math.sin(angle) * distance;
            int y = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mth.floor(x), Mth.floor(z));
            BlockPos surface = BlockPos.containing(x, y, z);

            if (isValidCombatLowCloneSpawn(serverLevel, surface)) {
                return new Vec3(x, surface.getY(), z);
            }
        }
        return null;
    }

    private static boolean isValidCombatLowCloneSpawn(ServerLevel serverLevel, BlockPos spawnPos) {
        return serverLevel.isLoaded(spawnPos)
                && serverLevel.getWorldBorder().isWithinBounds(spawnPos)
                && serverLevel.isEmptyBlock(spawnPos)
                && serverLevel.isEmptyBlock(spawnPos.above())
                && !serverLevel.isEmptyBlock(spawnPos.below());
    }

    private static Mob createCombatLowClone(ServerLevel serverLevel, boolean shadowClone) {
        return shadowClone
                ? new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel)
                : new LowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), serverLevel);
    }

    private static void configureCombatLowClone(Mob clone) {
        if (clone instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
            lowHerobrineCloneEntity.setSummoned(true);
            lowHerobrineCloneEntity.setRenderPortal(false);
        } else if (clone instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
            lowShadowHerobrineCloneEntity.setSummoned(true);
            lowShadowHerobrineCloneEntity.setRenderPortal(false);
        }
    }

    private static void equipLowCloneGear(Mob clone, RandomSource random) {
        if (random.nextFloat() < 0.3F) {
            clone.setItemSlot(EquipmentSlot.HEAD, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_HELMET.get()), random));
        }
        if (random.nextFloat() < 0.3F) {
            clone.setItemSlot(EquipmentSlot.CHEST, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get()), random));
        }
        if (random.nextFloat() < 0.3F) {
            clone.setItemSlot(EquipmentSlot.LEGS, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_LEGGINGS.get()), random));
        }
        if (random.nextFloat() < 0.3F) {
            clone.setItemSlot(EquipmentSlot.FEET, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_BOOTS.get()), random));
        }

        clone.setItemSlot(EquipmentSlot.MAINHAND, damageRandomly(new ItemStack(HerobrineGregEntity.listWeapons.get(random.nextInt(HerobrineGregEntity.listWeapons.size()))), random));
    }

    private static ItemStack damageRandomly(ItemStack itemStack, RandomSource random) {
        if (!itemStack.isDamageableItem()) {
            return itemStack;
        }

        int maxDamage = itemStack.getMaxDamage();
        itemStack.setDamageValue(random.nextInt(Math.max(1, maxDamage / 3), Math.max(2, maxDamage * 3 / 4)));
        return itemStack;
    }

    private boolean hasNearbySixPortalSupport(SwordsmanHerobrineEntity swordsman) {
        UUID gregUuid = swordsman.getGregUUID();
        if (gregUuid != null && HerobrinePortalCombatUtil.hasNearbyPortalGroup(
                swordsman,
                gregUuid,
                6,
                SWORDSMAN_SIX_PORTAL_RADIUS
        )) {
            return true;
        }

        return HerobrinePortalCombatUtil.hasNearbyPortalGroup(
                swordsman,
                null,
                6,
                SWORDSMAN_SIX_PORTAL_RADIUS
        );
    }

    @Nullable
    private SwordsmanHerobrineEntity findGregSixPortalSupportTarget(HerobrineGregEntity greg) {
        if (!(greg.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, greg, 6)) {
            return null;
        }

        for (LivingEntity support : HerobrinePortalCombatUtil.findSupportHerobrines(greg, 40.0D)) {
            if (support instanceof SwordsmanHerobrineEntity swordsman && this.canUseGregSixPortalSupport(greg, swordsman)) {
                return swordsman;
            }
        }

        return null;
    }

    private boolean canUseGregSixPortalSupport(HerobrineGregEntity greg, SwordsmanHerobrineEntity swordsman) {
        return swordsman.isAlive()
                && swordsman.getState() > 0
                && swordsman.getTarget() != null
                && swordsman.getTarget().isAlive()
                && swordsman.getMainHandItem().is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())
                && !DemoniacVoltageReaverItem.hasSnakeAnimation(swordsman.getMainHandItem())
                && !this.hasNearbySixPortalSupport(swordsman)
                && (swordsman.getGregUUID() == null || swordsman.getGregUUID().equals(greg.getUUID()));
    }

    private enum Action {
        PORTAL_ESCAPE_STEP_BACK,
        SUPPORT_ESCAPE_PORTAL,
        RANGED_COUNTER_PORTAL,
        SIX_PORTAL_SUPPORT,
        LOW_CLONE_SUPPORT,
        TWO_PORTAL_SUPPORT
    }

    private record WeightedAction(Action action, int weight) {
    }
}
