package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;


public class ReaperHerobrineEntity extends HerobrineMob {
    private HerobrineDragonEntity thunderHerobrineDragon;
    private UUID thunderHerobrineDragonUUID;
    private HerobrineDragonEntity meteoriteHerobrineDragon;
    private UUID meteoriteHerobrineDragonUUID;
    private HerobrineDragonEntity healingHerobrineDragon;
    private UUID healingHerobrineDragonUUID;
    private boolean spawnDragonInit = false;
    private int dragonSummonCooldown = 0;

    public ReaperHerobrineEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), level);
    }

    public ReaperHerobrineEntity(EntityType<ReaperHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.9F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
        this.setChatName(this.getDisplayName().getString());
    }

    @Override
    public @Nullable SoundEvent getAttackVoiceSound() {
        return AnnoyingVillagersModSounds.ELITE_HEROBRINE_SAY.get();
    }

    public HerobrineDragonEntity getThunderHerobrineDragon() {
        return thunderHerobrineDragon;
    }

    public UUID getThunderHerobrineDragonUUID() {
        return thunderHerobrineDragonUUID;
    }

    public HerobrineDragonEntity getMeteoriteHerobrineDragon() {
        return meteoriteHerobrineDragon;
    }

    public UUID getMeteoriteHerobrineDragonUUID() {
        return meteoriteHerobrineDragonUUID;
    }

    public HerobrineDragonEntity getHealingHerobrineDragon() {
        return healingHerobrineDragon;
    }

    public UUID getHealingHerobrineDragonUUID() {
        return healingHerobrineDragonUUID;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (thunderHerobrineDragonUUID != null) {
            tag.putUUID("ThunderHerobrineDragonUUID", thunderHerobrineDragonUUID);
        }
        if (meteoriteHerobrineDragonUUID != null) {
            tag.putUUID("MeteoriteHerobrineDragonUUID", meteoriteHerobrineDragonUUID);
        }
        if (healingHerobrineDragonUUID != null) {
            tag.putUUID("HealingHerobrineDragonUUID", healingHerobrineDragonUUID);
        }
        tag.putBoolean("SpawnDragonInit", spawnDragonInit);
        tag.putInt("DragonSummonCooldown", dragonSummonCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("ThunderHerobrineDragonUUID")) {
            thunderHerobrineDragonUUID = tag.getUUID("ThunderHerobrineDragonUUID");
        }
        if (tag.hasUUID("MeteoriteHerobrineDragonUUID")) {
            meteoriteHerobrineDragonUUID = tag.getUUID("MeteoriteHerobrineDragonUUID");
        }
        if (tag.hasUUID("HealingHerobrineDragonUUID")) {
            healingHerobrineDragonUUID = tag.getUUID("HealingHerobrineDragonUUID");
        }
        spawnDragonInit = tag.getBoolean("SpawnDragonInit");
        dragonSummonCooldown = tag.contains("DragonSummonCooldown") ? tag.getInt("DragonSummonCooldown") : dragonSummonCooldown;
    }

    // 0: thunder dragon
    // 1: meteorite dragon
    // 2: healing dragon
    public void summonEnderDragon(int type) {
        if (this.level() instanceof ServerLevel serverLevel) {
            HerobrineDragonEntity dragon = new HerobrineDragonEntity(AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get(), serverLevel);
            dragon.moveTo(this.getX(), this.getY() + 20.0D, this.getZ(), this.getRandom().nextFloat() * 360.0F, 0.0F);
            dragon.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(dragon.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);

            dragon.setPersistenceRequired();

            dragon.setSummoner(this);
            dragon.setSummonerUUID(this.getUUID());

            serverLevel.addFreshEntity(dragon);
            TeamUtil.addOrJoinTeam(dragon, "herobrine");

            if (type == 0) {
                this.thunderHerobrineDragonUUID = dragon.getUUID();
                this.thunderHerobrineDragon = dragon;
            } else if (type == 1) {
                this.meteoriteHerobrineDragonUUID = dragon.getUUID();
                this.meteoriteHerobrineDragon = dragon;
            } else {
                this.healingHerobrineDragonUUID = dragon.getUUID();
                this.healingHerobrineDragon = dragon;
                EndCrystal endCrystal = new EndCrystal(EntityType.END_CRYSTAL, serverLevel);
                endCrystal.moveTo(dragon.getX(), dragon.getY(), dragon.getZ());
                serverLevel.addFreshEntity(endCrystal);
                endCrystal.startRiding(dragon, true);
            }

            if (this.level().getServer() != null) {
                Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(
                        Component.literal("<" + this.getChatName() + "> " +
                                Component.translatable("subtitles.herobrine_summon").getString()),
                        false
                );
            }
        }
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (!spawnDragonInit) {
                this.spawnDragonInit = true;
                summonEnderDragon(0);
            }

            if (dragonSummonCooldown <= 0) {
                // First stage
                // Below 50% health summon meteorite
                // Above 50% health summon thunder
                // Second stage
                // Summon any missing dragon
                if (this.getState() < 2) {
                    if (this.getHealth() > this.getMaxHealth() / 2 && thunderHerobrineDragon == null && thunderHerobrineDragonUUID == null) {
                        summonEnderDragon(0);
                    } else if (this.getHealth() <= this.getMaxHealth() / 2 && meteoriteHerobrineDragon == null && meteoriteHerobrineDragonUUID == null) {
                        summonEnderDragon(1);
                    }
                } else if (this.getState() == 2) {
                    if (this.thunderHerobrineDragon == null && thunderHerobrineDragonUUID == null) {
                        summonEnderDragon(0);
                    } else if (meteoriteHerobrineDragon == null && meteoriteHerobrineDragonUUID == null) {
                        summonEnderDragon(1);
                    } else if (healingHerobrineDragon == null && healingHerobrineDragonUUID == null) {
                        summonEnderDragon(2);
                    }
                }
            } else {
                dragonSummonCooldown--;
            }

            if (thunderHerobrineDragon == null && thunderHerobrineDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(thunderHerobrineDragonUUID);
                if (entity instanceof HerobrineDragonEntity dragon) {
                    thunderHerobrineDragon = dragon;
                } else {
                    thunderHerobrineDragon = null;
                }
            }

            if (meteoriteHerobrineDragon == null && meteoriteHerobrineDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(meteoriteHerobrineDragonUUID);
                if (entity instanceof HerobrineDragonEntity dragon) {
                    meteoriteHerobrineDragon = dragon;
                } else {
                    meteoriteHerobrineDragon = null;
                }
            }

            if (healingHerobrineDragon == null && healingHerobrineDragonUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(healingHerobrineDragonUUID);
                if (entity instanceof HerobrineDragonEntity dragon) {
                    healingHerobrineDragon = dragon;
                } else {
                    healingHerobrineDragon = null;
                }
            }

            if (thunderHerobrineDragon != null && !thunderHerobrineDragon.isAlive()) {
                thunderHerobrineDragon = null;
                thunderHerobrineDragonUUID = null;

                if (this.level().getServer() != null) {
                    Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(
                            Component.literal("<" + this.getChatName() + ">  " +
                                    Component.translatable("subtitles.reaper_herobrine_return_dragon").getString()),
                            false
                    );
                }
                if (dragonSummonCooldown == 0) {
                    if (this.getState() < 2) {
                        dragonSummonCooldown = new Random().nextInt(4800, 7200);
                    } else if (this.getState() == 2) {
                        dragonSummonCooldown = new Random().nextInt(2400, 4800);
                    }
                }
            }

            if (meteoriteHerobrineDragon != null && !meteoriteHerobrineDragon.isAlive()) {
                meteoriteHerobrineDragon = null;
                meteoriteHerobrineDragonUUID = null;

                if (this.level().getServer() != null) {
                    Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(
                            Component.literal("<" + this.getChatName() + ">  " +
                                    Component.translatable("subtitles.reaper_herobrine_return_dragon").getString()),
                            false
                    );
                }

                if (dragonSummonCooldown == 0) {
                    if (this.getState() < 2) {
                        dragonSummonCooldown = new Random().nextInt(4800, 7200);
                    } else if (this.getState() == 2) {
                        dragonSummonCooldown = new Random().nextInt(2400, 4800);
                    }
                }
            }

            if (healingHerobrineDragon != null && !healingHerobrineDragon.isAlive()) {
                healingHerobrineDragon = null;
                healingHerobrineDragonUUID = null;

                if (this.level().getServer() != null) {
                    Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(
                            Component.literal("<" + this.getChatName() + ">  " +
                                    Component.translatable("subtitles.reaper_herobrine_return_dragon").getString()),
                            false
                    );
                }

                if (dragonSummonCooldown == 0) {
                    if (this.getState() < 2) {
                        dragonSummonCooldown = new Random().nextInt(4800, 7200);
                    } else if (this.getState() == 2) {
                        dragonSummonCooldown = new Random().nextInt(2400, 4800);
                    }
                }
            }

            if (this.tickCount % 20 == 0) {
                if (this.getState() > 0) {
                    HerobrineUtil.spawnEliteEffect(this.level(), this.getX(), this.getY(), this.getZ(), this);
                }
            }
        }
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
    public void remove(@NotNull RemovalReason reason) {
        if (this.thunderHerobrineDragon != null) {
            this.thunderHerobrineDragon.kill();
            this.thunderHerobrineDragon = null;
            this.thunderHerobrineDragonUUID = null;
        }
        if (this.meteoriteHerobrineDragon != null) {
            this.meteoriteHerobrineDragon.kill();
            this.meteoriteHerobrineDragon = null;
            this.meteoriteHerobrineDragonUUID = null;
        }
        if (this.healingHerobrineDragon != null) {
            this.healingHerobrineDragon.kill();
            this.healingHerobrineDragon = null;
            this.healingHerobrineDragonUUID = null;
        }
        super.remove(reason);
    }

    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity = new EliteHerobrineKnockedEntity(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), serverLevel);

            eliteHerobrineKnockedEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
            eliteHerobrineKnockedEntity.getPersistentData().putString("FromElite", "EnderSlayerScythe");
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

    public static Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
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
