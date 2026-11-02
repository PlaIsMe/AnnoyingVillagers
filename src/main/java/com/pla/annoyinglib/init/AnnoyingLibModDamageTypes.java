package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class AnnoyingLibModDamageTypes {
    public static final ResourceKey<DamageType> IMPACT_EXPLOSION =
            ResourceKey.create(
                    Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "impact_explosion")
            );

    public static class Sources {
        private static Holder.Reference<DamageType> getHolder(RegistryAccess access, ResourceKey<DamageType> key) {
            return access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
        }

        public static DamageSource impactExplosion(RegistryAccess access, Entity directEntity) {
            return new DamageSource(getHolder(access, IMPACT_EXPLOSION), directEntity, null);
        }
    }
}
