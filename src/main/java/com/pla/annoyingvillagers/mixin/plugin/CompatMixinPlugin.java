package com.pla.annoyingvillagers.mixin.plugin;

import java.util.List;
import java.util.Set;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class CompatMixinPlugin implements IMixinConfigPlugin {
    private static final String EFN_COMPAT_PREFIX = "com.pla.annoyingvillagers.mixin.compat.efn.";
    private static final String DUAL_AXES_COMPAT_PREFIX = "com.pla.annoyingvillagers.mixin.compat.dualaxes.";
    private static final String CDMOVESET_COMPAT_PREFIX = "com.pla.annoyingvillagers.mixin.compat.cdmoveset.";
    private static final String REFM_COMPAT_PREFIX = "com.pla.annoyingvillagers.mixin.compat.refm.";
    private static final String DUAL_GREATSWORDS_COMPAT_PREFIX = "com.pla.annoyingvillagers.mixin.compat.dualgreatswords.";
    private static final String SMART_NPC_COMPAT_PREFIX = "com.pla.annoyingvillagers.mixin.compat.smartnpc.";
    private static final String CLASH_BLADE_MIXIN = "com.pla.annoyingvillagers.mixin.ClashBladeMixin";
    private static final String MOB_CLASH_BLADE_MIXIN = "com.pla.annoyingvillagers.mixin.MobClashBladeMixin";

    private static boolean isModLoadedEarly(String modId) {
        LoadingModList list = FMLLoader.getLoadingModList();
        return list != null && list.getModFileById(modId) != null;
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith(EFN_COMPAT_PREFIX)) {
            return isModLoadedEarly("efn");
        }
        if (mixinClassName.startsWith(DUAL_AXES_COMPAT_PREFIX)) {
            return isModLoadedEarly("dualaxes");
        }
        if (mixinClassName.startsWith(DUAL_GREATSWORDS_COMPAT_PREFIX)) {
            return isModLoadedEarly("dualgreatswords");
        }
        if (mixinClassName.startsWith(SMART_NPC_COMPAT_PREFIX)) {
            return isModLoadedEarly("smart_npc");
        }
        if (mixinClassName.equals(CLASH_BLADE_MIXIN) || mixinClassName.equals(MOB_CLASH_BLADE_MIXIN)) {
            return isModLoadedEarly("efclash_blade");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
