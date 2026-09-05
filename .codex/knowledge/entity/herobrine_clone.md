# Herobrine Clone Variant Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrineMob.java`
- `src/main/java/com/pla/annoyingvillagers/util/CommonGoals.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigCombatProfiles.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/ShadowHerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/Herobrine7Entity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/ArmoredHerobrineEntity.java`

## Shared Current Architecture

`HerobrineCloneEntity`, `ShadowHerobrineCloneEntity`, `Herobrine7Entity`, and `ArmoredHerobrineEntity` all extend `HerobrineMob` and use the current native Rig combat flow.

`HerobrineMob.registerGoals()` supplies the shared Herobrine behavior: retargeting, dangerous reaction, healing, optional `RollItemGoal`, protect/healing follow behavior, hostile goals, and wandering. `CommonGoals.registerGoalForHostileNpc(...)` now gives normal `HerobrineMob` variants `RigShieldGuardGoal` and `RigAnimatedMeleeAttackGoal`; it does not use `PortalApproachGoal` for ordinary Herobrines.

Combat style comes from `RigCombatProfiles.getCombatProfile(mob)`:

- a `RigCombatProfileProvider` item owns its custom style;
- ordinary sword -> `BASIC`;
- two ordinary swords -> `DUAL_BASIC`;
- ordinary axe -> `AXE`;
- anything else / empty main hand -> `UNARMED`.

Do not add entity-specific melee goals to these normal Herobrine variants just to make Rig attacks run. Their inherited `HerobrineMob`/`CommonGoals` flow already owns melee selection.

All four current classes use `ATTACK_DAMAGE = 5.0D`, so native Rig collider hits have a real base damage value.

## Spawn Rules

The current `canSpawn(...)` implementations for these variants require:

- `HerobrineMobData` is not occupied;
- nighttime;
- at least `Difficulty.MEDIUM` progression;
- normal `Monster.checkMonsterSpawnRules(...)`.

The current clone/7/armored classes do not have the old local "every 3 days" test in their `canSpawn(...)` methods. Natural Herobrine singleton claim/release and initial spawn handling remain owned by `HerobrineMob`.

## HerobrineCloneEntity

Default equipment:

- main hand: `OBSIDIAN_WEAPON`.

Attributes:

- max health 100;
- movement speed 0.45;
- attack damage 5;
- follow range 64;
- armor 40;
- armor toughness 20;
- knockback resistance 1.

It uses Herobrine Clone attack/hurt/death voices. On death, it has a 20% branch that creates `InfectedChrisEntity`; otherwise it creates `InfectedPlayerNpcEntity` with `possessed_by = herobrine_clone` and copies armor slots.

## ShadowHerobrineCloneEntity

Default equipment:

- main hand: `SHADOW_OBSIDIAN_PILLAR`.

Its attributes match the normal Herobrine Clone: 100 health, 0.45 speed, 5 attack damage, 64 follow range, 40 armor, 20 armor toughness, 1 knockback resistance.

On death it creates an `InfectedPlayerNpcEntity` with `possessed_by = shadow_herobrine_clone` and copies armor slots.

This class is distinct from the elite `ShadowHerobrineEntity`; elite Shadow Herobrine behavior is documented in `shadow_herobrine.md`.

## Herobrine7Entity

Default equipment:

- main hand: `SHADOW_OBSIDIAN_WEAPON`.

Attributes match the standard clone family: 100 health, 0.45 speed, 5 attack damage, 64 follow range, 40 armor, 20 armor toughness, 1 knockback resistance.

On death it creates `InfectedPlayerNpcEntity` with `possessed_by = herobrine_7`.

## ArmoredHerobrineEntity

`ArmoredHerobrineEntity` also implements `RollItemUser`. Because `HerobrineMob.registerGoals()` checks `instanceof RollItemUser`, it automatically gets `RollItemGoal`; do not register a second copy.

Default equipment:

- head: `HEROBRINE_OBSIDIAN_DIAMOND_HELMET`;
- chest: `HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE`;
- main hand: `SHADOW_OBSIDIAN_SWORD`.

`canRollItem()` requires a live target and inherited `swapWeaponCooldown == 0`. `rollItem()` calls `super.rollItem()` to start the inherited 100-200 tick swap cooldown, then toggles a `SHADOW_OBSIDIAN_SWORD` in the offhand. The item/profile resolver therefore naturally switches between one-sword and dual-compatible Obsidian combat styles as equipment changes.

Attributes are again 100 health, 0.45 speed, 5 attack damage, 64 follow range, 40 armor, 20 armor toughness, and 1 knockback resistance.

On death it creates `InfectedTheMostMoistBurrit0Entity`.

## Damage Rules

These four variants ignore fall, cactus, wither, drown, wither-skull, and dragon-breath damage. Ordinary `AbstractArrow` damage is rejected unless it is one of the explicitly allowed custom arrow/trident paths in each class.

## Portal Support Relationship

Normal Herobrine clones do not have a movement goal that pathfinds them into support portals. Greg/Transporter support logic observes the supported Herobrine's existing movement and places a portal in that movement path. This is intentional; do not re-add `PortalApproachGoal` to common Herobrine hostile goals.
