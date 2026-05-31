package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BowFunction {
    public static void bowShoot(LivingEntityPatch<?> livingEntityPatch) {
        LivingEntity shooter = livingEntityPatch.getOriginal();
        Level level = shooter.level();

        if (level.isClientSide) return;

        ItemStack bowStack = shooter.getItemInHand(InteractionHand.MAIN_HAND);
        if (!(bowStack.getItem() instanceof BowItem bowItem)) {
            return;
        }

        if (shooter instanceof Player && !BowFunction.hasArrowOrInfinity(shooter, bowStack)) {
            return;
        }

        if (!bowStack.isEmpty() && bowStack.getTag() != null) {
            bowStack.getTag().putFloat("Pulling", 0.65F);
        }

        ItemStack arrowStack;
        boolean creativeOrInfinity;

        if (shooter instanceof Player player) {
            arrowStack = player.getProjectile(bowStack);
            creativeOrInfinity = player.getAbilities().instabuild ||
                    EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bowStack) > 0;
        } else {
            if ((shooter instanceof VillagerScoutEntity
                    || shooter instanceof RedVillagerGeneralEntity
                    || shooter instanceof BlueVillagerGeneralEntity
                    || shooter instanceof GreenVillagerGeneralEntity
                    || shooter instanceof PurpleVillagerGeneralEntity
                    || shooter instanceof VillagerScoutCaptainEntity) && ((AVNpc) shooter).getTarget() instanceof HerobrineMob){
                arrowStack = new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get());
            } else {
                arrowStack = new ItemStack(Items.ARROW);
            }
            creativeOrInfinity = true;
        }

        if (arrowStack.isEmpty() && !creativeOrInfinity) {
            return;
        }

        if (arrowStack.isEmpty()) {
            arrowStack = new ItemStack(Items.ARROW);
        }

        int charge = BowItem.MAX_DRAW_DURATION;
        float power = BowItem.getPowerForTime(charge);
        if (power < 0.1F) {
            return;
        }

        ArrowItem arrowItem = arrowStack.getItem() instanceof ArrowItem ai ? ai : (ArrowItem) Items.ARROW;
        AbstractArrow abstractArrow = arrowItem.createArrow(level, arrowStack, shooter);
        abstractArrow = bowItem.customArrow(abstractArrow);

        float xRot;
        float yRot;
        float arrowInaccuracy = 1.0F;

        if (!(shooter instanceof Player)) {
            LivingEntity target = livingEntityPatch.getTarget();

            if (target != null && target.isAlive()) {
                double distance = shooter.distanceTo(target);

                double horizontalSpread = 0.15D + distance * 0.03D;
                double verticalSpread = 0.05D + distance * 0.02D;

                double aimX = target.getX() + (level.getRandom().nextDouble() - 0.5D) * 2.0D * horizontalSpread;
                double aimY = target.getEyeY() + (level.getRandom().nextDouble() - 0.5D) * 2.0D * verticalSpread;
                double aimZ = target.getZ() + (level.getRandom().nextDouble() - 0.5D) * 2.0D * horizontalSpread;

                double dx = aimX - shooter.getX();
                double dz = aimZ - shooter.getZ();
                double dy = aimY - shooter.getEyeY();
                double horiz = Math.sqrt(dx * dx + dz * dz);

                yRot = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
                xRot = (float) (-(Mth.atan2(dy, horiz) * (180F / Math.PI)));
                xRot = Mth.clamp(xRot, -89.9F, 89.9F);

                shooter.setYRot(yRot);
                shooter.setXRot(xRot);
                shooter.setYBodyRot(yRot);
                shooter.setYHeadRot(yRot);

                arrowInaccuracy = 2.0F;
            } else {
                xRot = shooter.getXRot();
                yRot = shooter.getYRot();
                arrowInaccuracy = 2.0F;
            }
        } else {
            xRot = shooter.getXRot();
            yRot = shooter.getYRot();
        }

        abstractArrow.setOwner(shooter);
        abstractArrow.shootFromRotation(shooter, xRot, yRot, 0.0F, power * 3.0F, arrowInaccuracy);

        if (!bowStack.isEmpty() && bowStack.getTag() != null) {
            bowStack.getTag().remove("Pulling");
        }


        if (power == 1.0F) {
            abstractArrow.setCritArrow(true);
        }

        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bowStack);
        if (powerLevel > 0) {
            abstractArrow.setBaseDamage(
                    abstractArrow.getBaseDamage() + powerLevel * 0.5D + 0.5D
            );
        }

        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bowStack);
        if (punchLevel > 0) {
            abstractArrow.setKnockback(punchLevel);
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bowStack) > 0) {
            abstractArrow.setSecondsOnFire(100);
        }

        level.addFreshEntity(abstractArrow);
        if ((shooter instanceof VillagerScoutEntity
                || shooter instanceof VillagerScoutCaptainEntity) && ((AVNpc) shooter).getVoiceCooldown() == 0) {
            shooter.playSound(AnnoyingVillagersModSounds.VILLAGER_SCOUTS_SAY_ON_FIRE.get(), 0.5F, 1.0F);
            ((AVNpc) shooter).resetVoiceCooldown((Mob) shooter);
        }
        if ((shooter instanceof RedVillagerGeneralEntity
                || shooter instanceof BlueVillagerGeneralEntity
                || shooter instanceof GreenVillagerGeneralEntity
                || shooter instanceof PurpleVillagerGeneralEntity) && ((AVNpc) shooter).getVoiceCooldown() == 0) {
            shooter.playSound(AnnoyingVillagersModSounds.VILLAGER_GENERALS_SAY_ON_FIRE.get(), 0.5F, 1.0F);
            ((AVNpc) shooter).resetVoiceCooldown((Mob) shooter);
        }
        level.playSound(
                null,
                shooter.getX(), shooter.getY(), shooter.getZ(),
                SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS,
                1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F
        );

        if (shooter instanceof Player player && !player.getAbilities().instabuild) {
            boolean infiniteArrow = creativeOrInfinity ||
                    (arrowItem.isInfinite(arrowStack, bowStack, player)
                            && arrowStack.is(Items.ARROW));

            if (!infiniteArrow) {
                arrowStack.shrink(1);
                if (arrowStack.isEmpty()) {
                    player.getInventory().removeItem(arrowStack);
                }
            }

            bowStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            player.awardStat(Stats.ITEM_USED.get(bowItem));
        }
    }

    public static boolean hasArrowOrInfinity(LivingEntity entity, ItemStack bowStack) {
        if (!(entity instanceof Player player)) {
            return true;
        } else if (player.getAbilities().instabuild) {
            return true;
        }

        if (!(bowStack.getItem() instanceof BowItem)) {
            return false;
        }
        int infinityLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bowStack);
        boolean hasInfinity = infinityLevel > 0;

        ItemStack projectile = player.getProjectile(bowStack);
        boolean hasArrow = !projectile.isEmpty();

        return hasArrow || hasInfinity;
    }
}