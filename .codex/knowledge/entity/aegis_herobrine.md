# Aegis Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/AegisHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/item/EnderAegisItem.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigCombatProfiles.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigShieldGuardController.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/EliteHerobrineSecondFormGoal.java`

## Current Non-EpicFight Role

`AegisHerobrineEntity` extends `HerobrineMob`, equips `ENDER_AEGIS`, and uses the vanilla articulated rig combat system. Do not describe its live implementation as an Epic Fight `MobPatch`/`StaticAnimation` path.

`EnderAegisItem` extends `ShieldItem`, implements `RigCombatProfileProvider`, and resolves `RigCombatStyle.AEGIS_HEROBRINE`. `RigShieldGuardController` recognizes Aegis's authored main-hand guard so the custom `AEGIS_HEROBRINE_GUARD` animation is preferred over the generic shield pose.

## Rig Combat Profile

The Aegis profile contains authored attacks 1-5, dash/jump specials, normal roll/step movement, and `AEGIS_HEROBRINE_EXTRA_ATTACK`. Second-form ULT playback is owned by `EliteHerobrineSecondFormGoal`, not the ordinary profile attack selector.

Important spec hooks:

- `AEGIS_HEROBRINE_ATTACK5`: sword damage window followed by a large ground-slam hook; successful hits launch the victim upward.
- `AEGIS_HEROBRINE_JUMP_ATTACK`: jump attack with a ground-slam hook.
- `AEGIS_HEROBRINE_ULT`: non-damaging, dangerous, invulnerable ULT. Its tick-2 hook calls `AegisHerobrineEntity.fireSecondFormShieldShot()`.

## Second-Form Action Budget

Aegis registers `EliteHerobrineSecondFormGoal` for `AEGIS_HEROBRINE_ULT` while holding `EnderAegisItem`.

`HerobrineMob` owns the shared second-form state/budget:

- state 0: no active window; a goal may open one after the shared cooldown;
- state 1: limited action count (`secondFormHitLeft`);
- state 2: fully transformed and actions are unlimited.

`fireSecondFormShieldShot()` calls `EnderAegisItem.shieldShoot(...)` and then consumes one action only when the common state permits it. Successful custom Aegis guard logic may use the same helper, so do not duplicate a separate Aegis-only budget.

`AegisHerobrineEntity.tick()` keeps the Ender Aegis `SecondForm` item tag synchronized with `state > 0` for rendering/model predicates.

## Death / Portal Behavior

On death Aegis creates `EliteHerobrineKnockedEntity` with the Ender Aegis identity and can notify Greg through the existing protection flow. Aegis does not inherit a common `PortalApproachGoal`; Greg/Transporter support portals are placed into Aegis's existing combat movement when Aegis is a valid supported ally.
