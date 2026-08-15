package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.gameasset.AnimsEnderAegis;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;
import yesman.epicfight.world.entity.eventlistener.TakeDamageEvent;

import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnderAegisSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("348aa19d-7c78-4959-9639-00c467ed258d");
    private static final float RESOURCE_PER_PARRY = 5.0F;

    public EnderAegisSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public static void onParry(ServerPlayerPatch serverPlayerPatch) {
        SkillContainer container = serverPlayerPatch.getSkill(AVSkills.ENDER_AEGIS);
        if (container == null
                || !(container.getSkill() instanceof EnderAegisSkill skill)
                || container.getStack() >= skill.getMaxStack()) {
            return;
        }

        float gainedResource = Math.min(RESOURCE_PER_PARRY, container.getNeededResource());
        if (gainedResource > 0.0F) {
            skill.setConsumptionSynchronize(container, container.getResource() + gainedResource);
        }
    }

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)
                || event.getBlockedDamage() <= 0.0F
                || !(serverPlayer.getUseItem().getItem() instanceof EnderAegisItem)) {
            return;
        }

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, PlayerPatch.class);
        if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
            onParry(serverPlayerPatch);
        }
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        if (!this.isActivated(container)) {
            super.executeOnServer(container, args);
            container.getExecutor().playAnimationSynchronized(AnimsEnderAegis.ENDER_AEGIS_INNATE, 0.0F);
        }
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        super.cancelOnServer(container, args);
    }

    @Override
    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    @Override
    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                EventType.TAKE_DAMAGE_EVENT_ATTACK,
                EVENT_UUID,
                EnderAegisSkill::handleIncomingAttack
        );
    }

    private static void handleIncomingAttack(TakeDamageEvent.Attack event) {
        ItemStack itemStack = event.getPlayerPatch().getOriginal().getMainHandItem();
        if (!(itemStack.getItem() instanceof EnderAegisItem)) {
            return;
        }

        tryBlockDuringInnate(event, event.getPlayerPatch());
    }

    private static void tryBlockDuringInnate(
            TakeDamageEvent.Attack event,
            ServerPlayerPatch serverPlayerPatch
    ) {
        DamageSource damageSource = event.getDamageSource();
        if (damageSource.is(DamageTypes.MAGIC)
                || damageSource.is(DamageTypes.EXPLOSION)
                || damageSource.is(DamageTypes.ON_FIRE)
                || damageSource.is(DamageTypes.IN_FIRE)
                || damageSource.is(DamageTypes.FALL)) {
            return;
        }

        PlayerPatch<?> playerPatch = event.getPlayerPatch();
        AnimationPlayer animationPlayer = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null));
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
        EntityState entityState = dynamicAnimation.get().getState(playerPatch, animationPlayer.getElapsedTime());
        if (dynamicAnimation != AnimsEnderAegis.ENDER_AEGIS_INNATE || entityState.getLevel() >= 3) {
            return;
        }

        Entity attacker = damageSource.getEntity();
        ServerPlayer serverPlayer = serverPlayerPatch.getOriginal();
        if (attacker == null || !isInFront(serverPlayer, attacker)) {
            return;
        }

        event.setCanceled(true);
        event.setResult(AttackResult.ResultType.BLOCKED);
        attacker.setDeltaMovement(new Vec3(attacker.getLookAngle().x * -0.2D, 0.0D, attacker.getLookAngle().z * -0.2D));
        serverPlayer.setDeltaMovement(new Vec3(serverPlayer.getLookAngle().x * -0.2D, 0.0D, serverPlayer.getLookAngle().z * -0.2D));
        if (serverPlayer.level() instanceof ServerLevel serverLevel) {
            EpicfightUtil.damageBlocked(damageSource, serverPlayer, serverLevel);
        }
        onParry(serverPlayerPatch);
    }

    private static boolean isInFront(ServerPlayer player, Entity attacker) {
        Vec3 toAttacker = attacker.position().subtract(player.getEyePosition()).normalize();
        return toAttacker.dot(player.getViewVector(1.0F)) > 0.0D;
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        Player player = container.getExecutor().getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (!(itemStack.getItem() instanceof EnderAegisItem)) {
            return;
        }

        if (container.getStack() >= 1) {
            if (!itemStack.getOrCreateTag().getBoolean(EnderAegisItem.AWAKEN_SOUND_PLAYED_TAG)) {
                player.playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(), 0.5F, 1.0F);
                itemStack.getOrCreateTag().putBoolean(EnderAegisItem.AWAKEN_SOUND_PLAYED_TAG, true);
            }
        } else if (itemStack.hasTag() && itemStack.getTag() != null) {
            itemStack.getTag().remove(EnderAegisItem.AWAKEN_SOUND_PLAYED_TAG);
        }
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
        super.onRemoved(container);
    }
}
