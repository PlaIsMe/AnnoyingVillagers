package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.spawnhandler.AlexData;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.UUID;


public class AlexEntity extends PathfinderMobInventory {
    private JevEntity jevToProtect;
    private UUID jevUUID;
    private boolean jevDeathMessageSent = false;
    private boolean spawnJev = false;

    public void setProtectingJev(JevEntity jev) {
        this.jevToProtect = jev;
    }

    public void setJevUUID(UUID jevUUID) {
        this.jevUUID = jevUUID;
    }

    public AlexEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.ALEX.get(), level);
    }

    public AlexEntity(EntityType<AlexEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.8F);
        this.xpReward = 60;
        this.setNoAi(false);
        this.setCustomName(Component.translatable(this.getType().getDescriptionId()));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        sword.enchant(Enchantments.SHARPNESS, 5);
        sword.enchant(Enchantments.FIRE_ASPECT, 2);
        sword.enchant(Enchantments.KNOCKBACK, 2);
        sword.enchant(Enchantments.UNBREAKING, 5);
        this.setItemSlot(EquipmentSlot.MAINHAND, sword);
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> jevToProtect != null
                && jevToProtect.isAlive()
                && target != null
                && target.getLastHurtMob() == jevToProtect));
        CommonGoals.registerGoalForNeutralNpc(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (jevUUID != null) {
            tag.putUUID("JevUUID", jevUUID);
        }
        tag.putBoolean("JevDeathMessageSent", jevDeathMessageSent);
        tag.putBoolean("SpawnJev", spawnJev);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("JevUUID")) {
            jevUUID = tag.getUUID("JevUUID");
        }
        jevDeathMessageSent = tag.getBoolean("JevDeathMessageSent");
        spawnJev = tag.getBoolean("SpawnJev");
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

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        AlexOnHurtProcedure.execute(this.level(), this, damagesource.getEntity(), f);
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.FALLING_ANVIL)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        AlexOnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if (this.level() instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
            ServerLevel serverlevel = (ServerLevel)levelaccessor;
            AlexDeadEntity alexdeadentity = new AlexDeadEntity((EntityType) AnnoyingVillagersModEntities.ALEX_DEAD.get(), serverlevel);

            alexdeadentity.moveTo(this.getX(), this.getY(), this.getZ(), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            if (alexdeadentity instanceof Mob) {
                Mob mob = (Mob)alexdeadentity;

                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(alexdeadentity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            }
            this.remove(RemovalReason.KILLED);
            levelaccessor.addFreshEntity(alexdeadentity);
            try {
                alexdeadentity.getServer().getCommands().getDispatcher().execute(
                        "kill @s",
                        alexdeadentity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
    }

    private void spawnJev() {
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;

            JevEntity jevEntity = new JevEntity((EntityType) AnnoyingVillagersModEntities.JEV.get(), serverlevel);
            jevEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            jevEntity.setFollowTarget(this);
            jevEntity.setFollowTargetUUID(this.getUUID());
            jevEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(jevEntity);

            this.setJevUUID(jevEntity.getUUID());
            this.setProtectingJev(jevEntity);
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (mobspawntype == MobSpawnType.NATURAL || mobspawntype == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverlevelaccessor.getLevel();
            AlexData alexData = AlexData.get(serverLevel);

            if (!alexData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            }
        }

        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        AlexOnSpawnProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        if (entity != null) {
            if (Math.random() < 0.2D && !entity.level().isClientSide() && entity.getServer() != null) {
                entity.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getDisplayName().getString() + "> " + "Hah, a loser beneath me"), false);
            }
        }
    }

    public void baseTick() {
        super.baseTick();
        AlexOnTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (!spawnJev) {
                this.spawnJev = true;
                spawnJev();
            }
            if (jevToProtect == null && jevUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(jevUUID);
                if (entity instanceof JevEntity jev) {
                    jevToProtect = jev;
                } else {
                    jevUUID = null;
                }
            }
            if (jevToProtect != null && !jevToProtect.isAlive()) {
                if (!jevDeathMessageSent) {
                    jevDeathMessageSent = true;
                    if (level() instanceof ServerLevel serverLevel) {
                        String[] JEV_DEATH_LINES = {
                                "Jev...? Jev!? No... please no...",
                                "Not you too, Jev...",
                                "They killed him... They actually killed Jev...",
                                "I told you to stay back, Jev...",
                                "You idiot... you weren't supposed to die...",
                                "Damn it, Jev... you were all I had left...",
                                "Rest now, Jev... I'll handle this.",
                                "Heh... even in death, you're still loyal, Jev...",
                                "They’ll pay for this... I swear it, Jev"
                        };

                        String message = JEV_DEATH_LINES[level().getRandom().nextInt(JEV_DEATH_LINES.length)];
                        serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getDisplayName().getString() + "> " + message), false);
                    }
                }
                jevToProtect = null;
                jevUUID = null;
            }
        }
    }

    public static boolean canSpawn(EntityType<AlexEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (AlexData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            AlexData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 50.0D);
        builder = builder.add(Attributes.ARMOR, 20.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5.0D);
        return builder;
    }
}
