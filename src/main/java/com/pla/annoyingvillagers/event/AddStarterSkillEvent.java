package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class AddStarterSkillEvent {
    private static final String KEY = AnnoyingVillagers.MODID + ":has_joined_before";
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent playerloggedinevent) {
        execute(playerloggedinevent, playerloggedinevent.getEntity().level(), playerloggedinevent.getEntity().getX(), playerloggedinevent.getEntity().getY(), playerloggedinevent.getEntity().getZ(), playerloggedinevent.getEntity());
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag oldData = oldPlayer.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag newRoot = newPlayer.getPersistentData();
        CompoundTag newData = newRoot.getCompound(Player.PERSISTED_NBT_TAG);

        newData.merge(oldData);
        newRoot.put(Player.PERSISTED_NBT_TAG, newData);
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute(null, levelaccessor, d0, d1, d2, entity);
    }

    private static CompoundTag persisted(Player p) {
        CompoundTag root = p.getPersistentData();
        CompoundTag data = root.getCompound(Player.PERSISTED_NBT_TAG);
        root.put(Player.PERSISTED_NBT_TAG, data);
        return data;
    }
    public static boolean hasJoinedBefore(Player p) {
        return persisted(p).getBoolean(KEY);
    }
    public static void markJoined(Player p) {
        persisted(p).putBoolean(KEY, true);
    }

    private static void giveSkill(ServerPlayer player, ServerPlayerPatch patch, SkillSlot slot, Skill skill) {
        if (skill == null) return;

        SkillContainer container = patch.getSkillCapability().getSkillContainerFor(slot);
        if (container == null) return;

        if (container.setSkill(skill)) {
            if (skill.getCategory().learnable()) {
                patch.getSkillCapability().addLearnedSkill(skill);
            }

            EpicFightNetworkManager.sendToPlayer(container.createSyncPacketToLocalPlayer(), player);
            EpicFightNetworkManager.sendToAllPlayerTrackingThisEntity(container.createSyncPacketToRemotePlayer(), player);
        }
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (entity instanceof ServerPlayer serverPlayer && !hasJoinedBefore(serverPlayer)) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
                    if (playerPatch == null) return;

                    giveSkill(serverPlayer, playerPatch, SkillSlots.GUARD, EpicFightSkills.GUARD);
                    giveSkill(serverPlayer, playerPatch, SkillSlots.GUARD, EpicFightSkills.PARRYING);
                    giveSkill(serverPlayer, playerPatch, SkillSlots.DODGE, EpicFightSkills.ROLL);

                    markJoined((Player) entity);
                }
            }
        }
    }
}
