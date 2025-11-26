package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

public class AegisHerobrineEntity extends HerobrineMob {
    private int nextStack = 3;
    public AegisHerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), level);
    }

    public AegisHerobrineEntity(EntityType<AegisHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.5F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.ENDER_AEGIS.get()));
        this.setChatName(this.getDisplayName().getString());
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public @NotNull SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public int getCooldownTicks() {
        return this.getPersistentData().getInt("ShieldCooldown");
    }

    public void setCooldownTicks(int ticks) {
        this.getPersistentData().putInt("ShieldCooldown", ticks);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        ItemStack itemStack = this.getMainHandItem();
        if (itemStack.getTag().getBoolean("SecondForm") && pEntity instanceof LivingEntity livingEntity) {
            if (!pEntity.level().isClientSide()) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 2));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 2));
                livingEntity.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 40, 2));
            }
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public void tick() {
        super.tick();
        boolean playSound = false;
        if (!this.level().isClientSide()) {
            if (this.tickCount == 5 && this.getPersistentData().getBoolean("init_animation")) {
                final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
                if (livingentitypatch != null) {
                    livingentitypatch.playAnimationSynchronized(AVAnimations.BLOCK_MAINHAND, 0.0F);
                }
            }

            ItemStack itemStack = this.getMainHandItem();
            if (itemStack.getTag() == null) return;
            if (itemStack.getTag().getBoolean("SecondForm")) {
                HerobrineWeaponEffectProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);

                int cooldown = getCooldownTicks();
                if (cooldown > 0) {
                    setCooldownTicks(cooldown - 1);
                } else {
                    itemStack.getTag().remove("SecondForm");
                }
            } else if (!itemStack.getTag().getBoolean("SecondForm") && this.getPersistentData().getInt("ParryCount") >= nextStack) {
                itemStack.getTag().putBoolean("SecondForm", true);
                setCooldownTicks(200);
                if (!this.level().isClientSide()) {
                    this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2));
                    this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 2));
                }
                if (itemStack.getItem() instanceof EnderAegisItem enderAegisItem) {
                    enderAegisItem.shieldShoot(this.level(), this);
                }
                this.getPersistentData().remove("ParryCount");
                nextStack = new Random().nextInt(3, 6);
                playSound = true;
            }
        }
        if (playSound) {
            if (!this.level().isClientSide()) {
                this.level().playSound((Player) null, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        pCompound.putInt("NextStack", nextStack);

    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        nextStack = pCompound.contains("NextStack") ? pCompound.getInt("NextStack") : nextStack;
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (!this.getPersistentData().getBoolean("kick_x")) {
            this.setSprinting(true);
            AegisHerobrineEntity entity = this;
            new DelayedTask(10) {
                @Override
                public void run() {
                    entity.setSprinting(false);
                }
            };
            if (Math.random() <= 0.5D && this instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)this;

                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                }
            }
        }
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel)levelaccessor;
            EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity = new EliteHerobrineKnockedEntity((EntityType) AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), serverlevel);

            eliteHerobrineKnockedEntity.moveTo(this.getX(), this.getY(), this.getZ(), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            eliteHerobrineKnockedEntity.getPersistentData().putString("FromElite", "EnderAegis");
            eliteHerobrineKnockedEntity.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(eliteHerobrineKnockedEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            this.remove(RemovalReason.KILLED);
            levelaccessor.addFreshEntity(eliteHerobrineKnockedEntity);

            if (this.getGregUUID() != null) {
                Entity entity = levelaccessor.getEntity(this.getGregUUID());
                if (entity instanceof HerobrineGregEntity herobrineGregEntity && entity.isAlive()) {
                    herobrineGregEntity.requestProtect(eliteHerobrineKnockedEntity.getUUID(), eliteHerobrineKnockedEntity);
                }
            }
        }
    }

    public void baseTick() {
        super.baseTick();
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 250.0D);
        builder = builder.add(Attributes.ARMOR, 25.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
