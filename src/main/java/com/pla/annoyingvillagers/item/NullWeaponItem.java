package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.entity.NullAxeEntity;
import com.pla.annoyingvillagers.entity.NullHoeEntity;
import com.pla.annoyingvillagers.entity.NullPickaxeEntity;
import com.pla.annoyingvillagers.entity.NullShovelEntity;
import com.pla.annoyingvillagers.entity.NullSwordEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NullWeaponItem extends Item {
    public static final String CHARGE_TAG = "NullWeaponCharge";
    private static final String RELEASE_ACTIVE_TAG = "NullWeaponReleaseActive";
    private static final String RELEASE_TICKS_LEFT_TAG = "NullWeaponReleaseTicksLeft";
    public static final float MAX_CHARGE = 100.0F;
    private static final int RELEASE_COOLDOWN_TICKS = 30 * 20;
    private static final int CHARGE_METER_STEPS = 18;
    private static final int CHARGE_COLOR = 0xA66BFF;
    private static final int CHARGE_DIM_COLOR = 0x4C435C;
    private static final int CHARGE_TEXT_COLOR = 0xE2D1FF;
    private static final int CHARGE_FULL_COLOR = 0xC28CFF;
    private static final int HAND_PARTICLE_COUNT = 6;
    private static final float HAND_PARTICLE_RADIUS = 0.18F;

    private static final List<NullWeaponEntry> WEAPONS = List.of(
            new NullWeaponEntry("NullSwordUUID", 0.0F, AnnoyingVillagersModEntities.NULL_SWORD::get, NullSwordEntity.class),
            new NullWeaponEntry("NullAxeUUID", 20.0F, AnnoyingVillagersModEntities.NULL_AXE::get, NullAxeEntity.class),
            new NullWeaponEntry("NullPickaxeUUID", 40.0F, AnnoyingVillagersModEntities.NULL_PICKAXE::get, NullPickaxeEntity.class),
            new NullWeaponEntry("NullHoeUUID", 60.0F, AnnoyingVillagersModEntities.NULL_HOE::get, NullHoeEntity.class),
            new NullWeaponEntry("NullShovelUUID", 80.0F, AnnoyingVillagersModEntities.NULL_SHOVEL::get, NullShovelEntity.class)
    );

    public NullWeaponItem() {
        super(new Properties().stacksTo(1));
    }

    public static List<String> getWeaponKeys() {
        return WEAPONS.stream().map(NullWeaponEntry::uuidKey).toList();
    }

    public static NullWeapon pickRandomSummonedWeapon(ServerLevel level, CompoundTag data, RandomSource random) {
        List<NullWeapon> candidates = new ArrayList<>();

        for (NullWeaponEntry entry : WEAPONS) {
            if (!data.hasUUID(entry.uuidKey())) {
                continue;
            }

            Entity entity = level.getEntity(data.getUUID(entry.uuidKey()));
            if (entity instanceof NullWeapon nullWeapon && nullWeapon.isAlive() && !nullWeapon.isRemoved()) {
                candidates.add(nullWeapon);
            }
        }

        return candidates.isEmpty() ? null : candidates.get(random.nextInt(candidates.size()));
    }

    public static boolean isHeldBy(Player player) {
        return player.getMainHandItem().getItem() instanceof NullWeaponItem
                || player.getOffhandItem().getItem() instanceof NullWeaponItem;
    }

    public static ItemStack getHeldStack(Player player) {
        ItemStack main = player.getMainHandItem();
        if (main.getItem() instanceof NullWeaponItem) {
            return main;
        }

        ItemStack offhand = player.getOffhandItem();
        boolean offhandIsNullWeapon = offhand.getItem() instanceof NullWeaponItem;
        return offhandIsNullWeapon ? offhand : ItemStack.EMPTY;
    }

    public static float getCharge(ItemStack stack) {
        return stack.hasTag() && stack.getTag() != null ? stack.getTag().getFloat(CHARGE_TAG) : 0.0F;
    }

    public static void addCharge(Player player, float amount) {
        ItemStack stack = getHeldStack(player);
        if (!stack.isEmpty() && !isReleaseActive(stack)) {
            addCharge(stack, amount);
        }
    }

    public static void addCharge(ItemStack stack, float amount) {
        if (!(stack.getItem() instanceof NullWeaponItem)) {
            return;
        }

        CompoundTag tag = stack.getOrCreateTag();
        tag.putFloat(CHARGE_TAG, Mth.clamp(tag.getFloat(CHARGE_TAG) + amount, 0.0F, MAX_CHARGE));
    }

    public static boolean isFullyCharged(ItemStack stack) {
        return getCharge(stack) >= MAX_CHARGE;
    }

    public static void clearReleaseState(ItemStack stack) {
        if (!(stack.getItem() instanceof NullWeaponItem) || !stack.hasTag()) {
            return;
        }

        CompoundTag tag = stack.getOrCreateTag();
        tag.remove(RELEASE_ACTIVE_TAG);
        tag.remove(RELEASE_TICKS_LEFT_TAG);
    }

    public static void clearInventoryReleaseState(Player player) {
        player.getInventory().items.forEach(NullWeaponItem::clearReleaseState);
        player.getInventory().offhand.forEach(NullWeaponItem::clearReleaseState);
    }

    public static void discardPlayerOwnedWeapons(ServerLevel level, Player player) {
        CompoundTag data = player.getPersistentData();
        for (NullWeaponEntry entry : WEAPONS) {
            discardTrackedWeapon(level, data, entry.uuidKey());
            discardNearbyOwnedWeapons(level, player, entry);
        }
    }

    public static boolean isTrackedByOwner(Player player, NullWeapon nullWeapon) {
        CompoundTag data = player.getPersistentData();
        for (NullWeaponEntry entry : WEAPONS) {
            if (entry.expectedClass().isInstance(nullWeapon)
                    && data.hasUUID(entry.uuidKey())
                    && data.getUUID(entry.uuidKey()).equals(nullWeapon.getUUID())
                    && player.getUUID().equals(nullWeapon.getPlayerUUID())) {
                return true;
            }
        }
        return false;
    }

    public static boolean tryReleaseHeldWeapons(Player player) {
        ItemStack stack = getHeldStack(player);
        if (stack.isEmpty() || isReleaseActive(stack) || !isFullyCharged(stack)) {
            return false;
        }
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        updateSummonedWeapons(serverLevel, player, stack);

        CompoundTag data = player.getPersistentData();
        boolean releasedAny = false;
        int unlockedWeapons = getUnlockedWeaponCount(stack);
        for (int i = 0; i < unlockedWeapons; i++) {
            NullWeaponEntry entry = WEAPONS.get(i);
            if (!data.hasUUID(entry.uuidKey())) {
                continue;
            }

            Entity entity = serverLevel.getEntity(data.getUUID(entry.uuidKey()));
            if (entity instanceof NullWeapon nullWeapon) {
                nullWeapon.releaseForAWhile();
                releasedAny = true;
            }
        }

        if (releasedAny) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putFloat(CHARGE_TAG, MAX_CHARGE);
            tag.putBoolean(RELEASE_ACTIVE_TAG, true);
            tag.putInt(RELEASE_TICKS_LEFT_TAG, RELEASE_COOLDOWN_TICKS);
        }

        return releasedAny;
    }

    public static void stopReleasedWeapons(Player player) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        CompoundTag data = player.getPersistentData();
        for (NullWeaponEntry entry : WEAPONS) {
            if (!data.hasUUID(entry.uuidKey())) {
                continue;
            }

            Entity entity = serverLevel.getEntity(data.getUUID(entry.uuidKey()));
            if (entity instanceof NullWeapon nullWeapon) {
                nullWeapon.setReleased(false);
            }
        }
    }

    public static void spawnHeldNullWeaponParticles(LivingEntityPatch<?> livingEntityPatch) {
        if (!(livingEntityPatch.getOriginal() instanceof Player player) || !player.level().isClientSide()) {
            return;
        }
        if (livingEntityPatch.getAnimator() == null || livingEntityPatch.getArmature() == null || Armatures.BIPED.get() == null) {
            return;
        }

        var biped = Armatures.BIPED.get();
        if (player.getMainHandItem().getItem() instanceof NullWeaponItem) {
            spawnHandCircleParticles(livingEntityPatch, biped.handR);
            spawnHandCircleParticles(livingEntityPatch, biped.toolR);
        }
        if (player.getOffhandItem().getItem() instanceof NullWeaponItem) {
            spawnHandCircleParticles(livingEntityPatch, biped.handL);
            spawnHandCircleParticles(livingEntityPatch, biped.toolL);
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return super.isFoil(stack) || isFullyCharged(stack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!(entity instanceof Player player) || !isHeldStack(player, stack)) {
            return;
        }

        boolean activeHeldStack = isActiveHeldStack(player, stack);
        if (level.isClientSide()) {
            LivingEntityPatch<?> livingEntityPatch = activeHeldStack ? EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class) : null;
            if (livingEntityPatch != null) {
                spawnHeldNullWeaponParticles(livingEntityPatch);
            }
            return;
        }

        if (level instanceof ServerLevel serverLevel) {
            if (!activeHeldStack) {
                if (isReleaseActive(stack)) {
                    ItemStack activeStack = getHeldStack(player);
                    if (!isReleaseActive(activeStack)) {
                        stopReleasedWeapons(player);
                        updateSummonedWeapons(serverLevel, player, activeStack);
                    }
                    clearReleaseState(stack);
                }
                return;
            }

            boolean releaseActive = tickRelease(serverLevel, player, stack);
            if (releaseActive || (player.tickCount >= 40 && player.tickCount % 10 == 0)) {
                updateSummonedWeapons(serverLevel, player, stack, releaseActive);
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.annoyingvillagers.null_weapon"));
        addChargeTooltip(tooltip, getCharge(stack));
    }

    private static boolean isHeldStack(Player player, ItemStack stack) {
        return player.getMainHandItem() == stack || player.getOffhandItem() == stack;
    }

    private static boolean isActiveHeldStack(Player player, ItemStack stack) {
        return getHeldStack(player) == stack;
    }

    private static void updateSummonedWeapons(ServerLevel level, Player player, ItemStack stack) {
        updateSummonedWeapons(level, player, stack, false);
    }

    private static void updateSummonedWeapons(ServerLevel level, Player player, ItemStack stack, boolean removeReleasedLockedWeapons) {
        CompoundTag data = player.getPersistentData();
        int unlockedWeapons = getUnlockedWeaponCount(stack);

        for (int i = 0; i < WEAPONS.size(); i++) {
            NullWeaponEntry entry = WEAPONS.get(i);
            if (i < unlockedWeapons) {
                ensureWeapon(level, player, data, entry);
                if (removeReleasedLockedWeapons) {
                    releaseTrackedWeapon(level, data, entry.uuidKey());
                }
                teleportWeapon(level, data, entry.uuidKey());
            } else {
                removeLockedWeapon(level, data, entry.uuidKey(), removeReleasedLockedWeapons);
            }
        }
    }

    private static boolean tickRelease(ServerLevel level, Player player, ItemStack stack) {
        if (!isReleaseActive(stack)) {
            return false;
        }

        CompoundTag tag = stack.getOrCreateTag();
        int ticksLeft = tag.contains(RELEASE_TICKS_LEFT_TAG) ? tag.getInt(RELEASE_TICKS_LEFT_TAG) : 0;
        ticksLeft = Math.max(0, ticksLeft - 1);

        tag.putFloat(CHARGE_TAG, MAX_CHARGE * ticksLeft / RELEASE_COOLDOWN_TICKS);
        if (ticksLeft <= 0) {
            finishRelease(level, player, stack);
            return false;
        }

        tag.putInt(RELEASE_TICKS_LEFT_TAG, ticksLeft);
        return true;
    }

    private static void finishRelease(ServerLevel level, Player player, ItemStack stack) {
        stack.getOrCreateTag().putFloat(CHARGE_TAG, 0.0F);
        discardLockedWeapons(level, player.getPersistentData(), stack);
        stopReleasedWeapons(player);
        clearReleaseState(stack);
    }

    private static boolean isReleaseActive(ItemStack stack) {
        return stack.hasTag() && stack.getTag() != null && stack.getTag().getBoolean(RELEASE_ACTIVE_TAG);
    }

    private static int getUnlockedWeaponCount(ItemStack stack) {
        float charge = getCharge(stack);
        int count = 0;
        for (NullWeaponEntry entry : WEAPONS) {
            if (charge >= entry.unlockCharge()) {
                count++;
            }
        }
        return count;
    }

    private static void discardLockedWeapons(ServerLevel level, CompoundTag data, ItemStack stack) {
        int unlockedWeapons = getUnlockedWeaponCount(stack);
        for (int i = unlockedWeapons; i < WEAPONS.size(); i++) {
            discardTrackedWeapon(level, data, WEAPONS.get(i).uuidKey());
        }
    }

    private static void discardNearbyOwnedWeapons(ServerLevel level, Player player, NullWeaponEntry entry) {
        List<? extends NullWeapon> ownedWeapons = level.getEntitiesOfClass(
                entry.expectedClass(),
                player.getBoundingBox().inflate(128.0D),
                weapon -> player.getUUID().equals(weapon.getPlayerUUID()) && weapon.isAlive() && !weapon.isRemoved()
        );
        ownedWeapons.forEach(Entity::discard);
    }

    private static void ensureWeapon(ServerLevel level, Player player, CompoundTag data, NullWeaponEntry entry) {
        removeTrackedEntityIfWrongType(level, data, entry.uuidKey(), entry.expectedClass());
        NullWeapon ownedWeapon = getSingleOwnedWeapon(level, player, data, entry);
        if (ownedWeapon != null) {
            data.putUUID(entry.uuidKey(), ownedWeapon.getUUID());
            return;
        }
        if (data.hasUUID(entry.uuidKey())) {
            return;
        }

        NullWeapon nullWeapon = entry.type().get().create(level);
        if (nullWeapon != null) {
            nullWeapon.summonNullWeaponForPlayer(entry.uuidKey(), level, player);
        }
    }

    private static void removeLockedWeapon(ServerLevel level, CompoundTag data, String uuidKey, boolean removeReleasedWeapon) {
        if (!data.hasUUID(uuidKey)) {
            return;
        }

        Entity entity = level.getEntity(data.getUUID(uuidKey));
        if (entity instanceof NullWeapon nullWeapon && (removeReleasedWeapon || !nullWeapon.isReleased())) {
            nullWeapon.remove(Entity.RemovalReason.KILLED);
        } else if (entity == null) {
            data.remove(uuidKey);
        }
    }

    private static void releaseTrackedWeapon(ServerLevel level, CompoundTag data, String uuidKey) {
        if (!data.hasUUID(uuidKey)) {
            return;
        }

        Entity entity = level.getEntity(data.getUUID(uuidKey));
        if (entity instanceof NullWeapon nullWeapon && !nullWeapon.isReleased()) {
            nullWeapon.releaseForAWhile();
        }
    }

    private static void discardTrackedWeapon(ServerLevel level, CompoundTag data, String uuidKey) {
        if (!data.hasUUID(uuidKey)) {
            return;
        }

        Entity entity = level.getEntity(data.getUUID(uuidKey));
        if (entity != null) {
            entity.discard();
        }
        data.remove(uuidKey);
    }

    private static void removeTrackedEntityIfWrongType(ServerLevel level, CompoundTag data, String uuidKey, Class<? extends Entity> expectedClass) {
        if (!data.hasUUID(uuidKey)) {
            return;
        }

        Entity trackedEntity = level.getEntity(data.getUUID(uuidKey));
        if (trackedEntity == null || !expectedClass.isInstance(trackedEntity) || !trackedEntity.isAlive()) {
            data.remove(uuidKey);
        }
    }

    private static NullWeapon getSingleOwnedWeapon(ServerLevel level, Player player, CompoundTag data, NullWeaponEntry entry) {
        NullWeapon trackedWeapon = null;
        if (data.hasUUID(entry.uuidKey())) {
            Entity trackedEntity = level.getEntity(data.getUUID(entry.uuidKey()));
            if (entry.expectedClass().isInstance(trackedEntity)) {
                NullWeapon candidate = entry.expectedClass().cast(trackedEntity);
                if (player.getUUID().equals(candidate.getPlayerUUID()) && candidate.isAlive() && !candidate.isRemoved()) {
                    trackedWeapon = candidate;
                }
            }
        }

        List<? extends NullWeapon> ownedWeapons = level.getEntitiesOfClass(
                entry.expectedClass(),
                player.getBoundingBox().inflate(128.0D),
                weapon -> player.getUUID().equals(weapon.getPlayerUUID()) && weapon.isAlive() && !weapon.isRemoved()
        );
        NullWeapon keptWeapon = trackedWeapon != null ? trackedWeapon : (ownedWeapons.isEmpty() ? null : ownedWeapons.get(0));
        for (NullWeapon ownedWeapon : ownedWeapons) {
            if (ownedWeapon != keptWeapon) {
                ownedWeapon.discard();
            }
        }

        return keptWeapon;
    }

    private static void teleportWeapon(ServerLevel level, CompoundTag data, String uuidKey) {
        if (!data.hasUUID(uuidKey)) {
            return;
        }

        Entity entity = level.getEntity(data.getUUID(uuidKey));
        if (entity instanceof NullWeapon nullWeapon) {
            nullWeapon.processTeleportByPlayer();
        }
    }

    private static void addChargeTooltip(List<Component> tooltip, float charge) {
        int displayCharge = Mth.clamp(Mth.floor(charge), 0, (int) MAX_CHARGE);
        tooltip.add(
                Component.literal("Null Charge")
                        .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(CHARGE_COLOR)))
        );
        tooltip.add(
                Component.literal(displayCharge + " / " + (int) MAX_CHARGE)
                        .withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_TEXT_COLOR)))
        );
        tooltip.add(buildChargeMeter(charge));

        if (charge >= MAX_CHARGE) {
            tooltip.add(
                    Component.literal("Fully Charged")
                            .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(CHARGE_FULL_COLOR)))
            );
        }
    }

    private static Component buildChargeMeter(float charge) {
        int filledSteps = Math.round((charge / MAX_CHARGE) * CHARGE_METER_STEPS);
        filledSteps = Mth.clamp(filledSteps, 0, CHARGE_METER_STEPS);

        MutableComponent meter = Component.empty();
        meter.append(
                Component.literal("[")
                        .withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_DIM_COLOR)))
        );

        for (int i = 0; i < CHARGE_METER_STEPS; i++) {
            boolean filled = i < filledSteps;
            meter.append(
                    Component.literal(filled ? "|" : ".")
                            .withStyle(style -> style.withColor(TextColor.fromRgb(filled ? CHARGE_COLOR : CHARGE_DIM_COLOR)))
            );
        }

        meter.append(
                Component.literal("]")
                        .withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_DIM_COLOR)))
        );
        return meter;
    }

    private static void spawnHandCircleParticles(LivingEntityPatch<?> livingEntityPatch, Joint joint) {
        if (joint == null) {
            return;
        }

        Player player = (Player) livingEntityPatch.getOriginal();
        OpenMatrix4f transform = livingEntityPatch.getArmature().getBoundTransformFor(
                livingEntityPatch.getAnimator().getPose(1.0F),
                joint
        );
        OpenMatrix4f.mul(
                (new OpenMatrix4f()).rotate(
                        -((float) Math.toRadians(player.yBodyRotO + 180.0F)),
                        new Vec3f(0.0F, 1.0F, 0.0F)
                ),
                transform,
                transform
        );

        double centerX = (double) transform.m30 + player.getX();
        double centerY = (double) transform.m31 + player.getY();
        double centerZ = (double) transform.m32 + player.getZ();
        double tickAngle = player.tickCount * 0.35D;

        for (int i = 0; i < HAND_PARTICLE_COUNT; i++) {
            double angle = tickAngle + (Math.PI * 2.0D * i / HAND_PARTICLE_COUNT);
            double offsetX = Math.cos(angle) * HAND_PARTICLE_RADIUS;
            double offsetY = Math.sin(angle) * HAND_PARTICLE_RADIUS;

            player.level().addParticle(
                    AnnoyingVillagersModParticleTypes.NULL.get(),
                    centerX + offsetX,
                    centerY + offsetY,
                    centerZ,
                    -offsetX * 0.02D,
                    -offsetY * 0.02D,
                    0.0D
            );
        }
    }

    private record NullWeaponEntry(
            String uuidKey,
            float unlockCharge,
            Supplier<EntityType<? extends NullWeapon>> type,
            Class<? extends NullWeapon> expectedClass
    ) {}
}
