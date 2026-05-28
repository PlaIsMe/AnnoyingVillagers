package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.SteveEntity;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.item.WoopieTheSwordItem;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.network.ClientboundWoopieSwordWindFx;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.gameasset.animations.weapons.AnimsSatsujin;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class SteveWoopieSword {
    public static final Builder<MobPatch<?>> WOOPIE_THE_SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot(5.0D))
            .newBehaviorRoot(CombatBehaviourTemplates.escapeWithGuardRoot(4.0D, Animations.BIPED_STEP_BACKWARD, false))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .withinDistance(1.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .withinDistance(1.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .withinDistance(1.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .withinDistance(1.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_STEP_FORWARD, Animations.BIPED_STEP_BACKWARD, Animations.BIPED_STEP_LEFT, Animations.BIPED_STEP_RIGHT))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot())
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.animations(
                                    Animations.SWORD_AUTO1,
                                    Animations.SWORD_AUTO2,
                                    Animations.SWORD_AUTO3,
                                    AnimsSatsujin.SATSUJIN_AUTO_1,
                                    AnimsSatsujin.SATSUJIN_AUTO_2
                            ),
                            CombatCommon.animations(
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
                            ),
                            CombatCommon.animations(
                                    AnimsSatsujin.SATSUJIN_TSUKUYOMI,
                                    AnimsHerrscher.HERRSCHER_VERDAMMNIS,
                                    AnimsPugilistSteve.RUSH_SWORD
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.stepAnimations())
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(20)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(AnimsHerrscher.HERRSCHER_AUTO_2, 0.0F)
                                            .addExBehavior(SteveWoopieSword::woopieWindup)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());

    static void woopieWindup(MobPatch<?> mobpatch) {
        SteveEntity steveEntity = (SteveEntity) mobpatch.getOriginal();
        ItemStack itemStack = steveEntity.getMainHandItem();
        steveEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2));
        if (itemStack.getItem() instanceof WoopieTheSwordItem && steveEntity.level() instanceof ServerLevel) {
            new DelayedTask(10) {
                @Override
                public void run() {
                    Vec3 windPos = EpicfightUtil.getJointWithTranslation(
                            steveEntity,
                            new Vec3f(0.0F, 0.0F, 0.0F),
                            Armatures.BIPED.get().toolR,
                            5.3F,
                            0.5F
                    );
                    if (windPos != null) {
                        BlockPos mutePos = BlockPos.containing(windPos);
                        AnnoyingVillagers.PACKET_HANDLER.send(
                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> steveEntity),
                                new ClientboundMuteExplosionAtPos(mutePos, 4)
                        );
                        steveEntity.level().explode(steveEntity, windPos.x, windPos.y, windPos.z,
                                2.0F, false, Level.ExplosionInteraction.NONE);
                        AnnoyingVillagers.PACKET_HANDLER.send(
                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> steveEntity),
                                new ClientboundWoopieSwordWindFx(windPos)
                        );
                    }
                }
            };
        }
    }
}
