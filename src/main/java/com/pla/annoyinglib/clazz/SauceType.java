package com.pla.annoyinglib.clazz;

public enum SauceType {
    BBQ_SAUCE("entity.annoyinglib.bbq_sauce"),
    SWEET_ONION_SAUCE("entity.annoyinglib.sweet_onion_sauce"),
    HONEY_MUSTARD_SAUCE("entity.annoyinglib.honey_mustard_sauce"),
    SOY_SAUCE("entity.annoyinglib.soy_sauce");

    private final String translationKey;

    SauceType(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public boolean isSupport() {
        return this == SWEET_ONION_SAUCE;
    }

    public boolean isShockSauce() {
        return this == HONEY_MUSTARD_SAUCE || this == SOY_SAUCE;
    }
}