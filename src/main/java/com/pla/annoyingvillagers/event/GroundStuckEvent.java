package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.potion.GroundStuckMobEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GroundStuckEvent {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (!(target.level() instanceof ServerLevel level) || event.getAmount() <= 0.0F) return;

        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
        boolean hasEffect = target.hasEffect(AnnoyingVillagersModMobEffects.GROUND_STUCK.get());
        boolean stuckNbt = target.getPersistentData().getBoolean(GroundStuckMobEffect.NBT_STUCK);
        boolean sledgehammerHit = attacker != null
                && attacker.getMainHandItem().is(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get());

        // RigAnimationController invokes its onHit hook after hurt() succeeds, so the
        // first Sledgehammer ULT impact applies Ground Stuck without being doubled here.
        boolean wasStuck = hasEffect && stuckNbt;
        if (!wasStuck) {
            return;
        }

        event.setAmount(event.getAmount() * 2.0F);
        MobEffectInstance instance = target.getEffect(AnnoyingVillagersModMobEffects.GROUND_STUCK.get());
        int amplifier = instance == null ? 0 : instance.getAmplifier();
        float chance = GroundStuckMobEffect.getKnockoutChance(amplifier);
        float roll = level.random.nextFloat();

        if (sledgehammerHit && roll < chance) {
            GroundStuckMobEffect.knockOut(target, attacker);
            return;
        }

        GroundStuckMobEffect.playGroundHitReaction(target);
    }

    @SubscribeEvent
    public static void onEnderPearl(EntityTeleportEvent.EnderPearl event) {
        if (event.getPlayer().hasEffect(AnnoyingVillagersModMobEffects.GROUND_STUCK.get())) {
            GroundStuckMobEffect.clear(event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ServerLevel level)) return;
        CompoundTag tag = entity.getPersistentData();

        if (tag.getBoolean(GroundStuckMobEffect.NBT_STUCK)
                && !entity.hasEffect(AnnoyingVillagersModMobEffects.GROUND_STUCK.get())) {
            GroundStuckMobEffect.clear(entity);
        }

        int ticks = tag.getInt(GroundStuckMobEffect.NBT_KNOCKOUT_TICKS);
        if (ticks <= 0) return;
        if (ticks < GroundStuckMobEffect.KNOCKOUT_TICKS - 4
                && (entity.onGround() || entity.horizontalCollision || entity.verticalCollision)) {
            clearKnockout(entity);
            return;
        }

        tag.putInt(GroundStuckMobEffect.NBT_KNOCKOUT_TICKS, ticks - 1);
        entity.xxa = 0.0F;
        entity.yya = 0.0F;
        entity.zza = 0.0F;
        if (entity instanceof Mob mob) mob.getNavigation().stop();
        entity.hasImpulse = true;
        entity.hurtMarked = true;
        hurtProjectileCollisions(level, entity, tag);
        if (ticks <= 1) clearKnockout(entity);
    }

    private static void hurtProjectileCollisions(ServerLevel level, LivingEntity projectile, CompoundTag tag) {
        Vec3 motion = projectile.getDeltaMovement();
        if (motion.lengthSqr() < 0.01D) return;

        UUID sourceUuid = tag.hasUUID(GroundStuckMobEffect.NBT_KNOCKOUT_SOURCE)
                ? tag.getUUID(GroundStuckMobEffect.NBT_KNOCKOUT_SOURCE)
                : null;
        AABB hitBox = projectile.getBoundingBox().expandTowards(motion).inflate(0.3D);
        for (LivingEntity target : level.getEntitiesOfClass(
                LivingEntity.class,
                hitBox,
                other -> other.isAlive() && other != projectile
                        && (sourceUuid == null || !other.getUUID().equals(sourceUuid)))) {
            if (target.invulnerableTime > 0) continue;
            AttributeInstance attackDamage = projectile.getAttribute(Attributes.ATTACK_DAMAGE);
            float damage = attackDamage == null ? 4.0F : (float) Math.max(4.0D, attackDamage.getValue() * 0.75D);
            DamageSource damageSource = projectile instanceof Player player
                    ? level.damageSources().playerAttack(player)
                    : level.damageSources().mobAttack(projectile);
            if (target.hurt(damageSource, damage)) {
                target.knockback(0.8D, -motion.x, -motion.z);
            }
        }
    }

    private static void clearKnockout(LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData();
        tag.remove(GroundStuckMobEffect.NBT_KNOCKOUT_TICKS);
        tag.remove(GroundStuckMobEffect.NBT_KNOCKOUT_SOURCE);
        GroundStuckMobEffect.syncKnockout(entity, 0);
    }
}
