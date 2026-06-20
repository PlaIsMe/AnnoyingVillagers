# Null Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/NullEntity.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/NullWeapon.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## NullEntity

`NullEntity` extends `HerobrineMob`.

It owns up to five `NullWeapon` entities:

- sword
- axe
- pickaxe
- shovel
- hoe

Null periodically calls each weapon's teleport process while the weapon exists.

Because `NullEntity` extends `HerobrineMob`, it inherits common portal approach logic for its own movement.

## NullWeapon

`NullWeapon` extends `Monster` and uses flying movement/navigation.

It can be released temporarily with `releaseForAWhile`, setting `released = true`.

The session patch adds `PortalApproachGoal` to `NullWeapon.registerGoals`.

`HerobrinePortalCombatUtil.canUsePortalApproach` allows Null weapons to use portal approach only while `isReleased()` is true. This matches the requested release-mode portal behavior.

## Portal Interaction

Released Null weapons can path toward a linked portal entrance when that portal exits near their target. They rely on normal `PortalEntity` teleportation after moving into the portal.
