package com.pla.annoyinglib.compat;
import com.pla.villager_kingdom.item.AdvancedFishingRod;
import net.minecraft.client.renderer.entity.EntityRenderers;
import com.pla.villager_kingdom.client.renderer.SpriteArrowRenderer;
import java.util.Map;
import net.minecraft.world.entity.projectile.AbstractArrow;
import com.pla.villager_kingdom.init.VillagerKingdomModEntities;
import com.pla.villager_kingdom.entity.EnchantedArrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.pla.villager_kingdom.network.ServerboundDestructionEyeAttack;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import com.pla.villager_kingdom.init.VillagerKingdomModItems;
import com.pla.annoyinglib.clazz.AVNpc;
import com.pla.annoyinglib.event.RigMobClashBladeEvent;
import com.pla.annoyinglib.rig.RigOrientedBox;
import com.pla.annoyinglib.specialanimation.SpecialAnimationController;
import com.pla.villager_kingdom.clazz.VillagerArmyEntity;
import com.pla.villager_kingdom.entity.AvGolem;
import com.pla.villager_kingdom.entity.BlueVillagerKnightEntity;
import com.pla.villager_kingdom.entity.GolemArms;
import com.pla.villager_kingdom.entity.GreenVillagerKnightEntity;
import com.pla.villager_kingdom.entity.PurpleVillagerKnightEntity;
import com.pla.villager_kingdom.entity.RedVillagerKnightEntity;
import com.pla.villager_kingdom.entity.VillagerScoutCaptainEntity;
import com.pla.villager_kingdom.entity.VillagerScoutEntity;
import com.pla.villager_kingdom.item.DestructionEyeItem;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

/** Optional content bridge. Call only when its mod is loaded. */
public final class VillagerKingdom {
    private VillagerKingdom() {}

    public static boolean isVillagerScoutCaptainEntity(Object value) {
        return value instanceof VillagerScoutCaptainEntity;
    }

    public static boolean isRedVillagerKnightEntity(Object value) {
        return value instanceof RedVillagerKnightEntity;
    }

    public static boolean isBlueVillagerKnightEntity(Object value) {
        return value instanceof BlueVillagerKnightEntity;
    }

    public static boolean isGreenVillagerKnightEntity(Object value) {
        return value instanceof GreenVillagerKnightEntity;
    }

    public static boolean isPurpleVillagerKnightEntity(Object value) {
        return value instanceof PurpleVillagerKnightEntity;
    }



    public static boolean isGolemArms(Object value) {
        return value instanceof GolemArms;
    }

    public static boolean isVillagerArmyEntity(Object value) {
        return value instanceof VillagerArmyEntity;
    }

    public static boolean isVillagerScoutEntity(Object value) {
        return value instanceof VillagerScoutEntity;
    }

    public static boolean isDestructionEyeItem(Object value) {
        return value instanceof DestructionEyeItem;
    }

    public static void enchantNpcBow(AVNpc npc, ItemStack bow) {
        if (npc instanceof VillagerScoutCaptainEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 1);
            bow.enchant(Enchantments.PUNCH_ARROWS, 1);
        }
        if (npc instanceof RedVillagerKnightEntity) {
            bow.enchant(Enchantments.FLAMING_ARROWS, 2);
        }
        if (npc instanceof BlueVillagerKnightEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
        }
        if (npc instanceof GreenVillagerKnightEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 1);
            bow.enchant(Enchantments.FLAMING_ARROWS, 1);
        }
        if (npc instanceof PurpleVillagerKnightEntity) {
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
        }
    }

    public static void registerGoalForHostileNpcTargets(PathfinderMob monster) {
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, VillagerScoutEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, VillagerScoutCaptainEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, RedVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, BlueVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, GreenVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, PurpleVillagerKnightEntity.class, true, false));
    }

    public static void registerGoalForBlueDemonNpcTargets(PathfinderMob monster) {
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, VillagerScoutEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, VillagerScoutCaptainEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, RedVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, BlueVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, GreenVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, PurpleVillagerKnightEntity.class, true, false));
    }

    public static void registerGoalForNeutralNpcTargets(PathfinderMob mob) {
        if (!(mob.getTarget() instanceof VillagerScoutEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, VillagerScoutEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof VillagerScoutCaptainEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, VillagerScoutCaptainEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof BlueVillagerKnightEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, BlueVillagerKnightEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof GreenVillagerKnightEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, GreenVillagerKnightEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof RedVillagerKnightEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, RedVillagerKnightEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof PurpleVillagerKnightEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, PurpleVillagerKnightEntity.class, 12.0F, 1.2D, 1.4D));
        }
    }

    public static LivingEntity armsOwner(Mob mob) {
        return mob instanceof GolemArms arms ? arms.getOwnerLiving() : mob;
    }

    public static void damageArmsItem(Mob mob) {
        if (mob instanceof GolemArms arms) DestructionEyeItem.damageForArmsHit(arms);
    }

    public static Mob findClashingArms(LivingEntity defender, Entity attacker, ServerLevel serverLevel) {
        List<RigOrientedBox> attackingBoxes = RigMobClashBladeEvent.activeAttackBoxes(attacker, serverLevel);
        if (attackingBoxes.isEmpty()) return null;

        for (GolemArms arms : serverLevel.getEntitiesOfClass(
                GolemArms.class,
                defender.getBoundingBox().inflate(3.0D),
                candidate -> candidate.isAlive() && !candidate.isRemoved() && candidate.getOwnerLiving() == defender
        )) {
            List<RigOrientedBox> armsBoxes = SpecialAnimationController.activeAttackCollisionBoxes(arms);
            if (armsBoxes.isEmpty()) continue;
            if (RigMobClashBladeEvent.intersectsAny(armsBoxes, attackingBoxes)) return arms;
        }
        return null;
    }

    public static void addArmsAttackBoxes(ServerLevel serverLevel, LivingEntity livingAttacker, List<RigOrientedBox> boxes) {
        for (GolemArms arms : serverLevel.getEntitiesOfClass(
                            GolemArms.class,
                            livingAttacker.getBoundingBox().inflate(3.0D),
                            candidate -> candidate.isAlive() && !candidate.isRemoved() && candidate.getOwnerLiving() == livingAttacker
                    )) {
                        boxes.addAll(SpecialAnimationController.activeAttackCollisionBoxes(arms));
                    }
    }

    public static boolean isAvGolem(Object value) {
        return value instanceof AvGolem;
    }
    public static Item getItems_VILLAGER_SCOUT_HELMET_FIX() {
        return VillagerKingdomModItems.VILLAGER_SCOUT_HELMET_FIX.get();
    }

    public static Item getItems_VILLAGER_SCOUT_HELMET() {
        return VillagerKingdomModItems.VILLAGER_SCOUT_HELMET.get();
    }

    public static Item getItems_BLUE_VILLAGER_KNIGHT_HELMET_FIX() {
        return VillagerKingdomModItems.BLUE_VILLAGER_KNIGHT_HELMET_FIX.get();
    }

    public static Item getItems_BLUE_VILLAGER_KNIGHT_HELMET() {
        return VillagerKingdomModItems.BLUE_VILLAGER_KNIGHT_HELMET.get();
    }

    public static Item getItems_RED_VILLAGER_KNIGHT_HELMET_FIX() {
        return VillagerKingdomModItems.RED_VILLAGER_KNIGHT_HELMET_FIX.get();
    }

    public static Item getItems_RED_VILLAGER_KNIGHT_HELMET() {
        return VillagerKingdomModItems.RED_VILLAGER_KNIGHT_HELMET.get();
    }

    public static Item getItems_GREEN_VILLAGER_KNIGHT_HELMET_FIX() {
        return VillagerKingdomModItems.GREEN_VILLAGER_KNIGHT_HELMET_FIX.get();
    }

    public static Item getItems_GREEN_VILLAGER_KNIGHT_HELMET() {
        return VillagerKingdomModItems.GREEN_VILLAGER_KNIGHT_HELMET.get();
    }

    public static Item getItems_PURPLE_VILLAGER_KNIGHT_HELMET_FIX() {
        return VillagerKingdomModItems.PURPLE_VILLAGER_KNIGHT_HELMET_FIX.get();
    }

    public static Item getItems_PURPLE_VILLAGER_KNIGHT_HELMET() {
        return VillagerKingdomModItems.PURPLE_VILLAGER_KNIGHT_HELMET.get();
    }

    public static Item getItems_HEATER_SHIELD() {
        return VillagerKingdomModItems.HEATER_SHIELD.get();
    }

    public static Item getItems_ENCHANTED_ARROW() {
        return VillagerKingdomModItems.ENCHANTED_ARROW.get();
    }
    public static void addCrazyNpcTargets(PathfinderMob mob) {
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, VillagerScoutEntity.class, false, false));
        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(mob, VillagerScoutCaptainEntity.class, false, false));
        mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(mob, RedVillagerKnightEntity.class, false, false));
        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(mob, BlueVillagerKnightEntity.class, false, false));
        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(mob, GreenVillagerKnightEntity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, PurpleVillagerKnightEntity.class, false, false));
    }

    public static void sendDestructionEyeAttack() {
        com.pla.villager_kingdom.VillagerKingdom.PACKET_HANDLER.sendToServer(new ServerboundDestructionEyeAttack());
    }

    public static void replaceArrowRenderers() {
        Map<EntityType<?>, EntityRendererProvider<?>> providers = EntityRenderers.PROVIDERS;
        if (providers.containsKey(EntityType.ARROW)) providers.put(EntityType.ARROW, (EntityRendererProvider) SpriteArrowRenderer::new);
        if (providers.containsKey(EntityType.SPECTRAL_ARROW)) providers.put(EntityType.SPECTRAL_ARROW, (EntityRendererProvider) SpriteArrowRenderer::new);
    }

    public static AbstractArrow createColoredArrow(Entity entity, int glintMode) {
        EnchantedArrowEntity arrow = new EnchantedArrowEntity(VillagerKingdomModEntities.ENCHANTED_ARROW.get(), entity.level());
        arrow.setColorGlint(glintMode);
        return arrow;
    }

    public static boolean isEnchantedArrowEntity(Object value) { return value instanceof EnchantedArrowEntity; }


    public static void addGregAvoidBlueVillagerKnightEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, BlueVillagerKnightEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidGreenVillagerKnightEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, GreenVillagerKnightEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidPurpleVillagerKnightEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, PurpleVillagerKnightEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidRedVillagerKnightEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, RedVillagerKnightEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidVillagerScoutCaptainEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, VillagerScoutCaptainEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidVillagerScoutEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, VillagerScoutEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static boolean isAdvancedFishingRod(Object value) { return value instanceof AdvancedFishingRod; }
}
