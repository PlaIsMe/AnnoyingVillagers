# AdvancedMobPatch Session Knowledge

This file records session knowledge for the temporary AdvancedMobPatch combat path and Alex's current use of it.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/mobpatch/AdvancedMobPatch.java`
- `src/main/java/com/pla/annoyingvillagers/mobpatch/AlexPatch.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/AdvancedNpcCombatBehaviorBuilder.java`
- `src/main/java/com/pla/annoyingvillagers/mixin/WeaponCapabilityAccessor.java`
- `src/main/java/com/pla/annoyingvillagers/init/AnnoyingVillagersModEntities.java`
- `build.gradle`

## AdvancedMobPatch Role

`AdvancedMobPatch<T extends Mob>` extends Combat Evolution's `CEHumanoidPatch<T>`.

Its combat behavior path builds a CE combat behavior from the current main-hand Epic Fight `CapabilityItem`.

The flow is:

1. Read main-hand and off-hand item capabilities.
2. Resolve the active `Style` from the main-hand capability.
3. Try to generate a behavior builder from the weapon capability's own auto-attack motions.
4. Fall back to `CEHumanoidPatch.getCustomWeaponMotionBuilder()` only when `useDefaultMoveset(...)` returns true.

`getAdvancedCustomWeaponMotionBuilder(...)` was removed. The older NPC bow override hook is no longer part of this advanced path.

## Weapon Capability Motions

`AdvancedMobPatch` generates attack chains from Epic Fight weapon capability data instead of hardcoded patch-local weapon attack maps.

`WeaponCapabilityAccessor` is a mixin accessor for `WeaponCapability.autoAttackMotions`.

For a generated moveset:

- the main-hand capability must be a non-empty `WeaponCapability`
- category must not be `NOT_WEAPON`, `FIST`, `BOW`, `CROSSBOW`, or `SHIELD`
- the active style's auto attack list is used, falling back to `Styles.COMMON` if needed

`CEHumanoidPatch.setWeaponMotions()` is abstract, so `AdvancedMobPatch` implements it as a no-op. This keeps the advanced path from defining hardcoded CE weapon attack maps. Generated attacks still come from Epic Fight `WeaponCapability.autoAttackMotions`.

## Generated Attack Profiles

`AdvancedNpcCombatBehaviorBuilder.weapon(...)` creates generated CE combat chains from the capability combo list.

The generated profile defaults are:

- `normalAttacksPerChain = 3`
- `generatedChainCount = 50`
- utility roots enabled
- guard root enabled
- jump root enabled

Patch subclasses can override:

- `getAdditionalAttackGroups(...)`
- `getNormalAttacksPerChain()`
- `getGeneratedChainCount()`
- `useDefaultMoveset(...)`
- `canGuard()`
- `getGuardChance()`

## AlexPatch

`AlexPatch` is the only registered mobpatch while this AdvancedMobPatch system is being tested.

`AnnoyingVillagersModEntities.setPatch(...)` registers only:

- `AnnoyingVillagersModEntities.ALEX -> AlexPatch::new`

`AlexPatch` extends `AdvancedMobPatch<PathfinderMob>` and keeps Alex's existing animator setup, guard behavior, custom guard-hit handling, execution support, and Alex/Jev hook combat tick integration.

`AlexPatch.setWeaponMotions()` calls `super.setWeaponMotions()` first, which is the AdvancedMobPatch no-op. Alex only adds her sword guard-hit animation mapping after that.

## Alex Thunder Diamond Blade Additional Attacks

The former `AlexThunderDiamondBlade` helper logic has been moved into `AlexPatch`.

Alex's additional attack groups are no longer global. `getAdditionalAttackGroups(...)` returns no extra attacks unless Alex's main-hand capability is `WeaponCategories.SWORD` and Alex's actual main-hand item is `AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE`.

For one-hand style, Alex can add:

- `EFNSwordAnimations.NF_SWORD_SKILL`
- `AnimsAVSword.THUNDER_DIAMOND_BLADE_INNATE`

For two-hand style, Alex can add:

- `StraightSwordAnimations.STRAIGHTSWORD_DUAL_DODGE_SLASH`
- `AnimsAVSword.THUNDER_DIAMOND_BLADE_DUAL_INNATE`

Both groups use `AdditionalAttackGroup.random(0.35F, ...)`.

The forced `CombatCommon.stepAnimations()` group was removed from the Thunder Diamond Blade additional attack lists.

## Gradle Source Filtering

`build.gradle` contains temporary source filtering for the AdvancedMobPatch test path.

Under `src/main/java/com/pla/annoyingvillagers/mobpatch`, only these files are compiled:

- `AdvancedMobPatch.java`
- `AlexPatch.java`

Under `src/main/java/com/pla/annoyingvillagers/combatbehaviour`, the filter keeps only the shared/advanced support files needed while older weapon-specific combat definitions are broken:

- `AdvancedNpcCombatBehaviorBuilder.java`
- `AlexJevHookCombat.java`
- `AvNpcCombatBehaviorBuilder.java`
- `AvNpcCombatBehaviourBuilder.java`
- `CombatBehaviourTemplates.java`
- `CombatCommon.java`
- `HerobrineCommon.java`
- `TransporterHerobrineCombatValues.java`

`AlexThunderDiamondBlade.java` is excluded because its logic has moved into `AlexPatch`.
