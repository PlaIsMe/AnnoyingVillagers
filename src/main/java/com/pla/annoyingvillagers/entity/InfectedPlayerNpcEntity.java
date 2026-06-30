package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.FakePlayer;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import javax.annotation.Nullable;
import java.util.Objects;

public class InfectedPlayerNpcEntity extends FakePlayer {
    final LivingEntityPatch<?> livingEntityPatch =  EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);

    public InfectedPlayerNpcEntity(EntityType<? extends InfectedPlayerNpcEntity> type, Level level) {
        super(type, level);
        this.xpReward = 300;
        this.setMaxUpStep(0.6F);
        this.xpReward = 7;
        this.setNoAi(true);
        this.setCustomNameVisible(true);
    }

    public InfectedPlayerNpcEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.INFECTED_PLAYER_NPC.get(), level);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.hurt")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.death")));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && this.livingEntityPatch != null) {
            this.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 2, 0, false, false));
            this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 2, 0, false, false));
            this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 2, 0, false, false));
        }
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        String possessedBy = this.getPersistentData().getString("possessed_by");
        switch (possessedBy) {
            case "herobrine_clone" -> HerobrineUtil.dropHerobrineCloneLoot(this.level(), this.getX(), this.getY(), this.getZ());
            case "shadow_herobrine_clone" -> HerobrineUtil.dropShadowHerobrineCloneLoot(this.level(), this.getX(), this.getY(), this.getZ());
            case "low_herobrine_clone", "low_shadow_herobrine_clone" -> HerobrineUtil.dropLowHerobrineCloneLoot(this.level(), this.getX(), this.getY(), this.getZ());
            case "herobrine_7" -> HerobrineUtil.dropHerobrine7Loot(this.level(), this.getX(), this.getY(), this.getZ());
            case "shadow_herobrine" -> HerobrineUtil.dropShadowHerobrineLoot(this.level(), this.getX(), this.getY(), this.getZ());
            case "null" -> HerobrineUtil.dropNullLoot(this.level(), this.getX(), this.getY(), this.getZ());
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            ItemStack itemstack;
            ItemEntity itementity;

            itemstack = this.getItemBySlot(EquipmentSlot.FEET);
            itementity = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
            itementity.setPickUpDelay(10);
            serverLevel.addFreshEntity(itementity);

            itemstack = this.getItemBySlot(EquipmentSlot.FEET);
            itementity = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
            itementity.setPickUpDelay(10);
            serverLevel.addFreshEntity(itementity);

            itemstack = this.getItemBySlot(EquipmentSlot.CHEST);
            itementity = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
            itementity.setPickUpDelay(10);
            serverLevel.addFreshEntity(itementity);

            itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
            itementity = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
            itementity.setPickUpDelay(10);
            serverLevel.addFreshEntity(itementity);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (!this.level().isClientSide()) {
            TeamUtil.addOrJoinTeam(this, "herobrine");
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity entity) {}

    protected void pushEntities() {}

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32.0D);
        return builder;
    }
}
