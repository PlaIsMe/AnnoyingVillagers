package com.pla.annoyingvillagers.compat.p1nero_bow;

import com.p1nero.epicfightbow.animations.ScanAttackAnimation;
import com.p1nero.epicfightbow.item.EFBowItems;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class EpicFightBow {
    public static boolean isP1neroBowAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation) {
        return dynamicAnimation.get() instanceof ScanAttackAnimation;
    }

    public static boolean isMortisBow(CapabilityItem mainHandCap) {
        return mainHandCap == EpicFightCapabilities.getItemStackCapability(EFBowItems.MORTIS.get().getDefaultInstance());
    }
}
