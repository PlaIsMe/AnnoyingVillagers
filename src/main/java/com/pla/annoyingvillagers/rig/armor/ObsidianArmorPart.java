package com.pla.annoyingvillagers.rig.armor;

import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.world.entity.EquipmentSlot;

public enum ObsidianArmorPart {
    HELMET(EquipmentSlot.HEAD, SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "OBSIDIAN_HELMET_EXTENDED_", 26),
    CHESTPLATE(EquipmentSlot.CHEST, SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "OBSIDIAN_CHESTPLATE_EXTENDED_", 20);

    private final EquipmentSlot slot;
    private final SpecialAnimationFamily family;
    private final String animationIdPrefix;
    private final int animationCount;

    ObsidianArmorPart(EquipmentSlot slot, SpecialAnimationFamily family, String animationIdPrefix, int animationCount) {
        this.slot = slot;
        this.family = family;
        this.animationIdPrefix = animationIdPrefix;
        this.animationCount = animationCount;
    }

    public EquipmentSlot slot() {
        return this.slot;
    }

    public SpecialAnimationFamily family() {
        return this.family;
    }

    public int animationCount() {
        return this.animationCount;
    }

    public SpecialAnimationId animationId(int animationIndex) {
        if (animationIndex < 1 || animationIndex > this.animationCount) {
            throw new IllegalArgumentException("Unknown " + this + " EXTENDED_" + animationIndex);
        }
        return SpecialAnimationId.valueOf(this.animationIdPrefix + animationIndex);
    }

    public static ObsidianArmorPart fromAnimationId(SpecialAnimationId animationId) {
        if (animationId == null) throw new IllegalArgumentException("Obsidian armor animation id cannot be null");
        for (ObsidianArmorPart part : values()) {
            if (animationId.family() == part.family) return part;
        }
        throw new IllegalArgumentException(animationId + " is not an Obsidian Diamond armor animation");
    }
}
