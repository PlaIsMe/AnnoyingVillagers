package com.pla.annoyingvillagers.clazz;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.ObsidianBlock;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.*;

import static com.pla.annoyingvillagers.procedures.HerobrinePortalProcedure.*;

public class HerobrineMob extends Monster {
    private boolean renderPortal = false;
    private int recallTicks = 0;
    private String chatName;
    private boolean neverRecall = false;
    private UUID gregUUID = null;
    private boolean initialSpawn = true;
    private BlockPos lastFeetPos = null;
    private EliteHerobrineKnockedEntity protectEntity;
    private UUID protectUUID;
    private int healingCooldown = 0;
    private int healingAnimationCooldown = 0;
    private boolean healing = false;
    private final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);

    private Entity firstPossessedHerobrine;
    private UUID firstPossessedHerobrineUuid;

    private Entity secondPossessedHerobrine;
    private UUID secondPossessedHerobrineUuid;

    private Entity thirdPossessedHerobrine;
    private UUID thirdPossessedHerobrineUuid;

    private Entity fourthPossessedHerobrine;
    private UUID fourthPossessedHerobrineUuid;

    public Entity getFirstPossessedHerobrine() {
        return firstPossessedHerobrine;
    }

    public Entity getSecondPossessedHerobrine() {
        return secondPossessedHerobrine;
    }

    public Entity getThirdPossessedHerobrine() {
        return thirdPossessedHerobrine;
    }

    public Entity getFourthPossessedHerobrine() {
        return fourthPossessedHerobrine;
    }

    public boolean isAvailableSlot() {
        return firstPossessedHerobrineUuid == null || secondPossessedHerobrineUuid == null || thirdPossessedHerobrineUuid == null || fourthPossessedHerobrineUuid == null;
    }

    private int getEmptyBoundClone() {
        int returnValue = 0;
        if (firstPossessedHerobrineUuid == null) returnValue = returnValue + 1;
        if (secondPossessedHerobrineUuid == null) returnValue = returnValue + 1;
        if (thirdPossessedHerobrineUuid == null) returnValue = returnValue + 1;
        if (fourthPossessedHerobrineUuid == null) returnValue = returnValue + 1;
        return returnValue;
    }

    public boolean boundPossessed(Entity entity) {
        if (firstPossessedHerobrineUuid == null) {
            firstPossessedHerobrineUuid = entity.getUUID();
            firstPossessedHerobrine = entity;
            return true;
        } else if (secondPossessedHerobrineUuid == null) {
            secondPossessedHerobrineUuid = entity.getUUID();
            secondPossessedHerobrine = entity;
            return true;
        } else if (thirdPossessedHerobrineUuid == null) {
            thirdPossessedHerobrineUuid = entity.getUUID();
            thirdPossessedHerobrine = entity;
            return true;
        } else if (fourthPossessedHerobrineUuid == null) {
            fourthPossessedHerobrineUuid = entity.getUUID();
            fourthPossessedHerobrine = entity;
            return true;
        } else {
            return false;
        }
    }

    public void setProtectUUID(UUID protectUUID) {
        this.protectUUID = protectUUID;
    }

    public void setProtectEntity(EliteHerobrineKnockedEntity protectEntity) {
        this.protectEntity = protectEntity;
    }

    public void setGregUUID(UUID gregUUID) {
        this.gregUUID = gregUUID;
    }

    public UUID getGregUUID() {
        return gregUUID;
    }

    public void setRecallTicks(int recallTicks) {
        this.recallTicks = recallTicks;
    }

    public int getRecallTicks() {
        return recallTicks;
    }

    public void setRenderPortal(boolean renderPortal) {
        this.renderPortal = renderPortal;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setNeverRecall(boolean neverRecall) {
        this.neverRecall = neverRecall;
    }

    public void setInitialSpawn(boolean initialSpawn) {
        this.initialSpawn = initialSpawn;
    }

    public boolean isHealing() {
        return healing;
    }

    protected HerobrineMob(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
    }

    public static class AnyFluidPathNavigation extends GroundPathNavigation {
        public AnyFluidPathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
            this.nodeEvaluator = new WalkNodeEvaluator();
            this.nodeEvaluator.setCanPassDoors(true);
            return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
        }

        @Override
        protected boolean hasValidPathType(@NotNull BlockPathTypes type) {
            if (type == BlockPathTypes.WATER
                    || type == BlockPathTypes.WATER_BORDER
                    || type == BlockPathTypes.LAVA
                    || type == BlockPathTypes.DANGER_FIRE
                    || type == BlockPathTypes.DAMAGE_FIRE) {
                return true;
            }
            return super.hasValidPathType(type);
        }

        @Override
        public boolean isStableDestination(@NotNull BlockPos blockPos) {
            return this.level.getFluidState(blockPos).getType() != Fluids.EMPTY || super.isStableDestination(blockPos);
        }
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return protectEntity != null && protectEntity.isAlive() && distanceTo(protectEntity) > (float)10.0D * 0.9F;
            }

            @Override
            public void tick() {
                if (protectEntity != null && protectEntity.isAlive()) {
                    getNavigation().moveTo(protectEntity, 2.0D);
                    getLookControl().setLookAt(protectEntity, 30.0F, 30.0F);
                    if (distanceToSqr(protectEntity) > 10.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(protectEntity, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return protectEntity != null && protectEntity.isAlive() && distanceTo(protectEntity) > 50.0D;
            }
        });
        CommonGoals.registerGoalForHostileNpc(this);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    protected void dropCustomDeathLoot(@NotNull DamageSource damagesource, int i, boolean flag) {
        super.dropCustomDeathLoot(damagesource, i, flag);
        this.spawnAtLocation(new ItemStack(Blocks.OBSIDIAN));
    }

    public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.hurt")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.death")));
    }

    public boolean causeFallDamage(float f, float f1, @NotNull DamageSource damagesource) {
        return super.causeFallDamage(f, f1, damagesource);
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (this.getPersistentData().getBoolean(NBT_RISING) || this.getPersistentData().getBoolean(NBT_SINKING) || this.healing) {
            if (!this.level().isClientSide()) {
                try {
                    this.getServer().getCommands().getDispatcher().execute(
                            "playsound epicfight:entity.hit.clash neutral @p",
                            this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    this.getServer().getCommands().getDispatcher().execute(
                            "execute at @s run particle epicfight:hit_blade ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                            this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }
            return false;
        }
        return super.hurt(damagesource, f);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        recallTicks = pCompound.getInt("RecallTicks");
        renderPortal = pCompound.getBoolean("RenderPortal");
        neverRecall = pCompound.getBoolean("NeverRecall");
        if (pCompound.contains("GregUUID")) {
            gregUUID = pCompound.getUUID("GregUUID");
        }
        initialSpawn = pCompound.getBoolean("InitialSpawn");
        if (pCompound.hasUUID("ProtectUUID")) {
            protectUUID = pCompound.getUUID("ProtectUUID");
        }
        if (pCompound.hasUUID("FirstPossessedHerobrineUuid")) {
            firstPossessedHerobrineUuid = pCompound.getUUID("FirstPossessedHerobrineUuid");
        }
        if (pCompound.hasUUID("SecondPossessedHerobrineUuid")) {
            secondPossessedHerobrineUuid = pCompound.getUUID("SecondPossessedHerobrineUuid");
        }
        if (pCompound.hasUUID("ThirdPossessedHerobrineUuid")) {
            thirdPossessedHerobrineUuid = pCompound.getUUID("ThirdPossessedHerobrineUuid");
        }
        if (pCompound.hasUUID("FourthPossessedHerobrineUuid")) {
            fourthPossessedHerobrineUuid = pCompound.getUUID("FourthPossessedHerobrineUuid");
        }
        healingCooldown = pCompound.getInt("HealingCooldown");
        healing = pCompound.getBoolean("Healing");
        healingAnimationCooldown = pCompound.getInt("HealingAnimationCooldown");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("RecallTicks", recallTicks);
        pCompound.putBoolean("RenderPortal", renderPortal);
        pCompound.putBoolean("NeverRecall", neverRecall);
        if (gregUUID != null) {
            pCompound.putUUID("GregUUID", gregUUID);
        }
        pCompound.putBoolean("InitialSpawn", initialSpawn);
        if (protectUUID != null) {
            pCompound.putUUID("ProtectUUID", protectUUID);
        }
        if (firstPossessedHerobrineUuid != null) {
            pCompound.putUUID("FirstPossessedHerobrineUuid", firstPossessedHerobrineUuid);
        }
        if (secondPossessedHerobrineUuid != null) {
            pCompound.putUUID("SecondPossessedHerobrineUuid", secondPossessedHerobrineUuid);
        }
        if (thirdPossessedHerobrineUuid != null) {
            pCompound.putUUID("ThirdPossessedHerobrineUuid", thirdPossessedHerobrineUuid);
        }
        if (fourthPossessedHerobrineUuid != null) {
            pCompound.putUUID("FourthPossessedHerobrineUuid", fourthPossessedHerobrineUuid);
        }
        pCompound.putInt("HealingCooldown", healingCooldown);
        pCompound.putBoolean("Healing", healing);
        pCompound.putInt("HealingAnimationCooldown", healingAnimationCooldown);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AnyFluidPathNavigation(this, level);
    }

    private void floatOnAnyFluid() {
        BlockPos pos = this.blockPosition();
        FluidState fluidState = this.level().getFluidState(pos);
        if (fluidState.isEmpty()) {
            return;
        }

        CollisionContext collisionContext = CollisionContext.of(this);
        Fluid typeHere = fluidState.getType();
        FluidState above = this.level().getFluidState(pos.above());

        if (collisionContext.isAbove(LiquidBlock.STABLE_SHAPE, pos, true) && above.getType() != typeHere) {
            this.setOnGround(true);

            double surfaceY = pos.getY() + fluidState.getHeight(this.level(), pos);
            double bottomY  = this.getBoundingBox().minY;
            double diff     = surfaceY - bottomY - 0.001D;

            if (diff > 0.0D) {
                Vec3 vel = this.getDeltaMovement();
                this.setDeltaMovement(vel.x, Math.max(vel.y, Math.min(0.2D, diff * 0.2D)), vel.z);
            }
        } else {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
        }

        this.fallDistance = 0.0F;
    }

    @Override
    public boolean isInWater() {
        FluidState fs = this.level().getFluidState(this.blockPosition());
        if (!fs.isEmpty() && this.canStandOnFluid(fs)) return false;
        return super.isInWater();
    }

    @Override
    public boolean canStandOnFluid(FluidState state) {
        return !state.isEmpty();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    private void placeObsidianBlockWhenInWater(Block block) {
        BlockPos feet = this.getOnPos();
        if (lastFeetPos == null) lastFeetPos = feet;
        if (!feet.equals(lastFeetPos)) {
            if (!this.level().getBlockState(lastFeetPos).is(block)) {
                FluidState fluidState = this.level().getFluidState(lastFeetPos);
                if (!fluidState.isEmpty()) {
                    int replace = fluidState.is(FluidTags.WATER) ? 1 : (fluidState.is(FluidTags.LAVA) ? 2 : 0);
                    this.level().setBlockAndUpdate(
                            lastFeetPos,
                            block
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.REPLACE_BY_LIQUID, replace)
                    );
                }
            }
            lastFeetPos = feet;
        }
    }

    private void recoverAfterHealing() {
        this.healing = false;
        this.setNoAi(false);
        this.removeAllEffects();
        this.livingentitypatch.applyStun(StunType.FALL, 0.0F);
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 2));
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400, 2));
        this.addEffect(new MobEffectInstance(MobEffects.JUMP, 2400, 2));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400, 2));
    }

    @Override
    public void tick() {
        super.tick();
        this.floatOnAnyFluid();
        this.checkInsideBlocks();
        if (!this.level().isClientSide) {
            if (this instanceof HerobrineCloneEntity || this instanceof HerobrineChrisEntity) {
                placeObsidianBlockWhenInWater(AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get());
            } else if (this instanceof ShadowHerobrineCloneEntity || this instanceof Herobrine7Entity
                    || this instanceof ArmoredHerobrineEntity || this instanceof ShadowHerobrineEntity) {
                placeObsidianBlockWhenInWater(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get());
            } else if (!(this instanceof NullEntity)) {
                placeObsidianBlockWhenInWater(AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get());
            }

            if (this.hasEffect(MobEffects.DAMAGE_BOOST) && this.hasEffect(MobEffects.MOVEMENT_SPEED) &&
            this.hasEffect(MobEffects.JUMP) && this.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
                if (new Random().nextBoolean()) {
                    try {
                        this.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:full_cowl ^ ^ ^ 0.3 1.2 0.3 0 1", this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {

                    }
                }
            }

            if (this.tickCount == 1) {
                if (this.renderPortal) {
                    AnnoyingVillagers.PACKET_HANDLER.send(
                            PacketDistributor.TRACKING_ENTITY.with(() -> this),
                            new ClientboundHerobrinePortalFx(HerobrinePortalProcedure.finalSurfacePos(this))
                    );
                    this.renderPortal = false;
                }
                if (this.initialSpawn) {
                    this.setNoAi(true);
                    final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
                    if (livingentitypatch != null && !this.level().isClientSide()) {
                        if (this instanceof ReaperHerobrineEntity || this instanceof GlaiveHerobrineEntity) {
                            livingentitypatch.playAnimationSynchronized(AVAnimations.GLOWING_AGONY_GUARD, 0.0F);
                        } else if (this instanceof AegisHerobrineEntity aegisHerobrineEntity) {
                            // For some reason the block animation can't be played inside finalize spawn
                            aegisHerobrineEntity.getPersistentData().putBoolean("init_animation", true);
                        } else if (!(this instanceof SledgehammerHerobrineEntity) && !(this instanceof SwordsmanHerobrineEntity)) {
                            livingentitypatch.playAnimationSynchronized(AVAnimations.HEROBRINE_ANIMATE, 0.0F);
                        }
                    }
                    this.initialSpawn = false;
                }
            }

            if (!neverRecall) {
                this.recallTicks = this.recallTicks - 1;
                int remaining = this.recallTicks;

                if (remaining == SHINK_TIME_START) {
                    AnnoyingVillagers.PACKET_HANDLER.send(
                            PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                            new ClientboundHerobrinePortalFx(new Vec3(this.getX(), this.getY(), this.getZ()))
                    );
                    if (this.level() instanceof ServerLevel serverLevel) {
                        try {
                            this.getServer().getCommands().getDispatcher().execute(
                                    "playsound annoyingvillagers:portal_natural neutral @a ~ ~ ~",
                                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                        HerobrinePortalProcedure.sinkIntoGround(serverLevel, this, 0.06);
                    }
                }
                if (remaining <= 0) {
                    this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal(this.getChatName() + " was sent back to the §4Herobrine Vessel Realm§r"), false);
                    this.discard();
                }
            }

            if (protectEntity == null && protectUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(protectUUID);
                if (entity instanceof EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity) {
                    protectEntity = eliteHerobrineKnockedEntity;
                } else {
                    protectEntity = null;
                }
            }
            if (protectEntity != null && !protectEntity.isAlive()) {
                protectEntity = null;
                protectUUID = null;
                this.recallTicks = 41;
            }

            if (firstPossessedHerobrine == null && firstPossessedHerobrineUuid != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(firstPossessedHerobrineUuid);
                this.firstPossessedHerobrine = entity;
            }
            if (secondPossessedHerobrine == null && secondPossessedHerobrineUuid != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(secondPossessedHerobrineUuid);
                this.secondPossessedHerobrine = entity;
            }
            if (thirdPossessedHerobrine == null && thirdPossessedHerobrineUuid != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(thirdPossessedHerobrineUuid);
                this.thirdPossessedHerobrine = entity;
            }
            if (fourthPossessedHerobrine == null && fourthPossessedHerobrineUuid != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(fourthPossessedHerobrineUuid);
                this.fourthPossessedHerobrine = entity;
            }

            if (firstPossessedHerobrine != null && !firstPossessedHerobrine.isAlive()) {
                firstPossessedHerobrine = null;
                firstPossessedHerobrineUuid = null;
                if (this.healing && this.getEmptyBoundClone() == 4) {
                    recoverAfterHealing();
                }
            }
            if (secondPossessedHerobrine != null && !secondPossessedHerobrine.isAlive()) {
                secondPossessedHerobrine = null;
                secondPossessedHerobrineUuid = null;
                if (this.healing && this.getEmptyBoundClone() == 4) {
                    recoverAfterHealing();
                }
            }
            if (thirdPossessedHerobrine != null && !thirdPossessedHerobrine.isAlive()) {
                thirdPossessedHerobrine = null;
                thirdPossessedHerobrineUuid = null;
                if (this.healing && this.getEmptyBoundClone() == 4) {
                    recoverAfterHealing();
                }
            }
            if (fourthPossessedHerobrine != null && !fourthPossessedHerobrine.isAlive()) {
                fourthPossessedHerobrine = null;
                fourthPossessedHerobrineUuid = null;
                if (this.healing && this.getEmptyBoundClone() >= 4) {
                    recoverAfterHealing();
                }
            }

            if (!this.healing && this.healingCooldown > 0) {
                this.healingCooldown = this.healingCooldown - 1;
            }
            if (this.getHealth() <= Math.max((float) AnnoyingVillagersConfig.HEROBRINE_HEALING_HEALTH_TRIGGER.get() / 100 * this.getMaxHealth(), 10.0F) && !this.healing && this.healingCooldown == 0) {
                if (this instanceof ShadowHerobrineEntity || this instanceof NullEntity ||
                        this instanceof AegisHerobrineEntity || this instanceof SwordsmanHerobrineEntity ||
                        this instanceof GlaiveHerobrineEntity || this instanceof ReaperHerobrineEntity ||
                        this instanceof SledgehammerHerobrineEntity) {
                    if (this.getEmptyBoundClone() > 0) {
                        summonClonesByAmount(this.getEmptyBoundClone());
                    }
                    this.healingAnimationCooldown = 60;
                    if (this.gregUUID != null) {
                        ServerLevel serverLevel = (ServerLevel) this.level();
                        Entity entity = serverLevel.getEntity(this.gregUUID);
                        if (entity instanceof HerobrineGregEntity herobrineGregEntity && entity.isAlive()) {
                            try {
                                herobrineGregEntity.getServer().getCommands().getDispatcher().execute(
                                        "playsound annoyingvillagers:greg_requesting_assistance voice @a ~ ~ ~",
                                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                            }
                            herobrineGregEntity.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<"
                                    + Component.translatable("entity.annoyingvillagers.herobrine_greg").getString() + "> "
                                    + Component.translatable("subtitles.herobrine_request").getString()), false);
                            herobrineGregEntity.setNoAi(true);
                        } else {
                            try {
                                this.getServer().getCommands().getDispatcher().execute(
                                        "playsound annoyingvillagers:self_requesting_assistance voice @a ~ ~ ~",
                                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                            }
                            this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + "> "
                                    + Component.translatable("subtitles.herobrine_request").getString()), false);
                        }
                    } else {
                        try {
                            this.getServer().getCommands().getDispatcher().execute(
                                    "playsound annoyingvillagers:self_requesting_assistance voice @a ~ ~ ~",
                                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                        this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + "> "
                                + Component.translatable("subtitles.herobrine_request").getString()), false);
                    }
                    if (AnnoyingVillagersConfig.HEROBRINE_HEALING_MIN_COOLDOWN.get() == AnnoyingVillagersConfig.HEROBRINE_HEALING_MAX_COOLDOWN.get()) {
                        this.healingCooldown = AnnoyingVillagersConfig.HEROBRINE_HEALING_MIN_COOLDOWN.get() * 1200;
                    } else {
                        this.healingCooldown = new Random().nextInt(AnnoyingVillagersConfig.HEROBRINE_HEALING_MIN_COOLDOWN.get() * 1200, AnnoyingVillagersConfig.HEROBRINE_HEALING_MAX_COOLDOWN.get() * 1200);
                    }
                } else {
                    if (this.getEmptyBoundClone() != 4) {
                        this.healingAnimationCooldown = 60;
                        try {
                            this.getServer().getCommands().getDispatcher().execute(
                                    "playsound annoyingvillagers:self_requesting_assistance voice @a ~ ~ ~",
                                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                        this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + "> "
                                + Component.translatable("subtitles.herobrine_request").getString()), false);
                        if (AnnoyingVillagersConfig.HEROBRINE_HEALING_MIN_COOLDOWN.get() == AnnoyingVillagersConfig.HEROBRINE_HEALING_MAX_COOLDOWN.get()) {
                            this.healingCooldown = AnnoyingVillagersConfig.HEROBRINE_HEALING_MIN_COOLDOWN.get() * 1200;
                        } else {
                            this.healingCooldown = new Random().nextInt(AnnoyingVillagersConfig.HEROBRINE_HEALING_MIN_COOLDOWN.get() * 1200, AnnoyingVillagersConfig.HEROBRINE_HEALING_MAX_COOLDOWN.get() * 1200);
                        }
                    }
                }
            }
            if (this.healingAnimationCooldown > 0) {
                this.healingAnimationCooldown = this.healingAnimationCooldown - 1;
            }
            if (this.healingAnimationCooldown == 50) {
                this.healing = true;
                this.setNoAi(true);
                if (this.gregUUID != null) {
                    ServerLevel serverLevel = (ServerLevel) this.level();
                    Entity entity = serverLevel.getEntity(this.gregUUID);
                    if (entity instanceof HerobrineGregEntity herobrineGregEntity && entity.isAlive()) {
                        if (herobrineGregEntity.getLivingentitypatch() != null) {
                            herobrineGregEntity.getLivingentitypatch().playAnimationSynchronized(AVAnimations.PORTAL_SUMMON, 0.0F);
                        }
                    }
                }
            }
            if (this.healingAnimationCooldown == 10) {
                this.healing = true;
                this.setNoAi(true);
                if (this.gregUUID != null) {
                    ServerLevel serverLevel = (ServerLevel) this.level();
                    Entity entity = serverLevel.getEntity(this.gregUUID);
                    if (entity instanceof HerobrineGregEntity herobrineGregEntity && entity.isAlive()) {
                        herobrineGregEntity.setNoAi(false);
                    }
                }
                this.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 10, 3, false, false));
                if (this.firstPossessedHerobrine != null) {
                    ((Mob) firstPossessedHerobrine).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 3, false, false));
                    this.clearHandAndDropItem(firstPossessedHerobrine);
                    if (firstPossessedHerobrine instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                        lowHerobrineCloneEntity.setSacrificing(true);
                    } else if (firstPossessedHerobrine instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                        lowShadowHerobrineCloneEntity.setSacrificing(true);
                    }
                    firstPossessedHerobrine.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(this.getX(), this.getY(), this.getZ()));
                    try {
                        firstPossessedHerobrine.getServer().getCommands().getDispatcher().execute(
                                "playsound annoyingvillagers:herobrine_understood voice @a ~ ~ ~",
                                firstPossessedHerobrine.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
                if (this.secondPossessedHerobrine != null) {
                    ((Mob) secondPossessedHerobrine).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 10, 3, false, false));
                    this.clearHandAndDropItem(secondPossessedHerobrine);
                    if (secondPossessedHerobrine instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                        lowHerobrineCloneEntity.setSacrificing(true);
                    } else if (secondPossessedHerobrine instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                        lowShadowHerobrineCloneEntity.setSacrificing(true);
                    }
                    secondPossessedHerobrine.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(this.getX(), this.getY(), this.getZ()));
                    try {
                        secondPossessedHerobrine.getServer().getCommands().getDispatcher().execute(
                                "playsound annoyingvillagers:herobrine_understood voice @a ~ ~ ~",
                                secondPossessedHerobrine.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
                if (this.thirdPossessedHerobrine != null) {
                    ((Mob) thirdPossessedHerobrine).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 10, 3, false, false));
                    this.clearHandAndDropItem(thirdPossessedHerobrine);
                    if (thirdPossessedHerobrine instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                        lowHerobrineCloneEntity.setSacrificing(true);
                    } else if (thirdPossessedHerobrine instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                        lowShadowHerobrineCloneEntity.setSacrificing(true);
                    }
                    thirdPossessedHerobrine.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(this.getX(), this.getY(), this.getZ()));
                    thirdPossessedHerobrine.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(this.getX(), this.getY(), this.getZ()));
                    try {
                        thirdPossessedHerobrine.getServer().getCommands().getDispatcher().execute(
                                "playsound annoyingvillagers:herobrine_understood voice @a ~ ~ ~",
                                thirdPossessedHerobrine.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
                if (this.fourthPossessedHerobrine != null) {
                    ((Mob) fourthPossessedHerobrine).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 10, 3, false, false));
                    this.clearHandAndDropItem(fourthPossessedHerobrine);
                    if (fourthPossessedHerobrine instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                        lowHerobrineCloneEntity.setSacrificing(true);
                    } else if (fourthPossessedHerobrine instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                        lowShadowHerobrineCloneEntity.setSacrificing(true);
                    }
                    fourthPossessedHerobrine.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(this.getX(), this.getY(), this.getZ()));
                    try {
                        fourthPossessedHerobrine.getServer().getCommands().getDispatcher().execute(
                                "playsound annoyingvillagers:herobrine_understood voice @a ~ ~ ~",
                                fourthPossessedHerobrine.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
            }
            if (this.healing && this.healingAnimationCooldown == 0) {
                if (this.getEmptyBoundClone() == 4) {
                    this.setNoAi(false);
                    return;
                }
                this.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 1, 3, false, false));
                if (this.livingentitypatch != null) {
                    this.livingentitypatch.playAnimationSynchronized(AVAnimations.HEROBRINE_HEALING, 0.0F);
                }
            }
        }
    }

    private void clearHandAndDropItem(Entity entity) {
        LevelAccessor levelaccessor1 = this.level();
        ItemEntity itementity;
        LivingEntity livingentity = (LivingEntity)entity;
        ItemStack itemstack;

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getMainHandItem();
                itementity = new ItemEntity(level, entity.getX(), entity.getY() + 1.0D, entity.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
                ((LivingEntity) entity).setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getOffhandItem();
                itementity = new ItemEntity(level, entity.getX(), entity.getY() + 1.0D, entity.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
                ((LivingEntity) entity).setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }
    }

    private void summonClonesByAmount(int amount) {
        if (!(this.level() instanceof ServerLevel server)) return;

        final float yaw = this.getYRot();
        final Vec3 forward = Vec3.directionFromRotation(0.0F, yaw).normalize();
        final Vec3 right = new Vec3(-forward.z, 0.0D, forward.x).normalize();

        final double fwdDist = Math.max(10.0D, this.getBbWidth() * 10.0D);
        final double sideDist = Math.max(10.0D, this.getBbWidth() * 10.0D);

        final double y = this.getY() + 0.01D;

        final Vec3 posFront = this.position().add(forward.scale(fwdDist));
        final Vec3 posBack  = this.position().subtract(forward.scale(fwdDist));
        final Vec3 posLeft  = this.position().subtract(right.scale(sideDist));
        final Vec3 posRight = this.position().add(right.scale(sideDist));

        switch (amount) {
            case 1 -> {
                summonLowCloneAt(server, new Vec3(posFront.x, y, posFront.z));
            }
            case 2 -> {
                summonLowCloneAt(server, new Vec3(posLeft.x,  y, posLeft.z));
                summonLowCloneAt(server, new Vec3(posRight.x, y, posRight.z));
            }
            case 3 -> {
                summonLowCloneAt(server, new Vec3(posFront.x, y, posFront.z));
                summonLowCloneAt(server, new Vec3(posLeft.x,  y, posLeft.z));
                summonLowCloneAt(server, new Vec3(posRight.x, y, posRight.z));
            }
            case 4 -> {
                summonLowCloneAt(server, new Vec3(posFront.x, y, posFront.z));
                summonLowCloneAt(server, new Vec3(posLeft.x,  y, posLeft.z));
                summonLowCloneAt(server, new Vec3(posRight.x, y, posRight.z));
                summonLowCloneAt(server, new Vec3(posBack.x, y, posBack.z));
            }
            default -> {}
        }
    }

    private ItemStack randomDamage(ItemStack itemStack) {
        int maxDamage = itemStack.getMaxDamage();
        itemStack.setDamageValue(new Random().nextInt(maxDamage / 3, maxDamage * 3 / 4));
        return itemStack;
    }

    private void equipGearForLowClone(Mob mob) {
        if (random.nextFloat() < 0.3f) {
            mob.setItemSlot(EquipmentSlot.HEAD, randomDamage(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_HELMET.get())));
        }
        if (random.nextFloat() < 0.3f) {
            mob.setItemSlot(EquipmentSlot.CHEST, randomDamage(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get())));
        }
        if (random.nextFloat() < 0.3f) {
            mob.setItemSlot(EquipmentSlot.LEGS, randomDamage(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_LEGGINGS.get())));
        }
        if (random.nextFloat() < 0.3f) {
            mob.setItemSlot(EquipmentSlot.FEET, randomDamage(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_BOOTS.get())));
        }
    }

    private void summonLowCloneAt(ServerLevel server, Vec3 pos) {
        Mob clone;
        if (this instanceof ShadowHerobrineEntity || this instanceof NullEntity) {
            clone = new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), server);
        } else {
            clone = new LowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), server);
        }
        int surfaceY = server.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(pos)).getY();
        clone.moveTo(pos.x, surfaceY, pos.z, this.getYRot(), this.getXRot());
        if (clone instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
            lowShadowHerobrineCloneEntity.setRenderPortal(true);
            lowShadowHerobrineCloneEntity.setPossessedByEntity(this);
            lowShadowHerobrineCloneEntity.setPossessedByUuid(this.getUUID());
        } else {
            LowHerobrineCloneEntity lowHerobrineCloneEntity = (LowHerobrineCloneEntity) clone;
            lowHerobrineCloneEntity.setRenderPortal(true);
            lowHerobrineCloneEntity.setPossessedByEntity(this);
            lowHerobrineCloneEntity.setPossessedByUuid(this.getUUID());
        }
        equipGearForLowClone(clone);
        server.addFreshEntity(clone);
        clone.setNoAi(true);
        try {
            clone.getServer().getCommands().getDispatcher().execute(
                    "playsound annoyingvillagers:portal_natural neutral @a ~ ~ ~",
                    clone.createCommandSourceStack().withSuppressedOutput().withPermission(4));
        } catch (CommandSyntaxException e) {
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            HerobrineMobData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (mobspawntype == MobSpawnType.NATURAL || mobspawntype == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverlevelaccessor.getLevel();
            HerobrineMobData herobrineMobData = HerobrineMobData.get(serverLevel);

            if (!herobrineMobData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            } else {
            }

            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            this.moveTo(spawnPos, this.getYRot(), this.getXRot());
            this.initialSpawn = false;
        }

        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        HerobrineOnInitialSpawnProcedure.execute(serverlevelaccessor, this, recallTicks, mobspawntype);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        HerobrineTransfromProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity, this);
    }
}
