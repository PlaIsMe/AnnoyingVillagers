package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.entity.EnderAegisProjectile;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundEnderAegisSparkFx;
import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.util.LegacyShieldProperties;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnderAegisItem extends ShieldItem implements RigCombatProfileProvider {
    private static final double ATTACK_DAMAGE_MODIFIER = 7.0D;
    private static final double ATTACK_SPEED_MODIFIER = -2.8D;
    public static final String SECOND_FORM_TAG = "SecondForm";
    public static final String AWAKEN_SOUND_PLAYED_TAG = "PlaySound";
    private static final String SECOND_FORM_UNTIL_TAG = "SecondFormUntil";
    private static final String CHARGE_TAG = "EnderAegisCharge";
    private static final String SPECIAL_COOLDOWN_TAG = "AVEnderAegisSpecialCooldown";
    private static final float MAX_CHARGE = 100.0F;
    private static final int SECOND_FORM_DURATION_TICKS = 20 * 60;
    private static final int SPECIAL_COOLDOWN_TICKS = 20;
    public EnderAegisItem() {
        super(LegacyShieldProperties.withBlocking(com.pla.annoyingvillagers.util.LegacyItemProperties.create()
                .stacksTo(1).durability(1561).fireResistant())
                .attributes(
                        ItemAttributeModifiers.builder()
                                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, ATTACK_DAMAGE_MODIFIER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, ATTACK_SPEED_MODIFIER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .build()));
    }

    public static boolean isSecondForm(ItemStack stack) {
        return LegacyItemData.has(stack) && LegacyItemData.get(stack) != null && LegacyItemData.get(stack).getBooleanOr(SECOND_FORM_TAG, false);
    }

    public static void setSecondForm(ItemStack stack,boolean secondForm) {
        if (secondForm) {
            LegacyItemData.update(stack, tag -> tag.putBoolean(SECOND_FORM_TAG, true));
        } else if (LegacyItemData.has(stack) && LegacyItemData.get(stack) != null) {
            LegacyItemData.update(stack, tag -> {
                tag.remove(SECOND_FORM_TAG);
                tag.remove(AWAKEN_SOUND_PLAYED_TAG);
            });
        }
    }

    public static float getCharge(ItemStack stack) {
        CompoundTag tag = LegacyItemData.get(stack);
        return Mth.clamp(tag == null ? 0.0F : tag.getFloatOr(CHARGE_TAG, 0.0F), 0.0F, MAX_CHARGE);
    }

    public static void addBlockedCharge(ItemStack stack, Player player, float blockedDamage) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || blockedDamage <= 0.0F || isSecondForm(stack)) return;
        float charge = Mth.clamp(getCharge(stack) + blockedDamage, 0.0F, MAX_CHARGE);
        LegacyItemData.update(stack, tag -> tag.putFloat(CHARGE_TAG, charge));
        if (charge >= MAX_CHARGE) {
            setSecondForm(stack, true);
            LegacyItemData.update(stack, tag -> tag.putLong(SECOND_FORM_UNTIL_TAG, player.level().getGameTime() + SECOND_FORM_DURATION_TICKS));
            player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(stack.getItem()), SECOND_FORM_DURATION_TICKS);
        }
    }

    public static boolean activateVanillaSpecial(Player player) {
        return false;
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
            ((com.pla.annoyingvillagers.mixin.AbstractArrowAccessor) (Object) proj)
                    .annoyingVillagers$setPierceLevel((byte) 5);

            proj.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
            proj.shoot(dir.x, dir.y, dir.z, velocity, inaccuracy);

            serverLevel.addFreshEntity(proj);
        }

        Vec3 sparkFrom = eye.add(0.0D, -1.0D, 0.0D);
        Vec3 sparkTo = eye.add(forward.scale(1.2D)).add(0.0D, -1.0D, 0.0D);
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new ClientboundEnderAegisSparkFx(sparkFrom, sparkTo));

        level.playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.ENDER_SHOT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void secondFormNbtTag(@NotNull ItemStack itemstack, @NotNull Level level, @NotNull Entity entity) {
//        Add this code in AV_EFM
//        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
//        if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
//            SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_AEGIS);
//            if (skillContainer != null && LegacyItemData.get(itemstack) != null) {
//                if (!skillContainer.isActivated() && LegacyItemData.get(itemstack).getBooleanOr("SecondForm", false)) {
//                    LegacyItemData.get(itemstack).putBoolean("SecondForm", false);
//                }
//                if (skillContainer.isActivated() && !LegacyItemData.get(itemstack).getBooleanOr("SecondForm", false)) {
//                    LegacyItemData.get(itemstack).putBoolean("SecondForm", true);
//                }
//            }
//        }
//        Handle vanilla code
    }

    public void inventoryTick(net.minecraft.world.item.ItemStack itemstack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int i = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, itemstack);
        boolean flag = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        super.inventoryTick(itemstack, level, entity, equipmentSlot);
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && !level.isClientSide() && entity instanceof Player player && !isSecondForm(itemstack) && getCharge(itemstack) >= MAX_CHARGE) {
            setSecondForm(itemstack, true);
            LegacyItemData.update(itemstack, tag -> tag.putLong(SECOND_FORM_UNTIL_TAG, level.getGameTime() + SECOND_FORM_DURATION_TICKS));
            player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(itemstack.getItem()), SECOND_FORM_DURATION_TICKS);
        }
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && !level.isClientSide() && isSecondForm(itemstack)) {
            CompoundTag tag = LegacyItemData.get(itemstack);
            long remaining = tag != null && tag.contains(SECOND_FORM_UNTIL_TAG)
                    ? tag.getLongOr(SECOND_FORM_UNTIL_TAG, 0L) - level.getGameTime()
                    : 0L;
            if (remaining <= 0L) {
                setSecondForm(itemstack, false);
                LegacyItemData.update(itemstack, data -> {
                    data.remove(SECOND_FORM_UNTIL_TAG);
                    data.putFloat(CHARGE_TAG, 0.0F);
                });
            } else if (entity instanceof Player player && player.getCooldowns().getCooldownPercent(new net.minecraft.world.item.ItemStack(itemstack.getItem()), 0.0F) <= 0.0F) {
                player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(itemstack.getItem()), (int)Math.min(Integer.MAX_VALUE, remaining));
            }
        }
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && flag && isSecondForm(itemstack)) HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
    }

    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, display, list, tooltipflag);
        list.accept(Component.translatable("tooltip.annoyingvillagers.ender_aegis"));
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.AEGIS_HEROBRINE;
    }
}
