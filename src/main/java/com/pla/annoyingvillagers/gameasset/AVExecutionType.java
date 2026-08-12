package com.pla.annoyingvillagers.gameasset;

import net.minecraft.world.phys.Vec3;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;

public class AVExecutionType {
    public static final ExecutionTypeManager.Type STRANGLE =
            new ExecutionTypeManager.Type(
                    AnimsAVExecute.STRANGLE_EXECUTE, AnimsAVExecute.STRANGLE_EXECUTE_HIT,
                    new Vec3(0.8F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type WRESTLING =
            new ExecutionTypeManager.Type(
                    AnimsAVExecute.WRESTLING_EXECUTE, AnimsAVExecute.WRESTLING_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type WRESTLING_BACK =
            new ExecutionTypeManager.Type(
                    AnimsAVExecute.WRESTLING_BACK_EXECUTE, AnimsAVExecute.WRESTLING_BACK_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type STAB =
            new ExecutionTypeManager.Type(
                    AnimsAVExecute.STAB_EXECUTE, AnimsAVExecute.STAB_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type DUAL_STAB =
            new ExecutionTypeManager.Type(
                    AnimsAVExecute.DUAL_STAB_EXECUTE, AnimsAVExecute.STAB_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type SHIELD =
            new ExecutionTypeManager.Type(
                    AnimsAVExecute.SHIELD_EXECUTE, AnimsAVExecute.SHIELD_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);
}
