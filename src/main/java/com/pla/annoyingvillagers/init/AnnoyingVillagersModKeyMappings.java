package com.pla.annoyingvillagers.init;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.network.ServerboundActivateArmor;
import com.pla.annoyingvillagers.network.SpecialAttackMessage;
import com.pla.annoyingvillagers.network.ThrowingEnderPearlMessage;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class AnnoyingVillagersModKeyMappings {
    private static final double SPECIAL_ATTACK_CROSSHAIR_RANGE = 32.0D;
    private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(
            net.minecraft.resources.Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "annoyingvillagers")
    );

    public static final KeyMapping SPECIAL_ATTACK = new KeyMapping(
            "key.annoyingvillagers.special_attack",
            GLFW.GLFW_KEY_C,
            CATEGORY) {
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
                ClientPacketDistributor.sendToServer(createSpecialAttackMessage(type, heldTicks));
                this.pressedAtTick = -1;
            }

            this.isDownOld = flag;
        }
    };

    public static final KeyMapping THROW_ENDER_PEARL = new KeyMapping(
            "key.annoyingvillagers.throw_ender_pearl",
            GLFW.GLFW_KEY_F,
            CATEGORY) {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag && Minecraft.getInstance().player != null) {
                ClientPacketDistributor.sendToServer(new ThrowingEnderPearlMessage(0, 0));
                ThrowingEnderPearlMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };

    public static final KeyMapping ACTIVATE_ARMOR = new KeyMapping(
            "key.annoyingvillagers.activate_armor",
            GLFW.GLFW_KEY_Z,
            CATEGORY
    );

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        event.register(SPECIAL_ATTACK);
        event.register(THROW_ENDER_PEARL);
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
            org.joml.Vector3fc look = camera.forwardVector();
            return camera.position().add(
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
        public static void onClientTick(ClientTickEvent.Post event) {
            if (false) {
                return;
            }

            Minecraft mc = Minecraft.getInstance();

            if (mc.screen == null) {
                SPECIAL_ATTACK.consumeClick();
                THROW_ENDER_PEARL.consumeClick();
                while (ACTIVATE_ARMOR.consumeClick()) ClientPacketDistributor.sendToServer(new ServerboundActivateArmor());
            }
        }
    }
}
