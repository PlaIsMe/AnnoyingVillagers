# Angry Steve Entity Session Knowledge

This file records session knowledge for `AngrySteveEntity`, his combat behavior, death loot, and how Angry Steve uses `tony_the_fishing_rod`.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/entity/AngrySteveEntity.java`
- `src/main/java/com/pla/annoyingvillagers/compat/epicfight/patch/AngryStevePatch.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatCommon.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatBehaviourTemplates.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/AvNpcCombatBehaviorBuilder.java`
- `src/main/java/com/pla/annoyingvillagers/util/InventoryUtils.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/FillWaterBucketGoal.java`
- `src/main/java/com/pla/annoyingvillagers/item/TonyTheFishingRod.java`
- `src/main/java/com/pla/annoyingvillagers/item/FishingRodGrappleUtil.java`

## Identity And State

`AngrySteveEntity` extends `AVNpc` and implements `BurstProtectEntity`.

Angry Steve is added to team `"steve"` on spawn.

Saved state includes:

- `NeverLeave`
- `LeaveTicks`

Angry Steve is persistent, has custom-name visibility enabled, has `maxUpStep = 3.0F`, has `xpReward = 8`, and has place-block-parry chance `1.0`.

## Goals

Angry Steve registers common AVNpc goals through `super.registerGoals()`.

He also registers:

- `KeepPositionGoal`
- crazy NPC goals through `CommonGoals.registerGoalForCrazyNpc(this)`

Because Angry Steve extends `AVNpc`, he also receives the common AVNpc goal set that includes closer-threat retargeting, weapon recovery, floating, line-of-sight bow handling, nearby item burning, idle look, and stroll behavior.

## Attributes

Angry Steve has high combat attributes:

- max health: 250
- movement speed: 0.45
- attack damage: 10
- follow range: 64
- armor: 10
- armor toughness: 20
- knockback resistance: 1
- Epic Fight impact: 4
- Epic Fight armor negation: 10
- Epic Fight stun armor: 20
- max strikes: 100
- stamina: 60
- stamina regeneration: 1.5

## Spawn Equipment

On finalized spawn, Angry Steve equips Legendary Sword in the main hand.

The Legendary Sword is enchanted with:

- Sharpness 5
- Smite 5
- Sweeping Edge 5

The same stack is stored as `mainWeaponItem`.

`leaveTicks` is initialized from config min/max minutes, converted to ticks.

## Inventory Backed Supplies

Angry Steve inherits the `AVNpc` 27 slot `SimpleContainer` inventory.

On first tick, `AVNpc.implementFirstTick` calls `AVNpc.seedInventory()`. The default AVNpc seed logic lives directly in `AVNpc.seedInventory()`: it only runs when the container is empty, then rolls golden apples, foods, arrows, pearls, buckets, blocks, and carried materials such as coal, iron, gold, redstone, lapis, emeralds, or diamonds. If Angry Steve was created from Steve's transformation, Steve's remaining inventory is transferred before finalize spawn, so the empty-inventory guard preserves the transferred supplies.

Combat supply actions are inventory-backed:

- bow shots require and consume arrows,
- ender pearl counters require and consume pearls,
- eating consumes the selected food only after eating completes,
- regular food heals without absorption,
- water bucket self-extinguish consumes a water bucket and returns either water bucket or empty bucket based on source recovery,
- empty buckets can refill from nearby source water through `FillWaterBucketGoal`,
- block placement consumes one block item per placed block.

## Burst Protection And Effects

Angry Steve has burst protection with `getBurstProtectCapRatio() == 0.05F`.

Angry Steve only accepts beneficial effects and glowing.

Every few ticks, he receives stun immunity effects:

- `CEMobEffects.FULL_STUN_IMMUNITY`
- Epic Fight stun immunity through the patch utility path

## Combat Tick Behavior

On first tick, Angry Steve plays his spawn voice and `AnimsPugilistSteve.GUARD_BREAK_ATTACK`.

During tick:

- burst protection decay is updated,
- if `CombatCommon.canEscape` is true, the MOVE goal flag is disabled and navigation stops,
- otherwise the MOVE goal flag is enabled,
- stun immunity is refreshed,
- leave countdown is processed unless `neverLeave` is true.

When `leaveTicks` reaches around 40 ticks, Angry Steve stops moving with no AI and plays his tried voice. When the countdown reaches zero, he broadcasts the retreat/discard behavior and is removed.

`doHurtTarget` also damages target armor by a random amount from 1 to 4.

## Death Loot

Angry Steve death loot includes remaining inventory contents from `AVNpc.dropCustomDeathLoot` plus damaged combat stacks.

Damaged combat drops include:

- compressed diamond helmet and chestplate with high protection enchantments,
- Legendary Sword with Sharpness 5, Smite 5, and Sweeping Edge 5,
- Tony The Fishing Rod.

The damaged stacks are passed through the same random-damage drop path used by other AVNpc combat gear.

Generated normal resource drops were removed. Food, pearls, arrows, buckets, blocks, and carried materials drop only if they remain in Angry Steve's inventory at death.

## Epic Fight Patch

`AngryStevePatch` maps Angry Steve's held weapon behavior.

Legendary Sword and greatsword paths map to the Angry Steve legendary sword combat category.

Bow behavior can be overridden through `MobPatchCommon`.

## Tony Fishing Rod Availability

Angry Steve is an NPC combat fishing rod user.

`CombatCommon.getNpcCombatFishingRodItem(mob)` returns `AnnoyingVillagersModItems.TONY_THE_FISHING_ROD` for Angry Steve.

Unlike Steve state `1`, Angry Steve does not use Jessica The Dark Shield payload fishing rod actions.

## Angry Steve Tony Fishing Rod Actions

`chooseNpcCombatFishingRodAction` gives Angry Steve these choices:

- 30 percent: pull target.
- otherwise, if roll `< 0.70` or distance is greater than 8 blocks: self-to-target plunge.
- otherwise: around/escape/reposition hook.

If Angry Steve already has a sticky fishing rod target stored, the next action is forced to normal target pull.

The normal pull action can store a sticky target with 30 to 50 percent chance. A later pull on the same sticky target has a 35 percent chance to lose the stick.

Angry Steve never uses the Jessica shield fishing rod payload path.

## NPC Fishing Rod Session Flow

Angry Steve uses the same NPC fishing rod session manager as Steve and Villager Generals.

The flow:

1. Save original offhand.
2. Mark the NPC rod session active.
3. Equip Tony The Fishing Rod in off hand.
4. Swing and play cast sound.
5. Use the casting animation root.
6. Spawn a tagged NPC combat fishing hook.
7. Wait for hook resolution or 80 tick max wait.
8. Resolve target pull, self plunge, or around/escape movement.
9. Return the hook.
10. Increment use count and maybe restore offhand.

After each hook, restore chance is `min(0.6, useCount * 0.2)`, so the practical session restore chances are 20 percent, 40 percent, then 60 percent.

When restore succeeds, Angry Steve starts the shared NPC rod cooldown of `120 + random(0..120)` ticks.
