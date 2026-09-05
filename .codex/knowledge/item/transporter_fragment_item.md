# Portal Entity And Transporter Fragment Session Knowledge

This file records the session knowledge for `PortalEntity`, portal rendering and effects, and the `transporter_fragment` item.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. The relevant implementation files are:

- `src/main/java/com/pla/annoyingvillagers/entity/PortalEntity.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/PortalEntityRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/item/TransporterFragmentItem.java`
- `src/main/java/com/pla/annoyingvillagers/event/SpecialAttackOnKeyPressedEvent.java`
- `src/main/java/com/pla/annoyingvillagers/gameasset/AnimsSculkSteve.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/client/PatchedItemInHandLayerMixin.java`
- `src/main/java/com/pla/annoyingvillagers/util/AAAParticlesUtil.java`
- `src/main/java/com/pla/annoyingvillagers/network/ClientboundTeleportPortalFx.java`
- `src/main/java/com/pla/annoyingvillagers/compat/aaa_particles/emitterinfo/TeleportPortalParticleEmitterInfo.java`

## PortalEntity Contract

`PortalEntity` is a no-physics, no-gravity entity used as the functional teleport portal. It does not need see-through rendering. It can use `textures/entities/portal.png` as a fallback visual.

Important constants:

- `WIDTH = 2.2F`
- `HEIGHT = 3.0F`
- `LIFETIME_TICKS = 20 * 10`
- `PORTAL_COOLDOWN_TAG = "AnnoyingVillagersPortalCooldown"`
- `TELEPORT_COOLDOWN_TICKS = 8`

The lifetime constant is intentionally centralized so the portal duration is easy to change.

Synced portal data:

- linked portal UUID
- owner UUID
- portal group UUID
- portal order
- starter portal flag

`tick()` keeps no physics and no gravity enabled, zeros delta movement, discards the portal server-side after `LIFETIME_TICKS`, and server-side calls `teleportIntersectingEntities()`.

## PortalEntity Teleport Behavior

`teleportIntersectingEntities()` does nothing if the portal has no linked portal or the linked portal is removed. This means entities simply pass through unlinked portals because the portal has no collision.

The teleport search checks entities intersecting the portal teleport box inflated by `0.35D`. It supports players, mobs, living entities, projectiles, and other normal teleportable entities, but rejects:

- `PortalEntity`
- `SnakeBladeEntity`
- `HerobrineDragonEntity`
- removed entities
- dead entities
- passengers
- spectator players
- entities still under the portal cooldown tag

After these base checks, `PortalEntity.canTeleportByOwnerRule` applies owner-based filtering:

- ownerless portals keep the previous permissive behavior
- Herobrine-side owned portals (`HerobrineMob`, `HerobrineGregEntity`, `LowHerobrineCloneEntity`, `LowShadowHerobrineCloneEntity`, `NullWeapon`) only teleport the owner entity itself, Herobrine-side entities, and projectiles
- player-owned portals reject Herobrine-side entities and `Monster` entities, while still allowing players, villagers, animals, projectiles, and other non-monster/non-Herobrine entities

Intersection uses three checks:

- current entity bounding box intersects the portal box
- swept previous-to-current bounding box intersects the portal box
- a center-line clip from previous to current position hits the portal box

The cooldown is stored in the entity persistent data under `AnnoyingVillagersPortalCooldown`, using world game time plus `TELEPORT_COOLDOWN_TICKS`.

## PortalEntity Exit Position And Motion

Teleporting preserves side relative to the source portal normal. `findExitPosition` computes an offset in front of the linked portal and tries several offsets, then tries small vertical offsets, then falls back to a position in front of the linked portal.

The exit Y keeps the entity's relative Y inside the source portal, clamped to fit the destination portal height.

`transformMotion` maps motion from the source portal normal/right basis into the linked portal normal/right basis. It preserves vertical motion and provides a minimum forward push if transformed motion is too small.

After teleporting, the entity:

- teleports to the exit position
- moves with zero self movement to refresh collision
- receives the transformed delta movement
- has fall distance reset
- has yaw adjusted by `linkedYaw - sourceYaw`
- if living, also has head yaw and body yaw adjusted

Both portals play enderman teleport sounds. The server sends `ClientboundTeleportPortalFx` for the source and destination portal.

## PortalEntity Geometry Helpers

`getPortalCenter()` returns the entity position plus half its bounding height.

`getNormal()` converts portal yaw into a horizontal normal using `(-sin(yaw), 0, cos(yaw)).normalize()`.

The entity is not pickable, is not attackable, and `hurt` always returns false.

## Portal Rendering

`PortalEntityRenderer` uses fallback texture `textures/entities/portal.png`.

If Photon is enabled for `TELEPORT_PORTAL`, the renderer uses Photon effect `snakeportal` through `PhotonClientFxUtil.followPortal`. The Photon portal effect is keyed by portal entity id, follows the portal center, and updates its rotation so the effect's authored local `+Z` faces `PortalEntity.getNormal()`.

If AAA particles are enabled for `TELEPORT_PORTAL` and `AAAParticlesUtil.sendTeleportPortal` succeeds, the renderer skips the textured quad and uses the AAA effect. The AAA visual is refreshed every `AAA_PORTAL_REFRESH_TICKS = 10` ticks per portal id.

If Photon is unavailable or fails, the renderer can fall back to AAA particles. If Photon and AAA particles are unavailable, disabled, or fail to spawn, the fallback renderer draws a double-sided translucent quad using `PortalEntity.WIDTH` and `PortalEntity.HEIGHT` at full bright light.

`AnnoyingVillagersClientConfig.VfxEffect.TELEPORT_PORTAL` is configured under `teleportPortal`, displays as `Teleport Portal (Photon: snakeportal)`, and supports `DEFAULT`, `PHOTON`, `AAA_PARTICLE`, and `VANILLA`.

## Teleport Portal AAA Effect

`ClientboundTeleportPortalFx` carries a portal center position and normal vector from server to client. Its handler calls `ClientPacketHandlers.handleTeleportPortalFx`, which routes `VfxEffect.TELEPORT_PORTAL` through `ClientVfxRouter` and calls `AAAParticlesUtil.sendTeleportPortal(level, msg.pos(), msg.normal())`.

`AAAParticlesUtil.sendTeleportPortal(Level level, Vec3 pos, Vec3 normal)` returns false when the level or position is missing or the level is not client-side. On success, it logs `[AV MOD DEBUG] sendTeleportPortal called from aaa particle`, creates a `TeleportPortalParticleEmitterInfo` for `annoyingvillagers:teleport_portal`, orients it with `atPortal(pos, normal, ForwardAxis.PLUS_Z, 0.0F)`, and spawns it in the client world.

`TeleportPortalParticleEmitterInfo` stores position, normal, forward axis, and roll. It normalizes the direction, loads the Effekseer effect from the AAA registry, sets emitter position, and rotates the effect so the chosen forward axis faces the portal normal.

## Teleport Portal Photon Effect

`ClientPacketHandlers.handleTeleportPortalFx` routes Photon first for `VfxEffect.TELEPORT_PORTAL` and calls `PhotonClientFxUtil.spawnPortal(level, "snakeportal", msg.pos(), msg.normal())`.

`PhotonClientFxUtil.spawnPortal` and `PhotonClientFxUtil.followPortal` normalize the supplied normal and rotate the Photon effect so authored local `+Z` faces that normal. The rotation math matches the AAA portal helper:

- yaw: `atan2(normal.x, normal.z)`
- pitch: `-atan2(normal.y, sqrt(normal.x * normal.x + normal.z * normal.z))`
- roll: `0`

This matches the vanilla portal renderer's yaw basis because `PortalEntity.getNormal()` is derived from the portal yaw as `(-sin(yaw), 0, cos(yaw))`.

## TransporterFragmentItem Identity

The portal summon item was renamed to `TransporterFragmentItem`.

Registry and resource facts:

- item registry field: `AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT`
- item id: `transporter_fragment`
- item class: `com.pla.annoyingvillagers.item.TransporterFragmentItem`
- language key: `item.annoyingvillagers.transporter_fragment = "Transporter Fragment"`
- model file: `src/main/resources/assets/annoyingvillagers/models/item/transporter_fragment.json`
- item properties: stack size 1, durability 300, fire resistant, epic rarity

## TransporterFragmentItem Constants

Current constants:

- `PORTAL_COUNT = 6`
- `MAX_ACTIVE_PORTALS_PER_OWNER = 6`
- `MAX_DURABILITY = 300`
- `SAVED_TELEPORT_DURABILITY_COST = 10`
- `SAVED_TELEPORT_SINK_TICKS = HerobrinePortalUtil.SHINK_TIME_START`
- `LOOK_PORTAL_RANGE = 32.0D`
- `HORIZONTAL_SEARCH_RADIUS = 30`
- `VERTICAL_SEARCH_RADIUS = 15`
- `TARGET_PRIORITY_RADIUS = 16`
- `MIN_PORTAL_GAP = 3.0D`
- `MAX_PORTAL_GAP = 6.0D`
- `TARGET_CLUSTER_DISTANCE = 8.0D`
- `CASTER_PORTAL_MIN_DISTANCE = 3.0D`
- `CASTER_PORTAL_MAX_DISTANCE = 5.0D`
- `COOLDOWN_TICKS = 20`

The session changed the random portal count behavior so six-portal summoning always requests 6 portals instead of randomly choosing 4 or 6.

Every spawned portal costs 1 durability. Saved-location teleport costs 10 durability. If the active transporter fragment does not have enough remaining durability for the requested action, the special attack input is consumed but activation fails, so no animation plays.

## Mob AI Linked Pair API

`TransporterFragmentItem.spawnLinkedPortalPair(Level level, LivingEntity caster, Vec3 firstPreferredPos, Vec3 secondPreferredPos)` is a public static helper added for Herobrine combat AI.

It:

- only runs on `ServerLevel`
- reads the caster's active owned portals
- enforces `MAX_ACTIVE_PORTALS_PER_OWNER`
- resolves both preferred positions through the same look-portal placement search
- selects a portal group from existing active portals when possible
- uses the next portal order after active owned portals
- spawns exactly one linked pair with the existing pair-spawn logic

Current Greg/Transporter support goals call this pair API through `HerobrineUtil`. The support utility computes safe entrance/exit positions for dangerous-reaction escape, projectile counter, approach, and self escape, then asks `TransporterFragmentItem` to create the linked pair.

`TransporterFragmentItem.canSpawnOwnedPortals(ServerLevel level, LivingEntity caster, int portalCount)` exposes the active-owner cap check used before portal actions. Normal support actions reserve 2 slots. Linked Greg six-portal support reserves 6 slots and uses `spawnPortalPairsBatch(serverLevel, greg, swordsman)`. The batch is now initiated by `HerobrineGregSixPortalSupportGoal`: Greg starts `PORTAL_SUMMON`, waits until animation tick 20, then calls the batch spawn helper. The Swordsman's ULT hook no longer asks for the batch directly.

## Transporter Special Attack Flow

`TransporterFragmentItem.tryUseSpecialAttack(Player player)` returns a `UseResult` with:

- whether the special attack input was consumed
- whether the item actually activated
- use mode: none, main hand, off hand, or both hands

Use mode is based on whether the player has `TRANSPORTER_FRAGMENT` in main hand, off hand, or both.

If the player has no transporter fragment, the result is missed. If the item is on cooldown, the result is consumed but not activated.

Server-side, the item finds active owned portals. It requests 6 portals when the fragment is in the main hand or both hands, and 1 portal when the fragment is only in the off hand. If `activePortals.size() + requestedPortals > MAX_ACTIVE_PORTALS_PER_OWNER`, the result is consumed but not activated. This blocks the special attack without cooldown and without animation.

When activation succeeds:

- main hand or both hands call `spawnPortalPairs(serverLevel, player)`
- off hand calls `spawnLookPortal(serverLevel, player, activePortals)`
- if at least one portal is spawned, the item receives `COOLDOWN_TICKS`
- the active item stack loses 1 durability per portal spawned

`TransporterFragmentItem.tryUseHeldSpecialAttack(Player player)` is separate from pressed special attack. It only works from the main hand and starts the saved-location teleport flow.

## SpecialAttackOnKeyPressedEvent Integration

`SpecialAttackOnKeyPressedEvent.execute` checks transporter fragment handling server-side before falling through to the normal special attack item/category logic.

If `tryUseSpecialAttack` returns consumed:

- if activated, it plays the transporter animation for the use mode
- it returns immediately

If the max active portal cap blocks activation, no animation is played because `activated()` is false.

Animation mapping:

- both hands: `AnimsSculkSteve.PORTAL_SUMMON`
- off hand only: `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP`
- main hand only: `AnimsSculkSteve.PORTAL_SUMMON`

## SpecialAttackOnKeyHeldEvent Integration

`SpecialAttackOnKeyHeldEvent.execute` checks `TransporterFragmentItem.tryUseHeldSpecialAttack(player)` before the normal held-special logic.

If the result is consumed:

- if activated, it plays `AnimsSculkSteve.PORTAL_SUMMON`
- it returns immediately

Held special activation fails without animation when the main-hand fragment is on cooldown, lacks a saved location, lacks 10 durability, the saved location belongs to another dimension, the saved target is outside the world border, or the player is already in Herobrine rising/sinking transporter state.

## Saved Location Flow

Main-hand right click with `transporter_fragment` stores a saved location in item NBT under `TransporterSavedLocation`.

Saved NBT fields:

- `X`
- `Y`
- `Z`
- `Dimension`

Main-hand shift-right-click clears the saved location.

Right-click behavior:

- `useOn` stores the adjacent block position from the clicked block face
- `interactLivingEntity` stores the clicked living entity position
- air/right-click use raycasts up to `LOOK_PORTAL_RANGE = 32.0D` and stores the snapped look target

The tooltip shows `Saved Location`, `Saved pos: x y z`, and the saved dimension. If no location is saved, it shows `Saved Location: none`.

## Saved Teleport Flow

Holding special attack with a main-hand transporter fragment and a saved location starts a player version of the Herobrine ground escape flow.

The start step:

- records the caster origin and saved target on the player's persistent data
- captures the caster plus all alive non-spectator entities within `5.0D` blocks, storing their UUIDs and offsets from the caster
- sends `ClientboundHerobrinePortalFx` at the caster feet
- plays `PORTAL_NATURAL`
- calls `HerobrinePortalUtil.sinkIntoGround(serverLevel, livingEntity, 0.06D)` for every captured living entity, not only the caster
- damages the main-hand transporter fragment by 10

`RiseFromGroundEvent` watches for `TransporterFragmentItem.NBT_SAVED_TELEPORT_PENDING` while the entity is sinking. When the sink ticks reach `TransporterFragmentItem.SAVED_TELEPORT_SINK_TICKS`, it calls `TransporterFragmentItem.finishPendingSavedTeleport(entity)`.

The finish step:

- sends `ClientboundHerobrinePortalFx` at the saved target
- plays `PORTAL_NATURAL`
- teleports each captured entity to `saved target + captured offset`
- clears the caster's sink state
- starts `HerobrinePortalUtil.spawnRising(serverLevel, livingEntity, destination.x, destination.z, 0.06D)` for every captured living entity
- clears the transporter pending teleport NBT from the caster

Captured living entities are no-physics, no-gravity, and invulnerable during the sink/rise window because those flags are applied by `HerobrinePortalUtil.sinkIntoGround` and `HerobrinePortalUtil.spawnRising`, then cleared by `RiseFromGroundEvent.finishRise`.

Player sink/rise uses `HerobrinePortalUtil.moveTransitionEntity`, which calls `ServerPlayer.teleportTo(x, y, z)` for players and `setPos` for other living entities. `RiseFromGroundEvent` uses this helper for every per-tick sinking and rising movement, and `spawnRising` uses it for the initial below-ground rise position. This keeps player clients synced during the ground sink/rise animation instead of relying on server-only `setPos` updates that can be overwritten by player movement packets.

## PORTAL_SUMMON Animation Lock

`AnimsSculkSteve.PORTAL_SUMMON` is registered as `biped/sculk_steve/portal_summon`.

Its animation state blocks movement and cancel behavior:

- `EntityState.MOVEMENT_LOCKED = true`
- `EntityState.CAN_BASIC_ATTACK = false`
- `EntityState.CAN_SKILL_EXECUTION = false`
- `AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE = false`

This means the portal summon animation blocks movement and cannot be canceled by moving.

## Off-Hand Portal Flow

Off-hand pressed special attack no longer calls the random six-portal summon. It spawns exactly one portal toward what the player is looking at.

`findLookPortalTarget` casts from the player's eye to `LOOK_PORTAL_RANGE = 32.0D`.

Target choice:

- if a pickable entity is hit before or at the block hit distance, the portal target is that entity's base position and faces the entity center
- if a block is hit, the portal spawns at the adjacent block in the hit direction and faces the player's eye position
- if nothing is hit, the portal spawns at the max ray position and faces the player's eye position

`findLookPortalPosition` snaps the preferred position to block center/floor Y. It uses the exact snapped position if valid, otherwise searches offsets with radius 1 to 3 and Y offsets -1 to 2.

## Off-Hand Linking Rules

`spawnLookPortal` supports progressive off-hand linking:

- the first off-hand portal is unlinked
- the second off-hand portal links to the first pending unlinked portal
- the third off-hand portal starts a new unlinked pair
- the fourth links to the third
- this continues while respecting the max active portal cap

The group UUID is selected from the pending portal if possible, otherwise from the latest active portal group, otherwise a new UUID is generated.

The next portal order is one greater than the highest active owned portal order.

`linkPortalPair` links both portal UUIDs bidirectionally, sets the group UUID on both, and turns both portals to face each other.

## Six-Portal Pair Flow

`spawnPortalPairs(Level level, LivingEntity caster)` is kept as a public static method for future use.

It enforces the per-owner max active portal cap server-side. It attempts to build exactly 6 portal positions. It creates one portal group UUID and spawns pairs by order:

- order 0 links to order 1
- order 2 links to order 3
- order 4 links to order 5

If an odd leftover position exists, it can spawn one unlinked portal. This supports robustness for future odd-count cases.

The first portal is marked as the starter portal when order is 0. Paired portals face each other.

## Portal Distribution

`buildPortalPositions` always creates one portal near the caster first. This guarantees the snake blade has a nearby starter portal to attack first.

Priority targets are found with a filter matching the user-provided logic:

- living entity
- not the caster
- not allied both ways
- not spectator
- not creative player
- `Mob` or `Player`
- caster has line of sight

Targets are sorted by distance and clustered with `TARGET_CLUSTER_DISTANCE = 8.0D`, so two close entities generally receive one nearby portal cluster instead of multiple portals stacked around them.

After the caster portal, odd slot indexes are treated as exit slots. If clustered priority targets exist, those slots try to place portals near targets at a distance from `MIN_PORTAL_GAP` to `MAX_PORTAL_GAP`. If no target placement is available, the code falls back to random distribution.

Random distribution uses distance tiers so portals spread in all directions and at mixed distances instead of forming paired clumps on one side:

- near: about 5 to 11 blocks
- mid: about 12 to 19 blocks
- far: about 20 to 29 blocks
- late fallback: about 4 to 29 blocks

Y placement is clamped from caster floor Y up to `VERTICAL_SEARCH_RADIUS = 15`, avoiding underground placement relative to the caster.

## Portal Position Validation

Random portal positions must:

- be within caster Y to caster Y + 15
- be within 30 blocks horizontally in X and Z from the caster
- be inside the world border
- be at least `MIN_PORTAL_GAP` away from existing chosen portal positions
- pass `isAreaClear`

`isAreaClear` rejects positions outside build height, positions with collision in the portal bounding box, and positions where any checked block or fluid in a 2 block radius and portal height is not empty air.

Look portal positions use `isLookPortalPositionValid`, which checks world border and `isAreaClear`.

## Unlinked Portal Behavior

For normal entities, an unlinked portal does not teleport anything because `PortalEntity.teleportIntersectingEntities` returns when there is no linked portal. Since the portal has no collision, entities can simply walk through it.

`PortalEntity.canTeleportEntity` also rejects `HerobrineDragonEntity`, and already rejects passengers. This prevents Herobrine dragons and dragon-mounted riders from being physically teleported by portal collision.

For snake blade logic, unlinked portals are still valid targets. `SnakeBladeEntity.createChainThroughPortal` treats an unlinked entrance as the chain origin, allowing the blade to keep moving to nearby targets or the next portal instead of failing immediately.

## Offhand EpicFight Rendering

`PatchedItemInHandLayerMixin` wraps EpicFight offhand item validation. If EpicFight already considers the offhand item valid, it returns true. Otherwise, it forces rendering for utility items including:

- fishing rod grapple items
- hook gun
- transporter fragment
- buckets

The transporter fragment check is `livingEntity.getOffhandItem().is(AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT.get())`.

This makes the transporter fragment render in the off hand similarly to bucket and fishing rod special cases.

## Relationship To Snake Blade

The transporter fragment creates owned portals with portal group, order, linked UUIDs, and starter flag. Demoniac Voltage Reaver `process()` then targets the nearest valid owned or ownerless portal first.

For six-portal main-hand or both-hand summons, the intended path is:

- snake blade attacks the starter portal near the caster first
- if linked, it exits from the paired portal
- it attacks a valid entity near that exit if one exists
- if no entity exists, it advances to the next ordered portal
- it continues through later pairs such as `3 <-> 4` and `5 <-> 6`
- touched portals prevent reusing portals already attacked or entered

For off-hand look portals, pairs are built over multiple activations. A single unlinked portal is harmless for normal teleportation and is still usable as a snake blade chain point.
