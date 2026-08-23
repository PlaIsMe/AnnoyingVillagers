package com.pla.annoyingvillagers.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelRig;
import com.pla.annoyingvillagers.client.model.ModelRigVillager;
import com.pla.annoyingvillagers.client.model.ModelRigArmor;
import com.pla.annoyingvillagers.client.renderer.RigItemVisualResolver;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public final class WhiteAfterimageParticle extends Particle {
    private static final ResourceLocation WHITE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/white.png");
    private static final RenderType WHITE_RENDER_TYPE = RenderType.entityTranslucent(WHITE_TEXTURE);
    private static final float ITEM_X_OFFSET = 1.0F / 16.0F;
    private static final float ITEM_DEPTH_OFFSET = 2.0F / 16.0F;

    private final Mob entity;
    private final HumanoidModel<?> model;
    private final float bodyYaw;
    private final HumanoidArm mainArm;
    private final ModelRigArmor<?> innerArmorModel;
    private final ModelRigArmor<?> outerArmorModel;
    private final ItemStack mainHandItem;
    private final ItemStack offHandItem;
    private final ItemStack headArmor;
    private final ItemStack chestArmor;
    private final ItemStack legArmor;
    private final ItemStack footArmor;
    private float alphaO = 1.0F;

    private WhiteAfterimageParticle(ClientLevel level, double x, double y, double z, Mob entity, HumanoidModel<?> model) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.entity = entity;
        this.model = model;
        this.bodyYaw = Mth.rotLerp(1.0F, entity.yBodyRotO, entity.yBodyRot);
        this.mainArm = entity.getMainArm();
        this.mainHandItem = RigItemVisualResolver.resolve(entity, entity.getMainHandItem()).copy();
        this.offHandItem = RigItemVisualResolver.resolve(entity, entity.getOffhandItem()).copy();
        this.headArmor = entity.getItemBySlot(EquipmentSlot.HEAD).copy();
        this.chestArmor = entity.getItemBySlot(EquipmentSlot.CHEST).copy();
        this.legArmor = entity.getItemBySlot(EquipmentSlot.LEGS).copy();
        this.footArmor = entity.getItemBySlot(EquipmentSlot.FEET).copy();
        this.innerArmorModel = new ModelRigArmor<>(0.5F);
        this.outerArmorModel = new ModelRigArmor<>(1.0F);
        copyModelProperties(this.model, this.innerArmorModel);
        copyModelProperties(this.model, this.outerArmorModel);
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
        this.renderArmor(poseStack, whiteConsumer, this.headArmor, EquipmentSlot.HEAD, packedLight, alpha);
        this.renderArmor(poseStack, whiteConsumer, this.chestArmor, EquipmentSlot.CHEST, packedLight, alpha);
        this.renderArmor(poseStack, whiteConsumer, this.legArmor, EquipmentSlot.LEGS, packedLight, alpha);
        this.renderArmor(poseStack, whiteConsumer, this.footArmor, EquipmentSlot.FEET, packedLight, alpha);
        this.renderHeldItem(poseStack, buffers, this.mainHandItem, InteractionHand.MAIN_HAND, packedLight);
        this.renderHeldItem(poseStack, buffers, this.offHandItem, InteractionHand.OFF_HAND, packedLight);
        buffers.endBatch(WHITE_RENDER_TYPE);

        poseStack.popPose();
        RenderSystem.getModelViewStack().popPose();
        RenderSystem.applyModelViewMatrix();
    }


    private void renderArmor(PoseStack poseStack, VertexConsumer consumer, ItemStack stack, EquipmentSlot slot, int packedLight, float alpha) {
        if (!(stack.getItem() instanceof ArmorItem)) return;
        ModelRigArmor<?> armorModel = slot == EquipmentSlot.LEGS ? this.innerArmorModel : this.outerArmorModel;
        armorModel.setVisibleForSlot(slot);
        armorModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void copyModelProperties(HumanoidModel<?> source, HumanoidModel<?> target) {
        ((HumanoidModel)source).copyPropertiesTo((HumanoidModel)target);
    }

    private void renderHeldItem(PoseStack poseStack, MultiBufferSource.BufferSource buffers, ItemStack stack, InteractionHand hand, int packedLight) {
        if (stack.isEmpty()) return;
        HumanoidArm arm = hand == InteractionHand.MAIN_HAND ? this.mainArm : this.mainArm.getOpposite();
        ItemDisplayContext displayContext = arm == HumanoidArm.LEFT ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;

        poseStack.pushPose();
        this.translateToTool(arm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(arm == HumanoidArm.LEFT ? -ITEM_X_OFFSET : ITEM_X_OFFSET, ITEM_DEPTH_OFFSET, 0.0F);

        MultiBufferSource whiteBuffers = ignored -> buffers.getBuffer(WHITE_RENDER_TYPE);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, displayContext, packedLight, OverlayTexture.NO_OVERLAY, poseStack, whiteBuffers, this.entity.level(), this.entity.getId());
        poseStack.popPose();
    }

    private void translateToTool(HumanoidArm arm, PoseStack poseStack) {
        if (this.model instanceof ModelRig<?> rigModel) rigModel.translateToTool(arm, poseStack);
        else if (this.model instanceof ModelRigVillager<?> villagerModel) villagerModel.translateToTool(arm, poseStack);
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
            return snapshot;
        }

        if (liveModel instanceof ModelRigVillager<?> villagerModel) {
            ModelRigVillager<?> snapshot = new ModelRigVillager<>(minecraft.getEntityModels().bakeLayer(ModelRigVillager.LAYER_LOCATION));
            villagerModel.copyPoseTo(snapshot);
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
