package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import org.jetbrains.annotations.NotNull;
import se.gory_moon.player_mobs.utils.NameManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;


public class HerobrineCloneEntity extends HerobrineMob {
    private boolean wasAiming = false;

    public HerobrineCloneEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(), level);
    }

    public HerobrineCloneEntity(EntityType<HerobrineCloneEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.7F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setCustomNameVisible(false);
        this.setChatName(this.getDisplayName().getString());
//        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get()));
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    protected void dropCustomDeathLoot(@NotNull DamageSource damagesource, int i, boolean flag) {
        super.dropCustomDeathLoot(damagesource, i, flag);
        this.spawnAtLocation(new ItemStack(Blocks.OBSIDIAN));
    }

    public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.hurt"));
    }

    public @NotNull SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.death"));
    }

    public void thunderHit(ServerLevel serverlevel, LightningBolt lightningbolt) {
        super.thunderHit(serverlevel, lightningbolt);
    }

    public boolean causeFallDamage(float f, float f1, @NotNull DamageSource damagesource) {
        return super.causeFallDamage(f, f1, damagesource);
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        HerobrineCloneOnHurtProcedure.execute(this);
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "herobrine_clone");
            if (killedName.isEmpty()) {
                killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
            }
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            corpse.setItemSlot(EquipmentSlot.HEAD, this.getItemBySlot(EquipmentSlot.HEAD).copy());
            corpse.setItemSlot(EquipmentSlot.CHEST, this.getItemBySlot(EquipmentSlot.CHEST).copy());
            corpse.setItemSlot(EquipmentSlot.LEGS, this.getItemBySlot(EquipmentSlot.LEGS).copy());
            corpse.setItemSlot(EquipmentSlot.FEET, this.getItemBySlot(EquipmentSlot.FEET).copy());
            serverLevel.addFreshEntity(corpse);
        }
    }

    private static String currentEfAnimIdOrNull(LivingEntity self) {
        try {
            var patch = EpicFightCapabilities
                    .getEntityPatch(self, LivingEntityPatch.class);
            if (patch == null) return null;

            var player = patch.getAnimator().getPlayerFor((DynamicAnimation) null);
            if (player == null) return null;

            var anim = player.getAnimation();
            if (anim == null) return null;
            try {
                var m = anim.getClass().getMethod("getLocation");
                var rl = (net.minecraft.resources.ResourceLocation) m.invoke(anim);
                return rl != null ? rl.getPath().toLowerCase(java.util.Locale.ROOT) : null;
            } catch (Exception ignored) {
                return anim.toString().toLowerCase(java.util.Locale.ROOT);
            }
        } catch (Throwable t) {
            return null;
        }
    }

    private static boolean isAiming(String id) {
        if (id == null) return false;
        return id.contains("biped/combat/fist_dash") || id.endsWith("/fist_dash") || id.contains("fist_dash");
    }

    public void shootDarkObsAtTarget(double speed) {
        if (this.level().isClientSide) return;

        Vec3 to;
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive()) {
            to = target.getEyePosition(1.0F);
        } else {
            to = this.getEyePosition().add(this.getLookAngle().scale(16.0));
        }

        BlockState block = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState();
        BlockProjectileEntity throwingObsidian = new BlockProjectileEntity(
                this.level(),
                this,
                block
        );
        this.level().addFreshEntity(throwingObsidian);
        Vec3 dir = to.subtract(throwingObsidian.position());
        if (dir.lengthSqr() < 1.0e-6) dir = this.getLookAngle();
        Vec3 vel = dir.normalize().scale(speed);
        throwingObsidian.setDeltaMovement(vel);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            String animId = currentEfAnimIdOrNull(this);
            boolean aimingNow = isAiming(animId);
            if (aimingNow && !wasAiming) {
                HerobrineCloneEntity herobrine = this;
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        if (herobrine.isAlive()) {
                            herobrine.shootDarkObsAtTarget(2.0F);
                        }
                    }
                };
            }
            wasAiming = aimingNow;
        }
    }

    public void baseTick() {
        super.baseTick();
        HerobrineCloneBaseTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public static boolean canSpawn(EntityType<HerobrineCloneEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        int passesDay = (int) (serverLevel.getGameTime() / 24000);
        if (passesDay != 0 && passesDay % 3 != 0) {
            return false;
        }
        if (HerobrineMobData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        if (!serverLevel.isNight()) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 9.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2.0D);
        return builder;
    }
}
