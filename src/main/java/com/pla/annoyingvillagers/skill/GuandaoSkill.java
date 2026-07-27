package com.pla.annoyingvillagers.skill;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightGuandao;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.client.input.InputManager;
import yesman.epicfight.api.client.input.MovementDirection;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.client.CPSkillRequest;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class GuandaoSkill extends WeaponInnateSkill {

    private final List<AnimationAccessor<AttackAnimation>> animations = Lists.newArrayList();
    private static final UUID EVENT_UUID = UUID.fromString("4e85bc19-a63d-4bf1-98b7-d1d08284c314");

    public GuandaoSkill(SkillBuilder<? extends WeaponInnateSkill> skillbuilder) {
        super(skillbuilder);
        this.animations.add(AnimsEpicFightGuandao.FALCHION_FORWARD);
        this.animations.add(AnimsEpicFightGuandao.FALCHION_BACKWARD);
        this.animations.add(AnimsEpicFightGuandao.FALCHION_SIDE);
    }

    public void onInitiate(SkillContainer skillcontainer) {
        super.onInitiate(skillcontainer);
        skillcontainer.getExecutor().getEventListener().addEventListener(EventType.TAKE_DAMAGE_EVENT_HURT, GuandaoSkill.EVENT_UUID, (hurt) -> {
            ServerPlayerPatch serverplayerpatch = hurt.getPlayerPatch();
            AnimationPlayer animationplayer = serverplayerpatch.getAnimator().getPlayerFor(null);
            if (animationplayer != null) {
                DamageSource damagesource = hurt.getDamageSource();
                if (damagesource instanceof EpicFightDamageSource epicfightdamagesource) {
                    if (animationplayer.getAnimation() == AnimsEpicFightGuandao.FALCHION_SIDE || animationplayer.getAnimation() == AnimsEpicFightGuandao.FALCHION_AUTO3) {
                        epicfightdamagesource.setStunType(StunType.NONE);
                    }
                }
            }
        });
    }

    public void onRemoved(SkillContainer skillcontainer) {
        super.onRemoved(skillcontainer);
        skillcontainer.getExecutor().getEventListener().removeListener(EventType.TAKE_DAMAGE_EVENT_HURT, GuandaoSkill.EVENT_UUID);
    }

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public Object getExecutionPacket(SkillContainer skillcontainer, FriendlyByteBuf friendlybytebuf) {
        LocalPlayerPatch localplayerpatch = skillcontainer.getClientExecutor();
        LocalPlayer localplayer = localplayerpatch.getOriginal();
        MovementDirection movementdirection = MovementDirection.fromInputState(InputManager.getInputState(localplayer.input));
        int i = movementdirection.vertical();
        int j = movementdirection.horizontal();
        int k;

        if (i == 0) {
            if (j == 0) {
                k = 1;
            } else {
                k = 2;
            }
        } else {
            k = i >= 0 ? 0 : 1;
        }
        CPSkillRequest cpskillrequest = new CPSkillRequest(skillcontainer.getSlot());
        cpskillrequest.getBuffer().writeInt(k);
        return cpskillrequest;
    }

    public void executeOnServer(SkillContainer skillcontainer, FriendlyByteBuf friendlybytebuf) {
        int i = friendlybytebuf.readInt();
        skillcontainer.getExecutor().playAnimationSynchronized(this.animations.get(i), 0.0F);
        super.executeOnServer(skillcontainer, friendlybytebuf);
    }
}
