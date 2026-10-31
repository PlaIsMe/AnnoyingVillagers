package com.pla.annoyinglib.item;

import com.pla.annoyinglib.entity.ElectricPhaseEntity;
import com.pla.annoyinglib.init.AnnoyingLibModItems;
import com.pla.annoyinglib.rig.RigCombatProfileProvider;
import com.pla.annoyinglib.rig.RigCombatStyle;
import com.pla.annoyinglib.rig.RigDualWieldGroup;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ThunderDiamondBladeItem extends SwordItem implements RigCombatProfileProvider {
    private static final int VANILLA_RIGHT_CLICK_COOLDOWN_TICKS = 20 * 15;


    public ThunderDiamondBladeItem() {
        super(new Tier() {
            public int getUses() {
                return 2561;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 4.0F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(AnnoyingLibModItems.COMPRESSED_DIAMOND.get()));
            }
        }, 3, -2.0F, (new Properties()));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.pass(stack);
        if (level instanceof ServerLevel serverLevel) {
            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, player, false);
            VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_ONE_HANDED_UPPERCUT_RIGHT);
            VanillaWeaponAbilityUtil.damageHeldItem(player, InteractionHand.MAIN_HAND, 1);
            player.getCooldowns().addCooldown(this, VANILLA_RIGHT_CLICK_COOLDOWN_TICKS);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }


    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.SPECIAL_SWORD;
    }

    @Override
    public RigDualWieldGroup getDualWieldGroup(ItemStack stack) {
        return RigDualWieldGroup.SWORD;
    }

    @Override
    public RigCombatStyle getDualRigCombatStyle(ItemStack self, ItemStack other) {
        return RigCombatStyle.DUAL_SPECIAL_SWORD;
    }
}
