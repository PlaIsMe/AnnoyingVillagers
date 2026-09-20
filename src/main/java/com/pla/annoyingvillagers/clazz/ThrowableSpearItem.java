package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.item.LegacyTier;
import com.pla.annoyingvillagers.util.EnchantmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow.Pickup;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ThrowableSpearItem extends Item {
    private static final int THROW_THRESHOLD_TIME = 10;
    private static final float SHOOT_POWER = 2.5F;

    protected ThrowableSpearItem(ToolMaterial material, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(properties.sword(material, attackDamageModifier, attackSpeedModifier));
    }

    protected ThrowableSpearItem(LegacyTier material, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(material.applySwordProperties(properties, attackDamageModifier, attackSpeedModifier));
    }

    protected AbstractArrow createThrownProjectile(Level level, Player player, ItemStack stack) {
        return new ThrownTrident(level, player, stack);
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
        return ItemUseAnimation.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return InteractionResult.FAIL;
        }

        if (EnchantmentUtil.getLevel(Enchantments.RIPTIDE, stack) > 0 && !player.isInWaterOrRain()) {
            return InteractionResult.FAIL;
        }

        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int timeLeft) {
        if (!(livingEntity instanceof Player player)) {
            return false;
        }

        int useTicks = this.getUseDuration(stack, livingEntity) - timeLeft;
        if (useTicks < THROW_THRESHOLD_TIME) {
            return false;
        }

        int riptide = EnchantmentUtil.getLevel(Enchantments.RIPTIDE, stack);
        if (riptide > 0 && !player.isInWaterOrRain()) {
            return false;
        }

        if (riptide == 0) {
            playEpicFightShotAnimation(player);
        }

        if (!level.isClientSide()) {
            stack.hurtAndBreak(1, player, livingEntity.getUsedItemHand().asEquipmentSlot());

            if (riptide == 0) {
                AbstractArrow thrownProjectile = this.createThrownProjectile(level, player, stack);
                thrownProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, SHOOT_POWER, 1.0F);

                if (player.getAbilities().instabuild) {
                    thrownProjectile.pickup = Pickup.CREATIVE_ONLY;
                }

                level.addFreshEntity(thrownProjectile);
                level.playSound(null, thrownProjectile, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);

                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(stack);
                }
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        if (riptide > 0) {
            launchRiptide(player, level, riptide);
        }
        return true;
    }

    @Override
    public boolean supportsEnchantment(@NotNull ItemStack stack, @NotNull Holder<Enchantment> enchantment) {
        return enchantment.value().isSupportedItem(new ItemStack(Items.TRIDENT)) || super.supportsEnchantment(stack, enchantment);
    }

    private static void playEpicFightShotAnimation(Player player) {
//        ADD THIS CODE IN AV_EFM

//        LivingEntityPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
//        if (playerPatch != null) {
//            playerPatch.playShootingAnimation();
//        }

//         Create VANILLA_ANIMATION
    }

    private static void launchRiptide(Player player, Level level, int riptide) {
        float yRot = player.getYRot();
        float xRot = player.getXRot();
        float motionX = -Mth.sin(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
        float motionY = -Mth.sin(xRot * Mth.DEG_TO_RAD);
        float motionZ = Mth.cos(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
        float motionLength = Mth.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        float riptideStrength = 3.0F * (1.0F + (float) riptide) / 4.0F;

        motionX *= riptideStrength / motionLength;
        motionY *= riptideStrength / motionLength;
        motionZ *= riptideStrength / motionLength;

        player.push(motionX, motionY, motionZ);
        player.startAutoSpinAttack(20, 8.0F, player.getUseItem());

        if (player.onGround()) {
            player.move(MoverType.SELF, new Vec3(0.0D, 1.1999999D, 0.0D));
        }

        SoundEvent soundEvent;
        if (riptide >= 3) {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_3.value();
        } else if (riptide == 2) {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_2.value();
        } else {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_1.value();
        }

        level.playSound(null, player, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
