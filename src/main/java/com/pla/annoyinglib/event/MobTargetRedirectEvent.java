package com.pla.annoyinglib.event;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import com.pla.annoyinglib.compat.MobSupremacy;
import com.pla.annoyinglib.compat.VillagerKingdom;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.AVNpc;
import com.pla.annoyinglib.compat.SmartNpc;
import com.pla.annoyinglib.potion.ObedienceMobEffect;
import com.pla.annoyinglib.util.CommonUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobTargetRedirectEvent {
    public static boolean shouldPreserveRedirectTarget(Mob mob) {
        LivingEntity currentTarget = mob.getTarget();
        return currentTarget != null
                && (getRedirectTarget(mob, currentTarget) != null || isActiveRedirectObjective(currentTarget));
    }

    @Nullable
    public static LivingEntity getRedirectTarget(Mob mob, @Nullable LivingEntity currentTarget) {
        if (currentTarget == null || (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonEntity(mob)) || (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBbqEntity(mob))) {
            return null;
        }

        if (ModList.get().isLoaded("herobrine_vessels")) return HerobrineVessels.redirectTarget(currentTarget);

        return null;
    }

    private static boolean shouldBlockVillagerKnightJevTarget(Mob mob, @Nullable LivingEntity target) {
        return (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isJevEntity(target)) && isVillagerKnight(mob);
    }

    @Nullable
    private static LivingEntity getVillagerKnightJevReplacementTarget(Mob mob, @Nullable LivingEntity target) {
        if (ModList.get().isLoaded("legendary_npcs")) return LegendaryNpcs.getVillagerKnightJevReplacementTarget(mob, target);
        return null;
    }

    private static boolean isVillagerKnight(Mob mob) {
        return (ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isRedVillagerKnightEntity(mob))
                || (ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isBlueVillagerKnightEntity(mob))
                || (ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isGreenVillagerKnightEntity(mob))
                || (ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isPurpleVillagerKnightEntity(mob));
    }

    private static LivingEntity normalizeRedirectTarget(LivingEntity redirectTarget) {
        if (ModList.get().isLoaded("herobrine_vessels")) return HerobrineVessels.normalizeRedirectTarget(redirectTarget);

        return redirectTarget;
    }

    private static boolean isActiveRedirectObjective(LivingEntity currentTarget) {
        if (ModList.get().isLoaded("herobrine_vessels")) return HerobrineVessels.isActiveRedirectObjective(currentTarget);

        return false;
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Mob mob) {
            if ((ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonEntity(mob)) || (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBbqEntity(mob))) return;

            LivingEntity currentTarget = mob.getTarget();
            if (shouldBlockVillagerKnightJevTarget(mob, currentTarget)) {
                mob.setTarget(getVillagerKnightJevReplacementTarget(mob, currentTarget));
                return;
            }

            LivingEntity redirectTarget = getRedirectTarget(mob, currentTarget);
            if (redirectTarget != null) {
                mob.setTarget(redirectTarget);
                if (
                    ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.isReaperDragonRedirect(currentTarget, redirectTarget) &&
                         (mob instanceof AVNpc || (ModList.get().isLoaded("smart_npc") && SmartNpc.isSmartNpc(mob)))) {
                    CommonUtil.swapToBow(mob);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }

        if (!ObedienceMobEffect.isObedientMob(mob)) {
            return;
        }

        LivingEntity newTarget = event.getNewTarget();
        if (shouldBlockVillagerKnightJevTarget(mob, newTarget)) {
            event.setNewTarget(getVillagerKnightJevReplacementTarget(mob, newTarget));
            return;
        }

        if (newTarget != null && ObedienceMobEffect.shouldBlockTarget(mob, newTarget)) {
            event.setNewTarget(null);
        }
    }
}
