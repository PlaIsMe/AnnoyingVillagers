package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.DangerousReaction;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.VillagerArmyEntity;
import com.pla.annoyingvillagers.compat.SmartNpc;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.entity.goal.DangerousReactionGoal;
import com.pla.annoyingvillagers.entity.goal.HerobrinePortalDangerousReactionGoal;
import com.pla.annoyingvillagers.entity.goal.KeepPositionGoal;
import com.pla.annoyingvillagers.entity.goal.RigAnimatedMeleeAttackGoal;
import com.pla.annoyingvillagers.entity.goal.RigShieldGuardGoal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;

public class CommonGoals {
    private static boolean hasCombatTarget(Mob mob) {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive();
    }

    private static Goal createMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        if (supportsRigCombat(mob)) {
            return new RigAnimatedMeleeAttackGoal(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        return new MeleeAttackGoal(mob, speedModifier, followingTargetEvenIfNotSeen);
    }

    private static void addRigShieldGuardGoal(PathfinderMob mob, int priority) {
        if (supportsRigCombat(mob)) {
            mob.goalSelector.addGoal(priority, new RigShieldGuardGoal(mob));
        }
    }

    private static boolean supportsRigCombat(Mob mob) {
        return mob instanceof AVNpc || mob instanceof HerobrineMob || mob instanceof BlueDemonEntity
                || mob instanceof LowHerobrineCloneEntity || mob instanceof LowShadowHerobrineCloneEntity;
    }

    private static Goal createRandomStrollGoal(PathfinderMob mob, double speedModifier) {
        return new RandomStrollGoal(mob, speedModifier) {
            @Override
            public boolean canUse() {
                return (!(mob instanceof DangerousReaction) || !DangerousReaction.hasDangerousTarget(mob)) && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return (!(mob instanceof DangerousReaction) || !DangerousReaction.hasDangerousTarget(mob)) && super.canContinueToUse();
            }

            @Override
            public void stop() {
                super.stop();
                if (mob instanceof DangerousReaction && DangerousReaction.hasDangerousTarget(mob)) mob.getNavigation().stop();
            }
        };
    }

    public static void registerDangerousReactionGoals(Mob mob) {
        if (!(mob instanceof DangerousReaction dangerousReaction)) return;
        if (mob instanceof HerobrinePortalSupportCaster supportCaster) {
            mob.goalSelector.addGoal(-7, new HerobrinePortalDangerousReactionGoal(mob, dangerousReaction, supportCaster));
        } else {
            mob.goalSelector.addGoal(-7, new DangerousReactionGoal(mob, dangerousReaction));
        }
        mob.goalSelector.addGoal(-6, new KeepPositionGoal(mob));
    }

    public static void registerGoalForHostileNpc(PathfinderMob monster) {
        monster.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        monster.targetSelector.addGoal(1, new HurtByTargetGoal(monster));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, Player.class, true, false));
        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(monster, 1);
        }
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, SteveEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AngrySteveEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, ChrisEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AlexEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, JevEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, Villager.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, IronGolem.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, BlueDemonEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, VillagerScoutEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, VillagerScoutCaptainEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, RedVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, BlueVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, GreenVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, PurpleVillagerKnightEntity.class, true, false));
        if (!(monster instanceof TransporterHerobrineCloneEntity)) {
            addRigShieldGuardGoal(monster, 1);
            monster.goalSelector.addGoal(2, createMeleeAttackGoal(monster, 1.2D, false));
        }
        monster.goalSelector.addGoal(3, createRandomStrollGoal(monster, 1.0D));
        monster.goalSelector.addGoal(4, new RandomLookAroundGoal(monster));
        monster.goalSelector.addGoal(5, new FloatGoal(monster));
    }

    public static void registerGoalForBlueDemonNpc(Monster monster) {
        monster.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        monster.targetSelector.addGoal(1, new HurtByTargetGoal(monster));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, HerobrineMob.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, LowHerobrineCloneEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, LowShadowHerobrineCloneEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, EliteHerobrineKnockedEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, Player.class, true, false));
        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(monster, 2);
        }
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, AngrySteveEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, Villager.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, VillagerScoutEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, VillagerScoutCaptainEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, RedVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, BlueVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, GreenVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, PurpleVillagerKnightEntity.class, true, false));
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, AbstractIllager.class, true, false));
        addRigShieldGuardGoal(monster, 2);
        monster.goalSelector.addGoal(3, createMeleeAttackGoal(monster, 1.2D, false));
        monster.goalSelector.addGoal(4, createRandomStrollGoal(monster, 1.0D));
        monster.goalSelector.addGoal(5, new RandomLookAroundGoal(monster));
        monster.goalSelector.addGoal(6, new FloatGoal(monster));
    }

    public static void registerGoalForVillagerKnightNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        if (!(mob instanceof VillagerArmyEntity)) {
            mob.targetSelector.addGoal(1, (new HurtByTargetGoal(mob)).setAlertOthers());
        }

        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineMob.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));

        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(mob, 2);
        }
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, Player.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, Monster.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, AbstractIllager.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, BlueDemonEntity.class, true, false));

        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, SteveEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, AngrySteveEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, AlexEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, ChrisEntity.class, true, false));
        addRigShieldGuardGoal(mob, 0);
        mob.goalSelector.addGoal(5, createMeleeAttackGoal(mob, 1.2D, false));
        mob.goalSelector.addGoal(6, new RandomStrollGoal(mob, 1.0D) {
            @Override
            public boolean canUse() {
                return !hasCombatTarget(mob) && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !hasCombatTarget(mob) && super.canContinueToUse();
            }

            @Override
            public void stop() {
                super.stop();
                if (hasCombatTarget(mob)) {
                    mob.getNavigation().stop();
                }
            }
        });
        mob.goalSelector.addGoal(7, new FollowMobGoal(mob, 1.3D, 20.0F, 15.0F) {
            @Override
            public boolean canUse() {
                return !hasCombatTarget(mob) && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !hasCombatTarget(mob) && super.canContinueToUse();
            }

            @Override
            public void stop() {
                super.stop();
                if (hasCombatTarget(mob)) {
                    mob.getNavigation().stop();
                }
            }
        });
        mob.goalSelector.addGoal(8, new OpenDoorGoal(mob, true));
        mob.goalSelector.addGoal(9, new OpenDoorGoal(mob, false));
        mob.goalSelector.addGoal(10, new RandomLookAroundGoal(mob) {
            @Override
            public boolean canUse() {
                return !hasCombatTarget(mob) && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !hasCombatTarget(mob) && super.canContinueToUse();
            }
        });
        mob.goalSelector.addGoal(11, new FloatGoal(mob));
    }

    public static void registerGoalForNeutralNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);

        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineMob.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, EliteHerobrineKnockedEntity.class, true, false));
        mob.targetSelector.addGoal(2, new HurtByTargetGoal(mob));
        mob.goalSelector.addGoal(2, createMeleeAttackGoal(mob, 1.2D, false));

        if (!(mob.getTarget() instanceof VillagerScoutEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, VillagerScoutEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof VillagerScoutCaptainEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, VillagerScoutCaptainEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof BlueVillagerKnightEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, BlueVillagerKnightEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof GreenVillagerKnightEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, GreenVillagerKnightEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof RedVillagerKnightEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, RedVillagerKnightEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(mob.getTarget() instanceof PurpleVillagerKnightEntity)) {
            mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, PurpleVillagerKnightEntity.class, 12.0F, 1.2D, 1.4D));
        }
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, Monster.class, false, (target) -> !(target instanceof FakePlayer || target instanceof BlueDemonEntity)));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, AbstractIllager.class, true, false));
        addRigShieldGuardGoal(mob, 0);
        mob.goalSelector.addGoal(3, createMeleeAttackGoal(mob, 1.2D, false));
        mob.goalSelector.addGoal(4, createRandomStrollGoal(mob, 1.0D));
        mob.goalSelector.addGoal(5, new OpenDoorGoal(mob, true));
        mob.targetSelector.addGoal(6, new HurtByTargetGoal(mob));
        mob.goalSelector.addGoal(7, new OpenDoorGoal(mob, false));
        mob.goalSelector.addGoal(8, new RandomLookAroundGoal(mob));
        mob.goalSelector.addGoal(9, new FloatGoal(mob));
    }

    public static void registerGoalForCrazyNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, Monster.class, false, false));
        mob.targetSelector.addGoal(1, new HurtByTargetGoal(mob));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineMob.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));
        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(mob, 2);
        }
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, VillagerScoutEntity.class, false, false));
        mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(mob, VillagerScoutCaptainEntity.class, false, false));
        mob.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(mob, RedVillagerKnightEntity.class, false, false));
        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(mob, BlueVillagerKnightEntity.class, false, false));
        mob.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(mob, GreenVillagerKnightEntity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, PurpleVillagerKnightEntity.class, false, false));
        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(mob, BlueDemonEntity.class, false, false));
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, EliteHerobrineKnockedEntity.class, true, false));
        mob.targetSelector.addGoal(20, new NearestAttackableTargetGoal<>(mob, AlexEntity.class, false, false));
        mob.targetSelector.addGoal(20, new NearestAttackableTargetGoal<>(mob, ChrisEntity.class, false, false));
        mob.targetSelector.addGoal(21, new NearestAttackableTargetGoal<>(mob, Player.class, true, true));
        addRigShieldGuardGoal(mob, 0);
        mob.goalSelector.addGoal(22, createMeleeAttackGoal(mob, 1.2D, false));
        mob.goalSelector.addGoal(23, createRandomStrollGoal(mob, 1.0D));
        mob.goalSelector.addGoal(24, new RandomLookAroundGoal(mob));
        mob.goalSelector.addGoal(25, new FloatGoal(mob));
    }

}
