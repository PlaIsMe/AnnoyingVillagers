package com.pla.annoyingvillagers.rig.armor;

import com.pla.annoyingvillagers.util.EnchantmentUtil;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.HerobrineObsidianArmorCharge;
import com.pla.annoyingvillagers.network.ClientboundObsidianArmorAnimation;
import com.pla.annoyingvillagers.rig.LockableRigAttackAnimation;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigOrientedBox;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationController;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class ObsidianArmorController {
    public static final int DURATION_TICKS = 15;
    public static final int ATTACK_START_TICK = 2;
    public static final int ATTACK_END_TICK_EXCLUSIVE = 12;
    public static final float CHESTPLATE_BASE_DAMAGE = 8.0F;
    public static final float HELMET_BASE_DAMAGE = 6.0F;
    public static final double MIN_KNOCKBACK_BLOCKS = 6.0D;
    public static final double MAX_KNOCKBACK_BLOCKS = 12.0D;
    private static final double KNOCKBACK_STRENGTH_PER_BLOCK = 0.4D;
    private static final Map<UUID, ActiveState> ACTIVE = new HashMap<>();

    private ObsidianArmorController() {}

    public static boolean activateChargedArmor(ServerPlayer player) {
        EnumMap<ObsidianArmorPart, SpecialAnimationId> parts = new EnumMap<>(ObsidianArmorPart.class);
        for (ObsidianArmorPart part : ObsidianArmorPart.values()) {
            ItemStack stack = player.getItemBySlot(part.slot());
            if (isMatchingArmor(stack, part) && HerobrineObsidianArmorCharge.isFullyCharged(stack)) {
                parts.put(part, randomAnimation(player, part));
            }
        }
        return !parts.isEmpty() && start(player, parts, true, false);
    }

    public static boolean activateArmoredHerobrine(Mob mob) {
        EnumMap<ObsidianArmorPart, SpecialAnimationId> parts = new EnumMap<>(ObsidianArmorPart.class);
        for (ObsidianArmorPart part : ObsidianArmorPart.values()) {
            ItemStack stack = mob.getItemBySlot(part.slot());
            if (isMatchingArmor(stack, part)) parts.put(part, randomAnimation(mob, part));
        }
        return !parts.isEmpty() && start(mob, parts, false, true);
    }

    public static boolean isActive(LivingEntity wearer) {
        return getState(wearer) != null;
    }

    public static boolean isInAttackWindow(LivingEntity wearer) {
        ActiveState state = getState(wearer);
        if (state == null) return false;
        int elapsed = state.elapsedTicks(wearer);
        return elapsed >= ATTACK_START_TICK && elapsed < ATTACK_END_TICK_EXCLUSIVE;
    }

    public static void tick(LivingEntity wearer) {
        if (wearer.level().isClientSide) return;
        ActiveState state = getState(wearer);
        if (state == null) return;
        for (Map.Entry<ObsidianArmorPart, ItemStack> entry : state.equipment().entrySet()) {
            if (wearer.getItemBySlot(entry.getKey().slot()) != entry.getValue()) {
                clear(wearer);
                return;
            }
        }
        if (!wearer.isAlive() || wearer.isRemoved() || wearer.isDeadOrDying() || state.elapsedTicks(wearer) >= DURATION_TICKS) {
            finish(wearer, state);
            return;
        }
        if (isInAttackWindow(wearer)) attack(wearer, state);
    }

    public static List<RigOrientedBox> activeAttackCollisionBoxes(LivingEntity wearer) {
        ActiveState state = getState(wearer);
        if (state == null) return List.of();
        int elapsed = state.elapsedTicks(wearer);
        if (elapsed < ATTACK_START_TICK || elapsed >= ATTACK_END_TICK_EXCLUSIVE) return List.of();
        return collisionBoxes(wearer, state, elapsed);
    }

    public static void clear(LivingEntity wearer) {
        if (wearer.level().isClientSide) return;
        ActiveState state = ACTIVE.remove(wearer.getUUID());
        if (state != null) {
            releaseLock(state);
            for (SpecialAnimationId animation : state.animations().values()) sendAnimation(wearer, animation, 0);
        }
    }

    public static void clearAll() {
        for (ActiveState state : List.copyOf(ACTIVE.values())) releaseLock(state);
        ACTIVE.clear();
    }

    private static boolean start(LivingEntity wearer, EnumMap<ObsidianArmorPart, SpecialAnimationId> parts,
                                 boolean consumeCharge, boolean allowConcurrentAttack) {
        if (wearer.level().isClientSide || !wearer.isAlive() || wearer.isRemoved() || ACTIVE.containsKey(wearer.getUUID())) return false;
        if (!allowConcurrentAttack && wearer instanceof Mob mob
                && (RigAnimationController.hasActiveProfileAttack(mob) || SpecialAnimationController.hasActiveAnimation(mob))) return false;

        boolean locked = false;
        if (!allowConcurrentAttack && wearer instanceof LockableRigAttackAnimation lockable) {
            lockable.lock();
            locked = true;
        }

        // Keep the actual equipped stacks so swapping for another copy of the same
        // item cancels the attack, while changing charge/durability NBT does not.
        EnumMap<ObsidianArmorPart, ItemStack> equipment = new EnumMap<>(ObsidianArmorPart.class);
        for (ObsidianArmorPart part : parts.keySet()) equipment.put(part, wearer.getItemBySlot(part.slot()));
        ActiveState state = new ActiveState(wearer, new EnumMap<>(parts), equipment, wearer.tickCount, new HashSet<>(), locked);
        ACTIVE.put(wearer.getUUID(), state);
        if (consumeCharge) for (ObsidianArmorPart part : parts.keySet()) HerobrineObsidianArmorCharge.setCharge(wearer.getItemBySlot(part.slot()), 0);
        if (wearer.level() instanceof ServerLevel serverLevel) serverLevel.playSound(null, wearer.getX(), wearer.getY(), wearer.getZ(), AnnoyingVillagersModSounds.OB_PLACE.get(), wearer instanceof ServerPlayer ? SoundSource.PLAYERS : SoundSource.HOSTILE, 1.0F, 1.0F);
        for (SpecialAnimationId animationId : parts.values()) sendAnimation(wearer, animationId, DURATION_TICKS);
        return true;
    }

    private static void attack(LivingEntity wearer, ActiveState state) {
        if (!(wearer.level() instanceof ServerLevel serverLevel)) return;
        EnumMap<ObsidianArmorPart, List<RigOrientedBox>> boxesByPart = collisionBoxesByPart(wearer, state, state.elapsedTicks(wearer));
        List<RigOrientedBox> boxes = flatten(boxesByPart);
        if (boxes.isEmpty()) return;
        AABB bounds = boxes.get(0).bounds();
        for (int i = 1; i < boxes.size(); i++) bounds = bounds.minmax(boxes.get(i).bounds());
        for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, bounds.inflate(0.1D), target -> canDamage(wearer, target))) {
            if (state.hitEntities().contains(target.getUUID())) continue;
            ObsidianArmorPart hitPart = hitPart(boxesByPart, target.getBoundingBox());
            if (hitPart == null) continue;
            if (clashesWithTargetAttack(wearer, target, boxes, serverLevel)) {
                state.hitEntities().add(target.getUUID());
                continue;
            }
            state.hitEntities().add(target.getUUID());
            hurt(wearer, target, hitPart);
        }
    }

    private static ObsidianArmorPart hitPart(EnumMap<ObsidianArmorPart, List<RigOrientedBox>> boxesByPart, AABB targetBounds) {
        // The chestplate has the higher base damage, so prefer it if both armor pieces
        // happen to intersect the same target during the same extension.
        List<RigOrientedBox> chestBoxes = boxesByPart.get(ObsidianArmorPart.CHESTPLATE);
        if (chestBoxes != null && intersects(chestBoxes, targetBounds)) return ObsidianArmorPart.CHESTPLATE;
        List<RigOrientedBox> helmetBoxes = boxesByPart.get(ObsidianArmorPart.HELMET);
        if (helmetBoxes != null && intersects(helmetBoxes, targetBounds)) return ObsidianArmorPart.HELMET;
        return null;
    }

    private static void hurt(LivingEntity wearer, LivingEntity target, ObsidianArmorPart part) {
        DamageSource source;
        if (wearer instanceof ServerPlayer player) source = wearer.level().damageSources().playerAttack(player);
        else if (wearer instanceof Mob mob) source = wearer.level().damageSources().mobAttack(mob);
        else return;

        float damage = armorSpikeDamage(wearer, part);
        int previousInvulnerableTime = target.invulnerableTime;
        target.invulnerableTime = 0;
        boolean hurt = target.hurt(source, damage);
        target.invulnerableTime = previousInvulnerableTime;
        if (!hurt) return;

        Vec3 away = target.position().subtract(wearer.position());
        if (away.horizontalDistanceSqr() > 1.0E-6D) {
            double desiredBlocks = MIN_KNOCKBACK_BLOCKS + wearer.getRandom().nextDouble() * (MAX_KNOCKBACK_BLOCKS - MIN_KNOCKBACK_BLOCKS);
            target.knockback(desiredBlocks * KNOCKBACK_STRENGTH_PER_BLOCK, -away.x, -away.z);
        }
        if (wearer instanceof Mob mob) mob.setLastHurtMob(target);
    }

    public static float armorSpikeDamage(LivingEntity wearer, ObsidianArmorPart part) {
        float baseDamage = part == ObsidianArmorPart.CHESTPLATE ? CHESTPLATE_BASE_DAMAGE : HELMET_BASE_DAMAGE;
        ItemStack armor = wearer.getItemBySlot(part.slot());
        int protectionLevel = EnchantmentUtil.getLevel(Enchantments.PROTECTION, armor);
        // Reuse vanilla Sharpness' exact level-to-damage curve, but feed it the
        // Protection level from the armor piece that produced this spike hit.
        float protectionBonus = protectionLevel > 0
                ? 0.5F * protectionLevel + 0.5F
                : 0.0F;
        return baseDamage + protectionBonus;
    }

    private static boolean clashesWithTargetAttack(LivingEntity wearer, LivingEntity target, List<RigOrientedBox> armorBoxes, ServerLevel level) {
        if (!(target instanceof Mob targetMob)) return false;
        List<RigOrientedBox> attackBoxes = new ArrayList<>();
        attackBoxes.addAll(RigAnimationController.activeAttackCollisionBoxes(targetMob));
        attackBoxes.addAll(SpecialAnimationController.activeAttackCollisionBoxes(targetMob));
        if (!intersectsAny(armorBoxes, attackBoxes)) return false;
        applyClashRecoil(wearer);
        applyClashRecoil(target);
        CommonUtil.damageBlockedForce(wearer, target, level);
        return true;
    }

    private static List<RigOrientedBox> collisionBoxes(LivingEntity wearer, ActiveState state, float elapsedTicks) {
        return flatten(collisionBoxesByPart(wearer, state, elapsedTicks));
    }

    private static EnumMap<ObsidianArmorPart, List<RigOrientedBox>> collisionBoxesByPart(LivingEntity wearer, ActiveState state, float elapsedTicks) {
        EnumMap<ObsidianArmorPart, List<RigOrientedBox>> boxesByPart = new EnumMap<>(ObsidianArmorPart.class);
        for (Map.Entry<ObsidianArmorPart, SpecialAnimationId> entry : state.animations().entrySet()) {
            List<RigOrientedBox> partBoxes = ObsidianArmorColliderSystem.collisionBoxes(wearer, entry.getValue(), elapsedTicks);
            if (!partBoxes.isEmpty()) boxesByPart.put(entry.getKey(), partBoxes);
        }
        return boxesByPart;
    }

    private static List<RigOrientedBox> flatten(EnumMap<ObsidianArmorPart, List<RigOrientedBox>> boxesByPart) {
        if (boxesByPart.isEmpty()) return List.of();
        List<RigOrientedBox> boxes = new ArrayList<>();
        for (List<RigOrientedBox> partBoxes : boxesByPart.values()) boxes.addAll(partBoxes);
        return boxes.isEmpty() ? List.of() : List.copyOf(boxes);
    }

    private static boolean canDamage(LivingEntity wearer, LivingEntity target) {
        if (target == wearer || !target.isAlive() || target.isRemoved() || target.isDeadOrDying()) return false;
        if (areAllied(wearer, target)) return false;
        return wearer.canAttack(target);
    }

    private static boolean areAllied(LivingEntity source, LivingEntity target) {
        Team sourceTeam = source.getTeam();
        Team targetTeam = target.getTeam();
        if (sourceTeam != null && targetTeam != null && (sourceTeam == targetTeam || sourceTeam.isAlliedTo(targetTeam) || targetTeam.isAlliedTo(sourceTeam))) return true;
        return source.isAlliedTo(target) || target.isAlliedTo(source);
    }

    private static boolean intersects(List<RigOrientedBox> boxes, AABB target) {
        for (RigOrientedBox box : boxes) if (box.intersects(target)) return true;
        return false;
    }

    public static boolean intersectsAny(List<RigOrientedBox> first, List<RigOrientedBox> second) {
        for (RigOrientedBox a : first) for (RigOrientedBox b : second) if (a.intersects(b)) return true;
        return false;
    }

    private static void applyClashRecoil(LivingEntity entity) {
        Vec3 look = new Vec3(entity.getLookAngle().x, 0.0D, entity.getLookAngle().z);
        if (look.lengthSqr() < 1.0E-7D) return;
        Vec3 recoil = look.normalize().scale(-0.2D);
        entity.setDeltaMovement(recoil.x, 0.0D, recoil.z);
        entity.hasImpulse = true;
        entity.hurtMarked = true;
    }

    private static SpecialAnimationId randomAnimation(LivingEntity wearer, ObsidianArmorPart part) {
        return part.animationId(1 + wearer.getRandom().nextInt(part.animationCount()));
    }

    private static boolean isMatchingArmor(ItemStack stack, ObsidianArmorPart part) {
        return part == ObsidianArmorPart.HELMET
                ? HerobrineObsidianArmorCharge.isHelmet(stack) : HerobrineObsidianArmorCharge.isChestplate(stack);
    }

    private static ActiveState getState(LivingEntity wearer) {
        if (wearer.level().isClientSide) return null;
        ActiveState state = ACTIVE.get(wearer.getUUID());
        if (state == null) return null;
        if (state.wearer() != wearer || state.elapsedTicks(wearer) < 0 || state.elapsedTicks(wearer) > DURATION_TICKS) {
            if (ACTIVE.remove(wearer.getUUID(), state)) releaseLock(state);
            return null;
        }
        return state;
    }

    private static void finish(LivingEntity wearer, ActiveState state) {
        if (ACTIVE.remove(wearer.getUUID(), state)) releaseLock(state);
    }

    private static void releaseLock(ActiveState state) {
        if (state.lockedProfileAttack() && state.wearer() instanceof LockableRigAttackAnimation lockable) lockable.unlock();
    }

    private static void sendAnimation(LivingEntity wearer, SpecialAnimationId animationId, int durationTicks) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(wearer, new ClientboundObsidianArmorAnimation(wearer.getId(), animationId, durationTicks));
    }

    private record ActiveState(LivingEntity wearer, EnumMap<ObsidianArmorPart, SpecialAnimationId> animations,
                               EnumMap<ObsidianArmorPart, ItemStack> equipment, int startTick,
                               Set<UUID> hitEntities, boolean lockedProfileAttack) {
        private int elapsedTicks(LivingEntity entity) {
            return entity.tickCount - this.startTick;
        }
    }
}
