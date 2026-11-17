package com.pla.annoyinglib.mixin;

import com.pla.annoyinglib.world.structure.StructureGenerationHooks;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChunkGeneratorStructureState.class)
public abstract class StructureStateMixin {
    @ModifyVariable(method="createForFlat",at=@At("HEAD"),argsOnly=true)
    private static Stream<Holder<StructureSet>> av$flatContentStructureSets(Stream<Holder<StructureSet>> sets){return StructureGenerationHooks.flatSets(sets.toList()).stream();}
    @ModifyVariable(method="<init>",at=@At("HEAD"),argsOnly=true)
    private static List<Holder<StructureSet>> av$mergeContentStructureSets(List<Holder<StructureSet>> sets){return StructureGenerationHooks.sets(sets);}
}
