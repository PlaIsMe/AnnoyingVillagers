package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.rig.RigAnimationController;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class RigMobClashBladeEvent {
    private static final double FRONT_DOT_THRESHOLD = 0.0D;
    private static final double CLASH_RECOIL = 0.2D;

    private RigMobClashBladeEvent() {
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.isCanceled()) {
            return;
        }

        if (!(event.getEntity() instanceof Mob defender) || !(defender.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        DamageSource damageSource = event.getSource();
        Entity attacker = damageSource.getEntity();
        if (attacker == null
                || attacker == defender
                || !hasClashWeapon(defender)
                || !canRegularClash(damageSource)
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
