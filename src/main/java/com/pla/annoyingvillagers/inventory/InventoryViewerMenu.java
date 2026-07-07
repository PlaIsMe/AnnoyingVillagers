package com.pla.annoyingvillagers.inventory;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryViewerMenu extends AbstractContainerMenu {
    public static final int IMAGE_WIDTH = 176;
    public static final int IMAGE_HEIGHT = 222;

    public static final int TOP_PANEL_HEIGHT = 60;
    public static final int CUSTOM_INVENTORY_X = 8;
    public static final int CUSTOM_INVENTORY_Y = 68;
    public static final int PLAYER_INVENTORY_X = 8;
    public static final int PLAYER_INVENTORY_Y = 140;
    public static final int HOTBAR_Y = 198;

    private static final int EQUIPMENT_SIZE = 6;
    private static final int CUSTOM_INVENTORY_SIZE = 27;
    private static final int CUSTOM_INVENTORY_END = EQUIPMENT_SIZE + CUSTOM_INVENTORY_SIZE;
    private static final int PLAYER_INVENTORY_END = CUSTOM_INVENTORY_END + 27;
    private static final int MENU_SLOT_COUNT = PLAYER_INVENTORY_END + 9;

    private static final EquipmentSlot[] EQUIPMENT_SLOTS = new EquipmentSlot[] {
            EquipmentSlot.MAINHAND,
            EquipmentSlot.OFFHAND,
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    private final LivingEntity target;

    public InventoryViewerMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, resolveTarget(playerInventory, data.readInt()));
    }

    public InventoryViewerMenu(int containerId, Inventory playerInventory, LivingEntity target, SimpleContainer npcInventory) {
        this(containerId, playerInventory, target, target == null ? new SimpleContainer(EQUIPMENT_SIZE) : new EquipmentContainer(target), npcInventory);
    }

    private InventoryViewerMenu(int containerId, Inventory playerInventory, LivingEntity target) {
        this(containerId, playerInventory, target, target == null ? new SimpleContainer(CUSTOM_INVENTORY_SIZE) : getNpcInventoryOrEmpty(target));
    }

    private InventoryViewerMenu(int containerId, Inventory playerInventory, LivingEntity target, Container equipmentInventory, SimpleContainer npcInventory) {
        super(AnnoyingVillagersModMenus.INVENTORY_VIEWER.get(), containerId);
        this.target = target;
        checkContainerSize(equipmentInventory, EQUIPMENT_SIZE);
        checkContainerSize(npcInventory, CUSTOM_INVENTORY_SIZE);

        addEquipmentSlot(equipmentInventory, 0, 80, 17);
        addEquipmentSlot(equipmentInventory, 1, 80, 35);
        addEquipmentSlot(equipmentInventory, 2, 44, 17);
        addEquipmentSlot(equipmentInventory, 3, 44, 35);
        addEquipmentSlot(equipmentInventory, 4, 116, 17);
        addEquipmentSlot(equipmentInventory, 5, 116, 35);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(npcInventory, column + row * 9, CUSTOM_INVENTORY_X + column * 18, CUSTOM_INVENTORY_Y + row * 18));
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9, PLAYER_INVENTORY_X + column * 18, PLAYER_INVENTORY_Y + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, PLAYER_INVENTORY_X + column * 18, HOTBAR_Y));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.target != null && this.target.isAlive() && player.distanceToSqr(this.target) <= 64.0D;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack copiedStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) {
            return copiedStack;
        }

        ItemStack stack = slot.getItem();
        copiedStack = stack.copy();
        if (index < CUSTOM_INVENTORY_END) {
            if (!this.moveItemStackTo(stack, CUSTOM_INVENTORY_END, MENU_SLOT_COUNT, true)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(stack, EQUIPMENT_SIZE, CUSTOM_INVENTORY_END, false)) {
            if (index < PLAYER_INVENTORY_END) {
                if (!this.moveItemStackTo(stack, PLAYER_INVENTORY_END, MENU_SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, CUSTOM_INVENTORY_END, PLAYER_INVENTORY_END, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == copiedStack.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return copiedStack;
    }

    private void addEquipmentSlot(Container equipmentInventory, int slotIndex, int x, int y) {
        addSlot(new EquipmentViewerSlot(equipmentInventory, slotIndex, x, y, EQUIPMENT_SLOTS[slotIndex]));
    }

    private static LivingEntity resolveTarget(Inventory playerInventory, int entityId) {
        Entity entity = playerInventory.player.level().getEntity(entityId);
        return entity instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    private static SimpleContainer getNpcInventoryOrEmpty(LivingEntity target) {
        if (target instanceof AVNpc avNpc) {
            return avNpc.getInventory();
        }
        return new SimpleContainer(CUSTOM_INVENTORY_SIZE);
    }

    private static class EquipmentViewerSlot extends Slot {
        private final EquipmentSlot equipmentSlot;

        private EquipmentViewerSlot(Container container, int slot, int x, int y, EquipmentSlot equipmentSlot) {
            super(container, slot, x, y);
            this.equipmentSlot = equipmentSlot;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            if (this.equipmentSlot == EquipmentSlot.MAINHAND || this.equipmentSlot == EquipmentSlot.OFFHAND) {
                return true;
            }
            return LivingEntity.getEquipmentSlotForItem(stack) == this.equipmentSlot;
        }
    }

    private static class EquipmentContainer extends SimpleContainer {
        private final LivingEntity target;

        private EquipmentContainer(LivingEntity target) {
            super(EQUIPMENT_SIZE);
            this.target = target;
            for (int slot = 0; slot < EQUIPMENT_SLOTS.length; slot++) {
                super.setItem(slot, target.getItemBySlot(EQUIPMENT_SLOTS[slot]));
            }
        }

        @Override
        public void setItem(int slot, @NotNull ItemStack stack) {
            super.setItem(slot, stack);
            syncSlot(slot);
        }

        @Override
        public @NotNull ItemStack removeItem(int slot, int count) {
            ItemStack removed = super.removeItem(slot, count);
            syncSlot(slot);
            return removed;
        }

        @Override
        public @NotNull ItemStack removeItemNoUpdate(int slot) {
            ItemStack removed = super.removeItemNoUpdate(slot);
            syncSlot(slot);
            return removed;
        }

        @Override
        public void clearContent() {
            super.clearContent();
            for (int slot = 0; slot < EQUIPMENT_SLOTS.length; slot++) {
                syncSlot(slot);
            }
        }

        @Override
        public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
            EquipmentSlot equipmentSlot = EQUIPMENT_SLOTS[slot];
            return equipmentSlot == EquipmentSlot.MAINHAND
                    || equipmentSlot == EquipmentSlot.OFFHAND
                    || LivingEntity.getEquipmentSlotForItem(stack) == equipmentSlot;
        }

        private void syncSlot(int slot) {
            if (slot >= 0 && slot < EQUIPMENT_SLOTS.length && !this.target.level().isClientSide()) {
                this.target.setItemSlot(EQUIPMENT_SLOTS[slot], getItem(slot));
            }
        }
    }
}
