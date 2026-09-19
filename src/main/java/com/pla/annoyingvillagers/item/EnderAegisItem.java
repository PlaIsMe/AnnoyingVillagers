package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.entity.EnderAegisProjectile;
import com.pla.annoyingvillagers.event.ShieldRendererEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundEnderAegisSparkFx;
import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

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
        super(new Properties().stacksTo(1).durability(1561).fireResistant().attributes(
                ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, ATTACK_DAMAGE_MODIFIER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, ATTACK_SPEED_MODIFIER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ShieldRendererEvent.instance;
            }
        });
    }

    public static boolean isSecondForm(ItemStack stack) {
        return LegacyItemData.has(stack) && LegacyItemData.get(stack) != null && LegacyItemData.get(stack).getBoolean(SECOND_FORM_TAG);
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
        return Mth.clamp(LegacyItemData.getOrCreate(stack).getFloat(CHARGE_TAG), 0.0F, MAX_CHARGE);
    }

    public static void addBlockedCharge(ItemStack stack, Player player, float blockedDamage) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || blockedDamage <= 0.0F || isSecondForm(stack)) return;
        float charge = Mth.clamp(getCharge(stack) + blockedDamage, 0.0F, MAX_CHARGE);
        LegacyItemData.update(stack, tag -> tag.putFloat(CHARGE_TAG, charge));
        if (charge >= MAX_CHARGE) {
            setSecondForm(stack, true);
            LegacyItemData.update(stack, tag -> tag.putLong(SECOND_FORM_UNTIL_TAG, player.level().getGameTime() + SECOND_FORM_DURATION_TICKS));
            player.getCooldowns().addCooldown(stack.getItem(), SECOND_FORM_DURATION_TICKS);
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
            proj.setPierceLevel((byte) 5);

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
//                if (!skillContainer.isActivated() && LegacyItemData.get(itemstack).getBoolean("SecondForm")) {
//                    LegacyItemData.get(itemstack).putBoolean("SecondForm", false);
//                }
//                if (skillContainer.isActivated() && !LegacyItemData.get(itemstack).getBoolean("SecondForm")) {
//                    LegacyItemData.get(itemstack).putBoolean("SecondForm", true);
//                }
//            }
//        }
//        Handle vanilla code
    }

    public void inventoryTick(@NotNull ItemStack itemstack,@NotNull Level level,@NotNull Entity entity,int i,boolean flag) {
        super.inventoryTick(itemstack,level,entity,i,flag);
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && !level.isClientSide() && entity instanceof Player player && !isSecondForm(itemstack) && getCharge(itemstack) >= MAX_CHARGE) {
            setSecondForm(itemstack, true);
            LegacyItemData.update(itemstack, tag -> tag.putLong(SECOND_FORM_UNTIL_TAG, level.getGameTime() + SECOND_FORM_DURATION_TICKS));
            player.getCooldowns().addCooldown(itemstack.getItem(), SECOND_FORM_DURATION_TICKS);
        }
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && !level.isClientSide() && isSecondForm(itemstack)) {
            CompoundTag tag = LegacyItemData.get(itemstack);
            long remaining = tag != null && tag.contains(SECOND_FORM_UNTIL_TAG)
                    ? tag.getLong(SECOND_FORM_UNTIL_TAG) - level.getGameTime()
                    : 0L;
            if (remaining <= 0L) {
                setSecondForm(itemstack, false);
                LegacyItemData.update(itemstack, data -> {
                    data.remove(SECOND_FORM_UNTIL_TAG);
                    data.putFloat(CHARGE_TAG, 0.0F);
                });
            } else if (entity instanceof Player player && player.getCooldowns().getCooldownPercent(itemstack.getItem(), 0.0F) <= 0.0F) {
                player.getCooldowns().addCooldown(itemstack.getItem(), (int)Math.min(Integer.MAX_VALUE, remaining));
            }
        }
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && flag && isSecondForm(itemstack)) HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
    }

    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.ender_aegis"));
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.AEGIS_HEROBRINE;
    }
}
