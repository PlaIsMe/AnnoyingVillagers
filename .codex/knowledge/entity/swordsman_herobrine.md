# Swordsman Herobrine Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/SwordsmanHerobrineEntity.java`
- `src/main/java/com/pla/annoyingvillagers/item/DemoniacVoltageReaverItem.java`
- `src/main/java/com/pla/annoyingvillagers/entity/SnakeBladeEntity.java`
- `src/main/java/com/pla/annoyingvillagers/util/HerobrinePortalCombatUtil.java`

## Entity Role

`SwordsmanHerobrineEntity` extends `HerobrineMob`.

It equips `DEMONIAC_VOLTAGE_REAVER` in the main hand in its constructor.

On tick 1, it clears the `SnakeAnimation` tag from its main hand item.

During state greater than 0, it spawns elite effects and sets the Demoniac Voltage Reaver `SecondForm` tag.

## Snake Blade Portal Support

`DemoniacVoltageReaverItem.process()` prioritizes the closest valid portal before normal living targets.

The session patch allows Swordsman Herobrine to use Herobrine-owned portals from Greg and Transporter Herobrine Clone through `HerobrinePortalCombatUtil.canUsePortalOwnedBy`.

When a support portal pair is created nearby, `HerobrinePortalCombatUtil.triggerSwordsmanSnakeBladeNear` can play `AVAnimations.SNAKE_BLADE` on the nearest valid Swordsman with a live target.

## Portal Approach

Because Swordsman extends `HerobrineMob`, it inherits common hostile goals and can run into a linked portal if the exit is near its target.

## Snake Blade Details

Detailed Demoniac Voltage Reaver and SnakeBladeEntity behavior is stored in `.codex/knowledge/entity/demoniac_voltage_reaver.md` and `.codex/knowledge/entity/snake_blade.md`.
