package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.Nullable;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

public class PlayerMobDeadEntity extends PlayerMobEntity {

    public PlayerMobDeadEntity(EntityType<? extends PlayerMobDeadEntity> type, Level level) {
        super(type, level);
    }

    public PlayerMobDeadEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), level);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        return pSpawnData;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity
                .createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 20.0F)
                .add(Attributes.ATTACK_KNOCKBACK).add(Attributes.ATTACK_DAMAGE, 3.5F)
                .add(Attributes.MOVEMENT_SPEED, 0.24);
    }
}