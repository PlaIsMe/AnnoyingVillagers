package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.skill.DemoniacVoltageReaverSkill;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.*;

public class SnakeBladeEntity extends Entity {
    private static final EntityDataAccessor<Optional<UUID>> CREATOR_ID =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> FROM_ID =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TARGET_COUNT =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CURRENT_TARGET_ID =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> PROGRESS =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DAMAGE =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> RETRACTING =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_BLADE =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ENCHANTED =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GUARD =
            SynchedEntityData.defineId(SnakeBladeEntity.class, EntityDataSerializers.BOOLEAN);

    public static final float MAX_EXTEND_TIME = 5.0F;

    private final List<Entity> previouslyTouched = new ArrayList<>();
    private boolean hasChained = false;

    public float prevProgress = 0.0F;

    private String guardDirection = null;

    public SnakeBladeEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public SnakeBladeEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.SNAKE_BLADE.get(), level);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(CREATOR_ID, Optional.empty());
        this.entityData.define(FROM_ID, -1);
        this.entityData.define(TARGET_COUNT, 0);
        this.entityData.define(CURRENT_TARGET_ID, -1);
        this.entityData.define(PROGRESS, 0.0F);
        this.entityData.define(DAMAGE, new Random().nextFloat(10.0F, 15.0F));
        this.entityData.define(RETRACTING, false);
        this.entityData.define(HAS_BLADE, true);
        this.entityData.define(ENCHANTED, false);
        this.entityData.define(GUARD, false);
    }

    public void setEnchanted(boolean enchanted) {
        this.entityData.set(ENCHANTED, enchanted);
    }

    public boolean isEnchanted() {
        return this.entityData.get(ENCHANTED);
    }

    private float getBaseDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setGuard(boolean guard) {
        this.entityData.set(GUARD, guard);
    }

    public boolean isGuard() {
        return this.entityData.get(GUARD);
    }

    public void setGuardDirection(String direction) {
        this.guardDirection = direction;
        this.entityData.set(GUARD, direction != null);
    }

    public void increaseSkillPoint(Entity entity, float value) {
        if (!(entity instanceof Player player)) return;

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;

        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.DEMONIAC_VOLTAGE_REAVER);
        if (skillContainer == null) return;

        DemoniacVoltageReaverSkill skill = (DemoniacVoltageReaverSkill) skillContainer.getSkill();
        float current = skillContainer.getResource();
        float needed = skillContainer.getNeededResource();
        float add = Math.min(value, needed);

        skill.setConsumptionSynchronize(skillContainer, current + add);
    }

    @Override
    public void tick() {
        if (getCreatorEntity() != null
                && getCreatorEntity() instanceof LivingEntity livingEntity
                && !(livingEntity.getMainHandItem().getItem() instanceof DemoniacVoltageReaverItem)) {
            this.discard();
            return;
        }
        HerobrineUtil.spawnEliteEffect(this.level(), this.getX(), this.getY(), this.getZ(), this);

        float progressBefore = this.getProgress();
        this.prevProgress = progressBefore;

        super.tick();

        Entity creator = getCreatorEntity();

        if (!this.level().isClientSide() && this.isGuard() && this.tickCount % 5 == 0) {
            tickGuardAoe(creator);
        }

        updateProgressAndHandleRemoval(creator);
        if (this.isRemoved()) return;

        updateMovementAndAttack(creator);

        if (!this.level().isClientSide()) {
            handleChaining(creator);
        }

        applyVelocity();
    }

    private void tickGuardAoe(Entity creator) {
        final double size = 2.0D;
        final double radiusSqr = size * size;
        final float knockBackStrength = 1.0F;

        LivingEntity owner = (creator instanceof LivingEntity living) ? living : null;

        for (LivingEntity target : this.level().getEntitiesOfClass(
                LivingEntity.class,
                this.getBoundingBox().inflate(size, size, size),
                e -> e.isAlive() && !e.isSpectator()
        )) {
            if (target == owner) continue;
            if (owner != null && (owner.isAlliedTo(target) || target.isAlliedTo(owner))) continue;

            double dx0 = target.getX() - this.getX();
            double dy0 = target.getY(0.5D) - this.getY(0.5D);
            double dz0 = target.getZ() - this.getZ();
            if ((dx0 * dx0 + dy0 * dy0 + dz0 * dz0) > radiusSqr) continue;

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        EpicFightParticles.HIT_BLUNT.get(),
                        this.getX(), this.getY() + 1.5, this.getZ() + 0.8,
                        1,
                        0.1, 0.1, 0.1,
                        1
                );
            }

            this.playSound(AnnoyingVillagersModSounds.OBSIDIAN_HIT.get(), 0.5F, (float) (0.5 + Math.random() * 0.5));

            LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);

            DamageSource src = (owner != null)
                    ? this.level().damageSources().indirectMagic(this, owner)
                    : this.level().damageSources().generic();
            target.hurt(src, this.getBaseDamage() / 2);
            EpicfightUtil.dealStaminaDamage(src, 1.0F, targetPatch, false);

            if (creator != null) {
                increaseSkillPoint(creator, 3.0F);
            }
            if (targetPatch != null) {
                targetPatch.knockBackEntity(this.position(), knockBackStrength);
            } else {
                double kbX = this.getX() - target.getX();
                double kbZ = this.getZ() - target.getZ();
                target.knockback(knockBackStrength, kbX, kbZ);
            }
        }
    }

    private void updateProgressAndHandleRemoval(Entity creator) {
        float progress = this.getProgress();

        if (!this.isRetracting() && progress < MAX_EXTEND_TIME) {
            this.setProgress(progress + 1.0F);
        } else if (this.isRetracting() && progress > 0.0F) {
            this.setProgress(progress - 1.0F);
        }

        if (this.isRetracting() && this.getProgress() == 0.0F) {
            onFullyRetracted(creator);
        }
    }

    private void onFullyRetracted(Entity creator) {
        Entity from = this.getFromEntity();

        if (from instanceof SnakeBladeEntity parentSnakeBladeEntity) {
            parentSnakeBladeEntity.setRetracting(true);
            updateLastFragment(parentSnakeBladeEntity);
        } else {
            updateLastFragment(null);
            clearSnakeAnimationTag(creator);

            LivingEntityPatch<?> creatorPatch = EpicFightCapabilities.getEntityPatch(creator, LivingEntityPatch.class);
            if (creatorPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(creatorPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (dynamicAnimation == AVAnimations.SNAKE_BLADE || dynamicAnimation == AVAnimations.SNAKE_BLADE_GUARD) {
                    creatorPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                }
            }
        }

        this.remove(RemovalReason.DISCARDED);
    }

    private void clearSnakeAnimationTag(Entity creator) {
        if (creator instanceof Player player) {
            for (ItemStack stack : player.getInventory().items) {
                if (stack.is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) {
                    stack.removeTagKey("SnakeAnimation");
                }
            }
        } else if (creator instanceof LivingEntity living) {
            living.getMainHandItem().removeTagKey("SnakeAnimation");
        }
    }

    private void updateMovementAndAttack(Entity creator) {
        if (!(creator instanceof LivingEntity livingCreator)) return;

        Entity currentTarget = getToEntity();
        Vec3 targetPos = null;

        if (currentTarget != null) {
            targetPos = new Vec3(currentTarget.getX(), currentTarget.getY(0.4F), currentTarget.getZ());
        } else if (this.guardDirection != null) {
            targetPos = DemoniacVoltageReaverItem.guardTargetFor(livingCreator, this.guardDirection);
        }

        if (targetPos != null) {
            Vec3 delta = targetPos.subtract(this.position());
            this.setDeltaMovement(delta.scale(0.5F));
        }

        if (currentTarget != null && !this.level().isClientSide && this.getProgress() >= MAX_EXTEND_TIME) {
            if (this.tickCount % 2 == 0) {
                tryAttackTarget(livingCreator, currentTarget);
            }
        }
    }

    private void tryAttackTarget(LivingEntity creator, Entity target) {
        if (target == creator) return;

        if (target.hurt(this.level().damageSources().indirectMagic(this, creator), this.getBaseDamage())) {
            // Mark touched so child chains avoid bouncing back
            if (!previouslyTouched.contains(target)) {
                previouslyTouched.add(target);
            }

            increaseSkillPoint(creator, 5.0F);

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        EpicFightParticles.HIT_BLUNT.get(),
                        this.getX(), this.getY() + 1.5, this.getZ() + 0.8,
                        1,
                        0.1, 0.1, 0.1,
                        1
                );
            }

            this.playSound(AnnoyingVillagersModSounds.OBSIDIAN_HIT.get(), 0.5F, (float) (0.5 + Math.random() * 0.5));
            LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
            if (targetPatch != null) {
                EpicfightUtil.dealStaminaDamageByPercentage(
                        this.level().damageSources().indirectMagic(this, creator), targetPatch, 0.5D, true);
            }

            if (target instanceof LivingEntity livingTarget) {
                float strength = 3.0F;
                double dx = this.getX() - target.getX();
                double dz = this.getZ() - target.getZ();
                livingTarget.knockback(strength, dx, dz);
            }
        }
    }

    private void handleChaining(Entity creator) {
        if (this.hasChained) return;

        if (this.getTargetsHit() > 5) {
            this.setRetracting(true);
            return;
        }

        if (!(creator instanceof LivingEntity livingCreator)) return;
        if (this.getProgress() < MAX_EXTEND_TIME) return;

        if (this.guardDirection != null) {
            String nextDirection = nextGuardDirection(this.guardDirection);
            createChainGuard(nextDirection);
            this.hasChained = true;
            return;
        }

        Entity closestValid = null;
        for (Entity candidate : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(12.0D))) {
            if (candidate.equals(creator)) continue;
            if (previouslyTouched.contains(candidate)) continue;
            if (!isValidTarget(livingCreator, candidate)) continue;
            if (!hasLineOfSightTo(candidate)) continue;

            if (closestValid == null || this.distanceTo(candidate) < this.distanceTo(closestValid)) {
                closestValid = candidate;
            }
        }

        if (closestValid != null) {
            createChain(closestValid);
            this.hasChained = true;
        } else {
            this.setRetracting(true);
        }
    }

    private void applyVelocity() {
        Vec3 vel = this.getDeltaMovement();

        double x = this.getX() + vel.x;
        double y = this.getY() + vel.y;
        double z = this.getZ() + vel.z;

        this.setDeltaMovement(vel.scale(0.99F));
        this.setPos(x, y, z);
    }

    private boolean isValidTarget(LivingEntity creator, Entity entity) {
        if (!creator.isAlliedTo(entity) && !entity.isAlliedTo(creator) && entity instanceof Mob) {
            return true;
        }

        return (creator.getLastHurtMob() != null && creator.getLastHurtMob().getUUID().equals(entity.getUUID()))
                || (creator.getLastHurtByMob() != null && creator.getLastHurtByMob().getUUID().equals(entity.getUUID()));
    }

    private boolean hasLineOfSightTo(Entity target) {
        if (target.level() != this.level()) return false;

        Vec3 from = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 to = new Vec3(target.getX(), target.getEyeY(), target.getZ());

        if (to.distanceTo(from) > 128.0D) return false;

        return this.level().clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this))
                .getType() == HitResult.Type.MISS;
    }

    private void updateLastFragment(SnakeBladeEntity lastSnakeBladeEntity) {
        Entity creator = getCreatorEntity();
        if (creator == null) {
            UUID uuid = this.getCreatorEntityUUID();
            if (uuid != null) {
                creator = this.level().getPlayerByUUID(uuid);
            }
        }

        if (creator instanceof LivingEntity livingCreator) {
            DemoniacVoltageReaverItem.setLastFragment(livingCreator, lastSnakeBladeEntity);
        }
    }

    private void createChain(Entity nextTarget) {
        this.entityData.set(HAS_BLADE, false);

        SnakeBladeEntity child = AnnoyingVillagersModEntities.SNAKE_BLADE.get().create(this.level());
        if (child == null) return;

        if (this.isEnchanted()) {
            child.setEnchanted(true);
        }

        child.previouslyTouched.addAll(this.previouslyTouched);
        if (!child.previouslyTouched.contains(nextTarget)) {
            child.previouslyTouched.add(nextTarget);
        }

        child.setCreatorEntityUUID(this.getCreatorEntityUUID());
        child.setFromEntityID(this.getId());
        child.setToEntityID(nextTarget.getId());
        child.setPos(nextTarget.getX(), nextTarget.getY(0.4F), nextTarget.getZ());
        child.setTargetsHit(this.getTargetsHit() + 1);

        updateLastFragment(child);
        this.level().addFreshEntity(child);
    }

    private void createChainGuard(String nextDirection) {
        this.entityData.set(HAS_BLADE, false);

        SnakeBladeEntity child = AnnoyingVillagersModEntities.SNAKE_BLADE.get().create(this.level());
        if (child == null) return;

        if (this.isEnchanted()) {
            child.setEnchanted(true);
        }

        child.previouslyTouched.addAll(this.previouslyTouched);
        child.setCreatorEntityUUID(this.getCreatorEntityUUID());
        child.setFromEntityID(this.getId());
        child.setToEntityID(-1);
        child.setTargetsHit(this.getTargetsHit() + 1);
        child.setGuardDirection(nextDirection);

        Entity creator = getCreatorEntity();
        if (creator instanceof LivingEntity living) {
            Vec3 p = DemoniacVoltageReaverItem.guardTargetFor(living, nextDirection);
            child.setPos(p.x, p.y, p.z);
        } else {
            child.copyPosition(this);
        }

        updateLastFragment(child);
        this.level().addFreshEntity(child);
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float amount) {
        if (!this.level().isClientSide() && this.level() instanceof ServerLevel serverLevel && !pSource.is(DamageTypes.IN_WALL)) {
            EpicfightUtil.damageBlocked(pSource, this, serverLevel);
        }
        return false;
    }

    private static String nextGuardDirection(String current) {
        if ("forward_left".equalsIgnoreCase(current)) return "forward_right";
        if ("forward_right".equalsIgnoreCase(current)) return "backward_right";
        if ("backward_right".equalsIgnoreCase(current)) return "backward_left";
        return "forward_left";
    }

    public UUID getCreatorEntityUUID() {
        return this.entityData.get(CREATOR_ID).orElse(null);
    }

    public void setCreatorEntityUUID(UUID id) {
        this.entityData.set(CREATOR_ID, Optional.ofNullable(id));
    }

    public Entity getCreatorEntity() {
        UUID uuid = getCreatorEntityUUID();
        if (uuid != null && !this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
            return serverLevel.getEntity(uuid);
        }
        return null;
    }

    public int getFromEntityID() {
        return this.entityData.get(FROM_ID);
    }

    public void setFromEntityID(int id) {
        this.entityData.set(FROM_ID, id);
    }

    public Entity getFromEntity() {
        int id = getFromEntityID();
        return id == -1 ? null : this.level().getEntity(id);
    }

    public int getToEntityID() {
        return this.entityData.get(CURRENT_TARGET_ID);
    }

    public void setToEntityID(int id) {
        this.entityData.set(CURRENT_TARGET_ID, id);
    }

    public Entity getToEntity() {
        int id = getToEntityID();
        return id == -1 ? null : this.level().getEntity(id);
    }

    public int getTargetsHit() {
        return this.entityData.get(TARGET_COUNT);
    }

    public void setTargetsHit(int count) {
        this.entityData.set(TARGET_COUNT, count);
    }

    public float getProgress() {
        return this.entityData.get(PROGRESS);
    }

    public void setProgress(float progress) {
        this.entityData.set(PROGRESS, progress);
    }

    public boolean isRetracting() {
        return this.entityData.get(RETRACTING);
    }

    public void setRetracting(boolean retract) {
        this.entityData.set(RETRACTING, retract);
    }

    public boolean hasBlade() {
        return this.entityData.get(HAS_BLADE);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
    }

    public boolean isCreator(Entity mob) {
        UUID creatorUuid = this.getCreatorEntityUUID();
        return creatorUuid != null && mob.getUUID().equals(creatorUuid);
    }
}
