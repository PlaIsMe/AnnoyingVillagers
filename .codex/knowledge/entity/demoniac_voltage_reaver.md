# Demoniac Voltage Reaver Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/item/DemoniacVoltageReaverItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SnakeBladeEntity.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/SnakeBladeRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SwordsmanHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Swordsman Herobrine Entry Point

`SwordsmanHerobrineEntity` equips `DEMONIAC_VOLTAGE_REAVER`.

`HerobrineCommon.playSecondFormAnimation` calls `DemoniacVoltageReaverItem.process(item, herobrineMob)` for Swordsman and sets the `SnakeAnimation` item tag.

`AVAnimations.SNAKE_BLADE` also calls `DemoniacVoltageReaverItem.process` at animation time.

## process()

`DemoniacVoltageReaverItem.process` selects the closest usable portal first. If no portal is found, it searches living targets within `TARGET_SEARCH_RADIUS = 16.0D`.

The living target filter rejects allies, spectators, creative players, non-player/non-mob entities, and targets without line of sight.

The portal search radius is `PORTAL_TARGET_SEARCH_RADIUS = 64.0D`.

Portals owned by the attacker are valid. The session patch also allows portals owned by another Herobrine-side entity through `HerobrinePortalCombatUtil.canUsePortalOwnedBy`.

## processGuard()

`processGuard` is the guard-mode entry point. It creates guard snake blade chains around the guarded entity.

Guard chains intentionally do not use portal-chain behavior because `SnakeBladeEntity.handleChaining` processes guard mode before portal chaining.

## SnakeBladeEntity Portal Flow

`SnakeBladeEntity` is excluded from physical `PortalEntity` teleportation. Instead, portal travel is implemented through chain creation.

When the current target is a portal, `createChainThroughPortal`:

- marks the entrance touched
- resolves the linked exit if present
- marks the exit touched if present
- uses the exit portal center as the chain origin, or the entrance if unlinked
- tries to attack a living target near the origin
- otherwise advances to the next ordered portal in the same group
- otherwise tries the closest usable portal
- otherwise retracts

This supports paired portals like `1 <-> 2`, `3 <-> 4`, and `5 <-> 6`, while also supporting single unlinked portals.

## Retraction And Rendering

Snake blade progress extends and retracts over `MAX_EXTEND_TIME = 5.0F`.

When a child fully retracts, the parent is set to retract. When the root fully retracts, the creator capability is cleared, `SnakeAnimation` is removed from the item, and the creator may be returned to `AVAnimations.IDLE_BREAK`.

`SnakeBladeRenderer` uses `getRenderFromEntity`. If the render-from entity is a `PortalEntity`, it uses `portal.getPortalCenter`, so child chains visually emerge from the portal center.

## Portal Support Trigger

When Greg, Transporter Herobrine Clone, or Aegis creates support portals, `HerobrinePortalCombatUtil` can trigger nearby Swordsman Herobrine to play `AVAnimations.SNAKE_BLADE`, which runs `process()`.
