package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AnimsEnderGlaive;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

public class EnderGlaiveSkill extends WeaponInnateSkill {
    public EnderGlaiveSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            skillContainer.getExecutor().playAnimationSynchronized(AnimsEnderGlaive.ENDER_GLAIVE_INNATE, 0.0F);
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
        Player player = container.getExecutor().getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (container.getStack() == 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof EnderGlaiveItem && !itemStack.getTag().getBoolean("PlaySound")) {
            container.getExecutor().getOriginal().playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(), 0.5F, 1.0F);
            itemStack.getTag().putBoolean("PlaySound", true);
        } else if (container.getStack() < 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof EnderGlaiveItem && itemStack.getTag().getBoolean("PlaySound")) {
            itemStack.getTag().remove("PlaySound");
        }
    }
}
