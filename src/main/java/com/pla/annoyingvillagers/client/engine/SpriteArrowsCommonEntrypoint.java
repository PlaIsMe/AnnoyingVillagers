/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: MIT
 *
 * Upstream: Sprite Arrows - iliandev
 * Source: https://github.com/justliliandev/arrow-sprites
 *
 * This file contains code adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/MIT.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.client.renderer.SpriteArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;

import java.util.Map;

public class SpriteArrowsCommonEntrypoint {
    public static void replace() {
        Map<EntityType<?>, EntityRendererProvider<?>> providers = EntityRenderers.PROVIDERS;
        replaceForArrow(providers, EntityType.ARROW);
        replaceForArrow(providers, EntityType.SPECTRAL_ARROW);
    }

    @SuppressWarnings({"rawtypes"})
    private static <T extends AbstractArrow> void replaceForArrow(
            Map<EntityType<?>, EntityRendererProvider<?>> providers,
            EntityType<T> type) {
        if (!providers.containsKey(type)) {
            return;
        }
        providers.put(type, (EntityRendererProvider) SpriteArrowRenderer::new);
    }
}
