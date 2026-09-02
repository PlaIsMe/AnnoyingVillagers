package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VacuumSliceEntity extends Entity {
    public static final int MAX_LIFETIME = 36;
    private static final double BLADE_HALF_WIDTH = 4.25D, BLADE_HALF_LENGTH = 4.25D, BLADE_HALF_THICKNESS = 0.55D;
    private static final double GROUND_CUT_HALF_WIDTH = 4.50D, BLOCK_SAMPLE_SPACING = 0.75D;
    private static final int FRACTURE_SAMPLE_COUNT = 5;
    private static final double FRACTURE_RADIUS = 1.65D, TRACE_UP = 2.5D, TRACE_DOWN = 8.0D, PARTICLE_Y_OFFSET = 0.055D;
    private static final float END_FIRE_CHANCE = 0.50F;

    private final Set<UUID> hitEntities = new HashSet<>();
    private float damage = 10.0F;
    private int sharpnessLevel, fireAspectLevel, flameLevel, knockbackLevel;
    @Nullable private LivingEntity owner;
    @Nullable private UUID ownerUuid;

    public VacuumSliceEntity(EntityType<? extends VacuumSliceEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.setInvulnerable(true);
        this.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return EntityDimensions.scalable((float) (BLADE_HALF_WIDTH * 2.0D), (float) (BLADE_HALF_THICKNESS * 2.0D));
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUuid != null && this.level() instanceof ServerLevel level) {
            Entity entity = level.getEntity(this.ownerUuid);
            if (entity instanceof LivingEntity living) this.owner = living;
        }
        return this.owner;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUuid = owner == null ? null : owner.getUUID();
    }

    public void captureWeaponEnchantments(ItemStack weapon) {
        this.sharpnessLevel = weapon.isEmpty() ? 0 : EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, weapon);
        this.fireAspectLevel = weapon.isEmpty() ? 0 : EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, weapon);
        this.flameLevel = weapon.isEmpty() ? 0 : EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, weapon);
        this.knockbackLevel = weapon.isEmpty() ? 0 : EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, weapon);
    }

    public float getDamage() { return this.damage; }
    public void setDamage(float damage) { this.damage = Math.max(0.0F, damage); }

    public float getRenderAlpha(float partialTicks) {
        float age = this.tickCount + partialTicks, fadeStart = MAX_LIFETIME - 10.0F;
        return age <= fadeStart ? 1.0F : Mth.clamp((MAX_LIFETIME - age) / 10.0F, 0.0F, 1.0F);
    }

    @Override protected void defineSynchedData() {}

    @Override
    public void tick() {
        super.tick();
        Vec3 velocity = this.getDeltaMovement();
        if (velocity.lengthSqr() < 1.0E-7D || this.tickCount > MAX_LIFETIME) {
            if (!this.level().isClientSide) this.discard();
            return;
        }

        this.updateRotationFromVelocity(velocity);
        Vec3 start = this.position(), end = start.add(velocity);

        if (!this.level().isClientSide) {
            ServerLevel level = (ServerLevel) this.level();
            if (!level.hasChunkAt(BlockPos.containing(end))) {
                this.discard();
                return;
            }

            Vec3 impactCenter = this.findWideBlockHit(level, start, end, velocity);
            if (impactCenter != null) {
                this.setPos(impactCenter);
                this.createHorizontalGroundCut(level, impactCenter, velocity);
                this.discard();
                return;
            }
            this.damageEntitiesAlongPath(start, end, velocity);
        }
        this.move(MoverType.SELF, velocity);
    }

    @Nullable
    private Vec3 findWideBlockHit(ServerLevel level, Vec3 start, Vec3 end, Vec3 velocity) {
        Vec3 bladeForward = velocity.normalize(), flatForward = horizontalDirection(bladeForward);
        Vec3 right = new Vec3(-flatForward.z, 0.0D, flatForward.x);
        Vec3 leadingStart = start.add(bladeForward.scale(BLADE_HALF_LENGTH)), leadingEnd = end.add(bladeForward.scale(BLADE_HALF_LENGTH));
        int samples = Math.max(1, Mth.ceil(BLADE_HALF_WIDTH * 2.0D / BLOCK_SAMPLE_SPACING));
        Set<BlockPos> cutBlocks = new HashSet<>();
        Vec3 closestCenter = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i <= samples; i++) {
            double lateral = -BLADE_HALF_WIDTH + BLADE_HALF_WIDTH * 2.0D * i / samples;
            Vec3 sideOffset = right.scale(lateral), rayStart = leadingStart.add(sideOffset), rayEnd = leadingEnd.add(sideOffset);

            while (rayStart.distanceToSqr(rayEnd) > 1.0E-6D) {
                BlockHitResult hit = level.clip(new ClipContext(rayStart, rayEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                if (hit.getType() != HitResult.Type.BLOCK) break;

                if (hit.getDirection() == Direction.UP) {
                    double distance = leadingStart.add(sideOffset).distanceToSqr(hit.getLocation());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestCenter = hit.getLocation().subtract(sideOffset);
                    }
                    break;
                }

                BlockPos pos = hit.getBlockPos();
                BlockState state = level.getBlockState(pos);
                if (cutBlocks.add(pos) && !this.cutBlock(level, pos, state, velocity)) {
                    double distance = leadingStart.add(sideOffset).distanceToSqr(hit.getLocation());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestCenter = hit.getLocation().subtract(sideOffset);
                    }
                    break;
                }

                rayStart = hit.getLocation().add(bladeForward.scale(0.05D));
            }
        }
        return closestCenter;
    }

    private boolean cutBlock(ServerLevel level, BlockPos pos, BlockState state, Vec3 velocity) {
        if (state.isAir()) return true;
        this.onVacuumSliceHitBlock(level, pos, state, velocity);
        if (state.is(AnnoyingVillagersModBlocks.END_FIRE.get())) return true;
        if (state.getDestroySpeed(level, pos) < 0.0F) return false;

        ItemStack item = new ItemStack(state.getBlock().asItem());
        if (!item.isEmpty()) Block.popResource(level, pos, item);
        Entity breaker = this.getOwner() == null ? this : this.getOwner();
        if (!level.destroyBlock(pos, false, breaker)) return false;
        if (level.getRandom().nextFloat() < END_FIRE_CHANCE) this.tryPlaceEndFire(level, pos, velocity);
        return true;
    }

    private void updateRotationFromVelocity(Vec3 velocity) {
        double horizontal = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.setYRot((float) (Mth.atan2(-velocity.x, velocity.z) * Mth.RAD_TO_DEG));
        this.setXRot((float) (Mth.atan2(-velocity.y, horizontal) * Mth.RAD_TO_DEG));
    }

    private void damageEntitiesAlongPath(Vec3 start, Vec3 end, Vec3 velocity) {
        Vec3 segment = end.subtract(start);
        double length = segment.length();
        if (length < 1.0E-7D) return;

        Vec3 bladeForward = segment.scale(1.0D / length), flatForward = horizontalDirection(bladeForward);
        Vec3 right = new Vec3(-flatForward.z, 0.0D, flatForward.x), normal = right.cross(bladeForward).normalize();
        Vec3 sweepStart = start.subtract(bladeForward.scale(BLADE_HALF_LENGTH));
        Vec3 sweepEnd = end.add(bladeForward.scale(BLADE_HALF_LENGTH));
        AABB searchBox = new AABB(sweepStart, sweepEnd).inflate(BLADE_HALF_WIDTH + 1.0D, BLADE_HALF_THICKNESS + 2.0D, BLADE_HALF_WIDTH + 1.0D);
        LivingEntity owner = this.getOwner();

        for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> this.canHit(entity, owner))) {
            Vec3 relative = target.getBoundingBox().getCenter().subtract(start);
            double along = relative.dot(bladeForward), lateral = Math.abs(relative.dot(right)), normalDistance = Math.abs(relative.dot(normal));
            double horizontalRadius = target.getBbWidth() * 0.5D, verticalRadius = target.getBbHeight() * 0.5D;
            boolean insideLength = along >= -BLADE_HALF_LENGTH - horizontalRadius && along <= length + BLADE_HALF_LENGTH + horizontalRadius;
            if (insideLength && lateral <= BLADE_HALF_WIDTH + horizontalRadius && normalDistance <= BLADE_HALF_THICKNESS + verticalRadius) {
                this.hurtTarget(target, owner, velocity, 1.0F);
            }
        }
    }

    private boolean canHit(LivingEntity target, @Nullable LivingEntity owner) {
        return target.isAlive() && target != owner && !this.hitEntities.contains(target.getUUID()) && (owner == null || !owner.isAlliedTo(target));
    }

    private void hurtTarget(LivingEntity target, @Nullable LivingEntity owner, Vec3 velocity, float multiplier) {
        DamageSource source = owner == null ? this.damageSources().magic() : this.damageSources().mobProjectile(this, owner);
        float sharpness = this.sharpnessLevel > 0 ? this.sharpnessLevel * 0.5F + 0.5F : 0.0F;
        if (!target.hurt(source, (this.damage + sharpness) * multiplier)) return;

        this.hitEntities.add(target.getUUID());
        if (this.fireAspectLevel > 0) target.setSecondsOnFire(this.fireAspectLevel * 4);
        else if (this.flameLevel > 0) target.setSecondsOnFire(5);

        Vec3 horizontal = horizontalDirection(velocity);
        if (this.knockbackLevel > 0) target.knockback(this.knockbackLevel * 0.5D, -horizontal.x, -horizontal.z);
        if (target instanceof Mob targetMob && RigStunController.supports(targetMob)) RigStunController.applyStunBack(targetMob);
        if (owner != null) owner.setLastHurtMob(target);
    }

    private void createHorizontalGroundCut(ServerLevel level, Vec3 impactCenter, Vec3 velocity) {
        Vec3 flatForward = horizontalDirection(velocity), right = new Vec3(-flatForward.z, 0.0D, flatForward.x);
        Vec3 groundCenter = this.spawnHorizontalImpact(level, impactCenter, velocity);
        if (groundCenter == null) groundCenter = impactCenter;
        this.applyLandingLine(level, groundCenter, right, velocity);
        this.damageGroundCut(level, groundCenter, flatForward, right, velocity);
    }

    @Nullable
    private Vec3 spawnHorizontalImpact(ServerLevel level, Vec3 impactPosition, Vec3 velocity) {
        Vec3 flightForward = horizontalDirection(velocity), cutDirection = new Vec3(-flightForward.z, 0.0D, flightForward.x);
        BlockHitResult centerHit = this.findGround(level, impactPosition);
        if (centerHit == null) return null;

        Vec3 center = surfacePosition(centerHit);
        float cutYaw = yawFromDirection(cutDirection);
        int halfSamples = FRACTURE_SAMPLE_COUNT / 2;

        for (int i = -halfSamples; i <= halfSamples; i++) {
            double offset = GROUND_CUT_HALF_WIDTH * i / halfSamples;
            BlockHitResult hit = this.findGround(level, center.add(cutDirection.scale(offset)));
            if (hit == null) continue;
            Vec3 surface = surfacePosition(hit);
            CommonUtil.circleSlamFracture(null,level,fracturePosition(hit),FRACTURE_RADIUS,true,true,false);
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.CAMPFIRE_COSY_SMOKE,surface.x,surface.y + PARTICLE_Y_OFFSET,surface.z,2,0.15D,0.03D,0.15D,0.01D);
        }

        double slashOffset = Math.min(GROUND_CUT_HALF_WIDTH * 0.55D, 2.5D);
        this.spawnGroundSlash(level, center.add(cutDirection.scale(slashOffset)), cutYaw, 0.0D);
        this.spawnGroundSlash(level, center.add(cutDirection.scale(-slashOffset)), cutYaw, 180.0D);
        return center;
    }

    private void spawnGroundSlash(ServerLevel level, Vec3 position, float yaw, double roll) {
        BlockHitResult hit = this.findGround(level, position);
        if (hit == null) return;
        Vec3 surface = surfacePosition(hit);
        CommonUtil.spawnGroundSlam(level,surface,0.8D,20,0.6D);
    }

    private void applyLandingLine(ServerLevel level, Vec3 center, Vec3 right, Vec3 velocity) {
        int samples = Math.max(1, Mth.ceil(GROUND_CUT_HALF_WIDTH * 2.0D / BLOCK_SAMPLE_SPACING));
        Set<BlockPos> visited = new HashSet<>();

        for (int i = 0; i <= samples; i++) {
            double offset = -GROUND_CUT_HALF_WIDTH + GROUND_CUT_HALF_WIDTH * 2.0D * i / samples;
            BlockHitResult hit = this.findGround(level, center.add(right.scale(offset)));
            if (hit == null || !visited.add(hit.getBlockPos())) continue;

            BlockPos groundPos = hit.getBlockPos();
            BlockState groundState = level.getBlockState(groundPos);
            this.onVacuumSliceHitBlock(level, groundPos, groundState, velocity);

            BlockPos firePos = groundPos.above();
            BlockState fireState = level.getBlockState(firePos);
            if (!fireState.isAir()) this.onVacuumSliceHitBlock(level, firePos, fireState, velocity);
            if (level.getRandom().nextFloat() < END_FIRE_CHANCE) this.tryPlaceEndFire(level, firePos, velocity);
        }
    }

    private void tryPlaceEndFire(ServerLevel level, BlockPos pos, Vec3 velocity) {
        BlockState current = level.getBlockState(pos);
        if (current.is(AnnoyingVillagersModBlocks.END_FIRE.get())) return;
        if (!current.canBeReplaced() || !level.getFluidState(pos).isEmpty()) return;

        BlockState endFire = AnnoyingVillagersModBlocks.END_FIRE.get().defaultBlockState();
        if (!endFire.canSurvive(level, pos) || !level.setBlock(pos, endFire, Block.UPDATE_ALL)) return;
        this.onVacuumSliceHitBlock(level, pos, endFire, velocity);
    }

    protected void onVacuumSliceHitBlock(ServerLevel level, BlockPos pos, BlockState state, Vec3 incomingVelocity) {
        state.is(AnnoyingVillagersModBlocks.END_FIRE.get());
    }

    private void damageGroundCut(ServerLevel level, Vec3 center, Vec3 forward, Vec3 right, Vec3 velocity) {
        LivingEntity owner = this.getOwner();
        AABB area = new AABB(center.add(right.scale(-GROUND_CUT_HALF_WIDTH)).add(0.0D, -0.5D, 0.0D), center.add(right.scale(GROUND_CUT_HALF_WIDTH)).add(0.0D, 2.5D, 0.0D)).inflate(1.0D);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, entity -> this.canHit(entity, owner))) {
            Vec3 relative = target.getBoundingBox().getCenter().subtract(center);
            double lateral = Math.abs(relative.dot(right)), forwardDistance = Math.abs(relative.dot(forward));
            if (lateral <= GROUND_CUT_HALF_WIDTH + target.getBbWidth() * 0.5D && forwardDistance <= 1.5D + target.getBbWidth() * 0.5D && relative.y > -1.0D && relative.y < 3.0D) {
                this.hurtTarget(target, owner, velocity, 0.75F);
            }
        }
    }

    @Nullable
    private BlockHitResult findGround(ServerLevel level, Vec3 position) {
        BlockHitResult hit = level.clip(new ClipContext(position.add(0.0D, TRACE_UP, 0.0D), position.add(0.0D, -TRACE_DOWN, 0.0D), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hit.getType() != HitResult.Type.BLOCK) return null;
        BlockState state = level.getBlockState(hit.getBlockPos());
        return CommonUtil.canTransferShockWave(level,hit.getBlockPos(),state) ? hit : null;
    }

    private static Vec3 surfacePosition(BlockHitResult hit) { return hit.getLocation().add(0.0D, 0.01D, 0.0D); }
    private static Vec3 fracturePosition(BlockHitResult hit) { return new Vec3(hit.getLocation().x, hit.getBlockPos().getY(), hit.getLocation().z); }
    private static Vec3 horizontalDirection(Vec3 vector) {
        Vec3 horizontal = new Vec3(vector.x, 0.0D, vector.z);
        return horizontal.lengthSqr() < 1.0E-7D ? new Vec3(0.0D, 0.0D, 1.0D) : horizontal.normalize();
    }
    private static float yawFromDirection(Vec3 direction) { return (float) (Mth.atan2(-direction.x, direction.z) * Mth.RAD_TO_DEG); }

    @Override public boolean hurt(@NotNull DamageSource source, float amount) { return false; }
    @Override public boolean isPickable() { return false; }
    @Override public boolean isAttackable() { return false; }
    @Override public boolean displayFireAnimation() { return false; }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        this.damage = tag.getFloat("Damage");
        this.sharpnessLevel = tag.getInt("SharpnessLevel");
        this.fireAspectLevel = tag.getInt("FireAspectLevel");
        this.flameLevel = tag.getInt("FlameLevel");
        this.knockbackLevel = tag.getInt("KnockbackLevel");
        if (tag.hasUUID("Owner")) this.ownerUuid = tag.getUUID("Owner");
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        tag.putFloat("Damage", this.damage);
        tag.putInt("SharpnessLevel", this.sharpnessLevel);
        tag.putInt("FireAspectLevel", this.fireAspectLevel);
        tag.putInt("FlameLevel", this.flameLevel);
        tag.putInt("KnockbackLevel", this.knockbackLevel);
        if (this.ownerUuid != null) tag.putUUID("Owner", this.ownerUuid);
    }

    @Override public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
}
