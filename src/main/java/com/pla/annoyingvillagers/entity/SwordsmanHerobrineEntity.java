package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


public class SwordsmanHerobrineEntity extends HerobrineMob {
    private int nextStack = 3;

    public SwordsmanHerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(), level);
    }

    public SwordsmanHerobrineEntity(EntityType<SwordsmanHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        ItemStack sword = new ItemStack(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get());
        this.setItemSlot(EquipmentSlot.MAINHAND, sword);
        this.setChatName(this.getDisplayName().getString());
    }

    public int getCooldownTicks() {
        return this.getPersistentData().getInt("SwordCooldown");
    }

    public void setCooldownTicks(int ticks) {
        this.getPersistentData().putInt("SwordCooldown", ticks);
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

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (!pEntity.level().isClientSide()) {
            if (this.getPersistentData().getBoolean("SnakeForm") && this.getPersistentData().getInt("HitCount") >= 3) {
                if (SnakeBladeHit.process(this.getMainHandItem(), this)) {
                    this.getMainHandItem().getOrCreateTag().putBoolean("SnakeAnimation", true);
                    this.getPersistentData().remove("HitCount");
                }
            } else {
                this.getPersistentData().putInt("HitCount", (this.getPersistentData().contains("HitCount") ? this.getPersistentData().getInt("HitCount") : 0) + 1);
            }
        }
        return super.doHurtTarget(pEntity);
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

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (!this.getPersistentData().getBoolean("kick_x")) {
            this.setSprinting(true);
            SwordsmanHerobrineEntity entity = this;
            new DelayedTask(10) {
                @Override
                public void run() {
                    entity.setSprinting(false);
                }
            };

            LivingEntity livingentity = (LivingEntity)this;

            if (!livingentity.level().isClientSide()) {
                livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
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

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.getTarget() != null && !this.getMainHandItem().getTag().getBoolean("SecondForm")) {
                this.getMainHandItem().getTag().putBoolean("SecondForm", true);
            }
            if (this.getTarget() == null && this.getMainHandItem().getTag().getBoolean("SecondForm") && !this.getMainHandItem().getTag().getBoolean("SnakeAnimation")) {
                this.getMainHandItem().getTag().putBoolean("SecondForm", false);
            }
        }
        boolean playSound = false;
        if (!this.level().isClientSide()) {
            ItemStack itemStack = this.getMainHandItem();
            if (itemStack.getTag() == null) return;
            if (this.getPersistentData().getBoolean("SnakeForm")) {
                HerobrineWeaponEffectProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);

                int cooldown = getCooldownTicks();
                if (cooldown > 0) {
                    setCooldownTicks(cooldown - 1);
                } else {
                    this.getPersistentData().remove("SnakeForm");
                }
            } else if (itemStack.getTag().getBoolean("SecondForm") && !this.getPersistentData().getBoolean("SnakeForm") && this.getPersistentData().getInt("HitCount") >= nextStack) {
                this.getPersistentData().putBoolean("SnakeForm", true);
                setCooldownTicks(200);
                this.getPersistentData().remove("HitCount");
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

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel)levelaccessor;
            EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity = new EliteHerobrineKnockedEntity((EntityType) AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), serverlevel);

            eliteHerobrineKnockedEntity.moveTo(this.getX(), this.getY(), this.getZ(), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            eliteHerobrineKnockedEntity.getPersistentData().putString("FromElite", "DemoniacVoltageReaver");
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
