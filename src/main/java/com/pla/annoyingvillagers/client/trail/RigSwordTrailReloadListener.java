package com.pla.annoyingvillagers.client.trail;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.Map;

/** Loads explicitly enabled sword trails and optionally reuses item-skin geometry/timing. */
@EventBusSubscriber(value = Dist.CLIENT, modid = AnnoyingVillagers.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class RigSwordTrailReloadListener extends SimpleJsonResourceReloadListener {
    public static final RigSwordTrailReloadListener INSTANCE = new RigSwordTrailReloadListener();
    private static final SwordTrailReloadListener SWORD_TRAIL_INSTANCE = new SwordTrailReloadListener();

    private volatile Map<ResourceLocation, RigSwordTrailDefinition> itemSkinDefinitions = Map.of();
    private volatile Map<ResourceLocation, RigSwordTrailDefinition> swordTrailDefinitions = Map.of();
    private volatile Map<ResourceLocation, RigSwordTrailDefinition> definitions = Map.of();

    private RigSwordTrailReloadListener() {
        super(new GsonBuilder().create(), "item_skins");
    }

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(INSTANCE);
        event.registerReloadListener(SWORD_TRAIL_INSTANCE);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, RigSwordTrailDefinition> loaded = new HashMap<>();
        objects.forEach((itemId, json) -> {
            try {
                if (!BuiltInRegistries.ITEM.containsKey(itemId)) return;
                RigSwordTrailDefinition definition = RigSwordTrailDefinition.fromItemSkin(json);
                if (definition != null) loaded.put(itemId, definition);
            } catch (RuntimeException exception) {
                AnnoyingVillagers.LOGGER.error("Failed to load rig sword trail from item skin {}", itemId, exception);
            }
        });
        this.itemSkinDefinitions = Map.copyOf(loaded);
        this.rebuildDefinitions();
        AnnoyingVillagers.LOGGER.info("Loaded {} rig sword trail definitions from item_skins", loaded.size());
    }

    private void applySwordTrails(Map<ResourceLocation, JsonElement> objects) {
        Map<ResourceLocation, RigSwordTrailDefinition> loaded = new HashMap<>();
        objects.forEach((itemId, json) -> {
            try {
                if (!BuiltInRegistries.ITEM.containsKey(itemId)) return;
                RigSwordTrailDefinition definition = RigSwordTrailDefinition.fromSwordTrail(json);
                if (definition != null) loaded.put(itemId, definition);
            } catch (RuntimeException exception) {
                AnnoyingVillagers.LOGGER.error("Failed to load rig sword trail color {}", itemId, exception);
            }
        });
        this.swordTrailDefinitions = Map.copyOf(loaded);
        this.rebuildDefinitions();
        AnnoyingVillagers.LOGGER.info("Loaded {} enabled rig sword trail definitions from sword_trails", loaded.size());
    }

    private void rebuildDefinitions() {
        // sword_trails is the opt-in list. An item_skins entry by itself must not
        // enable a trail; it only contributes its geometry/timing when the same
        // item also has an explicit sword_trails definition.
        Map<ResourceLocation, RigSwordTrailDefinition> merged = new HashMap<>();
        this.swordTrailDefinitions.forEach((itemId, swordTrail) -> {
            RigSwordTrailDefinition itemSkin = this.itemSkinDefinitions.get(itemId);
            if (itemSkin == null) {
                merged.put(itemId, swordTrail);
            } else {
                merged.put(itemId, itemSkin.withColor(swordTrail.red(), swordTrail.green(), swordTrail.blue()));
            }
        });
        this.definitions = Map.copyOf(merged);
    }

    public RigSwordTrailDefinition get(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return itemId == null ? null : this.definitions.get(itemId);
    }

    public ResourceLocation getItemId(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        return BuiltInRegistries.ITEM.getKey(stack.getItem());
    }

    private static final class SwordTrailReloadListener extends SimpleJsonResourceReloadListener {
        private SwordTrailReloadListener() {
            super(new GsonBuilder().create(), "sword_trails");
        }

        @Override
        protected void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager resourceManager, ProfilerFiller profiler) {
            INSTANCE.applySwordTrails(objects);
        }
    }
}
