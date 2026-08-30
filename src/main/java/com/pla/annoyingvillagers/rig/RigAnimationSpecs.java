package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.TridentMode;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.entity.BlackFireEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.BlueDemonThrownTridentEntity;
import com.pla.annoyingvillagers.entity.BlueDemonThunderBeamEntity;
import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import com.pla.annoyingvillagers.entity.TridentLightningBolt;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.network.ClientboundDiamondAttractorFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.network.ClientboundWoopieSwordWindFx;
import com.pla.annoyingvillagers.potion.ObedienceMobEffect;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.BlueDemonUtil;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.RigPoseUtil;
import com.pla.annoyingvillagers.util.ScreenShakeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.pla.annoyingvillagers.rig.RigColliderAnchor.*;
import static com.pla.annoyingvillagers.rig.RigColliderPreset.*;
import static com.pla.annoyingvillagers.rig.RigColliderPreset.BODY;

public final class RigAnimationSpecs {
    private static final Map<RigAnimationId, RigAnimationSpec> SPECS = new EnumMap<>(RigAnimationId.class);

    private static final RigCollider RIGHT_FIST = RigCollider.of(RIGHT_HAND, FIST);
    private static final RigCollider LEFT_FIST = RigCollider.of(LEFT_HAND, FIST);
    private static final RigCollider RIGHT_DAGGER = RigCollider.of(RIGHT_TOOL, DAGGER);
    private static final RigCollider LEFT_DAGGER = RigCollider.of(LEFT_TOOL, DAGGER);
    private static final RigCollider RIGHT_SWORD = RigCollider.of(RIGHT_TOOL, SWORD);
    private static final RigCollider LEFT_SWORD = RigCollider.of(LEFT_TOOL, SWORD);
    private static final RigCollider RIGHT_LONGSWORD = RigCollider.of(RIGHT_TOOL, LONGSWORD);
    private static final RigCollider LEFT_LONGSWORD = RigCollider.of(LEFT_TOOL, LONGSWORD);
    private static final RigCollider RIGHT_GREATSWORD = RigCollider.of(RIGHT_TOOL, GREATSWORD);
    private static final RigCollider LEFT_GREATSWORD = RigCollider.of(LEFT_TOOL, GREATSWORD);
    private static final RigCollider RIGHT_SPEAR = RigCollider.of(RIGHT_TOOL, SPEAR);
    private static final RigCollider LEFT_SPEAR = RigCollider.of(LEFT_TOOL, SPEAR);
    private static final RigCollider RIGHT_AXE = RigCollider.of(RIGHT_TOOL, AXE);
    private static final RigCollider LEFT_AXE = RigCollider.of(LEFT_TOOL, AXE);
    private static final RigCollider RIGHT_TACHI = RigCollider.of(RIGHT_TOOL, TACHI);
    private static final RigCollider LEFT_TACHI = RigCollider.of(LEFT_TOOL, TACHI);
    private static final RigCollider RIGHT_GLAIVE = RigCollider.of(RIGHT_TOOL, GLAIVE);
    private static final RigCollider LEFT_GLAIVE = RigCollider.of(LEFT_TOOL, GLAIVE);
    private static final RigCollider RIGHT_SCYTHE = RigCollider.of(RIGHT_TOOL, SCYTHE);
    private static final RigCollider LEFT_SCYTHE = RigCollider.of(LEFT_TOOL, SCYTHE);
    private static final RigCollider RIGHT_SLEDGEHAMMER = RigCollider.of(RIGHT_TOOL, SLEDGEHAMMER);
    private static final RigCollider LEFT_SLEDGEHAMMER = RigCollider.of(LEFT_TOOL, SLEDGEHAMMER);
    private static final RigCollider RIGHT_FOOT = RigCollider.of(RIGHT_LOWER_LEG, FOOT);
    private static final RigCollider RIGHT_KNEE = RigCollider.of(RIGHT_LEG, FOOT);
    private static final RigCollider LEFT_FOOT = RigCollider.of(LEFT_LOWER_LEG, FOOT);
    private static final RigCollider RIGHT_ELBOW = RigCollider.of(RIGHT_ARM, BODY);

    static {
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_AIM_DOWN, 14, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_AIM_MID, 14, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_AIM_UP, 14, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_ATTACK_DOWN, 2, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_ATTACK_MID, 2, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_ATTACK_UP, 2, RigAnimationPlaybackType.UPPER_BODY));

        put(RigAnimationSpec.attack(RigAnimationId.BASIC_ATTACK1, 12, false,
                RigAttackWindow.of(3, 8, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.BASIC_ATTACK2, 12, false,
                RigAttackWindow.of(3, 8, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.BASIC_ATTACK3, 12, false,
                RigAttackWindow.of(3, 5, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.BASIC_ATTACK4, 13, false,
                RigAttackWindow.of(3, 5, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.BASIC_DASH_ATTACK, 13, false,
                        RigAttackWindow.of(3, 8, RIGHT_SWORD))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.BASIC_JUMP_ATTACK, 13, true,
                        RigAttackWindow.of(3, 8, RIGHT_SWORD))
                .damageMultiplier(1.5F)
                .criticalChance(0.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.BASIC_ULT, 33, false,
                        hookAt(2, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;

                            if (mob.getMainHandItem().getItem() instanceof ThunderDiamondBladeItem) {
                                ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, mob, false);
                            }
                        }),
                        RigAttackWindow.of(5, 15, RIGHT_SWORD))
                .damageMultiplier(2.0F)
                .criticalChance(0.5F)
                .onHit((attacker, target, critical) -> {
                    if (!(attacker.getMainHandItem().getItem() instanceof DNAxHookedSwordItem)) return;
                    if (!(target instanceof Mob targetMob)) return;
                    ObedienceMobEffect.applyObedience(targetMob, attacker, 20 * 5);
                }));
        put(RigAnimationSpec.attack(RigAnimationId.BASIC_MOUNT_ATTACK, 12, false,
                RigAttackWindow.of(2, 6, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_BASIC_ATTACK1, 12, false,
                RigAttackWindow.of(3, 8, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_BASIC_ATTACK2, 12, false,
                RigAttackWindow.of(3, 8, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_BASIC_ATTACK3, 15, false,
                RigAttackWindow.of(5, 12, RIGHT_SWORD, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_BASIC_DASH_ATTACK, 15, false,
                        RigAttackWindow.of(3, 8, RIGHT_SWORD, LEFT_SWORD))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_BASIC_JUMP_ATTACK, 13, true,
                        RigAttackWindow.of(3, 8, RIGHT_SWORD, LEFT_SWORD))
                .damageMultiplier(1.5F)
                .criticalChance(0.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_BASIC_ULT, 33, false,
                        hookAt(2, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;

                            if (mob.getMainHandItem().getItem() instanceof ThunderDiamondBladeItem) {
                                ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, mob, false);
                            }

                            if (mob.getOffhandItem().getItem() instanceof ThunderDiamondBladeItem) {
                                ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, mob, true);
                            }
                        }),
                        RigAttackWindow.of(5, 8, RIGHT_SWORD),
                        RigAttackWindow.of(8, 13, LEFT_SWORD),
                        RigAttackWindow.of(13, 18, RIGHT_SWORD))
                .damageMultiplier(2.0F)
                .criticalChance(0.5F)
                .onHit((attacker, target, critical) -> {
                    if (!(attacker.getMainHandItem().getItem() instanceof DNAxHookedSwordItem)) return;
                    if (!(target instanceof Mob targetMob)) return;

                    int durationTicks = attacker.getOffhandItem().getItem() instanceof DNAxHookedSwordItem ? 20 * 10 : 20 * 5;
                    ObedienceMobEffect.applyObedience(targetMob, attacker, durationTicks);
                }));

        put(RigAnimationSpec.attack(RigAnimationId.FIST_ATTACK1, 17, false,
                RigAttackWindow.of(3, 8, LEFT_FIST)));
        put(RigAnimationSpec.attack(RigAnimationId.FIST_ATTACK2, 17, false,
                RigAttackWindow.of(3, 8, RIGHT_FIST)));
        put(RigAnimationSpec.attack(RigAnimationId.FIST_ATTACK3, 17, false,
                RigAttackWindow.of(3, 8, LEFT_FIST)));
        put(RigAnimationSpec.attack(RigAnimationId.FIST_ATTACK4, 22, false,
                RigAttackWindow.of(9, 17, RIGHT_FIST)));
        put(RigAnimationSpec.attack(RigAnimationId.FIST_ATTACK5, 37, false,
                RigAttackWindow.of(12, 20, LEFT_FIST)));
        put(RigAnimationSpec.attack(RigAnimationId.FIST_DASH_ATTACK, 27, false,
                        RigAttackWindow.of(6, 15, RIGHT_FIST))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.FIST_JUMP_ATTACK, 17, true,
                        RigAttackWindow.of(6, 12, RIGHT_FIST))
                .damageMultiplier(1.5F)
                .criticalChance(0.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.FIST_ULT, 33, false,
                        RigAttackWindow.of(1, 2, LEFT_FIST),
                        RigAttackWindow.of(3, 4, RIGHT_FIST),
                        RigAttackWindow.of(5, 6, LEFT_FIST),
                        RigAttackWindow.of(7, 8, RIGHT_FIST),
                        RigAttackWindow.of(9, 10, LEFT_FIST),
                        RigAttackWindow.of(11, 12, RIGHT_FIST),
                        RigAttackWindow.of(13, 14, LEFT_FIST),
                        RigAttackWindow.of(15, 16, RIGHT_FIST))
                .damageMultiplier(1.5F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.FIST_EXTRA_ATTACK, 32, false,
                RigAttackWindow.of(9, 17, RIGHT_FOOT)));

        put(RigAnimationSpec.attack(RigAnimationId.KICK_ATTACK1, 25, false,
                RigAttackWindow.of(5, 12, RIGHT_FOOT)));
        put(RigAnimationSpec.attack(RigAnimationId.KICK_ATTACK2, 17, false,
                RigAttackWindow.of(5, 12, LEFT_FOOT)));
        put(RigAnimationSpec.attack(RigAnimationId.KICK_ATTACK3, 20, false,
                RigAttackWindow.of(5, 12, RIGHT_FOOT)));
        put(RigAnimationSpec.attack(RigAnimationId.KICK_ATTACK4, 17, false,
                RigAttackWindow.of(5, 12, LEFT_FOOT)));
        put(RigAnimationSpec.attack(RigAnimationId.KICK_COMBO_ATTACK, 28, false,
                        RigAttackWindow.of(8, 9, LEFT_FOOT),
                        RigAttackWindow.of(10, 11, LEFT_FOOT),
                        RigAttackWindow.of(12, 13, LEFT_FOOT),
                        RigAttackWindow.of(14, 15, LEFT_FOOT),
                        RigAttackWindow.of(16, 17, LEFT_FOOT))
                .damageMultiplier(1.5F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.KICK_DASH_ATTACK, 19, false,
                        RigAttackWindow.of(2, 12, RIGHT_FOOT))
                .damageMultiplier(2.5F)
                .criticalChance(0.8F)
        );

        put(RigAnimationSpec.attack(RigAnimationId.AXE_ATTACK1, 25, false,
                RigAttackWindow.of(5, 16, RIGHT_AXE)));
        put(RigAnimationSpec.attack(RigAnimationId.AXE_ATTACK2, 26, false,
                RigAttackWindow.of(6, 16, RIGHT_AXE)));
        put(RigAnimationSpec.attack(RigAnimationId.AXE_ATTACK3, 22, false,
                RigAttackWindow.of(5, 12, RIGHT_AXE)));
        put(RigAnimationSpec.attack(RigAnimationId.AXE_ATTACK4, 20, false,
                RigAttackWindow.of(5, 12, RIGHT_AXE)));
        put(RigAnimationSpec.attack(RigAnimationId.AXE_ATTACK5, 28, false,
                RigAttackWindow.of(5, 18, RIGHT_AXE)));
        put(RigAnimationSpec.attack(RigAnimationId.AXE_DASH_ATTACK, 32, false,
                        RigAttackWindow.of(11, 24, RIGHT_AXE))
                .damageMultiplier(1.2F)
                .criticalChance(0.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.AXE_JUMP_ATTACK, 32, true,
                        RigAttackWindow.of(4, 24, RIGHT_AXE))
                .damageMultiplier(2.5F)
                .criticalChance(0.8F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.AXE_ULT, 30, false,
                        RigAttackWindow.of(8, 25, RIGHT_AXE))
                .damageMultiplier(1.5F)
                .criticalChance(0.4F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_AXE_ULT, 60, false,
                        RigAttackWindow.of(3, 5, RIGHT_AXE),
                        RigAttackWindow.of(5, 8, LEFT_AXE),
                        RigAttackWindow.of(10, 12, LEFT_AXE),
                        RigAttackWindow.of(12, 15, RIGHT_AXE),
                        RigAttackWindow.of(15, 16, LEFT_AXE),
                        RigAttackWindow.of(18, 20, LEFT_AXE),
                        RigAttackWindow.of(20, 22, RIGHT_AXE),
                        RigAttackWindow.of(22, 24, LEFT_AXE),
                        RigAttackWindow.of(24, 27, RIGHT_AXE),
                        RigAttackWindow.of(27, 28, LEFT_AXE),
                        RigAttackWindow.of(28, 30, RIGHT_AXE),
                        RigAttackWindow.of(30, 32, LEFT_AXE),
                        RigAttackWindow.of(32, 34, RIGHT_AXE),
                        RigAttackWindow.of(34, 36, LEFT_AXE),
                        RigAttackWindow.of(36, 38, RIGHT_AXE),
                        RigAttackWindow.of(40, 44, LEFT_AXE))
                .damageMultiplier(1.5F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.nonDamaging(RigAnimationId.EARTH_AXE_ULT, 33, RigAnimationPlaybackType.DEFAULT,
                hookAt(4, mob -> {
                    if (mob.level() instanceof ServerLevel serverLevel) {
                        EarthAxeItem.summonEarthWall(serverLevel, mob);
                    }
                })));
        put(RigAnimationSpec.attack(RigAnimationId.EARTH_AXE_EXTRA_ATTACK, 33, false,
                hookAt(20, mob -> {
                    if (mob.level() instanceof ServerLevel serverLevel) {
                        Vec3 bladePos = RigPoseUtil.getPartPosition(mob, RigAnimationId.EARTH_AXE_EXTRA_ATTACK, 20.0F, RigPart.RIGHT_WEAPON, Vec3.ZERO, 0.5D, 0.0D);
                        BlockPos liftPos = EarthAxeItem.findLiftableBlockUnderPoint(serverLevel, bladePos, 6, 1);
                        if (liftPos != null) {
                            EarthAxeItem.liftBlockAt(serverLevel, liftPos, mob);
                        }
                    }
                }),
                RigAttackWindow.of(6, 8, RIGHT_AXE)));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.GREATSWORD_IDLE, 60));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.GREATSWORD_RUN, 10));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.GREATSWORD_WALK, 14));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.CARRY, 54));
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_ATTACK1, 42, false,
                RigAttackWindow.of(12, 20, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_ATTACK2, 38, false,
                RigAttackWindow.of(10, 17, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_ATTACK3, 39, false,
                RigAttackWindow.of(9, 20, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_ATTACK4, 32, false,
                RigAttackWindow.of(3, 12, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_ATTACK5, 38, false,
                        groundSlamHook(18, RigAnimationId.GREATSWORD_ATTACK5, 1.4D, 0.7D, 35, 0.7D, 2.0D),
                        RigAttackWindow.of(3, 6, RIGHT_GREATSWORD),
                        RigAttackWindow.of(8, 22, RIGHT_GREATSWORD))
                .damageMultiplier(1.5F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_DASH_ATTACK, 40, false,
                        RigAttackWindow.of(11, 20, RIGHT_GREATSWORD))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_JUMP_ATTACK, 48, true,
                        groundSlamHook(12, RigAnimationId.GREATSWORD_JUMP_ATTACK, 1.4D, 0.7D, 35, 0.7D, 2.0D),
                        RigAttackWindow.of(9, 12, RIGHT_GREATSWORD))
                .damageMultiplier(1.8F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_EXTRA_ATTACK, 50, false,
                groundSlamHook(23, RigAnimationId.GREATSWORD_EXTRA_ATTACK, 1.4D, 0.7D, 35, 0.7D, 2.0D),
                RigAttackWindow.of(15, 25, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.GREATSWORD_ULT, 47, false,
                        RigAttackWindow.of(2, 5, RIGHT_GREATSWORD),
                        RigAttackWindow.of(5, 8, RIGHT_GREATSWORD),
                        RigAttackWindow.of(10, 12, RIGHT_GREATSWORD),
                        RigAttackWindow.of(12, 15, RIGHT_GREATSWORD),
                        RigAttackWindow.of(15, 16, RIGHT_GREATSWORD),
                        RigAttackWindow.of(18, 20, RIGHT_GREATSWORD),
                        RigAttackWindow.of(20, 22, RIGHT_GREATSWORD),
                        RigAttackWindow.of(22, 24, RIGHT_GREATSWORD),
                        RigAttackWindow.of(24, 27, RIGHT_GREATSWORD),
                        RigAttackWindow.of(27, 28, RIGHT_GREATSWORD),
                        RigAttackWindow.of(28, 30, RIGHT_GREATSWORD),
                        RigAttackWindow.of(30, 32, RIGHT_GREATSWORD),
                        RigAttackWindow.of(32, 34, RIGHT_GREATSWORD),
                        RigAttackWindow.of(34, 36, RIGHT_GREATSWORD),
                        RigAttackWindow.of(37, 40, RIGHT_GREATSWORD))
                .damageMultiplier(2.5F)
                .criticalChance(0.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.GREATAXE_ATTACK4, 42, false,
                RigAttackWindow.of(9, 20, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.GREATAXE_ATTACK5, 31, false,
                RigAttackWindow.of(6, 15, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.GREATAXE_DASH_ATTACK, 48, false,
                        groundSlamHook(18, RigAnimationId.GREATAXE_DASH_ATTACK, 1.4D, 0.7D, 35, 0.7D, 2.0D),
                        RigAttackWindow.of(9, 18, RIGHT_GREATSWORD))
                .damageMultiplier(1.5F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.GREATAXE_JUMP_ATTACK, 42, true,
                        groundSlamHook(18, RigAnimationId.GREATAXE_JUMP_ATTACK, 1.4D, 0.7D, 35, 0.7D, 2.0D),
                        RigAttackWindow.of(16, 23, RIGHT_GREATSWORD))
                .damageMultiplier(2.5F)
                .criticalChance(0.4F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.GREATAXE_ULT, 67, false,
                        groundSlamHook(28, RigAnimationId.GREATAXE_ULT, 1.4D, 1.0D, 50, 1.0D, 3.5D),
                        RigAttackWindow.of(20, 28, RIGHT_GREATSWORD))
                .damageMultiplier(2.5F)
                .criticalChance(1.0F)
        );

        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_ATTACK1, 28, false,
                RigAttackWindow.of(5, 12, RIGHT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_ATTACK2, 31, false,
                RigAttackWindow.of(3, 12, RIGHT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_ATTACK3, 38, false,
                RigAttackWindow.of(5, 12, RIGHT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_ATTACK4, 37, false,
                RigAttackWindow.of(8, 18, RIGHT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_ATTACK5, 33, false,
                        RigAttackWindow.of(6, 15, RIGHT_LONGSWORD))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_DASH_ATTACK, 43, false,
                        RigAttackWindow.of(12, 25, RIGHT_LONGSWORD))
                .damageMultiplier(1.5F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_JUMP_ATTACK, 23, true,
                        RigAttackWindow.of(2, 8, RIGHT_LONGSWORD))
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_EXTRA_ATTACK, 28, false,
                RigAttackWindow.of(5, 9, RIGHT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LONGSWORD_ULT, 32, false,
                        RigAttackWindow.of(7, 15, RIGHT_LONGSWORD))
                .damageMultiplier(1.5F)
                .criticalChance(0.5F)
        );

        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_ATTACK1, 33, false,
                RigAttackWindow.of(10, 20, RIGHT_LONGSWORD, LEFT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_ATTACK2, 47, false,
                RigAttackWindow.of(12, 20, RIGHT_LONGSWORD),
                RigAttackWindow.of(16, 25, LEFT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_ATTACK3, 37, false,
                RigAttackWindow.of(13, 20, RIGHT_LONGSWORD),
                RigAttackWindow.of(14, 20, LEFT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_ATTACK4, 31, false,
                RigAttackWindow.of(14, 20, RIGHT_LONGSWORD),
                RigAttackWindow.of(14, 20, LEFT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_ATTACK5, 42, false,
                        RigAttackWindow.of(14, 20, RIGHT_LONGSWORD),
                        RigAttackWindow.of(14, 20, LEFT_LONGSWORD))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_DASH_ATTACK, 42, false,
                        RigAttackWindow.of(14, 24, RIGHT_KNEE))
                .damageMultiplier(1.5F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_JUMP_ATTACK, 37, false,
                        RigAttackWindow.of(2, 4, RIGHT_LONGSWORD, LEFT_LONGSWORD),
                        RigAttackWindow.of(9, 20, RIGHT_LONGSWORD, LEFT_LONGSWORD))
                .criticalChance(0.8F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_EXTRA_ATTACK, 36, false,
                RigAttackWindow.of(10, 20, RIGHT_LONGSWORD),
                RigAttackWindow.of(10, 20, LEFT_LONGSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_LONGSWORD_ULT, 35, false,
                        RigAttackWindow.of(5, 15, RIGHT_LONGSWORD, LEFT_LONGSWORD))
                .damageMultiplier(2.5F)
                .criticalChance(0.5F)
        );

        put(RigAnimationSpec.nonDamaging(RigAnimationId.SPEAR_IDLE, 60));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SPEAR_WALK, 13));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SPEAR_RUN, 13));
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_ATTACK1, 40, false,
                RigAttackWindow.of(11, 18, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_ATTACK2, 36, false,
                RigAttackWindow.of(12, 19, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_ATTACK3, 33, false,
                RigAttackWindow.of(4, 18, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_ATTACK4, 38, false,
                RigAttackWindow.of(5, 10, RIGHT_SPEAR),
                RigAttackWindow.of(11, 16, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_ATTACK5, 80, false,
                        RigAttackWindow.of(15, 30, RIGHT_SPEAR))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_DASH_ATTACK, 40, false,
                        RigAttackWindow.of(10, 20, RIGHT_SPEAR))
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_JUMP_ATTACK, 40, true,
                        RigAttackWindow.of(10, 15, RIGHT_SPEAR),
                        RigAttackWindow.of(16, 23, RIGHT_SPEAR))
                .damageMultiplier(1.5F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_EXTRA_ATTACK, 26, false,
                RigAttackWindow.of(6, 8, RIGHT_SPEAR),
                RigAttackWindow.of(10, 13, RIGHT_SPEAR),
                RigAttackWindow.of(15, 22, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.SPEAR_ULT, 33, false,
                        RigAttackWindow.of(12, 20, RIGHT_SPEAR))
                .damageMultiplier(2.5F)
                .criticalChance(0.5F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.STAFF_ULT, 51, false,
                        RigAttackWindow.of(10, 15, RIGHT_SPEAR),
                        RigAttackWindow.of(33, 42, RIGHT_SPEAR))
                .damageMultiplier(2.5F)
                .criticalChance(0.5F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.SICKLE_ULT, 53, false,
                        RigAttackWindow.of(13, 16, RIGHT_SPEAR),
                        RigAttackWindow.of(16, 20, RIGHT_SPEAR),
                        RigAttackWindow.of(21, 28, RIGHT_SPEAR))
                .damageMultiplier(2.5F)
                .criticalChance(0.5F)
        );

        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_ATTACK1, 23, false,
                RigAttackWindow.of(1, 10, RIGHT_DAGGER)));
        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_ATTACK2, 17, false,
                RigAttackWindow.of(1, 10, RIGHT_DAGGER)));
        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_ATTACK3, 20, false,
                RigAttackWindow.of(3, 12, RIGHT_DAGGER)));
        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_ATTACK4, 20, false,
                RigAttackWindow.of(2, 10, RIGHT_DAGGER)));
        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_ATTACK5, 20, false,
                        RigAttackWindow.of(2, 3, RIGHT_DAGGER),
                        RigAttackWindow.of(4, 10, RIGHT_DAGGER))
                .criticalChance(0.5F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_DASH_ATTACK, 30, false,
                        RigAttackWindow.of(5, 7, RIGHT_DAGGER),
                        RigAttackWindow.of(8, 14, RIGHT_DAGGER))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_JUMP_ATTACK, 17, true,
                        RigAttackWindow.of(3, 12, RIGHT_DAGGER))
                .damageMultiplier(1.5F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_EXTRA_ATTACK, 26, false,
                RigAttackWindow.of(2, 12, RIGHT_ELBOW)));
        put(RigAnimationSpec.attack(RigAnimationId.DAGGER_ULT, 34, false,
                        RigAttackWindow.of(4, 6, RIGHT_DAGGER),
                        RigAttackWindow.of(7, 15, RIGHT_DAGGER))
                .damageMultiplier(2.5F)
                .criticalChance(0.5F)
        );

        put(RigAnimationSpec.nonDamaging(RigAnimationId.TACHI_IDLE, 60));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.TACHI_RUN, 13));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.TACHI_WALK, 14));
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_ATTACK1, 31, false,
                RigAttackWindow.of(6, 15, RIGHT_TACHI)));
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_ATTACK2, 30, false,
                RigAttackWindow.of(6, 15, RIGHT_TACHI)));
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_ATTACK3, 30, false,
                RigAttackWindow.of(8, 15, RIGHT_TACHI)));
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_ATTACK4, 40, false,
                RigAttackWindow.of(6, 15, RIGHT_TACHI)));
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_ATTACK5, 50, false,
                        RigAttackWindow.of(6, 15, RIGHT_TACHI))
                .damageMultiplier(1.2F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_DASH_ATTACK, 33, false,
                        RigAttackWindow.of(6, 15, RIGHT_TACHI))
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_JUMP_ATTACK, 38, true,
                        RigAttackWindow.of(6, 15, RIGHT_TACHI))
                .damageMultiplier(1.5F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_EXTRA_ATTACK, 41, false,
                RigAttackWindow.of(13, 22, RIGHT_TACHI)));
        put(RigAnimationSpec.attack(RigAnimationId.TACHI_ULT, 66, false,
                        RigAttackWindow.of(10, 18, RIGHT_TACHI),
                        RigAttackWindow.of(26, 38, RIGHT_TACHI))
                .damageMultiplier(2.5F)
                .criticalChance(0.5F)
        );

        put(RigAnimationSpec.attack(RigAnimationId.SWORD_ATTACK1, 23, false,
                RigAttackWindow.of(3, 12, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORD_ATTACK2, 30, false,
                RigAttackWindow.of(6, 15, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORD_ATTACK3, 25, false,
                RigAttackWindow.of(4, 14, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORD_ATTACK4, 33, false,
                RigAttackWindow.of(5, 16, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORD_ATTACK5, 30, false,
                        RigAttackWindow.of(3, 15, RIGHT_SWORD))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.SWORD_DASH_ATTACK, 37, false,
                        RigAttackWindow.of(11, 20, RIGHT_SWORD))
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.SWORD_JUMP_ATTACK, 27, true,
                        RigAttackWindow.of(7, 15, RIGHT_SWORD))
                .damageMultiplier(1.2F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.SWORD_EXTRA_ATTACK, 33, false,
                RigAttackWindow.of(8, 13, RIGHT_SWORD),
                RigAttackWindow.of(14, 20, RIGHT_ELBOW)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORD_ULT, 27, false,
                        RigAttackWindow.of(1, 16, RIGHT_SWORD))
                .damageMultiplier(2.5F)
                .criticalChance(0.5F)
        );

        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_ATTACK1, 40, false,
                RigAttackWindow.of(7, 15, LEFT_SWORD),
                RigAttackWindow.of(11, 20, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_ATTACK2, 40, false,
                RigAttackWindow.of(13, 22, RIGHT_SWORD),
                RigAttackWindow.of(13, 22, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_ATTACK3, 40, false,
                RigAttackWindow.of(9, 18, RIGHT_SWORD),
                RigAttackWindow.of(9, 18, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_ATTACK4, 40, false,
                RigAttackWindow.of(7, 9, LEFT_SWORD),
                RigAttackWindow.of(11, 15, LEFT_SWORD),
                RigAttackWindow.of(16, 22, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_ATTACK5, 40, false,
                        RigAttackWindow.of(6, 10, RIGHT_SWORD),
                        RigAttackWindow.of(10, 16, LEFT_SWORD))
                .damageMultiplier(1.2F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_DASH_ATTACK, 33, false,
                        RigAttackWindow.of(5, 8, LEFT_SWORD),
                        RigAttackWindow.of(9, 15, RIGHT_SWORD))
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_JUMP_ATTACK, 30, true,
                        RigAttackWindow.of(5, 9, RIGHT_SWORD),
                        RigAttackWindow.of(11, 20, LEFT_SWORD))
                .damageMultiplier(1.2F)
                .criticalChance(0.3F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_EXTRA_ATTACK, 38, false,
                RigAttackWindow.of(10, 20, RIGHT_SWORD, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_SWORD_ULT, 33, false,
                        RigAttackWindow.of(2, 4, RIGHT_SWORD, LEFT_SWORD),
                        RigAttackWindow.of(4, 6, RIGHT_SWORD, LEFT_SWORD),
                        RigAttackWindow.of(6, 7, RIGHT_SWORD, LEFT_SWORD),
                        RigAttackWindow.of(9, 18, RIGHT_SWORD, LEFT_SWORD))
                .damageMultiplier(2.5F)
                .criticalChance(0.5F)
        );

        put(RigAnimationSpec.attack(RigAnimationId.BLACK_FIRE_SWORD_ULT, 30, false,
                hookAt(8, mob -> {
                    if (mob.level() instanceof ServerLevel serverLevel) {
                        BlackFireEntity.shootFromOwnerLook(serverLevel, mob);
                    }
                }),
                RigAttackWindow.of(2, 4)));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.DIAMOND_ATTRACTOR_ULT, 33, RigAnimationPlaybackType.DEFAULT,
                hookAt(2, mob -> {
                    if (mob.level() instanceof ServerLevel serverLevel) {
                        serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), AnnoyingVillagersModSounds.DIAMOND_ATTRACTOR.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
                        AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundDiamondAttractorFx(mob));
                        DiamondAttractorSwordItem.pullWeapons(mob);
                    }
                })));
        put(RigAnimationSpec.attack(RigAnimationId.DIAMOND_BLASTER_ULT, 17, false,
                        RigAttackWindow.of(1, 15, RIGHT_SWORD))
                .onHit((attacker, target, critical) -> {
                    if (target instanceof Mob targetMob && RigStunController.supports(targetMob)) {
                        RigStunController.applyStun(targetMob, RigAnimationId.SUPER_KNOCK_BACK);
                    }
                    CommonUtil.pushEntityFromCaster(target, attacker);
                })
                .damageMultiplier(1.5F)
                .criticalChance(0.7F)
        );
        put(RigAnimationSpec.attack(RigAnimationId.HOOK_SWORD_ULT1, 33, false,
                RigAttackWindow.of(8, 15, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.HOOK_SWORD_ULT2, 40, false,
                RigAttackWindow.of(8, 15, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.HOOK_SWORD_DUAL_ULT, 33, false,
                RigAttackWindow.of(5, 8, RIGHT_SWORD),
                RigAttackWindow.of(8, 13, LEFT_SWORD),
                RigAttackWindow.of(13, 18, RIGHT_SWORD))
        );
        put(RigAnimationSpec.attack(RigAnimationId.FLANKER_HOOK_SWORD_ULT, 33, false,
                RigAttackWindow.of(8, 18, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.HACKER_SWORD_ULT, 48, false,
                whiteAfterimageHooks(0, 7, 17, 27, 37),
                RigAttackWindow.of(9, 13, RIGHT_SWORD),
                RigAttackWindow.of(16, 24, RIGHT_SWORD),
                RigAttackWindow.of(27, 35, RIGHT_SWORD),
                RigAttackWindow.of(36, 45, RIGHT_SWORD),
                RigAttackWindow.of(46, 47, RIGHT_SWORD))
        );
        put(RigAnimationSpec.nonDamaging(RigAnimationId.GREAT_SWORD_ULT, 178));
        put(RigAnimationSpec.attack(RigAnimationId.WOOPIE_THE_SWORD_EXTRA_ULT, 24, false,
                woopieRushStartHook(RigAnimationId.WOOPIE_THE_SWORD_EXTRA_ULT),
                RigAttackWindow.of(5, 15, RIGHT_SWORD)));

        put(RigAnimationSpec.attack(RigAnimationId.WOOPIE_THE_SWORD_EXTRA_ULT_LEGENDARY, 21, false,
                woopieRushStartHook(RigAnimationId.WOOPIE_THE_SWORD_EXTRA_ULT_LEGENDARY),
                RigAttackWindow.of(5, 15, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.WOOPIE_THE_SWORD_FLY, 13, false,
                List.of(
                        RigAnimationSpec.RigTimedAnimationHook.at(0, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
                            mob.setDeltaMovement(Vec3.ZERO);
                            mob.hasImpulse = true;
                            mob.hurtMarked = true;

                            Vec3 offHandPos = RigPoseUtil.getLeftWeaponPosition(mob, RigAnimationId.WOOPIE_THE_SWORD_FLY, 0.0F);
                            Vec3 windPos = new Vec3(offHandPos.x, mob.getY() + 0.05D, offHandPos.z);
                            AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundMuteExplosionAtPos(BlockPos.containing(windPos), 4));
                            serverLevel.explode(mob, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
                            AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundWoopieSwordWindFx(windPos));
                        }),
                        RigAnimationSpec.RigTimedAnimationHook.at(12, mob -> RigAnimationController.play(mob, RigAnimationId.LEGENDARY_SWORD_ULT))
                ),
                RigAttackWindow.of(0, 10, LEFT_SWORD)).withVerticalMotion()
                .invulnerable()
                .dangerous()
        );
        put(RigAnimationSpec.attack(RigAnimationId.WOOPIE_THE_SWORD_ULT, 39, false,
                        hookAt(15, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
                            Vec3 windPos = RigPoseUtil.getPartPosition(mob, RigAnimationId.WOOPIE_THE_SWORD_ULT,
                                    4.0F, RigPart.RIGHT_WEAPON, Vec3.ZERO, 4.3D, 0.5D);
                            AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob),
                                    new ClientboundMuteExplosionAtPos(BlockPos.containing(windPos), 4));
                            serverLevel.explode(mob, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
                            AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob),
                                    new ClientboundWoopieSwordWindFx(windPos));
                        }),
                        RigAttackWindow.of(9, 20, RIGHT_SWORD))
                .damageMultiplier(2.1F));

        put(RigAnimationSpec.rolling(RigAnimationId.ROLL_BACKWARD, 13));
        put(RigAnimationSpec.rolling(RigAnimationId.ROLL_FORWARD, 13));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_FORWARD, 8));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_BACKWARD, 8));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_LEFT, 8));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_RIGHT, 8));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKDOWN_WAKEUP_LEFT, 12));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKDOWN_WAKEUP_RIGHT, 12));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.JUMP, 10));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.EAT_OFFHAND, 32, RigAnimationPlaybackType.LEFT_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.EAT_MAINHAND, 32, RigAnimationPlaybackType.MAIN_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.DEATH, 27));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.LAYING_DEATH, 27));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.LAYING_DEATH_DEAD, 27));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.IDLE, 48));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.IDLE_DUAL, 54));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNEEL, 48));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.MOUNT, 48));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SNEAK, 16));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SWIM, 20));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.WALK, 16));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.RUN, 12));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.RUN_HOLDING_DUAL_WEAPON, 11));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.RUN_HOLDING_WEAPON, 12));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SHIELD_OFFHAND, 56, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLOCK_SHIELD_OFFHAND, 4, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SHIELD_MAINHAND, 56, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLOCK_SHIELD_MAINHAND, 4, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SPINNING_WEAPON, 16, RigAnimationPlaybackType.MAIN_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.POINT_LEFT_HAND_TOWARD, 10, RigAnimationPlaybackType.LEFT_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.POINT_LEFT_HAND_MIDDLE, 10, RigAnimationPlaybackType.LEFT_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.POINT_LEFT_HAND_UP, 10, RigAnimationPlaybackType.LEFT_HAND));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.HOOK_GUN, 23));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.LEFT_HAND_HOOK, 20, RigAnimationPlaybackType.LEFT_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.LEFT_HAND_HOOK_TOP, 20, RigAnimationPlaybackType.LEFT_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.RIGHT_HAND_HOOK, 20, RigAnimationPlaybackType.MAIN_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.RIGHT_HAND_HOOK_TOP, 20, RigAnimationPlaybackType.MAIN_HAND));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.EATING_ELITE_1, 27));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.EATING_ELITE_2, 27));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.EATING_ELITE_3, 27));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.EATING_ELITE_4, 27));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.ELITE_HOLD_WEAPON, 68));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.ELITE_RUN_WEAPON, 68));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.ELITE_WALK_WEAPON, 68));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.HEROBRINE_ANIMATE, 100));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.HEROBRINE_ASSISTANCE, 54));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.HEROBRINE_RUN, 11));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.HEROBRINE_SACRIFICING, 54));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.HEROBRINE_STAGE_CHANGE, 54));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKED_ELITE, 27));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.LOW_CLONE_ESCAPE, 20));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.PLAYER_HEROBRINE_POSSESSION, 80));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.PORTAL_SUMMON, 80));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.HIT_BACKWARD, 21));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.HIT_LEFT, 19));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.HIT_RIGHT, 17));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKDOWN_FORWARD, 78));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKDOWN_BACKWARD, 47));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKDOWN_LEFT, 78));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKDOWN_RIGHT, 78));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.STUN_BACK, 37));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SUPER_KNOCK_BACK, 50));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.LEGENDARY_SWORD_KNOCKDOWN, 113));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SHOCKED, 17));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SHOCKED_LONG, 30));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_TWOHAND_RUN, 11));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_DIE, 660).invulnerable());
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_DIE_START, 20).invulnerable());
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_DIE_TICK, 20).invulnerable());
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_STATE_TRANSFORM, 1117).invulnerable().dangerous());
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_STATE_TRANSFORM_END, 20).invulnerable().dangerous());
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL, 100, RigAnimationPlaybackType.DEFAULT,
                List.of(
                        RigAnimationSpec.RigTimedAnimationHook.at(2, mob -> {
                            if (!(mob.level() instanceof ServerLevel)) return;
                            if (!(mob instanceof BlueDemonEntity blueDemonEntity)) return;
                            blueDemonEntity.setState(1);
                            blueDemonEntity.playSound(AnnoyingVillagersModSounds.BLUE_DEMON_SAY_TRIDENT_FESTIVAL.get(), 1.0F, 1.0F);
                        }),
                        blueDemonBothHandEffectHook(4, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL),
                        RigAnimationSpec.RigTimedAnimationHook.at(6, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
                            if (mob instanceof BlueDemonEntity) BlueDemonTridentItem.summonMissingTridentAndAnimate(serverLevel, mob);
                            ScreenShakeUtil.applyScreenShake(serverLevel, mob.blockPosition().getCenter(), 12.0D, 80, 8);
                        }),
                        RigAnimationSpec.RigTimedAnimationHook.at(10, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
                            BlueDemonTridentItem.spawnDamageZones(serverLevel, mob);
                            BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, mob, true);
                        }),
                        blueDemonBothHandEffectHook(16, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL),
                        RigAnimationSpec.RigTimedAnimationHook.at(24, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
                            BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, mob, true);
                            playBlueDemonTridentEffect(mob, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL, 24, RigPart.RIGHT_HAND);
                            playBlueDemonTridentEffect(mob, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL, 24, RigPart.LEFT_HAND);
                        }),
                        blueDemonBothHandEffectHook(36, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL),
                        blueDemonBothHandEffectHook(44, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL),
                        blueDemonBothHandEffectHook(56, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL),
                        blueDemonBothHandEffectHook(64, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL),
                        RigAnimationSpec.RigTimedAnimationHook.at(70, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
                            BlueDemonTridentItem.summonSuperLightningAtGroundedTridents(serverLevel, mob);
                            BlueDemonTridentItem.setStormEnergy(mob.getMainHandItem(), 0);
                            BlueDemonTridentItem.setStormEnergy(mob.getOffhandItem(), 0);
                            if (mob instanceof BlueDemonEntity blueDemonEntity) {
                                blueDemonEntity.beginStateTwoTransform();
                                RigAnimationController.play(blueDemonEntity, RigAnimationId.BLUE_DEMON_STATE_TRANSFORM);
                            }
                        }),
                        blueDemonBothHandEffectHook(76, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL)))
                .invulnerable()
                .dangerous()
        );
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_EXTRA_ATTACK, 14, RigAnimationPlaybackType.DEFAULT,
                List.of(
                        blueDemonSpinHook(4),
                        blueDemonBothHandEffectHook(4, RigAnimationId.BLUE_DEMON_EXTRA_ATTACK),
                        blueDemonSpinHook(5),
                        blueDemonSpinHook(6),
                        blueDemonSpinHook(7),
                        blueDemonBothHandEffectHook(7, RigAnimationId.BLUE_DEMON_EXTRA_ATTACK),
                        blueDemonSpinHook(8),
                        blueDemonSpinHook(10))));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_EXTRA_ATTACK_LEGENDARY, 47, false,
                List.of(
                        blueDemonHandEffectHook(2, RigAnimationId.BLUE_DEMON_EXTRA_ATTACK_LEGENDARY, RigPart.LEFT_HAND),
                        blueDemonThrowTridentHook(8, RigAnimationId.BLUE_DEMON_EXTRA_ATTACK_LEGENDARY, InteractionHand.OFF_HAND, TridentMode.DEFAULT),
                        RigAnimationSpec.RigTimedAnimationHook.hideLeftToolAt(8)),
                RigAttackWindow.of(10, 13, RIGHT_SPEAR),
                RigAttackWindow.of(14, 16, LEFT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_ATTACK1, 23, false,
                RigAttackWindow.of(5, 15, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_ATTACK2, 38, false,
                RigAttackWindow.of(10, 13, RIGHT_SPEAR),
                RigAttackWindow.of(14, 25, LEFT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_ATTACK3, 32, false,
                List.of(
                        blueDemonHandEffectHook(2, RigAnimationId.BLUE_DEMON_ATTACK3, RigPart.RIGHT_HAND),
                        blueDemonHandEffectHook(6, RigAnimationId.BLUE_DEMON_ATTACK3, RigPart.RIGHT_HAND),
                        blueDemonSoundHook(10, SoundEvents.TRIDENT_HIT_GROUND, 1.0F, 1.0F),
                        blueDemonHandEffectHook(10, RigAnimationId.BLUE_DEMON_ATTACK3, RigPart.RIGHT_HAND),
                        RigAnimationSpec.RigTimedAnimationHook.at(12, mob -> blueDemonGroundFracture(mob, 2.0D, -0.24D, 1.2D))),
                RigAttackWindow.of(10, 20, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_ATTACK4, 17, false,
                RigAttackWindow.of(5, 15, LEFT_SPEAR, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_ATTACK5, 27, false,
                List.of(
                        blueDemonSoundHook(4, SoundEvents.TRIDENT_RETURN, 1.0F, 1.0F),
                        blueDemonHandEffectHook(4, RigAnimationId.BLUE_DEMON_ATTACK5, RigPart.RIGHT_HAND),
                        blueDemonSoundHook(7, SoundEvents.TRIDENT_RETURN, 1.0F, 1.0F),
                        blueDemonHandEffectHook(7, RigAnimationId.BLUE_DEMON_ATTACK5, RigPart.RIGHT_HAND),
                        RigAnimationSpec.RigTimedAnimationHook.at(16, mob -> spawnBlueDemonTridentLightningAtRightTool(mob, RigAnimationId.BLUE_DEMON_ATTACK5, 16))),
                RigAttackWindow.of(15, 25, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_ATTACK6, 44, false,
                List.of(
                        blueDemonBothHandEffectHook(8, RigAnimationId.BLUE_DEMON_ATTACK6),
                        blueDemonBothHandEffectHook(14, RigAnimationId.BLUE_DEMON_ATTACK6),
                        blueDemonBothHandEffectHook(20, RigAnimationId.BLUE_DEMON_ATTACK6)),
                RigAttackWindow.of(6, 8, RIGHT_SPEAR),
                RigAttackWindow.of(8, 10, LEFT_SPEAR),
                RigAttackWindow.of(10, 12, RIGHT_SPEAR),
                RigAttackWindow.of(12, 14, LEFT_SPEAR),
                RigAttackWindow.of(14, 16, RIGHT_SPEAR),
                RigAttackWindow.of(16, 18, LEFT_SPEAR),
                RigAttackWindow.of(25, 35, RIGHT_SPEAR, LEFT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_DASH_ATTACK, 29, false,
                RigAttackWindow.of(7, 20, RIGHT_SPEAR, LEFT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_JUMP_ATTACK, 41, true,
                RigAttackWindow.of(5, 15, RIGHT_SPEAR, LEFT_SPEAR),
                RigAttackWindow.of(18, 25, RIGHT_SPEAR, LEFT_SPEAR),
                RigAttackWindow.of(28, 35, RIGHT_SPEAR, LEFT_SPEAR)));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_ULT, 100, RigAnimationPlaybackType.DEFAULT,
                List.of(
                        blueDemonBothHandEffectHook(4, RigAnimationId.BLUE_DEMON_ULT),
                        blueDemonBothHandEffectHook(16, RigAnimationId.BLUE_DEMON_ULT),
                        RigAnimationSpec.RigTimedAnimationHook.at(20, mob -> {
                            if (mob.level() instanceof ServerLevel serverLevel) BlueDemonTridentItem.spawnDamageZones(serverLevel, mob);
                        }),
                        blueDemonBothHandEffectHook(24, RigAnimationId.BLUE_DEMON_ULT),
                        blueDemonBothHandEffectHook(36, RigAnimationId.BLUE_DEMON_ULT),
                        blueDemonBothHandEffectHook(44, RigAnimationId.BLUE_DEMON_ULT),
                        blueDemonBothHandEffectHook(56, RigAnimationId.BLUE_DEMON_ULT),
                        blueDemonBothHandEffectHook(64, RigAnimationId.BLUE_DEMON_ULT),
                        blueDemonBothHandEffectHook(76, RigAnimationId.BLUE_DEMON_ULT)))
                .invulnerable()
                .dangerous()
        );
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_THROW_ATTACK1, 28, false,
                List.of(
                        blueDemonHandEffectHook(0, RigAnimationId.BLUE_DEMON_THROW_ATTACK1, RigPart.RIGHT_HAND),
                        blueDemonThrowTridentHook(1, RigAnimationId.BLUE_DEMON_THROW_ATTACK1, InteractionHand.MAIN_HAND, TridentMode.DEFAULT),
                        RigAnimationSpec.RigTimedAnimationHook.hideRightToolAt(1)),
                RigAttackWindow.of(4, 6, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_THROW_ATTACK2, 43, false,
                List.of(
                        blueDemonHandEffectHook(2, RigAnimationId.BLUE_DEMON_THROW_ATTACK2, RigPart.LEFT_HAND),
                        blueDemonThrowTridentHook(12, RigAnimationId.BLUE_DEMON_THROW_ATTACK2, InteractionHand.OFF_HAND, TridentMode.DEFAULT),
                        RigAnimationSpec.RigTimedAnimationHook.hideLeftToolAt(12)),
                RigAttackWindow.of(10, 13, RIGHT_SPEAR),
                RigAttackWindow.of(14, 16, LEFT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_THROW_ATTACK3, 23, false,
                List.of(
                        blueDemonHandEffectHook(2, RigAnimationId.BLUE_DEMON_THROW_ATTACK3, RigPart.RIGHT_WEAPON),
                        RigAnimationSpec.RigTimedAnimationHook.at(6, mob -> {
                            playBlueDemonTridentEffect(mob, RigAnimationId.BLUE_DEMON_THROW_ATTACK3, 6, RigPart.RIGHT_WEAPON);
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
                            BlueDemonThunderBeamEntity beam = new BlueDemonThunderBeamEntity(AnnoyingVillagersModEntities.BLUE_DEMON_THUNDER_BEAM.get(), serverLevel, mob, 10, 6, 7.5F);
                            beam.initSpawnState();
                            serverLevel.addFreshEntity(beam);
                        }),
                        blueDemonHandEffectHook(10, RigAnimationId.BLUE_DEMON_THROW_ATTACK3, RigPart.RIGHT_WEAPON)),
                RigAttackWindow.of(6, 10, RIGHT_FIST),
                RigAttackWindow.of(10, 14, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_THROW_ATTACK4, 43, false,
                List.of(
                        blueDemonBothHandEffectHook(9, RigAnimationId.BLUE_DEMON_THROW_ATTACK4),
                        blueDemonThrowTridentHook(9, RigAnimationId.BLUE_DEMON_THROW_ATTACK4, InteractionHand.OFF_HAND, TridentMode.LIGHTNING),
                        RigAnimationSpec.RigTimedAnimationHook.hideLeftToolAt(9),
                        blueDemonThrowTridentHook(9, RigAnimationId.BLUE_DEMON_THROW_ATTACK4, InteractionHand.MAIN_HAND, TridentMode.LIGHTNING),
                        RigAnimationSpec.RigTimedAnimationHook.hideRightToolAt(9)),
                RigAttackWindow.of(11, 24)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_THROW_ATTACK5, 40, false,
                List.of(
                        blueDemonSoundHook(4, SoundEvents.TRIDENT_RETURN, 1.0F, 1.0F),
                        blueDemonHandEffectHook(4, RigAnimationId.BLUE_DEMON_THROW_ATTACK5, RigPart.RIGHT_HAND),
                        blueDemonSoundHook(7, SoundEvents.TRIDENT_RETURN, 1.0F, 1.0F),
                        blueDemonHandEffectHook(7, RigAnimationId.BLUE_DEMON_THROW_ATTACK5, RigPart.RIGHT_HAND),
                        blueDemonHandEffectHook(6, RigAnimationId.BLUE_DEMON_THROW_ATTACK5, RigPart.RIGHT_HAND),
                        blueDemonThrowTridentHook(14, RigAnimationId.BLUE_DEMON_THROW_ATTACK5, InteractionHand.MAIN_HAND, TridentMode.EXPLOSION),
                        RigAnimationSpec.RigTimedAnimationHook.hideRightToolAt(15)),
                RigAttackWindow.of(15, 25, RIGHT_SPEAR)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_THROW_DASH_ATTACK, 42, false,
                List.of(
                        blueDemonHandEffectHook(3, RigAnimationId.BLUE_DEMON_THROW_DASH_ATTACK, RigPart.LEFT_HAND),
                        blueDemonThrowTridentHook(3, RigAnimationId.BLUE_DEMON_THROW_DASH_ATTACK, InteractionHand.OFF_HAND, TridentMode.DEFAULT),
                        RigAnimationSpec.RigTimedAnimationHook.hideLeftToolAt(3),
                        blueDemonHandEffectHook(5, RigAnimationId.BLUE_DEMON_THROW_DASH_ATTACK, RigPart.RIGHT_HAND),
                        blueDemonThrowTridentHook(5, RigAnimationId.BLUE_DEMON_THROW_DASH_ATTACK, InteractionHand.MAIN_HAND, TridentMode.DEFAULT),
                        RigAnimationSpec.RigTimedAnimationHook.hideRightToolAt(5)),
                RigAttackWindow.of(7, 27)));
        put(RigAnimationSpec.attack(RigAnimationId.BLUE_DEMON_THROW_JUMP_ATTACK, 30, true,
                List.of(
                        blueDemonBothHandEffectHook(4, RigAnimationId.BLUE_DEMON_THROW_JUMP_ATTACK),
                        blueDemonThrowTridentHook(4, RigAnimationId.BLUE_DEMON_THROW_JUMP_ATTACK, InteractionHand.OFF_HAND, TridentMode.DEFAULT),
                        RigAnimationSpec.RigTimedAnimationHook.hideLeftToolAt(4),
                        blueDemonThrowTridentHook(4, RigAnimationId.BLUE_DEMON_THROW_JUMP_ATTACK, InteractionHand.MAIN_HAND, TridentMode.DEFAULT),
                        RigAnimationSpec.RigTimedAnimationHook.hideRightToolAt(4)),
                RigAttackWindow.of(7, 9, RIGHT_SPEAR, LEFT_SPEAR),
                RigAttackWindow.of(9, 12, RIGHT_SPEAR, LEFT_SPEAR)));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BLUE_DEMON_THROW_ULT, 100, RigAnimationPlaybackType.DEFAULT,
                List.of(
                        blueDemonBothHandEffectHook(4, RigAnimationId.BLUE_DEMON_THROW_ULT),
                        blueDemonBothHandEffectHook(16, RigAnimationId.BLUE_DEMON_THROW_ULT),
                        RigAnimationSpec.RigTimedAnimationHook.at(20, mob -> {
                            if (mob.level() instanceof ServerLevel serverLevel) BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, mob);
                        }),
                        blueDemonBothHandEffectHook(24, RigAnimationId.BLUE_DEMON_THROW_ULT),
                        blueDemonBothHandEffectHook(36, RigAnimationId.BLUE_DEMON_THROW_ULT),
                        blueDemonBothHandEffectHook(44, RigAnimationId.BLUE_DEMON_THROW_ULT),
                        blueDemonBothHandEffectHook(56, RigAnimationId.BLUE_DEMON_THROW_ULT),
                        blueDemonBothHandEffectHook(64, RigAnimationId.BLUE_DEMON_THROW_ULT),
                        blueDemonBothHandEffectHook(76, RigAnimationId.BLUE_DEMON_THROW_ULT),
                        RigAnimationSpec.RigTimedAnimationHook.at(80, mob -> {
                            if (mob.level() instanceof ServerLevel serverLevel) BlueDemonTridentItem.summonLightningAtGroundedTridents(serverLevel, mob);
                        })))
                .invulnerable()
                .dangerous()
        );

        put(RigAnimationSpec.nonDamaging(RigAnimationId.LEGENDARY_SWORD_IDLE, 54));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.LEGENDARY_SWORD_WALK, 17));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.LEGENDARY_SWORD_RUN, 11));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_ATTACK1, 50, false,
                RigAttackWindow.of(12, 20, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_ATTACK2, 44, false,
                RigAttackWindow.of(10, 18, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_ATTACK3, 44, false,
                RigAttackWindow.of(9, 19, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_ATTACK4, 39, false,
                RigAttackWindow.of(5, 15, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_ATTACK5, 41, false,
                List.of(
                        RigAnimationSpec.RigTimedAnimationHook.at(0, mob -> {
                            mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 2, false, false, false));
                            CommonUtil.stunImmunity(mob, 30, 10);
                        }),
                        groundSlamTimedHook(14, RigAnimationId.LEGENDARY_SWORD_ATTACK5, 1.5D, 0.7D, 35, 0.7D, 2.5D),
                        RigAnimationSpec.RigTimedAnimationHook.at(14, mob -> {
                            if (mob.level() instanceof ServerLevel serverLevel) ScreenShakeUtil.applyScreenShake(serverLevel, mob.position(), 12.0D, 20, 2);
                        })),
                RigAttackWindow.of(3, 15, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_EXTRA_ATTACK, 46, false,
                RigAttackWindow.of(10, 20, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_JUMP_ATTACK, 53, true,
                List.of(
                        whiteAfterimageHook(3),
                        whiteAfterimageHook(5),
                        whiteAfterimageHook(7),
                        whiteAfterimageHook(9),
                        whiteAfterimageHook(11),
                        whiteAfterimageHook(13),
                        whiteAfterimageHook(15),
                        whiteAfterimageHook(17),
                        groundSlamTimedHook(18, RigAnimationId.LEGENDARY_SWORD_JUMP_ATTACK, 1.5D, 0.8D, 45, 0.7D, 2.5D)),
                RigAttackWindow.of(2, 6, RIGHT_GREATSWORD),
                RigAttackWindow.of(16, 18, RIGHT_GREATSWORD),
                RigAttackWindow.of(19, 25, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_DASH_ATTACK, 25, false,
                whiteAfterimageHooks(3, 5, 7, 9, 11),
                RigAttackWindow.of(3, 16, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_ULT, 40, false,
                List.of(
                        RigAnimationSpec.RigTimedAnimationHook.at(0, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;

                            serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), AnnoyingVillagersModSounds.HEAVY_ATTACK_START.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD_2.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, mob.getX(), mob.getY(), mob.getZ(), 15, 0.0D, 0.0D, 0.0D, 0.2D);
                            serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, mob.getX(), mob.getEyeY(), mob.getZ(), 100, 0.0D, 0.0D, 0.0D, 0.5D);
                        }),
                        groundSlamTimedHook(10, RigAnimationId.LEGENDARY_SWORD_ULT, 2.0D, 0.8D, 50, 0.6D, 2.5D),
                        RigAnimationSpec.RigTimedAnimationHook.at(10, mob -> {
                            if (!(mob.level() instanceof ServerLevel serverLevel)) return;

                            Vec3 legendarySwordPos = RigPoseUtil.getRightWeaponPosition(mob, RigAnimationId.LEGENDARY_SWORD_ULT, 10, 1.5D);

                            BlockPos centerPos = BlockPos.containing(legendarySwordPos);
                            for (int radius = 1; radius <= 6; radius++) {
                                int delayTicks = (radius - 1) * 2;
                                int ringRadius = radius;
                                new DelayedTask(delayTicks) {
                                    @Override
                                    public void run() {
                                        if (mob.isRemoved()) return;
                                        LegendarySwordItem.spawnCircleRing(serverLevel, centerPos, ringRadius, mob);
                                    }
                                };
                            }
                        })),
                RigAttackWindow.of(10, 20, RIGHT_GREATSWORD))
                .invulnerable()
                .dangerous()
        );
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_EXTRA_ULT, 45, false,
                List.of(
                        RigAnimationSpec.RigTimedAnimationHook.at(11, mob -> {
                            if (mob instanceof AngrySteveEntity angrySteve) angrySteve.startLegendaryAwakening();
                        })),
                RigAttackWindow.of(11, 15, RIGHT_GREATSWORD))
                .invulnerable()
                .dangerous()
        );
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_DUAL_AUTO1, 49, false,
                RigAttackWindow.of(10, 13, RIGHT_GREATSWORD),
                RigAttackWindow.of(14, 16, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_DUAL_AUTO2, 42, false,
                RigAttackWindow.of(7, 10, LEFT_SWORD),
                RigAttackWindow.of(11, 18, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.LEGENDARY_SWORD_DUAL_AUTO3, 40, false,
                RigAttackWindow.of(9, 13, RIGHT_GREATSWORD),
                RigAttackWindow.of(14, 20, LEFT_SWORD)));

        put(RigAnimationSpec.attack(RigAnimationId.SWORDMAN_HEROBRINE_ATTACK1, 21, false,
                RigAttackWindow.of(4, 12, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORDMAN_HEROBRINE_ATTACK2, 33, false,
                RigAttackWindow.of(4, 10, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORDMAN_HEROBRINE_ATTACK3, 36, false,
                emptyHooks(9),
                RigAttackWindow.of(4, 8, RIGHT_GREATSWORD),
                RigAttackWindow.of(11, 14, LEFT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORDMAN_HEROBRINE_ATTACK4, 30, false,
                RigAttackWindow.of(5, 8, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORDMAN_HEROBRINE_ATTACK5, 54, false,
                emptyHooks(16),
                RigAttackWindow.of(13, 16, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORDMAN_HEROBRINE_DASH_ATTACK, 44, false,
                emptyHooks(9),
                RigAttackWindow.of(3, 7, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORDMAN_HEROBRINE_JUMP_ATTACK, 26, true,
                emptyHooks(5, 10),
                RigAttackWindow.of(5, 11, RIGHT_GREATSWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.SWORDMAN_HEROBRINE_EXTRA_ATTACK, 52, false,
                emptyHooks(7),
                RigAttackWindow.of(7, 8)));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SWORDMAN_HEROBRINE_ULT, 82, RigAnimationPlaybackType.DEFAULT,
                emptyHooks(0)));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SWORDMAN_HEROBRINE_EXTRA_ULT, 81, RigAnimationPlaybackType.DEFAULT,
                emptyHooks(0)));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.AEGIS_HEROBRINE_IDLE, 50));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.AEGIS_HEROBRINE_GUARD, 50));
        put(RigAnimationSpec.attack(RigAnimationId.AEGIS_HEROBRINE_ATTACK1, 33, false,
                RigAttackWindow.of(2, 4, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.AEGIS_HEROBRINE_ATTACK2, 23, false,
                emptyHooks(6),
                RigAttackWindow.of(2, 4, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.AEGIS_HEROBRINE_ATTACK3, 23, false,
                RigAttackWindow.of(4, 9, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.AEGIS_HEROBRINE_ATTACK4, 40, false,
                emptyHooks(16),
                RigAttackWindow.of(6, 10, RIGHT_FIST),
                RigAttackWindow.of(14, 17, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.AEGIS_HEROBRINE_ATTACK5, 80, false,
                emptyHooks(11, 19, 21, 22),
                RigAttackWindow.of(21, 23)));
        put(RigAnimationSpec.attack(RigAnimationId.AEGIS_HEROBRINE_DASH_ATTACK, 45, false,
                RigAttackWindow.of(3, 4, LEFT_FOOT),
                RigAttackWindow.of(9, 15, LEFT_FOOT)));
        put(RigAnimationSpec.attack(RigAnimationId.AEGIS_HEROBRINE_JUMP_ATTACK, 70, true,
                emptyHooks(13, 27, 53, 65, 69),
                RigAttackWindow.of(13, 15)));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.AEGIS_HEROBRINE_ULT, 13, RigAnimationPlaybackType.DEFAULT,
                emptyHooks(2)));
        put(RigAnimationSpec.attack(RigAnimationId.AEGIS_HEROBRINE_EXTRA_ATTACK, 33, false,
                RigAttackWindow.of(4, 5),
                RigAttackWindow.of(6, 7),
                RigAttackWindow.of(8, 9),
                RigAttackWindow.of(10, 11),
                RigAttackWindow.of(12, 13),
                RigAttackWindow.of(14, 15),
                RigAttackWindow.of(16, 17),
                RigAttackWindow.of(20, 22)));

        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_ATTACK1, 42, false,
                RigAttackWindow.of(3, 5, RIGHT_GLAIVE),
                RigAttackWindow.of(11, 13, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_ATTACK2, 41, false,
                RigAttackWindow.of(12, 15, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_ATTACK3, 50, false,
                RigAttackWindow.of(13, 15, RIGHT_GLAIVE),
                RigAttackWindow.of(20, 23, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_ATTACK4, 47, false,
                RigAttackWindow.of(10, 12, RIGHT_GLAIVE),
                RigAttackWindow.of(21, 23, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_ATTACK5, 42, false,
                RigAttackWindow.of(2, 4, RIGHT_GLAIVE),
                RigAttackWindow.of(5, 7, RIGHT_GLAIVE),
                RigAttackWindow.of(11, 14, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_DASH_ATTACK, 44, false,
                RigAttackWindow.of(10, 12, RIGHT_GLAIVE),
                RigAttackWindow.of(14, 16, RIGHT_GLAIVE),
                RigAttackWindow.of(22, 25, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_JUMP_ATTACK, 40, true,
                emptyHooks(8, 13),
                RigAttackWindow.of(1, 4, RIGHT_GLAIVE),
                RigAttackWindow.of(6, 9, RIGHT_GLAIVE),
                RigAttackWindow.of(10, 15, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_ULT, 49, false,
                emptyHooks(23),
                RigAttackWindow.of(17, 22, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_EXTRA_ULT, 54, false,
                emptyHooks(27),
                RigAttackWindow.of(23, 26, RIGHT_GLAIVE)));
        put(RigAnimationSpec.attack(RigAnimationId.GLAIVE_HEROBRINE_EXTRA_ATTACK, 31, false,
                RigAttackWindow.of(1, 4, RIGHT_GLAIVE)));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.REAPER_HEROBRINE_IDLE, 40));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_ATTACK1, 42, false,
                RigAttackWindow.of(12, 14, RIGHT_SCYTHE)));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_ATTACK2, 33, false,
                RigAttackWindow.of(3, 9, RIGHT_SCYTHE)));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_ATTACK3, 29, false,
                RigAttackWindow.of(4, 7, RIGHT_SCYTHE),
                RigAttackWindow.of(12, 13)));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_ATTACK4, 64, false,
                RigAttackWindow.of(12, 15, RIGHT_SCYTHE)));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_ATTACK5, 40, false,
                RigAttackWindow.of(10, 15, RIGHT_SCYTHE)));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_DASH_ATTACK, 43, false,
                RigAttackWindow.of(12, 14, RIGHT_SCYTHE)));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_JUMP_ATTACK, 43, true,
                RigAttackWindow.of(11, 13, RIGHT_SCYTHE)));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_EXTRA_ATTACK, 32, false,
                RigAttackWindow.of(3, 5, RIGHT_SCYTHE),
                RigAttackWindow.of(6, 8, RIGHT_SCYTHE),
                RigAttackWindow.of(9, 11, RIGHT_SCYTHE),
                RigAttackWindow.of(12, 14, RIGHT_SCYTHE),
                RigAttackWindow.of(15, 17, RIGHT_SCYTHE)));
        put(RigAnimationSpec.attack(RigAnimationId.REAPER_HEROBRINE_ULT, 58, false,
                emptyHooks(0, 4, 22),
                RigAttackWindow.of(20, 23)));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.REAPER_HEROBRINE_EXTRA_ULT, 23));

        put(RigAnimationSpec.nonDamaging(RigAnimationId.NULL_IDLE, 40));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.NULL_WALK, 20));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.NULL_RUN, 22));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_ATTACK1, 20, false,
                emptyHooks(1),
                RigAttackWindow.of(6, 8)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_ATTACK2, 22, false,
                emptyHooks(1),
                RigAttackWindow.of(6, 8),
                RigAttackWindow.of(12, 14)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_ATTACK3, 34, false,
                emptyHooks(1),
                RigAttackWindow.of(4, 6),
                RigAttackWindow.of(8, 10),
                RigAttackWindow.of(14, 16)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_ATTACK4, 40, false,
                RigAttackWindow.of(2, 5),
                RigAttackWindow.of(18, 20, LEFT_FIST),
                RigAttackWindow.of(20, 22, LEFT_FIST),
                RigAttackWindow.of(22, 24, LEFT_FIST),
                RigAttackWindow.of(24, 27, LEFT_FIST)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_ATTACK5, 60, false,
                emptyHooks(1, 21, 29),
                RigAttackWindow.of(29, 30)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_DASH_ATTACK, 34, false,
                RigAttackWindow.of(5, 9),
                RigAttackWindow.of(9, 13)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_JUMP_ATTACK, 30, true,
                emptyHooks(1, 7, 9, 10, 11),
                RigAttackWindow.of(10, 11)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_EXTRA_ATTACK, 20, false,
                RigAttackWindow.of(1, 2, LEFT_FIST)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_EXTRA_ULT, 57, false,
                emptyHooks(30),
                RigAttackWindow.of(10, 12),
                RigAttackWindow.of(35, 41)));
        put(RigAnimationSpec.attack(RigAnimationId.NULL_SKELETON_SPAWN, 59, false,
                RigAttackWindow.of(13, 15),
                RigAttackWindow.of(26, 28),
                RigAttackWindow.of(35, 37)));

        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK1, 42, false,
                RigAttackWindow.of(3, 10, RIGHT_SLEDGEHAMMER)));
        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK2, 33, false,
                RigAttackWindow.of(3, 10, RIGHT_SLEDGEHAMMER)));
        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK3, 46, false,
                emptyHooks(15),
                RigAttackWindow.of(14, 16)));
        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK4, 35, false,
                RigAttackWindow.of(3, 6, RIGHT_SLEDGEHAMMER)));
        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK5, 32, false,
                emptyHooks(11),
                RigAttackWindow.of(3, 6, RIGHT_SLEDGEHAMMER),
                RigAttackWindow.of(8, 11, RIGHT_SLEDGEHAMMER)));
        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_EXTRA_ATTACK, 51, false,
                emptyHooks(10, 17, 28),
                RigAttackWindow.of(9, 10),
                RigAttackWindow.of(16, 17),
                RigAttackWindow.of(27, 28)));
        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_DASH_ATTACK, 38, false,
                RigAttackWindow.of(11, 15, RIGHT_SLEDGEHAMMER)));
        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_JUMP_ATTACK, 45, true,
                emptyHooks(2, 18),
                RigAttackWindow.of(16, 23, RIGHT_SLEDGEHAMMER)));
        put(RigAnimationSpec.attack(RigAnimationId.SLEDGEHAMMER_HEROBRINE_ULT, 45, false,
                emptyHooks(12),
                RigAttackWindow.of(11, 14, RIGHT_SLEDGEHAMMER)));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SLEDGEHAMMER_HEROBRINE_EXTRA_ULT, 91, RigAnimationPlaybackType.DEFAULT,
                emptyHooks(30, 34, 36, 38, 40)));

        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_MACHINE_GUN, 50, false,
                emptyHooks(2),
                RigAttackWindow.of(0, 1)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_JUMP_ATTACK, 23, true,
                emptyHooks(6, 10),
                RigAttackWindow.of(6, 10, RIGHT_FOOT)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_DASH_ATTACK, 19, false,
                emptyHooks(2),
                RigAttackWindow.of(2, 3, LEFT_FIST)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_EXTRA_ULT, 30, false,
                emptyHooks(1, 7, 9, 10, 11, 12),
                RigAttackWindow.of(10, 11)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_EXTRA_ATTACK, 28, false,
                emptyHooks(12),
                RigAttackWindow.of(9, 14, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_PILLAR_EXTRA_ATTACK, 23, false,
                emptyHooks(2),
                RigAttackWindow.of(2, 4, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_ULT1, 51, false,
                emptyHooks(2),
                RigAttackWindow.of(0, 1)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_ULT2, 26, false,
                emptyHooks(13),
                RigAttackWindow.of(10, 12)));

        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_SWORD_ATTACK1, 35, false,
                RigAttackWindow.of(4, 8, LEFT_SWORD),
                RigAttackWindow.of(10, 14, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_SWORD_ATTACK2, 37, false,
                RigAttackWindow.of(7, 17, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_SWORD_ATTACK3, 42, false,
                RigAttackWindow.of(4, 6, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_SWORD_ATTACK4, 17, false,
                RigAttackWindow.of(3, 5, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_SWORD_JUMP_ATTACK, 46, true,
                emptyHooks(11),
                RigAttackWindow.of(9, 11, RIGHT_SWORD),
                RigAttackWindow.of(10, 13, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_SWORD_DASH_ATTACK, 36, false,
                RigAttackWindow.of(5, 10, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.OBSIDIAN_SWORD_ULT, 50, false,
                emptyHooks(10, 17, 28),
                RigAttackWindow.of(9, 10, RIGHT_SWORD),
                RigAttackWindow.of(16, 17, RIGHT_SWORD),
                RigAttackWindow.of(27, 28, RIGHT_SWORD)));


        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_SWORD_ATTACK1, 25, false,
                emptyHooks(6),
                RigAttackWindow.of(2, 4, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_SWORD_ATTACK2, 43, false,
                RigAttackWindow.of(14, 16, RIGHT_SWORD),
                RigAttackWindow.of(14, 16, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_SWORD_ATTACK3, 48, false,
                RigAttackWindow.of(14, 16, RIGHT_SWORD),
                RigAttackWindow.of(14, 16, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_SWORD_ATTACK4, 44, false,
                emptyHooks(17),
                RigAttackWindow.of(7, 17, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_SWORD_JUMP_ATTACK, 23, true,
                emptyHooks(8),
                RigAttackWindow.of(5, 8, RIGHT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_SWORD_DASH_ATTACK, 38, false,
                emptyHooks(9),
                RigAttackWindow.of(4, 8, RIGHT_SWORD),
                RigAttackWindow.of(11, 14, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_SWORD_EXTRA_ATTACK, 42, false,
                emptyHooks(16),
                RigAttackWindow.of(6, 10, LEFT_FIST),
                RigAttackWindow.of(14, 17, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_SWORD_ULT, 42, false,
                emptyHooks(25),
                RigAttackWindow.of(22, 23, RIGHT_SWORD),
                RigAttackWindow.of(26, 28, LEFT_SWORD)));
        put(RigAnimationSpec.attack(RigAnimationId.DUAL_OBSIDIAN_PILLAR_ULT, 41, false,
                emptyHooks(25),
                RigAttackWindow.of(22, 23, RIGHT_SWORD),
                RigAttackWindow.of(26, 28, LEFT_SWORD)));
    }

    private RigAnimationSpecs() {}

    public static RigAnimationSpec get(RigAnimationId animationId) {
        RigAnimationSpec spec = SPECS.get(animationId);
        if (spec == null) throw new IllegalArgumentException("Missing rig animation spec for " + animationId);
        return spec;
    }

    public static boolean isAttack(RigAnimationId animationId) {
        RigAnimationSpec spec = SPECS.get(animationId);
        return spec != null && spec.damagesTarget();
    }

    private static List<RigAnimationSpec.RigTimedAnimationHook> startHook(RigAnimationSpec.RigAnimationHook hook) {
        return List.of(RigAnimationSpec.RigTimedAnimationHook.at(RigAnimationSpec.RigTimedAnimationHook.START, hook));
    }

    private static List<RigAnimationSpec.RigTimedAnimationHook> endHook(RigAnimationSpec.RigAnimationHook hook) {
        return List.of(RigAnimationSpec.RigTimedAnimationHook.at(RigAnimationSpec.RigTimedAnimationHook.END, hook));
    }

    private static List<RigAnimationSpec.RigTimedAnimationHook> hookAt(int tick, RigAnimationSpec.RigAnimationHook hook) {
        return List.of(RigAnimationSpec.RigTimedAnimationHook.at(tick, hook));
    }

    private static List<RigAnimationSpec.RigTimedAnimationHook> groundSlamHook(int tick, RigAnimationId animationId, double forwardOffset, double particleRadius, int particleCount, double spread, double fractureRadius) {
        return List.of(groundSlamTimedHook(tick, animationId, forwardOffset, particleRadius, particleCount, spread, fractureRadius));
    }

    private static RigAnimationSpec.RigTimedAnimationHook groundSlamTimedHook(int tick, RigAnimationId animationId, double forwardOffset, double particleRadius, int particleCount, double spread, double fractureRadius) {
        return RigAnimationSpec.RigTimedAnimationHook.at(tick, mob -> {
            if (!(mob.level() instanceof ServerLevel serverLevel)) return;

            Vec3 impactPos = RigPoseUtil.getRightWeaponPosition(mob, animationId, tick, forwardOffset);
            CommonUtil.spawnGroundSlamFracture(mob, serverLevel, impactPos, particleRadius, particleCount, spread, fractureRadius);
        });
    }

    private static RigAnimationSpec.RigTimedAnimationHook whiteAfterimageHook(int tick) {
        return RigAnimationSpec.RigTimedAnimationHook.at(tick, mob -> {
            if (!(mob.level() instanceof ServerLevel serverLevel)) return;

            serverLevel.sendParticles(AnnoyingVillagersModParticleTypes.WHITE_AFTERIMAGE.get(), mob.getX(), mob.getY(), mob.getZ(), 0, mob.getId(), 0.0D, 0.0D, 1.0D);
        });
    }

    private static List<RigAnimationSpec.RigTimedAnimationHook> whiteAfterimageHooks(int... ticks) {
        List<RigAnimationSpec.RigTimedAnimationHook> hooks = new ArrayList<>(ticks.length);

        for (int tick : ticks) {
            hooks.add(whiteAfterimageHook(tick));
        }

        return List.copyOf(hooks);
    }

    private static List<RigAnimationSpec.RigTimedAnimationHook> woopieRushStartHook(RigAnimationId animationId) {
        return startHook(mob -> {
            if (!(mob.level() instanceof ServerLevel serverLevel)) return;

            boolean mainHandWoopie = mob.getMainHandItem().is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
            boolean offHandWoopie = mob.getOffhandItem().is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
            if (!mainHandWoopie && !offHandWoopie) return;

            Vec3 windPos = offHandWoopie ? RigPoseUtil.getLeftWeaponPosition(mob, animationId, 0.0F) : RigPoseUtil.getRightWeaponPosition(mob, animationId, 0.0F);
            if (windPos == null) return;

            AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundMuteExplosionAtPos(BlockPos.containing(windPos), 4));
            serverLevel.explode(mob, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
            AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundWoopieSwordWindFx(windPos));

            Vec3 dashDir = mob.getLookAngle();
            LivingEntity target = mob.getTarget();

            if (target != null && target.isAlive()) {
                Vec3 toTarget = target.position().subtract(mob.position());
                dashDir = new Vec3(toTarget.x, 0.0D, toTarget.z);
            }

            dashDir = new Vec3(dashDir.x, 0.0D, dashDir.z);
            if (dashDir.lengthSqr() < 1.0E-6D) dashDir = Vec3.directionFromRotation(0.0F, mob.getYRot());

            Vec3 dash = dashDir.normalize().scale(2.2D);

            for (int tick = 1; tick <= 3; tick++) {
                new DelayedTask(tick) {
                    @Override
                    public void run() {
                        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
                        if (RigAnimationController.getActiveAnimationId(mob) != animationId) return;

                        Vec3 currentMotion = mob.getDeltaMovement();
                        mob.setDeltaMovement(dash.x, currentMotion.y, dash.z);
                        mob.hasImpulse = true;
                        mob.hurtMarked = true;
                    }
                };
            }
        });
    }

    private static RigAnimationSpec.RigTimedAnimationHook blueDemonHandEffectHook(int tick, RigAnimationId animationId, RigPart part) {
        return RigAnimationSpec.RigTimedAnimationHook.at(tick, mob -> playBlueDemonTridentEffect(mob, animationId, tick, part));
    }

    private static RigAnimationSpec.RigTimedAnimationHook blueDemonBothHandEffectHook(int tick, RigAnimationId animationId) {
        return RigAnimationSpec.RigTimedAnimationHook.at(tick, mob -> {
            playBlueDemonTridentEffect(mob, animationId, tick, RigPart.RIGHT_HAND);
            playBlueDemonTridentEffect(mob, animationId, tick, RigPart.LEFT_HAND);
        });
    }

    private static void playBlueDemonTridentEffect(Mob mob, RigAnimationId animationId, int tick, RigPart part) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        if (!(mob.getMainHandItem().getItem() instanceof BlueDemonTridentItem)) return;

        double forwardOffset = serverLevel.random.nextFloat() * 2.0F - 1.0F;
        Vec3 effectPos = RigPoseUtil.getPartPosition(mob, animationId, tick, part, Vec3.ZERO, forwardOffset, 0.0D);
        if (effectPos == null) return;

        BlueDemonUtil.spawnBlueDemonEffect(serverLevel, mob, effectPos, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        float volume = (float)Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
        float pitch = (float)Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);
        serverLevel.playSound(null, BlockPos.containing(effectPos), AnnoyingVillagersModSounds.ELECTRIFY.get(), SoundSource.NEUTRAL, volume, pitch);
    }

    private static RigAnimationSpec.RigTimedAnimationHook blueDemonSpinHook(int tick) {
        return RigAnimationSpec.RigTimedAnimationHook.at(tick, mob -> {
            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
            serverLevel.playSound(null, mob.blockPosition(), SoundEvents.TRIDENT_RETURN, SoundSource.NEUTRAL, 0.5F, 1.0F + serverLevel.random.nextFloat() * 0.2F);
        });
    }

    private static RigAnimationSpec.RigTimedAnimationHook blueDemonSoundHook(int tick, SoundEvent soundEvent, float volume, float pitch) {
        return RigAnimationSpec.RigTimedAnimationHook.at(tick, mob -> {
            if (mob.level() instanceof ServerLevel serverLevel) serverLevel.playSound(null, mob.blockPosition(), soundEvent, SoundSource.NEUTRAL, volume, pitch);
        });
    }

    private static RigAnimationSpec.RigTimedAnimationHook blueDemonThrowTridentHook(int tick, RigAnimationId animationId, InteractionHand hand, TridentMode mode) {
        return RigAnimationSpec.RigTimedAnimationHook.at(tick, mob -> {
            if (!(mob.level() instanceof ServerLevel serverLevel)) return;

            ItemStack stack = mob.getItemInHand(hand);
            if (!(stack.getItem() instanceof BlueDemonTridentItem)) return;

            Vec3 spawnPos = hand == InteractionHand.MAIN_HAND
                    ? RigPoseUtil.getRightHandPosition(mob, animationId, tick)
                    : RigPoseUtil.getLeftHandPosition(mob, animationId, tick);
            if (spawnPos == null) return;

            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(mob, spawnPos);
            if (direction == null || direction.lengthSqr() < 1.0E-7D) return;

            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, mob, stack.copy());
            trident.assignSpawnSequence(mob);
            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
            trident.setMode(mode);
            trident.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180.0D / Math.PI)));
            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180.0D / Math.PI)));
            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
            trident.shoot(direction.x, direction.y, direction.z, 2.5F, 1.0F);
            serverLevel.addFreshEntity(trident);
        });
    }

    private static void spawnBlueDemonTridentLightningAtRightTool(Mob mob, RigAnimationId animationId, int tick) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        Vec3 tridentTip = RigPoseUtil.getRightWeaponPosition(mob, animationId, tick, 1.2D);
        if (tridentTip == null) return;

        BlockPos.MutableBlockPos checkPos = BlockPos.containing(tridentTip).mutable();
        while (checkPos.getY() > serverLevel.getMinBuildHeight() && !serverLevel.getBlockState(checkPos).isSolidRender(serverLevel, checkPos)) checkPos.move(0, -1, 0);
        if (!serverLevel.getBlockState(checkPos).isSolidRender(serverLevel, checkPos)) return;

        TridentLightningBolt tridentLightningBolt = new TridentLightningBolt(AnnoyingVillagersModEntities.TRIDENT_LIGHTNING_BOLT.get(), serverLevel);
        tridentLightningBolt.setOwner(mob);
        tridentLightningBolt.moveTo(checkPos.getX() + 0.5D, checkPos.getY() + 1.0D, checkPos.getZ() + 0.5D);
        serverLevel.addFreshEntity(tridentLightningBolt);
    }

    private static void blueDemonGroundFracture(Mob mob, double forwardOffset, double yOffset, double radius) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        Vec3 forward = Vec3.directionFromRotation(0.0F, mob.yBodyRot).scale(forwardOffset);
        Vec3 weaponEdge = mob.position().add(forward.x, forward.y + yOffset, forward.z);
        BlockHitResult hitResult = serverLevel.clip(new ClipContext(mob.position().add(0.0D, 0.1D, 0.0D), weaponEdge, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob));
        Vec3 slamStartPos;

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Direction direction = hitResult.getDirection();
            BlockPos collidePos = hitResult.getBlockPos().offset(direction.getStepX(), direction.getStepY(), direction.getStepZ());
            if (!CommonUtil.canTransferShockWave(serverLevel, collidePos, serverLevel.getBlockState(collidePos))) collidePos = collidePos.below();
            slamStartPos = new Vec3(collidePos.getX(), collidePos.getY(), collidePos.getZ());
        } else {
            slamStartPos = weaponEdge.subtract(0.0D, 1.0D, 0.0D);
        }

        CommonUtil.circleSlamFracture(mob, serverLevel, slamStartPos, radius);
    }

    private static List<RigAnimationSpec.RigTimedAnimationHook> emptyHooks(int... ticks) {
        List<RigAnimationSpec.RigTimedAnimationHook> hooks = new ArrayList<>(ticks.length);
        for (int tick : ticks) hooks.add(RigAnimationSpec.RigTimedAnimationHook.at(tick, mob -> {}));
        return List.copyOf(hooks);
    }

    private static void put(RigAnimationSpec spec) {
        if (SPECS.put(spec.animationId(), spec) != null) throw new IllegalStateException("Duplicate rig animation spec for " + spec.animationId());
    }
}
