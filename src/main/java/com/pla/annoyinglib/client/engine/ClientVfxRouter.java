package com.pla.annoyinglib.client.engine;

import com.pla.annoyinglib.config.AnnoyingLibClientConfig;
import com.pla.annoyinglib.config.AnnoyingLibClientConfig.VfxEffect;
import com.pla.annoyinglib.config.AnnoyingLibClientConfig.VfxMode;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.BooleanSupplier;

@OnlyIn(Dist.CLIENT)
public final class ClientVfxRouter {
    private ClientVfxRouter() {
    }

    public static void run(VfxEffect effect, BooleanSupplier photon, BooleanSupplier aaa, Runnable vanilla) {
        VfxMode mode = AnnoyingLibClientConfig.getMode(effect);
        if (mode == VfxMode.VANILLA) {
            runVanilla(vanilla);
            return;
        }

        boolean photonTried = false;
        boolean aaaTried = false;

        if (mode == VfxMode.PHOTON) {
            photonTried = true;
            if (tryPhoton(photon)) {
                return;
            }
        } else if (mode == VfxMode.AAA_PARTICLE) {
            aaaTried = true;
            if (tryAaa(effect, aaa)) {
                return;
            }
        }

        if (!photonTried && tryPhoton(photon)) {
            return;
        }

        if (!aaaTried && tryAaa(effect, aaa)) {
            return;
        }

        runVanilla(vanilla);
    }

    public static void run(VfxEffect effect, BooleanSupplier photon, Runnable vanilla) {
        run(effect, photon, null, vanilla);
    }

    private static boolean tryPhoton(BooleanSupplier photon) {
        return photon != null && photon.getAsBoolean();
    }

    private static boolean tryAaa(VfxEffect effect, BooleanSupplier aaa) {
        return aaa != null
                && effect.supportsAaa()
                && AnnoyingLibClientConfig.isAaaParticlesLoaded()
                && aaa.getAsBoolean();
    }

    private static void runVanilla(Runnable vanilla) {
        if (vanilla != null) {
            vanilla.run();
        }
    }
}
