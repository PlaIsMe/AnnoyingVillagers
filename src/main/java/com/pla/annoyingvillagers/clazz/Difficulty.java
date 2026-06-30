package com.pla.annoyingvillagers.clazz;

import java.util.Locale;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    public static Difficulty byName(String name) {
        Difficulty difficulty = findByName(name);
        return difficulty != null ? difficulty : EASY;
    }

    public static Difficulty findByName(String name) {
        for (Difficulty difficulty : values()) {
            if (difficulty.name().equalsIgnoreCase(name)) {
                return difficulty;
            }
        }
        return null;
    }

    public String id() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
