# Steve Entity Session Knowledge

This file records session knowledge for `SteveEntity`, his combat state flow, death loot, and how Steve uses `tony_the_fishing_rod`.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/entity/SteveEntity.java`
- `src/main/java/com/pla/annoyingvillagers/event/TotemUsingEvent.java`
- `src/main/java/com/pla/annoyingvillagers/compat/epicfight/patch/StevePatch.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatCommon.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatBehaviourTemplates.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/AvNpcCombatBehaviorBuilder.java`
- `src/main/java/com/pla/annoyingvillagers/item/TonyTheFishingRod.java`
- `src/main/java/com/pla/annoyingvillagers/item/FishingRodGrappleUtil.java`

## Identity And State

`SteveEntity` extends `AVNpc` and implements `BurstProtectEntity`.

Steve is added to team `"steve"` on spawn. Natural/chunk spawn ownership is controlled by `SteveData`.

Important saved state:

- `State`: phase value. `0` is first phase, `1` is second phase.
- swap cooldown
- `SayLegendary`

Steve is persistent, has custom-name visibility enabled, has `maxUpStep = 3.0F`, has `xpReward = 8`, and has a place-block-parry chance of `0.8`.

## First Phase Survival And Totem

Steve has burst protection with `getBurstProtectCapRatio() == 0.15F`.

In state `0`, `afterBurstProtection` can prevent lethal damage when final health would become `<= 1.0F` and Steve is not holding a `TOTEM_OF_UNDYING` in the off hand. When it triggers, Steve is set to 1 health and the damage is consumed.

On server tick in state `0`, if Steve health is `<= 20` and he is not already holding a totem, he equips a vanilla totem in the off hand.

If Steve is in state `0`, has a target, health is above 20, and off hand is a totem, the tick logic clears the off hand again.

## Totem Transition To Second Phase

`TotemUsingEvent.onLivingUseTotem` handles Steve using a vanilla totem.

After Steve uses the totem:

- 1 tick later, Steve health is restored to max.
- 1 tick later, Steve receives a diamond sword enchanted with Sharpness 5 and Smite 5 in the off hand and as off weapon.
- 1 tick later, `setState(1)` is called.
- If the Epic Fight patch exists, Steve plays `AnimsPugilistSteve.GUARD_BREAK_ATTACK`.
- 10 ticks later, Steve equips a compressed diamond helmet with Protection 5, Projectile Protection 5, Fire Protection 5, and Blast Protection 5.
- 20 ticks later, Steve plays armor equip sound and equips a compressed diamond chestplate with the same protection enchantments.

## Normal Equipment Rolling

`rollItem()` controls Steve's combat item swaps.

In state `1`, when Steve is above half health:

- roll `< 0.2`: Woopie The Sword in main hand and Jessica The Dark Shield in off hand.
- roll `< 0.4`: diamond greatsword with Sharpness 5 and Knockback 5.
- roll `< 0.6`: Samantha The Killer Axe with Sharpness 5 and Fire Aspect 2, with Jessica The Dark Shield in off hand.
- otherwise: dual diamond swords with Sharpness 5 and Smite 5.

In state `1`, when Steve is low health:

- chance `<= 0.4`: Woopie The Sword and Jessica The Dark Shield.
- otherwise: Steve says his legendary line once and equips Legendary Sword.

In state `0`, if health is `<= 20`, Steve equips a diamond sword and a totem.

If no phase-specific branch selected equipment, Steve picks one normal item from diamond sword, wooden door, crafting table, ladder, or trapdoor.

After rolling, Steve stores a copy of the main hand stack as `mainWeaponItem` and sets swap cooldown to a random value from 100 to 199 ticks.

## Tick Behavior

If Steve has a living target and his main hand is empty, he rolls combat equipment and says `"what"`.

If Steve has no target, `state != 2`, and main hand is not empty, he clears both hands.

Steve manages state `0` totem offhand behavior every tick and decrements swap cooldown.

## Death And Angry Steve Spawn

On server-side death, Steve can transform into `AngrySteveEntity` instead of completing normal death flow.

If a random roll is within config `ANGRY_STEVE_CHANCE`:

- an Angry Steve is created at Steve's position,
- Steve's inventory is transferred,
- Steve is discarded,
- `SteveData` claim is moved to Angry Steve,
- Angry Steve finalizes spawn and is added to the world,
- killer/target context is transferred where possible.

If the transform does not happen, Steve plays his death voice and then runs normal death handling.

## Death Loot

Steve death loot includes damaged combat stacks and normal resource stacks.

Damaged combat drops include:

- compressed diamond helmet and chestplate with Protection 5, Projectile Protection 5, Fire Protection 5, and Blast Protection 5.
- diamond sword with Sharpness 5 and Smite 5.
- possible second diamond sword.
- bow with Power 5 and Punch 5.
- one random utility weapon from wooden door, crafting table, ladder, trapdoor, or mending diamond sword.
- one rare weapon roll:
  - 30 percent: diamond greatsword with Sharpness 5, Smite 5, and Sweeping Edge 5.
  - 30 percent: Samantha The Killer Axe with Sharpness 5, Smite 5, and Sweeping Edge 5.
  - 40 percent: Woopie The Sword with Sharpness 5, Smite 5, and Sweeping Edge 5.
- Jessica The Dark Shield.
- Tony The Fishing Rod.

Every stack in the damaged drop list is passed through `EquipmentDataLoader.getRandomDamage(stack)` before dropping.

Normal drops include gapples, ender pearls, dirt, TNT, diamond block, dragon egg, white bed, cake, water bucket, food, dye, carrots, potatoes, sticks, ingots, diamonds, compressed diamonds, and 10 to 29 arrows.

## Epic Fight Patch

`StevePatch` maps Steve's held items and weapon categories to Steve-specific combat behavior.

Known mappings include:

- fist behavior
- sword behavior
- wooden door behavior
- crafting table behavior
- ladder behavior
- trapdoor behavior
- Woopie Sword behavior
- Legendary Sword behavior
- greatsword behavior
- Samantha Killer Axe behavior

Bow behavior can be overridden through `MobPatchCommon`.

When the EFN compatibility path is loaded, guard hit handling applies for diamond sword and Woopie Sword cases.

## Tony Fishing Rod Availability

Steve is an NPC combat fishing rod user.

`CombatCommon.getNpcCombatFishingRodItem(mob)` returns `AnnoyingVillagersModItems.TONY_THE_FISHING_ROD` for Steve.

Steve state `0` is blocked from starting new fishing rod sessions by `isStevePhaseOneFishingRodBlocked`. This preserves first-phase totem/offhand behavior. If a fishing rod session is already active, the active session is allowed to finish.

Steve state `1` can use Tony The Fishing Rod through `CombatBehaviourTemplates.combatFishingRodRoot()` and `combatFishingRodEscapeRoot()`.

## Steve Tony Fishing Rod Actions

For Steve in state `1`, `chooseNpcCombatFishingRodAction` uses this action split:

- 50 percent: Jessica shield pull target.
- next 20 percent: normal target pull.
- next 20 percent: self-to-target plunge.
- final 10 percent: around/escape/reposition hook.

If Steve already has a sticky fishing rod target stored, the next action is forced to normal target pull.

The normal pull action can store the target as a sticky target with a 30 to 50 percent chance. A later pull on the same sticky target has a 35 percent chance to lose the stick. This sticky target is NPC combat memory, not an item entity payload.

## Jessica Shield Fishing Rod Pull

Steve state `1` can use a Jessica The Dark Shield payload with Tony The Fishing Rod.

For the Jessica action:

- the NPC hook receives a Jessica The Dark Shield `ItemProjectile` payload,
- the hook flies toward the target,
- the scheduler waits until the hook resolves or reaches max wait,
- the target is pulled toward Steve,
- `CombatCommon.damageEnemyHitByNpcHookedFishingRodItem` is called with the stuck item.

Jessica shield damage from this path is 10.

If the target patch is not already stunned, the logic applies a long stun. If the target is stunned, it plays `AnimsPugilistSteve.GUARD_BREAK_ATTACK`. The code also plays shield block sound feedback.

Jessica payloads used by NPC fishing rod sessions are discarded by the hook cleanup path instead of becoming farmable item drops.

## NPC Fishing Rod Session Flow

Steve's fishing rod use is not a normal player right click.

The NPC combat flow:

1. Save Steve's original offhand.
2. Mark the NPC fishing rod session active.
3. Equip Tony The Fishing Rod in the off hand.
4. Swing offhand and play bobber throw sound.
5. Use `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP` through the combat behavior root.
6. Spawn a tagged NPC combat fishing hook.
7. Wait until the hook hits/resolves or until max wait reaches 80 ticks.
8. Resolve the pull/plunge/escape action.
9. Return the hook and increment session use count.
10. Maybe restore the original offhand.

The restore chance after each hook is based on session use count:

- first next-hook check: 20 percent.
- second next-hook check: 40 percent.
- third and later next-hook checks: capped at 60 percent.

When restore succeeds, the original offhand or empty hand is restored and a cooldown of `120 + random(0..120)` ticks starts.

