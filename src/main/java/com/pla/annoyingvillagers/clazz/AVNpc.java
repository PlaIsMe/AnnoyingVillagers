package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.util.EnchantmentUtil;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.entity.goal.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.rig.LockableRigAttackAnimation;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigBowAnimationSelector;
import com.pla.annoyingvillagers.rig.RigStunEscapeEntity;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class AVNpc extends PathfinderMob implements RangedAttackMob, CombatVoiceLineEntity, LockableRigAttackAnimation, RigStunEscapeEntity {
    private static final EntityDataAccessor<Boolean> RECOVERY_DIGGING = SynchedEntityData.defineId(AVNpc.class, EntityDataSerializers.BOOLEAN);

    /** 26.1 replaced getMyRidingOffset with an entity-type vehicle attachment. */
    @Override
    public Vec3 getVehicleAttachmentPoint(Entity vehicle) {
        return super.getVehicleAttachmentPoint(vehicle).add(0.0D, 0.35D, 0.0D);
    }
    private static final EntityDataAccessor<Boolean> HEALING = SynchedEntityData.defineId(AVNpc.class, EntityDataSerializers.BOOLEAN);
    private Object recoveryOwner;
    private int recoveryStartTick;

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(RECOVERY_DIGGING, false);
        builder.define(HEALING, false);
    }

    public boolean isRecoveryActionActive() { return this.recoveryOwner != null; }
    public boolean isRecoveryDigging() { return this.entityData.get(RECOVERY_DIGGING); }
    public void setRecoveryDigging(boolean value) { this.entityData.set(RECOVERY_DIGGING, value); }

    public boolean beginRecoveryAction(Object owner) {
        if (this.recoveryOwner != null || this.isLocked() || this.isHealing()) return false;
        this.recoveryOwner = owner;
        this.recoveryStartTick = this.tickCount;
        this.lock();
        return true;
    }

    public void endRecoveryAction(Object owner) {
        if (this.recoveryOwner != owner) return;
        this.setRecoveryDigging(false);
        this.recoveryOwner = null;
        this.unlock();
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        LivingEntity current = this.getTarget();
        // TargetGoal's sight timeout must not discard an enemy midway through opening its wall.
        if (target == null && this.isRecoveryActionActive() && this.tickCount - this.recoveryStartTick < 800
                && current != null && current.isAlive() && !current.isRemoved() && !this.isAlliedTo(current)
                && this.distanceToSqr(current) <= 28.0D * 28.0D
                && !(current instanceof net.minecraft.world.entity.player.Player player && (player.isCreative() || player.isSpectator()))) return;
        super.setTarget(target);
    }
    private static final int PLACE_BLOCK_PARRY_COOLDOWN_TICKS = 60;
    private static final float VILLAGER_ARMOR_DROP_CHANCE = 0.12F;
    private static final float VILLAGER_WEAPON_DROP_CHANCE = 0.16F;
    private static final float VILLAGER_OFFHAND_EQUIPMENT_DROP_CHANCE = 0.10F;
    private static final float VILLAGER_EQUIPMENT_LOOTING_BONUS = 0.015F;
    private static final int WATER_BUCKET_MIN_COOLDOWN_TICKS = 220;
    private static final int WATER_BUCKET_RANDOM_COOLDOWN_TICKS = 180;
    protected static final List<ItemLike> REGULAR_FOODS = List.of(
            Items.COOKED_BEEF,
            Items.BREAD,
            Items.COOKED_PORKCHOP,
            Items.COOKED_CHICKEN,
            Items.COOKED_MUTTON,
            Items.COOKED_COD,
            Items.COOKED_SALMON,
            Items.BAKED_POTATO,
            Items.CARROT,
            Items.APPLE
    );
    protected static final List<ItemLike> PLACEABLE_BLOCKS = List.of(
            Items.COBBLESTONE,
            Items.MOSSY_COBBLESTONE,
            Items.DIRT,
            Items.OAK_PLANKS,
            Items.DARK_OAK_PLANKS,
            Items.STONE,
            Items.COBBLED_DEEPSLATE,
            Items.DEEPSLATE,
            Items.GRAVEL,
            Items.SAND
    );

    protected final SimpleContainer inventory = new SimpleContainer(27);
    private int gapCooldown;
    private int enderPearlCooldown;
    private int waterBucketCooldown;
    private int swapToBowCooldown = 0;
    private ItemStack mainWeaponItem = ItemStack.EMPTY;
    private ItemStack offWeaponItem = ItemStack.EMPTY;
    private boolean initialSpawn = false;
    private boolean useBow = true;
    private Entity blockDamage = null;
    private double placeBlockToParryChance;
    private int rigAttackAnimationLockCount;
    private int placeBlockParryCooldown = 0;
    private int stunEscapeCooldown = 0;
    @Nullable
    private IdleAnimation idleAnimationChoice;
    private boolean idleMessageBroadcast = false;
    private boolean playingIdle;
    private int playingIdleCooldown = 1200;
    private boolean isStrolling;
    private boolean mainWeaponDisarmed = false;

    public boolean isMainWeaponDisarmed() {
        return mainWeaponDisarmed;
    }

    public void setMainWeaponDisarmed(boolean mainWeaponDisarmed) {
        this.mainWeaponDisarmed = mainWeaponDisarmed;
    }

    private int voiceCooldown = 0;

    @Override
    public int getVoiceCooldown() {
        return voiceCooldown;
    }

    @Override
    public void setVoiceCooldown(int cooldown) {
        this.voiceCooldown = cooldown;
    }

    public boolean isStrolling() {
        return isStrolling;
    }

    public void setStrolling(boolean strolling) {
        this.isStrolling = strolling;
    }

    public boolean isPlayingIdle() {
        return playingIdle;
    }

    public void setPlayingIdle(boolean playingIdle) {
        this.playingIdle = playingIdle;
    }

    public int getPlayingIdleCooldown() {
        return playingIdleCooldown;
    }

    public void setPlayingIdleCooldown(int playingIdleCooldown) {
        this.playingIdleCooldown = playingIdleCooldown;
    }

    @Nullable
    public IdleAnimation getIdleAnimationChoice() {
        return idleAnimationChoice;
    }

    public void setIdleAnimationChoice(@Nullable IdleAnimation choice) {
        this.idleAnimationChoice = choice;
    }

    public boolean isIdleMessageBroadcast() {
        return idleMessageBroadcast;
    }

    public void setIdleMessageBroadcast(boolean idleMessageBroadcast) {
        this.idleMessageBroadcast = idleMessageBroadcast;
    }

    public void clearIdleAnimationState() {
        this.idleAnimationChoice = null;
        this.idleMessageBroadcast = false;
    }

    public boolean isIdleAnimationGoalAvailable() {
        return true;
    }

    public boolean canStartIdleAnimationGoal(@Nullable IdleAnimation choice) {
        return !isLocked() && !isUsingItem() && !isSleeping()
                && !isRecoveryActionActive() && !isRecoveryDigging()
                && !RigStunController.isStunned(this)
                && !RigAnimationController.hasActiveAnimation(this);
    }

    public boolean canContinueIdleAnimationGoal(@Nullable IdleAnimation choice, int ticksLeft) {
        return choice != null && ticksLeft > 0 && !isLocked() && !isUsingItem()
                && !isSleeping() && !isRecoveryActionActive() && !isRecoveryDigging()
                && !RigStunController.isStunned(this)
                && RigAnimationController.getActiveAnimationId(this) == choice.rigAnimation();
    }

    public void onIdleAnimationGoalStart(IdleAnimation choice) {
        RigAnimationController.playHeldPose(this, choice.rigAnimation());
    }

    public void onIdleAnimationGoalTick(IdleAnimation choice) {
    }

    public void onIdleAnimationGoalStop(@Nullable IdleAnimation choice) {
        // The goal calls this from stop(); do not terminate a newer hit/recovery action.
        if (choice != null) RigAnimationController.stop(this, choice.rigAnimation());
    }

    public boolean canUseLockedRandomStrollGoal() {
        return true;
    }

    public boolean canContinueLockedRandomStrollGoal() {
        return true;
    }

    public void onLockedRandomStrollGoalStart() {
    }

    public void onLockedRandomStrollGoalStop() {
    }

    public int getStunEscapeCooldown() {
        return stunEscapeCooldown;
    }

    public void setStunEscapeCooldown(int stunEscapeCooldown) {
        this.stunEscapeCooldown = stunEscapeCooldown;
    }

    public Entity getBlockDamage() {
        return blockDamage;
    }

    public double getPlaceBlockToParryChance() {
        return placeBlockToParryChance;
    }

    public void setPlaceBlockToParryChance(double placeBlockToParryChance) {
        this.placeBlockToParryChance = placeBlockToParryChance;
    }

    public boolean rollsPlaceBlockToParryChance() {
        return this.placeBlockParryCooldown == 0
                && this.blockDamage == null
                && !this.isHealing()
                && this.getRandom().nextDouble() <= this.placeBlockToParryChance;
    }

    public boolean hasPlaceBlockParryCooldown() {
        return this.placeBlockParryCooldown > 0;
    }

    public void setPlaceBlockParryCooldown() {
        this.placeBlockParryCooldown = PLACE_BLOCK_PARRY_COOLDOWN_TICKS;
    }

    public void setBlockDamage(Entity blockDamage) {
        this.blockDamage = blockDamage;
    }

    public boolean isHealing() {
        return this.entityData.get(HEALING);
    }

    public void setHealing(boolean healing) {
        this.entityData.set(HEALING, healing);
    }

    public int getSwapToBowCooldown() {
        return swapToBowCooldown;
    }

    public void setSwapToBowCooldown() {
        this.swapToBowCooldown = random.nextInt(100, 300);
    }

    public ItemStack getBowItem() {
        ItemStack bow = new ItemStack(Items.BOW);

        if (this instanceof VillagerScoutCaptainEntity) {
            EnchantmentUtil.enchant(bow, Enchantments.POWER, 1);
            EnchantmentUtil.enchant(bow, Enchantments.PUNCH, 1);
        }
        if (this instanceof RedVillagerKnightEntity) {
            EnchantmentUtil.enchant(bow, Enchantments.FLAME, 2);
        }
        if (this instanceof BlueVillagerKnightEntity) {
            EnchantmentUtil.enchant(bow, Enchantments.POWER, 2);
        }
        if (this instanceof GreenVillagerKnightEntity) {
            EnchantmentUtil.enchant(bow, Enchantments.POWER, 1);
            EnchantmentUtil.enchant(bow, Enchantments.FLAME, 1);
        }
        if (this instanceof PurpleVillagerKnightEntity) {
            EnchantmentUtil.enchant(bow, Enchantments.PUNCH, 2);
        }
        if ((this instanceof SteveEntity steveEntity && steveEntity.getState() == 1)
                || this instanceof AngrySteveEntity) {
            EnchantmentUtil.enchant(bow, Enchantments.POWER, 2);
            EnchantmentUtil.enchant(bow, Enchantments.PUNCH, 2);
            if (this instanceof AngrySteveEntity) {
                EnchantmentUtil.enchant(bow, Enchantments.FLAME, 2);
            }
        }
        if (this instanceof AlexEntity alexEntity && alexEntity.getState() == 1) {
            EnchantmentUtil.enchant(bow, Enchantments.PUNCH, 2);
            EnchantmentUtil.enchant(bow, Enchantments.POWER, 2);
            EnchantmentUtil.enchant(bow, Enchantments.FLAME, 1);
        }
        if (this instanceof ChrisEntity chrisEntity && chrisEntity.getState() == 1) {
            EnchantmentUtil.enchant(bow, Enchantments.POWER, 2);
            EnchantmentUtil.enchant(bow, Enchantments.PUNCH, 2);
        }

        return bow;
    }

    public int getGapCooldown() {
        return gapCooldown;
    }

    public int getEnderPearlCooldown() {
        return enderPearlCooldown;
    }

    public int getWaterBucketCooldown() {
        return waterBucketCooldown;
    }

    public void setGapCooldown() {
        this.gapCooldown = random.nextInt(100, 300);
    }

    public void resetGapCooldown() {this.gapCooldown = 0; }

    public void setEnderPearlCooldown() {
        this.enderPearlCooldown = random.nextInt(100, 300);
    }

    public void setWaterBucketCooldown() {
        this.waterBucketCooldown = WATER_BUCKET_MIN_COOLDOWN_TICKS + random.nextInt(WATER_BUCKET_RANDOM_COOLDOWN_TICKS + 1);
    }

    public ItemStack getMainWeaponItem() {
        return mainWeaponItem;
    }

    public void setMainWeaponItem(ItemStack mainWeaponItem) {
        this.mainWeaponItem = mainWeaponItem.copy();

        if (!this.mainWeaponItem.isEmpty()) {
            this.mainWeaponDisarmed = false;
        }
    }

    public ItemStack getOffWeaponItem() { return offWeaponItem; }

    public void setOffWeaponItem(ItemStack offWeaponItem) {
        this.offWeaponItem = offWeaponItem;
    }

    @Override
    public void lock() {
        this.rigAttackAnimationLockCount++;
    }

    @Override
    public void unlock() {
        if (this.rigAttackAnimationLockCount > 0) this.rigAttackAnimationLockCount--;
    }

    @Override
    public boolean isLocked() {
        return this.rigAttackAnimationLockCount > 0;
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public boolean hasInventoryItem(Predicate<ItemStack> matcher) {
        return InventoryUtils.hasItem(this.inventory, matcher);
    }

    public boolean hasInventoryItem(ItemLike itemLike) {
        return InventoryUtils.hasItem(this.inventory, itemLike);
    }

    public Optional<ItemStack> consumeInventoryItem(Predicate<ItemStack> matcher, int count) {
        return InventoryUtils.consumeItem(this.inventory, matcher, count);
    }

    public Optional<ItemStack> consumeInventoryItem(ItemLike itemLike, int count) {
        return InventoryUtils.consumeItem(this.inventory, itemLike, count);
    }

    public boolean eatHealingFoodFromInventory() {
        return CombatBehaviour.eatInventoryHealingFood(this);
    }

    public void setUseBow(boolean useBow) {
        this.useBow = useBow;
    }

    public boolean isUseBow() {
        return useBow;
    }

    protected AVNpc(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setDropChance(EquipmentSlot.LEGS, 0.0F);
        this.setDropChance(EquipmentSlot.FEET, 0.0F);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
        CompoundTag tag = new CompoundTag();
        super.addAdditionalSaveData(output);
        net.minecraft.nbt.ListTag inventoryTag = new net.minecraft.nbt.ListTag();
        for (int slot = 0; slot < this.inventory.getContainerSize(); slot++) {
            ItemStack stack = this.inventory.getItem(slot);
            if (stack.isEmpty()) continue;
            CompoundTag itemTag = com.pla.annoyingvillagers.util.LegacyNbt.saveItem(stack, this.registryAccess());
            itemTag.putByte("Slot", (byte) slot);
            inventoryTag.add(itemTag);
        }
        tag.put("Inventory", inventoryTag);
        tag.putInt("GapCooldown", this.gapCooldown);
        tag.putInt("EnderPearlCooldown", this.enderPearlCooldown);
        tag.putInt("WaterBucketCooldown", this.waterBucketCooldown);
        tag.putInt("SwapToBowCooldown", this.swapToBowCooldown);
        tag.putBoolean("InitialSpawn", this.initialSpawn);
        tag.putBoolean("UseBow", this.useBow);
        tag.putDouble("BlockProjectileChance", this.placeBlockToParryChance);
        tag.putInt("BlockParryCooldown", this.placeBlockParryCooldown);
        if (!this.mainWeaponItem.isEmpty()) {
            tag.put("MainHandItem", com.pla.annoyingvillagers.util.LegacyNbt.saveItem(this.mainWeaponItem, this.registryAccess()));
        }
        if (!this.offWeaponItem.isEmpty()) {
            tag.put("OffHandItem", com.pla.annoyingvillagers.util.LegacyNbt.saveItem(this.offWeaponItem, this.registryAccess()));
        }
        tag.putInt("VoiceCooldown", this.voiceCooldown);
        tag.putBoolean("MainWeaponDisarmed", this.mainWeaponDisarmed);
    
        com.pla.annoyingvillagers.util.LegacyValueIO.write(output, tag);
    }

    @Override
    public void onEquipItem(@NotNull EquipmentSlot pSlot, @NotNull ItemStack pOldItem, @NotNull ItemStack pNewItem) {
        if (pSlot == EquipmentSlot.MAINHAND &&
                (com.pla.annoyingvillagers.item.LegacySwordItem.isSword(pNewItem) || pNewItem.getItem() instanceof AxeItem)) {
            this.mainWeaponItem = pNewItem.copy();
            this.mainWeaponDisarmed = false;
        }

        if (pSlot == EquipmentSlot.OFFHAND &&
                (com.pla.annoyingvillagers.item.LegacySwordItem.isSword(pNewItem) || pNewItem.getItem() instanceof AxeItem || pNewItem.getItem() instanceof ShieldItem)) {
            this.offWeaponItem = pNewItem.copy();
        }

        super.onEquipItem(pSlot, pOldItem, pNewItem);

        if (this.level().isClientSide()) return;
        if (!this.isAlive() || this.isDeadOrDying() || this.getHealth() <= 0.0F) return;
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        CompoundTag tag = com.pla.annoyingvillagers.util.LegacyValueIO.read(input);
        super.readAdditionalSaveData(input);
        if (tag.contains("Inventory")) {
            this.inventory.clearContent();
            for (net.minecraft.nbt.Tag entry : tag.getList("Inventory").orElseGet(net.minecraft.nbt.ListTag::new)) {
                if (!(entry instanceof CompoundTag itemTag)) continue;
                int slot = itemTag.getByte("Slot").orElse((byte) -1);
                if (slot >= 0 && slot < this.inventory.getContainerSize()) {
                    this.inventory.setItem(slot, com.pla.annoyingvillagers.util.LegacyNbt.loadItem(itemTag, this.registryAccess()));
                }
            }
        }
        this.gapCooldown = tag.getIntOr("GapCooldown", 0);
        this.enderPearlCooldown = tag.getIntOr("EnderPearlCooldown", 0);
        this.waterBucketCooldown = tag.getIntOr("WaterBucketCooldown", 0);
        this.swapToBowCooldown = tag.getIntOr("SwapToBowCooldown", 0);
        this.initialSpawn = tag.getBooleanOr("InitialSpawn", false);
        this.useBow = tag.getBooleanOr("UseBow", false);
        this.placeBlockToParryChance = tag.getDoubleOr("BlockProjectileChance", 0.0D);
        this.placeBlockParryCooldown = tag.getIntOr("BlockParryCooldown", 0);
        if (tag.contains("MainHandItem")) {
            this.mainWeaponItem = com.pla.annoyingvillagers.util.LegacyNbt.loadItem(tag.getCompound("MainHandItem").orElseGet(net.minecraft.nbt.CompoundTag::new), this.registryAccess());
        } else {
            this.mainWeaponItem = ItemStack.EMPTY;
        }
        if (tag.contains("OffHandItem")) {
            this.offWeaponItem = com.pla.annoyingvillagers.util.LegacyNbt.loadItem(tag.getCompound("OffHandItem").orElseGet(net.minecraft.nbt.CompoundTag::new), this.registryAccess());
        } else {
            this.offWeaponItem = ItemStack.EMPTY;
        }
        this.mainWeaponDisarmed = tag.getBooleanOr("MainWeaponDisarmed", false);

        // A bow equipped by AVNpcRangedBowAttackGoal is temporary.  The goal's
        // previous-hands fields are not persisted, so after a reload a temporary
        // bow would otherwise look like the NPC's permanent weapon and the goal
        // could never restore the cached melee weapon.
        if (this.getMainHandItem().getItem() instanceof BowItem
                && !this.mainWeaponItem.isEmpty()
                && !this.mainWeaponDisarmed) {
            ItemStack temporaryBow = this.getMainHandItem().copy();
            this.setItemSlot(EquipmentSlot.MAINHAND, this.mainWeaponItem.copy());
            this.setUseBow(false);
            if (!InventoryUtils.addItem(this.inventory, temporaryBow)) {
                com.pla.annoyingvillagers.util.LegacyEntityOps.spawnAtLocation(this, temporaryBow);
            }
        }
        this.voiceCooldown = tag.getIntOr("VoiceCooldown", 0);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        int looting = 0;

        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                com.pla.annoyingvillagers.util.LegacyEntityOps.spawnAtLocation(this, stack);
            }
        }

        this.dropVillagerCombatEquipment(looting);
    }

    private void dropVillagerCombatEquipment(int looting) {
        if (!this.shouldDropVillagerCombatEquipment()) {
            return;
        }

        this.tryDropVillagerEquipmentSlot(EquipmentSlot.MAINHAND, VILLAGER_WEAPON_DROP_CHANCE, looting);
        this.tryDropVillagerEquipmentSlot(EquipmentSlot.OFFHAND, VILLAGER_OFFHAND_EQUIPMENT_DROP_CHANCE, looting);

        this.tryDropVillagerEquipmentSlot(EquipmentSlot.HEAD, VILLAGER_ARMOR_DROP_CHANCE, looting);
        this.tryDropVillagerEquipmentSlot(EquipmentSlot.CHEST, VILLAGER_ARMOR_DROP_CHANCE, looting);
        this.tryDropVillagerEquipmentSlot(EquipmentSlot.LEGS, VILLAGER_ARMOR_DROP_CHANCE, looting);
        this.tryDropVillagerEquipmentSlot(EquipmentSlot.FEET, VILLAGER_ARMOR_DROP_CHANCE, looting);
    }

    private boolean shouldDropVillagerCombatEquipment() {
        return this.isVillagerKnight()
                || this instanceof VillagerScoutCaptainEntity;
    }

    protected boolean isVillagerKnight() {
        return this instanceof BlueVillagerKnightEntity
                || this instanceof GreenVillagerKnightEntity
                || this instanceof RedVillagerKnightEntity
                || this instanceof PurpleVillagerKnightEntity;
    }

    private void tryDropVillagerEquipmentSlot(EquipmentSlot slot, float baseChance, int looting) {
        ItemStack equipped = this.getDroppableEquipmentStack(slot);

        if (equipped.isEmpty()) {
            return;
        }

        float chance = Math.min(0.85F, baseChance + looting * VILLAGER_EQUIPMENT_LOOTING_BONUS);
        if (this.getRandom().nextFloat() > chance) {
            return;
        }

        ItemStack drop = this.prepareVillagerEquipmentDrop(equipped);
        if (!drop.isEmpty()) {
            com.pla.annoyingvillagers.util.LegacyEntityOps.spawnAtLocation(this, drop);
        }
    }

    private ItemStack getDroppableEquipmentStack(EquipmentSlot slot) {
        ItemStack equipped = this.getItemBySlot(slot);

        if (slot == EquipmentSlot.MAINHAND) {
            if (!this.mainWeaponItem.isEmpty()) {
                return this.mainWeaponItem.copy();
            }

            return this.isDroppableMainhandEquipment(equipped) ? equipped : ItemStack.EMPTY;
        }

        if (slot == EquipmentSlot.OFFHAND && !this.isDroppableOffhandEquipment(equipped)) {
            return ItemStack.EMPTY;
        }

        return equipped;
    }

    private boolean isDroppableMainhandEquipment(ItemStack stack) {
        Item item = stack.getItem();
        return com.pla.annoyingvillagers.item.LegacySwordItem.isSword(item)
                || com.pla.annoyingvillagers.item.LegacySwordItem.isTool(item)
                || item instanceof TridentItem;
    }

    private boolean isDroppableOffhandEquipment(ItemStack stack) {
        Item item = stack.getItem();
        return com.pla.annoyingvillagers.item.LegacySwordItem.isSword(item)
                || item instanceof AxeItem
                || item instanceof ShieldItem;
    }

    private ItemStack prepareVillagerEquipmentDrop(ItemStack equipped) {
        ItemStack drop = this.convertVillagerHelmetFixItem(equipped);
        drop.setCount(1);

        if (drop.isDamageableItem()) {
            int maxDamage = drop.getMaxDamage();
            int minDamage = Math.max(1, maxDamage / 3);
            int maxDamageBound = Math.max(minDamage + 1, maxDamage * 3 / 4);
            drop.setDamageValue(this.getRandom().nextInt(minDamage, maxDamageBound));
        }

        return drop;
    }

    protected ItemStack createDamagedDropStack(Item item) {
        ItemStack stack = new ItemStack(item);
        if (stack.isDamageableItem()) {
            stack.setDamageValue(CommonUtil.getRandomDamage(stack));
        }
        return stack;
    }

    private ItemStack convertVillagerHelmetFixItem(ItemStack equipped) {
        Item replacement = null;

        if (equipped.is(AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET_FIX.get())) {
            replacement = AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET.get();
        } else if (equipped.is(AnnoyingVillagersModItems.BLUE_VILLAGER_KNIGHT_HELMET_FIX.get())) {
            replacement = AnnoyingVillagersModItems.BLUE_VILLAGER_KNIGHT_HELMET.get();
        } else if (equipped.is(AnnoyingVillagersModItems.RED_VILLAGER_KNIGHT_HELMET_FIX.get())) {
            replacement = AnnoyingVillagersModItems.RED_VILLAGER_KNIGHT_HELMET.get();
        } else if (equipped.is(AnnoyingVillagersModItems.GREEN_VILLAGER_KNIGHT_HELMET_FIX.get())) {
            replacement = AnnoyingVillagersModItems.GREEN_VILLAGER_KNIGHT_HELMET.get();
        } else if (equipped.is(AnnoyingVillagersModItems.PURPLE_VILLAGER_KNIGHT_HELMET_FIX.get())) {
            replacement = AnnoyingVillagersModItems.PURPLE_VILLAGER_KNIGHT_HELMET.get();
        }

        if (replacement == null) {
            return equipped.copy();
        }

        ItemStack converted = new ItemStack(replacement);
        if (LegacyItemData.has(equipped)) {
            LegacyItemData.set(converted, LegacyItemData.get(equipped).copy());
        }
        return converted;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new RetargetCloserThreatGoal(this));
        this.goalSelector.addGoal(-6, new WaterFallGoal(this));
        this.goalSelector.addGoal(-5, new ProjectileBlockGoal(this));
        this.goalSelector.addGoal(-4, new EscapeWallGoal(this));
        this.goalSelector.addGoal(-4, new EscapeHoleWithBlockGoal(this));
        this.goalSelector.addGoal(-4, new BreakTargetObstructionGoal(this));
        this.goalSelector.addGoal(-4, new UseLiquidBucketGoal(this));
        this.goalSelector.addGoal(-3, new WaterEnderPearlEscapeGoal(this));
        this.goalSelector.addGoal(-3, new ThrowEnderPearlToTargetGoal(this));
        this.goalSelector.addGoal(-3, new RandomEnderPearlEscapeGoal(this));
        this.goalSelector.addGoal(-2, new RecoverWeaponInCombatGoal(this, 1.2D, 10.0D));
        this.goalSelector.addGoal(-1, new EatHealingFoodGoal(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        if (this instanceof RollItemUser) {
            this.goalSelector.addGoal(1, new RollItemGoal(this));
        }
        if (this instanceof FishingRodUser) {
            this.goalSelector.addGoal(1, new CombatFishingRodGoal(this));
        }
        this.goalSelector.addGoal(1, new AVNpcRangedBowAttackGoal(this, 1.15D, 20, 14.0F));
        this.goalSelector.addGoal(2, new RandomCombatJumpGoal(this));
        this.goalSelector.addGoal(4, new BowLineOfSightGoal(this, 1.15D, 7.0D, 14.0D));
        this.goalSelector.addGoal(5, new BurnNearbyItemGoal(this, 1.0D, 10.0D));
        this.goalSelector.addGoal(6, new PlayIdleAnimationGoal(this, new Random().nextInt(120, 240)));
        this.goalSelector.addGoal(7, new LockedRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FillWaterBucketGoal(this, 1.0D));
    }

    public boolean canFireProjectileWeapon(@NotNull ProjectileWeaponItem item) {
        return item instanceof BowItem;
    }

    public boolean canFireProjectileWeapon(@NotNull Item item) {
        if (item instanceof ProjectileWeaponItem weaponItem) {
            return this.canFireProjectileWeapon(weaponItem);
        }
        return false;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity pTarget, float pVelocity) {
        if (!BowFunction.hasClearShot(this, pTarget)) {
            return;
        }

        boolean shouldUseEnchantedArrow = this.getTarget() instanceof HerobrineMob;
        ItemStack itemstack = InventoryUtils.consumeArrowAmmo(this, shouldUseEnchantedArrow).orElse(ItemStack.EMPTY);
        if (itemstack.isEmpty()) {
            return;
        }

        ItemStack weapon = this.getMainHandItem();
        AbstractArrow mobArrow = ProjectileUtil.getMobArrow(this, itemstack, pVelocity, weapon);
        if (this.getMainHandItem().getItem() instanceof BowItem) {
            mobArrow = ((BowItem)this.getMainHandItem().getItem()).customArrow(mobArrow, itemstack, weapon);
        }

        double x = pTarget.getX() - this.getX();
        double y = pTarget.getY(0.3333333333333333) - mobArrow.getY();
        double z = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(x * x + z * z);
        mobArrow.setOwner(this);
        mobArrow.shoot(x, y + d3 * (double)0.2F, z, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        RigAnimationController.play(this, RigAnimationSpecs.get(RigBowAnimationSelector.shotForTarget(this, pTarget)), pTarget);
        this.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(mobArrow);
    }

    private boolean isInventoryFull() {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack s = inventory.getItem(i);
            if (s.isEmpty() || s.getCount() < s.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    private void pickupNearbyItems() {
        if (!isAlive() || isRemoved() || this.isDeadOrDying()) return;

        var box = getBoundingBox().inflate(1.5D);
        List<ItemEntity> items = level().getEntitiesOfClass(
                ItemEntity.class,
                box,
                e -> !e.isRemoved() && !e.hasPickUpDelay()
        );

        for (ItemEntity itemEntity : items) {
            tryPickup(itemEntity);
        }
    }

    private void tryPickup(ItemEntity itemEntity) {
        ItemStack remaining = itemEntity.getItem().copy();

        for (int i = 0; i < inventory.getContainerSize() && !remaining.isEmpty(); i++) {
            if (remaining.isEmpty()) break;
            ItemStack slotStack = this.inventory.getItem(i);

            if (slotStack.isEmpty()) {
                this.inventory.setItem(i, remaining);
                remaining = ItemStack.EMPTY;
                break;
            } else if (ItemStack.isSameItemSameComponents(slotStack, remaining) &&
                    slotStack.getCount() < slotStack.getMaxStackSize()) {
                int transferable = Math.min(
                        remaining.getCount(),
                        slotStack.getMaxStackSize() - slotStack.getCount()
                );
                slotStack.grow(transferable);
                remaining.shrink(transferable);
            }
        }

        if (remaining.isEmpty()) {
            itemEntity.setDeltaMovement(
                    (this.getX() - itemEntity.getX()) * 0.25,
                    (this.getY() + 1.0 - itemEntity.getY()) * 0.25,
                    (this.getZ() - itemEntity.getZ()) * 0.25
            );
            itemEntity.setPickUpDelay(0);
            itemEntity.discard();
            this.level().playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.HOSTILE, 0.2F, 1.0F);
        } else {
            itemEntity.setItem(remaining);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnData) {
        SpawnGroupData result = super.finalizeSpawn(level,difficulty,spawnType,spawnData);
        this.setLeftHanded(false);
        return result;
    }

    protected void implementFirstTick(ServerLevel serverLevel) {
        this.seedInventory();
    }

    protected boolean seedInventory() {
        if (!InventoryUtils.isEmpty(this.inventory)) {
            return false;
        }
        InventoryUtils.addItem(this.inventory, this.getBowItem());
        boolean diamondTools = this instanceof SteveEntity || this instanceof AngrySteveEntity
                || this instanceof AlexEntity || this instanceof ChrisEntity;
        InventoryUtils.addItem(this.inventory, new ItemStack(diamondTools ? Items.DIAMOND_PICKAXE : Items.IRON_PICKAXE));
        InventoryUtils.addItem(this.inventory, new ItemStack(diamondTools ? Items.DIAMOND_AXE : Items.IRON_AXE));
        return true;
    }

    public void jump() {
        this.jumpFromGround();
        Vec3 motion = this.getDeltaMovement();
        Vec3 forward = this.getForward();
        double strength = new Random().nextDouble(0.2, 0.4);
        this.setDeltaMovement(
                motion.x + forward.x * strength,
                motion.y,
                motion.z + forward.z * strength
        );
        this.hurtMarked = true;
    }

    public void shortPillarJump() {
        if (!this.onGround()) return;
        Vec3 v = this.getDeltaMovement();
        double keepH = 0.02D;
        this.setDeltaMovement(v.x * keepH, 0.42D, v.z * keepH);
        this.hurtMarked = true;
    }

    @Override
    public boolean hurtServer(net.minecraft.server.level.ServerLevel serverLevel, DamageSource damageSource, float f) {
        boolean result = super.hurtServer(serverLevel, damageSource, f);
        if (result) {
            this.sayHurtSound(this, damageSource);
        }
        return result;
    }

    @Override
    public boolean doHurtTarget(net.minecraft.server.level.ServerLevel serverLevel, Entity target) {
        boolean result = super.doHurtTarget(serverLevel, target);
        if (result) {
            this.sayAttackSound(this, target);
        }
        return result;
    }

    protected boolean afterBurstProtection(@NotNull ServerLevel serverLevel,
                                           @NotNull DamageSource source,
                                           float finalDamage) {
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
    public void tick() {
        super.tick();
        if (!(this.level() instanceof ServerLevel)) return;
        this.tickVoiceCooldown();

        if (this.tickCount == 1 && !this.initialSpawn) {
            implementFirstTick((ServerLevel) this.level());
            this.initialSpawn = true;
        }

        if (gapCooldown > 0) gapCooldown--;
        if (enderPearlCooldown > 0) enderPearlCooldown--;
        if (waterBucketCooldown > 0) waterBucketCooldown--;
        if (swapToBowCooldown > 0) swapToBowCooldown--;
        if (placeBlockParryCooldown > 0) placeBlockParryCooldown--;
        if (stunEscapeCooldown > 0) stunEscapeCooldown--;
        if (this instanceof FishingRodUser fishingRodUser) fishingRodUser.tickCombatFishingRodCooldown();
        if (playingIdleCooldown > 0) playingIdleCooldown--;

        if ((tickCount + getId()) % 20 == 0) {
            if (!isInventoryFull()) {
                pickupNearbyItems();
            }
        }
    }
}
