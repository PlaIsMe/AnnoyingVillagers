package com.pla.annoyingvillagers.compat.p1nero_bow;

import com.p1nero.epicfightbow.animations.ScanAttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

public class EpicFightBow {
    public static boolean isP1neroBowAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation) {
        return dynamicAnimation.get() instanceof ScanAttackAnimation;
    }
}
