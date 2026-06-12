package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.HookedDiamondSwordItem;
import com.pla.annoyingvillagers.item.WoopieTheSwordItem;
import com.pla.annoyingvillagers.util.*;
import com.pla.annoyingvillagers.clazz.AVNpc;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Random;
import java.util.function.Consumer;


public class RedVillagerGeneralEntity extends AVNpc {
    public RedVillagerGeneralEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL.get(), level);
    }

    public RedVillagerGeneralEntity(EntityType<RedVillagerGeneralEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 8;
        this.setNoAi(false);
        this.setPlaceBlockToParryChance(0.7);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForVillagerKnightNpc(this);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.ambient"));
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.death"));
    }

    @Override
    public @Nullable SoundEvent getAttackVoiceSound() {
        return AnnoyingVillagersModSounds.VILLAGER_GENERALS_SAY.get();
    }

    @Override
    protected boolean hasEnderPearlCounter() {
        return true;
    }

    @Override
    protected void beforeEnderPearlCounter(@NotNull DamageSource damageSource) {
        this.restoreOffhandLater(150);
    }

    @Override
    protected ItemStack getEnderPearlCounterRestoreOffhandItem() {
        return new ItemStack(Items.ENDER_PEARL);
    }

    @Override
    protected void doEnderPearlCounterPattern(@NotNull DamageSource damageSource) {
        this.doVillagerGeneralStyleEnderPearlCounter();
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (this.level() instanceof ServerLevel serverLevel) {
            final double x = this.getX();
            final double y = this.getY() + 1.0D;
            final double z = this.getZ();

            Consumer<ItemStack> dropStack = (stack) -> {
                ItemEntity drop = new ItemEntity(serverLevel, x, y, z, stack);
                drop.setPickUpDelay(10);
                serverLevel.addFreshEntity(drop);
            };

            ItemStack[] drops = new ItemStack[] {
                    new ItemStack(AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD.get()),

                    new ItemStack(Items.DIAMOND),
                    new ItemStack(Items.DIAMOND),
                    new ItemStack(Items.DIAMOND),
                    new ItemStack(Items.DIAMOND),

                    new ItemStack(Items.BREAD),
                    new ItemStack(Items.BREAD),

                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),

                    new ItemStack(Items.WHEAT),
                    new ItemStack(Items.WHEAT),

                    new ItemStack(AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()),

                    new ItemStack(Blocks.OAK_PLANKS),
                    new ItemStack(Blocks.OAK_PLANKS),

                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),
                    new ItemStack(Items.ENDER_PEARL),

                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),
                    new ItemStack(Items.ARROW),

                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),
                    new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get()),

                    this.createDamagedDropStack(AnnoyingVillagersModItems.ADVANCED_FISHING_ROD.get()),

                    new ItemStack(Items.IRON_PICKAXE),

                    new ItemStack(Items.APPLE),
                    new ItemStack(Items.APPLE),

                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.GOLD_INGOT),

                    new ItemStack(Items.STICK),
                    new ItemStack(Items.STICK),

                    new ItemStack(Items.FIREWORK_ROCKET),
                    new ItemStack(Items.FIREWORK_ROCKET),

                    new ItemStack(Items.POISONOUS_POTATO),

                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD),
                    new ItemStack(Items.EMERALD)
            };

            for (ItemStack stack : drops) {
                dropStack.accept(stack);
            }
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);

        TeamUtil.addOrJoinTeam(this, "villagers");

        this.setItemSlot(EquipmentSlot.MAINHAND, VillagerUtil.generateMainWeaponItem());
        if (this.getMainHandItem().getItem() instanceof WoopieTheSwordItem
                || this.getMainHandItem().getItem() instanceof HookedDiamondSwordItem) {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(AnnoyingVillagersModItems.HEATER_SHIELD.get()));
        } else {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
        }
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.RED_VILLAGER_GENERAL_HELMET_FIX.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.RED_VILLAGER_GENERAL_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(AnnoyingVillagersModItems.VILLAGER_GENERAL_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(AnnoyingVillagersModItems.VILLAGER_GENERAL_BOOTS.get()));
        this.setMainWeaponItem(this.getMainHandItem().copy());
        this.setOffWeaponItem(this.getOffWeaponItem().copy());

        if (new Random().nextBoolean()) {
            this.setUseBow(false);
        }

        return returnSpawnGroupData;
    }

    @Override
    protected void implementFirstTick(ServerLevel serverLevel) {
        super.implementFirstTick(serverLevel);
        if (new Random().nextDouble() <= 0.3D) {
            RidingUtil.rideRandomAnimal(serverLevel, this);
        }
    }

    public static boolean canSpawn(EntityType<RedVillagerGeneralEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ARMOR, 30.0D);
    }
}
