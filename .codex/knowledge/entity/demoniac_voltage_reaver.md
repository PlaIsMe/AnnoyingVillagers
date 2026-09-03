# Demoniac Voltage Reaver Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/item/DemoniacVoltageReaverItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SnakeBladeEntity.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/SnakeBladeRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SwordsmanHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/util/RigPoseUtil.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Current Swordsman Entry Point

`SwordsmanHerobrineEntity` equips `DEMONIAC_VOLTAGE_REAVER` and registers a rig second-form goal for `SWORDMAN_HEROBRINE_ULT` / `SWORDMAN_HEROBRINE_EXTRA_ULT`.

The current rig hooks call:

- `DemoniacVoltageReaverItem.tryStartSnakeAnimation(stack, swordsman, false)` for ULT;
- `DemoniacVoltageReaverItem.tryStartSnakeAnimation(stack, swordsman, true)` for EXTRA ULT.

Do not document `HerobrineCommon.playSecondFormAnimation` or `AVAnimations.SNAKE_BLADE` as the current Swordsman second-form entry point. Those are legacy Epic Fight-era flow descriptions.

## tryStartSnakeAnimation

`tryStartSnakeAnimation` calls either `process` or `processGuard`. If a chain launches, or a last fragment is still present, it sets the item `SnakeAnimation` tag. Otherwise it clears snake-animation/portal-target state.

`clearInterruptedSnakeAnimation` waits while a live last fragment exists or while the Swordsman rig ULT/EXTRA ULT is still playing, preventing early cleanup.

## Epic Fight Compatibility Comments / Rig Fallbacks

The commented `ADD THIS CODE IN AV_EFM` blocks in this item are intentional and must be preserved for a future Epic Fight compatibility mixin/addon.

`isPlayingSnakeBladeAnimation` currently falls back to `RigAnimationController.getActiveAnimationId(mob)` and recognizes the two Swordsman ULT ids.

`getToolTipPos` currently uses `RigPoseUtil.getRightWeaponPosition(...)` for an active mob rig animation. If no usable rig pose exists it falls back to `CommonUtil.getVanillaSwordOrBodyPosition(...)`. The commented Epic Fight armature/joint calculation remains in place above this fallback.

## process()

`process` selects the preferred/closest usable portal first. If no portal route is available, it searches living targets within `TARGET_SEARCH_RADIUS = 16.0D`.

The living-target filter rejects allies, spectators, creative players, non-player/non-mob entities, and targets without line of sight. Portal search uses the broader portal target range and `HerobrinePortalCombatUtil.canUsePortalOwnedBy(...)` for Herobrine-side ownership rules.

## processGuard()

`processGuard` is guard-mode snake-blade behavior. The Swordsman EXTRA ULT uses this path by passing `true` to `tryStartSnakeAnimation`.

## SnakeBladeEntity Portal Flow

`SnakeBladeEntity` is excluded from physical `PortalEntity` teleportation. Portal travel is represented by chain creation. When a chain target is a portal, the entity resolves the linked exit/portal group, chooses a new origin/target, and continues or retracts.

## Retraction And Rendering

Snake blade extension/retraction state owns cleanup of the creator capability and item `SnakeAnimation` flag. `SnakeBladeRenderer` uses the chain's render-from entity; portal-origin chains render from portal center rather than pretending the weapon/entity itself physically teleported.
