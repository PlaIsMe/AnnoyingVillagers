package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnderGlaiveItem extends SwordItem {

    public EnderGlaiveItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 5.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(AnnoyingVillagersModItems.ELITE_OBSIDIAN.get());
            }
        }, 3, -2.5F, (new Properties().fireResistant()));
    }

    public static void spawnExplosionFallback(Level level, Vec3 center) {
        if (level == null || center == null) {
            return;
        }

        RandomSource rand = level.getRandom();
        level.addParticle(
                AnnoyingVillagersModParticleTypes.FIREBALL.get(),
                true,
                center.x, center.y, center.z,
                5.0D, 1.0D, 0.0D
        );

        for (int i = 0; i < 6; i++) {
            Vec3 normal = randomUnit(rand);
            spawnRing3d(level, rand, center, normal, 52, 2.0D, 0.10D, 0.12D, 0.035D);
            spawnRing3d(level, rand, center, normal, 60, 2.8D, 0.14D, 0.11D, 0.030D);
        }
    }

    private static void spawnRing3d(Level level, RandomSource rand, Vec3 center, Vec3 normal,
                                    int points, double radius, double thickness,
                                    double tangentialSpeed, double outwardSpeed) {
        Vec3 n = normal.normalize();
        Vec3 u = n.cross(new Vec3(0.0D, 1.0D, 0.0D));
        if (u.lengthSqr() < 1.0E-6D) {
            u = n.cross(new Vec3(1.0D, 0.0D, 0.0D));
        }
        u = u.normalize();
        Vec3 v = n.cross(u).normalize();

        for (int i = 0; i < points; i++) {
            double angle = (i / (double) points) * (Math.PI * 2.0D) + rand.nextDouble() * 0.10D;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            Vec3 radial = u.scale(cos).add(v.scale(sin));
            Vec3 tangent = n.cross(radial).normalize();
            Vec3 pos = center
                    .add(radial.scale(radius))
                    .add(n.scale((rand.nextDouble() - 0.5D) * 2.0D * thickness));
            Vec3 velocity = tangent.scale(tangentialSpeed)
                    .add(radial.scale(outwardSpeed))
                    .add((rand.nextDouble() - 0.5D) * 0.02D, (rand.nextDouble() - 0.5D) * 0.02D, (rand.nextDouble() - 0.5D) * 0.02D);

            level.addParticle(AnnoyingVillagersModParticleTypes.ENDER.get(), true,
                    pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z);

            if ((i & 3) == 0) {
                level.addParticle(ParticleTypes.REVERSE_PORTAL, true,
                        pos.x, pos.y, pos.z, velocity.x * 0.35D, velocity.y * 0.2D, velocity.z * 0.35D);
            }
        }
    }

    private static Vec3 randomUnit(RandomSource rand) {
        double z = rand.nextDouble() * 2.0D - 1.0D;
        double angle = rand.nextDouble() * Math.PI * 2.0D;
        double radius = Math.sqrt(Math.max(0.0D, 1.0D - z * z));
        return new Vec3(radius * Math.cos(angle), z, radius * Math.sin(angle));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
//        Add this code in AV_EFM
//        if (pAttacker instanceof Player player) {
//            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
//            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
//                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_GLAIVE);
//                if (skillContainer == null) return super.hurtEnemy(pStack, pTarget, pAttacker);
//                EnderGlaiveSkill enderGlaiveSkill = (EnderGlaiveSkill) skillContainer.getSkill();
//
//                float currentResource = skillContainer.getResource();
//                float neededResource = skillContainer.getNeededResource();
//                float addResource = Math.min(2.0F, neededResource);
//                enderGlaiveSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
//            }
//        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
//        Add this in AV_EFM
//        if (flag && entity instanceof Player player) {
//            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
//            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
//                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_GLAIVE);
//                if (skillContainer != null) {
//                    if (skillContainer.getStack() >= 1) {
//                        HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
//                    }
//                }
//            }
//        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(Component.translatable("tooltip.annoyingvillagers.ender_glaive").getString()));
    }
}
