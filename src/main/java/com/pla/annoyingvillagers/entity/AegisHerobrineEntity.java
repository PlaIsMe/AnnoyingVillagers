package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.entity.goal.EliteHerobrineSecondFormGoal;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AegisHerobrineEntity extends HerobrineMob {
    public AegisHerobrineEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), level);
    }

    public AegisHerobrineEntity(EntityType<AegisHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.5F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.ENDER_AEGIS.get()));
        this.setChatName(this.getDisplayName().getString());
    }

    @Override
    public @Nullable SoundEvent getAttackVoiceSound() {
        return AnnoyingVillagersModSounds.ELITE_HEROBRINE_SAY.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0,new EliteHerobrineSecondFormGoal<>(this,RigAnimationId.AEGIS_HEROBRINE_ULT,aegis -> aegis.getMainHandItem().getItem() instanceof EnderAegisItem));
    }

    @Override
    protected void onSecondFormActionWindowOpened() {
        this.playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(),0.5F,1.0F);
    }

    /**
     * Aegis-specific second-form effect used by the ULT hook and successful Aegis guards.
     * HerobrineMob owns the state/budget; state 2 therefore remains unlimited.
     */
    public void fireSecondFormShieldShot() {
        if (!this.canUseSecondFormAction()) return;
        EnderAegisItem.shieldShoot(this.level(),this);
        this.consumeSecondFormAction();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.tickCount % 20 == 0) {
                ItemStack itemStack = this.getMainHandItem();
                if (this.getState() > 0) {
                    if (itemStack.getItem() instanceof EnderAegisItem
                            && itemStack.getTag() != null && !itemStack.getTag().getBoolean("SecondForm")) {
                        itemStack.getTag().putBoolean("SecondForm", true);
                    }
                } else {
                    if (itemStack.getItem() instanceof EnderAegisItem
                            && itemStack.getTag() != null && itemStack.getTag().contains("SecondForm")) {
                        itemStack.getTag().remove("SecondForm");
                    }
                }
            }
        }
    }

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (damageSource.is(DamageTypes.FALL)) return false;
        if (damageSource.is(DamageTypes.CACTUS)) return false;
        if (damageSource.is(DamageTypes.WITHER)) return false;
        if (damageSource.is(DamageTypes.DROWN)) return false;
        if (damageSource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damageSource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (!(damageSource.getDirectEntity() instanceof EnchantedArrowEntity)
                && damageSource.getDirectEntity() instanceof AbstractArrow
                && !(damageSource.getDirectEntity() instanceof BlueDemonThrownTridentEntity)) return false;
        return super.hurt(damageSource, f);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity = new EliteHerobrineKnockedEntity(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), serverLevel);

            eliteHerobrineKnockedEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            eliteHerobrineKnockedEntity.getPersistentData().putString("FromElite", "EnderAegis");
            eliteHerobrineKnockedEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(eliteHerobrineKnockedEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(eliteHerobrineKnockedEntity);

            if (this.getGregUUID() != null) {
                Entity entity = serverLevel.getEntity(this.getGregUUID());
                if (entity instanceof HerobrineGregEntity herobrineGregEntity && entity.isAlive()) {
                    herobrineGregEntity.requestProtect(eliteHerobrineKnockedEntity.getUUID(), eliteHerobrineKnockedEntity);
                }
            }
        }
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
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 80.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 40.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return addEpicFightAttributes(builder);
    }
}
