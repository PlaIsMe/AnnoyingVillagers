package com.pla.annoyingvillagers.event;

import com.hm.efn.gameasset.animations.EFNSwordAnimations;
import com.pla.annoyingvillagers.compat.cdmoveset.EpicFightResurrection;
import com.pla.annoyingvillagers.compat.refm.EpicFightRapierMoveset;
import com.pla.annoyingvillagers.entity.BlackFireEntity;
import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.skill.*;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.shelmarow.ef_awaken.efassets.animations.StraightSwordAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

import java.util.Objects;

public class SpecialAttackOnKeyPressedEvent {
    private static void playHookGunBindAnimationAfterHandRefresh(Player player) {
        new DelayedTask(2) {
            @Override
            public void run() {
                if (player.isRemoved() || player.level().isClientSide()) {
                    return;
                }

                LivingEntityPatch<?> freshPatch = EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
                if (freshPatch != null) {
                    freshPatch.playAnimationSynchronized(AVAnimations.HOOK_GUN, 0.0F);
                }
            }
        };
    }

    private static void playTransporterFragmentAnimation(
            Player player,
            LivingEntityPatch<?> livingEntityPatch,
            TransporterFragmentItem.UseMode useMode
    ) {
        switch (useMode) {
            case BOTH_HANDS, MAIN_HAND -> {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.PORTAL_SUMMON, 0.0F);
            }
            case OFF_HAND -> livingEntityPatch.playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F);
            case NONE -> {
            }
        }
    }

    public static void execute(LevelAccessor world, Entity entity) {
        execute(world, entity, null);
    }

    public static void execute(LevelAccessor world, Entity entity, Vec3 crosshairTarget) {
        if (entity == null) return;

        PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) return;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, livingEntityPatch)) {
            return;
        }

        if (entity instanceof Player player
                && !player.level().isClientSide()
                && HookGunItem.tryBindFromSpecialAttack(player)) {
            playHookGunBindAnimationAfterHandRefresh(player);
            return;
        }

        if (entity instanceof Player player && !player.level().isClientSide()) {
            TransporterFragmentItem.UseResult transporterUseResult = TransporterFragmentItem.tryUseSpecialAttack(player, crosshairTarget);
            if (transporterUseResult.consumed()) {
                if (transporterUseResult.activated()) {
                    playTransporterFragmentAnimation(player, livingEntityPatch, transporterUseResult.mode());
                }
                return;
            }
        }

        if (entity instanceof Player player) {
            // Spawn special effect without playing animation
            ItemStack holdingItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.BLACK_FIRE_SWORD.get())) {
                PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.BLACK_FIRE_SWORD);
                    if (skillContainer != null
                            && entity.level() instanceof ServerLevel serverLevel) {
                        if (skillContainer.getResource() >= 5){
                            Skill.setSkillConsumptionSynchronize(
                                    skillContainer,
                                    skillContainer.getResource() - 5
                            );
                            BlackFireEntity.spawnOnOwnerSword(serverLevel, player);
                        }
                    }
                }
            }

            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get())) {
                PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.THUNDER_DIAMOND_BLADE);
                    if (skillContainer != null
                            && entity.level() instanceof ServerLevel serverLevel) {
                        if (skillContainer.getResource() >= 10){
                            Skill.setSkillConsumptionSynchronize(
                                    skillContainer,
                                    skillContainer.getResource() - 10
                            );
                            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, player);
                            return;
                        }
                    }

                    skillContainer = serverPlayerPatch.getSkill(AVSkills.DUAL_THUNDER_DIAMOND_BLADE);
                    if (skillContainer != null
                            && entity.level() instanceof ServerLevel serverLevel) {
                        if (skillContainer.getResource() >= 10){
                            Skill.setSkillConsumptionSynchronize(
                                    skillContainer,
                                    skillContainer.getResource() - 10
                            );
                            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, player);
                            if (offHandItem.getItem().equals(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get())) {
                                ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, player, true);
                            }
                            return;
                        }
                    }
                }
            }
        }

        if (entity.level() instanceof ServerLevel) {
            if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
                return;
            }
        }

        if (entity instanceof Player player && !player.level().isClientSide() &&
                !player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()) &&
                !player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
            player.getInventory().items.stream()
                    .filter(s -> !s.isEmpty() && s.is(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()))
                    .findFirst()
                    .map(stack -> {
                        if (stack.getItem() instanceof HerobrineEnderEyeItem herobrineEnderEyeItem) {
                            var cooldowns = player.getCooldowns();
                            if (cooldowns.isOnCooldown(herobrineEnderEyeItem)) {
                                return false;
                            }

                            HerobrineEnderEyeItem.spawnAndShootDarkObPillars((ServerLevel) player.level(), player, 10);
                            player.getCooldowns().addCooldown(herobrineEnderEyeItem, 40);
                            stack.hurtAndBreak(5, player, p -> {
                            });
                            return true;
                        }
                        return false;
                    });
        }

        if (entity instanceof Player player) {
            // Check by item
            ItemStack holdingItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get())) {
                if (entity.level() instanceof ServerLevel) {
                    if (offHandItem.getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get())) {
                        livingEntityPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_SPECIAL, 0.0F);
                        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                        if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.TRIDENT_FESTIVAL);
                            if (skillContainer != null && skillContainer.getSkill() instanceof TridentFestivalSkill tridentFestivalSkill) {
                                tridentFestivalSkill.toggleMode(skillContainer);
                            }
                        }
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_3, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_AEGIS.get())) {
                if (entity.level() instanceof ServerLevel) {
                    if (EnderAegisItem.isSecondForm(holdingItem)) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.AEGIS_SHIELD_SHOOT_MAINHAND, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.ENDER_AEGIS_PUSH, 0.0F);
                    }
                    return;
                }
            }
            if (offHandItem.getItem().equals(AnnoyingVillagersModItems.ENDER_AEGIS.get())
                    && EnderAegisItem.isSecondForm(offHandItem)) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.AEGIS_SHIELD_SHOOT_OFFHAND, 0.0F);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.EARTH_AXE.get())) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(AnimsAVAxe.EARTH_AXE_SPECIAL, 0.0F);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_GLAIVE.get())) {
                if (entity.level() instanceof ServerLevel) {
                    boolean success = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_GLAIVE);
                        if (skillContainer != null && skillContainer.getSkill() instanceof EnderGlaiveSkill enderGlaiveSkill) {
                            if (skillContainer.getStack() >= 1) {
                                livingEntityPatch.playAnimationSynchronized(AnimsEnderGlaive.ENDER_GLAIVE_INNATE_SPECIAL, 0.0F);
                                enderGlaiveSkill.getResourceType().consumer
                                        .consume(skillContainer, serverPlayerPatch, enderGlaiveSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                success = true;
                            }
                        }
                    }
                    if (!success) {
                        livingEntityPatch.playAnimationSynchronized(AnimsEnderGlaive.ENDER_GLAIVE_SPECIAL, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) {
                if (entity.level() instanceof ServerLevel
                        && holdingItem.getTag() != null && !holdingItem.getTag().getBoolean("SnakeAnimation")) {
                    boolean success = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.DEMONIAC_VOLTAGE_REAVER);
                        if (skillContainer != null && skillContainer.getSkill() instanceof DemoniacVoltageReaverSkill demoniacVoltageReaverSkill) {
                            if (skillContainer.getStack() >= 1) {
                                livingEntityPatch.playAnimationSynchronized(AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL, 0.0F);
                                demoniacVoltageReaverSkill.getResourceType().consumer
                                        .consume(skillContainer, serverPlayerPatch, demoniacVoltageReaverSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                success = true;
                            }
                        }
                    }
                    if (!success) {
                        livingEntityPatch.playAnimationSynchronized(AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_SPECIAL, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get())) {
                if (entity.level() instanceof ServerLevel) {
                    boolean success = false;

                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_SLEDGEHAMMER);
                        if (skillContainer != null && skillContainer.getSkill() instanceof ObsidianSledgeHammerSkill obsidianSledgeHammerSkill && player.level() instanceof ServerLevel) {
                            if (skillContainer.getStack() >= 1) {
                                livingEntityPatch.playAnimationSynchronized(AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_INNATE_SPECIAL, 0.0F);
                                obsidianSledgeHammerSkill.getResourceType().consumer
                                        .consume(skillContainer, serverPlayerPatch, obsidianSledgeHammerSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                success = true;
                            }
                        }
                    }
                    if (!success) {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_SPECIAL, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get())) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    boolean usedInnateSpecial = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_SLAYER_SCYTHE);
                        if (skillContainer != null
                                && skillContainer.getSkill() instanceof EnderSlayerScytheSkill) {
                            if (skillContainer.isActivated() && entity.getPersistentData().hasUUID(EnderSlayerScytheSkill.DRAGON_UUID_TAG)) {
                                Entity dragon = serverLevel.getEntity(player.getPersistentData().getUUID(EnderSlayerScytheSkill.DRAGON_UUID_TAG));

                                if (dragon instanceof HerobrineDragonEntity herobrineDragonEntity) {
                                    if (player.getVehicle() == herobrineDragonEntity) {
                                        usedInnateSpecial = true;
                                    } else {
                                        livingEntityPatch.playAnimationSynchronized(AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_SPECIAL_INNATE, 0.0F);
                                        herobrineDragonEntity.recallAndLand(true);
                                        usedInnateSpecial = true;
                                    }
                                }
                            }
                        }
                    }
                    if (!usedInnateSpecial) {
                        livingEntityPatch.playAnimationSynchronized(AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_SPECIAL, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.NULL_WEAPON.get())) {
                if (entity.level() instanceof ServerLevel) {
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.NULL_WEAPON);
                        if (skillContainer != null && skillContainer.getSkill() instanceof NullWeaponSkill && !skillContainer.isActivated()) {
                            livingEntityPatch.playAnimationSynchronized(AnimsNullWeapon.NULL_WEAPON_SPECIAL, 0.0F);
                        } else {
                            livingEntityPatch.playAnimationSynchronized(AnimsNullWeapon.NULL_WEAPON_INNATE_SPECIAL, 0.0F);
                        }
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get())) {
                if (entity.level() instanceof ServerLevel) {
                    boolean success = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_WEAPON);
                        if (skillContainer != null && skillContainer.getStack() >= 1
                                && entity.level() instanceof ServerLevel
                                && skillContainer.getSkill() instanceof ObsidianWeaponSkill obsidianWeaponSkill) {
                            success = true;
                            obsidianWeaponSkill.getResourceType().consumer
                                    .consume(skillContainer, serverPlayerPatch, obsidianWeaponSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                        }
                    }
                    if (success) {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_INNATE_SPECIAL, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_SPECIAL, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get())) {
                if (entity.level() instanceof ServerLevel) {
                    boolean success = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_WEAPON);
                        if (skillContainer != null && skillContainer.getStack() >= 1
                                && entity.level() instanceof ServerLevel
                                && skillContainer.getSkill() instanceof ObsidianWeaponSkill obsidianWeaponSkill) {
                            success = true;
                            obsidianWeaponSkill.getResourceType().consumer
                                    .consume(skillContainer, serverPlayerPatch, obsidianWeaponSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                        }
                    }
                    if (success) {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_INNATE_SPECIAL, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_SPECIAL, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get())) {
                if (entity.level() instanceof ServerLevel) {
                    boolean success = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.SHADOW_OBSIDIAN_PILLAR);
                        if (skillContainer != null && skillContainer.getStack() >= 1
                                && entity.level() instanceof ServerLevel
                                && skillContainer.getSkill() instanceof ShadowObsidianPillarSkill shadowObsidianPillarSkill) {
                            success = true;
                            shadowObsidianPillarSkill.getResourceType().consumer
                                    .consume(skillContainer, serverPlayerPatch, shadowObsidianPillarSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                        }
                    }
                    if (success) {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_INNATE_SPECIAL, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.SHADOW_OBSIDIAN_PILLAR_SPECIAL, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get())) {
                if (entity.level() instanceof ServerLevel) {
                    if (offHandItem.getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get())) {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_SPECIAL, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_SPECIAL, 0.0F);
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())
                    || offHandItem.getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_MACHINE_GUN, 0.0F);
                    if (player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                        player.getMainHandItem().hurtAndBreak(10, player, p -> {
                        });
                    } else if (player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                        player.getOffhandItem().hurtAndBreak(10, player, p -> {
                        });
                    }
                    return;
                }
            }
            if (holdingItem.getItem() instanceof BowItem) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(AnimsBow.BOW_AUTO_2, 0.0F);
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())) {
                if (entity.level() instanceof ServerLevel) {
                    boolean success = false;
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.LEGENDARY_SWORD);
                        if (skillContainer != null && skillContainer.getSkill() instanceof LegendarySwordSkill legendarySwordSkill && player.level() instanceof ServerLevel) {
                            if (LegendarySwordSkill.isAwakened(skillContainer)
                                    && offHandItem.getItem().equals(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) {
                                livingEntityPatch.playAnimationSynchronized(AnimsAVSword.WOOPIE_INNATE_SPECIAL_LEGENDARY, 0.0F);
                                return;
                            }

                            if (skillContainer.getStack() >= 1) {
                                if (offHandItem.getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get())) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_ELECTRIC_FIELD, 0.0F);
                                    legendarySwordSkill.getResourceType().consumer
                                            .consume(skillContainer, serverPlayerPatch, legendarySwordSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                } else {
                                    livingEntityPatch.playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_INNATE_SPECIAL, 0.0F);
                                }
                                success = true;
                            }
                        }
                    }

                    if (!success) {
                        if (offHandItem.getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get())) {
                            livingEntityPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_SPECIAL_LEGENDARY, 0.0F);
                        } else {
                            livingEntityPatch.playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_SPECIAL, 0.0F);
                        }
                    }
                    return;
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) {
                if (entity.level() instanceof ServerLevel) {
                    PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.WOOPIE_THE_SWORD);
                        if (skillContainer != null && skillContainer.getStack() == 1
                                && entity.level() instanceof ServerLevel
                                && skillContainer.getSkill() instanceof WoopieTheSwordSkill woopieTheSwordSkill) {
                            livingEntityPatch.playAnimationSynchronized(AnimsAVSword.WOOPIE_INNATE_SPECIAL, 0.0F);
                            woopieTheSwordSkill.getResourceType().consumer
                                    .consume(skillContainer, serverPlayerPatch, woopieTheSwordSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                            return;
                        }
                    }
                }
            }
            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.BLUE_FLAME_SWORD.get())) {
                if (entity.level() instanceof ServerLevel) {
                    if (player.level() instanceof ServerLevel level) {
                        double reach = player.getBlockReach();
                        HitResult hitResult = player.pick(reach, 0.0F, false);

                        if (hitResult.getType() == HitResult.Type.BLOCK) {
                            BlockHitResult blockHit = (BlockHitResult) hitResult;
                            BlockPos lookedPos = blockHit.getBlockPos();

                            BlockState lookedState = level.getBlockState(lookedPos);

                            if (lookedState.is(Blocks.SOUL_SAND) || lookedState.is(Blocks.SOUL_SOIL)) {
                                BlockPos firePos = lookedPos.above();
                                if (level.isEmptyBlock(firePos)) {
                                    BlockState soulFireState = Blocks.SOUL_FIRE.defaultBlockState();
                                    if (soulFireState.canSurvive(level, firePos)) {
                                        level.setBlock(firePos, soulFireState, Block.UPDATE_ALL);
                                        livingEntityPatch.playAnimationSynchronized(AnimsAVSword.BLUE_FLAME_SWORD_SPECIAL, 0.0F);
                                        holdingItem.hurtAndBreak(1, player, (serverPlayer1) -> serverPlayer1.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Check by categories
            if (playerpatch == null) return;

            ResourceLocation key = BuiltInRegistries.ITEM.getKey(holdingItem.getItem());
            if (ModList.get().isLoaded("efn") && key.getNamespace().equals("efn")) return;

            CapabilityItem mainHandCapability = playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
            if (mainHandCapability.getWeaponCategory() == WeaponCategories.SWORD
                    || mainHandCapability.getWeaponCategory() == WeaponCategories.AXE) {
                if (mainHandCapability.getStyle(playerpatch) == Styles.ONE_HAND && entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(EFNSwordAnimations.NF_SWORD_SKILL, 0.0F);
                    return;
                }

                if (mainHandCapability.getStyle(playerpatch) == Styles.TWO_HAND && entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(StraightSwordAnimations.STRAIGHTSWORD_DUAL_DODGE_SLASH, 0.0F);
                    return;
                }
            }

            if (mainHandCapability.getWeaponCategory() == WeaponCategories.TACHI) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(AnimsAVTachi.AV_TACHI_SPECIAL, 0.0F);
                    return;
                }
            }

            if (mainHandCapability.getWeaponCategory() == WeaponCategories.DAGGER) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(EFNSwordAnimations.NF_SWORD_SKILL_SECOND, 0.0F);
                    return;
                }
            }

            if (mainHandCapability.getWeaponCategory() == WeaponCategories.LONGSWORD) {
                if (mainHandCapability.getStyle(playerpatch) == Styles.ONE_HAND && entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(StraightSwordAnimations.STRAIGHTSWORD_DODGE_SLASH1, 0.0F);
                    return;
                }

                if (mainHandCapability.getStyle(playerpatch) == Styles.TWO_HAND && entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(StraightSwordAnimations.STRAIGHTSWORD_DUAL_DODGE_PURSUIT, 0.0F);
                    return;
                }
            }

            if (mainHandCapability.getWeaponCategory() == WeaponCategories.GREATSWORD) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(AnimsAVGreatsword.AV_GREATSWORD_SPECIAL, 0.0F);
                    return;
                }
            }

            if (mainHandCapability.getWeaponCategory() == WeaponCategories.FIST
                    || mainHandCapability.getWeaponCategory() == WeaponCategories.NOT_WEAPON
                    || mainHandCapability.getWeaponCategory() == WeaponCategories.BOW
                    || mainHandCapability.getWeaponCategory() == WeaponCategories.CROSSBOW) {
                if (entity.level() instanceof ServerLevel) {
                    if (entity.isSprinting()) {
                        if (entity.level() instanceof ServerLevel) {
                            livingEntityPatch.playAnimationSynchronized(AnimsAVFist.WHIRLWIND_KICK, 0.0F);
                        }
                    } else {
                        if (!entity.getPersistentData().contains("FistCombo")) {
                            livingEntityPatch.playAnimationSynchronized(AnimsAVFist.FIST_LEFT, 0.0F);
                            entity.getPersistentData().putDouble("FistCombo", 1.0);
                        } else if (entity.getPersistentData().getDouble("FistCombo") == 1.0) {
                            livingEntityPatch.playAnimationSynchronized(AnimsAVFist.FIST_UP, 0.0F);
                            entity.getPersistentData().putDouble("FistCombo", 2.0);
                        } else if (entity.getPersistentData().getDouble("FistCombo") == 2.0) {
                            livingEntityPatch.playAnimationSynchronized(AnimsAVFist.FIST_DASH, 0.0F);
                            entity.getPersistentData().remove("FistCombo");
                        }
                    }
                    return;
                }
            }

            if (mainHandCapability.getWeaponCategory() == WeaponCategories.SPEAR
                    || mainHandCapability.getWeaponCategory() == WeaponCategories.TRIDENT) {
                if (entity.level() instanceof ServerLevel) {
                    livingEntityPatch.playAnimationSynchronized(AnimsAVSpear.AV_SPEAR_SPECIAL, 0.0F);
                }
            }

            if (ModList.get().isLoaded("refm")) {
                if (EpicFightRapierMoveset.addRefmSpecialAttack(playerpatch, entity, livingEntityPatch)) {
                    return;
                }
            }

            if (ModList.get().isLoaded("cdmoveset")) {
                if (EpicFightResurrection.addMoreSpecialAttack(playerpatch, entity, livingEntityPatch)) {
                    return;
                }
            }
        }
    }
}
