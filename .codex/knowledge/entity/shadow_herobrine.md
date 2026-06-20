# Shadow Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/ShadowHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/HerobrineCommon.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Entity Role

`ShadowHerobrineEntity` extends `HerobrineMob`.

It manages prepared dark obsidian projectiles:

- `darkObUp`
- `darkObLeft`
- `darkObRight`

It also has `obsidianMachineGunTick` and cooldown fields for shadow obsidian machine-gun behavior.

## Prepared Dark Obsidian Shots

`shootDarkObsAtTarget(double speed)` normally aims prepared dark obsidian projectiles at the target eye position.

The session patch asks `HerobrinePortalCombatUtil.getProjectilePortalAim` for a nearby portal entrance linked to an exit near the target. If found, prepared dark obsidian shots aim into that portal.

## Obsidian Machine Gun

`shootChain(BlockState block, float velocity, int length)` creates a short chain of `BlockProjectileEntity` projectiles along the current look direction.

The session patch redirects the look direction toward a valid portal entrance when the linked exit is near the current target.

## Portal Approach

Because Shadow Herobrine extends `HerobrineMob`, it inherits common portal approach logic and can run into a linked portal if the exit is near its target.
