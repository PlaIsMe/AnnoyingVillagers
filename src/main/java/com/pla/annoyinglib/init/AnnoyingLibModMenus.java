package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.inventory.InventoryViewerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingLibModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, AnnoyingLib.MODID);

    public static final RegistryObject<MenuType<InventoryViewerMenu>> INVENTORY_VIEWER = REGISTRY.register(
            "inventory_viewer",
            () -> IForgeMenuType.create(InventoryViewerMenu::new)
    );
}
