package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.item.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AnnoyingLibModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, AnnoyingLib.MODID);
    // Existing IDs are retained for items returned from the split project.
    public static final DeferredRegister<Item> RETURNED_ITEMS_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "annoyingvillagers");
    public static final RegistryObject<Item> DESTRUCTION_EYE = RETURNED_ITEMS_REGISTRY.register("destruction_eye", DestructionEyeItem::new);
    public static final RegistryObject<Item> DIAMOND_BOLT = RETURNED_ITEMS_REGISTRY.register("diamond_bolt", DiamondBoltItem::new);


    public static final RegistryObject<Item> DIAMOND_ATTRACTOR_SWORD = AnnoyingLibModItems.REGISTRY.register("diamond_attractor_sword", DiamondAttractorSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_BLASTER_SWORD = AnnoyingLibModItems.REGISTRY.register("diamond_blaster_sword", DiamondBlasterSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_BLASTER_SWORD_ABILITY = AnnoyingLibModItems.REGISTRY.register("diamond_blaster_sword_ability", DiamondBlasterSwordAbilityItem::new);
    public static final RegistryObject<Item> DIAMOND_FALCHION = AnnoyingLibModItems.REGISTRY.register("diamond_falchion", DiamondFalchionItem::new);
    public static final RegistryObject<Item> DIAMOND_WARBLADE = AnnoyingLibModItems.REGISTRY.register("diamond_warblade", DiamondWarbladeItem::new);
    public static final RegistryObject<Item> DIAMOND_GREAT_FALCHION = AnnoyingLibModItems.REGISTRY.register("diamond_great_falchion", DiamondGreatFalchionItem::new);
    public static final RegistryObject<Item> DIAMOND_SABRE = AnnoyingLibModItems.REGISTRY.register("diamond_sabre", DiamondSabreItem::new);
    public static final RegistryObject<Item> HOOKED_DIAMOND_SWORD = AnnoyingLibModItems.REGISTRY.register("hooked_diamond_sword", HookedDiamondSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_LONGSWORD = AnnoyingLibModItems.REGISTRY.register("diamond_longsword", DiamondLongSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_CHIPPED_LONGSWORD = AnnoyingLibModItems.REGISTRY.register("diamond_chipped_longsword", DiamondChippedLongswordItem::new);
    public static final RegistryObject<Item> PALADIN_SWORD = AnnoyingLibModItems.REGISTRY.register("paladin_sword", PaladinSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_GREATAXE = AnnoyingLibModItems.REGISTRY.register("diamond_greataxe", DiamondGreataxeItem::new);
    public static final RegistryObject<Item> DIAMOND_ARMBLADE = AnnoyingLibModItems.REGISTRY.register("diamond_armblade", DiamondArmbladeItem::new);
    public static final RegistryObject<Item> DIAMOND_MOON_BLADE = AnnoyingLibModItems.REGISTRY.register("diamond_moon_blade", DiamondMoonBladeItem::new);
    public static final RegistryObject<Item> DIAMOND_SICKLE = AnnoyingLibModItems.REGISTRY.register("diamond_sickle", DiamondSickleItem::new);
    public static final RegistryObject<Item> DOUBLE_DIAMOND_GLAIVE = AnnoyingLibModItems.REGISTRY.register("double_diamond_glaive", DoubleDiamondGlaiveItem::new);

    private static RegistryObject<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> entityType) {
        return REGISTRY.register(name, () -> new ForgeSpawnEggItem(entityType, 0xFFFFFF, 0xFFFFFF, new Properties()));
    }

    // Spawn egg
    public static final RegistryObject<Item> BLUE_DEMON_SPAWN_EGG = registerSpawnEgg("blue_demon_spawn_egg", AnnoyingLibModEntities.BLUE_DEMON);















    public static final RegistryObject<Item> VILLAGER_SCOUT_SPAWN_EGG = registerSpawnEgg("villager_scout_spawn_egg", AnnoyingLibModEntities.VILLAGER_SCOUT);
    public static final RegistryObject<Item> VILLAGER_SCOUT_CAPTAIN_SPAWN_EGG = registerSpawnEgg("villager_scout_captain_spawn_egg", AnnoyingLibModEntities.VILLAGER_SCOUT_CAPTAIN);
    public static final RegistryObject<Item> BLUE_VILLAGER_KNIGHT_SPAWN_EGG = registerSpawnEgg("blue_villager_knight_spawn_egg", AnnoyingLibModEntities.BLUE_VILLAGER_KNIGHT);
    public static final RegistryObject<Item> GREEN_VILLAGER_KNIGHT_SPAWN_EGG = registerSpawnEgg("green_villager_knight_spawn_egg", AnnoyingLibModEntities.GREEN_VILLAGER_KNIGHT);
    public static final RegistryObject<Item> RED_VILLAGER_KNIGHT_SPAWN_EGG = registerSpawnEgg("red_villager_knight_spawn_egg", AnnoyingLibModEntities.RED_VILLAGER_KNIGHT);
    public static final RegistryObject<Item> PURPLE_VILLAGER_KNIGHT_SPAWN_EGG = registerSpawnEgg("purple_villager_knight_spawn_egg", AnnoyingLibModEntities.PURPLE_VILLAGER_KNIGHT);
    public static final RegistryObject<Item> STEVE_SPAWN_EGG = registerSpawnEgg("steve_spawn_egg", AnnoyingLibModEntities.STEVE);
    public static final RegistryObject<Item> ANGRY_STEVE_SPAWN_EGG = registerSpawnEgg("angry_steve_spawn_egg", AnnoyingLibModEntities.ANGRY_STEVE);
    public static final RegistryObject<Item> ALEX_SPAWN_EGG = registerSpawnEgg("alex_spawn_egg", AnnoyingLibModEntities.ALEX);
    public static final RegistryObject<Item> JEV_SPAWN_EGG = registerSpawnEgg("jev_spawn_egg", AnnoyingLibModEntities.JEV);
    public static final RegistryObject<Item> CHRIS_SPAWN_EGG = registerSpawnEgg("chris_spawn_egg", AnnoyingLibModEntities.CHRIS);
    public static final RegistryObject<Item> IRON_GOLEM_WARRIOR_SPAWN_EGG = registerSpawnEgg("iron_golem_warrior_spawn_egg", AnnoyingLibModEntities.IRON_GOLEM_WARRIOR);
    // ------------------------------

    // Misc item
    public static final RegistryObject<Item> ENCHANT_BED_ITEM = AnnoyingLibModItems.REGISTRY.register("enchant_bed_item", EnchantBedItem::new);
    public static final RegistryObject<Item> ENCHANTED_ARROW = AnnoyingLibModItems.REGISTRY.register("enchanted_arrow", () -> new EnchantedArrowItem(new Item.Properties()));
    public static final RegistryObject<Item> ADVANCED_FISHING_ROD = AnnoyingLibModItems.REGISTRY.register("advanced_fishing_rod", AdvancedFishingRod::new);
    public static final RegistryObject<Item> TONY_THE_FISHING_ROD = AnnoyingLibModItems.REGISTRY.register("tony_the_fishing_rod", TonyTheFishingRod::new);
    public static final RegistryObject<Item> HOOK_GUN = AnnoyingLibModItems.REGISTRY.register("hook_gun", HookGunItem::new);

    public static final RegistryObject<Item> INVENTORY_VIEWER = AnnoyingLibModItems.REGISTRY.register("inventory_viewer", InventoryViewerItem::new);
    public static final RegistryObject<Item> VILLAGER_HEAD = AnnoyingLibModItems.REGISTRY.register("villager_head", VillagerHeadItem::new);
    public static final RegistryObject<Item> JEV_GLASSES = AnnoyingLibModItems.REGISTRY.register("jev_glasses", JevGlassesItem::new);

    public static final RegistryObject<Item> JEV_BOOK = AnnoyingLibModItems.REGISTRY.register("jev_book", JevBookItem::new);
    public static final RegistryObject<Item> JEV_PENCIL = AnnoyingLibModItems.REGISTRY.register("jev_pencil", JevPencilItem::new);
    public static final RegistryObject<Item> POISON_EGG_ITEM = AnnoyingLibModItems.REGISTRY.register("poison_egg", PoisonEggItem::new);










    public static final RegistryObject<Item> VILLAGER_SCOUT_HELMET_FIX = AnnoyingLibModItems.REGISTRY.register("villager_scout_helmet_fix", VillagerScoutHelmetFixItem::new);
    public static final RegistryObject<Item> RED_VILLAGER_KNIGHT_HELMET_FIX = AnnoyingLibModItems.REGISTRY.register("red_villager_knight_helmet_fix", RedVillagerKnightHelmetFixItem::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_KNIGHT_HELMET_FIX = AnnoyingLibModItems.REGISTRY.register("blue_villager_knight_helmet_fix", BlueVillagerKnightHelmetFixItem::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_KNIGHT_HELMET_FIX = AnnoyingLibModItems.REGISTRY.register("green_villager_knight_helmet_fix", GreenVillagerKnightHelmetFixItem::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_KNIGHT_HELMET_FIX = AnnoyingLibModItems.REGISTRY.register("purple_villager_knight_helmet_fix", PurpleVillagerKnightHelmetFixItem::new);
    // ------------------------------

    // Gems
    public static final RegistryObject<Item> COMPRESSED_DIAMOND = AnnoyingLibModItems.REGISTRY.register("compressed_diamond", CompressedDiamondItem::new);
    public static final RegistryObject<Item> RUBY = AnnoyingLibModItems.REGISTRY.register("ruby", RubyItem::new);
    public static final RegistryObject<Item> DARK_NETHERITE = AnnoyingLibModItems.REGISTRY.register("dark_netherite", DarkNetheriteItem::new);
    public static final RegistryObject<Item> PURPLE_GEM = AnnoyingLibModItems.REGISTRY.register("purple_gem", PurpleGemItem::new);
    // ------------------------------

    // Weapon
    public static final RegistryObject<Item> LEGENDARY_SWORD = AnnoyingLibModItems.REGISTRY.register("legendary_sword", LegendarySwordItem::new);



    public static final RegistryObject<Item> CRAFTING_TABLE = AnnoyingLibModItems.REGISTRY.register("crafting_table", CraftingTableItem::new);
    public static final RegistryObject<Item> WOODEN_DOOR = AnnoyingLibModItems.REGISTRY.register("wooden_door", WoodenDoorItem::new);
    public static final RegistryObject<Item> LADDER = AnnoyingLibModItems.REGISTRY.register("ladder", LadderItem::new);
    public static final RegistryObject<Item> TRAPDOOR = AnnoyingLibModItems.REGISTRY.register("trapdoor", TrapdoorItem::new);

















    public static final RegistryObject<Item> BLUE_DEMON_TRIDENT = AnnoyingLibModItems.REGISTRY.register("blue_demon_trident", BlueDemonTridentItem::new);
    public static final RegistryObject<Item> DARKNESS_SWORD = AnnoyingLibModItems.REGISTRY.register("darkness_sword", DarknessSwordItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE = AnnoyingLibModItems.REGISTRY.register("red_steel_axe", RedSteelAxeItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE_FORKED = AnnoyingLibModItems.REGISTRY.register("red_steel_axe_forked", RedSteelAxeForkedItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE_SPEAR_SHORT = AnnoyingLibModItems.REGISTRY.register("red_steel_axe_spear_short", RedSteelAxeSpearShortItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE_SPEAR_MIDDLE = AnnoyingLibModItems.REGISTRY.register("red_steel_axe_spear_middle", RedSteelAxeSpearMiddleItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE_SPEAR_LONG = AnnoyingLibModItems.REGISTRY.register("red_steel_axe_spear_long", RedSteelAxeSpearLongItem::new);
    public static final RegistryObject<Item> HOLY_LLAMA_HAMMER = AnnoyingLibModItems.REGISTRY.register("holy_llama_hammer", HolyLlamaHammerItem::new);
    public static final RegistryObject<Item> BLACK_FIRE_SWORD = AnnoyingLibModItems.REGISTRY.register("black_fire_sword", BlackFireSwordItem::new);
    public static final RegistryObject<Item> BLUE_FLAME_SWORD = AnnoyingLibModItems.REGISTRY.register("blue_flame_sword", BlueFlameSwordItem::new);
    public static final RegistryObject<Item> CENTRANOS_SWORD = AnnoyingLibModItems.REGISTRY.register("centranos_sword", CentranosSwordItem::new);
    public static final RegistryObject<Item> CLOW_SWORD = AnnoyingLibModItems.REGISTRY.register("clow_sword", ClowSwordItem::new);



    public static final RegistryObject<Item> HACKER_SWORD = AnnoyingLibModItems.REGISTRY.register("hacker_sword", HackerSwordItem::new);





    public static final RegistryObject<Item> HOOKED_IRON_SWORD = AnnoyingLibModItems.REGISTRY.register("hooked_iron_sword", HookedIronSwordItem::new);
    public static final RegistryObject<Item> HOOKED_GOLDEN_SWORD = AnnoyingLibModItems.REGISTRY.register("hooked_golden_sword", HookedGoldenSwordItem::new);
    public static final RegistryObject<Item> IRON_CLEAVER = AnnoyingLibModItems.REGISTRY.register("iron_cleaver", IronCleaverItem::new);
    public static final RegistryObject<Item> DIAMOND_LAEVATEINN = AnnoyingLibModItems.REGISTRY.register("diamond_laevateinn", DiamondLaevateinnItem::new);

    public static final RegistryObject<Item> GOLDEN_LONGSWORD = AnnoyingLibModItems.REGISTRY.register("golden_longsword", GoldenLongSwordItem::new);
    public static final RegistryObject<Item> IRON_LONGSWORD = AnnoyingLibModItems.REGISTRY.register("iron_longsword", IronLongSwordItem::new);

    public static final RegistryObject<Item> DIAMOND_GREATSWORD = AnnoyingLibModItems.REGISTRY.register("diamond_greatsword", DiamondGreatswordItem::new);
    public static final RegistryObject<Item> DNAX_HOOKED_SWORD = AnnoyingLibModItems.REGISTRY.register("dnax_hooked_sword", DNAxHookedSwordItem::new);
    public static final RegistryObject<Item> DNAX_HOOKED_SWORD_ABILITY = AnnoyingLibModItems.REGISTRY.register("dnax_hooked_sword_ability", DNAxHookedSwordAbilityItem::new);
    public static final RegistryObject<Item> FLANKER_HOOKED_SWORD = AnnoyingLibModItems.REGISTRY.register("flanker_hooked_sword", FlankerHookedSwordItem::new);
    public static final RegistryObject<Item> GREAT_SWORD = AnnoyingLibModItems.REGISTRY.register("great_sword", GreatSwordItem::new);
    public static final RegistryObject<Item> GREAT_SWORD_SKILL = AnnoyingLibModItems.REGISTRY.register("great_sword_skill", GreatSwordSkillItem::new);
    public static final RegistryObject<Item> IRON_TWIN_BLADE_KATANA = AnnoyingLibModItems.REGISTRY.register("iron_twin_blade_katana", IronTwinBladeKatanaItem::new);

    public static final RegistryObject<Item> RUBY_GREATSWORD = AnnoyingLibModItems.REGISTRY.register("ruby_greatsword", RubyGreatswordItem::new);
    public static final RegistryObject<Item> RUBY_LONGSWORD = AnnoyingLibModItems.REGISTRY.register("ruby_longsword", PurpleGemLongSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_KNIGHT_SWORD = AnnoyingLibModItems.REGISTRY.register("diamond_knight_sword", DiamondKnightSwordItem::new);
    public static final RegistryObject<Item> RUBY_KNIGHT_SWORD = AnnoyingLibModItems.REGISTRY.register("ruby_knight_sword", RubyKnightSwordItem::new);
    public static final RegistryObject<Item> RUBY_SWORD = AnnoyingLibModItems.REGISTRY.register("ruby_sword", RubySwordItem::new);
    public static final RegistryObject<Item> THUNDER_DIAMOND_BLADE = AnnoyingLibModItems.REGISTRY.register("thunder_diamond_blade", ThunderDiamondBladeItem::new);
    public static final RegistryObject<Item> JADE_SWORD = AnnoyingLibModItems.REGISTRY.register("jade_sword", JadeSwordItem::new);
    public static final RegistryObject<Item> RED_DIAMOND_SWORD = AnnoyingLibModItems.REGISTRY.register("red_diamond_sword", RedDiamondSwordItem::new);
    public static final RegistryObject<Item> WOOPIE_THE_SWORD = AnnoyingLibModItems.REGISTRY.register("woopie_the_sword", WoopieTheSwordItem::new);
    public static final RegistryObject<Item> NETHERITE_SABRE = AnnoyingLibModItems.REGISTRY.register("netherite_sabre", NetheriteSabreItem::new);
    public static final RegistryObject<Item> NETHERITE_FALCHION = AnnoyingLibModItems.REGISTRY.register("netherite_falchion", NetheriteFalchionItem::new);
    public static final RegistryObject<Item> DIAMOND_HALBERD = AnnoyingLibModItems.REGISTRY.register("diamond_halberd", DiamondHalberdItem::new);

    public static final RegistryObject<Item> DIAMOND_BATTLEAXE = AnnoyingLibModItems.REGISTRY.register("diamond_battleaxe", DiamondBattleaxeItem::new);
    public static final RegistryObject<Item> EARTH_AXE = AnnoyingLibModItems.REGISTRY.register("earth_axe", EarthAxeItem::new);
    public static final RegistryObject<Item> EXTERMINATOR_BATTLEAXE_GREEN = AnnoyingLibModItems.REGISTRY.register("exterminator_battleaxe_green", ExterminatorBattleaxeGreenItem::new);
    public static final RegistryObject<Item> EXTERMINATOR_BATTLEAXE = AnnoyingLibModItems.REGISTRY.register("exterminator_battleaxe", ExterminatorBattleaxeItem::new);
    public static final RegistryObject<Item> GIANT_NETHERITE_AXE = AnnoyingLibModItems.REGISTRY.register("giant_netherite_axe", GiantNetheriteAxeItem::new);
    public static final RegistryObject<Item> RED_AXE = AnnoyingLibModItems.REGISTRY.register("red_axe", RedAxeItem::new);
    public static final RegistryObject<Item> GIANT_RED_AXE = AnnoyingLibModItems.REGISTRY.register("giant_red_axe", GiantRedAxeItem::new);
    public static final RegistryObject<Item> IRON_DOUBLE_BLADED_HALBERD = AnnoyingLibModItems.REGISTRY.register("iron_double_bladed_halberd", IronDoubleBladedHalberdItem::new);
    public static final RegistryObject<Item> IRON_GREATAXE = AnnoyingLibModItems.REGISTRY.register("iron_greataxe", IronGreataxeItem::new);
    public static final RegistryObject<Item> IRON_HALBERD = AnnoyingLibModItems.REGISTRY.register("iron_halberd", IronHalberdItem::new);
    public static final RegistryObject<Item> NETHERITE_GREATAXE = AnnoyingLibModItems.REGISTRY.register("netherite_greataxe", NetheriteGreataxeItem::new);
    public static final RegistryObject<Item> SAMANTHA_THE_KILLER_AXE = AnnoyingLibModItems.REGISTRY.register("samantha_the_killer_axe", SamanthaTheKillerAxeItem::new);
    public static final RegistryObject<Item> SPEAR_AXE = AnnoyingLibModItems.REGISTRY.register("spear_axe", SpearAxeItem::new);

    public static final RegistryObject<Item> DIAMOND_KNIFE = AnnoyingLibModItems.REGISTRY.register("diamond_knife", DiamondKnifeItem::new);
    public static final RegistryObject<Item> KNIFE = AnnoyingLibModItems.REGISTRY.register("knife", KnifeItem::new);
    public static final RegistryObject<Item> GOLDEN_MOON_BLADE = AnnoyingLibModItems.REGISTRY.register("golden_moon_blade", GoldenMoonBladeItem::new);

    public static final RegistryObject<Item> NETHERITE_KNIFE = AnnoyingLibModItems.REGISTRY.register("netherite_knife", NetheriteKnifeItem::new);
    public static final RegistryObject<Item> GEM_SHIELD = AnnoyingLibModItems.REGISTRY.register("gem_shield", GemShieldItem::new);
    public static final RegistryObject<Item> HEATER_SHIELD = AnnoyingLibModItems.REGISTRY.register("heater_shield", HeaterShield::new);
    public static final RegistryObject<Item> JESSICA_THE_DARK_SHIELD = AnnoyingLibModItems.REGISTRY.register("jessica_the_dark_shield", JessicaTheDarkShieldItem::new);
    public static final RegistryObject<Item> NETHERITE_SHIELD = AnnoyingLibModItems.REGISTRY.register("netherite_shield", NetheriteShield::new);
    public static final RegistryObject<Item> BLACKSCRATCHER = AnnoyingLibModItems.REGISTRY.register("blackscratcher", BlackscratcherItem::new);
    public static final RegistryObject<Item> BLACKSCRATCHER_TOP = AnnoyingLibModItems.REGISTRY.register("blackscratcher_top", BlackscratcherTopItem::new);
    public static final RegistryObject<Item> BLACKSCRATCHER_BOTTOM = AnnoyingLibModItems.REGISTRY.register("blackscratcher_bottom", BlackscratcherBottomItem::new);


    public static final RegistryObject<Item> DIAMOND_SPEAR = AnnoyingLibModItems.REGISTRY.register("diamond_spear", DiamondSpearItem::new);

    public static final RegistryObject<Item> IRON_SICKLE = AnnoyingLibModItems.REGISTRY.register("iron_sickle", IronSickleItem::new);
    public static final RegistryObject<Item> NETHERITE_SPEAR = AnnoyingLibModItems.REGISTRY.register("netherite_spear", NetheriteSpearItem::new);
    public static final RegistryObject<Item> TWIN_DIAMOND_SPEAR = AnnoyingLibModItems.REGISTRY.register("twin_diamond_spear", TwinDiamondSpearItem::new);
    public static final RegistryObject<Item> GOLDEN_MACE = AnnoyingLibModItems.REGISTRY.register("golden_mace", GoldenMaceItem::new);
    public static final RegistryObject<Item> DIAMOND_MACE = AnnoyingLibModItems.REGISTRY.register("diamond_mace", DiamondMaceItem::new);
    // ------------------------------


    // Item not shown in creative tab
    public static final RegistryObject<Item> ENCHANT_BED = block(AnnoyingLibModBlocks.ENCHANT_BED, null);



    public static final RegistryObject<Item> HEAVY_ATTACK_LEGENDARY_SWORD = AnnoyingLibModItems.REGISTRY.register("heavy_attack_legendary_sword", HeavyAttackLegendarySwordItem::new);



    // ------------------------------

    // Armor
    public static final RegistryObject<Item> BLUE_DEMON_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("blue_demon_chestplate", BlueDemonChestplateItem.Chestplate::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_HELMET = AnnoyingLibModItems.REGISTRY.register("compressed_diamond_helmet", CompressedDiamondArmorItem.Helmet::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("compressed_diamond_chestplate", CompressedDiamondArmorItem.Chestplate::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_LEGGINGS = AnnoyingLibModItems.REGISTRY.register("compressed_diamond_leggings", CompressedDiamondArmorItem.Leggings::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_BOOTS = AnnoyingLibModItems.REGISTRY.register("compressed_diamond_boots", CompressedDiamondArmorItem.Boots::new);
    public static final RegistryObject<Item> RUBY_HELMET = AnnoyingLibModItems.REGISTRY.register("ruby_helmet", RubyArmorItem.Helmet::new);
    public static final RegistryObject<Item> RUBY_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("ruby_chestplate", RubyArmorItem.Chestplate::new);
    public static final RegistryObject<Item> RUBY_LEGGINGS = AnnoyingLibModItems.REGISTRY.register("ruby_leggings", RubyArmorItem.Leggings::new);
    public static final RegistryObject<Item> RUBY_BOOTS = AnnoyingLibModItems.REGISTRY.register("ruby_boots", RubyArmorItem.Boots::new);
    public static final RegistryObject<Item> EMERALD_HELMET = AnnoyingLibModItems.REGISTRY.register("emerald_helmet", EmeraldArmorItem.Helmet::new);
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("emerald_chestplate", EmeraldArmorItem.Chestplate::new);
    public static final RegistryObject<Item> EMERALD_LEGGINGS = AnnoyingLibModItems.REGISTRY.register("emerald_leggings", EmeraldArmorItem.Leggings::new);
    public static final RegistryObject<Item> EMERALD_BOOTS = AnnoyingLibModItems.REGISTRY.register("emerald_boots", EmeraldArmorItem.Boots::new);
    public static final RegistryObject<Item> VILLAGER_SCOUT_HELMET = AnnoyingLibModItems.REGISTRY.register("villager_scout_helmet", VillagerScoutHelmetItem.Helmet::new);
    public static final RegistryObject<Item> CLASSIC_GOLDEN_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("classic_golden_chestplate", ClassicGoldenSetArmorItem.Chestplate::new);
    public static final RegistryObject<Item> CLASSIC_GOLDEN_LEGGINGS = AnnoyingLibModItems.REGISTRY.register("classic_golden_leggings", ClassicGoldenSetArmorItem.Leggings::new);
    public static final RegistryObject<Item> CLASSIC_GOLDEN_BOOTS = AnnoyingLibModItems.REGISTRY.register("classic_golden_boots", ClassicGoldenSetArmorItem.Boots::new);
    public static final RegistryObject<Item> RED_VILLAGER_KNIGHT_HELMET = AnnoyingLibModItems.REGISTRY.register("red_villager_knight_helmet", RedVillagerKnightArmorItem.Armor::new);
    public static final RegistryObject<Item> RED_VILLAGER_KNIGHT_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("red_villager_knight_chestplate", RedVillagerKnightArmorItem.Chestplate::new);
    public static final RegistryObject<Item> VILLAGER_KNIGHT_LEGGINGS = AnnoyingLibModItems.REGISTRY.register("villager_knight_leggings", RedVillagerKnightArmorItem.Leggings::new);
    public static final RegistryObject<Item> VILLAGER_KNIGHT_BOOTS = AnnoyingLibModItems.REGISTRY.register("villager_knight_boots", RedVillagerKnightArmorItem.Boots::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_KNIGHT_HELMET = AnnoyingLibModItems.REGISTRY.register("blue_villager_knight_helmet", BlueVillagerKnightArmorItem.Helmet::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_KNIGHT_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("blue_villager_knight_chestplate", BlueVillagerKnightArmorItem.Chestplate::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_KNIGHT_HELMET = AnnoyingLibModItems.REGISTRY.register("green_villager_knight_helmet", GreenVillagerKnightArmorItem.Helmet::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_KNIGHT_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("green_villager_knight_chestplate", GreenVillagerKnightArmorItem.Chestplate::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_KNIGHT_HELMET = AnnoyingLibModItems.REGISTRY.register("purple_villager_knight_helmet", PurpleVillagerKnightArmorItem.Helmet::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_KNIGHT_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("purple_villager_knight_chestplate", PurpleVillagerKnightArmorItem.Chestplate::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_HELMET = AnnoyingLibModItems.REGISTRY.register("unlight_diamond_helmet", UnlightDiamondArmorItem.Helmet::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_CHESTPLATE = AnnoyingLibModItems.REGISTRY.register("unlight_diamond_chestplate", UnlightDiamondArmorItem.Chestplate::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_LEGGINGS = AnnoyingLibModItems.REGISTRY.register("unlight_diamond_leggings", UnlightDiamondArmorItem.Leggings::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_BOOTS = AnnoyingLibModItems.REGISTRY.register("unlight_diamond_boots", UnlightDiamondArmorItem.Boots::new);







    private static RegistryObject<Item> block(RegistryObject<Block> registryobject, CreativeModeTab creativemodetab) {
        return AnnoyingLibModItems.REGISTRY.register(registryobject.getId().getPath(), () -> new BlockItem(registryobject.get(), (new Properties())));
    }
}
