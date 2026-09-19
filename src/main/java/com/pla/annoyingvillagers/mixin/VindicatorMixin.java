package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(value = {Vindicator.class}, remap = true)
public class VindicatorMixin {
    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void monsterJoinHerobrineTeam(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, CallbackInfoReturnable<SpawnGroupData> cir) {
//        Vindicator self = (Vindicator) (Object) this;
//        if (!self.level().isClientSide() && self.getServer() != null) {
//            Random random = new Random();
//            if (random.nextBoolean()) {
//                if (random.nextBoolean()) {
//                    self.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.IRON_POLEAXE.get()));
//                } else {
//                    self.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.IRON_AXE));
//                }
//            }
//        }
    }
}
