package com.pla.annoyingvillagers.gametest;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.Difficulty;
import com.pla.annoyingvillagers.util.GlintColorHelper;
import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.util.LegacyNbt;
import com.pla.annoyingvillagers.util.LegacyValueIO;
import com.pla.annoyingvillagers.util.ProgressionData;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import com.pla.annoyingvillagers.entity.VacuumSliceEntity;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.util.EndFireEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public final class PortGameTests {
    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "port/" + path);
    }

    @SubscribeEvent
    public static void functions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, id("item_data"), () -> PortGameTests::itemData);
        event.register(Registries.TEST_FUNCTION, id("value_io"), () -> PortGameTests::valueIO);
        event.register(Registries.TEST_FUNCTION, id("entity_roundtrip"), () -> PortGameTests::entityRoundtrip);
        event.register(Registries.TEST_FUNCTION, id("progression"), () -> PortGameTests::progression);
        event.register(Registries.TEST_FUNCTION, id("glaive_ability"), () -> PortGameTests::glaiveAbility);
        event.register(Registries.TEST_FUNCTION, id("glaive_damage"), () -> PortGameTests::glaiveDamage);
        event.register(Registries.TEST_FUNCTION, id("native_melee"), () -> PortGameTests::nativeMelee);
        event.register(Registries.TEST_FUNCTION, id("blocking_and_fire"), () -> PortGameTests::blockingAndFire);
    }

    @SubscribeEvent
    public static void tests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("environment"), new TestEnvironmentDefinition.AllOf(List.of()));
        for (String name : List.of("item_data", "value_io", "entity_roundtrip", "progression", "glaive_ability", "glaive_damage", "native_melee", "blocking_and_fire")) {
            ResourceKey<Consumer<GameTestHelper>> function = ResourceKey.create(Registries.TEST_FUNCTION, id(name));
            event.registerTest(id(name), new FunctionGameTestInstance(function, new TestData<>(environment, id("empty"), 100, 0, true)));
        }
    }

    private static void itemData(GameTestHelper helper) {
        ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);
        LegacyItemData.update(stack, tag -> tag.putInt("OtherState", 7));
        GlintColorHelper.setColor(stack, GlintColorHelper.PURPLE);
        helper.assertValueEqual(GlintColorHelper.PURPLE, GlintColorHelper.getColor(stack), "string glint");
        ItemStack decoded = ItemStack.CODEC.parse(helper.getLevel().registryAccess().createSerializationContext(net.minecraft.nbt.NbtOps.INSTANCE),
                ItemStack.CODEC.encodeStart(helper.getLevel().registryAccess().createSerializationContext(net.minecraft.nbt.NbtOps.INSTANCE), stack).getOrThrow()).getOrThrow();
        helper.assertValueEqual(GlintColorHelper.PURPLE, GlintColorHelper.getColor(decoded), "serialized glint");
        LegacyItemData.update(stack, tag -> tag.putInt(GlintColorHelper.TAG_COLOR_GLINT, GlintColorHelper.CYAN));
        helper.assertValueEqual(GlintColorHelper.CYAN, GlintColorHelper.getColor(stack), "legacy integer glint");
        GlintColorHelper.clearColor(stack);
        helper.assertValueEqual(GlintColorHelper.NONE, GlintColorHelper.getColor(stack), "cleared glint");
        helper.assertValueEqual(7, LegacyItemData.get(stack).getIntOr("OtherState", 0), "unrelated data survives");
        GlintColorHelper.setColor(stack, GlintColorHelper.BLUE);
        GlintColorHelper.setColor(stack, GlintColorHelper.NONE);
        helper.assertValueEqual(GlintColorHelper.NONE, GlintColorHelper.getColor(stack), "NONE clears glint");
        helper.succeed();
    }

    private static void valueIO(GameTestHelper helper) {
        var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, helper.getLevel().registryAccess());
        output.putInt("ParentField", 17);
        CompoundTag custom = new CompoundTag();
        UUID owner = UUID.randomUUID();
        LegacyNbt.putUUID(custom, "Owner", owner);
        custom.putString("Phase", "second");
        LegacyValueIO.write(output, custom);
        CompoundTag decoded = LegacyValueIO.read(TagValueInput.create(ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), output.buildResult()));
        helper.assertValueEqual(17, decoded.getIntOr("ParentField", 0), "inherited field survives");
        helper.assertValueEqual(owner, LegacyNbt.getUUID(decoded, "Owner"), "owner UUID survives");
        helper.assertValueEqual("second", decoded.getStringOr("Phase", ""), "mod field survives");
        helper.succeed();
    }

    private static void entityRoundtrip(GameTestHelper helper) {
        int checked = 0;
        for (var type : BuiltInRegistries.ENTITY_TYPE) {
            if (!BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals(AnnoyingVillagers.MODID)) continue;
            var original = type.create(helper.getLevel(), EntitySpawnReason.LOAD);
            helper.assertTrue(original != null, "entity factory: " + type);
            original.getPersistentData().putString("PortProbe", "retained");
            if (original instanceof com.pla.annoyingvillagers.clazz.AVNpc npc) {
                ItemStack weapon = new ItemStack(Items.IRON_SWORD);
                GlintColorHelper.setColor(weapon, GlintColorHelper.PURPLE);
                npc.setItemInHand(InteractionHand.MAIN_HAND, weapon);
                npc.getInventory().setItem(5, new ItemStack(Items.GOLDEN_APPLE, 3));
            }
            var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, helper.getLevel().registryAccess());
            original.saveWithoutId(output);
            var restored = type.create(helper.getLevel(), EntitySpawnReason.LOAD);
            restored.load(TagValueInput.create(ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), output.buildResult()));
            helper.assertValueEqual(original.getUUID(), restored.getUUID(), "UUID: " + type);
            helper.assertValueEqual("retained", restored.getPersistentData().getStringOr("PortProbe", ""), "persistent data: " + type);
            if (restored instanceof com.pla.annoyingvillagers.clazz.AVNpc npc) {
                helper.assertTrue(npc.getInventory().getItem(5).is(Items.GOLDEN_APPLE), "NPC inventory item: " + type);
                helper.assertValueEqual(3, npc.getInventory().getItem(5).getCount(), "NPC inventory count: " + type);
                helper.assertTrue(npc.getMainHandItem().is(Items.IRON_SWORD), "NPC held weapon: " + type);
                helper.assertValueEqual(GlintColorHelper.PURPLE, GlintColorHelper.getColor(npc.getMainHandItem()), "NPC weapon data: " + type);
            }
            checked++;
        }
        helper.assertTrue(checked > 0, "mod entities were registered");
        AnnoyingVillagers.LOGGER.info("Port persistence check covered {} entity types", checked);
        helper.succeed();
    }

    private static void progression(GameTestHelper helper) {
        ProgressionData data = ProgressionData.get(helper.getLevel().getServer());
        data.setDifficulty(Difficulty.HARD);
        helper.assertTrue(data == ProgressionData.get(helper.getLevel().getServer()), "SavedData identity is stable");
        var restored = ProgressionData.load(data.save(new CompoundTag(), helper.getLevel().registryAccess()));
        helper.assertValueEqual(Difficulty.HARD, restored.getDifficulty(), "saved mod difficulty");
        helper.assertTrue(restored.isManualDifficulty(), "manual progression choice survives");
        helper.succeed();
    }

    private static net.neoforged.neoforge.common.util.FakePlayer player(GameTestHelper helper) {
        var player = net.neoforged.neoforge.common.util.FakePlayerFactory.get(helper.getLevel(), new com.mojang.authlib.GameProfile(UUID.randomUUID(), "PortTester"));
        Vec3 position = helper.absoluteVec(new Vec3(8, 3, 4));
        player.setPos(position);
        player.setYRot(0);
        player.setXRot(0);
        return player;
    }

    private static void glaiveAbility(GameTestHelper helper) {
        var player = player(helper);
        ItemStack glaive = new ItemStack(AnnoyingVillagersModItems.ENDER_GLAIVE.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, glaive);
        glaive.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getCooldowns().isOnCooldown(glaive), "glaive starts its cooldown");
        var slices = helper.getLevel().getEntitiesOfClass(VacuumSliceEntity.class, player.getBoundingBox().inflate(4), slice -> slice.getOwner() == player);
        helper.assertValueEqual(1, slices.size(), "one owned vacuum slice");
        glaive.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertValueEqual(1, helper.getLevel().getEntitiesOfClass(VacuumSliceEntity.class, player.getBoundingBox().inflate(4), slice -> slice.getOwner() == player).size(), "cooldown rejects a second use");
        slices.forEach(net.minecraft.world.entity.Entity::discard);
        helper.succeed();
    }

    private static void glaiveDamage(GameTestHelper helper) {
        var player = player(helper);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.ENDER_GLAIVE.get()));
        var target = helper.spawn(EntityType.COW, new Vec3(8, 3, 7));
        target.setNoAi(true);
        target.setNoGravity(true);
        float health = target.getHealth();
        EnderGlaiveItem.spawnVacuumSlice(helper.getLevel(), player, 1.0D, 0.0D, 4.0F);
        helper.succeedWhen(() -> helper.assertTrue(target.getHealth() < health, "vacuum slice applies server damage"));
    }

    private static void nativeMelee(GameTestHelper helper) {
        var attacker = helper.spawnWithNoFreeWill(AnnoyingVillagersModEntities.STEVE.get(), new Vec3(8, 3, 4));
        attacker.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        var target = helper.spawnWithNoFreeWill(EntityType.COW, new Vec3(8, 3, 5.4));
        float health = target.getHealth();
        helper.runAfterDelay(8, () -> RigAnimationController.play(attacker, RigAnimationSpecs.get(RigAnimationId.BASIC_ATTACK1), target));
        helper.succeedWhen(() -> helper.assertTrue(target.getHealth() < health, "native rig attack applies server damage"));
    }

    private static void blockingAndFire(GameTestHelper helper) {
        var player = player(helper);
        ItemStack aegis = new ItemStack(AnnoyingVillagersModItems.ENDER_AEGIS.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, aegis);
        player.startUsingItem(InteractionHand.MAIN_HAND);
        aegis.get(DataComponents.BLOCKS_ATTACKS).disable(helper.getLevel(), player, 5, aegis);
        helper.assertTrue(!player.getCooldowns().isOnCooldown(aegis), "Aegis resists blocking disable");
        helper.assertTrue(player.isUsingItem(), "Aegis keeps guarding");
        var entity = EntityType.COW.create(helper.getLevel(), EntitySpawnReason.LOAD);
        entity.setRemainingFireTicks(100);
        var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, helper.getLevel().registryAccess());
        entity.saveWithoutId(output);
        CompoundTag legacy = output.buildResult();
        legacy.putBoolean("AnnoyingVillagersEndFire", true);
        entity.load(TagValueInput.create(ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), legacy));
        entity.baseTick();
        helper.assertTrue(((EndFireEntity) entity).annoyingVillagers$isEndFireBurning(), "legacy End Fire migrates after load");
        entity.clearFire();
        helper.assertTrue(entity.getRemainingFireTicks() > 0, "End Fire resists ordinary extinguishing");
        helper.succeed();
    }
}
