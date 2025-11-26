package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import se.gory_moon.player_mobs.utils.NameManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.UUID;


public class ShadowHerobrineEntity extends HerobrineMob {
    private boolean wasLanding = false;
    private boolean wasAiming = false;

    public BlockProjectileEntity darkObUp;
    public UUID darkObUpUUID;

    public BlockProjectileEntity darkObLeft;
    public UUID darkObLeftUUID;

    public BlockProjectileEntity darkObRight;
    public UUID darkObRightUUID;

    public ShadowHerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), level);
    }

    public ShadowHerobrineEntity(EntityType<ShadowHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.8F);
        this.xpReward = 60;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setChatName(this.getDisplayName().getString());
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

    public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public @NotNull SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (!this.isHealing()) {
            DarkHerobrineOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        }
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
        if (this.darkObUp != null) {
            this.darkObUp.discard();
        }
        if (this.darkObRight != null) {
            this.darkObRight.discard();
        }
        if (this.darkObLeft != null) {
            this.darkObLeft.discard();
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "shadow_herobrine");
            if (killedName.isEmpty()) {
                killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
            }
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(corpse);
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.getPersistentData().contains("Shooting")) {
            tag.putInt("Shooting", this.getPersistentData().getInt("Shooting"));
        }
        if (darkObUpUUID != null) {
            tag.putUUID("DarkObUpUUID", darkObUpUUID);
        }
        if (darkObLeftUUID != null) {
            tag.putUUID("DarkObLeftUUID", darkObLeftUUID);
        }
        if (darkObRightUUID != null) {
            tag.putUUID("DarkObRightUUID", darkObRightUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Shooting")) {
            this.getPersistentData().putInt("Shooting", tag.getInt("Shooting"));
        }
        if (tag.hasUUID("DarkObUpUUID")) {
            darkObUpUUID = tag.getUUID("DarkObUpUUID");
        }
        if (tag.hasUUID("DarkObLeftUUID")) {
            darkObLeftUUID = tag.getUUID("DarkObLeftUUID");
        }
        if (tag.hasUUID("DarkObRightUUID")) {
            darkObRightUUID = tag.getUUID("DarkObRightUUID");
        }
    }

    public void baseTick() {
        super.baseTick();
    }

    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (!this.isHealing()) {
            DarkHerobrineOnPlayerTouchProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        }
    }

    private Vec3 getUpBlockPos() {
        final double upY     = 2.0;
        Vec3 eye = this.getEyePosition(1.0F);
        return eye.add(0.0, upY, 0.0);
    }

    private Vec3 getRightBlockPos() {
        final double lateral = 2.0;
        final double sideY   = 0.0;

        Vec3 eye = this.getEyePosition(1.0F);
        Vec3 look = this.getViewVector(1.0F);
        Vec3 horiz = new Vec3(look.x, 0.0, look.z);

        if (horiz.lengthSqr() < 1.0e-6) {
            float yaw = this.getYRot() * ((float)Math.PI / 180F);
            horiz = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
        }
        Vec3 upAxis = new Vec3(0, 1, 0);
        Vec3 rightAxis = horiz.cross(upAxis).normalize();

        return eye.add(rightAxis.scale(lateral)).add(0.0, sideY, 0.0);
    }

    private Vec3 getLeftBlockPos() {
        final double lateral = 2.0;
        final double sideY   = 0.0;

        Vec3 eye = this.getEyePosition(1.0F);
        Vec3 look = this.getViewVector(1.0F);
        Vec3 horiz = new Vec3(look.x, 0.0, look.z);

        if (horiz.lengthSqr() < 1.0e-6) {
            float yaw = this.getYRot() * ((float)Math.PI / 180F);
            horiz = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
        }
        Vec3 upAxis = new Vec3(0, 1, 0);
        Vec3 rightAxis = horiz.cross(upAxis).normalize();
        Vec3 leftAxis  = rightAxis.scale(-1);

        return eye.add(leftAxis.scale(lateral)).add(0.0, sideY, 0.0);
    }

    public void spawnDarkObEntities() {
        if (this.level() instanceof ServerLevel serverLevel) {
            BlockState block = AnnoyingVillagersModBlocks.DARKOB.get().defaultBlockState();

            if (this.darkObUp == null) {
                BlockProjectileEntity darkObbyUp = new BlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyUp.setNoGravity(true);
                darkObbyUp.setNotReadyForShoot(true);
                darkObbyUp.moveTo(getUpBlockPos());
                serverLevel.addFreshEntity(darkObbyUp);
                this.darkObUpUUID = darkObbyUp.getUUID();
                this.darkObUp = darkObbyUp;
            }

            if (this.darkObRight == null) {
                BlockProjectileEntity darkObbyRight = new BlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyRight.setNoGravity(true);
                darkObbyRight.setNotReadyForShoot(true);
                darkObbyRight.moveTo(getRightBlockPos());
                serverLevel.addFreshEntity(darkObbyRight);
                this.darkObRightUUID = darkObbyRight.getUUID();
                this.darkObRight = darkObbyRight;
            }

            if (this.darkObLeft == null) {
                BlockProjectileEntity darkObbyLeft = new BlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyLeft.setNoGravity(true);
                darkObbyLeft.setNotReadyForShoot(true);
                darkObbyLeft.moveTo(getLeftBlockPos());
                serverLevel.addFreshEntity(darkObbyLeft);
                this.darkObLeftUUID = darkObbyLeft.getUUID();
                this.darkObLeft = darkObbyLeft;
            }
        }
    }

    private static void shootChain(Entity shooter, BlockState block, float velocity, int length) {
        Level level = shooter.level();
        if (level.isClientSide) return;

        double eyeY = shooter.getEyeY();
        Vec3 look = shooter.getLookAngle().normalize();
        RandomSource rand = level.getRandom();

        for (int i = 0; i < length; i++) {
            BlockProjectileEntity proj = new BlockProjectileEntity(
                    level,
                    shooter instanceof LivingEntity ? (LivingEntity) shooter : null,
                    block
            );

            Vec3 forward = look.scale(i * 1.0);

            double sideX = (rand.nextDouble() - 0.5) * 2.0;
            double sideY = (rand.nextDouble() - 0.5) * 2.0;
            double sideZ = (rand.nextDouble() - 0.5) * 2.0;

            proj.setPos(
                    shooter.getX() + forward.x + sideX,
                    eyeY + forward.y + sideY,
                    shooter.getZ() + forward.z + sideZ
            );
            proj.setDeltaMovement(look.scale(velocity));

            level.addFreshEntity(proj);
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

    private static boolean isLandAnimId(String id) {
        if (id == null) return false;
        return id.contains("biped/living/landing") || id.endsWith("/landing") || id.contains("landing");
    }

    private static boolean isAiming(String id) {
        if (id == null) return false;
        return id.contains("biped/combat/fist_dash") || id.endsWith("/fist_dash") || id.contains("fist_dash");
    }

    private void shootOne(BlockProjectileEntity ob, Vec3 to, double speed, String position, ShadowHerobrineEntity shadowHerobrineEntity) {
        if (ob == null || !ob.isAlive()) return;
        Vec3 dir = to.subtract(ob.position());
        if (dir.lengthSqr() < 1.0e-6) dir = this.getLookAngle();
        ob.setNoGravity(false);
        Vec3 vel = dir.normalize().scale(speed);
        ob.setDeltaMovement(vel);
        ob.setNotReadyForShoot(false);
        if (position.equals("up")) {
            shadowHerobrineEntity.darkObUpUUID = null;
            shadowHerobrineEntity.darkObUp = null;
        } else if (position.equals("left")) {
            shadowHerobrineEntity.darkObLeftUUID = null;
            shadowHerobrineEntity.darkObLeft = null;
        } else if (position.equals("right")) {
            shadowHerobrineEntity.darkObRightUUID = null;
            shadowHerobrineEntity.darkObRight = null;
        }
    }

    public void shootDarkObsAtTarget(double speed, ShadowHerobrineEntity shadowHerobrineEntity) {
        if (shadowHerobrineEntity.level().isClientSide) return;

        Vec3 to;
        LivingEntity target = shadowHerobrineEntity.getTarget();
        if (target != null && target.isAlive()) {
            to = target.getEyePosition(1.0F);
        } else {
            to = shadowHerobrineEntity.getEyePosition().add(shadowHerobrineEntity.getLookAngle().scale(16.0));
        }

        if (shadowHerobrineEntity.darkObUp != null) {
            shootOne(shadowHerobrineEntity.darkObUp, to, speed, "up", shadowHerobrineEntity);
        }
        if (shadowHerobrineEntity.darkObLeft != null) {
            shootOne(shadowHerobrineEntity.darkObLeft, to, speed, "left", shadowHerobrineEntity);
        }
        if (shadowHerobrineEntity.darkObRight != null) {
            shootOne(shadowHerobrineEntity.darkObRight, to, speed, "right", shadowHerobrineEntity);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            String animId = currentEfAnimIdOrNull(this);
            boolean isLandingNow = isLandAnimId(animId);

            if (isLandingNow && !wasLanding) {
                this.getPersistentData().putInt("Shooting", 15);
            }
            wasLanding = isLandingNow;

            int shooting = this.getPersistentData().getInt("Shooting");
            if (shooting > 0) {
                BlockState block = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState();
                setDeltaMovement(Vec3.ZERO);
                shootChain(this, block, 2.5F, 5);
                this.getPersistentData().putInt("Shooting", shooting - 1);
            } else {
                if (this.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }

            if (darkObUp == null && darkObUpUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(darkObUpUUID);
                if (entity instanceof BlockProjectileEntity blockProjectileEntity) {
                    this.darkObUp = blockProjectileEntity;
                } else {
                    this.darkObUpUUID = null;
                }
            }
            if (darkObLeft == null && darkObLeftUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(darkObLeftUUID);
                if (entity instanceof BlockProjectileEntity blockProjectileEntity) {
                    this.darkObLeft = blockProjectileEntity;
                } else {
                    this.darkObLeftUUID = null;
                }
            }
            if (darkObRight == null && darkObRightUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(darkObRightUUID);
                if (entity instanceof BlockProjectileEntity blockProjectileEntity) {
                    this.darkObRight = blockProjectileEntity;
                } else {
                    this.darkObRightUUID = null;
                }
            }
            if (this.darkObUp != null) {
                this.darkObUp.moveTo(getUpBlockPos());
            }
            if (this.darkObRight != null) {
                this.darkObRight.moveTo(getRightBlockPos());
            }
            if (this.darkObLeft != null) {
                this.darkObLeft.moveTo(getLeftBlockPos());
            }

            boolean aimingNow = isAiming(animId);
            if (aimingNow && !wasAiming) {
                spawnDarkObEntities();
                ShadowHerobrineEntity shadowHerobrineEntity = this;
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        if (shadowHerobrineEntity.isAlive()) {
                            shootDarkObsAtTarget(2.0F, shadowHerobrineEntity);
                        }
                    }
                };
            }
            wasAiming = aimingNow;
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (this.darkObUp != null) {
            this.darkObUp.discard();
        }
        if (this.darkObLeft != null) {
            this.darkObLeft.discard();
        }
        if (this.darkObRight != null) {
            this.darkObRight.discard();
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 120.0D);
        builder = builder.add(Attributes.ARMOR, 25.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
