package com.pla.annoyingvillagers.rig;

import net.minecraft.world.item.ItemStack;

public interface RigCombatProfileProvider {

    RigCombatStyle getRigCombatStyle(ItemStack stack);

    default RigDualWieldGroup getDualWieldGroup(ItemStack stack) {
        return RigDualWieldGroup.NONE;
    }

    default RigCombatStyle getDualRigCombatStyle(ItemStack self, ItemStack other) {
        return getRigCombatStyle(self);
    }
}