package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.entity.EnchantedEnderPearlEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ThrowingPearlKeyPressedEvent {
    private static Vec3 getFrontLeftPos(Entity entity) {
        Vec3 base = (entity instanceof LivingEntity le)
                ? le.getEyePosition(1.0F)
                : entity.position().add(0.0, entity.getBbHeight() * 0.85, 0.0);

        base = base.add(0.0, -0.1, 0.0);

        Vec3 forward = entity.getLookAngle();
        Vec3 forwardH = new Vec3(forward.x, 0.0, forward.z);
        if (forwardH.lengthSqr() < 1.0E-6) {
            forwardH = entity.getForward();
            forwardH = new Vec3(forwardH.x, 0.0, forwardH.z);
        }
        forwardH = forwardH.normalize();

        Vec3 left = new Vec3(0.0, 1.0, 0.0).cross(forwardH);
        if (left.lengthSqr() < 1.0E-6) {
            left = new Vec3(1.0, 0.0, 0.0);
        } else {
            left = left.normalize();
        }

        return base.add(forwardH.scale(0.35)).add(left.scale(0.25));
    }

    private static boolean efmConditionToExecute(Entity entity) {
//        Add this code in AV_EFM

//        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
//        if (livingEntityPatch == null) return false;
//        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
//        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, livingEntityPatch)) {
//            return false;
//        }
//        if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
//            return false;
//        }
        return true;
    }

    private static void playThrowingPearlAnimation(Entity entity) {
//        Add Animation in AV_EFM

//        livingEntityPatch.playAnimationSynchronized(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F);
//        Create VANILLA_ANIMATION
    }

    public static void execute(final Entity entity) {
        if (entity != null) {
            if (!(entity.level() instanceof ServerLevel)) return;
            if (!efmConditionToExecute(entity)) {
                return;
            }
            if (entity instanceof Player player) {
                boolean used = player.getInventory().items.stream()
                        .filter(s -> !s.isEmpty() && s.is(AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()))
                        .findFirst()
                        .map(stack -> {
                            playThrowingPearlAnimation(entity);
                            Level level = entity.level();
                            var projectile = new EnchantedEnderPearlEntity(
                                    AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), level);
                            projectile.setOwner(entity);
                            Vec3 handPos = getFrontLeftPos(entity);

                            projectile.setPos(handPos.x, handPos.y, handPos.z);
                            projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.5F, 0.0F);
                            level.addFreshEntity(projectile);
                            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
                            stack.hurtAndBreak(1, player, p -> {
                            });
                            return true;
                        }).orElse(false);

                if (used) {
                    return;
                }
            }

            Level level;
            Projectile projectile;

            if (entity instanceof Player player) {

                if (player.getInventory().contains(new ItemStack(Items.ENDER_PEARL))) {
                    playThrowingPearlAnimation(entity);
                    level = entity.level();
                    projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                    projectile.setOwner(entity);

                    Vec3 handPos = getFrontLeftPos(entity);
                    projectile.setPos(handPos.x, handPos.y, handPos.z);
                    projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.5F, 0.0F);
                    level.addFreshEntity(projectile);

                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
                    Player player2 = (Player) entity;
                    ItemStack itemStack = new ItemStack(Items.ENDER_PEARL);

                    player2.getInventory().clearOrCountMatchingItems((stack) -> itemStack.getItem() == stack.getItem(), 1, player2.inventoryMenu.getCraftSlots());
                }
            }
        }
    }
}

