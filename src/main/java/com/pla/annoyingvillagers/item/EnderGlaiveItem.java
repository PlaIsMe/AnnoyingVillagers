package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.VacuumSliceEntity;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

public class EnderGlaiveItem extends SwordItem {
    private static final double DEFAULT_SPEED = 1.60D;
    private static final double DEFAULT_DOWN_ANGLE_DEGREES = 24.0D;
    public static final float DEFAULT_DAMAGE = 10.0F;

    private static final Tier TIER = new Tier() {
        @Override
        public int getUses() {
            return 1561;
        }

        @Override
        public float getSpeed() {
            return 4.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 5.0F;
        }

        @Override
        public int getLevel() {
            return 1;
        }

        @Override
        public int getEnchantmentValue() {
            return 2;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(AnnoyingVillagersModItems.ELITE_OBSIDIAN.get());
        }
    };

    public EnderGlaiveItem() {
        super(TIER, 3, -2.5F, new Properties().fireResistant());
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!selected || !(entity instanceof Player player)) return;

        PlayerPatch<?> patch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (!(patch instanceof ServerPlayerPatch serverPatch)) return;

        SkillContainer skill = serverPatch.getSkill(AVSkills.ENDER_GLAIVE);
        if (skill != null && skill.getStack() >= 1) {
            HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }
    }

    public static void spawnVacuumSlice(ServerLevel level, LivingEntity owner) {
        spawnVacuumSlice(level, owner, DEFAULT_SPEED, DEFAULT_DOWN_ANGLE_DEGREES, DEFAULT_DAMAGE);
    }

    public static void spawnVacuumSlice(ServerLevel level, LivingEntity owner, float damage) {
        spawnVacuumSlice(level, owner, DEFAULT_SPEED, DEFAULT_DOWN_ANGLE_DEGREES, damage);
    }

    public static void spawnVacuumSlice(ServerLevel level, LivingEntity owner, double speed, double downwardAngleDegrees, float damage) {
        VacuumSliceEntity slice = AnnoyingVillagersModEntities.VACUUM_SLICE.get().create(level);
        if (slice == null) return;

        Vec3 horizontalDirection = getHorizontalDirection(owner);
        double angleRadians = Math.toRadians(Mth.clamp(downwardAngleDegrees, 0.0D, 89.0D));
        Vec3 velocity = horizontalDirection.scale(Math.cos(angleRadians) * speed)
                .add(0.0D, -Math.sin(angleRadians) * speed, 0.0D);
        Vec3 spawnPosition = owner.getBoundingBox().getCenter();

        slice.setOwner(owner);
        slice.captureWeaponEnchantments(owner.getMainHandItem());
        slice.setDamage(damage);
        slice.setPos(spawnPosition.x, spawnPosition.y, spawnPosition.z);
        slice.setDeltaMovement(velocity);
        setInitialRotation(slice, velocity);

        level.addFreshEntity(slice);
    }

    private static Vec3 getHorizontalDirection(LivingEntity owner) {
        Vec3 look = owner.getLookAngle();
        Vec3 horizontal = new Vec3(look.x, 0.0D, look.z);
        if (horizontal.lengthSqr() >= 1.0E-7D) return horizontal.normalize();

        float yaw = owner.getYRot() * Mth.DEG_TO_RAD;
        return new Vec3(-Mth.sin(yaw), 0.0D, Mth.cos(yaw));
    }

    private static void setInitialRotation(VacuumSliceEntity slice, Vec3 velocity) {
        double horizontalSpeed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        slice.setYRot((float) (Mth.atan2(-velocity.x, velocity.z) * Mth.RAD_TO_DEG));
        slice.setXRot((float) (Mth.atan2(-velocity.y, horizontalSpeed) * Mth.RAD_TO_DEG));
        slice.yRotO = slice.getYRot();
        slice.xRotO = slice.getXRot();
    }

    public static void spawnVacumSlise(ServerLevel level, LivingEntity owner) {
        spawnVacuumSlice(level, owner);
    }

    public static void spawnVacumSlise(ServerLevel level, LivingEntity owner, float damage) {
        spawnVacuumSlice(level, owner, damage);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.annoyingvillagers.ender_glaive"));
    }
}