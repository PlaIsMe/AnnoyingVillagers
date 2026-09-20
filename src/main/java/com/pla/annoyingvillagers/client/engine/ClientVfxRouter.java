package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.config.AnnoyingVillagersClientConfig;
import com.pla.annoyingvillagers.config.AnnoyingVillagersClientConfig.VfxEffect;
import com.pla.annoyingvillagers.config.AnnoyingVillagersClientConfig.VfxMode;
import net.neoforged.api.distmarker.Dist;

import java.util.function.BooleanSupplier;

public final class ClientVfxRouter {
    private ClientVfxRouter() {
    }

    public static void run(VfxEffect effect, BooleanSupplier photon, BooleanSupplier aaa, Runnable vanilla) {
        VfxMode mode = AnnoyingVillagersClientConfig.getMode(effect);
        if (mode == VfxMode.VANILLA) {
            runVanilla(vanilla);
            return;
        }

        // The bundled Effekseer effects are the native visuals for effects
        // that support AAA Particles. Prefer them in DEFAULT mode; Photon is
        // still the fallback and remains first when explicitly selected.
        if (mode == VfxMode.DEFAULT && tryAaa(effect, aaa)) {
            return;
        }

        boolean photonTried = false;
        boolean aaaTried = mode == VfxMode.DEFAULT;

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

        // When Photon is present and this effect has a Photon implementation,
        // do not hide compatibility failures behind another renderer.
        if (photon != null && AnnoyingVillagersClientConfig.isPhotonModLoaded()) {
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
                && AnnoyingVillagersClientConfig.isAaaParticlesLoaded()
                && aaa.getAsBoolean();
    }

    private static void runVanilla(Runnable vanilla) {
        if (vanilla != null) {
            vanilla.run();
        }
    }
}
