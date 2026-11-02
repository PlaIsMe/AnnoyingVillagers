package com.pla.annoyinglib.util;

import com.pla.annoyinglib.clazz.HookDisarmLaunch;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class HookSwordAbilityUtil {
    private HookSwordAbilityUtil() {}

    public static InteractionResultHolder<ItemStack> useHookSword(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(stack.getItem())) return InteractionResultHolder.pass(stack);
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_ONE_HANDED_UPPERCUT_RIGHT);
            LivingEntity target = VanillaWeaponAbilityUtil.performVanillaMeleeHit(player, 5.0D);
            if (target != null && level.random.nextBoolean()) {
                HookDisarmLaunch launch = level.random.nextBoolean() ? HookDisarmLaunch.LEFT : HookDisarmLaunch.RIGHT;
                CommonUtil.applyHookClashDisarmLogic(player, target, serverLevel, launch);
            }
            player.getCooldowns().addCooldown(stack.getItem(), 20 * 45);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
