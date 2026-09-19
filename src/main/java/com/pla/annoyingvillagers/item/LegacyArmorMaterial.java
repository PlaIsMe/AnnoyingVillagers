package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

/** Adapter for the pre-1.21 armor-material methods used by the existing armor classes. */
public interface LegacyArmorMaterial {
    int getDurabilityForType(ArmorItem.Type type);
    int getDefenseForType(ArmorItem.Type type);
    int getEnchantmentValue();
    Object getEquipSound();
    Ingredient getRepairIngredient();
    String getName();
    float getToughness();
    float getKnockbackResistance();

    @SuppressWarnings("unchecked")
    default Holder<ArmorMaterial> asHolder() {
        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) defense.put(type, getDefenseForType(type));

        Object sound = getEquipSound();
        Holder<SoundEvent> soundHolder = sound instanceof Holder<?> holder
                ? (Holder<SoundEvent>) holder
                : Holder.direct((SoundEvent) sound);
        String name = getName();
        ResourceLocation layer = name.indexOf(':') >= 0
                ? ResourceLocation.parse(name)
                : ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, name);
        return Holder.direct(new ArmorMaterial(
                defense,
                getEnchantmentValue(),
                soundHolder,
                this::getRepairIngredient,
                List.of(new ArmorMaterial.Layer(layer)),
                getToughness(),
                getKnockbackResistance()
        ));
    }
}
