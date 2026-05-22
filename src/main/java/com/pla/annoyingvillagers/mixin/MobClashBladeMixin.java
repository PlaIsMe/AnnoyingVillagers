package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.item.FlankerHookedSwordItem;
import com.pla.annoyingvillagers.item.HookedDiamondSwordItem;
import com.pla.annoyingvillagers.item.HookedGoldenSwordItem;
import com.pla.annoyingvillagers.item.HookedIronSwordItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.EscapeUtil;
import com.pla.efclash_blade.event.MobClashBladeEvent;
import com.pla.efkick.animations.KickAttackAnimation;
import com.pla.efkick.config.EFKickConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsMoonless;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;

@Mixin(value = MobClashBladeEvent.class, remap = false)
public class MobClashBladeMixin {
    @Inject(method = "customAdditionClashBladeLogic", at = @At("HEAD"), cancellable = true)
    private static void addMoreClashBladeCondition(LivingAttackEvent livingAttackEvent,
                                                   LivingEntityPatch<?> defenderLivingEntityPatch,
                                                   AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                   EntityState defenderEntityState, Entity attacker, Entity defender,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (EpicfightUtil.isLongHitAnimation(defenderDynamicAnimation, defenderLivingEntityPatch)) {
            cir.setReturnValue(false);
            return;
        }

        // Auto clash while playing animation
        if (defender instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AnimsEpicFight.AEGIS_SHIELD_SHOOT
                && defenderEntityState.getLevel() == 3) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AnimsWom.ENDER_AEGIS_NAPOLEON_RELOAD_1) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof SwordsmanHerobrineEntity
                && defenderDynamicAnimation == WOMAnimations.TORMENT_BERSERK_CONVERT) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof GlaiveHerobrineEntity
                && (defenderDynamicAnimation == AnimsWom.AGONY_GUARD_HIT_1)) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof SledgehammerHerobrineEntity
                && (defenderDynamicAnimation == WOMAnimations.TORMENT_BERSERK_CONVERT)) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof HerobrineChrisEntity
                && (defenderDynamicAnimation == AnimsMoonless.MOONLESS_GUARD_HIT_1)) {
            cir.setReturnValue(true);
            return;
        }
        if (livingAttackEvent.getSource().getDirectEntity() instanceof Projectile
                && defender.onGround()
                && !defender.isPassenger()
                && defender.level() instanceof ServerLevel) {
            Entity projectile = livingAttackEvent.getSource().getDirectEntity();
            // Projectile clashing
            if (defender instanceof AVNpc AVNpc
                    && AVNpc.getBlockDamage() == null
                    && new Random().nextDouble() <= AVNpc.getPlaceBlockToParryChance()
                    && (AVNpc.getItemInHand(InteractionHand.MAIN_HAND).getItem()
                    .equals(AVNpc.getMainWeaponItem().getItem())
                    || AVNpc.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)
                    && !AVNpc.isHealing()) {
                AVNpc.setBlockDamage(projectile);
                CombatCommon.swapToBlock((MobPatch<?>) defenderLivingEntityPatch);
                cir.setReturnValue(true);
                return;
            }

            if (defender instanceof PlayerNpcEntity playerNpcEntity
                    && playerNpcEntity.getBlockDamage() == null
                    && new Random().nextDouble() <= playerNpcEntity.getPlaceBlockToParryChance()
                    && (playerNpcEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem()
                    .equals(playerNpcEntity.getMainWeaponItem().getItem())
                    || playerNpcEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)
                    && !playerNpcEntity.isHealing()) {
                playerNpcEntity.setBlockDamage(projectile);
                CombatCommon.swapToBlock((MobPatch<?>) defenderLivingEntityPatch);
                cir.setReturnValue(true);
                return;
            }

            if (defender instanceof HerobrineMob herobrineMob) {
                if (!(projectile instanceof AbstractArrow) || projectile instanceof EnchantedArrowEntity || projectile instanceof BlueDemonThrownTridentEntity) {
                    herobrineMob.setBlockDamage(projectile);
                    cir.setReturnValue(true);
                }
            }

            if (defender instanceof BlueDemonEntity blueDemonEntity) {
                blueDemonEntity.setBlockDamage(projectile);
                cir.setReturnValue(true);
                return;
            }

        } else if (!(livingAttackEvent.getSource().getDirectEntity() instanceof Projectile)
                && defender.onGround()
                && !defender.isPassenger()
                && livingAttackEvent.getSource().getDirectEntity() != null
                && defender.level() instanceof ServerLevel) {
            // Non-projectile clashing
            ResourceLocation indirectEntity = BuiltInRegistries.ENTITY_TYPE.getKey(livingAttackEvent.getSource().getDirectEntity().getType());
            boolean isDamageFromGunKnight = indirectEntity.getNamespace().equals("torchesbecomesunlight")
                    && (indirectEntity.getPath().equals("gun_knight_patriot") || indirectEntity.getPath().equals("turret"));
            boolean ignisFileBall = indirectEntity.getNamespace().equals("cataclysm")
                    && (indirectEntity.getPath().equals("ignis_abyss_fireball") || indirectEntity.getPath().equals("ignis_fireball")
                    || indirectEntity.getPath().equals("flame_jet") || indirectEntity.getPath().equals("flame_strike"));
            boolean isMeteorite = livingAttackEvent.getSource().getDirectEntity() instanceof DragonMeteoriteEntity
                    || livingAttackEvent.getSource().getEntity() instanceof DragonMeteoriteEntity
                    || livingAttackEvent.getSource().getDirectEntity() instanceof ObsidianSledgehammerProjectileEntity
                    || livingAttackEvent.getSource().getEntity() instanceof ObsidianSledgehammerProjectileEntity;
            if (isDamageFromGunKnight || ignisFileBall || isMeteorite || livingAttackEvent.getSource().is(DamageTypes.EXPLOSION)) {
                if (defender instanceof AVNpc AVNpc
                        && AVNpc.getBlockDamage() == null
                        && new Random().nextDouble() <= AVNpc.getPlaceBlockToParryChance()
                        && (AVNpc.getItemInHand(InteractionHand.MAIN_HAND).getItem()
                        .equals(AVNpc.getMainWeaponItem().getItem())
                        || AVNpc.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)) {
                    AVNpc.setBlockDamage(livingAttackEvent.getSource().getDirectEntity());
                    CombatCommon.swapToBlock((MobPatch<?>) defenderLivingEntityPatch);
                    cir.setReturnValue(true);
                    return;
                }

                if (defender instanceof PlayerNpcEntity playerNpcEntity
                        && playerNpcEntity.getBlockDamage() == null
                        && new Random().nextDouble() <= playerNpcEntity.getPlaceBlockToParryChance()
                        && (playerNpcEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem()
                        .equals(playerNpcEntity.getMainWeaponItem().getItem())
                        || playerNpcEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)) {
                    playerNpcEntity.setBlockDamage(livingAttackEvent.getSource().getDirectEntity());
                    CombatCommon.swapToBlock((MobPatch<?>) defenderLivingEntityPatch);
                    cir.setReturnValue(true);
                    return;
                }

                if (defender instanceof HerobrineMob herobrineMob
                        && herobrineMob.getBlockDamage() == null) {
                    herobrineMob.setBlockDamage(livingAttackEvent.getSource().getDirectEntity());
                    cir.setReturnValue(true);
                    return;
                }

                if (defender instanceof BlueDemonEntity blueDemonEntity
                        && blueDemonEntity.getBlockDamage() == null) {
                    blueDemonEntity.setBlockDamage(livingAttackEvent.getSource().getDirectEntity());
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }

    @Inject(method = "customPreAdditionClashBlade", at = @At("HEAD"), cancellable = true)
    private static void customLogicBeforeClashing(LivingAttackEvent livingAttackEvent,
                                                  LivingEntityPatch<?> defenderLivingEntityPatch,
                                                  AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                  EntityState defenderEntityState, Entity attacker,
                                                  Entity defender, int clashBy,
                                                  CallbackInfo ci) {
        if (defender instanceof LivingEntity livingEntity
                && defender.level() instanceof ServerLevel serverLevel) {
            // Herobrine playing animation
            if (clashBy != 0) {
                if (ModList.get().isLoaded("efn")) {
                    if (defender instanceof AegisHerobrineEntity || defender instanceof GlaiveHerobrineEntity
                            || defender instanceof SledgehammerHerobrineEntity || defender instanceof ReaperHerobrineEntity
                            || defender instanceof SwordsmanHerobrineEntity || defender instanceof ShadowHerobrineEntity) {
                        HerobrineMob herobrineMob = (HerobrineMob) defender;
                        if (herobrineMob.getLivingEntityPatch() != null) {
                            EpicFightNightFall.playEfnGuardHit(herobrineMob.getLivingEntityPatch(), herobrineMob.getEfnGuardHitState(), livingAttackEvent.getSource());
                            herobrineMob.postPlayEfnGuardHit();
                        }
                    }
                } else {
                    if (defender instanceof AegisHerobrineEntity || defender instanceof GlaiveHerobrineEntity
                            || defender instanceof SledgehammerHerobrineEntity || defender instanceof ReaperHerobrineEntity) {
                        defenderLivingEntityPatch.playAnimationSynchronized(AnimsAgony.AGONY_GUARD_HIT_1, 0.0F);
                    }
                    if (defender instanceof SwordsmanHerobrineEntity) {
                        defenderLivingEntityPatch.playAnimationSynchronized(AnimsSolar.SOLAR_GUARD_HIT, 0.0F);
                    }
                }
            }

            // Place block to clash
            if (clashBy == 1) {
                Entity projectile = null;
                if (defender instanceof AVNpc AVNpc) {
                    projectile = AVNpc.getBlockDamage();
                } else if (defender instanceof PlayerNpcEntity playerNpcEntity) {
                    projectile = playerNpcEntity.getBlockDamage();
                } else if (defender instanceof HerobrineMob herobrineMob && !(defender instanceof NullEntity)) {
                    projectile = herobrineMob.getBlockDamage();
                }
                if (projectile != null) {
                    Random random = new Random();
                    int pattern = random.nextInt(11);
                    int rot = random.nextInt(4);

                    BiFunction<Integer, Integer, int[]> toWorld = getIntegerIntegerBiFunction(defender, rot);

                    BlockState placeState;
                    if (livingEntity instanceof HerobrineChrisEntity || livingEntity instanceof HerobrineCloneEntity) {
                        placeState = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState().setValue(ShadowObsidianBlock.FROM_PLAYER, false);
                    } else if (livingEntity instanceof Herobrine7Entity
                            || livingEntity instanceof ArmoredHerobrineEntity
                            || livingEntity instanceof ShadowHerobrineCloneEntity
                            || livingEntity instanceof ShadowHerobrineEntity) {
                        placeState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState().setValue(ShadowObsidianBlock.FROM_PLAYER, false);
                    } else if (livingEntity instanceof AegisHerobrineEntity
                            || livingEntity instanceof SwordsmanHerobrineEntity
                            || livingEntity instanceof ReaperHerobrineEntity
                            || livingEntity instanceof SledgehammerHerobrineEntity
                            || livingEntity instanceof GlaiveHerobrineEntity) {
                        placeState = AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get().defaultBlockState().setValue(ShadowObsidianBlock.FROM_PLAYER, false);
                    } else {
                        ItemStack handStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
                        placeState = Blocks.COBBLESTONE.defaultBlockState();
                        if (handStack.getItem() instanceof BlockItem blockItem) {
                            placeState = blockItem.getBlock().defaultBlockState();
                        }
                    }

                    BlockPos baseXZ;
                    int topY;
                    ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(projectile.getType());
                    if (key.getNamespace().equals("tacz")
                            || (key.getNamespace().equals("torchesbecomesunlight") && (key.getPath().equals("gun_knight_patriot") || key.getPath().equals("turret")))) {
                        Direction facing = defender.getDirection();
                        baseXZ = defender.blockPosition().relative(facing, 1);
                        topY = Mth.floor(defender.getY() + defender.getBbHeight());
                    } else {
                        baseXZ = BlockPos.containing(projectile.getX(), 0.0, projectile.getZ());
                        topY = Mth.floor(projectile.getY());
                    }
                    int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
                    BlockPos projXZ = new BlockPos(baseXZ.getX(), 0, baseXZ.getZ());

                    for (int y = surfaceY; y <= topY; y++) {
                        int layer = y - surfaceY;

                        BlockPos center = new BlockPos(projXZ.getX(), y, projXZ.getZ());
                        if (!serverLevel.getBlockState(center).canBeReplaced()) break;

                        defenderLivingEntityPatch.playAnimationSynchronized(AVAnimations.PLACE_BLOCK, 0.0F);
                        defender.playSound(SoundEvents.STONE_PLACE, 2.0F, 1.0F);
                        serverLevel.setBlockAndUpdate(center, placeState);

                        int[][] extrasLocal = switch (pattern) {
                            case 0 -> new int[][]{};

                            case 1 -> {
                                if (layer == 3) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 2 -> {
                                if (layer == 0) yield new int[][]{{-1, 0}, {1, 0}, {2, 0}};
                                else if (layer == 1) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 3 -> {
                                if (layer == 1) yield new int[][]{{-1, 0}, {1, 0}};
                                else yield new int[][]{};
                            }

                            case 4 -> {
                                if (layer == 0) yield new int[][]{{-1, 0}, {1, 0}};
                                else yield new int[][]{};
                            }

                            case 5 -> new int[][]{{1, 0}};

                            case 6 -> {
                                if (layer <= 1) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 7 -> {
                                if (layer == 0) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 8 -> {
                                if (layer == 1) yield new int[][]{{1, 0}};
                                else yield new int[][]{};
                            }

                            case 9 -> {
                                if (layer == 0) yield new int[][]{{-1, 0}};
                                else yield new int[][]{};
                            }

                            default -> {
                                if (layer == 1) yield new int[][]{{-1, 0}};
                                else yield new int[][]{};
                            }
                        };

                        for (int[] ab : extrasLocal) {
                            int[] dzdx = toWorld.apply(ab[0], ab[1]);
                            int dx = dzdx[0];
                            int dz = dzdx[1];

                            BlockPos p = center.offset(dx, 0, dz);
                            if (serverLevel.getBlockState(p).canBeReplaced()) {
                                serverLevel.setBlockAndUpdate(p, placeState);
                            }
                        }
                    }
                }
            }
        }
    }

    private static @NotNull BiFunction<Integer, Integer, int[]> getIntegerIntegerBiFunction(Entity defender, int rot) {
        Direction facing = defender.getDirection();

        int fx = facing.getStepX();
        int fz = facing.getStepZ();
        int rx = -fz;
        int rz = fx;

        for (int i = 0; i < rot; i++) {
            int nfx = rx, nfz = rz;
            int nrx = -fz, nrz = fx;
            fx = nfx; fz = nfz;
            rx = nrx; rz = nrz;
        }

        int finalRx = rx;
        int finalFx = fx;
        int finalRz = rz;
        int finalFz = fz;
        return (a, b) ->
                new int[]{a * finalRx + b * finalFx, a * finalRz + b * finalFz};
    }

    @Inject(method = "blacklistClashBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void rejectClashBladeFromAnimationsCondition(LivingAttackEvent livingAttackEvent,
                                                   LivingEntityPatch<?> defenderLivingEntityPatch,
                                                   AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                   EntityState defenderEntityState, Entity attacker, Entity defender,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (defenderDynamicAnimation.get() instanceof BowAttackAnimation) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "conditionToPlayClashSound", at = @At("HEAD"), cancellable = true)
    private static void disableClashSoundFromProjectileBlock(LivingAttackEvent livingAttackEvent,
                                                                LivingEntityPatch<?> defenderLivingEntityPatch,
                                                                AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                                EntityState defenderEntityState, Entity attacker, Entity defender,
                                                                CallbackInfoReturnable<Boolean> cir) {
        if (defender instanceof AVNpc AVNpc
                && AVNpc.getBlockDamage() != null
                && defender.level() instanceof ServerLevel) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "customPostAdditionClashBlade", at = @At("HEAD"))
    private static void moreLogicAfterClashing(LivingAttackEvent livingAttackEvent,
                                                  LivingEntityPatch<?> defenderLivingEntityPatch,
                                                  AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                  EntityState defenderEntityState, Entity attacker,
                                                  Entity defender, int clashBy,
                                                  CallbackInfo ci) {
        if (!(defender.level() instanceof ServerLevel serverLevel)) return;

        // Hook Sword
        if (attacker instanceof LivingEntity livingAttacker && defender instanceof LivingEntity defenderLivingDefender) {
            if (defenderLivingDefender.getMainHandItem().getItem() instanceof HookedIronSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof HookedGoldenSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof HookedDiamondSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof FlankerHookedSwordItem) {
                if (defenderDynamicAnimation == AnimsEpicFight.HOOK_AXE_AUTO1) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AnimsPugilistSteve.KNOCKDOWN_RIGHT,
                            HookDisarmLaunch.RIGHT
                    );
                }

                if (defenderDynamicAnimation == AnimsEpicFight.HOOK_AXE_AUTO2) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AnimsPugilistSteve.KNOCKDOWN_LEFT,
                            HookDisarmLaunch.LEFT
                    );
                }

                if (defenderDynamicAnimation == AnimsEpicFight.HOOK_DANCING_EDGE || defenderDynamicAnimation == AnimsWom.HOOK_HERRSCHER_UP) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AnimsPugilistSteve.GUARD_BREAK_ATTACK,
                            HookDisarmLaunch.BACKWARD
                    );
                }
            }
        }

        LivingEntityPatch<?> attackerLivingEntityPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);

        // Clash kick post
        if (clashBy == 0) {
            if (attackerLivingEntityPatch != null) {
                AssetAccessor<? extends StaticAnimation> attackerDynamicAnimation = Objects.requireNonNull(attackerLivingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (attackerDynamicAnimation != null) {
                    if (defender instanceof ServerPlayer serverPlayer && EscapeUtil.isAnimationDangerous(attackerDynamicAnimation) && CommonUtil.isAvDamageableEfnWeaponsMob(attacker)) {
                        boolean damaged = false;
                        int breakValue = AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get();
                        if (ModList.get().isLoaded("efn")) {
                            if (EpicFightNightFall.isEfnWeapons(serverPlayer.getMainHandItem())) {
                                breakValue = AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get() * EpicFightNightFall.MULTIPLIER_DAMAGE_VALUE;
                            }
                        }
                        if ((serverPlayer.getOffhandItem().getItem() instanceof SwordItem || serverPlayer.getOffhandItem().getItem() instanceof AxeItem) && (new Random()).nextBoolean()) {
                            damaged = true;
                            serverPlayer.getOffhandItem().hurtAndBreak(breakValue, serverPlayer, (player) -> player.broadcastBreakEvent(InteractionHand.OFF_HAND));
                        }

                        if (!damaged) {
                            serverPlayer.getMainHandItem().hurtAndBreak(breakValue, serverPlayer, (player) -> player.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        }
                    }
                    if ((defender instanceof AVNpc AVNpc && AVNpc.getBlockDamage() != null)
                            || (defender instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.getBlockDamage() != null)) {
                        return;
                    }
                    if (attackerDynamicAnimation.get() instanceof KickAttackAnimation) {
                        EpicfightUtil.dealStaminaDamageByPercentage(livingAttackEvent.getSource(),
                                attackerLivingEntityPatch,
                                EFKickConfig.KICK_STAMINA_DECREASE_PERCENTAGE.get(),
                                true);
                    }
                }
            }
        }

        // Clash projectile post
        if (clashBy == 1) {
            if ((defender instanceof AVNpc AVNpc && AVNpc.getBlockDamage() != null)
                    || (defender instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.getBlockDamage() != null)) {
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        boolean rollAndSwap = false;
                        if (defender instanceof AVNpc AVNpc
                                && AVNpc.getBlockDamage() != null) {
                            AVNpc.setBlockDamage(null);
                            rollAndSwap = true;
                        }

                        if (defender instanceof PlayerNpcEntity playerNpcEntity
                                && playerNpcEntity.getBlockDamage() != null) {
                            playerNpcEntity.setBlockDamage(null);
                            rollAndSwap = true;
                        }

                        if (rollAndSwap) {
                            if (CombatCommon.canSwapToBow((MobPatch<?>) defenderLivingEntityPatch)) {
                                double chance = new Random().nextDouble(0.0, 1.0);
                                if (chance <= 0.25) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                                    CombatCommon.swapToBow((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.5) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                                    CombatCommon.swapToBow((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.7) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                                    CombatCommon.swapToBow((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.8) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.9) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                } else {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                }
                            } else {
                                double chance = new Random().nextDouble(0.0, 1.0);
                                if (chance <= 0.4) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                } else if (chance <= 0.5) {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                } else {
                                    defenderLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                                    CombatCommon.swapToMelee((MobPatch<?>) defenderLivingEntityPatch);
                                }
                            }
                        }
                    }
                };
            }
        }
    }
}