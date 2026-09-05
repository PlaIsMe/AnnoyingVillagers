# Herobrine Greg Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/HerobrineGregEntity.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/HerobrinePortalSupportCaster.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrineUtil.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineGregSixPortalSupportGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineSupportEscapePortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineProjectileCounterPortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineSupportApproachPortalGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrineLowCloneSupportGoal.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/HerobrinePortalDangerousReactionGoal.java`
- `src/main/java/com/pla/annoyingvillagers/spawnhandler/GregData.java`

## Base Role

`HerobrineGregEntity` extends `Monster`, implements `RigStunnableEntity`, `DangerousReaction`, and `HerobrinePortalSupportCaster`, and remains a supporter/caster rather than a normal melee Herobrine.

Natural singleton ownership still uses `GregData`.

Greg's ordinary support filter accepts normal `HerobrineMob` variants but excludes Transporter and both low-clone classes.

## Cooldowns

Greg uses two combat-support cooldowns:

- shared `portalActionCooldown`: random 20-40 seconds for normal/two-portal actions and six-portal support;
- `lowCloneSupportCooldown`: random 90-180 seconds for rare combat low-clone support.

There is no live `sixPortalSupportCooldown`. Old dedicated six-portal cooldown fields/methods/NBT may remain commented in source for recovery only.

## Animation Rules

`playPortalSupportAnimation(...)` uses native Rig animations. AV_EFM comments should map:

- `PORTAL_SUMMON` -> `AnimsSculkSteve.PORTAL_SUMMON`;
- `POINT_LEFT_HAND_TOWARD` -> `AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP`.

Greg uses `PORTAL_SUMMON` for:

- low-clone support;
- six-portal support.

Greg uses `POINT_LEFT_HAND_TOWARD` for:

- support escape/two-portal casts;
- projectile counter;
- approach portal;
- self dangerous-reaction portal.

## Goal Registration

Greg currently registers:

- priority `-7`: portal-aware dangerous reaction through common goal registration;
- priority `-6`: `HerobrineGregSixPortalSupportGoal`;
- priority `-5`: `HerobrineSupportEscapePortalGoal`;
- priority `-4`: `HerobrineProjectileCounterPortalGoal`;
- priority `-3`: `HerobrineSupportApproachPortalGoal`;
- priority `-2`: `HerobrineLowCloneSupportGoal`.

## Low-Clone Support

Greg's low-clone goal plays `PORTAL_SUMMON` and performs the actual summon at animation tick 20. It may spawn 1-3 temporary combat low clones up to Greg's support-slot limit and then starts the separate 90-180 second low-clone cooldown.

## Six-Portal Support

Six-portal support is owned by Greg's AI goal and is not requested by the Swordsman ULT.

`HerobrineGregSixPortalSupportGoal` uses Greg's normal support selection. The six-portal cast is legal only when:

- shared `portalActionCooldown <= 0`;
- Greg's currently selected support is a `SwordsmanHerobrineEntity`;
- that Swordsman is in state 2;
- that Swordsman's `gregUUID` equals this Greg's UUID;
- the shared `AbstractHerobrinePortalActionGoal` readiness checks also pass (server side, alive, AI enabled, not passenger, not stunned, no active Rig animation, and support portal action allowed).

Greg plays `PORTAL_SUMMON` first. At animation tick 20 the goal discards Greg's older owned portals and attempts the full six-portal batch. A successful batch is stored as the Swordsman's preferred Demoniac Voltage Reaver portal group, Greg is marked as supporting, and the shared random 20-40 second `portalActionCooldown` is reset.

The six-portal goal now includes temporary diagnostics under the log prefix `[AV SIX PORTAL DEBUG]`. The abstract portal goal reports the exact base readiness failure, the Greg goal reports six-portal-specific failures and lifecycle events, and `HerobrineGregEntity` emits a heartbeat every 40 ticks so there is still evidence when the goal selector never reaches the goal. Grep the server log for that prefix when diagnosing why the action does not start or does not reach tick 20.

Once a six-portal group exists, `EliteHerobrineSecondFormGoal` on the linked Swordsman can force the normal `SWORDSMAN_HEROBRINE_ULT` when the Swordsman's normal second-form action checks are ready. `SWORDSMAN_HEROBRINE_EXTRA_ULT` is not used for the prepared six-portal follow-up.

The old `canAnswerSixPortalSupportRequest()`, `trySixPortalSupport(...)`, and dedicated six-portal cooldown implementations are commented legacy/reference code only.

## Utility Ownership

All live Greg support discovery, portal geometry, low-clone support, and six-portal helper logic is now in `HerobrineUtil`.

`HerobrineSupportPortalUtil.java` and `HerobrinePortalCombatUtil.java` are fully commented legacy snapshots and must not be reactivated.
