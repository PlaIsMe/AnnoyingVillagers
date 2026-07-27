package com.pla.annoyingvillagers.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.Random;

public class ClowSwordItem extends SwordItem {
    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!pAttacker.level().isClientSide) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(pAttacker, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (dynamicAnimation == AnimsHerrscher.HERRSCHER_BEFREIUNG) {
                    pTarget.spawnAtLocation(new ItemStack(Items.LAPIS_LAZULI, new Random().nextInt(1, 3)));
                }
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public ClowSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 2.4F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.LAPIS_LAZULI));
            }
        }, 3, -2.2F, (new Properties()));
    }
}
