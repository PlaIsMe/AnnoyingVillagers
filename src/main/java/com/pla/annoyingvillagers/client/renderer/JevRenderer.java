package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigVillagerRenderer;
import com.pla.annoyingvillagers.entity.JevEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class JevRenderer extends RigVillagerRenderer<JevEntity> {

    public JevRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull JevEntity jevEntity) {
        if (jevEntity.isDeadOrDying()) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/jev_dead.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/jev.png");
        }
    }
}
