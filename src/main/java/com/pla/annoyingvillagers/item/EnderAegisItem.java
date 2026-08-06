package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.EnderAegisProjectile;
import com.pla.annoyingvillagers.event.ShieldRendererEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundEnderAegisSparkFx;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class EnderAegisItem extends ShieldItem {
    public static final String SECOND_FORM_TAG = "SecondForm";
    public static final String SECOND_FORM_TICKS_TAG = "SecondFormTicks";
    public static final String CHARGE_TAG = "EnderAegisCharge";
    public static final float MAX_CHARGE = 100.0F;
    public static final int SECOND_FORM_DURATION_TICKS = 20 * 20;
    private static final int CHARGE_METER_STEPS = 18;
    private static final int CHARGE_COLOR = 0xA66BFF;
    private static final int CHARGE_DIM_COLOR = 0x4C435C;
    private static final int CHARGE_TEXT_COLOR = 0xE2D1FF;
    private static final int CHARGE_FULL_COLOR = 0xC28CFF;

    public EnderAegisItem() {
        super(new Properties()
                .stacksTo(1)
                .durability(1561)
        );
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ShieldRendererEvent.instance;
            }
        });
    }

    public static boolean isSecondForm(ItemStack stack) {
        return stack.hasTag() && stack.getTag() != null && stack.getTag().getBoolean(SECOND_FORM_TAG);
    }

    public static float getCharge(ItemStack stack) {
        return stack.hasTag() && stack.getTag() != null ? stack.getTag().getFloat(CHARGE_TAG) : 0.0F;
    }

    public static void addBlockedDamageCharge(ItemStack stack, float blockedDamage) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putFloat(CHARGE_TAG, Mth.clamp(tag.getFloat(CHARGE_TAG) + blockedDamage, 0.0F, MAX_CHARGE));
    }

    public static boolean activateSecondForm(Player player, ItemStack stack) {
        if (!(stack.getItem() instanceof EnderAegisItem
                && !isSecondForm(stack)
                && getCharge(stack) >= MAX_CHARGE)) {
            return false;
        }

        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(SECOND_FORM_TAG, true);
        tag.putInt(SECOND_FORM_TICKS_TAG, SECOND_FORM_DURATION_TICKS);
        tag.putFloat(CHARGE_TAG, 0.0F);
        player.getCooldowns().addCooldown(stack.getItem(), SECOND_FORM_DURATION_TICKS);
        return true;
    }

    public static boolean isFullyCharged(ItemStack stack) {
        return getCharge(stack) >= MAX_CHARGE;
    }

    public static void shieldShoot(Level level, Entity entity) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        Vec3 eye = entity.getEyePosition(1.0F);
        Vec3 look = entity.getLookAngle();

        if (entity instanceof Mob mob) {
            LivingEntity target = mob.getTarget();
            if (target != null) {
                look = target.getEyePosition(1.0F).subtract(eye);
            }
        } else if (entity instanceof Player) {
            look = new Vec3(look.x, 0.0D, look.z);
        }

        if (look.lengthSqr() < 1.0E-6D) {
            float yawRad = (float) Math.toRadians(entity.getYRot());
            look = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
        }
        Vec3 forward = look.normalize();

        Vec3 up = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = forward.cross(up).normalize();

        double spawnForward = 0.0D;
        double spread = 0.05D;
        float velocity = 1.2F;
        float inaccuracy = 0.0F;

        Vec3[] offsets = new Vec3[] {
                Vec3.ZERO,
                up,
                up.scale(-1.0D),
                right.scale(-1.0D),
                right
        };

        for (Vec3 off : offsets) {
            Vec3 spawnPos = eye.add(forward.scale(spawnForward)).add(off.scale(0.15D));
            Vec3 dir = forward.add(off.scale(spread)).normalize();

            EnderAegisProjectile proj = new EnderAegisProjectile(
                    AnnoyingVillagersModEntities.ENDER_AEGIS_PROJECTILE.get(), level
            );
            proj.setOwner(entity);
            proj.setBaseDamage(15.0F);
            proj.setKnockback(5);
            proj.setSilent(true);
            proj.setPierceLevel((byte) 5);

            proj.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
            proj.shoot(dir.x, dir.y, dir.z, velocity, inaccuracy);

            serverLevel.addFreshEntity(proj);
        }

        Vec3 sparkFrom = eye.add(0.0D, -1.0D, 0.0D);
        Vec3 sparkTo = eye.add(forward.scale(1.2D)).add(0.0D, -1.0D, 0.0D);
        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
                new ClientboundEnderAegisSparkFx(sparkFrom, sparkTo)
        );

        level.playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.COOL_DOWN.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        level.playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.ENDER_SHOT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        level.playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.BLOOM.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return super.isFoil(stack) || isFullyCharged(stack);
    }

    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag) {
            CompoundTag tag = itemstack.getTag();
            if (tag != null && tag.getBoolean(SECOND_FORM_TAG)) {
                HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);

                if (!level.isClientSide()) {
                    int ticks = tag.getInt(SECOND_FORM_TICKS_TAG);
                    if (ticks > 0) {
                        tag.putInt(SECOND_FORM_TICKS_TAG, ticks - 1);
                    } else {
                        tag.putBoolean(SECOND_FORM_TAG, false);
                        tag.remove(SECOND_FORM_TICKS_TAG);
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        float charge = getCharge(itemstack);
        list.add(Component.translatable("tooltip.annoyingvillagers.ender_aegis"));
        addChargeTooltip(list, charge);
    }

    private static void addChargeTooltip(List<Component> tooltip, float charge) {
        int displayCharge = Mth.clamp(Mth.floor(charge), 0, (int) MAX_CHARGE);
        tooltip.add(
                Component.literal("Shield Charge")
                        .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(CHARGE_COLOR)))
        );
        tooltip.add(
                Component.literal(displayCharge + " / " + (int) MAX_CHARGE)
                        .withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_TEXT_COLOR)))
        );
        tooltip.add(buildChargeMeter(charge));

        if (charge >= MAX_CHARGE) {
            tooltip.add(
                    Component.literal("Fully Charged")
                            .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(CHARGE_FULL_COLOR)))
            );
        }
    }

    private static Component buildChargeMeter(float charge) {
        int filledSteps = Math.round((charge / MAX_CHARGE) * CHARGE_METER_STEPS);
        filledSteps = Mth.clamp(filledSteps, 0, CHARGE_METER_STEPS);

        MutableComponent meter = Component.empty();
        meter.append(
                Component.literal("🛡 ")
                        .withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_COLOR)))
        );

        for (int i = 0; i < CHARGE_METER_STEPS; i++) {
            boolean filled = i < filledSteps;
            meter.append(
                    Component.literal(filled ? "▰" : "▱")
                            .withStyle(style -> style.withColor(TextColor.fromRgb(filled ? CHARGE_COLOR : CHARGE_DIM_COLOR)))
            );
        }

        return meter;
    }
}
