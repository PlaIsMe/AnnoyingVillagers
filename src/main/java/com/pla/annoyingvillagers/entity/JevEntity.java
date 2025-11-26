package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class JevEntity extends PathfinderMobInventory {
    private UUID followTargetUUID;
    private AlexEntity followTarget;
    private boolean alexDeathMessageSent = false;

    public void setFollowTarget(AlexEntity followTarget) {
        this.followTarget = followTarget;
    }

    public void setFollowTargetUUID(UUID followTargetUUID) {
        this.followTargetUUID = followTargetUUID;
    }

    public JevEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.JEV.get(), level);
    }

    public JevEntity(EntityType<JevEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 10;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.JEV_BOOK.get()));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.JEV_PENCIL.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike) AnnoyingVillagersModItems.JEV_GLASSES.get()));
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, AlexEntity.class, 12.0F));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Monster.class, 5.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(2, new Goal() {
            @Override
            public boolean canUse() {
                return followTarget != null && followTarget.isAlive();
            }

            @Override
            public void tick() {
                if (followTarget != null && followTarget.isAlive()) {
                    getNavigation().moveTo(followTarget, 2.0D);
                }
            }

            @Override
            public boolean canContinueToUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > 10.0D;
            }
        });
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1.0D, 10.0F, 5.0F));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team add alex",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team modify alex friendlyFire false",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team join alex @s",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        return spawngroupdata1;
    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        if (this.level() instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
            ServerLevel serverlevel = levelaccessor;
            JevDeadEntity jevDeadEntity = new JevDeadEntity((EntityType) AnnoyingVillagersModEntities.JEV_DEAD.get(), serverlevel);
            jevDeadEntity.moveTo(this.getX(), this.getY(), this.getZ(), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            if (jevDeadEntity instanceof Mob) {
                Mob mob = (Mob)jevDeadEntity;
                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(jevDeadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            }
            this.remove(RemovalReason.KILLED);
            levelaccessor.addFreshEntity(jevDeadEntity);
            try {
                jevDeadEntity.getServer().getCommands().getDispatcher().execute(
                        "kill @s",
                        jevDeadEntity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (followTarget == null && followTargetUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(followTargetUUID);
                if (entity instanceof AlexEntity alex) {
                    followTarget = alex;
                } else {
                    followTargetUUID = null;
                }
            }
            if (followTarget != null && !followTarget.isAlive()) {
                if (!alexDeathMessageSent) {
                    alexDeathMessageSent = true;
                    if (level() instanceof ServerLevel serverLevel) {
                        String[] ALEX_DEATH_LINES = {
                                "Oh no... my Alex...",
                                "Why... Alex, why did you leave me...",
                                "I was supposed to protect you, Alex ...",
                                "Alex, come back, please...",
                                "Please, Alex, wake up...",
                                "Alex...? No..."
                        };

                        String message = ALEX_DEATH_LINES[level().getRandom().nextInt(ALEX_DEATH_LINES.length)];
                        serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getDisplayName().getString() + "> " + message), false);
                    }
                }
                followTarget = null;
                followTargetUUID = null;
            }
            if (followTarget != null && followTarget.isAlive()) {
                double distanceSq = this.distanceToSqr(followTarget);

                if (distanceSq > 600.0D) {
                    this.teleportTo(
                            followTarget.getX(),
                            followTarget.getY(),
                            followTarget.getZ()
                    );
                }
            }
        }
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (followTargetUUID != null) {
            tag.putUUID("FollowTarget", followTargetUUID);
        }
        tag.putBoolean("AlexDeathMessageSent", alexDeathMessageSent);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("FollowTarget")) {
            followTargetUUID = tag.getUUID("FollowTarget");
        }
        alexDeathMessageSent = tag.getBoolean("AlexDeathMessageSent");
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getAmbientSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.ambient"));
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        if (this.getGapCooldown() == 0 && this.getHealth() <= ((float) 2/3 * this.getMaxHealth())) {
            CombatBehaviour.drinkingHealingPotion(this, this.level(), false, f);
            this.setGapCooldown();
        }
        if (damagesource.is(DamageTypes.THROWN)) return false;
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.FALLING_ANVIL)) return false;
        return super.hurt(damagesource, f);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 50.0D);
        builder = builder.add(Attributes.ARMOR, 1.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5.0D);
        return builder;
    }
}
