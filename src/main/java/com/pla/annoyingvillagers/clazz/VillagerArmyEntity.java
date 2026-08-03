package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.entity.goal.VillagerArmyHurtByTargetGoal;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class VillagerArmyEntity extends AVNpc implements FishingRodUser {
    private static final float OFFHAND_SHIELD_SPAWN_CHANCE = 0.20F;
    private final FishingRodUser.State combatFishingRodState = new FishingRodUser.State();

    protected VillagerArmyEntity(EntityType<? extends VillagerArmyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public FishingRodUser.State getCombatFishingRodState() {
        return this.combatFishingRodState;
    }

    @Override
    public Item getCombatFishingRodItem() {
        return AnnoyingVillagersModItems.ADVANCED_FISHING_ROD.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new VillagerArmyHurtByTargetGoal(this));
    }

    @Override
    protected boolean seedInventory() {
        SimpleContainer inventory = this.getInventory();
        if (super.seedInventory()) {
            Random random = new Random();
            InventoryUtils.addItem(inventory, new ItemStack(Items.GOLDEN_APPLE, random.nextInt(8, 12)));

            List<ItemLike> foods = new ArrayList<>(REGULAR_FOODS);
            for (int i = 0; i < 2 && !foods.isEmpty(); i++) {
                ItemLike food = foods.remove(random.nextInt(foods.size()));
                InventoryUtils.addItem(inventory, new ItemStack(food, random.nextInt(12, 32)));
            }

            InventoryUtils.addItem(inventory, new ItemStack(Items.ARROW, random.nextInt(16, 32)));
            InventoryUtils.addItem(inventory, new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get(), random.nextInt(12, 24)));
            InventoryUtils.addItem(inventory, new ItemStack(Items.ENDER_PEARL, random.nextInt(8, 12)));
            InventoryUtils.addItem(inventory, new ItemStack(Items.WATER_BUCKET));
            if (this.isVillagerKnight() && random.nextFloat() < 0.45F) {
                InventoryUtils.addItem(inventory, new ItemStack(Items.LAVA_BUCKET));
            }

            List<ItemLike> blocks = new ArrayList<>(PLACEABLE_BLOCKS);
            int blockStacks = random.nextInt(1, 2);
            for (int i = 0; i < blockStacks && !blocks.isEmpty(); i++) {
                ItemLike block = blocks.remove(random.nextInt(blocks.size()));
                InventoryUtils.addItem(inventory, new ItemStack(block, random.nextInt(32, 96)));
            }

            InventoryUtils.addItem(inventory, new ItemStack(Items.IRON_INGOT, random.nextInt(2, 11)));
            InventoryUtils.addItem(inventory, new ItemStack(Items.GOLD_INGOT, random.nextInt(1, 7)));
            InventoryUtils.addItem(inventory, new ItemStack(Items.EMERALD, random.nextInt(1, 6)));

            return true;
        } else {
            return false;
        }
    }

    protected void maybeEquipSpawnShield() {
        ItemStack offhand = this.getOffhandItem();
        if (offhand.getItem() instanceof ShieldItem || (!offhand.isEmpty() && !offhand.is(Items.ENDER_PEARL))) {
            return;
        }

        if (this.getRandom().nextFloat() < OFFHAND_SHIELD_SPAWN_CHANCE) {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }
}
