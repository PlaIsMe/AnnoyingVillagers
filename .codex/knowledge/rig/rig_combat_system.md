# Rig Combat System

## Common ids and metadata

Common server-safe rig combat metadata lives in `src/main/java/com/pla/annoyingvillagers/rig`.

`RigAnimationId` defines shared ids for sword attacks, rolling, and side-step animations. Current attack ids are:
- `SWORD_AUTO1`, `SWORD_AUTO2`, `SWORD_AUT3`
- `SWORD_DASH`
- `SWORD_AIRSLASH`
- `SWEEPING_EDGE`
- `SWORD_DUAL_AUTO1`, `SWORD_DUAL_AUTO2`, `SWORD_DUAL_AUTO3`
- `SWORD_DUAL_DASH`
- `SWORD_DUAL_AIRSLASH`
- `DANCING_EDGE`

`RigAnimationId.isAttack()` is true for those ids. Damage is only allowed for attack ids.

`RigAnimationId.isUltimateAttack()` is true for `SWEEPING_EDGE` and `DANCING_EDGE`.
`RigAnimationId.isRollAnimation()` is true for `ROLL_FORWARD` and `ROLL_BACKWARD`.
`RigAnimationId.isStepAnimation()` is true for `STEP_FORWARD`, `STEP_BACKWARD`, `STEP_LEFT`, and `STEP_RIGHT`.

`RigAnimationSpec` defines logic metadata:
- `durationTicks`
- `attackWindows`
- `attackReachBlocks`
- `movementType`
- `lungeDistanceBlocks`
- `jumpStrength`
- `damagesTarget`

`attackWindows` is an array of `RigAttackWindow(startTickInclusive, endTickExclusive)` values. A window represents the active sword-swing state for that phase. Simple attacks have one window. Multi-phase attacks define multiple windows.

Factory methods enforce intended construction:
- `normalAttack(id, durationTicks, attackStartTickInclusive, attackEndTickExclusive)`
- `dashAttack(id, durationTicks, attackStartTickInclusive, attackEndTickExclusive, lungeDistanceBlocks)`
- `jumpAttack(id, durationTicks, attackStartTickInclusive, attackEndTickExclusive, jumpStrength)`
- `jumpTowardAttack(id, durationTicks, attackStartTickInclusive, attackEndTickExclusive, jumpStrength, lungeDistanceBlocks)`
- `ultimateAttack(id, durationTicks, attackStartTickInclusive, attackEndTickExclusive, movementType, lungeDistanceBlocks, jumpStrength)`
- `ultimateAttack(id, durationTicks, movementType, lungeDistanceBlocks, jumpStrength, attackWindows...)`
- `rolling(id, durationTicks, movementType, rollDistanceBlocks)`

`RigAnimationSpecs` is the central spec registry. It assigns server duration, hit windows, reach, movement type, movement distance, and jump strength for each id. Current timings:
- `SWORD_AUTO1`: duration `12`, windows `[0 -> 2]`
- `SWORD_AUTO2`: duration `12`, windows `[1 -> 3]`
- `SWORD_AUTO3`: duration `12`, windows `[1 -> 3]`
- `SWORD_AUTO4`: duration `13`, windows `[1 -> 3]`
- `SWORD_DASH`: duration `13`, windows `[3 -> 5]`, lunge `1.48`
- `SWORD_AIRSLASH`: duration `13`, windows `[3 -> 6]`, jump `0.42`
- `SWEEPING_EDGE`: duration `20`, windows `[3 -> 6]`, lunge `1.11`
- `SWORD_DUAL_AUTO1`: duration `12`, windows `[2 -> 4]`
- `SWORD_DUAL_AUTO2`: duration `12`, windows `[2 -> 4]`
- `SWORD_DUAL_AUTO3`: duration `15`, windows `[5 -> 7]`
- `SWORD_DUAL_DASH`: duration `15`, windows `[1 -> 6]`, lunge `4.11`
- `SWORD_DUAL_AIRSLASH`: duration `13`, windows `[3 -> 6]`, jump `0.42`
- `DANCING_EDGE`: duration `25`, windows `[5 -> 8]`, `[8 -> 10]`, `[12 -> 14]`, lunge `1.83`
- `ROLL_FORWARD`: duration `13`, distance `3.52`
- `ROLL_BACKWARD`: duration `13`, distance `3.50`
- `STEP_FORWARD`: duration `7`, distance `1.34`
- `STEP_BACKWARD`: duration `7`, distance `1.34`
- `STEP_LEFT`: duration `7`, distance `1.52`
- `STEP_RIGHT`: duration `7`, distance `1.44`

Attack windows correspond to the active swing state. Before a window is anticipation/preparation. After a window is recovery. Among current shared ids, only `DANCING_EDGE` is multi-phase and has three separate windows. `SWORD_DUAL_AUTO3` and `SWORD_DUAL_DASH` use both weapon colliders in one phase, so each remains a single server damage window.

## Server-side playback API

`RigAnimationController.play(Mob mob, RigAnimationSpec spec, LivingEntity target)` is the common API for `AVNpc`, `HerobrineMob`, and other rig-capable mobs.

It runs only server-side. It:
1. faces the target when present
2. swings the main hand only for attack animations
3. marks the mob aggressive
4. sends `ClientboundRigAnimation` to tracking clients and self
5. schedules rig sound events
6. applies movement logic on the server
7. schedules one damage window task group for each value in `attackWindows` for damaging attack specs

Movement behavior:
- `LUNGE`: moves forward by `lungeDistanceBlocks` over short scheduled steps
- `JUMP`: applies vertical velocity from `jumpStrength`
- `JUMP_LUNGE`: jumps first, then lunges after a short delay
- `ROLL_FORWARD`, `ROLL_BACKWARD`, `ROLL_RIGHT`, `ROLL_LEFT`: move by the rolling or step spec distance without damaging

Hit timing uses `DelayedTask`. During every attack window tick, the target must still be alive, attackable, non-allied, and inside reach. Each window can hit once; if the target enters reach during the window, the hit can still connect. Multi-window attacks temporarily clear the target hurt cooldown for each window so each phase can deal damage.

Sound timing also uses `DelayedTask` from the server. The controller calls `level.playSound(null, x, y, z, sound, SoundSource.HOSTILE, volume, pitch)`, so no player is excluded and nearby players hear the sound through normal Minecraft sound broadcasting.

Rig sound rules:
- normal, dash, jump, and jump-toward attacks play `AnnoyingVillagersModSounds.SWORD_WHOOSH` when each attack window starts
- ultimate attacks play `AnnoyingVillagersModSounds.WHOOSH_SHARP` when each attack window starts
- successful rig damage plays `AnnoyingVillagersModSounds.BLADE_HIT` at the target position
- `ROLL_FORWARD` and `ROLL_BACKWARD` play `AnnoyingVillagersModSounds.ROLL` at animation start
- step animations play the current block-under-feet hit sound at animation start instead of using a custom step asset

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
