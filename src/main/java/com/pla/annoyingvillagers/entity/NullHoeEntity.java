package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.level.Level;

public class NullHoeEntity extends NullWeapon {
        public NullHoeEntity(EntityType<NullHoeEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setWeapon("hoe");
    }

    public static Builder createAttributes() {
        return NullWeapon.createAttributes();
    }
}
