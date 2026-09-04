package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.compat.SmartNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundGroundFracture;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class CommonUtil {
    public static void damageBlocked(DamageSource damagesource, Entity livingentity, ServerLevel level) {
        if (livingentity == null) return;
        if (!damagesource.is(DamageTypes.IN_WALL) && !damagesource.is(DamageTypes.IN_FIRE) && !damagesource.is(DamageTypes.ON_FIRE)) {
            livingentity.playSound(AnnoyingVillagersModSounds.CLASH.get(), 0.5F, 1.0F);
        }
        AnnoyingVillagersModParticleTypes.HIT_BLUNT.get().spawnParticleWithArgument(level, livingentity, damagesource.getEntity());
        if (damagesource.getEntity() instanceof Player player) {
            ScreenShakeUtil.applyScreenShake(level, player.getOnPos().getCenter(), 0.5F, 20, 4);
        }
    }

    public static void damageBlockedForce(Entity defender, Entity attacker, ServerLevel level) {
        defender.playSound(AnnoyingVillagersModSounds.CLASH.get(), 1.0F, 1.0F);
        AnnoyingVillagersModParticleTypes.HIT_BLUNT.get().spawnParticleWithArgument(level, defender, attacker);
        if (attacker instanceof Player player) {
            ScreenShakeUtil.applyScreenShake(level, player.getOnPos().getCenter(), 1.0, 20, 4);
        }
    }

    public static void stunImmunity(Mob mob, int duration, int pAmplifier) {
//      ADD THIS CODE IN AV_EFM
//        mob.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), duration, pAmplifier));
//        mob.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), duration, pAmplifier));
    }

    public static void stunEscapeAi(Mob mob) {
//      ADD THIS CODE IN AV_EFM

//        if (ModList.get().isLoaded("efkick") && this.stunEscapeCooldown == 0 && this.level() instanceof ServerLevel) {
//            if (getLivingEntityPatch() != null) {
//                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(getLivingEntityPatch().getAnimator().getPlayerFor(null)).getRealAnimation();
//                if (EpicfightUtil.isLongHitAnimationNotExecutedAnimation(dynamicAnimation, getLivingEntityPatch()) && mob.isAlive()) {
//                    if (new Random().nextFloat() < CombatBehaviour.calculateGuardBreakWakeUpChance(mob)) {
//                        if (mob instanceof HerobrineMob herobrineMob) {
//                            herobrineMob.setStunEscapeCooldown(60);
//                        } else if (mob instanceof AVNpc avnpc) {
//                            avnpc.setStunEscapeCooldown(60);
//                        } else if (mob instanceof BlueDemonEntity blueDemonEntity) {
//                            blueDemonEntity.setStunEscapeCooldown(60);
//                        }
//                        new DelayedTask(new Random().nextInt(5, 10)) {
//                            @Override
//                            public void run() {
//                                if (getLivingEntityPatch() != null && EpicfightUtil.isLongHitAnimationNotExecutedAnimation(dynamicAnimation, getLivingEntityPatch()) && mob.isAlive()) {
//                                    CombatBehaviour.postGuardBreakWakeUp(mob, getLivingEntityPatch(), serverLevel);
//                                } else {
//                                    if (mob instanceof HerobrineMob herobrineMob) {
//                                        herobrineMob.setStunEscapeCooldown(-1);
//                                    } else if (mob instanceof AVNpc avnpc) {
//                                        avnpc.setStunEscapeCooldown(-1);
//                                    } else if (mob instanceof BlueDemonEntity avnpc) {
//                                        blueDemonEntity.setStunEscapeCooldown(60);
//                                    }
//                                }
//                            }
//                        };
//                    }
//                }
//            }
//        }
    }

    public static Vec3 getVanillaSwordOrBodyPosition(Entity entity) {
        return getVanillaSwordOrBodyPosition(entity, 1.0F);
    }

    public static Vec3 getVanillaSwordOrBodyPosition(Entity entity, float partialTick) {
        if (entity instanceof Mob mob) {
            RigAnimationId animationId = RigAnimationController.getActiveAnimationId(mob);
            int startTick = RigAnimationController.getActiveAnimationStartTick(mob);

            if (animationId != null && startTick >= 0) {
                float elapsedTicks = mob.tickCount - startTick + partialTick;

                Vec3 rigPosition = mob.getMainArm() == HumanoidArm.LEFT
                        ? RigPoseUtil.getLeftWeaponPosition(mob, animationId, elapsedTicks)
                        : RigPoseUtil.getRightWeaponPosition(mob, animationId, elapsedTicks);

                if (rigPosition != null) return rigPosition;
            }
        }

        if (entity instanceof LivingEntity living) {
            int armSign = living.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            float bodyYaw = Mth.lerp(partialTick, living.yBodyRotO, living.yBodyRot) * Mth.DEG_TO_RAD;
            double sinYaw = Mth.sin(bodyYaw);
            double cosYaw = Mth.cos(bodyYaw);
            double armOffset = armSign * 0.35D;
            double crouchOffset = living.isCrouching() ? -0.1875D : 0.0D;
            double x = Mth.lerp((double)partialTick, living.xo, living.getX());
            double y = Mth.lerp((double)partialTick, living.yo, living.getY());
            double z = Mth.lerp((double)partialTick, living.zo, living.getZ());

            return new Vec3(
                    x - cosYaw * armOffset - sinYaw * 0.8D,
                    y + living.getEyeHeight() - 0.45D + crouchOffset,
                    z - sinYaw * armOffset + cosYaw * 0.8D
            );
        }

        return new Vec3(
                Mth.lerp((double)partialTick, entity.xo, entity.getX()),
                Mth.lerp((double)partialTick, entity.yo, entity.getY()) + entity.getBbHeight() * 0.65D,
                Mth.lerp((double)partialTick, entity.zo, entity.getZ())
        );
    }

    public static boolean circleSlamFracture(@Nullable LivingEntity caster, Level level, Vec3 center, double radius) {
        return circleSlamFracture(caster, level, center, radius, false, false, true);
    }

    public static boolean circleSlamFracture(@Nullable LivingEntity caster, Level level, Vec3 center, double radius, boolean hurtEntities) {
        return circleSlamFracture(caster, level, center, radius, false, false, hurtEntities);
    }

    public static boolean circleSlamFracture(@Nullable LivingEntity caster, Level level, Vec3 center, double radius, boolean noSound, boolean noParticle) {
        return circleSlamFracture(caster, level, center, radius, noSound, noParticle, true);
    }

    public static boolean circleSlamFracture(@Nullable LivingEntity caster, Level level, Vec3 center, double radius, boolean noSound, boolean noParticle, boolean hurtEntities) {
        if (level == null || center == null) {
            return false;
        }

        center = snapSlamCenter(center);
        radius = Math.max(0.5D, radius);

        BlockPos origin = findSlamSurface(level, Mth.floor(center.x), Mth.floor(center.y), Mth.floor(center.z));
        if (origin == null) {
            return false;
        }

        BlockState originState = level.getBlockState(origin);
        if (!canTransferShockWave(level, origin, originState)) {
            return false;
        }

        List<BlockPos> affectedBlocks = collectSlamBlocks(level, origin, center, radius);
        if (affectedBlocks.isEmpty()) {
            return false;
        }

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            Vec3 fractureCenter = new Vec3(center.x, origin.getY(), center.z);
            double fractureRadius = radius;
            AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_CHUNK.with(() -> serverLevel.getChunkAt(origin)), new ClientboundGroundFracture(fractureCenter, fractureRadius, noSound, noParticle));
            if (hurtEntities) damageCircleSlamEntities(caster, level, fractureCenter, radius);
        }

        return true;
    }


    public static void spawnGroundSlam(ServerLevel level, Vec3 position, double particleRadius, int particleCount, double spread) {
        if (level == null || position == null) return;
        BlockPos surface = findSlamSurface(level, Mth.floor(position.x), Mth.floor(position.y), Mth.floor(position.z));
        if (surface == null) return;
        level.sendParticles(AnnoyingVillagersModParticleTypes.GROUND_SLAM.get(), position.x, surface.getY() + 1.0D, position.z, 0, particleRadius, particleCount, spread, 1.0D);
    }

    public static void spawnGroundSlamFracture(@Nullable LivingEntity caster, ServerLevel level, Vec3 particlePosition, Vec3 fracturePosition, double particleRadius, int particleCount, double spread, double fractureRadius) {
        if (level == null || particlePosition == null || fracturePosition == null) return;
        BlockPos surface = findSlamSurface(level, Mth.floor(particlePosition.x), Mth.floor(particlePosition.y), Mth.floor(particlePosition.z));
        if (surface == null) return;
        Vec3 particlePos = new Vec3(particlePosition.x, surface.getY() + 1.0D, particlePosition.z);
        Vec3 fractureCenter = new Vec3(fracturePosition.x, surface.getY(), fracturePosition.z);
        level.sendParticles(AnnoyingVillagersModParticleTypes.GROUND_SLAM.get(), particlePos.x, particlePos.y, particlePos.z, 0, particleRadius, particleCount, spread, 1.0D);
        level.playSound(null,
                fractureCenter.x, fractureCenter.y, fractureCenter.z,
                AnnoyingVillagersModSounds.GROUND_SLAM.get(),
                SoundSource.BLOCKS,
                0.8F, 1.0F);
        circleSlamFracture(caster, level, fractureCenter, fractureRadius, true, true, false);
    }

    public static void spawnGroundSlamFracture(@Nullable LivingEntity caster, ServerLevel level, Vec3 position, double particleRadius, int particleCount, double spread, double fractureRadius) {
        spawnGroundSlamFracture(caster, level, position, position, particleRadius, particleCount, spread, fractureRadius);
    }

    public static boolean canTransferShockWave(Level level, BlockPos blockPos, BlockState blockState) {
        return !blockState.isAir()
                && Block.isFaceFull(blockState.getCollisionShape(level, blockPos, CollisionContext.empty()), Direction.DOWN);
    }

    private static Vec3 snapSlamCenter(Vec3 center) {
        Vec3 closestEdge = new Vec3(Math.round(center.x), Math.floor(center.y), Math.round(center.z));
        Vec3 centerOfBlock = new Vec3(Math.floor(center.x) + 0.5D, Math.floor(center.y), Math.floor(center.z) + 0.5D);
        return closestEdge.distanceToSqr(center) < centerOfBlock.distanceToSqr(center) ? closestEdge : centerOfBlock;
    }

    @Nullable
    private static BlockPos findSlamSurface(Level level, int x, int y, int z) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int dy = 2; dy >= -3; dy--) {
            mutable.set(x, y + dy, z);
            BlockState state = level.getBlockState(mutable);

            if (!canTransferShockWave(level, mutable, state)) {
                continue;
            }

            BlockPos above = mutable.above();
            BlockState aboveState = level.getBlockState(above);

            if (!canTransferShockWave(level, above, aboveState)) {
                return mutable.immutable();
            }
        }

        return null;
    }

    private static List<BlockPos> collectSlamBlocks(Level level, BlockPos origin, Vec3 center, double radius) {
        List<BlockPos> affectedBlocks = new ArrayList<>();
        int xFrom = Mth.floor(center.x - radius);
        int xTo = Mth.ceil(center.x + radius);
        int zFrom = Mth.floor(center.z - radius);
        int zTo = Mth.ceil(center.z + radius);
        double radiusSqr = radius * radius;

        for (int x = xFrom; x <= xTo; x++) {
            for (int z = zFrom; z <= zTo; z++) {
                double dx = x + 0.5D - center.x;
                double dz = z + 0.5D - center.z;

                if (dx * dx + dz * dz > radiusSqr) {
                    continue;
                }

                BlockPos surface = findSlamSurface(level, x, origin.getY(), z);
                if (surface != null) {
                    affectedBlocks.add(surface);
                }
            }
        }

        return affectedBlocks;
    }

    private static void spawnCircleSlamParticles(Level level, Vec3 center, double radius, List<BlockPos> affectedBlocks) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(
                AnnoyingVillagersModParticleTypes.GROUND_SLAM.get(),
                center.x,
                center.y,
                center.z,
                0,
                1.0D,
                radius * 10.0D,
                0.5D,
                1.0D
        );
    }

    private static void playCircleSlamSound(Level level, Vec3 center, double radius) {
        BlockPos pos = BlockPos.containing(center);
        boolean smallSlam = radius < 1.5D;

        level.playSound(
                null,
                pos,
                smallSlam ? SoundEvents.PLAYER_ATTACK_KNOCKBACK : SoundEvents.GENERIC_EXPLODE,
                SoundSource.BLOCKS,
                smallSlam ? 0.8F : 1.4F,
                smallSlam ? 1.1F : 0.75F + level.random.nextFloat() * 0.1F
        );
        level.playSound(
                null,
                pos,
                SoundEvents.STONE_BREAK,
                SoundSource.BLOCKS,
                0.9F,
                0.75F + level.random.nextFloat() * 0.2F
        );
    }

    private static void damageCircleSlamEntities(@Nullable LivingEntity caster, Level level, Vec3 center, double radius) {
        AABB hitBox = new AABB(
                center.x - radius,
                center.y - radius,
                center.z - radius,
                center.x + radius,
                center.y + radius + 1.5D,
                center.z + radius
        );
        Set<UUID> hitEntities = new HashSet<>();
        DamageSource source = getCircleSlamDamageSource(caster, level);

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, hitBox)) {
            if (!canCircleSlamHit(caster, target) || !hitEntities.add(target.getUUID())) {
                continue;
            }

            double distance = target.position().distanceTo(center);
            if (distance > radius + target.getBbWidth() * 0.5D) {
                continue;
            }

            double damageScale = 1.0D - ((distance - radius * 0.5D) / radius);
            float damage = (float) (radius * 2.0D * Mth.clamp(damageScale, 0.0D, 1.0D));

            if (damage <= 0.0F) {
                continue;
            }

            if (target.hurt(source, damage)) {
                Vec3 away = target.position().subtract(center);
                Vec3 horizontal = new Vec3(away.x, 0.0D, away.z);

                if (horizontal.lengthSqr() < 1.0E-6D) {
                    horizontal = new Vec3(level.random.nextDouble() - 0.5D, 0.0D, level.random.nextDouble() - 0.5D);
                }

                Vec3 push = horizontal.normalize().scale(0.65D);
                target.push(push.x, 0.32D, push.z);
                target.hurtMarked = true;
            }
        }
    }

    private static DamageSource getCircleSlamDamageSource(@Nullable LivingEntity caster, Level level) {
        if (caster instanceof Player player) {
            return level.damageSources().playerAttack(player);
        }

        if (caster != null) {
            return level.damageSources().mobAttack(caster);
        }

        return level.damageSources().generic();
    }

    private static boolean canCircleSlamHit(@Nullable LivingEntity caster, LivingEntity target) {
        if (target == null || !target.isAlive() || target.isSpectator()) {
            return false;
        }

        if (caster == null) {
            return true;
        }

        return target != caster && !caster.isAlliedTo(target) && !target.isAlliedTo(caster);
    }

    public static boolean isGroundWithin(Entity e, double maxGap) {
        Level level = e.level();
        AABB bb = e.getBoundingBox();
        double feetY = bb.minY;

        int x = Mth.floor(e.getX());
        int z = Mth.floor(e.getZ());
        int startY = Mth.floor(feetY - 1.0e-4);

        int maxSteps = Mth.ceil(maxGap) + 2;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, startY, z);

        for (int i = 0; i <= maxSteps; i++) {
            pos.setY(startY - i);

            BlockState state = level.getBlockState(pos);
            if (state.isAir()) continue;
            VoxelShape shape = state.getCollisionShape(level, pos);
            if (shape.isEmpty()) continue;

            double topY = pos.getY() + shape.max(Direction.Axis.Y);
            double gap = feetY - topY;

            if (gap >= -1.0e-3 && gap <= maxGap + 1.0e-3) {
                return true;
            }
        }

        return false;
    }

    public static void swapToBlock(LivingEntity livingEntity) {
        if (livingEntity instanceof AVNpc avNpc) {
            ItemStack blockStack = InventoryUtils.peekPlaceableBlock(livingEntity).orElse(ItemStack.EMPTY);
            if (blockStack.isEmpty()) {
                return;
            }
            livingEntity.setItemInHand(InteractionHand.MAIN_HAND, blockStack);
            avNpc.setPlaceBlockParryCooldown();
        }
    }

    public static void swapToBow(LivingEntity livingEntity) {
        if (ModList.get().isLoaded("smart_npc") && SmartNpc.isSmartNpc(livingEntity)) {
            SmartNpc.swapToBow(livingEntity);
            return;
        }

        if (!(livingEntity instanceof AVNpc avNpc) || !InventoryUtils.hasArrowAmmo(avNpc)) {
            return;
        }

        if (!(livingEntity.getMainHandItem().getItem() instanceof BowItem) && !InventoryUtils.hasBow(avNpc)) {
            return;
        }

        avNpc.setUseBow(true);
    }

    public static boolean isAvDamageableEfnWeaponsMob(Entity livingEntity) {
        return livingEntity instanceof BlueDemonEntity
                || livingEntity instanceof AngrySteveEntity
                || livingEntity instanceof HerobrineMob
                || livingEntity instanceof HerobrineGregEntity
                || livingEntity instanceof LowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }

    public static boolean isAvRunawayJudgementCutEndMob(Entity livingEntity) {
        return livingEntity instanceof BlueDemonEntity
                || livingEntity instanceof AVNpc
                || livingEntity instanceof HerobrineMob
                || livingEntity instanceof HerobrineGregEntity
                || livingEntity instanceof LowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }

    public static void forceRotate(LivingEntity entity, LivingEntity lookAtEntity) {
        if (entity == null || lookAtEntity == null) {
            return;
        }

        forceRotate(entity, lookAtEntity.getEyePosition());
    }

    public static void forceRotate(LivingEntity entity, BlockPos lookAtPos) {
        if (entity == null || lookAtPos == null) {
            return;
        }

        forceRotate(entity, Vec3.atCenterOf(lookAtPos));
    }

    public static void forceRotate(LivingEntity entity, Vec3 lookAtPos) {
        if (entity == null || lookAtPos == null) {
            return;
        }

        Vec3 eyePos = entity.getEyePosition();

        double dx = lookAtPos.x - eyePos.x;
        double dy = lookAtPos.y - eyePos.y;
        double dz = lookAtPos.z - eyePos.z;

        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

        if (horizontalDistance < 1.0E-7D) {
            return;
        }

        float yaw = (float) (Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F;
        float pitch = (float) -(Mth.atan2(dy, horizontalDistance) * Mth.RAD_TO_DEG);

        yaw = Mth.wrapDegrees(yaw);
        pitch = Mth.clamp(Mth.wrapDegrees(pitch), -90.0F, 90.0F);

        entity.setYRot(yaw);
        entity.setXRot(pitch);

        entity.yRotO = yaw;
        entity.xRotO = pitch;

        entity.setYHeadRot(yaw);
        entity.yHeadRotO = yaw;

        entity.yBodyRot = yaw;
        entity.yBodyRotO = yaw;
    }

    public static void pullEntityTowardCaster(LivingEntity target, LivingEntity caster) {
        pullEntityTowardCaster(target, caster, 0.22D, 0.04D, true);
    }

    public static void pullEntityTowardCaster(
            LivingEntity target,
            LivingEntity caster,
            double strength,
            double yBoost,
            boolean forceLookAtCaster
    ) {
        if (target == null || caster == null) {
            return;
        }

        if (!target.isAlive() || !caster.isAlive()) {
            return;
        }

        if (forceLookAtCaster) {
            forceRotate(target, caster);
        }

        pullEntityTowardPosition(target, caster.position(), strength, yBoost);
    }

    public static void pullEntityTowardPosition(
            Entity target,
            Vec3 targetPos,
            double strength,
            double yBoost
    ) {
        if (target == null || targetPos == null) {
            return;
        }

        Vec3 direction = targetPos.subtract(target.position());
        applyHorizontalDirectionalMotion(target, direction, strength, yBoost);
    }

    public static void pushEntityFromCaster(LivingEntity target, LivingEntity caster) {
        forceRotate(target, caster);
        pushEntityFromCaster(target, caster, 0.35D, 0.08D);
    }

    public static void pushEntityFromCaster(
            Entity target,
            Entity caster,
            double strength,
            double yBoost
    ) {
        if (target == null || caster == null) {
            return;
        }

        pushEntityFromPosition(target, caster.position(), strength, yBoost);
    }

    public static void pushEntityFromPosition(
            Entity target,
            Vec3 sourcePos,
            double strength,
            double yBoost
    ) {
        if (target == null || sourcePos == null) {
            return;
        }

        Vec3 direction = target.position().subtract(sourcePos);

        if (direction.horizontalDistanceSqr() < 1.0E-7D) {
            direction = target.getLookAngle();
        }

        applyHorizontalDirectionalMotion(target, direction, strength, yBoost);
    }

    private static void applyHorizontalDirectionalMotion(
            Entity entity,
            Vec3 direction,
            double strength,
            double yBoost
    ) {
        if (entity == null || direction == null) {
            return;
        }

        if (strength <= 0.0D) {
            return;
        }

        Vec3 horizontal = new Vec3(direction.x, 0.0D, direction.z);

        if (horizontal.lengthSqr() < 1.0E-7D) {
            return;
        }

        Vec3 motion = horizontal.normalize().scale(strength);

        entity.setDeltaMovement(
                entity.getDeltaMovement().add(
                        motion.x,
                        yBoost,
                        motion.z
                )
        );

        entity.hasImpulse = true;
        entity.hurtMarked = true;
    }

    private static boolean matchesItemEntry(ItemStack stack, String entry) {
        if (stack.isEmpty() || entry == null || entry.isBlank()) {
            return false;
        }

        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());

        if (itemId == null) {
            return false;
        }

        if (entry.startsWith("#")) {
            ResourceLocation tagId = ResourceLocation.tryParse(entry.substring(1));

            if (tagId == null) {
                return false;
            }

            return stack.is(TagKey.create(Registries.ITEM, tagId));
        }

        if (entry.endsWith(":*")) {
            String namespace = entry.substring(0, entry.length() - 2);
            return itemId.getNamespace().equals(namespace);
        }
        if (!entry.contains(":")) {
            return itemId.getNamespace().equals(entry);
        }
        return itemId.toString().equals(entry);
    }

    private static boolean matchesEntityEntry(LivingEntity entity, String entry) {
        if (entry == null || entry.isBlank()) {
            return false;
        }

        EntityType<?> type = entity.getType();
        ResourceLocation typeId = ForgeRegistries.ENTITY_TYPES.getKey(type);

        if (typeId == null) {
            return false;
        }

        if (entry.startsWith("#")) {
            ResourceLocation tagId = ResourceLocation.tryParse(entry.substring(1));

            if (tagId == null) {
                return false;
            }

            return type.is(TagKey.create(Registries.ENTITY_TYPE, tagId));
        }

        if (entry.endsWith(":*")) {
            String namespace = entry.substring(0, entry.length() - 2);
            return typeId.getNamespace().equals(namespace);
        }

        if (!entry.contains(":")) {
            return typeId.getNamespace().equals(entry);
        }

        return typeId.toString().equals(entry);
    }

    public static boolean isPullableWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();

        return item instanceof SwordItem
                || item instanceof DiggerItem
                || item instanceof TridentItem;
    }

    public static boolean isBlacklistedWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        for (String entry : AnnoyingVillagersConfig.WEAPON_DISARMS_BLACKLIST.get()) {
            if (matchesItemEntry(stack, entry)) {
                return true;
            }
        }

        return false;
    }

    public static boolean entityCanBeDisarmed(LivingEntity entity) {
        List<? extends String> entries =
                AnnoyingVillagersConfig.WEAPON_DISARMS_AFFECTED_ENTITY_TYPES.get();

        if (entries.isEmpty()) {
            return false;
        }

        for (String entry : entries) {
            if (matchesEntityEntry(entity, entry)) {
                return true;
            }
        }

        return false;
    }

    public static void fallBackOnBlackListWeapon(
            LivingEntity owner,
            Entity source,
            ItemStack blacklistedStack
    ) {

    }

    public static void applyHookClashDisarmLogic(LivingEntity defender, LivingEntity attacker, ServerLevel serverLevel, HookDisarmLaunch launch) {
        if (defender == null || attacker == null || !attacker.isAlive()) return;
        forceRotate(attacker, defender);
        tryDisarmAndLaunchWeapon(serverLevel, defender, attacker, launch);
    }

    private static void tryDisarmAndLaunchWeapon(
            ServerLevel serverLevel,
            LivingEntity livingEntity,
            LivingEntity target,
            HookDisarmLaunch launch
    ) {
        if (!entityCanBeDisarmed(target)) {
            return;
        }

        List<InteractionHand> candidateHands = new ArrayList<>(2);
        ItemStack blacklistedStack = ItemStack.EMPTY;

        ItemStack mainHand = target.getMainHandItem();
        ItemStack offHand = target.getOffhandItem();
        if (isPullableWeapon(mainHand)) {
            if (isBlacklistedWeapon(mainHand)) {
                blacklistedStack = mainHand;
            } else {
                candidateHands.add(InteractionHand.MAIN_HAND);
            }
        }
        if (isPullableWeapon(offHand)) {
            if (isBlacklistedWeapon(offHand)) {
                blacklistedStack = offHand;
            } else {
                candidateHands.add(InteractionHand.OFF_HAND);
            }
        }
        if (candidateHands.isEmpty()) {
            if (!blacklistedStack.isEmpty()) {
                fallBackOnBlackListWeapon(livingEntity, target, blacklistedStack);
            }

            return;
        }
        InteractionHand chosenHand = candidateHands.get(
                serverLevel.random.nextInt(candidateHands.size())
        );
        ItemStack chosenStack = target.getItemInHand(chosenHand);
        if (chosenStack.isEmpty()) {
            return;
        }
        ItemStack droppedStack = chosenStack.copy();
        clearCachedNpcWeapon(target, chosenHand);
        target.setItemInHand(chosenHand, ItemStack.EMPTY);
        if (chosenHand == InteractionHand.MAIN_HAND) {
            tryMoveOffhandWeaponToMainhand(target);
        }
        spawnDisarmedItem(serverLevel, livingEntity, target, droppedStack, launch);
    }

    public static int getRandomDamage(ItemStack itemStack) {
        int maxDamage = itemStack.getMaxDamage();
        int min = maxDamage / 3;
        int max = maxDamage * 3 / 4;
        return new Random().nextInt(max - min + 1) + min;
    }

    private static void tryMoveOffhandWeaponToMainhand(LivingEntity target) {
        ItemStack offhandStack = target.getOffhandItem();
        if (offhandStack.isEmpty()) {
            return;
        }
        if (!isPullableWeapon(offhandStack)) {
            return;
        }
        ItemStack movedStack = offhandStack.copy();
        target.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        target.setItemInHand(InteractionHand.MAIN_HAND, movedStack.copy());
        if (target instanceof AVNpc avNpc) {
            avNpc.setOffWeaponItem(ItemStack.EMPTY);
            avNpc.setMainWeaponItem(movedStack.copy());
            avNpc.setMainWeaponDisarmed(false);
        }
        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.disarmMainWeapon(target, movedStack);
        }
    }

    private static void clearCachedNpcWeapon(LivingEntity target, InteractionHand hand) {
        if (target instanceof AVNpc avNpc) {
            if (hand == InteractionHand.MAIN_HAND) {
                avNpc.setMainWeaponItem(ItemStack.EMPTY);
                avNpc.setMainWeaponDisarmed(true);
            } else {
                avNpc.setOffWeaponItem(ItemStack.EMPTY);
            }
        }

        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.clearCachedWeapon(target, hand);
        }
    }

    private static void spawnDisarmedItem(
            ServerLevel serverLevel,
            LivingEntity livingEntity,
            LivingEntity target,
            ItemStack stack,
            HookDisarmLaunch launch
    ) {
        if (stack.isEmpty()) {
            return;
        }

        Vec3 spawnPos = getVanillaSwordOrBodyPosition(target);
        if (spawnPos == null) {
            spawnPos = target.getEyePosition().subtract(0.0D, 0.35D, 0.0D);
        }

        Vec3 towardAttacker = livingEntity.position().subtract(target.position());
        towardAttacker = new Vec3(towardAttacker.x, 0.0D, towardAttacker.z);

        if (towardAttacker.lengthSqr() < 1.0E-7D) {
            towardAttacker = target.getLookAngle();
            towardAttacker = new Vec3(towardAttacker.x, 0.0D, towardAttacker.z);
        }

        if (towardAttacker.lengthSqr() < 1.0E-7D) {
            towardAttacker = new Vec3(0.0D, 0.0D, 1.0D);
        }

        towardAttacker = towardAttacker.normalize();

        Vec3 right = new Vec3(-towardAttacker.z, 0.0D, towardAttacker.x).normalize();

        Vec3 motion;
        int dropAfterTicks;

        switch (launch) {
            case RIGHT -> {
                motion = right.scale(0.72D)
                        .add(towardAttacker.scale(0.12D))
                        .add(0.0D, 0.40D, 0.0D);

                dropAfterTicks = 16;
            }

            case LEFT -> {
                motion = right.scale(-0.72D)
                        .add(towardAttacker.scale(0.12D))
                        .add(0.0D, 0.40D, 0.0D);

                dropAfterTicks = 16;
            }

            case BACKWARD -> {
                Vec3 backward = towardAttacker.scale(-1.0D);
                spawnPos = target.getEyePosition().add(0.0D, 0.10D, 0.0D);
                motion = backward.scale(0.85D)
                        .add(0.0D, 0.78D, 0.0D);

                dropAfterTicks = 22;
            }

            default -> {
                motion = new Vec3(0.0D, 0.45D, 0.0D);
                dropAfterTicks = 16;
            }
        }

        ItemProjectile projectile = ItemProjectile.createDisarmLaunch(
                serverLevel,
                livingEntity,
                stack.copy(),
                spawnPos,
                motion,
                dropAfterTicks
        );

        serverLevel.addFreshEntity(projectile);
    }
}
