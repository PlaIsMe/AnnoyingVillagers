package com.pla.annoyingvillagers.mixin.compat.aaa_particles;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "mod.chloeprime.aaaparticles.forge.client.ForgePlatformMethods", remap = false)
public abstract class ForgePlatformMethodsMixin {
    @Inject(method = "isModLoaded(Ljava/lang/String;)Z", at = @At("HEAD"), cancellable = true, require = 1)
    private void av$useLoadingModListDuringBootstrap(String modId, CallbackInfoReturnable<Boolean> cir) {
        if (ModList.get() == null) {
            LoadingModList loadingMods = FMLLoader.getLoadingModList();
            cir.setReturnValue(loadingMods != null && loadingMods.getModFileById(modId) != null);
        }
    }
}
