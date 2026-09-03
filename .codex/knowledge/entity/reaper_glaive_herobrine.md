# Reaper And Glaive Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/ReaperHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/GlaiveHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HerobrineDragonEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/DragonSummonRiseGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/MountOrDismountDragonGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/EliteHerobrineSecondFormGoal.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigCombatProfiles.java`
- `src/main/java/com/pla/annoyingvillagers/item/EnderGlaiveItem.java`

## Current Rig Architecture

Both entities use the current non-EpicFight articulated rig system. Epic Fight animation/skill classes from the compatibility branch are reference material only; live gameplay hooks belong in `RigAnimationSpecs`, entity helpers, and vanilla AI goals.

## Glaive Herobrine

`GlaiveHerobrineEntity` extends `HerobrineMob`, equips `ENDER_GLAIVE`, and registers `EliteHerobrineSecondFormGoal` with two possible second-form animations: `GLAIVE_HEROBRINE_ULT` and `GLAIVE_HEROBRINE_EXTRA_ULT`.

The `GLAIVE_HEROBRINE` profile owns authored attacks 1-5, dash/jump specials, roll/step movement, and `GLAIVE_HEROBRINE_EXTRA_ATTACK`.

Important spec behavior:

- `GLAIVE_HEROBRINE_JUMP_ATTACK`: forces a downward component at tick 8 while airborne and triggers a ground slam at tick 13.
- `GLAIVE_HEROBRINE_ULT`: tick-23 hook calls `EnderGlaiveItem.spawnVacumSlise(...)`; state-1 action budget is consumed there.
- `GLAIVE_HEROBRINE_EXTRA_ULT`: tick-27 hook calls the same vacuum-slice entry point and consumes the state-1 action there.
- Both ULT specs are dangerous/invulnerable during playback.

Do not move the second-form action consumption back into an Epic Fight skill container.

## Reaper Herobrine Dragon Slots

`ReaperHerobrineEntity` extends `HerobrineMob`, equips `ENDER_SLAYER_SCYTHE`, and owns three persistent dragon slots/UUIDs:

- type 0: thunder dragon;
- type 1: meteorite dragon;
- type 2: healing dragon.

The progression order remains intentional:

1. initial spawn requests the thunder dragon;
2. before state 2, meteorite is requested when Reaper is at or below 50% health;
3. once state 2 is reached, any missing slot may be restored, including the healing dragon.

The initial thunder request is retried until its summon ULT actually starts; an unrelated active rig animation on the first tick must not permanently consume `spawnDragonInit`.

## Reaper Dragon Summon ULT

`summonEnderDragon(int type)` is now the non-EpicFight replacement for the old Ender Slayer Scythe innate summon flow.

Rules:

- server side only, type 0-2;
- refuses a new request while another summon is pending or any rig animation is active;
- stops navigation, clears aggression, and plays `PORTAL_NATURAL`;
- starts `REAPER_HEROBRINE_ULT`;
- after successful start, locks normal profile attacks for the full ULT duration;
- an interrupted/replaced ULT clears the pending request so normal progression can retry.

The `REAPER_HEROBRINE_ULT` spec owns the actual effect at tick 22. It creates the large ground fracture and calls `completePendingDragonSummon()`.

The dragon is created at Reaper's X/Z about 5 blocks underground. `HerobrineDragonEntity.startSummonRise(...)` temporarily enables no-gravity and `noPhysics`, and `DragonSummonRiseGoal` flies it vertically toward a safe point roughly 15 blocks above Reaper. Collision is restored after the dragon reaches Reaper's original Y; normal gravity/navigation state is restored when the summon-rise state completes.

This separation is important: `summonEnderDragon` requests/locks the animation, while the timed ULT hook performs the spawn.

## Reaper Mount / Dismount Goal

`MountOrDismountDragonGoal` is the vanilla-AI equivalent of the old Epic Fight behavior. It uses a random cooldown from 60 to 180 seconds.

When Reaper is already a passenger, activation calls `stopRiding()`. Otherwise it prefers an available thunder dragon, then meteorite dragon, and calls `recallAndLand(true)` so the dragon lands with auto-mount behavior. The goal will not start during a pending summon, stun, or active rig animation.

## Reaper Second-Form Dragon Commands

Reaper registers a dynamic `EliteHerobrineSecondFormGoal`. The extra condition requires at least one usable dragon and the selector randomly chooses only among currently valid commands:

- `POINT_LEFT_HAND_TOWARD` requires a live, non-recalling thunder dragon. Its tick-5 spec hook calls `castThunderFromSecondForm()`, plays `REAPER_FIRE`, and uses `shootThunderBreathAtTarget(target)`.
- `POINT_LEFT_HAND_UP` requires a live, non-recalling meteorite dragon. Its tick-5 hook calls `shootMeteoriteAtTarget(target)`.
- `POINT_LEFT_HAND_MIDDLE` requires a live healing dragon with no passengers. Its tick-5 hook creates an `EndCrystal` at the dragon and mounts the crystal onto it.

The pointing specs are shared animation IDs but their hooks are explicitly guarded with `instanceof ReaperHerobrineEntity`, so other rig mobs do not inherit Reaper dragon commands.

Each successful command consumes one state-1 action through `HerobrineMob.consumeSecondFormAction()`; state 2 remains unlimited.

## Portal Notes

Meteorite portal aiming and the existing dragon portal-teleport guard remain separate from the summon/second-form migration. Dragons should not be made normal portal-collision passengers simply to support these new goals.
