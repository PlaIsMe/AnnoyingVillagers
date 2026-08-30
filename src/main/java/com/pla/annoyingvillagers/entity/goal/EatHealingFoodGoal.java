package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;

public class EatHealingFoodGoal extends Goal {
    private static final float HEALING_HEALTH_RATIO = 0.7F;
    private static final int EAT_TICKS = 32;

    private final AVNpc avNpc;
    private ItemStack foodStack = ItemStack.EMPTY;
    private ItemStack previousMainHand = ItemStack.EMPTY;
    private InteractionHand foodHand = InteractionHand.MAIN_HAND;
    private boolean usingTemporaryFood;
    private boolean finishedEating;
    private boolean startedEating;
    private boolean wasSprinting;
    private int backoffTicks;
    private int totalTicks;
    private int useTicks;

    public EatHealingFoodGoal(AVNpc avNpc) {
        this.avNpc = avNpc;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.avNpc.level() instanceof ServerLevel
                && this.avNpc.isAlive()
                && !this.avNpc.isNoAi()
                && !RigStunController.isStunned(this.avNpc)
                && !this.avNpc.isPassenger()
                && !this.avNpc.isHealing()
                && this.avNpc.getGapCooldown() <= 0
                && this.avNpc.getHealth() < this.avNpc.getMaxHealth() * HEALING_HEALTH_RATIO
                && this.selectFood();
    }

    @Override
    public boolean canContinueToUse() {
        return this.useTicks < this.totalTicks
                && this.avNpc.level() instanceof ServerLevel
                && this.avNpc.isAlive()
                && !this.avNpc.isNoAi()
                && !RigStunController.isStunned(this.avNpc)
                && !this.avNpc.isPassenger()
                && !this.foodStack.isEmpty()
                && !this.finishedEating;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        this.useTicks = 0;
        this.finishedEating = false;
        this.startedEating = false;
        this.wasSprinting = this.avNpc.isSprinting();
        this.avNpc.setHealing(true);
        this.avNpc.setSprinting(false);
        this.avNpc.getNavigation().stop();

        if (this.usingTemporaryFood && !this.equipTemporaryFood()) {
            this.finishedEating = true;
            this.totalTicks = 0;
            this.avNpc.setHealing(false);
            return;
        }

        RigAnimationId backoffAnimation = this.avNpc.getRandom().nextBoolean()
                ? RigAnimationId.ROLL_BACKWARD
                : RigAnimationId.STEP_BACKWARD;
        this.backoffTicks = RigAnimationController.animationPlaybackTicks(RigAnimationSpecs.get(backoffAnimation));
        this.totalTicks = this.backoffTicks + EAT_TICKS;
        RigAnimationController.play(this.avNpc, RigAnimationSpecs.get(backoffAnimation), this.avNpc.getTarget());
    }

    @Override
    public void tick() {
        this.useTicks++;

        LivingEntity target = this.avNpc.getTarget();
        if (target != null && target.isAlive()) {
            this.avNpc.getLookControl().setLookAt(target, 50.0F, 50.0F);
        }

        if (!this.startedEating && this.useTicks >= this.backoffTicks) {
            this.startedEating = true;
            this.avNpc.startUsingItem(this.foodHand);
            RigAnimationController.play(this.avNpc, this.foodHand == InteractionHand.OFF_HAND
                    ? RigAnimationId.EAT_OFFHAND
                    : RigAnimationId.EAT_MAINHAND);
        }

        if (this.useTicks >= this.totalTicks) {
            this.finishEating();
        }
    }

    @Override
    public void stop() {
        this.avNpc.stopUsingItem();

        if (this.usingTemporaryFood) {
            if (!this.finishedEating && !this.foodStack.isEmpty()) {
                this.giveOrDrop(this.foodStack.copy());
            }
            this.avNpc.setItemInHand(InteractionHand.MAIN_HAND, this.previousMainHand.copy());
        }

        this.avNpc.setSprinting(this.wasSprinting);
        this.avNpc.getNavigation().stop();
        this.avNpc.setHealing(false);
        this.reset();
    }

    private boolean selectFood() {
        ItemStack offhand = this.avNpc.getOffhandItem();
        if (InventoryUtils.isHealingFoodStack(offhand)) {
            this.setSelectedFood(offhand, InteractionHand.OFF_HAND, false);
            return true;
        }

        ItemStack mainHand = this.avNpc.getMainHandItem();
        if (InventoryUtils.isHealingFoodStack(mainHand)) {
            this.setSelectedFood(mainHand, InteractionHand.MAIN_HAND, false);
            return true;
        }

        ItemStack inventoryFood = InventoryUtils.selectHealingFood(this.avNpc, this.avNpc.getRandom()).orElse(ItemStack.EMPTY);
        if (!inventoryFood.isEmpty()) {
            this.setSelectedFood(inventoryFood, InteractionHand.MAIN_HAND, true);
            return true;
        }

        this.resetSelectedFood();
        return false;
    }

    private void setSelectedFood(ItemStack stack, InteractionHand hand, boolean temporaryFood) {
        this.foodStack = stack.copy();
        this.foodStack.setCount(1);
        this.foodHand = hand;
        this.usingTemporaryFood = temporaryFood;
    }

    private boolean equipTemporaryFood() {
        ItemStack consumed = this.avNpc.consumeInventoryItem(
                stack -> ItemStack.isSameItemSameTags(stack, this.foodStack),
                1
        ).orElse(ItemStack.EMPTY);
        if (consumed.isEmpty()) {
            this.resetSelectedFood();
            return false;
        }

        this.previousMainHand = this.avNpc.getMainHandItem().copy();
        this.foodStack = consumed.copy();
        this.foodStack.setCount(1);
        this.avNpc.setItemInHand(InteractionHand.MAIN_HAND, this.foodStack.copy());
        return true;
    }

    private void finishEating() {
        if (this.finishedEating || this.foodStack.isEmpty()) {
            return;
        }

        ItemStack useStack = this.foodStack.copy();
        useStack.setCount(1);

        if (!this.usingTemporaryFood) {
            ItemStack heldStack = this.avNpc.getItemInHand(this.foodHand);
            if (!ItemStack.isSameItemSameTags(heldStack, this.foodStack)) {
                return;
            }

            heldStack.shrink(1);
            if (heldStack.isEmpty()) {
                this.avNpc.setItemInHand(this.foodHand, ItemStack.EMPTY);
            }
        }

        if (CombatBehaviour.finishUsingHealingFood(this.avNpc, useStack, this.foodHand)) {
            this.finishedEating = true;
            this.avNpc.setGapCooldown();
        }
    }

    private void giveOrDrop(ItemStack stack) {
        if (!InventoryUtils.addItem(this.avNpc, stack)) {
            this.avNpc.spawnAtLocation(stack);
        }
    }

    private void reset() {
        this.useTicks = 0;
        this.backoffTicks = 0;
        this.totalTicks = 0;
        this.finishedEating = false;
        this.startedEating = false;
        this.wasSprinting = false;
        this.previousMainHand = ItemStack.EMPTY;
        this.resetSelectedFood();
    }

    private void resetSelectedFood() {
        this.foodStack = ItemStack.EMPTY;
        this.foodHand = InteractionHand.MAIN_HAND;
        this.usingTemporaryFood = false;
    }
}
