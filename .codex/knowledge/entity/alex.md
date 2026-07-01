# Alex Entity Session Knowledge

This file records session knowledge for `AlexEntity`, Alex/Jev shared hook combat behavior, and how Alex uses `hook_gun`.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/entity/AlexEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/JevEntity.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/AlexJevHookCombat.java`
- `src/main/java/com/pla/annoyingvillagers/item/HookGunItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HookGunHookEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/HookUtil.java`
- `src/main/java/com/pla/annoyingvillagers/util/InventoryUtils.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/FillWaterBucketGoal.java`

## Identity And Relationship To Jev

`AlexEntity` extends `AVNpc` and implements `BurstProtectEntity`. Alex owns the combat pair relationship from her side with:

- `jevToProtect`
- `jevUUID`
- `spawnJev`
- `state`
- `currentBoundHook`

Alex and Jev are both added to team `"alex"` on spawn. Alex spawns Jev once in `tick()` when `spawnJev` is false. The spawned Jev receives Alex as `followTarget`, stores Alex's UUID, finalizes spawn, and is stored back into Alex as `jevToProtect`.

Alex resolves Jev from `jevUUID` on server tick if the direct reference is missing. If Jev is dead, Alex clears both `jevToProtect` and `jevUUID`.

## Spawn Equipment And Persistent State

On spawn, Alex equips an enchanted `THUNDER_DIAMOND_BLADE` in the main hand:

- Sharpness 5
- Fire Aspect 2
- Knockback 2
- Unbreaking 5

Alex starts with an `ENDER_PEARL` in the off hand, stores the sword as main weapon, stores the ender pearl as off weapon, and sets `currentBoundHook` to the default enchanted iron pickaxe. Alex does not seed or sync a hook gun into her inventory on spawn.

Because Alex extends `AVNpc`, she also has the shared 27 slot utility inventory. Empty AVNpc inventories are seeded on first tick with golden apples, two regular food stacks, arrows, ender pearls, water bucket, possible empty bucket, random placeable blocks, and possible carried materials such as coal, iron, gold, redstone, lapis, emeralds, or diamonds.

Shared AVNpc combat supplies are inventory-backed: bow shots consume arrows, ender pearl counters consume pearls, eating consumes food after completion, water bucket use returns full or empty bucket based on source recovery, and block placement consumes one block per placed block.

Alex saves and loads:

- `JevUUID`
- `State`
- `SpawnJev`
- `CurrentBoundHook`

If no `CurrentBoundHook` exists on load, it defaults to `AlexJevHookCombat.createAlexDefaultPickaxe()`.

## Hook Gun Inventory State

`getCurrentBoundHook()` returns a copy of `currentBoundHook`. If it is empty, it first initializes it to the default enchanted iron pickaxe.

`setCurrentBoundHook(ItemStack)` stores one copy of the item. It does not write to hook guns in Alex's inventory.

Alex no longer has `ensureHookGunInventory()`. Her inventory gets no hook gun from spawn logic or combat tick logic.

`canDualHookInSecondPhase()` returns true only when Alex is in state 1 and has at least one actual hook gun item in her custom inventory.

Jev's death drops the hook gun bound with Jev's pickaxe. Alex must pick up that hook gun into her inventory to unlock dual hook.

## Goals And Passive Behavior

Alex registers normal neutral NPC goals through `CommonGoals.registerGoalForNeutralNpc(this)`.

Alex also has a target goal that can attack a living entity when:

- Jev exists and is alive
- the candidate target is not null
- the candidate target's last hurt mob is Jev

Alex does not despawn when far away. She has `maxUpStep = 2.8F`, `xpReward = 60`, and persistent custom name visibility.

## Damage And Survival

Alex implements burst protection with `getBurstProtectCapRatio() == 0.15F`.

Alex overrides `actuallyHurt` to run the Forge hurt/damage hooks, armor and magic reduction, absorption handling, burst protection, and then apply final health loss manually.

In phase/state 0, `afterBurstProtection` prevents lethal damage when all are true:

- `state == 0`
- final health would become `<= 1.0F`
- Alex is not holding a `TOTEM_OF_UNDYING` in the off hand

When this triggers, Alex is set to 1 health and the damage is consumed.

On server tick, if `state == 0`, health is `<= 20`, and Alex is not already holding a totem, she puts a `TOTEM_OF_UNDYING` in the off hand.

Alex has ender pearl counter behavior enabled. `doEnderPearlCounterPattern` calls `doChrisStyleEnderPearlCounter()`.

## Death And Drops

When Alex dies server-side, `AlexJevHookCombat.onAlexDeath(this)` runs before normal death handling. If Jev is alive, Jev enters a run-away window and shoots a pickaxe hook at Alex's death position.

Alex custom death loot includes:

- damaged enchanted `THUNDER_DIAMOND_BLADE`
- damaged bow with Punch 3, Power 3, Flame 2
- hook gun bound with Alex's current bound hook
- Alex's current bound hook item

Food, block items, arrows, ender pearls, buckets, and carried materials drop only if they remain in Alex's AVNpc inventory.

## Shared Combat Constants

Alex/Jev hook combat lives in `AlexJevHookCombat`.

Important constants:

- `SHOOT_DELAY_TICKS = 7`
- `DEFAULT_RETRIEVE_DELAY_TICKS = 44`
- `DEFAULT_RESTORE_DELAY_TICKS = 58`
- `GRAPPLE_RETRIEVE_DELAY_TICKS = 54`
- `GRAPPLE_RESTORE_DELAY_TICKS = 70`
- `PICKAXE_HOOK_ATTACH_TIMEOUT_TICKS = 60`
- `HOOK_SESSION_ABSOLUTE_RESTORE_TICKS = 140`
- `ALEX_MIN_COOLDOWN_TICKS = 90`
- `ALEX_RANDOM_COOLDOWN_TICKS = 80`
- `MAX_HOOK_TARGET_DISTANCE_SQR = 34 * 34`
- `PICKAXE_ENTITY_PULL_MAX_DISTANCE_SQR = 22 * 22`
- `ALEX_PICKAXE_ENTITY_PULL_MIN_DISTANCE_SQR = 4 * 4`
- `ALEX_PICKAXE_ENTITY_PULL_CHANCE = 0.38`
- `ALEX_PULL_JEV_TO_SAFE_PLACE_CHANCE = 0.18`

## Alex Hook Combat Tick

`tickAlex(MobPatch<?>)` is the primary Alex hook combat scheduler.

It returns unless:

- the patched entity is an alive `AlexEntity`
- the level is a `ServerLevel`
- Alex has an alive target
- the target is within 34 blocks squared
- Alex is not already in a hook session
- Alex has no active hook
- `CombatCommon.canPerformNormalAttackLogic(mobPatch)` allows it
- Alex's hook cooldown has expired

Before combat decisions, Alex syncs target with Jev and cleans up stale hook sessions. Combat no longer creates or syncs a hook gun in Alex's inventory.

Decision order:

1. Try sword hook burst.
2. Try pulling Jev to Alex if Jev is safe and far enough.
3. In state 1, if dual hook is unlocked, sometimes perform dual hook.
4. In state 1, sometimes hook flint and steel into the target.
5. Maybe switch `currentBoundHook` between default pickaxe and diamond sword.
6. If bound item is a pickaxe, try pickaxe entity pull, otherwise hook a block anchor.
7. If bound item is not a pickaxe, shoot it at the target as an item hook.

When a hook fires, Alex gets cooldown `90 + random(0..80)` ticks unless the specific branch sets a different cooldown.

## Alex Default Hook Items

`createAlexDefaultPickaxe()` creates an iron pickaxe enchanted with:

- Mending 1
- Unbreaking 3
- Efficiency 3

`createAlexHookSword()` creates a diamond sword enchanted with:

- Sharpness 5
- Smite 5

`createAlexHelmet()` creates a diamond helmet enchanted with:

- Protection 4
- Unbreaking 3

## Alex Bound Item Switching

`maybeSwitchAlexBoundHook(AlexEntity)` switches Alex's remembered hook item:

- if current item is a pickaxe, Alex has a 22 percent chance to switch to the diamond sword
- if current item is not a pickaxe, Alex has a 34 percent chance to switch back to default pickaxe
- otherwise no switch happens

When a switch happens, Alex plays hook gun animation, remembers the last hook bound item for the off hand, and plays leather equip sound.

## Alex Sword Hook Burst

Sword hook burst is stored in persistent data under `AlexSwordHookBurstRemaining`.

State 0 sword burst:

- only when Alex has line of sight
- blocked if Alex health is `<= 45% max health`
- blocked if Alex offhand is a totem
- target must be at least 11 blocks away
- start chance is 24 percent
- burst length is 2 to 3 shots

State 1 sword burst:

- only when Alex has line of sight
- target must be at least 5 blocks away
- start chance is 86 percent
- burst length is 4 to 7 shots

Each burst shot sets Alex's current bound hook to the enchanted diamond sword and shoots the offhand hook at the target.

## Alex Dual Hook Logic

Dual hook is available only in state 1 when `canDualHookInSecondPhase()` is true.

`performAlexDualHook` refuses to run if Alex already has an active hook session or active hook.

Dual hook patterns:

- Roll `< 0.18`: left default pickaxe and right diamond sword both target the enemy eye with small left/right offsets, if pickaxe entity pull is valid.
- Roll `< 0.34`: left default pickaxe targets a block anchor, right diamond sword targets the enemy.
- Roll `< 0.56`: both hooks are diamond swords and both target enemy eye offsets.
- Roll `< 0.78`: both hooks are default pickaxes and target two block anchors.
- Otherwise: left lava bucket and right water bucket target support blocks near the enemy.

Dual hook temporarily places hook guns in both hands, fires after `SHOOT_DELAY_TICKS`, then restores the saved hands when the hook session completes.

## Alex Partner Pull Logic

Alex can pull Jev to herself with a pickaxe hook when:

- random chance passes `ALEX_PULL_JEV_TO_SAFE_PLACE_CHANCE`
- Jev exists, is alive, is not spectator, is not Alex
- Alex has line of sight to Jev
- Alex does not have a nearby enemy targeting Alex within 3 blocks
- distance is at least 3 blocks and at most 22 blocks

The hook target is Jev's eye position. Since the bound item is a pickaxe, `HookGunHookEntity` yanks the entity toward the hook owner.

## Hook Session Flow For Alex

Alex's NPC hook use is not a normal player right click. `AlexJevHookCombat.shootHook` and `shootDualHook` create a temporary hook session:

- save original hand item(s)
- put hook gun(s) bound to the desired item in hand
- swing hand(s)
- optionally play `AnimsPugilistSteve.HOOK_GUN`
- wait `SHOOT_DELAY_TICKS`
- aim the entity at the target
- call `HookGunItem.launchHookAt`
- monitor active hook state
- return pickaxe hooks if they never attach or if the absolute restore timeout is reached
- restore the saved hand item(s)

The hook gun animation only plays when the bound item differs from the last remembered hook item for that hand, or when the bound item is consumable food or potion-like.

## Combat Relationship With Jev

`syncAlexAndJevTarget` makes Alex and Jev share targets. If Alex has an alive non-allied target and Jev has no alive target, Jev receives Alex's target. If Jev has an alive non-allied target and Alex has none, Alex receives Jev's target.

For current Jev hook logic, Jev only starts hook support actions when Alex has an alive target. Outside Alex combat mode, Jev follows and moves around Alex instead of shooting hooks.
