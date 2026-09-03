# Sledgehammer Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/SledgehammerHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/item/ObsidianSledgehammerItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/EliteHerobrineSecondFormGoal.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/potion/GroundStuckMobEffect.java`
- `src/main/java/com/pla/annoyingvillagers/event/GroundStuckEvent.java`
- `src/main/java/com/pla/annoyingvillagers/network/ClientboundGroundStuckKnockoutFx.java`
- `src/main/java/com/pla/annoyingvillagers/client/engine/GroundStuckKnockoutClient.java`
- `src/main/java/com/pla/annoyingvillagers/client/particle/smoke_wave/SmokeWaveOptions.java`
- `src/main/java/com/pla/annoyingvillagers/client/particle/smoke_wave/SmokeWaveParticle.java`

## Current Non-EpicFight Role

`SledgehammerHerobrineEntity` extends `HerobrineMob`, equips `OBSIDIAN_SLEDGEHAMMER`, and uses `RigCombatStyle.SLEDGEHAMMER_HEROBRINE`. The live implementation no longer depends on `AnimsObsidianSledgehammer` or Epic Fight skill/event classes.

It registers `EliteHerobrineSecondFormGoal` with `SLEDGEHAMMER_HEROBRINE_ULT` and `SLEDGEHAMMER_HEROBRINE_EXTRA_ULT` while the Obsidian Sledgehammer is equipped.

## Sledgehammer ULT

`SLEDGEHAMMER_HEROBRINE_ULT` is a damaging, dangerous, invulnerable rig attack.

At tick 12 its timed hooks:

- perform the authored ground-slam/fracture effect;
- consume one state-1 second-form action.

The successful rig hit callback applies `GroundStuckMobEffect` to the victim. Applying it from `RigAnimationSpec.onHit` is intentional because the callback runs only after the collider successfully damages a target; do not re-add an Epic Fight `AttackAnimation`/`GroundStuckEvent` injection for the initial hit.

## Sledgehammer EXTRA ULT / Smoke Waves

`SLEDGEHAMMER_HEROBRINE_EXTRA_ULT` is non-damaging as a direct animation but dangerous/invulnerable while active.

- tick 30: plays the sledgehammer sound and consumes the state-1 action;
- ticks 34/36/38/40: calls `ObsidianSledgehammerItem.spawnWave(...)` with four expanding annuli.

`spawnWave` is the port of the compatibility-branch wave logic. It sends oriented `SmokeWaveOptions` particles around the caster and applies Ground Stuck to enemies inside each wave ring. `SmokeWaveParticle` uses the custom double-sided particle render type and the eight `smoke_wave_*.png` frames.

## Ground Stuck

`AnnoyingVillagersModMobEffects.GROUND_STUCK` is a normal Forge mob effect in the non-EpicFight branch.

`GroundStuckMobEffect` stores anchor/support/knockout state in persistent entity NBT and keeps a stuck target fixed to its ground support. Follow-up damage while stuck is handled by `GroundStuckEvent`, including the amplified follow-up damage and knockout chance/launch behavior inherited from the old mechanic.

Hit-reaction compatibility is split by renderer capability:

- rig-supported mobs play `RigAnimationId.HIT_LEFT` or `HIT_RIGHT`;
- players and other non-rig living entities receive the 2-second fallback of nausea, slowness, and mining fatigue.

`RigStunCombatEvent` must not replace the Ground Stuck reaction with a normal rig hit/stun animation while the effect is active.

Knockout spin is synchronized with `ClientboundGroundStuckKnockoutFx`, stored client-side in `GroundStuckKnockoutClient`, and rendered by the vanilla `LivingEntityRenderer` mixin. This replaces the old Epic Fight renderer hook.

## Portal Behavior

Sledgehammer still inherits normal `HerobrineMob` portal-approach behavior. Any older documentation describing `AVAnimations.SLEDGEHAMMER_SHOOT` as the primary implementation is legacy compatibility-branch knowledge and not the current second-form path.
