package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.EnderAegisProjectile;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
    @Redirect(
            method = "onHitEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSources;arrow(Lnet/minecraft/world/entity/projectile/AbstractArrow;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/damagesource/DamageSource;")
    )
    private DamageSource redirectArrowDamage(DamageSources sources, AbstractArrow arrow, @Nullable Entity shooter) {
        if (arrow instanceof EnderAegisProjectile) {
            ResourceKey<DamageType> key =
                    (shooter instanceof Player) ? DamageTypes.PLAYER_ATTACK :
                            (shooter instanceof LivingEntity) ? DamageTypes.MOB_ATTACK :
                                    DamageTypes.GENERIC;

            Registry<DamageType> reg = arrow.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE);
            Holder<DamageType> holder = reg.getOrThrow(key);
            return new DamageSource(holder, shooter, arrow);
        }
        return sources.arrow(arrow, shooter);
    }
}
