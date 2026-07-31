package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.NullEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class NullWeaponRenderer extends RigMobRenderer<NullWeapon> {

    public NullWeaponRenderer(Context context) {
        super(context, false);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull NullWeapon nullWeapon) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/empty.png");
    }
}
