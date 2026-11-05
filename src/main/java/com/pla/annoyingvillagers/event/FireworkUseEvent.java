package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.VillagerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class FireworkUseEvent {

    @SubscribeEvent
    public static void onRightClickBlock(RightClickBlock event) {
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }

        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!tryUseVillagerSignalFirework(serverLevel, event.getPos(), event.getEntity(), event.getHand())) {
            return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    private static boolean tryUseVillagerSignalFirework(ServerLevel serverLevel,
                                                        BlockPos clickedPos,
                                                        Player player,
                                                        InteractionHand hand) {
        ItemStack usedStack = player.getItemInHand(hand);
        if (!VillagerUtil.isBlackCreeperSignalFirework(usedStack)) {
            return false;
        }

        if (player.experienceLevel < 5) {
            return false;
        }

        player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(usedStack.getItem()), 250);
        player.giveExperienceLevels(-5);

        if (!player.getAbilities().instabuild) {
            usedStack.shrink(1);
        }

        double x = clickedPos.getX() + 0.5D;
        double y = clickedPos.getY() + 1.0D;
        double z = clickedPos.getZ() + 0.5D;
        float yaw = player.getYRot();

        serverLevel.sendParticles(ParticleTypes.FIREWORK, x, y, z, 40, 0.0D, 3.0D, 0.0D, 1.0D);
        serverLevel.playSound(null, clickedPos, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.NEUTRAL, 1.0F, 2.0F);
        VillagerUtil.launchBlackCreeperSignalFirework(serverLevel, x, y, z);

        new DelayedTask(50) {
            @Override
            public void run() {
                BlockPos signalPos = BlockPos.containing(x, y, z);
                if (!serverLevel.isLoaded(signalPos)) {
                    return;
                }

                serverLevel.playSound(null, signalPos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1.0F, 2.0F);
                VillagerUtil.summonRandomVillagerSupportWave(serverLevel, new Vec3(x, y, z), yaw);
            }
        };

        return true;
    }
}