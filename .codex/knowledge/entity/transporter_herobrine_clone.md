# Transporter Herobrine Clone Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/TransporterHerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrineMob.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrinePortalSupportCaster.java`
- `src/main/java/com/pla/annoyingvillagers/util/CommonGoals.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrineUtil.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineSupportEscapePortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineProjectileCounterPortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineSupportApproachPortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineLowCloneSupportGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrinePortalDangerousReactionGoal.java`

## Role

`TransporterHerobrineCloneEntity` extends `HerobrineMob` but is a support/portal unit, not a normal melee fighter. It implements `HerobrinePortalSupportCaster` and normally carries `TRANSPORTER_FRAGMENT`.

Its support filter is broader than Greg's and can support normal `HerobrineMob` entities plus both low-clone classes.

## Cooldowns

Transporter uses:

- shared `portalActionCooldown`: random 20-45 seconds;
- rare `lowCloneSupportCooldown`: random 90-180 seconds.

Keep the older low-health self-escape lifecycle separate from these combat-support cooldowns.

## Animation Rules

AV_EFM comments should map:

- `PORTAL_SUMMON` -> `AnimsSculkSteve.PORTAL_SUMMON`;
- `POINT_LEFT_HAND_TOWARD` -> `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP`.

Transporter low-clone summons use `PORTAL_SUMMON` and perform at tick 20.

Two-portal support escape, projectile counter, approach, and self dangerous-reaction portal actions use `POINT_LEFT_HAND_TOWARD`.

## Goal Registration

Transporter adds:

- priority `-5`: `HerobrineSupportEscapePortalGoal`;
- priority `-4`: `HerobrineProjectileCounterPortalGoal`;
- priority `-3`: `HerobrineSupportApproachPortalGoal`;
- priority `-2`: `HerobrineLowCloneSupportGoal`;
- priority `2`: its own `SafeCombatPositionGoal`.

Common goal registration supplies portal-aware dangerous reaction at priority `-7`.

## Standalone Low-Clone Summoning

Transporter must be able to summon combat low clones whether or not it is linked to/supporting another Herobrine.

`HerobrineUtil.findLowCloneSupportPlan(...)` resolves Transporter plans in this order:

1. use a supported Herobrine's live enemy when available;
2. otherwise use Transporter's own valid target;
3. otherwise search for a nearby valid enemy within the support radius and use Transporter itself as the anchor.

Therefore absence of a linked/supported Herobrine is not a blocker. The existing randomized 90-180 second low-clone cooldown and support-slot limits still apply.

## SafeCombatPositionGoal

Transporter's supporter movement may move Transporter itself but must not command the supported Herobrine. Portal entrances for supported allies are based on the ally's existing authored movement/target path.

## Low-Health Legacy Escape

Transporter's older low-health escape remains separate from combat support. Current behavior includes the 10% health threshold, escape timing/retry lifecycle, fishing-hook cancellation handling, and ordinary Transporter Fragment drop behavior.

## Utility Ownership

All live support/portal helpers now live in `HerobrineUtil`.

`HerobrineSupportPortalUtil.java` and `HerobrinePortalCombatUtil.java` are fully commented legacy snapshots only.
