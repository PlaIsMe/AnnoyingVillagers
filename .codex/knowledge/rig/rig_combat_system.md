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

`RigAnimationId.isUltimateAttack()` is true only for `DANCING_EDGE`. `SWEEPING_EDGE` is a normal attack even though it used to be treated as an ultimate.
`RigAnimationId.isRollAnimation()` is true for `ROLL_FORWARD` and `ROLL_BACKWARD`.
`RigAnimationId.isStepAnimation()` is true for `STEP_FORWARD`, `STEP_BACKWARD`, `STEP_LEFT`, and `STEP_RIGHT`.

`RigAnimationSpec` defines logic metadata:
- `durationTicks`
- `attackWindows`
- `attackReachBlocks`
- `damagesTarget`

`RigAnimationSpec` no longer stores movement metadata. Do not reintroduce `RigMovementType`, `lungeDistanceBlocks`, or `jumpStrength`; animation movement comes from `RigRootMotion`.

`attackWindows` is an array of `RigAttackWindow(startTickInclusive, endTickExclusive)` values. A window represents the active sword-swing state for that phase. Simple attacks have one window. Multi-phase attacks define multiple windows.

Factory methods enforce intended construction:
- `normalAttack(id, durationTicks, attackStartTickInclusive, attackEndTickExclusive)`
- `normalAttack(id, durationTicks, attackStartTickInclusive, attackEndTickExclusive, attackReachBlocks)`
- `ultimateAttack(id, durationTicks, attackWindows...)`
- `rolling(id, durationTicks)`
- `nonDamaging(id, durationTicks)`

`dashAttack`, `jumpAttack`, `jumpTowardAttack`, and `movementOnly` were removed. Dash attacks and single-window former ultimate attacks are normal attacks. Use `ultimateAttack` only when an animation needs multiple `RigAttackWindow` entries; currently this means `DANCING_EDGE`.

`RigAnimationSpecs` is the central spec registry. It assigns server duration, hit windows, and reach for each id. Current timings:
- `SWORD_AUTO1`: duration `12`, windows `[0 -> 2]`
- `SWORD_AUTO2`: duration `12`, windows `[1 -> 3]`
- `SWORD_AUTO3`: duration `12`, windows `[1 -> 3]`
- `SWORD_AUTO4`: duration `12`, windows `[1 -> 3]`
- `SWORD_DASH`: duration `13`, windows `[3 -> 5]`, reach `3.4`
- `SWORD_AIRSLASH`: duration `13`, windows `[7 -> 10]`, reach `3.2`
- `SWEEPING_EDGE`: duration `20`, windows `[3 -> 6]`, reach `4.0`
- `SWORD_DUAL_AUTO1`: duration `12`, windows `[2 -> 4]`
- `SWORD_DUAL_AUTO2`: duration `12`, windows `[2 -> 4]`
- `SWORD_DUAL_AUTO3`: duration `15`, windows `[5 -> 7]`
- `SWORD_DUAL_DASH`: duration `15`, windows `[1 -> 6]`, reach `3.4`
- `SWORD_DUAL_AIRSLASH`: duration `13`, windows `[7 -> 10]`, reach `3.2`
- `DANCING_EDGE`: duration `25`, windows `[5 -> 8]`, `[8 -> 10]`, `[12 -> 14]`
- `ROLL_FORWARD`: duration `13`
- `ROLL_BACKWARD`: duration `13`
- `STEP_FORWARD`: duration `7`
- `STEP_BACKWARD`: duration `7`
- `STEP_LEFT`: duration `7`
- `STEP_RIGHT`: duration `7`
- `JUMP`: duration `10`

Attack windows correspond to the active swing state. Before a window is anticipation/preparation. After a window is recovery. Among current shared ids, only `DANCING_EDGE` is multi-phase and has three separate windows. `SWORD_DUAL_AUTO3` and `SWORD_DUAL_DASH` use both weapon colliders in one phase, so each remains a single server damage window.

## Root motion

`RigRootMotion` stores root-motion curves extracted from generated animation body position tracks. It is common/server-safe and keyed by `RigAnimationId`.

The server samples root motion with previous and current elapsed ticks, converts model units to blocks, rotates the local delta by the mob's attack-facing direction, and moves the real entity with `mob.move(MoverType.SELF, delta)`. This is the authoritative movement path for dash attacks, sweeping attacks, rolls, steps, and other moving rig animations.

Clients receive `ClientboundRigAnimation` for playback and vanilla entity tracking for the real server position. This project intentionally avoids a custom `ClientboundRigAnimationPosition` packet and avoids writing client `lerpX`, `lerpY`, `lerpZ`, or `lerpSteps` directly for rig root motion. A separate position packet would add a second sync path and can create jitter or disagreement with vanilla tracking.

## Server-side playback API

`RigAnimationController.play(Mob mob, RigAnimationSpec spec, LivingEntity target)` is the common API for `AVNpc`, `HerobrineMob`, and other rig-capable mobs.

It runs only server-side. It:
1. faces the target when present
2. swings the main hand only for attack animations
3. marks the mob aggressive
4. sends `ClientboundRigAnimation` to tracking clients and self
5. schedules rig sound events
6. schedules server-authoritative root motion from `RigRootMotion`
7. schedules one damage window task group for each value in `attackWindows` for damaging attack specs

Movement behavior is no longer controlled by movement types. `RigAnimationController` schedules `RigRootMotion.worldDelta(animationId, previousElapsedTicks, elapsedTicks, forward)` for each animation tick and applies nonzero deltas with `mob.move(MoverType.SELF, delta)`.

Hit timing uses `DelayedTask`. During every attack window tick, the target must still be alive, attackable, non-allied, and inside reach. Each window can hit once; if the target enters reach during the window, the hit can still connect. Multi-window attacks temporarily clear the target hurt cooldown for each window so each phase can deal damage.

Sound timing also uses `DelayedTask` from the server. The controller calls `level.playSound(null, x, y, z, sound, SoundSource.HOSTILE, volume, pitch)`, so no player is excluded and nearby players hear the sound through normal Minecraft sound broadcasting.

Rig sound rules:
- normal attacks, including dash and former single-window ultimate attacks, play `AnnoyingVillagersModSounds.SWORD_WHOOSH` when each attack window starts
- `DANCING_EDGE` plays `AnnoyingVillagersModSounds.WHOOSH_SHARP` when each attack window starts
- successful rig damage plays `AnnoyingVillagersModSounds.BLADE_HIT` at the target position
- `ROLL_FORWARD` and `ROLL_BACKWARD` play `AnnoyingVillagersModSounds.ROLL` at animation start
- step animations play the current block-under-feet hit sound at animation start instead of using a custom step asset

## Weapon profiles

`RigCombatProfile` stores categorized animation sets:
- normal attack chain
- special attacks for dash, airslash, or other non-combo interrupts
- rolling/root-motion interrupts
- ultimate interrupts
- chances for special, rolling, and ultimate interrupts

Normal attacks are deterministic and sequential. A profile with normal attacks `[1, 2, 3, 4]` plays `1 -> 2 -> 3 -> 4 -> 1` unless interrupted. Interrupts do not advance the normal combo index.

Closing attacks are selected from special attacks with movement distance. A closing attack may open distance, but it should not immediately repeat the same single closing animation; if the only available closing option was just played, the goal should keep pathing toward melee range so the normal combo can resume.

Closing attack distance is derived from `attackReachBlocks + RigRootMotion.maxHorizontalDistanceBlocks(animationId)`, not from lunge fields.

`RigCombatProfiles.getCombatProfile` returns `DUAL_SWORD` when the mob has a `SwordItem` in both main hand and off hand. `DUAL_SWORD` uses `SWORD_DUAL_AUTO1`, `SWORD_DUAL_AUTO2`, and `SWORD_DUAL_AUTO3` as the deterministic normal combo, `SWORD_DUAL_DASH` and `SWORD_DUAL_AIRSLASH` as non-combo interrupts, and `DANCING_EDGE` as the rare ultimate interrupt.

Other mobs use `DEFAULT_SWORD`. `DEFAULT_SWORD` includes `SWEEPING_EDGE` in the deterministic normal combo, uses `SWORD_DASH` and `SWORD_AIRSLASH` as special interrupts, and has no current ultimate interrupt.

This class is the extension point for exact custom weapon chains. When weapon-specific profiles are reintroduced, keep normal attacks deterministic, keep dash/root-motion attacks as normal or special attacks, and reserve ultimate lists for genuine multi-window ultimate attacks.

## Combat goal

`RigAnimatedMeleeAttackGoal` replaces vanilla melee only for rig-capable mobs selected by `CommonGoals.supportsRigCombat`: `AVNpc` and `HerobrineMob`.

The goal ignores mobs holding bows. It moves toward the target, starts root-motion closing attacks from farther range, and starts normal/interruption attacks from melee range. While an active rig animation is playing, it stops path navigation so scheduled root motion and hit timing drive the action.

`CommonGoals.createMeleeAttackGoal` returns:
- `RigAnimatedMeleeAttackGoal` for `AVNpc` and `HerobrineMob`
- vanilla `MeleeAttackGoal` for other mobs, including vanilla mobs that receive shared goals through mixins

## Client rendering

Server attack selection never imports client `AnimationDefinition`. It sends only `RigAnimationId` and duration. The client model resolves the id through `RigAnimationResolver` and applies the active one-shot animation before locomotion.
