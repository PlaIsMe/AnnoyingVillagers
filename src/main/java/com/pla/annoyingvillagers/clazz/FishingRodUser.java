package com.pla.annoyingvillagers.clazz;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface FishingRodUser {
    State getCombatFishingRodState();

    Item getCombatFishingRodItem();

    default boolean canStartCombatFishingRodSession(Mob self) {
        return true;
    }

    default boolean canUseStickyCombatFishingRodTarget() {
        return true;
    }

    default boolean canUseJessicaCombatFishingRodHook() {
        return false;
    }

    default void tickCombatFishingRodCooldown() {
        State state = this.getCombatFishingRodState();
        if (state.cooldownTicks > 0) {
            state.cooldownTicks--;
        }
    }

    default boolean isCombatFishingRodSessionActive() {
        return this.getCombatFishingRodState().sessionActive;
    }

    default void beginCombatFishingRodSession(Mob self) {
        State state = this.getCombatFishingRodState();
        if (!state.sessionActive) {
            state.originalOffhand = self.getOffhandItem().copy();
            state.sessionActive = true;
            state.useCount = 0;
            state.stickyTargetId = 0;
        }

        Item rodItem = this.getCombatFishingRodItem();
        if (!self.getOffhandItem().is(rodItem)) {
            self.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(rodItem));
        }
    }

    default void restoreCombatFishingRodSession(Mob self, boolean setCooldown, int minCooldownTicks, int randomCooldownTicks) {
        State state = this.getCombatFishingRodState();
        self.setItemInHand(InteractionHand.OFF_HAND, state.originalOffhand.copy());
        state.sessionActive = false;
        state.originalOffhand = ItemStack.EMPTY;
        state.useCount = 0;
        state.stickyTargetId = 0;

        if (setCooldown) {
            int randomTicks = randomCooldownTicks <= 0 ? 0 : self.getRandom().nextInt(randomCooldownTicks + 1);
            state.cooldownTicks = minCooldownTicks + randomTicks;
        }
    }

    final class State {
        private boolean sessionActive;
        private ItemStack originalOffhand = ItemStack.EMPTY;
        private int useCount;
        private int cooldownTicks;
        private int stickyTargetId;

        public int getUseCount() {
            return useCount;
        }

        public void incrementUseCount() {
            this.useCount++;
        }

        public int getCooldownTicks() {
            return cooldownTicks;
        }

        public int getStickyTargetId() {
            return stickyTargetId;
        }

        public void setStickyTargetId(int stickyTargetId) {
            this.stickyTargetId = stickyTargetId;
        }
    }
}
