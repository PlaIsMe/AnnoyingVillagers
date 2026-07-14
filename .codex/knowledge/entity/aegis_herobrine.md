# Aegis Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/AegisHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Entity Role

`AegisHerobrineEntity` extends `HerobrineMob`.

It equips `ENDER_AEGIS` in the main hand and uses `AnimsEpicFight.SHIELD_MAINHAND` for init animation when its persistent init flag is set.

It has 250 health, high defensive attributes, and acts as a tanker/protector style elite Herobrine.

On death, it creates `EliteHerobrineKnockedEntity` with `FromElite = "EnderAegis"` and can notify Greg through `requestProtect`.

That helper spawns a linked portal pair near Aegis and near the ally. This lets Aegis use the portal route to protect or close distance.

## Portal Approach

Because Aegis extends `HerobrineMob`, it inherits the common portal approach goal and can run through linked portals whose exits are near its target.
