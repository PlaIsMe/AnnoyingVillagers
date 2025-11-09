package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.procedures.HerobrinePortalProcedure;
import com.pla.annoyingvillagers.procedures.LowHerobrineCloneOnHurtProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnInitialSpawnProcedure;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class LowHerobrineCloneEntity extends PlayerMobEntity {
    private boolean summoned = false;
    private boolean initialSpawn = true;
    private boolean autoKill = false;
    private HerobrineMob possessedByEntity;
    private UUID possessedByUuid;
    private boolean bound = false;
    private boolean sacrificing = false;
    private final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
    private EliteHerobrineKnockedEntity protectEntity;
    private UUID protectUUID;
    boolean renderPortal = false;

    public boolean isSacrificing() {
        return sacrificing;
    }

    public HerobrineMob getPossessedByEntity() {
        return possessedByEntity;
    }

    public void setRenderPortal(boolean renderPortal) {
        this.renderPortal = renderPortal;
    }

    public boolean isSummoned() {
        return summoned;
    }

    public void setSummoned(boolean summoned) {
        this.summoned = summoned;
    }

    public void setInitialSpawn(boolean initialSpawn) {
        this.initialSpawn = initialSpawn;
    }

    public void setPossessedByUuid(UUID possessedByUuid) {
        this.possessedByUuid = possessedByUuid;
    }

    public void setPossessedByEntity(HerobrineMob possessedByEntity) {
        this.possessedByEntity = possessedByEntity;
    }

    public void setSacrificing(boolean sacrificing) {
        this.sacrificing = sacrificing;
    }

    public LowHerobrineCloneEntity(EntityType<? extends LowHerobrineCloneEntity> type, Level level) {
        super(type, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setCustomNameVisible(false);
    }

    public LowHerobrineCloneEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), level);
    }

    public boolean hurt(DamageSource damagesource, float f) {
        if (sacrificing) {
            if (new Random().nextBoolean()) {
                try {
                    this.getServer().getCommands().getDispatcher().execute(
                            "playsound epicfight:entity.hit.clash neutral @p",
                            this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    this.getServer().getCommands().getDispatcher().execute(
                            "execute at @s run particle epicfight:hit_blade ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                            this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
                return false;
            } else {
                return super.hurt(damagesource, f/2.0F);
            }
        } else {
            LowHerobrineCloneOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), damagesource.getEntity());
        }
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        return super.hurt(damagesource, f);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("§5Low Herobrine Clone§r");
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();
        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return protectEntity != null && protectEntity.isAlive() && distanceTo(protectEntity) > (float)10.0D * 0.9F;
            }

            @Override
            public void tick() {
                if (protectEntity != null && protectEntity.isAlive()) {
                    getNavigation().moveTo(protectEntity, 2.0D);
                    getLookControl().setLookAt(protectEntity, 30.0F, 30.0F);
                    if (distanceToSqr(protectEntity) > 10.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(protectEntity, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return protectEntity != null && protectEntity.isAlive() && distanceTo(protectEntity) > 50.0D;
            }
        });
        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return possessedByEntity != null && possessedByEntity.isAlive() && distanceTo(possessedByEntity) > (float)20.0D * 0.9F;
            }

            @Override
            public void tick() {
                if (possessedByEntity != null && possessedByEntity.isAlive()) {
                    getNavigation().moveTo(possessedByEntity, 2.0D);
                    getLookControl().setLookAt(possessedByEntity, 30.0F, 30.0F);
                    if (distanceToSqr(possessedByEntity) > 20.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(possessedByEntity, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return possessedByEntity != null && possessedByEntity.isAlive() && distanceTo(possessedByEntity) > 50.0D;
            }
        });
        CommonGoals.registerGoalForHostileNpc(this);
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
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (!autoKill) {
            if (this.level() instanceof ServerLevel serverLevel) {
                InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
                corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                String killedName = this.getCustomName().getString();
                corpse.getPersistentData().putString("possessed_by", "low_herobrine_clone");
                corpse.setUsername(killedName);
                corpse.setCustomName(Component.literal(killedName));
                corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                        MobSpawnType.MOB_SUMMONED, null, null);
                this.setInvisible(true);
                this.remove(RemovalReason.KILLED);
                corpse.setItemSlot(EquipmentSlot.HEAD, this.getItemBySlot(EquipmentSlot.HEAD).copy());
                corpse.setItemSlot(EquipmentSlot.CHEST, this.getItemBySlot(EquipmentSlot.CHEST).copy());
                corpse.setItemSlot(EquipmentSlot.LEGS, this.getItemBySlot(EquipmentSlot.LEGS).copy());
                corpse.setItemSlot(EquipmentSlot.FEET, this.getItemBySlot(EquipmentSlot.FEET).copy());
                serverLevel.addFreshEntity(corpse);
            }
        } else if (this.level() instanceof ServerLevel serverLevel && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
            PlayerMobDeadEntity corpse =  new PlayerMobDeadEntity(AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            corpse.setUsername(this.getUsername());
            corpse.setProfile(this.getProfile());
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            this.setInvisible(true);
            this.remove(Entity.RemovalReason.KILLED);
            serverLevel.addFreshEntity(corpse);
            new DelayedTask(3) {
                @Override
                public void run() {
                    try {
                        corpse.getServer().getCommands().getDispatcher().execute(
                                "kill @s",
                                corpse.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
            };
        }
        LevelAccessor levelaccessor1 = this.level();
        ItemEntity itementity;
        LivingEntity livingentity = (LivingEntity)this;
        ItemStack itemstack;

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getMainHandItem();
                itementity = new ItemEntity(level, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getOffhandItem();
                itementity = new ItemEntity(level, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        HerobrineOnInitialSpawnProcedure.execute(serverlevelaccessor, this, 0, mobspawntype);
        return spawngroupdata;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        summoned = pCompound.getBoolean("Summoned");
        renderPortal = pCompound.getBoolean("RenderPortal");
        initialSpawn = pCompound.getBoolean("InitialSpawn");
        autoKill = pCompound.getBoolean("AutoKill");
        if (pCompound.hasUUID("ProtectUUID")) {
            protectUUID = pCompound.getUUID("ProtectUUID");
        }
        if (pCompound.hasUUID("PossessedByUuid")) {
            possessedByUuid = pCompound.getUUID("PossessedByUuid");
        }
        bound = pCompound.getBoolean("Bound");
        sacrificing = pCompound.getBoolean("Sacrificing");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Summoned", summoned);
        pCompound.putBoolean("InitialSpawn", initialSpawn);
        pCompound.putBoolean("RenderPortal", renderPortal);
        pCompound.putBoolean("AutoKill", autoKill);
        if (protectUUID != null) {
            pCompound.putUUID("ProtectUUID", protectUUID);
        }
        if (possessedByUuid != null) {
            pCompound.putUUID("PossessedByUuid", possessedByUuid);
        }
        pCompound.putBoolean("Bound", bound);
        pCompound.putBoolean("Sacrificing", sacrificing);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.tickCount == 1) {
                if (this.initialSpawn) {
                    if (this.renderPortal) {
                        AnnoyingVillagers.PACKET_HANDLER.send(
                                PacketDistributor.TRACKING_ENTITY.with(() -> this),
                                new ClientboundHerobrinePortalFx(HerobrinePortalProcedure.finalSurfacePos(this))
                        );
                        renderPortal = false;
                    }
                    if (this.summoned) {
                        this.setNoAi(true);
                    }
                    final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
                    if (livingentitypatch != null && !this.level().isClientSide()) {
                        livingentitypatch.playAnimationSynchronized(AVAnimations.HEROBRINE_ANIMATE, 0.0F);
                    }
                    this.initialSpawn = false;
                }
            }

            if (protectEntity == null && protectUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(protectUUID);
                if (entity instanceof EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity) {
                    protectEntity = eliteHerobrineKnockedEntity;
                } else {
                    protectEntity = null;
                }
            }
            if (protectEntity != null && !protectEntity.isAlive()) {
                protectEntity = null;
                protectUUID = null;
                autoKill = true;
                this.kill();
            }

            if (possessedByEntity == null && possessedByUuid != null) {
                Entity entity = ((ServerLevel) level()).getEntity(possessedByUuid);
                if (entity instanceof HerobrineMob herobrineMob) {
                    possessedByEntity = herobrineMob;
                } else {
                    possessedByEntity = null;
                }
            }
            if (!bound && possessedByEntity != null && possessedByEntity.isAlive() && !possessedByEntity.isHealing()) {
                if (possessedByEntity.isAvailableSlot()) {
                    if (possessedByEntity.boundPossessed(this)) {
                        this.bound = true;
                    }
                }
            }
            if (possessedByEntity != null && !possessedByEntity.isAlive()) {
                AABB area = new AABB(this.blockPosition()).inflate(60);
                List<Entity> nearby = level().getEntities(this, area, entity ->
                        entity instanceof EliteHerobrineKnockedEntity
                );
                if (!nearby.isEmpty()) {
                    Entity entity = nearby.get(0);
                    if (entity instanceof EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity) {
                        this.protectEntity = eliteHerobrineKnockedEntity;
                        this.protectUUID = eliteHerobrineKnockedEntity.getUUID();
                    } else {
                        possessedByEntity = null;
                        possessedByUuid = null;
                        autoKill = true;
                        this.kill();
                    }
                } else {
                    possessedByEntity = null;
                    possessedByUuid = null;
                    autoKill = true;
                    this.kill();
                }
            }
            if (this.sacrificing) {
                if (this.getHealth() <= 2) {
                    this.sacrificing = false;
                    autoKill = true;
                    this.kill();
                }
                this.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 1, 3, false, false));
                if (this.livingentitypatch != null) {
                    this.livingentitypatch.playAnimationSynchronized(AVAnimations.HEROBRINE_SACRIFICING, 0.0F);
                }
                if (this.tickCount % 140 == 0 && this.possessedByEntity.getHealth() < this.possessedByEntity.getMaxHealth() * 0.8) {
                    try {
                        this.getServer().getCommands().getDispatcher().execute(
                                "playsound annoyingvillagers:herobrine_understood voice @a ~ ~ ~",
                                this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
                if (this.tickCount % 20 == 0 && this.possessedByEntity != null) {
                    if (this.possessedByEntity.getMaxHealth() == this.possessedByEntity.getHealth()) {
                        this.sacrificing = false;
                        autoKill = true;
                        this.kill();
                    }
                    if (this.getHealth() <= 4) {
                        this.sacrificing = false;
                        autoKill = true;
                        this.kill();
                    } else {
                        this.setHealth(this.getHealth() - 2.0F);
                    }
                    this.possessedByEntity.heal(this.possessedByEntity.getMaxHealth() * 0.01F);
                }
                if (this.possessedByEntity != null) {
                    ServerLevel server = (ServerLevel)this.level();
                    Vec3 from = getArmPosition(this, new Vec3f(0,0,0), Armatures.BIPED.toolR, 1.2F, 0.0F);
                    if (from == null) {
                        return;
                    }
                    Vec3 to = this.possessedByEntity.getEyePosition();

                    AABB box = this.possessedByEntity.getBoundingBox().inflate(0.05);
                    Vec3 end = box.clip(from, to).orElse(to);

                    Vec3 d = end.subtract(from);
                    double len = d.length();
                    if (len <= 1.0e-4) return;

                    Vec3 dir = d.scale(1.0 / len);

                    Vec3 any = Math.abs(dir.y) < 0.99 ? new Vec3(0,1,0) : new Vec3(1,0,0);
                    Vec3 u = dir.cross(any).normalize();
                    Vec3 v = dir.cross(u).normalize();

                    int steps = Mth.clamp((int)(len * 6.0), 6, 72);
                    double step = len / steps;

                    final int stride = 4;
                    int phase = (this.tickCount >> 1) % stride;
                    RandomSource r = this.getRandom();

                    for (int i = phase; i <= steps; i += stride) {
                        if (r.nextFloat() < 0.70f) continue;

                        double t = (i * step) / len;
                        double R = 0.05 + 0.20 * t;
                        double ang = r.nextDouble() * (Math.PI * 2);
                        double rad = R * Math.sqrt(r.nextDouble());
                        Vec3 off = u.scale(Math.cos(ang) * rad).add(v.scale(Math.sin(ang) * rad));

                        Vec3 p = from.add(dir.scale(i * step)).add(off);

                        double vx = dir.x * 0.02 + off.x * 0.10;
                        double vy = dir.y * 0.02 + off.y * 0.10;
                        double vz = dir.z * 0.02 + off.z * 0.10;

                        server.sendParticles(AnnoyingVillagersModParticleTypes.LIGHT.get(),
                                p.x, p.y, p.z,
                                1,
                                vx, vy, vz,
                                0.0);
                    }
                }
            }
        }
    }

    private static Vec3 getArmPosition(Entity entity, Vec3f translation, Joint joint, float handToTip, double yOffset) {
        LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (entitypatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = entitypatch.getArmature()
                .getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        if (handToTip != 0.0f) {
            OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
            OpenMatrix4f.mul(m, tipOffset, m);
        }

        float yawRad = (float) -Math.toRadians(((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = (LivingEntity) entitypatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        );
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 25.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}