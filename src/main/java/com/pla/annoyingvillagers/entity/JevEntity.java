package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.BurstProtectEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.combatbehaviour.AlexJevHookCombat;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class JevEntity extends AVNpc implements BurstProtectEntity {
    private UUID followTargetUUID;
    private AlexEntity followTarget;
    protected float recentDamageTaken = 0.0F;
    protected int recentHitCounter = 0;
    @Override
    public float getRecentDamageTaken() {
        return recentDamageTaken;
    }

    @Override
    public void setRecentDamageTaken(float value) {
        recentDamageTaken = value;
    }

    @Override
    public int getRecentHitCounter() {
        return recentHitCounter;
    }

    @Override
    public void setRecentHitCounter(int value) {
        recentHitCounter = value;
    }

    @Override
    public float getBurstProtectCapRatio() {
        return 0.15F;
    }

    public void setFollowTarget(AlexEntity followTarget) {
        this.followTarget = followTarget;
    }

    public AlexEntity getFollowTarget() {
        return followTarget;
    }

    public void setFollowTargetUUID(UUID followTargetUUID) {
        this.followTargetUUID = followTargetUUID;
    }

    public JevEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.JEV.get(), level);
    }

    public JevEntity(EntityType<JevEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 10;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(AnnoyingVillagersModItems.JEV_BOOK.get()));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.JEV_PENCIL.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.JEV_GLASSES.get()));
        this.setPlaceBlockToParryChance(0.0);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, AlexEntity.class, 12.0F));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Monster.class, 5.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 5.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(2, new Goal() {
            @Override
            public boolean canUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > (float)20.0D * 0.9F;
            }

            @Override
            public void tick() {
                if (followTarget != null && followTarget.isAlive()) {
                    getNavigation().moveTo(followTarget, 2.0D);
                    getLookControl().setLookAt(followTarget, 30.0F, 30.0F);
                    if (distanceToSqr(followTarget) > 20.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(followTarget, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > 50.0D;
            }
        });
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1.0D, 10.0F, 5.0F));
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
        finalDamage = this.applyBurstProtection(this, pDamageSource, finalDamage);

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
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        TeamUtil.addOrJoinTeam(this, "alex");
        setMainWeaponItem(new ItemStack(AnnoyingVillagersModItems.JEV_PENCIL.get()));
        setOffWeaponItem(new ItemStack(AnnoyingVillagersModItems.JEV_BOOK.get()));
        return returnSpawnGroupData;
    }

    @Override
    public void die(@NotNull DamageSource pDamageSource) {
        if (!this.level().isClientSide) {
            AlexJevHookCombat.onJevDeath(this);
        }
        super.die(pDamageSource);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        this.spawnAtLocation(new ItemStack(AnnoyingVillagersModItems.JEV_GLASSES.get()));
        this.spawnAtLocation(new ItemStack(AnnoyingVillagersModItems.JEV_PENCIL.get()));
        this.spawnAtLocation(new ItemStack(AnnoyingVillagersModItems.JEV_BOOK.get()));
        this.dropHookGunForAlex();
        this.dropRandomCombatSupplies();
    }

    @Override
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        this.playSound(
                AnnoyingVillagersModSounds.JEV_SAY_ON_SPAWN.get(),
                0.5F, 1.0F
        );
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (followTarget == null && followTargetUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(followTargetUUID);
                if (entity instanceof AlexEntity alex) {
                    followTarget = alex;
                } else {
                    followTargetUUID = null;
                }
            }
            if (followTarget != null && !followTarget.isAlive()) {
                followTarget = null;
                followTargetUUID = null;
            }
            if (followTarget != null && followTarget.isAlive()) {
                double distanceSq = this.distanceToSqr(followTarget);

                if (distanceSq > 600.0D) {
                    this.teleportTo(
                            followTarget.getX(),
                            followTarget.getY(),
                            followTarget.getZ()
                    );
                }
            }
        }
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (followTargetUUID != null) {
            tag.putUUID("FollowTarget", followTargetUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("FollowTarget")) {
            followTargetUUID = tag.getUUID("FollowTarget");
        }
    }

    private void dropHookGunForAlex() {
        ItemStack hookGun = AlexJevHookCombat.createBoundHookGun(AlexJevHookCombat.createJevPickaxe());
        AlexEntity alex = this.followTarget != null && this.followTarget.isAlive() ? this.followTarget : null;
        if (alex != null && !alex.canStoreInInventory(hookGun)) {
            alex.setCanDualHookInSecondPhase(true);
            return;
        }

        this.spawnAtLocation(hookGun);
    }

    private void dropRandomCombatSupplies() {
        RandomSource random = this.getRandom();

        int blockRolls = 3 + random.nextInt(5);
        for (int i = 0; i < blockRolls; i++) {
            if (random.nextFloat() < 0.82F) {
                this.spawnAtLocation(withRandomCount(AlexJevHookCombat.createRandomJevLootBlock(random), 2, 12, random));
            }
        }

        int plantRolls = 3 + random.nextInt(5);
        for (int i = 0; i < plantRolls; i++) {
            if (random.nextFloat() < 0.86F) {
                this.spawnAtLocation(withRandomCount(AlexJevHookCombat.createRandomJevPlantLoot(random), 1, 8, random));
            }
        }

        int foodRolls = 2 + random.nextInt(5);
        for (int i = 0; i < foodRolls; i++) {
            if (random.nextFloat() < 0.84F) {
                this.spawnAtLocation(withRandomCount(AlexJevHookCombat.createRandomJevLootFood(random), 1, 6, random));
            }
        }

        int potionRolls = 2 + random.nextInt(4);
        for (int i = 0; i < potionRolls; i++) {
            if (random.nextFloat() < 0.78F) {
                this.spawnAtLocation(AlexJevHookCombat.createRandomJevLootPotion(random));
            }
        }
    }

    private static ItemStack withRandomCount(ItemStack stack, int minCount, int randomCount, RandomSource random) {
        if (stack.isEmpty()) {
            return stack;
        }

        int count = minCount + random.nextInt(randomCount);
        stack.setCount(Math.min(count, stack.getMaxStackSize()));
        return stack;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.villager.ambient"));
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.villager.death"));
    }

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (this.getGapCooldown() == 0
                && !this.isHealing()
                && this.getHealth() - f <= ((float) 2 / 3 * this.getMaxHealth())) {
            boolean isEnchanted = this.random.nextDouble() <= Math.max(0.25D, this.getPlaceBlockToParryChance());
            if (!this.level().isClientSide) {
                this.setItemInHand(InteractionHand.MAIN_HAND,
                        new ItemStack(isEnchanted ? Items.ENCHANTED_GOLDEN_APPLE : Items.GOLDEN_APPLE));
            }
            this.setGapCooldown();
            CombatBehaviour.eatingGoldenApple(this, this.level(), 20.0D, isEnchanted);
        }
        return super.hurt(damageSource, f);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 50.0D);
        builder = builder.add(Attributes.ARMOR, 20.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5.0D);
        return builder;
    }
}
