package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.entity.AlexEntity;
import com.pla.annoyingvillagers.entity.ChrisEntity;
import com.pla.annoyingvillagers.entity.SteveEntity;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber
public class TotemUsingEvent {
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
                        diamondSword.enchant(Enchantments.SHARPNESS, 5);
                        diamondSword.enchant(Enchantments.SMITE, 5);
                        steveEntity.setItemInHand(InteractionHand.OFF_HAND, diamondSword);
                        steveEntity.setOffWeaponItem(diamondSword);
                        steveEntity.setState(1);
                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (!entity.level().isClientSide() && entity.getServer() != null && livingEntityPatch != null) {
                            livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.GUARD_BREAK_ATTACK, 0.0F);
                        }
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
                        compressedDiamondHelmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                        compressedDiamondHelmet.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
                        compressedDiamondHelmet.enchant(Enchantments.FIRE_PROTECTION, 5);
                        compressedDiamondHelmet.enchant(Enchantments.BLAST_PROTECTION, 5);
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
                        compressedDiamondChestplate.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                        compressedDiamondChestplate.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
                        compressedDiamondChestplate.enchant(Enchantments.FIRE_PROTECTION, 5);
                        compressedDiamondChestplate.enchant(Enchantments.BLAST_PROTECTION, 5);
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
                        diamondSword.enchant(Enchantments.SHARPNESS, 5);
                        diamondSword.enchant(Enchantments.FIRE_ASPECT, 2);
                        diamondSword.enchant(Enchantments.KNOCKBACK, 2);
                        diamondSword.enchant(Enchantments.UNBREAKING, 5);
                        alexEntity.setItemInHand(InteractionHand.OFF_HAND, diamondSword);
                        alexEntity.setItemInHand(InteractionHand.MAIN_HAND, diamondSword);
                        alexEntity.setOffWeaponItem(diamondSword);
                        alexEntity.setMainWeaponItem(diamondSword);
                        alexEntity.setState(1);
                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (!entity.level().isClientSide() && entity.getServer() != null && livingEntityPatch != null) {
                            livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.GUARD_BREAK_ATTACK, 0.0F);
                        }
                    }
                };
            }

            if (entity instanceof ChrisEntity chrisEntity && entity.level() instanceof ServerLevel) {
                new DelayedTask(1) {
                    @Override
                    public void run() {
                        chrisEntity.setHealth(chrisEntity.getMaxHealth());
                        ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
                        diamondSword.enchant(Enchantments.KNOCKBACK, 5);
                        diamondSword.enchant(Enchantments.SHARPNESS, 5);
                        diamondSword.enchant(Enchantments.UNBREAKING, 5);
                        chrisEntity.setItemInHand(InteractionHand.OFF_HAND, diamondSword);
                        chrisEntity.setOffWeaponItem(diamondSword);
                        chrisEntity.setState(1);
                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (!entity.level().isClientSide() && entity.getServer() != null && livingEntityPatch != null) {
                            livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.GUARD_BREAK_ATTACK, 0.0F);
                        }
                    }
                };
            }
        }
    }
}
