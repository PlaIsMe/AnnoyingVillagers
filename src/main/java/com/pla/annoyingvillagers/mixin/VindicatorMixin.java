package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = {Vindicator.class}, remap = true)
public class VindicatorMixin {
    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void monsterJoinHerobrineTeam(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        Vindicator self = (Vindicator) (Object) this;
        if (!self.level().isClientSide() && self.level().getServer() != null) {
            Random random = new Random();
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    self.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.SPEAR_AXE.get()));
                } else {
                    self.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.IRON_AXE));
                }
            }
        }
    }
}
