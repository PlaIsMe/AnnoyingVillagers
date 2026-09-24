package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.HookGunItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.item.TrackingItemStackRenderState;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/** The attachment needs per-stack bounds and a GUI cache identity, not a static special-model box. */
public final class HookGunItemRenderer implements ItemModel {
    public static final Identifier TYPE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "hook_gun_attachment");
    private static final Identifier BASE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "item/hook_gun");
    private final ModelRenderProperties properties;
    private final Matrix4fc transformation;

    private HookGunItemRenderer(ModelRenderProperties properties, Matrix4fc transformation) {
        this.properties = properties;
        this.transformation = new Matrix4f(transformation);
    }

    @Override
    public void update(ItemStackRenderState output, ItemStack hookGun, ItemModelResolver resolver,
                       ItemDisplayContext displayContext, @Nullable ClientLevel level,
                       @Nullable ItemOwner owner, int seed) {
        ItemStack boundItem = HookGunItem.getBoundItem(hookGun);
        // Use this gun's synchronized state, not a comparison with the local
        // player's hands (which could hide another entity's identical gun).
        if (boundItem.isEmpty() || boundItem.getItem() instanceof HookGunItem
                || HookGunItem.isVisualHookOut(hookGun)) return;

        TrackingItemStackRenderState boundState = new TrackingItemStackRenderState();
        resolver.updateForTopItem(boundState, boundItem,
                HookItemRenderTransforms.getHookGunAttachmentDisplayContext(boundItem, displayContext),
                level, owner, seed);

        PoseStack attachmentPose = new PoseStack();
        attachmentPose.mulPose(this.transformation);
        HookItemRenderTransforms.applyHookGunAttachment(attachmentPose, boundItem, displayContext);

        // Resolve once during extraction. Submission and GUI clipping must use
        // the same bound model, including its own display transform and animation.
        AttachmentRenderer renderer = new AttachmentRenderer(boundState);
        List<Vector3fc> extents = new ArrayList<>();
        renderer.getExtents(extents::add);
        Vector3fc[] bounds = extents.toArray(Vector3fc[]::new);
        ItemStackRenderState.LayerRenderState layer = output.newLayer();
        layer.setExtents(() -> bounds);
        layer.setLocalTransform(attachmentPose.last().pose());
        layer.setupSpecialModel(renderer, null);
        this.properties.applyToLayer(layer, displayContext);

        output.appendModelIdentityElement(this);
        output.appendModelIdentityElement(boundState.getModelIdentity());
        if (boundState.isAnimated()) output.setAnimated();
    }

    private record AttachmentRenderer(ItemStackRenderState state) implements NoDataSpecialModelRenderer {
        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, int overlay,
                           boolean hasFoil, int outlineColor) {
            this.state.submit(poseStack, collector, light, overlay, outlineColor);
        }

        @Override
        public void getExtents(Consumer<Vector3fc> output) {
            // visitExtents reuses its scratch vector, so retain a copy of each point.
            this.state.visitExtents(point -> output.accept(new Vector3f(point)));
        }
    }

    public record Unbaked() implements ItemModel.Unbaked {
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            resolver.markDependency(BASE);
        }

        @Override
        public ItemModel bake(ItemModel.BakingContext context, Matrix4fc transformation) {
            ResolvedModel base = context.blockModelBaker().getModel(BASE);
            return new HookGunItemRenderer(ModelRenderProperties.fromResolvedModel(
                    context.blockModelBaker(), base, base.getTopTextureSlots()), transformation);
        }

        @Override
        public MapCodec<? extends ItemModel.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
