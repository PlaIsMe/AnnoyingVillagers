package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.TridentLightningBolt;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerExplosion;
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

@Mixin(ServerExplosion.class)
public abstract class ExplosionMixin {
    @Shadow
    @Final
    private ServerLevel level;

    @Shadow @Nullable
    public abstract LivingEntity getIndirectSourceEntity();

    @Shadow @Final @Nullable
    private Entity source;

    @ModifyArg(
            method = "createFire",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            ),
            index = 1
    )
    private BlockState replaceVanillaFire(BlockState originalState) {
        if (!(originalState.getBlock() instanceof BaseFireBlock)) return originalState;

        LivingEntity owner = this.getIndirectSourceEntity();
        if (owner != null && owner.isAlive()) {
            ItemStack stack = owner.getMainHandItem();
            if (stack.getItem() instanceof EnderGlaiveItem) {
                return AnnoyingVillagersModBlocks.END_FIRE.get().defaultBlockState();
            }
        }
        return originalState;
    }

    @Redirect(
            method = "hurtEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;push(Lnet/minecraft/world/phys/Vec3;)V"
            )
    )
    private void noKnockbackFromTridentLightning(Entity instance, Vec3 pDeltaMovement) {
        if (this.source instanceof TridentLightningBolt) {
            return;
        }
        instance.push(pDeltaMovement);
    }

    @Redirect(
            method = "hurtEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object noPlayerExplosionVector(Map<?, ?> map, Object key, Object value) {
        if (this.source instanceof TridentLightningBolt) {
            return null;
        }
        return ((Map<Object, Object>) map).put(key, value);
    }
}
