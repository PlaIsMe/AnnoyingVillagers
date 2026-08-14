package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.compat.SmartNpc;
import com.pla.annoyingvillagers.entity.ItemProjectile;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiamondAttractorSwordItem extends SwordItem {

    public DiamondAttractorSwordItem() {
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
                return Ingredient.of(new ItemStack(Items.DIAMOND));
            }
        }, 3, -2.8F, (new Properties()));
    }

    public static void pullWeapons(LivingEntity owner) {
        Level rawLevel = owner.level();

        if (!(rawLevel instanceof ServerLevel level)) {
            return;
        }

        double radius = 7;
        AABB area = owner.getBoundingBox().inflate(radius);

        pullDroppedWeapons(level, owner, area);
        pullHeldWeapons(level, owner, area);
    }

    private static void pullDroppedWeapons(ServerLevel level, LivingEntity owner, AABB area) {
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(
                ItemEntity.class,
                area,
                itemEntity -> itemEntity.isAlive() && CommonUtil.isPullableWeapon(itemEntity.getItem())
        );

        for (ItemEntity itemEntity : itemEntities) {
            ItemStack stackInWorld = itemEntity.getItem();

            if (stackInWorld.isEmpty()) {
                continue;
            }

            if (CommonUtil.isBlacklistedWeapon(stackInWorld)) {
                CommonUtil.fallBackOnBlackListWeapon(owner, itemEntity, stackInWorld);
                continue;
            }

            ItemStack pulledStack = stackInWorld.copy();
            pulledStack.setCount(1);

            ItemStack remainder = stackInWorld.copy();
            remainder.shrink(1);

            if (remainder.isEmpty()) {
                itemEntity.discard();
            } else {
                itemEntity.setItem(remainder);
            }

            spawnPulledWeapon(level, owner, pulledStack, itemEntity.position());
        }
    }

    private static void pullHeldWeapons(ServerLevel level, LivingEntity owner, AABB area) {
        List<LivingEntity> livingEntities = level.getEntitiesOfClass(
                LivingEntity.class,
                area,
                entity -> entity.isAlive()
                        && entity != owner
        );

        for (LivingEntity target : livingEntities) {
            CommonUtil.pullEntityTowardCaster(target, owner);
            LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
            if (targetPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(targetPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (!EpicfightUtil.isLongHitAnimation(dynamicAnimation, targetPatch)) {
                    targetPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_FORWARD, 0.0F);
                }
            }
            if (CommonUtil.entityCanBeDisarmed(target)) {
                List<InteractionHand> candidateHands = new ArrayList<>(2);

                ItemStack mainHand = target.getMainHandItem();
                ItemStack offHand = target.getOffhandItem();

                if (CommonUtil.isPullableWeapon(mainHand)) {
                    candidateHands.add(InteractionHand.MAIN_HAND);
                }

                if (CommonUtil.isPullableWeapon(offHand)) {
                    candidateHands.add(InteractionHand.OFF_HAND);
                }

                if (candidateHands.isEmpty()) {
                    continue;
                }

                InteractionHand chosenHand = candidateHands.get(
                        level.random.nextInt(candidateHands.size())
                );

                ItemStack chosenStack = target.getItemInHand(chosenHand);

                if (CommonUtil.isBlacklistedWeapon(chosenStack)) {
                    CommonUtil.fallBackOnBlackListWeapon(owner, target, chosenStack);
                    continue;
                }

                ItemStack pulledStack = chosenStack.copy();
                target.setItemInHand(chosenHand, ItemStack.EMPTY);
                if (target instanceof AVNpc avNpc) {
                    if (chosenHand == InteractionHand.MAIN_HAND) {
                        avNpc.setMainWeaponItem(ItemStack.EMPTY);
                    } else {
                        avNpc.setOffWeaponItem(ItemStack.EMPTY);
                    }
                }

                if (ModList.get().isLoaded("smart_npc")) {
                    SmartNpc.clearPlayerNpcItems(target, chosenHand);
                }
                Vec3 spawnPos = getHeldWeaponSpawnPos(target);
                spawnPulledWeapon(level, owner, pulledStack, spawnPos);
            }
        }
    }

    private static Vec3 getHeldWeaponSpawnPos(LivingEntity target) {
        return target.getEyePosition().subtract(0.0D, 0.25D, 0.0D);
    }

    private static void spawnPulledWeapon(
            ServerLevel level,
            LivingEntity owner,
            ItemStack stack,
            Vec3 spawnPos
    ) {
        if (stack.isEmpty()) {
            return;
        }

        ItemProjectile projectile =
                new ItemProjectile(
                        level,
                        owner,
                        stack,
                        spawnPos.add(0.0D, 0.15D, 0.0D)
                );

        level.addFreshEntity(projectile);
    }
}
