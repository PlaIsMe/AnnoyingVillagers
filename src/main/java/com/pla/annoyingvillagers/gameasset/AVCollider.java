package com.pla.annoyingvillagers.gameasset;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class AVCollider {
    public static final Collider SHADOW_OBSIDIAN_PILLAR = new MultiOBBCollider(3, 0.2, 3.0, 0.2, 0.0F, 0.0F, 0.0F);
    public static final Collider SANJI_SPIN = new OBBCollider(2.0D, 1.5D, 2.0D, 0.0D, 0.75D, 0.0D);
}
