package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.EnderSlayerScytheSkill;
import com.pla.annoyingvillagers.skill.LegendarySwordSkill;
import com.pla.annoyingvillagers.skill.NullWeaponSkill;
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
    public static final RegistryObject<SkillDataKey<Boolean>> LEGENDARY_SWORD_AWAKENED;
    public static final RegistryObject<SkillDataKey<Boolean>> ENDER_SLAYER_SCYTHE_SUMMON_PENDING;
    public static final RegistryObject<SkillDataKey<Integer>> NULL_WEAPON_RELEASE_STACKS;

    public AVSkillDataKeys() {
    }

    static {
        IS_TRIDENT_RANGED_MODE = DATA_KEYS.register("is_trident_ranged_mode", () -> SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, true, TridentFestivalSkill.class));
        TRIDENT_AMOUNT = DATA_KEYS.register("trident_amount", () -> SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, true, TridentFestivalSkill.class));
        LEGENDARY_SWORD_AWAKENED = DATA_KEYS.register("legendary_sword_awakened", () -> SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, true, LegendarySwordSkill.class));
        ENDER_SLAYER_SCYTHE_SUMMON_PENDING = DATA_KEYS.register("ender_slayer_scythe_summon_pending", () -> SkillDataKey.createSkillDataKey(PacketBufferCodec.BOOLEAN, false, true, EnderSlayerScytheSkill.class));
        NULL_WEAPON_RELEASE_STACKS = DATA_KEYS.register("null_weapon_release_stacks", () -> SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0, true, NullWeaponSkill.class));
    }
}
