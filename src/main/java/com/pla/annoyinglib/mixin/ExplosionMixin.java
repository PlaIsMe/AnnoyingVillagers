package com.pla.annoyinglib.mixin;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.util.ExplosionFxMute;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(value = {Explosion.class}, remap = true)
public abstract class ExplosionMixin {
    @Shadow
    @Final
    private Level level;

    @Shadow @Nullable
    public abstract LivingEntity getIndirectSourceEntity();

    @Shadow @Final @Nullable
    private Entity source;

    @Unique
    private boolean muteAtThisPos() {
        if (!this.level.isClientSide()) return false;

        Vec3 pos = ((Explosion)(Object)this).getPosition();
        long key = BlockPos.asLong(Mth.floor(pos.x), Mth.floor(pos.y), Mth.floor(pos.z));

        return ExplosionFxMute.shouldMute(key, this.level.getGameTime());
    }

    @ModifyArg(
            method = "finalizeExplosion(Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"
            ),
            index = 5
    )
    private float muteExplosionSound(float vol) {
        return muteAtThisPos() ? 0.0F : vol;
    }

    @ModifyVariable(
            method = "finalizeExplosion(Z)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private boolean disableParticlesWhenMuted(boolean spawnParticles) {
        return muteAtThisPos() ? false : spawnParticles;
    }

    @ModifyArg(
            method = "finalizeExplosion(Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            ),
            index = 1
    )
    private BlockState replaceVanillaFire(BlockState originalState) {
        if (!(originalState.getBlock() instanceof BaseFireBlock)) return originalState;

        LivingEntity owner = this.getIndirectSourceEntity();
        if (owner != null && owner.isAlive()) {
            ItemStack stack = owner.getMainHandItem();
            if (ModList.get().isLoaded("herobrine_vessels")) return HerobrineVessels.explosionFire(stack, originalState);
        }
        return originalState;
    }

    @Redirect(
            method = "explode",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object noPlayerExplosionVector(Map<?, ?> map, Object key, Object value) {
        if ((ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isTridentLightningBolt(this.source))) {
            return null;
        }
        return ((Map<Object, Object>) map).put(key, value);
    }
}
