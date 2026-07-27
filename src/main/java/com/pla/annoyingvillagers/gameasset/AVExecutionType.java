package com.pla.annoyingvillagers.gameasset;

import net.minecraft.world.phys.Vec3;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;

public class AVExecutionType {
    public static final ExecutionTypeManager.Type STRANGLE =
            new ExecutionTypeManager.Type(
                    AnimsPugilistSteve.STRANGLE_EXECUTE, AnimsPugilistSteve.STRANGLE_EXECUTE_HIT,
                    new Vec3(0.8F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type WRESTLING =
            new ExecutionTypeManager.Type(
                    AnimsPugilistSteve.WRESTLING_EXECUTE, AnimsPugilistSteve.WRESTLING_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type WRESTLING_BACK =
            new ExecutionTypeManager.Type(
                    AnimsPugilistSteve.WRESTLING_BACK_EXECUTE, AnimsPugilistSteve.WRESTLING_BACK_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type STAB =
            new ExecutionTypeManager.Type(
                    AnimsPugilistSteve.STAB_EXECUTE, AnimsPugilistSteve.STAB_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type DUAL_STAB =
            new ExecutionTypeManager.Type(
                    AnimsPugilistSteve.DUAL_STAB_EXECUTE, AnimsPugilistSteve.STAB_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);

    public static final ExecutionTypeManager.Type SHIELD =
            new ExecutionTypeManager.Type(
                    AnimsPugilistSteve.SHIELD_EXECUTE, AnimsPugilistSteve.SHIELD_EXECUTE_HIT,
                    new Vec3(1.2F, 0.0F, 0.0F), -10.0F, 100);
}
