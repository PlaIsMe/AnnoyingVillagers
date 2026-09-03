# Herobrine Portal Combat Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/PortalApproachGoal.java`
- `src/main/java/com/pla/annoyingvillagers/util/CommonGoals.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/NullWeapon.java`
- `src/main/java/com/pla/annoyingvillagers/entity/PortalEntity.java`
- `src/main/java/com/pla/annoyingvillagers/item/TransporterFragmentItem.java`

## Herobrine-Side Entity Filter

`HerobrinePortalCombatUtil.isHerobrineSide` returns true for:

- `HerobrineMob`
- `HerobrineGregEntity`
- `LowHerobrineCloneEntity`
- `LowShadowHerobrineCloneEntity`
- `NullWeapon`

This is the shared filter used for Herobrine portal sharing and portal combat navigation.

## Portal Approach Goal

`PortalApproachGoal` is a movement goal for mobs with a live target. It asks `HerobrinePortalCombatUtil.findRouteToTarget` for a linked portal route.

The route is valid when:

- the entrance portal is near the mob
- the entrance portal is closer to the mob than the direct target body position
- the entrance has a linked portal
- the linked exit is near the target
- both portals are usable by the mob or shared by Herobrine-side ownership

For walking routes, route scoring prioritizes the linked exit closest to the target, with entrance distance as the secondary score. This makes the chosen route behave like "nearest useful entrance linked to the portal nearest the enemy" instead of picking a close entrance whose exit is less useful.

When active, the mob navigates to the entrance portal center and looks at it. `PortalApproachGoal.canContinueToUse` refreshes the route through `HerobrinePortalCombatUtil.findRouteToTarget`; if the target moves so the direct enemy path is closer than the portal entrance, or the linked exit is no longer near the target, the goal stops and normal combat movement resumes. The normal `PortalEntity` collision teleport then moves the mob to the linked exit.

## Shared Goal Wiring

`CommonGoals.registerGoalForHostileNpc` adds `PortalApproachGoal` at priority 0. This covers `HerobrineMob` variants and low shadow clones using hostile common goals, and gives portal approach priority over normal melee approach when a valid route exists.

`LowHerobrineCloneEntity` calls `CommonGoals.registerGoalForHostileNpc(this)`, so it also gets the portal approach goal.

`HerobrineGregEntity` and `NullWeapon` have custom goal registration, so each now adds `PortalApproachGoal` directly.

## Dragon Exclusion

`PortalEntity.canTeleportEntity` rejects `HerobrineDragonEntity`. It already rejects passengers, so Herobrines riding dragons are not teleported by walking or flying through a portal.

Projectiles launched by dragon logic can still be aimed through portals because projectile aiming uses `HerobrinePortalCombatUtil.getProjectilePortalAim`.

## Portal Collision Ownership Rules

`PortalEntity.canTeleportEntity` applies owner-based teleport filtering after its normal base checks.

Normal base checks reject `PortalEntity`, `SnakeBladeEntity`, `HerobrineDragonEntity`, removed/dead entities, passengers, spectator players, and entities still under the portal teleport cooldown.

Ownerless portals keep the previous permissive behavior after those base checks.

If the portal owner is Herobrine-side (`HerobrineMob`, `HerobrineGregEntity`, `LowHerobrineCloneEntity`, `LowShadowHerobrineCloneEntity`, or `NullWeapon`), the portal only teleports the owner entity itself, Herobrine-side entities, and projectiles.

If the portal owner is a `Player`, the portal rejects Herobrine-side entities and any `Monster`. It still allows players, villagers, animals, projectiles, and other non-monster/non-Herobrine entities.

## Portal Entity Sounds

`AnnoyingVillagersModSounds` now registers dedicated portal entity sounds:

- `PORTAL_OPEN`
- `PORTAL_AMBIENT`
- `PORTAL_FIZZLE`
- `PORTAL_ENTER`
- `PORTAL_EXIT`

`PortalEntity` now uses those sounds directly:

- open sound on the first server tick after spawn
- ambient sound every `AMBIENT_SOUND_INTERVAL_TICKS = 80`
- fizzle sound when the 10-second lifetime expires
- enter sound on the source portal during teleport
- exit sound on the linked destination portal during teleport

All of those portal entity sounds are played at volume `1.0F` and pitch `1.0F`. The older portal summon sounds are not used by `PortalEntity` itself.

## Shared Portal Ownership

`DemoniacVoltageReaverItem.findClosestPortalTarget` still prefers portals by distance, starter flag, and portal order, but it now allows portals owned by another Herobrine-side entity through `HerobrinePortalCombatUtil.canUsePortalOwnedBy`.

Player-owned one-hand portals keep owner filtering unless the user is Herobrine-side.

## Projectile Portal Aim

`HerobrinePortalCombatUtil.getProjectilePortalAim` finds an entrance portal near the shooter whose linked exit is near the target. If found, ranged attacks aim at the entrance portal center instead of directly at the target.

Current users:

- `AVAnimations.SLEDGEHAMMER_SHOOT`
- `HerobrineDragonEntity.shootMeteoriteAtTarget`
- `ShadowHerobrineEntity.shootDarkObsAtTarget`
- `ShadowHerobrineEntity.shootChain`

## Support Portal Spawning

`TransporterFragmentItem.spawnLinkedPortalPair(Level, LivingEntity, Vec3, Vec3)` is the public helper for mob AI. It keeps the active owner cap and existing placement validation, then spawns exactly one linked pair near the two preferred positions.

`TransporterFragmentItem.canSpawnOwnedPortals(ServerLevel, LivingEntity, int)` checks whether the caster has enough remaining owned active portal capacity before multi-pair AI actions. Greg uses this before active support reposition because that action needs four portal slots.

`HerobrinePortalCombatUtil.spawnSupportPortalPair` places one linked portal pair near the chosen entrance/exit entities. In the current non-EpicFight source, `playPortalPairSummon` / `playSixPortalSummon` play `PORTAL_NATURAL` and swing the caster's main hand; they do not call Epic Fight animation assets.

Greg's support portal logic now requires a ground support Herobrine. It waits until Greg is within 10 blocks of the chosen support before casting support portals. If he is farther away, he pathfinds toward that support and retries soon. Greg's support selection now supports multiple nearby Herobrines:

- first preference is a supported Herobrine whose enemy is at least 10 blocks away
- if no one needs direct approach help, Greg can instead connect two spread-out nearby Herobrines with a gather portal pair

Greg's support-position goal has higher priority than `PortalApproachGoal`, so when a support Herobrine exists Greg behaves like a support caster instead of portal-approaching the enemy. While supporting a ground Herobrine, `HerobrineGregEntity.markSupportingHerobrine` keeps Greg's Herobrine body texture, white-eye overlay, and broken diamond chestplate active through synced entity state and the existing renderer layers.

After a successful Greg portal support cast, `portalSupportCooldown` is randomized from 90 to 180 seconds. Failed/no-cast checks retry after 10 seconds.

Greg's six-portal support is separated from ordinary support and is implemented by `HerobrinePortalCombatGoal`. It targets a live `SwordsmanHerobrineEntity` in `state > 0` with a live target, Demoniac Voltage Reaver equipped, no active `SnakeAnimation` tag, no nearby six-portal batch, and compatible Greg ownership. A successful batch stores the portal group as the Swordsman's preferred portal target; it does not directly start the snake animation. Greg's current six-portal cooldown is 30-60 seconds.

Greg's support-position goal uses `HerobrinePortalCombatUtil.isEnemyOf` to avoid enemies during combat while staying near the supported Herobrine. It samples safe stand positions around the support Herobrine, favors positions farther from nearby enemies, rejects colliding positions, and stops movement once the current support spot is safe.

Greg's support-position goal now treats the supported Herobrine's current target as the primary threat. If that threat is close, Greg uses a wider danger stand radius and can actively run to `activeSupportRetreatPos`.

Greg active support reposition can spawn two linked portal pairs: one pair from the supported Herobrine to Greg's retreat position, and another pair from the retreat area to a flank near the enemy. This is separate from normal approach portal support and is triggered by danger proximity or Greg being hit.

Shared combat escape now also asks a linked Greg to call `tryOpenRetreatPortalFor` when a `HerobrineMob` performs `CombatCommon.performEscapeRunAway`, so retreat step-back behavior can immediately get a Greg-made retreat portal.

Greg also has a separate combat low-clone support cooldown in `HerobrineGregEntity`. That cooldown is randomized from 180 to 300 seconds after successful clone support. It can spawn 1 to 3 geared `LowHerobrineCloneEntity` or `LowShadowHerobrineCloneEntity` support clones near Greg's support anchor only when the supported Herobrine is less than 10 blocks from its enemy. It does not use `summonHerobrine`, so it does not overwrite Greg's main summoned-Herobrine slots.

`TransporterHerobrineCloneEntity` now uses the same multi-support portal plan logic through `tryTransporterPortalSupport`, so transporter clone portal casts can prefer an allied Herobrine that is far from its enemy, fall back to gather portals between spread-out Herobrines, or finally use self-to-enemy support if no ally plan is available.

## Swordsman Preferred Portal Support

Current six-portal support does not call an Epic Fight patch or directly invoke `AVAnimations.SNAKE_BLADE`. `HerobrinePortalCombatGoal` stores the spawned portal group on the Swordsman's Demoniac Voltage Reaver with `DemoniacVoltageReaverItem.setPreferredPortalTarget(...)`. The normal rig second-form goal/ULT hook later calls `tryStartSnakeAnimation`, and `process()` consumes that preferred portal route first.
