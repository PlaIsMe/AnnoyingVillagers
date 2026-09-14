package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.specialanimation.SpecialAnimationController;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
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
        this.goalSelector.removeAllGoals(goal -> goal instanceof net.minecraft.world.entity.ai.goal.MeleeAttackGoal || goal instanceof net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal);
        this.goalSelector.addGoal(0, new AvGolemCombatGoal(this));
    }

    public AvGolemWeaponStyle getWeaponStyle() {
        ItemStack mainHand = this.getMainHandItem();
        ItemStack offHand = this.getOffhandItem();
        if (canUseDualSword(mainHand, offHand)) return AvGolemWeaponStyle.DUAL_SWORD;
        if (canUseDualAxe(mainHand, offHand)) return AvGolemWeaponStyle.DUAL_AXE;
        if (canUseSpear(mainHand, offHand)) return AvGolemWeaponStyle.SPEAR;
        if (canUseSword(mainHand, offHand)) return AvGolemWeaponStyle.SWORD;
        if (canUseAxe(mainHand, offHand)) return AvGolemWeaponStyle.AXE;
        return AvGolemWeaponStyle.NORMAL;
    }

    public boolean canUseSword(ItemStack mainHand, ItemStack offHand) {
        return mainHand.getItem() instanceof SwordItem && !(offHand.getItem() instanceof SwordItem);
    }

    public boolean canUseDualSword(ItemStack mainHand, ItemStack offHand) {
        return mainHand.getItem() instanceof SwordItem && offHand.getItem() instanceof SwordItem;
    }

    public boolean canUseAxe(ItemStack mainHand, ItemStack offHand) {
        return false;
    }

    public boolean canUseDualAxe(ItemStack mainHand, ItemStack offHand) {
        return false;
    }

    public boolean canUseSpear(ItemStack mainHand, ItemStack offHand) {
        return false;
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
