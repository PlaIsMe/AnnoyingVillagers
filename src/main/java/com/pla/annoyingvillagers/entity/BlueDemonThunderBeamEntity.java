package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.engine.ClientVfxRouter;
import com.pla.annoyingvillagers.client.engine.PhotonClientFxUtil;
import com.pla.annoyingvillagers.config.AnnoyingVillagersClientConfig.VfxEffect;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.AAAParticlesUtil;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlueDemonThunderBeamEntity extends Entity {
    public LivingEntity caster;

    public double collidePosX, collidePosY, collidePosZ;
    public double prevCollidePosX, prevCollidePosY, prevCollidePosZ;

    public boolean on = true;
    public Direction blockSide;
    private int power;

    private static final EntityDataAccessor<Integer> DURATION =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CASTER =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> START_X =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_Y =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> START_Z =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> END_X =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> END_Y =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> END_Z =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> BEAM_LEN =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> LAST_DIR_X =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> LAST_DIR_Z =
            SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> USE_NO_VFX_THUNDER = SynchedEntityData.defineId(BlueDemonThunderBeamEntity.class, EntityDataSerializers.BOOLEAN);

    @OnlyIn(Dist.CLIENT)
    private Vec3[] attractorPos;

    private boolean renderBeam = false;
    private boolean playSound = false;

    public BlueDemonThunderBeamEntity(EntityType<? extends BlueDemonThunderBeamEntity> type, Level level) {
        super(type, level);
        this.noCulling = true;
        if (level.isClientSide) this.attractorPos = new Vec3[]{Vec3.ZERO};
    }

    public BlueDemonThunderBeamEntity(EntityType<? extends BlueDemonThunderBeamEntity> type,
                                      Level level,
                                      LivingEntity caster,
                                      int duration,
                                      int power,
                                      double beamLength) {
        this(type, level);
        this.caster = caster;
        this.setDuration(duration);
        this.setPower(power);
        this.setBeamLength((float) beamLength);

        this.entityData.set(LAST_DIR_X, 1.0F);
        this.entityData.set(LAST_DIR_Z, 0.0F);

        if (!level.isClientSide && caster != null) {
            this.setCasterID(caster.getId());
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DURATION, 0);
        this.entityData.define(CASTER, -1);

        this.entityData.define(START_X, 0.0F);
        this.entityData.define(START_Y, 0.0F);
        this.entityData.define(START_Z, 0.0F);

        this.entityData.define(END_X, 0.0F);
        this.entityData.define(END_Y, 0.0F);
        this.entityData.define(END_Z, 0.0F);

        this.entityData.define(BEAM_LEN, 7.5F);

        this.entityData.define(LAST_DIR_X, 1.0F);
        this.entityData.define(LAST_DIR_Z, 0.0F);

        this.entityData.define(USE_NO_VFX_THUNDER, false);
    }

    @Override protected void readAdditionalSaveData(@NotNull CompoundTag tag) {}
    @Override protected void addAdditionalSaveData(@NotNull CompoundTag tag) {}

    public void setUseNoVfxThunder(boolean noVfxThunder) {
        this.entityData.set(USE_NO_VFX_THUNDER, noVfxThunder);
    }

    public boolean isSetUseNoVfxThunder() {
        return this.entityData.get(USE_NO_VFX_THUNDER);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public @NotNull PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    public int getDuration() { return this.entityData.get(DURATION); }
    public void setDuration(int duration) { this.entityData.set(DURATION, duration); }

    public int getCasterID() { return this.entityData.get(CASTER); }
    public void setCasterID(int id) { this.entityData.set(CASTER, id); }

    public void setPower(int power) { this.power = power; }

    public float getBeamLength() { return this.entityData.get(BEAM_LEN); }
    public void setBeamLength(float len) { this.entityData.set(BEAM_LEN, len); }

    public Vec3 getStartPos() {
        return new Vec3(entityData.get(START_X), entityData.get(START_Y), entityData.get(START_Z));
    }

    public Vec3 getEndPos() {
        return new Vec3(entityData.get(END_X), entityData.get(END_Y), entityData.get(END_Z));
    }

    private void setStartPos(Vec3 pos) {
        entityData.set(START_X, (float) pos.x);
        entityData.set(START_Y, (float) pos.y);
        entityData.set(START_Z, (float) pos.z);
    }

    private void setEndPos(Vec3 pos) {
        entityData.set(END_X, (float) pos.x);
        entityData.set(END_Y, (float) pos.y);
        entityData.set(END_Z, (float) pos.z);
    }

    private Vec3 getLastDirXZ() {
        Vec3 d = new Vec3(entityData.get(LAST_DIR_X), 0.0D, entityData.get(LAST_DIR_Z));
        return d.lengthSqr() < 1e-8 ? new Vec3(1,0,0) : d.normalize();
    }

    private void setLastDirXZ(Vec3 dir) {
        entityData.set(LAST_DIR_X, (float) dir.x);
        entityData.set(LAST_DIR_Z, (float) dir.z);
    }

    private boolean isPhotonBeamAlive() {
        return this.on && this.isAlive() && !this.isRemoved() && this.caster != null && this.caster.isAlive();
    }

    public void initSpawnState() {
        if (caster == null) return;

        Vec3 fallbackStart = caster.position().add(0.0D, caster.getEyeHeight() * 0.8D, 0.0D);
        Vec3 fallbackEnd = fallbackStart.add(caster.getLookAngle().scale(getBeamLength()));

        setStartPos(fallbackStart);
        setEndPos(fallbackEnd);
        moveTo(fallbackStart.x, fallbackStart.y, fallbackStart.z, caster.getYRot(), caster.getXRot());
    }

    private void updateBeamFromHands() {
        if (caster == null) return;

        Vec3 handLeft = EpicfightUtil.getJointWithTranslation(
                caster, new Vec3f(0,0,0), Armatures.BIPED.get().handL, 0.0F, 0.0F
        );
        Vec3 handRight = EpicfightUtil.getJointWithTranslation(
                caster, new Vec3f(0,0,0), Armatures.BIPED.get().handR, 0.0F, 0.0F
        );

        if (handLeft == null || handRight == null) return;
        Vec3 horizontal = handRight.subtract(handLeft);
        horizontal = new Vec3(horizontal.x, 0.0D, horizontal.z);

        if (horizontal.lengthSqr() < 1.0E-6D) {
            horizontal = getLastDirXZ();
        } else {
            horizontal = horizontal.normalize();
            setLastDirXZ(horizontal);
        }

        double lookY = caster.getLookAngle().y;
        Vec3 dir = new Vec3(horizontal.x, lookY, horizontal.z).normalize();

        double len = getBeamLength();
        Vec3 end = handRight.add(dir.scale(len));

        setStartPos(handRight);
        setEndPos(end);
        setPos(handRight.x, handRight.y, handRight.z);
    }

    public BlueDemonThunderBeamHitResult raytraceEntities(Level world, Vec3 from, Vec3 to) {
        BlueDemonThunderBeamHitResult result = new BlueDemonThunderBeamHitResult();

        BlockHitResult blockHit = world.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        result.setBlockHit(blockHit);

        Vec3 actualTo;
        if (result.blockHit != null) {
            actualTo = result.blockHit.getLocation();
            this.blockSide = result.blockHit.getDirection();
        } else {
            actualTo = to;
            this.blockSide = null;
        }

        this.collidePosX = actualTo.x;
        this.collidePosY = actualTo.y;
        this.collidePosZ = actualTo.z;

        AABB beamBox = new AABB(from, actualTo).inflate(0.5D);
        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, beamBox);

        for (LivingEntity entity : entities) {
            if (entity == this.caster) continue;
            if (entity instanceof Player player && this.caster instanceof Player casterPlayer && player.getUUID().equals(casterPlayer.getUUID())) continue;

            float pad = entity.getPickRadius() + 0.25F;
            AABB aabb = entity.getBoundingBox().inflate(pad);
            Optional<Vec3> hit = aabb.clip(from, actualTo);

            if (aabb.contains(from) || hit.isPresent()) {
                result.addEntityHit(entity);
            }
        }

        return result;
    }

    @Override
    public void tick() {
        super.tick();
        prevCollidePosX = collidePosX;
        prevCollidePosY = collidePosY;
        prevCollidePosZ = collidePosZ;

        if (tickCount == 1 && level().isClientSide) {
            Entity e = level().getEntity(getCasterID());
            if (e instanceof LivingEntity living) caster = living;
        }

        if (caster == null && getCasterID() != -1) {
            Entity e = level().getEntity(getCasterID());
            if (e instanceof LivingEntity living) caster = living;
        }

        if (!on || (caster != null && !caster.isAlive())) {
            discard();
            return;
        }

        updateBeamFromHands();

        Vec3 start = getStartPos();
        Vec3 end = getEndPos();

        if (level().isClientSide && tickCount >= 2) {
            ClientVfxRouter.run(
                    VfxEffect.BLUE_DEMON_THUNDER_BEAM,
                    () -> {
                        if (!PhotonClientFxUtil.isLoaded()) {
                            return false;
                        }
                        if (caster == null) {
                            return true;
                        }

                        boolean handled = PhotonClientFxUtil.followBeam(
                                "blue_demon_thunder_beam:" + getId(),
                                level(),
                                "bluedemonbeam",
                                this,
                                partialTicks -> getStartPos(),
                                partialTicks -> getEndPos(),
                                this::isPhotonBeamAlive,
                                getDuration() + 5);
                        if (handled) {
                            renderBeam = true;
                        }
                        return handled;
                    },
                    () -> {
                        if (renderBeam) {
                            return true;
                        }

                        renderBeam = true;
                        AAAParticlesUtil.sendBlueDemonThunderBeam(level(), this);
                        return true;
                    },
                    () -> {
                        if (!renderBeam) {
                            renderBeam = true;
                            setUseNoVfxThunder(true);
                        }
                    });
        }

        if (!playSound) {
            playSound = true;
            playSound(AnnoyingVillagersModSounds.ELECTRIC_SHOOT.get(), 1.0F, 1.0F);
        }

        List<LivingEntity> hit = raytraceEntities(level(), start, end).entities;

        if (level() instanceof ServerLevel) {
            for (LivingEntity target : hit) {
                if (caster != null) target.hurt(damageSources().indirectMagic(this, caster), (float) power);
                else target.hurt(damageSources().magic(), (float) power);

                target.hurtMarked = true;
                target.addEffect(new MobEffectInstance(
                        AnnoyingVillagersModMobEffects.ELECTRIFY.get(),
                        60,
                        1
                ));
            }
        }

        if (tickCount > getDuration()) {
            on = false;
            discard();
        }
    }

    public static class BlueDemonThunderBeamHitResult {
        private BlockHitResult blockHit;
        private final List<LivingEntity> entities = new ArrayList<>();

        public void setBlockHit(HitResult rayTraceResult) {
            if (rayTraceResult != null && rayTraceResult.getType() == HitResult.Type.BLOCK) {
                this.blockHit = (BlockHitResult) rayTraceResult;
            }
        }

        public void addEntityHit(LivingEntity entity) {
            this.entities.add(entity);
        }
    }
}
