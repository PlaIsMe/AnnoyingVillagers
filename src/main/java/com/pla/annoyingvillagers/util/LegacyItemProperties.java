package com.pla.annoyingvillagers.util;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

/** Supplies registry-aware properties to legacy no-argument item constructors. */
public final class LegacyItemProperties {
    private static final ThreadLocal<ResourceKey<Item>> CURRENT_ID = new ThreadLocal<>();

    private LegacyItemProperties() {}

    public static <T extends Item> T construct(Identifier id, Supplier<? extends T> factory) {
        CURRENT_ID.set(ResourceKey.create(Registries.ITEM, id));
        try {
            return factory.get();
        } finally {
            CURRENT_ID.remove();
        }
    }

    public static Item.Properties create() {
        ResourceKey<Item> id = CURRENT_ID.get();
        if (id == null) throw new IllegalStateException("Item properties requested outside deferred registration");
        return new Item.Properties().setId(id);
    }
}
