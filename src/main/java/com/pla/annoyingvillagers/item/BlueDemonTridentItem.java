package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.clazz.TridentMode;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.BlueDemonThrownTridentEntity;
import com.pla.annoyingvillagers.entity.ElectricAreaEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import com.pla.annoyingvillagers.rig.RigDualWieldGroup;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class BlueDemonTridentItem extends LegacySwordItem implements RigCombatProfileProvider {
    private static final double OWNER_HALF_BOX = 50.0D;

    private static final int DAMAGE_ZONE_DURATION = 100;
    private static final float DAMAGE_ZONE_DAMAGE = 4.0F;
    private static final int DAMAGE_ZONE_INTERVAL = 10;

    private static final float RELAUNCH_SPEED = 2.5F;
    public static final String TAG_STORM_ENERGY = "BlueDemonStormEnergy";
    private static final String TAG_STORM_RESET_AT = "BlueDemonStormResetAt";
    public static final int MAX_STORM_ENERGY = 100;
    private static final int VANILLA_SPECIAL_COOLDOWN_TICKS = 20 * 60;
    private static final int VANILLA_FESTIVAL_COOLDOWN_TICKS = 20 * 60 * 5;
    private static final String VANILLA_ABILITY_COOLDOWN_TAG = "AVBlueDemonAbilityCooldown";
    private static final int VANILLA_TRIDENT_MIN_CHARGE_TICKS = 10;
    private static final float VANILLA_TRIDENT_THROW_SPEED = 2.5F;
    private static final float VANILLA_TRIDENT_INACCURACY = 1.0F;

    private static final int ENERGY_METER_STEPS = 18;
    private static final int ENERGY_COLOR = 0x55F7FF;
    private static final int ENERGY_DIM_COLOR = 0x3E4A4D;
    private static final int ENERGY_TEXT_COLOR = 0xBDFBFF;
    private static final int ENERGY_FULL_COLOR = 0x7CFFFF;

    private static final int FESTIVAL_GATHER_SIZE = 10;
    private static final int FESTIVAL_GATHER_MAX_Y_DIFF = 6;
    private static final double FESTIVAL_GATHER_MIN_OWNER_DISTANCE_SQR  = 2.25D;

    public BlueDemonTridentItem() {
        super(new LegacyTier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 3.5F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return null;
            }
        }, 3, -2.7F, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
    }

    public static Vec3 getTridentThrowDirection(LivingEntity livingEntity, Vec3 startPos) {
        if (livingEntity instanceof Player) {
            return livingEntity.getViewVector(1.0F).normalize();
        }

        if (livingEntity instanceof Mob mob) {
            LivingEntity target = mob.getTarget();
            if (target != null && target.isAlive()) {
                Vec3 targetPos = target.position().add(0.0D, target.getBbHeight() * 0.7D, 0.0D);
                Vec3 dir = targetPos.subtract(startPos);
                if (dir.lengthSqr() > 1.0E-7) {
                    return dir.normalize();
                }
            }
        }

        Vec3 fallback = livingEntity.getViewVector(1.0F);
        return fallback.lengthSqr() > 1.0E-7 ? fallback.normalize() : null;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return super.isFoil(stack) || isFullyCharged(stack);
    }

    public static boolean isBlueDemonTrident(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof BlueDemonTridentItem;
    }

    public static int getOnlyStormEnergy(ItemStack stack) {
        if (!isBlueDemonTrident(stack)) {
            return 0;
        }

        CompoundTag tag = LegacyItemData.get(stack);
        return tag == null ? 0 : Mth.clamp(tag.getIntOr(TAG_STORM_ENERGY, 0), 0, MAX_STORM_ENERGY);
    }

    public static boolean checkOnlyFullyCharged(ItemStack stack) {
        return getOnlyStormEnergy(stack) >= MAX_STORM_ENERGY;
    }

    public static int getStormEnergy(ItemStack stack) {
        if (!isBlueDemonTrident(stack)) {
            return 0;
        }

        CompoundTag tag = LegacyItemData.get(stack);
        if (tag == null) {
            return 0;
        }
        return Mth.clamp(tag.getIntOr(TAG_STORM_ENERGY, 0), 0, MAX_STORM_ENERGY);
    }

    public static void setStormEnergy(ItemStack stack, int value) {
        if (!isBlueDemonTrident(stack)) {
            return;
        }

        int energy = Mth.clamp(value, 0, MAX_STORM_ENERGY);
        LegacyItemData.update(stack, tag -> tag.putInt(TAG_STORM_ENERGY, energy));
    }

    public static boolean isFullyCharged(ItemStack stack) {
        return getStormEnergy(stack) >= MAX_STORM_ENERGY;
    }

    public static void addStormEnergy(ItemStack stack, int amount) {
        if (!isBlueDemonTrident(stack) || amount <= 0) {
            return;
        }

        int current = getStormEnergy(stack);
        int added = Math.min(amount, MAX_STORM_ENERGY - current);

        if (added > 0) {
            setStormEnergy(stack, current + added);
        }

    }

    public static void spawnDamageZones(ServerLevel serverLevel, LivingEntity owner) {
        ElectricAreaEntity ownerZone = new ElectricAreaEntity(
                serverLevel,
                owner,
                owner.position(),
                2.5D,
                DAMAGE_ZONE_DURATION,
                DAMAGE_ZONE_DAMAGE,
                DAMAGE_ZONE_INTERVAL
        );
        serverLevel.addFreshEntity(ownerZone);

        for (BlueDemonThrownTridentEntity trident : getGroundedOwnerTridents(serverLevel, owner)) {
            ElectricAreaEntity tridentZone = new ElectricAreaEntity(
                    serverLevel,
                    owner,
                    trident.position(),
                    1.5D,
                    DAMAGE_ZONE_DURATION,
                    DAMAGE_ZONE_DAMAGE,
                    DAMAGE_ZONE_INTERVAL
            );
            serverLevel.addFreshEntity(tridentZone);
        }
    }

    public static void relaunchGroundedTridents(ServerLevel serverLevel, LivingEntity owner) {
        relaunchGroundedTridents(serverLevel, owner, false);
    }

    public static void relaunchGroundedTridents(ServerLevel serverLevel, LivingEntity owner, boolean skipSummoned) {
        List<BlueDemonThrownTridentEntity> tridents;
        if (!skipSummoned) {
            tridents = getGroundedOwnerTridents(serverLevel, owner);
        } else {
            tridents = getGroundedOwnerTridentsSkipSummoned(serverLevel, owner);
        }
        if (tridents.isEmpty()) {
            return;
        }

        List<LivingEntity> targets = getNearbyTargets(serverLevel, owner);

        for (int i = 0; i < tridents.size(); i++) {
            BlueDemonThrownTridentEntity trident = tridents.get(i);

            LivingEntity target = targets.isEmpty() ? null : targets.get(i % targets.size());
            Vec3 fallback = target == null
                    ? BlueDemonTridentItem.getTridentThrowDirection(owner, trident.position())
                    : null;

            int extraDelay = 2 + i * 2 + serverLevel.getRandom().nextInt(3);
            trident.beginAnimatedRelaunch(target, fallback, RELAUNCH_SPEED, 0.0F, extraDelay);
        }
    }

    public static void summonLightningAtGroundedTridents(ServerLevel serverLevel, LivingEntity owner) {
        for (BlueDemonThrownTridentEntity trident : getAllOwnerTridents(serverLevel, owner)) {
            trident.summonLightningAtSelf();
        }
    }

    private static void spawnMissingFestivalSupportTridents(
            ServerLevel serverLevel,
            LivingEntity owner,
            int missingCount,
            List<BlueDemonThrownTridentEntity> occupiedTridents,
            boolean strikeWhenFinished
    ) {
        if (missingCount <= 0) {
            return;
        }

        List<BlockPos> freePositions = getFreeGatherStandPositions(serverLevel, owner, occupiedTridents);
        int spawnCount = Math.min(missingCount, freePositions.size());

        for (int i = 0; i < spawnCount; i++) {
            BlueDemonThrownTridentEntity spawned = spawnFestivalSupportTrident(
                    serverLevel,
                    owner,
                    freePositions.get(i),
                    strikeWhenFinished
            );
            spawned.setSummonedGroundTridentFestival(true);
            occupiedTridents.add(spawned);
        }
    }

    private static double horizontalDistanceToOwnerSqr(BlockPos pos, LivingEntity owner) {
        double dx = (pos.getX() + 0.5D) - owner.getX();
        double dz = (pos.getZ() + 0.5D) - owner.getZ();
        return dx * dx + dz * dz;
    }

    private static List<BlockPos> buildRandomGatherStandPositions(ServerLevel serverLevel, LivingEntity owner) {
        List<BlockPos> result = new ArrayList<>();

        int half = FESTIVAL_GATHER_SIZE / 2;
        int startX = Mth.floor(owner.getX()) - half;
        int startZ = Mth.floor(owner.getZ()) - half;
        int ownerY = Mth.floor(owner.getY());

        for (int x = 0; x < FESTIVAL_GATHER_SIZE; x++) {
            for (int z = 0; z < FESTIVAL_GATHER_SIZE; z++) {
                double sampleX = startX + x + 0.5D;
                double sampleZ = startZ + z + 0.5D;

                BlockPos candidate = findNearestStandablePosNearY(serverLevel, sampleX, sampleZ, ownerY);
                if (candidate == null) {
                    continue;
                }

                if (Math.abs(candidate.getY() - owner.blockPosition().getY()) > FESTIVAL_GATHER_MAX_Y_DIFF) {
                    continue;
                }

                Vec3 center = new Vec3(
                        candidate.getX() + 0.5D,
                        candidate.getY() + 0.05D,
                        candidate.getZ() + 0.5D
                );

                if (center.distanceToSqr(owner.position()) < FESTIVAL_GATHER_MIN_OWNER_DISTANCE_SQR ) {
                    continue;
                }

                if (!result.contains(candidate)) {
                    result.add(candidate.immutable());
                }
            }
        }

        Collections.shuffle(result, new Random(serverLevel.getRandom().nextLong()));
        return result;
    }

    private static List<BlockPos> buildCompactGatherStandPositions(ServerLevel serverLevel, LivingEntity owner) {
        List<BlockPos> result = new ArrayList<>();

        int half = FESTIVAL_GATHER_SIZE / 2;
        int startX = Mth.floor(owner.getX()) - half;
        int startZ = Mth.floor(owner.getZ()) - half;
        int ownerY = Mth.floor(owner.getY());

        for (int x = 0; x < FESTIVAL_GATHER_SIZE; x++) {
            for (int z = 0; z < FESTIVAL_GATHER_SIZE; z++) {
                double sampleX = startX + x + 0.5D;
                double sampleZ = startZ + z + 0.5D;

                BlockPos standPos = findNearestStandablePosNearY(serverLevel, sampleX, sampleZ, ownerY);
                if (standPos == null) {
                    continue;
                }

                if (Math.abs(standPos.getY() - owner.blockPosition().getY()) > FESTIVAL_GATHER_MAX_Y_DIFF) {
                    continue;
                }

                if (horizontalDistanceToOwnerSqr(standPos, owner) < FESTIVAL_GATHER_MIN_OWNER_DISTANCE_SQR) {
                    continue;
                }

                if (!result.contains(standPos)) {
                    result.add(standPos.immutable());
                }
            }
        }
        result.sort(
                Comparator.<BlockPos>comparingDouble(pos -> horizontalDistanceToOwnerSqr(pos, owner))
                        .thenComparingInt(BlockPos::getY)
                        .thenComparingInt(BlockPos::getX)
                        .thenComparingInt(BlockPos::getZ)
        );

        return result;
    }

    private static List<BlockPos> getFreeGatherStandPositions(
            ServerLevel serverLevel,
            LivingEntity owner,
            List<BlueDemonThrownTridentEntity> occupiedTridents
    ) {
        List<BlockPos> result = buildRandomGatherStandPositions(serverLevel, owner);

        result.removeIf(pos -> {
            Vec3 center = new Vec3(
                    pos.getX() + 0.5D,
                    pos.getY() + 0.05D,
                    pos.getZ() + 0.5D
            );

            for (BlueDemonThrownTridentEntity other : occupiedTridents) {
                if (!other.isAlive()) {
                    continue;
                }
                if (other.position().distanceToSqr(center) < 0.25D) {
                    return true;
                }
            }

            return false;
        });

        return result;
    }

    public static void gatherGroundedTridentsAroundOwner(ServerLevel serverLevel, LivingEntity owner) {
        List<BlueDemonThrownTridentEntity> tridents = new ArrayList<>(getGroundedOwnerTridents(serverLevel, owner));
        if (tridents.isEmpty()) {
            return;
        }

        List<BlockPos> standPositions = buildRandomGatherStandPositions(serverLevel, owner);
        if (standPositions.isEmpty()) {
            return;
        }

        tridents.sort(
                Comparator.comparingLong(BlueDemonThrownTridentEntity::getSpawnSequence)
                        .thenComparing(BlueDemonThrownTridentEntity::getUUID)
        );

        int count = Math.min(tridents.size(), standPositions.size());
        for (int i = 0; i < count; i++) {
            tridents.get(i).placeAsGroundedSupport(owner, standPositions.get(i));
        }
    }

    @Nullable
    private static BlockPos findNearestStandablePosNearY(
            ServerLevel serverLevel,
            double x,
            double z,
            int originY
    ) {
        int blockX = Mth.floor(x);
        int blockZ = Mth.floor(z);

        for (int offset = 0; offset <= 8; offset++) {
            BlockPos downPos = new BlockPos(blockX, originY - offset, blockZ);
            if (isValidFestivalStandPos(serverLevel, downPos)) {
                return downPos;
            }

            if (offset != 0) {
                BlockPos upPos = new BlockPos(blockX, originY + offset, blockZ);
                if (isValidFestivalStandPos(serverLevel, upPos)) {
                    return upPos;
                }
            }
        }

        return null;
    }

    private static boolean isValidFestivalStandPos(ServerLevel serverLevel, BlockPos standPos) {
        if (!serverLevel.isInWorldBounds(standPos) || !serverLevel.isInWorldBounds(standPos.below())) {
            return false;
        }

        if (!serverLevel.isEmptyBlock(standPos)) {
            return false;
        }

        if (!serverLevel.getFluidState(standPos).isEmpty()) {
            return false;
        }

        if (!serverLevel.getFluidState(standPos.below()).isEmpty()) {
            return false;
        }

        return serverLevel.getBlockState(standPos.below()).blocksMotion();
    }

    private static BlueDemonThrownTridentEntity spawnFestivalSupportTrident(
            ServerLevel serverLevel,
            LivingEntity owner,
            BlockPos standPos,
            boolean strikeWhenFinished
    ) {
        ItemStack stack = owner.getMainHandItem();

        BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, owner, stack);
        trident.assignSpawnSequence(owner);
        trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
        trident.beginFestivalGroundRise(owner, standPos, strikeWhenFinished);

        serverLevel.addFreshEntity(trident);

        serverLevel.sendParticles(
                AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                standPos.getX() + 0.5D,
                standPos.getY() + 0.15D,
                standPos.getZ() + 0.5D,
                12,
                0.18D, 0.25D, 0.18D,
                0.02D
        );

        serverLevel.playSound(
                null,
                BlockPos.containing(standPos.getX() + 0.5D, standPos.getY(), standPos.getZ() + 0.5D),
                AnnoyingVillagersModSounds.ELECTRIFY.get(),
                SoundSource.NEUTRAL,
                0.8F,
                0.9F + serverLevel.getRandom().nextFloat() * 0.2F
        );

        return trident;
    }

    public static void summonMissingTridentAndAnimate(ServerLevel serverLevel, LivingEntity owner) {
        gatherGroundedTridentsAroundOwner(serverLevel, owner);
        List<BlueDemonThrownTridentEntity> existingTridents = new ArrayList<>(getAllOwnerTridents(serverLevel, owner));
        if (existingTridents.size() >= 20) return;
        spawnMissingFestivalSupportTridents(serverLevel, owner, 20 - existingTridents.size(), existingTridents, false);
    }

    public static void summonSuperLightningAtGroundedTridents(ServerLevel serverLevel, LivingEntity owner) {
        for (BlueDemonThrownTridentEntity trident : getGroundedOwnerTridents(serverLevel, owner)) {
            trident.summonSuperLightningAtSelf();

            if (trident.isSummonedGroundTridentFestival()) {
                trident.finishSummonedGroundTridentFestival();
            }
        }
    }

    public static List<BlueDemonThrownTridentEntity> getAllOwnerTridents(ServerLevel serverLevel, LivingEntity owner) {
        return serverLevel.getEntitiesOfClass(
                BlueDemonThrownTridentEntity.class,
                makeOwnerBox(owner),
                trident -> trident.isAlive()
                        && trident.belongsToOwner(owner)
        );
    }

    public static List<BlueDemonThrownTridentEntity> getGroundedOwnerTridents(ServerLevel serverLevel, LivingEntity owner) {
        return serverLevel.getEntitiesOfClass(
                BlueDemonThrownTridentEntity.class,
                makeOwnerBox(owner),
                trident -> trident.isAlive()
                        && trident.isGroundedTrident()
                        && trident.belongsToOwner(owner)
        );
    }

    public static List<BlueDemonThrownTridentEntity> getGroundedOwnerTridentsSkipSummoned(ServerLevel serverLevel, LivingEntity owner) {
        return serverLevel.getEntitiesOfClass(
                BlueDemonThrownTridentEntity.class,
                makeOwnerBox(owner),
                trident -> trident.isAlive()
                        && trident.isGroundedTrident()
                        && !trident.isSummonedGroundTridentFestival()
                        && trident.belongsToOwner(owner)
        );
    }

    private static List<LivingEntity> getNearbyTargets(ServerLevel serverLevel, LivingEntity owner) {
        List<LivingEntity> targets = serverLevel.getEntitiesOfClass(
                LivingEntity.class,
                makeOwnerBox(owner),
                target -> isValidTarget(owner, target)
        );

        targets.sort(Comparator.comparingDouble(target -> target.distanceToSqr(owner)));
        return targets;
    }

    private static boolean isValidTarget(LivingEntity owner, LivingEntity target) {
        if (target == owner) {
            return false;
        }

        if (!target.isAlive() || target.isSpectator()) {
            return false;
        }

        if (target instanceof Player player && player.isCreative()) {
            return false;
        }

        if (owner instanceof BlueDemonEntity blueDemonEntity
                && blueDemonEntity.getBbqEntity() != null
                && target == blueDemonEntity.getBbqEntity()) {
            return false;
        }

        return !owner.isAlliedTo(target);
    }

    private static AABB makeOwnerBox(Entity owner) {
        return new AABB(
                owner.getX() - OWNER_HALF_BOX,
                owner.level().getMinY(),
                owner.getZ() - OWNER_HALF_BOX,
                owner.getX() + OWNER_HALF_BOX,
                owner.level().getMaxY(),
                owner.getZ() + OWNER_HALF_BOX
        );
    }

    @Override
    public ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
        return ItemUseAnimation.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled()) return InteractionResult.PASS;
        boolean dualTridents = isBlueDemonTrident(player.getMainHandItem()) && isBlueDemonTrident(player.getOffhandItem());
        if (hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown() && dualTridents) {
            ItemStack offhandStack = player.getOffhandItem();
            if (offhandStack.getDamageValue() >= offhandStack.getMaxDamage() - 1) return InteractionResult.FAIL;
            player.startUsingItem(InteractionHand.OFF_HAND);
            return InteractionResult.CONSUME;
        }
        if (hand == InteractionHand.OFF_HAND && isBlueDemonTrident(player.getMainHandItem())) return InteractionResult.PASS;
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) return InteractionResult.FAIL;
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int timeLeft) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || !(livingEntity instanceof Player player)) return false;
        int chargeTicks = getUseDuration(stack, livingEntity) - timeLeft;
        if (chargeTicks < VANILLA_TRIDENT_MIN_CHARGE_TICKS || level.isClientSide() || !(level instanceof ServerLevel serverLevel)) return false;
        InteractionHand hand = player.getUsedItemHand();
        if (hand == InteractionHand.OFF_HAND && player.isShiftKeyDown() && isBlueDemonTrident(player.getMainHandItem()) && isBlueDemonTrident(player.getOffhandItem())) {
            throwVanillaTrident(serverLevel, player, stack, InteractionHand.OFF_HAND, 0.28D);
            VanillaWeaponAbilityUtil.swingOffHand(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            return true;
        }
        throwVanillaTrident(serverLevel, player, stack, hand, 0.0D);
        player.awardStat(Stats.ITEM_USED.get(this));
        return true;
    }

    private static void throwVanillaTrident(ServerLevel serverLevel, Player player, ItemStack sourceStack, InteractionHand hand, double sideOffset) {
        BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, player, sourceStack.copy());
        trident.assignSpawnSequence(player);
        trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
        double roll = serverLevel.getRandom().nextDouble();
        if (roll < 0.10D) trident.setMode(TridentMode.LIGHTNING);
        else if (roll < 0.20D) trident.setMode(TridentMode.EXPLOSION);
        else trident.setMode(TridentMode.DEFAULT);
        Vec3 look = player.getLookAngle();
        Vec3 side = new Vec3(-look.z, 0.0D, look.x);
        if (side.lengthSqr() > 1.0E-6D) side = side.normalize().scale(sideOffset);
        trident.setPos(player.getX() + side.x, player.getEyeY() - 0.1D, player.getZ() + side.z);
        trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, VANILLA_TRIDENT_THROW_SPEED, VANILLA_TRIDENT_INACCURACY);
        serverLevel.addFreshEntity(trident);
        sourceStack.hurtAndBreak(1, player, hand.asEquipmentSlot());
        serverLevel.playSound(null, trident, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static void activateVanillaElectricField(Player player) {
        if (!canUseDualVanillaSpecial(player)) return;
        BlueDemonTridentItem item = (BlueDemonTridentItem)player.getMainHandItem().getItem();
        if (!VanillaWeaponAbilityUtil.isInternalCooldownReady(player, VANILLA_ABILITY_COOLDOWN_TAG) || !(player.level() instanceof ServerLevel serverLevel)) return;
        VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_FIST_ATTACK);
        VanillaWeaponAbilityUtil.damageHeldItem(player, InteractionHand.MAIN_HAND, 1);
        VanillaWeaponAbilityUtil.damageHeldItem(player, InteractionHand.OFF_HAND, 1);
        new DelayedTask(20) { @Override public void run() { if (player.isAlive() && !player.isRemoved()) spawnDamageZones(serverLevel, player); } };
        VanillaWeaponAbilityUtil.setInternalCooldown(player, VANILLA_ABILITY_COOLDOWN_TAG, VANILLA_SPECIAL_COOLDOWN_TICKS);
        player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), VANILLA_SPECIAL_COOLDOWN_TICKS);
    }

    public static void activateVanillaThunderAttack(Player player) {
        if (!canUseDualVanillaSpecial(player)) return;
        BlueDemonTridentItem item = (BlueDemonTridentItem)player.getMainHandItem().getItem();
        if (!VanillaWeaponAbilityUtil.isInternalCooldownReady(player, VANILLA_ABILITY_COOLDOWN_TAG) || !(player.level() instanceof ServerLevel serverLevel)) return;
        VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_FIST_ATTACK);
        VanillaWeaponAbilityUtil.damageHeldItem(player, InteractionHand.MAIN_HAND, 1);
        VanillaWeaponAbilityUtil.damageHeldItem(player, InteractionHand.OFF_HAND, 1);
        new DelayedTask(20) { @Override public void run() { if (player.isAlive() && !player.isRemoved()) relaunchGroundedTridents(serverLevel, player); } };
        new DelayedTask(80) { @Override public void run() { if (player.isAlive() && !player.isRemoved()) summonLightningAtGroundedTridents(serverLevel, player); } };
        VanillaWeaponAbilityUtil.setInternalCooldown(player, VANILLA_ABILITY_COOLDOWN_TAG, VANILLA_SPECIAL_COOLDOWN_TICKS);
        player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), VANILLA_SPECIAL_COOLDOWN_TICKS);
    }

    public static boolean activateVanillaFestival(Player player) {
        if (!canUseDualVanillaSpecial(player)) return false;
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        BlueDemonTridentItem item = (BlueDemonTridentItem)mainHand.getItem();
        if (!isFullyCharged(mainHand) || !isFullyCharged(offHand) || !VanillaWeaponAbilityUtil.isInternalCooldownReady(player, VANILLA_ABILITY_COOLDOWN_TAG) || !(player.level() instanceof ServerLevel serverLevel)) return false;
        long resetAt = player.level().getGameTime() + 70L;
        markStormEnergyReset(mainHand, resetAt);
        markStormEnergyReset(offHand, resetAt);
        VanillaWeaponAbilityUtil.swingMainHand(player, VanillaWeaponAbilityUtil.BETTER_COMBAT_FIST_ATTACK);
        VanillaWeaponAbilityUtil.damageHeldItem(player, InteractionHand.MAIN_HAND, 1);
        VanillaWeaponAbilityUtil.damageHeldItem(player, InteractionHand.OFF_HAND, 1);
        new DelayedTask(6) { @Override public void run() { if (player.isAlive() && !player.isRemoved()) summonMissingTridentAndAnimate(serverLevel, player); } };
        new DelayedTask(10) { @Override public void run() { if (player.isAlive() && !player.isRemoved()) { spawnDamageZones(serverLevel, player); relaunchGroundedTridents(serverLevel, player, true); } } };
        new DelayedTask(24) { @Override public void run() { if (player.isAlive() && !player.isRemoved()) relaunchGroundedTridents(serverLevel, player, true); } };
        new DelayedTask(70) { @Override public void run() { if (!player.isAlive() || player.isRemoved()) return; summonSuperLightningAtGroundedTridents(serverLevel, player); clearPendingStormEnergy(player, resetAt); } };
        VanillaWeaponAbilityUtil.setInternalCooldown(player, VANILLA_ABILITY_COOLDOWN_TAG, VANILLA_FESTIVAL_COOLDOWN_TICKS);
        player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), VANILLA_FESTIVAL_COOLDOWN_TICKS);
        return true;
    }

    private static boolean canUseDualVanillaSpecial(Player player) {
        return VanillaWeaponAbilityUtil.abilitiesEnabled() && !player.level().isClientSide() && isBlueDemonTrident(player.getMainHandItem()) && isBlueDemonTrident(player.getOffhandItem());
    }

    public void inventoryTick(net.minecraft.world.item.ItemStack itemstack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int i = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, itemstack);
        boolean flag = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        super.inventoryTick(itemstack, level, entity, equipmentSlot);
        if (!level.isClientSide()) {
            CompoundTag tag = LegacyItemData.get(itemstack);
            if (tag != null && tag.getLongOr(TAG_STORM_RESET_AT, 0L) > 0L && level.getGameTime() >= tag.getLongOr(TAG_STORM_RESET_AT, 0L)) {
                clearStormEnergy(itemstack);
            }
        }
//        Add this in AV_EFM

//        if (flag && entity instanceof Player player && entity.level() instanceof ServerLevel serverLevel) {
//            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
//            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
//                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.TRIDENT_FESTIVAL);
//                if (skillContainer != null) {
//                    if (skillContainer.getStack() >= 1) {
//                        double d0 = entity.getX();
//                        double d1 = entity.getY();
//                        double d2 = entity.getZ();
//                        if (Math.random() <= 0.1D) {
//                            BlueDemonUtil.spawnBlueDemonEffect(serverLevel, entity);
//
//                            if (serverLevel.getRandom().nextDouble() <= 0.8D) {
//                                float volume = (float) Mth.nextDouble(serverLevel.getRandom(), 0.05D, 0.5D);
//                                float pitch = (float) Mth.nextDouble(serverLevel.getRandom(), 0.8D, 1.1D);
//
//                                serverLevel.playSound(
//                                        null,
//                                        BlockPos.containing(d0, d1, d2),
//                                        AnnoyingVillagersModSounds.ELECTRIFY.get(),
//                                        SoundSource.NEUTRAL,
//                                        volume,
//                                        pitch
//                                );
//                            }
//                        }
//                    }
//                }
//            }
//        }

//        Add VANILLA_ANIMATION cover this effect when trident is activated
    }

    private static void markStormEnergyReset(ItemStack stack, long resetAt) {
        LegacyItemData.update(stack, tag -> tag.putLong(TAG_STORM_RESET_AT, resetAt));
    }

    private static void clearPendingStormEnergy(Player player, long resetAt) {
        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) clearPendingStormEnergy(stack, resetAt);
        clearPendingStormEnergy(player.getOffhandItem(), resetAt);
    }

    private static void clearPendingStormEnergy(ItemStack stack, long resetAt) {
        if (!isBlueDemonTrident(stack)) return;
        CompoundTag tag = LegacyItemData.get(stack);
        if (tag != null && tag.getLongOr(TAG_STORM_RESET_AT, 0L) == resetAt) clearStormEnergy(stack);
    }

    private static void clearStormEnergy(ItemStack stack) {
        LegacyItemData.update(stack, tag -> {
            tag.putInt(TAG_STORM_ENERGY, 0);
            tag.remove(TAG_STORM_RESET_AT);
        });
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, display, tooltip, flag);
        int energy = getStormEnergy(stack);
        tooltip.accept(Component.translatable("tooltip.annoyingvillagers.blue_demon_trident"));
        addStormChargeTooltip(tooltip, energy);
    }

    private static void addStormChargeTooltip(java.util.function.Consumer<Component> tooltip, int energy) {
        tooltip.accept(
                Component.translatable("tooltip.annoyingvillagers.blue_demon_trident_thunder_charge")
                        .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(ENERGY_COLOR)))
        );

        tooltip.accept(
                Component.literal(energy + " / " + MAX_STORM_ENERGY)
                        .withStyle(style -> style.withColor(TextColor.fromRgb(ENERGY_TEXT_COLOR)))
        );

        tooltip.accept(buildStormMeter(energy));

        if (energy >= MAX_STORM_ENERGY) {
            tooltip.accept(
                    Component.translatable("tooltip.annoyingvillagers.thunder_charged")
                            .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(ENERGY_FULL_COLOR)))
            );
        }
    }

    private static Component buildStormMeter(int energy) {
        int filledSteps = Math.round((energy / (float) MAX_STORM_ENERGY) * ENERGY_METER_STEPS);
        filledSteps = Mth.clamp(filledSteps, 0, ENERGY_METER_STEPS);

        MutableComponent meter = Component.empty();

        meter.append(
                Component.literal("\u26A1 ")
                        .withStyle(style -> style.withColor(TextColor.fromRgb(ENERGY_COLOR)))
        );

        for (int i = 0; i < ENERGY_METER_STEPS; i++) {
            boolean filled = i < filledSteps;

            meter.append(
                    Component.literal(filled ? "\u25B0" : "\u25B1")
                            .withStyle(style -> style.withColor(TextColor.fromRgb(filled ? ENERGY_COLOR : ENERGY_DIM_COLOR)))
            );
        }

        return meter;
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.BLUE_DEMON;
    }

    @Override
    public RigDualWieldGroup getDualWieldGroup(ItemStack stack) {
        return RigDualWieldGroup.LEGENDARY_SWORD;
    }
}
