package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.block.CryingObsidianBlock;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.util.ScreenShakeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ObsidianSledgehammerProjectileEntity extends PathfinderMob {
    private static final int MAX_LIFETIME_TICKS = 20 * 8;
    private static final double meteoriteTrailStartDistanceSquared = 4.0D;

    private Vec3 posToAim;
    private LivingEntity owner;

    private boolean motionInited = false;
    private double xd = 0.0;
    private double yd = 0.0;
    private double zd = 0.0;

    private boolean shouldStun = false;

    private boolean meteoriteTrailEnabled = false;

    public void setPosToAim(@Nullable Vec3 pos) {
        this.posToAim = pos;
        this.motionInited = false;
    }

    public LivingEntity getOwner() {
        return owner;
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    public void setShouldStun(boolean shouldStun) {
        this.shouldStun = shouldStun;
    }

    public ObsidianSledgehammerProjectileEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.OBSIDIAN_SLEDGEHAMMER_PROJECTILE.get(), level);
    }

    public ObsidianSledgehammerProjectileEntity(EntityType<ObsidianSledgehammerProjectileEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
        this.setNoAi(false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (posToAim != null) {
            tag.putDouble("AimX", posToAim.x);
            tag.putDouble("AimY", posToAim.y);
            tag.putDouble("AimZ", posToAim.z);
        }
        tag.putBoolean("ShouldStun", shouldStun);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("AimX") && tag.contains("AimY") && tag.contains("AimZ")) {
            this.posToAim = new Vec3(tag.getDouble("AimX"), tag.getDouble("AimY"), tag.getDouble("AimZ"));
        } else {
            this.posToAim = null;
        }
        this.motionInited = false;
        this.shouldStun = tag.getBoolean("ShouldStun");
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

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    public boolean hurt(DamageSource damagesource, float f) {
        return !damagesource.is(DamageTypes.IN_FIRE)
                && (!(damagesource.getDirectEntity() instanceof AbstractArrow)
                && (!(damagesource.getDirectEntity() instanceof Player)
                && (!(damagesource.getDirectEntity() instanceof ThrownPotion)
                && !(damagesource.getDirectEntity() instanceof AreaEffectCloud)
                && (!damagesource.is(DamageTypes.FALL)
                && (!damagesource.is(DamageTypes.CACTUS)
                && (!damagesource.is(DamageTypes.DROWN)
                && (!damagesource.is(DamageTypes.LIGHTNING_BOLT)
                && (!damagesource.is(DamageTypes.EXPLOSION)
                && !damagesource.is(DamageTypes.PLAYER_EXPLOSION)
                && (!damagesource.is(DamageTypes.TRIDENT)
                && (!damagesource.is(DamageTypes.FALLING_ANVIL)
                && (!damagesource.is(DamageTypes.DRAGON_BREATH)
                && (!damagesource.is(DamageTypes.WITHER)
                && !damagesource.is(DamageTypes.WITHER_SKULL)
                && super.hurt(damagesource, f)))))))))))));
    }

    public boolean ignoreExplosion() {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setInvulnerable(true);
        this.playSound(AnnoyingVillagersModSounds.MUFFLED_BOOM.get(), new Random().nextFloat(34.0F, 42.0F), new Random().nextFloat(0.0F, 0.2F));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    private boolean shouldEnableMeteoriteTrail() {
        if (this.owner == null) return true;

        double deltaX = this.getX() - this.owner.getX();
        double deltaZ = this.getZ() - this.owner.getZ();
        return (deltaX * deltaX + deltaZ * deltaZ) >= meteoriteTrailStartDistanceSquared;
    }

    private @Nullable EntityHitResult findEntityHit(ServerLevel serverLevel, Vec3 start, Vec3 end) {
        Vec3 motion = end.subtract(start);
        AABB inflate = this.getBoundingBox().expandTowards(motion).inflate(0.3D);

        return ProjectileUtil.getEntityHitResult(
                serverLevel,
                this,
                start,
                end,
                inflate,
                e -> e instanceof LivingEntity livingEntity
                        && livingEntity.isAlive()
                        && livingEntity != this
                        && livingEntity != this.owner
                        && !(livingEntity instanceof DragonMeteoriteEntity)
        );
    }

    private void explode(ServerLevel serverLevel, double d0, double d1, double d2) {
        serverLevel.explode(null, d0, d1, d2, new Random().nextFloat(2.0F, 4.0F), Level.ExplosionInteraction.NONE);
        ScreenShakeUtil.applyScreenShake(serverLevel, this.position(), 24.0, 20, 6);

        BlockState cryingObsidianBlock = AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get()
                .defaultBlockState()
                .setValue(CryingObsidianBlock.FROM_PLAYER, this.getOwner() instanceof Player);

        FallingBlockEntity falling = FallingBlockEntity.fall(serverLevel, BlockPos.containing(d0, d1, d2), cryingObsidianBlock);
        Entity owner = this.getOwner();
        if (owner != null) {
            CompoundTag tileData = new CompoundTag();
            tileData.putUUID("Owner", owner.getUUID());
            falling.blockData = tileData;
        }
        serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, d0, d1, d2, 1, 0.0D, 0.0D, 0.0D, 0.0D);

        Vec3 center = new Vec3(d0, d1, d2);
        AABB box = new AABB(center, center).inflate(10.0D);

        var damageTypeReg = serverLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        DamageSource damageSource = new DamageSource(damageTypeReg.getHolderOrThrow(DamageTypes.EXPLOSION), this);

        for (LivingEntity entity : serverLevel.getEntitiesOfClass(LivingEntity.class, box,
                livingEntity -> livingEntity.isAlive()
                        && !(livingEntity instanceof DragonMeteoriteEntity)
                        && !(livingEntity instanceof HerobrineDragonEntity)
                        && livingEntity != this.getOwner())) {

            Vec3 dir = entity.position().subtract(center);
            double dist = Math.max(0.001D, dir.length());
            double falloff = 1.0D - Math.min(dist / 10.0D, 1.0D);

            Vec3 push = dir.scale(1.0D / dist)
                    .scale(1.2D * falloff)
                    .add(0.0D, 0.35D * falloff, 0.0D);

            entity.setDeltaMovement(entity.getDeltaMovement().add(push));
            float damage = shouldStun ? 8.0F : 4.0F;
            if (this.owner != null) {
                entity.hurt(damageSources().indirectMagic(this, this.owner), damage);
            } else {
                entity.hurt(damageSource, damage);
            }
            entity.hasImpulse = true;
            if (this.shouldStun) {
                applyLongStun(entity);
            }
            increaseSkillPoint(this.getOwner(), 5.0F);
        }

        this.playSound(SoundEvents.GENERIC_EXPLODE, 5.0F, 0.0F);
        this.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE_FAR, 6.0F, 0.0F);
        this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 10.0F, 0.0F);
        this.discard();
    }

    private void applyLongStun(Entity entity) {
//      ADD THIS CODE IN AV_EFM
//        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
//        if (livingEntityPatch != null) {
//            livingEntityPatch.applyStun(StunType.LONG, 20.0F);
//        }
    }

    public void baseTick() {
        super.baseTick();
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.tickCount > MAX_LIFETIME_TICKS || !serverLevel.hasChunkAt(this.blockPosition())) {
                this.discard();
                return;
            }

            double d0 = this.getX();
            double d1 = this.getY();
            double d2 = this.getZ();

            double d3;
            double d4;
            double d5;

            this.setInvulnerable(true);
            if (!motionInited) {
                if (posToAim != null) {
                    Vec3 from = this.position();
                    Vec3 dir = posToAim.subtract(from);

                    double dist = dir.length();
                    if (dist > 1.0E-4) {
                        double speed = 1.8D;

                        Vec3 vel = dir.scale(speed / dist);
                        xd = vel.x;
                        yd = vel.y;
                        zd = vel.z;
                    } else {
                        xd = yd = zd = 0.0D;
                    }
                } else {
                    RandomSource r = serverLevel.getRandom();
                    xd = Mth.nextDouble(r, -0.7D, 0.7D);
                    yd = -1.8D;
                    zd = Mth.nextDouble(r, -0.7D, 0.7D);
                }
                motionInited = true;
            }

            Vec3 start = this.position();
            Vec3 end = start.add(xd, yd, zd);

            if (!serverLevel.hasChunkAt(BlockPos.containing(end))) {
                this.discard();
                return;
            }

            EntityHitResult entityHitResult = findEntityHit(serverLevel, start, end);
            if (entityHitResult != null) {
                Vec3 hitPos = entityHitResult.getLocation();
                this.setPos(hitPos.x, hitPos.y, hitPos.z);
                explode(serverLevel, hitPos.x, hitPos.y, hitPos.z);
                return;
            }

            if (this.onGround() || this.isInWall() || (posToAim != null && this.position().distanceToSqr(posToAim) < 1.0D)) {
                explode(serverLevel, d0, d1, d2);
            }

            this.setNoGravity(true);
            this.setDeltaMovement(xd, yd, zd);
            this.hasImpulse = true;

            if (posToAim != null) {
                Vec3 fromEye = this.getEyePosition();
                Vec3 toEye = posToAim;

                double dx = toEye.x - fromEye.x;
                double dz = toEye.z - fromEye.z;
                double dy = toEye.y - fromEye.y;
                double distXZ = Math.sqrt(dx * dx + dz * dz);

                float yaw = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
                float pitch = (float)(-(Mth.atan2(dy, distXZ) * (180F / Math.PI)));

                this.setYRot(yaw);
                this.setYHeadRot(yaw);
                this.setYBodyRot(yaw);
                this.setXRot(pitch);
            }

            d3 = -5.0D;
            for (int i = 0; i < 10; ++i) {
                d4 = -5.0D;

                for (int j = 0; j < 10; ++j) {
                    d5 = -5.0D;

                    for (int k = 0; k < 10; ++k) {
                        if ((double) serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getDestroySpeed(serverLevel, BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)) < 0.4D && serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getDestroySpeed(serverLevel, BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)) >= 0.0F) {
                            serverLevel.destroyBlock(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), false);
                            serverLevel.updateNeighborsAt(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getBlock());
                            serverLevel.scheduleTick(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getBlock(), 1);
                        }

                        if (serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).is(BlockTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "logs")))) {
                            serverLevel.destroyBlock(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), false);
                            serverLevel.updateNeighborsAt(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getBlock());
                            serverLevel.scheduleTick(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getBlock(), 1);
                        }
                        ++d5;
                    }
                    ++d4;
                }
                ++d3;
            }

            if (this.shouldStun) {
                HerobrineUtil.spawnEliteEffect(this.level(), this.getX(), this.getY(), this.getZ(), this);
            }

            if (this.isInWaterOrBubble()) {
                for (int i = 0; i < 10; ++i) {
                    serverLevel.addParticle(AnnoyingVillagersModParticleTypes.BIG_SPLASH.get(), d0 + Mth.nextDouble(RandomSource.create(), -1.0D, 1.0D), d1 + 2.0D, d2 + Mth.nextDouble(RandomSource.create(), -1.0D, 1.0D), 0.0D, 1.0D, 0.0D);
                }
            }

            if (!this.isInWaterOrBubble()) {
                Entity entity = this;
                new DelayedTask(2) {
                    @Override
                    public void run() {
                        if (entity.isInWaterOrBubble()) {
                            serverLevel.playSound(null, BlockPos.containing(d0, d1, d2), SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.NEUTRAL, 6.0F, 0.0F);
                        }
                    }
                };
            }

            if (!this.meteoriteTrailEnabled && shouldEnableMeteoriteTrail()) {
                this.meteoriteTrailEnabled = true;
            }

            if (this.meteoriteTrailEnabled) {
                serverLevel.sendParticles(
                        AnnoyingVillagersModParticleTypes.METEORITE_TRAIL.get(),
                        d0, d1 + 0.5D, d2,
                        0,
                        0.0D, 0.01D, 0.0D,
                        0.0D
                );
            }
        }
    }

    public void increaseSkillPoint(Entity entity, float value) {
//      ADD THIS CODE IN AV_EFM
//        if (!(entity instanceof Player player)) return;
//
//        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
//        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;
//
//        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_SLEDGEHAMMER);
//        if (skillContainer == null) return;
//
//        ObsidianSledgeHammerSkill skill = (ObsidianSledgeHammerSkill) skillContainer.getSkill();
//
//        float currentResource = skillContainer.getResource();
//        float neededResource = skillContainer.getNeededResource();
//        float addResource = Math.min(value, neededResource);
//
//        skill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
    }

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 200.0D);
        return builder;
    }
}
