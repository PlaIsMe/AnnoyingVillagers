package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.inventory.InventoryViewerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AnnoyingVillagersModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.MENU, AnnoyingVillagers.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<InventoryViewerMenu>> INVENTORY_VIEWER = REGISTRY.register(
            "inventory_viewer",
            () -> IMenuTypeExtension.create(InventoryViewerMenu::new)
    );
}
