package com.pla.annoyingvillagers.clazz;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class ThrowableSpearItem extends SwordItem {
    private static final int THROW_THRESHOLD_TIME = 10;
    private static final float SHOOT_POWER = 2.5F;

    protected ThrowableSpearItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    protected AbstractArrow createThrownProjectile(Level level, Player player, ItemStack stack) {
        return new ThrownTrident(level, player, stack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(stack);
        }

        if (EnchantmentHelper.getRiptide(stack) > 0 && !player.isInWaterOrRain()) {
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int timeLeft) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        int useTicks = this.getUseDuration(stack) - timeLeft;
        if (useTicks < THROW_THRESHOLD_TIME) {
            return;
        }

        int riptide = EnchantmentHelper.getRiptide(stack);
        if (riptide > 0 && !player.isInWaterOrRain()) {
            return;
        }

        if (riptide == 0) {
            playEpicFightShotAnimation(player);
        }

        if (!level.isClientSide()) {
            stack.hurtAndBreak(1, player, (owner) -> owner.broadcastBreakEvent(livingEntity.getUsedItemHand()));

            if (riptide == 0) {
                AbstractArrow thrownProjectile = this.createThrownProjectile(level, player, stack);
                thrownProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, SHOOT_POWER, 1.0F);

                if (player.getAbilities().instabuild) {
                    thrownProjectile.pickup = Pickup.CREATIVE_ONLY;
                }

                level.addFreshEntity(thrownProjectile);
                level.playSound(null, thrownProjectile, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);

                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(stack);
                }
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        if (riptide > 0) {
            launchRiptide(player, level, riptide);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack, @NotNull Enchantment enchantment) {
        return enchantment.category.canEnchant(Items.TRIDENT) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    private static void playEpicFightShotAnimation(Player player) {
        LivingEntityPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
        if (playerPatch != null) {
            playerPatch.playShootingAnimation();
        }
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
        player.startAutoSpinAttack(20);

        if (player.onGround()) {
            player.move(MoverType.SELF, new Vec3(0.0D, 1.1999999D, 0.0D));
        }

        SoundEvent soundEvent;
        if (riptide >= 3) {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_3;
        } else if (riptide == 2) {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_2;
        } else {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_1;
        }

        level.playSound(null, player, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
