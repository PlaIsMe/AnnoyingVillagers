# Progression Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/clazz/Difficulty.java`
- `src/main/java/com/pla/annoyingvillagers/util/ProgressionData.java`
- `src/main/java/com/pla/annoyingvillagers/util/ProgressionUtil.java`
- `src/main/java/com/pla/annoyingvillagers/event/ProgressionEvent.java`
- `src/main/java/com/pla/annoyingvillagers/event/AnnoyingVillagersCommandEvent.java`

## Difficulty Enum

Progression uses:

```java
public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
}
```

`Difficulty.findByName` is case-insensitive.

`Difficulty.byName` returns EASY when the input does not match an enum value.

`Difficulty.id()` returns lowercase enum text through `Locale.ROOT`, so values become `easy`, `medium`, and `hard`.

## Saved Data

Progression state is stored in `ProgressionData`, a `SavedData`.

Storage name:

```java
annoyingvillagers_progression
```

It is stored through overworld data storage:

```java
server.overworld().getDataStorage().computeIfAbsent(...)
```

NBT fields:

- `Difficulty`
- `ManualDifficulty`

Default difficulty is EASY.

`Difficulty` stores the lowercase difficulty id.

`ManualDifficulty` records whether the current value was set manually through command.

## Setting And Increasing

`ProgressionData.setDifficulty(Difficulty difficulty)`:

- sets the exact requested difficulty
- marks `manualDifficulty = true`
- marks saved data dirty when value or manual state changes
- can set lower or higher values

`ProgressionData.increaseDifficulty(Difficulty difficulty)`:

- only raises when requested difficulty ordinal is higher than current difficulty ordinal
- never lowers difficulty
- sets `manualDifficulty = false` when automatic progression raises the stage

## Public API

`ProgressionUtil` exposes:

```java
isDifficulty(Difficulty difficulty)
isDifficulty(MinecraftServer server, Difficulty difficulty)
isAtLeastDifficulty(Difficulty difficulty)
isAtLeastDifficulty(MinecraftServer server, Difficulty difficulty)
getDifficulty(MinecraftServer server)
setDifficulty(MinecraftServer server, Difficulty difficulty)
increaseDifficulty(MinecraftServer server, Difficulty difficulty)
reconcileHistoricalProgression(MinecraftServer server)
reconcileHistoricalProgression(ServerPlayer player)
reconcileDragonFightProgression(MinecraftServer server)
```

The no-server `isDifficulty` and `isAtLeastDifficulty` variants use:

```java
ServerLifecycleHooks.getCurrentServer()
```

If there is no current server, they return false.

## Historical Progression

Historical progression exists for old worlds that already have relevant progress before this system was added.

Historical progression uses `increaseHistoricalDifficulty`.

`increaseHistoricalDifficulty` checks:

```java
if (!data.isManualDifficulty()) {
    data.increaseDifficulty(difficulty);
}
```

This prevents old-world reconciliation from immediately overwriting an admin command value.

Historical server reconciliation:

```java
reconcileHistoricalProgression(MinecraftServer server)
```

It checks historical dragon fight progress, then checks every currently connected player.

Historical player reconciliation raises MEDIUM if any of these are true:

- player is currently outside `Level.OVERWORLD`
- player has advancement `minecraft:story/enter_the_nether`
- player has advancement `minecraft:end/root`

Historical player reconciliation raises HARD if:

- player has advancement `minecraft:end/kill_dragon`

Historical dragon fight reconciliation raises HARD if:

- End level exists
- `EndDragonFight` exists
- `dragonFight.hasPreviouslyKilledDragon()` is true

## Live Progression Events

`ProgressionEvent` is a Forge bus subscriber.

Server started:

```java
ServerStartedEvent
```

Runs full historical reconciliation.

Server tick:

```java
TickEvent.ServerTickEvent
```

At END phase every 20 ticks, runs dragon fight reconciliation.

Player login:

```java
PlayerEvent.PlayerLoggedInEvent
```

Runs historical reconciliation for that player.

Player changed dimension:

```java
PlayerEvent.PlayerChangedDimensionEvent
```

Raises difficulty to MEDIUM with normal automatic progression:

```java
ProgressionUtil.increaseDifficulty(player.server, Difficulty.MEDIUM)
```

Ender dragon death event:

```java
LivingDeathEvent
```

If the dead entity is `EnderDragon` and the level is `ServerLevel`, raises difficulty to HARD.

Ender dragon death tick fallback:

```java
LivingEvent.LivingTickEvent
```

If the entity is `EnderDragon`, `dragon.dragonDeathTime > 0`, and level is `ServerLevel`, raises difficulty to HARD.

This catches dragon death timing where the normal death event may not persist the state early enough.

## Progression Levels

EASY:

- default stage
- no trigger needed

MEDIUM:

- live trigger: player changes dimension
- historical triggers: player outside overworld, Nether entry advancement, or End root advancement

HARD:

- live trigger: Ender Dragon death
- live fallback: Ender Dragon death-time tick
- historical triggers: End dragon fight previously killed dragon, or player kill-dragon advancement

## Commands

`AnnoyingVillagersCommandEvent` registers difficulty commands under:

```mcfunction
/annoyingvillagers difficulty
```

Permission requirement:

```java
source.hasPermission(2)
```

Get command:

```mcfunction
/annoyingvillagers difficulty get
```

Reports current difficulty id.

Set command:

```mcfunction
/annoyingvillagers difficulty set <easy|medium|hard>
```

Argument uses `StringArgumentType.word()`.

Suggestions are:

- `easy`
- `medium`
- `hard`

Invalid difficulty names fail with:

```text
Unknown Annoying Villagers difficulty: <name>
```

Valid set calls route through:

```java
ProgressionUtil.setDifficulty(source.getServer(), difficulty)
```

This marks the difficulty as manual so historical reconciliation does not immediately overwrite it.

Future live progression events can still raise the difficulty after a manual set.

