package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.ShockWaveBlockEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.ArmorUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class LegendarySwordItem extends SwordItem {

    public LegendarySwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 6.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
            }
        }, 3, -2.32F, (new Properties()).fireResistant());
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!pAttacker.level().isClientSide()) {
            ArmorUtil.damageArmor(pTarget, new Random().nextInt(1, 5));
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void appendHoverText(@NotNull ItemStack itemStack, Level level, @NotNull List<Component> componentList, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        componentList.add(Component.translatable("tooltip.annoyingvillagers.legendary_sword"));
    }

    public static void spawnCircleRing(ServerLevel level, BlockPos centerPos, int radius, LivingEntity owner) {
        double inner = (radius - 0.5D) * (radius - 0.5D);
        double outer = (radius + 0.5D) * (radius + 0.5D);

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double dist2 = (double) dx * dx + (double) dz * dz;
                if (dist2 >= inner && dist2 <= outer) {
                    spawnShockWaveBlock(level, centerPos.offset(dx, 0, dz), owner);
                }
            }
        }
    }

    private static void spawnShockWaveBlock(ServerLevel level, BlockPos startPos, LivingEntity owner) {
        final int BLOCK_SEARCH_DEPTH = 256;
        final int ENTITY_GROUND_LIFETIME = 10;

        BlockPos pos = startPos;
        BlockState state = level.getBlockState(pos);

        int minY = level.getMinBuildHeight();

        for (int i = 0; i < BLOCK_SEARCH_DEPTH && pos.getY() > minY && state.getRenderShape() != RenderShape.MODEL; i++) {
            pos = pos.below();
            state = level.getBlockState(pos);
        }

        if (state.getRenderShape() != RenderShape.MODEL) return;

        ShockWaveBlockEntity blockEntity = new ShockWaveBlockEntity(
                level,
                pos.getX() + 0.5D,
                pos.getY() + 1.0D,
                pos.getZ() + 0.5D,
                state,
                ENTITY_GROUND_LIFETIME
        );
        blockEntity.setOwnerUuid(owner.getUUID());
        level.addFreshEntity(blockEntity);
    }
}
