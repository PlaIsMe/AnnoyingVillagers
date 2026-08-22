package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.rig.pose.RigPoseLibrary;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public record RigCombatProfile(
        List<RigAnimationId> normalAttacks,
        List<RigAnimationId> specialAttacks,
        List<RigAnimationId> rollingAnimations,
        List<RigAnimationId> ultimateAttacks,
        double specialAttackChance,
        double rollingChance,
        double ultimateAttackChance,
        RigLocomotionStyle locomotionStyle
) {
    public RigCombatProfile {
        normalAttacks = List.copyOf(normalAttacks);
        specialAttacks = List.copyOf(specialAttacks);
        rollingAnimations = List.copyOf(rollingAnimations);
        ultimateAttacks = List.copyOf(ultimateAttacks);
        if (normalAttacks.isEmpty()) {
            throw new IllegalArgumentException("normalAttacks cannot be empty");
        }
    }

    public boolean containsAttack(RigAnimationId animationId) {
        return this.normalAttacks.contains(animationId) || this.specialAttacks.contains(animationId) || this.ultimateAttacks.contains(animationId);
    }

    public RigAnimationId normalAt(int comboIndex) {
        return this.normalAttacks.get(Math.floorMod(comboIndex, this.normalAttacks.size()));
    }

    public RigAnimationId pickInterrupt(RandomSource random, RigAnimationId previousAnimation) {
        if (!this.ultimateAttacks.isEmpty() && random.nextDouble() < this.ultimateAttackChance) {
            return pickDifferent(this.ultimateAttacks, random, previousAnimation);
        }

        if (!this.rollingAnimations.isEmpty() && random.nextDouble() < this.rollingChance) {
            return pickDifferent(this.rollingAnimations, random, previousAnimation);
        }

        if (!this.specialAttacks.isEmpty() && random.nextDouble() < this.specialAttackChance) {
            return pickDifferent(this.specialAttacks, random, previousAnimation);
        }

        return null;
    }

    public RigAnimationId pickClosingAttack(RandomSource random, RigAnimationId previousAnimation) {
        if (this.specialAttacks.isEmpty()) {
            return null;
        }

        List<RigAnimationId> movingAttacks = new ArrayList<>();
        for (RigAnimationId animationId : this.specialAttacks) {
            if (RigPoseLibrary.maxHorizontalMotionBlocks(animationId) > 0.0D) {
                movingAttacks.add(animationId);
            }
        }

        return pickDifferentOrNull(movingAttacks.isEmpty() ? this.specialAttacks : movingAttacks, random, previousAnimation);
    }

    public boolean hasClosingAttack() {
        return !this.specialAttacks.isEmpty();
    }

    private static RigAnimationId pickDifferent(List<RigAnimationId> choices, RandomSource random, RigAnimationId previousAnimation) {
        if (choices.size() == 1) {
            return choices.get(0);
        }

        RigAnimationId chosen = choices.get(random.nextInt(choices.size()));
        if (chosen != previousAnimation) {
            return chosen;
        }

        return choices.get((choices.indexOf(chosen) + 1) % choices.size());
    }

    private static RigAnimationId pickDifferentOrNull(List<RigAnimationId> choices, RandomSource random, RigAnimationId previousAnimation) {
        if (choices.isEmpty()) {
            return null;
        }
        if (choices.size() == 1) {
            RigAnimationId onlyChoice = choices.get(0);
            return onlyChoice == previousAnimation ? null : onlyChoice;
        }

        return pickDifferent(choices, random, previousAnimation);
    }
}
