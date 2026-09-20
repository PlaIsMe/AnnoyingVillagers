package com.pla.annoyingvillagers.client.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.item.LegendarySwordItem;
import com.pla.annoyingvillagers.item.RedAxeItem;
import com.pla.annoyingvillagers.item.ShadowObsidianPillarItem;
import com.pla.annoyingvillagers.item.ShadowObsidianSwordItem;
import com.pla.annoyingvillagers.util.LegacyItemData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/** Data-driven replacement for the removed global ItemProperties registry. */
public record LegacyItemRangeProperty(Kind kind) implements RangeSelectItemModelProperty {
    public static final MapCodec<LegacyItemRangeProperty> MAP_CODEC = Codec.STRING.fieldOf("kind")
            .xmap(name -> new LegacyItemRangeProperty(Kind.valueOf(name.toUpperCase())),
                    property -> property.kind.name().toLowerCase());

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        LivingEntity entity = owner == null ? null : owner.asLivingEntity();
        return switch (kind) {
            case SECOND_FORM -> bool(LegacyItemData.has(stack)
                    ? LegacyItemData.get(stack).getBooleanOr("SecondForm", false)
                    : EnderAegisItem.isSecondForm(stack));
            case SNAKE_ANIMATION -> bool(LegacyItemData.has(stack) && LegacyItemData.get(stack).getBooleanOr("SnakeAnimation", false));
            case SNAKE_ANIMATION_READY -> bool(LegacyItemData.has(stack) && LegacyItemData.get(stack).getIntOr("HitCount", 0) == 5);
            case AWAKENED -> bool(level != null && LegendarySwordItem.isAwakened(stack, level));
            case CAST -> FishingRodGrappleUtil.getCastProperty(stack, entity);
            case HOOK -> bool(LegacyItemData.has(stack) && LegacyItemData.get(stack).contains("hook"));
            case ATTACHED -> bool(entity != null && HookGunItem.hasAttachedHook(entity.level(), entity));
            case BLOCKING -> bool(entity != null && entity.isUsingItem() && entity.getUseItem() == stack);
            case BURST -> bool(ShadowObsidianPillarItem.isBurst(stack));
            case STRAIGHT_FORM -> bool(ShadowObsidianSwordItem.isStraightForm(stack));
            case GIANT_FORM -> bool(RedAxeItem.isGiantForm(stack, level));
        };
    }

    private static float bool(boolean value) {
        return value ? 1.0F : 0.0F;
    }

    @Override
    public MapCodec<LegacyItemRangeProperty> type() {
        return MAP_CODEC;
    }

    public enum Kind {
        SECOND_FORM, SNAKE_ANIMATION, SNAKE_ANIMATION_READY, AWAKENED, CAST, HOOK, ATTACHED,
        BLOCKING, BURST, STRAIGHT_FORM, GIANT_FORM
    }
}
