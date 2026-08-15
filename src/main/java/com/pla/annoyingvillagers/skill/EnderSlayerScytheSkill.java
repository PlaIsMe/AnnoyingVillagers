package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class EnderSlayerScytheSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f79be742-fddd-454d-bd28-4d030613b284");
    public static final String DRAGON_UUID_TAG = "DragonUUID";
    private static final int SUMMON_RISE_DURATION_TICKS = 120;
    private static final double SUMMON_UNDERGROUND_DISTANCE = 5.0D;
    private static final double SUMMON_RISE_DISTANCE = 15.0D;
    private static final int SUMMON_PENDING_TIMEOUT_TICKS = 60;

    public EnderSlayerScytheSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public static void discardSummonedDragon(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            discardSummonedDragon(player, serverLevel);
        } else {
            player.getPersistentData().remove(DRAGON_UUID_TAG);
        }
    }

    private static void discardSummonedDragon(Player player, ServerLevel serverLevel) {
        CompoundTag data = player.getPersistentData();
        if (!data.hasUUID(DRAGON_UUID_TAG)) return;

        UUID dragonId = data.getUUID(DRAGON_UUID_TAG);
        Entity entity = findTrackedDragonEntity(player, serverLevel, dragonId);
        data.remove(DRAGON_UUID_TAG);

        if (entity instanceof HerobrineDragonEntity dragon && !dragon.isRemoved()) {
            dragon.ejectPassengers();
            dragon.discard();
        }
    }

    private static Entity findTrackedDragonEntity(Player player, ServerLevel currentLevel, UUID dragonId) {
        Entity entity = currentLevel.getEntity(dragonId);
        if (entity != null) return entity;

        MinecraftServer server = player.getServer();
        if (server == null) return null;

        for (ServerLevel level : server.getAllLevels()) {
            if (level == currentLevel) continue;

            entity = level.getEntity(dragonId);
            if (entity != null) return entity;
        }

        return null;
    }

    public static void deactivateFor(Player player) {
        if (player.level().isClientSide()) return;

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;

        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_SLAYER_SCYTHE);
        if (skillContainer != null
                && skillContainer.isActivated()
                && skillContainer.getSkill() instanceof EnderSlayerScytheSkill enderSlayerScytheSkill) {
            enderSlayerScytheSkill.cancelOnServer(skillContainer, null);
        }
    }

    public static void activateFromInnateAnimation(PlayerPatch<?> playerPatch) {
        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;

        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_SLAYER_SCYTHE);
        if (skillContainer != null && skillContainer.getSkill() instanceof EnderSlayerScytheSkill enderSlayerScytheSkill) {
            enderSlayerScytheSkill.activateFromInnateEvent(skillContainer);
        }
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (skillContainer.isActivated()) return;
        if (isSummonPending(skillContainer)) return;

        Player player = skillContainer.getExecutor().getOriginal();

        setSummonPending(skillContainer, true);
        skillContainer.getExecutor().playAnimationSynchronized(AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_INNATE, 0.0F);
        player.playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(), 0.5F, 1.0F);

        new DelayedTask(SUMMON_PENDING_TIMEOUT_TICKS) {
            @Override
            public void run() {
                if (!skillContainer.isActivated()) {
                    setSummonPending(skillContainer, false);
                }
            }
        };
    }

    private void activateFromInnateEvent(SkillContainer skillContainer) {
        if (skillContainer.isActivated()) return;
        if (!isSummonPending(skillContainer)) return;

        Player player = skillContainer.getExecutor().getOriginal();
        setSummonPending(skillContainer, false);
        if (!(player.level() instanceof ServerLevel serverLevel)) return;
        if (!(player.getMainHandItem().getItem() instanceof EnderSlayerScytheItem)) return;

        discardSummonedDragon(player, serverLevel);
        HerobrineDragonEntity herobrineDragonEntity = spawnEnderDragon(player, serverLevel);
        if (herobrineDragonEntity == null) return;

        player.getPersistentData().putUUID(DRAGON_UUID_TAG, herobrineDragonEntity.getUUID());
        super.executeOnServer(skillContainer, null);
        this.setDurationSynchronize(skillContainer, this.maxDuration);
        skillContainer.activate();
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;
                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    ItemStack itemStack = event.getPlayerPatch().getOriginal().getMainHandItem();
                    ServerPlayerPatch serverPlayerPatch = skillContainer.getServerExecutor();
                    Player player = serverPlayerPatch.getOriginal();

                    if (skillContainer.isActivated()) {
                        event.setCanceled(true);
                        if (event.getPlayerPatch().getOriginal().getCooldowns().getCooldownPercent(itemStack.getItem(), 0) == 0
                                && itemStack.getItem() instanceof EnderSlayerScytheItem && player.level() instanceof ServerLevel serverLevel
                                && player.getPersistentData().hasUUID(DRAGON_UUID_TAG)) {
                            UUID dragonId = player.getPersistentData().getUUID(DRAGON_UUID_TAG);
                            Entity entity = serverLevel.getEntity(dragonId);

                            if (entity == null) {
                                player.getPersistentData().remove(DRAGON_UUID_TAG);
                                return;
                            }

                            LivingEntity target = player.getLastHurtMob();
                            if (target == null || !target.isAlive() || target == player) {
                                target = player.getLastHurtByMob();
                            }
                            if (target == null || !target.isAlive() || target == player) {
                                target = HerobrineDragonEntity.getNearestLivingEntity(player.level(), player, 48.0D);
                            }
                            if (entity instanceof HerobrineDragonEntity herobrineDragonEntity && target != null && target.isAlive()) {
                                skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F);
                                LivingEntity finalTarget = target;
                                new DelayedTask(10) {
                                    @Override
                                    public void run() {
                                        herobrineDragonEntity.shootThunderBreathAtTarget(finalTarget);
                                        ItemCooldowns cooldowns = event.getPlayerPatch().getOriginal().getCooldowns();
                                        cooldowns.addCooldown(itemStack.getItem(), 120);
                                    }
                                };
                            }
                        }
                    } else if (!skillContainer.isActivated() && player.isPassenger()
                            && player.getVehicle() != null
                            && player.getVehicle() instanceof HerobrineDragonEntity) {
                        event.setCanceled(true);
                        skillContainer.getExecutor().playAnimationSynchronized(Animations.SPEAR_MOUNT_ATTACK, 0.0F);
                    }
                }
        );

        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID, (event) -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;
                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    ItemStack itemStack = event.getPlayerPatch().getOriginal().getMainHandItem();
                    ServerPlayerPatch serverPlayerPatch = skillContainer.getServerExecutor();
                    Player player = serverPlayerPatch.getOriginal();
                    Skill skill = event.getSkillContainer().getSkill();

                    if (skillContainer.isActivated()
                            && itemStack.getTag() != null && skill.getCategory() == SkillCategories.GUARD) {
                        event.setCanceled(true);
                        if (event.getPlayerPatch().getOriginal().getCooldowns().getCooldownPercent(itemStack.getItem(), 0) == 0
                                && itemStack.getItem() instanceof EnderSlayerScytheItem && player.level() instanceof ServerLevel serverLevel
                                && player.getPersistentData().hasUUID(DRAGON_UUID_TAG)) {
                            UUID dragonId = player.getPersistentData().getUUID(DRAGON_UUID_TAG);
                            Entity entity = serverLevel.getEntity(dragonId);

                            if (entity == null) {
                                player.getPersistentData().remove(DRAGON_UUID_TAG);
                                return;
                            }

                            LivingEntity target = player.getLastHurtMob();
                            if (target == null || !target.isAlive() || target == player) {
                                target = player.getLastHurtByMob();
                            }
                            if (target == null || !target.isAlive() || target == player) {
                                target = HerobrineDragonEntity.getNearestLivingEntity(player.level(), player, 40.0D);
                            }
                            ItemCooldowns cooldowns = event.getPlayerPatch().getOriginal().getCooldowns();
                            cooldowns.addCooldown(itemStack.getItem(), 20);
                            if (entity instanceof HerobrineDragonEntity herobrineDragonEntity && target != null && target.isAlive()) {
                                skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_UP, 0.0F);
                                LivingEntity finalTarget = target;
                                new DelayedTask(10) {
                                    @Override
                                    public void run() {
                                        herobrineDragonEntity.shootMeteoriteAtTarget(finalTarget);
                                    }
                                };
                            }
                        }
                    }
                });

        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID, (event) -> {
                    SkillContainer skillContainer = container.getExecutor().getSkill(AVSkills.ENDER_SLAYER_SCYTHE);
                    if (skillContainer == null) return;
                    EnderSlayerScytheSkill enderSlayerScytheSkill = (EnderSlayerScytheSkill) skillContainer.getSkill();
                    if (!skillContainer.isActivated() && skillContainer.getStack() < 1) {
                        float currentResource = skillContainer.getResource();
                        float neededResource = skillContainer.getNeededResource();
                        float addResource = Math.min(10f, neededResource);
                        enderSlayerScytheSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
                    } else if (skillContainer.isActivated()) {
                        enderSlayerScytheSkill.setDurationSynchronize(
                                skillContainer,
                                Math.min(skillContainer.getRemainDuration() + 80, enderSlayerScytheSkill.maxDuration)
                        );
                    }
        });
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        if (!container.getExecutor().isLogicalClient()) {
            discardSummonedDragon(container.getExecutor().getOriginal());
            setSummonPending(container, false);
        }
        container.deactivate();
        super.cancelOnServer(container, args);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        ItemStack itemstack = container.getExecutor().getOriginal().getMainHandItem();

        return EpicFightCapabilities.getItemStackCapability(itemstack).getInnateSkill(container.getExecutor(), itemstack) == this
                && (container.getExecutor().getOriginal().getVehicle() == null
                || (container.getExecutor().getOriginal().getVehicle() != null && container.getExecutor().getOriginal().getVehicle() instanceof HerobrineDragonEntity))
                && !isSummonPending(container)
                && (!this.isActivated(container) || this.activateType == ActivateType.TOGGLE);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        if (!container.getExecutor().isLogicalClient()) {
            Player player = container.getExecutor().getOriginal();
            setSummonPending(container, false);
            discardSummonedDragon(player);
        }
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (container.getExecutor().isLogicalClient()) return;

        ServerPlayerPatch serverPlayerPatch = container.getServerExecutor();
        Player player = serverPlayerPatch.getOriginal();
        boolean summonPending = isSummonPending(container);
        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        if (!container.isActivated()) {
            if (!summonPending) {
                discardSummonedDragon(player, serverLevel);
            }
            return;
        }

        if (player.tickCount % 5 != 0) return;

        CompoundTag data = player.getPersistentData();
        if (!data.hasUUID(DRAGON_UUID_TAG)) {
            this.cancelOnServer(container, null);
            return;
        }

        UUID id = data.getUUID(DRAGON_UUID_TAG);
        Entity entity = serverLevel.getEntity(id);
        if (!(entity instanceof HerobrineDragonEntity) || entity.isRemoved()) {
            data.remove(DRAGON_UUID_TAG);
            this.cancelOnServer(container, null);
        }
    }

    private HerobrineDragonEntity spawnEnderDragon(Player player, ServerLevel serverLevel) {
        if (!player.isAlive()) return null;
        HerobrineDragonEntity herobrineDragonEntity = new HerobrineDragonEntity(AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get(), serverLevel);
        Vec3 spawnPos = findSummonSpawnPos(serverLevel, player);
        herobrineDragonEntity.setPos(spawnPos);
        herobrineDragonEntity.setYRot(player.getYRot());
        herobrineDragonEntity.setYHeadRot(player.getYRot());
        herobrineDragonEntity.setYBodyRot(player.getYRot());
        herobrineDragonEntity.setXRot(-85.0F);
        herobrineDragonEntity.setSummoner(player);
        herobrineDragonEntity.setSummonerUUID(player.getUUID());
        herobrineDragonEntity.startSummonRise(
                findSummonRiseTarget(serverLevel, player, herobrineDragonEntity),
                SUMMON_RISE_DURATION_TICKS,
                player.getY()
        );
        serverLevel.addFreshEntity(herobrineDragonEntity);
        return herobrineDragonEntity;
    }

    private static Vec3 findSummonSpawnPos(ServerLevel serverLevel, Player player) {
        double y = Mth.clamp(
                player.getY() - SUMMON_UNDERGROUND_DISTANCE,
                serverLevel.getMinBuildHeight() + 2.0D,
                serverLevel.getMaxBuildHeight() - 8.0D
        );
        return new Vec3(player.getX(), y, player.getZ());
    }

    private static Vec3 findSummonRiseTarget(ServerLevel serverLevel, Player player, HerobrineDragonEntity dragon) {
        double minY = serverLevel.getMinBuildHeight() + 6.0D;
        double maxY = serverLevel.getMaxBuildHeight() - 6.0D;

        if (serverLevel.dimensionType().hasCeiling()) {
            BlockPos col = BlockPos.containing(player.getX(), 0.0D, player.getZ());
            int roofAirY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
            maxY = Math.min(maxY, roofAirY - dragon.getBbHeight() - 2.0D);
        }

        if (maxY < minY) maxY = minY;

        double desiredY = Mth.clamp(player.getY() + SUMMON_RISE_DISTANCE, minY, maxY);
        int start = Mth.floor(desiredY);
        int end = Mth.floor(Math.max(minY, player.getY() + 8.0D));

        for (int y = start; y >= end; y--) {
            if (canDragonFitAt(serverLevel, dragon, player.getX(), y, player.getZ())) {
                return new Vec3(player.getX(), y, player.getZ());
            }
        }

        return new Vec3(player.getX(), desiredY, player.getZ());
    }

    private static boolean canDragonFitAt(ServerLevel serverLevel, HerobrineDragonEntity dragon, double x, double y, double z) {
        AABB movedBox = dragon.getBoundingBox().move(
                x - dragon.getX(),
                y - dragon.getY(),
                z - dragon.getZ()
        );
        return serverLevel.noCollision(dragon, movedBox) && !serverLevel.containsAnyLiquid(movedBox);
    }

    private static void setSummonPending(SkillContainer container, boolean pending) {
        container.getDataManager().setDataSync(AVSkillDataKeys.ENDER_SLAYER_SCYTHE_SUMMON_PENDING.get(), pending);
    }

    private static boolean isSummonPending(SkillContainer container) {
        return Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.ENDER_SLAYER_SCYTHE_SUMMON_PENDING.get()));
    }
}
