package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.spawnhandler.BluedemonData;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

import java.util.Random;
import java.util.UUID;
import net.minecraft.util.RandomSource;

public class BlueDemonEntity extends Monster {
    private BbqEntity bbqEntityToProtect;
    private UUID bbqUUID;
    private boolean spawnBbq = false;

    public void setProtectingBbq(BbqEntity bbqEntity) {
        this.bbqEntityToProtect = bbqEntity;
    }

    public void setBbqUUID(UUID bbqUUID) {
        this.bbqUUID = bbqUUID;
    }

    public BlueDemonEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), level);
    }

    public UUID getBbqUUID() {
        return bbqUUID;
    }

    public BlueDemonEntity(EntityType<BlueDemonEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike) AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get()));
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> bbqEntityToProtect != null
                && bbqEntityToProtect.isAlive()
                && target != null
                && target.getLastHurtMob() == bbqEntityToProtect));
        CommonGoals.registerGoalForBlueDemonNpc(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (!spawnBbq) {
                this.spawnBbq = true;
                spawnBbq();
            }

            if (bbqEntityToProtect == null && bbqUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(bbqUUID);
                if (entity instanceof BbqEntity bbqEntity) {
                    bbqEntityToProtect = bbqEntity;
                } else {
                    bbqUUID = null;
                }
            }
            if (bbqEntityToProtect != null && !bbqEntityToProtect.isAlive()) {
                bbqEntityToProtect = null;
                bbqUUID = null;
            }
        }
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        BlueDemonOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity());
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.TRIDENT)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        BlueDemonOnEntityDeathProcedure.execute(this.level(), this, damagesource.getEntity());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (bbqUUID != null) {
            tag.putUUID("BbqUUID", bbqUUID);
        }
        tag.putBoolean("SpawnBbq", spawnBbq);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("BbqUUID")) {
            bbqUUID = tag.getUUID("BbqUUID");
        }
        spawnBbq = tag.getBoolean("SpawnBbq");
    }

    private void spawnBbq() {
        if (this.level() instanceof ServerLevel serverLevel) {
            BbqEntity bbqEntity = new BbqEntity((EntityType) AnnoyingVillagersModEntities.BBQ.get(), serverLevel);
            bbqEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            bbqEntity.setFollowTarget(this);
            bbqEntity.setFollowTargetUUID(this.getUUID());

            bbqEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            serverLevel.addFreshEntity(bbqEntity);

            this.setProtectingBbq(bbqEntity);
            this.setBbqUUID(bbqEntity.getUUID());
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (mobspawntype == MobSpawnType.NATURAL || mobspawntype == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverlevelaccessor.getLevel();
            BluedemonData bluedemonData = BluedemonData.get(serverLevel);

            if (!bluedemonData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            }

            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            this.moveTo(spawnPos, this.getYRot(), this.getXRot());
        }

        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        BlueDemonOnEntityInitialSpawnProcedure.execute(this.level(), this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        BlueDemonOnEntityKillOtherEntityProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity);
    }

    public void baseTick() {
        super.baseTick();
        BlueDemonOnEntityUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public static boolean canSpawn(EntityType<BlueDemonEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (!serverLevel.isThundering()) return false;
        if (BluedemonData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return Monster.checkAnyLightMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            BluedemonData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 5.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
