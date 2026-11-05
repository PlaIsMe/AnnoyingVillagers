package com.pla.annoyingvillagers.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlocksAttacks;

import java.util.List;
import java.util.Optional;

/** The blocking components supplied to vanilla's shield in 1.21.11. */
public final class LegacyShieldProperties {
    private LegacyShieldProperties() {}

    public static Item.Properties withBlocking(Item.Properties properties) {
        return properties.component(DataComponents.BLOCKS_ATTACKS, new BlocksAttacks(
                        0.25F, 1.0F,
                        List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                        new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                        Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                        Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK);
    }
}
