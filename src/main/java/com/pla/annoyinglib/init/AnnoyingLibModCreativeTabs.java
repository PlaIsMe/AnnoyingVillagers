package com.pla.annoyinglib.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class AnnoyingLibModCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "annoyinglib");
    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("annoyinglib_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(AnnoyingLibModItems.DIAMOND_KNIGHT_SWORD.get()))
            .title(Component.translatable("itemGroup.annoyinglib"))
            .displayItems((parameters, output) -> AnnoyingLibModItems.REGISTRY.getEntries().forEach(entry -> {
                if (isVisible(entry.get())) output.accept(entry.get());
            })).build());

    private static boolean isVisible(Item item) {
        return item != AnnoyingLibModItems.DIAMOND_BLASTER_SWORD_ABILITY.get()
                && item != AnnoyingLibModItems.INVENTORY_VIEWER.get();
    }

    public static void register(IEventBus bus) { TABS.register(bus); }
}
