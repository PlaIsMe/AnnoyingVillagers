package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.GlintColorHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EnchantedArrowEntity extends Arrow {
    private static final EntityDataAccessor<Integer> COLOR_GLINT =
            SynchedEntityData.defineId(EnchantedArrowEntity.class, EntityDataSerializers.INT);

    public EnchantedArrowEntity(EntityType<? extends EnchantedArrowEntity> type, Level level) {
        super(type, level);
    }

    public EnchantedArrowEntity(@NotNull Level level, @NotNull LivingEntity shooter) {
        this(AnnoyingVillagersModEntities.ENCHANTED_ARROW.get(), level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());

        if (shooter instanceof Player player) {
            this.pickup = player.getAbilities().instabuild
                    ? AbstractArrow.Pickup.CREATIVE_ONLY
                    : AbstractArrow.Pickup.ALLOWED;
        } else {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COLOR_GLINT, GlintColorHelper.NONE);
    }

    public void setColorGlint(int mode) {
        this.entityData.set(COLOR_GLINT, GlintColorHelper.sanitize(mode));
    }

    public int getColorGlint() {
        return this.entityData.get(COLOR_GLINT);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
        CompoundTag tag = new CompoundTag();
        super.addAdditionalSaveData(output);
        tag.putInt(GlintColorHelper.TAG_COLOR_GLINT, this.getColorGlint());
    
        com.pla.annoyingvillagers.util.LegacyValueIO.write(output, tag);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            int amount = this.isInGround() ? (this.inGroundTime % 5 == 0 ? 1 : 0) : 2;
            if (amount > 0) {
                spawnColoredParticles(amount);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        CompoundTag tag = com.pla.annoyingvillagers.util.LegacyValueIO.read(input);
        super.readAdditionalSaveData(input);

        if (tag.contains(GlintColorHelper.TAG_COLOR_GLINT)) {
            this.setColorGlint(tag.getIntOr(GlintColorHelper.TAG_COLOR_GLINT, 0));
        } else if (tag.contains(GlintColorHelper.TAG_COLOR_GLINT)) {
            this.setColorGlint(GlintColorHelper.fromName(tag.getStringOr(GlintColorHelper.TAG_COLOR_GLINT, "")));
        }
    }

    private void spawnColoredParticles(int amount) {
        Vec3 rgb = GlintColorHelper.getParticleColor(this.getColorGlint());

        for (int i = 0; i < amount; i++) {
            this.level().addParticle(
                    net.minecraft.core.particles.ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, (float) rgb.x, (float) rgb.y, (float) rgb.z),
                    this.getRandomX(0.5D),
                    this.getRandomY(),
                    this.getRandomZ(0.5D),
                    0.0D, 0.0D, 0.0D
            );
        }
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        ItemStack stack = new ItemStack(AnnoyingVillagersModItems.ENCHANTED_ARROW.get());
        GlintColorHelper.setColor(stack, this.getColorGlint());
        return stack;
    }

    @Override
    protected boolean tryPickup(@NotNull Player pPlayer) {
        ItemStack stack = this.getPickupItem();
        GlintColorHelper.clearColor(stack);

        return switch (this.pickup) {
            case ALLOWED -> pPlayer.getInventory().add(stack);
            case CREATIVE_ONLY -> pPlayer.getAbilities().instabuild;
            default -> false;
        };
    }
}
