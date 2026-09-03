# Herobrine Common Combat Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrineMob.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/EliteHerobrineSecondFormGoal.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationController.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigCombatProfiles.java`

## Current Common Second-Form State

The old `combatbehaviour/HerobrineCommon.java` and weapon-specific Epic Fight combat-behavior builders are not part of the current normal AV source tree. Do not use their `playSecondFormAnimation` helpers as the live architecture.

`HerobrineMob` now owns the shared second-form state/budget:

- state 0: normal state. A second-form window may open only when cooldown is zero and no action budget remains;
- state 1: limited second-form window with `secondFormHitLeft` initialized to 2-3 actions;
- state 2: fully transformed; second-form actions are unlimited.

`canStartSecondFormAction()` is the goal-level gate. `canUseSecondFormAction()` is the hook/effect-level gate. `consumeSecondFormAction()` decrements only state 1; once the count reaches zero it returns the mob to state 0 and rolls the shared 600-1200 tick cooldown. State 2 is never decremented.

## EliteHerobrineSecondFormGoal

`EliteHerobrineSecondFormGoal` is the common vanilla-AI launcher. It can be configured with one animation, two random animations, or a dynamic selector plus an entity-specific predicate.

The goal checks the shared state gate, a live target, no active rig animation, no rig stun, and its entity-specific condition. Gameplay effects belong in `RigAnimationSpecs` timed/on-hit hooks rather than in a copied Epic Fight skill container.

Current elite integrations include Aegis, Glaive, Reaper, Sledgehammer, and Swordsman. Their exact animation/effect mappings are documented in their entity knowledge files.

## Profile-Attack Locks

`RigAnimationController.lockProfileAttacksFor(...)` uses `LockableRigAttackAnimation` lock-count semantics. It blocks only attacks recognized by `RigCombatProfiles.isProfileAttack(...)`; it is not a global animation lock.

Hand actions that must not overlap combat should first refuse to start while an active rig animation/profile attack is present, then acquire their own profile-attack lock. Reaper dragon summon follows this pattern after successfully starting `REAPER_HEROBRINE_ULT`.

## Portal Additions

Portal movement/support remains separate from second-form ownership:

- portal approach is provided by common goals;
- `HerobrinePortalCombatGoal` and `HerobrinePortalCombatUtil` choose support actions/routes;
- item/projectile helpers may consume a preferred portal target.

Do not reintroduce removed Epic Fight common combat-behavior classes merely to trigger a rig second-form animation.

## Removed EFN Guard-Hit State

The non-EpicFight `HerobrineMob` does not use the old `efnGuardHitState` / `efnGuardHitCooldown` cycle. Rig guard/stun state belongs to the current rig controllers.
