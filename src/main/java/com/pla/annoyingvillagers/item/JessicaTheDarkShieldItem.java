package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.event.ShieldRendererEvent;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class JessicaTheDarkShieldItem extends ShieldItem {
    public JessicaTheDarkShieldItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(1561)
        );
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ShieldRendererEvent.instance;
            }
        });
    }
}