package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.entity.goal.BowLineOfSightGoal;
import com.pla.annoyingvillagers.entity.goal.BurnNearbyItemGoal;
import com.pla.annoyingvillagers.entity.goal.FillWaterBucketGoal;
import com.pla.annoyingvillagers.entity.goal.LockedRandomStrollGoal;
import com.pla.annoyingvillagers.entity.goal.PlayIdleAnimationGoal;
import com.pla.annoyingvillagers.entity.goal.RecoverWeaponInCombatGoal;
import com.pla.annoyingvillagers.entity.goal.RetargetCloserThreatGoal;
import com.pla.annoyingvillagers.entity.goal.WaterEnderPearlEscapeGoal;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class AVNpc extends PathfinderMob implements RangedAttackMob, CombatVoiceLineEntity {
    private static final int PLACE_BLOCK_PARRY_COOLDOWN_TICKS = 60;
    private static final float VILLAGER_ARMOR_DROP_CHANCE = 0.12F;
    private static final float VILLAGER_WEAPON_DROP_CHANCE = 0.16F;
    private static final float VILLAGER_OFFHAND_EQUIPMENT_DROP_CHANCE = 0.10F;
    private static final float VILLAGER_EQUIPMENT_LOOTING_BONUS = 0.015F;
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
    private int swapToBowCooldown = 0;
    private ItemStack mainWeaponItem = ItemStack.EMPTY;
    private ItemStack offWeaponItem = ItemStack.EMPTY;
    private boolean healing = false;
    private boolean initialSpawn = false;
    private boolean useBow = true;
    private Entity blockDamage = null;
    private double placeBlockToParryChance;
    private int placeBlockParryCooldown = 0;
    private boolean swapBackToBow = false;;
    private int stunEscapeCooldown = 0;
    @Nullable
    private IdleAnimation idleAnimationChoice;
    @Nullable private AssetAccessor<? extends StaticAnimation> idleAnimationAsset;
    private boolean idleMessageBroadcast = false;
    private boolean playingIdle;
    private int playingIdleCooldown = 1200;
    private boolean isStrolling;
    private int efnGuardHitState = 0;
    private int efnGuardHitCooldown = 0;
    private boolean mainWeaponDisarmed = false;

    public boolean isMainWeaponDisarmed() {
        return mainWeaponDisarmed;
    }

    public void setMainWeaponDisarmed(boolean mainWeaponDisarmed) {
        this.mainWeaponDisarmed = mainWeaponDisarmed;
    }

    public int getEfnGuardHitState() {
        return efnGuardHitState;
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

    public void postPlayEfnGuardHit() {
        if (efnGuardHitState == 2) {
            efnGuardHitState = 0;
        } else {
            efnGuardHitState = efnGuardHitState + 1;
        }
        efnGuardHitCooldown = 100;
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

    @Nullable
    public AssetAccessor<? extends StaticAnimation> getIdleAnimation() {
        return idleAnimationAsset;
    }

    public void setIdleAnimation(@Nullable AssetAccessor<? extends StaticAnimation> anim) {
        this.idleAnimationAsset = anim;
    }

    public boolean isIdleMessageBroadcast() {
        return idleMessageBroadcast;
    }

    public void setIdleMessageBroadcast(boolean idleMessageBroadcast) {
        this.idleMessageBroadcast = idleMessageBroadcast;
    }

    public void clearIdleAnimationState() {
        this.idleAnimationChoice = null;
        this.idleAnimationAsset = null;
        this.idleMessageBroadcast = false;
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

    public void setSwapBackToBow(boolean swapBackToBow) {
        this.swapBackToBow = swapBackToBow;
    }

    public boolean isSwapBackToBow() {
        return swapBackToBow;
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
                && this.random.nextDouble() <= this.placeBlockToParryChance;
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
        return healing;
    }

    public void setHealing(boolean healing) {
        this.healing = healing;
    }

    public int getSwapToBowCooldown() {
        return swapToBowCooldown;
    }

    public void setSwapToBowCooldown() {
        this.swapToBowCooldown = random.nextInt(100, 300);
    }

    public ItemStack getBowItem() {
        return new ItemStack(Items.BOW);
    }

    @Nullable
    public LivingEntityPatch<?> getLivingEntityPatch() {
        return EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
    }

    public int getGapCooldown() {
        return gapCooldown;
    }

    public int getEnderPearlCooldown() {
        return enderPearlCooldown;
    }

    public void setGapCooldown() {
        this.gapCooldown = random.nextInt(100, 300);
    }

    public void resetGapCooldown() {this.gapCooldown = 0; }

    public void setEnderPearlCooldown() {
        this.enderPearlCooldown = random.nextInt(100, 300);
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
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Inventory", this.inventory.createTag());
        tag.putInt("GapCooldown", this.gapCooldown);
        tag.putInt("EnderPearlCooldown", this.enderPearlCooldown);
        tag.putInt("SwapToBowCooldown", this.swapToBowCooldown);
        tag.putBoolean("InitialSpawn", this.initialSpawn);
        tag.putBoolean("UseBow", this.useBow);
        tag.putDouble("BlockProjectileChance", this.placeBlockToParryChance);
        tag.putInt("BlockParryCooldown", this.placeBlockParryCooldown);
        if (!this.mainWeaponItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            this.mainWeaponItem.save(itemTag);
            tag.put("MainHandItem", itemTag);
        }
        if (!this.offWeaponItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            this.offWeaponItem.save(itemTag);
            tag.put("OffHandItem", itemTag);
        }
        tag.putInt("VoiceCooldown", this.voiceCooldown);
        tag.putBoolean("MainWeaponDisarmed", this.mainWeaponDisarmed);
    }

    @Override
    public void onEquipItem(@NotNull EquipmentSlot pSlot, @NotNull ItemStack pOldItem, @NotNull ItemStack pNewItem) {
        if (pSlot == EquipmentSlot.MAINHAND &&
                (pNewItem.getItem() instanceof SwordItem || pNewItem.getItem() instanceof AxeItem)) {
            this.mainWeaponItem = pNewItem.copy();
            this.mainWeaponDisarmed = false;
        }

        if (pSlot == EquipmentSlot.OFFHAND &&
                (pNewItem.getItem() instanceof SwordItem || pNewItem.getItem() instanceof AxeItem || pNewItem.getItem() instanceof ShieldItem)) {
            this.offWeaponItem = pNewItem.copy();
        }

        super.onEquipItem(pSlot, pOldItem, pNewItem);

        if (this.level().isClientSide) return;
        if (!this.isAlive() || this.isDeadOrDying() || this.getHealth() <= 0.0F) return;

        if (isPlayingIdle() && getLivingEntityPatch() != null && idleAnimationAsset != null) {
            getLivingEntityPatch().playAnimationSynchronized(idleAnimationAsset, 0.0F);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Inventory", Tag.TAG_LIST)) {
            this.inventory.fromTag(tag.getList("Inventory", Tag.TAG_COMPOUND));
        }
        this.gapCooldown = tag.getInt("GapCooldown");
        this.enderPearlCooldown = tag.getInt("EnderPearlCooldown");
        this.swapToBowCooldown = tag.getInt("SwapToBowCooldown");
        this.initialSpawn = tag.getBoolean("InitialSpawn");
        this.useBow = tag.getBoolean("UseBow");
        this.placeBlockToParryChance = tag.getDouble("BlockProjectileChance");
        this.placeBlockParryCooldown = tag.getInt("BlockParryCooldown");
        if (tag.contains("MainHandItem", Tag.TAG_COMPOUND)) {
            this.mainWeaponItem = ItemStack.of(tag.getCompound("MainHandItem"));
        } else {
            this.mainWeaponItem = ItemStack.EMPTY;
        }
        if (tag.contains("OffHandItem", Tag.TAG_COMPOUND)) {
            this.offWeaponItem = ItemStack.of(tag.getCompound("OffHandItem"));
        } else {
            this.offWeaponItem = ItemStack.EMPTY;
        }
        this.voiceCooldown = tag.getInt("VoiceCooldown");
        this.mainWeaponDisarmed = tag.getBoolean("MainWeaponDisarmed");
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
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
        if (this.random.nextFloat() > chance) {
            return;
        }

        ItemStack drop = this.prepareVillagerEquipmentDrop(equipped);
        if (!drop.isEmpty()) {
            this.spawnAtLocation(drop);
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
        return item instanceof SwordItem
                || item instanceof DiggerItem
                || item instanceof TridentItem;
    }

    private boolean isDroppableOffhandEquipment(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof SwordItem
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
            drop.setDamageValue(this.random.nextInt(minDamage, maxDamageBound));
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
        if (equipped.hasTag()) {
            converted.setTag(equipped.getTag().copy());
        }
        return converted;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new RetargetCloserThreatGoal(this));
        this.goalSelector.addGoal(-3, new WaterEnderPearlEscapeGoal(this));
        this.goalSelector.addGoal(-2, new RecoverWeaponInCombatGoal(this, 1.2D, 10.0D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new BowLineOfSightGoal(this, 1.15D, 7.0D, 14.0D));
        this.goalSelector.addGoal(5, new BurnNearbyItemGoal(this, 1.0D, 10.0D));
        this.goalSelector.addGoal(6, new PlayIdleAnimationGoal(this, new Random().nextInt(3000, 6000)));
        this.goalSelector.addGoal(7, new LockedRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FillWaterBucketGoal(this, 1.0D));
    }

    @Override
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

        ItemStack weaponStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, this::canFireProjectileWeapon));
        ItemStack itemstack = InventoryUtils.consumeArrowAmmo(this).orElse(ItemStack.EMPTY);
        if (itemstack.isEmpty()) {
            return;
        }

        AbstractArrow mobArrow = ProjectileUtil.getMobArrow(this, itemstack, pVelocity);
        if (this.getMainHandItem().getItem() instanceof BowItem) {
            mobArrow = ((BowItem)this.getMainHandItem().getItem()).customArrow(mobArrow);
        }

        double x = pTarget.getX() - this.getX();
        double y = pTarget.getY(0.3333333333333333) - mobArrow.getY();
        double z = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(x * x + z * z);
        mobArrow.setOwner(this);
        mobArrow.shoot(x, y + d3 * (double)0.2F, z, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
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
            } else if (ItemStack.isSameItemSameTags(slotStack, remaining) &&
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

    protected void implementFirstTick(ServerLevel serverLevel) {
        this.seedInventory();
    }

    protected boolean seedInventory() {
        if (!InventoryUtils.isEmpty(this.inventory)) {
            return false;
        }
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
        this.hasImpulse = true;
    }

    public void shortPillarJump() {
        if (!this.onGround()) return;
        Vec3 v = this.getDeltaMovement();
        double keepH = 0.02D;
        this.setDeltaMovement(v.x * keepH, 0.42D, v.z * keepH);
        this.hasImpulse = true;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (this.hasEnderPearlCounter()) {
            this.tryTriggerEnderPearlCounter(damageSource);
        }
        boolean result = super.hurt(damageSource, f);
        if (result) {
            this.sayHurtSound(this, damageSource);
        }
        return result;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result) {
            this.sayAttackSound(this, target);
        }
        return result;
    }

    protected boolean hasEnderPearlCounter() {
        return false;
    }

    protected void beforeEnderPearlCounter(@NotNull DamageSource damageSource) {
    }

    protected void afterEnderPearlCounter(@NotNull DamageSource damageSource) {
    }

    protected void doEnderPearlCounterPattern(@NotNull DamageSource damageSource) {
        this.throwEnderPearlNow(180.0F);
    }

    protected void playEnderPearlCounterAnimation() {
        if (this.getLivingEntityPatch() != null) {
            this.getLivingEntityPatch().playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_UP, 0.0F);
        }
    }

    protected boolean throwEnderPearlNow(float angle) {
        return CombatBehaviour.throwEnderPearl(this, angle);
    }

    protected void throwEnderPearlLater(int delayTicks, float angle) {
        AVNpc entity = this;
        new DelayedTask(delayTicks) {
            @Override
            public void run() {
                if (!entity.isAlive()) return;
                entity.playEnderPearlCounterAnimation();
                entity.throwEnderPearlNow(angle);
            }
        };
    }

    protected void throwEnderPearlLater(int delayTicks, double chance, float angle) {
        if (this.random.nextDouble() <= chance) {
            this.throwEnderPearlLater(delayTicks, angle);
        }
    }

    protected void doChrisStyleEnderPearlCounter() {
        this.throwEnderPearlNow(180.0F);
        this.throwEnderPearlLater(20, 0.2D, 90.0F);
    }

    protected void doSteveStyleEnderPearlCounter() {
        this.throwEnderPearlNow(new Random().nextFloat(90.0F, 180.0F));
        this.throwEnderPearlLater(20, 0.5D, 180.0F);
        this.throwEnderPearlLater(20, 0.3D, 90.0F);
    }

    protected void doVillagerKnightStyleEnderPearlCounter() {
        this.throwEnderPearlNow(new Random().nextFloat(90.0F, 180.0F));
        this.throwEnderPearlLater(40, 0.5D, 0.0F);
        this.throwEnderPearlLater(20, 0.2D, 180.0F);
        this.throwEnderPearlLater(20, 0.1D, 90.0F);
    }

    protected AssetAccessor<? extends StaticAnimation> getCurrentAnimationOrEmpty() {
        LivingEntityPatch<?> patch = this.getLivingEntityPatch();
        if (patch == null) {
            return Animations.EMPTY_ANIMATION;
        }

        AnimationPlayer player = patch.getAnimator().getPlayerFor(null);
        return player != null ? player.getRealAnimation() : Animations.EMPTY_ANIMATION;
    }

    protected void tryTriggerEnderPearlCounter(@NotNull DamageSource damageSource) {
        LivingEntityPatch<?> patch = this.getLivingEntityPatch();
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = this.getCurrentAnimationOrEmpty();

        if (damageSource.getEntity() == null) {
            return;
        }

        if (this.getEnderPearlCooldown() != 0) {
            return;
        }

        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, patch)) {
            return;
        }

        if (!(this.level() instanceof ServerLevel)) {
            return;
        }

        if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
            return;
        }

        if (!(patch instanceof MobPatch<?> mobPatch)) {
            return;
        }

        if (!CombatCommon.canPerformNormalAttackLogic(mobPatch)) {
            return;
        }

        if (!InventoryUtils.hasItem(this, Items.ENDER_PEARL)) {
            return;
        }

        this.beforeEnderPearlCounter(damageSource);
        this.playEnderPearlCounterAnimation();
        this.doEnderPearlCounterPattern(damageSource);
        this.afterEnderPearlCounter(damageSource);
        this.setEnderPearlCooldown();
    }

    protected ItemStack getEnderPearlCounterRestoreOffhandItem() {
        return this.getOffWeaponItem().copy();
    }

    protected void restoreOffhandLater(int delayTicks) {
        AVNpc entity = this;
        ItemStack restore = this.getEnderPearlCounterRestoreOffhandItem().copy();

        new DelayedTask(delayTicks) {
            @Override
            public void run() {
                if (!entity.isAlive()) return;
                entity.setItemInHand(InteractionHand.OFF_HAND, restore.copy());
            }
        };
    }

    protected void swapOffhandDuringEnderPearlCounter(ItemStack temporaryOffhand, int restoreDelayTicks) {
        this.setItemInHand(InteractionHand.OFF_HAND, temporaryOffhand.copy());
        this.restoreOffhandLater(restoreDelayTicks);
    }

    protected boolean afterBurstProtection(@NotNull ServerLevel serverLevel,
                                           @NotNull DamageSource source,
                                           float finalDamage) {
        return false;
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource pDamageSource, float pDamageAmount) {
        if (pDamageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            super.actuallyHurt(pDamageSource, pDamageAmount);
            return;
        }

        if (this.isInvulnerableTo(pDamageSource)) {
            return;
        }

        pDamageAmount = ForgeHooks.onLivingHurt(this, pDamageSource, pDamageAmount);
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

        finalDamage = ForgeHooks.onLivingDamage(this, pDamageSource, finalDamage);

        if (this.level() instanceof ServerLevel serverLevel
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
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        this.tickVoiceCooldown();

        if (this.tickCount == 1 && !this.initialSpawn) {
            implementFirstTick((ServerLevel) this.level());
            this.initialSpawn = true;
        }
        CombatCommon.tryPerformAvNpcWaterBucketSelfExtinguish(this);

        if (ModList.get().isLoaded("efkick") && this.stunEscapeCooldown == 0 && this.level() instanceof ServerLevel) {
            if (getLivingEntityPatch() != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(getLivingEntityPatch().getAnimator().getPlayerFor(null)).getRealAnimation();
                if (EpicfightUtil.isLongHitAnimationNotExecutedAnimation(dynamicAnimation, getLivingEntityPatch()) && this.isAlive()) {
                    if (this.getRandom().nextFloat() < CombatBehaviour.calculateGuardBreakWakeUpChance(this)) {
                        if (this instanceof AngrySteveEntity) {
                            this.stunEscapeCooldown = 60;
                        } else {
                            this.stunEscapeCooldown = 100;
                        }
                        AVNpc entity = this;
                        new DelayedTask(new Random().nextInt(5, 10)) {
                            @Override
                            public void run() {
                                if (getLivingEntityPatch() != null && EpicfightUtil.isLongHitAnimationNotExecutedAnimation(dynamicAnimation, getLivingEntityPatch()) && entity.isAlive()) {
                                    CombatBehaviour.postGuardBreakWakeUp(entity, getLivingEntityPatch(), serverLevel);
                                } else {
                                    entity.stunEscapeCooldown = 1;
                                }
                            }
                        };
                    }
                }
            }
        }

        if (gapCooldown > 0) gapCooldown--;
        if (enderPearlCooldown > 0) enderPearlCooldown--;
        if (swapToBowCooldown > 0) swapToBowCooldown--;
        if (placeBlockParryCooldown > 0) placeBlockParryCooldown--;
        if (stunEscapeCooldown > 0) stunEscapeCooldown--;
        if (playingIdleCooldown > 0) playingIdleCooldown--;
        if (efnGuardHitCooldown > 0) efnGuardHitCooldown--;

        if (efnGuardHitCooldown == 0 && efnGuardHitState != 0) {
            efnGuardHitState = 0;
        }


        if ((tickCount + getId()) % 20 == 0) {
            if (!isInventoryFull()) {
                pickupNearbyItems();
            }
        }
    }
}
