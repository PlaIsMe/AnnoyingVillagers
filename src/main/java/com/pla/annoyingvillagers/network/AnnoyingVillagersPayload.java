package com.pla.annoyingvillagers.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/** Marker shared by the mod's play-phase payloads. */
public interface AnnoyingVillagersPayload extends CustomPacketPayload {
    @Override
    default Type<? extends CustomPacketPayload> type() {
        return NetworkRegister.type(getClass());
    }
}
