package com.pla.annoyinglib.client.engine;
import com.pla.annoyinglib.compat.VillagerKingdom;
import net.minecraftforge.fml.ModList;
public final class SpriteArrowsCommonEntrypoint {
    public static void replace() {
        if (ModList.get().isLoaded("villager_kingdom")) VillagerKingdom.replaceArrowRenderers();
    }
}
