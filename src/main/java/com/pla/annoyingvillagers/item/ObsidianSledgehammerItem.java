package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.client.particle.smoke_wave.SmokeWaveOptions;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.potion.GroundStuckMobEffect;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

public class ObsidianSledgehammerItem extends SwordItem {

    public ObsidianSledgehammerItem() {
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
                return 32;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(AnnoyingVillagersModItems.ELITE_OBSIDIAN.get());
            }
        }, 1, -2.6F, (new Properties()).fireResistant());
    }

    public static void spawnWave(LivingEntity caster, float yaw, float innerRadius, float radius, int particleCount) {
        if (!(caster.level() instanceof ServerLevel level)) return;
        Vec3 center = caster.position();
        double yawRad = Math.toRadians(yaw);
        double forwardX = -Math.sin(yawRad), forwardZ = Math.cos(yawRad), rightX = Math.cos(yawRad), rightZ = Math.sin(yawRad);
        for (int i = 0; i < particleCount; i++) {
            double angle = Math.PI * 2.0D * i / particleCount, localX = Math.cos(angle) * radius, localZ = Math.sin(angle) * radius;
            double x = center.x + forwardX * localX + rightX * localZ, z = center.z + forwardZ * localX + rightZ * localZ;
            float particleYaw = yaw - 360.0F / particleCount * i, particlePitch = -30.0F + level.random.nextFloat() * 60.0F - 30.0F;
            level.sendParticles(new SmokeWaveOptions(particleYaw, particlePitch, 0.0D), x, center.y + 0.4D, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(radius, 4.0D, radius), target -> target != caster && target.isAlive() && !caster.isAlliedTo(target))) {
            double dx = target.getX() - caster.getX(), dz = target.getZ() - caster.getZ(), distance = Math.sqrt(dx * dx + dz * dz);
            if ((innerRadius > 0.0F && distance <= innerRadius) || distance > radius) continue;
            int amplifier = Mth.clamp(Math.round(5.0F * (1.0F - (float)(distance / 16.0D))), 0, 5);
            GroundStuckMobEffect.apply(target, GroundStuckMobEffect.DEFAULT_DURATION, amplifier);
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!selected || !(entity instanceof Player player)) return;

        PlayerPatch<?> patch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (!(patch instanceof ServerPlayerPatch serverPatch)) return;

        SkillContainer skill = serverPatch.getSkill(AVSkills.OBSIDIAN_SLEDGEHAMMER);
        if (skill != null && skill.getStack() >= 1) {
            HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(Component.translatable("tooltip.annoyingvillagers.obsidian_sledgehammer").getString()));
    }
}
