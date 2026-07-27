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

package com.pla.annoyingvillagers.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;

import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {

    @Inject(method = "put", at = @At("HEAD"))
    private static void addGlintTypes(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> mapBuildersIn, RenderType renderTypeIn, CallbackInfo callbackInfo) {
        ColoredGlintRenderTypes.registerIntoFixed(mapBuildersIn);
    }
}