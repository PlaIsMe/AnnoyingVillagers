# Reaper And Glaive Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/ReaperHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/GlaiveHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HerobrineDragonEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Glaive Herobrine

`GlaiveHerobrineEntity` extends `HerobrineMob` and equips `ENDER_GLAIVE`.

Because it extends `HerobrineMob`, its non-riding behavior uses common portal approach logic and can run through linked portals whose exits are near its target.

On death, it creates `EliteHerobrineKnockedEntity` with `FromElite = "EnderGlaive"`.

## Reaper Herobrine

`ReaperHerobrineEntity` extends `HerobrineMob`.

It manages three possible `HerobrineDragonEntity` references:

- thunder dragon
- meteorite dragon
- healing dragon

The thunder dragon is separate from meteorite behavior.

## Meteorite Dragon Portal Aim

`HerobrineDragonEntity.shootMeteoriteAtTarget` creates `DragonMeteoriteEntity`.

The normal aim point is the target body midpoint. The session patch asks `HerobrinePortalCombatUtil.getProjectilePortalAim(this, target)` for a portal route. If found, the meteorite aims at the portal entrance instead.

`shootThunderBreathAtTarget` is not changed by the portal patch.

## Dragon Teleport Guard

`PortalEntity.canTeleportEntity` rejects `HerobrineDragonEntity`, and passengers are already rejected. This prevents dragons and dragon-mounted Herobrines from being teleported by portal collision.
