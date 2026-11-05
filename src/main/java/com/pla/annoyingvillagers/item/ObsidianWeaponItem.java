package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ObsidianWeaponItem extends LegacySwordItem implements RigCombatProfileProvider {
    public static final int VANILLA_ABILITY_COOLDOWN_TICKS = 20 * 30;

    public ObsidianWeaponItem() {
        super(new LegacyTier() {
            public int getUses() { return 3000; }
            public float getSpeed() { return 50.0F; }
            public float getAttackDamageBonus() { return 2.0F; }
            public int getLevel() { return 1; }
            public int getEnchantmentValue() { return 0; }
            public Ingredient getRepairIngredient() { return Ingredient.of(net.minecraft.core.HolderSet.direct(com.pla.annoyingvillagers.init.AnnoyingVillagersModItems.OBSIDIAN_ITEM)); }
        }, 3, 0.5F, com.pla.annoyingvillagers.util.LegacyItemProperties.create().fireResistant());
    }

    public static void onVanillaCriticalHit(Player player) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || !(player.level() instanceof ServerLevel serverLevel)) return;
        BlockState state = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, true);
        Vec3 handPos = CommonUtil.getVanillaSwordOrBodyPosition(player);
        if (handPos == null) handPos = player.getEyePosition().add(player.getLookAngle().scale(0.5D));
        HerobrineUtil.summonObsidianBlocksFromPosition(serverLevel, player, state, 2, handPos);
    }

    @Override
    public void hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(new net.minecraft.world.item.ItemStack(this))) return super.use(level, player, hand);
        if (level instanceof ServerLevel serverLevel) {
            BlockState state = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, true);
            HerobrineUtil.summonObsidianCube3x3x3(serverLevel, player, state);
            VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_FIST_ATTACK);
            player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(this), VANILLA_ABILITY_COOLDOWN_TICKS);
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean activateVanillaSpecial(Player player) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || !(player.getMainHandItem().getItem() instanceof ObsidianWeaponItem item) || player.getCooldowns().isOnCooldown(new net.minecraft.world.item.ItemStack(item)) || !(player.level() instanceof ServerLevel serverLevel)) return false;
        BlockState state = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, true);
        HerobrineUtil.summonObsidianPillarAtTarget(serverLevel, player, state);
        VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_FIST_ATTACK);
        player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), VANILLA_ABILITY_COOLDOWN_TICKS);
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, display, list, tooltipflag);
        list.accept(Component.translatable("tooltip.annoyingvillagers.obsidian_weapon"));
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.OBSIDIAN_WEAPON;
    }
}
