# Advanced Fishing Rod Session Knowledge

This file records session knowledge for `advanced_fishing_rod`, its player behavior, NPC behavior, rendering compatibility, and relationship to Villager Generals.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/item/AdvancedFishingRod.java`
- `src/main/java/com/pla/annoyingvillagers/item/FishingRodGrappleUtil.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatCommon.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatBehaviourTemplates.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/AvNpcCombatBehaviorBuilder.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/FishingHookReturnMixin.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/GuardSkillMixin.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/client/FishingHookRendererMixin.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/client/PatchedItemInHandLayerMixin.java`

## Item Identity

Advanced Fishing Rod is `AnnoyingVillagersModItems.ADVANCED_FISHING_ROD`.

`AdvancedFishingRod` extends vanilla `FishingRodItem`.

Item properties:

- stack size: 1
- durability: 250

`inventoryTick` delegates to `FishingRodGrappleUtil.inventoryTick`.

`use` delegates to `FishingRodGrappleUtil.use(this, level, player, hand)`.

## Drop Source

Advanced Fishing Rod drops from Villager Generals.

All four Villager General classes add Advanced Fishing Rod through the damaged stack drop path.

Villager Generals also use Advanced Fishing Rod in NPC combat. Steve and Angry Steve use Tony The Fishing Rod instead.

## Shared Custom Rod Tagging

Custom fishing rod hooks are vanilla `FishingHook` entities with custom persistent data.

The common custom hook marker is:

- `KEY_GRAPPLE_HOOK = "avGrappleFishingRod"`

Returning hooks are marked with:

- `KEY_RETURNING`

Advanced and Tony share the same base custom rod use flow through `FishingRodGrappleUtil`.

## Player Cast And Return Flow

When the player uses Advanced Fishing Rod and has no active `player.fishing`, the utility spawns a vanilla fishing hook and tags it as a custom grapple hook.

When the player uses Advanced Fishing Rod and an active hook exists:

- if the hook is already returning, the rod can recast from the hook's current return position instead of starting from the player,
- otherwise it tries to plunge a hooked target toward the owner,
- if no hooked target can be plunged, it tries to plunge the player toward a latched hook anchor,
- then it damages the rod by the return damage value,
- then it starts the visible hook return.

Starting hook return marks the hook returning, disables gravity, zeroes movement, and lets the hook/rope retract visually instead of using only the old vanilla instant retrieval behavior.

## Player Plunge To Hook Anchor

Advanced Fishing Rod can plunge the player toward a block anchor.

The plunge path requires:

- the hook is tagged as a custom grapple hook,
- there is no hooked target,
- grapple cooldown has expired,
- the hook is not in water,
- the hook has latched onto a valid anchor.

`latchHookIfReady` stores an anchor when the hook is on ground or nearly stopped and is not in water.

When plunge succeeds:

- the player velocity is set toward the anchor,
- grounded players get a small upward boost,
- fall distance is cleared,
- the player receives a short Levitation effect for 5 ticks at amplifier 1,
- grapple cooldown is applied.

Advanced player plunge power:

- grounded: `2.7`
- airborne: `2.0`

## Pulling Hooked Targets

Advanced Fishing Rod can pull a hooked target toward the owner.

The target pull path can use:

- the vanilla fishing hook's currently hooked entity,
- nearby fallback target search around the hook,
- `ItemEntity`,
- `ItemProjectile`.

Rejected targets include:

- the owner,
- dead entities,
- spectators.

Advanced target pull power:

- grounded target: `1.2`
- airborne target: `0.9`
- distance scale: `0.22`

When target pull succeeds, the hook stores a target-plunged marker so the same target is not repeatedly processed in the same hook flow.

## Difference From Tony The Fishing Rod

Advanced Fishing Rod does not own Tony's sticky living target or sticky item projectile payload behavior.

Advanced can:

- plunge the player toward a hook anchor,
- pull hooked entities or item entities toward the owner,
- recast from a returning hook position,
- render/retract the rope visibly.

Advanced does not:

- convert item entities into 100 percent sticky item projectile payloads,
- keep item projectiles attached to the hook as payloads,
- run Tony's 30 to 50 percent living entity stick chance,
- use Jessica The Dark Shield payload logic.

## NPC Villager General Use

Villager Generals use Advanced Fishing Rod through `CombatCommon.getNpcCombatFishingRodItem`.

The NPC combat fishing rod radius is 32 blocks.

The NPC combat max wait is 80 ticks, which is 4 seconds. If the hook does not hit or resolve in time, the scheduler forces return and cleanup.

Villager Generals can use Advanced Fishing Rod to:

- pull a target toward themselves,
- hook a block between themselves and the target and plunge toward the target,
- hook around/upper/side/back to reposition,
- hook around as escape behavior.

For around and escape hooks, anchor search favors leaves and high valid blocks, so trees are preferred when available.

## NPC Session Flow

NPC use of Advanced Fishing Rod is not a normal item right click.

The NPC session manager:

1. Saves the original offhand.
2. Marks session active.
3. Equips Advanced Fishing Rod in offhand.
4. Swings offhand and plays cast sound.
5. Uses `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP` in the combat behavior root.
6. Spawns a tagged NPC combat fishing hook.
7. Waits for hit/resolution or 80 tick max wait.
8. Resolves pull, plunge, or escape movement.
9. Returns the hook.
10. Increments session use count.
11. Maybe restores the original offhand and starts cooldown.

The restore chance after each hook is:

- 20 percent after the first hook,
- 40 percent after the second hook,
- 60 percent capped after the third and later hooks.

NPC rod cooldown after restore is `120 + random(0..120)` ticks.

## Rendering And Epic Fight Compatibility

Advanced Fishing Rod has a custom cast model property registered through `FishingRodGrappleUtil.getCastProperty`.

`FishingHookRendererMixin` handles custom hook and rope rendering.

`PatchedItemInHandLayerMixin` forces offhand rendering for custom fishing rod utilities when Epic Fight weapon capabilities would normally hide offhand items.

`GuardSkillMixin` lets offhand fishing rod right click take priority instead of being swallowed by guard behavior.

This compatibility exists for Advanced Fishing Rod and Tony The Fishing Rod, not for vanilla fishing rod.

## Current Implementation Boundary

The current source read in this session supports anchor latch, player plunge, hooked-target pull, returning rope animation, NPC combat rod sessions, and offhand render/use compatibility.

The current source read in this session did not show a separate player rope-radius wall-walking implementation in `FishingRodGrappleUtil`. The 32 block value confirmed in current code is the NPC combat fishing rod radius.

