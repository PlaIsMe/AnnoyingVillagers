/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: CC-BY-NC-SA-3.0
 *
 * Upstream: Quark - Vazkii and contributors
 * Source: https://github.com/VazkiiMods/Quark
 *
 * This file contains code adapted from the upstream project (colored enchantment rendering).
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/CC-BY-NC-SA-3.0.md
 *
 * Modifications and integration:
 *   Copyright (c) 2025 pla_is_me
 */

package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class ColoredGlintRenderTypes extends RenderType {
    private ColoredGlintRenderTypes(String name, VertexFormat vf, VertexFormat.Mode mode,
                                    int bufSize, boolean affectsCrumbling, boolean sortOnUpload,
                                    Runnable setup, Runnable clean) {
        super(name, vf, mode, bufSize, affectsCrumbling, sortOnUpload, setup, clean);
        throw new UnsupportedOperationException("Don't instantiate");
    }

    private static RenderType glintDirect(String suffix, ResourceLocation tex) {
        return RenderType.create(
                "glint_direct_" + suffix,
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                256,
                false,
                false,
                CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_GLINT_DIRECT_SHADER)
                        .setTextureState(new TextureStateShard(tex, true, false))
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                        .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                        .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                        .createCompositeState(false)
        );
    }

    private static RenderType entityGlintDirect(String suffix, ResourceLocation tex) {
        return RenderType.create(
                "entity_glint_direct_" + suffix,
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                256,
                false,
                false,
                CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER)
                        .setTextureState(new TextureStateShard(tex, true, false))
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                        .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                        .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                        .createCompositeState(false)
        );
    }

    private static RenderType glint(String suffix, ResourceLocation tex) {
        return RenderType.create(
                "glint_" + suffix,
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                256,
                false,
                false,
                CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_GLINT_SHADER)
                        .setTextureState(new TextureStateShard(tex, true, false))
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                        .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                        .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                        .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                        .createCompositeState(false)
        );
    }

    private static RenderType entityGlint(String suffix, ResourceLocation tex) {
        return RenderType.create(
                "entity_glint_" + suffix,
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                256,
                false,
                false,
                CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_SHADER)
                        .setTextureState(new TextureStateShard(tex, true, false))
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                        .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                        .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                        .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                        .createCompositeState(false)
        );
    }

    private static RenderType armorEntityGlint(String suffix, ResourceLocation tex) {
        return RenderType.create(
                "armor_entity_glint_" + suffix,
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                256,
                false,
                false,
                CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER)
                        .setTextureState(new TextureStateShard(tex, true, false))
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                        .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                        .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                        .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                        .createCompositeState(false)
        );
    }

    public static RenderType getGlintDirect(int mode, RenderType fallback) {
        return switch (mode) {
            case ColoredGlintState.ORANGE -> GLINT_DIRECT_ORANGE;
            case ColoredGlintState.CYAN -> GLINT_DIRECT_CYAN;
            case ColoredGlintState.BLUE -> GLINT_DIRECT_BLUE;
            case ColoredGlintState.GREEN -> GLINT_DIRECT_GREEN;
            case ColoredGlintState.LIGHT_BLUE -> GLINT_DIRECT_LIGHT_BLUE;
            case ColoredGlintState.LIME -> GLINT_DIRECT_LIME;
            case ColoredGlintState.MAGENTA -> GLINT_DIRECT_MAGENTA;
            case ColoredGlintState.PINK -> GLINT_DIRECT_PINK;
            case ColoredGlintState.PURPLE -> GLINT_DIRECT_PURPLE;
            case ColoredGlintState.RED -> GLINT_DIRECT_RED;
            case ColoredGlintState.YELLOW -> GLINT_DIRECT_YELLOW;
            default -> fallback;
        };
    }

    public static RenderType getEntityGlintDirect(int mode, RenderType fallback) {
        return switch (mode) {
            case ColoredGlintState.ORANGE -> ENTITY_GLINT_DIRECT_ORANGE;
            case ColoredGlintState.CYAN -> ENTITY_GLINT_DIRECT_CYAN;
            case ColoredGlintState.BLUE -> ENTITY_GLINT_DIRECT_BLUE;
            case ColoredGlintState.GREEN -> ENTITY_GLINT_DIRECT_GREEN;
            case ColoredGlintState.LIGHT_BLUE -> ENTITY_GLINT_DIRECT_LIGHT_BLUE;
            case ColoredGlintState.LIME -> ENTITY_GLINT_DIRECT_LIME;
            case ColoredGlintState.MAGENTA -> ENTITY_GLINT_DIRECT_MAGENTA;
            case ColoredGlintState.PINK -> ENTITY_GLINT_DIRECT_PINK;
            case ColoredGlintState.PURPLE -> ENTITY_GLINT_DIRECT_PURPLE;
            case ColoredGlintState.RED -> ENTITY_GLINT_DIRECT_RED;
            case ColoredGlintState.YELLOW -> ENTITY_GLINT_DIRECT_YELLOW;
            default -> fallback;
        };
    }

    public static RenderType getGlint(int mode, RenderType fallback) {
        return switch (mode) {
            case ColoredGlintState.ORANGE -> GLINT_ORANGE;
            case ColoredGlintState.CYAN -> GLINT_CYAN;
            case ColoredGlintState.BLUE -> GLINT_BLUE;
            case ColoredGlintState.GREEN -> GLINT_GREEN;
            case ColoredGlintState.LIGHT_BLUE -> GLINT_LIGHT_BLUE;
            case ColoredGlintState.LIME -> GLINT_LIME;
            case ColoredGlintState.MAGENTA -> GLINT_MAGENTA;
            case ColoredGlintState.PINK -> GLINT_PINK;
            case ColoredGlintState.PURPLE -> GLINT_PURPLE;
            case ColoredGlintState.RED -> GLINT_RED;
            case ColoredGlintState.YELLOW -> GLINT_YELLOW;
            default -> fallback;
        };
    }

    public static RenderType getEntityGlint(int mode, RenderType fallback) {
        return switch (mode) {
            case ColoredGlintState.ORANGE -> ENTITY_GLINT_ORANGE;
            case ColoredGlintState.CYAN -> ENTITY_GLINT_CYAN;
            case ColoredGlintState.BLUE -> ENTITY_GLINT_BLUE;
            case ColoredGlintState.GREEN -> ENTITY_GLINT_GREEN;
            case ColoredGlintState.LIGHT_BLUE -> ENTITY_GLINT_LIGHT_BLUE;
            case ColoredGlintState.LIME -> ENTITY_GLINT_LIME;
            case ColoredGlintState.MAGENTA -> ENTITY_GLINT_MAGENTA;
            case ColoredGlintState.PINK -> ENTITY_GLINT_PINK;
            case ColoredGlintState.PURPLE -> ENTITY_GLINT_PURPLE;
            case ColoredGlintState.RED -> ENTITY_GLINT_RED;
            case ColoredGlintState.YELLOW -> ENTITY_GLINT_YELLOW;
            default -> fallback;
        };
    }

    public static RenderType getArmorEntityGlint(int mode, RenderType fallback) {
        return switch (mode) {
            case ColoredGlintState.ORANGE -> ARMOR_ENTITY_GLINT_ORANGE;
            case ColoredGlintState.CYAN -> ARMOR_ENTITY_GLINT_CYAN;
            case ColoredGlintState.BLUE -> ARMOR_ENTITY_GLINT_BLUE;
            case ColoredGlintState.GREEN -> ARMOR_ENTITY_GLINT_GREEN;
            case ColoredGlintState.LIGHT_BLUE -> ARMOR_ENTITY_GLINT_LIGHT_BLUE;
            case ColoredGlintState.LIME -> ARMOR_ENTITY_GLINT_LIME;
            case ColoredGlintState.MAGENTA -> ARMOR_ENTITY_GLINT_MAGENTA;
            case ColoredGlintState.PINK -> ARMOR_ENTITY_GLINT_PINK;
            case ColoredGlintState.PURPLE -> ARMOR_ENTITY_GLINT_PURPLE;
            case ColoredGlintState.RED -> ARMOR_ENTITY_GLINT_RED;
            case ColoredGlintState.YELLOW -> ARMOR_ENTITY_GLINT_YELLOW;
            default -> fallback;
        };
    }

    private static final ResourceLocation ORANGE_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_orange.png");

    private static final ResourceLocation CYAN_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_cyan.png");

    private static final ResourceLocation BLUE_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_blue.png");

    private static final ResourceLocation GREEN_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_green.png");

    private static final ResourceLocation LIGHT_BLUE_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_light_blue.png");

    private static final ResourceLocation LIME_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_lime.png");

    private static final ResourceLocation MAGENTA_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_magenta.png");

    private static final ResourceLocation PINK_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_pink.png");

    private static final ResourceLocation PURPLE_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_purple.png");

    private static final ResourceLocation RED_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_red.png");

    private static final ResourceLocation YELLOW_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_yellow.png");

    public static final RenderType GLINT_DIRECT_ORANGE = glintDirect("orange", ORANGE_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_ORANGE = entityGlintDirect("orange", ORANGE_TEX);
    public static final RenderType GLINT_ORANGE = glint("orange", ORANGE_TEX);
    public static final RenderType ENTITY_GLINT_ORANGE = entityGlint("orange", ORANGE_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_ORANGE = armorEntityGlint("orange", ORANGE_TEX);

    public static final RenderType GLINT_DIRECT_CYAN = glintDirect("cyan", CYAN_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_CYAN = entityGlintDirect("cyan", CYAN_TEX);
    public static final RenderType GLINT_CYAN = glint("cyan", CYAN_TEX);
    public static final RenderType ENTITY_GLINT_CYAN = entityGlint("cyan", CYAN_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_CYAN = armorEntityGlint("cyan", CYAN_TEX);

    public static final RenderType GLINT_DIRECT_BLUE = glintDirect("blue", BLUE_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_BLUE = entityGlintDirect("blue", BLUE_TEX);
    public static final RenderType GLINT_BLUE = glint("blue", BLUE_TEX);
    public static final RenderType ENTITY_GLINT_BLUE = entityGlint("blue", BLUE_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_BLUE = armorEntityGlint("blue", BLUE_TEX);

    public static final RenderType GLINT_DIRECT_GREEN = glintDirect("green", GREEN_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_GREEN = entityGlintDirect("green", GREEN_TEX);
    public static final RenderType GLINT_GREEN = glint("green", GREEN_TEX);
    public static final RenderType ENTITY_GLINT_GREEN = entityGlint("green", GREEN_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_GREEN = armorEntityGlint("green", GREEN_TEX);

    public static final RenderType GLINT_DIRECT_LIGHT_BLUE = glintDirect("light_blue", LIGHT_BLUE_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_LIGHT_BLUE = entityGlintDirect("light_blue", LIGHT_BLUE_TEX);
    public static final RenderType GLINT_LIGHT_BLUE = glint("light_blue", LIGHT_BLUE_TEX);
    public static final RenderType ENTITY_GLINT_LIGHT_BLUE = entityGlint("light_blue", LIGHT_BLUE_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_LIGHT_BLUE = armorEntityGlint("light_blue", LIGHT_BLUE_TEX);

    public static final RenderType GLINT_DIRECT_LIME = glintDirect("lime", LIME_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_LIME = entityGlintDirect("lime", LIME_TEX);
    public static final RenderType GLINT_LIME = glint("lime", LIME_TEX);
    public static final RenderType ENTITY_GLINT_LIME = entityGlint("lime", LIME_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_LIME = armorEntityGlint("lime", LIME_TEX);

    public static final RenderType GLINT_DIRECT_MAGENTA = glintDirect("magenta", MAGENTA_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_MAGENTA = entityGlintDirect("magenta", MAGENTA_TEX);
    public static final RenderType GLINT_MAGENTA = glint("magenta", MAGENTA_TEX);
    public static final RenderType ENTITY_GLINT_MAGENTA = entityGlint("magenta", MAGENTA_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_MAGENTA = armorEntityGlint("magenta", MAGENTA_TEX);

    public static final RenderType GLINT_DIRECT_PINK = glintDirect("pink", PINK_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_PINK = entityGlintDirect("pink", PINK_TEX);
    public static final RenderType GLINT_PINK = glint("pink", PINK_TEX);
    public static final RenderType ENTITY_GLINT_PINK = entityGlint("pink", PINK_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_PINK = armorEntityGlint("pink", PINK_TEX);

    public static final RenderType GLINT_DIRECT_PURPLE = glintDirect("purple", PURPLE_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_PURPLE = entityGlintDirect("purple", PURPLE_TEX);
    public static final RenderType GLINT_PURPLE = glint("purple", PURPLE_TEX);
    public static final RenderType ENTITY_GLINT_PURPLE = entityGlint("purple", PURPLE_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_PURPLE = armorEntityGlint("purple", PURPLE_TEX);

    public static final RenderType GLINT_DIRECT_RED = glintDirect("red", RED_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_RED = entityGlintDirect("red", RED_TEX);
    public static final RenderType GLINT_RED = glint("red", RED_TEX);
    public static final RenderType ENTITY_GLINT_RED = entityGlint("red", RED_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_RED = armorEntityGlint("red", RED_TEX);

    public static final RenderType GLINT_DIRECT_YELLOW = glintDirect("yellow", YELLOW_TEX);
    public static final RenderType ENTITY_GLINT_DIRECT_YELLOW = entityGlintDirect("yellow", YELLOW_TEX);
    public static final RenderType GLINT_YELLOW = glint("yellow", YELLOW_TEX);
    public static final RenderType ENTITY_GLINT_YELLOW = entityGlint("yellow", YELLOW_TEX);
    public static final RenderType ARMOR_ENTITY_GLINT_YELLOW = armorEntityGlint("yellow", YELLOW_TEX);

    public static void registerIntoFixed(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> map) {
        put(map,
                GLINT_DIRECT_ORANGE, ENTITY_GLINT_DIRECT_ORANGE, GLINT_ORANGE, ENTITY_GLINT_ORANGE, ARMOR_ENTITY_GLINT_ORANGE,
                GLINT_DIRECT_CYAN, ENTITY_GLINT_DIRECT_CYAN, GLINT_CYAN, ENTITY_GLINT_CYAN, ARMOR_ENTITY_GLINT_CYAN,
                GLINT_DIRECT_BLUE, ENTITY_GLINT_DIRECT_BLUE, GLINT_BLUE, ENTITY_GLINT_BLUE, ARMOR_ENTITY_GLINT_BLUE,
                GLINT_DIRECT_GREEN, ENTITY_GLINT_DIRECT_GREEN, GLINT_GREEN, ENTITY_GLINT_GREEN, ARMOR_ENTITY_GLINT_GREEN,
                GLINT_DIRECT_LIGHT_BLUE, ENTITY_GLINT_DIRECT_LIGHT_BLUE, GLINT_LIGHT_BLUE, ENTITY_GLINT_LIGHT_BLUE, ARMOR_ENTITY_GLINT_LIGHT_BLUE,
                GLINT_DIRECT_LIME, ENTITY_GLINT_DIRECT_LIME, GLINT_LIME, ENTITY_GLINT_LIME, ARMOR_ENTITY_GLINT_LIME,
                GLINT_DIRECT_MAGENTA, ENTITY_GLINT_DIRECT_MAGENTA, GLINT_MAGENTA, ENTITY_GLINT_MAGENTA, ARMOR_ENTITY_GLINT_MAGENTA,
                GLINT_DIRECT_PINK, ENTITY_GLINT_DIRECT_PINK, GLINT_PINK, ENTITY_GLINT_PINK, ARMOR_ENTITY_GLINT_PINK,
                GLINT_DIRECT_PURPLE, ENTITY_GLINT_DIRECT_PURPLE, GLINT_PURPLE, ENTITY_GLINT_PURPLE, ARMOR_ENTITY_GLINT_PURPLE,
                GLINT_DIRECT_RED, ENTITY_GLINT_DIRECT_RED, GLINT_RED, ENTITY_GLINT_RED, ARMOR_ENTITY_GLINT_RED,
                GLINT_DIRECT_YELLOW, ENTITY_GLINT_DIRECT_YELLOW, GLINT_YELLOW, ENTITY_GLINT_YELLOW, ARMOR_ENTITY_GLINT_YELLOW
        );
    }

    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> map, RenderType... types) {
        for (RenderType rt : types) {
            if (!map.containsKey(rt)) {
                map.put(rt, new BufferBuilder(rt.bufferSize()));
            }
        }
    }
}