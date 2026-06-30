package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.Difficulty;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.ShadowObsidianSwordItem;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.ProgressionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Random;

public class ArmoredHerobrineEntity extends HerobrineMob {
    public ArmoredHerobrineEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(), level);
    }

    public ArmoredHerobrineEntity(EntityType<ArmoredHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(4.0F);
        this.xpReward = 60;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE.get()));
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));
        this.setChatName(this.getDisplayName().getString());
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (!(damagesource.getDirectEntity() instanceof EnchantedArrowEntity)
                && damagesource.getDirectEntity() instanceof AbstractArrow
                && !(damagesource.getDirectEntity() instanceof BlueDemonThrownTridentEntity)) return false;
        return super.hurt(damagesource, f);
    }

    @Override
    public void rollItem() {
        super.rollItem();
        ItemStack offHand = this.getOffhandItem();
        if (offHand.getItem() instanceof ShadowObsidianSwordItem) {
            this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        } else {
            this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));
        }
    }

    @Override
    public @Nullable SoundEvent getAttackVoiceSound() {
        if (new Random().nextFloat() <= 0.2F) {
            return AnnoyingVillagersModSounds.ARMORED_HEROBRINE_SAY.get();
        } else {
            return AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY.get();
        }
    }

    @Override
    public @Nullable SoundEvent getHurtVoiceSound() {
        return AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY_ON_HURT.get();
    }

    public void die(@NotNull DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (AnnoyingVillagersConfig.TURN_ON_NPC_VOICE.get()) {
                this.playSound(AnnoyingVillagersModSounds.ARMORED_HEROBRINE_SAY_ON_DEATH.get(), 0.5F, 1.0F);
            }
            InfectedTheMostMoistBurrit0Entity infectedTheMostMoistBurrit0Entity = new InfectedTheMostMoistBurrit0Entity(AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(), serverLevel);

            infectedTheMostMoistBurrit0Entity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            infectedTheMostMoistBurrit0Entity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(infectedTheMostMoistBurrit0Entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(infectedTheMostMoistBurrit0Entity);
        }
    }

    public static boolean canSpawn(EntityType<ArmoredHerobrineEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (HerobrineMobData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        if (!serverLevel.isNight()) {
            return false;
        }
        return ProgressionUtil.isAtLeastDifficulty(Difficulty.MEDIUM) && Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(EpicFightAttributes.IMPACT.get(), 4.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10.0D)
                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 60.0D)
                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);
    }
}
