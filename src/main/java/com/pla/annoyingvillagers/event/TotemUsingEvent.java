package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.util.EnchantmentUtil;
import com.pla.annoyingvillagers.entity.AlexEntity;
import com.pla.annoyingvillagers.entity.ChrisEntity;
import com.pla.annoyingvillagers.entity.SteveEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber
public class TotemUsingEvent {
    private static void playGuardBreakAttackAnimation(LivingEntity entity) {
//        Add this code in AV_EFM

//        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
//        if (!entity.level().isClientSide() && entity.getServer() != null && livingEntityPatch != null) {
//            livingEntityPatch.playAnimationSynchronized(AVAnimations.STUN_BACK, 0.0F);
//        }

        if (entity instanceof Mob mob && !entity.level().isClientSide() && entity.getServer() != null) {
            RigAnimationController.play(mob, RigAnimationId.STUN_BACK);
        }
    }

    @SubscribeEvent
    public static void onLivingUseTotem(LivingUseTotemEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack totem = event.getTotem();

        if (totem.is(Items.TOTEM_OF_UNDYING)) {
            if (entity instanceof SteveEntity steveEntity && entity.level() instanceof ServerLevel serverLevel) {
                new DelayedTask(1) {
                    @Override
                    public void run() {
                        steveEntity.setHealth(steveEntity.getMaxHealth());
                        ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
                        EnchantmentUtil.enchant(diamondSword, Enchantments.SHARPNESS, 5);
                        EnchantmentUtil.enchant(diamondSword, Enchantments.SMITE, 5);
                        steveEntity.setItemInHand(InteractionHand.OFF_HAND, diamondSword);
                        steveEntity.setOffWeaponItem(diamondSword);
                        steveEntity.setState(1);
                        playGuardBreakAttackAnimation(entity);
                    }
                };

                new DelayedTask(10) {
                    @Override
                    public void run() {
                        serverLevel.playSound(
                                null,
                                entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.ARMOR_EQUIP_DIAMOND,
                                SoundSource.NEUTRAL,
                                1.0F, 1.0F
                        );
                        ItemStack compressedDiamondHelmet = new ItemStack(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get());
                        EnchantmentUtil.enchant(compressedDiamondHelmet, Enchantments.PROTECTION, 5);
                        EnchantmentUtil.enchant(compressedDiamondHelmet, Enchantments.PROJECTILE_PROTECTION, 5);
                        EnchantmentUtil.enchant(compressedDiamondHelmet, Enchantments.FIRE_PROTECTION, 5);
                        EnchantmentUtil.enchant(compressedDiamondHelmet, Enchantments.BLAST_PROTECTION, 5);
                        steveEntity.setItemSlot(EquipmentSlot.HEAD, compressedDiamondHelmet);
                    }
                };

                new DelayedTask(20) {
                    @Override
                    public void run() {
                        serverLevel.playSound(
                                null,
                                entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.ARMOR_EQUIP_DIAMOND,
                                SoundSource.NEUTRAL,
                                1.0F, 1.0F
                        );
                        ItemStack compressedDiamondChestplate = new ItemStack(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get());
                        EnchantmentUtil.enchant(compressedDiamondChestplate, Enchantments.PROTECTION, 5);
                        EnchantmentUtil.enchant(compressedDiamondChestplate, Enchantments.PROJECTILE_PROTECTION, 5);
                        EnchantmentUtil.enchant(compressedDiamondChestplate, Enchantments.FIRE_PROTECTION, 5);
                        EnchantmentUtil.enchant(compressedDiamondChestplate, Enchantments.BLAST_PROTECTION, 5);
                        steveEntity.setItemSlot(EquipmentSlot.CHEST, compressedDiamondChestplate);
                    }
                };
            }

            if (entity instanceof AlexEntity alexEntity && entity.level() instanceof ServerLevel) {
                new DelayedTask(1) {
                    @Override
                    public void run() {
                        alexEntity.setHealth(alexEntity.getMaxHealth());
                        ItemStack diamondSword = new ItemStack(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get());
                        EnchantmentUtil.enchant(diamondSword, Enchantments.SHARPNESS, 5);
                        EnchantmentUtil.enchant(diamondSword, Enchantments.FIRE_ASPECT, 2);
                        EnchantmentUtil.enchant(diamondSword, Enchantments.KNOCKBACK, 2);
                        EnchantmentUtil.enchant(diamondSword, Enchantments.UNBREAKING, 5);
                        alexEntity.setItemInHand(InteractionHand.OFF_HAND, diamondSword);
                        alexEntity.setItemInHand(InteractionHand.MAIN_HAND, diamondSword);
                        alexEntity.setOffWeaponItem(diamondSword);
                        alexEntity.setMainWeaponItem(diamondSword);
                        alexEntity.setState(1);
                        playGuardBreakAttackAnimation(entity);
                    }
                };
            }

            if (entity instanceof ChrisEntity chrisEntity && entity.level() instanceof ServerLevel) {
                new DelayedTask(1) {
                    @Override
                    public void run() {
                        chrisEntity.setHealth(chrisEntity.getMaxHealth());
                        ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
                        EnchantmentUtil.enchant(diamondSword, Enchantments.KNOCKBACK, 5);
                        EnchantmentUtil.enchant(diamondSword, Enchantments.SHARPNESS, 5);
                        EnchantmentUtil.enchant(diamondSword, Enchantments.UNBREAKING, 5);
                        chrisEntity.setItemInHand(InteractionHand.OFF_HAND, diamondSword);
                        chrisEntity.setOffWeaponItem(diamondSword);
                        chrisEntity.setState(1);
                        playGuardBreakAttackAnimation(entity);
                    }
                };
            }
        }
    }
}
