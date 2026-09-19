package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.util.EndFireEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
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
    private static final String ANNOYINGVILLAGERS_END_FIRE_TAG = "AnnoyingVillagersEndFire";
    @Unique
    private boolean annoyingVillagers$pendingLegacyEndFire;
    @Shadow
    public abstract int getRemainingFireTicks();

    @Shadow
    public abstract Level level();

    @Override
    public boolean annoyingVillagers$isEndFireBurning() {
        return ((Entity) (Object) this).getData(AnnoyingVillagersModCapabilities.END_FIRE);
    }

    @Override
    public void annoyingVillagers$setEndFireBurning(boolean endFireBurning) {
        Entity self = (Entity) (Object) this;
        self.setData(AnnoyingVillagersModCapabilities.END_FIRE, endFireBurning);
    }

    @Inject(method = "clearFire", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$preventEndFireExtinguish(CallbackInfo ci) {
        // Water, rain, bubble columns and vanilla clearFire() calls must not extinguish end fire.
        // Do not gate this on remainingFireTicks: the custom flag itself is the authoritative
        // fire type, and checking the tick count caused the short vanilla-fire window.
        if (this.annoyingVillagers$isEndFireBurning()) {
            ci.cancel();
        }
    }

    @Inject(method = "baseTick",at = @At("TAIL"))
    private void annoyingVillagers$clearExpiredEndFire(CallbackInfo ci) {
        // Loading old player NBT happens before ServerPlayer.connection exists.
        // AttachmentType#setData synchronizes automatically, so migrate the
        // legacy root tag only after the entity has entered the ticking world.
        if (!this.level().isClientSide && this.annoyingVillagers$pendingLegacyEndFire) {
            this.annoyingVillagers$pendingLegacyEndFire = false;
            if (this.getRemainingFireTicks() > 0 && !this.annoyingVillagers$isEndFireBurning()) {
                this.annoyingVillagers$setEndFireBurning(true);
            }
        }

        // The server owns expiration. Keeping the client-side flag until the synced update arrives
        // prevents a one-frame/short vanilla-fire fallback immediately after touching end fire.
        if (!this.level().isClientSide
                && this.annoyingVillagers$isEndFireBurning()
                && this.getRemainingFireTicks() <= 0) {
            this.annoyingVillagers$setEndFireBurning(false);
        }
    }

    @Inject(method = "saveWithoutId",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void annoyingVillagers$saveEndFire(CompoundTag tag,CallbackInfoReturnable<CompoundTag> cir) {
        tag.putBoolean(ANNOYINGVILLAGERS_END_FIRE_TAG,this.annoyingVillagers$isEndFireBurning() && this.getRemainingFireTicks() > 0);
    }

    @Inject(method = "load",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void annoyingVillagers$loadEndFire(CompoundTag tag,CallbackInfo ci) {
        // NeoForge deserializes its attachment payload itself. This only
        // migrates the root tag written by the 1.20.1 implementation.
        this.annoyingVillagers$pendingLegacyEndFire = !this.level().isClientSide
                && tag.getBoolean(ANNOYINGVILLAGERS_END_FIRE_TAG)
                && this.getRemainingFireTicks() > 0;
    }
}
