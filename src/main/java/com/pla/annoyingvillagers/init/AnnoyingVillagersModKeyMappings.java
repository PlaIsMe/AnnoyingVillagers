package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.network.SpecialAttackMessage;
import com.pla.annoyingvillagers.network.ThrowingEnderPearlMessage;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class AnnoyingVillagersModKeyMappings {
    private static final double SPECIAL_ATTACK_CROSSHAIR_RANGE = 32.0D;

    public static final KeyMapping SPECIAL_ATTACK = new KeyMapping(
            "key.annoyingvillagers.special_attack",
            GLFW.GLFW_KEY_C,
            "key.categories.annoyingvillagers") {
        private static final int HOLD_THRESHOLD_TICKS = 10;

        private boolean isDownOld = false;
        private int pressedAtTick = -1;

        @Override
        public void setDown(boolean flag) {
            super.setDown(flag);

            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == null) {
                this.isDownOld = flag;
                return;
            }

            if (!this.isDownOld && flag) {
                this.pressedAtTick = minecraft.player.tickCount;
            }

            if (this.isDownOld && !flag) {
                int heldTicks = this.pressedAtTick >= 0
                        ? minecraft.player.tickCount - this.pressedAtTick
                        : 0;

                int type = heldTicks >= HOLD_THRESHOLD_TICKS ? 1 : 0;
                AnnoyingVillagers.PACKET_HANDLER.sendToServer(createSpecialAttackMessage(type, heldTicks));
                this.pressedAtTick = -1;
            }

            this.isDownOld = flag;
        }
    };

    public static final KeyMapping THROW_ENDER_PEARL = new KeyMapping(
            "key.annoyingvillagers.throw_ender_pearl",
            GLFW.GLFW_KEY_F,
            "key.categories.annoyingvillagers") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag && Minecraft.getInstance().player != null) {
                AnnoyingVillagers.PACKET_HANDLER.sendToServer(new ThrowingEnderPearlMessage(0, 0));
                ThrowingEnderPearlMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };

    public static final KeyMapping DRAGON_FLIGHT_DESCENT_KEY = new KeyMapping(
            "key.annoyingvillagers.dragon_flight_descent",
            GLFW.GLFW_KEY_Z,
            "key.categories.annoyingvillagers"
    );

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(SPECIAL_ATTACK);
        event.register(THROW_ENDER_PEARL);
        event.register(DRAGON_FLIGHT_DESCENT_KEY);
    }

    private static SpecialAttackMessage createSpecialAttackMessage(int type, int heldTicks) {
        Vec3 crosshairTarget = findSpecialAttackCrosshairTarget(Minecraft.getInstance());
        return crosshairTarget == null
                ? new SpecialAttackMessage(type, heldTicks)
                : new SpecialAttackMessage(type, heldTicks, crosshairTarget);
    }

    private static Vec3 findSpecialAttackCrosshairTarget(Minecraft minecraft) {
        if (minecraft.hitResult != null && minecraft.hitResult.getType() != HitResult.Type.MISS) {
            return minecraft.hitResult.getLocation();
        }

        Camera camera = minecraft.gameRenderer.getMainCamera();
        if (camera.isInitialized()) {
            Vector3f look = camera.getLookVector();
            return camera.getPosition().add(
                    look.x() * SPECIAL_ATTACK_CROSSHAIR_RANGE,
                    look.y() * SPECIAL_ATTACK_CROSSHAIR_RANGE,
                    look.z() * SPECIAL_ATTACK_CROSSHAIR_RANGE
            );
        }

        return minecraft.player == null
                ? null
                : minecraft.player.getEyePosition(1.0F).add(minecraft.player.getViewVector(1.0F).scale(SPECIAL_ATTACK_CROSSHAIR_RANGE));
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }

            Minecraft mc = Minecraft.getInstance();

            if (mc.screen == null) {
                SPECIAL_ATTACK.consumeClick();
                THROW_ENDER_PEARL.consumeClick();
            }
        }
    }
}
