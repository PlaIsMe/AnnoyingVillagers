package com.pla.annoyingvillagers.mixin.compat.smartnpc;

import com.pla.annoyingvillagers.clazz.Difficulty;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = com.pla.smart_npc.util.ProgressionUtil.class, remap = false)
public abstract class ProgressionUtilMixin {
    @Inject(method = "getDifficulty", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$getDifficulty(MinecraftServer server, CallbackInfoReturnable<com.pla.smart_npc.clazz.Difficulty> cir) {
        if (server == null) {
            cir.setReturnValue(com.pla.smart_npc.clazz.Difficulty.EASY);
            return;
        }

        cir.setReturnValue(toSmartDifficulty(com.pla.annoyingvillagers.util.ProgressionUtil.getDifficulty(server)));
    }

    @Inject(method = "setDifficulty", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$setDifficulty(MinecraftServer server, com.pla.smart_npc.clazz.Difficulty difficulty, CallbackInfoReturnable<Boolean> cir) {
        if (server == null || difficulty == null) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(com.pla.annoyingvillagers.util.ProgressionUtil.setDifficulty(server, toAnnoyingVillagersDifficulty(difficulty)));
    }

    @Inject(method = "increaseDifficulty", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$increaseDifficulty(MinecraftServer server, com.pla.smart_npc.clazz.Difficulty difficulty, CallbackInfo ci) {
        if (server != null && difficulty != null) {
            com.pla.annoyingvillagers.util.ProgressionUtil.increaseDifficulty(server, toAnnoyingVillagersDifficulty(difficulty));
        }
        ci.cancel();
    }

    private static Difficulty toAnnoyingVillagersDifficulty(com.pla.smart_npc.clazz.Difficulty difficulty) {
        return switch (difficulty) {
            case HARD -> Difficulty.HARD;
            case MEDIUM -> Difficulty.MEDIUM;
            default -> Difficulty.EASY;
        };
    }

    private static com.pla.smart_npc.clazz.Difficulty toSmartDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case HARD -> com.pla.smart_npc.clazz.Difficulty.HARD;
            case MEDIUM -> com.pla.smart_npc.clazz.Difficulty.MEDIUM;
            default -> com.pla.smart_npc.clazz.Difficulty.EASY;
        };
    }
}
