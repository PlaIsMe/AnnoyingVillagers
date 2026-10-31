package com.pla.annoyinglib.mixin;

import com.pla.annoyinglib.util.EndFireEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EndFireEntityMixin implements EndFireEntity {
    @Unique
    private static final String ANNOYINGLIB_END_FIRE_TAG = "AnnoyingVillagersEndFire";
    @Unique
    private static final EntityDataAccessor<Boolean> ANNOYINGLIB_END_FIRE = SynchedEntityData.defineId(Entity.class,EntityDataSerializers.BOOLEAN);

    @Final
    @Shadow
    protected SynchedEntityData entityData;

    @Shadow
    public abstract int getRemainingFireTicks();

    @Shadow
    public abstract Level level();

    @Override
    public boolean annoyingLib$isEndFireBurning() {
        return this.entityData.get(ANNOYINGLIB_END_FIRE);
    }

    @Override
    public void annoyingLib$setEndFireBurning(boolean endFireBurning) {
        this.entityData.set(ANNOYINGLIB_END_FIRE,endFireBurning);
    }

    @Inject(method = "<init>",at = @At("TAIL"))
    private void annoyingLib$defineEndFireData(EntityType<?> pEntityType, Level pLevel, CallbackInfo ci) {
        this.entityData.define(ANNOYINGLIB_END_FIRE,false);
    }

    @Inject(method = "clearFire", at = @At("HEAD"), cancellable = true)
    private void annoyingLib$preventEndFireExtinguish(CallbackInfo ci) {
        // Water, rain, bubble columns and vanilla clearFire() calls must not extinguish end fire.
        // Do not gate this on remainingFireTicks: the custom flag itself is the authoritative
        // fire type, and checking the tick count caused the short vanilla-fire window.
        if (this.annoyingLib$isEndFireBurning()) {
            ci.cancel();
        }
    }

    @Inject(method = "baseTick",at = @At("TAIL"))
    private void annoyingLib$clearExpiredEndFire(CallbackInfo ci) {
        // The server owns expiration. Keeping the client-side flag until the synced update arrives
        // prevents a one-frame/short vanilla-fire fallback immediately after touching end fire.
        if (!this.level().isClientSide
                && this.annoyingLib$isEndFireBurning()
                && this.getRemainingFireTicks() <= 0) {
            this.annoyingLib$setEndFireBurning(false);
        }
    }

    @Inject(method = "saveWithoutId",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void annoyingLib$saveEndFire(CompoundTag tag,CallbackInfoReturnable<CompoundTag> cir) {
        tag.putBoolean(ANNOYINGLIB_END_FIRE_TAG,this.annoyingLib$isEndFireBurning() && this.getRemainingFireTicks() > 0);
    }

    @Inject(method = "load",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void annoyingLib$loadEndFire(CompoundTag tag,CallbackInfo ci) {
        this.annoyingLib$setEndFireBurning(tag.getBoolean(ANNOYINGLIB_END_FIRE_TAG) && this.getRemainingFireTicks() > 0);
    }
}
