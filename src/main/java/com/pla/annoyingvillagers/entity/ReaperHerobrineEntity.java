package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.entity.goal.EliteHerobrineSecondFormGoal;
import com.pla.annoyingvillagers.entity.goal.MountOrDismountDragonGoal;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


public class ReaperHerobrineEntity extends HerobrineMob {
    private HerobrineDragonEntity thunderHerobrineDragon;
    private UUID thunderHerobrineDragonUUID;
    private HerobrineDragonEntity meteoriteHerobrineDragon;
    private UUID meteoriteHerobrineDragonUUID;
    private HerobrineDragonEntity healingHerobrineDragon;
    private UUID healingHerobrineDragonUUID;
    private boolean spawnDragonInit = false;
    private int dragonSummonCooldown = 0;
    private int pendingDragonSummonType = -1;

    private static final int SUMMON_RISE_DURATION_TICKS = 120;
    private static final double SUMMON_UNDERGROUND_DISTANCE = 5.0D;
    private static final double SUMMON_RISE_DISTANCE = 15.0D;

    public ReaperHerobrineEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), level);
    }

    public ReaperHerobrineEntity(EntityType<ReaperHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.9F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
        this.setChatName(this.getDisplayName().getString());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new EliteHerobrineSecondFormGoal<>(
                this,
                ReaperHerobrineEntity::hasAvailableSecondFormDragon,
                ReaperHerobrineEntity::selectAvailableSecondFormDragonAnimation
        ));
        this.goalSelector.addGoal(1, new MountOrDismountDragonGoal(this));
    }

    @Override
    public @Nullable SoundEvent getAttackVoiceSound() {
        return AnnoyingVillagersModSounds.ELITE_HEROBRINE_SAY.get();
    }

    public HerobrineDragonEntity getThunderHerobrineDragon() {
        return thunderHerobrineDragon;
    }

    public UUID getThunderHerobrineDragonUUID() {
        return thunderHerobrineDragonUUID;
    }

    public boolean canRideSummonedDragon() {
        return this.getState() == 2;
    }

    public boolean isSecondFormDragonRider() {
        return this.getState() == 2
                && this.isPassenger()
                && this.getVehicle() instanceof HerobrineDragonEntity;
    }

    public HerobrineDragonEntity getMeteoriteHerobrineDragon() {
        return meteoriteHerobrineDragon;
    }

    public UUID getMeteoriteHerobrineDragonUUID() {
        return meteoriteHerobrineDragonUUID;
    }

    public HerobrineDragonEntity getHealingHerobrineDragon() {
        return healingHerobrineDragon;
    }

    public UUID getHealingHerobrineDragonUUID() {
        return healingHerobrineDragonUUID;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (thunderHerobrineDragonUUID != null) {
            tag.putUUID("ThunderHerobrineDragonUUID", thunderHerobrineDragonUUID);
        }
        if (meteoriteHerobrineDragonUUID != null) {
            tag.putUUID("MeteoriteHerobrineDragonUUID", meteoriteHerobrineDragonUUID);
        }
        if (healingHerobrineDragonUUID != null) {
            tag.putUUID("HealingHerobrineDragonUUID", healingHerobrineDragonUUID);
        }
        tag.putBoolean("SpawnDragonInit", spawnDragonInit);
        tag.putInt("DragonSummonCooldown", dragonSummonCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("ThunderHerobrineDragonUUID")) {
            thunderHerobrineDragonUUID = tag.getUUID("ThunderHerobrineDragonUUID");
        }
        if (tag.hasUUID("MeteoriteHerobrineDragonUUID")) {
            meteoriteHerobrineDragonUUID = tag.getUUID("MeteoriteHerobrineDragonUUID");
        }
        if (tag.hasUUID("HealingHerobrineDragonUUID")) {
            healingHerobrineDragonUUID = tag.getUUID("HealingHerobrineDragonUUID");
        }
        spawnDragonInit = tag.getBoolean("SpawnDragonInit");
        dragonSummonCooldown = tag.contains("DragonSummonCooldown") ? tag.getInt("DragonSummonCooldown") : dragonSummonCooldown;
    }

    // 0: thunder dragon
    // 1: meteorite dragon
    // 2: healing dragon
    public void summonEnderDragon(int type) {
        if (!(this.level() instanceof ServerLevel) || type < 0 || type > 2) return;
        if (this.pendingDragonSummonType >= 0 || RigAnimationController.hasActiveAnimation(this)) return;

        this.pendingDragonSummonType = type;
        this.getNavigation().stop();
        this.setAggressive(false);
        this.playSound(AnnoyingVillagersModSounds.REAPER_SUMMON.get(), 2.0F, 1.0F);

        RigAnimationController.play(this, RigAnimationSpecs.get(RigAnimationId.REAPER_HEROBRINE_ULT), this.getTarget());
        if (RigAnimationController.getActiveAnimationId(this) != RigAnimationId.REAPER_HEROBRINE_ULT) {
            this.pendingDragonSummonType = -1;
            return;
        }

        // Lock normal rig profile attacks for the entire summoning animation.
        RigAnimationController.lockProfileAttacksFor(this, RigAnimationId.REAPER_HEROBRINE_ULT);
    }

    public boolean isDragonSummonPending() {
        return this.pendingDragonSummonType >= 0;
    }

    /** Called only by the REAPER_HEROBRINE_ULT timed hook. */
    public void completePendingDragonSummon() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        if (this.pendingDragonSummonType < 0) return;
        if (RigAnimationController.getActiveAnimationId(this) != RigAnimationId.REAPER_HEROBRINE_ULT) return;

        int type = this.pendingDragonSummonType;
        this.pendingDragonSummonType = -1;
        HerobrineDragonEntity dragon = spawnSummonedDragon(serverLevel);
        if (dragon == null) return;

        if (type == 0) {
            this.thunderHerobrineDragonUUID = dragon.getUUID();
            this.thunderHerobrineDragon = dragon;
        } else if (type == 1) {
            this.meteoriteHerobrineDragonUUID = dragon.getUUID();
            this.meteoriteHerobrineDragon = dragon;
        } else {
            this.healingHerobrineDragonUUID = dragon.getUUID();
            this.healingHerobrineDragon = dragon;
            respawnHealingCrystal();
        }
    }

    @Nullable
    private HerobrineDragonEntity spawnSummonedDragon(ServerLevel serverLevel) {
        if (!this.isAlive()) return null;

        HerobrineDragonEntity dragon = new HerobrineDragonEntity(AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get(), serverLevel);
        Vec3 spawnPos = findSummonSpawnPos(serverLevel);
        dragon.setPos(spawnPos);
        dragon.setYRot(this.getYRot());
        dragon.setYHeadRot(this.getYRot());
        dragon.setYBodyRot(this.getYRot());
        dragon.setXRot(-85.0F);
        dragon.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(BlockPos.containing(spawnPos)), MobSpawnType.MOB_SUMMONED, null, null);
        dragon.setPos(spawnPos);
        dragon.setYRot(this.getYRot());
        dragon.setYHeadRot(this.getYRot());
        dragon.setYBodyRot(this.getYRot());
        dragon.setXRot(-85.0F);
        dragon.setPersistenceRequired();
        dragon.setSummoner(this);
        dragon.setSummonerUUID(this.getUUID());
        dragon.startSummonRise(findSummonRiseTarget(serverLevel, dragon), SUMMON_RISE_DURATION_TICKS, this.getY());

        if (!serverLevel.addFreshEntity(dragon)) return null;
        TeamUtil.addOrJoinTeam(dragon, "herobrine");
        return dragon;
    }

    private Vec3 findSummonSpawnPos(ServerLevel serverLevel) {
        double y = Mth.clamp(
                this.getY() - SUMMON_UNDERGROUND_DISTANCE,
                serverLevel.getMinBuildHeight() + 2.0D,
                serverLevel.getMaxBuildHeight() - 8.0D
        );
        return new Vec3(this.getX(), y, this.getZ());
    }

    private Vec3 findSummonRiseTarget(ServerLevel serverLevel, HerobrineDragonEntity dragon) {
        double minY = serverLevel.getMinBuildHeight() + 6.0D;
        double maxY = serverLevel.getMaxBuildHeight() - 6.0D;

        if (serverLevel.dimensionType().hasCeiling()) {
            BlockPos column = BlockPos.containing(this.getX(), 0.0D, this.getZ());
            int roofAirY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column).getY();
            maxY = Math.min(maxY, roofAirY - dragon.getBbHeight() - 2.0D);
        }

        if (maxY < minY) maxY = minY;

        double desiredY = Mth.clamp(this.getY() + SUMMON_RISE_DISTANCE, minY, maxY);
        int start = Mth.floor(desiredY);
        int end = Mth.floor(Math.max(minY, this.getY() + 8.0D));

        for (int y = start; y >= end; y--) {
            if (canDragonFitAt(serverLevel, dragon, this.getX(), y, this.getZ())) {
                return new Vec3(this.getX(), y, this.getZ());
            }
        }

        return new Vec3(this.getX(), desiredY, this.getZ());
    }

    private static boolean canDragonFitAt(ServerLevel serverLevel, HerobrineDragonEntity dragon, double x, double y, double z) {
        AABB movedBox = dragon.getBoundingBox().move(
                x - dragon.getX(),
                y - dragon.getY(),
                z - dragon.getZ()
        );
        return serverLevel.noCollision(dragon, movedBox) && !serverLevel.containsAnyLiquid(movedBox);
    }

    public boolean hasAvailableSecondFormDragon() {
        return isThunderDragonAvailable() || isMeteoriteDragonAvailable() || isHealingDragonAvailable();
    }

    @Nullable
    public RigAnimationId selectAvailableSecondFormDragonAnimation() {
        List<RigAnimationId> available = new ArrayList<>(3);
        if (isThunderDragonAvailable()) available.add(RigAnimationId.POINT_LEFT_HAND_TOWARD);
        if (isHealingDragonAvailable()) available.add(RigAnimationId.POINT_LEFT_HAND_MIDDLE);
        if (isMeteoriteDragonAvailable()) available.add(RigAnimationId.POINT_LEFT_HAND_UP);
        return available.isEmpty() ? null : available.get(this.getRandom().nextInt(available.size()));
    }

    public boolean isThunderDragonAvailable() {
        return isCommandDragonAvailable(this.thunderHerobrineDragon);
    }

    public boolean isMeteoriteDragonAvailable() {
        return isCommandDragonAvailable(this.meteoriteHerobrineDragon);
    }

    public boolean isHealingDragonAvailable() {
        return this.healingHerobrineDragon != null
                && this.healingHerobrineDragon.isAlive()
                && !this.healingHerobrineDragon.isRemoved()
                && this.healingHerobrineDragon.getPassengers().isEmpty();
    }

    private boolean isCommandDragonAvailable(HerobrineDragonEntity dragon) {
        if (dragon == null || !dragon.isAlive() || dragon.isRemoved()) {
            return false;
        }

        /*
         * Once Reaper is already riding a summoned dragon in state 2, the recall
         * system must not disable his dragon-command attacks. A stale recallActive
         * flag is only relevant while recalling/landing before the mount completes.
         */
        return this.isSecondFormDragonRider() || !dragon.isRecallActive();
    }

    public void castThunderFromSecondForm() {
        if (!(this.level() instanceof ServerLevel) || !this.canUseSecondFormAction() || !isThunderDragonAvailable()) return;
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) return;
        this.playSound(AnnoyingVillagersModSounds.REAPER_FIRE.get(), 1.0F, 1.0F);
        this.thunderHerobrineDragon.shootThunderBreathAtTarget(target);
        this.consumeSecondFormAction();
    }

    public void castMeteoriteFromSecondForm() {
        if (!(this.level() instanceof ServerLevel) || !this.canUseSecondFormAction() || !isMeteoriteDragonAvailable()) return;
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) return;
        this.meteoriteHerobrineDragon.shootMeteoriteAtTarget(target);
        this.consumeSecondFormAction();
    }

    public void respawnHealingCrystalFromSecondForm() {
        if (!(this.level() instanceof ServerLevel) || !this.canUseSecondFormAction() || !isHealingDragonAvailable()) return;
        respawnHealingCrystal();
        this.consumeSecondFormAction();
    }

    private void respawnHealingCrystal() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        if (this.healingHerobrineDragon == null || !this.healingHerobrineDragon.isAlive()
                || !this.healingHerobrineDragon.getPassengers().isEmpty()) return;

        EndCrystal endCrystal = new EndCrystal(EntityType.END_CRYSTAL, serverLevel);
        endCrystal.moveTo(this.healingHerobrineDragon.getX(), this.healingHerobrineDragon.getY(), this.healingHerobrineDragon.getZ());
        serverLevel.addFreshEntity(endCrystal);
        endCrystal.startRiding(this.healingHerobrineDragon, true);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            // Reaper is only allowed to remain mounted during full second form.
            if (this.isPassenger()
                    && this.getVehicle() instanceof HerobrineDragonEntity
                    && !this.canRideSummonedDragon()) {
                this.stopRiding();
            }

            // Do not permanently consume the init summon flag just because another rig
            // animation (or a stun) happened to be active on Reaper's first server tick.
            // Keep retrying until the thunder summon request actually owns the ULT.
            if (!spawnDragonInit) {
                if (thunderHerobrineDragon != null || thunderHerobrineDragonUUID != null) {
                    this.spawnDragonInit = true;
                } else {
                    summonEnderDragon(0);
                    if (this.pendingDragonSummonType == 0
                            && RigAnimationController.getActiveAnimationId(this) == RigAnimationId.REAPER_HEROBRINE_ULT) {
                        this.spawnDragonInit = true;
                    }
                }
            }

            // A pending request normally resolves at the ULT tick-22 hook. If another
            // system ever interrupts/replaces the ULT first, release the request so the
            // normal dragon progression logic can retry instead of becoming stuck forever.
            if (this.pendingDragonSummonType >= 0
                    && RigAnimationController.getActiveAnimationId(this) != RigAnimationId.REAPER_HEROBRINE_ULT) {
                this.pendingDragonSummonType = -1;
            }

            if (dragonSummonCooldown <= 0) {
                // First stage
                // Below 50% health summon meteorite
                // Above 50% health summon thunder
                // Second stage
                // Summon any missing dragon
                if (this.getState() < 2) {
                    if (this.getHealth() > this.getMaxHealth() / 2 && thunderHerobrineDragon == null && thunderHerobrineDragonUUID == null) {
                        summonEnderDragon(0);
                    } else if (this.getHealth() <= this.getMaxHealth() / 2 && meteoriteHerobrineDragon == null && meteoriteHerobrineDragonUUID == null) {
                        summonEnderDragon(1);
                    }
                } else if (this.getState() == 2) {
                    if (this.thunderHerobrineDragon == null && thunderHerobrineDragonUUID == null) {
                        summonEnderDragon(0);
                    } else if (meteoriteHerobrineDragon == null && meteoriteHerobrineDragonUUID == null) {
                        summonEnderDragon(1);
                    } else if (healingHerobrineDragon == null && healingHerobrineDragonUUID == null) {
                        summonEnderDragon(2);
                    }
                }
            } else {
                dragonSummonCooldown--;
            }

            if (thunderHerobrineDragon == null && thunderHerobrineDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(thunderHerobrineDragonUUID);
                if (entity instanceof HerobrineDragonEntity dragon) {
                    thunderHerobrineDragon = dragon;
                } else {
                    thunderHerobrineDragon = null;
                }
            }

            if (meteoriteHerobrineDragon == null && meteoriteHerobrineDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(meteoriteHerobrineDragonUUID);
                if (entity instanceof HerobrineDragonEntity dragon) {
                    meteoriteHerobrineDragon = dragon;
                } else {
                    meteoriteHerobrineDragon = null;
                }
            }

            if (healingHerobrineDragon == null && healingHerobrineDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(healingHerobrineDragonUUID);
                if (entity instanceof HerobrineDragonEntity dragon) {
                    healingHerobrineDragon = dragon;
                } else {
                    healingHerobrineDragon = null;
                }
            }

            if (thunderHerobrineDragon != null && !thunderHerobrineDragon.isAlive()) {
                thunderHerobrineDragon = null;
                thunderHerobrineDragonUUID = null;
                if (dragonSummonCooldown == 0) {
                    if (this.getState() < 2) {
                        dragonSummonCooldown = new Random().nextInt(4800, 7200);
                    } else if (this.getState() == 2) {
                        dragonSummonCooldown = new Random().nextInt(2400, 4800);
                    }
                }
            }

            if (meteoriteHerobrineDragon != null && !meteoriteHerobrineDragon.isAlive()) {
                meteoriteHerobrineDragon = null;
                meteoriteHerobrineDragonUUID = null;
                if (dragonSummonCooldown == 0) {
                    if (this.getState() < 2) {
                        dragonSummonCooldown = new Random().nextInt(4800, 7200);
                    } else if (this.getState() == 2) {
                        dragonSummonCooldown = new Random().nextInt(2400, 4800);
                    }
                }
            }

            if (healingHerobrineDragon != null && !healingHerobrineDragon.isAlive()) {
                healingHerobrineDragon = null;
                healingHerobrineDragonUUID = null;

                if (dragonSummonCooldown == 0) {
                    if (this.getState() < 2) {
                        dragonSummonCooldown = new Random().nextInt(4800, 7200);
                    } else if (this.getState() == 2) {
                        dragonSummonCooldown = new Random().nextInt(2400, 4800);
                    }
                }
            }

        }
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (!(damagesource.getDirectEntity() instanceof EnchantedArrowEntity)
                && damagesource.getDirectEntity() instanceof AbstractArrow
                && !(damagesource.getDirectEntity() instanceof BlueDemonThrownTridentEntity)) return false;
        return super.hurt(damagesource, f);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        if (this.thunderHerobrineDragon != null) {
            this.thunderHerobrineDragon.kill();
            this.thunderHerobrineDragon = null;
            this.thunderHerobrineDragonUUID = null;
        }
        if (this.meteoriteHerobrineDragon != null) {
            this.meteoriteHerobrineDragon.kill();
            this.meteoriteHerobrineDragon = null;
            this.meteoriteHerobrineDragonUUID = null;
        }
        if (this.healingHerobrineDragon != null) {
            this.healingHerobrineDragon.kill();
            this.healingHerobrineDragon = null;
            this.healingHerobrineDragonUUID = null;
        }
        super.remove(reason);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity = new EliteHerobrineKnockedEntity(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), serverLevel);

            eliteHerobrineKnockedEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            eliteHerobrineKnockedEntity.getPersistentData().putString("FromElite", "EnderSlayerScythe");
            eliteHerobrineKnockedEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(eliteHerobrineKnockedEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(eliteHerobrineKnockedEntity);

            if (this.getGregUUID() != null) {
                Entity entity = serverLevel.getEntity(this.getGregUUID());
                if (entity instanceof HerobrineGregEntity herobrineGregEntity && entity.isAlive()) {
                    herobrineGregEntity.requestProtect(eliteHerobrineKnockedEntity.getUUID(), eliteHerobrineKnockedEntity);
                }
            }
        }
    }

    public static Builder addEpicFightAttributes(Builder builder) {
        //      ADD THIS CODE IN AV_EFM
//        return builder.add(EpicFightAttributes.IMPACT.get(), 4.0D)
//                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10.0D)
//                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
//                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
//                .add(EpicFightAttributes.MAX_STAMINA.get(), 60.0D)
//                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);

        return builder;
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 80.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 40.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return addEpicFightAttributes(builder);
    }
}

