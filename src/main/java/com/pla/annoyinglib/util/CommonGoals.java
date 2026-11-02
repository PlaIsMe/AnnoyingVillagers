package com.pla.annoyinglib.util;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import com.pla.annoyinglib.compat.MobSupremacy;
import com.pla.annoyinglib.compat.VillagerKingdom;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.clazz.DangerousReaction;
import com.pla.annoyinglib.clazz.AVNpc;
import com.pla.annoyinglib.compat.SmartNpc;
import com.pla.annoyinglib.entity.goal.DangerousReactionGoal;
import com.pla.annoyinglib.entity.goal.KeepPositionGoal;
import com.pla.annoyinglib.entity.goal.RigAnimatedMeleeAttackGoal;
import com.pla.annoyinglib.entity.goal.RigShieldGuardGoal;
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

    public static Goal createMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        if (supportsRigCombat(mob)) {
            return new RigAnimatedMeleeAttackGoal(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        return new MeleeAttackGoal(mob, speedModifier, followingTargetEvenIfNotSeen);
    }

    public static void addRigShieldGuardGoal(PathfinderMob mob, int priority) {
        if (supportsRigCombat(mob)) {
            mob.goalSelector.addGoal(priority, new RigShieldGuardGoal(mob));
        }
    }

    private static boolean supportsRigCombat(Mob mob) {
        return mob instanceof AVNpc || (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonEntity(mob)) || (ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.supportsRigCombat(mob));

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
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.addDangerousReactionGoal(mob, dangerousReaction);
        } else {
            mob.goalSelector.addGoal(-7, new DangerousReactionGoal(mob, dangerousReaction));
        }
        mob.goalSelector.addGoal(-6, new KeepPositionGoal(mob));
    }

    public static void registerTargetGoalForVanillaMonster(Monster monster) {
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, LivingEntity.class, true, target ->
                (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isSteveEntity(target)) ||
                        (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isAngrySteveEntity(target)) ||
                        (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isChrisEntity(target)) ||
                        (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isAlexEntity(target)) ||
                        (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isJevEntity(target)) ||
                        (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonEntity(target)) ||
                        (ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isVillagerArmyEntity(target))
        ));

        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(monster, 2);
        }
    }

    public static void registerGoalForHostileNpc(PathfinderMob monster) {
        monster.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        monster.targetSelector.addGoal(1, new HurtByTargetGoal(monster));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, Player.class, true, false));
        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(monster, 1);
        }
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.registerGoalForHostileNpcTargets(monster);
        
        
        
        
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, Villager.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, IronGolem.class, true, false));
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.registerGoalForHostileNpcTargets(monster);
        if (ModList.get().isLoaded("villager_kingdom")) VillagerKingdom.registerGoalForHostileNpcTargets(monster);
        
        
        
        
        
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.addHostileMeleeGoals(monster);
        } else {
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
    if (ModList.get().isLoaded("herobrine_vessels")) {
        HerobrineVessels.addBlueDemonTargets(monster);
    }
        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(monster, 2);
        }
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.registerGoalForBlueDemonNpcTargets(monster);
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, Villager.class, true, false));
        if (ModList.get().isLoaded("villager_kingdom")) VillagerKingdom.registerGoalForBlueDemonNpcTargets(monster);
        
        
        
        
        
        monster.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(monster, AbstractIllager.class, true, false));
        addRigShieldGuardGoal(monster, 2);
        monster.goalSelector.addGoal(3, createMeleeAttackGoal(monster, 1.2D, false));
        monster.goalSelector.addGoal(4, createRandomStrollGoal(monster, 1.0D));
        monster.goalSelector.addGoal(5, new RandomLookAroundGoal(monster));
        monster.goalSelector.addGoal(6, new FloatGoal(monster));
    }

    public static void registerGoalForVillagerKnightNpc(PathfinderMob mob) {
        mob.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        if (!((ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isVillagerArmyEntity(mob)))) {
            mob.targetSelector.addGoal(1, (new HurtByTargetGoal(mob)).setAlertOthers());
        }

        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.addKnightTargets(mob);
        }

        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(mob, 2);
        }
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, Player.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, Monster.class, true, false));
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, AbstractIllager.class, true, false));
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.registerGoalForVillagerKnightNpcTargets(mob);

        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.registerGoalForVillagerKnightNpcTargets(mob);
        
        
        
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

        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.addNeutralTargets(mob);
        } else {
            mob.targetSelector.addGoal(2, new HurtByTargetGoal(mob));
            mob.goalSelector.addGoal(2, createMeleeAttackGoal(mob, 1.2D, false));
        }

        if (ModList.get().isLoaded("villager_kingdom")) VillagerKingdom.registerGoalForNeutralNpcTargets(mob);
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, Monster.class, false, (target) -> !((ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.isFakePlayer(target)) || (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonEntity(target)))));
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
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.addCrazyTargets(mob);
        }
        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.targetPlayerNpc(mob, 2);
        }
        if (ModList.get().isLoaded("villager_kingdom")) VillagerKingdom.addCrazyNpcTargets(mob);
        
        
        
        
        
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.addCrazyNpcTargets(mob);
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.addKnockedEliteTarget(mob);
        }
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.addCrazyNpcTargets(mob);
        
        mob.targetSelector.addGoal(21, new NearestAttackableTargetGoal<>(mob, Player.class, true, true));
        addRigShieldGuardGoal(mob, 0);
        mob.goalSelector.addGoal(22, createMeleeAttackGoal(mob, 1.2D, false));
        mob.goalSelector.addGoal(23, createRandomStrollGoal(mob, 1.0D));
        mob.goalSelector.addGoal(24, new RandomLookAroundGoal(mob));
        mob.goalSelector.addGoal(25, new FloatGoal(mob));
    }

}
