package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class AnnoyingVillagersModDamageTypes {
    public static final ResourceKey<DamageType> IMPACT_EXPLOSION =
            ResourceKey.create(
                    Registries.DAMAGE_TYPE,
                    Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "impact_explosion")
            );

    public static class Sources {
        private static Holder.Reference<DamageType> getHolder(RegistryAccess access, ResourceKey<DamageType> key) {
            return access.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key);
        }

        public static DamageSource impactExplosion(RegistryAccess access, Entity directEntity) {
            return new DamageSource(getHolder(access, IMPACT_EXPLOSION), directEntity, null);
        }
    }
}
