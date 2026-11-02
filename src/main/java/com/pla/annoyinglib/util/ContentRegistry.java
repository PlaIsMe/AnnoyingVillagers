package com.pla.annoyinglib.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

/** Runtime lookups for content supplied by optional companion mods. */
public final class ContentRegistry {
    private ContentRegistry() {}

    public static Item item(String id) {
        Item value = ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(id));
        return value == null ? Items.AIR : value;
    }

    public static MobEffect effect(String id) {
        return ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.parse(id));
    }

    public static SoundEvent sound(String id) {
        SoundEvent value = ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse(id));
        return value == null ? SoundEvents.EMPTY : value;
    }
}
