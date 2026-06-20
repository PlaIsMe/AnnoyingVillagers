# Herobrine Clone Variant Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrineMob.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/ShadowHerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HerobrineChrisEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/Herobrine7Entity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/ArmoredHerobrineEntity.java`

## HerobrineMob Base

`HerobrineMob` is the main base for standard and elite Herobrine variants.

It registers common hostile NPC goals through `CommonGoals.registerGoalForHostileNpc(this)`, which now includes `PortalApproachGoal`.

Natural spawn and removal use `HerobrineMobData` singleton guard:

- `finalizeSpawn` tries to claim the singleton on natural or chunk-generation spawn
- if claim fails, the mob discards
- `remove` releases the claim on killed or discarded removal

`HerobrineMob.finalizeSpawn` also moves natural spawns to the surface heightmap and calls `HerobrineUtil.initialSpawn`.

## Standard Clone Rules

`HerobrineCloneEntity` and `ShadowHerobrineCloneEntity` both extend `HerobrineMob`.

They naturally spawn at night every 3 days, using the `HerobrineMobData` singleton guard.

Both ignore several environmental damage types and reject most normal arrows unless the direct entity is an allowed custom projectile.

Both create an infected player corpse on death and copy armor slots to that corpse.

## Portal Interaction

Because these variants extend `HerobrineMob` and use common hostile goals, they can run into a nearby linked portal when that portal exits near their attack target.

They can also use Herobrine-owned shared portals, including portals spawned by Greg and Transporter Herobrine Clone.

## Transporter Variant Relationship

`TransporterHerobrineCloneEntity` is a new Herobrine clone variant that extends `HerobrineMob` and uses Shadow Herobrine Clone visual/patch behavior. Its details are stored in `.codex/knowledge/entity/transporter_herobrine_clone.md`.
