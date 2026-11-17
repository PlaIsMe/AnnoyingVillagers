package com.pla.annoyinglib.mixin;

import com.pla.annoyinglib.world.structure.StructureGenerationHooks;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChunkGenerator.class)
public abstract class StructureStartMixin {
    @ModifyVariable(method="tryGenerateStructure",at=@At("STORE"),ordinal=0)
    private StructureStart av$decorateSelectedVillage(StructureStart start,StructureSet.StructureSelectionEntry entry,
            StructureManager manager,RegistryAccess registries,RandomState random,StructureTemplateManager templates,
            long seed,ChunkAccess chunk,ChunkPos pos,SectionPos section){
        return StructureGenerationHooks.decorate(start,registries,seed,pos,chunk);
    }
}
