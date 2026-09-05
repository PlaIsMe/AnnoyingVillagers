# Herobrine Portal Support Combat Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/util/HerobrineUtil.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrinePortalSupportCaster.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/AbstractHerobrinePortalActionGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineSupportEscapePortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineProjectileCounterPortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineSupportApproachPortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineLowCloneSupportGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrinePortalDangerousReactionGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineGregSixPortalSupportGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/EliteHerobrineSecondFormGoal.java`
- `src/main/java/com/pla/annoyingvillagers/item/TransporterFragmentItem.java`

`HerobrineSupportPortalUtil.java` and `HerobrinePortalCombatUtil.java` are now fully commented legacy snapshots only. Do not add live calls back to either class. Their live helper code was consolidated into `HerobrineUtil`.

## Architecture

Greg and Transporter implement `HerobrinePortalSupportCaster`. Portal behavior is split into focused Rig-aware goals while shared targeting, portal geometry, ownership, low-clone support, projectile routing, and Greg six-portal helpers live in `HerobrineUtil`.

Greg's normal/two-portal actions and six-portal support use the same `portalActionCooldown`, randomized to 20-40 seconds. Rare combat low-clone support uses its separate 90-180 second cooldown and dedicated clone slots.

There is no live `sixPortalSupportCooldown`; any old field/method/NBT for it is commented reference code only.

## Animation Rules

These animation rules are intentional and must stay consistent:

- summoning combat low clones: `RigAnimationId.PORTAL_SUMMON`;
- Greg six-portal support: `RigAnimationId.PORTAL_SUMMON`;
- two-portal support escape: `RigAnimationId.POINT_LEFT_HAND_TOWARD`;
- projectile counter portal: `RigAnimationId.POINT_LEFT_HAND_TOWARD`;
- support approach portal: `RigAnimationId.POINT_LEFT_HAND_TOWARD`;
- self dangerous-reaction/escape portal: `RigAnimationId.POINT_LEFT_HAND_TOWARD`.

AV_EFM compatibility comments should map `PORTAL_SUMMON` to `AnimsSculkSteve.PORTAL_SUMMON` and `POINT_LEFT_HAND_TOWARD` to `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP`.

## Abstract Portal Action Goal

`AbstractHerobrinePortalActionGoal` starts only server-side while the caster is alive, AI-enabled, not riding, not Rig-stunned, not already animating, and allowed to use support portal actions. It stops navigation, plays the selected Rig animation, keeps looking at the action target, and performs gameplay at the goal-specific animation tick.

Low-clone and Greg six-portal casts use `PORTAL_SUMMON` and perform at tick 20 so the visual cast starts before entities/portals appear.

## Two-Portal Actions

`HerobrineSupportEscapePortalGoal`, `HerobrineProjectileCounterPortalGoal`, `HerobrineSupportApproachPortalGoal`, and `HerobrinePortalDangerousReactionGoal` all use `POINT_LEFT_HAND_TOWARD`.

The support escape action places an entrance in the supported Herobrine's existing movement/retreat path and links it to a safe exit near the caster. The approach action places an entrance using the ally's existing movement/target direction and a safe target-side exit. Projectile counter redirects a real incoming arrow or an aimed bow attack. Dangerous reaction creates a self escape route and lets the caster's authored step-back movement enter it.

Shared geometry and ownership helpers for these actions live in `HerobrineUtil`.

## Low-Clone Support

`HerobrineLowCloneSupportGoal` uses `PORTAL_SUMMON` and performs at tick 20.

The helper can spawn 1-3 `LowHerobrineCloneEntity` / `LowShadowHerobrineCloneEntity` instances up to available support slots, assigns damaged combat gear, gives the clone a valid enemy target, puts it on the Herobrine team, and starts the separate low-clone cooldown.

Greg normally obtains the enemy from a supported Herobrine.

Transporter does not require a linked/supported Herobrine to summon low clones. Plan resolution is:

1. use a supported Herobrine and that ally's live enemy when available;
2. otherwise, for `TransporterHerobrineCloneEntity`, use Transporter's own valid target;
3. if Transporter has no current target, find a nearby valid enemy within the support search radius and use Transporter itself as the summon anchor.

This preserves standalone Transporter low-clone summoning behavior while retaining normal cooldown and slot limits.

## Greg Six-Portal Support

Six-portal support is no longer requested from the Swordsman's ULT hook.

`HerobrineGregSixPortalSupportGoal` is a real Greg AI goal at priority `-6`. Its six-portal-specific conditions are intentionally small:

- shared `portalActionCooldown == 0`;
- Greg's currently selected support from `findGregFollowSupportHerobrine()` is a `SwordsmanHerobrineEntity`;
- that Swordsman is state 2;
- that Swordsman's `gregUUID` equals this Greg's UUID.

The shared abstract goal additionally requires server side, alive, AI enabled, not riding, not Rig-stunned, no active Rig animation, and `canUseSupportPortalAction()`.

The goal plays `PORTAL_SUMMON`. At animation tick 20 it discards Greg's older owned portals, attempts the six-portal batch directly, stores a successful group as the Swordsman's preferred Demoniac Voltage Reaver portal group, marks Greg as supporting, and resets the shared random 20-40 second `portalActionCooldown`.

Temporary diagnostics use the prefix `[AV SIX PORTAL DEBUG]`. `AbstractHerobrinePortalActionGoal` exposes a single no-op `debugTrace(...)` hook; Greg's six-portal goal overrides it to log base readiness failures and lifecycle events. `HerobrineGregEntity` also emits a heartbeat every 40 ticks so debugging still shows Greg state even if goal arbitration prevents `canUse()` from being evaluated.

## Forced Swordsman ULT After Six Portals

`EliteHerobrineSecondFormGoal` owns the follow-up decision. The old direct call to `requestGregSixPortalSupport()` in `SWORDSMAN_HEROBRINE_ULT` remains commented out and must not be restored.

When the Swordsman is state 2 and a ready six-portal group owned by the linked Greg exists:

- the normal second-form goal cooldown must already be ready;
- `canStartSecondFormAction()` must pass;
- animation selection is forced to `SWORDSMAN_HEROBRINE_ULT`;
- `SWORDSMAN_HEROBRINE_EXTRA_ULT` must not be selected for this six-portal follow-up;
- the current combat target must be within 12 blocks before normal ULT may start;
- before the normal ULT starts, the ready six-portal group is set as the preferred portal target.

Snake Blade's living-target scan radius remains 16 blocks. The 12-block cast gate intentionally leaves a 4-block margin. If normal ULT is selected while farther than 12 blocks, the goal clears that selection and retries after 10 ticks so normal movement can close distance.

The ULT's normal tick-0 Snake Blade start and second-form budget consumption remain unchanged.

## HerobrineUtil Ownership

All live portal combat/support utility code belongs in `HerobrineUtil`. This includes generic projectile/Snake Blade routing previously associated with the old portal-combat utility and Greg/Transporter support helpers previously associated with the support utility.

The two old utility Java files exist only as commented reference snapshots for recovery/comparison.

## Source Editing Convention

For new or changed Java in this portal/Herobrine area, keep method invocations compact on one line when practical, for example `method(a, b, c)`. Do not reformat a call into a vertical argument list such as one argument per line unless Java syntax genuinely requires it.

When an old implementation is retired during this refactor, preserve it as commented source for comparison/recovery rather than deleting it outright.
