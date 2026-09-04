# Swordsman Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/SwordsmanHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/item/DemoniacVoltageReaverItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SnakeBladeEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/EliteHerobrineSecondFormGoal.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/util/RigPoseUtil.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Current Non-EpicFight Role

`SwordsmanHerobrineEntity` extends `HerobrineMob`, equips `DEMONIAC_VOLTAGE_REAVER`, and uses the articulated vanilla rig combat system.

It registers `EliteHerobrineSecondFormGoal` with two randomly selectable actions while the correct weapon is equipped and no snake animation is already active:

- `SWORDMAN_HEROBRINE_ULT`
- `SWORDMAN_HEROBRINE_EXTRA_ULT`

These are the current equivalents of the old Demoniac Voltage Reaver innate and innate-special entries from the Epic Fight branch.

## Rig ULT Hooks

The behavior is implemented in `RigAnimationSpecs`, not by directly playing old `AVAnimations.SNAKE_BLADE` from `HerobrineCommon`.

At animation start:

- `SWORDMAN_HEROBRINE_ULT` calls `DemoniacVoltageReaverItem.tryStartSnakeAnimation(stack, swordsman, false)`;
- `SWORDMAN_HEROBRINE_EXTRA_ULT` calls `DemoniacVoltageReaverItem.tryStartSnakeAnimation(stack, swordsman, true)`.

The hooks are explicitly restricted to `SwordsmanHerobrineEntity`, require the Demoniac Voltage Reaver, and use the shared `HerobrineMob` second-form action budget. Both specs are dangerous/invulnerable during playback.

## Snake Animation Compatibility

The active non-EpicFight fallback treats `SWORDMAN_HEROBRINE_ULT` and `SWORDMAN_HEROBRINE_EXTRA_ULT` as the snake-blade animation state for rig mobs. This prevents the `SnakeAnimation` NBT flag from being cleared merely because Epic Fight is absent.

`getToolTipPos(...)` likewise retains its commented Epic Fight joint-transform implementation. The current fallback samples the right-weapon position through `RigPoseUtil` when a rig animation is active, then falls back to `CommonUtil.getVanillaSwordOrBodyPosition(...)` for ordinary/non-rig entities.

## Portal / Snake Blade Flow

`DemoniacVoltageReaverItem.process()` still supports Herobrine portal targeting before ordinary living targets, and `SnakeBladeEntity` keeps its custom portal-chain flow. Those systems are independent from how the ULT animation itself is selected.

Detailed snake-chain behavior remains in `.codex/knowledge/entity/demoniac_voltage_reaver.md` and `.codex/knowledge/entity/snake_blade.md`.
