package com.pla.annoyingvillagers.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class BlueFlameSwordItem extends SwordItem {
    private static boolean conditionToBurn(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
//        Add this in AV_EFM
//        if (pAttacker.level() instanceof ServerLevel) {
//            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(pAttacker, LivingEntityPatch.class);
//            if (livingEntityPatch != null) {
//                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
//                if (dynamicAnimation == AnimsHerrscher.HERRSCHER_AUSROTTUNG) {
//                    return true;
//                }
//            }
//        }
//        ci.cancel here
        return true;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (conditionToBurn(pStack, pTarget, pAttacker)) {
            pTarget.setSecondsOnFire(2);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public BlueFlameSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 3.0F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.DIAMOND));
            }
        }, 3, -2.4F, (new Properties()));
    }
}