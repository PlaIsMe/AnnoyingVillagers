package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.TicketType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class AnnoyingVillagersModTicketTypes {
    public static final DeferredRegister<TicketType> REGISTRY =
            DeferredRegister.create(BuiltInRegistries.TICKET_TYPE, AnnoyingVillagers.MODID);

    public static final DeferredHolder<TicketType, TicketType> PERSISTENT_PLAYER_NPC = REGISTRY.register(
            "persistent_player_npc",
            () -> new TicketType(TicketType.NO_TIMEOUT, TicketType.FLAG_LOADING | TicketType.FLAG_SIMULATION));

    private AnnoyingVillagersModTicketTypes() {}
}
