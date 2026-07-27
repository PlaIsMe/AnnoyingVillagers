package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.TridentFestivalSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.utils.PacketBufferCodec;
import yesman.epicfight.skill.SkillDataKey;

public class AVSkillDataKeys {
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath("epicfight", "skill_data_keys"), AnnoyingVillagers.MODID);
    public static final RegistryObject<SkillDataKey<Boolean>> IS_TRIDENT_RANGED_MODE;
    public static final RegistryObject<SkillDataKey<Integer>> TRIDENT_AMOUNT;

    public AVSkillDataKeys() {
    }

    static {
        IS_TRIDENT_RANGED_MODE = DATA_KEYS.register("is_trident_ranged_mode", () -> SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, true, TridentFestivalSkill.class));
        TRIDENT_AMOUNT = DATA_KEYS.register("trident_amount", () -> SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, true, TridentFestivalSkill.class));
    }
}
