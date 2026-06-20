# Transporter Herobrine Clone Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/TransporterHerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/init/AnnoyingVillagersModEntities.java`
- `src/main/java/com/pla/annoyingvillagers/init/AnnoyingVillagersModEntityRenderers.java`
- `src/main/java/com/pla/annoyingvillagers/init/AnnoyingVillagersModItems.java`
- `src/main/java/com/pla/annoyingvillagers/config/AnnoyingVillagersSpawnConfig.java`
- `src/main/resources/assets/annoyingvillagers/lang/en_us.json`

## Identity And Registration

The entity id is `annoyingvillagers:transporter_herobrine_clone`.

The Java class is `TransporterHerobrineCloneEntity`, registered as `AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE`.

It extends `HerobrineMob`, so it inherits the Herobrine singleton natural-spawn claim/release behavior from `HerobrineMob.finalizeSpawn` and `HerobrineMob.remove`.

It uses:

- `ShadowHerobrineRenderer`
- `ShadowHerobrineClonePatch`
- the normal biped patched renderer
- Shadow Herobrine Clone visual texture behavior

## Spawn And Resources

Natural spawn is added through `AnnoyingVillagersSpawnConfig` with `fixedGroupEntry("transporter_herobrine_clone", 1, "Transporter Herobrine Clone")`.

Spawn placement uses `TransporterHerobrineCloneEntity.canSpawn`, which mirrors Herobrine clone rules:

- every 3 days, except day 0 is allowed
- only if `HerobrineMobData` is not occupied
- only at night
- normal monster spawn rules

The spawn egg is `transporter_herobrine_clone_spawn_egg`, with a model at `assets/annoyingvillagers/models/item/transporter_herobrine_clone_spawn_egg.json`.

## Attributes

`createAttributes` sets:

- max health: 30
- movement speed: 0.5
- attack damage: 0
- follow range: 48
- armor: 4
- knockback resistance: 1

## Damage And Retreat

The entity caps normal non-void damage to `1.0F`, Greg-style.

It ignores fall, cactus, wither, drown, wither skull, and dragon breath damage.

Low-health escape now starts every time health is at or below 35% max health and the retry cooldown has expired.

Escape timing uses:

- `ESCAPE_DURATION_TICKS = 70`
- portal summon effect at start and around tick 60
- `HerobrinePortalUtil.sinkIntoGround` around tick 40
- discard at tick 1

If hit during escape by non-void damage, only 15% of hits are allowed to deal the normal 1 damage. Escape is not canceled on hit.

## Drops

On normal death, `dropCustomDeathLoot` keeps the inherited HerobrineMob drop behavior and adds a `TRANSPORTER_FRAGMENT` drop with `TRANSPORTER_FRAGMENT_DROP_CHANCE = 0.1F`.

If the transporter clone dies while escape is active, it always drops one transporter fragment.

## Low Clone Summon

The entity periodically tries to summon either `LOW_HEROBRINE_CLONE` or `LOW_SHADOW_HEROBRINE_CLONE`.

The summon cooldown now matches Greg's support window:

- `LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS = 180 * 20`
- `LOW_CLONE_SUPPORT_COOLDOWN_MAX_TICKS = 300 * 20`
- failed summon checks retry after `LOW_CLONE_SUPPORT_RETRY_TICKS = 10 * 20`

Each successful summon cycle spawns 1 to 3 support clones.

Spawn position search uses nearby random surface positions from the `MOTION_BLOCKING_NO_LEAVES` heightmap and requires clear spawn space.

The summoned low clone:

- is spawned as `MOB_SUMMONED`
- is placed on team `herobrine`
- gets a portal summon sound
- receives combat support gear, including broken diamond armor rolls or rare netherite armor for some low shadow clones
- receives a support weapon chosen from iron sword, diamond sword, `OBSIDIAN_WEAPON`, or `SHADOW_OBSIDIAN_PILLAR`
- inherits the transporter's current target when one exists
- uses render-portal visual setup where supported

## Combat Portal Support

`portalSupportCooldown` periodically calls `HerobrinePortalCombatUtil.tryTransporterPortalSupport`.

That helper now shares Greg's multi-support plan logic:

- first preference is a nearby Herobrine-side ally whose enemy is still at least 10 blocks away
- if no ally currently needs direct approach help, it can connect two spread-out Herobrine-side allies with a gather portal pair
- if no ally plan is available, the entrance falls back to the transporter itself and the exit goes to the chosen enemy
