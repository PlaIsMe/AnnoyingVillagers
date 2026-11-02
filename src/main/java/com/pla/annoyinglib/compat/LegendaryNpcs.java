package com.pla.annoyinglib.compat;
import com.pla.legendary_npcs.network.ClientboundBlackFireFx;
import com.pla.legendary_npcs.item.BlackFireSwordItem;
import com.pla.legendary_npcs.entity.BlackFireEntity;
import com.pla.legendary_npcs.item.DiamondSpearItem;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraftforge.fml.ModList;
import java.util.UUID;

import com.pla.annoyinglib.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyinglib.init.AnnoyingLibModSounds;
import com.pla.annoyinglib.task.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import com.pla.legendary_npcs.init.LegendaryNpcsModSounds;
import com.pla.annoyinglib.rig.RigPart;
import com.pla.annoyinglib.util.RigPoseUtil;
import net.minecraft.core.particles.ParticleTypes;
import com.pla.annoyinglib.rig.RigAnimationId;
import com.pla.legendary_npcs.network.ClientboundWoopieSwordWindFx;
import net.minecraft.world.item.Item;
import com.pla.legendary_npcs.item.HookGunItem;
import net.minecraftforge.network.PacketDistributor;
import net.minecraft.core.BlockPos;
import com.pla.legendary_npcs.spawnhandler.ChrisData;
import net.minecraft.sounds.SoundEvent;
import com.pla.annoyinglib.rig.RigAnimationController;
import net.minecraft.world.entity.player.Player;
import com.pla.legendary_npcs.spawnhandler.AlexData;
import com.pla.legendary_npcs.spawnhandler.SteveData;
import com.pla.legendary_npcs.init.LegendaryNpcsModItems;
import net.minecraft.sounds.SoundSource;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.AVNpc;
import com.pla.legendary_npcs.entity.AlexEntity;
import com.pla.legendary_npcs.entity.AngrySteveEntity;
import com.pla.legendary_npcs.entity.ChrisEntity;
import com.pla.legendary_npcs.entity.JevEntity;
import com.pla.legendary_npcs.entity.SteveEntity;
import com.pla.legendary_npcs.item.BlueFlameSwordItem;
import com.pla.legendary_npcs.item.ClowSwordItem;
import com.pla.legendary_npcs.item.DNAxHookedSwordItem;
import com.pla.legendary_npcs.item.EarthAxeItem;
import com.pla.legendary_npcs.item.FlankerHookedSwordItem;
import com.pla.legendary_npcs.item.HackerSwordItem;
import com.pla.legendary_npcs.item.LegendarySwordItem;
import com.pla.legendary_npcs.item.RedAxeItem;
import com.pla.legendary_npcs.item.ThunderDiamondBladeItem;
import com.pla.legendary_npcs.item.TonyTheFishingRod;
import com.pla.legendary_npcs.item.WoopieTheSwordItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/** Optional content bridge. Call only when its mod is loaded. */
public final class LegendaryNpcs {
    private LegendaryNpcs() {}

    public static boolean isAngrySteveEntity(Object value) {
        return value instanceof AngrySteveEntity;
    }

    public static boolean isSteveEntity(Object value) {
        return value instanceof SteveEntity;
    }

    public static boolean isAlexEntity(Object value) {
        return value instanceof AlexEntity;
    }

    public static boolean isChrisEntity(Object value) {
        return value instanceof ChrisEntity;
    }

    public static boolean isJevEntity(Object value) {
        return value instanceof JevEntity;
    }

    public static boolean isRedAxeItem(Object value) {
        return value instanceof RedAxeItem;
    }

    public static boolean isHackerSwordItem(Object value) {
        return value instanceof HackerSwordItem;
    }

    public static boolean isEarthAxeItem(Object value) {
        return value instanceof EarthAxeItem;
    }

    public static boolean isWoopieTheSwordItem(Object value) {
        return value instanceof WoopieTheSwordItem;
    }

    public static boolean isThunderDiamondBladeItem(Object value) {
        return value instanceof ThunderDiamondBladeItem;
    }

    public static boolean isLegendarySwordItem(Object value) {
        return value instanceof LegendarySwordItem;
    }



    public static boolean isBlueFlameSwordItem(Object value) {
        return value instanceof BlueFlameSwordItem;
    }

    public static boolean isClowSwordItem(Object value) {
        return value instanceof ClowSwordItem;
    }









    public static boolean isFlankerHookedSwordItem(Object value) {
        return value instanceof FlankerHookedSwordItem;
    }

    public static boolean isDNAxHookedSwordItem(Object value) {
        return value instanceof DNAxHookedSwordItem;
    }

    public static boolean isTonyTheFishingRod(Object value) {
        return value instanceof TonyTheFishingRod;
    }

    public static void enchantNpcBow(AVNpc npc, ItemStack bow) {
        if ((npc instanceof SteveEntity steveEntity && steveEntity.getState() == 1)
                || npc instanceof AngrySteveEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
            if (npc instanceof AngrySteveEntity) {
                bow.enchant(Enchantments.FLAMING_ARROWS, 2);
            }
        }
        if (npc instanceof AlexEntity alexEntity && alexEntity.getState() == 1) {
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.FLAMING_ARROWS, 1);
        }
        if (npc instanceof ChrisEntity chrisEntity && chrisEntity.getState() == 1) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
        }
    }

    public static void registerGoalForHostileNpcTargets(PathfinderMob monster) {
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, SteveEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AngrySteveEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, ChrisEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, AlexEntity.class, true, false));
        monster.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(monster, JevEntity.class, true, false));
    }

    public static void registerGoalForBlueDemonNpcTargets(PathfinderMob monster) {
        monster.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(monster, AngrySteveEntity.class, true, false));
    }

    public static void registerGoalForVillagerKnightNpcTargets(PathfinderMob mob) {
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, SteveEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, AngrySteveEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, AlexEntity.class, true, false));
        mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, ChrisEntity.class, true, false));
    }

    public static LivingEntity getVillagerKnightJevReplacementTarget(Mob mob, @Nullable LivingEntity target) {
        if (!(target instanceof JevEntity jev)) {
            return null;
        }

        AlexEntity alex = jev.getFollowTarget();
        if (alex != null && alex.isAlive() && !alex.isSpectator() && !mob.isAlliedTo(alex)) {
            return alex;
        }

        return null;
    }

    public static void sendWoopieWind(Level level, double x, double y, double z) {
        com.pla.legendary_npcs.util.AAAParticlesUtil.sendWoopieWind(level, x, y, z);
    }




    public static void rigAction3(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            EarthAxeItem.summonEarthWall(serverLevel, mob);
        }
    }

    public static void rigAction4(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            Vec3 bladePos = RigPoseUtil.getPartPosition(mob, RigAnimationId.EARTH_AXE_EXTRA_ATTACK, 20.0F, RigPart.RIGHT_WEAPON, Vec3.ZERO, 0.5D, 0.0D);
            BlockPos liftPos = EarthAxeItem.findLiftableBlockUnderPoint(serverLevel, bladePos, 6, 1);
            if (liftPos != null) {
                EarthAxeItem.liftBlockAt(serverLevel, liftPos, mob);
            }
        }
    }





    public static void rigAction7(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        mob.setDeltaMovement(Vec3.ZERO);
        mob.hasImpulse = true;
        mob.hurtMarked = true;

        Vec3 offHandPos = RigPoseUtil.getLeftWeaponPosition(mob, RigAnimationId.WOOPIE_THE_SWORD_FLY, 0.0F);
        Vec3 windPos = new Vec3(offHandPos.x, mob.getY() + 0.05D, offHandPos.z);
        com.pla.annoyinglib.AnnoyingLib.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundMuteExplosionAtPos(BlockPos.containing(windPos), 4));
        serverLevel.explode(mob, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
        com.pla.legendary_npcs.LegendaryNpcs.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundWoopieSwordWindFx(windPos));
    }

    public static void rigAction8(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        Vec3 windPos = RigPoseUtil.getPartPosition(mob, RigAnimationId.WOOPIE_THE_SWORD_ULT,
                4.0F, RigPart.RIGHT_WEAPON, Vec3.ZERO, 4.3D, 0.5D);
        com.pla.annoyinglib.AnnoyingLib.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob),
                new ClientboundMuteExplosionAtPos(BlockPos.containing(windPos), 4));
        serverLevel.explode(mob, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
        com.pla.legendary_npcs.LegendaryNpcs.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob),
                new ClientboundWoopieSwordWindFx(windPos));
    }

    public static void rigAction20(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), AnnoyingLibModSounds.HEAVY_ATTACK_START.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), LegendaryNpcsModSounds.HEAVY_ATTACK_LEGENDARY_SWORD.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), LegendaryNpcsModSounds.HEAVY_ATTACK_LEGENDARY_SWORD_2.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, mob.getX(), mob.getY(), mob.getZ(), 15, 0.0D, 0.0D, 0.0D, 0.2D);
        serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, mob.getX(), mob.getEyeY(), mob.getZ(), 100, 0.0D, 0.0D, 0.0D, 0.5D);
    }

    public static void rigAction21(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        Vec3 legendarySwordPos = RigPoseUtil.getRightWeaponPosition(mob, RigAnimationId.LEGENDARY_SWORD_ULT, 10, 1.5D);

        BlockPos centerPos = BlockPos.containing(legendarySwordPos);
        for (int radius = 1; radius <= 6; radius++) {
            int delayTicks = (radius - 1) * 2;
            int ringRadius = radius;
            new DelayedTask(delayTicks) {
                @Override
                public void run() {
                    if (mob.isRemoved()) return;
                    LegendarySwordItem.spawnCircleRing(serverLevel, centerPos, ringRadius, mob);
                }
            };
        }
    }

    public static void rigAction22(Mob mob) {
        if (mob instanceof AngrySteveEntity angrySteve) angrySteve.startLegendaryAwakening();
    }

    public static void rigAction23(Mob mob, RigAnimationId animationId) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        boolean mainHandWoopie = mob.getMainHandItem().is(LegendaryNpcsModItems.WOOPIE_THE_SWORD.get());
        boolean offHandWoopie = mob.getOffhandItem().is(LegendaryNpcsModItems.WOOPIE_THE_SWORD.get());
        if (!mainHandWoopie && !offHandWoopie) return;

        Vec3 windPos = offHandWoopie ? RigPoseUtil.getLeftWeaponPosition(mob, animationId, 0.0F) : RigPoseUtil.getRightWeaponPosition(mob, animationId, 0.0F);
        if (windPos == null) return;

        com.pla.annoyinglib.AnnoyingLib.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundMuteExplosionAtPos(BlockPos.containing(windPos), 4));
        serverLevel.explode(mob, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
        com.pla.legendary_npcs.LegendaryNpcs.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundWoopieSwordWindFx(windPos));

        Vec3 dashDir = mob.getLookAngle();
        LivingEntity target = mob.getTarget();

        if (target != null && target.isAlive()) {
            Vec3 toTarget = target.position().subtract(mob.position());
            dashDir = new Vec3(toTarget.x, 0.0D, toTarget.z);
        }

        dashDir = new Vec3(dashDir.x, 0.0D, dashDir.z);
        if (dashDir.lengthSqr() < 1.0E-6D) dashDir = Vec3.directionFromRotation(0.0F, mob.getYRot());

        Vec3 dash = dashDir.normalize().scale(2.2D);

        for (int tick = 1; tick <= 3; tick++) {
            new DelayedTask(tick) {
                @Override
                public void run() {
                    if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
                    if (RigAnimationController.getActiveAnimationId(mob) != animationId) return;

                    Vec3 currentMotion = mob.getDeltaMovement();
                    mob.setDeltaMovement(dash.x, currentMotion.y, dash.z);
                    mob.hasImpulse = true;
                    mob.hurtMarked = true;
                }
            };
        }
    }

    public static boolean hookGunItem_hasAttachedHook(Level level, LivingEntity owner) {
        return HookGunItem.hasAttachedHook(level, owner);
    }

    public static boolean redAxeItem_isGiantForm(ItemStack stack, Level level) {
        return RedAxeItem.isGiantForm(stack, level);
    }

    public static boolean hookGunItem_tryBindFromSpecialAttack(Player player) {
        return HookGunItem.tryBindFromSpecialAttack(player);
    }

    public static boolean earthAxeItem_activateVanillaSpecial(Player player) {
        return EarthAxeItem.activateVanillaSpecial(player);
    }

    public static boolean woopieTheSwordItem_activateVanillaSpecial(Player player) {
        return WoopieTheSwordItem.activateVanillaSpecial(player);
    }

    public static boolean legendarySwordItem_activateVanillaSpecial(Player player) {
        return LegendarySwordItem.activateVanillaSpecial(player);
    }

    public static boolean hackerSwordItem_isComboAttack(Player player) {
        return HackerSwordItem.isComboAttack(player);
    }

    public static boolean legendarySwordItem_hasActiveVanillaAwakening(Player player) {
        return LegendarySwordItem.hasActiveVanillaAwakening(player);
    }

    public static void legendarySwordItem_clearVanillaAttackSpeed(Player player) {
        LegendarySwordItem.clearVanillaAttackSpeed(player);
    }

    private static SteveData steveData_get(ServerLevel serverLevel) {
        return SteveData.get(serverLevel);
    }

    private static AlexData alexData_get(ServerLevel serverLevel) {
        return AlexData.get(serverLevel);
    }

    private static ChrisData chrisData_get(ServerLevel serverLevel) {
        return ChrisData.get(serverLevel);
    }







    public static Item getItems_TONY_THE_FISHING_ROD() {
        return LegendaryNpcsModItems.TONY_THE_FISHING_ROD.get();
    }

    public static Item getItems_HOOK_GUN() {
        return LegendaryNpcsModItems.HOOK_GUN.get();
    }







    public static Item getItems_RED_AXE() {
        return LegendaryNpcsModItems.RED_AXE.get();
    }



    public static Item getItems_THUNDER_DIAMOND_BLADE() {
        return LegendaryNpcsModItems.THUNDER_DIAMOND_BLADE.get();
    }

    public static Item getItems_EARTH_AXE() {
        return LegendaryNpcsModItems.EARTH_AXE.get();
    }

    public static Item getItems_LEGENDARY_SWORD() {
        return LegendaryNpcsModItems.LEGENDARY_SWORD.get();
    }

    public static Item getItems_WOOPIE_THE_SWORD() {
        return LegendaryNpcsModItems.WOOPIE_THE_SWORD.get();
    }

    public static Item getItems_BLUE_FLAME_SWORD() {
        return LegendaryNpcsModItems.BLUE_FLAME_SWORD.get();
    }



    public static SoundEvent getSounds_WOOPIE_WIND() {
        return LegendaryNpcsModSounds.WOOPIE_WIND.get();
    }

    public static Item getItems_GIANT_RED_AXE() {
        return LegendaryNpcsModItems.GIANT_RED_AXE.get();
    }

    public static Item getItems_DNAX_HOOKED_SWORD_ABILITY() {
        return LegendaryNpcsModItems.DNAX_HOOKED_SWORD_ABILITY.get();
    }

    public static Item getItems_HEAVY_ATTACK_LEGENDARY_SWORD() {
        return LegendaryNpcsModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get();
    }
    public static boolean isLegendaryAwakened(Entity mob) {
        return mob instanceof AngrySteveEntity angrySteve && angrySteve.isLegendaryAwakened();
    }

    public static void addCrazyNpcTargets(PathfinderMob mob) {
        mob.targetSelector.addGoal(20, new NearestAttackableTargetGoal<>(mob, AlexEntity.class, false, false));
        mob.targetSelector.addGoal(20, new NearestAttackableTargetGoal<>(mob, ChrisEntity.class, false, false));
    }

    public static void handleWoopieSwordWind(Object packet) { com.pla.legendary_npcs.client.engine.ClientPacketHandlers.handleWoopieSwordWind((ClientboundWoopieSwordWindFx) packet); }





    public static void vacate(ServerLevel level, String identity, UUID id) {
        // Clear matching claims in any previous dimension too.
        for (ServerLevel previous : level.getServer().getAllLevels()) switch (identity) {
            case "Steve" -> (ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.steveData_get(previous) : null).vacateIfMatches(id);
            case "Alex" -> (ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.alexData_get(previous) : null).vacateIfMatches(id);
            case "Chris" -> (ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.chrisData_get(previous) : null).vacateIfMatches(id);
        }
    }

    public static void release(ServerLevel level, String identity, UUID id) {
        for (ServerLevel previous : level.getServer().getAllLevels()) switch (identity) {
            case "Steve" -> SteveData.get(previous).releaseIfMatches(previous, id);
            case "Alex" -> AlexData.get(previous).releaseIfMatches(previous, id);
            case "Chris" -> ChrisData.get(previous).releaseIfMatches(previous, id);
        }
    }

    public static void claim(ServerLevel level, String identity, UUID id) {
        switch (identity) {
            case "Steve" -> SteveData.get(level).forceClaim(level, id);
            case "Alex" -> AlexData.get(level).forceClaim(level, id);
            case "Chris" -> ChrisData.get(level).forceClaim(level, id);
        }
    }

    public static void addGregAvoidAlexEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, AlexEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidChrisEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, ChrisEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidJevEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, JevEntity.class, 12.0F, 1.0D, 1.35D)); }


    public static void addGregAvoidSteveEntity(PathfinderMob mob) { mob.goalSelector.addGoal(2, new AvoidEntityGoal<>(mob, SteveEntity.class, 24.0F, 1.0D, 1.35D)); }


    public static boolean isDiamondSpearItem(Object value) { return value instanceof DiamondSpearItem; }





    public static boolean isBlackFireSwordItem(Object value) {
        return value instanceof BlackFireSwordItem;
    }

    public static void sendBlackFire(Level level, Entity entity) {
        com.pla.legendary_npcs.util.AAAParticlesUtil.sendBlackFire(level, entity);
    }

    public static void rigAction5(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            BlackFireEntity.shootFromOwnerLook(serverLevel, mob);
        }
    }

    public static Vec3 blackFireSwordItem_getBlackFireFallbackPosition(Entity entity) {
        return BlackFireSwordItem.getBlackFireFallbackPosition(entity);
    }

    public static void blackFireSwordItem_startBlackFireFallback(Level level, int entityId) {
        BlackFireSwordItem.startBlackFireFallback(level, entityId);
    }

    public static Vec3 blackFireSwordItem_getSwordOrBodyPosition(Entity entity) {
        return BlackFireSwordItem.getSwordOrBodyPosition(entity);
    }

    public static Item getItems_BLACK_FIRE_SWORD() {
        return LegendaryNpcsModItems.BLACK_FIRE_SWORD.get();
    }

    public static void handleBlackFire(Object packet) { com.pla.legendary_npcs.client.engine.ClientPacketHandlers.handleBlackFire((ClientboundBlackFireFx) packet); }
}
