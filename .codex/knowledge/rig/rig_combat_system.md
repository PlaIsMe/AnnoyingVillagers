# Rig Combat System

## Common ids and metadata

Common server-safe rig combat metadata lives in `src/main/java/com/pla/annoyingvillagers/rig`.

`RigAnimationId` defines shared ids for sword attacks, rolling, and side-step animations. Current attack ids are:
- `SWORD_AUTO1`, `SWORD_AUTO2`, `SWORD_AUTO3`, `SWORD_AUTO4`
- `SWORD_DASH`
- `SWORD_AIRSLASH`
- `SWEEPING_EDGE`
- `SWORD_DUAL_AUTO1`, `SWORD_DUAL_AUTO2`, `SWORD_DUAL_AUTO3`
- `SWORD_DUAL_DASH`
- `SWORD_DUAL_AIRSLASH`
- `DANCING_EDGE`

`RigAnimationId.isAttack()` is true for those ids. Damage is only allowed for attack ids.

`RigAnimationSpec` defines logic metadata:
- `durationTicks`
- `impactDelayTicks`
- `attackReachBlocks`
- `movementType`
- `lungeDistanceBlocks`
- `jumpStrength`
- `damagesTarget`

Factory methods enforce intended construction:
- `normalAttack(id, durationTicks, impactDelayTicks)`
- `dashAttack(id, durationTicks, impactDelayTicks, lungeDistanceBlocks)`
- `jumpAttack(id, durationTicks, impactDelayTicks, jumpStrength)`
- `jumpTowardAttack(id, durationTicks, impactDelayTicks, jumpStrength, lungeDistanceBlocks)`
- `ultimateAttack(id, durationTicks, impactDelayTicks, movementType, lungeDistanceBlocks, jumpStrength)`
- `rolling(id, durationTicks, movementType, rollDistanceBlocks)`

`RigAnimationSpecs` is the central spec registry. It assigns server duration, hit delay, reach, movement type, movement distance, and jump strength for each id. Current generated clip durations are represented at 20 ticks per second:
- sword autos: `12` ticks
- sword dash and airslash: `13` ticks
- sweeping edge: `18` ticks
- dual sword autos: `13` ticks
- dual sword dash: `15` ticks
- dual sword airslash: `12` ticks
- dancing edge: `20` ticks
- rolls: `10` ticks
- steps: `8` ticks

Attack impact delays are tuned to the middle of each swing contact window, not the first wind-up frame. `DANCING_EDGE` is a multi-phase attack in concept, but the current `RigAnimationSpec` supports one impact delay, so it uses the first contact window as the temporary server hit point.

## Server-side playback API

`RigAnimationController.play(Mob mob, RigAnimationSpec spec, LivingEntity target)` is the common API for `AVNpc`, `HerobrineMob`, and other rig-capable mobs.

It runs only server-side. It:
1. faces the target when present
2. swings the main hand only for attack animations
3. marks the mob aggressive
4. sends `ClientboundRigAnimation` to tracking clients and self
5. applies movement logic on the server
6. schedules impact damage after `impactDelayTicks` for damaging attack specs

Movement behavior:
- `LUNGE`: moves forward by `lungeDistanceBlocks` over short scheduled steps
- `JUMP`: applies vertical velocity from `jumpStrength`
- `JUMP_LUNGE`: jumps first, then lunges after a short delay
- `ROLL_FORWARD`, `ROLL_BACKWARD`, `ROLL_RIGHT`, `ROLL_LEFT`: move by the rolling or step spec distance without damaging

Hit timing uses `DelayedTask`. At impact, the target must still be alive, attackable, non-allied, and inside reach.

## Weapon profiles

`RigCombatProfile` stores categorized animation sets:
- normal attack chain
- special attacks for dash/jump/jump-toward interrupts
- rolling interrupts
- ultimate interrupts
- chances for special, rolling, and ultimate interrupts

Normal attacks are deterministic and sequential. A profile with normal attacks `[1, 2, 3, 4]` plays `1 -> 2 -> 3 -> 4 -> 1` unless interrupted. Interrupts do not advance the normal combo index.

Closing attacks are selected from special attacks with movement distance. A closing attack may open distance, but it should not immediately repeat the same single closing animation; if the only available closing option was just played, the goal should keep pathing toward melee range so the normal combo can resume.

`RigCombatProfiles.getCombatProfile` returns `DUAL_SWORD` when the mob has a `SwordItem` in both main hand and off hand. `DUAL_SWORD` uses `SWORD_DUAL_AUTO1`, `SWORD_DUAL_AUTO2`, and `SWORD_DUAL_AUTO3` as the deterministic normal combo, `SWORD_DUAL_DASH` and `SWORD_DUAL_AIRSLASH` as special interrupts, roll/step animations as rolling interrupts, and `DANCING_EDGE` as the rare ultimate interrupt.

Other mobs use `DEFAULT_SWORD`. `DEFAULT_SWORD` uses the four sword auto attacks as the deterministic normal combo, `SWORD_DASH` and `SWORD_AIRSLASH` as special interrupts, roll/step animations as rolling interrupts, and `SWEEPING_EDGE` as the rare ultimate interrupt.

This class is the extension point for exact custom weapon chains. When weapon-specific profiles are reintroduced, keep normal attacks deterministic and put dash, jump, roll, and ultimate animations in their categorized interrupt lists.

## Combat goal

`RigAnimatedMeleeAttackGoal` replaces vanilla melee only for rig-capable mobs selected by `CommonGoals.supportsRigCombat`: `AVNpc` and `HerobrineMob`.

The goal ignores mobs holding bows. It moves toward the target, starts movement attacks from farther range, and starts normal/interruption attacks from melee range. While an active rig animation is playing, it stops path navigation so scheduled movement and hit timing drive the action.

`CommonGoals.createMeleeAttackGoal` returns:
- `RigAnimatedMeleeAttackGoal` for `AVNpc` and `HerobrineMob`
- vanilla `MeleeAttackGoal` for other mobs, including vanilla mobs that receive shared goals through mixins

## Client rendering

Server attack selection never imports client `AnimationDefinition`. It sends only `RigAnimationId` and duration. The client model resolves the id through `RigAnimationResolver` and applies the active one-shot animation before locomotion.
