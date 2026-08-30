# Rig Combat System

## Registry flow

Common server-safe rig animation metadata lives under `src/main/java/com/pla/annoyingvillagers/rig`.

The project has many authored/generated animations. Knowledge files should describe the integration flow rather than maintain a duplicate list of animation names.

Every one-shot animation that may be addressed by common gameplay code follows this path:

```text
client/animation/rig_animation/** AnimationDefinition
        ↓
RigAnimationId
        ↓
RigAnimationResolver (client only)
        ↓
RigAnimationSpecs (common/server metadata)
        ↓
generated RigPoseClip data when server pose sampling is required
```

`RigAnimationId` is the network-safe shared identifier registry. Existing enum constants must keep their ordinal order; new ids are appended so old packet ids do not silently change.

`RigAnimationResolver` is client-only and maps each common id to the authored `AnimationDefinition`. Do not import client animation holders into common/server code.

`RigAnimationSpecs` is the authoritative common registry for duration, attack/non-attack classification, attack windows, playback mask, optional jump-on-start flag, and timed hooks. `RigAnimationId.isAttack()` delegates to registered spec metadata instead of duplicating hundreds of attack ids in another switch.

When adding a new authored animation, update the id, resolver, and spec together, then regenerate common pose clips for the animation directories that need collider/root-motion sampling.

## Animation specs and timed hooks

`RigAnimationSpec.attack(...)` represents a damaging animation. `RigAnimationSpec.nonDamaging(...)` represents living, utility, movement, pose, hit, knockdown, or other non-damaging playback. Rolling/step helpers are still available for locomotion actions where their special sound/category behavior is desired.

Attack timing is represented by one or more `RigAttackWindow(startTickInclusive, endTickExclusive, colliders...)` values. Multi-phase attacks use multiple windows; a dual-hit phase can contain multiple colliders in one window.

A window may intentionally have zero colliders while a broad/custom collision phase is awaiting a project-specific collider definition. Empty windows preserve the imported timing but deal no collider-based damage until colliders are assigned.

`RigTimedAnimationHook` is the common event hook mechanism. When timing is imported from a reference `InTimeEvent`, placeholder hooks may be created at the corresponding server tick with an empty action. The gameplay logic inside those hooks must be authored explicitly for this project; do not automatically transplant reference event logic.

Timed hooks may also carry reusable tool-visibility metadata. `hideRightToolAt(...)` / `hideLeftToolAt(...)` hide only the rendered rig tool from that tick until the animation ends or a matching show hook occurs. Visibility is client-derived from the active `RigAnimationSpec`; do not remove or clear the real `ItemStack`, and do not hardcode visibility into projectile/throw hooks.

For EpicFight-to-vanilla ports, translate `InTimeEvent` seconds to rig ticks using `seconds * 20`, then verify each action against the source animation. Preserve separate concerns: particle/effect hooks, sound hooks, projectile spawn hooks, tool-visibility hooks, and state-transition hooks should remain independently reusable even when they execute at the same tick.

`jumpOnStart` is metadata owned by this project. Imported attack specs default it to `false` unless the project explicitly decides the animation should invoke vanilla jump control.

## Collider architecture

The attack collider is a temporary animated combat volume; the target keeps Minecraft's normal entity bounding box. `RigColliderSystem` tests animated attack shapes against `LivingEntity#getBoundingBox()`.

Reusable concepts are:
- `RigColliderAnchor`: rig part to follow
- `RigColliderPreset`: reusable dimensions/local center for a weapon or body category
- `RigCollider`: anchor + preset
- `RigAttackWindow`: active timing and colliders
- `RigPoseClip` / `RigPoseLibrary`: common-side sampled transforms
- `RigColliderSystem`: world-space collision generation and intersection

Tool colliders normally follow `RIGHT_TOOL` / `LEFT_TOOL`; unarmed attacks may follow hand anchors; kicks follow lower-leg anchors. Dual attacks use both relevant colliders rather than a special duplicated movement path.

Weapon families use centralized collider presets such as fist, dagger, sword/longsword/greatsword, spear/polearm, axe, tachi, glaive, scythe, sledgehammer, and foot. Preset dimensions are tuning data shared by all moves in that family. Do not hardcode a separate collider trajectory for each animation when the generated pose already contains the tool/hand/leg trajectory.

Broad/root/body colliders from external reference systems are not assumed to map cleanly to this rig. Leave those windows collider-empty or deliberately assign the held-weapon collider until a project-specific area shape is authored.

## Generated pose and server movement

Generated pose clips are the common source of truth for animated rig-part transforms and authored root/body translation:

```text
authored AnimationDefinition
        ↓ generate_rig_pose_clips.py
common RigPoseClip
        ├─ tool / hand / leg transforms → collider placement
        └─ body/root translation → server entity movement
```

The server samples previous/current body movement, converts model units to blocks, rotates the local delta by the animation-facing direction, and moves the real mob with normal entity movement. Vanilla entity tracking synchronizes that authoritative position to clients. Do not reintroduce a second hand-maintained root-motion keyframe table or a separate position packet.

The client compensates the same sampled movement in the rendered model so visual root translation and real entity translation are not applied twice.

After adding ids/resolver/spec entries, rerun the pose generator over all animation directories that need common-side sampling. A registered attack can play without a generated pose clip, but its part-following collider/root motion will not have the authored transform data until generation is updated.

## Server playback

`RigAnimationController.play(...)` is the common server-side playback entry point. It validates state/locks, records active playback, sends the id/duration packet, schedules hooks/sounds, applies generated-pose movement, and evaluates attack windows.

Actual damage comes from collider intersection, not a fixed global melee range. AI may estimate whether an attack is worth starting from the sampled collider reach plus authored root movement.

A target may be hit once per attack window; later windows may hit it again when the move is intentionally multi-phase.

## Combat-profile attack lock

`LockableRigAttackAnimation` is a narrow gate for combat-profile attacks. It exposes `lock()`, `unlock()`, and `isLocked()` and uses lock-count semantics so independent action systems can overlap safely.

Only ids selected by `RigCombatProfiles.isProfileAttack(...)` are blocked. This is not a global animation lock: utility, living, roll/step, bow, shield, hand-action, and other playback can remain available.

A hand-action system that must not overlap melee should first refuse to begin while `RigAnimationController.hasActiveProfileAttack(mob)` is true, then acquire one lock token for its own conflicting interval and release that token on completion or abort.

## Combat profiles and melee goal

`RigCombatProfile` chooses subsets of registered attacks for a particular equipment/combat style. The profile is selection policy; it is not the master animation registry. Do not duplicate every registered attack in project knowledge.

`RigAnimatedMeleeAttackGoal` paths while no rig attack is active, uses collider/motion-derived attack-start distance, and lets active animation movement/collision drive the action once playback begins.


### Blue Demon profile integration

`CommonGoals.createMeleeAttackGoal(...)` only chooses `RigAnimatedMeleeAttackGoal` for mobs accepted by `supportsRigCombat(...)`. Blue Demon must remain included there; otherwise `registerGoalForBlueDemonNpc(...)` silently falls back to vanilla `MeleeAttackGoal`.

Blue Demon Trident implements `RigCombatProfileProvider`, so equipment resolution must use the provider path before the generic `SwordItem` fallback. Dual tridents resolve `BLUE_DEMON`; phase-two Legendary Sword + Blue Demon Trident resolves `BLUE_DEMON_LEGENDARY_SWORD`.

## Sounds and locomotion separation

Starting a rig animation must not set `Mob#setAggressive(true)` as a generic animation side effect. AI goals own aggression state.

Run rendering must be based on sprinting/current horizontal movement rather than `isAggressive()` or stale damped limb-swing amplitude. This prevents attacks, rolls, and steps from causing a false RUN phase after one-shot playback.

## Debug and client separation

F3+B does not register arbitrary temporary melee volumes as vanilla entity hitboxes. The custom debug renderer draws the same oriented boxes used by `RigColliderSystem`.

Common/server classes must not import `AnimationDefinition`, `ModelPart`, or renderer classes. Client playback resolves `RigAnimationId` through `RigAnimationResolver`; common gameplay uses ids, specs, and generated pose data only.

### Dual-profile pair resolution

`RigCombatProfiles.getCombatProfile(...)` asks the main-hand provider for `getDualRigCombatStyle(...)` only when both providers have the same non-`NONE` `RigDualWieldGroup`. Item implementations that support mixed pairs must therefore make the result symmetric with respect to which item is in the main hand.

Current mixed Legendary Sword pairs:
- Legendary Sword + Woopie the Sword -> `LEGENDARY_SWORD_WOOPIE`
- Legendary Sword + Blue Demon Trident -> `BLUE_DEMON_LEGENDARY_SWORD`
- Legendary Sword + Legendary Sword -> `LEGENDARY_SWORD`
- Blue Demon Trident + Blue Demon Trident -> `BLUE_DEMON`

`LegendarySwordItem`, `WoopieTheSwordItem`, and `BlueDemonTridentItem` each participate in `RigDualWieldGroup.LEGENDARY_SWORD` and override dual style as needed so swapping hands does not silently change the profile.

Blue Demon phase two uses `LEGENDARY_SWORD` main hand + `BLUE_DEMON_TRIDENT` offhand, so the normal matching `LEGENDARY_SWORD` dual-wield group selects `BLUE_DEMON_LEGENDARY_SWORD`. Keep the pair-sensitive overrides symmetric in `LegendarySwordItem` and `BlueDemonTridentItem`.



### Angry Steve Legendary Sword profiles

Angry Steve extends `AVNpc`, so implementing `RollItemUser` automatically enables `RollItemGoal`; implementing `FishingRodUser` automatically enables `CombatFishingRodGoal`. His `rollItem()` keeps Legendary Sword in main hand and rolls the off hand between Tony The Fishing Rod and Woopie The Sword.

Profile resolution order matters:
1. matching dual-wield providers resolve first;
2. therefore Legendary Sword + Woopie resolves `LEGENDARY_SWORD_WOOPIE`;
3. otherwise, when the main provider returns `LEGENDARY_SWORD` and the mob is `AngrySteveEntity`, `RigCombatProfiles` substitutes `LEGENDARY_SWORD_ANGRY_STEVE`.

`LEGENDARY_SWORD_WOOPIE` uses the three authored `LEGENDARY_SWORD_DUAL_AUTO*` attacks first and uses `WOOPIE_THE_SWORD_FLY` / `WOOPIE_THE_SWORD_EXTRA_ULT` as its ultimate choices.

`LEGENDARY_SWORD_ANGRY_STEVE` owns the mob awakening path through `LEGENDARY_SWORD_EXTRA_ULT`. The tick-11 spec hook calls Angry Steve's synced awakening state; do not add player `SkillContainer` dependencies to common rig code.

## RollItemGoal integration

Weapon-switch methods such as `BlueDemonEntity.rollItem()` are executed through `RollItemGoal` when the mob implements `RollItemUser`. `RollItemGoal` owns the roll/step-back animation and calls `rollItem()` after the interface-provided switch delay. Mobs that extend `AVNpc` get the goal conditionally from `AVNpc.registerGoals()`, but standalone mobs such as `BlueDemonEntity` must register the goal explicitly.

For Blue Demon, priority 1 is intentional so the weapon-switch roll can preempt shield/melee goals when its long cooldown expires. `canRollItem()` must remain the behavior gate; do not call `rollItem()` directly from `tick()`.

## Totem guard-break recovery animation

The old EpicFight `TotemUsingEvent` played `AVAnimations.STUN_BACK` after Steve, Alex, or Chris consumed a Totem and switched into their upgraded state. The vanilla rig port keeps the commented AV_EFM block and then calls `RigAnimationController.play(mob, RigAnimationId.STUN_BACK)`. The current rig registry has `STUN_BACK`; it does not have a separate `STUN_FORWARD` id/clip, so do not invent one or substitute a knockdown animation unless that asset is explicitly ported later.

The old `efnGuardHitState`/`efnGuardHitCooldown` cycle is unrelated to this Totem animation and is dead in the non-EpicFight rig implementation. It has been removed from `AVNpc`, `HerobrineMob`, and `BlueDemonEntity`.


## Electrify shock stun animations

`StunAnimations2.SHOCKED` and `StunAnimations2.SHOCKED_LONG` are the vanilla-rig ports of the old Blue Demon `ZAP` / `ZAP_LONG` hit animations.

Registration rules:
- `RigAnimationId.SHOCKED` and `SHOCKED_LONG` are appended at the end of the enum so existing ordinal-based network ids are not shifted.
- `RigAnimationResolver` maps them to `StunAnimations2.SHOCKED` / `SHOCKED_LONG`.
- Specs are non-damaging stun clips: `SHOCKED = 17 ticks` (`0.85s`), `SHOCKED_LONG = 30 ticks` (`1.5s`).
- `RigStunController.applyShock(...)` must use the stun controller path, not ordinary `RigAnimationController.play(...)`. This keeps the mob locked for the animation and prevents normal combat playback from immediately overwriting the shock pose.
- Shock does not extend or replace an already active rig stun. This matches the old EpicFight Electrify check that only played ZAP when the patch was not already stunned.

`ElectrifyMobEffect.customEffectTick(...)` calls the reusable `playShockAnimation(LivingEntity, int)` compatibility entry point every 20 ticks. It only routes `Mob` instances supported by `RigStunController`; player visuals are client-side and separate.

## Electrify player POV effect

The non-EpicFight player shock effect is client-only and derives from the synced `ELECTRIFY` mob effect, so no extra network packet is required.

`ElectrifyScreenEffect`:
- renders four independent spark sprites, one randomized inside each screen corner region;
- chooses an independent `textures/particle/electric_spark_1.png` through `_27.png` frame, size, position, and alpha for each corner every two ticks;
- keeps the sprites relatively small instead of stretching the 256x256 texture fullscreen, preserving the crisp pixel-lightning shape;
- adds deterministic pitch/yaw/roll jitter through `ViewportEvent.ComputeCameraAngles`;
- uses stronger overlay/camera values when amplifier `> 1`, matching the `SHOCKED_LONG` selection;
- disables the vanilla POV effect when `av_epicfight` or `annoyingvillagers_epicfight` is loaded so the compatibility mod can own player animation/camera behavior.

If the final AV_EFM addon uses a different mod id, update the two checks in `ElectrifyScreenEffect.useVanillaPlayerShockFx()`.

## Hurt-stable stun animations

The following stun/reaction animations must continue playing when the mob takes additional damage instead of entering the normal chained hit reaction:
- `STUN_BACK`
- `SUPER_KNOCK_BACK`
- `LEGENDARY_SWORD_KNOCKDOWN`
- `SHOCKED`
- `SHOCKED_LONG`

`RigStunController` checks both its tracked `StunState.animationId` and `RigAnimationController.getActiveAnimationId(mob)`. Checking the active animation is required because `STUN_BACK` can be started directly by systems such as `TotemUsingEvent` rather than by the stun-state map. Damage itself is not cancelled; only `applyStun(...)` / `applyHitAnimation(...)` refuse to replace one of these protected clips with the generic hit chain.
