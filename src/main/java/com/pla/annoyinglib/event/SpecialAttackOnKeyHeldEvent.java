package com.pla.annoyinglib.event;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class SpecialAttackOnKeyHeldEvent {
    private static boolean efmConditionToExecute(Entity entity) {
//        Add this in AV_EFM
//        PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
//        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
//        if (livingEntityPatch == null) return false;
//        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
//        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, livingEntityPatch)) {
//            return false;
//        }
//        if (entity.level() instanceof ServerLevel) {
//            if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
//                return false;
//            }
//        }
        return true;
    }

    private static void playPortalSummonAnimation() {
//        Add this in AV_EFM
//        livingEntityPatch.playAnimationSynchronized(AVAnimations.PORTAL_SUMMON, 0.0F);
//        create VANILLA_ANIMATION
    }

    private static void playChestplateActivationAnimation() {
//        Add this in AV_EFM
//        livingEntityPatch.playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_MIDDLE, 0.0F);
//        create VANILLA_ANIMATION
    }

    public static void execute(LevelAccessor world, Entity entity) {
        execute(world, entity, null);
    }

    public static void execute(LevelAccessor world, Entity entity, Vec3 crosshairTarget) {
        if (entity == null) return;
        if (!efmConditionToExecute(entity)) {
            return;
        }

        if (entity instanceof Player player && !player.level().isClientSide()) {
            if (ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.activateHeldSpecial(player, crosshairTarget, SpecialAttackOnKeyHeldEvent::playPortalSummonAnimation)) return;
            if ((ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonTridentItem_activateVanillaFestival(player) : false)) return;
            if (ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.activateNullHeldSpecial(player)) return;
        }

        if (entity instanceof Player player) {
            if ((ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonChestplateItem(player.getItemBySlot(EquipmentSlot.CHEST).getItem())) && (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonChestplateItem_isBlueDemonChestplate(player.getItemBySlot(EquipmentSlot.CHEST)) : false)) {
                if (entity.level() instanceof ServerLevel) {
                playChestplateActivationAnimation();
                }
            }
        }
    }
}
