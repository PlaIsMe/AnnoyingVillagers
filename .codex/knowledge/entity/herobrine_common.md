# Herobrine Common Combat Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrineMob.java`
- `src/main/java/com/pla/annoyingvillagers/util/CommonGoals.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/EliteHerobrineSecondFormGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrinePortalDangerousReactionGoal.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationController.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigCombatProfiles.java`

## Shared HerobrineMob Flow

`HerobrineMob` is the normal shared base for the current Herobrine combat family. Its `registerGoals()` owns retargeting, dangerous reaction, Herobrine healing, optional `RollItemGoal`, protect/healing follow behavior, common hostile combat, and wandering.

`CommonGoals.registerGoalForHostileNpc(...)` selects native Rig melee through `RigAnimatedMeleeAttackGoal` when `supportsRigCombat(...)` is true. Normal `HerobrineMob` subclasses are included automatically. The two low-clone classes are also explicitly included even though they do not extend `HerobrineMob`.

`TransporterHerobrineCloneEntity` is intentionally excluded from the common shield/melee registration because it is a support unit with `ATTACK_DAMAGE = 0.0D` and its own support-position/portal goals.

The old common `PortalApproachGoal` registration is gone. Do not add it back to ordinary Herobrines or low clones. Greg/Transporter now place portal entrances into the supported ally's existing movement path.

## Rig Combat Profile Selection

`RigCombatProfiles.getCombatProfile(mob)` resolves the current held equipment in this order:

- main-hand `RigCombatProfileProvider` -> item-defined custom style;
- ordinary sword + ordinary sword offhand -> `DUAL_BASIC`;
- ordinary sword -> `BASIC`;
- ordinary axe -> `AXE`;
- otherwise / empty main hand -> `UNARMED`.

This resolution is why clone/low-clone combat should not hardcode attack lists inside their entity classes.

## Dangerous Reaction

Normal `HerobrineMob` dangerous-reaction animation is `STEP_BACKWARD`.

`CommonGoals.registerDangerousReactionGoals(...)` normally installs `DangerousReactionGoal` at priority `-7`, followed by `KeepPositionGoal` at `-6`.

If the mob also implements `HerobrinePortalSupportCaster` (currently Greg/Transporter), `CommonGoals` substitutes `HerobrinePortalDangerousReactionGoal`. That goal optionally creates a self escape portal using the shared portal cooldown and then performs the same committed dangerous reaction. If portal use is unavailable it falls back to the normal reaction immediately.

## Current Common Second-Form State

The old `combatbehaviour/HerobrineCommon.java` and Epic Fight behavior builders are reference/compatibility material, not the live normal AV architecture.

`HerobrineMob` owns the shared second-form state/budget:

- state 0: normal state; a second-form window may open when the shared cooldown permits;
- state 1: limited second-form window, normally 2-3 actions;
- state 2: fully transformed, with unlimited second-form actions.

`canStartSecondFormAction()` is the goal-level gate. `canUseSecondFormAction()` is the effect/hook-level gate. `consumeSecondFormAction()` decrements only state 1. When its budget reaches zero, the mob returns to state 0 and rolls the shared second-form cooldown. State 2 is not consumed.

## EliteHerobrineSecondFormGoal

`EliteHerobrineSecondFormGoal` is the current native launcher for elite Herobrine second-form actions. It may be configured with one animation, two random animations, or a selector plus an entity-specific predicate.

It checks the shared second-form state, live target, no Rig stun, no active Rig animation, and entity-specific conditions. Gameplay effects should live in `RigAnimationSpecs` timed/on-hit hooks rather than being copied from old Epic Fight behavior containers.

Aegis, Glaive, Reaper, Sledgehammer, and Swordsman use this shared flow; entity-specific details are documented in their own knowledge files.

## Profile-Attack Locks

`RigAnimationController.lockProfileAttacksFor(...)` uses `LockableRigAttackAnimation` lock-count semantics. It blocks profile attacks recognized by `RigCombatProfiles`; it is not a universal animation lock.

Special hand actions that must not overlap combat should explicitly check active Rig/stun state and acquire their intended lock rather than depending on obsolete Epic Fight state flags.

## Portal Support Separation

Portal support is separate from normal Herobrine movement/combat selection:

- `HerobrinePortalSupportCaster` describes Greg/Transporter support capability;
- dedicated portal goals decide when to cast;
- all live support geometry, ownership, low-clone, six-portal, projectile, and SnakeBlade portal helpers are consolidated in `HerobrineUtil`;
- `HerobrineSupportPortalUtil.java` and `HerobrinePortalCombatUtil.java` are fully commented legacy snapshots only.

Supported Herobrines do not receive a goal to pathfind into support portals.

For Swordsman specifically, a ready six-portal group from the linked Greg causes `EliteHerobrineSecondFormGoal` to use 100% chance and force the normal `SWORDSMAN_HEROBRINE_ULT` when the goal cooldown/action budget is ready; the extra ULT is not chosen for that prepared six-portal follow-up.

## Removed EFN Guard-Hit State

The non-EpicFight `HerobrineMob` does not use the old `efnGuardHitState` / `efnGuardHitCooldown` cycle. Current guard/stun behavior belongs to the Rig controllers.
