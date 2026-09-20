package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ThrownPoisonEggEntity extends ThrowableItemProjectile {
        public ThrownPoisonEggEntity(EntityType<? extends ThrownPoisonEggEntity> entitytype, Level level) {
        super(entitytype, level);
    }

    public ThrownPoisonEggEntity(EntityType<? extends ThrownPoisonEggEntity> entitytype, double d0, double d1, double d2, Level level) {
        super(entitytype, d0, d1, d2, level, new ItemStack(AnnoyingVillagersModItems.POISON_EGG_ITEM.get()));
    }

    public ThrownPoisonEggEntity(EntityType<? extends ThrownPoisonEggEntity> entitytype, LivingEntity livingentity, Level level) {
        super(entitytype, livingentity, level, new ItemStack(AnnoyingVillagersModItems.POISON_EGG_ITEM.get()));
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.08D;

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem().getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.getRandom().nextFloat() - 0.5D) * 0.08D, ((double)this.getRandom().nextFloat() - 0.5D) * 0.08D, ((double)this.getRandom().nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    public void spawnPoisonCloud(Level level, double x, double y, double z) {
        AreaEffectCloud cloud = new AreaEffectCloud(level, x, y, z);
        cloud.setRadius(3.0F);
        cloud.setRadiusPerTick(-0.05F);
        cloud.setDuration(20);
        cloud.setWaitTime(0);
        cloud.setCustomParticle(net.minecraft.core.particles.ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 0xFF4E9331));
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 20, 0));
        level.addFreshEntity(cloud);
    }


    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!(pResult.getEntity() instanceof BbqEntity)) {
            pResult.getEntity().hurtOrSimulate(this.damageSources().thrown(this, this.getOwner()), 0.5F);
        }
    }

    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide()) {
            if (this.getRandom().nextFloat() < 0.5F) {
                spawnPoisonCloud(this.level(), this.getX(), this.getY(), this.getZ());
            }
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    protected @NotNull Item getDefaultItem() {
        return AnnoyingVillagersModItems.POISON_EGG_ITEM.get();
    }
}
