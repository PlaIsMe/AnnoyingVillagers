package com.pla.annoyinglib.compat;
import com.pla.mob_supremacy.item.NetheriteSpearItem;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import com.pla.smart_npc.entity.PlayerNpcEntity;
import com.pla.mob_supremacy.entity.goal.EscapeAvoidGoal;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import com.pla.annoyinglib.rig.RigAnimationController;
import net.minecraft.server.level.ServerLevel;
import com.pla.annoyinglib.rig.RigPart;
import com.pla.annoyinglib.util.RigPoseUtil;
import com.pla.annoyinglib.rig.RigAnimationSpecs;
import net.minecraft.world.entity.projectile.AbstractArrow;
import com.pla.mob_supremacy.init.MobSupremacyModEntities;
import net.minecraft.world.entity.Mob;
import com.pla.annoyinglib.rig.RigAnimationId;
import com.pla.mob_supremacy.util.BlueDemonUtil;
import com.pla.mob_supremacy.entity.BlueDemonThrownTridentEntity;
import com.pla.mob_supremacy.init.MobSupremacyModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import com.pla.annoyinglib.util.ScreenShakeUtil;
import com.pla.mob_supremacy.entity.ElectricPhaseEntity;
import com.pla.annoyinglib.clazz.TridentMode;
import com.pla.mob_supremacy.init.MobSupremacyModSounds;
import net.minecraftforge.fml.ModList;
import com.pla.mob_supremacy.entity.BbqEntity;
import com.pla.mob_supremacy.entity.BlueDemonEntity;
import com.pla.mob_supremacy.entity.BlueDemonThunderBeamEntity;
import com.pla.mob_supremacy.entity.TridentLightningBolt;
import com.pla.mob_supremacy.init.MobSupremacyModMobEffects;
import com.pla.mob_supremacy.item.BlueDemonChestplateItem;
import com.pla.mob_supremacy.item.BlueDemonTridentItem;
import com.pla.mob_supremacy.network.ClientboundBlueDemonEffectFx;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Optional content bridge. Call only when its mod is loaded. */
public final class MobSupremacy {
    private MobSupremacy() {}

    public static boolean isBlueDemonEntity(Object value) {
        return value instanceof BlueDemonEntity;
    }

    public static boolean isBbqEntity(Object value) {
        return value instanceof BbqEntity;
    }

    public static boolean isBlueDemonChestplateItem(Object value) {
        return value instanceof BlueDemonChestplateItem;
    }

    public static boolean isBlueDemonTridentItem(Object value) {
        return value instanceof BlueDemonTridentItem;
    }

    public static boolean isTridentLightningBolt(Object value) {
        return value instanceof TridentLightningBolt;
    }

    public static void registerGoalForHostileNpcTargets(PathfinderMob monster) {
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, BlueDemonEntity.class, true, false));
    }

    public static void registerGoalForVillagerKnightNpcTargets(PathfinderMob mob) {
        mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, BlueDemonEntity.class, true, false));
    }

    public static void handleBlueDemonDamage(LivingEntity wearer, ItemStack chest, float finalDamage, Entity sourceEntity) {
        if (!BlueDemonChestplateItem.isBlueDemonChestplate(chest)) return;
        if (BlueDemonChestplateItem.isBuffActive(chest)) {
            if (sourceEntity instanceof LivingEntity attacker && attacker != wearer) {
                float chance = wearer.getRandom().nextFloat();
                if (chance <= 0.2F) attacker.addEffect(new MobEffectInstance(MobSupremacyModMobEffects.ELECTRIFY.get(), 20, 2));
                else if (chance <= 0.6F) attacker.addEffect(new MobEffectInstance(MobSupremacyModMobEffects.ELECTRIFY.get(), 20, 1));
            }
            return;
        }
        if (!BlueDemonChestplateItem.isFullyCharged(chest)) BlueDemonChestplateItem.addStoredCharge(chest, Math.max(1, Mth.ceil(finalDamage)));
    }

    public static void handleBlueDemonEffectFx(Object packet) { com.pla.mob_supremacy.client.engine.ClientPacketHandlers.handleBlueDemonEffectFx((ClientboundBlueDemonEffectFx) packet); }

    public static void sendBlueDemonThunderBeam(Level level, Entity beam) {
        com.pla.mob_supremacy.util.AAAParticlesUtil.sendBlueDemonThunderBeam(level, beam);
    }
    public static void rigAction1(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isThunderDiamondBladeItem(mob.getMainHandItem().getItem()))) {
            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, mob, false);
        }
    }

    public static void rigAction2(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isThunderDiamondBladeItem(mob.getMainHandItem().getItem()))) {
            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, mob, false);
        }

        if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isThunderDiamondBladeItem(mob.getOffhandItem().getItem()))) {
            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, mob, true);
        }
    }

    public static void rigAction9(Mob mob) {
        if (!(mob.level() instanceof ServerLevel)) return;
        if (!(mob instanceof BlueDemonEntity blueDemonEntity)) return;
        blueDemonEntity.setState(1);
    }

    public static void rigAction10(Mob mob) {
        if (!(mob.level() instanceof ServerLevel)) return;
        if (!(mob instanceof BlueDemonEntity blueDemonEntity)) return;
        blueDemonEntity.playSound(MobSupremacyModSounds.BLUE_DEMON_SAY_TRIDENT_FESTIVAL.get(), 1.0F, 1.0F);
    }

    public static void rigAction11(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        if ((ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonEntity(mob))) BlueDemonTridentItem.summonMissingTridentAndAnimate(serverLevel, mob);
        ScreenShakeUtil.applyScreenShake(serverLevel, mob.blockPosition().getCenter(), 12.0D, 80, 8);
    }

    public static void rigAction12(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        BlueDemonTridentItem.spawnDamageZones(serverLevel, mob);
        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, mob, true);
    }

    public static void rigAction13(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, mob, true);
        RigAnimationSpecs.playBlueDemonTridentEffect(mob, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL, 24, RigPart.RIGHT_HAND);
        RigAnimationSpecs.playBlueDemonTridentEffect(mob, RigAnimationId.BLUE_DEMON_TRIDENT_FESTIVAL, 24, RigPart.LEFT_HAND);
    }

    public static void rigAction14(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        BlueDemonTridentItem.summonSuperLightningAtGroundedTridents(serverLevel, mob);
        BlueDemonTridentItem.setStormEnergy(mob.getMainHandItem(), 0);
        BlueDemonTridentItem.setStormEnergy(mob.getOffhandItem(), 0);
        if (mob instanceof BlueDemonEntity blueDemonEntity) {
            blueDemonEntity.beginStateTwoTransform();
            RigAnimationController.play(blueDemonEntity, RigAnimationId.BLUE_DEMON_STATE_TRANSFORM);
        }
    }

    public static void rigAction15(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        Vec3 tridentTip = RigPoseUtil.getRightWeaponPosition(mob, RigAnimationId.BLUE_DEMON_ATTACK5, 16, 1.2D);
        if (tridentTip == null) return;

        BlockPos.MutableBlockPos checkPos = BlockPos.containing(tridentTip).mutable();
        while (checkPos.getY() > serverLevel.getMinBuildHeight() && !serverLevel.getBlockState(checkPos).isSolidRender(serverLevel, checkPos)) checkPos.move(0, -1, 0);
        if (!serverLevel.getBlockState(checkPos).isSolidRender(serverLevel, checkPos)) return;

        TridentLightningBolt lightningBolt = new TridentLightningBolt(MobSupremacyModEntities.TRIDENT_LIGHTNING_BOLT.get(), serverLevel);
        lightningBolt.setOwner(mob);
        lightningBolt.moveTo(checkPos.getX() + 0.5D, checkPos.getY() + 1.0D, checkPos.getZ() + 0.5D);
        serverLevel.addFreshEntity(lightningBolt);
    }

    public static void rigAction16(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) BlueDemonTridentItem.spawnDamageZones(serverLevel, mob);
    }

    public static void rigAction17(Mob mob) {
        RigAnimationSpecs.playBlueDemonTridentEffect(mob, RigAnimationId.BLUE_DEMON_THROW_ATTACK3, 6, RigPart.RIGHT_WEAPON);
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        BlueDemonThunderBeamEntity beam = new BlueDemonThunderBeamEntity(MobSupremacyModEntities.BLUE_DEMON_THUNDER_BEAM.get(), serverLevel, mob, 10, 6, 7.5F);
        beam.initSpawnState();
        serverLevel.addFreshEntity(beam);
    }

    public static void rigAction18(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, mob);
    }

    public static void rigAction19(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) BlueDemonTridentItem.summonLightningAtGroundedTridents(serverLevel, mob);
    }

    public static void rigAction24(Mob mob, int tick, RigAnimationId animationId, InteractionHand hand, TridentMode mode) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        ItemStack stack = mob.getItemInHand(hand);
        if (!((ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonTridentItem(stack.getItem())))) return;

        Vec3 spawnPos = hand == InteractionHand.MAIN_HAND
                ? RigPoseUtil.getRightHandPosition(mob, animationId, tick)
                : RigPoseUtil.getLeftHandPosition(mob, animationId, tick);
        if (spawnPos == null) return;

        Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(mob, spawnPos);
        if (direction == null || direction.lengthSqr() < 1.0E-7D) return;

        BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, mob, stack.copy());
        trident.assignSpawnSequence(mob);
        trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
        trident.setMode(mode);
        trident.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180.0D / Math.PI)));
        trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180.0D / Math.PI)));
        trident.pickup = AbstractArrow.Pickup.DISALLOWED;
        trident.shoot(direction.x, direction.y, direction.z, 2.5F, 1.0F);
        serverLevel.addFreshEntity(trident);
    }

    public static boolean blueDemonChestplateItem_isBlueDemonChestplate(ItemStack stack) {
        return BlueDemonChestplateItem.isBlueDemonChestplate(stack);
    }

    public static void blueDemonChestplateItem_stopBuff(ItemStack stack) {
        BlueDemonChestplateItem.stopBuff(stack);
    }

    public static boolean blueDemonTridentItem_activateVanillaFestival(Player player) {
        return BlueDemonTridentItem.activateVanillaFestival(player);
    }

    public static void blueDemonTridentItem_activateVanillaThunderAttack(Player player) {
        BlueDemonTridentItem.activateVanillaThunderAttack(player);
    }

    public static void blueDemonTridentItem_activateVanillaElectricField(Player player) {
        BlueDemonTridentItem.activateVanillaElectricField(player);
    }

    public static boolean blueDemonChestplateItem_isFullyCharged(ItemStack stack) {
        return BlueDemonChestplateItem.isFullyCharged(stack);
    }

    public static void blueDemonChestplateItem_activateBuff(ItemStack stack) {
        BlueDemonChestplateItem.activateBuff(stack);
    }

    public static void blueDemonUtil_spawnBlueDemonEffect(Level level, Entity trackingEntity, Vec3 pos, int count, double xOffset, double yOffset, double zOffset, double speed) {
        BlueDemonUtil.spawnBlueDemonEffect(level, trackingEntity, pos, count, xOffset, yOffset, zOffset, speed);
    }

    public static boolean blueDemonTridentItem_isBlueDemonTrident(ItemStack stack) {
        return BlueDemonTridentItem.isBlueDemonTrident(stack);
    }

    public static boolean blueDemonTridentItem_isFullyCharged(ItemStack stack) {
        return BlueDemonTridentItem.isFullyCharged(stack);
    }

    public static boolean blueDemonChestplateItem_isBuffActive(ItemStack stack) {
        return BlueDemonChestplateItem.isBuffActive(stack);
    }

    public static boolean blueDemonChestplateItem_hasBlueDemonHealingFoil(ItemStack stack) {
        return BlueDemonChestplateItem.hasBlueDemonHealingFoil(stack);
    }

    public static Item getItems_BLUE_DEMON_TRIDENT() {
        return MobSupremacyModItems.BLUE_DEMON_TRIDENT.get();
    }

    public static SoundEvent getSounds_ELECTRIFY() {
        return MobSupremacyModSounds.ELECTRIFY.get();
    }

    public static Item getItems_BLUE_DEMON_CHESTPLATE() {
        return MobSupremacyModItems.BLUE_DEMON_CHESTPLATE.get();
    }
    public static boolean isBbqEscapeActive(Mob mob) {
        return mob instanceof BlueDemonEntity blueDemon && blueDemon.isBbqHoleEscapeActive();
    }

    public static boolean isBlueDemonUnlocked(Mob mob) {
        return mob instanceof BlueDemonEntity blueDemon && !blueDemon.isLocked();
    }

    public static void addCrazyNpcTargets(PathfinderMob mob) {
        mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(mob, BlueDemonEntity.class, false, false));
    }

    public static void cancelOwnedTridentDamage(LivingHurtEvent event, Player player) {
        if (event.getSource().getDirectEntity() instanceof BlueDemonThrownTridentEntity trident && trident.getOwner() == player) {
            event.setCanceled(true);
            return;
        }
        if (event.getSource().getDirectEntity() instanceof TridentLightningBolt lightning && lightning.getOwner() == player) event.setCanceled(true);
    }

    public static void addEscapeGoal(PathfinderMob self, Class<? extends LivingEntity> targetType) {
        self.goalSelector.addGoal(1, new EscapeAvoidGoal<>((BbqEntity) self, targetType, 12.0F, 2.0D, 2.0D));
    }

    public static void addSmartNpcEscapeGoal(PathfinderMob self) {
        self.goalSelector.addGoal(1, new EscapeAvoidGoal<>((BbqEntity) self, PlayerNpcEntity.class, 12.0F, 2.0D, 2.0D));
    }

    public static boolean isBlueDemonThrownTridentEntity(Object value) { return value instanceof BlueDemonThrownTridentEntity; }


    public static void addGregAvoidBbqEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, BbqEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidBlueDemonEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, BlueDemonEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void spawnElectricPhase(ServerLevel level, LivingEntity owner, boolean offhand) {
        ElectricPhaseEntity.spawnOnOwnerSword(level, owner, offhand);
    }

    public static void extendObsidianStunCooldown(Entity entity) {
        if (entity instanceof BlueDemonEntity blueDemon) {
            int currentValue = blueDemon.getStunEscapeCooldown();
            if (currentValue < 100) blueDemon.setStunEscapeCooldown(currentValue + 20);
        }
    }


    public static Item getItems_GEM_SHIELD() {
        return MobSupremacyModItems.GEM_SHIELD.get();
    }

    public static Item getItems_NETHERITE_SHIELD() {
        return MobSupremacyModItems.NETHERITE_SHIELD.get();
    }

    public static Item getItems_SPEAR_AXE() {
        return MobSupremacyModItems.SPEAR_AXE.get();
    }

    public static boolean isNetheriteSpearItem(Object value) { return value instanceof NetheriteSpearItem; }
}
