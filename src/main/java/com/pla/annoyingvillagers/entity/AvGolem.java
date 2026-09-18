package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.item.DiamondHalberdItem;
import com.pla.annoyingvillagers.item.DiamondSpearItem;
import com.pla.annoyingvillagers.item.NetheriteSpearItem;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationController;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

public abstract class AvGolem extends IronGolem {
    protected AvGolem(EntityType<? extends IronGolem> type, Level level) {
        super(type, level);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal || goal instanceof MoveTowardsTargetGoal);
        this.goalSelector.addGoal(0, new AvGolemCombatGoal(this));
    }

    public AvGolemWeaponStyle getWeaponStyle() {
        ItemStack mainHand = this.getMainHandItem();
        ItemStack offHand = this.getOffhandItem();
        if (mainHand.getItem() instanceof DiamondHalberdItem) {
            if (offHand.getItem() instanceof DiamondHalberdItem) {
                return AvGolemWeaponStyle.DUAL_AXE;
            } else {
                return AvGolemWeaponStyle.AXE;
            }
        }
        if (mainHand.getItem() instanceof SwordItem) {
            if (offHand.getItem() instanceof SwordItem) {
                return AvGolemWeaponStyle.DUAL_SWORD;
            } else {
                return AvGolemWeaponStyle.SWORD;
            }
        }
        if (mainHand.getItem() instanceof DiamondSpearItem || mainHand.getItem() instanceof NetheriteSpearItem) {
            return AvGolemWeaponStyle.SPEAR;
        }
        return AvGolemWeaponStyle.NORMAL;
    }

    public boolean playAvAnimation(SpecialAnimationId animationId) {
        return SpecialAnimationController.play(this, animationId, this.getTarget());
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return IronGolem.createAttributes();
    }
}
