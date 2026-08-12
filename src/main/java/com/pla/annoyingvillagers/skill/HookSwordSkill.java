package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AnimsAVSword;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

import java.util.Random;

public class HookSwordSkill extends WeaponInnateSkill {
    public HookSwordSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            if (new Random().nextBoolean()) {
                skillContainer.getExecutor().playAnimationSynchronized(AnimsAVSword.HOOK_SWORD_INNATE1, 0.0F);
            } else {
                skillContainer.getExecutor().playAnimationSynchronized(AnimsAVSword.HOOK_SWORD_INNATE2, 0.0F);
            }
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        skillContainer.deactivate();
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
    }
}
