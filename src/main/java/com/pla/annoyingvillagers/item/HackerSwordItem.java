package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class HackerSwordItem extends SwordItem implements RigCombatProfileProvider {
    private static final int COMBO_HITS = 4;
    private static final int COMBO_COOLDOWN_TICKS = 20 * 12;
    private static final ThreadLocal<Player> COMBO_ATTACKER = new ThreadLocal<>();

    public static boolean isComboAttack(Player player) {
        return COMBO_ATTACKER.get() == player;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || hand != InteractionHand.MAIN_HAND
                || player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.pass(stack);
        if (!level.isClientSide()) {
            // Leave time for separate swings and the target's vanilla hurt immunity to expire.
            int interval = Math.max(4, (int)Math.ceil(player.getCurrentItemAttackStrengthDelay()));
            player.getCooldowns().addCooldown(this, COMBO_COOLDOWN_TICKS);
            strike(player, stack, level, interval, COMBO_HITS);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void strike(Player player, ItemStack stack, Level level, int interval, int remaining) {
        if (!player.isAlive() || player.isRemoved() || player.level() != level
                || player.getMainHandItem() != stack || stack.isEmpty()
                || !VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
        VanillaWeaponAbilityUtil.swingMainHand(player,
                VanillaWeaponAbilityUtil.BETTER_COMBAT_ONE_HANDED_SLAM, interval);
        Player previous = COMBO_ATTACKER.get();
        COMBO_ATTACKER.set(player);
        try {
            VanillaWeaponAbilityUtil.performVanillaMeleeHit(player, 3.0D);
        } finally {
            if (previous == null) COMBO_ATTACKER.remove();
            else COMBO_ATTACKER.set(previous);
        }
        if (remaining > 1) new DelayedTask(interval) {
            @Override
            public void run() { strike(player, stack, level, interval, remaining - 1); }
        };
    }

    public HackerSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 250;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 2.2F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.IRON_INGOT));
            }
        }, 3, -1.4F, (new Properties()));
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.HACKER_SWORD;
    }
}
