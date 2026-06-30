package com.pla.annoyingvillagers.client.overlaylayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.potion.ObedienceMobEffect;
import com.pla.annoyingvillagers.util.HerobrineEyesUtil;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class HumanoidMobEpicFightOverlayLayer<E extends LivingEntity, AM extends HumanoidMesh>
        extends ModelRenderLayer<E, LivingEntityPatch<E>, HumanoidModel<E>, RenderLayer<E, HumanoidModel<E>>, AM> {
    private final ResourceLocation BLOOD_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/player_mob_blood.png");
    private final ResourceLocation DEFAULT_EYE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/default/default.png");

    public HumanoidMobEpicFightOverlayLayer(AssetAccessor<AM> mesh) { super(mesh); }

    private ResourceLocation pickTexture(E e) {
        if (e instanceof LowHerobrineCloneEntity) {
            String name = e.hasCustomName() ? e.getCustomName().getString() : e.getName().getString();
            return HerobrineEyesUtil.getHerobrineEyesTexture(name);
        } else if (EntityType.getKey(e.getType()).equals(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "infected_player_npc"))) {
            return BLOOD_TEXTURE;
        } else if (e instanceof HerobrineMob || e instanceof LowShadowHerobrineCloneEntity
                || (e instanceof HerobrineGregEntity herobrineGregEntity && herobrineGregEntity.isWhiteEye())) {
            return DEFAULT_EYE;
        } else if (ObedienceMobEffect.canBeObedientMob(e) && e.hasEffect(AnnoyingVillagersModMobEffects.OBEDIENCE.get())) {
            if (e instanceof ZombieVillager) {
                return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/zombie_villager.png");
            } else if (e instanceof Zombie) {
                return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/zombie.png");
            } else if (e instanceof AbstractSkeleton) {
                return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/skeleton.png");
            } else if (e instanceof AbstractPiglin) {
                return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/piglin.png");
            }
        }
        return null;
    }

    @Override
    protected void renderLayer(LivingEntityPatch<E> eLivingEntityPatch, E e, @Nullable RenderLayer<E, HumanoidModel<E>> eHumanoidModelRenderLayer, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, OpenMatrix4f[] openMatrix4fs, float v, float v1, float v2, float v3) {
        ResourceLocation tex = pickTexture(e);
        if (tex == null) return;
        if (tex == BLOOD_TEXTURE) {
            (this.mesh.get()).draw(
                    poseStack, multiBufferSource, RenderType.entityCutoutNoCull(tex),
                    i,
                    1f, 1f, 1f, 1f, OverlayTexture.NO_OVERLAY,
                    eLivingEntityPatch.getArmature(), openMatrix4fs
            );
        } else {
            (this.mesh.get()).draw(
                    poseStack, multiBufferSource, RenderType.eyes(tex),
                    i,
                    1f, 1f, 1f, 1f, OverlayTexture.NO_OVERLAY,
                    eLivingEntityPatch.getArmature(), openMatrix4fs
            );
        }
    }
}
