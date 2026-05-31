package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Random;

public class VillagerUtil {
    public static ItemStack generateMainWeaponItem() {
        float chance = new Random().nextFloat();
        ItemStack itemStack;
        if (chance <= 0.2F) {
            itemStack = new ItemStack(AnnoyingVillagersModItems.DIAMOND_KNIFE.get());
        } else if (chance <= 0.4F) {
            itemStack = new ItemStack(AnnoyingVillagersModItems.DIAMOND_FALCHION.get());
        } else if (chance <= 0.6F) {
            itemStack = new ItemStack(AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD.get());
        } else {
            itemStack = new ItemStack(AnnoyingVillagersModItems.DIAMOND_KNIGHT_SWORD.get());
        }

        float enchantChance = new Random().nextFloat();
        if (enchantChance <= 0.2) {
            itemStack.enchant(Enchantments.FIRE_ASPECT, new Random().nextInt(1, 3));
        }
        if (enchantChance <= 0.4) {
            itemStack.enchant(Enchantments.SWEEPING_EDGE, new Random().nextInt(1, 3));
        }
        if (enchantChance <= 0.6) {
            itemStack.enchant(Enchantments.SMITE, new Random().nextInt(1, 3));
        }
        if (enchantChance <= 0.8) {
            itemStack.enchant(Enchantments.KNOCKBACK, new Random().nextInt(1, 3));
        }
        itemStack.enchant(Enchantments.SHARPNESS, new Random().nextInt(1, 3));
        return itemStack;
    }

    private static Vec3 localCommandOffset(Vec3 origin, float yaw, double localX, double localY, double localZ) {
        Vec3 forward = Vec3.directionFromRotation(0.0F, yaw).normalize();
        Vec3 left = new Vec3(forward.z, 0.0D, -forward.x).normalize();

        return origin.add(left.scale(localX))
                .add(0.0D, localY, 0.0D)
                .add(forward.scale(localZ));
    }

    private static boolean isValidGroundSpawn(ServerLevel level, Mob probe, BlockPos feetPos, float yaw) {
        BlockPos floorPos = feetPos.below();
        BlockPos headPos = feetPos.above();

        BlockState floorState = level.getBlockState(floorPos);
        BlockState feetState = level.getBlockState(feetPos);
        BlockState headState = level.getBlockState(headPos);

        if (floorState.isAir()) return false;
        if (floorState.getCollisionShape(level, floorPos).isEmpty()) return false;
        if (!level.getFluidState(floorPos).isEmpty()) return false;

        if (!feetState.getCollisionShape(level, feetPos).isEmpty()) return false;
        if (!headState.getCollisionShape(level, headPos).isEmpty()) return false;

        if (!level.getFluidState(feetPos).isEmpty()) return false;
        if (!level.getFluidState(headPos).isEmpty()) return false;

        probe.moveTo(feetPos.getX() + 0.5D, feetPos.getY(), feetPos.getZ() + 0.5D, yaw, 0.0F);
        return level.noCollision(probe) && !level.containsAnyLiquid(probe.getBoundingBox());
    }

    private static @Nullable Vec3 findSurfaceNearDeathY(ServerLevel level, Mob probe, BlockPos columnBase, float yaw, int maxDown, int maxUp) {
        int startY = columnBase.getY();
        int minY = Math.max(level.getMinBuildHeight() + 1, startY - maxDown);
        int maxY = Math.min(level.getMaxBuildHeight() - 2, startY + maxUp);

        for (int y = startY; y >= minY; y--) {
            BlockPos feetPos = new BlockPos(columnBase.getX(), y, columnBase.getZ());
            if (isValidGroundSpawn(level, probe, feetPos, yaw)) {
                return Vec3.atBottomCenterOf(feetPos);
            }
        }

        for (int y = startY + 1; y <= maxY; y++) {
            BlockPos feetPos = new BlockPos(columnBase.getX(), y, columnBase.getZ());
            if (isValidGroundSpawn(level, probe, feetPos, yaw)) {
                return Vec3.atBottomCenterOf(feetPos);
            }
        }

        return null;
    }

    private static @Nullable Vec3 findSafeSpawnNearLocalOffset(ServerLevel level, Vec3 origin, float yaw, Mob probe, double localX, double localY, double localZ) {
        Vec3 wanted = localCommandOffset(origin, yaw, localX, localY, localZ);

        int baseX = Mth.floor(wanted.x);
        int baseY = Mth.floor(origin.y + localY);
        int baseZ = Mth.floor(wanted.z);

        for (int radius = 0; radius <= 2; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (radius != 0 && Math.abs(dx) != radius && Math.abs(dz) != radius) {
                        continue;
                    }

                    BlockPos column = new BlockPos(baseX + dx, baseY, baseZ + dz);
                    Vec3 found = findSurfaceNearDeathY(level, probe, column, yaw, 16, 4);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    public static <T extends Mob> @Nullable T spawnMobAtLocalOffset(
            ServerLevel level,
            Vec3 origin,
            float yaw,
            EntityType<T> type,
            double localX,
            double localY,
            double localZ
    ) {
        T mob = type.create(level);
        if (mob == null) {
            return null;
        }

        Vec3 spawnPos = findSafeSpawnNearLocalOffset(level, origin, yaw, mob, localX, localY, localZ);
        if (spawnPos == null) {
            mob.discard();
            return null;
        }

        mob.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, yaw, 0.0F);
        mob.finalizeSpawn(
                level,
                level.getCurrentDifficultyAt(BlockPos.containing(spawnPos)),
                MobSpawnType.MOB_SUMMONED,
                null,
                null
        );
        level.addFreshEntity(mob);
        return mob;
    }

    public static void spawnBackupFirework(ServerLevel level, Vec3 origin) {
        ItemStack rocketStack = new ItemStack(Items.FIREWORK_ROCKET);

        CompoundTag fireworksTag = rocketStack.getOrCreateTagElement("Fireworks");
        fireworksTag.putByte("Flight", (byte)1);

        CompoundTag explosion = new CompoundTag();
        explosion.putByte("Type", (byte)3); // creeper
        explosion.putIntArray("Colors", new int[] {0x000000});
        explosion.putBoolean("Flicker", true);

        ListTag explosions = new ListTag();
        explosions.add(explosion);
        fireworksTag.put("Explosions", explosions);

        FireworkRocketEntity rocket = new FireworkRocketEntity(
                level,
                origin.x,
                origin.y + 10.0D,
                origin.z,
                rocketStack
        );
        level.addFreshEntity(rocket);
    }

    public static ItemStack createBlackCreeperSignalFirework() {
        ItemStack stack = new ItemStack(Items.FIREWORK_ROCKET);
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag fireworksTag = new CompoundTag();
        fireworksTag.putByte("Flight", (byte) 1);

        ListTag explosions = new ListTag();
        CompoundTag explosion = new CompoundTag();
        explosion.putByte("Type", (byte) 3); // creeper shape
        explosion.putIntArray("Colors", new int[]{0x000000}); // black
        explosion.putBoolean("Flicker", true);
        explosions.add(explosion);

        fireworksTag.put("Explosions", explosions);
        tag.put("Fireworks", fireworksTag);

        stack.setHoverName(Component.literal("Black Creeper Firework"));
        return stack;
    }

    public static boolean isBlackCreeperSignalFirework(ItemStack stack) {
        if (stack.isEmpty() || !stack.is(Items.FIREWORK_ROCKET) || !stack.hasTag()) {
            return false;
        }

        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return false;
        }

        if (!tag.contains("Fireworks", Tag.TAG_COMPOUND)) {
            return false;
        }

        CompoundTag fireworksTag = tag.getCompound("Fireworks");
        if (!fireworksTag.contains("Explosions", Tag.TAG_LIST)) {
            return false;
        }

        ListTag explosions = fireworksTag.getList("Explosions", Tag.TAG_COMPOUND);
        if (explosions.size() != 1) {
            return false;
        }

        CompoundTag explosion = explosions.getCompound(0);
        if (explosion.getByte("Type") != 3) {
            return false;
        }

        int[] colors = explosion.getIntArray("Colors");
        return colors.length == 1 && colors[0] == 0x000000;
    }

    public static void launchBlackCreeperSignalFirework(ServerLevel serverLevel, double x, double y, double z) {
        ItemStack rocketStack = createBlackCreeperSignalFirework();
        FireworkRocketEntity rocket = new FireworkRocketEntity(serverLevel, x, y, z, rocketStack);
        serverLevel.addFreshEntity(rocket);
    }

    public static <T extends Mob> void summonSupportAt(ServerLevel serverLevel,
                                                       EntityType<T> entityType,
                                                       double baseX, double baseY, double baseZ) {
        BlockPos spawnPos = findSafeSupportSpawn(serverLevel, baseX + new Random().nextDouble(-10, 10), baseY, baseZ + new Random().nextDouble(-10, 10));
        if (spawnPos == null) {
            return;
        }

        T mob = entityType.create(serverLevel);
        if (mob == null) {
            return;
        }

        mob.moveTo(
                spawnPos.getX() + 0.5D,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5D,
                serverLevel.random.nextFloat() * 360.0F,
                0.0F
        );

        if (!serverLevel.noCollision(mob) || !serverLevel.isUnobstructed(mob)) {
            return;
        }

        mob.finalizeSpawn(
                serverLevel,
                serverLevel.getCurrentDifficultyAt(spawnPos),
                MobSpawnType.MOB_SUMMONED,
                null,
                null
        );

        serverLevel.addFreshEntity(mob);
    }

    @Nullable
    public static BlockPos findSafeSupportSpawn(ServerLevel serverLevel, double x, double y, double z) {
        int baseX = Mth.floor(x);
        int baseZ = Mth.floor(z);
        int refY = Mth.clamp(Mth.floor(y), serverLevel.getMinBuildHeight() + 1, serverLevel.getMaxBuildHeight() - 2);
        for (int radius = 0; radius <= 2; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos found = findSafeSupportSpawnInColumn(serverLevel, baseX + dx, refY, baseZ + dz);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    @Nullable
    private static BlockPos findSafeSupportSpawnInColumn(ServerLevel serverLevel, int x, int refY, int z) {
        int minY = serverLevel.getMinBuildHeight() + 1;
        int maxUpY = Math.min(serverLevel.getMaxBuildHeight() - 2, refY + 3);

        for (int y = refY; y >= minY; y--) {
            BlockPos feetPos = new BlockPos(x, y, z);
            if (isSafeSupportSpawn(serverLevel, feetPos)) {
                return feetPos;
            }
        }
        for (int y = refY + 1; y <= maxUpY; y++) {
            BlockPos feetPos = new BlockPos(x, y, z);
            if (isSafeSupportSpawn(serverLevel, feetPos)) {
                return feetPos;
            }
        }

        return null;
    }

    private static boolean isSafeSupportSpawn(ServerLevel serverLevel, BlockPos feetPos) {
        BlockPos belowPos = feetPos.below();

        return serverLevel.getBlockState(belowPos).isFaceSturdy(serverLevel, belowPos, Direction.UP)
                && serverLevel.getFluidState(feetPos).isEmpty()
                && serverLevel.getFluidState(feetPos.above()).isEmpty()
                && serverLevel.getBlockState(feetPos).canBeReplaced()
                && serverLevel.getBlockState(feetPos.above()).canBeReplaced();
    }

    public static void summonSupportAtLocalOffset(
            ServerLevel level,
            Vec3 origin,
            float yaw,
            EntityType<? extends Mob> type,
            double localX,
            double localY,
            double localZ
    ) {
        Mob mob = type.create(level);
        if (mob == null) {
            return;
        }

        Vec3 spawnPos = findSafeSpawnNearLocalOffset(level, origin, yaw, mob, localX, localY, localZ);
        if (spawnPos == null) {
            mob.discard();
            return;
        }

        mob.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, yaw, 0.0F);

        if (!level.noCollision(mob) || level.containsAnyLiquid(mob.getBoundingBox())) {
            mob.discard();
            return;
        }

        mob.finalizeSpawn(
                level,
                level.getCurrentDifficultyAt(BlockPos.containing(spawnPos)),
                MobSpawnType.MOB_SUMMONED,
                null,
                null
        );

        level.addFreshEntity(mob);
    }

    private static EntityType<? extends Mob> rollRandomVillagerReinforcementType() {
        double roll = new Random().nextDouble();

        if (roll < 0.20D) {
            return AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL.get();
        } else if (roll < 0.40D) {
            return AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL.get();
        } else if (roll < 0.60D) {
            return AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL.get();
        } else if (roll < 0.80D) {
            return AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL.get();
        } else {
            return AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get();
        }
    }

    public static void summonRandomVillagerSupportWave(ServerLevel level, Vec3 origin, float yaw) {
        Random random = new Random();

        summonSupportAtLocalOffset(level, origin, yaw,
                AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                0.0D, 0.0D, 10.0D);

        summonSupportAtLocalOffset(level, origin, yaw,
                AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                -5.0D, 0.0D, 12.0D);

        summonSupportAtLocalOffset(level, origin, yaw,
                AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                5.0D, 0.0D, 12.0D);

        if (random.nextBoolean()) {
            summonSupportAtLocalOffset(level, origin, yaw,
                    AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                    -10.0D, 0.0D, 18.0D);

            summonSupportAtLocalOffset(level, origin, yaw,
                    AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                    10.0D, 0.0D, 18.0D);
        }

        summonSupportAtLocalOffset(level, origin, yaw,
                rollRandomVillagerReinforcementType(),
                0.0D, 0.0D, 22.0D);

        if (random.nextBoolean()) {
            summonSupportAtLocalOffset(level, origin, yaw,
                    AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(),
                    10.0D, 0.0D, 24.0D);
        }

        if (random.nextBoolean()) {
            summonSupportAtLocalOffset(level, origin, yaw,
                    rollRandomVillagerReinforcementType(),
                    -10.0D, 0.0D, 24.0D);
        }
    }
}
