package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.layer.GolemArmsEmissiveLayer;
import com.pla.annoyingvillagers.client.layer.GolemArmsRedGlintLayer;
import com.pla.annoyingvillagers.client.model.ModelGolemArm;
import com.pla.annoyingvillagers.entity.GolemArms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GolemArmsRenderer extends MobRenderer<GolemArms, ModelGolemArm> {
    private static final ResourceLocation BASE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/iron_golem/iron_golem.png");
    private static final ResourceLocation EMISSIVE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/golem_arms_emissive.png");

    public GolemArmsRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelGolemArm(context.bakeLayer(ModelGolemArm.LAYER_LOCATION)), 0.0F);
        this.addLayer(new GolemArmsRedGlintLayer(this));
        this.addLayer(new GolemArmsEmissiveLayer(this, EMISSIVE));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GolemArms entity) {
        return BASE;
    }

    @Override
    public @NotNull Vec3 getRenderOffset(@NotNull GolemArms entity, float partialTick) {
        Vec3 base = super.getRenderOffset(entity, partialTick);
        LivingEntity owner = entity.getOwnerLiving();
        if (owner == null) return base;

        double ownerX = Mth.lerp(partialTick, owner.xOld, owner.getX());
        double ownerY = Mth.lerp(partialTick, owner.yOld, owner.getY());
        double ownerZ = Mth.lerp(partialTick, owner.zOld, owner.getZ());
        double armsX = Mth.lerp(partialTick, entity.xOld, entity.getX());
        double armsY = Mth.lerp(partialTick, entity.yOld, entity.getY());
        double armsZ = Mth.lerp(partialTick, entity.zOld, entity.getZ());
        return base.add(ownerX - armsX, ownerY - armsY, ownerZ - armsZ);
    }
}
