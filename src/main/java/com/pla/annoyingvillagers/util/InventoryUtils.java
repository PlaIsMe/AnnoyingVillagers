package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class InventoryUtils {
    public static void transferInventory(SimpleContainer from, SimpleContainer to) {
        int size = Math.min(from.getContainerSize(), to.getContainerSize());
        for (int i = 0; i < size; i++) {
            ItemStack stack = from.getItem(i);
            if (!stack.isEmpty()) {
                to.setItem(i, stack.copy());
                from.setItem(i, ItemStack.EMPTY);
            }
        }
    }

    public static boolean isEmpty(SimpleContainer inventory) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!inventory.getItem(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean addItem(SimpleContainer inventory, ItemStack stack) {
        if (stack.isEmpty()) {
            return true;
        }

        ItemStack remaining = stack.copy();
        for (int i = 0; i < inventory.getContainerSize() && !remaining.isEmpty(); i++) {
            ItemStack slotStack = inventory.getItem(i);
            if (slotStack.isEmpty()
                    || !ItemStack.isSameItemSameTags(slotStack, remaining)
                    || slotStack.getCount() >= slotStack.getMaxStackSize()) {
                continue;
            }

            int transferable = Math.min(remaining.getCount(), slotStack.getMaxStackSize() - slotStack.getCount());
            slotStack.grow(transferable);
            remaining.shrink(transferable);
        }

        for (int i = 0; i < inventory.getContainerSize() && !remaining.isEmpty(); i++) {
            if (!inventory.getItem(i).isEmpty()) {
                continue;
            }

            ItemStack inserted = remaining.copy();
            inserted.setCount(Math.min(remaining.getCount(), remaining.getMaxStackSize()));
            inventory.setItem(i, inserted);
            remaining.shrink(inserted.getCount());
        }

        return remaining.isEmpty();
    }

    public static boolean addItem(Entity entity, ItemStack stack) {
        SimpleContainer inventory = getTrackedInventory(entity);
        return inventory != null && addItem(inventory, stack);
    }

    public static boolean hasItem(SimpleContainer inventory, ItemLike itemLike) {
        Item item = itemLike.asItem();
        return hasItem(inventory, stack -> stack.is(item));
    }

    public static boolean hasItem(Entity entity, ItemLike itemLike) {
        SimpleContainer inventory = getTrackedInventory(entity);
        return inventory != null && hasItem(inventory, itemLike);
    }

    public static boolean hasItem(SimpleContainer inventory, Predicate<ItemStack> matcher) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && matcher.test(stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasItem(Entity entity, Predicate<ItemStack> matcher) {
        SimpleContainer inventory = getTrackedInventory(entity);
        return inventory != null && hasItem(inventory, matcher);
    }

    public static Optional<ItemStack> consumeItem(SimpleContainer inventory, ItemLike itemLike, int count) {
        Item item = itemLike.asItem();
        return consumeItem(inventory, stack -> stack.is(item), count);
    }

    public static Optional<ItemStack> consumeItem(Entity entity, ItemLike itemLike, int count) {
        SimpleContainer inventory = getTrackedInventory(entity);
        if (inventory == null) {
            return Optional.empty();
        }
        return consumeItem(inventory, itemLike, count);
    }

    public static Optional<ItemStack> consumeItem(SimpleContainer inventory, Predicate<ItemStack> matcher, int count) {
        if (count <= 0) {
            return Optional.empty();
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || !matcher.test(stack)) {
                continue;
            }

            int consumedCount = Math.min(count, stack.getCount());
            ItemStack consumed = stack.copy();
            consumed.setCount(consumedCount);
            stack.shrink(consumedCount);
            if (stack.isEmpty()) {
                inventory.setItem(i, ItemStack.EMPTY);
            }
            return Optional.of(consumed);
        }

        return Optional.empty();
    }

    public static Optional<ItemStack> consumeItem(Entity entity, Predicate<ItemStack> matcher, int count) {
        SimpleContainer inventory = getTrackedInventory(entity);
        if (inventory == null) {
            return Optional.empty();
        }
        return consumeItem(inventory, matcher, count);
    }

    public static boolean hasArrowAmmo(Entity entity) {
        return hasItem(entity, InventoryUtils::isArrowStack);
    }

    public static Optional<ItemStack> consumeArrowAmmo(Entity entity) {
        return consumeItem(entity, InventoryUtils::isArrowStack, 1);
    }

    public static boolean hasPlaceableBlock(Entity entity) {
        return hasItem(entity, InventoryUtils::isPlaceableBlockStack);
    }

    public static Optional<ItemStack> peekPlaceableBlock(Entity entity) {
        return findItem(entity, InventoryUtils::isPlaceableBlockStack);
    }

    public static Optional<ItemStack> consumePlaceableBlock(Entity entity) {
        return consumeItem(entity, InventoryUtils::isPlaceableBlockStack, 1);
    }

    public static boolean hasHealingFood(Entity entity) {
        return hasItem(entity, InventoryUtils::isHealingFoodStack);
    }

    public static boolean isInventoryBackedSupplyDrop(ItemStack stack) {
        return !stack.isEmpty()
                && (isArrowStack(stack)
                || stack.is(Items.ENDER_PEARL)
                || stack.is(Items.BUCKET)
                || stack.is(Items.WATER_BUCKET)
                || stack.is(Items.LAVA_BUCKET)
                || isHealingFoodStack(stack)
                || isPlaceableBlockStack(stack)
                || isUtilityMaterialStack(stack)
                || stack.getItem() instanceof ThrowablePotionItem);
    }

    public static Optional<ItemStack> selectHealingFood(Entity entity, RandomSource random) {
        SimpleContainer inventory = getTrackedInventory(entity);
        if (inventory == null) {
            return Optional.empty();
        }

        List<ItemStack> enchanted = new ArrayList<>();
        List<ItemStack> golden = new ArrayList<>();
        List<ItemStack> regular = new ArrayList<>();

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (stack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
                enchanted.add(oneOf(stack));
            } else if (stack.is(Items.GOLDEN_APPLE)) {
                golden.add(oneOf(stack));
            } else if (isRegularFoodStack(stack)) {
                regular.add(oneOf(stack));
            }
        }

        if (!enchanted.isEmpty() && random.nextFloat() < 0.12F) {
            return Optional.of(enchanted.get(random.nextInt(enchanted.size())));
        }
        if (!golden.isEmpty() && (regular.isEmpty() || random.nextFloat() < 0.45F)) {
            return Optional.of(golden.get(random.nextInt(golden.size())));
        }
        if (!regular.isEmpty()) {
            return Optional.of(regular.get(random.nextInt(regular.size())));
        }
        if (!golden.isEmpty()) {
            return Optional.of(golden.get(random.nextInt(golden.size())));
        }
        if (!enchanted.isEmpty()) {
            return Optional.of(enchanted.get(random.nextInt(enchanted.size())));
        }

        return Optional.empty();
    }

    public static boolean consumeHealingFood(Entity entity, ItemStack foodStack) {
        if (foodStack.isEmpty()) {
            return false;
        }
        return consumeItem(entity, stack -> ItemStack.isSameItemSameTags(stack, foodStack), 1).isPresent();
    }

    public static BlockState getBlockState(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            return blockItem.getBlock().defaultBlockState();
        }
        return null;
    }

    private static Optional<ItemStack> findItem(Entity entity, Predicate<ItemStack> matcher) {
        SimpleContainer inventory = getTrackedInventory(entity);
        if (inventory == null) {
            return Optional.empty();
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && matcher.test(stack)) {
                return Optional.of(oneOf(stack));
            }
        }
        return Optional.empty();
    }

    private static boolean isArrowStack(ItemStack stack) {
        return stack.getItem() instanceof ArrowItem;
    }

    private static boolean isPlaceableBlockStack(ItemStack stack) {
        return stack.getItem() instanceof BlockItem;
    }

    private static boolean isHealingFoodStack(ItemStack stack) {
        return stack.is(Items.GOLDEN_APPLE)
                || stack.is(Items.ENCHANTED_GOLDEN_APPLE)
                || isRegularFoodStack(stack);
    }

    private static boolean isUtilityMaterialStack(ItemStack stack) {
        return stack.is(Items.EMERALD)
                || stack.is(Items.GOLD_INGOT)
                || stack.is(Items.IRON_INGOT)
                || stack.is(Items.DIAMOND)
                || stack.is(Items.COAL)
                || stack.is(Items.REDSTONE)
                || stack.is(Items.LAPIS_LAZULI)
                || stack.is(Items.WHEAT)
                || stack.is(Items.STICK)
                || stack.is(Items.BONE_MEAL);
    }

    private static boolean isRegularFoodStack(ItemStack stack) {
        return !stack.is(Items.GOLDEN_APPLE)
                && !stack.is(Items.ENCHANTED_GOLDEN_APPLE)
                && stack.isEdible();
    }

    private static ItemStack oneOf(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setCount(1);
        return copy;
    }

    private static SimpleContainer getTrackedInventory(Entity entity) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            return playerNpcEntity.getInventory();
        }
        if (entity instanceof AVNpc avNpc) {
            return avNpc.getInventory();
        }
        return null;
    }
}
