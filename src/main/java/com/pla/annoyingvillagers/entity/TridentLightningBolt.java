package com.pla.annoyingvillagers.entity;

import com.google.common.collect.Sets;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModDamageTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TridentLightningBolt extends LightningBolt {
    private int tridentLife = 2;
    private int tridentFlashes;
    private final Set<Entity> tridentHitEntities = Sets.newHashSet();
    boolean superLightning = false;

    public void setSuperLightning(boolean superLightning) {
        this.superLightning = superLightning;
    }

    @Nullable
    private LivingEntity owner;

    private boolean tridentVisualOnly = false;

    public TridentLightningBolt(EntityType<? extends LightningBolt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noCulling = true;
        this.tridentLife = 2;
        this.tridentFlashes = this.random.nextInt(3) + 1;
    }

        public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    @Override
    public void tick() {
        super.baseTick();

        if (this.tridentLife == 2) {
            if (this.level().isClientSide()) {
                this.level().playLocalSound(
                        this.getX(), this.getY(), this.getZ(),
                        SoundEvents.LIGHTNING_BOLT_THUNDER,
                        SoundSource.WEATHER,
                        10000.0F,
                        0.8F + this.random.nextFloat() * 0.2F,
                        false
                );
                this.level().playLocalSound(
                        this.getX(), this.getY(), this.getZ(),
                        SoundEvents.LIGHTNING_BOLT_IMPACT,
                        SoundSource.WEATHER,
                        2.0F,
                        0.5F + this.random.nextFloat() * 0.2F,
                        false
                );
            } else {
                this.gameEvent(GameEvent.LIGHTNING_STRIKE);
                if (this.owner instanceof ServerPlayer serverPlayer) {
                    this.setCause(serverPlayer);
                }
            }
        }

        --this.tridentLife;

        if (this.tridentLife < 0) {
            if (this.tridentFlashes == 0) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    List<Entity> list = this.level().getEntities(
                            this,
                            new AABB(
                                    this.getX() - 15.0D, this.getY() - 15.0D, this.getZ() - 15.0D,
                                    this.getX() + 15.0D, this.getY() + 21.0D, this.getZ() + 15.0D
                            ),
                            entity -> entity.isAlive() && !this.tridentHitEntities.contains(entity)
                    );

                    for (ServerPlayer serverPlayer : serverLevel.getPlayers(p -> p.distanceTo(this) < 256.0F)) {
                        CriteriaTriggers.LIGHTNING_STRIKE.trigger(serverPlayer, this, list);
                    }
                }

                this.discard();
            } else if (this.tridentLife < -this.random.nextInt(10)) {
                --this.tridentFlashes;
                this.tridentLife = 1;
            }
        }

        if (this.tridentLife >= 0) {
            if (!(this.level() instanceof ServerLevel serverLevel)) {
                this.level().setSkyFlashTime(2);
            } else if (!this.tridentVisualOnly) {
                List<Entity> list = this.level().getEntities(
                        this,
                        new AABB(
                                this.getX() - 3.0D, this.getY() - 3.0D, this.getZ() - 3.0D,
                                this.getX() + 3.0D, this.getY() + 9.0D, this.getZ() + 3.0D
                        ),
                        entity -> entity instanceof LivingEntity && entity.isAlive() && entity != this.owner && !(entity instanceof BbqEntity) && !entity.isSpectator() && !(entity instanceof Player player && player.isCreative())
                );

                if (this.superLightning) {
                    DamageSource explosionDamage =
                            AnnoyingVillagersModDamageTypes.Sources.impactExplosion(serverLevel.registryAccess(), this);

                    serverLevel.explode(
                            this,
                            explosionDamage,
                            null,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            serverLevel.random.nextFloat() * 5.0F + 5.0F,
                            false,
                            AnnoyingVillagersConfig.TRIDENT_FESTIVAL_CAN_BREAK_BLOCK.get() ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE
                    );
                }

                for (Entity entity : list) {
                    if (!EventHooks.onEntityStruckByLightning(entity, this)) {
                        if (entity instanceof LivingEntity livingEntity) {
                            if (this.superLightning) {
                                livingEntity.addEffect(new MobEffectInstance(
                                        AnnoyingVillagersModMobEffects.ELECTRIFY,
                                        100,
                                        2
                                ));
                            } else {
                                livingEntity.addEffect(new MobEffectInstance(
                                        AnnoyingVillagersModMobEffects.ELECTRIFY,
                                        60,
                                        1
                                ));
                            }
                        }

                        if (this.superLightning) {
                            entity.hurt(level().damageSources().indirectMagic(this, owner), 50.0F);
                        } else {
                            entity.hurt(level().damageSources().indirectMagic(this, owner), 5.0F);
                        }
                    }
                }

                this.tridentHitEntities.addAll(list);

                if (this.owner instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.CHANNELED_LIGHTNING.trigger(serverPlayer, list);
                }
            }
        }
    }
}