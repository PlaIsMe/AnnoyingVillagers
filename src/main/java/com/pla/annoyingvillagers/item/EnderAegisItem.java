package com.pla.annoyingvillagers.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.EnderAegisProjectile;
import com.pla.annoyingvillagers.event.ShieldRendererEvent;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundEnderAegisSparkFx;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;
import java.util.function.Consumer;

public class EnderAegisItem extends ShieldItem {
    private static final double ATTACK_DAMAGE_MODIFIER = 7.0D;
    private static final double ATTACK_SPEED_MODIFIER = -2.8D;
    public static final String SECOND_FORM_TAG = "SecondForm";
    public static final String AWAKEN_SOUND_PLAYED_TAG = "PlaySound";
    private static final String LEGACY_SECOND_FORM_TICKS_TAG = "SecondFormTicks";
    private static final String LEGACY_CHARGE_TAG = "EnderAegisCharge";
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public EnderAegisItem() {
        super(new Properties()
                .stacksTo(1)
                .durability(1561)
                .fireResistant()
        );

        ImmutableMultimap.Builder<Attribute, AttributeModifier> modifiers = ImmutableMultimap.builder();
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                BASE_ATTACK_DAMAGE_UUID,
                "Weapon modifier",
                ATTACK_DAMAGE_MODIFIER,
                AttributeModifier.Operation.ADDITION
        ));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(
                BASE_ATTACK_SPEED_UUID,
                "Weapon modifier",
                ATTACK_SPEED_MODIFIER,
                AttributeModifier.Operation.ADDITION
        ));
        this.defaultModifiers = modifiers.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
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

    private static void setSecondForm(ItemStack stack, boolean secondForm) {
        if (secondForm) {
            stack.getOrCreateTag().putBoolean(SECOND_FORM_TAG, true);
        } else if (stack.hasTag() && stack.getTag() != null) {
            stack.getTag().remove(SECOND_FORM_TAG);
            stack.getTag().remove(AWAKEN_SOUND_PLAYED_TAG);
        }
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

            EnderAegisProjectile projectile = new EnderAegisProjectile(
                    AnnoyingVillagersModEntities.ENDER_AEGIS_PROJECTILE.get(), level
            );
            projectile.setOwner(entity);
            projectile.setBaseDamage(15.0F);
            projectile.setKnockback(5);
            projectile.setSilent(true);
            projectile.setPierceLevel((byte) 5);

            projectile.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
            projectile.shoot(dir.x, dir.y, dir.z, velocity, inaccuracy);

            serverLevel.addFreshEntity(projectile);
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
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (level.isClientSide() || !(entity instanceof Player player)) {
            return;
        }

        CompoundTag tag = stack.getTag();
        if (tag != null) {
            tag.remove(LEGACY_CHARGE_TAG);
            tag.remove(LEGACY_SECOND_FORM_TICKS_TAG);
        }

        boolean secondForm = false;
        if (selected) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer container = serverPlayerPatch.getSkill(AVSkills.ENDER_AEGIS);
                secondForm = container != null && container.getStack() >= 1;
            }
        }

        setSecondForm(stack, secondForm);
        if (secondForm) {
            HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.annoyingvillagers.ender_aegis"));
    }
}
