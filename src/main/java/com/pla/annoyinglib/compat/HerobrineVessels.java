package com.pla.annoyinglib.compat;
import com.pla.herobrine_vessels.clazz.FakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.AbstractArrow;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.DangerousReaction;
import com.pla.annoyinglib.client.animation.rig_animation.living.HerobrineLivingAnimations;
import com.pla.annoyinglib.client.animation.rig_animation.living.LivingAnimations;
import com.pla.annoyinglib.client.renderer.RigColliderRenderer;
import com.pla.annoyinglib.entity.goal.DangerousReactionGoal;
import com.pla.annoyinglib.event.VanillaWeaponTooltipEvent;
import com.pla.annoyinglib.item.FishingRodGrappleUtil;
import com.pla.annoyinglib.network.VanillaAttackKeyMessage;
import com.pla.annoyinglib.rig.RigAnimationId;
import com.pla.annoyinglib.rig.RigAnimationSpec;
import com.pla.annoyinglib.rig.RigAnimationSpecs;
import com.pla.annoyinglib.rig.RigColliderSystem;
import com.pla.annoyinglib.rig.RigOrientedBox;
import com.pla.annoyinglib.rig.armor.ObsidianArmorColliderSystem;
import com.pla.annoyinglib.rig.armor.ObsidianArmorController;
import com.pla.annoyinglib.rig.armor.ObsidianArmorPart;
import com.pla.annoyinglib.specialanimation.SpecialAnimationId;
import com.pla.annoyinglib.util.CommonGoals;
import com.pla.annoyinglib.util.CommonUtil;
import com.pla.annoyinglib.util.GlintColorHelper;
import com.pla.annoyinglib.util.InventoryUtils;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import com.pla.herobrine_vessels.blockentity.ObsidianBlockEntity;
import com.pla.herobrine_vessels.blockentity.ShadowObsidianBlockEntity;
import com.pla.herobrine_vessels.blockentity.ShadowObsidianLongPillarBlockEntity;
import com.pla.herobrine_vessels.clazz.HerobrineMob;
import com.pla.herobrine_vessels.clazz.HerobrineObsidianBlock;
import com.pla.herobrine_vessels.clazz.HerobrinePortalSupportCaster;
import com.pla.herobrine_vessels.clazz.NullWeapon;
import com.pla.herobrine_vessels.client.animation.ObsidianArmorClientAnimationState;
import com.pla.herobrine_vessels.client.compat.BetterCombatSnakeAttachment;
import com.pla.herobrine_vessels.client.renderer.ObsidianArmorExtensionRenderer;
import com.pla.herobrine_vessels.compat.emmiterinfo.DragonBeamParticleEmitterInfo;
import com.pla.herobrine_vessels.compat.emmiterinfo.TeleportPortalParticleEmitterInfo;
import com.pla.herobrine_vessels.entity.AegisHerobrineEntity;
import com.pla.herobrine_vessels.entity.ArmoredHerobrineEntity;
import com.pla.herobrine_vessels.entity.AvWarden;
import com.pla.herobrine_vessels.entity.BlackHoleEntity;
import com.pla.herobrine_vessels.entity.EliteHerobrineKnockedEntity;
import com.pla.herobrine_vessels.entity.GlaiveHerobrineEntity;
import com.pla.herobrine_vessels.entity.HerobrineDragonEntity;
import com.pla.herobrine_vessels.entity.HerobrineGregEntity;
import com.pla.herobrine_vessels.entity.InfectedChrisEntity;
import com.pla.herobrine_vessels.entity.InfectedPlayerNpcEntity;
import com.pla.herobrine_vessels.entity.InfectedTheMostMoistBurrit0Entity;
import com.pla.herobrine_vessels.entity.LowHerobrineCloneEntity;
import com.pla.herobrine_vessels.entity.LowShadowHerobrineCloneEntity;
import com.pla.herobrine_vessels.entity.NullEntity;
import com.pla.herobrine_vessels.entity.NullSkeletonEntity;
import com.pla.herobrine_vessels.entity.ReaperHerobrineEntity;
import com.pla.herobrine_vessels.entity.ShadowHerobrineEntity;
import com.pla.herobrine_vessels.entity.SledgehammerHerobrineEntity;
import com.pla.herobrine_vessels.entity.TransporterHerobrineCloneEntity;
import com.pla.herobrine_vessels.entity.goal.HerobrineEscapeHoleGoal;
import com.pla.herobrine_vessels.entity.goal.HerobrinePortalDangerousReactionGoal;
import com.pla.herobrine_vessels.init.HerobrineVesselsModBlocks;
import com.pla.herobrine_vessels.init.HerobrineVesselsModItems;
import com.pla.herobrine_vessels.init.HerobrineVesselsModMobEffects;
import com.pla.herobrine_vessels.init.HerobrineVesselsModParticleTypes;
import com.pla.herobrine_vessels.init.HerobrineVesselsModSounds;
import com.pla.herobrine_vessels.item.DemoniacVoltageReaverItem;
import com.pla.herobrine_vessels.item.EnderAegisItem;
import com.pla.herobrine_vessels.item.EnderGlaiveItem;
import com.pla.herobrine_vessels.item.EnderSlayerScytheItem;
import com.pla.herobrine_vessels.item.HerobrineEnderEyeItem;
import com.pla.herobrine_vessels.item.HerobrineObsidianArmorCharge;
import com.pla.herobrine_vessels.item.NullWeaponItem;
import com.pla.herobrine_vessels.item.ObsidianSledgehammerItem;
import com.pla.herobrine_vessels.item.ObsidianWeaponItem;
import com.pla.herobrine_vessels.item.ShadowObsidianPillarItem;
import com.pla.herobrine_vessels.item.ShadowObsidianSwordItem;
import com.pla.herobrine_vessels.item.ShadowObsidianWeaponItem;
import com.pla.herobrine_vessels.item.TransporterFragmentItem;
import com.pla.herobrine_vessels.network.ClientboundObsidianArmorAnimation;
import com.pla.herobrine_vessels.potion.GroundStuckMobEffect;
import com.pla.herobrine_vessels.util.HerobrineEyesUtil;
import com.pla.herobrine_vessels.util.HerobrineUtil;
import com.pla.herobrine_vessels.util.EndFireUtil;
import com.pla.smart_npc.entity.PlayerNpcEntity;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Optional Vessels bridge. Call only after checking its loader mod ID. */
public final class HerobrineVessels {
    private HerobrineVessels() {}

    public static boolean isEndFireBurning(Entity entity) {
        return EndFireUtil.isEndFireBurning(entity);
    }

    // Legacy hook: AVNpc.java:725
    public static boolean isHerobrineMob(Entity entity) {
        return entity instanceof HerobrineMob;
    }

    // Legacy hook: DangerousReaction.java:141
    public static boolean isDragonRider(Mob mob) {
        return mob instanceof ReaperHerobrineEntity reaper && reaper.isSecondFormDragonRider();
    }

    // Legacy hook: SmartNpc.java:57
    public static void gregAvoid(PathfinderMob self) {
        self.goalSelector.addGoal(2, new AvoidEntityGoal<>(self, PlayerNpcEntity.class, 12.0F, 1.0D, 1.35D));
    }

    // Legacy hook: SmartNpc.java:66
    public static void copyPlayerNpcIdentity(Entity entity, Entity clone) {
        LowHerobrineCloneEntity lowHerobrineCloneEntity = (LowHerobrineCloneEntity) clone;

            if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) return;

            String username = playerNpcEntity.getUsername().getCombinedNames();
            if (username != null && !username.isBlank()) lowHerobrineCloneEntity.setUsername(username);
            var profile = playerNpcEntity.getProfile();
            if (profile != null) lowHerobrineCloneEntity.setProfile(profile);
    }

    // Legacy hook: SmartNpc.java:146
    public static boolean isMonsterTarget(LivingEntity target) {
        return target instanceof HerobrineGregEntity || target instanceof LowHerobrineCloneEntity || target instanceof LowShadowHerobrineCloneEntity || target instanceof HerobrineMob || target instanceof EliteHerobrineKnockedEntity;
    }

    // Legacy hook: SmartNpc.java:159
    public static boolean isHighDangerThreat(LivingEntity target) {
        return target instanceof HerobrineMob
                || target instanceof HerobrineGregEntity
                || target instanceof LowHerobrineCloneEntity
                || target instanceof LowShadowHerobrineCloneEntity
                || (ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isVillagerArmyEntity(target));
    }

    // Legacy hook: BbqEntity.java:233
    public static void addBbqEscapeGoal(PathfinderMob self) {
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.addEscapeGoal(self, HerobrineMob.class);
    }

    // Legacy hook: BlockProjectileEntity.java:61
    public static float blockProjectileDamage(BlockState carriedBlock) {
        if (carriedBlock.is(HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get())
                || carriedBlock.is(HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get())
                || carriedBlock.is(HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get())) {
            return 10.0F;
        }
        return 2.0F;
    }

    // Legacy hook: BlockProjectileEntity.java:136
    public static boolean isHerobrineFaction(Entity target) {
        return HerobrineUtil.isHerobrineFaction(target);
    }

    // Legacy hook: BlockProjectileEntity.java:261
    public static BlockState blockPlacementState(BlockState carriedBlock, UUID owner, int replaceByLiquid) {
        BlockState placeState;
        if (carriedBlock.is(HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get())
                || carriedBlock.is(HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get())) {
            placeState = HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get().defaultBlockState();
        } else if (carriedBlock.is(HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get())) {
            placeState = HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState();
        } else if (carriedBlock.is(HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get())) {
            placeState = HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState();
        } else {
            placeState = carriedBlock;
        }
        if (owner != null && placeState.hasProperty(HerobrineObsidianBlock.FROM_PLAYER)) {
            placeState = placeState.setValue(HerobrineObsidianBlock.FROM_PLAYER, true);
        }
        if (replaceByLiquid != 0 && placeState.hasProperty(HerobrineObsidianBlock.REPLACE_BY_LIQUID)) {
            placeState = placeState.setValue(HerobrineObsidianBlock.REPLACE_BY_LIQUID, replaceByLiquid);
        }
        return placeState;
    }

    // Legacy hook: BlockProjectileEntity.java:285
    public static void setBlockOwner(BlockEntity blockEntity, UUID owner) {
        if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
            obsidianBlockEntity.setOwner(owner);
        } else if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
            shadowObsidianBlockEntity.setOwner(owner);
        } else if (blockEntity instanceof ShadowObsidianLongPillarBlockEntity shadowObsidianLongPillarBlockEntity) {
            shadowObsidianLongPillarBlockEntity.setOwner(owner);
        }
    }

    // Legacy hook: BlueDemonEntity.java:534
    public static LivingEntity blueDemonLiveTarget(LivingEntity target) {
        if (target instanceof HerobrineMob herobrineMob
                && (herobrineMob.isSacrificing() || herobrineMob.isHealing())) {
            if (herobrineMob.getFirstPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getSecondPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getThirdPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getFourthPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
        }

        if (target instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                && lowHerobrineCloneEntity.isHealing()
                && lowHerobrineCloneEntity.getPossessedByEntity() != null
                && lowHerobrineCloneEntity.getPossessedByEntity().isAlive()) {
            return lowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (target instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                && (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null
                && lowShadowHerobrineCloneEntity.getPossessedByEntity().isAlive()) {
            return lowShadowHerobrineCloneEntity.getPossessedByEntity();
        }
        return target;
    }

    // Legacy hook: BlueDemonEntity.java:1137
    public static LivingEntity blueDemonDeadTarget(LivingEntity target) {
        if (target instanceof HerobrineMob herobrineMob
                && (herobrineMob.isSacrificing() || herobrineMob.isHealing())) {
            if (herobrineMob.getFirstPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getSecondPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getThirdPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
            if (herobrineMob.getFourthPossessedHerobrine() instanceof LivingEntity living && living.isAlive()) {
                return living;
            }
        }

        if (target instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                && lowHerobrineCloneEntity.isHealing()
                && lowHerobrineCloneEntity.getPossessedByEntity() != null) {
            if (!lowHerobrineCloneEntity.isAlive()) {
                return lowHerobrineCloneEntity.getPossessedByEntity();
            }
        }

        if (target instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                && (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null) {
            if (!lowShadowHerobrineCloneEntity.isAlive()) {
                return lowShadowHerobrineCloneEntity.getPossessedByEntity();
            }
        }
        return target;
    }

    // Legacy hook: AVNpcRangedBowAttackGoal.java:216
    public static boolean hasArrowAmmo(Mob mob, LivingEntity target) {
        return InventoryUtils.hasArrowAmmo(mob, target instanceof HerobrineMob);
    }

    // Legacy hook: AVNpcRangedBowAttackGoal.java:221
    public static boolean isDragon(Entity target) {
        return target instanceof HerobrineDragonEntity;
    }

    // Legacy hook: BowLineOfSightGoal.java:197
    public static double maxBowDistance(LivingEntity target, double maxShootDistance) {
        return target instanceof HerobrineDragonEntity ? 80.0D : maxShootDistance;
    }

    // Legacy hook: RetargetCloserThreatGoal.java:39
    public static boolean isHoleEscapeActive(Mob mob) {
        return mob instanceof HerobrineMob herobrine && herobrine.isHoleEscapeActive();
    }

    // Legacy hook: RigAnimatedMeleeAttackGoal.java:78
    public static boolean isNull(Entity mob) {
        return mob instanceof NullEntity;
    }

    // Legacy hook: RigAnimatedMeleeAttackGoal.java:155
    public static boolean canYieldAttackToHoleRecovery(Mob mob) {
        if (mob instanceof HerobrineMob herobrine
                && !(mob instanceof NullEntity)) return !herobrine.isLocked();
        return false;
    }

    // Legacy hook: RigAnimatedMeleeAttackGoal.java:317
    public static double nullFlightSpeed(Mob mob, double flightSpeed) {
        if (mob instanceof NullEntity nullEntity && nullEntity.getState() >= 2) flightSpeed *= 1.15D;
        return flightSpeed;
    }

    // Legacy hook: RigAnimatedMeleeAttackGoal.java:355
    public static double nullOrbitSpeed(Mob mob, double orbitSpeed) {
        if (mob instanceof NullEntity nullEntity && nullEntity.getState() >= 2) orbitSpeed *= 1.12D;
        return orbitSpeed;
    }

    // Legacy hook: RigAnimatedMeleeAttackGoal.java:388
    public static boolean canMoveWithSnakeAttackLock(Mob mob) {
        return DemoniacVoltageReaverItem.hasSnakeProfileAttackLock(mob);
    }

    // Legacy hook: RigAnimatedMeleeAttackGoal.java:425


    // Legacy hook: AVChestplateEvent.java:31
    public static void onArmorChanged(LivingEquipmentChangeEvent event) {
        if ((event.getSlot() == EquipmentSlot.HEAD || event.getSlot() == EquipmentSlot.CHEST) && HerobrineObsidianArmorCharge.isObsidianArmor(event.getFrom())) ObsidianArmorController.clear(event.getEntity());
    }

    // Legacy hook: AVChestplateEvent.java:59
    public static void chargeArmor(ItemStack helmet, ItemStack chest, int gainedCharge) {
        if (HerobrineObsidianArmorCharge.isHelmet(helmet) && !HerobrineObsidianArmorCharge.isFullyCharged(helmet)) HerobrineObsidianArmorCharge.addCharge(helmet, gainedCharge);
        if (HerobrineObsidianArmorCharge.isChestplate(chest) && !HerobrineObsidianArmorCharge.isFullyCharged(chest)) HerobrineObsidianArmorCharge.addCharge(chest, gainedCharge);
    }

    // Legacy hook: MobTargetRedirectEvent.java:33
    public static LivingEntity redirectTarget(LivingEntity currentTarget) {
        if (currentTarget instanceof HerobrineMob herobrineMob
                && (herobrineMob.isSacrificing() || herobrineMob.isHealing())) {
            if (herobrineMob.getFirstPossessedHerobrine() instanceof LivingEntity living) {
                return normalizeRedirectTarget(living);
            } else if (herobrineMob.getSecondPossessedHerobrine() instanceof LivingEntity living) {
                return normalizeRedirectTarget(living);
            } else if (herobrineMob.getThirdPossessedHerobrine() instanceof LivingEntity living) {
                return normalizeRedirectTarget(living);
            } else if (herobrineMob.getFourthPossessedHerobrine() instanceof LivingEntity living) {
                return normalizeRedirectTarget(living);
            }
        }

        if (currentTarget instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                && lowHerobrineCloneEntity.isHealing()
                && lowHerobrineCloneEntity.getPossessedByEntity() != null
                && !lowHerobrineCloneEntity.isAlive()) {
            return lowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (currentTarget instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                && (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null
                && !lowShadowHerobrineCloneEntity.isAlive()) {
            return lowShadowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (currentTarget instanceof NullEntity nullEntity) {
            if (nullEntity.getFirstWitherSkeleton() != null) {
                return normalizeRedirectTarget(nullEntity.getFirstWitherSkeleton());
            } else if (nullEntity.getSecondWitherSkeleton() != null) {
                return normalizeRedirectTarget(nullEntity.getSecondWitherSkeleton());
            }
        }

        if (currentTarget instanceof NullSkeletonEntity nullSkeletonEntity
                && nullSkeletonEntity.getNullEntity() != null
                && !nullSkeletonEntity.isAlive()) {
            return nullSkeletonEntity.getNullEntity();
        }

        if (currentTarget instanceof ReaperHerobrineEntity reaperHerobrineEntity
                && reaperHerobrineEntity.isPassenger()
                && reaperHerobrineEntity.getVehicle() instanceof HerobrineDragonEntity herobrineDragonEntity) {
            return herobrineDragonEntity;
        }

        if (currentTarget instanceof HerobrineDragonEntity herobrineDragonEntity
                && herobrineDragonEntity.getSummoner() instanceof ReaperHerobrineEntity reaperHerobrineEntity
                && !reaperHerobrineEntity.isPassenger()) {
            return reaperHerobrineEntity;
        }
        return null;
    }

    // Legacy hook: MobTargetRedirectEvent.java:116
    public static LivingEntity normalizeRedirectTarget(LivingEntity redirectTarget) {
        if (redirectTarget instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                && lowHerobrineCloneEntity.isHealing()
                && lowHerobrineCloneEntity.getPossessedByEntity() != null
                && !lowHerobrineCloneEntity.isAlive()) {
            return lowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (redirectTarget instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                && (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null
                && !lowShadowHerobrineCloneEntity.isAlive()) {
            return lowShadowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (redirectTarget instanceof NullSkeletonEntity nullSkeletonEntity
                && nullSkeletonEntity.getNullEntity() != null
                && !nullSkeletonEntity.isAlive()) {
            return nullSkeletonEntity.getNullEntity();
        }
        return redirectTarget;
    }

    // Legacy hook: MobTargetRedirectEvent.java:141
    public static boolean isActiveRedirectObjective(LivingEntity currentTarget) {
        if (currentTarget instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
            HerobrineMob owner = lowHerobrineCloneEntity.getPossessedByEntity();
            return lowHerobrineCloneEntity.isHealing()
                    && owner != null
                    && owner.isAlive()
                    && owner.isHealing();
        }

        if (currentTarget instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
            HerobrineMob owner = lowShadowHerobrineCloneEntity.getPossessedByEntity();
            return (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                    && owner != null
                    && owner.isAlive()
                    && (owner.isSacrificing() || owner.isHealing());
        }

        if (currentTarget instanceof NullSkeletonEntity nullSkeletonEntity) {
            NullEntity nullEntity = nullSkeletonEntity.getNullEntity();
            return nullEntity != null
                    && nullEntity.isAlive()
                    && (nullEntity.getFirstWitherSkeleton() == nullSkeletonEntity
                    || nullEntity.getSecondWitherSkeleton() == nullSkeletonEntity);
        }

        if (currentTarget instanceof HerobrineDragonEntity herobrineDragonEntity) {
            return herobrineDragonEntity.getSummoner() instanceof ReaperHerobrineEntity reaperHerobrineEntity
                    && reaperHerobrineEntity.isAlive()
                    && reaperHerobrineEntity.isPassenger()
                    && reaperHerobrineEntity.getVehicle() == herobrineDragonEntity;
        }
        return false;
    }

    // Legacy hook: MobTargetRedirectEvent.java:192
    public static boolean isReaperDragonRedirect(LivingEntity currentTarget, LivingEntity redirectTarget) {
        return currentTarget instanceof ReaperHerobrineEntity && redirectTarget instanceof HerobrineDragonEntity;
    }

    // Legacy hook: RigMobClashBladeEvent.java:89
    public static void clearPillarCycles() {
        HerobrineEscapeHoleGoal.clearActivePillarCycles();
    }

    // Legacy hook: RigStunCombatEvent.java:85
    public static boolean isGroundStuck(LivingEntity victim) {
        return victim.hasEffect(HerobrineVesselsModMobEffects.GROUND_STUCK.get());
    }

    // Legacy hook: SpecialAttackOnKeyHeldEvent.java:54
    public static boolean activateHeldSpecial(Player player, Vec3 crosshairTarget, Runnable playPortalSummonAnimation) {
        if (EnderSlayerScytheItem.activateMountedDragonDescent(player)) return true;
        if (HerobrineEnderEyeItem.activateVanillaHeldSpecial(player)) return true;
        TransporterFragmentItem.UseResult transporterUseResult = TransporterFragmentItem.tryUseHeldSpecialAttack(player, crosshairTarget);
        if (transporterUseResult.consumed()) {
            if (transporterUseResult.activated()) {
                if (transporterUseResult.mode() == TransporterFragmentItem.UseMode.OFF_HAND) VanillaWeaponAbilityUtil.swingOffHand(player);
                else VanillaWeaponAbilityUtil.swingMainHand(player);
                playPortalSummonAnimation.run();
            }
            return true;
        }
        return false;
    }

    // Legacy hook: SpecialAttackOnKeyHeldEvent.java:54
    public static boolean activateNullHeldSpecial(Player player) {
        return NullWeaponItem.activateHeldSpecial(player);
    }

    // Legacy hook: SpecialAttackOnKeyPressedEvent.java:52
    public static void playTransporterFragmentAnimation(Player player, Enum<?> useMode, Consumer<Player> playPortalSummonAnimation, Consumer<Player> playSinglePortalSummonAnimation) {
        switch ((TransporterFragmentItem.UseMode) useMode) {
            case BOTH_HANDS, MAIN_HAND -> {
                playPortalSummonAnimation.accept(player);
            }
            case OFF_HAND -> playSinglePortalSummonAnimation.accept(player);
            case NONE -> {
            }
        }
    }

    // Legacy hook: SpecialAttackOnKeyPressedEvent.java:109
    public static boolean activateSpecial(Player player) {
        if (ShadowObsidianSwordItem.activateVanillaSpecial(player)) return true;
        if (EnderSlayerScytheItem.activateVanillaSpecial(player)) return true;
        if (DemoniacVoltageReaverItem.activateVanillaSpecial(player)) return true;
        if (ObsidianSledgehammerItem.activateVanillaSpecial(player)) return true;
        if (ShadowObsidianPillarItem.activateVanillaSpecial(player)) return true;
        if (ShadowObsidianWeaponItem.activateVanillaSpecial(player)) return true;
        if (ObsidianWeaponItem.activateVanillaSpecial(player)) return true;
        return false;
    }

    // Legacy hook: VanillaWeaponCombatEvent.java:34
    public static void onCriticalHit(ItemStack stack, Player player) {
        if (stack.getItem() instanceof ShadowObsidianPillarItem) ShadowObsidianPillarItem.onVanillaCriticalHit(stack, player);
        else if (stack.getItem() instanceof ShadowObsidianSwordItem) ShadowObsidianSwordItem.onVanillaCriticalHit(stack, player);
        else if (stack.getItem() instanceof ShadowObsidianWeaponItem) ShadowObsidianWeaponItem.onVanillaCriticalHit(player);
        else if (stack.getItem() instanceof ObsidianWeaponItem) ObsidianWeaponItem.onVanillaCriticalHit(player);
    }

    // Legacy hook: VanillaWeaponCombatEvent.java:45
    public static void onAttackEntity(AttackEntityEvent event, Player player) {
        if (!(player.getMainHandItem().getItem() instanceof EnderSlayerScytheItem)) return;
        LivingEntity target = event.getTarget() instanceof LivingEntity livingEntity ? livingEntity : null;
        if (EnderSlayerScytheItem.commandThunder(player, target)) event.setCanceled(true);
    }

    // Legacy hook: VanillaWeaponCombatEvent.java:56
    public static void tickSavedTeleport(Player player) {
        TransporterFragmentItem.tickPendingSavedTeleport(player);
    }

    // Legacy hook: VanillaWeaponCombatEvent.java:65
    public static void onShieldBlock(ShieldBlockEvent event, Player player, ItemStack stack) {
        if (!(stack.getItem() instanceof EnderAegisItem)) return;
        boolean wasSecondForm = EnderAegisItem.isSecondForm(stack);
        if (wasSecondForm) EnderAegisItem.shieldShoot(player.level(), player);
        else EnderAegisItem.addBlockedCharge(stack, player, event.getBlockedDamage());
    }

    // Legacy hook: VanillaWeaponCombatEvent.java:73
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof Player attacker && event.getSource().getDirectEntity() == attacker && event.getEntity() != attacker) NullWeaponItem.onPlayerMeleeHit(attacker);
    }

    // Legacy hook: FishingRodGrappleUtil.java:1255
    public static FishingRodGrappleUtil.HerobrineEscapeHookResult cancelFishingEscape(FishingHook hook, Entity target) {
        if (target instanceof HerobrineGregEntity greg && greg.canFishingHookCancelEscape()) {
            if (greg.tryFishingHookCancelEscape()) {
                FishingRodGrappleUtil.setVanillaHookedEntity(hook, greg);
                return FishingRodGrappleUtil.HerobrineEscapeHookResult.CANCELLED;
            }

            FishingRodGrappleUtil.setVanillaHookedEntity(hook, null);
            return FishingRodGrappleUtil.HerobrineEscapeHookResult.FAILED;
        }

        if (target instanceof TransporterHerobrineCloneEntity transporter && transporter.canFishingHookCancelEscape()) {
            if (transporter.tryFishingHookCancelEscape()) {
                FishingRodGrappleUtil.setVanillaHookedEntity(hook, transporter);
                return FishingRodGrappleUtil.HerobrineEscapeHookResult.CANCELLED;
            }

            FishingRodGrappleUtil.setVanillaHookedEntity(hook, null);
            return FishingRodGrappleUtil.HerobrineEscapeHookResult.FAILED;
        }
        return FishingRodGrappleUtil.HerobrineEscapeHookResult.NONE;
    }

    // Legacy hook: BoatMixin.java:27
    public static boolean blocksVehicle(Entity entity) {
        return entity instanceof HerobrineMob || entity instanceof LowHerobrineCloneEntity || entity instanceof LowShadowHerobrineCloneEntity || entity instanceof HerobrineGregEntity || entity instanceof NullSkeletonEntity;
    }

    // Legacy hook: ExplosionMixin.java:82
    public static BlockState explosionFire(ItemStack stack, BlockState originalState) {
        if (stack.getItem() instanceof EnderGlaiveItem) {
            return HerobrineVesselsModBlocks.END_FIRE.get().defaultBlockState();
        }
        return originalState;
    }

    // Legacy hook: ItemCooldownsMixin.java:17
    public static boolean ignoresItemCooldown(Item item) {
        return item == HerobrineVesselsModItems.ENDER_AEGIS.get() || item == HerobrineVesselsModItems.ENDER_SLAYER_SCYTHE.get() || item == HerobrineVesselsModItems.DEMONIAC_VOLTAGE_REAVER.get();
    }

    // Legacy hook: PlayerMixin.java:48
    public static boolean keepAegisUsable(Player self) {
        return VanillaWeaponAbilityUtil.abilitiesEnabled() && self.getUseItem().getItem() instanceof EnderAegisItem;
    }

    // Legacy hook: ServerboundActivateArmor.java:30
    public static void activateArmor(ServerPlayer player) {
        activateChargedArmor(player);
    }

    // Legacy hook: VanillaAttackKeyMessage.java:31
    public static void onAttackKey(ServerPlayer player) {
        if (EnderSlayerScytheItem.isDragonActive(player.getMainHandItem())) {
            EnderSlayerScytheItem.commandThunder(player, null);
            return;
        }
        DemoniacVoltageReaverItem.activateVanillaNormalAttack(player);
    }

    // Legacy hook: ObsidianArmorController.java:47
    public static boolean activateChargedArmor(ServerPlayer player) {
        EnumMap<ObsidianArmorPart, SpecialAnimationId> parts = new EnumMap<>(ObsidianArmorPart.class);
        for (ObsidianArmorPart part : ObsidianArmorPart.values()) {
            ItemStack stack = player.getItemBySlot(part.slot());
            if (isMatchingArmor(stack, part) && HerobrineObsidianArmorCharge.isFullyCharged(stack)) {
                parts.put(part, ObsidianArmorController.randomAnimation(player, part));
            }
        }
        return !parts.isEmpty() && ObsidianArmorController.start(player, parts, true, false);
    }

    // Legacy hook: ObsidianArmorController.java:136
    public static void consumeArmorCharge(LivingEntity wearer, Set<ObsidianArmorPart> parts, boolean consumeCharge) {
        if (consumeCharge) for (ObsidianArmorPart part : parts) HerobrineObsidianArmorCharge.setCharge(wearer.getItemBySlot(part.slot()), 0);
    }

    // Legacy hook: ObsidianArmorController.java:275
    public static boolean isMatchingArmor(ItemStack stack, ObsidianArmorPart part) {
        return part == ObsidianArmorPart.HELMET
                ? HerobrineObsidianArmorCharge.isHelmet(stack) : HerobrineObsidianArmorCharge.isChestplate(stack);
    }

    // Legacy hook: ObsidianArmorController.java:301
    public static void sendArmorAnimation(LivingEntity wearer, SpecialAnimationId animationId, int durationTicks) {
        com.pla.herobrine_vessels.HerobrineVessels.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> wearer), new ClientboundObsidianArmorAnimation(wearer.getId(), animationId, durationTicks));
    }

    // Legacy hook: RigAnimationSpecs.java:183
    public static void summonFistColumn(ServerLevel serverLevel, Mob mob, BlockState state) {
        HerobrineUtil.summonObsidianVerticalColumnInFront(serverLevel, mob, state, 3);
    }

    // Legacy hook: RigAnimationSpecs.java:189
    public static void summonFistArc(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        BlockState state = HerobrineVessels.getObsidianFistState(mob);
        if (state == null) return;
        HerobrineUtil.summonObsidianArcInFront(serverLevel, mob, state);
    }

    // Legacy hook: RigAnimationSpecs.java:754
    public static void pointLeftToward(Mob mob) {
        if (mob instanceof ReaperHerobrineEntity reaper) {
            reaper.castThunderFromSecondForm();
        } else if (mob instanceof ShadowHerobrineEntity shadowHerobrine) {
            shadowHerobrine.shootDarkObsAtTarget(2.0D);
        }
    }

    // Legacy hook: RigAnimationSpecs.java:763
    public static void pointLeftMiddle(Mob mob) {
        if (mob instanceof ReaperHerobrineEntity reaper) {
            reaper.respawnHealingCrystalFromSecondForm();
        } else if (mob instanceof ShadowHerobrineEntity shadowHerobrine) {
            shadowHerobrine.spawnDarkObEntities();
        }
    }

    // Legacy hook: RigAnimationSpecs.java:772
    public static void pointLeftUp(Mob mob) {
        if (mob instanceof ReaperHerobrineEntity reaper) {
            reaper.castMeteoriteFromSecondForm();
        }
    }

    // Legacy hook: RigAnimationSpecs.java:795
    public static List<RigAnimationSpec.RigTimedAnimationHook> flyUpHooks() {
        return List.of(
        RigAnimationSpec.RigTimedAnimationHook.at(3, mob -> HerobrineEscapeHoleGoal.placeFlyUpPillarBlock(mob, 0)),
        RigAnimationSpec.RigTimedAnimationHook.at(6, mob -> HerobrineEscapeHoleGoal.placeFlyUpPillarBlock(mob, 1)),
        RigAnimationSpec.RigTimedAnimationHook.at(9, mob -> HerobrineEscapeHoleGoal.placeFlyUpPillarBlock(mob, 2)),
        RigAnimationSpec.RigTimedAnimationHook.at(12, mob -> HerobrineEscapeHoleGoal.placeFlyUpPillarBlock(mob, 3))
        );
    }

    // Legacy hook: RigAnimationSpecs.java:1198
    public static void aegisUltimate(Mob mob) {
        if (mob instanceof AegisHerobrineEntity aegisHerobrineEntity) {
            aegisHerobrineEntity.fireSecondFormShieldShot();
        }
    }

    // Legacy hook: RigAnimationSpecs.java:1261
    public static void glaiveUltimate(ServerLevel serverLevel, Mob mob) {
        EnderGlaiveItem.spawnVacumSlise(serverLevel, mob, EnderGlaiveItem.DEFAULT_DAMAGE);
        if (mob instanceof GlaiveHerobrineEntity glaiveHerobrineEntity && glaiveHerobrineEntity.getState() == 1) glaiveHerobrineEntity.consumeSecondFormAction();
    }

    // Legacy hook: RigAnimationSpecs.java:1319
    public static void reaperSummonDragon(Mob mob) {
        if (!(mob instanceof ReaperHerobrineEntity reaper) || !(mob.level() instanceof ServerLevel serverLevel)) return;
        CommonUtil.spawnGroundSlamFracture(reaper, serverLevel, reaper.position(),
                1.25D, 60, 1.0D, 5.0D);
        reaper.completePendingDragonSummon();
    }

    // Legacy hook: RigAnimationSpecs.java:1386
    public static void consumeSledgehammerAction(Mob mob) {
        if (mob instanceof SledgehammerHerobrineEntity sledgehammer
                && sledgehammer.canUseSecondFormAction()) {
            sledgehammer.consumeSecondFormAction();
        }
    }

    // Legacy hook: RigAnimationSpecs.java:1402
    public static void startSledgehammerWaves(Mob mob) {
        if (!(mob instanceof SledgehammerHerobrineEntity sledgehammer)) return;
        sledgehammer.playSound(HerobrineVesselsModSounds.SLEDGE_HAMMER.get(), 1.0F, 1.0F);
        if (sledgehammer.canUseSecondFormAction()) sledgehammer.consumeSecondFormAction();
    }

    // Legacy hook: RigAnimationSpecs.java:1402
    public static void sledgehammerWave(Mob mob, float startRadius, float endRadius, int segments) {
        ObsidianSledgehammerItem.spawnWave(mob, mob.getYRot(), startRadius, endRadius, segments);
    }

    // Legacy hook: RigAnimationSpecs.java:1386
    public static void applyGroundStuck(LivingEntity target) {
        GroundStuckMobEffect.apply(target);
    }

    // Legacy hook: RigAnimationSpecs.java:1468
    public static void startSnakeAttack(Mob mob) {
        DemoniacVoltageReaverItem.tryStartSnakeAnimation(mob.getMainHandItem(), mob, false);
        if (mob instanceof HerobrineMob herobrineMob) {
            herobrineMob.consumeSecondFormAction();
        }
    }

    // Legacy hook: RigAnimationSpecs.java:1478
    public static void startSnakeUltimate(Mob mob) {
        DemoniacVoltageReaverItem.tryStartSnakeAnimation(mob.getMainHandItem(), mob, true);
        if (mob instanceof HerobrineMob herobrineMob) {
            herobrineMob.consumeSecondFormAction();
        }
    }

    // Legacy hook: RigAnimationSpecs.java:1510
    public static void startNullBlackHole(Mob mob) {
        if (!(mob.level() instanceof ServerLevel)) return;
        mob.level().playSound(null, mob.blockPosition(), HerobrineVesselsModSounds.BLACK_HOLE_CHARGE.get(), SoundSource.HOSTILE, 2.0F, 0.85F);
        RigAnimationSpecs.spawnNullBlackHoleChargeParticles(mob, 1);
    }

    // Legacy hook: RigAnimationSpecs.java:1532
    public static void spawnNullBlackHole(ServerLevel serverLevel, Mob mob, Vec3 spawnPosition) {
        serverLevel.addFreshEntity(new BlackHoleEntity(serverLevel, mob, spawnPosition));
        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, spawnPosition.x, spawnPosition.y, spawnPosition.z, 48, 0.8D, 0.8D, 0.8D, 0.18D);
        serverLevel.sendParticles(HerobrineVesselsModParticleTypes.NULL.get(), spawnPosition.x, spawnPosition.y, spawnPosition.z, 64, 1.4D, 1.4D, 1.4D, 0.12D);
    }

    // Legacy hook: RigAnimationSpecs.java:1618
    public static void releaseNullWeapon(Mob mob) {
        if (!(mob instanceof NullEntity nullEntity)) return;
        LivingEntity target = RigAnimationSpecs.getNullCombatTarget(nullEntity);
        if (target != null) nullEntity.releaseRandomNullWeapon(target);
    }

    // Legacy hook: RigAnimationSpecs.java:1634
    public static void startObsidianMachineGun(Mob mob) {
        if (!(mob instanceof ShadowHerobrineEntity shadowHerobrine)) return;
        if (!(mob.level() instanceof ServerLevel)) return;

        shadowHerobrine.setObsidianMachineGunTick();
        shadowHerobrine.playSound(HerobrineVesselsModSounds.SHADOW_HEROBRINE_SAY_OBSIDIAN_MACHINE_GUN.get(), 1.0F, 1.0F);
    }

    // Legacy hook: RigAnimationSpecs.java:1653
    public static void summonSmallObsidianCross(ServerLevel serverLevel, Mob mob, Item item) {
        BlockState state;
        if (item instanceof ShadowObsidianWeaponItem) {
            state = HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        } else if (item instanceof ShadowObsidianPillarItem) {
            state = HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, false).setValue(BlockStateProperties.HORIZONTAL_FACING, mob.getDirection());
        } else if (item instanceof ShadowObsidianSwordItem || mob.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
            state = HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, false).setValue(BlockStateProperties.HORIZONTAL_FACING, mob.getDirection());
        } else if (item instanceof ObsidianWeaponItem) {
            state = HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        } else {
            return;
        }

        HerobrineUtil.summonObsidianSmallCross(serverLevel, mob, state);
    }

    // Legacy hook: RigAnimationSpecs.java:1676
    public static void summonObsidianCross(ServerLevel serverLevel, Mob mob) {
        Item item = mob.getMainHandItem().getItem();
        if (item instanceof ShadowObsidianPillarItem) {
            HerobrineUtil.summonShadowObsidianLongPillarDefenseWide(serverLevel, mob);
            return;
        }

        BlockState state;
        if (item instanceof ShadowObsidianWeaponItem) {
            state = HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        } else if (item instanceof ObsidianWeaponItem) {
            state = HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        } else {
            return;
        }

        HerobrineUtil.summonObsidianCross(serverLevel, mob, state);
    }

    // Legacy hook: RigAnimationSpecs.java:1711
    public static void summonObsidianPillar(ServerLevel serverLevel, Mob mob) {
        Item item = mob.getMainHandItem().getItem();
        if (item instanceof ShadowObsidianPillarItem || item instanceof ShadowObsidianSwordItem) {
            HerobrineUtil.summonShadowObsidianLongPillarShootToward(serverLevel, mob);
            return;
        }

        BlockState state;
        if (item instanceof ShadowObsidianWeaponItem) {
            state = HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        } else if (item instanceof ObsidianWeaponItem) {
            state = HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState().setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        } else {
            return;
        }

        HerobrineUtil.summonObsidianPillarAtTarget(serverLevel, mob, state);
    }

    // Legacy hook: RigAnimationSpecs.java:1735
    public static void summonObsidianCube(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        BlockState state = HerobrineVessels.getObsidianWallState(mob);
        if (state != null) HerobrineUtil.summonObsidianCube3x3x3(serverLevel, mob, state);
    }

    // Legacy hook: RigAnimationSpecs.java:1806
    public static void summonDualSwordPillars(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        Item item = mob.getMainHandItem().getItem();
        if (item instanceof ShadowObsidianPillarItem) {
            HerobrineUtil.summonShadowObsidianLongPillarCircle(serverLevel, mob, mob.getOnPos());
            HerobrineUtil.summonShadowObsidianLongPillarShootToward(serverLevel, mob);
        } else if (item instanceof ShadowObsidianSwordItem) {
            HerobrineUtil.summonShadowObsidianLongPillarCircle(serverLevel, mob, mob.getOnPos());
        }
    }

    // Legacy hook: RigAnimationSpecs.java:1874
    public static BlockState getObsidianFistState(Mob mob) {
        Item item = mob.getMainHandItem().getItem();
        if (!(item instanceof ObsidianWeaponItem || item instanceof ShadowObsidianWeaponItem || item instanceof ShadowObsidianPillarItem)) return null;
        if (item instanceof ShadowObsidianPillarItem) {
            return HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, mob.getDirection());
        }
        if (item instanceof ShadowObsidianWeaponItem) {
            return HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        }
        return HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
    }

    // Legacy hook: RigAnimationSpecs.java:1892
    public static BlockState getObsidianWallState(Mob mob) {
        Item item = mob.getMainHandItem().getItem();
        if (item instanceof ShadowObsidianPillarItem || item instanceof ShadowObsidianSwordItem) {
            return HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, mob.getDirection());
        }
        if (item instanceof ShadowObsidianWeaponItem) {
            return HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        }
        if (item instanceof ObsidianWeaponItem) {
            return HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        }
        return null;
    }

    // Legacy hook: RigAnimationSpecs.java:1911
    public static BlockState getObsidianProjectileState(Mob mob) {
        Item item = mob.getMainHandItem().getItem();
        if (item instanceof ShadowObsidianWeaponItem) {
            return HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        }
        if (item instanceof ShadowObsidianSwordItem) {
            return HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, mob.getDirection());
        }
        if (item instanceof ShadowObsidianPillarItem) {
            return HerobrineVesselsModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, mob.getDirection());
        }
        if (item instanceof ObsidianWeaponItem) {
            return HerobrineVesselsModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState()
                    .setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
        }
        return null;
    }

    // Legacy hook: RigAnimationSpecs.java:1943
    public static void summonObsidianFromHand(ServerLevel serverLevel, Mob mob, BlockState state, int amount, Vec3 handPosition) {
        HerobrineUtil.summonObsidianBlocksFromPosition(serverLevel, mob, state, amount, handPosition);
    }

    // Legacy hook: RigAnimationSpecs.java:1951
    public static void summonObsidianWall(ServerLevel serverLevel, Mob mob, Item item) {
        if (item instanceof ShadowObsidianPillarItem || item instanceof ShadowObsidianSwordItem) {
            HerobrineUtil.summonShadowObsidianLongPillarDefense(serverLevel, mob);
            return;
        }

        BlockState state = HerobrineVessels.getObsidianWallState(mob);
        if (state != null) HerobrineUtil.summonObsidianWall3x3(serverLevel, mob, state);
    }

    // Legacy hook: RigAnimationSpecs.java:2134
    public static void spawnNullChargeParticle(ServerLevel serverLevel, Vec3 particlePosition, Vec3 velocity) {
        serverLevel.sendParticles(HerobrineVesselsModParticleTypes.NULL.get(), particlePosition.x, particlePosition.y, particlePosition.z, 0, velocity.x, velocity.y, velocity.z, 1.0D);
    }

    // Legacy hook: RigShieldGuardController.java:86
    public static void onAegisBlock(Mob mob, boolean aegisMainhand) {
        if (aegisMainhand
                && mob instanceof AegisHerobrineEntity aegisHerobrineEntity) {
            // State 1 spends one limited second-form action per successful block;
            // state 2 deliberately shoots on every successful block without decrementing.
            aegisHerobrineEntity.fireSecondFormShieldShot();
        }
    }

    // Legacy hook: RigShieldGuardController.java:164
    public static boolean hasAegisMainhand(Mob mob, ItemStack mainhand) {
        return mob instanceof AegisHerobrineEntity
                && !mainhand.isEmpty()
                && mainhand.getItem() instanceof EnderAegisItem;
    }

    // Legacy hook: SpecialAnimationSpecs.java:50
    public static void wardenSonicBoom(Mob mob) {
        if (mob instanceof AvWarden warden) warden.fireAvSonicBoom(15.0F);
    }

    // Legacy hook: AAAParticlesUtil.java:18
    public static void sendDragonBeam(Vec3 from, Vec3 to, Level level, Entity caster, LivingEntity target) {
        new DragonBeamParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath("herobrine_vessels", "dragon_beam"))
                .fromTo(from, to, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                .follow((HerobrineDragonEntity) caster, target, 120, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                .spawnInWorld(level, null);
    }

    // Legacy hook: CommonGoals.java:48
    public static boolean supportsRigCombat(Mob mob) {
        return mob instanceof HerobrineMob || mob instanceof LowHerobrineCloneEntity || mob instanceof LowShadowHerobrineCloneEntity;
    }

    // Legacy hook: CommonGoals.java:75
    public static void addDangerousReactionGoal(Mob mob, DangerousReaction dangerousReaction) {
        if (mob instanceof HerobrinePortalSupportCaster supportCaster) {
            mob.goalSelector.addGoal(-7, new HerobrinePortalDangerousReactionGoal(mob, dangerousReaction, supportCaster));
        } else {
            mob.goalSelector.addGoal(-7, new DangerousReactionGoal(mob, dangerousReaction));
        }
    }

    // Legacy hook: CommonGoals.java:121
    public static void addHostileMeleeGoals(PathfinderMob monster) {
        if (!(monster instanceof TransporterHerobrineCloneEntity)) {
            CommonGoals.addRigShieldGuardGoal(monster, 1);
            monster.goalSelector.addGoal(2, CommonGoals.createMeleeAttackGoal(monster, 1.2D, false));
        }
    }

    // Legacy hook: CommonGoals.java:134
    public static void addBlueDemonTargets(Monster monster) {
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, HerobrineMob.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, LowHerobrineCloneEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, LowShadowHerobrineCloneEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, EliteHerobrineKnockedEntity.class, true, false));
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, Player.class, true, false));
    }

    // Legacy hook: CommonGoals.java:165
    public static void addKnightTargets(PathfinderMob mob) {
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineMob.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));
    }

    // Legacy hook: CommonGoals.java:283
    public static void addCrazyTargets(PathfinderMob mob) {
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineMob.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));
    }

    // Legacy hook: CommonGoals.java:241
    public static void addNeutralTargets(PathfinderMob mob) {
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HerobrineMob.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LowShadowHerobrineCloneEntity.class, true, false));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, EliteHerobrineKnockedEntity.class, true, false));
        mob.targetSelector.addGoal(2, new HurtByTargetGoal(mob));
        mob.goalSelector.addGoal(2, CommonGoals.createMeleeAttackGoal(mob, 1.2D, false));
    }

    // Legacy hook: CommonGoals.java:297
    public static void addKnockedEliteTarget(PathfinderMob mob) {
        mob.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(mob, EliteHerobrineKnockedEntity.class, true, false));
    }

    // Legacy hook: CommonUtil.java:424
    public static boolean isEfnCombatMob(Entity livingEntity) {
        return livingEntity instanceof HerobrineMob || livingEntity instanceof HerobrineGregEntity || livingEntity instanceof LowHerobrineCloneEntity || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }

    // Legacy hook: HookUtil.java:672
    public static boolean isArmorHookBlacklisted(LivingEntity target) {
        return target instanceof NullWeapon || target instanceof ArmoredHerobrineEntity;
    }

    /** Client hooks load only from client-side callers. */
    public static final class Client {
        public static void sendDragonBeamHit(Level level, BlockPos hitBlock) {
            com.pla.herobrine_vessels.util.AAAParticlesUtil.sendDragonBeamHit(level, hitBlock);
        }

        public static void sendHerobrinePortal(Level level, double x, double y, double z) {
            com.pla.herobrine_vessels.util.AAAParticlesUtil.sendHerobrinePortal(level, x, y, z);
        }

        public static void sendHerobrineAssistance(Level level, double x, double y, double z) {
            com.pla.herobrine_vessels.util.AAAParticlesUtil.sendHerobrineAssistance(level, x, y, z);
        }

        private Client() {}

        private static final ResourceLocation INFECTED_PLAYER_BLOOD = ResourceLocation.fromNamespaceAndPath("herobrine_vessels", "textures/entities/player_mob_blood.png");

        public static boolean isInfectedPlayerBloodTexture(ResourceLocation texture) {
            return INFECTED_PLAYER_BLOOD.equals(texture);
        }

        public static ResourceLocation infectedPlayerBlood(LivingEntity entity) {
            if (ResourceLocation.fromNamespaceAndPath("herobrine_vessels", "infected_player_npc").equals(EntityType.getKey(entity.getType()))) {
                return INFECTED_PLAYER_BLOOD;
            }
            return null;
        }

        // Legacy hook: RigClientAnimationEvents.java:19
        public static void clearArmorAnimations() {
            ObsidianArmorClientAnimationState.clear();
        }

        // Legacy hook: VanillaOverlayTexturePicker.java:31
        public static ResourceLocation lowCloneEyes(LivingEntity entity) {
            if (entity instanceof LowHerobrineCloneEntity) {
                String name = entity.hasCustomName() ? entity.getCustomName().getString() : entity.getName().getString();
                return HerobrineEyesUtil.getHerobrineEyesTexture(name);
            }
            return null;
        }

        // Legacy hook: VanillaOverlayTexturePicker.java:39
        public static ResourceLocation herobrineEyes(LivingEntity entity) {
            if (entity instanceof HerobrineMob
                    || entity instanceof LowShadowHerobrineCloneEntity
                    || entity instanceof HerobrineGregEntity herobrineGreg && herobrineGreg.isWhiteEye()) {
                return ResourceLocation.fromNamespaceAndPath("herobrine_vessels", "textures/entities/herobrine_eyes/default/default.png");
            }
            return null;
        }

        // Legacy hook: ModelRig.java:249
        public static boolean isInfectedCorpse(Entity entity) {
            return entity instanceof InfectedPlayerNpcEntity || entity instanceof InfectedChrisEntity || entity instanceof InfectedTheMostMoistBurrit0Entity;
        }

        // Legacy hook: ColoredGlintState.java:37
        public static int herobrineGlint(ItemStack stack, LivingEntity wearer) {
            if (stack.is(HerobrineVesselsModItems.HEROBRINE_ENDER_EYE.get())) {
                return GlintColorHelper.ORANGE;
            } else if (HerobrineObsidianArmorCharge.isObsidianArmor(stack) && (HerobrineObsidianArmorCharge.isFullyCharged(stack) || HerobrineObsidianArmorCharge.hasForcedPurpleFoil(stack) || wearer instanceof ArmoredHerobrineEntity)) {
                return GlintColorHelper.PURPLE;
            }
            return GlintColorHelper.NONE;
        }

        // Legacy hook: RigColliderRenderer.java:85
        public static void renderArmorColliders(Minecraft mc, PoseStack poseStack, VertexConsumer lines) {
            for (var entry : ObsidianArmorClientAnimationState.snapshot().entrySet()) {
                Entity entity = mc.level.getEntity(entry.getKey().entityId());
                if (!(entity instanceof LivingEntity living)) continue;
                ObsidianArmorClientAnimationState.State active = ObsidianArmorClientAnimationState.get(living, entry.getKey().part());
                if (active == null) continue;
                float elapsed = active.elapsedTicks();
                boolean attackTime = elapsed >= ObsidianArmorController.ATTACK_START_TICK && elapsed < ObsidianArmorController.ATTACK_END_TICK_EXCLUSIVE;
                float green = attackTime ? 0.0F : 1.0F;
                float blue = attackTime ? 0.0F : 1.0F;
                for (RigOrientedBox box : ObsidianArmorColliderSystem.collisionBoxes(living, active.animationId(), elapsed)) RigColliderRenderer.renderBox(poseStack, lines, box, 1.0F, green, blue);
            }
        }

        // Legacy hook: RigItemVisualResolver.java:44
        public static ItemStack resolveRigItem(Entity entity, ItemStack originalStack, RigAnimationId animationId, float animationTick, boolean leftHand) {
            if ((originalStack.getItem() instanceof ObsidianWeaponItem
                    || originalStack.getItem() instanceof ShadowObsidianWeaponItem)
                    && (animationId == RigAnimationId.HEROBRINE_RUN
                    || animationId == RigAnimationId.FIST_ATTACK1 || animationId == RigAnimationId.FIST_ATTACK2
                    || animationId == RigAnimationId.FIST_ATTACK3 || animationId == RigAnimationId.FIST_ATTACK4
                    || animationId == RigAnimationId.FIST_ATTACK5 || animationId == RigAnimationId.FIST_EXTRA_ATTACK
                    || animationId == RigAnimationId.OBSIDIAN_EXTRA_ULT)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.getItem() instanceof ShadowObsidianPillarItem
                    && (animationId == RigAnimationId.FIST_ATTACK1 || animationId == RigAnimationId.FIST_ATTACK3
                    || animationId == RigAnimationId.FIST_ATTACK5 || animationId == RigAnimationId.OBSIDIAN_ULT1
                    || animationId == RigAnimationId.OBSIDIAN_ULT2 || animationId == RigAnimationId.FIST_EXTRA_ATTACK
                    || animationId == RigAnimationId.OBSIDIAN_EXTRA_ULT)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.getItem() instanceof ShadowObsidianPillarItem
                    && animationId == RigAnimationId.FIST_ATTACK2
                    && animationTick >= 4.0F) {
                return new ItemStack(HerobrineVesselsModItems.SHADOW_OBSIDIAN_BURST.get());
            }

            if (originalStack.getItem() instanceof ShadowObsidianPillarItem
                    && animationId == RigAnimationId.FIST_ATTACK4
                    && animationTick >= 10.0F) {
                return new ItemStack(HerobrineVesselsModItems.SHADOW_OBSIDIAN_BURST.get());
            }

            if (originalStack.getItem() instanceof ShadowObsidianPillarItem
                    && animationId == RigAnimationId.OBSIDIAN_PILLAR_EXTRA_ATTACK
                    && animationTick >= 12.0F) {
                return new ItemStack(HerobrineVesselsModItems.SHADOW_OBSIDIAN_BURST.get());
            }

            if (originalStack.getItem() instanceof ShadowObsidianSwordItem
                    && animationId == RigAnimationId.OBSIDIAN_PILLAR_EXTRA_ATTACK
                    && animationTick >= 12.0F) {
                return new ItemStack(HerobrineVesselsModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
            }

            if (originalStack.getItem() instanceof ShadowObsidianSwordItem
                    && animationId == RigAnimationId.OBSIDIAN_SWORD_ULT
                    && animationTick >= 9.0F) {
                return new ItemStack(HerobrineVesselsModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
            }

            if (originalStack.getItem() instanceof ShadowObsidianSwordItem
                    && (animationId == RigAnimationId.DUAL_OBSIDIAN_SWORD_ATTACK2 || animationId == RigAnimationId.DUAL_OBSIDIAN_SWORD_ATTACK3
                    || animationId == RigAnimationId.DUAL_OBSIDIAN_SWORD_JUMP_ATTACK || animationId == RigAnimationId.DUAL_OBSIDIAN_SWORD_ULT)) {
                return new ItemStack(HerobrineVesselsModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
            }

            if (entity instanceof ShadowHerobrineEntity
                    && leftHand && animationId == RigAnimationId.OBSIDIAN_MACHINE_GUN) {
                return new ItemStack(HerobrineVesselsModItems.HEROBRINE_ENDER_EYE.get());
            }
            return originalStack;
        }

        // Legacy hook: RigSwordTrailManager.java:406
        public static double maxContinuousTrailMove(Entity mob, double nullWeaponLimit, double defaultLimit) {
            return mob instanceof NullWeapon ? nullWeaponLimit : defaultLimit;
        }

        // Legacy hook: VanillaWeaponTooltipEvent.java:33
        public static void addChargeTooltips(ItemStack stack, Item item, List<Component> tooltip) {
            if (item instanceof NullWeaponItem) VanillaWeaponTooltipEvent.addChargeTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.null_charge", NullWeaponItem.getCharge(stack), 100);
            if (item instanceof EnderAegisItem) VanillaWeaponTooltipEvent.addChargeTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.aegis_charge", Math.round(EnderAegisItem.getCharge(stack)), 100);
        }

        // Legacy hook: VanillaWeaponTooltipEvent.java:42
        public static void addWeaponTooltips(Item item, List<Component> tooltip) {
            if (item instanceof ShadowObsidianPillarItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.shadow_obsidian_pillar.critical", "tooltip.annoyinglib.vanilla_combat.shadow_obsidian_pillar.right_click", "tooltip.annoyinglib.vanilla_combat.shadow_obsidian_pillar.special");
            else if (item instanceof ShadowObsidianSwordItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.shadow_obsidian_sword.right_click", "tooltip.annoyinglib.vanilla_combat.shadow_obsidian_sword.special");
            else if (item instanceof ShadowObsidianWeaponItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.shadow_obsidian_weapon.critical", "tooltip.annoyinglib.vanilla_combat.shadow_obsidian_weapon.right_click", "tooltip.annoyinglib.vanilla_combat.shadow_obsidian_weapon.special");
            else if (item instanceof ObsidianWeaponItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.obsidian_weapon.critical", "tooltip.annoyinglib.vanilla_combat.obsidian_weapon.right_click", "tooltip.annoyinglib.vanilla_combat.obsidian_weapon.special");
            else if (item instanceof EnderGlaiveItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.ender_glaive.right_click");
            else if (item instanceof EnderSlayerScytheItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.ender_slayer_scythe.special", "tooltip.annoyinglib.vanilla_combat.ender_slayer_scythe.left_click", "tooltip.annoyinglib.vanilla_combat.ender_slayer_scythe.right_click", "tooltip.annoyinglib.vanilla_combat.ender_slayer_scythe.lifetime");
            else if (item instanceof DemoniacVoltageReaverItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.demoniac_voltage_reaver.special", "tooltip.annoyinglib.vanilla_combat.demoniac_voltage_reaver.left_click", "tooltip.annoyinglib.vanilla_combat.demoniac_voltage_reaver.right_click");
            else if (item instanceof ObsidianSledgehammerItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.obsidian_sledgehammer.right_click", "tooltip.annoyinglib.vanilla_combat.obsidian_sledgehammer.special");
            else if (item instanceof EnderAegisItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.ender_aegis.block", "tooltip.annoyinglib.vanilla_combat.ender_aegis.full", "tooltip.annoyinglib.vanilla_combat.ender_aegis.second_form_block");
            else if (item instanceof NullWeaponItem) VanillaWeaponTooltipEvent.addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.null_weapon.offhand", "tooltip.annoyinglib.vanilla_combat.null_weapon.thresholds", "tooltip.annoyinglib.vanilla_combat.null_weapon.hold_special", "tooltip.annoyinglib.vanilla_combat.null_weapon.recovery");
        }

        // Legacy hook: ArrowLayerMixin.java:30
        public static void renderStuckArrow(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Entity entity, float x, float y, float z, float partialTick, EntityRenderDispatcher dispatcher, int glintMode, CallbackInfo ci) {
            if (entity instanceof HerobrineMob && ModList.get().isLoaded("villager_kingdom")) {
                float f = Mth.sqrt(x * x + z * z);

                AbstractArrow arrow = VillagerKingdom.createColoredArrow(entity, glintMode);

                arrow.setPos(entity.getX(), entity.getY(), entity.getZ());
                arrow.setYRot((float) (Math.atan2((double) x, (double) z) * (180F / Math.PI)));
                arrow.setXRot((float) (Math.atan2((double) y, (double) f) * (180F / Math.PI)));
                arrow.yRotO = arrow.getYRot();
                arrow.xRotO = arrow.getXRot();

                dispatcher.render(
                        arrow,
                        0.0D, 0.0D, 0.0D,
                        0.0F,
                        partialTick,
                        poseStack,
                        buffer,
                        packedLight
                );

                ci.cancel();
            }
        }

        // Legacy hook: ItemRendererMixin.java:24
        public static void setSnakeItemOwner(LivingEntity entity) {
            if (ModList.get().isLoaded("bettercombat")) BetterCombatSnakeAttachment.setItemOwner(entity);
        }

        // Legacy hook: ItemRendererMixin.java:24
        public static void captureSnakeSocket(ItemStack stack, ItemDisplayContext context, PoseStack pose) {
            if (ModList.get().isLoaded("bettercombat")) BetterCombatSnakeAttachment.capture(stack, context, pose);
        }

        // Legacy hook: MinecraftAttackMixin.java:35
        public static void onClientAttack(Player player, CallbackInfoReturnable<Boolean> cir) {
            if (player.getMainHandItem().getItem() instanceof EnderSlayerScytheItem && EnderSlayerScytheItem.isDragonActive(player.getMainHandItem())) {
                AnnoyingLib.PACKET_HANDLER.sendToServer(new VanillaAttackKeyMessage());
                VanillaWeaponAbilityUtil.swingOffHand(player);
                cir.setReturnValue(false);
                return;
            }

            if (player.getMainHandItem().getItem() instanceof DemoniacVoltageReaverItem && DemoniacVoltageReaverItem.isVanillaAwakened(player.getMainHandItem(), player.level())) {
                AnnoyingLib.PACKET_HANDLER.sendToServer(new VanillaAttackKeyMessage());
                VanillaWeaponAbilityUtil.swingMainHand(player);
                cir.setReturnValue(false);
            }
        }

        // Legacy hook: PunchyArmRendererMixin.java:20
        public static void skipObsidianSleeve(AbstractClientPlayer player, CallbackInfo ci) {
            if (player != null && HerobrineObsidianArmorCharge.isChestplate(player.getItemBySlot(EquipmentSlot.CHEST))) ci.cancel();
        }

        // Legacy hook: PunchyArmRendererMixin.java:67
        public static void renderObsidianArm(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource buffer, int light) {
            ObsidianArmorExtensionRenderer.renderFirstPersonRightArm(player, poseStack, buffer, light);
        }

        // Legacy hook: AAAParticlesUtil.java:61
        public static boolean sendTeleportPortal(Level level, Vec3 pos, Vec3 normal) {
            if (level == null || pos == null || !level.isClientSide()) {
                return false;
            }

            new TeleportPortalParticleEmitterInfo(
                    ResourceLocation.fromNamespaceAndPath("herobrine_vessels", "teleport_portal"))
                    .atPortal(pos, normal, TeleportPortalParticleEmitterInfo.ForwardAxis.PLUS_Z, 0.0F)
                    .spawnInWorld(level, Minecraft.getInstance().player);
            return true;
        }

        // Legacy hook: AnimationUtil.java:121
        public static AnimationDefinition idleAnimation(Mob mob) {
            if (mob instanceof EliteHerobrineKnockedEntity) {
                return HerobrineLivingAnimations.KNOCKED_ELITE;
            } else if (mob instanceof InfectedPlayerNpcEntity
                    || mob instanceof InfectedChrisEntity
                    || mob instanceof InfectedTheMostMoistBurrit0Entity) {
                return LivingAnimations.LAYING_DEATH;
            }
            return null;
        }
    }

    public static boolean isFakePlayer(Object value) { return value instanceof FakePlayer; }

    public static boolean canStartNullAttack(Mob mob, LivingEntity target, RigAnimationId animationId, RigAnimationSpec spec) {
        if (animationId == RigAnimationId.NULL_EXTRA_ATTACK) return RigColliderSystem.canStartAttackWithinDistance(mob, target, 24.0D);
        return RigColliderSystem.canStartAttack3D(mob, target, spec);
    }
}
