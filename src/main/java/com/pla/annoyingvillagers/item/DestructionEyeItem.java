package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.entity.GolemArms;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DestructionEyeItem extends LegacySwordItem {
    private static final String ARMS_UUID_TAG = "DestructionEyeGolemArms";
    private static final int USE_DURATION = 72_000;

    public DestructionEyeItem() {
        super(ToolMaterial.IRON, 10, -3.0F, com.pla.annoyingvillagers.util.LegacyItemProperties.create().stacksTo(1));
    }

    @Override
    public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slotId = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean isSelected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        super.inventoryTick(stack, level, entity, equipmentSlot);
        if (!(entity instanceof Player player) || !(level instanceof ServerLevel serverLevel) || player.getMainHandItem() != stack) return;
        getOrCreateArms(serverLevel, player, stack);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
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
        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        if (stack.isEmpty() && player.isUsingItem()) player.stopUsingItem();
    }

    public static GolemArms getOrCreateArms(ServerLevel level, Player player, ItemStack stack) {
        GolemArms arms = findArms(level, stack);
        if (arms == null || !arms.isAlive() || arms.isRemoved()) {
            arms = AnnoyingVillagersModEntities.GOLEM_ARMS.get().create(level, net.minecraft.world.entity.EntitySpawnReason.TRIGGERED);
            if (arms == null) return null;
            arms.setOwner(player);
            arms.snapTo(player.getX(), player.getY(), player.getZ(), player.yBodyRot, player.getXRot());
            level.addFreshEntity(arms);
            UUID armsId = arms.getUUID();
            LegacyItemData.update(stack, tag -> com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, ARMS_UUID_TAG, armsId));
            return arms;
        }
        arms.setOwner(player);
        return arms;
    }

    public static GolemArms findArms(ServerLevel level, ItemStack stack) {
        if (!LegacyItemData.has(stack) || LegacyItemData.get(stack) == null || !com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(LegacyItemData.get(stack), ARMS_UUID_TAG)) return null;
        UUID uuid = com.pla.annoyingvillagers.util.LegacyNbt.getUUID(LegacyItemData.get(stack), ARMS_UUID_TAG);
        Entity entity = level.getEntity(uuid);
        return entity instanceof GolemArms arms ? arms : null;
    }

    public static boolean isBoundTo(ItemStack stack, GolemArms arms) {
        return LegacyItemData.has(stack) && LegacyItemData.get(stack) != null
                && com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(LegacyItemData.get(stack), ARMS_UUID_TAG)
                && com.pla.annoyingvillagers.util.LegacyNbt.getUUID(LegacyItemData.get(stack), ARMS_UUID_TAG).equals(arms.getUUID());
    }
}
