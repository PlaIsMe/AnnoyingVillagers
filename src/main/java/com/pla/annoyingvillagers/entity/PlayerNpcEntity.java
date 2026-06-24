package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.clazz.IdleAnimation;
import com.pla.annoyingvillagers.clazz.PlayerNpcTarget;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.entity.goal.BowLineOfSightGoal;
import com.pla.annoyingvillagers.entity.goal.BurnNearbyItemGoal;
import com.pla.annoyingvillagers.entity.goal.LockedRandomStrollGoal;
import com.pla.annoyingvillagers.entity.goal.PlayIdleAnimationGoal;
import com.pla.annoyingvillagers.entity.goal.RecoverWeaponInCombatGoal;
import com.pla.annoyingvillagers.entity.goal.RetargetCloserThreatGoal;
import com.pla.annoyingvillagers.entity.goal.WaterEnderPearlEscapeGoal;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PlayerNpcEntity extends PlayerMobEntity implements RangedAttackMob {
    private final SimpleContainer inventory = new SimpleContainer(27);
    private int gapCooldown = 0;
    private int enderPearlCooldown = 0;
    private int swapToBowCooldown = 0;
    private PlayerNpcTarget target;
    private ItemStack mainWeaponItem = ItemStack.EMPTY;
    private ItemStack offWeaponItem = ItemStack.EMPTY;
    private boolean healing = false;
    private boolean useBow = true;
    private Entity blockDamage = null;
    private double placeBlockToParryChance;
    private int stunEscapeCooldown = 0;
    @Nullable private IdleAnimation idleAnimationChoice;
    @Nullable private AssetAccessor<? extends StaticAnimation> idleAnimationAsset;
    private boolean idleMessageBroadcast = false;
    private boolean playingIdle;
    private int playingIdleCooldown = new Random().nextInt(600, 1200);
    private boolean isStrolling;

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

    public int getPlayingIdleCooldown() {
        return playingIdleCooldown;
    }

    public void setPlayingIdleCooldown(int playingIdleCooldown) {
        this.playingIdleCooldown = playingIdleCooldown;
    }

    public void clearIdleAnimationState() {
        this.idleAnimationChoice = null;
        this.idleAnimationAsset = null;
        this.idleMessageBroadcast = false;
    }

    @Nullable
    public LivingEntityPatch<?> getLivingEntityPatch() {
        return EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
    }

    public int getStunEscapeCooldown() {
        return stunEscapeCooldown;
    }

    public void setStunEscapeCooldown(int stunEscapeCooldown) {
        this.stunEscapeCooldown = stunEscapeCooldown;
    }

    public double getPlaceBlockToParryChance() {
        return placeBlockToParryChance;
    }

    public Entity getBlockDamage() {
        return blockDamage;
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

    public int getGapCooldown() {
        return gapCooldown;
    }

    public int getEnderPearlCooldown() {
        return enderPearlCooldown;
    }

    public int getSwapToBowCooldown() {
        return swapToBowCooldown;
    }

    public void setGapCooldown() {
        this.gapCooldown = random.nextInt(100, 300);
    }

    public void resetGapCooldown() {this.gapCooldown = 0; }

    public void setEnderPearlCooldown() {
        this.enderPearlCooldown = random.nextInt(100, 300);
    }

    public void setSwapToBowCooldown() {
        this.swapToBowCooldown = random.nextInt(100, 300);
    }

    private boolean mainWeaponDisarmed = false;

    public boolean isMainWeaponDisarmed() {
        return mainWeaponDisarmed;
    }

    public void setMainWeaponDisarmed(boolean mainWeaponDisarmed) {
        this.mainWeaponDisarmed = mainWeaponDisarmed;
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public PlayerNpcEntity(PlayMessages.SpawnEntity spawnentity, Level level) {
        this(AnnoyingVillagersModEntities.PLAYER_NPC.get(), level);
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

    public void setUseBow(boolean useBow) {
        this.useBow = useBow;
    }

    public boolean isUseBow() {
        return useBow;
    }

    public PlayerNpcEntity(EntityType<? extends PlayerNpcEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.6F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.placeBlockToParryChance = new Random().nextDouble(0.20, 0.40);
        this.setCanPickUpLoot(true);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Inventory", this.inventory.createTag());
        tag.putInt("GapCooldown", this.gapCooldown);
        tag.putInt("EnderPearlCooldown", this.enderPearlCooldown);
        tag.putInt("SwapToBowCooldown", this.swapToBowCooldown);
        tag.putBoolean("UseBow", this.useBow);
        tag.putDouble("BlockProjectileChance", this.placeBlockToParryChance);
        if (this.target != null) {
            tag.putString("PlayerNpcTarget", this.target.name());
        }
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
        tag.putBoolean("MainWeaponDisarmed", this.mainWeaponDisarmed);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)) {
            this.inventory.fromTag(tag.getList("Inventory", Tag.TAG_COMPOUND));
        }
        this.gapCooldown = tag.getInt("GapCooldown");
        this.enderPearlCooldown = tag.getInt("EnderPearlCooldown");
        this.swapToBowCooldown = tag.getInt("SwapToBowCooldown");
        this.useBow = tag.getBoolean("UseBow");
        this.placeBlockToParryChance = tag.getDouble("BlockProjectileChance");
        if (tag.contains("PlayerNpcTarget", Tag.TAG_STRING)) {
            String name = tag.getString("PlayerNpcTarget");
            try {
                this.target = PlayerNpcTarget.valueOf(name);
            } catch (IllegalArgumentException e) {
                this.target = PlayerNpcTarget.MONSTER_HUNTER;
            }
        }
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
    }

    private boolean shouldCustomInventoryPickup(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        EquipmentSlot slot = LivingEntity.getEquipmentSlotForItem(stack);

        if (slot.getType() == EquipmentSlot.Type.ARMOR) {
            return !this.wantsToPickUp(stack);
        }

        return !isRecoverableWeapon(stack)
                || this.getTarget() == null
                || !this.getMainHandItem().isEmpty();
    }

    private boolean isRecoverableWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();

        return item instanceof SwordItem
                || item instanceof DiggerItem
                || item instanceof TridentItem;
    }

    @Override
    public boolean wantsToPickUp(@NotNull ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        EquipmentSlot slot = LivingEntity.getEquipmentSlotForItem(stack);
        if (slot.getType() != EquipmentSlot.Type.ARMOR) {
            return false;
        }
        return super.wantsToPickUp(stack);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void hostileHunterPlayerMob() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerMobEntity.class, true));
        CommonGoals.attackAllMonstersGoals(this);
        CommonGoals.attackAllNpcGoals(this);
    }

    private void villagerHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        if (!(this.getTarget() instanceof PlayerNpcEntity)) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerNpcEntity.class, 12.0F, 1.2D, 1.4D));
        }
        if (!(this.getTarget() instanceof Player)) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.2D, 1.4D));
        }
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, JevEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        CommonGoals.attackAllVillagerArmyGoal(this);
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));
    }

    private void monsterHunterPlayerMob() {
        CommonGoals.attackAllMonstersGoals(this);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
    }

    private void playerHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerNpcEntity.class, true));
        CommonGoals.attackAllNpcGoals(this);
    }

    private void animalHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        if (!(this.getTarget() instanceof PlayerNpcEntity)) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerNpcEntity.class, 12.0F, 1.2D,
                    1.4D));
        }
        if (!(this.getTarget() instanceof Player)) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.2D, 1.8D));
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(-3, new WaterEnderPearlEscapeGoal(this));
        this.goalSelector.addGoal(-2, new RecoverWeaponInCombatGoal(this, 1.2D, 10.0D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(0, new RetargetCloserThreatGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new BurnNearbyItemGoal(this, 1.0D, 10.0D));
        this.goalSelector.addGoal(6, new PlayIdleAnimationGoal(this, new Random().nextInt(3000, 6000)));
        this.goalSelector.addGoal(7, new LockedRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(4, new BowLineOfSightGoal(this, 1.15D, 7.0D, 14.0D));
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.hurt")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.death")));
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

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (getLivingEntityPatch() == null) return super.hurt(damageSource, f);
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(getLivingEntityPatch().getAnimator().getPlayerFor(null)).getRealAnimation();

        if (damageSource.getEntity() != null && this.getEnderPearlCooldown() == 0
                && !EpicfightUtil.isLongHitAnimation(dynamicAnimation, getLivingEntityPatch())
                && (this.level() instanceof ServerLevel && dynamicAnimation == Animations.EMPTY_ANIMATION)
                && CombatCommon.canPerformNormalAttackLogic((MobPatch<?>) getLivingEntityPatch())) {
            getLivingEntityPatch().playAnimationSynchronized(AnimsEpicFightIronSpell.CASTING_ONE_HAND_BUFF, 0.0F);
            CombatBehaviour.throwEnderPearl(this, 180.0F);
            Entity entity = this;

            if (Math.random() <= 0.5D) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if (entity.isAlive()) {
                            getLivingEntityPatch().playAnimationSynchronized(AnimsEpicFightIronSpell.CASTING_ONE_HAND_BUFF, 0.0F);
                            CombatBehaviour.throwEnderPearl(entity, 90.0F);
                        }
                    }
                };
            }

            this.setEnderPearlCooldown();
        }
        return super.hurt(damageSource, f);
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
        ItemStack itemstack = this.getProjectile(weaponStack);
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

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.getPersistentData().getBoolean("die_by_possess")) {
                this.remove(Entity.RemovalReason.KILLED);
            }
        }
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
                e -> !e.isRemoved()
                        && !e.hasPickUpDelay()
                        && shouldCustomInventoryPickup(e.getItem())
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

    @Override
    public void tick() {
        super.tick();
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        if (this.stunEscapeCooldown == 0 && this.level() instanceof ServerLevel) {
            if (getLivingEntityPatch() != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(getLivingEntityPatch().getAnimator().getPlayerFor(null)).getRealAnimation();
                if (EpicfightUtil.isLongHitAnimationNotExecutedAnimation(dynamicAnimation, getLivingEntityPatch()) && this.isAlive()) {
                    if (this.getRandom().nextFloat() < CombatBehaviour.calculateGuardBreakWakeUpChance(this)) {
                        this.stunEscapeCooldown = 100;
                        this.playingIdleCooldown = playingIdleCooldown + 100;
                        PlayerNpcEntity entity = this;
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
        if (stunEscapeCooldown > 0) stunEscapeCooldown--;
        if (playingIdleCooldown > 0) playingIdleCooldown--;

        if ((tickCount + getId()) % 20 != 0) {
            return;
        }

        if (isInventoryFull()) return;

        pickupNearbyItems();
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata, compoundtag);

        ServerLevel serverLevel = serverLevelAccessor.getLevel();

        if ((mobSpawnType == MobSpawnType.CHUNK_GENERATION || mobSpawnType == MobSpawnType.NATURAL) && serverLevel.isDay() && Math.random() <= 0.8D) {
            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            if (serverLevel.getFluidState(spawnPos).isEmpty()) {
                this.moveTo(spawnPos, this.getYRot(), this.getXRot());
            }
        }

        this.target = PlayerNpcTarget.randomByWeight(this.getRandom());
        if (this.target != null) {
            switch (this.target) {
                case HOSTILE_HUNTER -> {
                    hostileHunterPlayerMob();
                }
                case VILLAGER_HUNTER -> {
                    villagerHunterPlayerMob();
                }
                case MONSTER_HUNTER -> {
                    monsterHunterPlayerMob();
                }
                case PLAYER_HUNTER -> {
                    playerHunterPlayerMob();
                }
                case ANIMAL_HUNTER -> {
                    animalHunterPlayerMob();
                }
                default -> {
                    CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
                    CommonGoals.runAwayFromVillagerArmyGoals(this);
                    if (!(this.getTarget() instanceof Player)) {
                        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 20.0F, 1.2D, 1.8D));
                    }
                }
            }
        }

        List<String> commands = EquipmentDataLoader.getEquipCommands(0.85f, this);
        for (String cmd : commands) {
            try {
                Objects.requireNonNull(this.getServer()).getCommands().getDispatcher().execute(
                        cmd,
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException ignored) {
            }
        }

        this.mainWeaponItem = this.getMainHandItem().copy();
        this.offWeaponItem = this.getOffWeaponItem().copy();

        ChatUtil.joinGame(this);

        if (Math.random() <= 0.05D) {
            TeamUtil.addOrJoinTeam(this, "player");
        }

        if (new Random().nextBoolean()) {
            this.setUseBow(false);
        }

        return returnSpawnGroupData;
    }

    public void awardKillScore(@NotNull Entity entity, int i, @NotNull DamageSource damageSource) {
        super.awardKillScore(entity, i, damageSource);
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

    public static boolean canSpawn(EntityType<PlayerNpcEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (serverLevel.isNight()) {
            // Nerf Player NPC spawn at night
            return false;
        }
        return Monster.checkAnyLightMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 30.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
