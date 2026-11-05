package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NullWeaponItem extends LegacySwordItem implements RigCombatProfileProvider {
    private static final String CHARGE_TAG = "AVNullWeaponCharge";
    private static final String RELEASE_UNTIL_TAG = "AVNullWeaponReleaseUntil";
    private static final String RECOVERY_UNTIL_TAG = "AVNullWeaponRecoveryUntil";
    private static final String LOGIN_RECONCILE_UNTIL_TAG = "AVNullWeaponLoginReconcileUntil";
    private static final int LOGIN_RECONCILE_TICKS = 20 * 5;
    private static final int RELEASE_DURATION_TICKS = 20 * 30;
    private static final int RECOVERY_COOLDOWN_TICKS = 20 * 60;
    private static final String[] OWNED_KEYS = {"NullPickaxeUUID", "NullHoeUUID", "NullAxeUUID", "NullShovelUUID", "NullSwordUUID"};

    public NullWeaponItem() {
        super(new LegacyTier() {
            public int getUses() { return 1561; }
            public float getSpeed() { return 4.0F; }
            public float getAttackDamageBonus() { return 3.0F; }
            public int getLevel() { return 1; }
            public int getEnchantmentValue() { return 4; }
            public Ingredient getRepairIngredient() { return null; }
        }, 3, -3.0F, com.pla.annoyingvillagers.util.LegacyItemProperties.create());
    }

    public static int getCharge(ItemStack stack) {
        return VanillaWeaponAbilityUtil.getCharge(stack, CHARGE_TAG, 100);
    }

    public static void setCharge(ItemStack stack, int charge) {
        VanillaWeaponAbilityUtil.setCharge(stack, CHARGE_TAG, charge, 100);
    }

    public static void beginLoginReconciliation(Player player) {
        if (!player.level().isClientSide()) {
            player.getPersistentData().putLong(LOGIN_RECONCILE_UNTIL_TAG, player.level().getGameTime() + LOGIN_RECONCILE_TICKS);
        }
    }

    public static void onOwnedWeaponHit(Player player) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || player.level().isClientSide()) return;
        ItemStack stack = player.getOffhandItem();
        if (!(stack.getItem() instanceof NullWeaponItem)) return;
        VanillaWeaponAbilityUtil.addCharge(stack, CHARGE_TAG, 1, 100);
        if (player.level() instanceof ServerLevel serverLevel) syncOwnedWeapons(serverLevel, player, stack);
    }

    public static void onPlayerMeleeHit(Player player) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || player.level().isClientSide()) return;
        ItemStack stack = player.getOffhandItem();
        if (!(stack.getItem() instanceof NullWeaponItem item) || player.getCooldowns().isOnCooldown(new net.minecraft.world.item.ItemStack(item))) return;
        VanillaWeaponAbilityUtil.addCharge(stack, CHARGE_TAG, 1, 100);
        if (player.level() instanceof ServerLevel serverLevel) syncOwnedWeapons(serverLevel, player, stack);
    }

    public static boolean activateHeldSpecial(Player player) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || player.level().isClientSide() || !(player.level() instanceof ServerLevel serverLevel)) return false;
        ItemStack stack = player.getOffhandItem();
        if (!(stack.getItem() instanceof NullWeaponItem item) || player.getCooldowns().isOnCooldown(new net.minecraft.world.item.ItemStack(item)) || getCharge(stack) < 20) return false;
        syncOwnedWeapons(serverLevel, player, stack);
        List<NullWeapon> weapons = getOwnedWeapons(serverLevel, player);
        if (weapons.isEmpty()) return false;
        LivingEntity target = VanillaWeaponAbilityUtil.findCommandTarget(player, 24.0D);
        for (NullWeapon weapon : weapons) weapon.releaseForTicks(target, RELEASE_DURATION_TICKS);
        long releaseUntil = player.level().getGameTime() + RELEASE_DURATION_TICKS;
        LegacyItemData.update(stack, tag -> {
            tag.putLong(RELEASE_UNTIL_TAG, releaseUntil);
            tag.remove(RECOVERY_UNTIL_TAG);
        });
        VanillaWeaponAbilityUtil.swingOffHand(player);
        VanillaWeaponAbilityUtil.damageHeldItem(player, InteractionHand.OFF_HAND, 1);
        player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), RELEASE_DURATION_TICKS);
        new DelayedTask(RELEASE_DURATION_TICKS) {
            @Override public void run() {
                if (!player.isAlive() || player.isRemoved()) return;
                if (player.level() instanceof ServerLevel level) {
                    for (NullWeapon weapon : getOwnedWeapons(level, player)) weapon.stopRelease();
                    ItemStack releasedStack = findReleasedWeapon(player, releaseUntil);
                    if (!releasedStack.isEmpty()) {
                        setCharge(releasedStack, 0);
                        LegacyItemData.update(releasedStack, tag -> {
                            tag.remove(RELEASE_UNTIL_TAG);
                            tag.putLong(RECOVERY_UNTIL_TAG, player.level().getGameTime() + RECOVERY_COOLDOWN_TICKS);
                        });
                    }
                    if (player.getOffhandItem() == releasedStack) syncOwnedWeapons(level, player, releasedStack);
                    else discardOwnedWeapons(level, player);
                }
                player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), RECOVERY_COOLDOWN_TICKS);
            }
        };
        return true;
    }

    @Override
    public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slot = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        super.inventoryTick(stack, level, entity, equipmentSlot);
        if (level.isClientSide() || !(entity instanceof Player player) || !(level instanceof ServerLevel serverLevel)) return;
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled()) {
            setCharge(stack, 0);
            discardOwnedWeapons(serverLevel, player);
            return;
        }
        long now = level.getGameTime();
        CompoundTag tag = LegacyItemData.get(stack);
        long releaseUntil = tag == null ? 0L : tag.getLongOr(RELEASE_UNTIL_TAG, 0L);
        long recoveryUntil = tag == null ? 0L : tag.getLongOr(RECOVERY_UNTIL_TAG, 0L);
        if (releaseUntil > 0L && now >= releaseUntil) {
            setCharge(stack, 0);
            for (NullWeapon weapon : getOwnedWeapons(serverLevel, player)) weapon.stopRelease();
            if (recoveryUntil <= now) {
                recoveryUntil = now + RECOVERY_COOLDOWN_TICKS;
            }
            long finalRecoveryUntil = recoveryUntil;
            LegacyItemData.update(stack, data -> {
                data.remove(RELEASE_UNTIL_TAG);
                data.putLong(RECOVERY_UNTIL_TAG, finalRecoveryUntil);
            });
        }
        long cooldownUntil = releaseUntil > now ? releaseUntil : recoveryUntil;
        long remaining = cooldownUntil - now;
        if (remaining > 0L && player.getCooldowns().getCooldownPercent(new net.minecraft.world.item.ItemStack(stack.getItem()), 0.0F) <= 0.0F) {
            player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(stack.getItem()), (int)Math.min(Integer.MAX_VALUE, remaining));
        } else if (recoveryUntil > 0L && recoveryUntil <= now) {
            LegacyItemData.update(stack, data -> data.remove(RECOVERY_UNTIL_TAG));
        }
        if (player.getOffhandItem() != stack) {
            setCharge(stack, 0);
            discardOwnedWeapons(serverLevel, player);
            return;
        }
        if (player.getOffhandItem() == stack) syncOwnedWeapons(serverLevel, player, stack);
    }

    private static ItemStack findReleasedWeapon(Player player, long releaseUntil) {
        for (ItemStack candidate : player.getInventory().getNonEquipmentItems()) {
            if (hasReleaseMarker(candidate, releaseUntil)) return candidate;
        }
        ItemStack offhand = player.getOffhandItem();
        if (hasReleaseMarker(offhand, releaseUntil)) return offhand;
        return ItemStack.EMPTY;
    }

    private static boolean hasReleaseMarker(ItemStack stack, long releaseUntil) {
        if (!(stack.getItem() instanceof NullWeaponItem)) return false;
        CompoundTag tag = LegacyItemData.get(stack);
        return tag != null && tag.getLongOr(RELEASE_UNTIL_TAG, 0L) == releaseUntil;
    }

    private static void syncOwnedWeapons(ServerLevel level, Player player, ItemStack stack) {
        int charge = getCharge(stack);
        ensureOwnedWeapon(level, player, "NullPickaxeUUID", AnnoyingVillagersModEntities.NULL_PICKAXE.get());
        if (charge >= 40) ensureOwnedWeapon(level, player, "NullHoeUUID", AnnoyingVillagersModEntities.NULL_HOE.get());
        if (charge >= 60) ensureOwnedWeapon(level, player, "NullAxeUUID", AnnoyingVillagersModEntities.NULL_AXE.get());
        if (charge >= 80) ensureOwnedWeapon(level, player, "NullShovelUUID", AnnoyingVillagersModEntities.NULL_SHOVEL.get());
        if (charge >= 100) ensureOwnedWeapon(level, player, "NullSwordUUID", AnnoyingVillagersModEntities.NULL_SWORD.get());
        discardOwnedWeaponAboveCharge(level, player, "NullHoeUUID", charge, 40);
        discardOwnedWeaponAboveCharge(level, player, "NullAxeUUID", charge, 60);
        discardOwnedWeaponAboveCharge(level, player, "NullShovelUUID", charge, 80);
        discardOwnedWeaponAboveCharge(level, player, "NullSwordUUID", charge, 100);
    }

    private static void discardOwnedWeaponAboveCharge(ServerLevel level, Player player, String key, int charge, int requiredCharge) {
        if (charge >= requiredCharge) return;
        NullWeapon weapon = getOwnedWeapon(level, player, key);
        if (weapon != null) weapon.discard();
        player.getPersistentData().remove(key);
    }

    private static void ensureOwnedWeapon(ServerLevel level, Player player, String key, EntityType<? extends NullWeapon> type) {
        NullWeapon existing = getOwnedWeapon(level, player, key);
        if (existing != null && existing.isAlive() && !existing.isRemoved()) return;
        // During login the player inventory may tick before nearby saved entities are
        // inserted into ServerLevel's UUID index. Preserve their UUID briefly instead
        // of spawning a replacement in that load-order window.
        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(player.getPersistentData(), key)
                && level.getGameTime() < player.getPersistentData().getLongOr(LOGIN_RECONCILE_UNTIL_TAG, 0L)) return;
        NullWeapon weapon = type.create(level, net.minecraft.world.entity.EntitySpawnReason.TRIGGERED);
        if (weapon == null) return;
        weapon.setPlayer(player);
        weapon.setPlayerUUID(player.getUUID());
        weapon.snapTo(player.getX(), player.getY() + 1.5D, player.getZ(), player.getYRot(), 0.0F);
        level.addFreshEntity(weapon);
        com.pla.annoyingvillagers.util.LegacyNbt.putUUID(player.getPersistentData(), key, weapon.getUUID());
    }

    @Nullable
    private static NullWeapon getOwnedWeapon(ServerLevel level, Player player, String key) {
        if (!com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(player.getPersistentData(), key)) return null;
        Entity entity = level.getEntity(com.pla.annoyingvillagers.util.LegacyNbt.getUUID(player.getPersistentData(), key));
        if (entity instanceof NullWeapon weapon && player.getUUID().equals(weapon.getPlayerUUID())) return weapon;
        if (entity == null && level.getGameTime() < player.getPersistentData().getLongOr(LOGIN_RECONCILE_UNTIL_TAG, 0L)) return null;
        player.getPersistentData().remove(key);
        return null;
    }

    private static List<NullWeapon> getOwnedWeapons(ServerLevel level, Player player) {
        List<NullWeapon> result = new ArrayList<>();
        for (String key : OWNED_KEYS) {
            NullWeapon weapon = getOwnedWeapon(level, player, key);
            if (weapon != null) result.add(weapon);
        }
        return result;
    }

    public static void discardOwnedWeapons(ServerLevel level, Player player) {
        for (String key : OWNED_KEYS) {
            NullWeapon weapon = getOwnedWeapon(level, player, key);
            if (weapon != null) weapon.discard();
            player.getPersistentData().remove(key);
        }
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) { return RigCombatStyle.NULL_HEROBRINE; }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) { return true; }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, display, list, tooltipflag);
        list.accept(Component.translatable("tooltip.annoyingvillagers.null_weapon_full"));
    }
}
