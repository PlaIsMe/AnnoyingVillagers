# Null Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/NullEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/NullSkeletonEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/NullSummonSkeletonGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/NullWeaponOrbitGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/RigAnimatedMeleeAttackGoal.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/NullWeapon.java`
- `src/main/java/com/pla/annoyingvillagers/item/NullWeaponItem.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/NullWeaponRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/NullSkeletonRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigCombatProfiles.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigCombatStyle.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigLocomotionStyle.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigColliderSystem.java`
- `src/main/java/com/pla/annoyingvillagers/util/AnimationUtil.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrineMob.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Native Rig Combat Profile

`NullWeaponItem` implements `RigCombatProfileProvider` and resolves to `RigCombatStyle.NULL_HEROBRINE`.

The Null combat profile is:

- normal attacks: `NULL_ATTACK1`, `NULL_ATTACK2`, `NULL_ATTACK3`, `NULL_ATTACK4`
- special attacks: `NULL_DASH_ATTACK`, `NULL_JUMP_ATTACK`
- roll attacks: none
- ultimate attacks: `NULL_ATTACK5`, `NULL_EXTRA_ATTACK`
- locomotion: `RigLocomotionStyle.NULL_HEROBRINE`

Null does not register `EliteHerobrineSecondFormGoal`. Its state-2 skeleton summon is handled by the dedicated `NullSummonSkeletonGoal`.

`AnimationUtil` maps the Null locomotion style to `NULL_IDLE`, `NULL_WALK`, and `NULL_RUN`.

## Null Aerial Combat / Flight Behaviour

Null uses `FlyingMoveControl` and `FlyingPathNavigation`. Its attribute supplier must include `Attributes.FLYING_SPEED`; the current native Rig setup uses `0.70D` so the aerial combat speed modifiers are noticeably stronger than the previous `0.45D`.

The Null branch inside `RigAnimatedMeleeAttackGoal` is responsible for aggressive target flight instead of ordinary ground navigation.

Important attack-start rule:

- all Null melee/profile attacks except `NULL_EXTRA_ATTACK` use a true 3D attack-start distance check
- this prevents Null from starting fist attacks while vertically far above the target
- `NULL_EXTRA_ATTACK` remains the ranged exception and may start without first flying into melee distance

When an attack cannot start yet, Null switches between direct approach and short BBQ-style aerial orbit phases.

### Direct approach

- predicts target movement before choosing the wanted position
- base aerial speed is randomized every 12-36 ticks
- normal base speed modifier is approximately `2.2D-3.9D`
- ascending toward a higher point is deliberately slowed, capped around `1.9D`
- descending/diving toward a lower enemy is accelerated to at least about `3.6D`
- long direct approaches receive an additional speed boost
- state 2 adds another combat-flight speed multiplier
- final direct flight speed modifier is clamped to `1.25D-5.0D`

This creates the intended slow-rise / fast-plunge behavior: Null can rise more deliberately, but when he is above the enemy and needs to close downward he dives much more aggressively.

### Target orbit

At medium combat distance, Null can randomly enter a short orbit phase inspired by `BbqEntity`:

- orbit is eligible around roughly 3-9 blocks from the target
- orbit chance is about 42% when a new flight phase is selected
- orbit radius is randomized around `2.75D-5.0D`
- orbit height is randomized around `0.75D-3.5D` above the target eye position
- orbit direction is randomized and can flip during the orbit
- radius can also change while circling
- vertical bob is added to avoid a perfectly flat circle
- rising portions are slower
- downward portions are faster
- state 2 increases orbit speed slightly

Orbit phases last only a short time, then Null returns to direct approach. This prevents circling from making the fight passive while still making his aerial path less predictable.

## Null Weapon Ownership

`NullEntity` owns up to five `NullWeapon` entities:

- sword
- axe
- pickaxe
- shovel
- hoe

The weapons remain associated with Null by UUID/entity references and are resolved again on the server when necessary.

Null also checks once per second for a genuinely missing owned weapon slot and recreates only the missing slot, preventing old saved `SpawnNullWeapon=true` data from permanently losing one of the five weapons.

## Smooth Idle Orbit + Random Teleport

Idle weapons are controlled by `NullWeaponOrbitGoal` while `isReleased()` is false.

The current idle behaviour intentionally combines smooth movement with the visual style of the old AV teleport logic:

- smoothly orbits around Null using `FlyingMoveControl`
- continuously follows owner movement
- predicts some owner velocity so fast Null movement does not leave the weapons far behind
- smoothly changes orbit radius, height, speed, and direction
- adds vertical bob and radius pulse
- approximately every 35-90 ticks a weapon can teleport to another random point around Null
- if a weapon falls more than about 7.5 blocks behind, it immediately performs a random catch-up teleport
- after each teleport, smooth orbiting resumes from the new location
- a much larger emergency owner-distance guard remains for extreme separation

The random teleport is therefore a deliberate style feature, while smooth orbiting remains the ordinary movement path.

## Floating Weapon Rendering / Collision

`NullWeapon` still extends `Monster` so the existing entity hierarchy and AI can be reused, but it behaves as a non-physical floating tool:

- `noPhysics = true`
- no gravity
- zero-size runtime `EntityDimensions`
- `isPickable()` returns false
- `isPushable()` returns false
- `canBeCollidedWith()` returns false
- `displayFireAnimation()` returns false
- the registered Null weapon entity types are `fireImmune()`

`NullWeaponRenderer` supplies a separate render-culling box so zero physical dimensions do not make the weapon disappear from rendering.

The renderer uses `ModelRig` only as the animation/tool-bone carrier. The visible item is rendered by the native `RigItemInHandLayer`; the normal Rig armor/arrow/vanilla humanoid layers are not installed.

Because the physical hitbox is zero-size, released weapon attacks use explicit target distance rather than bounding-box collision.

## Weapon Spin Animation

`spinfor5seconds()` uses native `RigAnimationId.SPINNING_WEAPON` as a held pose and cancels it after 100 ticks.

The old Epic Fight compatibility block inside `spinfor5seconds()` is intentionally preserved as commented code for future AV_EFM compatibility.

Idle, non-released weapons also trigger the spinning animation at randomized intervals. Starting another spin invalidates the previous delayed cancellation through `spinAnimationSequence` so an old delayed task cannot cancel a newer spin.

## NULL_ATTACK5 / NULL_EXTRA_ATTACK Weapon Release Hooks

`RigAnimationSpecs` attaches the same start hook to both `NULL_ATTACK5` and `NULL_EXTRA_ATTACK`.

The hook no longer samples a Null hand position and no longer moves/"shoots" the selected weapon out of Null's hand.

Current flow:

1. resolve Null's current valid combat target
2. call `NullEntity.releaseRandomNullWeapon(target)`
3. select one owned weapon according to the current Null state
4. call `chosen.release(target)` on that weapon at its current world position
5. the released weapon independently flies after and attacks its target

### State 0 / state below 2

- maximum one released attacking weapon at a time
- if an older weapon is still released, it is immediately recalled first
- the new release prefers a different weapon when another idle weapon is available
- exactly one weapon is then released
- the released weapon has a randomized 300-600 tick lifetime (15-30 seconds)
- when the lifetime ends, it returns to ordinary idle-orbit behaviour

### State 2

- already released weapons are left alone
- every `NULL_ATTACK5` / `NULL_EXTRA_ATTACK` releases one additional idle weapon
- the released count can build from one to all five
- state-2 released weapons use no ordinary release timeout
- if another release hook fires while all five weapons are already released, Null recalls all five at once
- that all-released trigger is a reset/recall action; it does not immediately release a sixth/new weapon

## Released Null Weapon Combat

A released `NullWeapon` is an independent flying attacker.

Target selection priority includes:

1. its current valid target
2. Null's current target
3. Null's last hurt target / last attacker
4. player-owner equivalents when applicable
5. the nearest valid non-allied living entity within about 18 blocks

Therefore a released weapon can automatically acquire and attack a nearby enemy even when its original assigned target dies or disappears.

Released movement:

- stays released even if no target is immediately available
- if no target exists, hovers/follows around its owner rather than freezing permanently
- when a target exists, predicts some target velocity and flies toward the target eye position
- follow speed increases with distance
- at close range it attacks on an 8-tick weapon attack cooldown
- while close it circles tightly around the target instead of stopping dead
- if the target dies, it tries to acquire another nearby valid enemy instead of immediately cancelling release

`PortalApproachGoal` remains higher priority and may temporarily take over released movement when portal approach is valid.

## Null Skeleton Summoning

State 2 uses `NullSummonSkeletonGoal` rather than `EliteHerobrineSecondFormGoal`.

Summoning flow:

1. `NullEntity.canSummonNullSkeleton()` requires state 2, an available skeleton slot, and summon cooldown <= 0.
2. Null plays `NULL_EXTRA_ULT` and stops movement during the summon goal.
3. At tick 30, a `NullSkeletonEntity` is spawned about two blocks in front of Null.
4. The skeleton is temporarily AI-disabled during its spawn animation.
5. The skeleton plays `NULL_SKELETON_SPAWN` through the native Rig animation controller.
6. Its AI is restored after the spawn animation duration.
7. Null claims one of its existing two guard slots.
8. The next summon receives a randomized 600-1200 tick cooldown (30-60 seconds).

If a claimed skeleton dies, its slot is cleared. In state 2 this starts a fresh randomized 30-60 second replacement cooldown.

`NullSkeletonEntity` uses `RigAnimatedMeleeAttackGoal` so it can attack through the native Rig combat system. `NullSkeletonRenderer` registers it with a `RigMobRenderer` model path instead of the vanilla `WitherSkeletonRenderer`.

## Herobrine State Effects

The once-per-second `HerobrineUtil.spawnEliteEffect` call in `HerobrineMob` is restricted to these state > 0 entities:

- `AegisHerobrineEntity`
- `SledgehammerHerobrineEntity`
- `SwordsmanHerobrineEntity`
- `ReaperHerobrineEntity`
- `GlaiveHerobrineEntity`

Null is not included in that elite-effect path.

For `NullEntity` and `ShadowHerobrineEntity`, `FULL_COWL` particles are emitted only while `state == 2`, with the existing random boolean gate. They are excluded from the generic potion-effect `FULL_COWL` branch so state 0 Null does not accidentally produce that effect.
