# Blue Demon

## Trident throw visibility

Projectile spawning and held-tool visibility are intentionally separate hooks.

At the old throw timestamp, a throw animation may schedule:
- the projectile spawn hook
- `hideRightToolAt(...)` and/or `hideLeftToolAt(...)`

The hide hook only affects rendering. Do not clear the mob's hand stack. This keeps later Blue Demon logic and item-state checks working while visually matching the old animation after the trident leaves the hand.

Current throw-side hiding:
- throw attack 1: hide right at tick 1
- throw attack 2: hide left at tick 12
- special legendary: hide left at tick 8
- throw attack 4: hide both at tick 9
- throw attack 5: hide right at tick 8
- throw dash: hide left at tick 3 and right at tick 5
- throw jump: hide both at tick 4

## Phase/state transition flow

The legacy `BlueDemonEntity` + `AnimsBlueDemonTrident` state machine is authoritative for the non-EpicFight port:

- `state == 0`: phase-one combat. A lethal hit is intercepted in `actuallyHurt(...)`; health is clamped to 1, both held Blue Demon tridents gain 100 storm energy, and `playTridentFestivalAnimation()` must start `RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL`.
- Festival tick 2 (`0.1s` in EpicFight): set `state = 1` and play the Trident Festival voice line. State 1 is the Festival transition state; `hurt(...)` blocks normal incoming damage while it is active.
- Festival tick 70 (`3.5s`): summon super lightning at grounded tridents, reset storm energy on both hands, call `beginStateTwoTransform()`, and replace Festival with `BLUE_DEMON_STATE_TRANSFORM`.
- `beginStateTwoTransform()`: back up the current target, retreat the sauce helpers, disable AI, reset `healingTick`, set `state = 2`, and set `stateTransformCooldown = 600`.
- `state == 2`: transformation/healing phase. Damage is blocked. While the 600-tick cooldown runs, Blue Demon heals 1 health every 2 ticks; at cooldown values 200/150/100/50 the existing damage-zone logic runs. `playStateTransformAnimation()` must not restart the same rig animation every tick.
- `stateTransformCooldown == 20`: play the phase-two release voice and replace the looping transform with `BLUE_DEMON_STATE_TRANSFORM_END`.
- `stateTransformCooldown == 10`: equip the current phase-two loadout: Legendary Sword in main hand and Blue Demon Trident in offhand, keeping the existing enchantment/effect logic.
- `stateTransformCooldown == 0`: `finishStateTwoTransform(...)` sets `state = 3`, restores AI and the backed-up target, equips the phase-two chestplate, seeds the weapon-swap cooldown, and brings in the phase-two sauce squad.
- `state == 3`: phase-two combat. A later lethal hit starts the final death sequence instead of Festival.

Do not confuse the states: Festival begins while state is still 0, changes to state 1 at tick 2, changes to state 2 at Festival tick 70, and only becomes state 3 after the 600-tick transform completes.

`BlueDemonEntity.playTridentFestivalAnimation()` is the bridge from the lethal-hit intercept into the rig timed-hook state machine. It must call `RigAnimationController.play(this, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL)`; leaving only the old EpicFight compatibility comment makes the phase transition dead code.

Keep the existing `// ADD THIS CODE IN AV_EFM` compatibility comments around EpicFight implementations when adding the vanilla rig path.

## Rig melee combat goal

`BlueDemonEntity` participates in the vanilla rig combat system the same way as `AVNpc` and `HerobrineMob`:
- `CommonGoals.supportsRigCombat(...)` includes `BlueDemonEntity`, so `registerGoalForBlueDemonNpc(...)` creates `RigAnimatedMeleeAttackGoal` instead of vanilla `MeleeAttackGoal`.
- `BlueDemonEntity` implements `LockableRigAttackAnimation` with the same lock-count semantics used by `AVNpc` and `HerobrineMob`. Do not replace this with a boolean lock.
- `RigCombatProfiles.getCombatProfile(...)` special-cases Blue Demon because the Blue Demon trident is a `SwordItem`; without the special case it would incorrectly resolve to generic `DUAL_BASIC`/`BASIC` combat.

Blue Demon uses two rig combat styles:
- `BLUE_DEMON`: normal chain `BLUE_DEMON_ATTACK1..6`; specials include Blue Demon dash/jump, extra attack, and throw attacks; ultimates are `BLUE_DEMON_ULT` and `BLUE_DEMON_THROW_ULT`.
- `BLUE_DEMON_LEGENDARY_SWORD`: the phase-two mixed Legendary Sword + Blue Demon Trident style, including `BLUE_DEMON_EXTRA_ATTACK_LEGENDARY`.

Phase two intentionally equips `LEGENDARY_SWORD` in the main hand and `BLUE_DEMON_TRIDENT` in the offhand. The mixed item pair resolves `BLUE_DEMON_LEGENDARY_SWORD` through `RigCombatProfileProvider` dual-style resolution; do not clear the offhand trident during the transform or when `rollItem()` switches back to Legendary Sword mode.

## 2026-08-26 current-source correction

The applied non-EpicFight source was checked again after Blue Demon still performed vanilla contact attacks. `BlueDemonEntity` already implemented `LockableRigAttackAnimation`, and the `BLUE_DEMON` / `BLUE_DEMON_LEGENDARY_SWORD` profiles already existed, but `CommonGoals.supportsRigCombat(...)` still only accepted `AVNpc` and `HerobrineMob`. This caused `registerGoalForBlueDemonNpc(...)` to create vanilla `MeleeAttackGoal`.

The required factory gate is:

```java
private static boolean supportsRigCombat(Mob mob) {
    return mob instanceof AVNpc || mob instanceof HerobrineMob || mob instanceof BlueDemonEntity;
}
```

Do not add a second melee goal directly to `BlueDemonEntity`; fix the shared factory gate so the existing Blue Demon goal registration creates `RigAnimatedMeleeAttackGoal`.

Current style names in source are `BLUE_DEMON` for the dual-trident moveset and `BLUE_DEMON_LEGENDARY_SWORD` for the phase-two Legendary Sword + Blue Demon Trident mixed style. Phase two should not use a single Legendary Sword anymore.

## RollItemUser weapon swapping

`BlueDemonEntity.rollItem()` is not a standalone tick method. In the non-EpicFight rig system it is driven by `RollItemGoal`, so Blue Demon must implement `RollItemUser` and register `new RollItemGoal(this)` at goal priority 1.

`canRollItem()` ports the old EpicFight `CombatCommon.canSwitchWeapon(...)` condition for Blue Demon: there must be a living combat target, Blue Demon must be in state 3, and `swapWeaponCooldown` must be 0. `finishStateTwoTransform(...)` already seeds the first swap cooldown with 200-600 ticks, so do not add another immediate phase-two swap trigger. `rollItem()` itself resets the later cooldown to 600-900 ticks.

Persist `SwapWeaponCooldown` in NBT. Otherwise reloading a state-3 Blue Demon resets the field to 0 and can cause an unintended immediate roll/swap.

The roll/switch animation is provided by `RollItemUser` defaults (`ROLL_BACKWARD` or `STEP_BACKWARD`), and `RollItemGoal` performs the actual `rollItem()` call after the configured switch delay. Keep `rollItem()` focused on equipment changes; do not duplicate animation scheduling inside it.

## Removed EFN guard-hit state

`efnGuardHitState`, `efnGuardHitCooldown`, `getEfnGuardHitState()`, `postPlayEfnGuardHit()`, and their tick reset/decrement logic were dead in the non-EpicFight source and were removed from `BlueDemonEntity`, `AVNpc`, and `HerobrineMob`. Do not reintroduce that state machine for vanilla rig hit/stun playback.

## Electrify ZAP rig port

The old `AnimsBlueDemonTrident.ZAP` / `ZAP_LONG` reaction is represented by the vanilla rig stun clips `RigAnimationId.SHOCKED` / `SHOCKED_LONG`, resolved from `StunAnimations2`.

`ElectrifyMobEffect` owns when the reaction is requested; Blue Demon combat animations do not schedule it themselves. Supported rig mobs route the reaction through `RigStunController.applyShock(...)` so movement/AI are locked for the authored stun duration and normal attack playback cannot immediately overwrite the shocked pose.

Current durations are `SHOCKED = 17 ticks` (`0.85s`) and `SHOCKED_LONG = 30 ticks` (`1.5s`). Electrify amplifier greater than 1 selects the long version. Keep these ids appended at the end of `RigAnimationId` because the enum ordinal is used as the network id.
