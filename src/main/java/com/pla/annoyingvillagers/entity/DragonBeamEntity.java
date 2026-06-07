package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.client.engine.PhotonClientFxUtil;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.AAAParticlesUtil;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.ScreenShakeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DragonBeamEntity extends Entity {
    public HerobrineDragonEntity caster;
    public LivingEntity target;
    public double endPosX;
    public double endPosY;
    public double endPosZ;
    public double collidePosX;
    public double collidePosY;
    public double collidePosZ;
    public double prevCollidePosX;
    public double prevCollidePosY;
    public double prevCollidePosZ;
    public float renderYaw;
    public float renderPitch;
    public boolean on;
    public Direction blockSide;
    private int power;
    private static final EntityDataAccessor<Float> YAW;
    private static final EntityDataAccessor<Float> PITCH;
    private static final EntityDataAccessor<Integer> DURATION;
    private static final EntityDataAccessor<Integer> CASTER;
    private static final EntityDataAccessor<Integer> TARGET;
    public float prevYaw;
    public float prevPitch;
    private Vec3 targetPos;
    private static final float DRAGON_PHOTON_VISUAL_BASE_LENGTH = 192.0F; // 0 disables authored-axis scaling.
    private boolean renderBeam = false;
    private boolean playSound = false;
    private static final EntityDataAccessor<Boolean> USE_NO_VFX_THUNDER = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Vector3f> THUNDER_START = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> THUNDER_STOP = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Boolean> HAS_TARGET_POS = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Vector3f> TARGET_POS = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.VECTOR3);

    public DragonBeamEntity(EntityType<? extends DragonBeamEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.on = true;
        this.blockSide = null;
        this.noCulling = true;
    }

    public DragonBeamEntity(EntityType<? extends DragonBeamEntity> type, Level world, HerobrineDragonEntity caster, LivingEntity target, double x, double y, double z, int duration, int pow) {
        this(type, world);
        this.caster = caster;
        this.target = target;
        this.setDuration(duration);
        this.setPower(pow);
        this.setPos(x, y, z);

        Vec3 from = new Vec3(x, y, z);
        Vec3 to = target.getEyePosition(1.0F);
        this.setTargetPos(to);

        float yawRad   = yawTowards(from, to);
        float pitchRad = pitchTowards(from, to);

        this.setYaw(yawRad);
        this.setPitch(pitchRad);

        if (world.isClientSide) {
            this.renderYaw = yawRad;
            this.renderPitch = pitchRad;
        }
        if (!world.isClientSide) {
            this.setCasterID(caster.getId());
            this.setTargetID(target.getId());
        }

        this.calculateEndPos();
    }

    protected void defineSynchedData() {
        this.entityData.define(YAW, 0.0F);
        this.entityData.define(PITCH, 0.0F);
        this.entityData.define(DURATION, 0);
        this.entityData.define(CASTER, -1);
        this.entityData.define(TARGET, -1);
        this.entityData.define(USE_NO_VFX_THUNDER, false);
        this.entityData.define(THUNDER_START, new Vector3f());
        this.entityData.define(THUNDER_STOP,  new Vector3f());
        this.entityData.define(HAS_TARGET_POS, false);
        this.entityData.define(TARGET_POS, new Vector3f());
    }

    public void setTargetID(int id) {
        this.entityData.set(TARGET, id);
    }

    public int getTargetID() {
        return this.entityData.get(TARGET);
    }

    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    public void setUseNoVfxThunder(boolean noVfxThunder) {
        this.entityData.set(USE_NO_VFX_THUNDER, noVfxThunder);
    }

    public boolean isSetUseNoVfxThunder() {
        return this.entityData.get(USE_NO_VFX_THUNDER);
    }

    public Vec3 getThunderStartVec3() {
        Vector3f vector3f = this.entityData.get(THUNDER_START);
        return new Vec3(vector3f.x, vector3f.y, vector3f.z);
    }

    public Vec3 getThunderStopVec3() {
        Vector3f vector3f = this.entityData.get(THUNDER_STOP);
        return new Vec3(vector3f.x, vector3f.y, vector3f.z);
    }

    public void setThunderStartStop(Vec3 from, Vec3 to) {
        this.entityData.set(THUNDER_START, new Vector3f((float) from.x, (float) from.y, (float) from.z));
        this.entityData.set(THUNDER_STOP, new Vector3f((float) to.x, (float) to.y, (float) to.z));
    }

    private void setTargetPos(Vec3 pos) {
        if (pos == null) {
            return;
        }

        this.targetPos = pos;
        this.entityData.set(TARGET_POS, new Vector3f((float) pos.x, (float) pos.y, (float) pos.z));
        this.entityData.set(HAS_TARGET_POS, true);
    }

    private Vec3 getStoredTargetPos() {
        if (this.targetPos != null) {
            return this.targetPos;
        }

        if (!this.entityData.get(HAS_TARGET_POS)) {
            return null;
        }

        Vector3f stored = this.entityData.get(TARGET_POS);
        this.targetPos = new Vec3(stored.x, stored.y, stored.z);
        return this.targetPos;
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public @NotNull PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    public float getYaw() {
        return this.getEntityData().get(YAW);
    }

    public void setYaw(float yaw) {
        this.getEntityData().set(YAW, yaw);
    }

    public float getPitch() {
        return this.getEntityData().get(PITCH);
    }

    public void setPitch(float pitch) {
        this.getEntityData().set(PITCH, pitch);
    }

    public int getDuration() {
        return this.getEntityData().get(DURATION);
    }

    public void setDuration(int duration) {
        this.getEntityData().set(DURATION, duration);
    }

    public int getCasterID() {
        return this.getEntityData().get(CASTER);
    }

    public void setCasterID(int id) {
        this.getEntityData().set(CASTER, id);
    }

    public void setPower(int power) {
        this.power = power;
    }

    private static float yawTowards(Vec3 from, Vec3 to) {
        Vec3 d = to.subtract(from);
        return (float) Math.atan2(d.z, d.x);
    }

    private static float pitchTowards(Vec3 from, Vec3 to) {
        Vec3 d = to.subtract(from);
        double len = d.length();
        return len == 0 ? 0f : (float) Math.asin(d.y / len);
    }

    private void calculateEndPos() {
        double radius = 128.0;
        double yaw = this.getYaw();
        double pitch = this.getPitch();

        this.endPosX = this.getX() + radius * Math.cos(yaw) * Math.cos(pitch);
        this.endPosZ = this.getZ() + radius * Math.sin(yaw) * Math.cos(pitch);
        this.endPosY = this.getY() + radius * Math.sin(pitch);

    }

    public DragonBeamHitResult raytraceEntities(Level world, Vec3 from, Vec3 to, boolean ignoreBlockWithoutBoundingBox) {
        DragonBeamHitResult result = new DragonBeamHitResult();
        result.setBlockHit(world.clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this)));
        result.setBlockHit(world.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)));
        if (result.blockHit != null) {
            Vec3 hitVec = result.blockHit.getLocation();
            BlockPos hitBlock = result.blockHit.getBlockPos();

            this.collidePosX = hitVec.x;
            this.collidePosY = hitVec.y;
            this.collidePosZ = hitVec.z;
            this.blockSide = result.blockHit.getDirection();

            if (world.isClientSide) {
                if (PhotonClientFxUtil.spawnAt(world, "dragonhitfire", hitVec)) {
                    // Photon handled the impact effect.
                } else if (ModList.get().isLoaded("aaa_particles")) {
                    AAAParticlesUtil.sendDragonBeamHit(world, hitBlock);
                } else {
                    world.addParticle(
                            ParticleTypes.EXPLOSION,
                            true,
                            hitBlock.getX(), hitBlock.getY() + 1.0D, hitBlock.getZ(),
                            0, 0, 0);
                    world.addParticle(
                            AnnoyingVillagersModParticleTypes.METEORITE_TRAIL.get(),
                            true,
                            hitBlock.getX(), hitBlock.getY() + 1.0D, hitBlock.getZ(),
                            0, 0, 0);
                    world.addParticle(
                            ParticleTypes.FLASH,
                            true,
                            hitBlock.getX(), hitBlock.getY() + 1.0D, hitBlock.getZ(),
                            0, 0, 0);
                }
            }

            if (!world.isClientSide) {
                boolean shouldBreak = true;

                if (this.target != null && this.target.isAlive()) {
                    double hitDist2 = from.distanceToSqr(hitVec);
                    double targetDist2 = from.distanceToSqr(this.target.getEyePosition(1.0F));
                    shouldBreak = hitDist2 + 1e-6 < targetDist2;

                    BlockPos targetFeet = this.target.blockPosition();
                    BlockPos targetEyes = BlockPos.containing(this.target.getEyePosition(1.0F));

                    if (hitBlock.getY() >= targetFeet.getY() && hitBlock.getY() <= targetEyes.getY()) {
                        shouldBreak = false;
                    }
                }

                if (shouldBreak) {
                    var hitState = world.getBlockState(hitBlock);
                    if (!hitState.isAir()) {
                        if (hitState.getDestroySpeed(world, hitBlock) > 0.0F) {
                            world.destroyBlock(hitBlock, true, this.caster);
                        }

                        BlockPos above = hitBlock.above();
                        if (world.getBlockState(above).isAir()) {
                            world.setBlockAndUpdate(above, AnnoyingVillagersModBlocks.END_FIRE.get().defaultBlockState());
                        }
                    }
                }

                if (world.getBlockState(hitBlock.above()).isAir() && world.getBlockState(hitBlock).isSolidRender(world, hitBlock)) {
                    world.setBlockAndUpdate(hitBlock.above(), AnnoyingVillagersModBlocks.END_FIRE.get().defaultBlockState());
                }
            }
        } else {
            this.collidePosX = this.endPosX;
            this.collidePosY = this.endPosY;
            this.collidePosZ = this.endPosZ;
            this.blockSide = null;
        }

        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, (new AABB(Math.min(this.getX(), this.collidePosX), Math.min(this.getY(), this.collidePosY), Math.min(this.getZ(), this.collidePosZ), Math.max(this.getX(), this.collidePosX), Math.max(this.getY(), this.collidePosY), Math.max(this.getZ(), this.collidePosZ))).inflate(1.0, 1.0, 1.0));

        for (LivingEntity entity : entities) {
            if (entity != this.caster) {
                float pad = entity.getPickRadius() + 0.5F;
                AABB aabb = entity.getBoundingBox().inflate(pad, pad, pad);
                Optional<Vec3> hit = aabb.clip(from, to);
                if (aabb.contains(from)) {
                    result.addEntityHit(entity);
                } else if (hit.isPresent()) {
                    result.addEntityHit(entity);
                }
            }
        }

        return result;
    }

    public boolean isRenderable() {
        return (this.target != null && this.target.isAlive()) || this.getStoredTargetPos() != null;
    }

    private Vec3 getBeamStopPos() {
        if (this.target != null && this.target.isAlive()) {
            return this.target.getEyePosition(1.0F);
        }

        Vec3 storedTargetPos = this.getStoredTargetPos();
        if (storedTargetPos != null) {
            return storedTargetPos;
        }

        return new Vec3(this.endPosX, this.endPosY, this.endPosZ);
    }

    private Vec3 getPhotonBeamStart(float partialTicks) {
        if (this.caster == null || !this.caster.isAlive()) {
            return null;
        }

        return this.caster.beamMouthPos(partialTicks);
    }

    private Vec3 getPhotonBeamEnd(float partialTicks) {
        Vec3 start = this.getPhotonBeamStart(partialTicks);
        Vec3 fullEnd = new Vec3(this.endPosX, this.endPosY, this.endPosZ);

        if (start != null) {
            BlockHitResult hit = this.level().clip(new ClipContext(start, fullEnd, Block.COLLIDER, Fluid.NONE, this));
            if (hit.getType() == Type.BLOCK) {
                return hit.getLocation();
            }
        }

        return fullEnd;
    }

    private boolean isPhotonBeamAlive() {
        return this.on
                && this.isAlive()
                && !this.isRemoved()
                && this.caster != null
                && this.caster.isAlive()
                && this.isRenderable();
    }

    public void push(@NotNull Entity entityIn) {
    }

    public boolean isPickable() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    private static float wrapRad(float a) {
        float twoPi = (float)(Math.PI * 2.0);
        a = a % twoPi;
        if (a >= Math.PI) a -= twoPi;
        if (a < -Math.PI) a += twoPi;
        return a;
    }

    private static float lerpAngleRad(float a, float b) {
        float diff = wrapRad(b - a);
        return a + diff * (float) 0.85;
    }

    public void tick() {
        super.tick();

        this.prevCollidePosX = this.collidePosX;
        this.prevCollidePosY = this.collidePosY;
        this.prevCollidePosZ = this.collidePosZ;
        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();

        this.prevYaw = this.renderYaw;
        this.prevPitch = this.renderPitch;
        if (this.level().isClientSide) {
            this.renderYaw = this.getYaw();
            this.renderPitch = this.getPitch();
        }

        if (this.tickCount == 1 && this.level().isClientSide) {
            this.caster = (HerobrineDragonEntity) this.level().getEntity(this.getCasterID());
            this.target = (LivingEntity) this.level().getEntity(this.getTargetID());
        }

        if (this.level().isClientSide && this.target == null && this.getTargetID() != -1) {
            Entity e = this.level().getEntity(this.getTargetID());
            if (e instanceof LivingEntity living) {
                this.target = living;
            }
        }

        if (this.caster != null) {
            Vec3 mouth = this.caster.beamMouthPos(1.0F);
            this.setPos(mouth.x, mouth.y, mouth.z);
        }

        if (this.level() instanceof ServerLevel serverLevel
                && this.tickCount >= 50) {
            Vec3 center = (this.blockSide != null)
                    ? new Vec3(this.collidePosX, this.collidePosY, this.collidePosZ)
                    : (this.getStoredTargetPos() != null ? this.getStoredTargetPos() : this.position());

            ScreenShakeUtil.applyScreenShake(serverLevel, center, 24.0, 4, 6);
            if (this.caster != null && this.caster.getPassengers().contains(this.caster.getSummoner()) && this.caster.getSummoner() instanceof Player) {
                center = new Vec3(this.caster.getSummoner().getX(), this.caster.getSummoner().getY(), this.caster.getSummoner().getZ());
                ScreenShakeUtil.applyScreenShake(serverLevel, center, 24.0, 4, 6);
            }
        }

        if (this.target != null && this.target.isAlive()) {
            Vec3 from = new Vec3(this.getX(), this.getY(), this.getZ());
            Vec3 to = target.getEyePosition(1.0F);

            this.setTargetPos(to);

            float targetYaw = yawTowards(from, to);
            float targetPitch = pitchTowards(from, to);

            float interpolatedYaw = lerpAngleRad(this.getYaw(), targetYaw);
            float interpolatedPitch = Mth.lerp(0.85f, this.getPitch(), targetPitch);

            this.setYaw(interpolatedYaw);
            this.setPitch(interpolatedPitch);

            if (this.level().isClientSide) {
                this.renderYaw = interpolatedYaw;
                this.renderPitch = interpolatedPitch;
            }
        } else {
            Vec3 from = new Vec3(this.getX(), this.getY(), this.getZ());
            Vec3 to = this.getStoredTargetPos();
            if (to == null) {
                to = new Vec3(this.endPosX, this.endPosY, this.endPosZ);
                this.setTargetPos(to);
            }

            float targetYaw = yawTowards(from, to);
            float targetPitch = pitchTowards(from, to);

            float interpolatedYaw = Mth.lerp(0.5f, this.getYaw(), targetYaw);
            float interpolatedPitch = Mth.lerp(0.5f, this.getPitch(), targetPitch);

            this.setYaw(interpolatedYaw);
            this.setPitch(interpolatedPitch);

            if (this.level().isClientSide) {
                this.renderYaw = interpolatedYaw;
                this.renderPitch = interpolatedPitch;
            }
        }

        if (!this.on || (this.caster != null && !this.caster.isAlive())) {
            this.discard();
            return;
        }

        if (this.level().isClientSide && this.tickCount <= 10 && this.caster != null) {
            int particleCount = 8;

            while (true) {
                --particleCount;
                if (particleCount == 0) {
                    break;
                }
            }
        }

        this.calculateEndPos();

        if (this.level().isClientSide) {
            if (this.isRenderable() && ModList.get().isLoaded("photon")) {
                if (this.tickCount >= 3 && this.caster != null && PhotonClientFxUtil.followBeam(
                                "dragon_beam:" + getId(),
                                this.level(),
                                "dragonbeam",
                                this,
                                this::getPhotonBeamStart,
                                this::getPhotonBeamEnd,
                                this::isPhotonBeamAlive,
                                PhotonClientFxUtil.BeamForwardAxis.POSITIVE_X,
                                DRAGON_PHOTON_VISUAL_BASE_LENGTH,
                                getDuration() + 5)) {
                    renderBeam = true;
                }
            } else if (this.isRenderable() && !renderBeam) {
                if (ModList.get().isLoaded("aaa_particles")) {
                    if (this.tickCount >= 3 && this.caster != null && this.target != null) {
                        renderBeam = true;
                        AAAParticlesUtil.sendDragonBeam(caster.beamMouthPos(1.0F), target.getEyePosition(1.0F), this.level(), caster, target);
                    }
                } else if (this.caster != null) {
                    if (this.tickCount >= 3) {
                        this.level().addParticle(ParticleTypes.DRAGON_BREATH, true,
                                caster.beamMouthPos(1.0F).x + new Random().nextDouble(-1, 1),
                                caster.beamMouthPos(1.0F).y + new Random().nextDouble(-1, 1),
                                caster.beamMouthPos(1.0F).z + new Random().nextDouble(-1, 1),
                                0, 0, 0);
                    }
                    if (this.tickCount >= 50) {
                        renderBeam = true;
                        setUseNoVfxThunder(true);
                        setThunderStartStop(caster.beamMouthPos(1.0F), getBeamStopPos());
                    }
                }
            }
        }

        if (this.isRenderable() && this.tickCount >= 50) {
            if (!playSound) {
                playSound = true;
                this.playSound(AnnoyingVillagersModSounds.DRAGON_BREATH.get(), 5.0F, 1.0F);
            }
            List<LivingEntity> hit = this.raytraceEntities(this.level(), new Vec3(this.getX(), this.getY(), this.getZ()), new Vec3(this.endPosX, this.endPosY, this.endPosZ), true).entities;
            if (!this.level().isClientSide) {
                for (LivingEntity target : hit) {
                    target.hurt(damageSources().indirectMagic(this, this.caster.getSummoner()), (float) this.power);
                    LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
                    EpicfightUtil.dealStaminaDamage(damageSources().indirectMagic(this, this.caster.getSummoner()), 0.1F, livingEntityPatch, false);
                    target.hurtMarked = true;
                    target.setDeltaMovement(0.0, 0.0, 0.0);
                    target.lerpMotion(0.0, 0.0, 0.0);
                }
            }
        }

        if (this.tickCount > this.getDuration()) {
            this.on = false;
        }

    }

    static {
        YAW = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.FLOAT);
        PITCH = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.FLOAT);
        DURATION = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.INT);
        CASTER = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.INT);
        TARGET = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.INT);
    }

    public static class DragonBeamHitResult {
        private BlockHitResult blockHit;
        private final List<LivingEntity> entities = new ArrayList<>();

        public DragonBeamHitResult() {
        }

        public BlockHitResult getBlockHit() {
            return this.blockHit;
        }

        public void setBlockHit(HitResult rayTraceResult) {
            if (rayTraceResult.getType() == Type.BLOCK) {
                this.blockHit = (BlockHitResult) rayTraceResult;
            }
        }

        public void addEntityHit(LivingEntity entity) {
            this.entities.add(entity);
        }
    }
}
