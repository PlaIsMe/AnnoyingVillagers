package com.pla.annoyingvillagers.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.EndFireUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.item.DyeableLeatherItem;
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
    private void monsterJoinHerobrineTeam(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        Zombie self = (Zombie) (Object) this;
        if (!self.level().isClientSide() && self.getServer() != null) {
            TeamUtil.addOrJoinTeam(self, "herobrine");

            try {
                self.getServer().getCommands().getDispatcher().execute(
                        "data merge entity @s {CanPickUpLoot: 1b}",
                        self.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException ignored) {

            }
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
        if (stack.getItem() instanceof DyeableLeatherItem dyeable) {
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            int color = (red << 16) | (green << 8) | blue;
            dyeable.setColor(stack, color);
        }
        return stack;
    }
}
