# Herobrine Greg Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/HerobrineGregEntity.java`
- `src/main/java/com/pla/annoyingvillagers/spawnhandler/GregData.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Base Role

`HerobrineGregEntity` extends `Monster`, not `HerobrineMob`.

Natural spawn uses `GregData` singleton guard instead of `HerobrineMobData`.

Greg has custom goal registration for following summoned Herobrines, avoiding threats, wandering, looking, floating, and now `PortalApproachGoal`.

Greg also has a stable support-position movement goal at priority 0, above `PortalApproachGoal`. It finds any nearby Herobrine-side support entity within `SUPPORT_SEARCH_RADIUS = 40.0D`, not only Herobrines stored in Greg's own summoned slots. It avoids support entities riding a Herobrine dragon. When active, Greg moves toward a stable position near the support entity, then stops navigation once he is within `SUPPORT_STAND_DISTANCE_SQR = 10.0D * 10.0D` and keeps looking at the support instead of orbiting around them.

During combat support positioning, Greg treats the supported Herobrine's target as the primary threat through `HerobrinePortalCombatUtil.findEnemyForSupport`. If that target is near Greg or the supported Herobrine, Greg samples `SUPPORT_SAFE_SAMPLE_COUNT = 16` candidate positions around the supported Herobrine at `SUPPORT_DANGER_STAND_RADIUS = 15.0D` instead of the normal `SUPPORT_STAND_RADIUS = 7.0D`. The danger stop range is `SUPPORT_DANGER_STAND_DISTANCE_SQR = 18.0D * 18.0D`, allowing Greg to run away from a close supported-Herobrine target while still staying near the support role. Candidate positions reject collisions and are scored by distance from the threat minus movement cost. Normal support movement uses `SUPPORT_MOVE_SPEED = 1.15D`; danger support movement uses `SUPPORT_DANGER_MOVE_SPEED = 1.25D`.

When Greg has `activeSupportRetreatTicks > 0`, the support-position goal prioritizes `activeSupportRetreatPos` and moves there at `SUPPORT_ACTIVE_RETREAT_MOVE_SPEED = 1.3D` before resuming normal support positioning.

Greg's generic `AvoidEntityGoal` entries use `AVOID_WALK_SPEED = 1.0D` and `AVOID_SPRINT_SPEED = 1.35D`, reduced from the prior faster combat avoidance values.

While supporting a Herobrine, Greg refreshes `supportingHerobrineVisualTicks` and sets synced `SUPPORTING_HEROBRINE = true`. During that visual window Greg forces `WHITE_EYE = true`, equips `BROKEN_DIAMOND_CHESTPLATE`, and the renderer uses `textures/entities/herobrine.png` when either `USE_HEROBRINE_TEXTURE` or `SUPPORTING_HEROBRINE` is true. The support texture state is transient and clears after the visual window, so it does not permanently overwrite `USE_HEROBRINE_TEXTURE`.

The older direct follow goals for Greg's first, second, and third summoned Herobrines still exist, but their distance thresholds are now closer:

- start following when farther than 8 blocks
- keep moving until about 5 blocks
- stop when close instead of staying far away

## Existing Summon And Escape Flow

Important fields:

- `summoning`
- `summonTiming`
- `escapeTiming`
- `summonTimestamp`
- `combatMode`
- `recallTime`
- `portalSupportCooldown`
- `sixPortalSupportCooldown`
- `lowCloneSupportCooldown`
- first, second, and third summoned Herobrine references and UUIDs

Low-health/day/night summon logic remains unchanged.

When `summonTiming == 1`, Greg calls:

- `summonHerobrines()` in combat mode
- `summonHerobrinesAndEscape()` otherwise

When `escapeTiming == 60` in combat mode, Greg plays portal summon sound, `AnimsSculkSteve.PORTAL_SUMMON`, and portal FX.

When `escapeTiming == 40`, Greg sinks into the ground with `HerobrinePortalUtil.sinkIntoGround`.

When `escapeTiming == 1`, Greg broadcasts his return message, auto-kills escaping low shadow clones, and discards.

## Damage Rules

Greg ignores damage while summoning.

Greg normally caps non-void damage to 1, and blocks damage if health is 1 or combat mode is active.

Updated session behavior: if Greg is hit during escape, each non-void hit has a 50% chance to deal Greg's normal 1 damage instead of being fully blocked. Escape is no longer canceled on hit. If Greg dies while escape is active, he drops one `TRANSPORTER_FRAGMENT`.

When Greg is hit outside escape/summoning, `markSupportPanicFromHit` sets `supportRetreatPanicTicks` and reduces `supportRepositionCooldown` so the active support reposition logic can run almost immediately.

## Portal Support

Greg now has `portalSupportCooldown`.

When not summoning and not escaping, he periodically calls `HerobrinePortalCombatUtil.tryGregPortalSupport`.

Portal support cooldown behavior:

- `PORTAL_SUPPORT_COOLDOWN_MIN_TICKS = 90 * 20`
- `PORTAL_SUPPORT_COOLDOWN_MAX_TICKS = 180 * 20`
- after a successful portal support cast, cooldown is randomized from 1 minute 30 seconds to 3 minutes
- if no portal is cast, Greg uses `PORTAL_SUPPORT_RETRY_TICKS = 10 * 20` so he can retry conditions without spamming actual portals
- six-portal support uses its own `sixPortalSupportCooldown`, randomized from 60 to 120 seconds after a successful cast

Greg support behavior:

- support focus now comes from `HerobrinePortalCombatUtil.findPortalSupportHerobrine`, so Greg can rotate across multiple nearby Herobrine-side allies instead of hard-locking on the nearest one
- ordinary portal support first looks for a supported Herobrine whose enemy is at least 10 blocks away, then can fall back to a gather portal between two spread-out Herobrines if no one currently needs direct approach help
- if the ground support Herobrine exists but Greg is more than 10 blocks away, Greg moves toward that support first and retries soon instead of casting from far away
- if the supported Herobrine is already less than 10 blocks from that enemy, Greg normally does not cast approach portals and just stays in safe support position
- the normal six-portal pattern is now reserved for `SwordsmanHerobrineEntity` support only, and only when `HerobrineCommon.canPlaySecondFormAnimation` is true for that swordsman
- after a successful swordsman six-portal cast, Greg immediately triggers that swordsman's snake blade animation
- otherwise he spawns one linked support pair either support-to-enemy or support-to-support, depending on the selected support plan

If Greg only moves toward a support Herobrine, his cooldown retries soon. If portal support activates, the 90-180 second cooldown is used.

Portal animation behavior:

- rare six-portal support uses `AnimsSculkSteve.PORTAL_SUMMON`
- one linked support pair uses `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP`

Portal creation can trigger nearby Swordsman Herobrine snake blade support.

Greg also exposes targeted helper methods:

- `tryOpenRetreatPortalFor`, used when a Greg-linked `HerobrineMob` performs a combat escape step-back
- `tryOpenApproachPortalFor`, used for Greg-assisted re-entry toward a target

## Active Support Reposition

Greg has active support reposition logic separate from normal support portals.

Timing:

- failed/no-op reposition retries after `SUPPORT_REPOSITION_RETRY_TICKS = 6 * 20`
- successful reposition cooldown is randomized from `SUPPORT_REPOSITION_MIN_COOLDOWN_TICKS = 35 * 20` to `SUPPORT_REPOSITION_MAX_COOLDOWN_TICKS = 70 * 20`
- hits set a panic window for `ACTIVE_SUPPORT_RETREAT_TICKS = 90`

Activation requires a ground supported Herobrine and a supported-Herobrine enemy. It runs when Greg is in panic, Greg is too near that enemy, or the supported Herobrine is too near that enemy.

Before spawning portals, Greg checks `TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, this, 4)` so there is room for both linked pairs.

On activation:

- Greg finds a far retreat surface position away from the supported Herobrine's enemy, roughly 16 to 24 blocks from the support anchor
- Greg spawns one linked pair from the supported Herobrine's current position to the retreat position, so the support can be pulled back through a portal
- Greg spawns a second linked pair from the retreat area to a flank position beside/behind the enemy, so the supported Herobrine can re-approach from another side
- Greg sets `activeSupportRetreatPos` and runs to it while playing the one-hand support portal animation

The shared combat escape flow in `CombatCommon.performEscapeRunAway` now also asks a linked Greg to try `tryOpenRetreatPortalFor`, so retreating Herobrines can get a Greg portal without waiting for Greg's normal periodic support tick.

## Combat Low Clone Support

Greg has a separate `lowCloneSupportCooldown` for combat low-clone support.

Low-clone support cooldown behavior:

- `LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS = 180 * 20`
- `LOW_CLONE_SUPPORT_COOLDOWN_MAX_TICKS = 300 * 20`
- if clone support fails, Greg retries after `LOW_CLONE_SUPPORT_RETRY_TICKS = 10 * 20`

When Greg is in combat mode, finished with his main summon timing (`summonTiming == -2`), not summoning, not escaping, has a ground supported Herobrine, and that supported Herobrine is less than 10 blocks from its enemy, `summonCombatLowCloneSupport` can spawn 1 to 3 support clones.

The support clones are randomly `LowHerobrineCloneEntity` or `LowShadowHerobrineCloneEntity`. They are marked summoned, get `renderPortal = false`, are given gear through Greg's existing `equipGearForLowHerobrineClone`, and target the supported Herobrine's close enemy. They are spawned by a separate helper instead of `summonHerobrine`, so they do not overwrite Greg's first/second/third main summoned-Herobrine slots or block his escape condition.

Clone placement samples nearby surface positions around Greg's support anchor, checks loaded/world-border/empty-space/ground, then checks entity collision before adding the clone. Successful clone support plays Greg's one-hand top casting support animation through `HerobrinePortalCombatUtil.playPortalPairSummon`.

Greg's tick only restores UUID-backed references for Herobrines in his own first/second/third summoned slots. A Greg spawned by egg does not automatically bind arbitrary nearby Herobrines into those slots. Portal support and support-orbit movement still work with separately spawned nearby Herobrines because the portal support helpers search nearby Herobrine-side entities directly.
