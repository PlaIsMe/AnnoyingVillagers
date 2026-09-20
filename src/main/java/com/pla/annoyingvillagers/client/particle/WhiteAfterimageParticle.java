package com.pla.annoyingvillagers.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelRig;
import com.pla.annoyingvillagers.client.model.ModelRigVillager;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.ParticleGroupRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RegisterParticleGroupsEvent;

public final class WhiteAfterimageParticle extends Particle {
    private static final Identifier WHITE_TEXTURE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/white.png");
    private static final RenderType WHITE_RENDER_TYPE = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(WHITE_TEXTURE);
    public static final ParticleRenderType GROUP = new ParticleRenderType(AnnoyingVillagers.MODID + ":white_afterimage");

    private final Model<Unit> model;
    private final float bodyYaw;
    private float alphaO = 1.0F;
    private float alpha = 1.0F;

    private WhiteAfterimageParticle(ClientLevel level, double x, double y, double z, Mob entity, Model<Unit> model) {
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
        this.alpha = Math.max(0.0F, (float)(this.lifetime - this.age) / this.lifetime);
    }

    @Override
    public ParticleRenderType getGroup() {
        return GROUP;
    }

    public static void registerGroup(RegisterParticleGroupsEvent event) {
        event.register(GROUP, Group::new);
    }

    private static Model<Unit> captureModel(Mob entity) {
        Minecraft minecraft = Minecraft.getInstance();
        EntityRenderer<? super Entity, ?> renderer = minecraft.getEntityRenderDispatcher().getRenderer(entity);
        if (!(renderer instanceof LivingEntityRenderer<?, ?, ?> livingRenderer)) return null;
        Object liveModel = livingRenderer.getModel();

        HumanoidModel<?> snapshot;
        if (liveModel instanceof ModelRig<?> rigModel) {
            ModelRig<?> rigSnapshot = new ModelRig<>(minecraft.getEntityModels().bakeLayer(
                    rigModel.isSlim() ? ModelRig.SLIM_LAYER_LOCATION : ModelRig.LAYER_LOCATION), rigModel.isSlim());
            rigModel.copyPoseTo(rigSnapshot);
            snapshot = rigSnapshot;
        } else if (liveModel instanceof ModelRigVillager<?> villagerModel) {
            ModelRigVillager<?> villagerSnapshot = new ModelRigVillager<>(
                    minecraft.getEntityModels().bakeLayer(ModelRigVillager.LAYER_LOCATION));
            villagerModel.copyPoseTo(villagerSnapshot);
            snapshot = villagerSnapshot;
        } else {
            return null;
        }
        return new Model.Simple(snapshot.root(), snapshot.renderType());
    }

    private record RenderState(Model<Unit> model, PoseStack poseStack, int light, int color) {
        private static RenderState from(WhiteAfterimageParticle particle, Camera camera, float partialTick) {
            float alpha = Mth.lerp(partialTick, particle.alphaO, particle.alpha);
            Vec3 cameraPos = camera.position();
            PoseStack poseStack = new PoseStack();
            poseStack.translate(
                    Mth.lerp(partialTick, particle.xo, particle.x) - cameraPos.x,
                    Mth.lerp(partialTick, particle.yo, particle.y) - cameraPos.y,
                    Mth.lerp(partialTick, particle.zo, particle.z) - cameraPos.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - particle.bodyYaw));
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.translate(0.0F, -1.501F, 0.0F);
            return new RenderState(particle.model, poseStack, particle.getLightCoords(partialTick),
                    ARGB.colorFromFloat(alpha, 1.0F, 1.0F, 1.0F));
        }
    }

    public static final class Group extends ParticleGroup<WhiteAfterimageParticle> {
        public Group(ParticleEngine engine) {
            super(engine);
        }

        @Override
        public ParticleGroupRenderState extractRenderState(Frustum frustum, Camera camera, float partialTick) {
            List<RenderState> states = this.particles.stream().map(p -> RenderState.from(p, camera, partialTick)).toList();
            return collectorState(states);
        }

        private static ParticleGroupRenderState collectorState(List<RenderState> states) {
            return new ParticleGroupRenderState() {
                @Override
                public void submit(SubmitNodeCollector collector, CameraRenderState camera) {
                    for (RenderState state : states) {
                        collector.submitModel(state.model, Unit.INSTANCE, state.poseStack, WHITE_RENDER_TYPE,
                                state.light, OverlayTexture.NO_OVERLAY, state.color, null, 0, null);
                    }
                }
            };
        }
    }

    public static final class Provider implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed, net.minecraft.util.RandomSource random) {
            Entity entity = level.getEntity(Mth.floor(xSpeed + 0.5D));
            if (!(entity instanceof Mob mob)) return null;
            Model<Unit> model = captureModel(mob);
            return model == null ? null : new WhiteAfterimageParticle(level, x, y, z, mob, model);
        }
    }
}
