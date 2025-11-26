package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.spawnhandler.BluedemonData;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.BlueDemon2OnEntityUpdateProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemon2OnEntityDeathProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemon2ParryingProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemon2OnEntityInitialSpawnProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemonOnEntityKillOtherEntityProcedure;

import java.util.Random;
import java.util.UUID;

public class BlueDemon2Entity extends Monster {
    private BbqEntity bbqEntityToProtect;
    private UUID bbqUUID;
    private boolean spawnBbq = false;

    public void setProtectingBbq(BbqEntity bbqEntity) {
        this.bbqEntityToProtect = bbqEntity;
    }

    public void setBbqUUID(UUID bbqUUID) {
        this.bbqUUID = bbqUUID;
    }

    public BlueDemon2Entity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_2.get(), level);
    }

    public BlueDemon2Entity(EntityType<BlueDemon2Entity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 400;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike) AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get()));
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
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft","entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft","entity.generic.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        BlueDemon2ParryingProcedure.execute(this);
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.TRIDENT)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        return super.hurt(damagesource, f);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        BlueDemon2OnEntityDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        double posX = this.getX();
        double posY = this.getY();
        double posZ = this.getZ();
        LevelAccessor levelAccessor = this.level();
        if (levelAccessor instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
            ServerLevel serverlevel = levelaccessor;
            BlueDemonDeadEntity deadEntity = new BlueDemonDeadEntity((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_DEAD.get(), serverlevel);
            deadEntity.moveTo(posX, posY, posZ, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            if (deadEntity instanceof Mob) {
                Mob mob = (Mob)deadEntity;
                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(deadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            }
            this.remove(RemovalReason.KILLED);
            levelaccessor.addFreshEntity(deadEntity);
            try {
                deadEntity.getServer().getCommands().getDispatcher().execute(
                        "kill @s",
                        deadEntity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        BlueDemon2OnEntityInitialSpawnProcedure.execute(this.level(), this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        BlueDemonOnEntityKillOtherEntityProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity);
    }

    public void baseTick() {
        super.baseTick();
        BlueDemon2OnEntityUpdateProcedure.execute(this.level(), (int) this.getX(), (int) this.getY(), (int) this.getZ(), this);
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
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;

            BbqEntity bbqEntity = new BbqEntity((EntityType) AnnoyingVillagersModEntities.BBQ.get(), serverlevel);
            bbqEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            bbqEntity.setFollowTarget(this);
            bbqEntity.setFollowTargetUUID(this.getUUID());
            bbqEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(bbqEntity);

            this.setProtectingBbq(bbqEntity);
            this.setBbqUUID(bbqEntity.getUUID());
        }
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
        builder = builder.add(Attributes.ARMOR, 1.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 7.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
