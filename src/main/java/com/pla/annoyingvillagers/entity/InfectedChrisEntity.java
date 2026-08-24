package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.rig.RigStunnableEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class InfectedChrisEntity extends PathfinderMob implements RigStunnableEntity {
    public InfectedChrisEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.INFECTED_CHRIS.get(), level);
    }

    public InfectedChrisEntity(EntityType<InfectedChrisEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 7;
        this.setNoAi(true);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.generic.death"));
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        HerobrineUtil.dropHerobrineChrisLoot(this.level(), this.getX(), this.getY(), this.getZ());
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata, compoundtag);

        if (!this.level().isClientSide()) {
            this.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 3000, 1));
        }
        return returnSpawnGroupData;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 2, 0, false, false));
            CommonUtil.stunImmunity(this, 2, 0);
        }
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity entity) {}

    protected void pushEntities() {}

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
