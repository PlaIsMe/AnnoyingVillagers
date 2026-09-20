package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.util.EnchantmentUtil;
import javax.annotation.Nullable;

import com.pla.annoyingvillagers.clazz.BurstProtectEntity;
import com.pla.annoyingvillagers.clazz.PersistentPlayerNpc;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.spawnhandler.ChrisData;
import com.pla.annoyingvillagers.util.*;
import com.pla.annoyingvillagers.clazz.AVNpc;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.CommonHooks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;


public class ChrisEntity extends AVNpc implements PersistentPlayerNpc, BurstProtectEntity {
    private int state = 0;
    @Override
    public float getBurstProtectCapRatio() {
        return 0.15F;
    }

    @Override
    public String persistentPlayerIdentity() { return "Chris"; }

        public ChrisEntity(EntityType<ChrisEntity> entitytype, Level level) {
        super(entitytype, level);
        this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1.0F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setPlaceBlockToParryChance(0.6);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
        CompoundTag tag = new CompoundTag();
        super.addAdditionalSaveData(output);
        tag.putInt("State", this.state);
    
        com.pla.annoyingvillagers.util.LegacyValueIO.write(output, tag);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        CompoundTag tag = com.pla.annoyingvillagers.util.LegacyValueIO.read(input);
        super.readAdditionalSaveData(input);
        state = tag.getIntOr("State", 0);
    }

        protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForNeutralNpc(this);
    }

        public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.fromNamespaceAndPath("minecraft","entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.fromNamespaceAndPath("minecraft","entity.generic.death"));
    }

    @Override
    protected boolean afterBurstProtection(@NotNull ServerLevel serverLevel,
                                           @NotNull DamageSource source,
                                           float finalDamage) {
        if (this.state == 0
                && (this.getHealth() - finalDamage) <= 1.0F
                && !this.getOffhandItem().is(Items.TOTEM_OF_UNDYING)) {
            this.setHealth(1.0F);
            return true;
        }
        return false;
    }

    @Override
    protected void actuallyHurt(net.minecraft.server.level.ServerLevel serverLevel, DamageSource pDamageSource, float pDamageAmount) {
        if (pDamageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            super.actuallyHurt(serverLevel, pDamageSource, pDamageAmount);
            return;
        }

        if (this.isInvulnerableTo(serverLevel, pDamageSource)) {
            return;
        }
        if (pDamageAmount <= 0.0F) {
            return;
        }

        pDamageAmount = this.getDamageAfterArmorAbsorb(pDamageSource, pDamageAmount);
        pDamageAmount = this.getDamageAfterMagicAbsorb(pDamageSource, pDamageAmount);

        float finalDamage = Math.max(pDamageAmount - this.getAbsorptionAmount(), 0.0F);
        float absorbed = pDamageAmount - finalDamage;
        if (absorbed > 0.0F) {
            this.setAbsorptionAmount(this.getAbsorptionAmount() - absorbed);
            if (this.getAbsorptionAmount() < 0.0F) {
                this.setAbsorptionAmount(0.0F);
            }
        }

        this.damageContainers.peek().setNewDamage(finalDamage);

        finalDamage = CommonHooks.onLivingDamagePre(this, this.damageContainers.peek());
        finalDamage = this.applyBurstProtection(this, pDamageSource, finalDamage);

        if (true
                && this.afterBurstProtection(serverLevel, pDamageSource, finalDamage)) {
            return;
        }

        if (finalDamage <= 0.0F) {
            return;
        }

        this.getCombatTracker().recordDamage(pDamageSource, finalDamage);
        this.setHealth(this.getHealth() - finalDamage);
        this.gameEvent(GameEvent.ENTITY_DAMAGE);
    }

    @Override
    protected void dropCustomDeathLoot(net.minecraft.server.level.ServerLevel level, @NotNull DamageSource source, boolean recentlyHit) {
        int looting = 0;
        super.dropCustomDeathLoot(level, source, recentlyHit);
        if (this.level() instanceof ServerLevel serverLevel) {
            final double x = this.getX();
            final double y = this.getY() + 1.0D;
            final double z = this.getZ();

            Consumer<ItemStack> dropStack = (stack) -> {
                if (InventoryUtils.isInventoryBackedSupplyDrop(stack)) {
                    return;
                }
                ItemEntity drop = new ItemEntity(serverLevel, x, y, z, stack);
                drop.setPickUpDelay(10);
                serverLevel.addFreshEntity(drop);
            };

            List<ItemStack> damagedStacks = new ArrayList<>();

            ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
            EnchantmentUtil.enchant(sword, Enchantments.KNOCKBACK, 5);
            EnchantmentUtil.enchant(sword, Enchantments.SHARPNESS, 5);
            EnchantmentUtil.enchant(sword, Enchantments.UNBREAKING, 5);
            damagedStacks.add(sword);

            ItemStack diamondHelmet = new ItemStack(Items.DIAMOND_HELMET);
            EnchantmentUtil.enchant(diamondHelmet, Enchantments.PROTECTION, 5);
            EnchantmentUtil.enchant(diamondHelmet, Enchantments.UNBREAKING, 5);
            damagedStacks.add(diamondHelmet);

            ItemStack diamondChestplate = new ItemStack(Items.DIAMOND_CHESTPLATE);
            EnchantmentUtil.enchant(diamondChestplate, Enchantments.PROTECTION, 5);
            EnchantmentUtil.enchant(diamondChestplate, Enchantments.UNBREAKING, 5);
            damagedStacks.add(diamondChestplate);

            ItemStack diamondBoots = new ItemStack(Items.DIAMOND_BOOTS);
            EnchantmentUtil.enchant(diamondBoots, Enchantments.PROTECTION, 5);
            EnchantmentUtil.enchant(diamondBoots, Enchantments.FROST_WALKER, 2);
            EnchantmentUtil.enchant(diamondBoots, Enchantments.UNBREAKING, 5);
            damagedStacks.add(diamondBoots);

            ItemStack bow = this.getBowItem();
            EnchantmentUtil.enchant(bow, Enchantments.POWER, 2);
            EnchantmentUtil.enchant(bow, Enchantments.PUNCH, 2);
            damagedStacks.add(bow);

            ItemStack ironPickaxe = new ItemStack(Items.IRON_PICKAXE);
            EnchantmentUtil.enchant(ironPickaxe, Enchantments.UNBREAKING, 3);
            damagedStacks.add(ironPickaxe);

            ItemStack ironAxe = new ItemStack(Items.IRON_AXE);
            EnchantmentUtil.enchant(ironAxe, Enchantments.UNBREAKING, 3);
            damagedStacks.add(ironAxe);

            for (ItemStack stack : damagedStacks) {
                stack.setDamageValue(CommonUtil.getRandomDamage(stack));
                dropStack.accept(stack);
            }
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull EntitySpawnReason mobSpawnType, @Nullable SpawnGroupData spawngroupdata) {
        if (mobSpawnType == EntitySpawnReason.NATURAL || mobSpawnType == EntitySpawnReason.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverLevelAccessor.getLevel();
            ChrisData chrisData = ChrisData.get(serverLevel);

            if (!chrisData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            }
        }

        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata);

        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        EnchantmentUtil.enchant(sword, Enchantments.KNOCKBACK, 5);
        EnchantmentUtil.enchant(sword, Enchantments.SHARPNESS, 5);
        EnchantmentUtil.enchant(sword, Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.MAINHAND, sword);

        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));

        ItemStack diamondHelmet = new ItemStack(Items.DIAMOND_HELMET);
        EnchantmentUtil.enchant(diamondHelmet, Enchantments.PROTECTION, 5);
        EnchantmentUtil.enchant(diamondHelmet, Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.HEAD, diamondHelmet);

        ItemStack diamondChestplate = new ItemStack(Items.DIAMOND_CHESTPLATE);
        EnchantmentUtil.enchant(diamondChestplate, Enchantments.PROTECTION, 5);
        EnchantmentUtil.enchant(diamondChestplate, Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.CHEST, diamondChestplate);

        ItemStack diamondBoots = new ItemStack(Items.DIAMOND_BOOTS);
        EnchantmentUtil.enchant(diamondBoots, Enchantments.PROTECTION, 5);
        EnchantmentUtil.enchant(diamondBoots, Enchantments.FROST_WALKER, 2);
        EnchantmentUtil.enchant(diamondBoots, Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.FEET, diamondBoots);

        TeamUtil.addOrJoinTeam(this, "steve");
        return returnSpawnGroupData;
    }

    public void awardKillScore(@NotNull Entity entity, int i, @NotNull DamageSource damagesource) {
        super.awardKillScore(entity, damagesource);
    }

    @Override
    protected boolean seedInventory() {
        if (super.seedInventory()) {
            Random random = new Random();
            InventoryUtils.addItem(this.inventory, new ItemStack(Items.GOLDEN_APPLE, random.nextInt(16, 32)));
            InventoryUtils.addItem(this.inventory, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, random.nextInt(16, 32)));

            List<ItemLike> foods = new ArrayList<>(REGULAR_FOODS);
            for (int i = 0; i < 2 && !foods.isEmpty(); i++) {
                ItemLike food = foods.remove(random.nextInt(foods.size()));
                InventoryUtils.addItem(this.inventory, new ItemStack(food, random.nextInt(16, 32)));
            }

            InventoryUtils.addItem(this.inventory, new ItemStack(Items.ARROW, random.nextInt(32, 64)));
            InventoryUtils.addItem(this.inventory, new ItemStack(Items.ENDER_PEARL, random.nextInt(16, 32)));
            InventoryUtils.addItem(this.inventory, new ItemStack(Items.WATER_BUCKET));
            if (this.isVillagerKnight() && random.nextFloat() < 0.45F) {
                InventoryUtils.addItem(this.inventory, new ItemStack(Items.LAVA_BUCKET));
            }

            List<ItemLike> blocks = new ArrayList<>(PLACEABLE_BLOCKS);
            int blockStacks = random.nextInt(1, 2);
            for (int i = 0; i < blockStacks && !blocks.isEmpty(); i++) {
                ItemLike block = blocks.remove(random.nextInt(blocks.size()));
                InventoryUtils.addItem(this.inventory, new ItemStack(block, random.nextInt(64, 128)));
            }

            InventoryUtils.addItem(this.inventory, new ItemStack(Items.COAL, random.nextInt(0, 8)));
            InventoryUtils.addItem(this.inventory, new ItemStack(Items.IRON_INGOT, random.nextInt(0, 12)));
            InventoryUtils.addItem(this.inventory, new ItemStack(Items.GOLD_INGOT, random.nextInt(0, 12)));
            InventoryUtils.addItem(this.inventory, new ItemStack(Items.DIAMOND, random.nextInt(0, 8)));
            return true;
        } else {
            return false;
        }
    }

    public static boolean canSpawn(EntityType<ChrisEntity> entityType, ServerLevelAccessor level, EntitySpawnReason spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (ChrisData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        this.playSound(
                AnnoyingVillagersModSounds.CHRIS_SAY_ON_SPAWN.get(),
                1.0F, 1.0F
        );
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            if (this.state == 0
                    && this.getHealth() <= 20
                    && !this.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING)) {
                this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            }
        }
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide() && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            ChrisData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public static Builder addEpicFightAttributes(Builder builder) {
//      ADD THIS CODE IN AV_EFM
//        return builder.add(EpicFightAttributes.IMPACT.get(), 2.0D)
//                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 5.0D)
//                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
//                .add(EpicFightAttributes.MAX_STRIKES.get(), 50.0D)
//                .add(EpicFightAttributes.MAX_STAMINA.get(), 30.0D)
//                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);

        return builder;
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return addEpicFightAttributes(builder);
    }
}

