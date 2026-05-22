/*
 * AnnoyingVillagers - Unified Third-Party Attribution & License Notice (6-in-1)
 *
 * SPDX-License-Identifier: GPL-3.0-only AND LGPL-2.1-only AND MIT AND AFL-3.0 AND LicenseRef-UNKNOWN
 *
 * This file may include code and/or assets adapted from one or more third-party projects listed below.
 * Keep this notice and all required upstream notices.
 *
 * Upstream projects:
 *
 *   [1] EpicACG - dfdyz
 *       License: GPL-3.0-only
 *       Source: https://github.com/dfdyz/epicacg-1.20
 *       Local license text: third_party/licenses/GPL-3.0.md
 *       Scope: assets (possible adapted portions)
 *
 *   [2] Epic Fight - Valour Guard - namelesslk
 *       License: LGPL-2.1-only
 *       Source: https://github.com/Cyber2049/valour-guard
 *       Local license text: third_party/licenses/LGPL-2.1.md
 *       Scope: assets (possible derived portions)
 *
 *   [3] Epic Fight x Iron's Spells: Enhanced Animations - YukamiNeeSan
 *       License: MIT
 *       Source: https://github.com/domanhthang2110/efiscompat
 *       Local license text: third_party/licenses/MIT.md
 *       Scope: assets (possible derived portions)
 *
 *   [4] Epic Fight - Infernal Gainer - reascer
 *       License: GPL-3.0-only
 *       Source: https://www.curseforge.com/minecraft/mc-mods/epic-fight-infernal-gainer
 *       Local license text: third_party/licenses/GPL-3.0.md
 *       Scope: assets (possible adapted portions)
 *
 *   [5] Epic Fight - Dual GreatSword - reascer
 *       License: MIT
 *       Source: https://www.curseforge.com/minecraft/mc-mods/epicfight-dual-greatsword
 *       Local license text: third_party/licenses/MIT.md
 *       Scope: assets (possible adapted portions)
 *
 *   [6] Annoying Villagers - Pugilist Steve
 *       License: AFL-3.0
 *       Source: https://space.bilibili.com/1337039598/dynamic
 *       Local license text: third_party/licenses/AFL-3.0.md
 *       Scope: assets (possible adapted portions)
 *
 *   [7] Epic Fight - Battle Arts - Forixaim
 *       License: CC-BY-SA
 *       Source: https://github.com/Forixaim/Epic-Fight-Battle-Arts
 *       Local license text: third_party/licenses/CC-BY-SA-4.0.md
 *       Scope: animations / assets (adapted or reused portions)
 *
 *   [8] Visitors from Omneria - Forixaim
 *       License: CC-BY-NC-SA
 *       Source: https://github.com/Forixaim/Visitors-from-Omneria
 *       Local license text: third_party/licenses/CC-BY-NC-SA-4.0.md
 *       Scope: animations / assets (adapted or reused portions)
 *
 *   [9] EpicFight - Awaken - ShelMarow
 *       License: GPL-3.0
 *       Source: https://www.curseforge.com/minecraft/mc-mods/epicfight-awaken
 *       Local license text: third_party/licenses/GPL-3.0.md
 *       Scope: animations / assets (adapted or reused portions)
 *
 *   [10] Tactical Imbuements - M3tte
 *       License: GPL-3.0
 *       Source: https://www.curseforge.com/minecraft/mc-mods/tactical-imbuements
 *       Local license text: third_party/licenses/MIT.md
 *       Scope: animations / assets (adapted or reused portions)
 *
 *   [11] Weapons of Miracles (dependency mod) - reacer
 *       License: Proprietary / All Rights Reserved
 *       Source: https://www.curseforge.com/minecraft/mc-mods/weapons-of-miracles-epicfight
 *       Local license text: third_party/licenses/LicenseRef-WOM-Proprietary.md
 *       Scope: assets (cloned/copied animations due to limitation in reusing animations from EpicFight)
 *
 * Notes:
 * - This notice does NOT replace any upstream license terms.
 * - If a portion of this file is under GPL/LGPL/MIT/AFL, those terms apply to that portion.
 * - MIT portions require preserving the MIT copyright + permission notice.
 * - GPL/LGPL portions may require providing corresponding source when distributing binaries/modifications.
 * - WOM portions are proprietary; redistribution may require permission from the WOM owner.
 *
 * Modifications and integration:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.annoyingvillagers.animations.HeavyAttackAnimation;
import com.pla.annoyingvillagers.animations.RushSwordAnimation;
import com.pla.annoyingvillagers.block.ObsidianBlock;
import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.TridentMode;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.network.*;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import reascer.wom.animation.WomAnimationProperty;
import reascer.wom.animation.attacks.AntitheusShootAttackAnimation;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.gameasset.ReuseableEvents;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSounds;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.damagesources.WOMDamageType;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.HitEntityList.Priority;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AVAnimations {
    // Animation clone and re-registered from EpicFight
    public static AnimationManager.AnimationAccessor<StaticAnimation> EAT_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DRINK_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHIELD_MAINHAND;
    public static AnimationManager.AnimationAccessor<ActionAnimation> AEGIS_SHIELD_SHOOT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHIELD_OFFHAND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO3;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> OBSIDIAN_FIST_AIR_SLASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SHADOW_OBSIDIAN_FIST_AIR_SLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_BIPED_LANDING;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_ZOMBIE_ATTACK3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_FIST_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_FIST_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SHADOW_HEROBRINE_BIPED_LANDING;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NERF_TSUNAMI_REINFORCED;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE_LEGENDARY_SWORD_START;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE_LEGENDARY_SWORD_TICK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> HOOK_AXE_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> HOOK_AXE_AUTO2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HOOK_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DNAX_HOOK_SWEEPING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DNAX_HOOK_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THUNDER_SWEEPING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THUNDER_DANCING_EDGE;

    // Animation from EpicFight Infernal Gainer
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> INFERNAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> INFERNAL_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> INFERNAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_INFERNAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_INFERNAL_AUTO_2;

    // Animation from EpicFight ACG
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_1;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_2;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_3;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_4;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_5;

    // Animation from Epicfight Valour Guard
    public static AnimationManager.AnimationAccessor<StaticAnimation> VALOUR_HOLD_GREATSWORD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> VALOUR_WALK_GREATSWORD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> VALOUR_RUN_GREATSWORD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VALOUR_FIST_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> VALOUR_FIST_GUARD_HIT;

    // Animation from EpicFight x Iron's Spells
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_TOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_INWARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_BUFF;

    // Animation from EpicFight Dual GreatSword
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_TWOHAND_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_TWOHAND_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AIRSLASH;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> GREATSWORD_DUAL_EARTHQUAKE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AIRSLASH;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE_PILLAR;

    // Animation from EpicFight Sanji
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SANJI_DIABLE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SANJI_CONCASSER;

    // Animation from Pugilist Steve Annoying Villagers 1.18.2
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_STATE_TRANSFORM;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_STATE_TRANSFORM_END;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE;
    public static AnimationManager.AnimationAccessor<ActionAnimation> TRIDENT_FESTIVAL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> COUNTER;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FIST_GUARD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WHIRLWIND_KICK;
    public static AnimationManager.AnimationAccessor<HeavyAttackAnimation> LEGENDARY_SWORD_HEAVY_ATTACK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HACKER_SWORD_SKILL_TWOHAND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> TRIDENT_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_UP;
    public static AnimationManager.AnimationAccessor<RushSwordAnimation> RUSH_SWORD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DUAL_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SWEEPING_EDGE;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_BACKWARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> SPEAR_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LEGENDARY_SWORD_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> LEGENDARY_SWORD_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<GuardAnimation> LEGENDARY_SWORD_GUARD_PARRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> POSE_UP;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CHECK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> BIPED_RUN_ESWORD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNIFE_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> KNIFE_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> KNIFE_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNIFE_CHECK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CARRY;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_LEFT;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_FORWARD;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_RIGHT;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_LEFT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AXE_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AXE_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HARD_KICK;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HARD_KICK_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> RUN_START;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LONGSWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<MovementAnimation> RUN_DUAL_BIG;
    public static AnimationManager.AnimationAccessor<MovementAnimation> RUN_HOLD;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> LONGEST_HIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HARD_GREATSWORD_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> HARD_GREATSWORD_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> HARD_GREATSWORD_GUARD_SKILL;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_LEFT;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_RIGHT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> SHAKE_HAND_TRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> SHAKE_HAND;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FIST_TRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FISTING;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GIANT_WHIRLWIND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SPEAR_THRUST;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DUAL_TACHI_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> DUAL_TACHI_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WHIRLWIND_KICK_LEFT;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SUPER_PUNCH;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> GUARD_BREAK_ATTACK;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SWORD_DASH;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> TACHI_DASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DUAL_END;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> TRIED;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATSWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LEGENDARY_SWORD_WAKE_UP_ATTACK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DUAL_E_END;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_FUN_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LEGENDARY_SWORD_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_FIST_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> TRIDENT_THROW_2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> TRIDENT_THROW_LEGENDARY;


    // Animation from Community EpicFight Battle Arts
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> ADVANCED_LANCER_AUTO1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ADVANCED_LANCER_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ADVANCED_DUELIST_WHIRLEDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ADVANCED_DUELIST_SHOOTING_STAR;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> TRIDENT_THROW_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TRIDENT_THROW_5;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SQUIRE_SWORD_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SQUIRE_SWORD_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SQUIRE_SWORD_RUN;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SQUIRE_SWORD_HOP_ATTACK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_HEAVY_BLOW;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SABRE_AUTO3;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SABRE_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SABRE_AIR_ATTACK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SABRE_QUAD_STING;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TACHI_BLOSSOM_SLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_AUTO3;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> AXE_DASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> AXE_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> AXE_INNATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BAXE_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> BAXE_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> BAXE_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BAXE_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BAXE_AUTO_2;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> BAXE_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> BAXE_AIR_ATTACK;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> BAXE_SEISMIC_IMPACT;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> GREATSWORD_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATSWORD_AIRSLAM;
    public static AnimationManager.AnimationAccessor<AttackAnimation> GREATSWORD_POWER_GEYSER;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> THIEF_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> THIEF_AUTO3;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> THIEF_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> THIEF_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THIEF_STEAL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_BLADES_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_BLADES_AIRSLAM;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DUAL_BLADES_WHIRLEDGE;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SWORD_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> IRON_LOTUS_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> IRON_LOTUS_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> IRON_LOTUS_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> IRON_LOTUS_DASH_ATTACK;

    // Animation from Visitors from Omneria
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> TRIDENT_THROW_3;

    // Animation from EpicFight-Awaken
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_DP_AIR_ATTACK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_HOOK_SPIN_SLASH_AIR;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_THROW_BLADE_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_THROW_BLADE_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THROW_HOOK_SLASH_AIR;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_LEFT_DP_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_AUTO_4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_DASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_LEFT_DP_DASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HOOK_SLASH_GROUND;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HOOK_SLASH_AIR;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_NIGHT_FALL;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_LEFT_DP_DUSK_REAVER_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_4_SPC;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_SHADOW_LUNGE_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_SHADOW_LUNGE_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_SHADOW_LUNGE_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_LEFT_DP_SHADOW_LUNGE_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_LEFT_DP_SHADOW_LUNGE_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_LEFT_DP_SHADOW_LUNGE_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CUT_LEFT_DP_PHANTOM_DANCE_END_1_ENHANCED;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_PHANTOM_DANCE_END_2_ENHANCED;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_FALLING_SHADOW;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> HOOK_AIR;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> HOOK_GROUND;

    // Animation from Tactical Imbuements
    public static AnimationManager.AnimationAccessor<LongHitAnimation> ZAP;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> ZAP_LONG;

    // Animation from Sculk Steve
    public static AnimationManager.AnimationAccessor<StaticAnimation> PLAYER_HEROBRINE_POSSESSION;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LEGENDARY_SWORD_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_SACRIFICING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ASSISTANCE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_STAGE_CHANGE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> PORTAL_SUMMON;

    // Animation from Yonchi Chikito
    public static AnimationManager.AnimationAccessor<ActionAnimation> DIAMOND_ATTRACTOR_SKILL;
    public static AnimationManager.AnimationAccessor<StaticAnimation> GREATAXE_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> GREATAXE_WALK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATAXE_SLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATAXE_OFFHAND_ATTACK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SLAM_FIRST;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SLAM_SECOND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SLAM_THIRD;
    public static AnimationManager.AnimationAccessor<AttackAnimation> MOON_BLADE_SKILL;

    // Animation made by me
    public static AnimationManager.AnimationAccessor<ActionAnimation> TRIDENT_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNOCKED_ELITE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ANIMATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_HEALING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LOW_CLONE_ESCAPE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> IDLE_BREAK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLACE_BLOCK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLACK_FIRE_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> BLUE_FLAME_SWORD;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DIAMOND_BLASTER_SKILL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> EARTH_AXE_SHOOT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> RED_AXE_ATTACK;

    // Animation clone and re-registered from WOM
    public static AnimationManager.AnimationAccessor<ActionAnimation> CUT_ANTITHEUS_ASCENSION;
    public static AnimationManager.AnimationAccessor<MovementAnimation> TORMENT_BERSERK_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TRIDENT_GUARD_HIT_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TRIDENT_GUARD_HIT_2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> ELECTRIC_FIELD;
    public static AnimationManager.AnimationAccessor<AttackAnimation> EARTH_AXE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> GLOWING_AGONY_GUARD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_BULL_CHARGE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_MOONLESS_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_MOONLESS_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_SOLAR_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_NAPOLEON_AUTO_3;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> YELLOW_NAPOLEON_AUSTERLITZ_SHOOT;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> ENDER_AEGIS_NAPOLEON_RELOAD_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUSTERLITZ;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_TORMENT_CHARGED_ATTACK_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_COMET;
    public static AnimationManager.AnimationAccessor<ActionAnimation> AGONY_GUARD_HIT_1;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> ENDER_GLAIVE_NAPOLEON_SHOOT_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_AGONY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AGRESSION;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_GUILLOTINE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CLONE_ANTITHEUS_ASCENSION;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CLONE_ANTITHEUS_LAPSE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_DEATHFALL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_BLINK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_BLACKHOLE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_WATERLOW;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_TORMENT_CHARGED_ATTACK_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ENDERBLASTER_ONEHAND_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_TORMENT_BERSERK_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_TORMENT_BERSERK_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_SOLAR_AUTO_3;
    public static AnimationManager.AnimationAccessor<AntitheusShootAttackAnimation> CLONE_ANTITHEUS_SHOOT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CLONE_ANTITHEUS_ASCENDED_IDLE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NULL_SKELETON_ANTITHEUS_ASCENSION;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_3;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> HEROBRINE_MOB_ENDERSTEP_OBSCURIS;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_ANTITHEUS_ASCENDED_DEATHFALL;
    public static AnimationManager.AnimationAccessor<MovementAnimation> OLD_MOONLESS_RUN;
    public static AnimationManager.AnimationAccessor<MovementAnimation> TRIDENT_TWO_HAND_RUN;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_STRONG_PUNCH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_2;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> CLONE_NAPOLEON_WATERLOW_SHOOT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CUT_ENDERBLASTER_TWOHAND_RELOAD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HACKER_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WARBLADE_SATSUJIN_TSUKUYOMI;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HOOK_HERRSCHER_UP;

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(AnnoyingVillagers.MODID, AVAnimations::build);
    }

    private static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        // Animation clone and re-registered from EpicFight
        EAT_OFFHAND = builder.nextAccessor("biped/epicfight_clone/eat_offhand",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        DRINK_OFFHAND = builder.nextAccessor("biped/epicfight_clone/drink_offhand",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        SHIELD_MAINHAND = builder.nextAccessor("biped/epicfight_clone/shield_mainhand",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        AEGIS_SHIELD_SHOOT = builder.nextAccessor("biped/epicfight_clone/aegis_shield_shoot",
                accessor -> new ActionAnimation(0.35F, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    EnderAegisItem.shieldShoot(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal());
                                    if (livingEntityPatch.getOriginal() instanceof Player player) {
                                        ItemCooldowns cooldowns = player.getCooldowns();
                                        cooldowns.addCooldown(player.getMainHandItem().getItem(), 10);
                                    }
                                }, Side.SERVER)
                        ));
        SHIELD_OFFHAND = builder.nextAccessor("biped/epicfight_clone/shield_offhand",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        OBSIDIAN_FIST_AUTO1 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_2_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));
        OBSIDIAN_FIST_AUTO2 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto2",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_2_OBSIDIAN_HAND_RIGHT, Side.SERVER)
                        ));
        OBSIDIAN_FIST_AUTO3 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto3",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.5F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_3_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));
        SHADOW_OBSIDIAN_FIST_AUTO1 = builder.nextAccessor("biped/epicfight_clone/shadow_obsidian_fist_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_2_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));
        SHADOW_OBSIDIAN_FIST_AUTO3 = builder.nextAccessor("biped/epicfight_clone/shadow_obsidian_fist_auto3",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.5F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_3_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));
        OBSIDIAN_FIST_AIR_SLASH = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_airslash",
                accessor -> new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.4F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.SUMMON_6_OBSIDIAN_HAND_RIGHT, Side.SERVER)
                        ));
        SHADOW_OBSIDIAN_FIST_AIR_SLASH = builder.nextAccessor("biped/epicfight_clone/shadow_obsidian_fist_airslash",
                accessor -> new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.4F, AVCollider.SHADOW_OBSIDIAN_PILLAR, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F));
        OBSIDIAN_BIPED_LANDING = builder.nextAccessor("biped/epicfight_clone/obsidian_landing",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_OBSIDIAN_PILLAR, Side.SERVER)
                        ));
        OBSIDIAN_ZOMBIE_ATTACK3 = builder.nextAccessor("biped/epicfight_clone/obsidian_zombie_attack3",
                accessor -> new AttackAnimation(0.1F, 0.5F, 0.5F, 0.6F, 1.15F, ColliderPreset.HEAD, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.65F, ReuseableEvents.SUMMON_OBSIDIAN_WALL, Side.SERVER)
                        ));
        SHADOW_HEROBRINE_BIPED_LANDING = builder.nextAccessor("biped/epicfight_clone/shadow_herobrine_landing",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                        if (livingEntity instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
                                            shadowHerobrineEntity.setObsidianMachineGunTick();
                                        }
                                    }
                                }, Side.SERVER)
                        ));
        NERF_TSUNAMI_REINFORCED = builder.nextAccessor("biped/epicfight_clone/tsunami_reinforced", accessor -> new AttackAnimation(0.1F, 0.2F, 0.35F, 0.45F, 0.7F, ColliderPreset.BIPED_BODY_COLLIDER, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2.0F))
                .addProperty(ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.RAW_COORD_WITH_X_ROT)
                .addProperty(ActionAnimationProperty.COORD_SET_TICK, null)
                .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.15F, 0.85F))
                .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                .addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.ROOT_X_MODIFIER)
                .addEvents(StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create(ReusableSources.RESTORE_BOUNDING_BOX, Side.BOTH))
                .addEvents(StaticAnimationProperty.TICK_EVENTS, AnimationEvent.SimpleEvent.create(ReusableSources.RESIZE_BOUNDING_BOX, Side.BOTH)
                        .params(EntityDimensions.scalable(0.6F, 1.0F)))
                .addEvents(AnimationEvent.InPeriodEvent.create(0.35F, 1.0F, (entitypatch, animation, params) -> {
                    Vec3 pos = entitypatch.getOriginal().position();

                    for(int x = -1; x <= 1; x += 2) {
                        for(int z = -1; z <= 1; z += 2) {
                            Vec3 rand = new Vec3(Math.random() * (double)x, Math.random(), Math.random() * (double)z).normalize().scale(2.0F);
                            entitypatch.getOriginal().level().addParticle(EpicFightParticles.TSUNAMI_SPLASH.get(), pos.x + rand.x, pos.y + rand.y - (double)1.0F, pos.z + rand.z, rand.x * 0.1, rand.y * 0.1, rand.z * 0.1);
                        }
                    }
                }, Side.CLIENT)));
        BLUE_DEMON_DIE_LEGENDARY_SWORD_START = builder.nextAccessor("biped/epicfight_clone/blue_demon_die_legendary_sword_start",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        BLUE_DEMON_DIE_LEGENDARY_SWORD_TICK = builder.nextAccessor("biped/epicfight_clone/blue_demon_die_legendary_sword_tick",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HOOK_AXE_AUTO1 = builder.nextAccessor("biped/epicfight_clone/hook_axe_auto1",
                (accessor) -> new BasicAttackAnimation(0.15F, 0.05F, 0.15F, 0.7F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED));
        HOOK_AXE_AUTO2 = builder.nextAccessor("biped/epicfight_clone/hook_axe_auto2", (accessor) -> new BasicAttackAnimation(0.15F, 0.05F, 0.15F, 0.85F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED));
        HOOK_DANCING_EDGE = builder.nextAccessor("biped/epicfight_clone/hook_dancing_edge",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED,
                        new Phase(0.0F, 0.25F, 0.4F, 0.4F, 0.4F, Armatures.BIPED.get().toolR, null),
                        new Phase(0.4F, 0.4F, 0.5F, 0.55F, 0.6F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new Phase(0.6F, 0.6F, 0.7F, 1.15F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true));
        DNAX_HOOK_SWEEPING_EDGE = builder.nextAccessor("biped/epicfight_clone/dnax_hook_sweeping_edge",
                (accessor) -> (new AttackAnimation(0.1F, 0.0F, 0.15F, 0.3F, 0.8F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER));
        DNAX_HOOK_DANCING_EDGE = builder.nextAccessor("biped/epicfight_clone/dnax_hook_dancing_edge",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED,
                        new Phase(0.0F, 0.25F, 0.4F, 0.4F, 0.4F, Armatures.BIPED.get().toolR, null),
                        new Phase(0.4F, 0.4F, 0.5F, 0.55F, 0.6F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new Phase(0.6F, 0.6F, 0.7F, 1.15F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true));
        THUNDER_SWEEPING_EDGE = builder.nextAccessor("biped/epicfight_clone/thunder_sweeping_edge",
                (accessor) -> (new AttackAnimation(0.1F, 0.0F, 0.15F, 0.3F, 0.8F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, assetAccessor, objects) -> {
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                                        ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, livingEntity);
                                    }

                                }, Side.BOTH)
                        ));
        THUNDER_DANCING_EDGE = builder.nextAccessor("biped/epicfight_clone/thunder_dancing_edge",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED,
                        new Phase(0.0F, 0.25F, 0.4F, 0.4F, 0.4F, Armatures.BIPED.get().toolR, null),
                        new Phase(0.4F, 0.4F, 0.5F, 0.55F, 0.6F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new Phase(0.6F, 0.6F, 0.7F, 1.15F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, assetAccessor, objects) -> {
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                                        ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, livingEntity);
                                        if (livingEntity.getOffhandItem().getItem() instanceof ThunderDiamondBladeItem) {
                                            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, livingEntity, true);
                                        }
                                    }

                                }, Side.BOTH)
                        ));


        // Animation from EpicFight Infernal Gainer
        INFERNAL_AUTO_1 = builder.nextAccessor("biped/epicfight_infernal_gainer/infernal_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, ColliderPreset.FIST, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));
        INFERNAL_AUTO_2 = builder.nextAccessor("biped/epicfight_infernal_gainer/infernal_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.2F, 0.1F, 0.2F, 0.25F, ColliderPreset.FIST, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));
        INFERNAL_AUTO_3 = builder.nextAccessor("biped/epicfight_infernal_gainer/infernal_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.35F, 0.45F, 0.5F, ColliderPreset.FIST, humanoidArmature.get().legL, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));
        OBSIDIAN_INFERNAL_AUTO_1 = builder.nextAccessor("biped/epicfight_infernal_gainer/obsidian_infernal_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 5.4F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.SUMMON_6_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));
        OBSIDIAN_INFERNAL_AUTO_2 = builder.nextAccessor("biped/epicfight_infernal_gainer/obsidian_infernal_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.2F, 0.1F, 0.2F, 0.25F, ColliderPreset.FIST, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 5.4F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_OBSIDIAN_WALL, Side.SERVER)
                        ));

        // Animation from EpicFight ACG
        BOW_AUTO_1 = builder.nextAccessor("biped/epic_agc/bow_auto1",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.62F, 0.8333F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH)
                        )
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_2 = builder.nextAccessor("biped/epic_agc/bow_auto2",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.7F, 0.98F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH)
                        )
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_3 = builder.nextAccessor("biped/epic_agc/bow_auto3",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.88F, 1.03F, 1.3F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.84F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH)
                        ));

        BOW_AUTO_4 = builder.nextAccessor("biped/epic_agc/bow_auto4",
                accessor -> new BowAttackAnimation(0.05F, 0, 2.12F, 2.733F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.2083F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.7916F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH),
                                AnimationEvent.InTimeEvent.create(2.0416F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_5 = builder.nextAccessor("biped/epic_agc/bow_auto5",
                accessor -> new BowAttackAnimation(0.02F, 0, 0.2F, 1.51F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.7083F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), Side.BOTH)
                        ));

        // Animation from Epicfight Valour Guard
        VALOUR_HOLD_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_hold_greatsword",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        VALOUR_RUN_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_run_greatsword",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        VALOUR_WALK_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_walk_greatsword",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        VALOUR_FIST_GUARD = builder.nextAccessor("biped/epicfight_valour_guard/valour_fist_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        VALOUR_FIST_GUARD_HIT = builder.nextAccessor("biped/epicfight_valour_guard/valour_fist_guard_hit",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));

        // Animation from EpicFight x Iron's Spells
        CASTING_ONE_HAND_TOP = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_top",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        CASTING_ONE_HAND_INWARD = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_inward",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        CASTING_ONE_HAND_BUFF = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_buff",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));

        // Animation from EpicFight Dual GreatSword
        GREATSWORD_TWOHAND_AUTO_1 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_twohand_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null),
                        new Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.55F));
        GREATSWORD_TWOHAND_AUTO_2 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_twohand_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));
        GREATSWORD_DUAL_AUTO_1 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null),
                        new Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));
        GREATSWORD_DUAL_AUTO_2 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.85F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT)
                        ));
        GREATSWORD_DUAL_AUTO_3 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT)
                        ));
        GREATSWORD_DUAL_AUTO_4 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.8F, 1.0F, 1.25F, InteractionHand.OFF_HAND, ColliderPreset.DUAL_SWORD, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75F));
        GREATSWORD_DUAL_DASH = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_dash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.1F, 0.4F, 0.4F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));
        GREATSWORD_DUAL_AIRSLASH = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_airslash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 0.45F, InteractionHand.OFF_HAND, WOMWeaponColliders.TORMENT_AIRSLAM, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(
                        AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT)
                ));
        GREATSWORD_DUAL_EARTHQUAKE = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_earthquake",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidArmature.get().toolR, ColliderPreset.DUAL_SWORD),
                        new Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, ColliderPreset.DUAL_SWORD))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT)
                        ));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_1 = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_twohand_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null),
                        new Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.55F));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_2 = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_twohand_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AUTO_3 = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_dual_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT)
                        ));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AIRSLASH = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_dual_airslash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 0.45F, InteractionHand.OFF_HAND, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT)
                        ));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_dual_earthquake",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.25F, ReuseableEvents.SUMMON_OBSIDIAN_CIRCLE, Side.SERVER)
                        ));

        SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE_PILLAR = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_dual_earthquake_pillar",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.25F, ReuseableEvents.SUMMON_OBSIDIAN_CIRCLE, Side.SERVER)
                        ));

        // Animation from EpicFight Sanji
        SANJI_DIABLE = builder.nextAccessor("biped/epicsanji/sanji_disable",
                accessor -> (new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 1.95F, 2.15F, 3.0F, Float.MAX_VALUE,  humanoidArmature.get().rootJoint, AVCollider.SANJI_SPIN)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, SoundEvents.BLAZE_SHOOT)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.55F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.65F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.75F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.85F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.95F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.05F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.15F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.25F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.35F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.45F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.55F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.65F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get())
                        ));
        SANJI_CONCASSER = builder.nextAccessor("biped/epicsanji/sanji_concasser",
                accessor -> (new BasicMultipleAttackAnimation(0.1F, 1.15F, 1.9F, 2.35F, AVCollider.SANJI_SPIN, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(8.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.05F)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.05F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.15F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.25F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.35F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.45F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.55F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()))
                        );

        // Animation from Pugilist Steve Annoying Villagers 1.18.2
        BLUE_DEMON_STATE_TRANSFORM = builder.nextAccessor("biped/pugilist_steve/blue_demon_state_transform",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        BLUE_DEMON_STATE_TRANSFORM_END = builder.nextAccessor("biped/pugilist_steve/blue_demon_state_transform_end",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        BLUE_DEMON_DIE = builder.nextAccessor("biped/pugilist_steve/blue_demon_die",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        TRIDENT_FESTIVAL = builder.nextAccessor("biped/pugilist_steve/trident_festival",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel) {
                                        if (livingEntityPatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity) {
                                            blueDemonEntity.setState(1);
                                            blueDemonEntity.playSound(AnnoyingVillagersModSounds.BLUE_DEMON_SAY_TRIDENT_FESTIVAL.get(), 1.0F, 1.0F);
                                        }
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        if (livingEntityPatch.getOriginal() instanceof BlueDemonEntity) {
                                            BlueDemonTridentItem.summonMissingTridentAndAnimate(serverLevel, livingEntityPatch.getOriginal());
                                        }
                                        ScreenShakeUtil.applyScreenShake(serverLevel, livingEntityPatch.getOriginal().blockPosition().getCenter(), 12.0, 80, 8);
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.spawnDamageZones(serverLevel, livingEntityPatch.getOriginal());
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal(), true);
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal(), true);
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.5F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.summonSuperLightningAtGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                        BlueDemonTridentItem.setStormEnergy(livingEntityPatch.getOriginal().getMainHandItem(), 0);
                                        BlueDemonTridentItem.setStormEnergy(livingEntityPatch.getOriginal().getOffhandItem(), 0);
                                        if (livingEntityPatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity) {
                                            blueDemonEntity.beginStateTwoTransform();
                                            livingEntityPatch.playAnimationSynchronized(BLUE_DEMON_STATE_TRANSFORM, 0.0F);
                                        }
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER)
                        ));
        COUNTER = builder.nextAccessor("biped/pugilist_steve/counter",
                accessor -> new BasicMultipleAttackAnimation(0.3F, 0.08F, 0.1F, 0.15F, 0.525F, ColliderPreset.FIST, humanoidArmature.get().legR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.COUNTER))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.5F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F)));
        FIST_GUARD = builder.nextAccessor("biped/pugilist_steve/fist_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        FIST_DASH = builder.nextAccessor("biped/pugilist_steve/fist_dash",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));
        WHIRLWIND_KICK = builder.nextAccessor("biped/pugilist_steve/whirlwind_kick",
                accessor -> new BasicMultipleAttackAnimation(0.2F, 0.29F, 0.45F, 0.85F, 1.8F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().legR, accessor, humanoidArmature).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReusableSources.PLAY_SOUND, Side.SERVER)
                                        .params(EpicFightSounds.WHOOSH.get()))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.5F));
        LEGENDARY_SWORD_HEAVY_ATTACK = builder.nextAccessor("biped/pugilist_steve/legendary_sword_heavy_attack",
                accessor -> new HeavyAttackAnimation(0.05F, 0.05F, 0.5F, 0.7F, 1.2F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.5F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicanimation, livingentitypatch, f, f1, pose) -> {
                            if (f1 >= 0.3F && f1 < 0.35F) {
                                float f2 = (float) livingentitypatch.getOriginal().getX();
                                float f3 = (float) livingentitypatch.getOriginal().getY();
                                float f4 = (float) livingentitypatch.getOriginal().getZ();

                                for (BlockState blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) f2, (int) f3, (int) f4))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) f2, (int) f3, (int) f4)))) {
                                    --f3;
                                }

                                float f5 = (float) Math.max(Math.abs(livingentitypatch.getOriginal().getY() - (double) f3) - 1.0D, 0.0D);

                                return 1.0F - (1.0F / (-f5 - 1.0F) + 1.0F);
                            } else {
                                return 1.0F;
                            }
                        }).addEvents(
                                new AnimationEvent.InTimeEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.6F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.SHOCK_WAVE, Side.SERVER)
                                })
        );
        HACKER_SWORD_SKILL_TWOHAND = builder.nextAccessor("biped/pugilist_steve/hacker_sword_skill",
                accessor -> new AttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.016F, 0.066F, 0.133F, 0.133F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new Phase(0.133F, 0.133F, 0.183F, 0.25F, 0.25F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new Phase(0.25F, 0.25F, 0.3F, 0.366F, 0.366F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new Phase(0.366F, 0.366F, 0.416F, 0.483F, 0.483F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new Phase(0.483F, 0.483F, 0.533F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new Phase(0.6F, 0.6F, 0.65F, 0.716F, 0.716F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new Phase(0.716F, 0.716F, 0.766F, 0.833F, 0.833F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new Phase(0.833F, 0.833F, 0.883F, 1.1F, 1.1F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new Phase(0.933F, 1.133F, 1.183F, 1.6F, 1.6F, humanoidArmature.get().toolL, ColliderPreset.SWORD)).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F));
        DUAL_SWORD_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto1",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_SWORD_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        TRIDENT_DUAL_AUTO2 = builder.nextAccessor("biped/pugilist_steve/trident_dual_auto2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        DUAL_SWORD_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto3",
                accessor -> new BasicAttackAnimation(0.16F, accessor, humanoidArmature, new Phase(0.0F, 0.66F, 0.69F, 0.733F, 1.0F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_SWORD_AUTO4 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto4",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        DUAL_SWORD_AUTO5 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto5",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        DUAL_SWORD1 = builder.nextAccessor("biped/pugilist_steve/dual_auto1",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, null), new Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        DUAL_SWORD2 = builder.nextAccessor("biped/pugilist_steve/dual_auto2",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.05F, 0.4F, 0.8F, 1.167F, 2.5F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.1F, 1.2F, 1.3F, 1.5F, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.1F, 1.4F, 1.5F, 2.1F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F));
        DUAL_SWORD3 = builder.nextAccessor("biped/pugilist_steve/dual_auto3",
                accessor -> new BasicAttackAnimation(0.1F, 0.0F, 0.0F, 0.06F, 0.3F, ColliderPreset.SWORD, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        FIST_UP = builder.nextAccessor("biped/pugilist_steve/fist_up",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 0.95F, WOMWeaponColliders.KICK, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, AVSounds.KICK.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));
        RUSH_SWORD = builder.nextAccessor("biped/pugilist_steve/rush_sword",
                accessor -> new RushSwordAnimation(
                        0.15F, 0.0F, 0.1F, 0.26F, 0.75F,
                        ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        DUAL_DANCING_EDGE = builder.nextAccessor("biped/pugilist_steve/dancing_edge",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature, new Phase(0.0F, 0.2F, 0.31F, 0.4F, 0.4F, humanoidArmature.get().toolR, null), new Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null), new Phase(0.65F, 0.76F, 0.85F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.DUAL_SWORD))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        SWEEPING_EDGE = builder.nextAccessor("biped/pugilist_steve/sweeping_edge",
                accessor -> new AttackAnimation(0.2F, 0.1F, 0.35F, 0.46F, 0.79F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.9F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        HIT_BACKWARD = builder.nextAccessor("biped/pugilist_steve/hit_backward",
                accessor -> new LongHitAnimation(0.08F, accessor, humanoidArmature));
        SPEAR_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/spear_guard_hit",
                accessor -> new GuardAnimation(0.05F, 0.2F, accessor, humanoidArmature)
                        .addEvents(new AnimationEvent.InTimeEvent[]{
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.FAST_SPINNING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.FAST_SPINNING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, ReuseableEvents.FAST_SPINNING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, ReuseableEvents.FAST_SPINNING, Side.CLIENT)}));
        LEGENDARY_SWORD_GUARD = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        LEGENDARY_SWORD_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard_hit",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        LEGENDARY_SWORD_GUARD_PARRY = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard_parry",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        POSE_UP = builder.nextAccessor("biped/pugilist_steve/pose_up",
                accessor -> new ActionAnimation(0.0F, 1.85F, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true));
        DAGGER_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dagger_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dagger_auto2",
                accessor -> new BasicAttackAnimation(0.08F, 0.0F, 0.1F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dagger_auto3",
                accessor -> new BasicAttackAnimation(0.08F, 0.15F, 0.26F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_DUAL_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.16F, 0.25F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_DUAL_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto2",
                accessor -> new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.16F, InteractionHand.OFF_HAND, null, humanoidArmature.get().toolL, accessor, humanoidArmature));
        DAGGER_DUAL_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto3",
                accessor -> new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_DUAL_AUTO4 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto4",
                accessor -> new BasicAttackAnimation(0.13F, 0.1F, 0.21F, 0.4F, ColliderPreset.DUAL_DAGGER_DASH, humanoidArmature.get().toolR, accessor, humanoidArmature));
        CHECK = builder.nextAccessor("biped/pugilist_steve/check",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        BIPED_RUN_ESWORD = builder.nextAccessor("biped/pugilist_steve/run_esword",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        KNIFE_IDLE = builder.nextAccessor("biped/pugilist_steve/knife_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        KNIFE_RUN = builder.nextAccessor("biped/pugilist_steve/knife_run",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        KNIFE_ATTACK = builder.nextAccessor("biped/pugilist_steve/knife_attack",
                accessor -> new BasicAttackAnimation(0.15F, 0.01F, 0.2F, 0.5F, 0.6F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addState(EntityState.MOVEMENT_LOCKED, false)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        KNIFE_CHECK = builder.nextAccessor("biped/pugilist_steve/knife_check",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        CARRY = builder.nextAccessor("biped/pugilist_steve/carry",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        FIST_LEFT = builder.nextAccessor("biped/pugilist_steve/fist_left",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 1.1F, ColliderPreset.FIST, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));
        KNOCKDOWN_FORWARD = builder.nextAccessor("biped/pugilist_steve/knockdown_forward",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        KNOCKDOWN_RIGHT = builder.nextAccessor("biped/pugilist_steve/knockdown_right",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        KNOCKDOWN_LEFT = builder.nextAccessor("biped/pugilist_steve/knockdown_left",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AXE_HEAVY_AUTO_1 = builder.nextAccessor("biped/pugilist_steve/axe_heavy_auto1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.3F, 0.6F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F));
        AXE_HEAVY_AUTO_2 = builder.nextAccessor("biped/pugilist_steve/axe_heavy_auto2",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.8F, 1.2F, 1.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F));
        SWORD_HEAVY_AUTO_1 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.11F, 0.27F, 0.5F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        SWORD_HEAVY_AUTO_2 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto2",
                accessor -> new BasicMultipleAttackAnimation(0.01F, 0.1F, 0.12F, 0.22F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        SWORD_HEAVY_AUTO_3 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto3",
                accessor -> new BasicMultipleAttackAnimation(0.01F, 0.1F, 0.21F, 0.32F, 1.2F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.5F));
        HARD_KICK = builder.nextAccessor("biped/pugilist_steve/hard_kick",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.29F, 1.1F, 1.2F, 3.1F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().legR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        HARD_KICK_HIT = builder.nextAccessor("biped/pugilist_steve/hard_kick_hit",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        RUN_START = builder.nextAccessor("biped/pugilist_steve/run_start",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        LONGSWORD_AUTO1 = builder.nextAccessor("biped/pugilist_steve/tachi_auto1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.2F, 0.3F, 0.75F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        RUN_HOLD = builder.nextAccessor("biped/pugilist_steve/run_hold",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        RUN_DUAL_BIG = builder.nextAccessor("biped/pugilist_steve/run_dual_big",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        LONGEST_HIT = builder.nextAccessor("biped/pugilist_steve/longest_hit",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        HARD_GREATSWORD_GUARD = builder.nextAccessor("biped/pugilist_steve/hard_greatsword",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HARD_GREATSWORD_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/hard_greatsword_hit",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        HARD_GREATSWORD_GUARD_SKILL = builder.nextAccessor("biped/pugilist_steve/hard_greatsword_skill",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        HIT_LEFT = builder.nextAccessor("biped/pugilist_steve/hit_left",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature));
        HIT_RIGHT = builder.nextAccessor("biped/pugilist_steve/hit_right",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature));
        SHAKE_HAND_TRY = builder.nextAccessor("biped/pugilist_steve/shake_hand_try",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        SHAKE_HAND = builder.nextAccessor("biped/pugilist_steve/shake_hand",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        FIST_TRY = builder.nextAccessor("biped/pugilist_steve/fist_try",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        FISTING = builder.nextAccessor("biped/pugilist_steve/fisting",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.15F, ReusableSources.PLAY_SOUND, Side.SERVER).params(EpicFightSounds.WHOOSH.get()))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        GIANT_WHIRLWIND = builder.nextAccessor("biped/pugilist_steve/giant_whirlwind",
                accessor -> new BasicAttackAnimation(0.41F, accessor, humanoidArmature, new Phase(0.0F, 0.3F, 0.35F, 0.55F, 0.9F, 0.9F, humanoidArmature.get().toolL, null), new Phase(0.9F, 0.95F, 1.05F, 1.2F, 1.5F, 1.5F, humanoidArmature.get().toolL, null), new Phase(1.5F, 1.65F, 1.75F, 1.95F, 2.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, ColliderPreset.GREATSWORD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_SWORD_DANCING_EDGE = builder.nextAccessor("biped/pugilist_steve/dual_sword_dancing_edge",
                accessor -> new BasicAttackAnimation(0.25F, accessor, humanoidArmature, new Phase(0.0F, 0.2F, 0.31F, 0.4F, 0.4F, humanoidArmature.get().toolR, null), new Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null), new Phase(0.65F, 0.76F, 0.85F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.DUAL_SWORD))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG, 2)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        SPEAR_THRUST = builder.nextAccessor("biped/pugilist_steve/spear_thrust",
                accessor -> new AttackAnimation(0.11F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.3F, 0.36F, 0.5F, 0.5F, humanoidArmature.get().toolR, ColliderPreset.SPEAR),
                        new Phase(0.5F, 0.5F, 0.56F, 0.75F, 0.75F, humanoidArmature.get().toolR, ColliderPreset.SPEAR),
                        new Phase(0.75F, 0.75F, 0.81F, 1.05F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.SPEAR))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        DUAL_TACHI_GUARD = builder.nextAccessor("biped/pugilist_steve/dual_tachi_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        DUAL_TACHI_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/dual_tachi_guard_hit",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        WHIRLWIND_KICK_LEFT = builder.nextAccessor("biped/pugilist_steve/whirlwind_kick_left",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.3F, 0.7F, 0.9F, Float.MAX_VALUE, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().legL, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10.8F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.23F, ReusableSources.PLAY_SOUND, Side.SERVER).params(EpicFightSounds.WHOOSH.get()))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.5F));
        SUPER_PUNCH = builder.nextAccessor("biped/pugilist_steve/super_punch",
                accessor -> new AttackAnimation(0.05F, 1.0F, 1.25F, 1.4F, Float.MAX_VALUE, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        GUARD_BREAK_ATTACK = builder.nextAccessor("biped/pugilist_steve/guard_break_attack",
                accessor -> new KnockdownAnimation(0.05F, accessor, humanoidArmature));
        SWORD_DASH = builder.nextAccessor("biped/pugilist_steve/sword_dash",
                accessor -> new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        TACHI_DASH = builder.nextAccessor("biped/pugilist_steve/tachi_dash",
                accessor -> new DashAttackAnimation(0.15F, 0.1F, 0.2F, 0.45F, 0.7F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG));
        DUAL_SWORD_SKILL = builder.nextAccessor("biped/pugilist_steve/dual_sword_skill",
                accessor -> new BasicAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.15F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, null).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.7F, 1.7F, 1.8F, 1.8F, 1.8F, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.8F, 1.8F, 1.9F, 1.9F, 1.9F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new Phase(1.9F, 2.0F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolL, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F)).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(2.5F, ReuseableEvents.END_ATTACK, Side.BOTH))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        DUAL_END = builder.nextAccessor("biped/pugilist_steve/dual_back_end",
                accessor -> new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        TRIED = builder.nextAccessor("biped/pugilist_steve/tried",
                accessor -> new KnockdownAnimation(0.2F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        GREATSWORD_SKILL = builder.nextAccessor("biped/pugilist_steve/greatsword_skill",
                accessor -> new BasicAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.1F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.7F, 1.7F, 1.8F, 1.85F, 1.85F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.85F, 1.85F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F));
        LEGENDARY_SWORD_WAKE_UP_ATTACK = builder.nextAccessor("biped/pugilist_steve/legendary_sword_wake_up_attack",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.15F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null), new Phase(0.45F, 0.5F, 0.8F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false));
        DUAL_E_END = builder.nextAccessor("biped/pugilist_steve/dual_e_end",
                accessor -> new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AXE_FUN_SKILL = builder.nextAccessor("biped/pugilist_steve/axe_fun_skill",
                accessor -> new BasicAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.1F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, ColliderPreset.SWORD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.5F, 1.5F, 1.55F, 1.55F, 1.55F, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new Phase(1.55F, 1.6F, 1.7F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, false).addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        LEGENDARY_SWORD_AUTO_4 = builder.nextAccessor("biped/pugilist_steve/legendary_sword_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        ));
        OBSIDIAN_FIST_DASH = builder.nextAccessor("biped/pugilist_steve/obsidian_fist_dash",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.THROW_OBSIDIAN, Side.SERVER)
                        ));
        SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG = builder.nextAccessor("biped/pugilist_steve/shadow_obsidian_sword_onehand_long",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.2F, 0.3F, 0.75F, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO4 = builder.nextAccessor("biped/pugilist_steve/shadow_obsidian_sword_dual_sword_auto4",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO5 = builder.nextAccessor("biped/pugilist_steve/shadow_obsidian_sword_dual_sword_auto5",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        TRIDENT_THROW_2 = builder.nextAccessor("biped/pugilist_steve/trident_throw_2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.THROW_TRIDENT_HAND_LEFT, Side.SERVER)
                        ));
        TRIDENT_THROW_LEGENDARY = builder.nextAccessor("biped/pugilist_steve/trident_throw_legendary",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, ReuseableEvents.THROW_TRIDENT_HAND_LEFT, Side.SERVER)
                        ));

        // Animation from Community EpicFight Battle Arts
        ADVANCED_LANCER_AUTO1 = builder.nextAccessor("biped/battle_style/advanced_lancer_auto1", access ->
                new BasicAttackAnimation(0.2f, 0.0f, 0.2f, 0.3f, 0.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        ADVANCED_LANCER_AUTO3 = builder.nextAccessor("biped/battle_style/advanced_lancer_auto3", access ->
                new AttackAnimation(0.2f, 0.0f, 0.75f, 0.9f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.2f, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.35f, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        Vec3 tridentTip = EpicfightUtil.getJointWithTranslation(
                                                livingEntityPatch.getOriginal(), new Vec3f(0,0,0), Armatures.BIPED.get().toolR, 1.2F, 0.0F
                                        );
                                        if (tridentTip != null) {
                                            BlockPos.MutableBlockPos checkPos = BlockPos.containing(tridentTip).mutable();
                                            while (checkPos.getY() > serverLevel.getMinBuildHeight()
                                                    && !serverLevel.getBlockState(checkPos).isSolidRender(serverLevel, checkPos)) {
                                                checkPos.move(0, -1, 0);
                                            }
                                            if (serverLevel.getBlockState(checkPos).isSolidRender(serverLevel, checkPos)) {
                                                TridentLightningBolt tridentLightningBolt = new TridentLightningBolt(AnnoyingVillagersModEntities.TRIDENT_LIGHTNING_BOLT.get(), serverLevel);
                                                tridentLightningBolt.setOwner(livingEntityPatch.getOriginal());
                                                tridentLightningBolt.moveTo(
                                                        checkPos.getX() + 0.5D,
                                                        checkPos.getY() + 1.0D,
                                                        checkPos.getZ() + 0.5D
                                                );
                                                serverLevel.addFreshEntity(tridentLightningBolt);
                                            }
                                        }
                                    }
                                }, Side.SERVER)
                        ));
        TRIDENT_THROW_1 = builder.nextAccessor("biped/battle_style/trident_throw_1", access ->
                new BasicAttackAnimation(0.2f, 0.0f, 0.2f, 0.3f, 0.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0f, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.05f, ReuseableEvents.THROW_TRIDENT_HAND_RIGHT, Side.SERVER)));
        TRIDENT_THROW_5 = builder.nextAccessor("biped/battle_style/trident_throw_5", access ->
                new AttackAnimation(0.2f, 0.0f, 0.75f, 0.9f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.2f, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.35f, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4f, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4f, ReuseableEvents.THROW_TRIDENT_HAND_RIGHT_EXPLODE, Side.SERVER)));
        ADVANCED_DUELIST_WHIRLEDGE = builder.nextAccessor("biped/battle_style/advanced_duelist_whirledge", access ->
                new AttackAnimation(0.2f, access, humanoidArmature,
                        new AttackAnimation.Phase(0.0f, 0.3f, 0.3f, 0.4f, 0.4f, 0.4f, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f)),
                        new AttackAnimation.Phase(0.4f, 0.0f, 0.4f, 0.5f, 0.5f, 0.5f, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.15f)),
                        new AttackAnimation.Phase(0.5f, 0.0f, 0.5f, 0.6f, 0.6f, 0.6f, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2f)),
                        new AttackAnimation.Phase(0.6f, 0.0f, 0.6f, 0.7f, 0.7f, 0.7f, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(0.7f, 0.0f, 0.7f, 0.8f, 0.8f, 0.8f, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f)),
                        new AttackAnimation.Phase(0.8f, 0.0f, 0.8f, 0.9f, 0.9f, 0.9f, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35f)),
                        new AttackAnimation.Phase(0.9f, 0.0f, 1.25f, 1.35f, 2f, 2f, InteractionHand.MAIN_HAND, humanoidArmature.get().rootJoint, ColliderPreset.BATTOJUTSU_DASH)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.7F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.7F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER)));
        ADVANCED_DUELIST_SHOOTING_STAR = builder.nextAccessor("biped/battle_style/advanced_duelist_shooting_star", access ->
                new AttackAnimation(0.2f, 0.6f, 0.5f, 0.6f, 1.9f, ColliderPreset.BATTOJUTSU_DASH, Armatures.BIPED.get().rootJoint, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.5f))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, speed, prevElapsedTime, elapsedTime) ->
                        {
                            if (elapsedTime >= 0.5F && elapsedTime < 0.6F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for(BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float)Math.max(Math.abs(livingEntityPatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                                LivingEntity livingentity = livingEntityPatch.getOriginal();
                                Vec3f direction = new Vec3f(2.5F, -1.5F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float)Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                if (distanceToGround > 0.5F) {
                                    livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                    return 0.025F;
                                } else {
                                    return speed;
                                }
                            } else {
                                return speed;
                            }
                        })
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.3F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.5f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT)
                                        .params(SoundEvents.TRIDENT_HIT_GROUND),
                                AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.SERVER)
                                        .params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.get().rootJoint, 1.2, 1F)));

        SQUIRE_SWORD_IDLE = builder.nextAccessor("biped/battle_style/squire_sword_idle",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        SQUIRE_SWORD_WALK = builder.nextAccessor("biped/battle_style/squire_sword_walk",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        SQUIRE_SWORD_RUN = builder.nextAccessor("biped/battle_style/squire_sword_run",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        SQUIRE_SWORD_AUTO_1 = builder.nextAccessor("biped/battle_style/squire_sword_auto1",
                accessor -> new AttackAnimation(0.1f, 0f, 0.2f, 0.35f, 0.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SQUIRE_SWORD_AUTO_2 = builder.nextAccessor("biped/battle_style/squire_sword_auto2",
                accessor -> new AttackAnimation(0.2f, 0f, 0.2f, 0.35f, 0.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SQUIRE_SWORD_AUTO_3 = builder.nextAccessor("biped/battle_style/squire_sword_auto3",
                accessor -> new AttackAnimation(0.2f, 0f, 0.2f, 0.35f, 2.0f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SQUIRE_SWORD_DASH_ATTACK = builder.nextAccessor("biped/battle_style/squire_sword_dash_attack",
                accessor -> new AttackAnimation(0.2f, accessor, Armatures.BIPED,
                new AttackAnimation.Phase(0.0f, 0.0f, 0.2f, 0.3f, 0.5f, 1.0f, Armatures.BIPED.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2f))));
        SQUIRE_SWORD_HOP_ATTACK = builder.nextAccessor("biped/battle_style/squire_sword_hop_attack",
                accessor ->
                new AirSlashAnimation(0.1f, 0f, 0.2f, 0.35f, 2f, false, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SQUIRE_SWORD_HEAVY_BLOW = builder.nextAccessor("biped/battle_style/squire_sword_heavy_blow",
                accessor -> new AttackAnimation(0.1f, 0f, 0.7f, 0.8f, 1.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addState(EntityState.CAN_SKILL_EXECUTION, false));
        SABRE_AUTO3 = builder.nextAccessor("biped/battle_style/sabre_auto3", access ->
                new AttackAnimation(0.05f, 0.0f, 0.1f, 0.2f, 1.9f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SABRE_DASH_ATTACK = builder.nextAccessor("biped/battle_style/sabre_dash_attack", access ->
                new DashAttackAnimation(0.2f, 0.0f, 0.3f, 0.45f, 1.9f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SABRE_AIR_ATTACK = builder.nextAccessor("biped/battle_style/sabre_aerial", access ->
                new AirSlashAnimation(0.2f, 0.0f, 0.5f, 0.6f, 1.9f, false, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SABRE_QUAD_STING = builder.nextAccessor("biped/battle_style/sabre_quadsting", access ->
                new AttackAnimation(0.2f, access, Armatures.BIPED, new AttackAnimation.Phase(
                        0.0f, 0.0f, 0.5f, 0.6f, 0.65f, 0.65f, Armatures.BIPED.get().toolR, null
                ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(
                        0.65f, 0.0f, 0.65f, 0.75f, 0.8f, 0.8f, Armatures.BIPED.get().toolR, null
                ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(
                        0.8f, 0.0f, 0.8f, 0.9f, 1.1f, 1.1f, Armatures.BIPED.get().toolR, null
                ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(
                        1.1f, 0.0f, 1.1f, 1.2f, 2f, 2f, Armatures.BIPED.get().toolR, null
                ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        TACHI_BLOSSOM_SLASH = builder.nextAccessor("biped/battle_style/tachi_blossom_slash", access ->
                new AttackAnimation(0.1f, access, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0f, 0.0f, 0.0f, 0.1f, 0.1f, 0.1f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD),
                        new AttackAnimation.Phase(0.15f, 0.0f, 0.15f, 0.25f, 0.25f, 0.25f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD),
                        new AttackAnimation.Phase(0.25f, 0.0f, 0.3f, 0.4f, 0.4f, 0.4f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD),
                        new AttackAnimation.Phase(0.4f, 0.0f, 0.45f, 0.55f, 0.6f, 0.6f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD),
                        new AttackAnimation.Phase(0.6f, 0.0f, 0.6f, 0.8f, 2f, 2f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD)
                )
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 4)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5f)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AXE_AUTO1 = builder.nextAccessor("biped/battle_style/axe_auto1",
                access -> new BasicAttackAnimation(0.2f, 0.0f, 0.7f, 0.8f, 1f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED));
        AXE_AUTO2 = builder.nextAccessor("biped/battle_style/axe_auto2",
                access -> new BasicAttackAnimation(0.2f, 0.0f, 0.5f, 0.6f, 1f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED));
        AXE_AUTO3 = builder.nextAccessor("biped/battle_style/axe_auto3",
                access -> new BasicAttackAnimation(0.2f, 0.0f, 0.35f, 0.55f, 1.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5f));
        AXE_DASH = builder.nextAccessor("biped/battle_style/axe_dash",
                access -> new DashAttackAnimation(0.2f, 0.0f, 0.35f, 0.45f, 1.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED));
        AXE_AIRSLASH = builder.nextAccessor("biped/battle_style/axe_airslash",
                access -> new AirSlashAnimation(0.2f, 0.0f, 0.5f, 0.65f, 1.5f, false, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false));
        AXE_INNATE = builder.nextAccessor("biped/battle_style/axe_innate",
                access -> new AttackAnimation(0.2f, 0.0f, 0.9f, 1.5f, 3f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.25f));
        BAXE_IDLE = builder.nextAccessor("biped/battle_style/baxe_idle",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        BAXE_WALK = builder.nextAccessor("biped/battle_style/baxe_walk",
                accessor -> new MovementAnimation(0.1f, true, accessor, Armatures.BIPED));
        BAXE_RUN = builder.nextAccessor("biped/battle_style/baxe_run",
                accessor -> new MovementAnimation(0.2f, true, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F));
        BAXE_AUTO_1 = builder.nextAccessor("biped/battle_style/baxe_auto1",
                accessor -> new BasicAttackAnimation(0.2f, 0.0f, 0.4f, 0.6f, 1.0f, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F));
        BAXE_AUTO_2 = builder.nextAccessor("biped/battle_style/baxe_auto2",
                accessor -> new BasicAttackAnimation(0.5f, 0.0f, 0.55f, 0.65f, 2.0f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2));
        BAXE_DASH_ATTACK = builder.nextAccessor("biped/battle_style/baxe_dash_attack",
                accessor -> new AirSlashAnimation(0.2f, 0.0f, 1.15f, 1.25f, 3.0f, false, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.7f))
                .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5f)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(1.25f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.get().toolR, 2.0, 2F)));
        BAXE_AIR_ATTACK = builder.nextAccessor("biped/battle_style/baxe_airslash",
                accessor -> new DashAttackAnimation(0.5f, 0.0f, 0.4f, 0.6f, 2.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS,2)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false));
        BAXE_SEISMIC_IMPACT = builder.nextAccessor("biped/battle_style/baxe_seismic_impact",
                access -> new AirSlashAnimation(0.5f, access, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0f, 0.0f, 1.4f, 1.6f, 1.6f, 1.6f, Armatures.BIPED.get().toolR, null
                        ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2f)),
                        new AttackAnimation.Phase(1.6f, 0.0f, 1.6f, 1.7f, 3.0f, 4.0f, Armatures.BIPED.get().rootJoint, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f))
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.GENERIC_EXPLODE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.6f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE,
                                                AnimationEvent.Side.CLIENT)
                                        .params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.get().toolR, 3.0, 2F
                                        ))
        );
        GREATSWORD_DASH_ATTACK = builder.nextAccessor("biped/battle_style/greatsword_dash_attack", access ->
                new DashAttackAnimation(0.2f, 0.0f, 0.5f, 0.65f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
        );
        GREATSWORD_POWER_GEYSER = builder.nextAccessor("biped/battle_style/greatsword_power_geyser", access ->
                new AttackAnimation(0.2f, 0.0f, 0.8f, 0.9f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1f))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.9f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.SERVER)
                                .params(new Vec3f(0.0F, -0.3F, -5.0F), Armatures.BIPED.get().toolR, 1.1, 1.55F))
        );
        GREATSWORD_AIRSLAM = builder.nextAccessor("biped/battle_style/greatsword_airslam", access ->
                new BasicAttackAnimation(0.2f, 0.0f, 0.5f, 0.65f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3f))
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.5f))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.65f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.SERVER)
                                        .params(new Vec3f(0.0F, -0.3F, -5.0F), Armatures.BIPED.get().toolR, 1.1, 1.55F)
                        )
        );
        THIEF_AUTO1 = builder.nextAccessor("biped/battle_style/thief_auto1",
                accessor -> new BasicAttackAnimation(0.2f, 0f, 0.2f, 0.35f, 0.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        THIEF_AUTO3 = builder.nextAccessor("biped/battle_style/thief_auto3",
                accessor -> new BasicAttackAnimation(0.2f, 0f, 0.55f, 0.65f, 1.7f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        THIEF_DASH_ATTACK = builder.nextAccessor("biped/battle_style/thief_dash_attack",
                accessor -> new DashAttackAnimation(0.2f, 0f, 0.3f, 0.4f, 1.7f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        THIEF_AIRSLASH = builder.nextAccessor("biped/battle_style/thief_airslash",
                accessor -> new AirSlashAnimation(0.2f, 0f, 0.2f, 0.3f, 1.7f, false, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        THIEF_STEAL = builder.nextAccessor("biped/battle_style/thief_steal",
                accessor -> new AttackAnimation(0.2f, 0f, 0.65f, 0.75f, 1.7f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_BLADES_AIRSLAM = builder.nextAccessor("biped/battle_style/dual_blades_airslam", access ->
                new BasicAttackAnimation(0.2f, access, humanoidArmature,
                        new AttackAnimation.Phase(0.0f, 0.0f, 0.4f, 0.5f, 0.6f, 0.6f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f)))
                        .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, assetAccessor, animationParameters) ->
                        {
                            if (assetAccessor.get() instanceof AttackAnimation animation && livingEntityPatch.getOriginal().getOffhandItem().getItem() instanceof SwordItem swordItem)
                            {
                                animation.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(swordItem.getDamage()));
                            }
                        }, AnimationEvent.Side.SERVER))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_BLADES_AUTO3 = builder.nextAccessor("biped/battle_style/dual_blades_auto3", access ->
                new BasicAttackAnimation(0.2f, access, humanoidArmature,
                        new AttackAnimation.Phase(0.0f, 0.0f, 0.55f, 0.65f, 0.6f, 0.65f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(0.65f, 0.0f, 0.65f, 0.75f, 0.75f, 0.75f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(0.75f, 0.0f, 0.9f, 1f, 1f, 1f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(1f, 0.0f, 1f, 1.1f, 3f, 3.0f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f))
                )
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        DUAL_BLADES_WHIRLEDGE = builder.nextAccessor("biped/battle_style/dual_blades_whirledge", access ->
                new AttackAnimation(0.2f, access, humanoidArmature,
                        new AttackAnimation.Phase(0.0f, 0.3f, 0.3f, 0.4f, 0.4f, 0.4f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f)),
                        new AttackAnimation.Phase(0.4f, 0.0f, 0.4f, 0.5f, 0.5f, 0.5f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.15f)),
                        new AttackAnimation.Phase(0.5f, 0.0f, 0.5f, 0.6f, 0.6f, 0.6f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2f)),
                        new AttackAnimation.Phase(0.6f, 0.0f, 0.6f, 0.7f, 0.7f, 0.7f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(0.7f, 0.0f, 0.7f, 0.8f, 0.8f, 0.8f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f)),
                        new AttackAnimation.Phase(0.8f, 0.0f, 0.8f, 0.9f, 0.9f, 0.9f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35f)),
                        new AttackAnimation.Phase(0.9f, 0.0f, 1.25f, 1.35f, 2f, 2f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, ColliderPreset.BATTOJUTSU_DASH)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f))
                )
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SWORD_DASH_ATTACK = builder.nextAccessor("biped/battle_style/sword_dash_attack", access ->
                new DashAttackAnimation(0.2f, 0.0f, 0.3f, 0.45f, 1.9f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        IRON_LOTUS_AUTO1 = builder.nextAccessor("biped/battle_style/iron_lotus_auto1", access ->
                new BasicAttackAnimation(0.1f, 0.0f, 0.1f, 0.2f, 0.25f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F));

        IRON_LOTUS_AUTO2 = builder.nextAccessor("biped/battle_style/iron_lotus_auto2", access ->
                new BasicAttackAnimation(0.1f, 0.0f, 0.1f, 0.2f, 0.25f, null, Armatures.BIPED.get().toolL,  access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F));

        IRON_LOTUS_AUTO3 = builder.nextAccessor("biped/battle_style/iron_lotus_auto3", access ->
                new BasicAttackAnimation(0.1f, 0.0f, 0.1f, 0.2f, 1.0f, null, Armatures.BIPED.get().legR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F));

        IRON_LOTUS_DASH_ATTACK = builder.nextAccessor("biped/battle_style/iron_lotus_dash_attack", access ->
                new BasicAttackAnimation(0.1f, 0.0f, 0.2f, 0.4f, 1.0f, ColliderPreset.BATTOJUTSU_DASH, Armatures.BIPED.get().rootJoint, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 5.0F));

        // Animation from Visitors from Omneria
        TRIDENT_THROW_3 = builder.nextAccessor("biped/omneria/trident_throw_3", accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.3F, 0.5F, 0.3F, 0.3F, InteractionHand.OFF_HAND, humanoidArmature.get().handR, WOMWeaponColliders.PUNCH),
                new Phase(0.3F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.PUNCH))
                .addProperty(AttackPhaseProperty.SWING_SOUND, SoundEvents.TRIDENT_RETURN)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.3F, ReuseableEvents.PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, p) -> {
                            if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                BlueDemonThunderBeamEntity beam = new BlueDemonThunderBeamEntity(
                                        AnnoyingVillagersModEntities.BLUE_DEMON_THUNDER_BEAM.get(),
                                        serverLevel,
                                        livingEntityPatch.getOriginal(),
                                        10,
                                        6,
                                        7.5F
                                );
                                beam.initSpawnState();
                                serverLevel.addFreshEntity(beam);
                            }
                        }, Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, Side.SERVER)
                ));

        // Animation from EpicFight-Awaken
        CUT_DP_AIR_ATTACK = builder.nextAccessor("biped/epicfight_awaken/cut_dp_airattack",
                accessor -> new AttackAnimation(0.05F, accessor, Armatures.BIPED,
                        new Phase(0.0F, 0.167F, 0.167F, 0.38F, 1.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolL, null))
                        .addProperty(AttackAnimationProperty.REMOVE_DELTA_MOVEMENT, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .newTimePair(0.0F, 0.3F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.3F, 10.0F));


        CUT_HOOK_SPIN_SLASH_AIR = builder.nextAccessor("biped/epicfight_awaken/cut_hook_spin_slash_air",
                accessor -> new AttackAnimation(0.15F, accessor, Armatures.BIPED,
                     new Phase(0.0F, 0.0F, 0.8F, 0.9F, 0.9F, 0.9F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                             .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F))
                             .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL),
                     new Phase(0.9F, 0.9F, 0.95F, 1.05F, 1.05F, 1.05F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                             .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F))
                             .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL),
                     new Phase(1.05F, 1.05F, 1.15F, 1.25F, 10.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                             .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F))
                             .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL))
                     .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                     .addProperty(ActionAnimationProperty.AFFECT_SPEED, true)
                     .addProperty(AttackAnimationProperty.STOP_MOVEMENT, false)
                     .newTimePair(0.0F, 1.45F)
                     .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                     .newTimePair(0.0F, 1.95F)
                     .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));

        DP_THROW_BLADE_AUTO_1 = builder.nextAccessor("biped/epicfight_awaken/throw_blade_auto_1",
                accessor -> new AttackAnimation(0.15F, 0.33F, 0.33F, 1.33F, 1.33F, ColliderPreset.FIST, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.15F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.15F, ReuseableEvents.THROW_TRIDENT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.23F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.23F, ReuseableEvents.THROW_TRIDENT_HAND_RIGHT, Side.SERVER))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .newTimePair(0.0F, 0.6F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.83F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_THROW_BLADE_AUTO_2 = builder.nextAccessor("biped/epicfight_awaken/throw_blade_auto_2",
                accessor -> new AttackAnimation(0.15F, 0.53F, 0.53F, 1.2F, 1.2F, ColliderPreset.FIST, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.43F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.43F, ReuseableEvents.THROW_TRIDENT_HAND_LEFT_LIGHTNING, Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.43F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.43F, ReuseableEvents.THROW_TRIDENT_HAND_RIGHT_LIGHTNING, Side.SERVER))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.5F)
                .newTimePair(0.0F, 0.76F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        THROW_HOOK_SLASH_AIR = builder.nextAccessor("biped/epicfight_awaken/throw_hook_slash_air",
                accessor -> new AttackAnimation(0.15F, accessor, Armatures.BIPED,
                        new Phase(0.0F, 0.0F, 0.33F, 0.46F, 0.46F, 0.46F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F)),
                        new Phase(0.46F, 0.46F, 0.47F, 0.6F, 10.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F)))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addProperty(ActionAnimationProperty.AFFECT_SPEED, true).addProperty(AttackAnimationProperty.STOP_MOVEMENT, false)
                        .newTimePair(0.0F, 0.85F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.35F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.THROW_TRIDENT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.THROW_TRIDENT_HAND_RIGHT, Side.SERVER)
                        ));
        DP_AUTO_1 = builder.nextAccessor("biped/epicfight_awaken/dp_auto_1",
                (animationaccessor) -> (new AttackAnimation(0.15F, animationaccessor, Armatures.BIPED, new Phase(0.0F, 0.33F, 0.33F, 0.43F, 0.53F, 0.53F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new Phase(0.53F, 0.53F, 0.53F, 0.63F, 0.8F, 0.8F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new Phase(0.8F, 0.8F, 0.8F, 0.93F, 1.5F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.16F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.43F, 10.0F));
        DP_AUTO_2 = builder.nextAccessor("biped/epicfight_awaken/dp_auto_2",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.23F, 0.23F, 0.38F, 0.38F, 0.38F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null),
                        new Phase(0.38F, 0.38F, 0.38F, 0.5F, 1.33F, Float.MAX_VALUE, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.56F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.83F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.38F, 10.0F));
        DP_AUTO_3 = builder.nextAccessor("biped/epicfight_awaken/dp_auto_3",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.3F, 0.3F, 0.4F, 0.5F, 0.5F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                        new Phase(0.5F, 0.5F, 0.5F, 0.67F, 1.43F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.7F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        CUT_LEFT_DP_AUTO_3 = builder.nextAccessor("biped/epicfight_awaken/cut_left_dp_auto_3",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.5F, 0.5F, 0.5F, 0.67F, 1.43F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.7F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_AUTO_4 = builder.nextAccessor("biped/epicfight_awaken/dp_auto_4",
                (animationaccessor) -> (new AttackAnimation(0.2F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.3F, 0.3F, 0.46F, 0.7F, 0.7F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new Phase(0.7F, 0.7F, 0.7F, 0.8F, 0.8F, 0.8F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F)),
                        new Phase(0.8F, 0.8F, 0.8F, 0.9F, 2.06F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_HEAVY_AUTO_1 = builder.nextAccessor("biped/epicfight_awaken/dp_heavy_auto_1",
                (animationaccessor) -> (new AttackAnimation(0.15F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.6F, 0.6F, 0.7F, 0.7F, 0.7F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.65F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)),
                        new Phase(0.7F, 0.7F, 0.7F, 0.8F, 1.16F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.65F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.8F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.9F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_HEAVY_AUTO_2 = builder.nextAccessor("biped/epicfight_awaken/dp_heavy_auto_2",
                (animationaccessor) -> (new AttackAnimation(0.1F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.4F, 0.4F, 0.56F, 0.56F, 0.56F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.85F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)),
                        new Phase(0.56F, 0.8F, 0.8F, 1.0F, 1.67F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.85F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 1.08F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.16F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_HEAVY_AUTO_3 = builder.nextAccessor("biped/epicfight_awaken/dp_heavy_auto_3",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.5F, 0.5F, 0.63F, 1.5F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.66F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.76F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_HEAVY_AUTO_4 = builder.nextAccessor("biped/epicfight_awaken/dp_heavy_auto_4",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.5F, 0.5F, 0.67F, 1.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.5F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(5.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(80.0F))
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.73F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.83F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_DASH = builder.nextAccessor("biped/epicfight_awaken/dp_dash",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.4F, 0.4F, 0.56F, 0.7F, 0.7F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F)),
                        new Phase(0.7F, 0.7F, 0.7F, 0.76F, 2.26F, Float.MAX_VALUE, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F))))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.REACH, 0.3F)
                        .newTimePair(0.0F, 0.8F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        CUT_LEFT_DP_DASH = builder.nextAccessor("biped/epicfight_awaken/cut_left_dp_dash",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.4F, 0.4F, 0.56F, 0.7F, 0.7F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.REACH, 0.3F)
                        .newTimePair(0.0F, 0.8F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)));
        HOOK_SLASH_GROUND = builder.nextAccessor("biped/epicfight_awaken/hook_slash_ground",
                (animationaccessor) -> (new AttackAnimation(0.15F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.0F, 0.1F, 0.2F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F)),
                        new Phase(0.2F, 0.0F, 0.2F, 0.3F, 0.3F, 0.3F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F)),
                        new Phase(0.3F, 0.0F, 0.3F, 0.36F, 0.36F, 0.36F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F)),
                        new Phase(0.36F, 0.0F, 0.46F, 0.56F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F))
                                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(ActionAnimationProperty.AFFECT_SPEED, true)
                        .addProperty(AttackAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AttackAnimationProperty.REACH, 2.0F)
                        .newTimePair(0.0F, 0.2F).addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.3F, Float.MAX_VALUE).addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 0.66F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.33F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, Float.MAX_VALUE)
                        .addState(EntityState.ATTACK_RESULT, (damagesource) -> damagesource.is(DamageTypeTags.IS_PROJECTILE) ? AttackResult.ResultType.MISSED : AttackResult.ResultType.SUCCESS));
        HOOK_SLASH_AIR = builder.nextAccessor("biped/epicfight_awaken/hook_slash_air",
                (animationaccessor) -> (new AttackAnimation(0.15F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.0F, 0.33F, 0.46F, 0.46F, 0.46F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F)),
                        new Phase(0.46F, 0.46F, 0.47F, 0.6F, 10.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F))))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(ActionAnimationProperty.AFFECT_SPEED, true)
                        .addProperty(AttackAnimationProperty.STOP_MOVEMENT, false)
                        .newTimePair(0.0F, 0.85F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.35F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, Float.MAX_VALUE).addState(EntityState.ATTACK_RESULT, (damagesource) -> !damagesource.is(DamageTypes.FALL) && !damagesource.is(DamageTypeTags.IS_PROJECTILE) ? AttackResult.ResultType.SUCCESS : AttackResult.ResultType.MISSED));
        DP_NIGHT_FALL = builder.nextAccessor("biped/epicfight_awaken/dp_night_fall",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.1F, 0.1F, 0.18F, 0.28F, 0.28F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F)),
                        new Phase(0.28F, 0.43F, 0.43F, 0.567F, 1.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F))
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AttackAnimationProperty.RESET_PLAYER_COMBO_COUNTER, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.567F))
                        .newTimePair(0.0F, 0.75F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        CUT_LEFT_DP_DUSK_REAVER_2 = builder.nextAccessor("biped/epicfight_awaken/cut_left_dp_dusk_reaver_2",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new Phase(0.0F, 0.33F, 0.23F, 0.33F, 2.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().rootJoint, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.4F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(80.0F))
                                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AttackAnimationProperty.RESET_PLAYER_COMBO_COUNTER, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addProperty(ActionAnimationProperty.COORD_SET_BEGIN,
                                (dynamicanimation, livingentitypatch, transformsheet) -> {
                                    if (!dynamicanimation.isLinkAnimation()) {
                                        transformsheet.readFrom(dynamicanimation.getCoord().copyAll().extendsZCoord(2.5F, 2, 4));
                                    } else {
                                        MoveCoordFunctions.RAW_COORD.set(dynamicanimation, livingentitypatch, transformsheet);
                                    }
                        })
                        .addProperty(ActionAnimationProperty.COORD_SET_TICK, null)
                        .newTimePair(0.0F, 0.67F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.33F, 10.0F));
        DP_HEAVY_AUTO_4_SPC = builder.nextAccessor("biped/epicfight_awaken/dp_heavy_auto_4_spc", 
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.0F, 0.06F, 0.06F, 0.16F, 0.16F, 0.16F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null)))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F)),
                        (new Phase(0.16F, 0.16F, 0.16F, 0.33F, 1.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null)))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.67F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.67F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_SHADOW_LUNGE_1 = builder.nextAccessor("biped/epicfight_awaken/dp_shadow_lunge_1",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.0F, 0.267F, 0.267F, 0.43F, 0.43F, 0.43F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)),
                        (new Phase(0.43F, 0.43F, 0.467F, 0.63F, 1.33F, Float.MAX_VALUE, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.REACH, 0.3F).newTimePair(0.0F, 0.63F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.63F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_SHADOW_LUNGE_2 = builder.nextAccessor("biped/epicfight_awaken/dp_shadow_lunge_2",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.0F, 0.267F, 0.267F, 0.367F, 0.43F, 0.43F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)),
                        (new Phase(0.43F, 0.43F, 0.43F, 0.6F, 1.33F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.REACH, 0.3F)
                        .newTimePair(0.0F, 1.1F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.1F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        DP_SHADOW_LUNGE_3 = builder.nextAccessor("biped/epicfight_awaken/dp_shadow_lunge_3",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.0F, 0.0F, 0.2F, 0.33F, 0.33F, 0.33F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.4F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.8F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)),
                        (new Phase(0.33F, 0.33F, 0.33F, 0.43F, 1.33F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.REACH, 0.3F)
                        .newTimePair(0.0F, 0.53F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        CUT_LEFT_DP_SHADOW_LUNGE_1 = builder.nextAccessor("biped/epicfight_awaken/cut_left_dp_shadow_lunge_1",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.0F, 0.267F, 0.267F, 0.43F, 0.43F, 0.43F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.REACH, 0.3F)
                        .newTimePair(0.0F, 0.63F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.63F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)));
        CUT_LEFT_DP_SHADOW_LUNGE_2 = builder.nextAccessor("biped/epicfight_awaken/cut_left_dp_shadow_lunge_2",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.43F, 0.43F, 0.43F, 0.6F, 1.33F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.8F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F)
                                ).addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.REACH, 0.3F)
                        .newTimePair(0.0F, 1.1F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.1F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        CUT_LEFT_DP_SHADOW_LUNGE_3 = builder.nextAccessor("biped/epicfight_awaken/cut_left_dp_shadow_lunge_3",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.33F, 0.33F, 0.33F, 0.43F, 1.33F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.REACH, 0.3F)
                        .newTimePair(0.0F, 0.53F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        CUT_LEFT_DP_PHANTOM_DANCE_END_1_ENHANCED = builder.nextAccessor("biped/epicfight_awaken/cut_left_dp_phantom_dance_end_1_enhanced",
                (animationaccessor) -> (new AttackAnimation(0.2F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.0F, 0.83F, 0.83F, 1.03F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null)))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(5.0F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL),
                        (new Phase(1.33F, 1.33F, 1.52F, 1.58F, 1.58F, 1.58F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F)),
                        (new Phase(1.58F, 1.58F, 1.58F, 1.64F, 1.64F, 1.64F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F)),
                        (new Phase(1.64F, 1.64F, 1.64F, 1.7F, 1.7F, 1.7F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F)),
                        (new Phase(1.7F, 1.7F, 1.7F, 1.8F, 2.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F))))
                        .addProperty(AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.03F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 1.8F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false).newTimePair(1.8F, 10.0F)
                        .newTimePair(0.0F, Float.MAX_VALUE).addState(EntityState.ATTACK_RESULT, (damagesource) -> damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) ? AttackResult.ResultType.SUCCESS : AttackResult.ResultType.MISSED));
        DP_PHANTOM_DANCE_END_2_ENHANCED = builder.nextAccessor("biped/epicfight_awaken/dp_phantom_dance_end_2_enhanced", 
                (animationaccessor) -> (new AttackAnimation(0.2F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.0F, 0.83F, 0.83F, 1.03F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null)))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(5.0F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL),
                        (new Phase(1.33F, 1.33F, 1.52F, 1.58F, 1.58F, 1.58F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F)),
                        (new Phase(1.58F, 1.58F, 1.58F, 1.64F, 1.64F, 1.64F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F)),
                        (new Phase(1.64F, 1.64F, 1.64F, 1.7F, 1.7F, 1.7F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG
                                ).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F)),
                        (new Phase(1.7F, 1.7F, 1.7F, 1.8F, 2.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, null))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35F))
                                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(100.0F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F))))
                        .addProperty(AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AttackAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.03F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 1.8F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, Float.MAX_VALUE).addState(EntityState.ATTACK_RESULT, (damagesource) -> {
                            return damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) ? AttackResult.ResultType.SUCCESS : AttackResult.ResultType.MISSED;
                        }));
        DP_FALLING_SHADOW = builder.nextAccessor("biped/epicfight_awaken/dp_falling_shadow",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new Phase(0.0F, 0.1F, 0.1F, 0.267F, 0.267F, 0.267F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null))
                                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F))
                                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL),
                        (new Phase(0.267F, 0.9F, 0.9F, 1.0F, 1.0F, 1.0F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null)))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F)),
                        (new Phase(1.0F, 1.0F, 1.0F, 1.1F, 1.1F, 1.1F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null)))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F)),
                        (new Phase(1.1F, 1.1F, 1.1F, 1.2F, 1.2F, 1.2F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null)))
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F)),
                        (new Phase(1.2F, 1.2F, 1.2F, 1.33F, 2.67F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null)))
                                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F))))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(DamageTypeTags.NO_IMPACT))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AttackAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.1F, 0.9F))
                        .addProperty(AttackAnimationProperty.MOVE_VERTICAL, true)
                        .newTimePair(0.0F, 1.8F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 2.0F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, Float.MAX_VALUE).addState(EntityState.ATTACK_RESULT, (damagesource) -> {
                            if (damagesource instanceof EpicFightDamageSource epicfightdamagesource) {
                                if (epicfightdamagesource.getStunType() != StunType.NEUTRALIZE) {
                                    epicfightdamagesource.setStunType(StunType.NONE);
                                }
                            }
                            return damagesource.is(DamageTypes.FALL) ? AttackResult.ResultType.MISSED : AttackResult.ResultType.SUCCESS;
        }));
        HOOK_AIR = builder.nextAccessor("biped/epicfight_awaken/hook_air",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.7F, 1.4F, 1.0F, null, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.STOP_MOVEMENT, false)
                        .newTimePair(0.0F, 0.25F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)));
        HOOK_GROUND = builder.nextAccessor("biped/epicfight_awaken/hook_ground",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.7F, 0.8F, 1.0F, null, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.25F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)));

        // Animation from Tactical Imbuements
        ZAP = builder.nextAccessor("biped/tactical_imbuements/zap",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
        );
        ZAP_LONG = builder.nextAccessor("biped/tactical_imbuements/zap_long",
                accessor -> (LongHitAnimation) new LongHitAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
        );

        // Animation from Sculk Steve
        PLAYER_HEROBRINE_POSSESSION = builder.nextAccessor("biped/sculk_steve/player_herobrine_possession",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        LEGENDARY_SWORD_IDLE = builder.nextAccessor("biped/sculk_steve/legendary_sword_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HEROBRINE_SACRIFICING = builder.nextAccessor("biped/sculk_steve/herobrine_sacrificing",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HEROBRINE_ASSISTANCE = builder.nextAccessor("biped/sculk_steve/herobrine_assistance",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HEROBRINE_STAGE_CHANGE = builder.nextAccessor("biped/sculk_steve/herobrine_stage_change",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        PORTAL_SUMMON = builder.nextAccessor("biped/sculk_steve/portal_summon",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));

        // Animation from Yonchi Chikito
        DIAMOND_ATTRACTOR_SKILL = builder.nextAccessor("biped/yonchi_chikito/diamond_attractor",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, p) -> {
                                    LivingEntity entity = livingEntityPatch.getOriginal();

                                    if (entity.level() instanceof ServerLevel serverLevel) {
                                        serverLevel.playSound(
                                                null,
                                                entity.getX(),
                                                entity.getY(),
                                                entity.getZ(),
                                                AnnoyingVillagersModSounds.DIAMOND_ATTRACTOR.get(),
                                                entity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE,
                                                1.0F,
                                                1.0F
                                        );

                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundDiamondAttractorFx(entity)
                                        );

                                        DiamondAttractorSwordItem.pullWeapons(entity);
                                    }
                                }, Side.SERVER)
                        ));
        GREATAXE_IDLE = builder.nextAccessor("biped/yonchi_chikito/greataxe_idle",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        GREATAXE_WALK = builder.nextAccessor("biped/yonchi_chikito/greataxe_walk",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        GREATAXE_SLASH = builder.nextAccessor("biped/yonchi_chikito/greataxe_slash",
                accessor -> (new BasicAttackAnimation(0.1F, 0.7F, 1.4F, 1.47F, null, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)));
        GREATAXE_OFFHAND_ATTACK = builder.nextAccessor("biped/yonchi_chikito/greataxe_offhand_attack",
                accessor -> (new BasicAttackAnimation(0.1F, 0.7F, 1.4F, 1.47F, null, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)));
        SLAM_FIRST = builder.nextAccessor("biped/yonchi_chikito/slamfirst",
                accessor -> (new BasicAttackAnimation(0.1F, 0.9F, 1.4F, 1.47F, WOMWeaponColliders.SOLAR, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT)
                        )));
        SLAM_SECOND = builder.nextAccessor("biped/yonchi_chikito/slamsecond",
                accessor -> (new BasicAttackAnimation(0.1F, 2.1F, 3.0F, 3.0F, WOMWeaponColliders.SOLAR, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(2.0F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT)
                        )));
        SLAM_THIRD = builder.nextAccessor("biped/yonchi_chikito/slamthird",
                accessor -> (new BasicAttackAnimation(0.1F, 2.1F, 3.0F, 3.0F, WOMWeaponColliders.SOLAR, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(2.425F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT)
                        )));
        SLAM_THIRD = builder.nextAccessor("biped/yonchi_chikito/slamthird",
                accessor -> new BasicAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(2.26F, 2.5F, 3.5F, 3.56F, 3.56F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.SOLAR),
                        new Phase(2.26F, 2.5F, 3.5F, 3.56F, 3.56F, humanoidArmature.get().toolR, WOMWeaponColliders.SOLAR))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(2.15F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(3.4F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT)
                        ));

        // Animation made by me
        TRIDENT_ATTACK = builder.nextAccessor("biped/pla/trident_attack",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(4.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.summonLightningAtGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER)
                        ));
        HEROBRINE_ANIMATE = builder.nextAccessor("biped/pla/herobrine_animate",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        HEROBRINE_HEALING = builder.nextAccessor("biped/pla/herobrine_healing",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        LOW_CLONE_ESCAPE = builder.nextAccessor("biped/pla/low_clone_escape",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        KNOCKED_ELITE = builder.nextAccessor("biped/pla/knocked_elite",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_1 = builder.nextAccessor("biped/pla/eating_elite_1",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_2 = builder.nextAccessor("biped/pla/eating_elite_2",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_3 = builder.nextAccessor("biped/pla/eating_elite_3",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_4 = builder.nextAccessor("biped/pla/eating_elite_4",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        SNAKE_BLADE = builder.nextAccessor("biped/pla/snake_blade",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    DemoniacVoltageReaverItem.process(livingEntityPatch.getOriginal().getMainHandItem(), livingEntityPatch.getOriginal());
                                    livingEntityPatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("SnakeAnimation", true);
                                }, Side.SERVER)
                        ));
        SNAKE_BLADE_GUARD = builder.nextAccessor("biped/pla/snake_blade_guard",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    DemoniacVoltageReaverItem.processGuard(livingEntityPatch.getOriginal().getMainHandItem(), livingEntityPatch.getOriginal());
                                    livingEntityPatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("SnakeAnimation", true);
                                }, Side.SERVER)
                        ));
        IDLE_BREAK = builder.nextAccessor("biped/pla/idle_break",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        PLACE_BLOCK = builder.nextAccessor("biped/pla/place_block",
                accessor -> new ActionAnimation(0.0F, accessor, humanoidArmature));
        BLACK_FIRE_SWORD_SKILL = builder.nextAccessor("biped/pla/black_fire_sword_skill",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.8F, (livingEntityPatch, self, p) -> BlackFireEntity.shootFromOwnerLook(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal()), Side.SERVER)
                        ));
        BLUE_FLAME_SWORD = builder.nextAccessor("biped/pla/blue_flame_sword",
                accessor -> new ActionAnimation(0.0F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false));
        DIAMOND_BLASTER_SKILL = builder.nextAccessor("biped/pla/diamond_blaster_skill",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)));
        EARTH_AXE_SHOOT = builder.nextAccessor("biped/pla/earth_axe_shoot",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null),
                        new Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, staticAnimation, object) -> {
                                    Vec3 bladePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                            Armatures.BIPED.get().toolR, 0.5F, 0.0F);
                                    if (bladePos == null) return;
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                                        BlockPos liftPos = EarthAxeItem.findLiftableBlockUnderPoint(serverLevel, bladePos, 6, 1);
                                        if (liftPos != null) {
                                            EarthAxeItem.liftBlockAt(serverLevel, liftPos, livingEntity);
                                        }
                                    }
                                }, Side.SERVER)
                        )
        );
        RED_AXE_ATTACK = builder.nextAccessor("biped/pla/red_axe_attack",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.2F, 2.5F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, (humanoidArmature.get()).rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.BYPASS_DODGE))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.35F, 0.9F))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
                        if (elapsedTime >= 0.9F && elapsedTime < 1.15F) {
                            float dpx = (float)(entitypatch.getOriginal()).getX();
                            float dpy = (float)(entitypatch.getOriginal()).getY();
                            float dpz = (float)(entitypatch.getOriginal()).getZ();

                            for(BlockState block = (entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                --dpy;
                            }

                            float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(entitypatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                            return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                        } else {
                            return speed;
                        }
                    }).addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.35F, reascer.wom.gameasset.ReuseableEvents.AIRBURST_JUMP, Side.CLIENT), AnimationEvent.InTimeEvent.create(1.15F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, Side.CLIENT)
                    }));

        // Animations cloned and registered from WOM
        CUT_ANTITHEUS_ASCENSION = builder.nextAccessor("biped/wom_clone/cut_antitheus_ascension",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level()
                                            instanceof ServerLevel
                                            && livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof BlueDemonChestplateItem) {
                                        BlueDemonChestplateItem.activateBuff(livingEntity.getItemBySlot(EquipmentSlot.CHEST));
                                    }
                                }, Side.SERVER)));
        TORMENT_BERSERK_WALK = builder.nextAccessor("biped/wom_clone/torment_berserk_walk", accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        TRIDENT_GUARD_HIT_1 = builder.nextAccessor("biped/wom_clone/trident_guard_hit1",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT)));
        TRIDENT_GUARD_HIT_2 = builder.nextAccessor("biped/wom_clone/trident_guard_hit2",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT)));
        ELECTRIC_FIELD = builder.nextAccessor("biped/wom_clone/electric_field",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.spawnDamageZones(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER)
                        ));
        EARTH_AXE = builder.nextAccessor("biped/wom_clone/earth_axe",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        EarthAxeItem.summonEarthWall(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER)
                        ));
        GLOWING_AGONY_GUARD = builder.nextAccessor("biped/wom_clone/glowing_agony_guard",
                accessor -> new StaticAnimation(0.05F, true, accessor, humanoidArmature)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.0F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.6F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.7F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, Side.CLIENT)));
        ENDER_AEGIS_BULL_CHARGE = builder.nextAccessor("biped/wom_clone/ender_aegis_bull_charge",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature, new Phase(0.0F, 0.2F, 0.25F, 0.29F, 0.29F,
                        humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.29F, 0.3F, 0.35F, 0.39F, 0.39F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.39F, 0.4F, 0.45F, 0.49F, 0.49F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.79F, 0.8F, 0.85F, 0.89F, 0.89F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new Phase(0.89F, 1.0F, 1.1F, 1.3F, Float.MAX_VALUE,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 2)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 3)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 3)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 3)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 4)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 4)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 4)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 4)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 4)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 4)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 5)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 5)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 5)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 5)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 5)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 5)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 6)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 6)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 6)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 6)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 6)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 6)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F), 7)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.0F), 7)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 7)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 7)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 7)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 7)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null));
        CLONE_ANTITHEUS_AGRESSION = builder.nextAccessor("biped/wom_clone/clone_antitheus_agression",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.35F, 0.59F, 0.59F, humanoidArmature.get().toolR, WOMWeaponColliders.ANTITHEUS_AGRESSION),
                        new Phase(0.59F, 0.6F, 0.65F, 0.85F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_AGRESSION_REAP)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_DOWN)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F), 1)
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(1.0F)), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get(), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_UP, 1)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(new AnimationEvent[] {
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        CLONE_ANTITHEUS_GUILLOTINE = builder.nextAccessor("biped/wom_clone/clone_antitheus_guillotine",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.75F, 0.79F, 0.79F, humanoidArmature.get().toolR, null),
                        new Phase(0.79F, 0.8F, 1.0F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE, 1)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 6)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F))
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        CLONE_ANTITHEUS_AUTO_1 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.35F, 0.55F, 0.69F, 0.69F, humanoidArmature.get().toolR, null),
                        new Phase(0.69F, 0.7F, 0.9F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.55F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.75F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false));
        CLONE_ANTITHEUS_AUTO_2 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.15F, 0.45F, 0.45F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        CLONE_ANTITHEUS_AUTO_3 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.5F, 0.5F, humanoidArmature.get().toolR, null),
                        new Phase(0.5F, 0.55F, 0.7F, 0.75F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE, 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 5)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F));
        CLONE_ANTITHEUS_AUTO_4 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.5F, 0.75F, 0.9F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        CLONE_ANTITHEUS_ASCENSION = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascension",
                accessor -> new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new Phase(0.65F, 1.75F, 2.05F, 2.8F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F));
        NULL_SKELETON_ANTITHEUS_ASCENSION = builder.nextAccessor("biped/wom_clone/null_skeleton_antitheus_ascension",
                accessor -> new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new Phase(0.65F, 1.75F, 2.05F, 2.8F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        NullSkeletonEntity nullSkeletonEntity = new NullSkeletonEntity(AnnoyingVillagersModEntities.NULL_SKELETON.get(), serverLevel);
                                        LivingEntity owner = livingEntityPatch.getOriginal();
                                        Vec3 forward = getVec3(owner);

                                        double dist = 2.0D;
                                        Vec3 spawnPos = owner.position().add(forward.scale(dist));

                                        nullSkeletonEntity.moveTo(
                                                spawnPos.x,
                                                spawnPos.y,
                                                spawnPos.z,
                                                owner.getYRot(),
                                                owner.getXRot()
                                        );
                                        if (owner instanceof Player player) {
                                            nullSkeletonEntity.setPlayer(player);
                                        } else if (owner instanceof NullEntity nullEntity) {
                                            nullSkeletonEntity.setNullEntity(nullEntity);
                                        }

                                        nullSkeletonEntity.finalizeSpawn(serverLevel,
                                                serverLevel.getCurrentDifficultyAt(nullSkeletonEntity.blockPosition()),
                                                MobSpawnType.MOB_SUMMONED,
                                                null, null
                                        );
                                        serverLevel.addFreshEntity(nullSkeletonEntity);
                                        if (owner instanceof NullEntity nullEntity) {
                                            nullEntity.claimWitherSkeletonSlot(nullSkeletonEntity);
                                        }
                                        LivingEntityPatch<?> nullSkeletonPatch = EpicFightCapabilities.getEntityPatch(nullSkeletonEntity, LivingEntityPatch.class);
                                        if (nullSkeletonPatch != null) {
                                            nullSkeletonPatch.playAnimationSynchronized(CLONE_ANTITHEUS_LAPSE, 0.0F);
                                        }
                                    }
                                }, Side.SERVER)
                        }));
        CLONE_ANTITHEUS_LAPSE = builder.nextAccessor("biped/wom_clone/clone_antitheus_lapse",
                accessor -> new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.65F, 0.75F, 0.8F, 0.8F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new Phase(0.8F, 1.3F, 1.4F, 1.45F, 1.45F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new Phase(1.45F, 1.75F, 1.85F, 2.3F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_HURT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_HURT, 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 2)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F));
        CLONE_ANTITHEUS_ASCENDED_DEATHFALL = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_deathfall",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.5F, 0.55F, 0.75F, WOMWeaponColliders.ANTITHEUS_ASCENDED_DEATHFALL, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.75F))
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), Side.SERVER), AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            for (int i = 0; i < 24; ++i) {
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) (new Random().nextFloat() - 0.5F), livingEntityPatch.getOriginal().getY() + 2.200000047683716D, livingEntityPatch.getOriginal().getZ() + (double) (new Random().nextFloat() - 0.5F), (new Random().nextFloat() - 0.5F) * 0.05F, -((double) new Random().nextFloat() * (livingEntityPatch.getOriginal().getY() - (double) f1) * 0.4000000059604645D), (new Random().nextFloat() - 0.5F) * 0.05F);
                            }

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), Side.SERVER), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.NEUTRAL, 0.7F, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.BLUNT_HIT_HARD.get(), SoundSource.NEUTRAL, 0.7F, 0.7F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            Vec3 vec3 = new Vec3(0.0D, (double) (f1 - 2.0F) - livingEntityPatch.getOriginal().getY(), 0.0D);

                            livingEntityPatch.getOriginal().move(MoverType.SELF, vec3);
                            byte b0 = 80;
                            double d0 = 0.6D;
                            double d1 = 0.01D;

                            for (int i = 0; i < b0; ++i) {
                                double d2 = 6.283185307179586D * new Random().nextDouble();
                                double d3 = (new Random().nextDouble() - 0.5D) * 3.141592653589793D * d1 / d0;
                                double d4 = d0 * Math.cos(d3) * Math.cos(d2);
                                double d5 = d0 * Math.cos(d3) * Math.sin(d2);
                                double d6 = d0 * Math.sin(d3);
                                float f3 = new Random().nextFloat() + 0.4F;
                                Vec3f vec3f = new Vec3f((float) d4 * f3, (float) d5 * f3, (float) d6 * f3);
                                OpenMatrix4f openmatrix4f = new OpenMatrix4f().rotate((float) Math.toRadians(90.0F), new Vec3f(1.0F, 0.0F, 0.0F));

                                OpenMatrix4f.transform3v(openmatrix4f, vec3f, vec3f);
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) vec3f.x, (float) (int) livingEntityPatch.getOriginal().getY() + vec3f.y + 0.02F, livingEntityPatch.getOriginal().getZ() + (double) vec3f.z, vec3f.x, vec3f.y, vec3f.z);
                            }

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), Side.SERVER)})
        );
        CLONE_ANTITHEUS_ASCENDED_BLINK = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_blink", accessor -> new BasicMultipleAttackAnimation(0.05F, 0.3F, 0.4F, 0.4F, WOMWeaponColliders.ANTITHEUS_ASCENDED_BLINK, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.0F)
                .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addEvents(AnimationEvent.InTimeEvent.create(0.05F, (entitypatch, self, params) -> {
                    entitypatch.getOriginal().level().playSound(null, entitypatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                    entitypatch.getOriginal().level().playSound(null, entitypatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                }, Side.CLIENT)));
        CLONE_ANTITHEUS_ASCENDED_BLACKHOLE = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_blackhole", accessor -> new BasicMultipleAttackAnimation(0.05F, 1.45F, 1.5F, 1.7F, WOMWeaponColliders.PLUNDER_PERDITION, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(30.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.7F))
                .addEvents(AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), WOMSounds.ANTITHEUS_BLACKKHOLE_CHARGEUP.get(), SoundSource.PLAYERS, 2.0F, 1.0F), Side.SERVER), AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> {
                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().toolL);
                    transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                    OpenMatrix4f.mul(new OpenMatrix4f().rotate((float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                    int n = 70;
                    double r = 5.0F;

                    for (int i = 0; i < n; ++i) {
                        double theta = Math.PI * 2D * new Random().nextDouble();
                        double phi = Math.acos((double) 2.0F * new Random().nextDouble() - (double) 1.0F);
                        double x = r * Math.sin(phi) * Math.cos(theta);
                        double y = r * Math.sin(phi) * Math.sin(theta);
                        double z = r * Math.cos(phi);
                        livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX() + x, (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY() + y, (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ() + z, (float) (-x * (double) 0.15F), (float) (-y * (double) 0.15F), (float) (-z * (double) 0.15F));
                    }

                }, Side.CLIENT), AnimationEvent.InTimeEvent.create(1.05F, (livingEntityPatch, self, params) -> {
                    livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                    livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                }, Side.CLIENT), AnimationEvent.InTimeEvent.create(1.45F, (livingEntityPatch, self, params) -> {
                    if (!(livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel)) return;

                    livingEntityPatch.getOriginal().level().playSound(
                            null,
                            livingEntityPatch.getOriginal(),
                            SoundEvents.WITHER_BREAK_BLOCK,
                            SoundSource.PLAYERS,
                            1.0F, 0.5F
                    );

                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature()
                            .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().handR);

                    OpenMatrix4f CORRECTION = new OpenMatrix4f()
                            .rotate((float) -Math.toRadians(livingEntityPatch.getOriginal().yRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                    CORRECTION.translate(new Vec3f(0.0F, 0.0F, -3.5F));
                    OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);

                    serverLevel.sendParticles(WOMParticles.ANTITHEUS_BLACKHOLE_START.get(), (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY(), (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ(), 1, 0.0F, 0.0F, 0.0F, 0.0F);
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY(), (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ(), 48, 0.0F, 0.0F, 0.0F, 0.5F);
                }, Side.SERVER), AnimationEvent.InTimeEvent.create(1.45F, (livingEntityPatch, self, params) -> {
                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().handR);
                    OpenMatrix4f CORRECTION = new OpenMatrix4f().rotate((float) -Math.toRadians(livingEntityPatch.getOriginal().yRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                    CORRECTION.translate(new Vec3f(0.0F, 0.0F, -3.5F));
                    OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);
                    Level level = livingEntityPatch.getOriginal().level();
                    Vec3 FractureCenter = new Vec3((double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY() - (double) 2.0F, (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ());
                    LevelUtil.circleSlamFracture(livingEntityPatch.getOriginal(), level, FractureCenter, 4.0F, true, true);
                }, Side.CLIENT)));
        ENDER_AEGIS_MOONLESS_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_aegis_moonless_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.25F, 0.45F, 0.5F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SMALL.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        ENDER_AEGIS_MOONLESS_AUTO_2 = builder.nextAccessor("biped/wom_clone/ender_aegis_moonless_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.8F, 1.0F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().toolR, WOMWeaponColliders.MOONLESS_BYPASS))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(0.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.SHARPCUT_ANGLED_DOWN_LEFT_SLASH)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));

        YELLOW_SOLAR_AUTO_2 = builder.nextAccessor("biped/wom_clone/yellow_solar_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.65F, 0.8F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.SOLAR_HIT_UP)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.8F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, Side.CLIENT)
                        ));
        YELLOW_NAPOLEON_AUTO_3 = builder.nextAccessor("biped/wom_clone/yellow_napoleon_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new Phase(0.39F, 0.5F, 0.7F, 0.74F, 0.74F, humanoidArmature.get().toolR, null),
                        new Phase(0.74F, 0.75F, 0.85F, 1.19F, 1.19F, humanoidArmature.get().toolR, null),
                        new Phase(1.19F, 1.2F, 2.2F, 2.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.1F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(1.2F, 2.25F)).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true).newTimePair(0.0F, 0.85F).addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.4F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        YELLOW_NAPOLEON_AUSTERLITZ_SHOOT = builder.nextAccessor("biped/wom_clone/yellow_napoleon_austerlitz_shoot",
                accessor -> new SpecialAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.4F, 0.41F, 0.41F, humanoidArmature.get().toolR, null),
                        new Phase(0.41F, 0.85F, 1.05F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.0F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.1F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_DASH, 0.0F);
                                    }
                                }, Side.SERVER)
                        })
        );
        ENDER_AEGIS_NAPOLEON_RELOAD_1 = builder.nextAccessor("biped/wom_clone/ender_aegis_napoleon_reload_1",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().toolR, null),
                        new Phase(0.3F, 0.35F, 0.45F, 0.5F, 0.5F, humanoidArmature.get().toolR, null),
                        new Phase(0.5F, 0.55F, 0.65F, 0.7F, 0.7F, humanoidArmature.get().toolR, null),
                        new Phase(0.7F, 0.75F, 0.95F, 1.0F, 1.0F, humanoidArmature.get().toolR, null),
                        new Phase(1.0F, 1.05F, 1.2F, 1.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 4)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 4)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));

        ENDER_GLAIVE_NAPOLEON_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.1F, 0.45F, 0.79F, 0.79F, humanoidArmature.get().toolR, null),
                        new Phase(0.79F, 0.8F, 1.0F, 1.05F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        ENDER_GLAIVE_NAPOLEON_AUTO_2 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.6F, 0.64F, 0.64F, humanoidArmature.get().toolR, null),
                        new Phase(0.64F, 0.65F, 0.95F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        ENDER_GLAIVE_NAPOLEON_AUTO_4 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.6F, 1.0F, 1.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.2F))
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        ENDER_GLAIVE_NAPOLEON_AUSTERLITZ = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_austerlitz",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.05F, 0.1F, 0.14F, 0.14F, humanoidArmature.get().toolR, null),
                        new Phase(0.14F, 0.15F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().toolR, null),
                        new Phase(0.35F, 0.45F, 0.55F, 0.59F, 0.59F, humanoidArmature.get().toolR, null),
                        new Phase(0.59F, 0.6F, 0.8F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        DEMONIAC_RUINE_AUTO_1 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, 0.2F, 0.55F, 0.55F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.75F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        DEMONIAC_RUINE_AUTO_2 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.55F, 0.59F, 0.59F, humanoidArmature.get().toolR, null),
                        new Phase(0.59F, 0.6F, 0.85F, 0.95F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.95F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        DEMONIAC_RUINE_AUTO_4 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, humanoidArmature.get().toolR, WOMWeaponColliders.RUINE_COMET),
                        new Phase(0.65F, 0.8F, 1.05F, 1.45F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.4F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(1.0F)), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NEUTRALIZE, 1)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        DEMONIAC_RUINE_COMET = builder.nextAccessor("biped/wom_clone/demoniac_ruine_comet",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.55F, 0.75F, WOMWeaponColliders.RUINE_COMET, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(0.5F)))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 20)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F)).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.35F && elapsedTime < 0.45F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for (BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) Math.max(Math.abs(livingEntityPatch.getOriginal().getY() - (double) dpy) - (double) 1.0F, 0.0F);
                                LivingEntity livingentity = livingEntityPatch.getOriginal();
                                Vec3f direction = new Vec3f(2.5F, -0.25F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f().rotate((float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                AABB box = AABB.ofSize(livingEntityPatch.getOriginal().getPosition(1.0F), 3.0F, 3.0F, 3.0F);
                                List<Entity> list = livingEntityPatch.getOriginal().level().getEntities(livingEntityPatch.getOriginal(), box);
                                if (distanceToGround > 0.5F && list.isEmpty()) {
                                    livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                    return 0.05F;
                                } else {
                                    return speed;
                                }
                            } else {
                                return speed;
                            }
                        })
                        .addEvents(AnimationEvent.InTimeEvent.create(0.25F, reascer.wom.gameasset.ReuseableEvents.RUINE_COMET_AIRBURST, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.RUINE_COMET_GROUNDTHRUST, Side.CLIENT))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        DEMONIAC_TORMENT_CHARGED_ATTACK_2 = builder.nextAccessor("biped/wom_clone/demoniac_torment_charged_attack_2",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.15F, 0.65F))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));

        AGONY_GUARD_HIT_1 = builder.nextAccessor("biped/wom_clone/agony_guard_hit1",
                accessor -> new ActionAnimation(0.05F, 0.5F, accessor, humanoidArmature)
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, Side.CLIENT)
                                }));
        ENDER_GLAIVE_NAPOLEON_SHOOT_3 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_shoot_3",
                accessor -> new SpecialAttackAnimation(0.2F, accessor, humanoidArmature, new Phase(0.0F, 0.3F, 0.4F, 0.44F, 0.44F, humanoidArmature.get().toolR, null),
                        new Phase(0.44F, 0.45F, 0.5F, 0.95F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                            livingEntityPatch.getOriginal(),
                                            new Vec3f(0.0F, 0.0F, 0.0F),
                                            Armatures.BIPED.get().toolR,
                                            4.3F,
                                            2.3F
                                    );
                                    if (tipPos != null) {
                                        BlockPos mutePos = BlockPos.containing(tipPos);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundMuteExplosionAtPos(mutePos, 4)
                                        );
                                        livingEntityPatch.getOriginal().level().explode(livingEntityPatch.getOriginal(), tipPos.x, tipPos.y, tipPos.z,
                                                2.0F, true, Level.ExplosionInteraction.TNT);
                                        Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                        Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                        );
                                        if (explosionPos != null) {
                                            livingEntityPatch.getOriginal().level().playSound(null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), AnnoyingVillagersModSounds.ENDER_SHOT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        }
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER))
        );
        ENDER_GLAIVE_AGONY_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_glaive_agony_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().toolR, null),
                        new Phase(0.3F, 0.55F, 0.65F, 0.7F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.29F), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(ActionAnimationProperty.COORD_SET_TICK, (self, livingEntityPatch, transformSheet) -> {
                            LivingEntity attackTarget = livingEntityPatch.getTarget();
                            if (!(Boolean) self.getRealAnimation().get().getProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && attackTarget != null) {
                                TransformSheet transform = self.getTransfroms().get("Root").copyAll();
                                Keyframe[] keyframes = transform.getKeyframes();
                                int startFrame = 0;
                                int endFrame = transform.getKeyframes().length - 1;
                                Vec3f keyLast = keyframes[endFrame].transform().translation();
                                Vec3 pos = livingEntityPatch.getOriginal().getEyePosition();
                                Vec3 targetPos = attackTarget.position().add(attackTarget.getDeltaMovement().scale(8.0F));
                                float horizontalDistance = Math.max((float) targetPos.subtract(pos).horizontalDistance() - (attackTarget.getBbWidth() + livingEntityPatch.getOriginal().getBbWidth()), 0.0F);
                                Vec3f worldPosition = new Vec3f(keyLast.x, 0.0F, -horizontalDistance);
                                float scale = Math.min(worldPosition.length() / keyLast.length(), 2.0F);

                                for (int i = startFrame; i <= endFrame; ++i) {
                                    Vec3f translation = keyframes[i].transform().translation();
                                    translation.z *= scale;
                                }

                                transformSheet.readFrom(transform);
                            } else {
                                transformSheet.readFrom(self.getTransfroms().get("Root"));
                            }

                        }).addEvents(
                                AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, p) -> {
                                    Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                            livingEntityPatch.getOriginal(),
                                            new Vec3f(0.0F, 0.0F, 0.0F),
                                            Armatures.BIPED.get().toolR,
                                            4.3F,
                                            2.3F
                                    );
                                    if (tipPos != null) {
                                        BlockPos mutePos = BlockPos.containing(tipPos);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundMuteExplosionAtPos(mutePos, 4)
                                        );
                                        livingEntityPatch.getOriginal().level().explode(livingEntityPatch.getOriginal(), tipPos.x, tipPos.y, tipPos.z,
                                                2.0F, true, Level.ExplosionInteraction.TNT);
                                        Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                        Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                        );
                                        if (explosionPos != null) {
                                            livingEntityPatch.getOriginal().level().playSound(null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), AnnoyingVillagersModSounds.ENDER_SHOT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        }
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER))
        );
        ENDER_GLAIVE_NAPOLEON_AUTO_3 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new Phase(0.39F, 0.5F, 0.7F, 0.74F, 0.74F, humanoidArmature.get().toolR, null),
                        new Phase(0.74F, 0.75F, 0.85F, 1.19F, 1.19F, humanoidArmature.get().toolR, null),
                        new Phase(1.19F, 1.2F, 2.2F, 2.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.1F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 3)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(1.2F, 2.25F)).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true).newTimePair(0.0F, 0.85F).addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));
        ENDER_GLAIVE_NAPOLEON_WATERLOW = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_waterlow",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new Phase(0.39F, 0.4F, 0.6F, 0.64F, 0.64F, humanoidArmature.get().toolR, null),
                        new Phase(0.64F, 0.65F, 1.0F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL, 2)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addProperty(AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.4F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        }));

        CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK = builder.nextAccessor("biped/wom_clone/clone_enderblaster_twohand_dash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.3F, 0.44F, 0.45F, 0.45F, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE),
                        new Phase(0.45F, 0.5F, 0.6F, 0.65F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.TORMENT_AIRSLAM))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(5.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(5.0F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.GROUND_BODYSCRAPE_LAND, Side.CLIENT)
                        }));
        YELLOW_TORMENT_CHARGED_ATTACK_3 = builder.nextAccessor("biped/wom_clone/yellow_torment_charged_attack_3",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.2F, 1.5F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.BYPASS_DODGE))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.35F, 0.9F))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.9F && elapsedTime < 1.15F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for (BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) Math.max(Math.abs(livingEntityPatch.getOriginal().getY() - (double) dpy) - (double) 1.0F, 0.0F);
                                return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                            } else {
                                return speed;
                            }
                        }).addEvents(
                                AnimationEvent.InTimeEvent.create(0.35F, reascer.wom.gameasset.ReuseableEvents.AIRBURST_JUMP, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.15F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.15F, ReuseableEvents.SHOCK_WAVE, Side.SERVER)
                        )
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, params) -> {
                            LivingEntity target = livingEntityPatch.getOriginal().getLastHurtMob();
                            if (target != null && target.distanceTo(livingEntityPatch.getOriginal()) < 30.0F) {
                                double offset = 4.0F;
                                double referenceX = target.getX();
                                double referenceY = target.getY();
                                double referenceZ = target.getZ();
                                float referenceYaw = livingEntityPatch.getOriginal().yHeadRot;
                                double newX = referenceX + offset * Math.sin(Math.toRadians(referenceYaw));
                                double newZ = referenceZ - offset * Math.cos(Math.toRadians(referenceYaw));
                                BlockPos blockPos = new BlockPos((int) newX, (int) referenceY, (int) newZ);
                                BlockState block = livingEntityPatch.getOriginal().level().getBlockState(blockPos);
                                if (!block.isCollisionShapeFullBlock(livingEntityPatch.getOriginal().level(), blockPos)) {
                                    livingEntityPatch.getOriginal().teleportTo(newX, referenceY, newZ);
                                } else {
                                    livingEntityPatch.getOriginal().teleportTo(referenceX, referenceY, referenceZ);
                                }

                                livingEntityPatch.getOriginal().setDeltaMovement(target.getDeltaMovement());
                            }

                            ((ServerLevel) livingEntityPatch.getOriginal().level())
                                    .sendParticles(ParticleTypes.REVERSE_PORTAL,
                                            livingEntityPatch.getOriginal().getX(),
                                            livingEntityPatch.getOriginal().getY() + (double) 1.0F,
                                            livingEntityPatch.getOriginal().getZ(),
                                            60, 0.05, 0.05, 0.05, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(
                                    null,
                                    livingEntityPatch.getOriginal().xo,
                                    livingEntityPatch.getOriginal().yo + (double) 1.0F,
                                    livingEntityPatch.getOriginal().zo,
                                    SoundEvents.ENDERMAN_TELEPORT,
                                    livingEntityPatch.getOriginal().getSoundSource(),
                                    2.0F, 1.0F - (new Random().nextFloat() - 0.5F) * 0.2F
                            );

                        }, Side.SERVER), AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> {
                            LivingEntity entity = livingEntityPatch.getOriginal();
                            livingEntityPatch.getOriginal().level()
                                    .addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(),
                                            entity.getX(), entity.getY(), entity.getZ(),
                                            Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F
                                    );
                        }, Side.CLIENT)}));
        CLONE_ENDERBLASTER_ONEHAND_DASH = builder.nextAccessor("biped/wom_clone/clone_enderblaster_onehand_dash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.15F, 0.2F, 0.45F, 0.45F, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE),
                        new Phase(0.45F, 0.45F, 0.75F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.COORD_SET_TICK,
                                (self, entitypatch, transformSheet) -> {
                                    LivingEntity attackTarget = entitypatch.getTarget();
                                    if (!(Boolean) self.getRealAnimation().get().getProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && attackTarget != null) {
                                        TransformSheet transform = self.getTransfroms().get("Root").copyAll();
                                        Keyframe[] keyframes = transform.getKeyframes();
                                        int startFrame = 0;
                                        int endFrame = transform.getKeyframes().length - 1;
                                        Vec3f keyLast = keyframes[endFrame].transform().translation();
                                        Vec3 pos = entitypatch.getOriginal().getEyePosition();
                                        Vec3 targetpos = attackTarget.position().add(attackTarget.getDeltaMovement().scale(1.5F));
                                        float horizontalDistance = Math.max((float) targetpos.subtract(pos).horizontalDistance() - (attackTarget.getBbWidth() + entitypatch.getOriginal().getBbWidth()), 0.0F);
                                        Vec3f worldPosition = new Vec3f(keyLast.x, 0.0F, -horizontalDistance);
                                        float scale = Math.min(worldPosition.length() / keyLast.length(), 1.5F);

                                        for (int i = startFrame; i <= endFrame; ++i) {
                                            Vec3f translation = keyframes[i].transform().translation();
                                            translation.z *= scale;
                                        }

                                        transformSheet.readFrom(transform);
                                    } else if (transformSheet != null) {
                                        transformSheet.readFrom(self.getTransfroms().get("Root"));
                                    }
                                }));
        SLEDGEHAMMER_TORMENT_BERSERK_AUTO_1 = builder.nextAccessor("biped/wom_clone/sledgehammer_torment_berserk_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.4F, 0.15F, 0.5F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(9.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.OVERBLOOD_HIT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.35F, ReuseableEvents.SLEDGEHAMMER_SHOOT, Side.SERVER)
                        )
        );
        SLEDGEHAMMER_TORMENT_BERSERK_AUTO_2 = builder.nextAccessor("biped/wom_clone/sledgehammer_torment_berserk_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.4F, 0.15F, 0.5F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(9.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.OVERBLOOD_HIT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.31F, ReuseableEvents.SLEDGEHAMMER_SHOOT, Side.SERVER)
                        )
        );
        SLEDGEHAMMER_SOLAR_AUTO_3 = builder.nextAccessor("biped/wom_clone/sledgehammer_solar_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.4F, 0.75F, 0.85F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.SLEDGEHAMMER_SHOOT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(IDLE_BREAK, 0.0F);
                                    }
                                }, Side.SERVER)
                        )
        );
        CLONE_ANTITHEUS_SHOOT = builder.nextAccessor("biped/wom_clone/clone_antitheus_shoot",
                accessor -> new AntitheusShootAttackAnimation(0.05F, 0.05F, 0.1F, 0.5F, WOMWeaponColliders.ANTITHEUS_SHOOT, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(1.0F)))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_SHOOT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, SoundEvents.WITHER_BREAK_BLOCK)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false));
        CLONE_ANTITHEUS_ASCENDED_IDLE = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_idle",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature));
        NULL_ANTITHEUS_ASCENDED_AUTO_1 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.3F, 0.4F, 0.4F, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F), Side.CLIENT)
                        }));
        NULL_ANTITHEUS_ASCENDED_AUTO_2 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.3F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new Phase(0.5F, 0.6F, 0.7F, 0.7F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F), Side.CLIENT)}));
        NULL_ANTITHEUS_ASCENDED_AUTO_3 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.2F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new Phase(0.35F, 0.4F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new Phase(0.55F, 0.7F, 0.8F, 0.85F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 2)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 2)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 0)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F), Side.CLIENT)}));
        HEROBRINE_MOB_ENDERSTEP_OBSCURIS = builder.nextAccessor("biped/wom_clone/herobrine_mob_ender_obscuris",
                accessor -> new DodgeAnimation(0.05F, accessor, 0.6F, 1.65F, humanoidArmature)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.15F))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.ENDER_STEP, Side.BOTH),
                                AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, params) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        Entity entity = livingEntityPatch.getOriginal();
                                        if (entity instanceof HerobrineMob herobrineMob && herobrineMob.getTarget() != null) {
                                            LivingEntity target = herobrineMob.getTarget();
                                            if (target != null) {
                                                double offset = 2.0D;

                                                double referenceX = target.getX();
                                                double referenceY = target.getY();
                                                double referenceZ = target.getZ();
                                                float referenceYaw = target.yHeadRot;

                                                double sin = Math.sin(Math.toRadians(referenceYaw));
                                                double cos = Math.cos(Math.toRadians(referenceYaw));
                                                double newX = referenceX + offset * sin;
                                                double newZ = referenceZ - offset * cos;
                                                double newY = referenceY;

                                                ServerLevel serverLevel = (ServerLevel) entity.level();
                                                int baseY = target.blockPosition().getY();
                                                int minY = serverLevel.getMinBuildHeight() + 1;
                                                int maxY = serverLevel.getMaxBuildHeight() - 2;
                                                baseY = Mth.clamp(baseY, minY, maxY);

                                                BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
                                                boolean found = false;
                                                for (int tries = 0; tries < 10 && offset > 0.25D; tries++) {
                                                    newX = referenceX + offset * sin;
                                                    newZ = referenceZ - offset * cos;

                                                    mpos.set(Mth.floor(newX), baseY, Mth.floor(newZ));
                                                    if (!serverLevel.isLoaded(mpos)) {
                                                        offset -= 0.25D;
                                                        continue;
                                                    }
                                                    int scan = 0;
                                                    while (scan++ < 12 && mpos.getY() > minY) {
                                                        BlockPos belowPos = mpos.below();
                                                        BlockState below = serverLevel.getBlockState(belowPos);

                                                        if (below.isFaceSturdy(serverLevel, belowPos, net.minecraft.core.Direction.UP) && !below.is(Blocks.VOID_AIR)) {
                                                            break;
                                                        }
                                                        mpos.move(0, -1, 0);
                                                    }

                                                    BlockPos belowPos = mpos.below();
                                                    BlockState below = serverLevel.getBlockState(belowPos);
                                                    BlockState feet  = serverLevel.getBlockState(mpos);
                                                    BlockState head  = serverLevel.getBlockState(mpos.above());

                                                    boolean solidBelow = below.isFaceSturdy(serverLevel, belowPos, net.minecraft.core.Direction.UP) && !below.is(Blocks.VOID_AIR);
                                                    boolean freeFeet = feet.isAir() || feet.getBlock() instanceof BushBlock;
                                                    boolean freeHead = head.isAir() || head.getBlock() instanceof BushBlock;

                                                    if (solidBelow && freeFeet && freeHead) {
                                                        newX = mpos.getX() + 0.5D;
                                                        newY = mpos.getY();
                                                        newZ = mpos.getZ() + 0.5D;
                                                        if (serverLevel.noCollision(entity,
                                                                entity.getBoundingBox().move(newX - entity.getX(), newY - entity.getY(), newZ - entity.getZ()))) {
                                                            found = true;
                                                            break;
                                                        }
                                                    }

                                                    offset -= 0.25D;
                                                }

                                                if (found) {
                                                    entity.teleportTo(newX, newY, newZ);
                                                    entity.setDeltaMovement(target.getDeltaMovement());
                                                    entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
                                                }
                                            }
                                        }

                                        ((ServerLevel) entity.level()).sendParticles(ParticleTypes.REVERSE_PORTAL, entity.getX(), entity.getY() + (double) 1.0F, entity.getZ(), 60, 0.05, 0.05, 0.05, 0.5F);
                                        entity.level().playSound(null, entity.xo, entity.yo + (double) 1.0F, entity.zo, SoundEvents.ENDERMAN_TELEPORT, entity.getSoundSource(), 2.0F, 1.0F - (new Random().nextFloat() - 0.5F) * 0.2F);
                                    }
                                }, Side.BOTH)
                        }));
        OBSIDIAN_ANTITHEUS_ASCENDED_DEATHFALL = builder.nextAccessor("biped/wom_clone/obsidian_antitheus_ascended_deathfall",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.5F, 0.55F, 0.75F, WOMWeaponColliders.ANTITHEUS_ASCENDED_DEATHFALL, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.8F)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.75F))
                        .addEvents(AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            Vec3 position = new Vec3(0.0F, 3.0F, 0.0F);
                            livingEntityPatch.getOriginal().move(MoverType.SELF, position);
                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), Side.SERVER), AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            for (int i = 0; i < 24; ++i) {
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) (new Random().nextFloat() - 0.5F), livingEntityPatch.getOriginal().getY() + 2.200000047683716D, livingEntityPatch.getOriginal().getZ() + (double) (new Random().nextFloat() - 0.5F), (new Random().nextFloat() - 0.5F) * 0.05F, -((double) new Random().nextFloat() * (livingEntityPatch.getOriginal().getY() - (double) f1) * 0.4000000059604645D), (new Random().nextFloat() - 0.5F) * 0.05F);
                            }

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), Side.SERVER), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.NEUTRAL, 0.7F, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.BLUNT_HIT_HARD.get(), SoundSource.NEUTRAL, 0.7F, 0.7F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            Vec3 vec3 = new Vec3(0.0D, (double) (f1 - 2.0F) - livingEntityPatch.getOriginal().getY(), 0.0D);

                            livingEntityPatch.getOriginal().move(MoverType.SELF, vec3);
                            byte b0 = 80;
                            double d0 = 0.6D;
                            double d1 = 0.01D;

                            for (int i = 0; i < b0; ++i) {
                                double d2 = 6.283185307179586D * new Random().nextDouble();
                                double d3 = (new Random().nextDouble() - 0.5D) * 3.141592653589793D * d1 / d0;
                                double d4 = d0 * Math.cos(d3) * Math.cos(d2);
                                double d5 = d0 * Math.cos(d3) * Math.sin(d2);
                                double d6 = d0 * Math.sin(d3);
                                float f3 = new Random().nextFloat() + 0.4F;
                                Vec3f vec3f = new Vec3f((float) d4 * f3, (float) d5 * f3, (float) d6 * f3);
                                OpenMatrix4f openmatrix4f = new OpenMatrix4f().rotate((float) Math.toRadians(90.0F), new Vec3f(1.0F, 0.0F, 0.0F));

                                OpenMatrix4f.transform3v(openmatrix4f, vec3f, vec3f);
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) vec3f.x, (float) (int) livingEntityPatch.getOriginal().getY() + vec3f.y + 0.02F, livingEntityPatch.getOriginal().getZ() + (double) vec3f.z, vec3f.x, vec3f.y, vec3f.z);
                            }

                        }, Side.CLIENT), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), Side.SERVER))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.SUMMON_OBSIDIAN_CROSS, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6F, ReuseableEvents.SUMMON_OBSIDIAN_CROSS_FIX_DELAY_SHADOW_HEROBRINE, Side.SERVER)
                        )
        );
        OLD_MOONLESS_RUN = builder.nextAccessor("biped/wom_clone/old_moonless_run",
                accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        TRIDENT_TWO_HAND_RUN = builder.nextAccessor("biped/wom_clone/trident_two_hand_run",
                accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        OBSIDIAN_STRONG_PUNCH = builder.nextAccessor("biped/wom_clone/obsidian_strong_punch",
                accessor -> new BasicMultipleAttackAnimation(0.3F, 0.1F, 0.15F, 0.35F, WOMWeaponColliders.PUNCH, humanoidArmature.get().handL, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SMALL.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.0F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ReuseableEvents.SUMMON_3_OBSIDIAN_HAND_LEFT, Side.SERVER)
                        ));
        OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW = builder.nextAccessor("biped/wom_clone/obsidian_enderblaster_twohand_tishnaw",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.3F, 0.5F, 0.65F, WOMWeaponColliders.KICK_HUGE, humanoidArmature.get().legR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.65F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.2F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 20)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F)).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.35F && elapsedTime < 0.45F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for(BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(livingEntityPatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                                LivingEntity livingentity = livingEntityPatch.getOriginal();
                                Vec3f direction = new Vec3f(2.5F, -0.25F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f().rotate(-org.joml.Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                AABB box = AABB.ofSize(livingEntityPatch.getOriginal().getPosition(1.0F), 3.0F, 3.0F, 3.0F);
                                List<Entity> list = livingEntityPatch.getOriginal().level().getEntities(livingEntityPatch.getOriginal(), box);
                                if (distanceToGround > 0.5F && list.isEmpty()) {
                                    livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                    return 0.05F;
                                } else {
                                    return speed;
                                }
                            } else {
                                return 1.0F;
                            }
                        })
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, params) -> {
                                            LivingEntity entity = livingEntityPatch.getOriginal();
                                            livingEntityPatch.getOriginal().level()
                                                    .addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(),
                                                            entity.getX(), entity.getY(), entity.getZ(),
                                                            Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F
                                                    );
                                        }, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.GROUND_BODYSCRAPE_LAND, Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.SUMMON_OBSIDIAN_SMALL_CROSS, Side.SERVER)
                                })
        );
        SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM = builder.nextAccessor("biped/wom_clone/shadow_obsidian_sword_torment_airslam",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.45F, 0.55F, 0.6F, 0.6F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new Phase(0.6F, 0.5F, 0.65F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.0F))
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER,
                                (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
            if (elapsedTime >= 0.3F && elapsedTime < 0.55F) {
                float dpx = (float) livingEntityPatch.getOriginal().getX();
                float dpy = (float) livingEntityPatch.getOriginal().getY();
                float dpz = (float) livingEntityPatch.getOriginal().getZ();
                for(BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                    --dpy;
                }
                float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(livingEntityPatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
            } else {
                return speed;
            }
        }).addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.55F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT)}));
        SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH = builder.nextAccessor("biped/wom_clone/shadow_obsidian_sword_torment_berserk_dash",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.45F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new Phase(0.55F, 0.8F, 0.85F, 0.9F, 0.9F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new Phase(0.9F, 1.35F, 1.4F, 1.4F, Float.MAX_VALUE, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 1)
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 2)
                        .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()), 2)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get()).
                        addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get(), 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get(), 2)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.SUMMON_OBSIDIAN_WALL, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.85F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.85F, ReuseableEvents.SUMMON_OBSIDIAN_WALL, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.4F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.4F, ReuseableEvents.SUMMON_OBSIDIAN_WALL, Side.SERVER)
                        }));
        SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_3 = builder.nextAccessor("biped/wom_clone/shadow_obsidian_sword_gezets_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.3F, 0.5F, 0.55F, 0.55F, InteractionHand.OFF_HAND, humanoidArmature.get().handL, WOMWeaponColliders.PUNCH), new Phase(0.55F, 0.7F, 0.85F, 1.0F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, WOMWeaponColliders.GESETZ))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.33F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.4F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.84F), 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get(), 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get(), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE, 1)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addEvents(
                                new AnimationEvent[] {
                                        AnimationEvent.InTimeEvent.create(0.8F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), SoundEvents.ANVIL_LAND, SoundSource.MASTER, 0.3F, 1.2F - (new Random().nextFloat() - 0.5F) * 0.2F), Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.THROW_OBSIDIAN_OFFHAND, Side.SERVER)
                                }
                        )
        );
        SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_2 = builder.nextAccessor("biped/wom_clone/shadow_obsidian_sword_gezets_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.1F, 0.2F, 0.3F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, WOMWeaponColliders.GESETZ_INSET_LARGE))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.4F)
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), SoundEvents.ANVIL_LAND, SoundSource.MASTER, 0.3F, 1.2F - (new Random().nextFloat() - 0.5F) * 0.2F), Side.CLIENT)}));
        CLONE_NAPOLEON_WATERLOW_SHOOT = builder.nextAccessor("biped/wom_clone/clone_napoleon_waterlow_shoot",
                accessor -> new SpecialAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.1F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().toolR, null),
                        new Phase(0.35F, 0.8F, 0.9F, 0.94F, 0.94F, humanoidArmature.get().toolR, null),
                        new Phase(0.94F, 0.95F, 1.1F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.NAPOLEON_WATERLOW_SHOOT))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.58F), 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.8F))
                        .addProperty(WomAnimationProperty.CAN_SPAM, true)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER,
                                (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime > 0.8F && elapsedTime < 0.9F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY() - 1.0F;
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();
                                BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz));
                                livingEntityPatch.getOriginal().setDeltaMovement(0.0F, -2.0F, 0.0F);
                                LivingEntity entity = livingEntityPatch.getOriginal();
                                if ((block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR) && dpy > -64.0F && !block.is(Blocks.WATER) && !entity.onGround()) {
                                    return (elapsedTime - 0.8F) / 0.1F;
                                }
                                return 2.0F;
                            }

                            return 1.0F;
                        })
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.15F, (livingEntityPatch, self, params) -> {
                                Level level = livingEntityPatch.getOriginal().level();
                                LivingEntity entity = livingEntityPatch.getOriginal();
                                level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                            }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.25F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                            }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                            }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                            }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.75F, (livingEntityPatch, self, params) -> {
                                Level level = livingEntityPatch.getOriginal().level();
                                LivingEntity entity = livingEntityPatch.getOriginal();
                                level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                            }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.85F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                            }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.95F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                            }, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.05F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                            }, Side.CLIENT),
                                AnimationEvent.InPeriodEvent.create(0.0F, 1.05F, (livingEntityPatch, self, params) -> {
                                livingEntityPatch.getOriginal().resetFallDistance();
                                Entity livingEntity = livingEntityPatch.getOriginal();
                                if (livingEntity instanceof Player player) {
                                    player.yCloak = 0.0F;
                                    player.yCloakO = 0.0F;
                                }
                            }, Side.BOTH))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.9F, reascer.wom.gameasset.ReuseableEvents.BODY_BIG_GROUNDSLAM, Side.CLIENT))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(LEGENDARY_SWORD_WAKE_UP_ATTACK, 0.0F);
                                    }
                                }, Side.SERVER)
                        )
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true)
                        .newTimePair(0.0F, 0.35F).addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.55F, 1.1F).addState(EntityState.CAN_SKILL_EXECUTION, false));
        CUT_ENDERBLASTER_TWOHAND_RELOAD = builder.nextAccessor("biped/wom_clone/cut_enderblaster_twohand_reload",
                accessor -> new StaticAnimation(0.1F, false, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.TRIDENT_SPINNING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.25F, ReuseableEvents.TRIDENT_SPINNING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, ReuseableEvents.TRIDENT_SPINNING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, ReuseableEvents.TRIDENT_SPINNING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, ReuseableEvents.TRIDENT_SPINNING, Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.TRIDENT_SPINNING, Side.CLIENT)));
        HACKER_SWORD_SKILL = builder.nextAccessor("biped/wom_clone/hacker_sword_skill", accessor -> (BasicMultipleAttackAnimation) new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new Phase(0.0F, 0.45F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new Phase(0.55F, 0.8F, 0.85F, 0.9F, 0.9F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new Phase(0.9F, 1.35F, 1.4F, 1.4F, 1.4F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new Phase(1.55F, 1.8F, 1.85F, 1.9F, 1.9F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new Phase(1.9F, 2.35F, 2.4F, 2.4F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.SWORD))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(0.0F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.35F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.85F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(1.35F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(1.85F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, Side.CLIENT)
                })
        );
        WARBLADE_SATSUJIN_TSUKUYOMI = builder.nextAccessor("biped/wom_clone/warblade_katana_tsukuyomi",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.6F, 0.75F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.SHARPCUT_UP_SLASH)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME,
                                TimePairList.create(0.0F, 0.6F)
                        )
                        .newTimePair(0.0F, 0.9F)
                        .addStateRemoveOld(EntityState.INACTION, true)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                    if (elapsedTime > 0.65F && elapsedTime < 0.75F) {
                        float dpx = (float) livingEntityPatch.getOriginal().getX();
                        float dpy = (float) livingEntityPatch.getOriginal().getY();
                        float dpz = (float) livingEntityPatch.getOriginal().getZ();

                        for(BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                            --dpy;
                        }

                        float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(livingEntityPatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                        LivingEntity livingentity = livingEntityPatch.getOriginal();
                        Vec3f direction = new Vec3f(0.0F, -0.75F, 0.0F);
                        OpenMatrix4f rotation = new OpenMatrix4f().rotate(-org.joml.Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                        OpenMatrix4f.transform3v(rotation, direction, direction);
                        livingentity.move(MoverType.SELF, direction.toDoubleVector());
                        return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                    } else {
                        return speed;
                    }
                })
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.25F, (livingEntityPatch, self, params) -> EpicfightUtil.shootFlyingShockwave(livingEntityPatch), Side.SERVER),
                                        AnimationEvent.InTimeEvent.create(0.7F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().resetFallDistance(), Side.SERVER)
                        })
        );
        HOOK_HERRSCHER_UP = builder.nextAccessor("biped/wom_clone/hook_herrscher_up",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.45F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, null)
                        .addProperty(AttackPhaseProperty.PARTICLE, WOMParticles.SHARPCUT_UP_SLASH)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE, WOMDamageType.BLACKOUT))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.25F, (entitypatch, self, params) -> {
            if (entitypatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                serverLevel.playSound(null, entitypatch.getOriginal().getX(), entitypatch.getOriginal().getY(), entitypatch.getOriginal().getZ(), EpicFightSounds.WHOOSH_ROD.get(), SoundSource.MASTER, 0.5F, 1.3F - ((new Random()).nextFloat() - 0.5F) * 0.1F);
            }

        }, Side.SERVER)}));
    }

    private static @NotNull Vec3 getVec3(LivingEntity owner) {
        Vec3 look = owner.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            float yawRad = (float) Math.toRadians(owner.getYRot());
            forward = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
        }
        forward = forward.normalize();
        return forward;
    }

    private static class ReuseableEvents {
        public static final AnimationEvent.E0 FAST_SPINNING =
                (livingentitypatch, staticAnimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.NEUTRAL, 0.5F, 1.1F - (new Random().nextFloat() - 0.5F) * 0.2F);
        public static final AnimationEvent.E0 TRIDENT_SPINNING =
                (livingentitypatch, staticAnimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), SoundEvents.TRIDENT_RETURN, SoundSource.NEUTRAL, 0.5F, 1.1F - (new Random().nextFloat() - 0.5F) * 0.2F);
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handL, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                            );
                            if (jointVec == null) return;

                            serverLevel.sendParticles(
                                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                                    jointVec.x, jointVec.y, jointVec.z,
                                    1,
                                    0.0D, 0.0D, 0.0D,
                                    0.0D
                            );

                            float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                            float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                            serverLevel.playSound(
                                    null,
                                    BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                    AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                    SoundSource.NEUTRAL,
                                    volume,
                                    pitch
                            );
                        }
                    }
                };
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_WEAPON_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().toolR, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                            );
                            if (jointVec == null) return;

                            serverLevel.sendParticles(
                                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                                    jointVec.x, jointVec.y, jointVec.z,
                                    1,
                                    0.0D, 0.0D, 0.0D,
                                    0.0D
                            );

                            float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                            float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                            serverLevel.playSound(
                                    null,
                                    BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                    AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                    SoundSource.NEUTRAL,
                                    volume,
                                    pitch
                            );
                        }
                    }
                };
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handR, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                            );
                            if (jointVec == null) return;

                            serverLevel.sendParticles(
                                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                                    jointVec.x, jointVec.y, jointVec.z,
                                    1,
                                    0.0D, 0.0D, 0.0D,
                                    0.0D
                            );

                            float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                            float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                            serverLevel.playSound(
                                    null,
                                    BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                    AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                    SoundSource.NEUTRAL,
                                    volume,
                                    pitch
                            );
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack =  livingEntity.getOffhandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handL, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack = livingEntity.getMainHandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handR, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_LEFT_LIGHTNING =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack =  livingEntity.getOffhandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handL, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setMode(TridentMode.LIGHTNING);
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT_LIGHTNING =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack = livingEntity.getMainHandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handR, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setMode(TridentMode.LIGHTNING);
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT_EXPLODE =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack = livingEntity.getMainHandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handR, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setMode(TridentMode.EXPLOSION);
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_LEG_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().legR);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().legR);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_LEG_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().legL);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().legL);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().toolR);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().toolR);
                        } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                            HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().toolR);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().toolL);
                        } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                            HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().toolL);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().toolL);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_3_OBSIDIAN_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 3, Armatures.BIPED.get().toolL);
                        } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                            HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 4, Armatures.BIPED.get().toolL);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 4, Armatures.BIPED.get().toolL);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_6_OBSIDIAN_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 6, Armatures.BIPED.get().toolL);
                        } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                            HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().toolL);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().toolL);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_6_OBSIDIAN_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 6, Armatures.BIPED.get().toolR);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().toolR);
                        } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                            HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().toolR);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_6_OBSIDIAN_LEG_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 6, Armatures.BIPED.get().legR);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().legR);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_OBSIDIAN_PILLAR =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianPillar(serverLevel, livingEntity, obsidian);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianLongPillarShootToward(serverLevel, livingEntity);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_OBSIDIAN_WALL =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianWall(serverLevel, livingEntity, obsidian);
                        } else if (weapon instanceof ShadowObsidianPillarItem || weapon instanceof ShadowObsidianSwordItem) {
                            HerobrineUtil.summonShadowObsidianLongPillarDefense(serverLevel, livingEntity);
                        }
                    }
                };

        public static final AnimationEvent.E0 SUMMON_OBSIDIAN_CIRCLE =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        HerobrineUtil.summonShadowObsidianLongPillarCircle(serverLevel, livingEntity, livingEntity.getOnPos());
                        if (livingEntity.getMainHandItem().getItem() instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianLongPillarShootToward(serverLevel, livingEntity);
                        }
                    }
                };

        public static final AnimationEvent.E0 SUMMON_OBSIDIAN_CROSS =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                            BlockState obsidian;
                            if (weapon instanceof ShadowObsidianWeaponItem) {
                                obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            } else {
                                obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                        .defaultBlockState()
                                        .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                            }
                            HerobrineUtil.summonObsidianCross(serverLevel, livingEntity, obsidian);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            // has delay with mob
                            if (livingEntity instanceof Player) {
                                HerobrineUtil.summonShadowObsidianLongPillarDefenseWide(serverLevel, livingEntity);
                            }
                        }
                    }
                };

        public static final AnimationEvent.E0 SUMMON_OBSIDIAN_CROSS_FIX_DELAY_SHADOW_HEROBRINE =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (livingEntity.level() instanceof ServerLevel serverLevel
                            && !(livingEntity instanceof Player)
                            && weapon instanceof ShadowObsidianPillarItem) {
                        HerobrineUtil.summonShadowObsidianLongPillarDefenseWide(serverLevel, livingEntity);
                    }
                };

        public static final AnimationEvent.E0 SUMMON_OBSIDIAN_SMALL_CROSS =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player)
                                    .setValue(BlockStateProperties.HORIZONTAL_FACING, livingEntity.getDirection());
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianSmallCross(serverLevel, livingEntity, obsidian);
                    }
                };

        public static final AnimationEvent.E0 THROW_OBSIDIAN =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else if (weapon instanceof ShadowObsidianSwordItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        LivingEntity attacker = livingEntityPatch.getOriginal();
                        Vec3 to = attacker.getEyePosition().add(attacker.getLookAngle().scale(16.0));
                        if (attacker instanceof Mob mob && mob.getTarget() != null) {
                            to = mob.getTarget().getEyePosition(1.0F);
                        }
                        BlockProjectileEntity throwingObsidian = new BlockProjectileEntity(
                                attacker.level(),
                                attacker,
                                obsidian
                        );
                        serverLevel.addFreshEntity(throwingObsidian);
                        Vec3 dir = to.subtract(throwingObsidian.position());
                        if (dir.lengthSqr() < 1.0e-6) dir = attacker.getLookAngle();
                        Vec3 vel = dir.normalize().scale(2.0F);
                        throwingObsidian.setDeltaMovement(vel);
                    }
                };

        public static final AnimationEvent.E0 THROW_OBSIDIAN_OFFHAND =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().toolL, 2.0F, 0.0F
                        );
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else if (weapon instanceof ShadowObsidianSwordItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        LivingEntity attacker = livingEntityPatch.getOriginal();
                        Vec3 to = attacker.getEyePosition().add(attacker.getLookAngle().scale(16.0));
                        if (attacker instanceof Mob mob && mob.getTarget() != null) {
                            to = mob.getTarget().getEyePosition(1.0F);
                        }
                        BlockProjectileEntity throwingObsidian = new BlockProjectileEntity(
                                attacker.level(),
                                attacker,
                                obsidian
                        );
                        serverLevel.addFreshEntity(throwingObsidian);
                        if (jointVec != null) {
                            throwingObsidian.moveTo(jointVec);
                        }
                        Vec3 dir = to.subtract(throwingObsidian.position());
                        if (dir.lengthSqr() < 1.0e-6) dir = attacker.getLookAngle();
                        Vec3 vel = dir.normalize().scale(2.0F);
                        throwingObsidian.setDeltaMovement(vel);
                    }
                };

        public static final AnimationEvent.E0 SLEDGEHAMMER_SHOOT =
                (livingEntityPatch, staticAnimation, object) -> {
                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                        LivingEntity shooterEntity = livingEntityPatch.getOriginal();

                        Vec3 aimPosition = null;

                        if (shooterEntity instanceof Mob mob && mob.getTarget() != null) {
                            aimPosition = mob.getTarget().getEyePosition(1.0F);
                        } else if (shooterEntity instanceof Player player) {
                            Vec3 playerEyePosition = player.getEyePosition(1.0F);
                            Vec3 playerLookDirection = player.getLookAngle();
                            double aimDistance = 64.0D;
                            aimPosition = playerEyePosition.add(playerLookDirection.scale(aimDistance));
                        }

                        ObsidianSledgehammerProjectileEntity obsidianSledgehammerProjectileEntity = new ObsidianSledgehammerProjectileEntity(AnnoyingVillagersModEntities.OBSIDIAN_SLEDGEHAMMER_PROJECTILE.get(), serverLevel);
                        Vec3 hammerPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().toolR, 1.0F, 0.0F);
                        if (hammerPos != null && aimPosition != null) {
                            obsidianSledgehammerProjectileEntity.moveTo(hammerPos.x, hammerPos.y, hammerPos.z, 0F, 0F);
                            obsidianSledgehammerProjectileEntity.setPosToAim(new Vec3(aimPosition.x, aimPosition.y, aimPosition.z));
                            obsidianSledgehammerProjectileEntity.setInvulnerable(true);
                            obsidianSledgehammerProjectileEntity.playSound(AnnoyingVillagersModSounds.METAL_HIT.get(), 1.0F, 1.0F);
                            obsidianSledgehammerProjectileEntity.setOwner(shooterEntity);
                            if (staticAnimation == SLEDGEHAMMER_SOLAR_AUTO_3) {
                                obsidianSledgehammerProjectileEntity.setShouldStun(true);
                            }
                            serverLevel.addFreshEntity(obsidianSledgehammerProjectileEntity);
                        }
                    }
                };
        public static final AnimationEvent.E0 SHOCK_WAVE =
                (livingEntityPatch, staticAnimation, object) -> {

                    Vec3 legendarySwordPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                            Armatures.BIPED.get().toolR, 1.5F, 0.0F);
                    final int MAX_SHOCKWAVE_RADIUS = 6;
                    final int TICKS_BETWEEN_LAYERS = 2;
                    for (int radius = 1; radius <= MAX_SHOCKWAVE_RADIUS; radius++) {
                        int delayTicks = (radius - 1) * TICKS_BETWEEN_LAYERS;
                        int ringRadius = radius;
                        if (legendarySwordPos == null) return;
                        BlockPos finalVec = BlockPos.containing(legendarySwordPos);
                        new DelayedTask(delayTicks) {
                            @Override
                            public void run() {
                                LegendarySwordItem.spawnCircleRing((ServerLevel) livingEntityPatch.getOriginal().level(), finalVec, ringRadius, livingEntityPatch.getOriginal());
                            }
                        };
                    }
                };

        public static final AnimationEvent.E0 END_ATTACK =
                (livingentitypatch, staticAnimation, object) -> {
                    if (livingentitypatch instanceof PlayerPatch) {
                        livingentitypatch.playAnimationSynchronized(DUAL_END, 0.1F);
                    }
                };

        public static Vec3 getFloor(LivingEntityPatch<?> livingentitypatch, Vec3f vec3f, Joint joint) {
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBoundTransformFor(livingentitypatch.getAnimator().getPose(1.0F), joint);

            openmatrix4f.translate(vec3f);
            OpenMatrix4f openMatrix4f1 = new OpenMatrix4f().rotate(-((float) Math.toRadians(livingentitypatch.getOriginal().yRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F));

            OpenMatrix4f.mul(openMatrix4f1, openmatrix4f, openmatrix4f);
            float f = openmatrix4f.m30 + (float) livingentitypatch.getOriginal().getX();
            float f1 = openmatrix4f.m31 + (float) livingentitypatch.getOriginal().getY();
            float f2 = openmatrix4f.m32 + (float) livingentitypatch.getOriginal().getZ();

            for (BlockState blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) f, (int) f1, (int) f2))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = livingentitypatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int) f, (int) f1, (int) f2)))) {
                --f1;
            }

            return new Vec3(f, f1, f2);
        }
    }
}
