package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.client.animation.RigClientAnimationState;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.RedAxeItem;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public final class RigItemVisualResolver {
    private RigItemVisualResolver() {}

    public static ItemStack resolve(Entity entity, ItemStack originalStack) {
        RigClientAnimationState.Active active = RigClientAnimationState.getActive(entity, entity.tickCount);
        if (active == null) return originalStack;

        RigAnimationId animationId = active.animationId();

        if (originalStack.getItem() instanceof RedAxeItem
                && animationId == RigAnimationId.GREATAXE_ULT) {
            return new ItemStack(AnnoyingVillagersModItems.GIANT_RED_AXE.get());
        }
        return originalStack;
    }
}