package com.pla.annoyinglib.init;


import com.pla.annoyinglib.AnnoyingLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingLibModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AnnoyingLib.MODID);

    public static final RegistryObject<CreativeModeTab> AV_TAB = CREATIVE_MODE_TABS.register("annoyinglib_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AnnoyingLibModItems.LEGENDARY_SWORD.get()))
                    .title(Component.translatable("creativetab.annoyinglib_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_BOLT.get());
                        pOutput.accept(AnnoyingLibModItems.DESTRUCTION_EYE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_ATTRACTOR_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_BLASTER_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_WARBLADE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_FALCHION.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_GREAT_FALCHION.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_SABRE.get());
                        pOutput.accept(AnnoyingLibModItems.HOOKED_DIAMOND_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_LONGSWORD.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_CHIPPED_LONGSWORD.get());
                        pOutput.accept(AnnoyingLibModItems.PALADIN_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_GREATAXE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_ARMBLADE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_MOON_BLADE.get());
                        pOutput.accept(AnnoyingLibModItems.DOUBLE_DIAMOND_GLAIVE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_SICKLE.get());















                        pOutput.accept(AnnoyingLibModItems.BLUE_DEMON_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.VILLAGER_SCOUT_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.VILLAGER_SCOUT_CAPTAIN_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.BLUE_VILLAGER_KNIGHT_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.GREEN_VILLAGER_KNIGHT_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.RED_VILLAGER_KNIGHT_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.PURPLE_VILLAGER_KNIGHT_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.IRON_GOLEM_WARRIOR_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.STEVE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.ANGRY_STEVE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.ALEX_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.CHRIS_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.JEV_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingLibModItems.INVENTORY_VIEWER.get());
                        pOutput.accept(AnnoyingLibModItems.ENCHANT_BED_ITEM.get());
                        pOutput.accept(AnnoyingLibModItems.ENCHANTED_ARROW.get());
                        pOutput.accept(AnnoyingLibModItems.ADVANCED_FISHING_ROD.get());
                        pOutput.accept(AnnoyingLibModItems.TONY_THE_FISHING_ROD.get());
                        pOutput.accept(AnnoyingLibModItems.HOOK_GUN.get());

                        pOutput.accept(AnnoyingLibModItems.VILLAGER_HEAD.get());
                        pOutput.accept(AnnoyingLibModItems.JEV_GLASSES.get());
                        pOutput.accept(AnnoyingLibModItems.JEV_BOOK.get());
                        pOutput.accept(AnnoyingLibModItems.JEV_PENCIL.get());



                        pOutput.accept(AnnoyingLibModItems.POISON_EGG_ITEM.get());

                        pOutput.accept(AnnoyingLibModItems.CRAFTING_TABLE.get());
                        pOutput.accept(AnnoyingLibModItems.WOODEN_DOOR.get());
                        pOutput.accept(AnnoyingLibModItems.TRAPDOOR.get());
                        pOutput.accept(AnnoyingLibModItems.LADDER.get());













                        pOutput.accept(AnnoyingLibModItems.LEGENDARY_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.BLUE_DEMON_TRIDENT.get());

                        pOutput.accept(AnnoyingLibModItems.DARKNESS_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.RED_STEEL_AXE.get());
                        pOutput.accept(AnnoyingLibModItems.HOLY_LLAMA_HAMMER.get());





                        pOutput.accept(AnnoyingLibModItems.BLACK_FIRE_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.BLUE_FLAME_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.IRON_CLEAVER.get());
                        pOutput.accept(AnnoyingLibModItems.CENTRANOS_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.CLOW_SWORD.get());


                        pOutput.accept(AnnoyingLibModItems.HACKER_SWORD.get());

                        pOutput.accept(AnnoyingLibModItems.DIAMOND_LAEVATEINN.get());


                        pOutput.accept(AnnoyingLibModItems.NETHERITE_FALCHION.get());

                        pOutput.accept(AnnoyingLibModItems.NETHERITE_SABRE.get());

                        pOutput.accept(AnnoyingLibModItems.HOOKED_GOLDEN_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.HOOKED_IRON_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.FLANKER_HOOKED_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.DNAX_HOOKED_SWORD.get());

                        pOutput.accept(AnnoyingLibModItems.GOLDEN_LONGSWORD.get());
                        pOutput.accept(AnnoyingLibModItems.IRON_LONGSWORD.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY_LONGSWORD.get());

                        pOutput.accept(AnnoyingLibModItems.DIAMOND_GREATSWORD.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY_GREATSWORD.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.JADE_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.RED_DIAMOND_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.THUNDER_DIAMOND_BLADE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_KNIGHT_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY_KNIGHT_SWORD.get());

                        pOutput.accept(AnnoyingLibModItems.GREAT_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.WOOPIE_THE_SWORD.get());
                        pOutput.accept(AnnoyingLibModItems.EARTH_AXE.get());
                        pOutput.accept(AnnoyingLibModItems.RED_AXE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_BATTLEAXE.get());
                        pOutput.accept(AnnoyingLibModItems.GIANT_NETHERITE_AXE.get());
                        pOutput.accept(AnnoyingLibModItems.EXTERMINATOR_BATTLEAXE.get());
                        pOutput.accept(AnnoyingLibModItems.EXTERMINATOR_BATTLEAXE_GREEN.get());
                        pOutput.accept(AnnoyingLibModItems.GOLDEN_MACE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_MACE.get());
                        pOutput.accept(AnnoyingLibModItems.IRON_GREATAXE.get());

                        pOutput.accept(AnnoyingLibModItems.NETHERITE_GREATAXE.get());
                        pOutput.accept(AnnoyingLibModItems.IRON_DOUBLE_BLADED_HALBERD.get());
                        pOutput.accept(AnnoyingLibModItems.IRON_HALBERD.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_HALBERD.get());
                        pOutput.accept(AnnoyingLibModItems.SAMANTHA_THE_KILLER_AXE.get());
                        pOutput.accept(AnnoyingLibModItems.KNIFE.get());
                        pOutput.accept(AnnoyingLibModItems.DIAMOND_KNIFE.get());
                        pOutput.accept(AnnoyingLibModItems.NETHERITE_KNIFE.get());

                        pOutput.accept(AnnoyingLibModItems.GOLDEN_MOON_BLADE.get());


                        pOutput.accept(AnnoyingLibModItems.GEM_SHIELD.get());
                        pOutput.accept(AnnoyingLibModItems.HEATER_SHIELD.get());
                        pOutput.accept(AnnoyingLibModItems.JESSICA_THE_DARK_SHIELD.get());
                        pOutput.accept(AnnoyingLibModItems.NETHERITE_SHIELD.get());
                        pOutput.accept(AnnoyingLibModItems.SPEAR_AXE.get());

                        pOutput.accept(AnnoyingLibModItems.DIAMOND_SPEAR.get());
                        pOutput.accept(AnnoyingLibModItems.NETHERITE_SPEAR.get());
                        pOutput.accept(AnnoyingLibModItems.TWIN_DIAMOND_SPEAR.get());
                        pOutput.accept(AnnoyingLibModItems.IRON_TWIN_BLADE_KATANA.get());

                        pOutput.accept(AnnoyingLibModItems.IRON_SICKLE.get());

                        pOutput.accept(AnnoyingLibModItems.BLACKSCRATCHER.get());
                        pOutput.accept(AnnoyingLibModItems.COMPRESSED_DIAMOND.get());
                        pOutput.accept(AnnoyingLibModItems.DARK_NETHERITE.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY.get());
                        pOutput.accept(AnnoyingLibModItems.PURPLE_GEM.get());


                        pOutput.accept(AnnoyingLibModItems.BLUE_DEMON_CHESTPLATE.get());




                        pOutput.accept(AnnoyingLibModItems.COMPRESSED_DIAMOND_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.COMPRESSED_DIAMOND_CHESTPLATE.get());
                        pOutput.accept(AnnoyingLibModItems.COMPRESSED_DIAMOND_LEGGINGS.get());
                        pOutput.accept(AnnoyingLibModItems.COMPRESSED_DIAMOND_BOOTS.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY_CHESTPLATE.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY_LEGGINGS.get());
                        pOutput.accept(AnnoyingLibModItems.RUBY_BOOTS.get());
                        pOutput.accept(AnnoyingLibModItems.EMERALD_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.EMERALD_CHESTPLATE.get());
                        pOutput.accept(AnnoyingLibModItems.EMERALD_LEGGINGS.get());
                        pOutput.accept(AnnoyingLibModItems.EMERALD_BOOTS.get());
                        pOutput.accept(AnnoyingLibModItems.UNLIGHT_DIAMOND_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.UNLIGHT_DIAMOND_CHESTPLATE.get());
                        pOutput.accept(AnnoyingLibModItems.UNLIGHT_DIAMOND_LEGGINGS.get());
                        pOutput.accept(AnnoyingLibModItems.UNLIGHT_DIAMOND_BOOTS.get());
                        pOutput.accept(AnnoyingLibModItems.VILLAGER_SCOUT_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.RED_VILLAGER_KNIGHT_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.RED_VILLAGER_KNIGHT_CHESTPLATE.get());
                        pOutput.accept(AnnoyingLibModItems.BLUE_VILLAGER_KNIGHT_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.BLUE_VILLAGER_KNIGHT_CHESTPLATE.get());
                        pOutput.accept(AnnoyingLibModItems.GREEN_VILLAGER_KNIGHT_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.GREEN_VILLAGER_KNIGHT_CHESTPLATE.get());
                        pOutput.accept(AnnoyingLibModItems.PURPLE_VILLAGER_KNIGHT_HELMET.get());
                        pOutput.accept(AnnoyingLibModItems.PURPLE_VILLAGER_KNIGHT_CHESTPLATE.get());
                        pOutput.accept(AnnoyingLibModItems.VILLAGER_KNIGHT_LEGGINGS.get());
                        pOutput.accept(AnnoyingLibModItems.VILLAGER_KNIGHT_BOOTS.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
