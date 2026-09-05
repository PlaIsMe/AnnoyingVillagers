# Shadow Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/ShadowHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/ObsidianMachineGunGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/ShadowHerobrineSummonDarkObGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/ShadowHerobrineShootDarkObGoal.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/RigItemVisualResolver.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrineUtil.java`

## Role

`ShadowHerobrineEntity` is the elite Shadow Herobrine and extends `HerobrineMob`. It implements `RollItemUser` and uses the native Rig combat system inherited from `HerobrineMob` plus dedicated Shadow abilities.

This entity is distinct from `ShadowHerobrineCloneEntity`; ordinary clone-family behavior is documented in `herobrine_clone.md`.

Current base attack damage is 10.

## Goal Registration

In addition to inherited Herobrine goals, Shadow Herobrine registers:

- priority `0`: `ObsidianMachineGunGoal`;
- priority `1`: `ShadowHerobrineShootDarkObGoal`;
- priority `1`: `ShadowHerobrineSummonDarkObGoal`.

These special goals require a live combat state and do not start while conflicting Rig/stun/passenger/machine-gun conditions are active.

## Prepared Dark Obsidian Projectiles

Shadow Herobrine tracks three prepared `BlockProjectileEntity` references/UUIDs:

- `darkObUp`;
- `darkObLeft`;
- `darkObRight`.

`spawnDarkObEntities()` creates missing prepared dark-ob projectiles at positions above/right/left of the entity. They are no-gravity and marked not ready for shooting until released.

`isDarkObReady()` is true when at least one of the three prepared projectiles exists.

`shootDarkObsAtTarget(double speed)` releases every available prepared projectile toward the live target. If a generic projectile portal route is available, it uses `HerobrineUtil.getProjectilePortalAim(...)` before falling back to the target eye position. After firing, `summonDarkObCooldown` is randomized to 200-600 ticks (10-30 seconds).

## Summon / Shoot Rig Animations

`ShadowHerobrineSummonDarkObGoal` requires:

- live target;
- no prepared dark obs currently ready;
- `summonDarkObCooldown == 0`;
- machine gun not active;
- not sacrificing/passenger/stunned;
- no active Rig animation.

It plays `POINT_LEFT_HAND_MIDDLE`.

`ShadowHerobrineShootDarkObGoal` requires the same general safety state but `isDarkObReady() == true`. It plays `POINT_LEFT_HAND_TOWARD`.

The shared point-animation specs dispatch by entity type at tick 5:

- `POINT_LEFT_HAND_TOWARD`: Reaper casts thunder; Shadow Herobrine calls `shootDarkObsAtTarget(2.0D)`;
- `POINT_LEFT_HAND_MIDDLE`: Reaper respawns a healing crystal; Shadow Herobrine calls `spawnDarkObEntities()`.

This lets the same authored left-hand animations serve different Herobrine abilities without duplicating animation IDs.

## Obsidian Machine Gun

`ObsidianMachineGunGoal` starts only in Shadow Herobrine state 2 with:

- live target;
- `obsidianMachineGunCooldown == 0`;
- `obsidianMachineGunTick == 0`;
- not healing/sacrificing/passenger/stunned;
- no active Rig animation.

It plays `OBSIDIAN_MACHINE_GUN`.

Current spec:

- duration 50 ticks;
- dangerous and invulnerable;
- attack window 0-12;
- tick 2 hook calls `setObsidianMachineGunTick()` and plays the machine-gun voice.

`setObsidianMachineGunTick()` starts a 20-tick firing sequence. During that sequence the entity tick freezes horizontal/body movement with `setDeltaMovement(Vec3.ZERO)` and calls `shootChain(SHADOW_OBSIDIAN_BLOCK, 2.5F, 5)` every tick. `shootChain` therefore creates five block projectiles per firing tick and can redirect the firing vector through `HerobrineUtil.getProjectilePortalAim(...)`.

When the counter reaches its final tick, Shadow Herobrine rolls a 200-300 tick machine-gun cooldown and calls `rollItem()` to select its next Obsidian weapon configuration.

## Weapon Rolling

Shadow Herobrine uses inherited `HerobrineMob.swapWeaponCooldown`; do not add a duplicate Shadow-specific swap cooldown.

Its `rollItem()` calls `super.rollItem()` and selects between Shadow Obsidian Weapon, Shadow Obsidian Pillar, Shadow Obsidian Sword, and possible dual-sword setup. State 2 gear is enchanted through the entity's existing enchant helper.

## Machine-Gun Ender Eye Visual

`RigItemVisualResolver.resolve(entity, originalStack, leftHand)` currently replaces the visual item with `HEROBRINE_ENDER_EYE` when:

- entity is `ShadowHerobrineEntity`;
- the rendered physical arm is the left hand (`leftHand == true`);
- active animation is `OBSIDIAN_MACHINE_GUN`.

This is client visual substitution only; the server-side equipment is not replaced for the machine-gun animation.

Current caveat: if the item-in-hand layer returns before calling the resolver when the underlying stack is empty, an empty physical offhand cannot be replaced visually. Do not assume empty-offhand Ender Eye rendering works unless the renderer first resolves the visual stack and only then checks `isEmpty()`.

## Portal Relationship

Shadow Herobrine does not use the removed common `PortalApproachGoal`. Greg/Transporter support portals are positioned from Shadow Herobrine's existing movement when they support it.

`HerobrineUtil` is still legitimately used by Shadow Herobrine's projectiles for portal aiming. That generic routing helper should not be confused with the removed Greg/Transporter combined support-goal architecture.
