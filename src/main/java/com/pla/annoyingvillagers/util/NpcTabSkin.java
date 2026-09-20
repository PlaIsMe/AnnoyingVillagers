package com.pla.annoyingvillagers.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.resources.Identifier;

import javax.annotation.Nullable;

/** Small, whitelisted skin identifiers carried in vanilla player-info profile properties. */
public enum NpcTabSkin {
    STEVE("steve"),
    ANGRY_STEVE("angry_steve"),
    ALEX("alex"),
    CHRIS("chris");

    private static final String PROPERTY = "annoyingvillagers:tab_skin";
    private final String key;
    private final Identifier texture;

    NpcTabSkin(String key) {
        this.key = key;
        this.texture = Identifier.fromNamespaceAndPath(
                "annoyingvillagers", "textures/entities/" + key + ".png");
    }

    public Identifier texture() { return texture; }

    public void apply(GameProfile profile) {
        profile.properties().removeAll(PROPERTY);
        profile.properties().put(PROPERTY, new Property(PROPERTY, key));
    }

    @Nullable
    public static NpcTabSkin forIdentity(String identity) {
        return switch (identity) {
            case "Steve" -> STEVE;
            case "Alex" -> ALEX;
            case "Chris" -> CHRIS;
            default -> null;
        };
    }

    @Nullable
    public static NpcTabSkin fromProfile(GameProfile profile) {
        if (!("zzAVN" + profile.id().toString().replace("-", ""))
                .substring(0, 16).equals(profile.name())) return null;
        for (Property property : profile.properties().get(PROPERTY)) {
            for (NpcTabSkin skin : values()) if (skin.key.equals(property.value())) return skin;
        }
        return null;
    }

    public static boolean isNpcTexture(Identifier texture) {
        for (NpcTabSkin skin : values()) if (skin.texture.equals(texture)) return true;
        return false;
    }
}
