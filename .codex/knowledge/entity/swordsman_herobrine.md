# Swordsman Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/SwordsmanHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HerobrineGregEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineGregSixPortalSupportGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/EliteHerobrineSecondFormGoal.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrineUtil.java`
- `src/main/java/com/pla/annoyingvillagers/item/DemoniacVoltageReaverItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SnakeBladeEntity.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/util/RigPoseUtil.java`

## Current Native Rig Role

`SwordsmanHerobrineEntity` extends `HerobrineMob`, equips `DEMONIAC_VOLTAGE_REAVER`, and uses the native Rig combat system.

Its `EliteHerobrineSecondFormGoal` can normally select either `SWORDSMAN_HEROBRINE_ULT` or `SWORDSMAN_HEROBRINE_EXTRA_ULT`. The entity-specific predicate still requires the Demoniac Voltage Reaver and no active SnakeAnimation.

## ULT Hooks

At tick 0 of `SWORDSMAN_HEROBRINE_ULT`:

1. start the normal Demoniac Voltage Reaver SnakeAnimation with `extra = false`;
2. consume the shared second-form action budget.

The former `requestGregSixPortalSupport()` call is intentionally commented out. Do not restore a same-tick Greg request there.

At tick 0 of `SWORDSMAN_HEROBRINE_EXTRA_ULT`:

1. start SnakeAnimation with `extra = true`;
2. consume the shared second-form action budget.

Both ULT specs remain dangerous and invulnerable.

## Greg Six-Portal Flow

Greg prepares six portals independently through `HerobrineGregSixPortalSupportGoal`; the Swordsman does not request the cast. Greg's currently selected support must be the Swordsman, the Swordsman must be state 2, the `gregUUID` link must match, and Greg's shared `portalActionCooldown` must be ready. Greg plays `PORTAL_SUMMON`, waits until animation tick 20, then creates the six-portal batch.

`EliteHerobrineSecondFormGoal` checks for a ready six-portal group owned by the Swordsman's exact linked Greg. If the normal second-form cooldown/action requirements are ready, the six-portal condition forces `SWORDSMAN_HEROBRINE_ULT`; `SWORDSMAN_HEROBRINE_EXTRA_ULT` is not selected for this prepared follow-up.

State 1 does not use the Greg six-portal flow. The six-portal interaction is state-2-only.

## SWORDSMAN_HEROBRINE_ULT Distance Rule

Snake Blade's normal living-target scan radius is 16 blocks, but the Swordsman may start `SWORDSMAN_HEROBRINE_ULT` only when its current combat target is within 12 blocks. The 12-block gate leaves a 4-block acquisition margin and prevents the Swordsman from standing too far away for the initial Snake Blade scan.

This distance restriction applies only to `SWORDSMAN_HEROBRINE_ULT`; `SWORDSMAN_HEROBRINE_EXTRA_ULT` is not blocked by this check. If normal ULT is selected while farther than 12 blocks, the second-form goal clears that selection and retries after 10 ticks so ordinary movement AI can close the distance.

## Snake Animation Compatibility

The non-EpicFight fallback treats both Swordsman ULT animations as active Snake Blade animation states for the Rig mob so SnakeAnimation NBT is not cleared merely because Epic Fight is absent.

`getToolTipPos(...)` retains its commented Epic Fight joint-transform implementation for AV_EFM. The native fallback uses `RigPoseUtil` right-weapon position during Rig animation and `CommonUtil.getVanillaSwordOrBodyPosition(...)` otherwise.

## Portal / Snake Blade Routing

All live generic portal-routing helpers used by `DemoniacVoltageReaverItem.process()` and `SnakeBladeEntity` are now in `HerobrineUtil`.

Do not restore the old direct-request six-portal flow or the old live `HerobrineSupportPortalUtil` / `HerobrinePortalCombatUtil` classes. Those Java files are commented legacy snapshots only.
