package com.pla.annoyingvillagers.clazz;


import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import net.minecraft.util.RandomSource;

public enum PlayerNpcTarget {
    MONSTER_HUNTER(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_MONSTER_HUNTER.get()),
    VILLAGER_HUNTER(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_VILLAGER_HUNTER.get()),
    PLAYER_HUNTER(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_PLAYER_HUNTER.get()),
    HOSTILE_HUNTER(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_HOSTILE_HUNTER.get()),
    PASSIVE_HUNTER(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_PASSIVE_HUNTER.get()),
    ANIMAL_HUNTER(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_ANIMAL_HUNTER.get());

    private final double weight;
    PlayerNpcTarget() {
        this(1.0D);
    }
    PlayerNpcTarget(double weight) {
        if (weight < 0) throw new IllegalArgumentException("weight must be >= 0");
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public static PlayerNpcTarget randomByWeight(RandomSource randomSource) {
        double total = 0.0D;
        for (PlayerNpcTarget playerNpcTarget : values()) total += playerNpcTarget.weight;
        if (total <= 0.0D) {
            PlayerNpcTarget[] value = values();
            return value[randomSource.nextInt(value.length)];
        }

        double random = randomSource.nextDouble() * total;
        for (PlayerNpcTarget playerNpcTarget : values()) {
            random -= playerNpcTarget.weight;
            if (random < 0.0D) return playerNpcTarget;
        }
        return values()[values().length - 1];
    }
}