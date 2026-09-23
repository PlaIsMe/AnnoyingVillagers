package com.pla.annoyingvillagers.mixin.compat.punchy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.renderer.ObsidianArmorExtensionRenderer;
import com.pla.annoyingvillagers.item.HerobrineObsidianArmorCharge;
import com.pla.annoyingvillagers.item.LegacySwordItem;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.neoforged.fml.ModList;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import punchy.client.render.PunchyArmRenderer;

/** Punchy first-person compatibility for AV weapon models and Obsidian armor. */
@Mixin(value = PunchyArmRenderer.class, remap = false)
public abstract class PunchyArmRendererMixin {
    /**
     * Punchy anchors items to its animated player arm and normally asks the item
     * model for a third-person transform. AV's oversized weapon models have
     * deliberately different first-person transforms, however. Applying their
     * NPC/third-person transform in camera space makes great weapons fill the
     * screen and can move the flat wooden weapons completely behind the camera.
     *
     * Change only the model display context. Punchy's arm pose, combat animation,
     * tuning and physics remain active, while the AV model supplies the transform
     * authored for first person. The local index is Punchy 2.8a's sole
     * ItemDisplayContext local (named "context" in its debug table).
     */
    @ModifyVariable(
            method = "renderItemInHand",
            at = @At("STORE"),
            index = 36,
            require = 1,
            allow = 1
    )
    private static ItemDisplayContext av$useFirstPersonItemTransform(
            ItemDisplayContext context,
            ItemInHandRenderer handRenderer,
            PlayerModel playerModel,
            AvatarRenderState renderState,
            LocalPlayer player,
            HumanoidArm arm,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int light,
            Matrix4f rootWorld,
            ModelPart geoArm,
            ModelPart geoItem,
            ModelPart geoGrip,
            float partialTick
    ) {
        InteractionHand hand = arm == player.getMainArm()
                ? InteractionHand.MAIN_HAND
                : InteractionHand.OFF_HAND;
        ItemStack stack = player.getItemInHand(hand);
        Identifier itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (itemId == null
                || !AnnoyingVillagers.MODID.equals(itemId.getNamespace())
                || !av$usesAuthoredWeaponTransform(stack)) {
            return context;
        }

        return arm == HumanoidArm.LEFT
                ? ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                : ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
    }

    private static boolean av$usesAuthoredWeaponTransform(ItemStack stack) {
        return stack.getItem() instanceof LegacySwordItem
                || stack.getItem() instanceof AxeItem
                || stack.getItem() instanceof HoeItem
                || stack.getItem() instanceof ShovelItem;
    }

    // Punchy 2.8 adds a vanilla sleeve pass that bypasses Forge's custom armor
    // model/texture hooks. Obsidian's custom UVs turn that sleeve solid black.
    // Its animated arm tiles are already drawn by av$renderObsidianRightArm.
    // Older Punchy versions have no renderArmArmor method.
    @Inject(method = "renderArmArmor", at = @At("HEAD"), cancellable = true, require = 0)
    private static void av$skipVanillaObsidianSleeve(
            AvatarRenderer<AbstractClientPlayer> renderer,
            AvatarRenderState renderState,
            PlayerModel model,
            AbstractClientPlayer player,
            HumanoidArm arm,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int light,
            CallbackInfo ci
    ) {
        if (player != null && HerobrineObsidianArmorCharge.isChestplate(
                player.getItemBySlot(EquipmentSlot.CHEST))) {
            ci.cancel();
        }
    }

    @Inject(
            method = "renderArm",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE,
                    remap = true
            ),
            require = 1
    )
    private static void av$renderObsidianRightArm(
            PlayerModel model,
            AbstractClientPlayer player,
            HumanoidArm arm,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int light,
            Identifier skin,
            boolean slim,
            float partialTick,
            CallbackInfo ci
    ) {
        // Epic Fight owns the whole first-person armor pass when its compatibility
        // add-on is present, so drawing here as well would duplicate the tiles.
        if (arm != HumanoidArm.RIGHT || ModList.get().isLoaded("epicfight")) return;

        poseStack.pushPose();
        try {
            model.rightArm.translateAndRotate(poseStack);
            ObsidianArmorExtensionRenderer.renderFirstPersonRightArm(player, poseStack, collector, light);
        } finally {
            poseStack.popPose();
        }
    }
}
