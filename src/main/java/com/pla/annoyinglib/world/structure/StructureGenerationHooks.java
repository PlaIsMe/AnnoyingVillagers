package com.pla.annoyinglib.world.structure;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.UnaryOperator;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;

/** Content modules register optional policy; the library never imports a sibling to generate structures. */
public final class StructureGenerationHooks {
    public interface StartDecorator {
        StructureStart apply(StructureStart start,RegistryAccess registries,long seed,ChunkPos chunk,ChunkAccess access);
    }
    private static final List<UnaryOperator<List<Holder<StructureSet>>>> SETS=new CopyOnWriteArrayList<>();
    private static final List<StartDecorator> DECORATORS=new CopyOnWriteArrayList<>();
    private static final List<UnaryOperator<List<Holder<StructureSet>>>> FLAT_SETS=new CopyOnWriteArrayList<>();
    private StructureGenerationHooks(){}
    public static void register(UnaryOperator<List<Holder<StructureSet>>> sets,StartDecorator decorator){SETS.add(sets);DECORATORS.add(decorator);}
    public static void registerFlatSets(UnaryOperator<List<Holder<StructureSet>>> sets){FLAT_SETS.add(sets);}
    public static List<Holder<StructureSet>> flatSets(List<Holder<StructureSet>> original){var result=original;for(var transform:FLAT_SETS)result=transform.apply(result);return result;}
    public static List<Holder<StructureSet>> sets(List<Holder<StructureSet>> original){var result=original;for(var transform:SETS)result=transform.apply(result);return result;}
    public static StructureStart decorate(StructureStart start,RegistryAccess registries,long seed,ChunkPos chunk,ChunkAccess access){
        if(!start.isValid())return start;
        for(var decorator:DECORATORS)start=decorator.apply(start,registries,seed,chunk,access);
        return start;
    }
}
