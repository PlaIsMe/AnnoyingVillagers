package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.util.CommonGoals;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.utils.NameManager;
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

public class LowShadowHerobrineCloneEntity extends Monster {
    private boolean summoned = false;
    private boolean initialSpawn = true;
    private EliteHerobrineKnockedEntity protectEntity;
    private UUID protectUUID;
    private boolean autoKill = false;
    private HerobrineMob possessedByEntity;
    private UUID possessedByUuid;
    private boolean bound = false;
    private boolean sacrificing = false;
    private final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);

    public boolean isSacrificing() {
        return sacrificing;
    }

    public HerobrineMob getPossessedByEntity() {
        return possessedByEntity;
    }

    public void setProtectUUID(UUID protectUUID) {
        this.protectUUID = protectUUID;
    }

    public void setProtectEntity(EliteHerobrineKnockedEntity protectEntity) {
        this.protectEntity = protectEntity;
    }

    public void setAutoKill(boolean autoKill) {
        this.autoKill = autoKill;
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

    public boolean isSummoned() {
        return summoned;
    }

    public void setSummoned(boolean summoned) {
        this.summoned = summoned;
    }

    boolean renderPortal = false;

    public void setRenderPortal(boolean renderPortal) {
        this.renderPortal = renderPortal;
    }

    public void setInitialSpawn(boolean initialSpawn) {
        this.initialSpawn = initialSpawn;
    }

    public LowShadowHerobrineCloneEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), level);
    }

    public LowShadowHerobrineCloneEntity(EntityType<LowShadowHerobrineCloneEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.0F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomNameVisible(false);
        this.setPersistenceRequired();
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
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
    public boolean doHurtTarget(Entity pEntity) {
        if (!this.level().isClientSide() && pEntity instanceof LivingEntity livingEntity) {
            if (this.getPersistentData().contains("DiamondShearHit")) {
                int hitCount = this.getPersistentData().getInt("DiamondShearHit");
                this.level().playSound(null, pEntity.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1.0F, 1.0F);
                if (hitCount == 10) {
                    livingEntity.removeAllEffects();
                    this.getPersistentData().putInt("DiamondShearHit", 0);
                } else {
                    this.getPersistentData().putInt("DiamondShearHit", hitCount + 1);
                }
            } else {
                this.getPersistentData().putInt("DiamondShearHit", 1);
            }
        }
        return super.doHurtTarget(pEntity);
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
            LowShadowHerobrineCloneOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), damagesource.getEntity());
        }
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (!autoKill) {
                InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
                corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                String killedName = this.getPersistentData().getString("killed_name");
                corpse.getPersistentData().putString("possessed_by", "low_shadow_herobrine_clone");
                if (killedName.isEmpty()) {
                    killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
                }
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
            } else if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                ShadowHerobrineDeadEntity corpse = new ShadowHerobrineDeadEntity(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_DEAD.get(), serverLevel);
                corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                        MobSpawnType.MOB_SUMMONED, null, null);
                this.setInvisible(true);
                this.remove(RemovalReason.KILLED);
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
        if (mobspawntype == MobSpawnType.NATURAL || mobspawntype == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverlevelaccessor.getLevel();
            HerobrineMobData herobrineMobData = HerobrineMobData.get(serverLevel);

            if (!herobrineMobData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            } else {
            }

            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            this.moveTo(spawnPos, this.getYRot(), this.getXRot());
        }
        HerobrineOnInitialSpawnProcedure.execute(serverlevelaccessor, this, 0, mobspawntype);
        return spawngroupdata;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.tickCount == 1) {
                if (this.renderPortal) {
                    AnnoyingVillagers.PACKET_HANDLER.send(
                            PacketDistributor.TRACKING_ENTITY.with(() -> this),
                            new ClientboundHerobrinePortalFx(HerobrinePortalProcedure.finalSurfacePos(this))
                    );
                    renderPortal = false;
                }
                if (this.initialSpawn) {
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
                if (this.getHealth() <= 1) {
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

    public void baseTick() {
        super.baseTick();
    }

    public static boolean canSpawn(EntityType<LowShadowHerobrineCloneEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        int passesDay = (int) (serverLevel.getGameTime() / 24000);
        if (passesDay != 0 && passesDay % 3 != 0) {
            return false;
        }
        if (HerobrineMobData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        if (!serverLevel.isNight()) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
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
        pCompound.putBoolean("RenderPortal", renderPortal);
        pCompound.putBoolean("InitialSpawn", initialSpawn);
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

    public static Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 25.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}
