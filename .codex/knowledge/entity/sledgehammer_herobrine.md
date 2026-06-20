# Sledgehammer Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/SledgehammerHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/gameasset/AVAnimations.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Entity Role

`SledgehammerHerobrineEntity` extends `HerobrineMob`.

It equips `OBSIDIAN_SLEDGEHAMMER` in the main hand and uses standard elite Herobrine attributes.

On death, it creates `EliteHerobrineKnockedEntity` with `FromElite = "ObsidianSledgehammer"` and can notify Greg through `requestProtect`.

## Projectile Portal Aim

`AVAnimations.SLEDGEHAMMER_SHOOT` creates `ObsidianSledgehammerProjectileEntity` from the hammer joint.

If the shooter is a mob with a target, the normal aim is the target eye position. The session patch asks `HerobrinePortalCombatUtil.getProjectilePortalAim` for a portal entrance near the shooter whose exit is near the target.

If such a route exists, the projectile aims at the portal entrance instead of the target, letting normal portal teleportation carry it to the target side.

## Portal Approach

Because Sledgehammer Herobrine extends `HerobrineMob`, it inherits the common portal approach goal and can run through linked portals whose exits are near its target.
