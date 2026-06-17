package com.pla.annoyingvillagers.item;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.pla.annoyingvillagers.capabilities.SnakeBladeCapability;
import com.pla.annoyingvillagers.entity.PortalEntity;
import com.pla.annoyingvillagers.entity.SnakeBladeEntity;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModCapabilities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class DemoniacVoltageReaverItem extends SwordItem {
    private static final double TARGET_SEARCH_RADIUS = 16.0D;
    private static final double PORTAL_TARGET_SEARCH_RADIUS = 64.0D;

    public DemoniacVoltageReaverItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 3.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 4;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(AnnoyingVillagersModItems.ELITE_OBSIDIAN.get());
            }
        }, 3, -3.0F, (new Properties()));
    }

    public static boolean checkNearbyTarget(LivingEntity attacker) {
        Level level = attacker.level();
        Entity closestValid = null;

        Vec3 attackerEyes = attacker.getEyePosition(1.0F);
        level.clip(new ClipContext(
                attackerEyes,
                attackerEyes.add(attacker.getLookAngle().scale(16.0D)),
                ClipContext.Block.VISUAL,
                ClipContext.Fluid.NONE,
                attacker
        ));

        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(TARGET_SEARCH_RADIUS))) {
            if (!entity.equals(attacker)
                    && !attacker.isAlliedTo(entity)
                    && !entity.isAlliedTo(attacker)
                    && !entity.isSpectator()
                    && !(entity instanceof Player player && player.isCreative())
                    && (entity instanceof Mob || entity instanceof Player)
                    && attacker.hasLineOfSight(entity)) {
                if (closestValid == null || attacker.distanceTo(entity) < attacker.distanceTo(closestValid)) {
                    closestValid = entity;
                }
            }
        }
        return closestValid != null || findClosestPortalTarget(attacker) != null;
    }

    public static void process(ItemStack stack, LivingEntity attacker) {
        Level level = attacker.level();
        Entity closestValid = findClosestPortalTarget(attacker);

        Vec3 attackerEyes = attacker.getEyePosition(1.0F);
        level.clip(new ClipContext(
                attackerEyes,
                attackerEyes.add(attacker.getLookAngle().scale(16.0D)),
                ClipContext.Block.VISUAL,
                ClipContext.Fluid.NONE,
                attacker
        ));

        if (closestValid == null) {
            for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(TARGET_SEARCH_RADIUS))) {
                if (!entity.equals(attacker)
                        && !attacker.isAlliedTo(entity)
                        && !entity.isAlliedTo(attacker)
                        && !entity.isSpectator()
                        && !(entity instanceof Player player && player.isCreative())
                        && (entity instanceof Mob || entity instanceof Player)
                        && attacker.hasLineOfSight(entity)) {
                    if (closestValid == null || attacker.distanceTo(entity) < attacker.distanceTo(closestValid)) {
                        closestValid = entity;
                    }
                }
            }
        }
        launchSnakeBladeAt(attacker, closestValid, stack);
    }

    private static PortalEntity findClosestPortalTarget(LivingEntity attacker) {
        Level level = attacker.level();
        PortalEntity closestPortal = null;
        UUID attackerUuid = attacker.getUUID();

        for (PortalEntity portal : level.getEntitiesOfClass(PortalEntity.class, attacker.getBoundingBox().inflate(PORTAL_TARGET_SEARCH_RADIUS))) {
            if (portal.isRemoved()) {
                continue;
            }

            UUID ownerUuid = portal.getOwnerUUID();
            if (ownerUuid != null && !ownerUuid.equals(attackerUuid)) {
                continue;
            }

            if (closestPortal == null || isBetterInitialPortal(attacker, portal, closestPortal)) {
                closestPortal = portal;
            }
        }

        return closestPortal;
    }

    private static boolean isBetterInitialPortal(LivingEntity attacker, PortalEntity candidate, PortalEntity current) {
        double candidateDistance = attacker.distanceTo(candidate);
        double currentDistance = attacker.distanceTo(current);
        if (candidateDistance < currentDistance) {
            return true;
        }
        if (candidateDistance > currentDistance) {
            return false;
        }

        if (candidate.isStarterPortal() != current.isStarterPortal()) {
            return candidate.isStarterPortal();
        }

        int candidateOrder = candidate.getPortalOrder() < 0 ? Integer.MAX_VALUE : candidate.getPortalOrder();
        int currentOrder = current.getPortalOrder() < 0 ? Integer.MAX_VALUE : current.getPortalOrder();
        if (candidateOrder != currentOrder) {
            return candidateOrder < currentOrder;
        }

        return false;
    }

    public static void processGuard(ItemStack stack, LivingEntity entityToGuard) {
        Level level = entityToGuard.level();
        SnakeBladeCapability.ISnakeBladeCapability snakeBladeCapability =
                AnnoyingVillagersModCapabilities.getCapability(entityToGuard, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);

        if (snakeBladeCapability != null) {
            if (canLaunchSnakeBlades(level, entityToGuard)) {
                retractFarFragments(level, entityToGuard);
                if (!level.isClientSide) {
                    launchSnakeBladeAt(entityToGuard, stack);
                }
            }
        }
    }

    public static void launchSnakeBladeAt(LivingEntity attacker, Entity closestValid, ItemStack stack) {
        Level level = attacker.level();
        SnakeBladeCapability.ISnakeBladeCapability snakeBladeCapability =
                AnnoyingVillagersModCapabilities.getCapability(attacker, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);

        if (snakeBladeCapability != null) {
            if (canLaunchSnakeBlades(level, attacker)) {
                retractFarFragments(level, attacker);
                if (!level.isClientSide) {
                    if (closestValid != null) {
                        SnakeBladeEntity snakeBladeEntity = AnnoyingVillagersModEntities.SNAKE_BLADE.get().create(level);
                        if (snakeBladeEntity != null) {
                            if (stack.hasFoil()) {
                                snakeBladeEntity.setEnchanted(true);
                            }
                            snakeBladeEntity.copyPosition(attacker);
                            level.addFreshEntity(snakeBladeEntity);
                            snakeBladeEntity.setCreatorEntityUUID(attacker.getUUID());
                            snakeBladeEntity.setFromEntityID(attacker.getId());
                            snakeBladeEntity.setToEntityID(closestValid.getId());
                            snakeBladeEntity.copyPosition(attacker);
                            snakeBladeEntity.setProgress(0.0F);
                            setLastFragment(attacker, snakeBladeEntity);
                        }
                    }
                }
            }
        }
    }

    public static boolean launchSnakeBladeAt(LivingEntity attacker, ItemStack stack) {
        Level level = attacker.level();
        SnakeBladeEntity snakeBladeEntity = AnnoyingVillagersModEntities.SNAKE_BLADE.get().create(level);
        if (snakeBladeEntity == null) return false;

        if (stack.hasFoil()) {
            snakeBladeEntity.setEnchanted(true);
        }

        snakeBladeEntity.setCreatorEntityUUID(attacker.getUUID());
        snakeBladeEntity.setFromEntityID(attacker.getId());
        snakeBladeEntity.setToEntityID(-1);
        snakeBladeEntity.setProgress(0.0F);
        snakeBladeEntity.setGuardDirection("forward_left");

        Vec3 spawn = guardTargetFor(attacker, "forward_left");
        snakeBladeEntity.setPos(spawn.x, spawn.y, spawn.z);

        level.addFreshEntity(snakeBladeEntity);
        setLastFragment(attacker, snakeBladeEntity);
        return true;
    }

    public static Vec3 guardTargetFor(LivingEntity ent, String direction) {
        Random random = new Random();
        if ("forward_left".equalsIgnoreCase(direction)) {
            return LocalSpace.localOffsetPos(ent, 1, 0, -1);
        } else if ("forward_right".equalsIgnoreCase(direction)) {
            return LocalSpace.localOffsetPos(ent, 2, 1, 1);
        } else if ("backward_right".equalsIgnoreCase(direction)) {
            return LocalSpace.localOffsetPos(ent, -1, 0, 2);
        } else {
            return LocalSpace.localOffsetPos(ent, -1, 2, -1);
        }
    }

    public static void setLastFragment(LivingEntity entity, SnakeBladeEntity snakeBladeEntity) {
        SnakeBladeCapability.ISnakeBladeCapability snakeBladeCapability =
                AnnoyingVillagersModCapabilities.getCapability(entity, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);

        if (snakeBladeCapability != null) {
            snakeBladeCapability.setHasSnakeBlade(snakeBladeEntity != null);

            if (snakeBladeEntity != null) {
                snakeBladeCapability.setLastSnakeBladeID(snakeBladeEntity.getId());
                snakeBladeCapability.setLastSnakeBladeUUID(snakeBladeEntity.getUUID());
            } else {
                snakeBladeCapability.setLastSnakeBladeID(-1);
                snakeBladeCapability.setLastSnakeBladeUUID(null);
            }
        }
    }

    public static void retractFarFragments(Level level, LivingEntity livingEntity) {
        SnakeBladeEntity last = getLastFragment(livingEntity);
        if (last != null) {
            last.remove(Entity.RemovalReason.DISCARDED);
            setLastFragment(livingEntity, null);
        }
    }

    public static boolean canLaunchSnakeBlades(Level level, LivingEntity livingEntity) {
        SnakeBladeEntity last = getLastFragment(livingEntity);
        if (last != null) {
            return last.isRemoved();
        }
        return true;
    }

    public static SnakeBladeEntity getLastFragment(LivingEntity livingEntity) {
        SnakeBladeCapability.ISnakeBladeCapability snakeBladeCapability =
                AnnoyingVillagersModCapabilities.getCapability(livingEntity, AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY);

        if (snakeBladeCapability == null) return null;

        UUID uuid = snakeBladeCapability.getLastSnakeBladeUUID();
        int id = snakeBladeCapability.getLastSnakeBladeID();
        Level level = livingEntity.level();

        Entity found = null;

        if (!level.isClientSide) {
            if (uuid != null && level instanceof ServerLevel serverLevel) {
                found = serverLevel.getEntity(uuid);
            }
            if (found == null && id != -1) {
                found = level.getEntity(id);
            }
        } else {
            if (id != -1) {
                found = level.getEntity(id);
            }
        }

        if (!(found instanceof SnakeBladeEntity snakeBladeEntity) || !found.isAlive()) {
            return null;
        }
        return snakeBladeEntity;
    }

    public static Vec3 getToolTipPos(Entity ent, float partialTicks, float handToTip) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (patch == null) return null;

        OpenMatrix4f joint = patch.getArmature()
                .getBoundTransformFor(patch.getAnimator().getPose(partialTicks), Armatures.BIPED.get().toolR);

        OpenMatrix4f localOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
        OpenMatrix4f.mul(joint, localOffset, joint);

        float yawRad = (float) -Math.toRadians(((LivingEntity) ent).yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, joint, joint);

        return new Vec3(
                joint.m30 + ent.getX(),
                joint.m31 + (ent.getY() + (ent.getBbHeight() / 1.8F) - 1.0F),
                joint.m32 + ent.getZ()
        );
    }

    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(Component.translatable("tooltip.annoyingvillagers.demoniac_voltage_reaver").getString()));
    }

    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag && entity instanceof Player player) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.DEMONIAC_VOLTAGE_REAVER);
                if (skillContainer != null) {
                    if (skillContainer.getStack() >= 1) {
                        HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
                        if (itemstack.getTag() != null && !itemstack.getTag().getBoolean("SecondForm")) {
                            itemstack.getTag().putBoolean("SecondForm", true);
                        }
                    } else if (skillContainer.getStack() < 1 && itemstack.getTag() != null && itemstack.getTag().getBoolean("SecondForm")) {
                        itemstack.getTag().remove("SecondForm");
                    }
                }
            }
        }
        if (!flag && itemstack.getTag().getBoolean("SnakeAnimation")) {
            itemstack.getTag().remove("SnakeAnimation");
        }
    }

    public static final class LocalSpace {
        private static final Vec3 UP = new Vec3(0, 1, 0);

        public static Vec3 forward(LivingEntity e) {
            float yawRad = e.yBodyRot * Mth.DEG_TO_RAD;
            return new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad)).normalize();
        }

        public static Vec3 right(LivingEntity e) {
            Vec3 f = forward(e);
            return UP.cross(f).normalize();
        }

        public static Vec3 left(LivingEntity e) {
            return right(e).scale(-1.0D);
        }

        public static Vec3 back(LivingEntity e) {
            return forward(e).scale(-1.0D);
        }

        public static Vec3 localOffsetPos(LivingEntity e, double leftU, double upU, double forwardU) {
            Vec3 base = e.position();
            Vec3 off = left(e).scale(leftU)
                    .add(UP.scale(upU))
                    .add(forward(e).scale(forwardU));
            return base.add(off);
        }
    }
}
