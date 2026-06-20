# Tony The Fishing Rod Session Knowledge

This file records session knowledge for `tony_the_fishing_rod`, its sticky hook behavior, item projectile payloads, player flow, NPC flow, and relationship to Steve and Angry Steve.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/item/TonyTheFishingRod.java`
- `src/main/java/com/pla/annoyingvillagers/item/FishingRodGrappleUtil.java`
- `src/main/java/com/pla/annoyingvillagers/entity/ItemProjectile.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/ItemProjectileRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/FishingHookReturnMixin.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/GuardSkillMixin.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/client/FishingHookRendererMixin.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/client/PatchedItemInHandLayerMixin.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatCommon.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SteveEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/AngrySteveEntity.java`

## Item Identity

Tony The Fishing Rod is `AnnoyingVillagersModItems.TONY_THE_FISHING_ROD`.

`TonyTheFishingRod` extends vanilla `FishingRodItem`.

Item properties:

- stack size: 1
- durability: 1561

`inventoryTick` delegates to `FishingRodGrappleUtil.inventoryTick`.

`use` delegates to `FishingRodGrappleUtil.use(this, level, player, hand)`.

## Drop Source

Tony The Fishing Rod drops from Steve and Angry Steve.

Steve drops Tony The Fishing Rod as a damaged combat drop.

Angry Steve drops Tony The Fishing Rod as a damaged combat drop.

Villager Generals do not use or drop Tony as their combat rod; they use and drop Advanced Fishing Rod.

## Shared Custom Rod Flow

Tony uses the same base custom rod utility as Advanced Fishing Rod.

Custom hooks are vanilla `FishingHook` entities tagged with custom persistent data.

Important shared tags:

- `KEY_GRAPPLE_HOOK = "avGrappleFishingRod"`
- `KEY_RETURNING`

When a player has no active hook, Tony spawns a custom tagged fishing hook.

When a player has an active hook, Tony processes target/item/sticky behavior, damages the rod, and starts visible hook return.

If the hook is already returning, Tony can recast from the current hook return position instead of always restarting from the player.

## Tony Power Constants

Tony player plunge power:

- grounded: `4.1`
- airborne: `3.1`

Tony target pull power:

- grounded target: `2.0`
- airborne target: `1.5`
- distance scale: `0.35`

Tony return constants:

- return speed: `1.35`
- return arrival distance: `0.65`

Tony sticky living constants:

- push/pull distance cutoff: `2.0` blocks
- living entity stick chance minimum: `0.30`
- living entity stick chance maximum: `0.50`
- detached hook gravity: `0.03`

## Player Use Flow

When a Tony hook exists and the player right clicks:

- if sneaking, Tony handles item target pull/release behavior and disables normal plunge;
- if not sneaking, Tony first handles an existing sticky living target;
- if no sticky living target is handled, Tony handles hooked targets and item payloads;
- if no target/item path handles the click, Tony tries player plunge toward a latched block anchor;
- after that, Tony damages the stack and starts hook return.

This means Tony can pull targets, plunge the owner, carry sticky item projectiles, and maintain sticky living target state.

## Player Plunge To Hook Anchor

Tony can plunge the player toward a block anchor.

The plunge path requires:

- custom grapple hook tag,
- no hooked target,
- cooldown expired,
- hook not in water,
- hook latched to a stored anchor.

When plunge succeeds:

- player velocity is set toward the hook anchor,
- grounded players get upward boost,
- fall distance is reset,
- short Levitation is applied,
- grapple cooldown is applied.

Tony uses stronger player plunge power than Advanced Fishing Rod.

## Sticky Living Entity Behavior

Tony is the rod with sticky living entity behavior.

Sticky living target id is stored under:

- `KEY_STICKY_TARGET_ID = "avStickyTargetId"`

When Tony has a sticky living target and the player right clicks:

- if the target is within 2 blocks of the owner, Tony plunges the target away from the owner;
- if the target is farther than 2 blocks, Tony pulls the target toward the owner.

This distance was changed from 1 block to 2 blocks during the session.

After a sticky living target is pulled or pushed, Tony rolls whether the target stays stuck. The chance to stay stuck is 30 to 50 percent. If the roll fails, the sticky target id is cleared.

Only item entity/projectile stick is 100 percent. Living entities are not meant to stay stuck forever.

## Fix For Sticking Into Rod Owner

Tony sticky logic avoids treating the owner as the sticky living target.

The hook owner is rejected from target pull/sticky target paths. This was needed because a previous behavior could make the rod appear stuck into the player using the rod, then later teleport back to the old target and push it.

## Item Entity Stop And Conversion

Tony has special `ItemEntity` behavior.

When a Tony hook flies near an item entity, `stopHookAtHitItemEntity` can stop the hook at the item instead of letting it pass through and fall into the ground.

The stop search checks direct hit and swept path area using item entity search inflation.

When the player pulls a hooked `ItemEntity`, Tony converts it into an `ItemProjectile`:

- `convertItemEntityToProjectile(owner, hook, itemEntity)` creates an `ItemProjectile` hook payload,
- the original item entity is discarded,
- the new projectile is attached to the hook,
- the projectile id is stored under `KEY_STICKY_ITEM_PROJECTILE_ID`,
- `KEY_STICKY_TARGET_ID` is cleared.

Item entity stick is 100 percent for Tony. It is not controlled by the living entity sticky chance.

## Sticky Item Projectile Behavior

Sticky item projectile id is stored under:

- `KEY_STICKY_ITEM_PROJECTILE_ID = "avStickyItemProjectileId"`

While an item projectile is attached to Tony's hook:

- the projectile moves with the hook,
- the hook ignores new entity hits,
- vanilla hook target state is cleared if the hook catches something else,
- the item projectile should not turn into a normal item entity just because the hook hits an enemy,
- the hook should not switch from carrying the item projectile to hooking that enemy.

This fixes the incorrect behavior where a carried item projectile could hit an enemy, turn into an item entity near the owner, and make the hook attach to the enemy.

## Returning Or Losing Sticky Item Payloads

Tony's item payload can be collected or released depending on hook state.

When the hook fully returns to a player owner and collection is expected:

- the item projectile is given to the player's inventory if possible,
- leftovers are dropped if inventory insertion fails.

If the hook is removed, owner leaves, or legacy vanilla hook break flow removes the hook, `onGrappleHookRemoved` releases the sticky item projectile back as an item unless suppression is explicitly set.

For NPC combat hook payloads, cleanup discards the payload instead of dropping it, preventing farming of special NPC payload items such as Jessica The Dark Shield.

If owner is missing and the item is meant to be collected, the item is dropped as an item entity.

If the sticky hook is detached but should keep its payload in the world, Tony has detached sticky hook ticking with gravity.

## ItemProjectile Hook Payload

`ItemProjectile.createHookPayload` creates a no-physics/no-gravity projectile attached to a fishing hook.

While hook-attached:

- `moveWithHook` moves it to the hook,
- rotation updates from motion or rope context,
- server-side damage checks can hit entities along the projectile path,
- `tickHookAttached` drops or discards the projectile if its active hook controller is missing.

`giveToOwnerOrDrop` gives the item to a player inventory or drops it if it cannot be inserted. Non-player receivers drop the item.

## Item Projectile Rendering

`ItemProjectileRenderer` avoids spin for hook-attached item projectiles.

Important rendering behavior:

- hook-attached non-sharp items do not random spin,
- loose non-sharp projectiles can spin,
- sharp hook-attached items align by rope owner direction,
- shield payloads use owner-look yaw,
- block items attached to hooks avoid spin.

This is the session fix for item projectiles stuck by a hook spinning incorrectly.

## Damage From Sticked Item Projectile

Hook-attached item projectile movement can damage entities along its path.

NPC combat item-hit customization is exposed through:

- `CombatCommon.damageEnemyHitByNpcHookedFishingRodItem(Mob owner, LivingEntity target, ItemStack stuckItem)`

The current known damage values from that method are:

- Jessica The Dark Shield: 10 damage,
- other shield: 8 damage,
- default item: 4 damage.

Jessica The Dark Shield also interacts with Epic Fight stun/guard-break logic:

- if the target patch is not already stunned, long stun is applied,
- if the target is stunned, `AnimsPugilistSteve.GUARD_BREAK_ATTACK` plays,
- shield block sound feedback plays.

## NPC Tony Use

Steve and Angry Steve use Tony The Fishing Rod in NPC combat.

`CombatCommon.getNpcCombatFishingRodItem(mob)` returns Tony for:

- `SteveEntity`
- `AngrySteveEntity`

Steve state `0` is blocked from starting new Tony sessions because that phase uses offhand totem logic.

Steve state `1` can use Tony with Jessica The Dark Shield payloads.

Angry Steve can use Tony for pull, self-plunge, and escape/reposition, but does not use Jessica payload actions.

## NPC Tony Session Flow

NPC Tony use is managed by the shared NPC combat fishing rod session code.

The session manager:

1. Saves original offhand.
2. Equips Tony The Fishing Rod.
3. Swings offhand and plays cast sound.
4. Uses `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP`.
5. Spawns tagged NPC combat hook.
6. Waits for hook hit/resolution or max wait of 80 ticks.
7. Resolves target pull, self plunge, around movement, escape, or Jessica payload action.
8. Starts hook return and cleanup.
9. Increments session use count.
10. Maybe restores original offhand and starts cooldown.

NPC rod restore chance is 20 percent, then 40 percent, then capped at 60 percent.

NPC rod cooldown after restore is `120 + random(0..120)` ticks.

## Steve Jessica Payload Behavior

Steve in state `1` has a 50 percent fishing rod action chance to use Jessica The Dark Shield payload.

For Jessica payload action:

- a Jessica shield `ItemProjectile` is attached to the hook,
- the hook targets the enemy,
- on resolution, the enemy is pulled toward Steve,
- `damageEnemyHitByNpcHookedFishingRodItem` deals Jessica shield damage and applies stun/guard-break logic,
- the payload is discarded by NPC cleanup rather than dropped.

## Angry Steve Non-Jessica Behavior

Angry Steve does not run Jessica payload actions.

His Tony action split is:

- 30 percent target pull,
- self-to-target plunge if the roll is below 70 percent or target distance is greater than 8 blocks,
- otherwise around/reposition.

## Rendering And Epic Fight Compatibility

Tony The Fishing Rod has a custom cast model property registered through `FishingRodGrappleUtil.getCastProperty`.

`FishingHookRendererMixin` handles custom hook and rope rendering.

`PatchedItemInHandLayerMixin` forces offhand rendering for Tony and Advanced rods when Epic Fight weapon capability rules would normally hide offhand items.

`GuardSkillMixin` lets offhand fishing rod right click take priority instead of being swallowed by guard behavior.

This forced offhand rod compatibility is for Tony The Fishing Rod and Advanced Fishing Rod, not vanilla fishing rod.

## Current Implementation Boundary

The current source read in this session supports Tony's anchor plunge, target pull, sticky living targets with chance to lose stick, 100 percent item entity/projectile sticking, item projectile no-spin rendering, payload cleanup on hook removal, NPC Jessica payload behavior, and offhand render/use compatibility.

The current source read in this session did not show a separate player rope-radius wall-walking implementation in `FishingRodGrappleUtil`. The confirmed 32 block rod value in current code is the NPC combat fishing rod radius.

