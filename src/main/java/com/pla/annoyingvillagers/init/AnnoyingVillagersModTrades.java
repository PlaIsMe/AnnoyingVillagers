package com.pla.annoyingvillagers.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class AnnoyingVillagersModTrades {

    @SubscribeEvent
    public static void registerWanderingTrades(WandererTradesEvent wanderertradesevent) {
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.SPECTRAL_ARROW), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.SKELETON_SKULL), new ItemStack(Items.GOLD_INGOT, 10), new ItemStack(Items.TOTEM_OF_UNDYING), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.MUSIC_DISC_STAL), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.MUSIC_DISC_STRAD), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.MUSIC_DISC_WARD), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.MUSIC_DISC_11), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.DIAMOND, 30), new ItemStack(Items.GOLD_NUGGET, 20), new ItemStack(Items.EMERALD, 64), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.NETHERITE_SWORD), new ItemStack(Items.DIAMOND), new ItemStack(Items.TRIDENT), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.TOTEM_OF_UNDYING), new ItemStack(Items.EXPERIENCE_BOTTLE, 64), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.EMERALD, 2), new ItemStack(Items.MILK_BUCKET), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.EMERALD), new ItemStack(Items.NAME_TAG), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.FLINT), new ItemStack(Blocks.TNT), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.EMERALD, 10), new ItemStack(Items.NETHERITE_INGOT), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.BOW), new ItemStack(Items.SADDLE), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.EMERALD, 40), new ItemStack(Items.AMETHYST_SHARD, 60), new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get()), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Blocks.ENCHANTING_TABLE), new ItemStack(Items.NETHER_STAR), new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get()), 10, 5, 1.0F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(Items.TOTEM_OF_UNDYING), new ItemStack(Blocks.WITHER_SKELETON_WALL_SKULL), 10, 5, 0.9F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.END_CRYSTAL, 5), new ItemStack(Blocks.BEACON, 50), new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get()), 10, 5, 1.0F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.MUSIC_DISC_11), new ItemStack(Items.HEART_OF_THE_SEA), new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get()), 10, 5, 1.0F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Blocks.ZOMBIE_HEAD), new ItemStack(Items.GHAST_TEAR), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get()), new ItemStack(Items.NETHER_WART, 50), 10, 5, 0.5F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.MUSIC_DISC_11), new ItemStack(Items.GHAST_TEAR, 30), 10, 5, 0.9F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.EMERALD, 2), new ItemStack(Items.NETHER_WART), 10, 5, 1.0F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Items.BLAZE_ROD), 10, 5, 1.0F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.ENDER_PEARL, 30), new ItemStack(Items.BLAZE_ROD, 20), 10, 5, 1.0F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Blocks.JACK_O_LANTERN), new ItemStack(Items.MAGMA_CREAM), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Blocks.GLOWSTONE), 10, 5, 0.05F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Blocks.CHEST, 55), new ItemStack(Items.ENDER_EYE, 20), new ItemStack(Items.SHULKER_SHELL, 8), 10, 5, 1.0F));
        wanderertradesevent.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.NAUTILUS_SHELL, 50), new ItemStack(Items.MUSIC_DISC_11), new ItemStack(Items.END_CRYSTAL), 10, 5, 1.0F));
    }
}
