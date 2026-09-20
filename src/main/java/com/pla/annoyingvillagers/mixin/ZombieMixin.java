package com.pla.annoyingvillagers.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.EndFireUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(value = {Zombie.class}, remap = true)
public class ZombieMixin {
    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void monsterTargetNpc(CallbackInfo ci) {
        Zombie self = (Zombie) (Object) this;
        if (!(self instanceof Drowned) && !(self instanceof ZombifiedPiglin)) {
            CommonGoals.registerGoalForHostileNpc(self);
        }
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void monsterJoinHerobrineTeam(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData, CallbackInfoReturnable<SpawnGroupData> cir) {
        Zombie self = (Zombie) (Object) this;
        if (self.level() instanceof net.minecraft.server.level.ServerLevel) {
            TeamUtil.addOrJoinTeam(self, "herobrine");

            self.setCanPickUpLoot(true);
            Random random = new Random();

            if (random.nextFloat() < 0.2f) {
                self.setItemSlot(EquipmentSlot.HEAD, createDyedArmor(Items.LEATHER_HELMET, random));
            }
            if (random.nextFloat() < 0.2f) {
                self.setItemSlot(EquipmentSlot.CHEST, createDyedArmor(Items.LEATHER_CHESTPLATE, random));
            }
            if (random.nextFloat() < 0.2f) {
                self.setItemSlot(EquipmentSlot.LEGS, createDyedArmor(Items.LEATHER_LEGGINGS, random));
            }
            if (random.nextFloat() < 0.2f) {
                self.setItemSlot(EquipmentSlot.FEET, createDyedArmor(Items.LEATHER_BOOTS, random));
            }
        }
    }

    private static ItemStack createDyedArmor(Item item, Random random) {
        ItemStack stack = new ItemStack(item);
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        int color = (red << 16) | (green << 8) | blue;
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color));
        return stack;
    }
}
