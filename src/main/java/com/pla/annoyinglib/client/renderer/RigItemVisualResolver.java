package com.pla.annoyinglib.client.renderer;
import net.minecraft.world.item.Items;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.client.animation.RigClientAnimationState;
import com.pla.annoyinglib.rig.RigAnimationId;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public final class RigItemVisualResolver {
    private RigItemVisualResolver() {}

    public static ItemStack resolve(Entity entity, ItemStack originalStack, boolean leftHand) {
        if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isLegendarySwordItem(originalStack.getItem()))
                && ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isLegendaryAwakened(entity)) {
            ItemStack awakenedStack = originalStack.copy();
            awakenedStack.getOrCreateTag().putInt("CustomModelData", 1);
            return awakenedStack;
        }

        RigClientAnimationState.Active active = RigClientAnimationState.getActive(entity, entity.tickCount);
        if (active == null) return originalStack;

        RigAnimationId animationId = active.animationId();
        float animationTick = active.elapsedTicks(entity.tickCount);

        if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isRedAxeItem(originalStack.getItem()))
                && animationId == RigAnimationId.GREATAXE_ULT) {
            return new ItemStack((ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.getItems_GIANT_RED_AXE() : Items.AIR));
        }

        if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isDNAxHookedSwordItem(originalStack.getItem()))
                && (animationId == RigAnimationId.BASIC_ULT || animationId == RigAnimationId.DUAL_BASIC_ULT)) {
            return new ItemStack((ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.getItems_DNAX_HOOKED_SWORD_ABILITY() : Items.AIR));
        }

        if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isLegendarySwordItem(originalStack.getItem()))
                && (animationId == RigAnimationId.LEGENDARY_SWORD_ULT || animationId == RigAnimationId.LEGENDARY_SWORD_EXTRA_ULT)) {
            return new ItemStack((ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.getItems_HEAVY_ATTACK_LEGENDARY_SWORD() : Items.AIR));
        }

        if (ModList.get().isLoaded("herobrine_vessels")) {
            return HerobrineVessels.Client.resolveRigItem(entity, originalStack, animationId, animationTick, leftHand);
        }

        return originalStack;
    }
}
