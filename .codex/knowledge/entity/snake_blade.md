# Demoniac Voltage Reaver And Snake Blade Session Knowledge

This file records the session knowledge for the Demoniac Voltage Reaver, `SnakeBladeEntity`, `process()`, `processGuard()`, snake blade rendering, and the portal-chain integration.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. The relevant implementation files are:

- `src/main/java/com/pla/annoyingvillagers/item/DemoniacVoltageReaverItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SnakeBladeEntity.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/SnakeBladeRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/entity/PortalEntity.java`
- `src/main/java/com/pla/annoyingvillagers/item/TransporterFragmentItem.java`

## DemoniacVoltageReaverItem Target Selection

`DemoniacVoltageReaverItem` defines two search radii:

- `TARGET_SEARCH_RADIUS = 16.0D`
- `PORTAL_TARGET_SEARCH_RADIUS = 64.0D`

`checkNearbyTarget(LivingEntity attacker)` searches normal living targets in a 16 block inflated bounding box and returns true if either a valid living target exists or `findClosestPortalTarget(attacker)` finds an owned or ownerless portal.

The living target filter rejects:

- the attacker itself
- allied entities in either direction
- spectators
- creative players
- non-`Mob` and non-`Player` entities
- entities without attacker line of sight

`findClosestPortalTarget(LivingEntity attacker)` searches `PortalEntity` within 64 blocks. It skips removed portals and skips portals owned by another UUID. It accepts linked and unlinked portals. Distance is primary priority. Ties prefer starter portals, then lower portal order.

## DemoniacVoltageReaverItem process()

`process(ItemStack stack, LivingEntity attacker)` now starts by selecting the closest usable portal with `findClosestPortalTarget(attacker)`. Only when no portal is found does it scan nearby living targets.

This means the snake blade special attack prioritizes the nearest owned or ownerless portal over the nearest entity. The intended behavior is that the blade first attacks or enters the nearest portal, then continues through the portal chain to attack entities near exits or move to the next portal.

After selecting a target, `process()` calls `launchSnakeBladeAt(attacker, closestValid, stack)`.

`launchSnakeBladeAt(LivingEntity attacker, Entity closestValid, ItemStack stack)` checks the snake blade capability and only launches if `canLaunchSnakeBlades(level, attacker)` allows it. It retracts the old far fragment, creates a `SnakeBladeEntity` on the server, copies enchantment state from the item foil, sets the creator UUID, sets `from` to the attacker entity id, sets `to` to the selected target entity id, starts progress at `0.0F`, and stores the spawned snake blade as the last fragment capability.

## DemoniacVoltageReaverItem processGuard()

`processGuard(ItemStack stack, LivingEntity entityToGuard)` is the guard-mode entry point. It obtains the snake blade capability, checks `canLaunchSnakeBlades(level, entityToGuard)`, retracts the far fragment, and server-side launches the guard overload `launchSnakeBladeAt(entityToGuard, stack)`.

The guard overload creates a `SnakeBladeEntity` with target id `-1`, progress `0.0F`, and guard direction `"forward_left"`. It positions the entity at `guardTargetFor(attacker, "forward_left")`.

Guard target positions use local offsets around the guarded entity:

- `"forward_left"` uses `(left=1, up=0, forward=-1)`
- `"forward_right"` uses `(left=2, up=1, forward=1)`
- `"backward_right"` uses `(left=-1, up=0, forward=2)`
- the fallback direction uses `(left=-1, up=2, forward=-1)`

Portal-chain behavior is intentionally skipped for guard chains. In `SnakeBladeEntity.handleChaining()`, guard mode is checked first with `guardDirection != null`; it creates the next guard segment and returns before portal-chain logic can run. This prevents the portal changes from expanding `processGuard()` beyond the legacy guard chain shape.

## Snake Blade Capability State

`DemoniacVoltageReaverItem.setLastFragment` writes the holder capability:

- `hasSnakeBlade`
- last snake blade entity id
- last snake blade UUID

`canLaunchSnakeBlades` allows launch only when there is no live last fragment. `retractFarFragments` removes the last fragment and clears the capability. `getLastFragment` resolves by UUID on the server, falls back to id, and on the client uses id.

## SnakeBladeEntity Synced Data

`SnakeBladeEntity` synchronizes:

- creator UUID
- active portal group UUID
- `from` entity id
- render-from entity id
- last portal order
- target count
- current target id
- progress
- base damage
- retracting flag
- has-blade flag
- enchanted flag
- guard flag

Important constants:

- `MAX_EXTEND_TIME = 5.0F`
- `MAX_PORTAL_CHAIN_TARGETS = 24`
- `MAX_NORMAL_CHAIN_TARGETS = 5`
- `MAX_GUARD_CHAIN_TARGETS = 5`
- `POST_HIT_CHAIN_DELAY_TICKS = 3`
- `PORTAL_CHAIN_SEARCH_RADIUS = 64.0D`

The local runtime state includes `previouslyTouched`, `hasChained`, `attemptedCurrentTargetHit`, `postHitChainDelayTicks`, `prevProgress`, and `guardDirection`.

## SnakeBladeEntity Tick Flow

At the start of `tick()`, if the creator is a living entity but no longer holds a `DemoniacVoltageReaverItem` in the main hand, the snake blade discards itself.

Each tick then:

- spawns the elite effect at the snake blade position
- stores previous progress in `prevProgress`
- calls `super.tick()`
- server-side, if guard mode and every 5 ticks, applies guard area damage with `tickGuardAoe`
- updates progress and handles full retraction
- updates movement and attack behavior
- server-side handles chaining
- applies velocity

## SnakeBladeEntity Progress And Retraction

When not retracting and progress is below `MAX_EXTEND_TIME`, progress increases by `1.0F` per tick. When retracting and progress is above zero, progress decreases by `1.0F` per tick.

When retracting reaches progress zero, `onFullyRetracted` runs. If the `from` entity is another `SnakeBladeEntity`, the parent is set to retract and the parent becomes the last fragment. Otherwise the last fragment is cleared, the `SnakeAnimation` tag is removed from the creator's Demoniac Voltage Reaver item, and if the creator is playing `AVAnimations.SNAKE_BLADE` or `AVAnimations.SNAKE_BLADE_GUARD`, it plays `AVAnimations.IDLE_BREAK`. The fully retracted entity is then removed.

The post-hit delay exists so a normal no-portal hit does not chain or discard so quickly that the return animation disappears.

## SnakeBladeEntity Movement And Attack

`targetCenter(Entity entity)` is used for movement and child spawn positions:

- for `PortalEntity`, it returns `portal.getPortalCenter()`
- for other entities, it returns the body midpoint: entity x/z and `entity.getY() + entity.getBbHeight() * 0.5D`

These center points are a legacy behavior invariant. Do not replace them with `PortalEntity.getSnakeBladeAnchor()` and do not subtract a generic `1.0D` Y offset from living targets. Doing that pushes normal target rays toward/below the floor, which can break long-distance chain LOS checks and make a blade reach portal A but fail to find the continuation from linked portal B.

`PortalEntity` legacy geometry is `WIDTH = 2.2F` and `HEIGHT = 3.0F`. Do not reduce the live portal height to `2.2F`: `getPortalCenter()` and the Snake Blade portal-chain origin depend on the entity height, so shortening the portal shifts chain geometry downward and can make portal-exit LOS/continuation fail.

Do not "fix" Snake Blade range by changing the established search radii. The compatibility values remain 16 blocks for the initial normal living-target scan, 64 blocks for portal targeting/search, 14 blocks for a living target after a portal exit, and 12 blocks for the ordinary next living-target chain. Geometry/LOS regressions should be fixed at the center/origin calculation instead.

`updateMovementAndAttack` moves toward the current target center, or toward the active guard target if there is no target. Delta movement is set to half of the vector from current position to the target position.

Portals are not damaged. If the target is not a portal, the code is server-side, and progress is at max extension, `tryAttackTarget` can damage the target.

`tryAttackTarget` ignores the creator and ignores portals. On successful damage, it marks the target touched, applies the post-hit chain delay, adds 5 skill resource to the creator's Demoniac Voltage Reaver skill, emits hit particles and sound, deals stamina damage, and knocks back living targets.

## SnakeBladeEntity Guard Damage

Guard mode applies area damage every 5 ticks with radius about 2 blocks. It excludes the owner and allies. It does half base damage, deals stamina damage, adds 3 skill resource to the creator, and knocks targets back.

## SnakeBladeEntity Chaining Overview

`handleChaining(Entity creator)` is server-side chain control. It returns early if this snake blade has already chained.

The max chain count depends on mode:

- guard chain: `MAX_GUARD_CHAIN_TARGETS`
- portal chain mode: `MAX_PORTAL_CHAIN_TARGETS`
- normal chain: `MAX_NORMAL_CHAIN_TARGETS`

Guard chains run before portal logic. They compute the next guard direction, create a guard child, set `hasChained = true`, and return.

For non-guard chains:

- if the current target is a living target and post-hit delay is active, the delay is decremented and chaining waits
- if the current target is a portal, `createChainThroughPortal` is attempted
- otherwise the blade searches the next ordered portal in the active portal group
- otherwise it searches the closest usable portal
- otherwise it searches a nearby valid living target within 12 blocks
- otherwise it retracts

## Portal Chain Mode

Portal chain mode is active if:

- the active portal group UUID is set
- the current target is a `PortalEntity`
- any previously touched entity is a `PortalEntity`

Portal mode raises the target limit to `MAX_PORTAL_CHAIN_TARGETS = 24`, allowing a snake blade to pass through all summoned portals rather than stopping at the normal small target count.

## createChainThroughPortal

When the current target is a portal, `createChainThroughPortal(LivingEntity livingCreator, PortalEntity entrancePortal)` runs.

It asks the entrance for its linked portal. If a linked exit exists and is not removed, the linked portal becomes the chain origin. If the portal is unlinked, the entrance itself becomes the chain origin. This supports single unlinked portals and odd portal counts by making them pass-through chain points for snake blade logic rather than hard failures.

The entrance portal is marked touched. A linked exit portal is also marked touched.

From the chain origin center, the code tries in order:

1. closest valid living target within 14 blocks
2. next ordered portal in the same portal group after the max order of entrance and exit
3. closest usable portal
4. fail and let the caller retract

When a chain is created from an exit portal, the child blade uses the exit portal as its render origin so the rendered chain visually comes out of the portal center.

## Ordered And Closest Portal Search

`findNextOrderedPortal` requires an active portal group UUID, skips touched or removed portals, requires the same group, requires portal order greater than the last portal order, and skips portals owned by a different UUID. It allows both linked and unlinked portals.

`findClosestUsablePortal` skips the excluded portal, touched portals, removed portals, and portals owned by a different UUID. It also allows both linked and unlinked portals.

The touched list is used so the snake blade does not attack or enter the same portal again. This prevents bouncing between `1 <-> 2`, or reusing an already attacked portal.

## Chain Creation Methods

`createChain(Entity nextTarget)` creates a child from the current snake blade to a normal target. The parent hides its blade head, the child copies enchantment, touched targets, and portal-chain state, marks the next target touched, uses the current snake blade as `from`, sets the child target id, places the child at the target center, increments targets hit, updates the last fragment, and spawns it.

`createChainToPortal(PortalEntity nextPortal)` is the portal-target version. It initializes portal-chain state if needed from the target portal group and order. The child is placed at the portal center.

`createChainFromPortalExit(PortalEntity exitPortal, Entity nextTarget)` creates the child after passing through a portal. It uses the current snake blade as `from`, sets render-from to the exit portal id, sets the target id to the next target, places the child at the next target center, updates portal group and last portal order from the exit portal, marks the exit and next target touched, updates the last fragment, and spawns it.

`createChainGuard(String nextDirection)` creates the next guard child, copies enchantment and touched state, sets target id `-1`, sets the next guard direction, places the child at the next guard target, updates the last fragment, and spawns it.

## SnakeBladeRenderer

`SnakeBladeRenderer` uses:

- `textures/entities/snake_blade.png`
- `textures/entities/fragment_chain.png`
- `MAX_NECK_SEGMENTS = 128`
- `FRAGMENT_LENGTH = 0.45F`
- `HEAD_CLEAR = 0.35F`

`shouldRender` also checks the bounding box union of the snake blade and its render-from entity, so long portal chains can remain visible.

In `render`, the renderer gets `fromEntity = snakeBladeEntity.getRenderFromEntity()`. If missing, it returns. It interpolates the snake blade position, computes progress from `prevProgress` and current progress, and uses `DemoniacVoltageReaverItem.getToolTipPos` to start the chain at the weapon tooltip when possible.

If the tooltip position is unavailable, it uses `getPositionOfPriorMob`. That method returns `portal.getPortalCenter()` when the prior entity is a `PortalEntity`, which is the rendering hook that makes a child chain emerge from the portal center after portal travel.

The non-EpicFight `DemoniacVoltageReaverItem.getToolTipPos(...)` fallback must return `null` for non-`LivingEntity` origins such as a `PortalEntity` or prior `SnakeBladeEntity`. The old Epic Fight joint lookup naturally returned no tooltip for those entities. Returning a generic body position for every Entity bypasses `getPositionOfPriorMob` and changes the legacy portal/chain rendering path.

Guard chains render straighter. Non-guard chains render with a deterministic wavy path based on snake blade id, from id, and target id. The renderer draws chain fragment cubes, then draws the blade head when `hasBlade()` or `isRetracting()` is true.

## Portal Interaction Summary

The portal entity handles real entity teleportation for players, mobs, living entities, and projectiles. `SnakeBladeEntity` is deliberately excluded from normal portal collision teleportation. Instead, snake blade portal travel is implemented as chaining logic:

- Demoniac Voltage Reaver `process()` targets the closest valid portal first
- the snake blade moves to the portal center
- it marks portals touched
- it creates a new child segment from the linked exit portal or from the same unlinked portal
- it attacks nearby valid entities after exiting
- if no entity exists, it advances to the next ordered portal or closest unused portal
- once no valid portal or target remains, it retracts through the parent chain

This is how the session implemented the requested behavior of `1 <-> 2`, `3 <-> 4`, `5 <-> 6`, while also supporting unlinked single portals and preventing repeated portal attacks.
