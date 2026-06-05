package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.entity.DragonMeteoriteEntity;
import com.pla.annoyingvillagers.entity.ObsidianSledgehammerProjectileEntity;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Random;
import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class MobPlaceBlockEvent {
    private static final double MAX_PLACE_BLOCK_GROUND_GAP = 2.0D;
    private static final int CLEAR_BLOCK_DAMAGE_DELAY = 10;
    private static final int PLACE_BLOCK_INITIAL_DELAY = 1;
    private static final int PLACE_BLOCK_LAYER_INTERVAL = 3;

    private MobPlaceBlockEvent() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingAttackEvent livingAttackEvent) {
        if (livingAttackEvent.isCanceled()) {
            return;
        }

        if (!(livingAttackEvent.getEntity() instanceof AVNpc avNpc)) {
            return;
        }

        if (!(avNpc.level() instanceof ServerLevel serverLevel)
                || !avNpc.onGround()
                || !CombatCommon.isGroundWithin(avNpc, MAX_PLACE_BLOCK_GROUND_GAP)
                || avNpc.isPassenger()) {
            return;
        }

        boolean projectileDamage = livingAttackEvent.getSource().getDirectEntity() instanceof Projectile;
        Entity blockDamage = getBlockDamageSource(livingAttackEvent);
        if (blockDamage == null
                || !isBlockDamageInFront(avNpc, blockDamage)
                || !canPlaceBlockParry(avNpc, projectileDamage)) {
            return;
        }

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(avNpc, LivingEntityPatch.class);
        if (!(patch instanceof MobPatch<?> mobPatch)) {
            return;
        }

        if (!projectileDamage) {
            avNpc.setPlaceBlockParryCooldown();
        }
        avNpc.setBlockDamage(blockDamage);
        CombatCommon.swapToBlock(mobPatch);
        int placementDelay = placeBlockWall(serverLevel, avNpc, blockDamage);
        livingAttackEvent.setCanceled(true);
        finishPlaceBlockParryLater(avNpc, placementDelay);
    }

    private static Entity getBlockDamageSource(LivingAttackEvent livingAttackEvent) {
        Entity directEntity = livingAttackEvent.getSource().getDirectEntity();
        if (directEntity instanceof Projectile) {
            return directEntity;
        }

        if (directEntity == null) {
            return null;
        }

        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(directEntity.getType());
        boolean isDamageFromGunKnight = key.getNamespace().equals("torchesbecomesunlight")
                && (key.getPath().equals("gun_knight_patriot") || key.getPath().equals("turret"));
        boolean ignisFireBall = key.getNamespace().equals("cataclysm")
                && (key.getPath().equals("ignis_abyss_fireball")
                || key.getPath().equals("ignis_fireball")
                || key.getPath().equals("flame_jet")
                || key.getPath().equals("flame_strike"));
        boolean isMeteorite = directEntity instanceof DragonMeteoriteEntity
                || livingAttackEvent.getSource().getEntity() instanceof DragonMeteoriteEntity
                || directEntity instanceof ObsidianSledgehammerProjectileEntity
                || livingAttackEvent.getSource().getEntity() instanceof ObsidianSledgehammerProjectileEntity;

        if (isDamageFromGunKnight || ignisFireBall || isMeteorite || livingAttackEvent.getSource().is(DamageTypes.EXPLOSION)) {
            return directEntity;
        }

        return null;
    }

    private static boolean canPlaceBlockParry(AVNpc avNpc, boolean projectileDamage) {
        Item currentItem = avNpc.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        boolean holdingValidWeapon = currentItem.equals(avNpc.getMainWeaponItem().getItem()) || currentItem instanceof BowItem;
        if (!holdingValidWeapon) {
            return false;
        }

        if (projectileDamage) {
            return avNpc.getBlockDamage() == null
                    && !avNpc.isHealing()
                    && new Random().nextDouble() <= avNpc.getPlaceBlockToParryChance();
        }

        return avNpc.rollsPlaceBlockToParryChance();
    }

    private static boolean isBlockDamageInFront(AVNpc avNpc, Entity blockDamage) {
        Vec3 look = horizontal(avNpc.getLookAngle());
        if (look.lengthSqr() < 1.0E-6D) {
            Direction facing = avNpc.getDirection();
            look = new Vec3(facing.getStepX(), 0.0D, facing.getStepZ());
        }

        if (look.lengthSqr() < 1.0E-6D) {
            return false;
        }

        look = look.normalize();

        boolean threatPositionInFront = false;
        Vec3 toThreat = horizontal(blockDamage.position().subtract(avNpc.position()));
        if (toThreat.lengthSqr() > 1.0E-6D) {
            threatPositionInFront = look.dot(toThreat.normalize()) > 0.15D;
        }

        Vec3 incomingFrom = horizontal(blockDamage.getDeltaMovement()).scale(-1.0D);
        boolean incomingFromFront = incomingFrom.lengthSqr() > 1.0E-6D
                && look.dot(incomingFrom.normalize()) > 0.15D;

        return threatPositionInFront || incomingFromFront;
    }

    private static Vec3 horizontal(Vec3 vector) {
        return new Vec3(vector.x, 0.0D, vector.z);
    }

    private static int placeBlockWall(ServerLevel serverLevel,
                                      AVNpc avNpc,
                                      Entity blockDamage) {
        Random random = new Random();
        int pattern = random.nextInt(11);
        int rot = random.nextInt(4);
        BiFunction<Integer, Integer, int[]> toWorld = getIntegerIntegerBiFunction(avNpc, rot);
        int lastPlacementDelay = 0;

        BlockState placeState = getPlaceState(avNpc);
        BlockPos baseXZ;
        int topY;
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(blockDamage.getType());
        if (key.getNamespace().equals("tacz")
                || (key.getNamespace().equals("torchesbecomesunlight")
                && (key.getPath().equals("gun_knight_patriot") || key.getPath().equals("turret")))) {
            Direction facing = avNpc.getDirection();
            baseXZ = avNpc.blockPosition().relative(facing, 1);
            topY = Mth.floor(avNpc.getY() + avNpc.getBbHeight());
        } else {
            baseXZ = BlockPos.containing(blockDamage.getX(), 0.0D, blockDamage.getZ());
            topY = Mth.floor(blockDamage.getY());
        }

        int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
        BlockPos projXZ = new BlockPos(baseXZ.getX(), 0, baseXZ.getZ());

        for (int y = surfaceY; y <= topY; y++) {
            int layer = y - surfaceY;
            BlockPos center = new BlockPos(projXZ.getX(), y, projXZ.getZ());
            if (!serverLevel.getBlockState(center).canBeReplaced()) {
                break;
            }

            final int[][] extrasLocal = switch (pattern) {
                case 0 -> new int[][]{};
                case 1 -> layer == 3 ? new int[][]{{1, 0}} : new int[][]{};
                case 2 -> {
                    if (layer == 0) yield new int[][]{{-1, 0}, {1, 0}, {2, 0}};
                    else if (layer == 1) yield new int[][]{{1, 0}};
                    else yield new int[][]{};
                }
                case 3 -> layer == 1 ? new int[][]{{-1, 0}, {1, 0}} : new int[][]{};
                case 4 -> layer == 0 ? new int[][]{{-1, 0}, {1, 0}} : new int[][]{};
                case 5 -> new int[][]{{1, 0}};
                case 6 -> layer <= 1 ? new int[][]{{1, 0}} : new int[][]{};
                case 7 -> layer == 0 ? new int[][]{{1, 0}} : new int[][]{};
                case 8 -> layer == 1 ? new int[][]{{1, 0}} : new int[][]{};
                case 9 -> layer == 0 ? new int[][]{{-1, 0}} : new int[][]{};
                default -> layer == 1 ? new int[][]{{-1, 0}} : new int[][]{};
            };

            final BlockPos layerCenter = center;
            int layerDelay = PLACE_BLOCK_INITIAL_DELAY + layer * PLACE_BLOCK_LAYER_INTERVAL;
            lastPlacementDelay = Math.max(lastPlacementDelay, layerDelay);
            new DelayedTask(layerDelay) {
                @Override
                public void run() {
                    if (!avNpc.isAlive() || !CombatCommon.isGroundWithin(avNpc, MAX_PLACE_BLOCK_GROUND_GAP)) {
                        return;
                    }

                    if (!placeIfReplaceable(serverLevel, layerCenter, placeState, avNpc)) {
                        return;
                    }

                    for (int[] ab : extrasLocal) {
                        int[] dzdx = toWorld.apply(ab[0], ab[1]);
                        BlockPos p = layerCenter.offset(dzdx[0], 0, dzdx[1]);
                        placeIfReplaceable(serverLevel, p, placeState, avNpc);
                    }
                }
            };
        }

        return lastPlacementDelay;
    }

    private static BlockState getPlaceState(AVNpc avNpc) {
        ItemStack handStack = avNpc.getItemInHand(InteractionHand.MAIN_HAND);
        if (handStack.getItem() instanceof BlockItem blockItem) {
            return blockItem.getBlock().defaultBlockState();
        }

        return Blocks.COBBLESTONE.defaultBlockState();
    }

    private static boolean placeIfReplaceable(ServerLevel serverLevel,
                                              BlockPos pos,
                                              BlockState placeState,
                                              AVNpc avNpc) {
        if (!serverLevel.getBlockState(pos).canBeReplaced()) {
            return false;
        }

        avNpc.swing(InteractionHand.MAIN_HAND, true);
        avNpc.playSound(SoundEvents.STONE_PLACE, 2.0F, 1.0F);
        serverLevel.setBlockAndUpdate(pos, placeState);
        return true;
    }

    private static void finishPlaceBlockParryLater(AVNpc avNpc, int placementDelay) {
        new DelayedTask(placementDelay + CLEAR_BLOCK_DAMAGE_DELAY) {
            @Override
            public void run() {
                avNpc.setBlockDamage(null);
                if (!avNpc.isAlive()) {
                    return;
                }

                LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(avNpc, LivingEntityPatch.class);
                if (!(patch instanceof MobPatch<?> mobPatch)) {
                    return;
                }

                rollAndSwapAfterPlaceBlock(mobPatch);
            }
        };
    }

    private static void rollAndSwapAfterPlaceBlock(MobPatch<?> mobPatch) {
        double chance = new Random().nextDouble();
        if (CombatCommon.canSwapToBow(mobPatch)) {
            if (chance <= 0.25D) {
                mobPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                CombatCommon.swapToBow(mobPatch);
            } else if (chance <= 0.5D) {
                mobPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                CombatCommon.swapToBow(mobPatch);
            } else if (chance <= 0.7D) {
                mobPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                CombatCommon.swapToBow(mobPatch);
            } else if (chance <= 0.8D) {
                mobPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                CombatCommon.swapToMelee(mobPatch);
            } else if (chance <= 0.9D) {
                mobPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                CombatCommon.swapToMelee(mobPatch);
            } else {
                mobPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                CombatCommon.swapToMelee(mobPatch);
            }
        } else {
            if (chance <= 0.4D) {
                mobPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                CombatCommon.swapToMelee(mobPatch);
            } else if (chance <= 0.5D) {
                mobPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                CombatCommon.swapToMelee(mobPatch);
            } else {
                mobPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                CombatCommon.swapToMelee(mobPatch);
            }
        }
    }

    private static BiFunction<Integer, Integer, int[]> getIntegerIntegerBiFunction(Entity anchor, int rot) {
        Direction facing = anchor.getDirection();

        int fx = facing.getStepX();
        int fz = facing.getStepZ();
        int rx = -fz;
        int rz = fx;

        for (int i = 0; i < rot; i++) {
            int nfx = rx;
            int nfz = rz;
            int nrx = -fz;
            int nrz = fx;
            fx = nfx;
            fz = nfz;
            rx = nrx;
            rz = nrz;
        }

        int finalRx = rx;
        int finalFx = fx;
        int finalRz = rz;
        int finalFz = fz;
        return (a, b) -> new int[]{a * finalRx + b * finalFx, a * finalRz + b * finalFz};
    }
}
