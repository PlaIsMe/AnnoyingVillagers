package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EscapeUtil;
import com.pla.annoyingvillagers.util.HerobrinePortalCombatUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiFunction;

import static com.pla.annoyingvillagers.combatbehaviour.CombatCommon.getIntegerIntegerBiFunction;

public class HerobrineCommon {
    private static final double SWORDSMAN_SIX_PORTAL_RADIUS = 48.0D;

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
                    && item.getTag() != null && item.getTag().contains("SnakeAnimation")) {
                return false;
            }
            return herobrineMob.getState() != 0;
        }
        return false;
    }

    public static boolean hasNearbySixPortalSupport(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal() instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity
                && hasNearbySixPortalSupport(swordsmanHerobrineEntity);
    }

    public static boolean hasNoNearbySixPortalSupport(MobPatch<?> mobpatch) {
        return !hasNearbySixPortalSupport(mobpatch);
    }

    public static boolean canPlaySecondFormGuardAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity
                && hasNearbySixPortalSupport(swordsmanHerobrineEntity)) {
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
                DemoniacVoltageReaverItem.tryStartSnakeAnimation(item, herobrineMob, false);
            } else if (herobrineMob instanceof ReaperHerobrineEntity reaperHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                HerobrineDragonEntity herobrineDragonEntity = reaperHerobrineEntity.getThunderHerobrineDragon();
                if (herobrineDragonEntity != null) {
                    reaperHerobrineEntity.playSound(AnnoyingVillagersModSounds.REAPER_FIRE.get(), 1.0F, 1.0F);
                    herobrineDragonEntity.shootThunderBreathAtTarget(herobrineMob.getTarget());
                }
            }
        }
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
                    && hasNearbySixPortalSupport(swordsmanHerobrineEntity)) {
                return;
            }
            ItemStack item = herobrineMob.getMainHandItem();
            if (herobrineMob.getState() < 2) {
                herobrineMob.setSecondFormHitLeft(herobrineMob.getSecondFormHitLeft() - 1);
            }
            if (herobrineMob instanceof SwordsmanHerobrineEntity && herobrineMob.level() instanceof ServerLevel) {
                DemoniacVoltageReaverItem.tryStartSnakeAnimation(item, herobrineMob, true);
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

    public static boolean isSupportingHerobrineEscaping(MobPatch<?> mobpatch) {
        LivingEntity caster = mobpatch.getOriginal();
        return canUseSupportPortalAction(caster)
                && canUseSupportEscapePortal(caster)
                && caster.level() instanceof ServerLevel serverLevel
                && TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)
                && findEscapingSupportHerobrine(caster) != null;
    }

    public static void summonSupportingHerobrineEscapePortal(MobPatch<?> mobpatch) {
        LivingEntity caster = mobpatch.getOriginal();
        LivingEntity support = findEscapingSupportHerobrine(caster);
        if (support != null && spawnEscapePortalPair(caster, support) > 0) {
            markPortalSupportCaster(caster);
            setSupportEscapePortalCooldown(caster);
        }
    }

    public static boolean isSupportingHerobrineGettingShot(MobPatch<?> mobpatch) {
        LivingEntity caster = mobpatch.getOriginal();
        return canUseSupportPortalAction(caster)
                && canUseRangedCounterPortal(caster)
                && HerobrinePortalCombatUtil.canBowCounterPortalSupport(caster);
    }

    public static void summonSupportCounterPortal(MobPatch<?> mobpatch) {
        LivingEntity caster = mobpatch.getOriginal();
        if (HerobrinePortalCombatUtil.tryBowCounterPortalSupport(caster)) {
            markPortalSupportCaster(caster);
            setRangedCounterPortalCooldown(caster);
        }
    }

    public static boolean canSummon2Portal(MobPatch<?> mobpatch) {
        LivingEntity caster = mobpatch.getOriginal();
        if (caster instanceof HerobrineGregEntity greg) {
            return greg.canUseSupportPortalAction()
                    && greg.getPortalPairCooldown() <= 0
                    && HerobrinePortalCombatUtil.canGregPortalSupport(greg);
        }
        return caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.canUseSupportPortalAction()
                && transporter.getPortalPairCooldown() <= 0
                && HerobrinePortalCombatUtil.canTransporterPortalSupport(transporter);
    }

    public static void summon2Portal(MobPatch<?> mobpatch) {
        LivingEntity caster = mobpatch.getOriginal();
        if (caster instanceof HerobrineGregEntity greg) {
            if (HerobrinePortalCombatUtil.tryGregPortalSupport(greg)) {
                greg.setPortalPairCooldown();
            }
        } else if (caster instanceof TransporterHerobrineCloneEntity transporter) {
            if (HerobrinePortalCombatUtil.tryTransporterPortalSupport(transporter)) {
                transporter.setPortalPairCooldown();
            }
        }
    }

    public static boolean canDo6Portal(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal() instanceof HerobrineGregEntity herobrineGregEntity
                && herobrineGregEntity.canAnswerSixPortalSupportRequest()
                && findGregSixPortalSupportTarget(herobrineGregEntity) != null;
    }

    public static void do6Portal(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineGregEntity herobrineGregEntity) {
            SwordsmanHerobrineEntity swordsmanHerobrineEntity = findGregSixPortalSupportTarget(herobrineGregEntity);
            if (swordsmanHerobrineEntity == null) {
                return;
            }

            TransporterFragmentItem.PortalSpawnBatch portalBatch = TransporterFragmentItem.spawnPortalPairsBatch(
                    herobrineGregEntity.level(),
                    herobrineGregEntity,
                    swordsmanHerobrineEntity
            );
            if (portalBatch.spawned() <= 0) {
                return;
            }

            if (portalBatch.portalGroup() != null) {
                DemoniacVoltageReaverItem.setPreferredPortalTarget(
                        swordsmanHerobrineEntity.getMainHandItem(),
                        portalBatch.portalGroup(),
                        herobrineGregEntity.getUUID()
                );
            }

            herobrineGregEntity.markSupportingHerobrine();
            herobrineGregEntity.getLookControl().setLookAt(swordsmanHerobrineEntity, 30.0F, 30.0F);
            herobrineGregEntity.setSixPortalSupportCooldown();
        }
    }

    public static boolean canPerformPortalEscapeStepBack(MobPatch<?> mobpatch) {
        LivingEntity livingEntity = mobpatch.getOriginal();
        return canUseSupportPortalAction(livingEntity)
                && canUsePortalEscapeStepBack(livingEntity)
                && livingEntity.level() instanceof ServerLevel serverLevel
                && TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, livingEntity, 2);
    }

    public static void performPortalEscapeStepBack(MobPatch<?> mobpatch) {
        LivingEntity livingEntity = mobpatch.getOriginal();
        if (!(livingEntity.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, livingEntity, 2)
                || spawnEscapePortalPair(livingEntity, livingEntity) <= 0) {
            return;
        }
        setPortalEscapeStepBackCooldown(livingEntity);
        if (livingEntity instanceof Mob mob) {
            mob.getNavigation().stop();
            mob.setTarget(null);
        }
        livingEntity.setSprinting(false);
        new DelayedTask(10) {
            @Override
            public void run() {
                if (!livingEntity.isAlive()) {
                    return;
                }
                mobpatch.playAnimationSynchronized(Animations.BIPED_STEP_BACKWARD, 0.0F);
                pushStepBackIntoPortal(livingEntity, 0.65D);
                new DelayedTask(2) {
                    @Override
                    public void run() {
                        if (livingEntity.isAlive()) {
                            pushStepBackIntoPortal(livingEntity, 0.35D);
                        }
                    }
                };
            }
        };
    }

    public static boolean canSummonLowCloneSupport(MobPatch<?> mobpatch) {
        LivingEntity caster = mobpatch.getOriginal();
        if (!caster.onGround()) {
            return false;
        }
        if (caster instanceof HerobrineGregEntity greg) {
            return greg.canSummonLowCloneSupport()
                    && findGregLowCloneSupportEnemy(greg) != null;
        }
        return caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.canSummonLowCloneSupport()
                && canFindTransporterLowCloneSupportSpawn(transporter);
    }

    public static void summonLowCloneSupport(MobPatch<?> mobpatch) {
        LivingEntity caster = mobpatch.getOriginal();
        if (caster instanceof HerobrineGregEntity greg) {
            if (!(greg.level() instanceof ServerLevel serverLevel)) {
                return;
            }

            LivingEntity support = greg.findGregFollowSupportHerobrine();
            LivingEntity enemy = findGregLowCloneSupportEnemy(greg);
            if (support == null || enemy == null) {
                return;
            }

            int availableSlots = greg.getAvailableCombatLowCloneSupportSlotCount();
            int count = Math.min(1 + greg.getRandom().nextInt(3), availableSlots);
            int spawned = 0;
            for (int i = 0; i < count && greg.hasAvailableCombatLowCloneSupportSlot(); i++) {
                if (spawnGregCombatLowCloneNear(serverLevel, greg, support, enemy)) {
                    spawned++;
                }
            }

            if (spawned > 0) {
                greg.markSupportingHerobrine();
                HerobrinePortalCombatUtil.playClonePortalSummon(greg);
                greg.setLowCloneSupportCooldown();
            }
            return;
        }

        if (caster instanceof TransporterHerobrineCloneEntity transporter) {
            if (!(transporter.level() instanceof ServerLevel serverLevel)) {
                return;
            }

            int availableSlots = transporter.getAvailableCombatLowCloneSupportSlotCount();
            int count = Math.min(1 + transporter.getRandom().nextInt(3), availableSlots);
            int spawned = 0;
            for (int i = 0; i < count && transporter.hasAvailableCombatLowCloneSupportSlot(); i++) {
                if (spawnTransporterLowClone(serverLevel, transporter)) {
                    spawned++;
                }
            }

            if (spawned > 0) {
                HerobrinePortalCombatUtil.playClonePortalSummon(transporter);
                transporter.setLowCloneSupportCooldown();
            }
        }
    }

    private static boolean canUseSupportPortalAction(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            return greg.canUseSupportPortalAction();
        }
        return caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.canUseSupportPortalAction();
    }

    private static boolean canUseSupportEscapePortal(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            return greg.getSupportEscapePortalCooldown() <= 0;
        }
        return caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.getSupportEscapePortalCooldown() <= 0;
    }

    private static void setSupportEscapePortalCooldown(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            greg.setSupportEscapePortalCooldown();
        } else if (caster instanceof TransporterHerobrineCloneEntity transporter) {
            transporter.setSupportEscapePortalCooldown();
        }
    }

    private static boolean canUseRangedCounterPortal(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            return greg.getRangedCounterPortalCooldown() <= 0;
        }
        return caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.getRangedCounterPortalCooldown() <= 0;
    }

    private static void setRangedCounterPortalCooldown(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            greg.setRangedCounterPortalCooldown();
        } else if (caster instanceof TransporterHerobrineCloneEntity transporter) {
            transporter.setRangedCounterPortalCooldown();
        }
    }

    private static boolean canUsePortalEscapeStepBack(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            return greg.getPortalEscapeStepBackCooldown() <= 0;
        }
        return caster instanceof TransporterHerobrineCloneEntity transporter
                && transporter.getPortalEscapeStepBackCooldown() <= 0;
    }

    private static void setPortalEscapeStepBackCooldown(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            greg.setPortalEscapeStepBackCooldown();
        } else if (caster instanceof TransporterHerobrineCloneEntity transporter) {
            transporter.setPortalEscapeStepBackCooldown();
        }
    }

    private static void markPortalSupportCaster(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            greg.markSupportingHerobrine();
        }
    }

    @Nullable
    private static LivingEntity findEscapingSupportHerobrine(LivingEntity caster) {
        if (caster instanceof HerobrineGregEntity greg) {
            return greg.findEscapingSupportHerobrine();
        }
        for (LivingEntity support : HerobrinePortalCombatUtil.findSupportHerobrines(caster, 40.0D)) {
            if (support instanceof Mob mob
                    && support.isAlive()
                    && !(support.isPassenger() && support.getVehicle() instanceof HerobrineDragonEntity)
                    && mob.getTarget() != null
                    && mob.getTarget().isAlive()
                    && EscapeUtil.checkEscape(mob)) {
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

    private static Vec3 getRandomPortalEscapeExit(ServerLevel serverLevel, LivingEntity anchor) {
        Random random = new Random(anchor.getRandom().nextLong());
        if (serverLevel != null) {
            for (int attempt = 0; attempt < 16; attempt++) {
                double angle = random.nextDouble() * Math.PI * 2.0D;
                double distance = 9.0D + random.nextDouble() * 2.0D;
                double x = anchor.getX() + Math.cos(angle) * distance;
                double z = anchor.getZ() + Math.sin(angle) * distance;
                BlockPos surface = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(x, anchor.getY(), z));
                int yOffset = random.nextInt(3);
                BlockPos portalPos = surface.above(yOffset);
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
    private static LivingEntity findGregLowCloneSupportEnemy(HerobrineGregEntity greg) {
        LivingEntity support = greg.findGregFollowSupportHerobrine();
        if (support == null || !support.isAlive()
                || (support.isPassenger() && support.getVehicle() instanceof HerobrineDragonEntity)) {
            return null;
        }

        LivingEntity enemy = HerobrinePortalCombatUtil.findThreateningEnemy(
                greg,
                support,
                48
        );
        return enemy != null
                ? enemy
                : HerobrinePortalCombatUtil.findEnemyForSupport(support, greg.getTarget(), 48);
    }

    private static boolean spawnGregCombatLowCloneNear(ServerLevel serverLevel, HerobrineGregEntity greg, Entity anchor, LivingEntity enemy) {
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
            AnnoyingVillagers.PACKET_HANDLER.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> clone),
                    new ClientboundHerobrinePortalFx(new Vec3(x, y, z))
            );
            return true;
        }
        return false;
    }

    private static boolean canFindTransporterLowCloneSupportSpawn(TransporterHerobrineCloneEntity transporter) {
        if (!(transporter.level() instanceof ServerLevel serverLevel)) {
            return false;
        }
        if (findTransporterLowCloneEnemy(transporter) == null) {
            return false;
        }
        return findTransporterLowCloneSpawnPosition(transporter, serverLevel) != null;
    }

    private static boolean spawnTransporterLowClone(ServerLevel serverLevel, TransporterHerobrineCloneEntity transporter) {
        if (!transporter.hasAvailableCombatLowCloneSupportSlot()) {
            return false;
        }

        Vec3 spawnPos = findTransporterLowCloneSpawnPosition(transporter, serverLevel);
        if (spawnPos == null) {
            return false;
        }

        Mob clone = createCombatLowClone(serverLevel, transporter.getRandom().nextBoolean());
        clone.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, transporter.getYRot(), transporter.getXRot());
        if (!serverLevel.noCollision(clone)) {
            return false;
        }
        clone.lookAt(EntityAnchorArgument.Anchor.EYES, transporter.getEyePosition());
        configureCombatLowClone(clone);
        equipLowCloneGear(clone, transporter.getRandom());

        LivingEntity enemy = findTransporterLowCloneEnemy(transporter);
        if (enemy != null && enemy.isAlive()) {
            clone.setTarget(enemy);
            clone.lookAt(EntityAnchorArgument.Anchor.EYES, enemy.getEyePosition());
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
    private static LivingEntity findTransporterLowCloneEnemy(TransporterHerobrineCloneEntity transporter) {
        LivingEntity enemy = HerobrinePortalCombatUtil.findThreateningEnemy(
                transporter,
                null,
                TransporterHerobrineCombatValues.SUPPORT_AVOID_SEARCH_RADIUS
        );
        return enemy != null
                ? enemy
                : HerobrinePortalCombatUtil.findEnemyForSupport(
                transporter,
                null,
                TransporterHerobrineCombatValues.SUPPORT_AVOID_SEARCH_RADIUS
        );
    }

    @Nullable
    private static Vec3 findTransporterLowCloneSpawnPosition(TransporterHerobrineCloneEntity transporter, ServerLevel serverLevel) {
        RandomSource random = transporter.getRandom();
        for (int attempt = 0; attempt < 32; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double distance = 3.0D + random.nextDouble() * 7.0D;
            double x = transporter.getX() + Math.cos(angle) * distance;
            double z = transporter.getZ() + Math.sin(angle) * distance;
            int groundX = Mth.floor(x);
            int groundZ = Mth.floor(z);
            int y = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, groundX, groundZ);
            BlockPos surface = BlockPos.containing(groundX, y, groundZ);

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

    private static boolean hasNearbySixPortalSupport(SwordsmanHerobrineEntity swordsmanHerobrineEntity) {
        UUID gregUuid = swordsmanHerobrineEntity.getGregUUID();
        if (gregUuid != null && HerobrinePortalCombatUtil.hasNearbyPortalGroup(
                swordsmanHerobrineEntity,
                gregUuid,
                6,
                SWORDSMAN_SIX_PORTAL_RADIUS
        )) {
            return true;
        }

        return HerobrinePortalCombatUtil.hasNearbyPortalGroup(
                swordsmanHerobrineEntity,
                null,
                6,
                SWORDSMAN_SIX_PORTAL_RADIUS
        );
    }

    private static SwordsmanHerobrineEntity findGregSixPortalSupportTarget(HerobrineGregEntity herobrineGregEntity) {
        if (!(herobrineGregEntity.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, herobrineGregEntity, 6)) {
            return null;
        }

        for (LivingEntity support : HerobrinePortalCombatUtil.findSupportHerobrines(herobrineGregEntity, 40.0D)) {
            if (support instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity
                    && canUseGregSixPortalSupport(herobrineGregEntity, swordsmanHerobrineEntity)) {
                return swordsmanHerobrineEntity;
            }
        }

        return null;
    }

    private static boolean canUseGregSixPortalSupport(HerobrineGregEntity herobrineGregEntity, SwordsmanHerobrineEntity swordsmanHerobrineEntity) {
        return swordsmanHerobrineEntity.isAlive()
                && swordsmanHerobrineEntity.getState() > 0
                && swordsmanHerobrineEntity.getTarget() != null
                && swordsmanHerobrineEntity.getTarget().isAlive()
                && swordsmanHerobrineEntity.getMainHandItem().is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())
                && !DemoniacVoltageReaverItem.hasSnakeAnimation(swordsmanHerobrineEntity.getMainHandItem())
                && !hasNearbySixPortalSupport(swordsmanHerobrineEntity)
                && (swordsmanHerobrineEntity.getGregUUID() == null
                || swordsmanHerobrineEntity.getGregUUID().equals(herobrineGregEntity.getUUID()));
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
