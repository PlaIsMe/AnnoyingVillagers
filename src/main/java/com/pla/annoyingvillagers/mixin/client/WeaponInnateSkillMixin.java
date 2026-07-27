package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVSkillDataKeys;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.skill.TridentFestivalSkill;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

@Mixin(value = WeaponInnateSkill.class, remap = false)
public class WeaponInnateSkillMixin {
    @Redirect(
            method = "drawOnGui",
            at = @At(
                    value = "INVOKE",
                    target = "Lyesman/epicfight/skill/Skill;getSkillTexture()Lnet/minecraft/resources/ResourceLocation;"
            )
    )
    private ResourceLocation dynamicSkillTextureForTridentFestival(Skill skill, BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y, float partialTick) {
        if (skill instanceof TridentFestivalSkill) {
            boolean ranged = container.getDataManager().getDataValue(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get());
            Player player = container.getExecutor().getOriginal();
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            boolean bothFullyCharged =
                    BlueDemonTridentItem.isBlueDemonTrident(mainHand)
                            && BlueDemonTridentItem.isBlueDemonTrident(offHand)
                            && BlueDemonTridentItem.checkOnlyFullyCharged(mainHand)
                            && BlueDemonTridentItem.checkOnlyFullyCharged(offHand);
            if (ranged && bothFullyCharged) {
                return ResourceLocation.fromNamespaceAndPath(
                        AnnoyingVillagers.MODID,
                        "textures/gui/skills/weapon_innate/trident_festival.png"
                );
            }
            return ranged
                    ? ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/gui/skills/weapon_innate/blue_demon_trident_ranged.png")
                    : ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/gui/skills/weapon_innate/blue_demon_trident_melee.png");
        }

        return skill.getSkillTexture();
    }

    @Inject(method = "drawOnGui", at = @At("TAIL"), remap = false)
    private void drawTridentFestivalStack(
            BattleModeGui gui,
            SkillContainer container,
            GuiGraphics guiGraphics,
            float x,
            float y,
            float partialTick,
            CallbackInfo ci
    ) {
        Skill skill = container.getSkill();
        if (!(skill instanceof TridentFestivalSkill) || skill.getMaxStack() > 1) {
            return;
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, (float) gui.getSlidingProgression(), 0.0F);

        String s = String.valueOf(container.getDataManager().getDataValue(AVSkillDataKeys.TRIDENT_AMOUNT.get()));
        int stringWidth = (gui.getFont().width(s) - 6) / 3;
        guiGraphics.drawString(gui.getFont(), s, x + 25.0F - (float) stringWidth, y + 22.0F, 16777215, true);

        guiGraphics.pose().popPose();
    }
}
