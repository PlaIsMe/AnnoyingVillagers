# Low Herobrine Clone Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/LowHerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/entity/LowShadowHerobrineCloneEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/CommonGoals.java`

## LowHerobrineCloneEntity

`LowHerobrineCloneEntity` extends `FakePlayer`.

It registers hostile common goals through `CommonGoals.registerGoalForHostileNpc(this)`, so it now receives `PortalApproachGoal`.

It can follow a protect entity or possessed Herobrine when those fields are set.

Damage is reduced by half in normal cases, with special handling for healing/autokill states.

## LowShadowHerobrineCloneEntity

`LowShadowHerobrineCloneEntity` extends `Monster`.

It supports:

- `summoned`
- `initialSpawn`
- `forEscaping`
- `autoKill`
- protect entity/UUID
- possessed Herobrine entity/UUID
- portal-render flag

It can be used by Greg escape logic and by Transporter Herobrine Clone low-clone summoning.

## Portal Interaction

Both low clone classes can use common hostile portal approach logic:

- `LowHerobrineCloneEntity` through `CommonGoals.registerGoalForHostileNpc`
- `LowShadowHerobrineCloneEntity` through its Monster hostile goals

They can run into linked portals that exit near their target and can share Herobrine-side portals.
