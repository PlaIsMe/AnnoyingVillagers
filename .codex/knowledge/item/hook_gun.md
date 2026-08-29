# Hook Gun Session Knowledge

This file records session knowledge for the `hook_gun` item, `HookGunHookEntity`, item-bound interactions, rendering, crosshair behavior, and NPC usage.

## Source Scope

Session facts in this file come from the current workspace code and the edits discussed in this session. Relevant implementation files:

- `src/main/java/com/pla/annoyingvillagers/item/HookGunItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/HookGunHookEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/HookUtil.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/HookGunItemRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/HookGunHookRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/HookItemRenderTransforms.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/HookGunCrosshairRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/ItemProjectileRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/event/SpecialAttackOnKeyPressedEvent.java`
- `src/main/java/com/pla/annoyingvillagers/util/HookGunCombatUtil.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationController.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationId.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigAnimationSpecs.java`

Hook gun motor constants, double-hook behavior, hook and rope rendering, and dual crosshair logic are adapted from the local `Grappling-Hook-Mod-Reforged-main` source by yyonne, GPL-3.0. The session explicitly used the local fixed source, not the old GitHub link.

## Item Identity And Assets

The hook gun item is `AnnoyingVillagersModItems.HOOK_GUN`.

Important assets:

- item model wrapper: `assets/annoyingvillagers/models/item/hook_gun.json`
- rendered body model: `assets/annoyingvillagers/models/item/hook_gun_body.json`
- item texture: `assets/annoyingvillagers/textures/item/hook_gun.png`
- rope texture: `assets/annoyingvillagers/textures/entities/hook_gun_rope.png`

`hook_gun.json` uses `builtin/entity` and delegates actual custom rendering to `HookGunItemRenderer`.

`HookGunItem` stacks to 1 and has durability 384.

## Hook Gun NBT

Hook gun item stack tags:

- `HookGunBoundItem`: stored bound item stack
- `HookGunVisualHookOut`: client/server render marker that hides the attached bound item while the hook is out

Owner persistent-data tags for hook hand animation:

- `HookGunLeftHookAnimation`
- `HookGunRightHookAnimation`

`getBoundItem(ItemStack)` returns the stored bound item or empty.

`setBoundItem(ItemStack hookGunStack, ItemStack boundItem)` stores one copy of the bound item.

`clearBoundItem` removes both bound item and visual-hook-out state.

## Binding Flow

Player binding is triggered from `SpecialAttackOnKeyPressedEvent` before the rest of special attack item handling.

Server-side:

- `HookGunItem.tryBindFromSpecialAttack(player)` is called
- if it returns true, a 2 tick delayed `playHookGunAnimation(player)` hook is invoked; in the current non-EpicFight repo that player animation helper is still a vanilla-animation TODO, while the commented `// Add this in AV_EFM` block preserves the old Epic Fight animation reminder

Binding rules:

- If main hand is hook gun and off hand is not, bind or unbind using off hand as source.
- If off hand is hook gun and main hand is not, bind or unbind using main hand as source.
- If both hands are hook guns, special attack binding does nothing.
- If source hand is empty and hook gun has a bound item, unbind and return the item to inventory or drop it.
- If source hand contains an item that is not a hook gun, bind one copy of that item.
- If the hook gun already had a bound item, the previous bound item is returned after the new item is bound.

Previous bound item return order:

1. Try stacking or placing into inventory away from the source hand.
2. If source hand is empty, put it there.
3. Otherwise add to inventory or drop.

Creative players do not consume the source item.

## Player Use Flow

Right click calls `HookGunItem.use`.

Client-side:

- if both hands hold hook guns and an active hook exists, swing both hands
- if no launchable bound item exists, return pass
- if both hands hold hook guns, swing launchable hands
- return sided success for visual feedback

Server-side:

- if any active hook exists, `useHookGun` returns hooks instead of launching
- if no active hook exists, it launches one or two hooks depending on held hook guns and bound items
- if a new launch happened, item cooldown is 8 ticks
- item use stat is awarded

The hook gun does not launch if the relevant hook gun has no bound item.

## Single And Dual Hook Launch

Single hook:

- uses the hand passed to `use`
- launches only when that hook gun has a non-empty bound item
- right hand flag is true for main hand and false for off hand

Dual hook:

- active when both hands hold hook guns
- each hand launches only if its hook gun has a bound item
- if both are launchable, off hand launches left with negative angle and main hand launches right with positive angle
- normal double hook angle is 20 degrees
- crouching double hook angle is 10 degrees

Launch constants:

- `THROW_SPEED = 2.0`
- `MAX_ROPE_LENGTH = 30.0`
- `HOOK_DESPAWN_DISTANCE = 42.0`
- `USE_COOLDOWN_TICKS = 8`

Aim target is raycast from the owner's eye to `HOOK_DESPAWN_DISTANCE` using block collider clipping. The launch direction is from hook start position to that hit location, which improves aiming compared with simply using eye look direction.

## Hook Start Position

In the current non-EpicFight repository, `HookGunItem.getHookStartPosition(LivingEntity owner, boolean rightHand)` uses the vanilla/entity-space fallback directly. The old Epic Fight joint placement is retained only as commented compatibility code under the `// Add this is AV_EFM` reminder (`toolR` / `toolL`, translation `(0.0F, -0.3F, 0.0F)`).

Current non-EpicFight start position:

- owner eye position
- forward by `0.45`
- side offset `+0.35` for right hand or `-0.35` for left hand
- Y offset `-0.18`

This same start position is used for hook launch, returning hook target, and rope rendering.

## Hook Projectile State

`HookGunHookEntity` synced data:

- owner entity id
- attached flag
- double mode flag
- right hand flag
- returning flag
- bound item stack
- anchor X/Y/Z

Saved NBT also stores owner UUID, attached state, double mode, right hand, returning, bound stack, anchor, grapple attach time, and grapple return delay.

Projectile constants:

- `HOOK_GRAVITY = 0.02`
- `AIR_DRAG = 0.99`
- `ENTITY_YANK_SCALE = 0.40`
- `RETURN_SPEED = 2.4`
- `RETURN_ARRIVE_DISTANCE = 0.55`
- `MAX_FLYING_LIFE = 80`
- `MAX_GRAPPLE_FLYING_LIFE = 60`
- attached grapple return delay is 30 to 50 ticks

## Hook Projectile Tick Flow

Each tick:

1. Server validates owner is alive and still holding a hook gun.
2. Server discards if the hook is too far from owner.
3. If returning, move toward `HookGunItem.getHookStartPosition`.
4. If attached, stay at synced anchor, disable gravity and physics, and for pickaxe hooks return after the attached delay.
5. If flying too long, start returning.
6. If bound item is empty bucket, run source-fluid special hit detection.
7. If bound item is bone meal, run sapling outline special hit detection.
8. Otherwise use normal projectile hit detection.
9. Move by current motion, update rotation, apply air drag and small gravity.

The bone meal special hit path only returns block hits for `BlockTags.SAPLINGS`, so saplings can be hit even though they are non-solid.

## Pickaxe Grapple Behavior

`HookGunHookEntity.isGrappleHook()` is true when `HookUtil.isPickaxe(boundItem)` returns true.

Pickaxe recognition includes:

- vanilla `PickaxeItem`
- Forge pickaxe dig tool action

When a pickaxe hook hits a block:

- it only attaches if the block is not air and has collision
- anchor is set to the hit location
- attached flag is true
- delta movement is zero
- gravity and physics are disabled
- attached return timer starts

When a pickaxe hook hits an entity:

- it pulls the target toward the owner
- pull vector uses owner eye/body center toward target body center
- Y pull is capped at 1.2
- target fall distance is reset
- hook returns

Motor movement on the owner is applied by `HookGunItem.tickMotor` while a pickaxe hook is attached:

- only attached grapple hooks count
- pull acceleration is `0.20 / grappleHookCount`
- max motor speed along pull is 4.0
- rope length correction starts past 30 blocks
- if close to anchor and colliding or on ground, motion is damped
- fall distance is reset

This is the motor-enabled grappling behavior. One hook uses single motor behavior; two hook guns can produce double motor behavior.

## Non-Grapple Return Behavior

Non-pickaxe bound items do not attach and do not motor-pull the shooter. On block or entity hit, the item effect runs through `HookUtil`, the hook starts returning, and the rope retracts visually because the projectile moves back to the hook start position.

If the hook reaches the owner within `RETURN_ARRIVE_DISTANCE`, it clears the hook gun visual-out flag and discards.

## Entity Hit Item Behavior

`HookUtil.handleEntityHitWithResult` controls bound item behavior on living targets.

General filters:

- empty bound item, dead target, or spectator target passes
- hitting the owner is treated as handled
- allied targets are blocked unless the item is allowed on allies

Items allowed on allies:

- armor
- water bucket
- snowball
- potion
- food

Entity hit item effects:

- spawn egg: spawns the egg entity type at target block position and consumes one egg if spawn succeeds
- egg: plays egg throw sound and has vanilla-like chance to hatch baby chickens, then consumes one egg
- shears: shears `IForgeShearable` entities, drops shear drops, damages shears
- empty bucket: buckets bucketable entity only if nearby source water is picked up; water is consumed
- water bucket: clears fire on burning target and becomes empty bucket
- snowball: clears fire, applies Slowness 100 ticks amplifier 1, consumes one snowball
- shield: deals 15 thrown damage, applies long stun through Epic Fight if possible, damages shield, plays blunt hit sound
- weapon-like item: deals calculated item attack damage plus enchantment damage using a normal thrown damage source, applies fire aspect and post-damage enchant effects, damages the tool, and plays the AV clash sound
- armor: equips target if target slot is empty, blacklisting `NullWeapon`, `BlueDemonEntity`, and `ArmoredHerobrineEntity`
- potion: applies all potion effects, instant effects use projectile and owner, flash potions spawn flash particles, consumes potion
- food: heals normal targets by nutrition and can apply food effects; inverted-heal targets take magic damage instead; consumes food
- fire charge: sets target on fire for 8 seconds and consumes one
- flint and steel: sets target on fire for 8 seconds and damages the tool

Weapon-like recognition includes swords, axes, hoes, shovels, pickaxes, and Forge tool actions. Epic Fight weapon categories are not required in the non-EpicFight repository.

## Block Hit Item Behavior

`HookUtil.handleBlockHitWithResult` controls bound item behavior on block hits.

Block hit item effects:

- spawn egg: spawns entity at hit block or adjacent face and consumes one egg if spawn succeeds
- egg: can hatch chickens at hit location and consumes one egg
- shears: if target block is leaves, drops resources, removes leaves, damages shears
- fire charge: ignites TNT or places fire, then consumes one
- bucket: empty bucket fills from source fluid or nearby bucketable fish; filled bucket empties fluid and returns empty bucket
- flint and steel: ignites TNT or places fire, damages tool
- bone meal: applies bonemeal to any `BonemealableBlock`, consumes on success
- block item: places the block at hit position if replaceable or adjacent face if valid, consumes one

Empty bucket fluid pickup uses both normal block hit handling and a special projectile source-fluid clip so water and lava sources can be taken reliably. When water is picked up, nearby bucketable entities can convert the result into a fish bucket after the water source is consumed.

## Bucket Behavior

Empty bucket:

- on entity hit, can bucket a `Bucketable` target only after picking up nearby source water
- on block hit, uses `BucketPickup.pickupBlock`
- consumes source water or lava through the block's pickup behavior
- if water was picked up, checks for nearby bucketable entity and can return its bucket item stack

Filled bucket:

- if the bucket fluid is empty, passes
- otherwise attempts `BucketItem.emptyContents`
- can place into liquid container blocks or adjacent face
- runs bucket extra content for fish buckets
- returns empty bucket on success

Water bucket against a burning living target clears fire and returns empty bucket.

## Fire, TNT, And Bone Meal

Flint and steel:

- entity hit burns target for 8 seconds
- block hit ignites TNT or places fire
- damages tool on success

Fire charge:

- entity hit burns target for 8 seconds and consumes one
- block hit ignites TNT or places fire charge fire and consumes one

Bone meal:

- block hit applies to `BonemealableBlock`
- consumes one on successful bonemeal
- hook projectile has special outline detection for saplings
- Jev's AI only targets saplings, not crops, for bone meal support

## Food, Potions, Armor, And Eggs

Food:

- entity hit heals target by at least 1 or nutrition value
- applies food effects by their chance
- inverted-heal targets take magic damage instead
- consumes one

Potions:

- entity hit applies potion effects
- instant effects use the projectile and owner for instant potion calculation
- custom flash potion detection is description id containing `"flash"`
- flash potions spawn one `FLASH` particle at target eye
- consumes one

Armor:

- entity hit equips the armor slot if empty
- mobs get drop chance 1.0 for that slot
- consumes one
- blacklisted targets are handled but not changed

Eggs:

- spawn eggs spawn their entity and consume on success
- normal eggs play sound, have a 1 in 8 chance to hatch, and a 1 in 32 chance inside that hatch to create 4 chickens instead of 1

## Rendering: Hook Gun Item

`HookGunItemRenderer` renders the hook gun item.

Flow:

1. Load and render baked model `item/hook_gun_body`.
2. Read bound item from `HookGunItem.getBoundItem(stack)`.
3. If no bound item, stop.
4. If `HookGunVisualHookOut` is true, stop.
5. If this exact hand currently has an active hook, stop.
6. Apply attachment transform.
7. Render the bound item attached to the gun.

This means a hook gun with a bound item shows that item while idle, but shows only the normal hook gun body while the hook is shot out.

Bound attachment transform constants:

- translate X `0.9`
- translate Y `0.1`
- translate Z `0.55`
- shield extra Z offset `0.25`
- rotate Z `-90`
- scale `0.65`

Attachment display context:

- shields use `ItemDisplayContext.NONE`
- other items use `ItemDisplayContext.GUI`

## Rendering: Hook Projectile And Rope

`HookGunHookRenderer` renders both the thrown bound item and the rope.

If owner exists:

- hand position is `HookGunItem.getHookStartPosition(owner, hook.isRightHand())`
- hook item is rendered with direction based on hook movement or line to hand
- rope is drawn from hook position to hand position

If owner is missing, only the hook item is rendered.

Rope render:

- texture `textures/entities/hook_gun_rope.png`
- render type `entitySolid`
- segment is a four-sided prism
- rope radius basis uses `0.025`
- `shouldRender` always returns true
- culling box also spans hook to owner eye in the entity

Projectile item scale:

- normal hook gun projectile scale `0.5`
- shield projectile scale `1.0`

Projectile display context:

- shields use `ItemDisplayContext.NONE`
- custom 3D sharp models use `ItemDisplayContext.FIXED`
- sharp items generally use `ItemDisplayContext.NONE`
- non-sharp items use `ItemDisplayContext.FIXED`

## Rendering: Item Alignment

`HookItemRenderTransforms` is shared by hook gun projectile rendering and normal `ItemProjectileRenderer`.

Projectile facing:

- yaw applies `yaw - 90`
- pitch applies negative pitch around Z
- sharp items receive extra model alignment

Sharp alignment recognizes:

- swords
- axes
- hoes
- shovels
- pickaxes
- Forge tool actions

Sharp item alignment constants:

- normal sword projectile roll `-45`
- custom 3D fixed positive Y roll `-45`
- custom 3D fixed negative Y roll `-135`
- pickaxe and hoe roll `-90`, pitch `-45`
- axe yaw `45`
- shovel roll `-45`

Block items render without projectile spin.

Shields use special facing:

- hook gun projectile rotates shield to face owner look yaw
- shield display transform can be compensated from the fixed transform
- shield projectile scale is kept original size at `1.0`

## Rendering: ItemProjectile Compatibility

`ItemProjectileRenderer` also uses `HookItemRenderTransforms`.

For sharp item projectiles, the renderer points the blade toward movement direction. If the item projectile is hook-attached, direction is from rope owner eye position to projectile position. This keeps vanilla and modded swords/tools more consistently blade-forward.

For non-sharp loose projectiles, it applies randomized spin. For block items attached to hooks, it avoids spin.

## AV_EFM Rendering Compatibility

The non-EpicFight repository no longer depends on `PatchedItemInHandLayerMixin`. `HookGunItemRenderer` still exposes `setRenderedHandContext(...)` / `clearRenderedHandContext()` for hand-aware rendering, and it also has player-hand fallbacks when no explicit render context is set.

If AV_EFM later needs Epic Fight's patched hand layer again, add that integration in the compatibility module and keep the core renderer usable without Epic Fight. Preserve compatibility comments with the project reminder style rather than reintroducing a hard dependency.

## Dual Crosshair

`HookGunCrosshairRenderer` runs after the vanilla crosshair overlay.

It only draws extra crosshairs when:

- camera is first person
- player is not spectator
- debug screen is not taking over
- player holds hook guns in both hands

It computes FOV projection and `HookGunItem.getDoubleHookAngle(player)` to place one extra crosshair left and one extra crosshair right of center.

Normal angle is 20 degrees. Crouching angle is 10 degrees.

## Hook Hand Animations

Hook hand animation state is owned by `HookGunItem` and updated server-side on living tick. This is shared by players and NPC hook sessions and avoids duplicating hook pose selection in Alex/Jev combat logic.

Persistent owner tags:

- `HookGunLeftHookAnimation`
- `HookGunRightHookAnimation`

Animation states:

- none
- normal
- top

Top is selected when hook direction Y is greater than `0.55` or the direction dot owner look is below `-0.20`, meaning the hook is high or behind the owner.

`getHookHandAnimation(boolean rightHand, byte state)` is the authoritative non-EpicFight mapping:

- left normal -> `RigAnimationId.LEFT_HAND_HOOK`
- left top -> `RigAnimationId.LEFT_HAND_HOOK_TOP`
- right normal -> `RigAnimationId.RIGHT_HAND_HOOK`
- right top -> `RigAnimationId.RIGHT_HAND_HOOK_TOP`

The four poses are static/repeating-equivalent hook poses. The non-EpicFight rig system uses `RigAnimationController.playHeldPose(...)` so the pose remains active until an explicit stop instead of expiring after the ordinary 20-tick animation spec. The held implementation sends a long-lived client duration and `RigAnimationController.stop(...)` sends duration `0` to explicitly remove the active pose.

`LEFT_HAND_HOOK` and `LEFT_HAND_HOOK_TOP` use `RigAnimationPlaybackType.LEFT_HAND`. `RIGHT_HAND_HOOK` and `RIGHT_HAND_HOOK_TOP` use `RigAnimationPlaybackType.MAIN_HAND`.

The hook pose is canceled when the owner stops holding that hook gun, the hook is missing/removed/returning, or active hooks are returned. If a combat-profile rig attack temporarily replaces the pose, `HookGunItem` does not interrupt that profile attack just to change the held pose; it can restore the proper hook pose afterward.

Keep Epic Fight equivalents only in the commented AV_EFM compatibility block beside `getHookHandAnimation(...)` and the stop logic.

## NPC Hook Session Behavior

NPCs do not right click hook guns directly. `HookGunCombatUtil` temporarily places hook guns in their hands.

Shared flow:

- reject if bound item is empty, level is client, session is active, or owner has active hook
- decide whether to play the hook-gun draw/aim animation based on last bound item and consumable status; for NPCs this uses `RigAnimationId.POINT_LEFT_HAND_TOWARD`
- save original main/off hand items
- equip hook gun bound to selected item
- swing hand(s)
- delay 7 ticks
- aim at target
- call `HookGunItem.launchHookAt`
- play arrow shoot sound
- monitor active hook
- force return stale pickaxe hooks
- restore original hand items

Pickaxe-bound hooks wait for attach and use longer grapple restore timing. Other items just hit and return.

## Alex And Jev Usage Summary

Alex uses hook gun for:

- default enchanted iron pickaxe grapple
- pickaxe entity pull
- enchanted diamond sword shots
- flint and steel burn shots in state 1
- dual hook in state 1 after second hook gun unlock
- hook to Jev's death position

Jev uses hook gun for:

- non-enchanted iron pickaxe hook away
- pulling Alex to Jev
- pulling enemies to Jev
- snowball support when Alex burns
- healing and buffing Alex only when Alex is missing health
- enemy harassment with bad potions, food, flint and steel, and fire charge
- cover and distraction block placement
- bone meal on visible saplings only
- hook to Alex's death position

## Non-EpicFight Alex/Jev Port Notes

The old EpicFight branch executed Jev hook support from `JevPatch.tick()`. In the non-EpicFight repository, both NPCs must enter the shared helper from their entity ticks:

- `AlexEntity.tick()` -> `HookGunCombatUtil.tickAlex(this, serverLevel)`
- `JevEntity.tick()` -> `HookGunCombatUtil.tickJev(this, serverLevel)`

The Jev call is essential. Porting the helper without restoring this tick entry point leaves Jev able to follow Alex but unable to run hook support.

`HookGunCombatUtil.playHookGunAnimation(...)` uses `RigAnimationId.POINT_LEFT_HAND_TOWARD` for the current non-EpicFight NPC draw/aim animation. Do not add a new `POINT_HAND_TOWARD` enum value.

Keep the persistent hook rope poses in `HookGunItem.getHookHandAnimation(...)`; do not duplicate the four-way mapping in `HookGunCombatUtil`, `AlexEntity`, or `JevEntity`.

## AV_EFM Compatibility Reminder

Keep compatibility placeholders in source using the established reminder, for example:

```java
// add this in AV_EFM
```

Future AV_EFM code should restore Epic Fight joint positioning, `HOOK_GUN`, and `HOOK_HAND_*` equivalents beside the vanilla rig implementation. The core non-EpicFight hook gun must continue to compile and work without Epic Fight classes.

## Knowledge Maintenance Rule

When future work ports hook-gun or related Alex/Jev logic from the Epic Fight branch into the non-EpicFight repository, read `knowledge/item/hook_gun.md`, `knowledge/entity/alex.md`, `knowledge/entity/jev.md`, plus any other knowledge files for touched systems before editing. After the port, update all relevant knowledge files with the resulting non-EpicFight behavior and compatibility decisions.

