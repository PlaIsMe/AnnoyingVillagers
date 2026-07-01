# Player NPC Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/entity/PlayerNpcEntity.java`
- `src/main/java/com/pla/annoyingvillagers/clazz/FakePlayer.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/FakePlayerRenderer.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/FakePlayerTextureUtils.java`
- `src/main/java/com/pla/annoyingvillagers/client/renderer/FakePlayerCapeLayer.java`
- `src/main/java/com/pla/annoyingvillagers/event/NpcGearLoadEvent.java`
- `src/main/java/com/pla/annoyingvillagers/util/EquipmentDataLoader.java`
- `src/main/java/com/pla/annoyingvillagers/util/InventoryUtils.java`
- `src/main/java/com/pla/annoyingvillagers/util/BowFunction.java`
- `src/main/java/com/pla/annoyingvillagers/util/CombatBehaviour.java`
- `src/main/java/com/pla/annoyingvillagers/combatbehaviour/CombatCommon.java`
- `src/main/java/com/pla/annoyingvillagers/entity/goal/FillWaterBucketGoal.java`
- `src/main/java/com/pla/annoyingvillagers/event/AnnoyingVillagersCommandEvent.java`
- `src/main/resources/data/annoyingvillagers/mobs_equipment/*.json`

## Entity Shape

`PlayerNpcEntity` extends `FakePlayer` and implements `RangedAttackMob`.

`FakePlayer` extends `PathfinderMob`, so Player NPC uses normal pathfinder mob spawning and navigation behavior instead of zombie-specific surface-spawn behavior.

`PlayerNpcEntity` persists custom state such as inventory, cooldowns, target type, main/offhand weapon snapshots, bow usage, block projectile chance, and disarmed state.

## Spawn Command

`AnnoyingVillagersCommandEvent` registers:

```mcfunction
/annoyingvillagers spawn_player <name>
```

The command requires permission level 2. It creates `PLAYER_NPC`, moves it to the command source position/rotation, calls `entity.setUsername(name)`, then calls `finalizeSpawn` with `MobSpawnType.COMMAND`, adds the entity to the level, and reports the spawned name.

Because the username is set before `finalizeSpawn`, the command-provided name drives the Minecraft profile, skin, and cape lookup.

## Fake Player Name System

`FakePlayer` owns the synced username field:

```java
private static final EntityDataAccessor<String> NAME
```

If no username exists during `FakePlayer.finalizeSpawn`, it assigns a hardcoded random name.

Names are represented by `FakePlayer.FakePlayerName`.

`FakePlayerName` accepts either:

- `skinName`
- `skinName:displayName`

`skinName` is used for Minecraft profile lookup. `displayName` is used for entity display if present. If no display name is present, display falls back to skin name.

The hardcoded name list is:

`Gory_Moon`, `Darkosto`, `Darkere`, `Darkhax`, `Emberwalker`, `Gigabit101`, `Kamefrede`, `KnightMiner_`, `Lat`, `LexManos`, `Mrbysco`, `P3pp3rF1y`, `Ray`, `Ridanis`, `SOTMead`, `ShyNieke`, `SkySom`, `Soaryn`, `ValkyrieofNight`, `XCompWiz`, `DaReal_BingoBear`, `darkphan`, `direwolf20`, `dmodoomsirius`, `dmodoomsirius`, `malte0811`, `nekosune`, `neptunepink`, `vadis365`, `wyld`, `paulsoaresjr`, `Mhykol`, `Vswe`, `TurkeyDev`, `Gen_Deathrow`, `Sevadus`.

Random-name algorithm:

1. `NAME_POOL` is a queue.
2. If the queue is empty, copy the hardcoded list.
3. Shuffle it with `Collections.shuffle(shuffled, new java.util.Random(random.nextLong()))`.
4. Add all shuffled names to the queue.
5. Poll one name.
6. If polling somehow returns null, fallback to `Steve`.
7. When a name is explicitly used, `useName` removes it from the queue to reduce duplicate immediate reuse.

Username/profile data is saved to NBT:

- `Username`
- complete `Profile`, when available

On load, `FakePlayer` restores `Username`, restores `Profile` when present, or chooses a hardcoded name server-side when no username exists.

## Minecraft Profile, Skin, Cape, And Elytra Fetch

`setUsername` resets profile and texture state when the name changes, then calls `getProfile`.

`getProfile` creates:

```java
new GameProfile(null, this.getUsername().getSkinName())
```

Then it queues the entity in `PROFILE_QUEUE`.

The background profile updater runs on daemon thread:

```java
"AnnoyingVillagers FakePlayer Profile Updater"
```

It calls:

```java
SkullBlockEntity.updateGameprofile(currentProfile, target::setProfile)
```

That completes the Minecraft/Mojang profile for the skin name. `setProfile` stores the completed profile and clears cached texture state.

Client texture lookup is in `FakePlayerTextureUtils`.

Skin type:

- `getPlayerSkinType(profile)` uses `Minecraft.getInstance().getSkinManager().getInsecureSkinInformation(profile)`.
- It checks the skin texture metadata `model`.
- `model=slim` uses slim player model.
- Otherwise it uses default player model.
- If no skin texture exists, it falls back to `DefaultPlayerSkin.getSkinModelName(id)`.

Skin texture:

- `getPlayerSkin(entity)` tries the entity cached SKIN texture first.
- If profile is complete, fallback is `DefaultPlayerSkin.getDefaultSkin(profile.getId())`.
- If no complete profile exists, fallback is vanilla default skin.

Cape texture:

- `getPlayerCape(entity)` fetches CAPE texture through the same helper.
- `FakePlayerCapeLayer` renders the cape only if the entity is visible, cape texture exists, and the chest slot is not an elytra.

Texture registration:

```java
MinecraftProfileTexture profileTexture =
    minecraft.getSkinManager().getInsecureSkinInformation(profile).get(type);
ResourceLocation location = minecraft.getSkinManager().registerTexture(profileTexture, type);
entity.setTexture(type, location);
```

`FakePlayer` caches SKIN, CAPE, and ELYTRA `ResourceLocation`s and availability booleans.

## Renderer

`FakePlayerRenderer` uses a `PlayerModel`.

It keeps both default and slim player models. Each render call checks the skin type, swaps the model, and swaps matching armor layer variants.

It sets hand pose from the mainhand item:

- crossbow charging/holding pose for crossbow
- bow pose for aggressive bow use
- item pose for other held items

The renderer scales the entity by `0.9375F`.

Main texture location is `FakePlayerTextureUtils.getPlayerSkin(entity)`.

## Gear Reloading

`AnnoyingVillagers` registers `NpcGearLoadEvent` on the Forge event bus.

`NpcGearLoadEvent.onAddReloadListeners` adds:

```java
new EquipmentDataLoader()
```

`EquipmentDataLoader` is a `SimpleJsonResourceReloadListener` rooted at:

```text
mobs_equipment
```

It loads JSON files from:

```text
src/main/resources/data/annoyingvillagers/mobs_equipment/*.json
```

The JSON file path becomes the default namespace. For example:

- `minecraft.json` turns `"diamond_sword"` into `minecraft:diamond_sword`
- `epicfight.json` turns `"diamond_greatsword"` into `epicfight:diamond_greatsword`
- `annoyingvillagers.json` turns `"knife"` into `annoyingvillagers:knife`

If an item id already contains `namespace:path`, it is used as-is.

The loader skips a whole file when `ModList.get().isLoaded(modId)` is false.

Supported slots:

- `MAINHAND`
- `OFFHAND`
- `HEAD`
- `CHEST`
- `LEGS`
- `FEET`

Gear entries can be strings or objects.

String entry:

```json
"stone_sword"
```

This defaults to minimum difficulty EASY.

Object entry:

```json
{ "id": "diamond_sword", "min_difficulty": "HARD" }
```

Invalid object entries are skipped. Unknown `min_difficulty` logs a warning and defaults to EASY.

All candidate item ids are validated against `ForgeRegistries.ITEMS`.

## Player NPC Gear Application

`PlayerNpcEntity.finalizeSpawn` calls:

```java
EquipmentDataLoader.getEquipCommands(0.85f, this)
```

The returned commands are executed as the entity with suppressed output and permission 4:

```java
item replace entity @s <slot> with <item>{Damage:<damage>}
```

Slot mapping:

- `MAINHAND` -> `weapon.mainhand`
- `OFFHAND` -> `weapon.offhand`
- `HEAD` -> `armor.head`
- `CHEST` -> `armor.chest`
- `LEGS` -> `armor.legs`
- `FEET` -> `armor.feet`

Damageable gear receives random damage between 1/3 and 3/4 of max durability.

After commands execute, Player NPC snapshots current mainhand and offhand into:

- `mainWeaponItem`
- `offWeaponItem`

`PlayerNpcEntity.finalizeSpawn` also calls `PlayerNpcEntity.seedInventory()`.

`PlayerNpcEntity.seedInventory()` owns the spawn inventory seed directly. It first checks that the 27 slot container is empty, then rolls progression-scaled food, arrows, pearls, water bucket access, block stacks, and material loot inside `PlayerNpcEntity` itself. `InventoryUtils` is only used for low-level inventory operations such as adding, checking, consuming, and dropping items.

## Inventory Backed Combat Supplies

Player NPC has a 27 slot `SimpleContainer` inventory that saves to and loads from NBT.

The entity exposes two inventory APIs:

- `hasInventoryItem(...)`
- `consumeInventoryItem(...)`

Both have predicate and exact-item overloads and delegate to `InventoryUtils`.

On spawn, an empty Player NPC inventory receives a difficulty-scaled hardcoded random utility loadout:

- EASY: 0-5 golden apples, no enchanted golden apples, two regular food stacks of 12-20 each, no arrows, no ender pearls, no water bucket, and 2-4 random block stacks of 8-16 each.
- MEDIUM: 8-16 golden apples, no enchanted golden apples, two regular food stacks of 20-32 each, 12-32 arrows, 0-12 ender pearls, one water bucket, and 2-4 random block stacks of 16-32 each.
- HARD: 16-32 golden apples, 0-6 enchanted golden apples, two regular food stacks of 32-64 each, 48-96 arrows, 16-32 ender pearls, one water bucket, and 2-4 random block stacks of 32-96 each.

The same seeding pass builds a progression-scaled material candidate pool, then randomly adds zero to two material types total:

- EASY: often no material loot; otherwise small coal and/or iron ingot counts.
- MEDIUM: coal and iron, with chances for gold ingots and redstone.
- HARD: larger coal, iron, gold, and redstone counts, plus lapis lazuli and chances for diamonds and emeralds.

Player NPC continues to pick up nearby items into this same container. Food, arrows, ender pearls, buckets, and block items can refill combat supplies after spawn.

Inventory-backed actions:

- bow behavior requires arrows in inventory,
- each bow shot consumes one arrow-like item from inventory,
- ender pearl throws require and consume one ender pearl,
- eating requires one available food item and consumes it only after the eating animation completes,
- regular food heals and gives short regeneration but no absorption,
- golden apples keep their special absorption/regeneration behavior,
- water bucket self-extinguish consumes a water bucket and returns a water bucket only when the placed source is recovered, otherwise it returns an empty bucket,
- empty buckets can be refilled by `FillWaterBucketGoal` from nearby source water when Player NPC has no active target,
- block escape/parry placement requires block items and consumes one block per placed block.

Player NPC death loot is inventory-backed. `PlayerNpcEntity.dropCustomDeathLoot` drops only remaining container contents, and the old delayed `PlayerNpcDeadEvent` generated item drops are skipped for Player NPC.

## Gear Difficulty Filtering

Equipment entries are stored as:

```java
EquipmentEntry(String itemId, Difficulty minDifficulty)
```

An item is available when:

```java
currentDifficulty.ordinal() >= minDifficulty.ordinal()
```

This means higher difficulty can still roll lower-difficulty gear.

Current equip chances:

- EASY mainhand: 15%
- EASY offhand: 3%
- EASY armor slot: 8%
- MEDIUM mainhand: 65%
- MEDIUM offhand: 35%
- MEDIUM armor slot: 45%
- HARD mainhand: 95%
- HARD offhand: 100%
- HARD armor slot: caller base chance, currently 85%

This makes EASY Player NPC spawns very likely to have empty armor and empty hands.

## Armor Set Matching

Armor slots can loosely match previous armor pieces.

The loader detects armor prefixes by suffix:

- head: `helmet`
- chest: `chestplate`
- legs: `leggings`, `legging`
- feet: `boots`, `boot`

When a previous armor item exists, there is a random 30-50% chance to prefer an item with the same prefix in the next armor slot.

## Offhand Generation

The loader can generate offhand items from the selected mainhand.

Main sources:

- bound offhand weapon map for specific custom weapons
- shield if EpicFight capability says the weapon can use shield
- mirrored or related weapon if the weapon can two-hand
- optional dual axe / dual greatsword support when those mods are loaded

Generated offhand pools are filtered by inferred minimum difficulty.

The inferred difficulty uses item id path keywords:

- HARD: `netherite`, `diamond`, `unlight`, `ruby`, `exterminator`, `blackscratcher`, `laevateinn`, `moon_blade`, `armblade`
- MEDIUM: `iron`, `gold`, `chainmail`, `turtle`, `jade`, `red_axe`
- EASY: everything else

Some weapons are blacklisted from random offhand generation, including moon blades, armblade, claw, cleaver, sabre, blackscratcher, warblade, and laevateinn variants.

## Spawn Rules

`PlayerNpcEntity.canSpawn` rejects spawning at night:

```java
if (serverLevel.isNight()) {
    return false;
}
```

Otherwise it delegates to:

```java
PathfinderMob.checkMobSpawnRules(...)
```
