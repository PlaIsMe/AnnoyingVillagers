package com.pla.annoyingvillagers.entity;

import java.util.Objects;
import java.util.Random;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class EnchantedEnderPearlEntity extends AbstractArrow implements ItemSupplier {

    public EnchantedEnderPearlEntity(SpawnEntity spawnEntity, Level level) {
        super(AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), level);
    }

    public EnchantedEnderPearlEntity(EntityType<? extends EnchantedEnderPearlEntity> entitytype, Level level) {
        super(entitytype, level);
    }

    public EnchantedEnderPearlEntity(EntityType<? extends EnchantedEnderPearlEntity> entitytype, double d0, double d1, double d2, Level level) {
        super(entitytype, d0, d1, d2, level);
    }

    public EnchantedEnderPearlEntity(EntityType<? extends EnchantedEnderPearlEntity> entitytype, LivingEntity livingentity, Level level) {
        super(entitytype, livingentity, level);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public @NotNull ItemStack getItem() {
        return new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get());
    }

    public @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    protected void doPostHurtEffects(@NotNull LivingEntity livingentity) {
        super.doPostHurtEffects(livingentity);
        livingentity.setArrowCount(livingentity.getArrowCount() - 1);
    }

    public void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide()) {
            this.level().playSound(null, new BlockPos(entityHitResult.getEntity().getBlockX(), entityHitResult.getEntity().getBlockY(), entityHitResult.getEntity().getBlockZ()), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            this.level().playLocalSound(entityHitResult.getEntity().getBlockX(), entityHitResult.getEntity().getBlockY(), entityHitResult.getEntity().getBlockZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }

        if (this.getOwner() != null) {
            this.getOwner().teleportTo(entityHitResult.getEntity().getBlockX(), entityHitResult.getEntity().getBlockY() + 1.0D, entityHitResult.getEntity().getBlockZ());
            if (this.getOwner() instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.teleport(entityHitResult.getEntity().getBlockX(), entityHitResult.getEntity().getBlockY() + 1.0D, entityHitResult.getEntity().getBlockZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.PORTAL, entityHitResult.getEntity().getBlockX(), entityHitResult.getEntity().getBlockY(), entityHitResult.getEntity().getBlockZ(), 50, 4.0D, 4.0D, 4.0D, 1.0D);
                serverLevel.sendParticles(
                        AnnoyingVillagersModParticleTypes.ENDER.get(),
                        this.getOwner().getX(), this.getOwner().getY() + 1.0D, this.getOwner().getZ(),
                        16, 0, 0, 0, 0.5);
            }
        }
    }

    public void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) {
            this.level().playSound(null, new BlockPos(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ()), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            this.level().playLocalSound(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }

        if (this.getOwner() != null) {
            this.getOwner().teleportTo(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY() + 1.0D, blockHitResult.getBlockPos().getZ());
            if (this.getOwner() instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.teleport(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY() + 1.0D, blockHitResult.getBlockPos().getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.PORTAL, blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), 50, 4.0D, 4.0D, 4.0D, 1.0D);
                serverLevel.sendParticles(
                        AnnoyingVillagersModParticleTypes.ENDER.get(),
                        this.getOwner().getX(), this.getOwner().getY() + 1.0D, this.getOwner().getZ(),
                        16, 0, 0, 0, 0.5);
            }
        }
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ENDERMAN_TELEPORT;
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.level().addParticle(AnnoyingVillagersModParticleTypes.ENDER.get(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
        if (this.inGround) {
            this.discard();
        }

    }

    public static EnchantedEnderPearlEntity shoot(Level level, LivingEntity livingentity, RandomSource random, float f, double d0, int i) {
        EnchantedEnderPearlEntity enchantedEnderPearl = new EnchantedEnderPearlEntity(AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), livingentity, level);

        enchantedEnderPearl.shoot(livingentity.getViewVector(1.0F).x, livingentity.getViewVector(1.0F).y, livingentity.getViewVector(1.0F).z, f * 2.0F, 0.0F);
        enchantedEnderPearl.setSilent(true);
        enchantedEnderPearl.setCritArrow(false);
        enchantedEnderPearl.setBaseDamage(d0);
        enchantedEnderPearl.setKnockback(i);
        level.addFreshEntity(enchantedEnderPearl);
        level.playSound((Player) null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "throw"))), SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + f / 2.0F);
        return enchantedEnderPearl;
    }

    public static EnchantedEnderPearlEntity shoot(LivingEntity livingentity, LivingEntity livingentity1) {
        EnchantedEnderPearlEntity enchantedEnderPearl = new EnchantedEnderPearlEntity((EntityType) AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), livingentity, livingentity.level());
        double d0 = livingentity1.getX() - livingentity.getX();
        double d1 = livingentity1.getY() + (double) livingentity1.getEyeHeight() - 1.1D;
        double d2 = livingentity1.getZ() - livingentity.getZ();

        enchantedEnderPearl.shoot(d0, d1 - enchantedEnderPearl.getY() + Math.hypot(d0, d2) * 0.20000000298023224D, d2, 2.6F, 12.0F);
        enchantedEnderPearl.setSilent(true);
        enchantedEnderPearl.setBaseDamage(0.0D);
        enchantedEnderPearl.setKnockback(0);
        enchantedEnderPearl.setCritArrow(false);
        livingentity.level().addFreshEntity(enchantedEnderPearl);
        livingentity.level().playSound((Player) null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "throw"))), SoundSource.PLAYERS, 1.0F, 1.0F / ((new Random()).nextFloat() * 0.5F + 1.0F));
        return enchantedEnderPearl;
    }
}
