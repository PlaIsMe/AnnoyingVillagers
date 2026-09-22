package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.accessors.ModelPartAccess;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelPart.class)
public class ModelPartMixin implements ModelPartAccess
{
    @Shadow public float xScale;
    @Shadow public float yScale;
    @Shadow public float zScale;

    @Override
    public float getXScale()
    {
        return xScale;
    }

    @Override
    public float getYScale()
    {
        return yScale;
    }

    @Override
    public float getZScale()
    {
        return zScale;
    }

    @Override
    public void setXScale(float x)
    {
        this.xScale = x;
    }

    @Override
    public void setYScale(float y)
    {
        this.yScale = y;
    }

    @Override
    public void setZScale(float z)
    {
        this.zScale = z;
    }
}
