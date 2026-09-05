# Demoniac Voltage Reaver Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/item/DemoniacVoltageReaverItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SnakeBladeEntity.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/SnakeBladeRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SwordsmanHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/util/RigPoseUtil.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrineUtil.java`

## Current Swordsman Entry Point

`SwordsmanHerobrineEntity` equips `DEMONIAC_VOLTAGE_REAVER` and registers a Rig second-form goal for `SWORDSMAN_HEROBRINE_ULT` / `SWORDSMAN_HEROBRINE_EXTRA_ULT`.

The normal ULT hook no longer requests Greg to create portals in the same tick. Its tick-0 hook only calls `DemoniacVoltageReaverItem.tryStartSnakeAnimation(stack, swordsman, false)` and consumes the second-form action budget. The old request line is intentionally left commented in source.

Greg independently prepares a six-portal group through `HerobrineGregSixPortalSupportGoal` when Greg's current support is his linked Swordsman in state 2 and the shared 20-40 second portal-action cooldown is ready. Greg plays `PORTAL_SUMMON`, creates the portal batch at animation tick 20, and stores that group as the preferred portal target.

When `EliteHerobrineSecondFormGoal` sees a ready six-portal group and its normal cooldown/action checks are ready, it forces `SWORDSMAN_HEROBRINE_ULT`; `SWORDSMAN_HEROBRINE_EXTRA_ULT` is not selected for that prepared six-portal follow-up. Normal ULT may start only while the Swordsman's current target is within 12 blocks, leaving margin inside the 16-block Snake Blade living-target scan radius.

The EXTRA ULT still calls `tryStartSnakeAnimation(stack, swordsman, true)`.

All live portal ownership/routing helpers now live in `HerobrineUtil`.

## tryStartSnakeAnimation

`tryStartSnakeAnimation` calls either `process` or `processGuard`. If a chain launches, or a last fragment is still present, it sets the item `SnakeAnimation` tag. Otherwise it clears snake-animation/portal-target state.

`clearInterruptedSnakeAnimation` waits while a live last fragment exists or while the Swordsman rig ULT/EXTRA ULT is still playing, preventing early cleanup.

## Epic Fight Compatibility Comments / Rig Fallbacks

`isPlayingSnakeBladeAnimation` currently falls back to `RigAnimationController.getActiveAnimationId(mob)` and recognizes the two Swordsman ULT ids.

`getToolTipPos` currently uses `RigPoseUtil.getRightWeaponPosition(...)` for an active mob rig animation. If no usable rig pose exists it falls back to `CommonUtil.getVanillaSwordOrBodyPosition(...)`. The commented Epic Fight armature/joint calculation remains in place above this fallback.

## process()

`process` selects the preferred/closest usable portal first. If no portal route is available, it searches living targets within `TARGET_SEARCH_RADIUS = 16.0D`.

The living-target filter rejects allies, spectators, creative players, non-player/non-mob entities, and targets without line of sight. Portal search uses the broader portal target range and `HerobrineUtil.canUsePortalOwnedBy(...)` for Herobrine-side ownership rules.

## processGuard()

`processGuard` is guard-mode snake-blade behavior. The Swordsman EXTRA ULT uses this path by passing `true` to `tryStartSnakeAnimation`.

## SnakeBladeEntity Portal Flow

`SnakeBladeEntity` is excluded from physical `PortalEntity` teleportation. Portal travel is represented by chain creation. When a chain target is a portal, the entity resolves the linked exit/portal group, chooses a new origin/target, and continues or retracts.

## Retraction And Rendering

Snake blade extension/retraction state owns cleanup of the creator capability and item `SnakeAnimation` flag. `SnakeBladeRenderer` uses the chain's render-from entity; portal-origin chains render from portal center rather than pretending the weapon/entity itself physically teleported.
