package com.pla.annoyingvillagers.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public final class FakePlayerTextureUtils {
    private static final Map<UUID, SkinType> SKIN_TYPE_CACHE = new ConcurrentHashMap<>();

    private FakePlayerTextureUtils() {
    }

    public static SkinType getPlayerSkinType(GameProfile profile) {
        if (!isComplete(profile)) {
            return SkinType.DEFAULT;
        }

        UUID id = profile.getId();
        SkinType cached = SKIN_TYPE_CACHE.get(id);
        if (cached != null) {
            return cached;
        }

        Optional<PlayerSkin> resolvedSkin = getResolvedSkin(profile);
        if (resolvedSkin.isEmpty()) {
            return SkinType.DEFAULT;
        }
        SkinType type = resolvedSkin.get().model() == PlayerSkin.Model.SLIM
                ? SkinType.SLIM
                : SkinType.DEFAULT;
        SKIN_TYPE_CACHE.put(id, type);
        return type;
    }

    public static ResourceLocation getPlayerSkin(FakePlayer entity) {
        return getTexture(entity, MinecraftProfileTexture.Type.SKIN).orElseGet(() -> {
            GameProfile profile = entity.getProfile();
            if (isComplete(profile)) {
                return DefaultPlayerSkin.get(profile).texture();
            }
            return DefaultPlayerSkin.getDefaultTexture();
        });
    }

    public static Optional<ResourceLocation> getPlayerCape(FakePlayer entity) {
        return getTexture(entity, MinecraftProfileTexture.Type.CAPE);
    }

    private static Optional<ResourceLocation> getTexture(FakePlayer entity, MinecraftProfileTexture.Type type) {
        if (entity.isTextureAvailable(type)) {
            return Optional.ofNullable(entity.getTexture(type));
        }

        GameProfile profile = entity.getProfile();
        if (!isComplete(profile)) {
            return Optional.empty();
        }

        PlayerSkin playerSkin = getResolvedSkin(profile).orElse(null);
        if (playerSkin == null) {
            // The lookup is asynchronous. Do not permanently cache the temporary default skin.
            return Optional.empty();
        }
        ResourceLocation location = switch (type) {
            case SKIN -> playerSkin.texture();
            case CAPE -> playerSkin.capeTexture();
            case ELYTRA -> playerSkin.elytraTexture();
        };
        if (location == null) {
            return Optional.empty();
        }

        entity.setTexture(type, location);
        return Optional.of(location);
    }

    private static Optional<PlayerSkin> getResolvedSkin(GameProfile profile) {
        CompletableFuture<PlayerSkin> lookup = Minecraft.getInstance().getSkinManager().getOrLoad(profile);
        return Optional.ofNullable(lookup.getNow(null));
    }

    private static boolean isComplete(GameProfile profile) {
        return profile != null && profile.getId() != null && profile.getName() != null;
    }

    public enum SkinType {
        DEFAULT,
        SLIM
    }
}
