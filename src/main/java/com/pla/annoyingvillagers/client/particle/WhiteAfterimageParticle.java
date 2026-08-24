package com.pla.annoyingvillagers.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelRig;
import com.pla.annoyingvillagers.client.model.ModelRigVillager;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public final class WhiteAfterimageParticle extends Particle {
    private static final ResourceLocation WHITE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/white.png");
    private static final RenderType WHITE_RENDER_TYPE = RenderType.entityTranslucent(WHITE_TEXTURE);
    private final HumanoidModel<?> model;
    private final float bodyYaw;
    private float alphaO = 1.0F;

    private WhiteAfterimageParticle(ClientLevel level, double x, double y, double z, Mob entity, HumanoidModel<?> model) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.model = model;
        this.bodyYaw = Mth.rotLerp(1.0F, entity.yBodyRotO, entity.yBodyRot);
        this.hasPhysics = false;
        this.lifetime = 20;
        this.alpha = 1.0F;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.alphaO = this.alpha;
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }
        this.alpha = Math.max(0.0F, (float)(this.lifetime - this.age) / (float)this.lifetime);
    }

    @Override
    public void render(VertexConsumer ignored, Camera camera, float partialTick) {
        float alpha = Mth.lerp(partialTick, this.alphaO, this.alpha);
        int packedLight = this.getLightColor(partialTick);
        PoseStack poseStack = new PoseStack();

        poseStack.pushPose();
        poseStack.mulPoseMatrix(RenderSystem.getModelViewStack().last().pose());
        RenderSystem.getModelViewStack().pushPose();
        RenderSystem.getModelViewStack().setIdentity();
        RenderSystem.applyModelViewMatrix();

        Vec3 cameraPos = camera.getPosition();
        poseStack.translate(Mth.lerp(partialTick, this.xo, this.x) - cameraPos.x, Mth.lerp(partialTick, this.yo, this.y) - cameraPos.y, Mth.lerp(partialTick, this.zo, this.z) - cameraPos.z);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - this.bodyYaw));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0F, -1.501F, 0.0F);

        MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer whiteConsumer = buffers.getBuffer(WHITE_RENDER_TYPE);
        this.model.renderToBuffer(poseStack, whiteConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
        buffers.endBatch(WHITE_RENDER_TYPE);

        poseStack.popPose();
        RenderSystem.getModelViewStack().popPose();
        RenderSystem.applyModelViewMatrix();
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    private static HumanoidModel<?> captureModel(Mob entity) {
        Minecraft minecraft = Minecraft.getInstance();
        EntityRenderer<? super Mob> renderer = minecraft.getEntityRenderDispatcher().getRenderer(entity);
        if (!(renderer instanceof LivingEntityRenderer<?, ?> livingRenderer)) return null;
        Object liveModel = livingRenderer.getModel();

        if (liveModel instanceof ModelRig<?> rigModel) {
            ModelRig<?> snapshot = new ModelRig<>(minecraft.getEntityModels().bakeLayer(rigModel.isSlim() ? ModelRig.SLIM_LAYER_LOCATION : ModelRig.LAYER_LOCATION), rigModel.isSlim());
            rigModel.copyPoseTo(snapshot);
            snapshot.young = rigModel.young;
            snapshot.riding = rigModel.riding;
            snapshot.attackTime = rigModel.attackTime;
            return snapshot;
        }

        if (liveModel instanceof ModelRigVillager<?> villagerModel) {
            ModelRigVillager<?> snapshot = new ModelRigVillager<>(minecraft.getEntityModels().bakeLayer(ModelRigVillager.LAYER_LOCATION));
            villagerModel.copyPoseTo(snapshot);
            snapshot.young = villagerModel.young;
            snapshot.riding = villagerModel.riding;
            snapshot.attackTime = villagerModel.attackTime;
            return snapshot;
        }

        return null;
    }

    public static final class Provider implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Entity entity = level.getEntity(Mth.floor(xSpeed + 0.5D));
            if (!(entity instanceof Mob mob)) return null;
            HumanoidModel<?> model = captureModel(mob);
            return model == null ? null : new WhiteAfterimageParticle(level, x, y, z, mob, model);
        }
    }
}
