package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.specialanimation.SpecialAnimationController;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * Epic-Fight-free port of the Super Golem / Modular Golem behavior-series AI.
 *
 * Each series keeps its original weight, cooldown and target-grid gate. Follow-up
 * attacks use the same distance gates as the reference mods, while the actual hit
 * detection is handled by the server-side special pose collider system.
 */
public class AvGolemCombatGoal extends Goal {
    private final AvGolem golem;
    private final Map<SpecialAnimationId, Integer> seriesCooldowns = new EnumMap<>(SpecialAnimationId.class);
    private List<SpecialAnimationId> combo = List.of();
    private int comboIndex;

    public AvGolemCombatGoal(AvGolem golem) {
        this.golem = golem;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.golem.getTarget();
        return target != null && target.isAlive() && this.golem.canAttack(target);
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void stop() {
        this.golem.setSprinting(false);
        this.combo = List.of();
        this.comboIndex = 0;
        this.golem.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = this.golem.getTarget();
        if (target == null) return;
        tickSeriesCooldowns();
        this.golem.getLookControl().setLookAt(target, 45.0F, 45.0F);

        if (SpecialAnimationController.hasActiveAnimation(this.golem)) {
            this.golem.setSprinting(false);
            this.golem.getNavigation().stop();
            return;
        }

        if (this.comboIndex < this.combo.size()) {
            this.golem.setSprinting(false);
            if (this.golem.distanceTo(target) <= 10.0F) playNextComboStep(target);
            else clearCombo();
            return;
        }

        AvGolemWeaponStyle style = this.golem.getWeaponStyle();
        List<BehaviorChoice> choices = buildChoices(style, target);
        BehaviorChoice selected = chooseWeighted(choices);
        if (selected == null) {
            boolean chasing = this.golem.distanceTo(target) > 1.75F;
            this.golem.setSprinting(chasing);
            if (chasing) this.golem.getNavigation().moveTo(target, 1.0D);
            else this.golem.getNavigation().stop();
            return;
        }

        this.golem.setSprinting(false);
        this.golem.getNavigation().stop();
        this.seriesCooldowns.put(selected.cooldownKey(), selected.cooldownTicks());
        this.combo = selected.combo();
        this.comboIndex = 0;
        playNextComboStep(target);
    }

    private void tickSeriesCooldowns() {
        this.seriesCooldowns.replaceAll((id, ticks) -> Math.max(0, ticks - 1));
    }

    private void playNextComboStep(LivingEntity target) {
        if (this.comboIndex >= this.combo.size()) return;
        SpecialAnimationId next = this.combo.get(this.comboIndex);
        if (SpecialAnimationController.play(this.golem, next, target)) this.comboIndex++;
    }

    private void clearCombo() {
        this.combo = List.of();
        this.comboIndex = 0;
    }

    private List<BehaviorChoice> buildChoices(AvGolemWeaponStyle style, LivingEntity target) {
        List<BehaviorChoice> choices = new ArrayList<>();
        switch (style) {
            case NORMAL -> addNormalChoices(choices, target);
            case SWORD -> {
                addChoice(choices, SpecialAnimationId.GOLEM_SWORD_ATK_1_1, 20, 0, targetInGrid(target, -1, 0, 1, 2), SpecialAnimationId.GOLEM_SWORD_ATK_1_1, SpecialAnimationId.GOLEM_SWORD_ATK_1_2);
                addChoice(choices, SpecialAnimationId.GOLEM_SWORD_SKILL_1, 20, 40, targetInGrid(target, -1, 3, 1, 5), SpecialAnimationId.GOLEM_SWORD_SKILL_1);
            }
            case DUAL_SWORD -> {
                addChoice(choices, SpecialAnimationId.GOLEM_DUAL_SWORD_ATK_1_1, 20, 0, targetInGrid(target, -1, 0, 1, 2), SpecialAnimationId.GOLEM_DUAL_SWORD_ATK_1_1, SpecialAnimationId.GOLEM_DUAL_SWORD_ATK_1_2);
                addChoice(choices, SpecialAnimationId.GOLEM_DUAL_SWORD_SKILL_1, 20, 40, targetInGrid(target, 3, -3, -3, 3), SpecialAnimationId.GOLEM_DUAL_SWORD_SKILL_1);
            }
            case AXE -> {
                addChoice(choices, SpecialAnimationId.GOLEM_AXE_ATK_1_1, 20, 0, targetInGrid(target, -1, 0, 1, 2), SpecialAnimationId.GOLEM_AXE_ATK_1_1, SpecialAnimationId.GOLEM_AXE_ATK_1_2);
                addChoice(choices, SpecialAnimationId.GOLEM_AXE_SKILL_1, 20, 20, targetInGrid(target, -1, 2, 1, 3), SpecialAnimationId.GOLEM_AXE_SKILL_1);
            }
            case DUAL_AXE -> {
                addChoice(choices, SpecialAnimationId.GOLEM_DUAL_AXE_ATK_1_1, 20, 0, targetInGrid(target, -1, 0, 1, 2), SpecialAnimationId.GOLEM_DUAL_AXE_ATK_1_1, SpecialAnimationId.GOLEM_DUAL_AXE_ATK_1_2, SpecialAnimationId.GOLEM_DUAL_AXE_ATK_1_3);
                float distance = this.golem.distanceTo(target);
                addChoice(choices, SpecialAnimationId.GOLEM_DUAL_AXE_SKILL_1, 20, 30, distance >= 4.0F && distance <= 13.0F, SpecialAnimationId.GOLEM_DUAL_AXE_SKILL_1);
            }
            case SPEAR -> {
                addChoice(choices, SpecialAnimationId.GOLEM_SPEAR_ATK_1_1, 20, 0, targetInGrid(target, -1, 0, 1, 2), SpecialAnimationId.GOLEM_SPEAR_ATK_1_1, SpecialAnimationId.GOLEM_SPEAR_ATK_1_2);
                addChoice(choices, SpecialAnimationId.GOLEM_SPEAR_SKILL_1, 20, 20, targetInGrid(target, -1, 2, 1, 3), SpecialAnimationId.GOLEM_SPEAR_SKILL_1);
            }
        }
        return choices;
    }

    private void addNormalChoices(List<BehaviorChoice> choices, LivingEntity target) {
        addChoice(choices, SpecialAnimationId.GOLEM_ATK_1_1, 10, 10, targetInGrid(target, 0, 1, 0, 2), SpecialAnimationId.GOLEM_ATK_1_1, SpecialAnimationId.GOLEM_ATK_1_2);
        addChoice(choices, SpecialAnimationId.GOLEM_ATK_2_1, 20, 10, targetInGrid(target, -1, 1, 1, 2), SpecialAnimationId.GOLEM_ATK_2_1, SpecialAnimationId.GOLEM_ATK_2_2, SpecialAnimationId.GOLEM_ATK_2_3);
        addChoice(choices, SpecialAnimationId.GOLEM_ATK_3_1, 20, 10, targetInGrid(target, 1, 1, -1, 2), SpecialAnimationId.GOLEM_ATK_3_1, SpecialAnimationId.GOLEM_ATK_3_2, SpecialAnimationId.GOLEM_ATK_3_3);
        addChoice(choices, SpecialAnimationId.GOLEM_SKILL_1, 50, 250, targetInGrid(target, -2, 2, -2, -2), SpecialAnimationId.GOLEM_SKILL_1);
        addChoice(choices, SpecialAnimationId.GOLEM_SKILL_2, 1, 200, targetInGrid(target, -1, 2, 1, 13), SpecialAnimationId.GOLEM_SKILL_2);
        addChoice(choices, SpecialAnimationId.GOLEM_SKILL_3, 100, 250, targetInGrid(target, -1, 4, 1, 7), SpecialAnimationId.GOLEM_SKILL_3);
    }

    private void addChoice(List<BehaviorChoice> choices, SpecialAnimationId cooldownKey, int weight, int cooldownTicks, boolean condition, SpecialAnimationId... animations) {
        if (!condition || this.seriesCooldowns.getOrDefault(cooldownKey, 0) > 0) return;
        choices.add(new BehaviorChoice(cooldownKey, weight, cooldownTicks, List.of(animations)));
    }

    private BehaviorChoice chooseWeighted(List<BehaviorChoice> choices) {
        int total = 0;
        for (BehaviorChoice choice : choices) total += choice.weight();
        if (total <= 0) return null;
        int roll = this.golem.getRandom().nextInt(total);
        for (BehaviorChoice choice : choices) {
            roll -= choice.weight();
            if (roll < 0) return choice;
        }
        return choices.get(choices.size() - 1);
    }

    private boolean targetInGrid(LivingEntity target, int x1, int z1, int x2, int z2) {
        double dx = target.getX() - this.golem.getX();
        double dz = target.getZ() - this.golem.getZ();
        double yaw = Math.toRadians(this.golem.getYRot());
        double cos = Math.cos(yaw);
        double sin = Math.sin(yaw);
        int gridX = (int)Math.floor(dx * cos + dz * sin + 0.5D);
        int gridZ = (int)Math.floor(-dx * sin + dz * cos + 0.5D);
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minZ = Math.min(z1, z2);
        int maxZ = Math.max(z1, z2);
        return Math.abs(gridX) < 24 && Math.abs(gridZ) < 24 && gridX >= minX && gridX <= maxX && gridZ >= minZ && gridZ <= maxZ;
    }

    private record BehaviorChoice(SpecialAnimationId cooldownKey, int weight, int cooldownTicks, List<SpecialAnimationId> combo) {
    }
}
