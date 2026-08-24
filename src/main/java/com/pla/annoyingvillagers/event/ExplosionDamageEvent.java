package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ExplosionDamageEvent {
    private ExplosionDamageEvent() {}

    @SubscribeEvent
    public static void onExplode(ExplosionEvent.Detonate event) {
        LivingEntity source = event.getExplosion().getIndirectSourceEntity();
        if (!(source instanceof Mob sourceMob) || !source.isAlive() || !(source.level() instanceof ServerLevel)) return;

        RigAnimationId animationId = RigAnimationController.getActiveAnimationId(sourceMob);
        if (!isWoopieExplosionAnimation(animationId)) return;

        Vec3 center = event.getExplosion().getPosition();

        for (Entity entity : event.getAffectedEntities()) {
            if (!(entity instanceof LivingEntity victim) || !victim.isAlive() || victim == source) continue;
            if (victim instanceof Player player && player.isCreative()) continue;

            double dx = center.x - victim.getX();
            double dz = center.z - victim.getZ();
            double distance = victim.position().distanceTo(center);
            double falloff = Mth.clamp(1.0D - distance / 8.0D, 0.0D, 1.0D);

            double horizontal = 6.0D * falloff;
            double up = 2.6D * falloff;

            victim.knockback(horizontal, dx, dz);
            victim.push(0.0D, up, 0.0D);
            victim.hurtMarked = true;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0.0F || !event.getSource().is(DamageTypeTags.IS_EXPLOSION)) return;
        if (!(event.getEntity() instanceof Mob victimMob) || !RigStunController.supports(victimMob)) return;

        Entity sourceEntity = event.getSource().getEntity();
        if (!(sourceEntity instanceof Mob sourceMob) || sourceMob == victimMob || !(sourceMob.level() instanceof ServerLevel)) return;

        RigAnimationId animationId = RigAnimationController.getActiveAnimationId(sourceMob);
        if (!isWoopieExplosionAnimation(animationId)) return;

        RigStunController.applyStunBack(victimMob);
    }

    private static boolean isWoopieExplosionAnimation(RigAnimationId animationId) {
        return animationId == RigAnimationId.WOOPIE_THE_SWORD_ULT
                || animationId == RigAnimationId.WOOPIE_THE_SWORD_EXTRA_ULT
                || animationId == RigAnimationId.WOOPIE_THE_SWORD_EXTRA_ULT_LEGENDARY
                || animationId == RigAnimationId.WOOPIE_THE_SWORD_FLY;
    }
}