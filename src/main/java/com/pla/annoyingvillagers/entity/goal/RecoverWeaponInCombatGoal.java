package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;

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

import java.util.EnumSet;
import java.util.List;

public class RecoverWeaponInCombatGoal extends Goal {
    private final Mob mob;
    private final double speed;
    private final double searchRadius;

    private ItemEntity targetItem;
    private int inventoryWeaponSlot = -1;
    private boolean restoreCachedWeapon;
    private boolean finished;

    private LivingEntity savedCombatTarget;
    private int lockTicks;
    private int repathCooldown;

    private static final int MAX_LOCK_TICKS = 60;
    private static final double PICKUP_DISTANCE_SQR = 2.4D * 2.4D;

    public RecoverWeaponInCombatGoal(Mob mob, double speed, double searchRadius) {
        this.mob = mob;
        this.speed = speed;
        this.searchRadius = searchRadius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (mob.level().isClientSide) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (mob.isNoAi()) return false;

        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (!mainWeaponIsEmpty()) {
            return false;
        }

        restoreCachedWeapon = !getCachedMainWeapon().isEmpty();
        if (restoreCachedWeapon) {
            targetItem = null;
            inventoryWeaponSlot = -1;
            return true;
        }

        inventoryWeaponSlot = findWeaponSlotInNpcInventory();
        if (inventoryWeaponSlot >= 0) {
            targetItem = null;
            return true;
        }

        targetItem = findNearestWeaponItem();
        return targetItem != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (finished) return false;

        if (mob.level().isClientSide) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (mob.isNoAi()) return false;

        if (!mainWeaponIsEmpty()) {
            return false;
        }

        if (targetItem == null || !targetItem.isAlive() || targetItem.getItem().isEmpty()) {
            return false;
        }

        return lockTicks < MAX_LOCK_TICKS;
    }

    @Override
    public void start() {
        this.savedCombatTarget = mob.getTarget();
        this.lockTicks = 0;
        this.repathCooldown = 0;
        this.finished = false;

        if (restoreCachedWeapon && restoreCachedMainWeapon()) {
            this.finished = true;
            this.targetItem = null;
            this.restoreCachedWeapon = false;
            return;
        }

        if (inventoryWeaponSlot >= 0 && tryEquipWeaponFromInventory(inventoryWeaponSlot)) {
            this.finished = true;
            this.targetItem = null;
            return;
        }

        inventoryWeaponSlot = -1;

        if (targetItem == null || !targetItem.isAlive() || targetItem.getItem().isEmpty()) {
            targetItem = findNearestWeaponItem();
        }

        if (targetItem != null) {
            mob.setTarget(null);
            mob.getNavigation().stop();
            mob.getNavigation().moveTo(targetItem, speed);
        }
    }

    private int findWeaponSlotInNpcInventory() {
        SimpleContainer inventory = getNpcInventory();

        if (inventory == null) {
            return -1;
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            if (!stack.isEmpty() && isUsefulWeapon(stack)) {
                return i;
            }
        }

        return -1;
    }

    private boolean tryEquipWeaponFromInventory(int slot) {
        SimpleContainer inventory = getNpcInventory();

        if (inventory == null) {
            return false;
        }
        if (slot < 0 || slot >= inventory.getContainerSize()) {
            return false;
        }
        if (!mob.getMainHandItem().isEmpty()) {
            return false;
        }
        ItemStack slotStack = inventory.getItem(slot);
        if (slotStack.isEmpty() || !isUsefulWeapon(slotStack)) {
            return false;
        }
        ItemStack equipStack = slotStack.split(1);
        if (slotStack.isEmpty()) {
            inventory.setItem(slot, ItemStack.EMPTY);
        } else {
            inventory.setItem(slot, slotStack);
        }
        inventory.setChanged();
        return equipRecoveredWeapon(equipStack);
    }

    private boolean equipRecoveredWeapon(ItemStack equipStack) {
        if (equipStack.isEmpty() || !isUsefulWeapon(equipStack)) {
            return false;
        }

        equipStack.setCount(1);
        mob.setItemSlot(EquipmentSlot.MAINHAND, equipStack.copy());
        if (mob instanceof AVNpc avNpc) {
            avNpc.setMainWeaponItem(equipStack.copy());
            avNpc.setMainWeaponDisarmed(false);
        }

        mob.swing(InteractionHand.MAIN_HAND);
        mob.level().playSound(
                null,
                mob.blockPosition(),
                SoundEvents.ITEM_PICKUP,
                SoundSource.HOSTILE,
                0.35F,
                1.0F
        );

        return true;
    }

    private boolean restoreCachedMainWeapon() {
        ItemStack weapon = getCachedMainWeapon();

        if (weapon.isEmpty()) {
            return false;
        }

        mob.setItemSlot(EquipmentSlot.MAINHAND, weapon.copy());

        if (mob instanceof AVNpc avNpc) {
            avNpc.setMainWeaponItem(weapon.copy());
            avNpc.setMainWeaponDisarmed(false);
        }

        return true;
    }

    private SimpleContainer getNpcInventory() {
        if (mob instanceof AVNpc avNpc) {
            return avNpc.getInventory();
        }

        return null;
    }

    @Override
    public void tick() {
        lockTicks++;

        if (targetItem == null || !targetItem.isAlive() || targetItem.getItem().isEmpty()) {
            return;
        }

        mob.setTarget(null);

        mob.getLookControl().setLookAt(
                targetItem.getX(),
                targetItem.getY() + targetItem.getBbHeight() * 0.5D,
                targetItem.getZ(),
                60.0F,
                60.0F
        );

        if (mob.distanceToSqr(targetItem) <= PICKUP_DISTANCE_SQR) {
            if (forceEquipWeaponFromItemEntity(targetItem)) {
                finished = true;
            }
            targetItem = null;
            return;
        }

        if (repathCooldown-- <= 0 || mob.getNavigation().isDone()) {
            repathCooldown = 4;

            mob.getNavigation().moveTo(
                    targetItem.getX(),
                    targetItem.getY(),
                    targetItem.getZ(),
                    speed
            );
        }
    }

    private boolean forceEquipWeaponFromItemEntity(ItemEntity itemEntity) {
        if (itemEntity == null || !itemEntity.isAlive()) {
            return false;
        }
        if (!mob.getMainHandItem().isEmpty()) {
            return false;
        }
        ItemStack groundStack = itemEntity.getItem();
        if (groundStack.isEmpty() || !isUsefulWeapon(groundStack)) {
            return false;
        }
        ItemStack equipStack = groundStack.split(1);
        if (groundStack.isEmpty()) {
            itemEntity.discard();
        } else {
            itemEntity.setItem(groundStack);
        }
        return equipRecoveredWeapon(equipStack);
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();

        if (savedCombatTarget != null && savedCombatTarget.isAlive()) {
            mob.setTarget(savedCombatTarget);
        }

        savedCombatTarget = null;
        targetItem = null;
        inventoryWeaponSlot = -1;
        lockTicks = 0;
        repathCooldown = 0;
        restoreCachedWeapon = false;
        finished = false;
    }

    private ItemEntity findNearestWeaponItem() {
        List<ItemEntity> items = mob.level().getEntitiesOfClass(
                ItemEntity.class,
                mob.getBoundingBox().inflate(searchRadius),
                itemEntity -> itemEntity.isAlive()
                        && !itemEntity.getItem().isEmpty()
                        && isUsefulWeapon(itemEntity.getItem())
        );

        if (items.isEmpty()) {
            return null;
        }

        ItemEntity best = null;
        double bestDistance = Double.MAX_VALUE;

        for (ItemEntity itemEntity : items) {
            double distance = mob.distanceToSqr(itemEntity);

            if (distance < bestDistance) {
                bestDistance = distance;
                best = itemEntity;
            }
        }

        return best;
    }

    private boolean mainWeaponIsEmpty() {
        return mob.getMainHandItem().isEmpty()
                || isFlintAndSteel(mob.getMainHandItem());
    }

    private ItemStack getCachedMainWeapon() {
        if (mob instanceof AVNpc avNpc) {
            return avNpc.getMainWeaponItem();
        }

        return ItemStack.EMPTY;
    }

    private boolean isFlintAndSteel(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.FLINT_AND_STEEL;
    }

    private boolean isUsefulWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();

        return item instanceof SwordItem
                || item instanceof DiggerItem
                || item instanceof TridentItem;
    }
}
