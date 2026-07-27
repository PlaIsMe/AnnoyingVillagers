package com.pla.annoyingvillagers.mobpatch;

import com.mojang.logging.LogUtils;
import com.pla.annoyingvillagers.entity.FlyingShockwaveProjectile;
import yesman.epicfight.world.capabilities.projectile.ProjectilePatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

public class FlyingShockwavePatch extends ProjectilePatch<FlyingShockwaveProjectile>
{

    @Override
    protected void setMaxStrikes(FlyingShockwaveProjectile flyingShockwaveProjectile, int i)
    {
        flyingShockwaveProjectile.setMaxStrikes(i);
    }

    @Override
    public void onAddedToWorld() {
        LogUtils.getLogger().debug("onAddedToWorld");
        if (this.getOriginal().level().isClientSide()) {

        }

    }

    @Override
    public EpicFightDamageSource createEpicFightDamageSource()
    {
        return new EpicFightDamageSource(this.getOriginal().level().damageSources().generic());
    }
}