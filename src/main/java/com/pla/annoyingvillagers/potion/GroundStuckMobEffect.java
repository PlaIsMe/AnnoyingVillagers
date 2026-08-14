package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.network.ClientboundGroundStuckKnockoutFx;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

public class GroundStuckMobEffect extends MobEffect {
    public static final int DEFAULT_DURATION = 20 * 8;
    public static final int DEFAULT_AMPLIFIER = 0;
    public static final float BASE_KNOCKOUT_CHANCE = 0.35F;
    public static final int KNOCKOUT_TICKS = 60;
    public static final String NBT_STUCK = "AVGroundStuck";
    public static final String NBT_ANCHOR_X = "AVGroundStuckX";
    public static final String NBT_ANCHOR_Y = "AVGroundStuckY";
    public static final String NBT_ANCHOR_Z = "AVGroundStuckZ";
    public static final String NBT_GROUND_POS = "AVGroundStuckGroundPos";
    public static final String NBT_GROUND_TOP = "AVGroundStuckGroundTop";
    public static final String NBT_STUN_TICKS = "AVGroundStuckStunTicks";
    public static final String NBT_KNOCKOUT_TICKS = "AVGroundStuckKnockoutTicks";
    public static final String NBT_KNOCKOUT_SOURCE = "AVGroundStuckKnockoutSource";

    public GroundStuckMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x594636);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "d5feeedf-a102-45d0-9528-5918efe5b5b5", -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "effect.annoyingvillagers.ground_stuck";
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (!(entity.level() instanceof ServerLevel level)) return;
        CompoundTag tag = entity.getPersistentData();
        if (!tag.getBoolean(NBT_STUCK) && !startStuck(level, entity)) {
            entity.removeEffect(AnnoyingVillagersModMobEffects.GROUND_STUCK.get());
            return;
        }
        if (!isAnchorValid(level, entity)) {
            clear(entity);
            return;
        }
        double x = tag.getDouble(NBT_ANCHOR_X), y = tag.getDouble(NBT_ANCHOR_Y), z = tag.getDouble(NBT_ANCHOR_Z);
        if (entity.distanceToSqr(x, y, z) > 6.25D) {
            clear(entity);
            return;
        }
        entity.xxa = 0.0F;
        entity.yya = 0.0F;
        entity.zza = 0.0F;
        entity.setDeltaMovement(Vec3.ZERO);
        entity.setPos(x, y, z);
        entity.hasImpulse = true;
        entity.hurtMarked = true;
        int stunTicks = tag.getInt(NBT_STUN_TICKS);
        if (stunTicks <= 1) { tag.putInt(NBT_STUN_TICKS, 20); applyPeriodicStun(entity); } else tag.putInt(NBT_STUN_TICKS, stunTicks - 1);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void apply(LivingEntity entity) {
        apply(entity, DEFAULT_DURATION, DEFAULT_AMPLIFIER);
    }

    public static void apply(LivingEntity entity, int duration, int amplifier) {
        if (entity.level().isClientSide()) return;
        entity.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.GROUND_STUCK.get(), duration, amplifier, false, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, 0, false, false, true));
        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, 0, false, false, true));
        entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, duration, 0, false, false, true));
    }

    public static void clear(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            entity.removeEffect(AnnoyingVillagersModMobEffects.GROUND_STUCK.get());
            entity.removeEffect(MobEffects.WEAKNESS);
            entity.removeEffect(MobEffects.DIG_SLOWDOWN);
            entity.removeEffect(MobEffects.CONFUSION);
        }
        clearData(entity);
    }

    public static void clearData(LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData();
        tag.remove(NBT_STUCK);
        tag.remove(NBT_ANCHOR_X);
        tag.remove(NBT_ANCHOR_Y);
        tag.remove(NBT_ANCHOR_Z);
        tag.remove(NBT_GROUND_POS);
        tag.remove(NBT_GROUND_TOP);
        tag.remove(NBT_STUN_TICKS);
    }

    public static float getKnockoutChance(int amplifier) {
        return Math.max(0.03F, BASE_KNOCKOUT_CHANCE / (amplifier + 1.0F));
    }

    public static boolean isCustomGroundHitAnimation(LivingEntityPatch<?> patch) {
        if (patch == null) return false;
        AnimationPlayer player = patch.getAnimator().getPlayerFor(null);
        if (player == null) return false;
        AssetAccessor<? extends StaticAnimation> animation = player.getRealAnimation();
        return animation == AVAnimations.HIT_LEFT || animation == AVAnimations.HIT_RIGHT;
    }

    public static void knockOut(LivingEntity entity, @Nullable LivingEntity attacker) {
        clear(entity);
        CompoundTag tag = entity.getPersistentData();
        tag.putInt(NBT_KNOCKOUT_TICKS, KNOCKOUT_TICKS);
        if (attacker != null) tag.putUUID(NBT_KNOCKOUT_SOURCE, attacker.getUUID());

        float yaw = attacker != null ? attacker.getYRot() : entity.getYRot();
        double radians = Math.toRadians(yaw);
        Vec3 direction = new Vec3(-Math.sin(radians), 0.0D, Math.cos(radians)).normalize();

        double horizontalPower = 3.0D;
        double verticalPower = 1.30D;

        entity.setDeltaMovement(direction.x * horizontalPower, verticalPower, direction.z * horizontalPower);
        entity.hasImpulse = true;
        entity.hurtMarked = true;
        syncKnockout(entity, KNOCKOUT_TICKS);
    }

    public static void syncKnockout(LivingEntity entity, int ticks) {
        if (!entity.level().isClientSide()) AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new ClientboundGroundStuckKnockoutFx(entity.getId(), ticks));
    }

    private static boolean startStuck(ServerLevel level, LivingEntity entity) {
        Support support = findSupport(level, entity);
        if (support == null) return false;
        CompoundTag tag = entity.getPersistentData();
        double x = entity.getX(), z = entity.getZ(), y = support.topY - entity.getBbHeight() * 0.5D;
        tag.putBoolean(NBT_STUCK, true);
        tag.putDouble(NBT_ANCHOR_X, x);
        tag.putDouble(NBT_ANCHOR_Y, y);
        tag.putDouble(NBT_ANCHOR_Z, z);
        tag.putLong(NBT_GROUND_POS, support.pos.asLong());
        tag.putDouble(NBT_GROUND_TOP, support.topY);
        tag.putInt(NBT_STUN_TICKS, 20);
        entity.setPos(x, y, z);
        entity.setDeltaMovement(Vec3.ZERO);
        entity.hasImpulse = true;
        entity.hurtMarked = true;
        LevelUtil.circleSlamFracture(null, level, new Vec3(x, support.topY - 0.01D, z), Math.max(1.75D, entity.getBbWidth() + 0.75D), false, false, false);
        return true;
    }

    private static boolean isAnchorValid(ServerLevel level, LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData();
        if (!tag.contains(NBT_GROUND_POS)) return false;
        BlockPos pos = BlockPos.of(tag.getLong(NBT_GROUND_POS));
        BlockState state = level.getBlockState(pos);
        VoxelShape shape = state.getCollisionShape(level, pos, CollisionContext.of(entity));
        if (shape.isEmpty()) return false;
        return Math.abs(pos.getY() + shape.max(Direction.Axis.Y) - tag.getDouble(NBT_GROUND_TOP)) <= 0.125D;
    }

    private static void applyPeriodicStun(LivingEntity entity) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch != null && !isCustomGroundHitAnimation(patch)) patch.applyStun(StunType.SHORT, 1.0F);
    }

    @Nullable
    private static Support findSupport(CollisionGetter level, LivingEntity entity) {
        double footY = entity.getBoundingBox().minY;
        BlockPos start = BlockPos.containing(entity.getX(), footY - 0.05D, entity.getZ());
        for (int i = 0; i <= 2; i++) {
            BlockPos pos = start.below(i);
            BlockState state = level.getBlockState(pos);
            VoxelShape shape = state.getCollisionShape(level, pos, CollisionContext.of(entity));
            if (shape.isEmpty()) continue;
            double topY = pos.getY() + shape.max(Direction.Axis.Y);
            if (topY <= footY + 0.35D && footY - topY <= 1.5D) return new Support(pos.immutable(), topY);
        }
        return null;
    }

    private record Support(BlockPos pos, double topY) {}
}
