package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.GolemArms;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DestructionEyeItem extends SwordItem {
    private static final String ARMS_UUID_TAG = "DestructionEyeGolemArms";
    private static final int USE_DURATION = 72_000;

    public DestructionEyeItem() {
        super(Tiers.IRON, 10, -3.0F, new Properties().stacksTo(1));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!(entity instanceof Player player) || !(level instanceof ServerLevel serverLevel) || player.getMainHandItem() != stack) return;
        getOrCreateArms(serverLevel, player, stack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) return InteractionResultHolder.pass(stack);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return USE_DURATION;
    }

    public static boolean isGuarding(Player player) {
        return player.isUsingItem() && player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getUseItem().getItem() instanceof DestructionEyeItem;
    }

    public static void damageForBlockedHit(Player player) {
        damageHeldEye(player, null);
    }

    public static void damageForArmsHit(GolemArms arms) {
        if (!(arms.getOwnerLiving() instanceof Player player)) return;
        damageHeldEye(player, arms);
    }

    private static void damageHeldEye(Player player, GolemArms expectedArms) {
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof DestructionEyeItem)) return;
        if (expectedArms != null && !isBoundTo(stack, expectedArms)) return;
        stack.hurtAndBreak(1, player, owner -> owner.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        if (stack.isEmpty() && player.isUsingItem()) player.stopUsingItem();
    }

    public static GolemArms getOrCreateArms(ServerLevel level, Player player, ItemStack stack) {
        GolemArms arms = findArms(level, stack);
        if (arms == null || !arms.isAlive() || arms.isRemoved()) {
            arms = AnnoyingVillagersModEntities.GOLEM_ARMS.get().create(level);
            if (arms == null) return null;
            arms.setOwner(player);
            arms.moveTo(player.getX(), player.getY(), player.getZ(), player.yBodyRot, player.getXRot());
            level.addFreshEntity(arms);
            stack.getOrCreateTag().putUUID(ARMS_UUID_TAG, arms.getUUID());
            return arms;
        }
        arms.setOwner(player);
        return arms;
    }

    public static GolemArms findArms(ServerLevel level, ItemStack stack) {
        if (!stack.hasTag() || stack.getTag() == null || !stack.getTag().hasUUID(ARMS_UUID_TAG)) return null;
        UUID uuid = stack.getTag().getUUID(ARMS_UUID_TAG);
        Entity entity = level.getEntity(uuid);
        return entity instanceof GolemArms arms ? arms : null;
    }

    public static boolean isBoundTo(ItemStack stack, GolemArms arms) {
        return stack.hasTag() && stack.getTag() != null && stack.getTag().hasUUID(ARMS_UUID_TAG) && stack.getTag().getUUID(ARMS_UUID_TAG).equals(arms.getUUID());
    }
}
