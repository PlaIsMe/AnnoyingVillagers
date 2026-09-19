package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.EnchantedArrowEntity;
import com.pla.annoyingvillagers.util.GlintColorHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EnchantedArrowItem extends ArrowItem {
    public EnchantedArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level level, @NotNull ItemStack ammoStack, @NotNull LivingEntity shooter, @NotNull ItemStack weapon) {
        EnchantedArrowEntity arrow = new EnchantedArrowEntity(level, shooter);
        arrow.setColorGlint(GlintColorHelper.getRandomColor());
        return arrow;
    }
}
