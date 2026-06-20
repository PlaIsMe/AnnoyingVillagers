# Herobrine Common Combat Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/HerobrineCommon.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/HerobrineDemoniacVoltageReaver.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/HerobrineEnderAegis.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/HerobrineObsidianSledgehammer.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrineMob.java`

## Common Second Form Hooks

`HerobrineCommon.canPlaySecondFormAnimation` prevents Swordsman Herobrine from starting another second-form animation while the Demoniac Voltage Reaver item still has `SnakeAnimation`.

`HerobrineCommon.playSecondFormAnimation` handles the main special behavior:

- Swordsman Herobrine calls `DemoniacVoltageReaverItem.process` and sets `SnakeAnimation`
- Reaper Herobrine uses the thunder dragon to shoot thunder breath at the target

`HerobrineCommon.playSecondFormGuardAnimation` calls `DemoniacVoltageReaverItem.processGuard` for Swordsman guard mode and sets `SnakeAnimation`.

`HerobrineCommon.playSecondFormSpecialAnimation` handles Reaper special meteorite behavior through the meteorite dragon.

## Combat Behavior Builders

`HerobrineDemoniacVoltageReaver` wires Swordsman Herobrine's Demoniac Voltage Reaver behavior and snake blade animation calls.

`HerobrineEnderAegis` wires Aegis Herobrine shield/projectile behavior.

`HerobrineObsidianSledgehammer` wires Sledgehammer Herobrine sledgehammer and projectile behavior.

## Portal Additions

Portal movement is not implemented in `HerobrineCommon`; it is implemented through goals and helpers:

- `CommonGoals.registerGoalForHostileNpc` adds `PortalApproachGoal`
- `HerobrinePortalCombatUtil` finds routes and support portals
- projectile animation events call `HerobrinePortalCombatUtil.getProjectilePortalAim`
