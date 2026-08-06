package com.pla.annoyingvillagers.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.util.ArmatureUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public abstract class AwakenJointFollowBaseParticle extends TextureSheetParticle {
    protected final SpriteSet sprites;
    protected Vec3 offset;
    protected int entityId;
    protected int jointId;

    protected AwakenJointFollowBaseParticle(ClientLevel level, double x, double y, double z,
                                            double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.x = x;
        this.y = y;
        this.z = z;
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.quadSize = 1.0F;
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        Entity entity = this.level.getEntity(this.entityId);

        if (entity != null) {
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            if (entityPatch != null) {
                Joint joint = entityPatch.getArmature().searchJointById(this.jointId);
                Vec3 position = ArmatureUtil.getJointWorldPosition(entityPatch, joint, this.offset);

                this.x = position.x;
                this.y = position.y;
                this.z = position.z;
            }
        }

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public void render(@NotNull VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        Vec3 cameraPosition = camera.getPosition();
        float x = (float) (Mth.lerp(partialTicks, this.xo, this.x) - cameraPosition.x);
        float y = (float) (Mth.lerp(partialTicks, this.yo, this.y) - cameraPosition.y);
        float z = (float) (Mth.lerp(partialTicks, this.zo, this.z) - cameraPosition.z);
        Entity entity = this.level.getEntity(this.entityId);

        if (entity != null) {
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            if (entityPatch != null) {
                Joint joint = entityPatch.getArmature().searchJointById(this.jointId);
                Vec3 position = ArmatureUtil.getJointWorldPosition(entityPatch, joint, this.offset, partialTicks);

                x = (float) (position.x - cameraPosition.x);
                y = (float) (position.y - cameraPosition.y);
                z = (float) (position.z - cameraPosition.z);
            }
        }

        Quaternionf quaternion = new Quaternionf(camera.rotation());
        quaternion.rotateZ(this.roll);
        Vector3f[] corners = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)
        };
        float quadSize = this.getQuadSize(partialTicks);

        for (Vector3f corner : corners) {
            corner.rotate(quaternion);
            corner.mul(quadSize);
            corner.add(x, y, z);
        }

        int light = this.getLightColor(partialTicks);
        vertexConsumer.vertex(corners[0].x(), corners[0].y(), corners[0].z()).uv(this.getU1(), this.getV1()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(corners[1].x(), corners[1].y(), corners[1].z()).uv(this.getU1(), this.getV0()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(corners[2].x(), corners[2].y(), corners[2].z()).uv(this.getU0(), this.getV0()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(corners[3].x(), corners[3].y(), corners[3].z()).uv(this.getU0(), this.getV1()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 15728880;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return AwakenParticleRenderType.PARTICLE_SHEET_OPAQUE_NO_CULL;
    }
}
