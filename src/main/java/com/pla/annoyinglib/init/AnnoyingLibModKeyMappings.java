package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.network.ServerboundActivateArmor;
import com.pla.annoyinglib.network.SpecialAttackMessage;
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
public class AnnoyingLibModKeyMappings {
    private static final double SPECIAL_ATTACK_CROSSHAIR_RANGE = 32.0D;

    public static final KeyMapping SPECIAL_ATTACK = new KeyMapping(
            "key.annoyinglib.special_attack",
            GLFW.GLFW_KEY_C,
            "key.categories.annoyinglib") {
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
                AnnoyingLib.PACKET_HANDLER.sendToServer(createSpecialAttackMessage(type, heldTicks));
                this.pressedAtTick = -1;
            }

            this.isDownOld = flag;
        }
    };

    public static final KeyMapping ACTIVATE_ARMOR = new KeyMapping(
            "key.annoyinglib.activate_armor",
            GLFW.GLFW_KEY_Z,
            "key.categories.annoyinglib"
    );

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(SPECIAL_ATTACK);
        event.register(ACTIVATE_ARMOR);
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
                while (ACTIVATE_ARMOR.consumeClick()) AnnoyingLib.PACKET_HANDLER.sendToServer(new ServerboundActivateArmor());
            }
        }
    }
}
