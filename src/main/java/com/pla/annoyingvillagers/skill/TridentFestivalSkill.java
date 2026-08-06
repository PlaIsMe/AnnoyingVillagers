package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class TridentFestivalSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("10cefa54-8fee-4627-a321-64a1a6388a25");

    public boolean isRangedMode(SkillContainer container) {
        return container.getDataManager().getDataValue(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get());
    }

    public boolean isMeleeMode(SkillContainer container) {
        return !this.isRangedMode(container);
    }

    public void toggleMode(SkillContainer container) {
        SkillDataManager data = container.getDataManager();
        boolean current = data.getDataValue(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get());
        data.setDataSync(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get(), !current);
    }

    public TridentFestivalSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            if (this.isRangedMode(skillContainer)) {
                Player player = skillContainer.getExecutor().getOriginal();
                ItemStack mainHand = player.getMainHandItem();
                ItemStack offHand = player.getOffhandItem();
                boolean bothFullyCharged =
                        BlueDemonTridentItem.isBlueDemonTrident(mainHand)
                                && BlueDemonTridentItem.isBlueDemonTrident(offHand)
                                && BlueDemonTridentItem.isFullyCharged(mainHand)
                                && BlueDemonTridentItem.isFullyCharged(offHand);
                if (bothFullyCharged) {
                    skillContainer.getExecutor().playAnimationSynchronized(AnimsPugilistSteve.TRIDENT_FESTIVAL, 0.0F);
                } else {
                    skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.TRIDENT_ATTACK, 0.0F);
                }
            } else {
                skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.ELECTRIC_FIELD, 0.0F);
            }
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        skillContainer.deactivate();
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        Player player = container.getExecutor().getOriginal();
        if (player.level() instanceof ServerLevel serverLevel && player.tickCount % 20 == 0) {
            SkillDataManager data = container.getDataManager();
            data.setDataSync(AVSkillDataKeys.TRIDENT_AMOUNT.get(), BlueDemonTridentItem.getAllOwnerTridents(serverLevel, player).size());
        }
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().setDataSync(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get(), false);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    if (event.getPlayerPatch().isLogicalClient()) {
                        return;
                    }

                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    if (skillContainer == null) {
                        return;
                    }

                    if (this.isRangedMode(skillContainer)) {
                        event.setCanceled(true);

                        final PlayerPatch<?> playerPatch = event.getPlayerPatch();
                        AssetAccessor<? extends StaticAnimation> dynamicAnimation =
                                Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();

                        if (dynamicAnimation != null && dynamicAnimation == AnimsEpicFightBattleArts.TRIDENT_THROW_1) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsPugilistSteve.TRIDENT_THROW_2, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AnimsPugilistSteve.TRIDENT_THROW_2) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsEpicFightBattleArts.TRIDENT_THROW_3, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AnimsEpicFightBattleArts.TRIDENT_THROW_3) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsEpicFightAwaken.DP_THROW_BLADE_AUTO_2, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AnimsEpicFightAwaken.DP_THROW_BLADE_AUTO_2) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsEpicFightBattleArts.TRIDENT_THROW_5, 0.0F);
                        } else {
                            if (playerPatch.getOriginal().isSprinting()) {
                                skillContainer.getExecutor().playAnimationSynchronized(AnimsEpicFightAwaken.DP_THROW_BLADE_AUTO_1, 0.0F);
                            } else if (!playerPatch.getOriginal().onGround() && !playerPatch.getOriginal().isInWater()) {
                                skillContainer.getExecutor().playAnimationSynchronized(AnimsEpicFightAwaken.THROW_HOOK_SLASH_AIR, 0.0F);
                            } else {
                                skillContainer.getExecutor().playAnimationSynchronized(AnimsEpicFightBattleArts.TRIDENT_THROW_1, 0.0F);
                            }
                        }
                    }
                });
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID, (pre) -> {
            if (pre.getPlayerPatch().isLogicalClient()) return;

            final PlayerPatch<?> playerPatch = pre.getPlayerPatch();
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (dynamicAnimation == null) return;

            if (dynamicAnimation == AVAnimations.TRIDENT_ATTACK || dynamicAnimation == AVAnimations.ELECTRIC_FIELD || dynamicAnimation == AnimsPugilistSteve.TRIDENT_FESTIVAL) {
                pre.setCanceled(true);
                pre.setResult(AttackResult.ResultType.BLOCKED);
            }

            if (playerPatch.getOriginal().isSprinting() && pre.getDamageSource().getDirectEntity() instanceof Projectile projectile) {
                Vec3 entityPosition = projectile.position();
                Vec3 entityViewVector = pre.getPlayerPatch().getOriginal().getViewVector(1.0F);
                Vec3 entitySubtract = entityPosition.subtract(pre.getPlayerPatch().getOriginal().getEyePosition()).normalize();

                if (entitySubtract.dot(entityViewVector) > 0.0D) {
                    pre.setCanceled(true);
                    pre.setResult(AttackResult.ResultType.BLOCKED);
                    if (playerPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                        EpicfightUtil.damageBlocked(pre.getDamageSource(), playerPatch.getOriginal(), serverLevel);
                    }
                    if (new Random().nextBoolean()) {
                        playerPatch.playAnimationSynchronized(AnimsWom.TRIDENT_GUARD_HIT_1, 0.0F);
                    } else {
                        playerPatch.playAnimationSynchronized(AnimsWom.TRIDENT_GUARD_HIT_2, 0.0F);
                    }
                }
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return super.getSkillTexture();
    }
}
