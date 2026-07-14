package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.util.InventoryUtils;
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
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BurnNearbyItemGoal extends Goal {
    private static final int BURN_COOLDOWN_TICKS = 80;
    private static final int BURN_TICKS = 24;
    private static final int BURN_ITEM_TICK = 10;
    private static final int REPATH_INTERVAL_TICKS = 10;
    private static final int MAX_FAILED_PATH_TICKS = 100;
    private static final int FAILED_BURN_COOLDOWN_TICKS = 300;
    private static final double BURN_DISTANCE = 1.5D;
    private static final double BURN_ITEM_SEARCH_RADIUS = 0.75D;

    private final Mob mob;
    private final double speed;
    private final double searchRadius;
    private ItemEntity targetItem;
    private ItemStack burnToolRestoreItem = ItemStack.EMPTY;
    private ItemStack activeBurnToolStack = ItemStack.EMPTY;
    private ItemStack burningStack = ItemStack.EMPTY;
    private BlockPos firePos;
    private BurnTool burnTool;
    private boolean equippedBurnTool;
    private int burnTicks;
    private int repathTicks;
    private int failedPathTicks;
    private int giveUpCooldownTicks;
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
        if (giveUpCooldownTicks > 0) {
            giveUpCooldownTicks--;
            return false;
        }
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
                && (burnTicks > 0
                || (targetItem.isAlive()
                && !targetItem.getItem().isEmpty()
                && isGroundItem(targetItem)));
    }

    @Override
    public void start() {
        burnToolRestoreItem = ItemStack.EMPTY;
        activeBurnToolStack = ItemStack.EMPTY;
        burningStack = ItemStack.EMPTY;
        firePos = null;
        burnTool = null;
        equippedBurnTool = false;
        burnTicks = 0;
        repathTicks = 0;
        failedPathTicks = 0;

        if (targetItem == null) {
            return;
        }

        if (shouldPickupOrEquipInsteadOfBurn(targetItem.getItem())) {
            restoreMainWeapon(false);
        }

        moveToTargetItem();
    }

    @Override
    public void tick() {
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        if (burnTicks > 0) {
            tickBurningGround(serverLevel);
            return;
        }

        if (targetItem == null || !targetItem.isAlive() || targetItem.getItem().isEmpty()) {
            return;
        }

        if (shouldPickupOrEquipInsteadOfBurn(targetItem.getItem())) {
            restoreMainWeapon(false);
        }

        double dist = mob.distanceTo(targetItem);
        if (dist > BURN_DISTANCE
                && (repathTicks-- <= 0 || mob.getNavigation().isDone() || mob.getNavigation().isStuck())) {
            repathTicks = REPATH_INTERVAL_TICKS;
            if (moveToTargetItem()) {
                failedPathTicks = 0;
            } else {
                failedPathTicks += REPATH_INTERVAL_TICKS;
                if (failedPathTicks >= MAX_FAILED_PATH_TICKS) {
                    abandonTarget();
                }
                return;
            }
        }

        mob.getLookControl().setLookAt(
                targetItem.getX(),
                targetItem.getY() + targetItem.getBbHeight() / 2.0,
                targetItem.getZ(),
                30.0F, 30.0F
        );

        if (dist <= BURN_DISTANCE) {
            if (shouldPickupOrEquipInsteadOfBurn(targetItem.getItem())) {
                if (tryHandleItemWithoutBurning(targetItem)) {
                    targetItem = null;
                    mob.getNavigation().stop();
                    return;
                }
            }

            if (shouldReserveInsteadOfBurn(targetItem.getItem())) {
                targetItem = null;
                mob.getNavigation().stop();
                return;
            }

            igniteGroundAtItem(serverLevel);
        }
    }

    @Override
    public void stop() {
        boolean shouldRestoreBurnTool = equippedBurnTool || isBurnTool(mob.getMainHandItem());

        clearTemporaryFire();
        targetItem = null;
        mob.getNavigation().stop();

        if (shouldRestoreBurnTool) {
            restoreMainWeapon(true);
        }

        returnActiveBurnToolIfNeeded();

        burnToolRestoreItem = ItemStack.EMPTY;
        activeBurnToolStack = ItemStack.EMPTY;
        burningStack = ItemStack.EMPTY;
        firePos = null;
        burnTool = null;
        equippedBurnTool = false;
        burnTicks = 0;
        repathTicks = 0;
        failedPathTicks = 0;
    }

    private boolean moveToTargetItem() {
        if (targetItem == null || !targetItem.isAlive()) {
            return false;
        }

        Path itemPath = mob.getNavigation().createPath(targetItem, 0);
        if (itemPath != null && itemPath.canReach() && mob.getNavigation().moveTo(itemPath, speed)) {
            return true;
        }

        BlockPos stand = findStandNearItem(targetItem);
        Path standPath = stand == null ? null : mob.getNavigation().createPath(stand, 0);
        if (standPath != null && standPath.canReach() && mob.getNavigation().moveTo(standPath, speed)) {
            return true;
        }

        return mob.getNavigation().moveTo(targetItem, speed);
    }

    private void abandonTarget() {
        giveUpCooldownTicks = FAILED_BURN_COOLDOWN_TICKS;
        targetItem = null;
        mob.getNavigation().stop();
    }

    private BlockPos findStandNearItem(ItemEntity item) {
        Level level = mob.level();
        if (item == null || !(level instanceof ServerLevel serverLevel)) {
            return null;
        }

        BlockPos itemPos = item.blockPosition();
        BlockPos bestStand = null;
        double bestDistance = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(itemPos.offset(-2, -2, -2), itemPos.offset(2, 2, 2))) {
            BlockPos stand = pos.immutable();
            if (!canStandAt(serverLevel, stand)) {
                continue;
            }

            double distance = mob.distanceToSqr(stand.getX() + 0.5D, stand.getY(), stand.getZ() + 0.5D);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestStand = stand;
            }
        }

        return bestStand;
    }

    private boolean canStandAt(ServerLevel serverLevel, BlockPos pos) {
        if (!serverLevel.isInWorldBounds(pos) || !serverLevel.getWorldBorder().isWithinBounds(pos)) {
            return false;
        }

        BlockState feet = serverLevel.getBlockState(pos);
        BlockState head = serverLevel.getBlockState(pos.above());
        BlockPos floorPos = pos.below();

        return feet.getCollisionShape(serverLevel, pos).isEmpty()
                && head.getCollisionShape(serverLevel, pos.above()).isEmpty()
                && feet.getFluidState().isEmpty()
                && head.getFluidState().isEmpty()
                && serverLevel.getBlockState(floorPos).isSolidRender(serverLevel, floorPos);
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
        } else if (isBurnTool(mob.getMainHandItem())) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    private ItemEntity findTargetItem(ServerLevel serverLevel) {
        List<ItemEntity> items = mob.level().getEntitiesOfClass(
                ItemEntity.class,
                mob.getBoundingBox().inflate(searchRadius),
                e -> e.isAlive()
                        && !e.hasPickUpDelay()
                        && !e.getItem().isEmpty()
                        && isGroundItem(e)
                        && (shouldPickupOrEquipInsteadOfBurn(e.getItem()) || canBurnItem(serverLevel, e))
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

    private boolean canBurnItem(ServerLevel serverLevel, ItemEntity itemEntity) {
        return itemEntity != null
                && !shouldReserveInsteadOfBurn(itemEntity.getItem())
                && selectBurnTarget(serverLevel, itemEntity.blockPosition()) != null;
    }

    private void igniteGroundAtItem(ServerLevel serverLevel) {
        if (targetItem == null || !targetItem.isAlive() || targetItem.getItem().isEmpty()) {
            return;
        }

        BurnTarget burnTarget = selectBurnTarget(serverLevel, targetItem.blockPosition());
        if (burnTarget == null || !equipBurnTool(burnTarget.tool())) {
            targetItem = null;
            return;
        }

        burningStack = targetItem.getItem().copy();
        burningStack.setCount(Math.min(burningStack.getCount(), 1));
        firePos = burnTarget.pos();
        burnTicks = BURN_TICKS;

        mob.getNavigation().stop();
        mob.getLookControl().setLookAt(
                firePos.getX() + 0.5D,
                firePos.getY() + 0.5D,
                firePos.getZ() + 0.5D,
                40.0F, 40.0F
        );
        mob.swing(InteractionHand.MAIN_HAND, true);

        if (burnTarget.tool() == BurnTool.LAVA_BUCKET) {
            serverLevel.setBlockAndUpdate(firePos, Blocks.LAVA.defaultBlockState());
            convertActiveLavaBucketToEmptyBucket();
            serverLevel.playSound(null, firePos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.HOSTILE, 1.0F, 1.0F);
        } else {
            serverLevel.setBlockAndUpdate(firePos, BaseFireBlock.getState(serverLevel, firePos));
            serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    firePos.getX() + 0.5D, firePos.getY() + 0.2D, firePos.getZ() + 0.5D,
                    8, 0.25D, 0.1D, 0.25D, 0.01D
            );
            serverLevel.playSound(null, firePos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.HOSTILE, 1.0F, 1.0F);
        }

        serverLevel.gameEvent(mob, GameEvent.BLOCK_PLACE, firePos);
    }

    private void tickBurningGround(ServerLevel serverLevel) {
        burnTicks--;

        if (firePos != null && burnTicks == BURN_ITEM_TICK) {
            burnItemsOnFire(serverLevel);
        }

        if (burnTicks > 0) {
            return;
        }

        clearTemporaryFire();
        if (!burningStack.isEmpty()) {
            tryBroadcastBurnMessage(serverLevel, burningStack);
            nextBurnTick = serverLevel.getGameTime() + BURN_COOLDOWN_TICKS;
        }

        burningStack = ItemStack.EMPTY;
        targetItem = null;
    }

    private void burnItemsOnFire(ServerLevel serverLevel) {
        if (firePos == null) {
            return;
        }

        AABB burnBox = new AABB(firePos).inflate(BURN_ITEM_SEARCH_RADIUS);
        List<ItemEntity> burningItems = serverLevel.getEntitiesOfClass(
                ItemEntity.class,
                burnBox,
                item -> item.isAlive() && !item.getItem().isEmpty()
        );

        if (burningItems.isEmpty()) {
            return;
        }

        ItemEntity itemToBurn = burningItems.get(0);
        if (targetItem != null && targetItem.isAlive() && targetItem.getBoundingBox().intersects(burnBox)) {
            itemToBurn = targetItem;
        }

        burningStack = itemToBurn.getItem().copy();
        burningStack.setCount(Math.min(burningStack.getCount(), 1));
        itemToBurn.discard();
        mob.swing(InteractionHand.MAIN_HAND, true);
    }

    private void clearTemporaryFire() {
        if (firePos == null || !(mob.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockState state = serverLevel.getBlockState(firePos);
        if (state.getBlock() instanceof BaseFireBlock || state.is(Blocks.LAVA)) {
            mob.swing(InteractionHand.MAIN_HAND, true);
            serverLevel.removeBlock(firePos, false);
        }
    }

    private BurnTarget selectBurnTarget(ServerLevel serverLevel, BlockPos itemPos) {
        BlockPos fire = findFirePos(serverLevel, itemPos);
        if (InventoryUtils.hasItem(mob, Items.FLINT_AND_STEEL) && fire != null) {
            return new BurnTarget(BurnTool.FLINT_AND_STEEL, fire);
        }

        BlockPos lava = findLavaPos(serverLevel, itemPos);
        if (InventoryUtils.hasItem(mob, Items.LAVA_BUCKET) && lava != null) {
            return new BurnTarget(BurnTool.LAVA_BUCKET, lava);
        }

        return null;
    }

    private BlockPos findFirePos(ServerLevel serverLevel, BlockPos itemPos) {
        BlockPos[] candidates = {
                itemPos,
                itemPos.above()
        };

        for (BlockPos candidate : candidates) {
            if (serverLevel.isInWorldBounds(candidate)
                    && serverLevel.getWorldBorder().isWithinBounds(candidate)
                    && serverLevel.getBlockState(candidate).isAir()
                    && BaseFireBlock.canBePlacedAt(serverLevel, candidate, mob.getDirection())) {
                return candidate.immutable();
            }
        }

        return null;
    }

    private BlockPos findLavaPos(ServerLevel serverLevel, BlockPos itemPos) {
        BlockPos[] candidates = {
                itemPos,
                itemPos.above()
        };

        for (BlockPos candidate : candidates) {
            if (serverLevel.isInWorldBounds(candidate)
                    && serverLevel.getWorldBorder().isWithinBounds(candidate)
                    && serverLevel.getBlockState(candidate).isAir()) {
                return candidate.immutable();
            }
        }

        return null;
    }

    private boolean equipBurnTool(BurnTool tool) {
        if (equippedBurnTool && burnTool == tool) {
            return true;
        }

        Item item = tool == BurnTool.LAVA_BUCKET ? Items.LAVA_BUCKET : Items.FLINT_AND_STEEL;
        ItemStack consumed = InventoryUtils.consumeItem(mob, item, 1).orElse(ItemStack.EMPTY);
        if (consumed.isEmpty()) {
            return false;
        }

        rememberRestoreItemBeforeBurnTool();
        equippedBurnTool = true;
        burnTool = tool;
        activeBurnToolStack = consumed.copy();
        activeBurnToolStack.setCount(1);
        mob.setItemSlot(EquipmentSlot.MAINHAND, activeBurnToolStack.copy());
        return true;
    }

    private void convertActiveLavaBucketToEmptyBucket() {
        if (burnTool == BurnTool.LAVA_BUCKET && !activeBurnToolStack.isEmpty()) {
            activeBurnToolStack = ItemStack.EMPTY;
            giveOrDrop(new ItemStack(Items.BUCKET));
        }
    }

    private void returnActiveBurnToolIfNeeded() {
        if (activeBurnToolStack.isEmpty()) {
            return;
        }

        giveOrDrop(activeBurnToolStack);
        activeBurnToolStack = ItemStack.EMPTY;
    }

    private void giveOrDrop(ItemStack stack) {
        if (!InventoryUtils.addItem(mob, stack)) {
            mob.spawnAtLocation(stack);
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

    private boolean shouldReserveInsteadOfBurn(ItemStack stack) {
        return InventoryUtils.isInventoryBackedSupplyDrop(stack);
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
            avNpc.setMainWeaponDisarmed(false);
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

            if (!slotStack.isEmpty()
                    && ItemStack.isSameItemSameTags(slotStack, remaining)
                    && slotStack.getCount() < slotStack.getMaxStackSize()) {
                int transferable = Math.min(
                        remaining.getCount(),
                        slotStack.getMaxStackSize() - slotStack.getCount()
                );

                slotStack.grow(transferable);
                remaining.shrink(transferable);
            }
        }

        for (int i = 0; i < inventory.getContainerSize() && !remaining.isEmpty(); i++) {
            ItemStack slotStack = inventory.getItem(i);

            if (!slotStack.isEmpty()) {
                continue;
            }

            int transferable = Math.min(
                    remaining.getCount(),
                    Math.min(remaining.getMaxStackSize(), inventory.getMaxStackSize())
            );

            ItemStack inserted = remaining.copy();
            inserted.setCount(transferable);

            inventory.setItem(i, inserted);
            remaining.shrink(transferable);
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
        if (!currentMainHand.isEmpty() && !isBurnTool(currentMainHand) && isUsefulWeapon(currentMainHand)) {
            burnToolRestoreItem = currentMainHand.copy();
            cacheMainWeapon(currentMainHand);
        }
    }

    private boolean mainWeaponIsEmpty() {
        if (!getCachedMainWeapon().isEmpty()) {
            return false;
        }

        return mob.getMainHandItem().isEmpty()
                || isBurnTool(mob.getMainHandItem());
    }

    private boolean isFlintAndSteel(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.FLINT_AND_STEEL;
    }

    private boolean isLavaBucket(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.LAVA_BUCKET;
    }

    private boolean isBurnTool(ItemStack stack) {
        return isFlintAndSteel(stack) || isLavaBucket(stack);
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

    private enum BurnTool {
        FLINT_AND_STEEL,
        LAVA_BUCKET
    }

    private record BurnTarget(BurnTool tool, BlockPos pos) {
    }
}
