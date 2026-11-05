package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.entity.ShockWaveBlockEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import com.pla.annoyingvillagers.rig.RigDualWieldGroup;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.ArmorUtil;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class LegendarySwordItem extends LegacySwordItem implements RigCombatProfileProvider {
    private static final String AWAKENED_TAG = "LegendarySwordAwakened";
    private static final String AWAKEN_UNTIL_TAG = "LegendarySwordAwakenUntil";
    private static final int ACTIVE_DURATION_TICKS = 20 * 30;
    private static final int RECOVERY_COOLDOWN_TICKS = 20 * 60 * 2;
    private static final Identifier ATTACK_SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath("annoyingvillagers", "legendary_sword_awakening_attack_speed");

    public LegendarySwordItem() {
        super(new LegacyTier() {
            public int getUses() { return 1561; }
            public float getSpeed() { return 4.0F; }
            public float getAttackDamageBonus() { return 6.0F; }
            public int getLevel() { return 1; }
            public int getEnchantmentValue() { return 2; }
            public @NotNull Ingredient getRepairIngredient() { return Ingredient.of(AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()); }
        }, 3, -2.32F, com.pla.annoyingvillagers.util.LegacyItemProperties.create().fireResistant());
    }

    public static boolean isAwakened(ItemStack stack, Level level) {
        return LegacyItemData.has(stack) && LegacyItemData.get(stack) != null && LegacyItemData.get(stack).getBooleanOr(AWAKENED_TAG, false) && level.getGameTime() < LegacyItemData.get(stack).getLongOr(AWAKEN_UNTIL_TAG, 0L);
    }

    public static boolean activateVanillaSpecial(Player player) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || player.level().isClientSide()) return false;
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof LegendarySwordItem item) || player.getCooldowns().isOnCooldown(new net.minecraft.world.item.ItemStack(item))) return false;
        LegacyItemData.update(stack, tag -> {
            tag.putBoolean(AWAKENED_TAG, true);
            tag.putLong(AWAKEN_UNTIL_TAG, player.level().getGameTime() + ACTIVE_DURATION_TICKS);
        });
        stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(List.of(1.0F), List.of(), List.of(), List.of()));
        VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_FIST_ATTACK);
        applyAttackSpeed(player);
        refreshBuffs(player);
        player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), ACTIVE_DURATION_TICKS);
        return true;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(new net.minecraft.world.item.ItemStack(this))) return InteractionResult.PASS;
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_TWO_HANDED_SLAM_HEAVY);
            VanillaWeaponAbilityUtil.performVanillaMeleeHit(player, 5.0D);
            BlockPos center = player.blockPosition();
            for (int radius = 1; radius <= 6; radius++) {
                int ringRadius = radius;
                new DelayedTask((radius - 1) * 2) { @Override public void run() { if (player.isAlive() && !player.isRemoved()) spawnCircleRing(serverLevel, center, ringRadius, player); } };
            }
            player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(this), ACTIVE_DURATION_TICKS);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slot = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        super.inventoryTick(stack, level, entity, equipmentSlot);
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || level.isClientSide() || !(entity instanceof Player player) || !LegacyItemData.has(stack) || LegacyItemData.get(stack) == null || !LegacyItemData.get(stack).getBooleanOr(AWAKENED_TAG, false)) return;
        if (isAwakened(stack, level)) {
            if (player.tickCount % 10 == 0) refreshBuffs(player);
            applyAttackSpeed(player);
            return;
        }
        clearAwakening(stack, player);
        player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(this), RECOVERY_COOLDOWN_TICKS);
    }

    private static void refreshBuffs(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 25, 2, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 25, 2, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 25, 1, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 25, 2, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 25, 2, false, false, true));
    }

    private static void applyAttackSpeed(Player player) {
        AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeed == null) return;
        attackSpeed.removeModifier(ATTACK_SPEED_MODIFIER_ID);
        attackSpeed.addTransientModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, 0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    private static void clearAwakening(ItemStack stack, Player player) {
        LegacyItemData.update(stack, tag -> {
            tag.remove(AWAKENED_TAG);
            tag.remove(AWAKEN_UNTIL_TAG);
            tag.remove("CustomModelData");
        });
        stack.remove(DataComponents.CUSTOM_MODEL_DATA);
        clearVanillaAttackSpeed(player);
    }

    public static boolean hasActiveVanillaAwakening(Player player) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled()) return false;
        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (stack.getItem() instanceof LegendarySwordItem && isAwakened(stack, player.level())) return true;
        }
        ItemStack offhand = player.getOffhandItem();
        if (offhand.getItem() instanceof LegendarySwordItem && isAwakened(offhand, player.level())) return true;
        return false;
    }

    public static void clearVanillaAttackSpeed(Player player) {
        AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeed != null) attackSpeed.removeModifier(ATTACK_SPEED_MODIFIER_ID);
    }

    @Override
    public void hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!attacker.level().isClientSide()) ArmorUtil.damageArmor(target, new Random().nextInt(1, 5));
        super.hurtEnemy(stack, target, attacker);
    }

    public void appendHoverText(@NotNull ItemStack itemStack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> componentList, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, display, componentList, tooltipFlag);
        componentList.accept(Component.translatable("tooltip.annoyingvillagers.legendary_sword"));
    }

    public static void spawnCircleRing(ServerLevel level, BlockPos centerPos, int radius, LivingEntity owner) {
        double inner = (radius - 0.5D) * (radius - 0.5D);
        double outer = (radius + 0.5D) * (radius + 0.5D);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double dist2 = (double)dx * dx + (double)dz * dz;
                if (dist2 >= inner && dist2 <= outer) spawnShockWaveBlock(level, centerPos.offset(dx, 0, dz), owner);
            }
        }
    }

    private static void spawnShockWaveBlock(ServerLevel level, BlockPos startPos, LivingEntity owner) {
        final int BLOCK_SEARCH_DEPTH = 256;
        final int ENTITY_GROUND_LIFETIME = 10;
        BlockPos pos = startPos;
        BlockState state = level.getBlockState(pos);
        int minY = level.getMinY();
        for (int i = 0; i < BLOCK_SEARCH_DEPTH && pos.getY() > minY && state.getRenderShape() != RenderShape.MODEL; i++) {
            pos = pos.below();
            state = level.getBlockState(pos);
        }
        if (state.getRenderShape() != RenderShape.MODEL) return;
        ShockWaveBlockEntity blockEntity = new ShockWaveBlockEntity(level, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, state, ENTITY_GROUND_LIFETIME);
        blockEntity.setOwnerUuid(owner.getUUID());
        level.addFreshEntity(blockEntity);
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) { return RigCombatStyle.LEGENDARY_SWORD; }

    @Override
    public RigDualWieldGroup getDualWieldGroup(ItemStack stack) { return RigDualWieldGroup.LEGENDARY_SWORD; }

    @Override
    public RigCombatStyle getDualRigCombatStyle(ItemStack self, ItemStack other) {
        if (other.getItem() instanceof WoopieTheSwordItem) return RigCombatStyle.LEGENDARY_SWORD_WOOPIE;
        if (other.getItem() instanceof BlueDemonTridentItem) return RigCombatStyle.BLUE_DEMON_LEGENDARY_SWORD;
        return RigCombatStyle.LEGENDARY_SWORD;
    }
}
