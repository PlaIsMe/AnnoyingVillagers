package com.pla.annoyinglib.clazz;

public interface PersistentPlayerNpc {
    String persistentPlayerIdentity();

    @javax.annotation.Nullable
    default com.pla.annoyinglib.util.NpcTabSkin tabSkin() {
        return com.pla.annoyinglib.util.NpcTabSkin.forIdentity(persistentPlayerIdentity());
    }
}
