/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: GPL-3.0-or-later AND Apache-2.0
 *
 * Upstream: Skyfall: Meteorites - Yoshi01111
 * Source: https://www.curseforge.com/minecraft/mc-mods/skyfall-meteorites
 *
 * This file contains code adapted from the upstream project.
 * Required upstream notices must be preserved (including Apache-2.0 NOTICE if provided).
 *
 * License texts:
 *   - third_party/licenses/GPL-3.0.md
 *   - third_party/licenses/Apache-2.0.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelDragonMeteorite;
import com.pla.annoyingvillagers.entity.ObsidianSledgehammerProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ObsidianSledgehammerProjectileRenderer extends MobRenderer<ObsidianSledgehammerProjectileEntity, ModelDragonMeteorite<ObsidianSledgehammerProjectileEntity>> {

    public ObsidianSledgehammerProjectileRenderer(Context context) {
        super(context, new ModelDragonMeteorite<>(context.bakeLayer(ModelDragonMeteorite.LAYER_LOCATION)), 0.0F);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull ObsidianSledgehammerProjectileEntity obsidianSledgehammerProjectileEntity) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dragon_meteorite.png");
    }

    protected boolean isShaking(@NotNull ObsidianSledgehammerProjectileEntity obsidianSledgehammerProjectileEntity) {
        return true;
    }
}
