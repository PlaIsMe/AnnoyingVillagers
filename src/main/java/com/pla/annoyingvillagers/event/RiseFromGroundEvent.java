package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.entity.LowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.annoyingvillagers.util.HerobrinePortalUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public class RiseFromGroundEvent {

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        var level = entity.level();
        if (level.isClientSide()) return;

        var tag = entity.getPersistentData();
        if (tag.getBooleanOr(HerobrinePortalUtil.NBT_RISING, false)) {

            double targetY = tag.getDoubleOr(HerobrinePortalUtil.NBT_TARGET_Y, 0.0D);
            double speed = tag.getDoubleOr(HerobrinePortalUtil.NBT_SPEED, 0.0D);
            int ticks = tag.getIntOr(HerobrinePortalUtil.NBT_TICKS, 0);
            int max = tag.getIntOr(HerobrinePortalUtil.NBT_MAX_TICKS, 0);

            double ny = entity.getY() + speed;
            if (ny >= targetY || ticks > max) {
                moveTransitionEntity(entity, entity.getX(), targetY, entity.getZ());
                finishRise(entity);
            } else {
                moveTransitionEntity(entity, entity.getX(), ny, entity.getZ());
                tag.putInt(HerobrinePortalUtil.NBT_TICKS, ticks + 1);
            }
            return;
        }

        if (tag.getBooleanOr(HerobrinePortalUtil.NBT_SINKING, false)) {
            double speed = tag.getDoubleOr(HerobrinePortalUtil.NBT_SINK_SPEED, 0.0D);
            int ticks = tag.getIntOr(HerobrinePortalUtil.NBT_SINK_TICKS, 0);
            int nextTicks = ticks + 1;

            moveTransitionEntity(entity, entity.getX(), entity.getY() - speed, entity.getZ());
            tag.putInt(HerobrinePortalUtil.NBT_SINK_TICKS, nextTicks);

            if (tag.getBooleanOr(TransporterFragmentItem.NBT_SAVED_TELEPORT_PENDING, false)
                    && nextTicks >= TransporterFragmentItem.SAVED_TELEPORT_SINK_TICKS) {
                TransporterFragmentItem.finishPendingSavedTeleport(entity);
            }
        }
    }

    private static void finishRise(LivingEntity entity) {
        HerobrinePortalUtil.finishGroundTransition(entity);
        HerobrinePortalUtil.clearRiseTransitionData(entity);

        if (entity instanceof HerobrineMob herobrineMob) {
            if (herobrineMob.getGregUUID() != null) {
                Entity greg = ((ServerLevel) herobrineMob.level()).getEntity(herobrineMob.getGregUUID());
                if (greg instanceof HerobrineGregEntity herobrineGregEntity && herobrineGregEntity.isAlive()) {
                    if (herobrineGregEntity.isSummoning()) {
                        herobrineGregEntity.setSummoning(false);
                        herobrineGregEntity.setNoAi(false);
                    }
                }
            }
            herobrineMob.setInitialSpawn(false);
        }
        if (entity instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
            lowHerobrineCloneEntity.setInitialSpawn(false);
        }
        if (entity instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
            lowShadowHerobrineCloneEntity.setInitialSpawn(false);
        }
    }
    private static void moveTransitionEntity(LivingEntity entity, double x, double y, double z) {
        if (entity instanceof ServerPlayer serverPlayer) TransporterFragmentItem.movePlayerTransition(serverPlayer, x, y, z);
        else HerobrinePortalUtil.moveTransitionEntity(entity, x, y, z);
    }

}
