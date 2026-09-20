package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.util.LegacyItemProperties;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class AnnoyingVillagersModItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(AnnoyingVillagers.MODID);

    private static <T extends Item> DeferredHolder<Item, T> register(String name, Supplier<? extends T> factory) {
        return REGISTRY.register(name, id -> LegacyItemProperties.construct(id, factory));
    }

    private static DeferredHolder<Item, Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> entityType) {
        return register(name, () -> new SpawnEggItem(LegacyItemProperties.create().component(
                DataComponents.ENTITY_DATA, TypedEntityData.of(entityType.get(), new CompoundTag()))));
    }

    // Spawn egg
    public static final DeferredHolder<Item, Item> BLUE_DEMON_SPAWN_EGG = registerSpawnEgg("blue_demon_spawn_egg", AnnoyingVillagersModEntities.BLUE_DEMON);
    public static final DeferredHolder<Item, Item> HEROBRINE_CLONE_SPAWN_EGG = registerSpawnEgg("herobrine_clone_spawn_egg", AnnoyingVillagersModEntities.HEROBRINE_CLONE);
    public static final DeferredHolder<Item, Item> SHADOW_HEROBRINE_CLONE_SPAWN_EGG = registerSpawnEgg("shadow_herobrine_clone_spawn_egg", AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE);
    public static final DeferredHolder<Item, Item> TRANSPORTER_HEROBRINE_CLONE_SPAWN_EGG = registerSpawnEgg("transporter_herobrine_clone_spawn_egg", AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE);
    public static final DeferredHolder<Item, Item> HEROBRINE_GREG_SPAWN_EGG = registerSpawnEgg("herobrine_greg_spawn_egg", AnnoyingVillagersModEntities.HEROBRINE_GREG);
    public static final DeferredHolder<Item, Item> LOW_HEROBRINE_CLONE_SPAWN_EGG = registerSpawnEgg("low_herobrine_clone_spawn_egg", AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE);
    public static final DeferredHolder<Item, Item> LOW_SHADOW_HEROBRINE_CLONE_SPAWN_EGG = registerSpawnEgg("low_shadow_herobrine_clone_spawn_egg", AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE);
    public static final DeferredHolder<Item, Item> HEROBRINE_7_SPAWN_EGG = registerSpawnEgg("herobrine_7_spawn_egg", AnnoyingVillagersModEntities.HEROBRINE_7);
    public static final DeferredHolder<Item, Item> NULL_SPAWN_EGG = registerSpawnEgg("null_spawn_egg", AnnoyingVillagersModEntities.NULL);
    public static final DeferredHolder<Item, Item> ARMORED_HEROBRINE_SPAWN_EGG = registerSpawnEgg("armored_herobrine_spawn_egg", AnnoyingVillagersModEntities.ARMORED_HEROBRINE);
    public static final DeferredHolder<Item, Item> DARK_HEROBRINE_SPAWN_EGG = registerSpawnEgg("shadow_herobrine_spawn_egg", AnnoyingVillagersModEntities.SHADOW_HEROBRINE);
    public static final DeferredHolder<Item, Item> GLAIVE_HEROBRINE_SPAWN_EGG = registerSpawnEgg("glaive_herobrine_spawn_egg", AnnoyingVillagersModEntities.GLAIVE_HEROBRINE);
    public static final DeferredHolder<Item, Item> REAPER_HEROBRINE_SPAWN_EGG = registerSpawnEgg("reaper_herobrine_spawn_egg", AnnoyingVillagersModEntities.REAPER_HEROBRINE);
    public static final DeferredHolder<Item, Item> SWORDSMAN_HEROBRINE_SPAWN_EGG = registerSpawnEgg("swordsman_herobrine_spawn_egg", AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE);
    public static final DeferredHolder<Item, Item> AEGIS_HEROBRINE_SPAWN_EGG = registerSpawnEgg("aegis_herobrine_spawn_egg", AnnoyingVillagersModEntities.AEGIS_HEROBRINE);
    public static final DeferredHolder<Item, Item> SLEDGEHAMMER_HEROBRINE_SPAWN_EGG = registerSpawnEgg("sledgehammer_herobrine_spawn_egg", AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE);
    public static final DeferredHolder<Item, Item> VILLAGER_SCOUT_SPAWN_EGG = registerSpawnEgg("villager_scout_spawn_egg", AnnoyingVillagersModEntities.VILLAGER_SCOUT);
    public static final DeferredHolder<Item, Item> VILLAGER_SCOUT_CAPTAIN_SPAWN_EGG = registerSpawnEgg("villager_scout_captain_spawn_egg", AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN);
    public static final DeferredHolder<Item, Item> BLUE_VILLAGER_KNIGHT_SPAWN_EGG = registerSpawnEgg("blue_villager_knight_spawn_egg", AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT);
    public static final DeferredHolder<Item, Item> GREEN_VILLAGER_KNIGHT_SPAWN_EGG = registerSpawnEgg("green_villager_knight_spawn_egg", AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT);
    public static final DeferredHolder<Item, Item> RED_VILLAGER_KNIGHT_SPAWN_EGG = registerSpawnEgg("red_villager_knight_spawn_egg", AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT);
    public static final DeferredHolder<Item, Item> PURPLE_VILLAGER_KNIGHT_SPAWN_EGG = registerSpawnEgg("purple_villager_knight_spawn_egg", AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT);
    public static final DeferredHolder<Item, Item> STEVE_SPAWN_EGG = registerSpawnEgg("steve_spawn_egg", AnnoyingVillagersModEntities.STEVE);
    public static final DeferredHolder<Item, Item> ANGRY_STEVE_SPAWN_EGG = registerSpawnEgg("angry_steve_spawn_egg", AnnoyingVillagersModEntities.ANGRY_STEVE);
    public static final DeferredHolder<Item, Item> ALEX_SPAWN_EGG = registerSpawnEgg("alex_spawn_egg", AnnoyingVillagersModEntities.ALEX);
    public static final DeferredHolder<Item, Item> JEV_SPAWN_EGG = registerSpawnEgg("jev_spawn_egg", AnnoyingVillagersModEntities.JEV);
    public static final DeferredHolder<Item, Item> CHRIS_SPAWN_EGG = registerSpawnEgg("chris_spawn_egg", AnnoyingVillagersModEntities.CHRIS);
    public static final DeferredHolder<Item, Item> IRON_GOLEM_WARRIOR_SPAWN_EGG = registerSpawnEgg("iron_golem_warrior_spawn_egg", AnnoyingVillagersModEntities.IRON_GOLEM_WARRIOR);
    // ------------------------------

    // Misc item
    public static final DeferredHolder<Item, Item> ENCHANT_BED_ITEM = register("enchant_bed_item", EnchantBedItem::new);
    public static final DeferredHolder<Item, Item> ENCHANTED_ARROW = register("enchanted_arrow", () -> new EnchantedArrowItem(LegacyItemProperties.create()));
    public static final DeferredHolder<Item, Item> ADVANCED_FISHING_ROD = register("advanced_fishing_rod", AdvancedFishingRod::new);
    public static final DeferredHolder<Item, Item> TONY_THE_FISHING_ROD = register("tony_the_fishing_rod", TonyTheFishingRod::new);
    public static final DeferredHolder<Item, Item> HOOK_GUN = register("hook_gun", HookGunItem::new);
    public static final DeferredHolder<Item, Item> TRANSPORTER_FRAGMENT = register("transporter_fragment", TransporterFragmentItem::new);
    public static final DeferredHolder<Item, Item> INVENTORY_VIEWER = register("inventory_viewer", InventoryViewerItem::new);
    public static final DeferredHolder<Item, Item> VILLAGER_HEAD = register("villager_head", VillagerHeadItem::new);
    public static final DeferredHolder<Item, Item> JEV_GLASSES = register("jev_glasses", JevGlassesItem::new);
    public static final DeferredHolder<Item, Item> ENCHANTED_ENDER_PEARL = register("enchanted_ender_pearl", EnchantedEnderPearlItem::new);
    public static final DeferredHolder<Item, Item> JEV_BOOK = register("jev_book", JevBookItem::new);
    public static final DeferredHolder<Item, Item> JEV_PENCIL = register("jev_pencil", JevPencilItem::new);
    public static final DeferredHolder<Item, Item> POISON_EGG_ITEM = register("poison_egg", PoisonEggItem::new);
    public static final DeferredHolder<Item, Item> ENDER_AEGIS_PROJECTILE = register("ender_aegis_projectile", EnderAegisProjectileItem::new);
    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_BURST = register("shadow_obsidian_burst", ShadowObsidianBurstItem::new);
    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_STRAIGHT = register("shadow_obsidian_straight", ShadowObsidianStraightItem::new);
    public static final DeferredHolder<Item, Item> DEMONIAC_VOLTAGE_REAVER_BLADE = register("demoniac_voltage_reaver_blade", DemoniacVoltageReaverBladeItem::new);
    public static final DeferredHolder<Item, Item> DEMONIAC_VOLTAGE_REAVER_FRAGMENT = register("demoniac_voltage_reaver_fragment", DemoniacVoltageReaverFragmentItem::new);
    public static final DeferredHolder<Item, Item> DEMONIAC_VOLTAGE_REAVER_HILT = register("demoniac_voltage_reaver_hilt", DemoniacVoltageReaverHiltItem::new);
    public static final DeferredHolder<Item, Item> ELITE_OBSIDIAN = register("elite_obsidian", EliteObsidianItem::new);
    public static final DeferredHolder<Item, Item> ELITE_OBSIDIAN_LONG = register("elite_obsidian_long", EliteObsidianLongItem::new);
    public static final DeferredHolder<Item, Item> ELITE_OBSIDIAN_BIG = register("elite_obsidian_big", EliteObsidianBigItem::new);
    public static final DeferredHolder<Item, Item> ELITE_OBSIDIAN_BODY = register("elite_obsidian_body", EliteObsidianBodyItem::new);
    public static final DeferredHolder<Item, Item> VILLAGER_SCOUT_HELMET_FIX = register("villager_scout_helmet_fix", VillagerScoutHelmetFixItem::new);
    public static final DeferredHolder<Item, Item> RED_VILLAGER_KNIGHT_HELMET_FIX = register("red_villager_knight_helmet_fix", RedVillagerKnightHelmetFixItem::new);
    public static final DeferredHolder<Item, Item> BLUE_VILLAGER_KNIGHT_HELMET_FIX = register("blue_villager_knight_helmet_fix", BlueVillagerKnightHelmetFixItem::new);
    public static final DeferredHolder<Item, Item> GREEN_VILLAGER_KNIGHT_HELMET_FIX = register("green_villager_knight_helmet_fix", GreenVillagerKnightHelmetFixItem::new);
    public static final DeferredHolder<Item, Item> PURPLE_VILLAGER_KNIGHT_HELMET_FIX = register("purple_villager_knight_helmet_fix", PurpleVillagerKnightHelmetFixItem::new);
    // ------------------------------

    // Gems
    public static final DeferredHolder<Item, Item> COMPRESSED_DIAMOND = register("compressed_diamond", CompressedDiamondItem::new);
    public static final DeferredHolder<Item, Item> RUBY = register("ruby", RubyItem::new);
    public static final DeferredHolder<Item, Item> DARK_NETHERITE = register("dark_netherite", DarkNetheriteItem::new);
    public static final DeferredHolder<Item, Item> PURPLE_GEM = register("purple_gem", PurpleGemItem::new);
    // ------------------------------

    // Weapon
    public static final DeferredHolder<Item, Item> LEGENDARY_SWORD = register("legendary_sword", LegendarySwordItem::new);
    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_WEAPON = register("shadow_obsidian_weapon", ShadowObsidianWeaponItem::new);
    public static final DeferredHolder<Item, Item> OBSIDIAN_WEAPON = register("obsidian_weapon", ObsidianWeaponItem::new);
    public static final DeferredHolder<Item, Item> BEDROCK_WEAPON = register("bedrock_weapon", BedrockWeaponItem::new);
    public static final DeferredHolder<Item, Item> CRAFTING_TABLE = register("crafting_table", CraftingTableItem::new);
    public static final DeferredHolder<Item, Item> WOODEN_DOOR = register("wooden_door", WoodenDoorItem::new);
    public static final DeferredHolder<Item, Item> LADDER = register("ladder", LadderItem::new);
    public static final DeferredHolder<Item, Item> TRAPDOOR = register("trapdoor", TrapdoorItem::new);
    public static final DeferredHolder<Item, Item> NULL_WEAPON = register("null_weapon", NullWeaponItem::new);
    public static final DeferredHolder<Item, Item> NULL_SWORD = register("null_sword", NullSwordItem::new);
    public static final DeferredHolder<Item, Item> NULL_AXE = register("null_axe", NullAxeItem::new);
    public static final DeferredHolder<Item, Item> NULL_PICKAXE = register("null_pickaxe", NullPickaxeItem::new);
    public static final DeferredHolder<Item, Item> NULL_SHOVEL = register("null_shovel", NullShovelItem::new);
    public static final DeferredHolder<Item, Item> NULL_HOE = register("null_hoe", NullHoeItem::new);
    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_PILLAR = register("shadow_obsidian_pillar", ShadowObsidianPillarItem::new);
    public static final DeferredHolder<Item, Item> ENDER_GLAIVE = register("ender_glaive", EnderGlaiveItem::new);
    public static final DeferredHolder<Item, Item> ENDER_SLAYER_SCYTHE = register("ender_slayer_scythe", EnderSlayerScytheItem::new);
    public static final DeferredHolder<Item, Item> DESTRUCTION_EYE = register("destruction_eye", DestructionEyeItem::new);
    public static final DeferredHolder<Item, Item> DEMONIAC_VOLTAGE_REAVER = register("demoniac_voltage_reaver", DemoniacVoltageReaverItem::new);
    public static final DeferredHolder<Item, Item> OBSIDIAN_SLEDGEHAMMER = register("obsidian_sledgehammer", ObsidianSledgehammerItem::new);
    public static final DeferredHolder<Item, Item> ENDER_AEGIS = register("ender_aegis", EnderAegisItem::new);
    public static final DeferredHolder<Item, Item> HEROBRINE_ENDER_EYE = register("herobrine_ender_eye", HerobrineEnderEyeItem::new);
    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_SWORD = register("shadow_obsidian_sword", ShadowObsidianSwordItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_CLAW = register("diamond_claw", DiamondClawItem::new);
    public static final DeferredHolder<Item, Item> WARDEN_AXE = register("warden_axe", WardenAxeItem::new);
    public static final DeferredHolder<Item, Item> BLUE_DEMON_TRIDENT = register("blue_demon_trident", BlueDemonTridentItem::new);
    public static final DeferredHolder<Item, Item> DARKNESS_SWORD = register("darkness_sword", DarknessSwordItem::new);
    public static final DeferredHolder<Item, Item> RED_STEEL_AXE = register("red_steel_axe", RedSteelAxeItem::new);
    public static final DeferredHolder<Item, Item> RED_STEEL_AXE_FORKED = register("red_steel_axe_forked", RedSteelAxeForkedItem::new);
    public static final DeferredHolder<Item, Item> RED_STEEL_AXE_SPEAR_SHORT = register("red_steel_axe_spear_short", RedSteelAxeSpearShortItem::new);
    public static final DeferredHolder<Item, Item> RED_STEEL_AXE_SPEAR_MIDDLE = register("red_steel_axe_spear_middle", RedSteelAxeSpearMiddleItem::new);
    public static final DeferredHolder<Item, Item> RED_STEEL_AXE_SPEAR_LONG = register("red_steel_axe_spear_long", RedSteelAxeSpearLongItem::new);
    public static final DeferredHolder<Item, Item> HOLY_LLAMA_HAMMER = register("holy_llama_hammer", HolyLlamaHammerItem::new);
    public static final DeferredHolder<Item, Item> BLACK_FIRE_SWORD = register("black_fire_sword", BlackFireSwordItem::new);
    public static final DeferredHolder<Item, Item> BLUE_FLAME_SWORD = register("blue_flame_sword", BlueFlameSwordItem::new);
    public static final DeferredHolder<Item, Item> CENTRANOS_SWORD = register("centranos_sword", CentranosSwordItem::new);
    public static final DeferredHolder<Item, Item> CLOW_SWORD = register("clow_sword", ClowSwordItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_ATTRACTOR_SWORD = register("diamond_attractor_sword", DiamondAttractorSwordItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_BLASTER_SWORD = register("diamond_blaster_sword", DiamondBlasterSwordItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_BLASTER_SWORD_ABILITY = register("diamond_blaster_sword_ability", DiamondBlasterSwordAbilityItem::new);
    public static final DeferredHolder<Item, Item> HACKER_SWORD = register("hacker_sword", HackerSwordItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_FALCHION = register("diamond_falchion", DiamondFalchionItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_WARBLADE = register("diamond_warblade", DiamondWarbladeItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_GREAT_FALCHION = register("diamond_great_falchion", DiamondGreatFalchionItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_SABRE = register("diamond_sabre", DiamondSabreItem::new);
    public static final DeferredHolder<Item, Item> HOOKED_DIAMOND_SWORD = register("hooked_diamond_sword", HookedDiamondSwordItem::new);
    public static final DeferredHolder<Item, Item> HOOKED_IRON_SWORD = register("hooked_iron_sword", HookedIronSwordItem::new);
    public static final DeferredHolder<Item, Item> HOOKED_GOLDEN_SWORD = register("hooked_golden_sword", HookedGoldenSwordItem::new);
    public static final DeferredHolder<Item, Item> IRON_CLEAVER = register("iron_cleaver", IronCleaverItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_LAEVATEINN = register("diamond_laevateinn", DiamondLaevateinnItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_LONGSWORD = register("diamond_longsword", DiamondLongSwordItem::new);
    public static final DeferredHolder<Item, Item> GOLDEN_LONGSWORD = register("golden_longsword", GoldenLongSwordItem::new);
    public static final DeferredHolder<Item, Item> IRON_LONGSWORD = register("iron_longsword", IronLongSwordItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_CHIPPED_LONGSWORD = register("diamond_chipped_longsword", DiamondChippedLongswordItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_GREATSWORD = register("diamond_greatsword", DiamondGreatswordItem::new);
    public static final DeferredHolder<Item, Item> DNAX_HOOKED_SWORD = register("dnax_hooked_sword", DNAxHookedSwordItem::new);
    public static final DeferredHolder<Item, Item> DNAX_HOOKED_SWORD_ABILITY = register("dnax_hooked_sword_ability", DNAxHookedSwordAbilityItem::new);
    public static final DeferredHolder<Item, Item> FLANKER_HOOKED_SWORD = register("flanker_hooked_sword", FlankerHookedSwordItem::new);
    public static final DeferredHolder<Item, Item> GREAT_SWORD = register("great_sword", GreatSwordItem::new);
    public static final DeferredHolder<Item, Item> GREAT_SWORD_SKILL = register("great_sword_skill", GreatSwordSkillItem::new);
    public static final DeferredHolder<Item, Item> IRON_TWIN_BLADE_KATANA = register("iron_twin_blade_katana", IronTwinBladeKatanaItem::new);
    public static final DeferredHolder<Item, Item> PALADIN_SWORD = register("paladin_sword", PaladinSwordItem::new);
    public static final DeferredHolder<Item, Item> RUBY_GREATSWORD = register("ruby_greatsword", RubyGreatswordItem::new);
    public static final DeferredHolder<Item, Item> RUBY_LONGSWORD = register("ruby_longsword", PurpleGemLongSwordItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_KNIGHT_SWORD = register("diamond_knight_sword", DiamondKnightSwordItem::new);
    public static final DeferredHolder<Item, Item> RUBY_KNIGHT_SWORD = register("ruby_knight_sword", RubyKnightSwordItem::new);
    public static final DeferredHolder<Item, Item> RUBY_SWORD = register("ruby_sword", RubySwordItem::new);
    public static final DeferredHolder<Item, Item> THUNDER_DIAMOND_BLADE = register("thunder_diamond_blade", ThunderDiamondBladeItem::new);
    public static final DeferredHolder<Item, Item> JADE_SWORD = register("jade_sword", JadeSwordItem::new);
    public static final DeferredHolder<Item, Item> RED_DIAMOND_SWORD = register("red_diamond_sword", RedDiamondSwordItem::new);
    public static final DeferredHolder<Item, Item> WOOPIE_THE_SWORD = register("woopie_the_sword", WoopieTheSwordItem::new);
    public static final DeferredHolder<Item, Item> NETHERITE_SABRE = register("netherite_sabre", NetheriteSabreItem::new);
    public static final DeferredHolder<Item, Item> NETHERITE_FALCHION = register("netherite_falchion", NetheriteFalchionItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_HALBERD = register("diamond_halberd", DiamondHalberdItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_GREATAXE = register("diamond_greataxe", DiamondGreataxeItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_BATTLEAXE = register("diamond_battleaxe", DiamondBattleaxeItem::new);
    public static final DeferredHolder<Item, Item> EARTH_AXE = register("earth_axe", EarthAxeItem::new);
    public static final DeferredHolder<Item, Item> EXTERMINATOR_BATTLEAXE_GREEN = register("exterminator_battleaxe_green", ExterminatorBattleaxeGreenItem::new);
    public static final DeferredHolder<Item, Item> EXTERMINATOR_BATTLEAXE = register("exterminator_battleaxe", ExterminatorBattleaxeItem::new);
    public static final DeferredHolder<Item, Item> GIANT_NETHERITE_AXE = register("giant_netherite_axe", GiantNetheriteAxeItem::new);
    public static final DeferredHolder<Item, Item> RED_AXE = register("red_axe", RedAxeItem::new);
    public static final DeferredHolder<Item, Item> GIANT_RED_AXE = register("giant_red_axe", GiantRedAxeItem::new);
    public static final DeferredHolder<Item, Item> IRON_DOUBLE_BLADED_HALBERD = register("iron_double_bladed_halberd", IronDoubleBladedHalberdItem::new);
    public static final DeferredHolder<Item, Item> IRON_GREATAXE = register("iron_greataxe", IronGreataxeItem::new);
    public static final DeferredHolder<Item, Item> IRON_HALBERD = register("iron_halberd", IronHalberdItem::new);
    public static final DeferredHolder<Item, Item> NETHERITE_GREATAXE = register("netherite_greataxe", NetheriteGreataxeItem::new);
    public static final DeferredHolder<Item, Item> SAMANTHA_THE_KILLER_AXE = register("samantha_the_killer_axe", SamanthaTheKillerAxeItem::new);
    public static final DeferredHolder<Item, Item> SPEAR_AXE = register("spear_axe", SpearAxeItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_ARMBLADE = register("diamond_armblade", DiamondArmbladeItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_KNIFE = register("diamond_knife", DiamondKnifeItem::new);
    public static final DeferredHolder<Item, Item> KNIFE = register("knife", KnifeItem::new);
    public static final DeferredHolder<Item, Item> GOLDEN_MOON_BLADE = register("golden_moon_blade", GoldenMoonBladeItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_MOON_BLADE = register("diamond_moon_blade", DiamondMoonBladeItem::new);
    public static final DeferredHolder<Item, Item> NETHERITE_KNIFE = register("netherite_knife", NetheriteKnifeItem::new);
    public static final DeferredHolder<Item, Item> GEM_SHIELD = register("gem_shield", GemShieldItem::new);
    public static final DeferredHolder<Item, Item> HEATER_SHIELD = register("heater_shield", HeaterShield::new);
    public static final DeferredHolder<Item, Item> JESSICA_THE_DARK_SHIELD = register("jessica_the_dark_shield", JessicaTheDarkShieldItem::new);
    public static final DeferredHolder<Item, Item> NETHERITE_SHIELD = register("netherite_shield", NetheriteShield::new);
    public static final DeferredHolder<Item, Item> BLACKSCRATCHER = register("blackscratcher", BlackscratcherItem::new);
    public static final DeferredHolder<Item, Item> BLACKSCRATCHER_TOP = register("blackscratcher_top", BlackscratcherTopItem::new);
    public static final DeferredHolder<Item, Item> BLACKSCRATCHER_BOTTOM = register("blackscratcher_bottom", BlackscratcherBottomItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_BOLT = register("diamond_bolt", DiamondBoltItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_SICKLE = register("diamond_sickle", DiamondSickleItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_SPEAR = register("diamond_spear", DiamondSpearItem::new);
    public static final DeferredHolder<Item, Item> DOUBLE_DIAMOND_GLAIVE = register("double_diamond_glaive", DoubleDiamondGlaiveItem::new);
    public static final DeferredHolder<Item, Item> IRON_SICKLE = register("iron_sickle", IronSickleItem::new);
    public static final DeferredHolder<Item, Item> NETHERITE_SPEAR = register("netherite_spear", NetheriteSpearItem::new);
    public static final DeferredHolder<Item, Item> TWIN_DIAMOND_SPEAR = register("twin_diamond_spear", TwinDiamondSpearItem::new);
    public static final DeferredHolder<Item, Item> GOLDEN_MACE = register("golden_mace", GoldenMaceItem::new);
    public static final DeferredHolder<Item, Item> DIAMOND_MACE = register("diamond_mace", DiamondMaceItem::new);
    // ------------------------------


    // Item not shown in creative tab
    public static final DeferredHolder<Item, Item> ENCHANT_BED = block(AnnoyingVillagersModBlocks.ENCHANT_BED, null);
    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_SHORT_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR, null);
    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_MIDDLE_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR, null);
    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_LONG_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR, null);
    public static final DeferredHolder<Item, Item> HEAVY_ATTACK_LEGENDARY_SWORD = register("heavy_attack_legendary_sword", HeavyAttackLegendarySwordItem::new);

    public static final DeferredHolder<Item, Item> SHADOW_OBSIDIAN_ITEM = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK, null);
    public static final DeferredHolder<Item, Item> OBSIDIAN_ITEM = block(AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK, null);
    // ------------------------------

    // Armor
    public static final DeferredHolder<Item, Item> BLUE_DEMON_CHESTPLATE = register("blue_demon_chestplate", BlueDemonChestplateItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> COMPRESSED_DIAMOND_HELMET = register("compressed_diamond_helmet", CompressedDiamondArmorItem.Helmet::new);
    public static final DeferredHolder<Item, Item> COMPRESSED_DIAMOND_CHESTPLATE = register("compressed_diamond_chestplate", CompressedDiamondArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> COMPRESSED_DIAMOND_LEGGINGS = register("compressed_diamond_leggings", CompressedDiamondArmorItem.Leggings::new);
    public static final DeferredHolder<Item, Item> COMPRESSED_DIAMOND_BOOTS = register("compressed_diamond_boots", CompressedDiamondArmorItem.Boots::new);
    public static final DeferredHolder<Item, Item> RUBY_HELMET = register("ruby_helmet", RubyArmorItem.Helmet::new);
    public static final DeferredHolder<Item, Item> RUBY_CHESTPLATE = register("ruby_chestplate", RubyArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> RUBY_LEGGINGS = register("ruby_leggings", RubyArmorItem.Leggings::new);
    public static final DeferredHolder<Item, Item> RUBY_BOOTS = register("ruby_boots", RubyArmorItem.Boots::new);
    public static final DeferredHolder<Item, Item> EMERALD_HELMET = register("emerald_helmet", EmeraldArmorItem.Helmet::new);
    public static final DeferredHolder<Item, Item> EMERALD_CHESTPLATE = register("emerald_chestplate", EmeraldArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> EMERALD_LEGGINGS = register("emerald_leggings", EmeraldArmorItem.Leggings::new);
    public static final DeferredHolder<Item, Item> EMERALD_BOOTS = register("emerald_boots", EmeraldArmorItem.Boots::new);
    public static final DeferredHolder<Item, Item> VILLAGER_SCOUT_HELMET = register("villager_scout_helmet", VillagerScoutHelmetItem.Helmet::new);
    public static final DeferredHolder<Item, Item> CLASSIC_GOLDEN_CHESTPLATE = register("classic_golden_chestplate", ClassicGoldenSetArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> CLASSIC_GOLDEN_LEGGINGS = register("classic_golden_leggings", ClassicGoldenSetArmorItem.Leggings::new);
    public static final DeferredHolder<Item, Item> CLASSIC_GOLDEN_BOOTS = register("classic_golden_boots", ClassicGoldenSetArmorItem.Boots::new);
    public static final DeferredHolder<Item, Item> RED_VILLAGER_KNIGHT_HELMET = register("red_villager_knight_helmet", RedVillagerKnightArmorItem.Armor::new);
    public static final DeferredHolder<Item, Item> RED_VILLAGER_KNIGHT_CHESTPLATE = register("red_villager_knight_chestplate", RedVillagerKnightArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> VILLAGER_KNIGHT_LEGGINGS = register("villager_knight_leggings", RedVillagerKnightArmorItem.Leggings::new);
    public static final DeferredHolder<Item, Item> VILLAGER_KNIGHT_BOOTS = register("villager_knight_boots", RedVillagerKnightArmorItem.Boots::new);
    public static final DeferredHolder<Item, Item> BLUE_VILLAGER_KNIGHT_HELMET = register("blue_villager_knight_helmet", BlueVillagerKnightArmorItem.Helmet::new);
    public static final DeferredHolder<Item, Item> BLUE_VILLAGER_KNIGHT_CHESTPLATE = register("blue_villager_knight_chestplate", BlueVillagerKnightArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> GREEN_VILLAGER_KNIGHT_HELMET = register("green_villager_knight_helmet", GreenVillagerKnightArmorItem.Helmet::new);
    public static final DeferredHolder<Item, Item> GREEN_VILLAGER_KNIGHT_CHESTPLATE = register("green_villager_knight_chestplate", GreenVillagerKnightArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> PURPLE_VILLAGER_KNIGHT_HELMET = register("purple_villager_knight_helmet", PurpleVillagerKnightArmorItem.Helmet::new);
    public static final DeferredHolder<Item, Item> PURPLE_VILLAGER_KNIGHT_CHESTPLATE = register("purple_villager_knight_chestplate", PurpleVillagerKnightArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> UNLIGHT_DIAMOND_HELMET = register("unlight_diamond_helmet", UnlightDiamondArmorItem.Helmet::new);
    public static final DeferredHolder<Item, Item> UNLIGHT_DIAMOND_CHESTPLATE = register("unlight_diamond_chestplate", UnlightDiamondArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> UNLIGHT_DIAMOND_LEGGINGS = register("unlight_diamond_leggings", UnlightDiamondArmorItem.Leggings::new);
    public static final DeferredHolder<Item, Item> UNLIGHT_DIAMOND_BOOTS = register("unlight_diamond_boots", UnlightDiamondArmorItem.Boots::new);
    public static final DeferredHolder<Item, Item> HEROBRINE_OBSIDIAN_DIAMOND_HELMET = register("herobrine_obsidian_diamond_helmet", HerobrineObsidianDiamondArmorHelmetItem.Helmet::new);
    public static final DeferredHolder<Item, Item> HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE = register("herobrine_obsidian_diamond_chestplate", HerobrineObsidianDiamondArmorChestplateItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> BROKEN_DIAMOND_HELMET = register("broken_diamond_helmet", BrokenDiamondArmorItem.Helmet::new);
    public static final DeferredHolder<Item, Item> BROKEN_DIAMOND_CHESTPLATE = register("broken_diamond_chestplate", BrokenDiamondArmorItem.Chestplate::new);
    public static final DeferredHolder<Item, Item> BROKEN_DIAMOND_LEGGINGS = register("broken_diamond_leggings", BrokenDiamondArmorItem.Leggings::new);
    public static final DeferredHolder<Item, Item> BROKEN_DIAMOND_BOOTS = register("broken_diamond_boots", BrokenDiamondArmorItem.Boots::new);

    private static DeferredHolder<Item, Item> block(DeferredHolder<Block, Block> registryobject, CreativeModeTab creativemodetab) {
        return register(registryobject.getId().getPath(), () -> new BlockItem(registryobject.get(), (LegacyItemProperties.create())));
    }
}
