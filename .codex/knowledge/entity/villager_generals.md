# Villager Generals Session Knowledge

This file records session knowledge for all Villager General entities, their AVNpc combat behavior, Advanced Fishing Rod use, and lava bucket behavior.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/entity/RedVillagerGeneralEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/BlueVillagerGeneralEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/GreenVillagerGeneralEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/PurpleVillagerGeneralEntity.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/VillagerArmyEntity.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/AVNpc.java`
- `src/main/java/com/pla/annoyingvillagers/compat/epicfight/patch/VillagerGeneralPatch.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/AvNpcCombatBehaviorBuilder.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatBehaviourTemplates.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatCommon.java`
- `src/main/java/com/pla/annoyingvillagers/item/AdvancedFishingRod.java`
- `src/main/java/com/pla/annoyingvillagers/item/FishingRodGrappleUtil.java`

## Entity Family

The Villager Generals are:

- `RedVillagerGeneralEntity`
- `BlueVillagerGeneralEntity`
- `GreenVillagerGeneralEntity`
- `PurpleVillagerGeneralEntity`

All four extend `VillagerArmyEntity`, which extends `AVNpc`.

All four register `CommonGoals.registerGoalForVillagerKnightNpc(this)`.

All four are added to team `"villagers"` on spawn.

All four generate their main hand item through `VillagerUtil.generateMainWeaponItem()` and store combat weapon copies for later restore logic.

All four can randomly start riding an animal on first tick.

## Shared AVNpc Goals

Because Villager Generals inherit from `AVNpc`, they receive the common AVNpc goal set.

Important common goals include:

- closer-threat retargeting through `RetargetCloserThreatGoal`,
- weapon recovery during combat,
- floating,
- bow line-of-sight handling,
- nearby item burning,
- idle look behavior,
- random stroll behavior.

`VillagerArmyEntity.registerGoals` adds `VillagerArmyHurtByTargetGoal` at target priority 1 after the common AVNpc target goals.

The closer-threat retargeting behavior is not hardcoded to 1v2. It scans for any closer valid enemy that is targeting the mob and can switch target in larger fights such as 1v5. It also respects `MobTargetRedirectEvent.shouldPreserveRedirectTarget(this.mob)` so special redirect mechanics are not overwritten.

## Individual General Notes

Red Villager General:

- `maxUpStep = 3.0F`
- `xpReward = 8`
- offhand is a heater shield if main hand is Woopie The Sword or Hooked Diamond Sword, otherwise Ender Pearl
- drops Hooked Diamond Sword and Advanced Fishing Rod as damaged combat drops

Blue Villager General:

- `maxUpStep = 2.0F`
- `xpReward = 10`
- mob type is undead
- offhand is Ender Pearl
- drops vanilla fishing rod and Advanced Fishing Rod as damaged combat drops

Green Villager General:

- `maxUpStep = 2.0F`
- `xpReward = 0`
- offhand and armor setup follow the green/purple class implementation
- drops Advanced Fishing Rod as damaged combat drop

Purple Villager General:

- `maxUpStep = 3.0F`
- `xpReward = 8`
- offhand and armor setup follow the purple/green class implementation
- drops Advanced Fishing Rod as damaged combat drop

All four use villager ambient, hurt, and death sounds, and use the Villager General attack voice set.

## Ender Pearl Counter

Villager Generals have a shared ender pearl counter pattern.

The pattern can throw pearls with timing and direction variations:

- immediate pearl at a random 90 to 180 degree direction,
- after 40 ticks, 50 percent chance for a 0 degree pearl,
- after 20 ticks, 20 percent chance for 180 degrees and 10 percent chance for 90 degrees.

Red overrides the restore offhand stack to Ender Pearl. Other generals use the inherited offhand weapon restore value.

## Death Loot And Utility Drops

All Villager Generals use custom death loot with supplies and combat gear.

Advanced Fishing Rod is included as a damaged drop for all four generals.

`AVNpc.dropUtilityBucketLoot` also applies:

- all AVNpc have a water bucket drop chance,
- Villager Generals have an additional lava bucket drop chance.

## Combat Behavior Builder

`AvNpcCombatBehaviorBuilder.weapon` and `AvNpcCombatBehaviorBuilder.fist` include the Villager General fishing rod and lava bucket behavior roots.

The relevant roots appear before the main combat roots:

- `CombatBehaviourTemplates.combatFishingRodRoot()`
- `CombatBehaviourTemplates.villagerGeneralLavaBucketRoot()`
- normal combat roots
- `CombatBehaviourTemplates.combatFishingRodEscapeRoot()`

The root being present in the shared AVNpc builder does not mean every AVNpc can use these actions. Predicate checks inside `CombatCommon` restrict fishing rod users and lava bucket users.

## Advanced Fishing Rod Use

Villager Generals use Advanced Fishing Rod, not Tony The Fishing Rod.

`CombatCommon.getNpcCombatFishingRodItem(mob)` returns `AnnoyingVillagersModItems.ADVANCED_FISHING_ROD` when `isGeneralMob(mob)` is true.

Villager Generals can use Advanced Fishing Rod during combat to:

- pull a target toward themselves,
- hook a block between themselves and the target and plunge toward the target,
- hook around, left, right, back, or upward to reposition,
- hook around for escape behavior.

NPC fishing rod range is 32 blocks through `NPC_COMBAT_FISHING_ROD_RADIUS`.

NPC fishing rod max wait is 80 ticks, which is 4 seconds. If the hook does not resolve before max wait, the session forces hook return and resolves cleanup.

## Villager General Rod Action Choice

For non-Steve and non-Angry-Steve NPC rod users, the default action split depends on distance.

If distance is greater than 12 blocks:

- 55 percent self-to-target plunge,
- otherwise pull target.

If distance is less than 3 blocks:

- 45 percent pull target,
- otherwise around/reposition.

At mid distance:

- 45 percent pull target,
- 35 percent self-to-target plunge,
- 20 percent around/reposition.

If the Villager General already has a sticky fishing rod target stored, the next action is forced to target pull.

The normal pull action can store a sticky target with 30 to 50 percent chance. A later pull on the same sticky target has a 35 percent chance to lose the stick.

This sticky target is NPC target memory. Villager Generals do not use Tony's item-sticking projectile behavior because they use Advanced Fishing Rod.

## Rod Anchor Selection

For self-to-target movement, the AI tries to find a hook block between the mob and the target by scanning along the eye line. If no valid block is found, it falls back to a target body point.

For around/escape movement, `findNpcCombatFishingRodAroundAnchor` searches within a 12 block radius and vertical range around the mob.

Valid anchor blocks are leaves or blocks with non-empty collision.

Scoring favors:

- leaves with a large bonus,
- higher blocks,
- escape anchors that move away from the target direction,
- non-escape anchors that are not directly toward the target.

This means tree leaves are prioritized for hook-around movement and escape when available.

## NPC Rod Session Flow

Villager General rod use is managed by the NPC combat fishing rod session code.

The flow:

1. Save original offhand.
2. Mark NPC fishing rod session active.
3. Equip Advanced Fishing Rod in off hand.
4. Swing offhand and play bobber throw sound.
5. Use `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP` through the combat behavior root.
6. Spawn tagged NPC combat fishing hook.
7. Wait until the hook resolves or max wait reaches 80 ticks.
8. Resolve pull, self plunge, around, or escape action.
9. Return the hook.
10. Increment use count and maybe restore original offhand.

After each hook, restore chance is `min(0.6, useCount * 0.2)`:

- first next-hook check: 20 percent,
- second next-hook check: 40 percent,
- third and later next-hook checks: capped at 60 percent.

When restore succeeds, the original offhand or empty hand is restored and cooldown starts for `120 + random(0..120)` ticks.

## Lava Bucket Combat

Only Villager Generals use the lava bucket combat behavior.

`villagerGeneralLavaBucketRoot` has an `isGeneral` predicate so other mobs and player NPCs do not run this logic.

The lava bucket behavior requires:

- target alive,
- server side,
- mob not passenger,
- no active fishing rod session,
- target within 12 blocks squared check range,
- lava bucket cooldown expired,
- a valid lava placement position near the target.

When executed:

1. Save original offhand.
2. Equip lava bucket in off hand.
3. Set cooldown to `160 + random(0..140)` ticks.
4. Swing offhand and look at target.
5. After 6 ticks, place lava at the target foot/above/side if the target position is replaceable.
6. Set offhand to bucket.
7. After 40 ticks, swing and pick the lava back up if it is still there.
8. Set offhand to lava bucket.
9. After 4 more ticks, restore original offhand.

The 40 tick delay is 2 seconds.

## Bow And Weapon Patch Notes

`VillagerGeneralPatch` uses `MobPatchCommon.overideCustomWeaponMotionBuilderForAvNpc` and bow override logic for custom weapon motions.

Villager General bow swaps can add color-specific enchantments:

- Red: Flame 2.
- Blue: Power 2.
- Green: Power 1 and Flame 1.
- Purple: Punch 2.

Green has an EFN special guard hit handling path.

