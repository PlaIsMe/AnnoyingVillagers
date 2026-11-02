package com.pla.annoyinglib.compat;

import net.minecraftforge.fml.ModList;
import com.pla.smart_npc.entity.PlayerNpcEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class SmartNpc {
    public static void clearPlayerNpcItems(Entity entity, InteractionHand chosenHand) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            if (chosenHand == InteractionHand.MAIN_HAND) {
                playerNpcEntity.setMainWeaponItem(ItemStack.EMPTY);
            } else {
                playerNpcEntity.setOffWeaponItem(ItemStack.EMPTY);
            }
        }
    }

    public static void disarmMainWeapon(Entity entity, ItemStack movedStack) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setOffWeaponItem(ItemStack.EMPTY);
            playerNpcEntity.setMainWeaponItem(movedStack.copy());
            playerNpcEntity.setMainWeaponDisarmed(false);
        }
    }

    public static void clearCachedWeapon(Entity entity, InteractionHand hand) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            if (hand == InteractionHand.MAIN_HAND) {
                playerNpcEntity.setMainWeaponItem(ItemStack.EMPTY);
                playerNpcEntity.setMainWeaponDisarmed(true);
            } else {
                playerNpcEntity.setOffWeaponItem(ItemStack.EMPTY);
            }
        }
    }

    public static void bbqEscapeAvoid(PathfinderMob self) {
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.addSmartNpcEscapeGoal(self);
    }

    public static void gregAvoid(PathfinderMob self) {
        if (ModList.get().isLoaded("herobrine_vessels")) HerobrineVessels.gregAvoid(self);
    }

    public static boolean isSmartNpc(Entity mob) {
        return mob instanceof PlayerNpcEntity;
    }

    public static void copyPlayerNpcIdentity(Entity entity, Entity clone) {
        if (ModList.get().isLoaded("herobrine_vessels")) HerobrineVessels.copyPlayerNpcIdentity(entity, clone);
    }

    public static SimpleContainer getInventory(Entity entity) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            return playerNpcEntity.getInventory();
        }
        return null;
    }

    public static boolean isHealing(Entity entity) {
        return entity instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.isHealing();
    }

    public static void setHealing(Entity entity, boolean healing) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setHealing(healing);
        }
    }

    public static void swapToBow(Entity entity) {
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return;
        }

        if (playerNpcEntity.getMainHandItem().getItem() instanceof BowItem) {
            playerNpcEntity.setUseBow(true);
            return;
        }

        ItemStack bow = playerNpcEntity.consumeInventoryItem(
                stack -> stack.getItem() instanceof BowItem,
                1
        ).orElse(ItemStack.EMPTY);
        if (bow.isEmpty()) {
            return;
        }

        stashCurrentMainHand(playerNpcEntity);
        playerNpcEntity.setUseBow(true);
        playerNpcEntity.setMainHandItemForAi(bow.copy());
    }

    private static void stashCurrentMainHand(PlayerNpcEntity playerNpcEntity) {
        ItemStack currentMainHand = playerNpcEntity.getMainHandItem();
        if (currentMainHand.isEmpty() || currentMainHand.getItem() instanceof BowItem) {
            return;
        }

        if (ItemStack.isSameItemSameTags(currentMainHand, playerNpcEntity.getMainWeaponItem())
                || ItemStack.isSameItemSameTags(currentMainHand, playerNpcEntity.getOffWeaponItem())) {
            return;
        }

        ItemStack stashedStack = currentMainHand.copy();
        if (playerNpcEntity.promoteMainWeaponItem(stashedStack)) {
            return;
        }

        if (!com.pla.smart_npc.util.InventoryUtils.addItem(playerNpcEntity, stashedStack)) {
            playerNpcEntity.spawnAtLocation(stashedStack);
        }
    }

    public static boolean isPlayerLikeTarget(LivingEntity target) {
        return (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isSteveEntity(target))
                || (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isAngrySteveEntity(target))
                || (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isAlexEntity(target))
                || (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isChrisEntity(target))
                || (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isJevEntity(target));
    }

    public static boolean isMonsterTarget(LivingEntity target) {
        return (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonEntity(target)) || (ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.isMonsterTarget(target));
    }

    public static boolean isVillagerTarget(LivingEntity target) {
        return (ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isVillagerArmyEntity(target));
    }

    public static boolean isHighDangerThreat(LivingEntity target) {
        if (ModList.get().isLoaded("herobrine_vessels")) return HerobrineVessels.isHighDangerThreat(target);
        return (ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isVillagerArmyEntity(target));
    }

    public static void increaseStunEscapeCooldown(Entity entity) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            int currentValue = playerNpcEntity.getStunEscapeCooldown();
            if (currentValue < 100) {
                playerNpcEntity.setStunEscapeCooldown(currentValue + 20);
            }
        }
    }

    public static void targetPlayerNpc(Mob mob, int priority) {
        mob.targetSelector.addGoal(priority, new NearestAttackableTargetGoal<>(mob, PlayerNpcEntity.class, true, false));
    }
}
