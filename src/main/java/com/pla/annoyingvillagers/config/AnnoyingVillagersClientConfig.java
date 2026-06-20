package com.pla.annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public final class AnnoyingVillagersClientConfig {
    private static final String PHOTON_MOD_ID = "photon";
    private static final String AAA_PARTICLES_MOD_ID = "aaa_particles";

    private static final Map<VfxEffect, ForgeConfigSpec.ConfigValue<String>> VFX_VALUES =
            new EnumMap<>(VfxEffect.class);

    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment(
                "Client VFX selection.",
                "DEFAULT keeps the old priority: photon > aaa_particles > vanilla.",
                "If the selected option is unavailable at runtime, the effect falls back to DEFAULT behavior. Example: choosing AAA_PARTICLE without aaa_particles installed will use DEFAULT routing.",
                "All options default to DEFAULT."
        ).push("vfx");

        for (VfxEffect effect : VfxEffect.values()) {
            VFX_VALUES.put(effect, builder
                    .comment(effect.displayName(), "Allowed values: " + allowedValuesComment(effect.supportsAaa()))
                    .define(effect.configKey(), VfxMode.DEFAULT.name(), value -> isValidMode(value, effect.supportsAaa())));
        }

        builder.pop();
        SPEC = builder.build();
    }

    private AnnoyingVillagersClientConfig() {
    }

    public static VfxMode getMode(VfxEffect effect) {
        ForgeConfigSpec.ConfigValue<String> value = VFX_VALUES.get(effect);
        if (value == null) {
            return VfxMode.DEFAULT;
        }

        VfxMode mode = parseMode(value.get(), effect.supportsAaa());
        return mode == null ? VfxMode.DEFAULT : mode;
    }

    public static boolean isAaaParticlesLoaded() {
        return ModList.get().isLoaded(AAA_PARTICLES_MOD_ID);
    }

    public static boolean isPhotonModLoaded() {
        return ModList.get().isLoaded(PHOTON_MOD_ID);
    }

    public static boolean shouldUseAaaParticles(VfxEffect effect) {
        if (effect == null || !effect.supportsAaa() || !isAaaParticlesLoaded()) {
            return false;
        }

        return getMode(effect) != VfxMode.VANILLA;
    }

    public static boolean shouldUsePhotonWhenAvailable(VfxEffect effect) {
        if (!isPhotonModLoaded()) {
            return false;
        }

        VfxMode mode = getMode(effect);
        if (mode == VfxMode.VANILLA) {
            return false;
        }

        if (mode == VfxMode.AAA_PARTICLE) {
            return !(effect.supportsAaa() && isAaaParticlesLoaded());
        }

        return true;
    }

    private static boolean isValidMode(Object rawValue, boolean supportsAaa) {
        return parseMode(rawValue, supportsAaa) != null;
    }

    private static VfxMode parseMode(Object rawValue, boolean supportsAaa) {
        if (!(rawValue instanceof String value)) {
            return null;
        }

        try {
            VfxMode mode = VfxMode.valueOf(value.trim().toUpperCase(Locale.ROOT));
            if (mode == VfxMode.AAA_PARTICLE && !supportsAaa) {
                return null;
            }
            return mode;
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private static String allowedValuesComment(boolean supportsAaa) {
        return supportsAaa
                ? "[DEFAULT, PHOTON, AAA_PARTICLE, VANILLA]"
                : "[DEFAULT, PHOTON, VANILLA]";
    }

    public enum VfxMode {
        DEFAULT,
        PHOTON,
        AAA_PARTICLE,
        VANILLA
    }

    public enum VfxEffect {
        GLAIVE_EXPLOSION("glaiveExplosion", "Ender Glaive explosion", true),
        HEROBRINE_PORTAL("herobrinePortal", "Herobrine portal", true),
        HEROBRINE_ASSISTANCE("herobrineAssistance", "Herobrine assistance", true),
        ENDER_AEGIS_SPARK("enderAegisSpark", "Ender Aegis spark", false),
        ELITE_HEROBRINE("spawnEliteEffect", "spawnEliteEffect / Elite Herobrine lightning", false),
        BLUE_DEMON_LIGHTNING("blueDemonLightning", "Blue Demon lightning", false),
        WOOPIE_SWORD_WIND("woopieSwordWind", "Woopie Sword wind", true),
        BLACK_FIRE("blackFire", "Black Fire", true),
        DIAMOND_ATTRACTOR("diamondAttractor", "Diamond Attractor", true),
        TELEPORT_PORTAL("teleportPortal", "Teleport Portal (Photon: snakeportal)", true),
        DRAGON_BEAM("dragonBeam", "Herobrine Dragon beam", true),
        DRAGON_BEAM_HIT("dragonBeamHit", "Herobrine Dragon beam hit", true),
        BLUE_DEMON_THUNDER_BEAM("blueDemonThunderBeam", "Blue Demon thunder beam", true);

        private final String configKey;
        private final String displayName;
        private final boolean supportsAaa;

        VfxEffect(String configKey, String displayName, boolean supportsAaa) {
            this.configKey = configKey;
            this.displayName = displayName;
            this.supportsAaa = supportsAaa;
        }

        public String configKey() {
            return configKey;
        }

        public String displayName() {
            return displayName;
        }

        public boolean supportsAaa() {
            return supportsAaa;
        }
    }
}
