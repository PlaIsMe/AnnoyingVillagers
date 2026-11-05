package com.pla.annoyingvillagers.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.resources.Identifier;

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

        UUID id = profile.id();
        SkinType cached = SKIN_TYPE_CACHE.get(id);
        if (cached != null) {
            return cached;
        }

        Optional<PlayerSkin> resolvedSkin = getResolvedSkin(profile);
        if (resolvedSkin.isEmpty()) {
            return SkinType.DEFAULT;
        }
        SkinType type = resolvedSkin.get().model() == PlayerModelType.SLIM
                ? SkinType.SLIM
                : SkinType.DEFAULT;
        SKIN_TYPE_CACHE.put(id, type);
        return type;
    }

    public static Identifier getPlayerSkin(FakePlayer entity) {
        return getTexture(entity, MinecraftProfileTexture.Type.SKIN).orElseGet(() -> {
            GameProfile profile = entity.getProfile();
            if (isComplete(profile)) {
                return DefaultPlayerSkin.get(profile).body().texturePath();
            }
            return DefaultPlayerSkin.getDefaultSkin().body().texturePath();
        });
    }

    public static Optional<Identifier> getPlayerCape(FakePlayer entity) {
        return getTexture(entity, MinecraftProfileTexture.Type.CAPE);
    }

    private static Optional<Identifier> getTexture(FakePlayer entity, MinecraftProfileTexture.Type type) {
        if (entity.isTextureAvailable(type)) {
            return Optional.ofNullable(entity.getTexture(type));
        }

        GameProfile profile = entity.getProfile();
        if (!isComplete(profile)) {
            return Optional.empty();
        }

        PlayerSkin playerSkin = getResolvedSkin(profile).orElse(null);
        if (playerSkin == null) {
            // Skin resolution and downloading are asynchronous. Never cache the temporary
            // default returned while the real premium-account texture is still loading.
            return Optional.empty();
        }
        Identifier location = switch (type) {
            case SKIN -> playerSkin.body().texturePath();
            case CAPE -> playerSkin.cape() == null ? null : playerSkin.cape().texturePath();
            case ELYTRA -> playerSkin.elytra() == null ? null : playerSkin.elytra().texturePath();
        };
        if (location == null) {
            return Optional.empty();
        }

        entity.setTexture(type, location);
        return Optional.of(location);
    }

    public static PlayerSkin getPlayerSkinData(FakePlayer entity) {
        GameProfile profile = entity.getProfile();
        Optional<PlayerSkin> resolvedSkin = isComplete(profile) ? getResolvedSkin(profile) : Optional.empty();
        PlayerSkin skin = resolvedSkin.orElseGet(() -> isComplete(profile)
                ? DefaultPlayerSkin.get(profile)
                : DefaultPlayerSkin.getDefaultSkin());
        if (resolvedSkin.isPresent()) {
            entity.setTexture(MinecraftProfileTexture.Type.SKIN, skin.body().texturePath());
            if (skin.cape() != null) entity.setTexture(MinecraftProfileTexture.Type.CAPE, skin.cape().texturePath());
            if (skin.elytra() != null) entity.setTexture(MinecraftProfileTexture.Type.ELYTRA, skin.elytra().texturePath());
        }
        return skin;
    }

    private static Optional<PlayerSkin> getResolvedSkin(GameProfile profile) {
        CompletableFuture<Optional<PlayerSkin>> lookup = Minecraft.getInstance().getSkinManager().get(profile);
        Optional<PlayerSkin> resolved = lookup.getNow(null);
        return resolved == null ? Optional.empty() : resolved;
    }

    private static boolean isComplete(GameProfile profile) {
        return profile != null && profile.id() != null && profile.name() != null;
    }

    public enum SkinType {
        DEFAULT,
        SLIM
    }
}
