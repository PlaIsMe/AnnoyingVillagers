package com.pla.annoyingvillagers.entity;

import com.mojang.logging.LogUtils;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.mehvahdjukaar.dummmmmmy.Dummmmmmy;
import net.mehvahdjukaar.dummmmmmy.common.TargetDummyEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;

public class FlyingShockwaveProjectile extends Projectile
{
    protected int lifetime = 40;
    protected Vec3 deceleration = null;
    protected double decelerationConstant = 0.2;
    protected float damage = 1;
    protected int maxStrikes = 1;

    public FlyingShockwaveProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick()
    {
        super.tick();

        Vec3 originalVec = this.getDeltaMovement();
        if (deceleration == null)
        {
            deceleration = originalVec.multiply(decelerationConstant, decelerationConstant, decelerationConstant);
        }

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        double d7;
        double d2;
        double d3;
        if (magnitude(originalVec) > 0.0)
        {
            Vec3 moveVec = originalVec.subtract(deceleration);
            double d5 = moveVec.x;
            double d6 = moveVec.y;
            double d1 = moveVec.z;
            d7 = this.getX() + d5;
            d2 = this.getY() + d6;
            d3 = this.getZ() + d1;
        }
        else
        {
            d7 = this.getX() + 0;
            d2 = this.getY() + 0;
            d3 = this.getZ() + 0;
        }
        this.setPos(d7, d2, d3);
        lifetime--;
        if (lifetime <= 0)
        {
            this.discard();
        }
    }

    private double magnitude(Vec3 vec)
    {
        return Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);
    }


    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy)
    {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy);
    }

    public void setDamage(float damage)
    {
        this.damage = damage;
    }

    @Override
    protected void onHit(HitResult pResult)
    {
        super.onHit(pResult);
    }

    public void setMaxStrikes(int maxStrikes)
    {
        this.maxStrikes = maxStrikes;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        LogUtils.getLogger().debug("Smack!");
        if (!this.level().isClientSide()) {
            Entity entity = hitResult.getEntity();
            Entity entity1 = this.getOwner();
            PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(this.getOwner(), PlayerPatch.class);
            if (entity1 instanceof LivingEntity livingEntity && playerpatch != null)
            {
                LogUtils.getLogger().debug("Check passed");
                if (!(entity instanceof Enemy || (ModList.get().isLoaded(Dummmmmmy.MOD_ID) && (entity instanceof TargetDummyEntity)))) {
                    if (entity instanceof TamableAnimal pet) {
                        if (Objects.requireNonNull(pet.getOwner()).is(entity1) || pet.getOwner().getTeam() == entity1.getTeam() || (pet.getOwner().getTeam() != null && pet.getOwner().getTeam().isAlliedTo(entity1.getTeam()))) {
                            LogUtils.getLogger().debug("Pet");
                            return;
                        }
                    }
                    if (livingEntity.getTeam() == entity1.getTeam() || (livingEntity.getTeam() != null && livingEntity.getTeam().isAlliedTo(entity1.getTeam()))) {
                        LogUtils.getLogger().debug("Teammate");
                        return;
                    }
                }
                EpicFightDamageSource damage = playerpatch.getDamageSource(AnimsWom.WARBLADE_SATSUJIN_TSUKUYOMI, InteractionHand.MAIN_HAND);
                damage.setStunType(StunType.HOLD);
                damage.setBaseImpact(0.5F);
                damage.addRuntimeTag(EpicFightDamageTypeTags.WEAPON_INNATE);
                entity.invulnerableTime = 0;
                playerpatch.attack(damage, entity, InteractionHand.MAIN_HAND);
                entity.playSound(EpicFightSounds.BLADE_HIT.get(), 1.0f, 1.0f);
                entity.level().addParticle(EpicFightParticles.HIT_BLADE.get(), entity.getX(), entity.getY(), entity.getZ(), 0.0D, 0.0D, 0.0D);
                this.discard();
            } else {
                entity.hurt(this.damageSources().magic(), 6.0F);
            }
        }
    }
    @Override
    protected void defineSynchedData()
    {

    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        if (pTarget instanceof LivingEntity livingEntity)
        {
            return true;
        }
        return super.canHitEntity(pTarget);
    }

    public boolean isFoil()
    {
        return false;
    }
}