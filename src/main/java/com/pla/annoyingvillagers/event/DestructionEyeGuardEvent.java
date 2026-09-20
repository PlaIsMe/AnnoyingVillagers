package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.DestructionEyeItem;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public final class DestructionEyeGuardEvent {
    private DestructionEyeGuardEvent() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingIncomingDamageEvent event) {
        if (event.isCanceled() || !(event.getEntity() instanceof Player player) || !(player.level() instanceof ServerLevel serverLevel)) return;
        DamageSource source = event.getSource();
        if (!DestructionEyeItem.isGuarding(player) || blockingSourceEntity(source) == null || !canGuard(source)) return;

        event.setCanceled(true);
        CommonUtil.damageBlocked(source, player, serverLevel);
        DestructionEyeItem.damageForBlockedHit(player);
    }

    private static Entity blockingSourceEntity(DamageSource source) {
        Entity direct = source.getDirectEntity();
        return direct == null ? source.getEntity() : direct;
    }

    private static boolean canGuard(DamageSource source) {
        if (source.is(DamageTypeTags.BYPASSES_SHIELD)) return false;
        return !(source.getDirectEntity() instanceof AbstractArrow arrow) || arrow.getPierceLevel() <= 0;
    }
}
