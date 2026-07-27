package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BurnNearbyItemGoal extends Goal {
    private static final int BURN_COOLDOWN_TICKS = 80;

    private final Mob mob;
    private final double speed;
    private final double searchRadius;
    private ItemEntity targetItem;
    private ItemStack burnToolRestoreItem = ItemStack.EMPTY;
    private boolean equippedBurnTool;
    private long nextBurnTick;

    private static List<String> keys(String prefix, int count) {
        List<String> list = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            list.add(prefix + "." + i);
        }
        return List.copyOf(list);
    }

    private static final List<String> burnMessageKeys = keys("burn_item.annoyingvillagers", 56);

    public BurnNearbyItemGoal(Mob mob, double speed, double searchRadius) {
        this.mob = mob;
        this.speed = speed;
        this.searchRadius = searchRadius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (mob.getTarget() != null) return false;
        if (mob.isNoAi()) return false;
        if (!AnnoyingVillagersConfig.AV_MOB_CAN_BURN_ITEM.get()) return false;
        if (mob instanceof AVNpc avNpc && avNpc.isHealing()) {
            return false;
        }
        if (serverLevel.getGameTime() < nextBurnTick) return false;

        targetItem = findTargetItem(serverLevel);
        return targetItem != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.level().isClientSide) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (mob.getTarget() != null) return false;
        if (mob.isNoAi()) return false;
        if (!AnnoyingVillagersConfig.AV_MOB_CAN_BURN_ITEM.get()) return false;

        return targetItem != null
                && targetItem.isAlive()
                && !targetItem.getItem().isEmpty()
                && isGroundItem(targetItem);
    }

    @Override
    public void start() {
        burnToolRestoreItem = ItemStack.EMPTY;
        equippedBurnTool = false;

        if (targetItem == null) {
            return;
        }

        if (shouldPickupOrEquipInsteadOfBurn(targetItem.getItem())) {
            restoreMainWeapon(false);
        } else {
            equipFlintAndSteel();
        }

        mob.getNavigation().moveTo(targetItem, speed);
    }

    @Override
    public void tick() {
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        if (targetItem == null || !targetItem.isAlive() || targetItem.getItem().isEmpty()) {
            return;
        }

        if (shouldPickupOrEquipInsteadOfBurn(targetItem.getItem())) {
            restoreMainWeapon(false);
        } else {
            equipFlintAndSteel();
        }

        if (mob.getNavigation().isDone()) {
            var path = mob.getNavigation().createPath(targetItem, 0);

            if (path == null) {
                return;
            }

            mob.getNavigation().moveTo(targetItem, speed);
        }

        mob.getLookControl().setLookAt(
                targetItem.getX(),
                targetItem.getY() + targetItem.getBbHeight() / 2.0,
                targetItem.getZ(),
                30.0F, 30.0F
        );

        double dist = mob.distanceTo(targetItem);

        if (dist <= 1.5D) {
            if (shouldPickupOrEquipInsteadOfBurn(targetItem.getItem())) {
                if (tryHandleItemWithoutBurning(targetItem)) {
                    targetItem = null;
                    mob.getNavigation().stop();
                    return;
                }
            }

            equipFlintAndSteel();

            ItemStack burnedStack = targetItem.getItem().copy();
            BlockPos firePos = targetItem.blockPosition();

            if (tryPlaceFireAtItem(serverLevel, targetItem)) {
                mob.swing(InteractionHand.MAIN_HAND);

                serverLevel.sendParticles(
                        ParticleTypes.FLAME,
                        firePos.getX() + 0.5D, firePos.getY() + 0.5D, firePos.getZ() + 0.5D,
                        8, 0.2, 0.2, 0.2, 0.01
                );

                mob.level().playSound(
                        null,
                        firePos,
                        SoundEvents.FLINTANDSTEEL_USE,
                        SoundSource.HOSTILE,
                        1.0f,
                        1.0f
                );

                tryBroadcastBurnMessage(serverLevel, burnedStack);
                nextBurnTick = serverLevel.getGameTime() + BURN_COOLDOWN_TICKS;
            }

            targetItem = null;
            mob.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        boolean shouldRestoreBurnTool = equippedBurnTool || isFlintAndSteel(mob.getMainHandItem());

        targetItem = null;
        mob.getNavigation().stop();

        if (shouldRestoreBurnTool) {
            restoreMainWeapon(true);
        }

        burnToolRestoreItem = ItemStack.EMPTY;
        equippedBurnTool = false;
    }

    private void tryBroadcastBurnMessage(ServerLevel serverLevel, ItemStack burnedStack) {
        if (!AnnoyingVillagersConfig.TURN_ON_NPC_CHAT.get()) return;
        if (mob.getRandom().nextFloat() >= 0.05F) return;

        String key = burnMessageKeys.get(mob.getRandom().nextInt(burnMessageKeys.size()));

        serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                Component.empty()
                        .append(Component.literal("<"))
                        .append(mob.getDisplayName())
                        .append(Component.literal("> "))
                        .append(Component.translatable(key, burnedStack.getHoverName())),
                false
        );
    }

    private void restoreMainWeapon(boolean addIdleCooldown) {
        ItemStack weapon = getCachedMainWeapon();

        if (mob instanceof AVNpc avNpc) {
            if (addIdleCooldown) {
                avNpc.setPlayingIdleCooldown(avNpc.getPlayingIdleCooldown() + 40);
            }
        }

        if ((weapon == null || weapon.isEmpty()) && !burnToolRestoreItem.isEmpty()) {
            weapon = burnToolRestoreItem;
        }

        if (weapon != null && !weapon.isEmpty()) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, weapon.copy());
            cacheMainWeapon(weapon);
        } else if (isFlintAndSteel(mob.getMainHandItem())) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    private ItemEntity findTargetItem(ServerLevel serverLevel) {
        List<ItemEntity> items = mob.level().getEntitiesOfClass(
                ItemEntity.class,
                mob.getBoundingBox().inflate(searchRadius),
                e -> e.isAlive()
                        && !e.getItem().isEmpty()
                        && isGroundItem(e)
                        && (shouldPickupOrEquipInsteadOfBurn(e.getItem()) || canPlaceFireAtItem(serverLevel, e))
        );

        if (items.isEmpty()) return null;
        ItemEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (ItemEntity it : items) {
            double d = mob.distanceToSqr(it);
            if (d < bestDist) {
                bestDist = d;
                best = it;
            }
        }
        return best;
    }

    private boolean isGroundItem(ItemEntity itemEntity) {
        return itemEntity != null && itemEntity.onGround();
    }

    private boolean canPlaceFireAtItem(ServerLevel serverLevel, ItemEntity itemEntity) {
        if (!isGroundItem(itemEntity)) {
            return false;
        }

        return BaseFireBlock.canBePlacedAt(serverLevel, itemEntity.blockPosition(), mob.getDirection());
    }

    private boolean tryPlaceFireAtItem(ServerLevel serverLevel, ItemEntity itemEntity) {
        if (!canPlaceFireAtItem(serverLevel, itemEntity)) {
            return false;
        }

        BlockPos firePos = itemEntity.blockPosition();
        serverLevel.setBlockAndUpdate(firePos, BaseFireBlock.getState(serverLevel, firePos));
        serverLevel.gameEvent(mob, GameEvent.BLOCK_PLACE, firePos);
        return true;
    }

    private void equipFlintAndSteel() {
        rememberRestoreItemBeforeBurnTool();
        equippedBurnTool = true;

        if (!isFlintAndSteel(mob.getMainHandItem())) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FLINT_AND_STEEL));
        }
    }

    private boolean shouldPickupOrEquipInsteadOfBurn(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        if (npcInventoryCanAccept(stack)) {
            return true;
        }

        if (mainWeaponIsEmpty() && isUsefulWeapon(stack)) {
            return true;
        }

        return emptyArmorSlotCanUse(stack);
    }

    private boolean tryHandleItemWithoutBurning(ItemEntity itemEntity) {
        if (itemEntity == null || !itemEntity.isAlive() || itemEntity.getItem().isEmpty()) {
            return false;
        }

        if (mainWeaponIsEmpty() && isUsefulWeapon(itemEntity.getItem())) {
            return tryEquipWeaponFromGround(itemEntity);
        }

        if (emptyArmorSlotCanUse(itemEntity.getItem())) {
            return tryEquipArmorFromGround(itemEntity);
        }

        if (npcInventoryCanAccept(itemEntity.getItem())) {
            return tryInsertIntoNpcInventory(itemEntity);
        }

        return false;
    }

    private boolean tryEquipWeaponFromGround(ItemEntity itemEntity) {
        ItemStack groundStack = itemEntity.getItem();

        if (groundStack.isEmpty() || !isUsefulWeapon(groundStack)) {
            return false;
        }

        ItemStack equipStack = groundStack.copy();
        equipStack.setCount(1);

        mob.setItemSlot(EquipmentSlot.MAINHAND, equipStack.copy());

        if (mob instanceof AVNpc avNpc) {
            avNpc.setMainWeaponItem(equipStack.copy());
        }

        groundStack.shrink(1);

        if (groundStack.isEmpty()) {
            itemEntity.discard();
        } else {
            itemEntity.setItem(groundStack);
        }

        mob.swing(InteractionHand.MAIN_HAND);

        mob.level().playSound(
                null,
                mob.blockPosition(),
                SoundEvents.ITEM_PICKUP,
                SoundSource.HOSTILE,
                0.2F,
                1.0F
        );

        return true;
    }

    private boolean tryEquipArmorFromGround(ItemEntity itemEntity) {
        ItemStack groundStack = itemEntity.getItem();

        if (groundStack.isEmpty()) {
            return false;
        }

        EquipmentSlot slot = LivingEntity.getEquipmentSlotForItem(groundStack);

        if (slot.getType() != EquipmentSlot.Type.ARMOR) {
            return false;
        }

        if (!mob.getItemBySlot(slot).isEmpty()) {
            return false;
        }

        ItemStack equipStack = groundStack.copy();
        equipStack.setCount(1);

        mob.setItemSlot(slot, equipStack.copy());

        groundStack.shrink(1);

        if (groundStack.isEmpty()) {
            itemEntity.discard();
        } else {
            itemEntity.setItem(groundStack);
        }

        mob.swing(InteractionHand.MAIN_HAND);

        mob.level().playSound(
                null,
                mob.blockPosition(),
                SoundEvents.ITEM_PICKUP,
                SoundSource.HOSTILE,
                0.2F,
                1.0F
        );

        return true;
    }

    private boolean tryInsertIntoNpcInventory(ItemEntity itemEntity) {
        SimpleContainer inventory = getNpcInventory();

        if (inventory == null || itemEntity == null || itemEntity.getItem().isEmpty()) {
            return false;
        }

        ItemStack remaining = itemEntity.getItem().copy();
        int originalCount = remaining.getCount();

        for (int i = 0; i < inventory.getContainerSize() && !remaining.isEmpty(); i++) {
            ItemStack slotStack = inventory.getItem(i);

            if (slotStack.isEmpty()) {
                int transferable = Math.min(
                        remaining.getCount(),
                        Math.min(remaining.getMaxStackSize(), inventory.getMaxStackSize())
                );

                ItemStack inserted = remaining.copy();
                inserted.setCount(transferable);

                inventory.setItem(i, inserted);
                remaining.shrink(transferable);
            } else if (ItemStack.isSameItemSameTags(slotStack, remaining)
                    && slotStack.getCount() < slotStack.getMaxStackSize()) {
                int transferable = Math.min(
                        remaining.getCount(),
                        slotStack.getMaxStackSize() - slotStack.getCount()
                );

                slotStack.grow(transferable);
                remaining.shrink(transferable);
            }
        }

        if (remaining.getCount() == originalCount) {
            return false;
        }

        inventory.setChanged();

        if (remaining.isEmpty()) {
            itemEntity.discard();
        } else {
            itemEntity.setItem(remaining);
        }

        mob.swing(InteractionHand.MAIN_HAND);

        mob.level().playSound(
                null,
                mob.blockPosition(),
                SoundEvents.ITEM_PICKUP,
                SoundSource.HOSTILE,
                0.2F,
                1.0F
        );

        return true;
    }

    private boolean npcInventoryCanAccept(ItemStack incoming) {
        SimpleContainer inventory = getNpcInventory();

        if (inventory == null || incoming.isEmpty()) {
            return false;
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack slotStack = inventory.getItem(i);

            if (slotStack.isEmpty()) {
                return true;
            }

            if (ItemStack.isSameItemSameTags(slotStack, incoming)
                    && slotStack.getCount() < slotStack.getMaxStackSize()) {
                return true;
            }
        }

        return false;
    }

    private SimpleContainer getNpcInventory() {
        if (mob instanceof AVNpc avNpc) {
            return avNpc.getInventory();
        }

        return null;
    }

    private ItemStack getCachedMainWeapon() {
        if (mob instanceof AVNpc avNpc) {
            return avNpc.getMainWeaponItem();
        }

        return ItemStack.EMPTY;
    }

    private void cacheMainWeapon(ItemStack weapon) {
        if (weapon == null || weapon.isEmpty()) {
            return;
        }

        if (mob instanceof AVNpc avNpc) {
            avNpc.setMainWeaponItem(weapon.copy());
        }
    }

    private void rememberRestoreItemBeforeBurnTool() {
        if (!burnToolRestoreItem.isEmpty()) {
            return;
        }

        ItemStack cachedWeapon = getCachedMainWeapon();
        if (!cachedWeapon.isEmpty()) {
            burnToolRestoreItem = cachedWeapon.copy();
            return;
        }

        ItemStack currentMainHand = mob.getMainHandItem();
        if (!currentMainHand.isEmpty() && !isFlintAndSteel(currentMainHand) && isUsefulWeapon(currentMainHand)) {
            burnToolRestoreItem = currentMainHand.copy();
            cacheMainWeapon(currentMainHand);
        }
    }

    private boolean mainWeaponIsEmpty() {
        if (!getCachedMainWeapon().isEmpty()) {
            return false;
        }

        return mob.getMainHandItem().isEmpty()
                || isFlintAndSteel(mob.getMainHandItem());
    }

    private boolean isFlintAndSteel(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.FLINT_AND_STEEL;
    }

    private boolean isUsefulWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return stack.getItem() instanceof SwordItem
                || stack.getItem() instanceof AxeItem
                || stack.getItem() instanceof DiggerItem
                || stack.getItem() instanceof TridentItem
                || stack.getItem() instanceof BowItem
                || stack.getItem() instanceof CrossbowItem;
    }

    private boolean emptyArmorSlotCanUse(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        EquipmentSlot slot = LivingEntity.getEquipmentSlotForItem(stack);

        if (slot.getType() != EquipmentSlot.Type.ARMOR) {
            return false;
        }

        return mob.getItemBySlot(slot).isEmpty();
    }
}
