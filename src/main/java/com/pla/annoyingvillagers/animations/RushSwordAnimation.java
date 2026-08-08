package com.pla.annoyingvillagers.animations;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.network.ClientboundWoopieSwordWindFx;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import javax.annotation.Nullable;

public class RushSwordAnimation extends BasicMultipleAttackAnimation {
    public RushSwordAnimation(float convertTime,
                              float antic, float preDelay, float contact, float recovery,
                              @Nullable Collider collider, Joint colliderJoint,
                              AnimationManager.AnimationAccessor<? extends BasicMultipleAttackAnimation> accessor,
                              AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
        LivingEntity livingEntity = entitypatch.getOriginal();
        if (!(livingEntity.level() instanceof ServerLevel)) {
            return;
        }

        Joint toolJoint = null;
        ItemStack mainHand = livingEntity.getMainHandItem();
        if (mainHand.is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) {
            toolJoint = Armatures.BIPED.get().toolR;
        }

        ItemStack offHand = livingEntity.getOffhandItem();
        if (offHand.is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) {
            toolJoint = Armatures.BIPED.get().toolL;
        }
        if (toolJoint == null) {
            return;
        }

        Vec3 windPos = EpicfightUtil.getJointWithTranslation(livingEntity,
                new Vec3f(0.0F, 0.0F, 0.0F),
                toolJoint,
                0,
                0.0
        );

        if (windPos == null) {
            return;
        }

        BlockPos mutePos = BlockPos.containing(windPos);
        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity),
                new ClientboundMuteExplosionAtPos(mutePos, 4)
        );
        livingEntity.level().explode(livingEntity, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity),
                new ClientboundWoopieSwordWindFx(windPos)
        );

        Vec3 dashDir = livingEntity.getLookAngle();

        if (livingEntity instanceof Mob mob) {
            LivingEntity target = mob.getTarget();
            if (target != null && target.isAlive()) {
                Vec3 toTarget = target.position().subtract(mob.position());
                dashDir = new Vec3(toTarget.x, 0.0, toTarget.z);
            }
        }

        Vec3 dash = dashDir.normalize().scale(2.2D);
        livingEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2, false, false));
        new DelayedTask(1) {
            @Override public void run() {
                Vec3 cur = livingEntity.getDeltaMovement();
                livingEntity.setDeltaMovement(cur.x + dash.x, cur.y + dash.y, cur.z + dash.z);
                livingEntity.hasImpulse = true;
            }
        };
    }
}
