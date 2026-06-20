# Jev Entity Session Knowledge

This file records session knowledge for `JevEntity`, Alex/Jev shared support combat behavior, and how Jev uses `hook_gun`.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/entity/JevEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/AlexEntity.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/AlexJevHookCombat.java`
- `src/main/java/com/pla/annoyingvillagers/item/HookGunItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HookGunHookEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/HookUtil.java`

## Identity And Relationship To Alex

`JevEntity` extends `AVNpc` and implements `BurstProtectEntity`. Jev tracks Alex with:

- `followTargetUUID`
- `followTarget`

Jev belongs to team `"alex"` after spawn. Alex creates Jev on her server tick and gives Jev a direct follow target and UUID reference.

Jev resolves Alex from `followTargetUUID` on server tick if the direct reference is missing. If Alex is dead, Jev clears the target reference and UUID.

## Spawn Equipment And Goals

Jev starts with:

- main hand: `JEV_PENCIL`
- off hand: `JEV_BOOK`
- head slot: `JEV_GLASSES`

Jev stores pencil and book as his main and off weapon items on spawn.

Jev has zero attack damage by attribute and is intended as a support NPC rather than a melee fighter.

Jev goals include:

- look at Alex within 12 blocks
- avoid monsters within 5 blocks
- avoid players within 5 blocks
- move toward Alex quickly when too far
- random stroll
- random look around
- float
- follow mob

If Jev is more than 600 distance squared from Alex during server tick, he teleports to Alex.

## Passive Movement Around Alex

`AlexJevHookCombat.moveJevAroundPartner` runs before Jev hook combat gating. It lets Jev move around Alex even when Alex is not in combat.

It returns if:

- Alex is missing or dead
- Jev is in a hook session
- Jev has an active hook
- `jev.tickCount % 45 != 0`
- Jev navigation is already in progress

When it runs, Jev picks a random angle and radius from 4 to 9 blocks around Alex and navigates there at speed `1.25`.

## Combat Mode Gate

Jev's hook logic only runs when Alex has an alive target:

- `alex.getTarget() != null`
- `alex.getTarget().isAlive()`

If Alex has no live target, Jev does not run hook support, hook away, food, potion, sapling, block, or enemy harassment logic. In non-combat mode he only follows and moves around Alex through normal legacy-style behavior.

After this combat gate, `syncAlexAndJevTarget` may copy Alex's target to Jev if Jev lacks an alive target.

## Jev Hook Combat Constants

Important constants from `AlexJevHookCombat`:

- `JEV_MIN_COOLDOWN_TICKS = 25`
- `JEV_RANDOM_COOLDOWN_TICKS = 35`
- `JEV_PICKAXE_ENTITY_PULL_MIN_DISTANCE_SQR = 7 * 7`
- `JEV_PICKAXE_ENTITY_PULL_CHANCE = 0.24`
- `JEV_PULL_ALEX_TO_SAFE_PLACE_CHANCE = 0.22`
- shared `PICKAXE_ENTITY_PULL_MAX_DISTANCE_SQR = 22 * 22`
- shared `SAFE_PLACE_PULL_RADIUS = 3`

Jev's pickaxe hook item is `createJevPickaxe()`, a non-enchanted iron pickaxe.

## Jev Hook Combat Tick

`tickJev(MobPatch<?>)` is Jev's support hook scheduler.

It returns unless:

- the patched entity is an alive `JevEntity`
- level is `ServerLevel`
- Alex exists and is alive
- Alex has an alive target
- Jev is not already in a hook session
- Jev has no active hook
- Jev's cooldown has expired

Decision order:

1. If Alex is burning, shoot snowball into Alex.
2. If Alex health is at or below 55 percent, shoot strong healing potion into Alex.
3. If Alex's enemy is bow-like, shoot a cover block near Alex.
4. If enemy is within 8 blocks of Jev, hook away with pickaxe.
5. Pull Alex to Jev if Jev is safe and Alex is far enough.
6. If valid, shoot pickaxe at enemy to pull enemy toward Jev.
7. Shoot enemy harassment item.
8. Shoot random distraction block near enemy.
9. Shoot bone meal at a visible sapling only.
10. If Alex is missing health, shoot food into Alex.
11. If Alex is missing health, shoot positive potion into Alex.
12. If Alex is missing health, possibly shoot golden apple into Alex.
13. In Alex state 1, if Alex has no helmet, possibly shoot enchanted diamond helmet into Alex.
14. Randomly hook away with pickaxe.
15. In Alex state 1 and if Alex is missing health, possibly shoot regeneration buff potion into Alex.
16. Shoot support block around Alex.
17. If Alex is missing health, fallback chance to shoot support food.

Food, support potions, golden apples, and regeneration buff potion are health gated and stop when Alex is full or nearly full health. The helper treats Alex as missing health only when `health < maxHealth - 0.5`.

## Jev Burning Support

When Alex is on fire, Jev shoots a hook gun bound with `SNOWBALL` at Alex.

HookUtil snowball behavior:

- clears fire on the target
- applies Slowness for 100 ticks at amplifier 1
- plays snowball throw sound
- consumes the snowball

This replaced earlier water bucket support to avoid flooding the arena.

## Jev Healing And Buff Support

Strong healing potion uses `Potions.STRONG_HEALING`.

Support food pool:

- bread
- potato
- cooked beef
- cooked chicken
- carrot

Positive potion pool:

- strong healing
- strong strength
- strong swiftness
- strong leaping
- custom haste
- strong regeneration

In state 1, Jev can shoot a diamond helmet to Alex if Alex has no helmet. That helmet has Protection 4 and Unbreaking 3.

## Jev Enemy Harassment

Enemy harassment items can be:

- strong poison splash potion
- weakness splash potion
- strong slowness splash potion
- custom nausea splash potion
- custom blindness splash potion
- custom wither splash potion
- strong harming splash potion
- poisonous potato
- pufferfish
- flint and steel
- fire charge

The hook gun item behavior is supplied by `HookUtil`: weapons deal item damage, potions apply effects, food heals or harms inverted-heal targets, flint and steel and fire charge burn targets.

## Jev Blocks, Cover, Distraction, And Saplings

When Alex's enemy holds a bow or crossbow, Jev tries to shoot a cover block near Alex, two blocks toward the enemy.

Cover block pool:

- oak door
- oak trapdoor
- oak fence
- oak fence gate
- glass
- glass pane
- oak leaves
- hay block
- spruce planks
- oak planks

Distraction block pool:

- oak planks
- spruce planks
- glass
- glass pane
- oak fence
- oak fence gate
- oak door
- oak trapdoor
- oak leaves
- hay block
- barrel
- crafting table
- pumpkin
- jack o'lantern
- lantern
- flower pot
- poppy
- dandelion
- oak sapling
- azalea
- cactus
- dead bush

Jev can also scan for visible saplings within radius 18, Y range -3 to +5, using `BlockTags.SAPLINGS`. If found, he shoots bone meal only at that sapling. The line check uses block outline clipping so non-solid saplings can be hit. Crops are not selected by Jev's sapling scan.

## Jev Pickaxe Hook Behavior

Jev uses a hook gun bound with non-enchanted iron pickaxe for:

- hook away movement
- pull Alex to Jev when Jev is safe
- pull enemies toward Jev
- hook to Alex's death position

Pickaxe-bound hook behavior is the grapple behavior. If a pickaxe hook hits a block, it attaches and motor-pulls the owner. If it hits an entity, it yanks that entity toward the owner.

Hook anchor search prefers distant anchors, with search radius 30 and ideal distance 22 blocks. If no normal anchor is found, Jev can search nearby ground anchors between 10 and 28 blocks horizontally away.

## Alex Death Response

When Alex dies server-side, `AlexJevHookCombat.onAlexDeath` runs.

If Jev exists and is alive:

- Jev stores `JevAlexDeathRunAwayUntil` for 360 ticks
- Jev shoots a pickaxe hook at Alex's eye position

This is intended to make Jev hook to Alex's death location and then run away. Loot pickup is left to normal `AVNpc` pickup behavior instead of explicit carrying flags.

## Jev Death Response

When Jev dies server-side, `AlexJevHookCombat.onJevDeath` runs.

If Alex exists and is alive, Alex shoots a default enchanted pickaxe hook at Jev's eye position. This lets Alex quickly approach Jev's death position. Loot pickup is left to normal `AVNpc` pickup behavior.

## Jev Drops

Jev always drops:

- `JEV_GLASSES`
- `JEV_PENCIL`
- `JEV_BOOK`
- a hook gun bound with Jev's non-enchanted iron pickaxe, unless Alex exists and cannot store it

If Alex exists and cannot store Jev's hook gun, the drop is skipped and Alex's `canDualHookInSecondPhase` is forced true.

Jev also drops random combat supplies.

Block drops:

- 3 to 7 rolls
- 82 percent chance per roll
- each stack count 2 to 13
- selected from cover or distraction block pools

Plant drops:

- 3 to 7 rolls
- 86 percent chance per roll
- each stack count 1 to 8
- selected from bone meal, poppy, dandelion, blue orchid, allium, oak sapling, spruce sapling, birch sapling, jungle sapling, acacia sapling, dark oak sapling, or cherry sapling

Food drops:

- 2 to 6 rolls
- 84 percent chance per roll
- each stack count 1 to 6
- selected from bread, potato, cooked beef, cooked chicken, carrot, golden apple, enchanted golden apple, poisonous potato, or pufferfish

Potion drops:

- 2 to 5 rolls
- 78 percent chance per roll
- selected from healing, buff, or debuff potion pools

## Jev Self Healing

Jev overrides `hurt`. If his golden apple cooldown is 0, he is not already healing, and incoming damage would put him at or below two thirds of max health, he puts a golden apple or enchanted golden apple in the main hand and calls `CombatBehaviour.eatingGoldenApple`.

The enchanted golden apple chance is `random <= max(0.25, placeBlockToParryChance)`. Jev's place block to parry chance is set to 0, so his minimum enchanted chance is 25 percent.

## Burst Protection

Jev implements `BurstProtectEntity` with `getBurstProtectCapRatio() == 0.15F` and uses the same manual Forge hurt/damage flow as Alex.

## Hook Session Flow For Jev

Jev's hook usage is implemented through `AlexJevHookCombat.shootHook`, not normal player right click.

The flow:

- save original hand item
- put a hook gun bound to the desired item into off hand
- swing off hand
- optionally play `AnimsPugilistSteve.HOOK_GUN`
- wait 7 ticks
- aim at target
- launch hook
- monitor active hook state
- return stale pickaxe hooks
- restore the saved hand item

Jev only plays hook gun animation when the bound item differs from the last remembered hook item for that hand, or when the item is food or potion-like.

