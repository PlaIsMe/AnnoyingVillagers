package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.gameasset.AnimsSculkSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Objects;

public class SpecialAttackOnKeyHeldEvent {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) return;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, livingEntityPatch)) {
            return;
        }

        if (entity instanceof Player player && !player.level().isClientSide()) {
            TransporterFragmentItem.UseResult transporterUseResult = TransporterFragmentItem.tryUseHeldSpecialAttack(player);
            if (transporterUseResult.consumed()) {
                if (transporterUseResult.activated()) {
                    livingEntityPatch.playAnimationSynchronized(AnimsSculkSteve.PORTAL_SUMMON, 0.0F);
                }
                return;
            }
        }

        if (entity instanceof Player player && !player.level().isClientSide()) {
            ItemStack mainHandItem = player.getMainHandItem();
            if (EnderAegisItem.activateSecondForm(player, mainHandItem)) {
                return;
            }

            ItemStack offHandItem = player.getOffhandItem();
            if (EnderAegisItem.activateSecondForm(player, offHandItem)) {
                return;
            }
        }

        if (entity.level() instanceof ServerLevel) {
            if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
                return;
            }
        }

        if (entity instanceof Player player) {
            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof BlueDemonChestplateItem && BlueDemonChestplateItem.isBlueDemonChestplate(player.getItemBySlot(EquipmentSlot.CHEST))) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(AnimsWom.CUT_ANTITHEUS_ASCENSION, 0.0F);
                    return;
                }
            }
        }
    }
}