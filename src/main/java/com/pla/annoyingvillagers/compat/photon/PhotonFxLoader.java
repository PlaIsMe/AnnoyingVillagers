package com.pla.annoyingvillagers.compat.photon;

import com.lowdragmc.lowdraglib2.Platform;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.lowdragmc.photon.client.fx.compat.FXCompat;
import com.lowdragmc.photon.gui.editor.FXProject;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.api.distmarker.Dist;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/** Loads both current Photon effects and the legacy effects exported by Photon for Minecraft 1.20.1. */
public final class PhotonFxLoader {
    private static final Map<Identifier, FX> LEGACY_CACHE = new HashMap<>();
    private static ResourceManager cachedResourceManager;

    private PhotonFxLoader() {
    }

    public static FX getFX(Identifier location) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        synchronized (LEGACY_CACHE) {
            if (cachedResourceManager != resourceManager) {
                LEGACY_CACHE.clear();
                cachedResourceManager = resourceManager;
            }

            FX cached = LEGACY_CACHE.get(location);
            if (cached != null) {
                return cached;
            }
        }

        FX loaded = loadLegacyFX(resourceManager, location);
        if (loaded == null) {
            return FXHelper.getFX(location);
        }

        synchronized (LEGACY_CACHE) {
            LEGACY_CACHE.put(location, loaded);
        }
        return loaded;
    }

    private static FX loadLegacyFX(ResourceManager resourceManager, Identifier location) {
        Identifier file = Identifier.fromNamespaceAndPath(
                location.getNamespace(), "fx/" + location.getPath() + ".fx");

        try (InputStream input = resourceManager.open(file)) {
            CompoundTag root = NbtIo.readCompressed(input, NbtAccounter.unlimitedHeap());
            if (!root.contains("fx")) {
                return null;
            }

            CompoundTag legacyFX = root.getCompound("fx").orElseGet(net.minecraft.nbt.CompoundTag::new);
            if (!legacyFX.contains("mainFX")) {
                return null;
            }

            CompoundTag fixedFX = FXCompat.mapEffect(root);
            fixedFX.putInt("version", FXProject.VERSION);

            FX fx = new FX();
            fx.setFxLocation(location);
            fx.deserializeNBT(Platform.getFrozenRegistry(), fixedFX);
            return fx;
        } catch (Exception exception) {
            AnnoyingVillagers.LOGGER.warn("Failed to migrate legacy Photon effect {} from {}", location, file, exception);
            return null;
        }
    }

}
