package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import com.pla.annoyingvillagers.rig.RigStunnableEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class InfectedTheMostMoistBurrit0Entity extends PathfinderMob implements RigStunnableEntity {
    private boolean initialSpawn = false;
        public InfectedTheMostMoistBurrit0Entity(EntityType<InfectedTheMostMoistBurrit0Entity> entitytype, Level level) {
        super(entitytype, level);
        this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(0.6F);
        this.xpReward = 0;
        this.setNoAi(true);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get()));
    }

            public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.death"));
    }

    @Override
    public void tick() {
        super.tick();
        if (!(this.level() instanceof ServerLevel)) return;
        if (this.tickCount == 1 && !this.initialSpawn) {
            if (AnnoyingVillagersConfig.TURN_ON_NPC_VOICE.get()) {
                this.playSound(AnnoyingVillagersModSounds.INFECTED_THE_MOSTMOISTBURRIT0_SAY_ON_SPAWN.get(), 0.5F, 1.0F);
            }
            this.initialSpawn = true;
        }
        this.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE, 2, 0, false, false));
        CommonUtil.stunImmunity(this, 2, 0);
    }

    @Override
    protected void dropCustomDeathLoot(net.minecraft.server.level.ServerLevel level, @NotNull DamageSource source, boolean recentlyHit) {
        int looting = 0;
        super.dropCustomDeathLoot(level, source, recentlyHit);
        HerobrineUtil.dropArmoredHerobrineLoot(this.level(), this.getX(), this.getY(), this.getZ());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("InitialSpawn", this.initialSpawn);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.initialSpawn = tag.getBoolean("InitialSpawn");
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (!this.level().isClientSide()) {
            TeamUtil.addOrJoinTeam(this, "herobrine");
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32.0D);
        return builder;
    }
}
