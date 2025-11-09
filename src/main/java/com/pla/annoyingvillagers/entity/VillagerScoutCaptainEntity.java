package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;


public class VillagerScoutCaptainEntity extends PathfinderMobInventory {
    public VillagerScoutCaptainEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), level);
    }

    public VillagerScoutCaptainEntity(EntityType<VillagerScoutCaptainEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.5F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET_FIX.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
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

    public boolean removeWhenFarAway(double d0) {
        return false;
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
        VillagerScoutOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity(), (double) f);
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        VillagerScoutCaptainOnEntityDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if (this.level() instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
            ServerLevel serverlevel = levelaccessor;
            VillagerScoutDeadEntity deadEntity = new VillagerScoutDeadEntity((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_DEAD.get(), serverlevel);
            deadEntity.moveTo(this.getX(), this.getY(), this.getZ(), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            if (deadEntity instanceof Mob) {
                Mob mob = (Mob) deadEntity;
                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(deadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            }
            this.remove(Entity.RemovalReason.KILLED);
            levelaccessor.addFreshEntity(deadEntity);
            try {
                deadEntity.getServer().getCommands().getDispatcher().execute(
                        "kill @s",
                        deadEntity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
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
//        VillagerScoutOnInteractProcedure.execute(level, d0, d1, d2, this);
//        return interactionresult;
//    }

    public void baseTick() {
        super.baseTick();
        VillagerScoutSmartSkillProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public void playerTouch(Player player) {
        super.playerTouch(player);
        VillagerScoutCaptainOnPlayerTouchProcedure.execute(this.level(), this);
    }

    public static boolean canSpawn(EntityType<VillagerScoutCaptainEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder = builder.add(Attributes.ARMOR, 6.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}

