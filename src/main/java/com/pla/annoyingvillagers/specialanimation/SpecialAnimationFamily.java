package com.pla.annoyingvillagers.specialanimation;

public enum SpecialAnimationFamily {
    AV_WARDEN("av_warden"),
    AV_GOLEM("av_golem"),
    GOLEM_ARMS("golem_arms");

    private final String path;

    SpecialAnimationFamily(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }
}
