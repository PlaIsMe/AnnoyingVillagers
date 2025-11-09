package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;


public class GreenVillagerGeneralEntity extends PathfinderMobInventory {

    public GreenVillagerGeneralEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL.get(), level);
    }

    public GreenVillagerGeneralEntity(EntityType<GreenVillagerGeneralEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.0F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike) AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_HELMET_FIX.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike) AnnoyingVillagersModItems.PURPLE_VILLAGER_GENERAL_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_GENERAL_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_GENERAL_BOOTS.get()));
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForVillagerKnightNpc(this);
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getAmbientSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.villager.ambient"));
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.villager.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        VillagerGeneralOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity(), f);
        if (damagesource.is(DamageTypes.FALL)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        PurpleVillagerGeneralOnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if (this.level() instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
            ServerLevel serverlevel = levelaccessor;
            GreenVillagerGeneralDeadEntity deadEntity = new GreenVillagerGeneralDeadEntity((EntityType) AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL_DEAD.get(), serverlevel);
            deadEntity.moveTo(this.getX(), this.getY(), this.getZ(), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            if (deadEntity instanceof Mob) {
                Mob mob = (Mob) deadEntity;
                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(deadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            }
            this.remove(RemovalReason.KILLED);
            levelaccessor.addFreshEntity(deadEntity);
            try {
                deadEntity.getServer().getCommands().getDispatcher().execute(
                        "kill @s",
                        deadEntity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        PurpleVillagerGeneralOnEntityInitialSpawnProcedure.execute(serverlevelaccessor, this.getX(), this.getY(), this.getZ(), this);
        return spawngroupdata1;
    }

//    public InteractionResult mobInteract(Player player, InteractionHand interactionhand) {
//        player.getItemInHand(interactionhand);
//        InteractionResult interactionresult = InteractionResult.sidedSuccess(this.level().isClientSide());
//
//        super.mobInteract(player, interactionhand);
//        double d0 = this.getX();
//        double d1 = this.getY();
//        double d2 = this.getZ();
//        Level level = this.level();
//
//        PurpleVillagerGeneralOnAttackingEntityProcedure.execute(level, d0, d1, d2, this);
//        return interactionresult;
//    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
    }

    public void baseTick() {
        super.baseTick();
        BlueVillagerGeneralOnTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public static boolean canSpawn(EntityType<GreenVillagerGeneralEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25D);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder = builder.add(Attributes.ARMOR, 8.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}
