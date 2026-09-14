package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.DestructionEyeItem;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DestructionEyeGuardEvent {
    private DestructionEyeGuardEvent() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingAttackEvent event) {
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
