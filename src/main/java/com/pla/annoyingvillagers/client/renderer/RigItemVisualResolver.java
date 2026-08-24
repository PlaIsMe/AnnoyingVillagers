package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.client.animation.RigClientAnimationState;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.DNAxHookedSwordItem;
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

        if (originalStack.getItem() instanceof DNAxHookedSwordItem
                && (animationId == RigAnimationId.BASIC_ULT || animationId == RigAnimationId.DUAL_BASIC_ULT)) {
            return new ItemStack(AnnoyingVillagersModItems.DNAX_HOOKED_SWORD_ABILITY.get());
        }
        return originalStack;
    }
}