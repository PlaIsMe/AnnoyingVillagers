package com.pla.annoyinglib.mixin.compat.punchy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = punchy.client.state.SpearStateMachine.class, remap = false)
public abstract class SpearStateMachineMixin {
    @Inject(method = "isSpearStack", at = @At("HEAD"), cancellable = true, require = 1)
    private static void av$leaveTridentToThrowAnimation(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        // Blue Demon uses SPEAR like vanilla tridents, but is not a melee charging spear.
        if (stack != null && (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonTridentItem_isBlueDemonTrident(stack) : false)) cir.setReturnValue(false);
    }
}
