package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkillDataKeys;
import com.pla.annoyingvillagers.gameasset.AnimsLegendarySword;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityDecorations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.entity.eventlistener.DealDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

import javax.annotation.Nullable;
import java.util.UUID;

public class LegendarySwordSkill extends WeaponInnateSkill {
    public static final String AWAKENED_TAG = "LegendarySwordAwakened";
    public static final String AWAKEN_UNTIL_TAG = "LegendarySwordAwakenUntil";
    public static final int AWAKEN_DURATION_TICKS = 20 * 15;

    private static final int AWAKEN_EFFECT_AMPLIFIER = 2;
    private static final double AWAKEN_ATTACK_SPEED_MULTIPLIER = 0.5D;
    private static final float TRIED_CHANCE = 0.3F;
    private static final UUID AWAKEN_DAMAGE_EVENT_UUID = UUID.fromString("b3bf9455-bf68-4b6a-8f8b-c56c7484ad0c");
    private static final UUID AWAKEN_ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("db78a10d-0191-4728-8a2c-2cb8efe69dfa");
    private static final ResourceLocation AWAKEN_TRAIL_MODIFIER = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "legendary_sword_awaken_trail");

    public LegendarySwordSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public static boolean isAwakened(ItemStack stack, @Nullable Entity entity) {
        return isAwakened(stack, entity == null ? null : entity.level());
    }

    public static boolean isAwakened(ItemStack stack, @Nullable Level level) {
        if (!stack.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()) || !stack.hasTag()) {
            return false;
        }

        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.getBoolean(AWAKENED_TAG)) {
            return false;
        }

        if (!tag.contains(AWAKEN_UNTIL_TAG, Tag.TAG_LONG)) {
            return false;
        }

        return level != null && level.getGameTime() < tag.getLong(AWAKEN_UNTIL_TAG);
    }

    public static boolean isAwakened(SkillContainer container) {
        if (container == null || container.getExecutor() == null) {
            return false;
        }

        Player player = container.getExecutor().getOriginal();
        return isAwakened(player.getMainHandItem(), player.level())
                && Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED.get()));
    }

    public static void clearAwakeningTags(Player player) {
        Inventory inventory = player.getInventory();
        inventory.items.forEach(LegendarySwordSkill::clearAwakeningTag);
        inventory.offhand.forEach(LegendarySwordSkill::clearAwakeningTag);
        inventory.armor.forEach(LegendarySwordSkill::clearAwakeningTag);
    }

    public static void clearExpiredAwakeningTags(Player player) {
        Inventory inventory = player.getInventory();
        inventory.items.forEach(stack -> clearExpiredAwakeningTag(stack, player.level()));
        inventory.offhand.forEach(stack -> clearExpiredAwakeningTag(stack, player.level()));
        inventory.armor.forEach(stack -> clearExpiredAwakeningTag(stack, player.level()));
    }

    private static boolean hasAwakeningTag(ItemStack stack) {
        return stack.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())
                && stack.hasTag()
                && stack.getTag() != null
                && stack.getTag().getBoolean(AWAKENED_TAG);
    }

    private static void clearExpiredAwakeningTag(ItemStack stack, Level level) {
        if (hasAwakeningTag(stack) && !isAwakened(stack, level)) {
            clearAwakeningTag(stack);
        }
    }

    private static void clearAwakeningTag(ItemStack stack) {
        if (!stack.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()) || !stack.hasTag()) {
            return;
        }

        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return;
        }

        tag.remove(AWAKENED_TAG);
        tag.remove(AWAKEN_UNTIL_TAG);

        if (tag.isEmpty()) {
            stack.setTag(null);
        }
    }

    public void startAwakening(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        if (player.level().isClientSide()) {
            return;
        }

        ItemStack stack = player.getMainHandItem();
        if (!stack.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())) {
            return;
        }

        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(AWAKENED_TAG, true);
        tag.putLong(AWAKEN_UNTIL_TAG, player.level().getGameTime() + AWAKEN_DURATION_TICKS);

        container.getDataManager().setDataSync(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED.get(), true);
        this.setMaxDurationSynchronize(container, AWAKEN_DURATION_TICKS);
        this.setDurationSynchronize(container, AWAKEN_DURATION_TICKS);
        applyAwakeningEffects(player);
    }

    private void clearAwakeningState(SkillContainer container) {
        if (container.getExecutor().isLogicalClient()) {
            return;
        }

        Player player = container.getExecutor().getOriginal();
        boolean hadAwakeningState = Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED.get()))
                || hasAwakeningTag(player.getMainHandItem());

        removeAwakeningAttackSpeed(player);

        if (!hadAwakeningState) {
            return;
        }

        clearAwakeningTags(player);
        container.getDataManager().setDataSync(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED.get(), false);
        this.setDurationSynchronize(container, 0);
        this.setMaxDurationSynchronize(container, this.maxDuration);
    }

    private static void applyAwakeningEffects(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        applyAwakeningAttackSpeed(player);
    }

    private static void applyAwakeningAttackSpeed(Player player) {
        applyAwakeningAttackSpeedModifier(player, Attributes.ATTACK_SPEED);
        applyAwakeningAttackSpeedModifier(player, EpicFightAttributes.OFFHAND_ATTACK_SPEED.get());
    }

    private static void applyAwakeningAttackSpeedModifier(Player player, Attribute attribute) {
        AttributeInstance attackSpeed = player.getAttribute(attribute);
        if (attackSpeed == null) {
            return;
        }

        attackSpeed.removeModifier(AWAKEN_ATTACK_SPEED_MODIFIER_UUID);
        attackSpeed.addTransientModifier(new AttributeModifier(
                AWAKEN_ATTACK_SPEED_MODIFIER_UUID,
                "Legendary sword awakening attack speed",
                AWAKEN_ATTACK_SPEED_MULTIPLIER,
                AttributeModifier.Operation.MULTIPLY_TOTAL
        ));
    }

    private static void removeAwakeningAttackSpeed(Player player) {
        removeAwakeningAttackSpeedModifier(player, Attributes.ATTACK_SPEED);
        removeAwakeningAttackSpeedModifier(player, EpicFightAttributes.OFFHAND_ATTACK_SPEED.get());
    }

    private static void removeAwakeningAttackSpeedModifier(Player player, Attribute attribute) {
        AttributeInstance attackSpeed = player.getAttribute(attribute);
        if (attackSpeed != null) {
            attackSpeed.removeModifier(AWAKEN_ATTACK_SPEED_MODIFIER_UUID);
        }
    }

    private static boolean shouldUseAwakenedTrail(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        ItemStack mainHand = player.getMainHandItem();
        return mainHand.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())
                && (Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED.get()))
                || isAwakened(mainHand, player.level()));
    }

    private boolean canExecuteAwakenedInnate(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        ItemStack mainHand = player.getMainHandItem();
        return this.checkExecuteCondition(container)
                && isAwakened(container)
                && mainHand.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())
                && EpicFightCapabilities.getItemStackCapability(mainHand).getInnateSkill(container.getExecutor(), mainHand) == this
                && player.getVehicle() == null;
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        return super.canExecute(container) || canExecuteAwakenedInnate(container);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (this.isActivated(skillContainer) && !isAwakened(skillContainer)) {
            return;
        }

        super.executeOnServer(skillContainer, friendlyByteBuf);
        skillContainer.activate();

        if (skillContainer.getExecutor().getOriginal().getOffhandItem().is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) {
            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.LEGENDARYSWORD_WOOPIE_FLY, 0.0F);
        } else {
            skillContainer.getExecutor().playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_INNATE, 0.0F);
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        clearAwakeningState(skillContainer);
        skillContainer.deactivate();
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.DEAL_DAMAGE_EVENT_DAMAGE,
                AWAKEN_DAMAGE_EVENT_UUID,
                (DealDamageEvent.Damage event) -> {
                    if (event.getPlayerPatch().isLogicalClient() || !isAwakened(container) || event.getAttackDamage() <= 0.0F) {
                        return;
                    }

                    Player player = event.getPlayerPatch().getOriginal();
                    if (player.getRandom().nextFloat() >= TRIED_CHANCE) {
                        return;
                    }

                    LivingEntity target = event.getTarget();
                    if (!target.isAlive()) {
                        return;
                    }

                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
                    if (targetPatch != null) {
                        targetPatch.playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_KNOCKDOWN, 0.0F);
                    }
                },
                10
        );
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onInitiateClient(SkillContainer container) {
        container.getExecutor().getEntityDecorations().addTrailInfoModifier(AWAKEN_TRAIL_MODIFIER, new EntityDecorations.AnimationPropertyModifier<>() {
            @Override
            public TrailInfo getModifiedValue(TrailInfo val, CapabilityItem object) {
                if (val.hand() != InteractionHand.MAIN_HAND || !shouldUseAwakenedTrail(container)) {
                    return val;
                }

                return val.unpackAsBuilder()
                        .r(253.0F / 255.0F)
                        .g(255.0F / 255.0F)
                        .b(118.0F / 255.0F)
                        .create();
            }

            @Override
            public boolean shouldRemove() {
                return container.getExecutor().getSkill(LegendarySwordSkill.this) == null;
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onRemoveClient(SkillContainer container) {
        container.getExecutor().getEntityDecorations().removeTrailInfoModifier(AWAKEN_TRAIL_MODIFIER);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.DEAL_DAMAGE_EVENT_DAMAGE, AWAKEN_DAMAGE_EVENT_UUID);
        clearAwakeningState(container);
        super.onRemoved(container);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (container.getExecutor().isLogicalClient()) {
            if (Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED.get()))) {
                int remainingDuration = container.getRemainDuration() - 1;
                container.setDuration(remainingDuration);

                if (remainingDuration <= 0) {
                    container.deactivate();
                }
            }

            return;
        }

        Player player = container.getExecutor().getOriginal();
        if (player.tickCount % 20 == 0) {
            clearExpiredAwakeningTags(player);
        }

        if (!Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED.get()))) {
            removeAwakeningAttackSpeed(player);
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (!isAwakened(mainHand, player.level())) {
            this.cancelOnServer(container, null);
            return;
        }

        int remainingDuration = container.getRemainDuration() - 1;
        container.setDuration(remainingDuration);

        if (remainingDuration <= 0) {
            this.cancelOnServer(container, null);
            return;
        }

        if (player.tickCount % 10 == 0) {
            applyAwakeningEffects(player);
        }
    }
}
