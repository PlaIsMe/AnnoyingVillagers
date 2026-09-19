package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.GolemArms;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigOrientedBox;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorController;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationController;
import com.pla.annoyingvillagers.entity.goal.HerobrineEscapeHoleGoal;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class RigMobClashBladeEvent {
    private static final double FRONT_DOT_THRESHOLD = 0.0D;
    private static final double CLASH_RECOIL = 0.2D;

    private RigMobClashBladeEvent() {
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingAttack(LivingIncomingDamageEvent event) {
        if (event.isCanceled()) {
            return;
        }

        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) {
            return;
        }

        DamageSource damageSource = event.getSource();
        Entity attacker = damageSource.getEntity();
        if (attacker == null || attacker == event.getEntity() || !canRegularClash(damageSource)) {
            return;
        }

        List<RigOrientedBox> armorBoxes = ObsidianArmorController.activeAttackCollisionBoxes(event.getEntity());
        if (!armorBoxes.isEmpty() && clashesWithArmor(armorBoxes, attacker, damageSource.getDirectEntity(), serverLevel)) {
            event.setCanceled(true);
            applyClashRecoil(attacker);
            applyClashRecoil(event.getEntity());
            CommonUtil.damageBlockedForce(event.getEntity(), attacker, serverLevel);
            return;
        }

        // GolemArms are intentionally non-attackable entities, so an incoming attack normally
        // reaches their owner instead. During an Arms attack window, compare the actual oriented
        // attack boxes. If an enemy rig/special attack box meets an Arms box first, treat that as
        // a weapon clash and cancel the damage to the owner.
        GolemArms clashingArms = findClashingArms(event.getEntity(), attacker, serverLevel);
        if (clashingArms != null) {
            event.setCanceled(true);
            applyClashRecoil(attacker);
            applyClashRecoil(event.getEntity());
            CommonUtil.damageBlockedForce(clashingArms, attacker, serverLevel);
            return;
        }

        if (!(event.getEntity() instanceof Mob defender)
                || !hasClashWeapon(defender)
                || !RigAnimationController.isInActiveAttackWindow(defender)
                || !isAttackerInFront(defender, attacker)) {
            return;
        }

        event.setCanceled(true);
        applyClashRecoil(attacker);
        applyClashRecoil(defender);
        CommonUtil.damageBlockedForce(defender, attacker, serverLevel);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        RigAnimationController.clearActiveAnimations();
        SpecialAnimationController.clearActiveAnimations();
        ObsidianArmorController.clearAll();
        HerobrineEscapeHoleGoal.clearActivePillarCycles();
    }

    private static GolemArms findClashingArms(LivingEntity defender, Entity attacker, ServerLevel serverLevel) {
        List<RigOrientedBox> attackingBoxes = activeAttackBoxes(attacker, serverLevel);
        if (attackingBoxes.isEmpty()) return null;

        for (GolemArms arms : serverLevel.getEntitiesOfClass(
                GolemArms.class,
                defender.getBoundingBox().inflate(3.0D),
                candidate -> candidate.isAlive() && !candidate.isRemoved() && candidate.getOwnerLiving() == defender
        )) {
            List<RigOrientedBox> armsBoxes = SpecialAnimationController.activeAttackCollisionBoxes(arms);
            if (armsBoxes.isEmpty()) continue;
            if (intersectsAny(armsBoxes, attackingBoxes)) return arms;
        }
        return null;
    }

    private static List<RigOrientedBox> activeAttackBoxes(Entity attacker, ServerLevel serverLevel) {
        List<RigOrientedBox> boxes = new ArrayList<>();
        if (attacker instanceof Mob attackingMob) {
            boxes.addAll(RigAnimationController.activeAttackCollisionBoxes(attackingMob));
            boxes.addAll(SpecialAnimationController.activeAttackCollisionBoxes(attackingMob));
        }

        // GolemArms damage is credited to its player owner, so the DamageSource entity is the
        // player rather than the floating Arms entity. Recover those active Arms boxes here too.
        if (attacker instanceof LivingEntity livingAttacker) {
            boxes.addAll(ObsidianArmorController.activeAttackCollisionBoxes(livingAttacker));
            for (GolemArms arms : serverLevel.getEntitiesOfClass(
                    GolemArms.class,
                    livingAttacker.getBoundingBox().inflate(3.0D),
                    candidate -> candidate.isAlive() && !candidate.isRemoved() && candidate.getOwnerLiving() == livingAttacker
            )) {
                boxes.addAll(SpecialAnimationController.activeAttackCollisionBoxes(arms));
            }
        }
        return boxes.isEmpty() ? List.of() : List.copyOf(boxes);
    }

    private static boolean clashesWithArmor(List<RigOrientedBox> armorBoxes, Entity attacker, Entity directEntity, ServerLevel serverLevel) {
        List<RigOrientedBox> attackBoxes = activeAttackBoxes(attacker, serverLevel);
        if (intersectsAny(armorBoxes, attackBoxes)) return true;
        Entity contactEntity = directEntity == null ? attacker : directEntity;
        if (contactEntity == null) return false;
        for (RigOrientedBox armorBox : armorBoxes) if (armorBox.intersects(contactEntity.getBoundingBox())) return true;
        return false;
    }

    private static boolean intersectsAny(List<RigOrientedBox> first, List<RigOrientedBox> second) {
        for (RigOrientedBox a : first) {
            for (RigOrientedBox b : second) {
                if (a.intersects(b)) return true;
            }
        }
        return false;
    }

    private static boolean canRegularClash(DamageSource damageSource) {
        return !damageSource.is(DamageTypes.MAGIC)
                && !damageSource.is(DamageTypeTags.IS_EXPLOSION)
                && !damageSource.is(DamageTypes.ON_FIRE)
                && !damageSource.is(DamageTypes.IN_FIRE)
                && !damageSource.is(DamageTypes.FALL);
    }

    private static boolean hasClashWeapon(Mob defender) {
        ItemStack stack = defender.getItemInHand(InteractionHand.MAIN_HAND);
        return stack.getItem() instanceof SwordItem
                || stack.getItem() instanceof DiggerItem
                || stack.getItem() instanceof TridentItem;
    }

    private static boolean isAttackerInFront(Mob defender, Entity attacker) {
        Vec3 defenderLook = horizontal(defender.getViewVector(1.0F));
        Vec3 toAttacker = horizontal(attacker.position().subtract(defender.position()));

        return defenderLook.lengthSqr() > 1.0E-7D
                && toAttacker.lengthSqr() > 1.0E-7D
                && defenderLook.normalize().dot(toAttacker.normalize()) > FRONT_DOT_THRESHOLD;
    }

    private static Vec3 horizontal(Vec3 vector) {
        return new Vec3(vector.x, 0.0D, vector.z);
    }

    private static void applyClashRecoil(Entity entity) {
        Vec3 look = horizontal(entity.getLookAngle());
        if (look.lengthSqr() < 1.0E-7D) {
            return;
        }

        Vec3 recoil = look.normalize().scale(-CLASH_RECOIL);
        entity.setDeltaMovement(recoil.x, 0.0D, recoil.z);
        entity.hasImpulse = true;
        entity.hurtMarked = true;
    }
}
