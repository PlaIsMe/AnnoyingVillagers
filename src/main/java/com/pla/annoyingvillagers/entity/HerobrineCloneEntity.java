package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.Difficulty;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.ProgressionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HerobrineCloneEntity extends HerobrineMob {
    public HerobrineCloneEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(), level);
    }

    public HerobrineCloneEntity(EntityType<HerobrineCloneEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.7F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setCustomNameVisible(false);
        this.setChatName(this.getDisplayName().getString());
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get()));
    }

    @Override
    public int getMinVoiceCooldown() {
        return 60;
    }

    @Override
    public int getMaxVoiceCooldown() {
        return 200;
    }

    @Override
    public @Nullable SoundEvent getAttackVoiceSound() {
        return AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY.get();
    }

    @Override
    public @Nullable SoundEvent getHurtVoiceSound() {
        return AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY_ON_HURT.get();
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

    public void die(@NotNull DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (AnnoyingVillagersConfig.TURN_ON_NPC_VOICE.get()) {
                this.playSound(AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY_ON_DEATH.get(), 0.5F, 1.0F);
            }
            InfectedPlayerNpcEntity corpse = new InfectedPlayerNpcEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_NPC.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "herobrine_clone");
            if (killedName.isEmpty()) {
                killedName = FakePlayer.getRandomHardcodedName(this.getRandom());
            }
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            corpse.setItemSlot(EquipmentSlot.HEAD, this.getItemBySlot(EquipmentSlot.HEAD).copy());
            corpse.setItemSlot(EquipmentSlot.CHEST, this.getItemBySlot(EquipmentSlot.CHEST).copy());
            corpse.setItemSlot(EquipmentSlot.LEGS, this.getItemBySlot(EquipmentSlot.LEGS).copy());
            corpse.setItemSlot(EquipmentSlot.FEET, this.getItemBySlot(EquipmentSlot.FEET).copy());
            serverLevel.addFreshEntity(corpse);
        }
    }

    public static boolean canSpawn(EntityType<HerobrineCloneEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (HerobrineMobData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        if (!serverLevel.isNight()) {
            return false;
        }
        return ProgressionUtil.isAtLeastDifficulty(Difficulty.MEDIUM) && Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder addEpicFightAttributes(Builder builder) {
//      ADD THIS CODE IN AV_EFM
//        return builder.add(EpicFightAttributes.IMPACT.get(), 4.0D)
//                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10.0D)
//                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
//                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
//                .add(EpicFightAttributes.MAX_STAMINA.get(), 60.0D)
//                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);

        return builder;
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return addEpicFightAttributes(builder);
    }
}
