package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.client.layer.RigArmorLayer;
import com.pla.annoyingvillagers.client.layer.RigArrowLayer;
import com.pla.annoyingvillagers.client.model.ModelRig;
import com.pla.annoyingvillagers.client.model.ModelRigArmor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.Mob;

public abstract class RigMobRenderer<T extends Mob> extends HumanoidMobRenderer<T, ModelRig<T>> {

    protected RigMobRenderer(EntityRendererProvider.Context context) {
        this(context, true);
    }

    protected RigMobRenderer(EntityRendererProvider.Context context, boolean addArrowLayer) {
        this(context, addArrowLayer, ModelRig.LAYER_LOCATION, false);
    }

    protected RigMobRenderer(EntityRendererProvider.Context context, ModelLayerLocation layerLocation, boolean slim) {
        this(context, true, layerLocation, slim);
    }

    protected RigMobRenderer(EntityRendererProvider.Context context, boolean addArrowLayer, ModelLayerLocation layerLocation, boolean slim) {
        super(context, new ModelRig<>(context.bakeLayer(layerLocation), slim), 0.5F);
        this.addLayer(new RigArmorLayer<>(
                this,
                new ModelRigArmor<>(0.5F),
                new ModelRigArmor<>(1.0F),
                context.getModelManager()));
        if (addArrowLayer) {
            this.addLayer(new RigArrowLayer<T>(context, this));
        }
    }
}
