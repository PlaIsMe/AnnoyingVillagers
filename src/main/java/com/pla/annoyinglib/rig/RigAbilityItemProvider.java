package com.pla.annoyinglib.rig;

import net.minecraft.world.item.Item;

/** Supplies the item rules for scripted rig ability hooks without changing equipment. */
public interface RigAbilityItemProvider {
    Item getRigAbilityItem();
}
