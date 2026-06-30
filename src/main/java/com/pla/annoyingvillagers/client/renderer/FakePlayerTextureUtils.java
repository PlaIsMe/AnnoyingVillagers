package com.pla.annoyingvillagers.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class FakePlayerTextureUtils {
    private static final Map<UUID, SkinType> SKIN_TYPE_CACHE = new ConcurrentHashMap<>();

    private FakePlayerTextureUtils() {
    }

    public static SkinType getPlayerSkinType(GameProfile profile) {
        if (profile == null || !profile.isComplete()) {
            return SkinType.DEFAULT;
        }

        UUID id = profile.getId();
        SkinType cached = SKIN_TYPE_CACHE.get(id);
        if (cached != null) {
            return cached;
        }

        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textures =
                Minecraft.getInstance().getSkinManager().getInsecureSkinInformation(profile);
        MinecraftProfileTexture skin = textures.get(MinecraftProfileTexture.Type.SKIN);
        SkinType type;
        if (skin != null) {
            type = "slim".equals(skin.getMetadata("model")) ? SkinType.SLIM : SkinType.DEFAULT;
        } else {
            type = "slim".equals(DefaultPlayerSkin.getSkinModelName(id)) ? SkinType.SLIM : SkinType.DEFAULT;
        }
        SKIN_TYPE_CACHE.put(id, type);
        return type;
    }

    public static ResourceLocation getPlayerSkin(FakePlayer entity) {
        return getTexture(entity, MinecraftProfileTexture.Type.SKIN).orElseGet(() -> {
            GameProfile profile = entity.getProfile();
            if (profile != null && profile.isComplete()) {
                return DefaultPlayerSkin.getDefaultSkin(profile.getId());
            }
            return DefaultPlayerSkin.getDefaultSkin();
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
        if (profile == null || !profile.isComplete() || profile.getName() == null) {
            return Optional.empty();
        }

        Minecraft minecraft = Minecraft.getInstance();
        MinecraftProfileTexture profileTexture =
                minecraft.getSkinManager().getInsecureSkinInformation(profile).get(type);
        if (profileTexture == null) {
            return Optional.empty();
        }

        ResourceLocation location = minecraft.getSkinManager().registerTexture(profileTexture, type);
        entity.setTexture(type, location);
        return Optional.of(location);
    }

    public enum SkinType {
        DEFAULT,
        SLIM
    }
}
