package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.HerobrinePortalCombatUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import static com.pla.annoyingvillagers.combatbehaviour.CombatCommon.getIntegerIntegerBiFunction;

public class HerobrineCommon {
    public static boolean canJump(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal().onGround() && !mobpatch.getOriginal().isPassenger();
    }

    public static boolean canPerformHealing(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob instanceof HerobrineCloneEntity || herobrineMob instanceof ShadowHerobrineCloneEntity
                    || herobrineMob instanceof ArmoredHerobrineEntity || herobrineMob instanceof HerobrineChrisEntity
                    || herobrineMob instanceof Herobrine7Entity) {
                if (getEntities(herobrineMob).isEmpty()) {
                    return false;
                }
            }
            return !herobrineMob.isSacrificing() && !herobrineMob.isHealing() && herobrineMob.getHealingCooldown() == 0;
        }
        return false;
    }

    public static boolean canSpinning(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof NullWeapon nullWeapon) {
            return nullWeapon.isSpinning();
        }
        return false;
    }

    public static boolean canSummonDarkOb(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
            return !shadowHerobrineEntity.isDarkObReady() && shadowHerobrineEntity.getSummonDarkObCooldown() == 0;
        }
        return false;
    }

    public static boolean canShootDarkOb(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
            return shadowHerobrineEntity.isDarkObReady();
        }
        return false;
    }

    public static boolean canPlayObsidianMachine(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
            return shadowHerobrineEntity.getState() == 2 && shadowHerobrineEntity.getObsidianMachineGunCooldown() == 0 && shadowHerobrineEntity.getObsidianMachineGunTick() == 0;
        }
        return false;
    }

    public static boolean canMountOrDismountDragon(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof ReaperHerobrineEntity reaperHerobrineEntity) {
            return reaperHerobrineEntity.getHealingHerobrineDragon() != null
                    || reaperHerobrineEntity.getThunderHerobrineDragon() != null
                    || reaperHerobrineEntity.getMeteoriteHerobrineDragon() != null;
        }
        return false;
    }

    public static boolean canChangeToSecondForm(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            ItemStack item = herobrineMob.getMainHandItem();
            if (herobrineMob instanceof SwordsmanHerobrineEntity
                    && item.getTag() != null && item.getTag().contains("SnakeAnimation")) {
                return false;
            }
            if (herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity
                    && reaperHerobrineEntity.getThunderHerobrineDragon() == null) {
                return false;
            }
            if (herobrineMob instanceof HerobrineCloneEntity || herobrineMob instanceof ShadowHerobrineEntity
                    || herobrineMob instanceof Herobrine7Entity || herobrineMob instanceof ArmoredHerobrineEntity) {
                return false;
            }
            return herobrineMob.getState() == 0;
        }
        return false;
    }

    public static boolean canPlaySecondFormAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            ItemStack item = herobrineMob.getMainHandItem();
            if (herobrineMob instanceof SwordsmanHerobrineEntity
                    && (item.getTag() != null && item.getTag().contains("SnakeAnimation")
                    || HerobrinePortalCombatUtil.isGregSixPortalSnakeBladePending((SwordsmanHerobrineEntity) herobrineMob))) {
                return false;
            }
            return herobrineMob.getState() != 0;
        }
        return false;
    }

    public static boolean canPlaySecondFormAnimationWithGregPortal(MobPatch<?> mobpatch) {
        if (!(mobpatch.getOriginal() instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity)) {
            return false;
        }
        return canPlaySecondFormAnimation(mobpatch)
                && HerobrinePortalCombatUtil.canQueueGregSixPortalSnakeBlade(swordsmanHerobrineEntity);
    }

    public static boolean canPlaySecondFormGuardAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity
                && HerobrinePortalCombatUtil.isGregSixPortalSnakeBladePending(swordsmanHerobrineEntity)) {
            return false;
        }
        return canPlaySecondFormAnimation(mobpatch);
    }

    public static boolean canCastMeteorite(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity
                    && (reaperHerobrineEntity.getMeteoriteHerobrineDragon() == null || reaperHerobrineEntity.getMeteoriteHerobrineDragon().isRecallActive())) {
                return false;
            }
            return herobrineMob.getState() != 0;
        }
        return false;
    }

    public static boolean canSummonNullSkeleton(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof NullEntity nullEntity) {
            return nullEntity.isAvailableWitherSkeletonSlot();
        }
        return false;
    }

    public static boolean canRespawnCrystal(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity
                    && (reaperHerobrineEntity.getHealingHerobrineDragon() == null
                    || !reaperHerobrineEntity.getHealingHerobrineDragon().getPassengers().isEmpty())) {
                return false;
            }
            return herobrineMob.getState() != 0;
        }
        return false;
    }

    public static boolean canCastThunder(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity
                    && (reaperHerobrineEntity.getThunderHerobrineDragon() == null || reaperHerobrineEntity.getThunderHerobrineDragon().isRecallActive())) {
                return false;
            }
            return herobrineMob.getState() != 0;
        }
        return false;
    }

    public static boolean canPerformGuarding(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        return !(entity instanceof HerobrineCloneEntity) && !(entity instanceof ShadowHerobrineCloneEntity)
                && !(entity instanceof HerobrineChrisEntity) && !(entity instanceof ArmoredHerobrineEntity) && !(entity instanceof Herobrine7Entity);
    }

    public static void performHealingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        if (!(entity instanceof HerobrineMob herobrineMob)) return;

        herobrineMob.setHealing(true);
        List<Entity> bound = getEntities(herobrineMob);

        Random random = new Random();
        Entity chosen;
        if (bound.isEmpty()) {
            double radius = 3.0D + random.nextDouble() * 3.0D;
            double angle = random.nextDouble() * (Math.PI * 2.0D);

            double dx = Math.cos(angle) * radius;
            double dz = Math.sin(angle) * radius;

            Vec3 rawPos = new Vec3(entity.getX() + dx, entity.getY(), entity.getZ() + dz);
            BlockPos xz = BlockPos.containing(rawPos.x, 0.0D, rawPos.z);
            int y = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, xz).getY();
            Vec3 spawnPos = new Vec3(rawPos.x, y, rawPos.z);

            Entity spawned;
            if (random.nextBoolean()) {
                LowHerobrineCloneEntity low = new LowHerobrineCloneEntity(
                        AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), serverLevel
                );
                low.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, herobrineMob.getYRot(), herobrineMob.getXRot());
                low.setPossessedByEntity(herobrineMob);
                low.setRenderPortal(false);
                low.setPossessedByUuid(herobrineMob.getUUID());
                low.setNoAi(true);
                TeamUtil.addOrJoinTeam(low, "herobrine");
                serverLevel.addFreshEntity(low);
                spawned = low;
            } else {
                LowShadowHerobrineCloneEntity low = new LowShadowHerobrineCloneEntity(
                        AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel
                );
                low.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, herobrineMob.getYRot(), herobrineMob.getXRot());
                low.setPossessedByEntity(herobrineMob);
                low.setRenderPortal(false);
                low.setPossessedByUuid(herobrineMob.getUUID());
                low.setNoAi(true);
                TeamUtil.addOrJoinTeam(low, "herobrine");
                serverLevel.addFreshEntity(low);
                spawned = low;
            }

            herobrineMob.boundPossessed(spawned);
            chosen = spawned;
        } else {
            chosen = bound.get(random.nextInt(bound.size()));
        }

        if (chosen instanceof LowShadowHerobrineCloneEntity lowShadow) {
            if (lowShadow.isHealing()) return;
            lowShadow.setPossessedByEntity(herobrineMob);
            lowShadow.setPossessedByUuid(herobrineMob.getUUID());
            lowShadow.setSacrificing(false);
            lowShadow.setHealing(true);
            lowShadow.setNoAi(true);
            return;
        }

        if (chosen instanceof LowHerobrineCloneEntity low) {
            if (low.isHealing()) return;
            low.setPossessedByEntity(herobrineMob);
            low.setPossessedByUuid(herobrineMob.getUUID());
            low.setHealing(true);
            low.setNoAi(true);
        }

        chosen.playSound(AnnoyingVillagersModSounds.HEROBRINE_UNDERSTOOD.get(), 1.0F, 1.0F);
    }

    public static @NotNull List<Entity> getEntities(HerobrineMob herobrineMob) {
        List<Entity> bound = new ArrayList<>(4);
        Entity c1 = herobrineMob.getFirstPossessedHerobrine();
        Entity c2 = herobrineMob.getSecondPossessedHerobrine();
        Entity c3 = herobrineMob.getThirdPossessedHerobrine();
        Entity c4 = herobrineMob.getFourthPossessedHerobrine();

        if (c1 != null && c1.isAlive()) bound.add(c1);
        if (c2 != null && c2.isAlive()) bound.add(c2);
        if (c3 != null && c3.isAlive()) bound.add(c3);
        if (c4 != null && c4.isAlive()) bound.add(c4);
        return bound;
    }

    public static void changeToSecondForm(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            herobrineMob.setState(1);
            herobrineMob.setSecondFormHitLeft(new Random().nextInt(2, 3));
            if (herobrineMob instanceof AegisHerobrineEntity || herobrineMob instanceof SwordsmanHerobrineEntity
                    || herobrineMob instanceof SledgehammerHerobrineEntity || herobrineMob instanceof ReaperHerobrineEntity
                    || herobrineMob instanceof GlaiveHerobrineEntity) {
                herobrineMob.playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(), 0.5F, 1.0F);
            }
        }
    }

    public static void releaseWeapon(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof NullEntity nullEntity) {
            nullEntity.releaseRandomWeapons((nullEntity.getState() < 2
                    ? new Random().nextInt(1, 3)
                    : new Random().nextInt(3, 5)));
        }
    }

    public static void playSecondFormAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            ItemStack item = herobrineMob.getMainHandItem();
            if (herobrineMob.getState() < 2) {
                herobrineMob.setSecondFormHitLeft(herobrineMob.getSecondFormHitLeft() - 1);
            }
            if (herobrineMob instanceof SwordsmanHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                DemoniacVoltageReaverItem.process(item, herobrineMob);
                item.getOrCreateTag().putBoolean("SnakeAnimation", true);
            } else if (herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                HerobrineDragonEntity herobrineDragonEntity = reaperHerobrineEntity.getThunderHerobrineDragon();
                if (herobrineDragonEntity != null) {
                    reaperHerobrineEntity.playSound(AnnoyingVillagersModSounds.REAPER_FIRE.get(), 1.0F, 1.0F);
                    herobrineDragonEntity.shootThunderBreathAtTarget(herobrineMob.getTarget());
                }
            }
        }
    }

    public static void queueSecondFormAnimationWithGregPortal(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity) {
            HerobrinePortalCombatUtil.queueGregSixPortalSnakeBlade(swordsmanHerobrineEntity);
        }
    }

    public static void playSecondFormAnimationWithGregPortal(MobPatch<?> mobpatch) {
        queueSecondFormAnimationWithGregPortal(mobpatch);
    }

    public static boolean triggerQueuedSecondFormSnakeBlade(SwordsmanHerobrineEntity swordsmanHerobrineEntity) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(swordsmanHerobrineEntity, LivingEntityPatch.class);
        if (!(patch instanceof MobPatch<?> mobPatch)) {
            return false;
        }

        playSecondFormAnimation(mobPatch);
        patch.playAnimationSynchronized(AVAnimations.SNAKE_BLADE, 0.0F);
        return true;
    }

    public static void playSecondFormSpecialAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob.getState() < 2) {
                herobrineMob.setSecondFormHitLeft(herobrineMob.getSecondFormHitLeft() - 1);
            }
            if (herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                HerobrineDragonEntity herobrineDragonEntity = reaperHerobrineEntity.getMeteoriteHerobrineDragon();
                if (herobrineDragonEntity != null) {
                    herobrineDragonEntity.shootMeteoriteAtTarget(herobrineMob.getTarget());
                }
            }
        }
    }

    public static void playSecondFormGuardAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity
                    && HerobrinePortalCombatUtil.isGregSixPortalSnakeBladePending(swordsmanHerobrineEntity)) {
                return;
            }
            ItemStack item = herobrineMob.getMainHandItem();
            if (herobrineMob.getState() < 2) {
                herobrineMob.setSecondFormHitLeft(herobrineMob.getSecondFormHitLeft() - 1);
            }
            if (herobrineMob instanceof SwordsmanHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                DemoniacVoltageReaverItem.processGuard(item, herobrineMob);
                item.getOrCreateTag().putBoolean("SnakeAnimation", true);
            }
        }
    }

    public static void respawnCrystal(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity && herobrineMob.level() instanceof ServerLevel serverLevel) {
                if (reaperHerobrineEntity.getHealingHerobrineDragon() != null
                        && reaperHerobrineEntity.getHealingHerobrineDragon().isAlive()
                        && reaperHerobrineEntity.getHealingHerobrineDragon().getPassengers().isEmpty()) {
                    EndCrystal endCrystal = new EndCrystal(EntityType.END_CRYSTAL, serverLevel);
                    endCrystal.moveTo(reaperHerobrineEntity.getHealingHerobrineDragon().getX(), reaperHerobrineEntity.getHealingHerobrineDragon().getY(), reaperHerobrineEntity.getHealingHerobrineDragon().getZ());
                    serverLevel.addFreshEntity(endCrystal);
                    endCrystal.startRiding(reaperHerobrineEntity.getHealingHerobrineDragon(), true);
                }
            }
        }
    }


    public static void jump(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob herobrineMob) {
            herobrineMob.jump();
        }
    }

    public static void giveSlowFalling(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob herobrineMob) {
            herobrineMob.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1));
        }
    }

    public static void giveSlowFallingAndRequestGregPortal(MobPatch<?> mobpatch) {
        giveSlowFalling(mobpatch);
        if (!(mobpatch.getOriginal() instanceof HerobrineMob herobrineMob)
                || !(herobrineMob.level() instanceof ServerLevel serverLevel)
                || herobrineMob.getTarget() == null
                || !herobrineMob.getTarget().isAlive()
                || herobrineMob.getGregUUID() == null
                || !new Random().nextBoolean()) {
            return;
        }

        Entity entity = serverLevel.getEntity(herobrineMob.getGregUUID());
        if (entity instanceof HerobrineGregEntity herobrineGregEntity && herobrineGregEntity.isAlive()) {
            herobrineGregEntity.requestApproachPortalFor(herobrineMob, herobrineMob.getTarget());
        }
    }

    public static void performEscapeRunAwayWithLowClone(MobPatch<?> mobpatch) {
        final Mob mob = mobpatch.getOriginal();
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        CombatCommon.performEscapeRunAway(mobpatch);
        if (mob.tickCount % 10 == 0) {
            new DelayedTask(1) {
                @Override
                public void run() {
                    if (!mob.isAlive()) return;

                    mobpatch.playAnimationSynchronized(AnimsEpicFightIronSpell.CASTING_ONE_HAND_INWARD, 0.0F);

                    final LivingEntity target = mob.getTarget();
                    final Direction dir = (target != null)
                            ? Direction.getNearest(target.getX() - mob.getX(), 0.0D, target.getZ() - mob.getZ())
                            : mob.getDirection();

                    final Random random = new Random();
                    final int dist = 1 + random.nextInt(3);

                    final int rot = random.nextInt(4);
                    final BiFunction<Integer, Integer, int[]> toWorld = getIntegerIntegerBiFunction(mob, rot);
                    final int lateral = random.nextInt(3) - 1;
                    final int[] dxz = toWorld.apply(lateral, 0);

                    BlockPos baseXZ = mob.blockPosition().relative(dir, dist).offset(dxz[0], 0, dxz[1]);
                    int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
                    BlockPos spawnPos = new BlockPos(baseXZ.getX(), surfaceY, baseXZ.getZ());

                    LowShadowHerobrineCloneEntity clone =
                            new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel);

                    float yaw = dir.toYRot();
                    clone.moveTo(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D, yaw, 0.0F);
                    clone.setRenderPortal(false);

                    clone.setForEscaping(true);
                    clone.setNoAi(true);
                    if (mob instanceof HerobrineMob herobrineMob) {
                        clone.setPossessedByEntity(herobrineMob);
                        clone.setPossessedByUuid(herobrineMob.getUUID());
                    }

                    serverLevel.addFreshEntity(clone);
                }
            };
        }
    }

    public static void performAgonySpecialAttack(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob) {
            new DelayedTask(10) {
                @Override
                public void run() {
                    mobpatch.playAnimationSynchronized(AnimsAgony.AGONY_RIPPING_FANGS, 0.0F);
                }
            };
        }
    }

    public static void performSpinning(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof NullWeapon nullWeapon) {
            nullWeapon.setSpinning(false);
        }
    }

    public static void performGuardWeaponSpinning(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof NullEntity nullEntity) {
            nullEntity.setSpinningToAllWeaponsAvailable(true);
        }
    }

    public static void mountOrDismountDragon(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob herobrineMob && herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity) {
            if (reaperHerobrineEntity.isPassenger()) {
                reaperHerobrineEntity.stopRiding();
            } else {
                if (reaperHerobrineEntity.getThunderHerobrineDragon() != null) {
                    reaperHerobrineEntity.getThunderHerobrineDragon().recallAndLand(true);
                } else if (reaperHerobrineEntity.getMeteoriteHerobrineDragon() != null) {
                    reaperHerobrineEntity.getMeteoriteHerobrineDragon().recallAndLand(true);
                }
            }
        }
    }

    public static void performSummonDarkOb(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
            shadowHerobrineEntity.spawnDarkObEntities();
        }
    }

    public static void performShootDarkOb(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
            shadowHerobrineEntity.shootDarkObsAtTarget(2.0F);
        }
    }

    public static void performObsidianMachine(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
            shadowHerobrineEntity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            shadowHerobrineEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()));
            if (shadowHerobrineEntity.level() instanceof ServerLevel) {
                shadowHerobrineEntity.playSound(AnnoyingVillagersModSounds.SHADOW_HEROBRINE_SAY_OBSIDIAN_MACHINE_GUN.get(), 1.0F, 1.0F);
            }
        }
    }
}
