# Low Herobrine Clone Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/LowHerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/LowShadowHerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/CommonGoals.java`
- `src/main/java/com/pla/annoyingvillagers/rig/RigCombatProfiles.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrineUtil.java`

## Native Rig Combat Fix

The low clones are not `HerobrineMob` subclasses:

- `LowHerobrineCloneEntity` extends `FakePlayer`;
- `LowShadowHerobrineCloneEntity` extends `Monster` and implements `RigStunnableEntity` / `BurstProtectEntity`.

They previously fell through to vanilla `MeleeAttackGoal`, which is why empty-hand low clones appeared to have no Rig combat profile/attack playback.

`CommonGoals.supportsRigCombat(...)` now explicitly includes both `LowHerobrineCloneEntity` and `LowShadowHerobrineCloneEntity`. Their `CommonGoals.registerGoalForHostileNpc(this)` call therefore creates `RigAnimatedMeleeAttackGoal` and `RigShieldGuardGoal` where appropriate.

Both now have `ATTACK_DAMAGE = 5.0D`:

- Low Herobrine Clone: health 40, speed 0.45, armor 25, attack 5, follow range 48;
- Low Shadow Herobrine Clone: health 40, speed 0.30, armor 25, attack 5, follow range 24.

Profile resolution is equipment-driven through `RigCombatProfiles`:

- empty/non-profile main hand -> `UNARMED`;
- ordinary sword -> `BASIC`;
- two ordinary swords -> `DUAL_BASIC`;
- ordinary axe -> `AXE`;
- custom `RigCombatProfileProvider` item -> that item's Rig style.

Do not add a second custom attack goal to either low clone. Their existing hostile-goal registration is now sufficient for native Rig combat.

## LowHerobrineCloneEntity

`LowHerobrineCloneEntity` owns `summoned`, `initialSpawn`, `autoKill`, possession, healing/protect state, and `renderPortal` state.

Its custom `registerGoals()` clears the inherited goal selectors, installs protect/possessor follow behavior, then calls `CommonGoals.registerGoalForHostileNpc(this)`. This means hostile targeting and Rig melee are restored after the custom follow goals are installed.

Normal damage is reduced by half, with special handling around healing/autokill behavior.

Fall damage is explicitly allowed again. `hurt(...)` now passes `DamageTypes.FALL` directly to `super.hurt(...)` before healing/block logic, so Low Herobrine Clone takes normal fall damage. The previous fall-immunity line remains commented for recovery.

## LowShadowHerobrineCloneEntity

`LowShadowHerobrineCloneEntity` owns `summoned`, `initialSpawn`, `forEscaping`, `autoKill`, possession, sacrifice/healing/protect state, and `renderPortal` state.

Its goal registration installs protect/possessor follow behavior and then calls `CommonGoals.registerGoalForHostileNpc(this)`, so it also receives the explicit low-clone Rig combat path.

Its current native compatibility animations include the Rig `LOW_CLONE_ESCAPE` playback while `forEscaping` is active.

Low Shadow Herobrine Clone also takes normal fall damage through an early `super.hurt(...)` path for `DamageTypes.FALL`; the old explicit fall immunity remains commented only for recovery.

## Greg / Transporter Support Relationship

Greg deliberately does not select Low Herobrine Clone or Low Shadow Herobrine Clone as normal portal-support/follow targets.

Transporter Herobrine Clone can support both low-clone types.

Greg and Transporter can also create temporary combat low clones through `HerobrineLowCloneSupportGoal` / `HerobrineUtil`. The goal plays `RigAnimationId.PORTAL_SUMMON` and performs the actual summon at animation tick 20. The shared helper spawns 1-3 low clones near a support anchor, gives them damaged combat gear, marks them summoned, disables their initial portal-render flag, assigns the enemy target, and tracks them in the support caster's dedicated slots.

Transporter does not require a linked/support Herobrine for this action: if no supported ally provides an enemy, it may use its own target or find a nearby valid enemy and use itself as the summon anchor.

The low-clone support action has its own randomized 90-180 second cooldown and is intentionally separate from the normal 20-45 second portal-action cooldown.

## No Portal-Approach Movement Goal

Do not add `PortalApproachGoal` to these low clones. Support portals are positioned from the ally's existing movement/target direction; the supported low clone should continue its normal combat movement and naturally collide with a portal placed in its path.
