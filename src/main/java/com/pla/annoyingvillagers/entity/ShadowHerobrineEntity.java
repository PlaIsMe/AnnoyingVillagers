package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.RollItemUser;
import com.pla.annoyingvillagers.entity.goal.ObsidianMachineGunGoal;
import com.pla.annoyingvillagers.entity.goal.ShadowHerobrineShootDarkObGoal;
import com.pla.annoyingvillagers.entity.goal.ShadowHerobrineSummonDarkObGoal;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.item.ShadowObsidianPillarItem;
import com.pla.annoyingvillagers.item.ShadowObsidianSwordItem;
import com.pla.annoyingvillagers.item.ShadowObsidianWeaponItem;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;


public class ShadowHerobrineEntity extends HerobrineMob implements RollItemUser {
    private BlockProjectileEntity darkObUp;
    private UUID darkObUpUUID;

    private BlockProjectileEntity darkObLeft;
    private UUID darkObLeftUUID;

    private BlockProjectileEntity darkObRight;
    private UUID darkObRightUUID;

    private int summonDarkObCooldown = 0;
    private int obsidianMachineGunCooldown = 0;
    private int darkObParryCooldown = 0;
    private int obsidianMachineGunTick = 0;

    public void clearDarkOb() {
        if (darkObUp != null) {
            darkObUp.discard();
            darkObUpUUID = null;
            darkObUp = null;
        }
        if (darkObRight != null) {
            darkObRight.discard();
            darkObRightUUID = null;
            darkObRight = null;
        }
        if (darkObLeft != null) {
            darkObLeft.discard();
            darkObLeftUUID = null;
            darkObLeft = null;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new ObsidianMachineGunGoal(this));
        this.goalSelector.addGoal(1, new ShadowHerobrineShootDarkObGoal(this));
        this.goalSelector.addGoal(1, new ShadowHerobrineSummonDarkObGoal(this));
    }

    @Override
    public boolean canRollItem() {
        LivingEntity target = this.getTarget();
        return target != null
                && target.isAlive()
                && this.getSwapWeaponCooldown() == 0
                && !this.isSacrificing();
    }

    public void setObsidianMachineGunTick() {
        this.obsidianMachineGunTick = 20;
    }

    public int getObsidianMachineGunTick() {
        return obsidianMachineGunTick;
    }

    public boolean isDarkObReady() {
        return darkObUp != null || darkObLeft != null || darkObRight != null;
    }

    public int getSummonDarkObCooldown() {
        return summonDarkObCooldown;
    }

    public void setSummonDarkObCooldown(int summonDarkObCooldown) {
        this.summonDarkObCooldown = summonDarkObCooldown;
    }

    public int getObsidianMachineGunCooldown() {
        return obsidianMachineGunCooldown;
    }

    public ShadowHerobrineEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), level);
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
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get()));
//        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));
    }

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (!this.isSacrificing() && this.level() instanceof ServerLevel serverLevel) {
            if (Math.random() <= 0.5D
                    && !damageSource.is(DamageTypes.IN_WALL)
                    && !damageSource.is(DamageTypes.IN_FIRE)
                    && !damageSource.is(DamageTypes.ON_FIRE)) {
                    serverLevel.playSound(null, this.blockPosition(), AnnoyingVillagersModSounds.OBSIDIAN_PLACE.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
                    HerobrineUtil.spawnObsidianEyeLineStaggered(serverLevel, this, AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState(), 1);
            } else if (this.getState() == 2
                && damageSource.getEntity() instanceof LivingEntity livingEntity
                && this.darkObParryCooldown == 0) {
                if (this.darkObUp != null) {
                    this.darkObUp.moveTo(livingEntity.blockPosition().getCenter());
                    shootOne(this.darkObUp, livingEntity.getOnPos().getCenter(), 2.0F, "up", this);
                    this.darkObParryCooldown = 40;
                    CommonUtil.damageBlocked(damageSource, this.darkObUp, serverLevel);
                    if (!isDarkObReady()) {
                        this.summonDarkObCooldown = new Random().nextInt(200, 600);
                    }
                    return false;
                } else if (this.darkObRight != null) {
                    this.darkObRight.moveTo(livingEntity.blockPosition().getCenter());
                    shootOne(this.darkObRight, livingEntity.getOnPos().getCenter(), 2.0F, "right", this);
                    this.darkObParryCooldown = 40;
                    CommonUtil.damageBlocked(damageSource, this.darkObRight, serverLevel);
                    if (!isDarkObReady()) {
                        this.summonDarkObCooldown = new Random().nextInt(200, 600);
                    }
                    return false;
                } if (this.darkObLeft != null) {
                    this.darkObLeft.moveTo(livingEntity.blockPosition().getCenter());
                    shootOne(this.darkObLeft, livingEntity.getOnPos().getCenter(), 2.0F, "left", this);
                    this.darkObParryCooldown = 40;
                    CommonUtil.damageBlocked(damageSource, this.darkObLeft, serverLevel);
                    if (!isDarkObReady()) {
                        this.summonDarkObCooldown = new Random().nextInt(200, 600);
                    }
                    return false;
                }
            }
        }
        if (damageSource.is(DamageTypes.FALL)) return false;
        if (damageSource.is(DamageTypes.CACTUS)) return false;
        if (damageSource.is(DamageTypes.WITHER)) return false;
        if (damageSource.is(DamageTypes.DROWN)) return false;
        if (damageSource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damageSource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (!(damageSource.getDirectEntity() instanceof EnchantedArrowEntity)
                && damageSource.getDirectEntity() instanceof AbstractArrow
                && !(damageSource.getDirectEntity() instanceof BlueDemonThrownTridentEntity)) return false;
        return super.hurt(damageSource, f);
    }

    public void die(@NotNull DamageSource damagesource) {
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
            InfectedPlayerNpcEntity corpse = new InfectedPlayerNpcEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_NPC.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "shadow_herobrine");
            if (killedName.isEmpty()) {
                killedName = FakePlayer.getRandomHardcodedName(this.getRandom());
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

    private void enchantGear(ItemStack itemStack) {
        itemStack.enchant(Enchantments.SHARPNESS, 5);
        itemStack.enchant(Enchantments.SWEEPING_EDGE, 5);
        itemStack.enchant(Enchantments.KNOCKBACK, 3);
    }

    @Override
    public void rollItem() {
        super.rollItem();
        ItemStack mainHand = this.getMainHandItem();
        ItemStack mainHandWeapon;
        ItemStack offHandWeapon = ItemStack.EMPTY;
        if (mainHand.getItem() instanceof ShadowObsidianWeaponItem) {
            if (new Random().nextBoolean()) {
                mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                if (this.getState() == 2) {
                    enchantGear(mainHandWeapon);
                }
                if (new Random().nextBoolean()) {
                    offHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                    if (this.getState() == 2) {
                        enchantGear(offHandWeapon);
                    }
                }
            } else {
                mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                if (this.getState() == 2) {
                    enchantGear(mainHandWeapon);
                }
                if (new Random().nextBoolean()) {
                    offHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                    if (this.getState() == 2) {
                        enchantGear(offHandWeapon);
                    }
                }
            }
        } else if (mainHand.getItem() instanceof ShadowObsidianPillarItem) {
            if (new Random().nextBoolean()) {
                mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get());
                if (this.getState() == 2) {
                    enchantGear(mainHandWeapon);
                }
            } else {
                mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                if (this.getState() == 2) {
                    enchantGear(mainHandWeapon);
                }
                if (new Random().nextBoolean()) {
                    offHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                    if (this.getState() == 2) {
                        enchantGear(offHandWeapon);
                    }
                }
            }
        } else if (mainHand.getItem() instanceof ShadowObsidianSwordItem) {
            if (new Random().nextBoolean()) {
                mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get());
                if (this.getState() == 2) {
                    enchantGear(mainHandWeapon);
                }
            } else {
                mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                if (this.getState() == 2) {
                    enchantGear(mainHandWeapon);
                }
                if (new Random().nextBoolean()) {
                    offHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                    if (this.getState() == 2) {
                        enchantGear(offHandWeapon);
                    }
                }
            }
        } else {
            float chance = new Random().nextFloat();
            if (chance <= 0.3F) {
                if (new Random().nextBoolean()) {
                    mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                    if (this.getState() == 2) {
                        enchantGear(mainHandWeapon);
                    }
                    if (new Random().nextBoolean()) {
                        offHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                        if (this.getState() == 2) {
                            enchantGear(offHandWeapon);
                        }
                    }
                } else {
                    mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                    if (this.getState() == 2) {
                        enchantGear(mainHandWeapon);
                    }
                    if (new Random().nextBoolean()) {
                        offHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                        if (this.getState() == 2) {
                            enchantGear(offHandWeapon);
                        }
                    }
                }
            } else if (chance <= 0.6F) {
                if (new Random().nextBoolean()) {
                    mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get());
                    if (this.getState() == 2) {
                        enchantGear(mainHandWeapon);
                    }
                } else {
                    mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                    if (this.getState() == 2) {
                        enchantGear(mainHandWeapon);
                    }
                    if (new Random().nextBoolean()) {
                        offHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                        if (this.getState() == 2) {
                            enchantGear(offHandWeapon);
                        }
                    }
                }
            } else {
                if (new Random().nextBoolean()) {
                    mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get());
                    if (this.getState() == 2) {
                        enchantGear(mainHandWeapon);
                    }
                } else {
                    mainHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                    if (this.getState() == 2) {
                        enchantGear(mainHandWeapon);
                    }
                    if (new Random().nextBoolean()) {
                        offHandWeapon = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                        if (this.getState() == 2) {
                            enchantGear(offHandWeapon);
                        }
                    }
                }
            }
        }
        this.setItemInHand(InteractionHand.MAIN_HAND, mainHandWeapon);
        this.setItemInHand(InteractionHand.OFF_HAND, offHandWeapon);
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

    private Vec3 getUpBlockPos() {
        final double upY = 2.0;
        Vec3 eye = this.getEyePosition(1.0F);
        return eye.add(0.0, upY, 0.0);
    }

    private Vec3 getRightBlockPos() {
        final double lateral = 2.0;
        final double sideY = 0.0;

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
            BlockState block = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get().defaultBlockState();

            if (this.darkObUp == null) {
                BlockProjectileEntity darkObbyUp = new BlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyUp.setNoGravity(true);
                darkObbyUp.setNotReadyForShoot(true);
                darkObbyUp.moveTo(getUpBlockPos());
                darkObbyUp.setOwnerUUID(this.getUUID());
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
                darkObbyRight.setOwnerUUID(this.getUUID());
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
                darkObbyLeft.setOwnerUUID(this.getUUID());
                darkObbyLeft.moveTo(getLeftBlockPos());
                serverLevel.addFreshEntity(darkObbyLeft);
                this.darkObLeftUUID = darkObbyLeft.getUUID();
                this.darkObLeft = darkObbyLeft;
            }
        }
    }

    public void shootChain(BlockState block, float velocity, int length) {
        Entity shooter = this;
        Level level = shooter.level();
        if (level.isClientSide) return;

        double eyeY = shooter.getEyeY();
        Vec3 look = shooter.getLookAngle().normalize();
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive()) {
            Vec3 portalAimPosition = HerobrineUtil.getProjectilePortalAim(this, target);
            if (portalAimPosition != null) {
                look = portalAimPosition.subtract(this.getEyePosition()).normalize();
            }
        }
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

    public void shootDarkObsAtTarget(double speed) {
        ShadowHerobrineEntity shadowHerobrineEntity = this;
        if (shadowHerobrineEntity.level().isClientSide) return;

        Vec3 to;
        LivingEntity target = shadowHerobrineEntity.getTarget();
        if (target != null && target.isAlive()) {
            to = target.getEyePosition(1.0F);
            Vec3 portalAimPosition = HerobrineUtil.getProjectilePortalAim(shadowHerobrineEntity, target);
            if (portalAimPosition != null) {
                to = portalAimPosition;
            }
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

        this.summonDarkObCooldown = new Random().nextInt(200, 600);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel) {
            if (this.summonDarkObCooldown > 0) this.summonDarkObCooldown--;
            if (this.darkObParryCooldown > 0) this.darkObParryCooldown--;
            if (this.obsidianMachineGunCooldown > 0) this.obsidianMachineGunCooldown--;
            if (this.obsidianMachineGunTick > 0) {
                if (this.obsidianMachineGunTick == 1) {
                    this.obsidianMachineGunCooldown = new Random().nextInt(200, 300);
                    rollItem();
                }
                this.obsidianMachineGunTick--;
                BlockState block = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState();
                setDeltaMovement(Vec3.ZERO);
                shootChain(block, 2.5F, 5);
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
        }
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
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

    public static Builder addEpicFightAttributes(Builder builder) {
        //      ADD THIS CODE IN AV_EFM
//        return builder.add(EpicFightAttributes.IMPACT.get(), 4.0D)
//                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10.0D)
//                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
//                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
//                .add(EpicFightAttributes.MAX_STAMINA.get(), 60.0D)
//                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);

        return builder;
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 80.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 40.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return addEpicFightAttributes(builder);
    }
}
