package com.pla.annoyingvillagers.compat;

import com.pla.annoyingvillagers.entity.BbqEntity;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.VillagerArmyEntity;
import com.pla.annoyingvillagers.entity.AlexEntity;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.ChrisEntity;
import com.pla.annoyingvillagers.entity.EliteHerobrineKnockedEntity;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.entity.JevEntity;
import com.pla.annoyingvillagers.entity.LowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.SteveEntity;
import com.pla.annoyingvillagers.entity.goal.EscapeAvoidGoal;
import com.pla.smart_npc.entity.PlayerNpcEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
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

    public static void bbqEscapeAvoid(BbqEntity self) {
        self.goalSelector.addGoal(1, new EscapeAvoidGoal<>(self, PlayerNpcEntity.class, 12.0F, 2.0D, 2.0D));
    }

    public static void gregAvoid(HerobrineGregEntity self) {
        self.goalSelector.addGoal(2, new AvoidEntityGoal<>(self, PlayerNpcEntity.class, 12.0F, 1.0D, 1.35D));
    }

    public static boolean isSmartNpc(Entity mob) {
        return mob instanceof PlayerNpcEntity;
    }

    public static void copyPlayerNpcIdentity(Entity entity, LowHerobrineCloneEntity lowHerobrineCloneEntity) {
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) return;

        String username = playerNpcEntity.getUsername().getCombinedNames();
        if (username != null && !username.isBlank()) lowHerobrineCloneEntity.setUsername(username);
        var profile = playerNpcEntity.getProfile();
        if (profile != null) lowHerobrineCloneEntity.setProfile(profile);
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
        return target instanceof SteveEntity
                || target instanceof AngrySteveEntity
                || target instanceof AlexEntity
                || target instanceof ChrisEntity
                || target instanceof JevEntity;
    }

    public static boolean isMonsterTarget(LivingEntity target) {
        return target instanceof HerobrineMob
                || target instanceof HerobrineGregEntity
                || target instanceof LowHerobrineCloneEntity
                || target instanceof LowShadowHerobrineCloneEntity
                || target instanceof BlueDemonEntity
                || target instanceof EliteHerobrineKnockedEntity;
    }

    public static boolean isVillagerTarget(LivingEntity target) {
        return target instanceof VillagerArmyEntity;
    }

    public static boolean isHighDangerThreat(LivingEntity target) {
        return target instanceof HerobrineMob
                || target instanceof HerobrineGregEntity
                || target instanceof LowHerobrineCloneEntity
                || target instanceof LowShadowHerobrineCloneEntity
                || target instanceof VillagerArmyEntity;
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
