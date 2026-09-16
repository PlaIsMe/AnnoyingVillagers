package com.pla.annoyingvillagers.specialanimation;

public enum SpecialAnimationFamily {
    AV_WARDEN("av_warden"),
    AV_GOLEM("av_golem"),
    GOLEM_ARMS("golem_arms"),
    OBSIDIAN_DIAMOND_HELMET("obsidian_diamond_helmet"),
    OBSIDIAN_DIAMOND_CHESTPLATE("obsidian_diamond_chestplate");

    private final String path;

    SpecialAnimationFamily(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }
}
