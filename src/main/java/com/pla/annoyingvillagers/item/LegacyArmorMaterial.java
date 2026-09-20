package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Repairable;
import org.jspecify.annotations.Nullable;

/** Adapter for the pre-1.21 armor-material methods used by the existing armor classes. */
public interface LegacyArmorMaterial {
    int getDurabilityForType(LegacyArmorItem.Type type);
    int getDefenseForType(LegacyArmorItem.Type type);
    int getEnchantmentValue();
    Object getEquipSound();
    @Nullable Ingredient getRepairIngredient();
    String getName();
    float getToughness();
    float getKnockbackResistance();

    @SuppressWarnings("unchecked")
    default Item.Properties applyProperties(LegacyArmorItem.Type type, Item.Properties properties) {
        Object sound = getEquipSound();
        Holder<SoundEvent> soundHolder = sound instanceof Holder<?> holder
                ? (Holder<SoundEvent>) holder
                : Holder.direct((SoundEvent) sound);
        String name = getName();
        Identifier asset = name.indexOf(':') >= 0
                ? Identifier.parse(name)
                : Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, name);
        ResourceKey<EquipmentAsset> assetKey = ResourceKey.create(EquipmentAssets.ROOT_ID, asset);
        Identifier modifierId = Identifier.fromNamespaceAndPath(
                AnnoyingVillagers.MODID, "armor." + type.getName() + "." + asset.getPath());
        ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.builder()
                .add(Attributes.ARMOR, new AttributeModifier(modifierId, getDefenseForType(type), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.bySlot(type.getSlot()))
                .add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(modifierId, getToughness(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.bySlot(type.getSlot()));
        if (getKnockbackResistance() > 0.0F) {
            attributes.add(Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(modifierId, getKnockbackResistance(), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.bySlot(type.getSlot()));
        }
        Item.Properties result = properties.durability(getDurabilityForType(type))
                .attributes(attributes.build())
                .enchantable(Math.max(1, getEnchantmentValue()))
                .component(DataComponents.EQUIPPABLE,
                        Equippable.builder(type.getSlot()).setEquipSound(soundHolder).setAsset(assetKey).build());
        Ingredient repairIngredient = getRepairIngredient();
        return repairIngredient == null
                ? result
                : result.component(DataComponents.REPAIRABLE, new Repairable(repairIngredient.getValues()));
    }
}
