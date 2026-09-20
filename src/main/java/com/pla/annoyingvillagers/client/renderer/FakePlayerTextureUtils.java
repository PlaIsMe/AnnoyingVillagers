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

        PlayerSkin skin = Minecraft.getInstance().getSkinManager().createLookup(profile, false).get();
        SkinType type = skin.model() == PlayerModelType.SLIM ? SkinType.SLIM : SkinType.DEFAULT;
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

        Minecraft minecraft = Minecraft.getInstance();
        PlayerSkin playerSkin = minecraft.getSkinManager().createLookup(profile, false).get();
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
        return isComplete(profile)
                ? Minecraft.getInstance().getSkinManager().createLookup(profile, false).get()
                : DefaultPlayerSkin.getDefaultSkin();
    }

    private static boolean isComplete(GameProfile profile) {
        return profile != null && profile.id() != null && profile.name() != null;
    }

    public enum SkinType {
        DEFAULT,
        SLIM
    }
}
