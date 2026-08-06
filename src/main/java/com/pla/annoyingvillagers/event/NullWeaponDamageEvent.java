package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.item.NullWeaponItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NullWeaponDamageEvent {
    private static final float BASE_BLOCK_CHANCE = 0.25F;
    private static final float FULL_CHARGE_BLOCK_CHANCE = 0.5F;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0.0F) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player) || !NullWeaponItem.isHeldBy(player)) {
            return;
        }

        DamageSource damageSource = event.getSource();
        if (isIgnoredDamageSource(damageSource) || !(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack heldStack = NullWeaponItem.getHeldStack(player);
        float blockChance = NullWeaponItem.isFullyCharged(heldStack) ? FULL_CHARGE_BLOCK_CHANCE : BASE_BLOCK_CHANCE;
        if (player.getRandom().nextFloat() > blockChance) {
            return;
        }

        CompoundTag data = player.getPersistentData();
        NullWeapon nullWeapon = NullWeaponItem.pickRandomSummonedWeapon(serverLevel, data, player.getRandom());
        if (nullWeapon == null) {
            return;
        }

        nullWeapon.moveTo(player.getX(), player.getY(), player.getZ(), nullWeapon.getYRot(), nullWeapon.getXRot());
        event.setAmount(0.0F);
        event.setCanceled(true);

        EpicfightUtil.damageBlocked(damageSource, player, serverLevel);
        nullWeapon.spinfor5seconds();

        EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(
                serverLevel,
                HitParticleType.FRONT_OF_EYES,
                HitParticleType.ZERO,
                player,
                damageSource.getEntity()
        );
    }

    private static boolean isIgnoredDamageSource(DamageSource damageSource) {
        return damageSource.is(DamageTypes.MAGIC)
                || damageSource.is(DamageTypes.EXPLOSION)
                || damageSource.is(DamageTypes.ON_FIRE)
                || damageSource.is(DamageTypes.IN_FIRE)
                || damageSource.is(DamageTypes.FALL)
                || damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)
                || damageSource.is(DamageTypes.DROWN);
    }
}
