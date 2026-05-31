package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.combatbehaviour.HerobrineCommon;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.EpicfightUtil;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.animations.weapons.AnimsMoonless;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Objects;

public class HerobrineChrisEntity extends HerobrineMob {
    public HerobrineChrisEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.HEROBRINE_CHRIS.get(), level);
    }

    public HerobrineChrisEntity(EntityType<HerobrineChrisEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setPersistenceRequired();
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
        if (this.level() instanceof ServerLevel serverLevel && HerobrineCommon.canPlaySecondFormAnimation((MobPatch<?>) Objects.requireNonNull(this.getLivingEntityPatch()))) {
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(this.getLivingEntityPatch().getAnimator().getPlayerFor(null)).getRealAnimation();
            if (!EpicfightUtil.isLongHitAnimation(dynamicAnimation, getLivingEntityPatch())
                    && (this.level() instanceof ServerLevel && dynamicAnimation == Animations.EMPTY_ANIMATION)) {
                Objects.requireNonNull(this.getLivingEntityPatch()).playAnimationSynchronized(AnimsMoonless.MOONLESS_GUARD_HIT_1, 0.0F);
                HerobrineCommon.playSecondFormAnimation((MobPatch<?>) Objects.requireNonNull(this.getLivingEntityPatch()));
                this.heal(4.0F);
                EpicfightUtil.damageBlocked(damagesource, this, serverLevel);
                return false;
            }
        }
        return super.hurt(damagesource, f);
    }

    public void die(@NotNull DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (AnnoyingVillagersConfig.TURN_ON_NPC_VOICE.get()) {
                this.playSound(AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY_ON_DEATH.get(), 0.5F, 1.0F);
            }
            serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("subtitles.herobrine_clone_die"), false);
            InfectedChrisEntity corpse = new InfectedChrisEntity(AnnoyingVillagersModEntities.INJECTED_CHRIS.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(corpse);
        }
    }

    public static boolean canSpawn(EntityType<HerobrineChrisEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        int passesDay = (int) (serverLevel.getGameTime() / 24000);
        if (passesDay != 0 && passesDay % 3 != 0) {
            return false;
        }
        if (HerobrineMobData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        if (!serverLevel.isNight()) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
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
