# Inventory Viewer Item Knowledge

## Source Scope

- `src/main/java/com/pla/annoyingvillagers/item/InventoryViewerItem.java`
- `src/main/java/com/pla/annoyingvillagers/inventory/InventoryViewerMenu.java`
- `src/main/java/com/pla/annoyingvillagers/client/gui/InventoryViewerScreen.java`
- `src/main/java/com/pla/annoyingvillagers/init/AnnoyingVillagersModMenus.java`
- `src/main/java/com/pla/annoyingvillagers/init/AnnoyingVillagersModItems.java`
- `src/main/resources/assets/annoyingvillagers/models/item/inventory_viewer.json`
- `src/main/resources/assets/annoyingvillagers/lang/en_us.json`

## Behavior

`InventoryViewerItem` is registered as `annoyingvillagers:inventory_viewer`.

It is intentionally not added to `AnnoyingVillagersModCreativeTabs`, so it is only exposed through commands such as:

```mcfunction
/give @p annoyingvillagers:inventory_viewer
```

Right-clicking a supported living entity opens a custom Forge menu. The top of the screen follows Mob Battle's armor editor layout:

- main hand and offhand in the middle column
- helmet and chestplate on the left
- leggings and boots on the right

The supported entity's tracked 27 slot Annoying Villagers inventory is shown below that equipment panel, followed by the player inventory for moving items in and out.

Supported targets:

- `PlayerNpcEntity`
- any `AVNpc` subclass

Unsupported targets show a gray action-bar message.

The item model uses the vanilla stick texture through `minecraft:item/stick`.

## Notes

The custom inventory opens the real backing `SimpleContainer`, not a copied snapshot. The equipment slots are also live slots backed by `LivingEntity#getItemBySlot` and `LivingEntity#setItemSlot`, so armor and held items can be inspected and adjusted from the same viewer.
