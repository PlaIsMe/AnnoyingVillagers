//package com.pla.annoyingvillagers.procedures;
//
//import M6FGR.dualaxes.gameassets.DualAxesAnimations;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import com.nameless.indestructible.gameasset.GuardAnimations;
//import java.util.List;
//import javax.annotation.Nullable;
//
//import com.pla.annoyingvillagers.util.DelayedTask;
//import net.corruptdog.cdm.api.animation.types.ExecuteAnimation;
//import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.event.entity.living.LivingAttackEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import com.pla.annoyingvillagers.animations.types.HeavyAttackAnimation;
//import com.pla.annoyingvillagers.animations.types.KickAttackAnimation;
//import com.pla.annoyingvillagers.gameasset.AVAnimations;
//import reascer.wom.gameasset.WOMAnimations;
//import yesman.epicfight.api.animation.AnimationProvider;
//import yesman.epicfight.api.animation.types.AttackAnimation;
//import yesman.epicfight.api.animation.types.DashAttackAnimation;
//import yesman.epicfight.api.animation.types.DynamicAnimation;
//import yesman.epicfight.gameasset.Animations;
//import yesman.epicfight.gameasset.EpicFightSounds;
//import yesman.epicfight.particle.EpicFightParticles;
//import yesman.epicfight.particle.HitParticleType;
//import yesman.epicfight.world.capabilities.EpicFightCapabilities;
//import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
//import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
//import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
//import yesman.epicfight.world.capabilities.item.CapabilityItem;
//import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
//
//@EventBusSubscriber
//public class ParryProcedure {
//
//    @SubscribeEvent
//    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
//        if (livingattackevent != null && livingattackevent.getEntity() != null) {
//            execute(livingattackevent, livingattackevent.getEntity().level(), livingattackevent.getEntity(), livingattackevent.getSource().getEntity());
//        }
//
//    }
//
//    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
//        execute((Event) null, levelaccessor, entity, entity1);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity, final Entity entity1) {
//        if (entity != null && entity1 != null) {
//            LivingEntityPatch livingentitypatch;
//            DynamicAnimation dynamicanimation;
//            DynamicAnimation dynamicanimation1;
//
//            if (!(entity instanceof Player)) {
//                if (entity1.isAlive()) {
//                    boolean flag = false;
//
//                    if (entity != null) {
//                        Vec3 vec3 = entity1.position();
//                        Vec3 vec31 = entity.getViewVector(1.0F);
//                        Vec3 vec32 = vec3.subtract(entity.getEyePosition()).normalize();
//
//                        if (vec32.dot(vec31) > 0.0D) {
//                            flag = true;
//                        }
//                    }
//
//                    livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
//                    if (livingentitypatch != null) {
//                        dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
//                        HumanoidMobPatch<?> humanoidmobpatch = (HumanoidMobPatch)EpicFightCapabilities.getEntityPatch(entity, HumanoidMobPatch.class);
//
//                        if (!(dynamicanimation instanceof KickAttackAnimation) && dynamicanimation != GuardAnimations.MOB_COUNTER_ATTACK && !(dynamicanimation instanceof HeavyAttackAnimation) && dynamicanimation != Animations.STEEL_WHIRLWIND && dynamicanimation != Animations.METEOR_SLAM && dynamicanimation != Animations.GREATSWORD_DASH && dynamicanimation != Animations.SWEEPING_EDGE && dynamicanimation != Animations.UCHIGATANA_SHEATHING_AUTO && dynamicanimation != Animations.UCHIGATANA_SHEATHING_DASH && dynamicanimation != Animations.THE_GUILLOTINE && dynamicanimation != Animations.BATTOJUTSU && dynamicanimation != Animations.BATTOJUTSU_DASH && dynamicanimation != Animations.BLADE_RUSH_COMBO3 && dynamicanimation != AVAnimations.Hacker_sword_skill_1 && !(dynamicanimation instanceof ExecuteAnimation) && dynamicanimation != DualAxesAnimations.AXE_SPINNING_DEATH && dynamicanimation != WOMAnimations.TORMENT_AUTO_3 && dynamicanimation != WOMAnimations.SOLAR_AUTO_3 && dynamicanimation != WOMAnimations.SOLAR_AUTO_4 && dynamicanimation != AVAnimations.Legendary_Sword_Wake_Up_Attack && dynamicanimation != AVAnimations.DUAL_SWORD_AUTO5 && dynamicanimation != WOMAnimations.SOLAR_AUTO_2) {
//                            HumanoidMobPatch<?> humanoidmobpatch1 = (HumanoidMobPatch)EpicFightCapabilities.getEntityPatch(entity, HumanoidMobPatch.class);
//
//                            if (humanoidmobpatch1 != null) {
//                                DynamicAnimation dynamicanimation2 = humanoidmobpatch1.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
//
//                                if (flag && dynamicanimation2 instanceof AttackAnimation && !(dynamicanimation2 instanceof KickAttackAnimation)) {
//                                    if (humanoidmobpatch1.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.FIST) {
//                                        if (event != null && event.isCancelable()) {
//                                            event.setCanceled(true);
//                                        }
//
//                                        if (!entity.level().isClientSide() && entity.getServer() != null) {
//                                            try {
//                                                entity.getServer().getCommands().getDispatcher().execute(
//                                                        "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
//                                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                                entity.getServer().getCommands().getDispatcher().execute(
//                                                        "execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100",
//                                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                            } catch (CommandSyntaxException e) {
//
//                                            }
//                                        }
//
//                                        humanoidmobpatch1.playSound((SoundEvent) EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
//                                        if (entity1 instanceof Player && !entity1.level().isClientSide() && entity1.getServer() != null) {
//                                            try {
//                                                entity1.getServer().getCommands().getDispatcher().execute(
//                                                        "impactful @s shake 15 5 6",
//                                                        entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                            } catch (CommandSyntaxException e) {
//
//                                            }
//                                        }
//
//                                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.2D, 0.0D, entity.getLookAngle().z * -0.2D));
//                                        entity1.setDeltaMovement(new Vec3(entity1.getLookAngle().x * -0.2D, 0.0D, entity1.getLookAngle().z * -0.2D));
//                                    } else {
//                                        float f;
//
//                                        if (entity instanceof LivingEntity) {
//                                            LivingEntity livingentity = (LivingEntity)entity;
//
//                                            f = livingentity.getMaxHealth();
//                                        } else {
//                                            f = -1.0F;
//                                        }
//
//                                        if (f >= 39.0F) {
//                                            if (event != null && event.isCancelable()) {
//                                                event.setCanceled(true);
//                                            }
//
//                                            if (!entity.level().isClientSide() && entity.getServer() != null) {
//                                                try {
//                                                    entity.getServer().getCommands().getDispatcher().execute(
//                                                            "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
//                                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                                    entity.getServer().getCommands().getDispatcher().execute(
//                                                            "execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100",
//                                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                                } catch (CommandSyntaxException e) {
//
//                                                }
//                                            }
//
//                                            humanoidmobpatch1.playSound((SoundEvent) EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
//                                            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.2D, 0.0D, entity.getLookAngle().z * -0.2D));
//                                            entity1.setDeltaMovement(new Vec3(entity1.getLookAngle().x * -0.2D, 0.0D, entity1.getLookAngle().z * -0.2D));
//                                            if (entity1 instanceof Player && !entity1.level().isClientSide() && entity1.getServer() != null) {
//                                                try {
//                                                    entity1.getServer().getCommands().getDispatcher().execute(
//                                                            "impactful @s shake 15 5 6",
//                                                            entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                                } catch (CommandSyntaxException e) {
//
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        } else if (humanoidmobpatch != null) {
//                            DynamicAnimation dynamicanimation3 = humanoidmobpatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
//
//                            if (dynamicanimation3 instanceof AttackAnimation) {
//                                humanoidmobpatch.playSound((SoundEvent) EpicFightSounds.NEUTRALIZE_MOBS.get(), -0.05F, 0.1F);
//                                if (entity.isAlive()) {
//                                    new DelayedTask(1) {
//                                        @Override
//                                        public void run() {
//                                            if (entity.isAlive()) {
//                                                Entity entity2 = entity;
//
//                                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
//                                                    try {
//                                                        entity2.getServer().getCommands().getDispatcher().execute(
//                                                                "indestructible @s play \"epicfight:biped/skill/guard_break1\" 0 10",
//                                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                                    } catch (CommandSyntaxException e) {
//
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    };
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {
//                PlayerPatch<?> playerpatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
//
//                livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
//                if (livingentitypatch != null) {
//                    dynamicanimation = playerpatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
//                    dynamicanimation1 = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
//                    CapabilityItem capabilityitem = EpicFightCapabilities.getItemStackCapability(((Player)playerpatch.getOriginal()).getMainHandItem());
//                    List<AnimationProvider<?>> list = capabilityitem.getAutoAttckMotion(playerpatch);
//
//                    if (!(dynamicanimation1 instanceof KickAttackAnimation) && dynamicanimation1 != GuardAnimations.MOB_COUNTER_ATTACK && !(dynamicanimation1 instanceof HeavyAttackAnimation) && dynamicanimation1 != Animations.STEEL_WHIRLWIND && dynamicanimation1 != Animations.METEOR_SLAM && dynamicanimation1 != Animations.GREATSWORD_DASH && dynamicanimation1 != Animations.SWEEPING_EDGE && dynamicanimation1 != Animations.UCHIGATANA_SHEATHING_AUTO && dynamicanimation1 != Animations.UCHIGATANA_SHEATHING_DASH && dynamicanimation1 != Animations.THE_GUILLOTINE && dynamicanimation1 != Animations.BATTOJUTSU && dynamicanimation1 != Animations.BATTOJUTSU_DASH && dynamicanimation1 != Animations.BLADE_RUSH_COMBO3 && dynamicanimation1 != AVAnimations.Hacker_sword_skill_1 && !(dynamicanimation1 instanceof ExecuteAnimation) && dynamicanimation1 != DualAxesAnimations.AXE_SPINNING_DEATH && dynamicanimation1 != WOMAnimations.TORMENT_AUTO_3 && dynamicanimation1 != WOMAnimations.SOLAR_AUTO_3 && dynamicanimation1 != WOMAnimations.SOLAR_AUTO_4 && dynamicanimation1 != AVAnimations.Legendary_Sword_Wake_Up_Attack && dynamicanimation1 != AVAnimations.DUAL_SWORD_AUTO5 && dynamicanimation1 != WOMAnimations.SOLAR_AUTO_2) {
//                        if (dynamicanimation1 instanceof AttackAnimation && dynamicanimation instanceof AttackAnimation && !(dynamicanimation instanceof KickAttackAnimation) && !list.contains(dynamicanimation) && !(dynamicanimation instanceof DashAttackAnimation)) {
//                            boolean flag1 = false;
//                            Vec3 vec33 = entity1.position();
//                            Vec3 vec34 = entity.getViewVector(1.0F);
//                            Vec3 vec35 = vec33.subtract(entity.getEyePosition()).normalize();
//
//                            if (vec35.dot(vec34) > 0.0D) {
//                                flag1 = true;
//                            }
//
//                            if (flag1 && playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.FIST) {
//                                if (event != null && event.isCancelable()) {
//                                    event.setCanceled(true);
//                                }
//                                playerpatch.playSound((SoundEvent) EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
//                                if (!entity.level().isClientSide() && entity.getServer() != null) {
//                                    try {
//                                        entity.getServer().getCommands().getDispatcher().execute(
//                                                "impactful @s shake 15 5 6",
//                                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                        entity.getServer().getCommands().getDispatcher().execute(
//                                                "execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100",
//                                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                        entity.getServer().getCommands().getDispatcher().execute(
//                                                "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
//                                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                    } catch (CommandSyntaxException e) {
//
//                                    }
//                                }
//                                if (entity1 instanceof Player && !entity1.level().isClientSide() && entity1.getServer() != null) {
//                                    try {
//                                        entity1.getServer().getCommands().getDispatcher().execute(
//                                                "impactful @s shake 15 5 6",
//                                                entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                    } catch (CommandSyntaxException e) {
//
//                                    }
//                                }
//                            }
//                        }
//                    } else if (list.contains(dynamicanimation)) {
//                        playerpatch.playSound((SoundEvent) EpicFightSounds.NEUTRALIZE_MOBS.get(), -0.05F, 0.1F);
//                        if (entity.isAlive() && entity.level() instanceof ServerLevel serverLevel) {
//                            ((HitParticleType)EpicFightParticles.AIR_BURST.get()).spawnParticleWithArgument(serverLevel, entity, entity1);
//                            if (!entity.level().isClientSide() && entity.getServer() != null) {
//                                try {
//                                    entity.getServer().getCommands().getDispatcher().execute(
//                                            "effect give @s cgm:blinded 1 1 true",
//                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                } catch (CommandSyntaxException e) {
//
//                                }
//                            }
//
//                            if (dynamicanimation instanceof DashAttackAnimation) {
//                                if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.FIST) {
//                                    new DelayedTask(1) {
//                                        @Override
//                                        public void run() {
//                                            Entity entity2 = entity;
//
//                                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
//                                                try {
//                                                    entity2.getServer().getCommands().getDispatcher().execute(
//                                                            "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
//                                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                                } catch (CommandSyntaxException e) {
//
//                                                }
//                                            }
//
//                                            entity2 = entity;
//                                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
//                                                try {
//                                                    entity2.getServer().getCommands().getDispatcher().execute(
//                                                            "indestructible @s play \"annoyingvillagers:biped/combat/guard_break_attack\" 0 10",
//                                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                                } catch (CommandSyntaxException e) {
//
//                                                }
//                                            }
//
//                                            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.4D, 0.0D, entity.getLookAngle().z * -0.4D));
//                                        }
//                                    };
//                                }
//                            } else {
//                                new DelayedTask(1) {
//                                    @Override
//                                    public void run() {
//                                        Entity entity2 = entity;
//
//                                        if (!entity2.level().isClientSide() && entity2.getServer() != null) {
//                                            try {
//                                                entity2.getServer().getCommands().getDispatcher().execute(
//                                                        "indestructible @s play \"epicfight:biped/skill/guard_break1\" 0 10",
//                                                        entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                            } catch (CommandSyntaxException e) {
//
//                                            }
//                                        }
//                                    }
//                                };
//                            }
//                        }
//                    }
//                }
//            }
//
//            LivingEntityPatch<?> livingentitypatch1 = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
//
//            livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
//            if (livingentitypatch1 != null && livingentitypatch != null) {
//                dynamicanimation = livingentitypatch1.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
//                dynamicanimation1 = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
//                if (dynamicanimation instanceof KickAttackAnimation) {
//                    if (dynamicanimation1 instanceof AttackAnimation) {
//                        livingentitypatch1.playSound((SoundEvent) EpicFightSounds.NEUTRALIZE_MOBS.get(), -0.05F, 0.1F);
//                        if (entity.isAlive()) {
//                            if (!entity.level().isClientSide() && entity.getServer() != null) {
//                                try {
//                                    entity.getServer().getCommands().getDispatcher().execute(
//                                            "effect give @s cgm:blinded 1 1 true",
//                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                } catch (CommandSyntaxException e) {
//
//                                }
//                            }
//
//                            new DelayedTask(1) {
//                                @Override
//                                public void run() {
//                                    if (entity.isAlive()) {
//                                        Entity entity2 = entity;
//
//                                        if (!entity2.level().isClientSide() && entity2.getServer() != null) {
//                                            try {
//                                                entity2.getServer().getCommands().getDispatcher().execute(
//                                                        "indestructible @s play \"epicfight:biped/skill/guard_break1\" 0 10",
//                                                        entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                            } catch (CommandSyntaxException e) {
//
//                                            }
//                                        }
//                                    }
//                                }
//                            };
//                        }
//                    } else if (dynamicanimation instanceof DashAttackAnimation && dynamicanimation1 instanceof DashAttackAnimation) {
//                        if (event != null && event.isCancelable()) {
//                            event.setCanceled(true);
//                        }
//
//                        livingentitypatch1.playSound((SoundEvent) EpicFightSounds.NEUTRALIZE_MOBS.get(), -0.05F, 0.1F);
//                        if (!entity.level().isClientSide() && entity.getServer() != null) {
//                            try {
//                                entity.getServer().getCommands().getDispatcher().execute(
//                                        "particle epicfight:air_burst ~ ~1.5 ~ 0 0 0 8 1",
//                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                            } catch (CommandSyntaxException e) {
//
//                            }
//                        }
//
//                        if (livingentitypatch1.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.FIST) {
//                            if (!entity.level().isClientSide() && entity.getServer() != null) {
//                                try {
//                                    entity.getServer().getCommands().getDispatcher().execute(
//                                            "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
//                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                } catch (CommandSyntaxException e) {
//
//                                }
//                            }
//
//                            livingentitypatch1.playSound((SoundEvent) EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
//                        }
//
//                        entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
//                        entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
//                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -0.2D, 0.0D, entity.getLookAngle().z * -0.2D));
//                        entity1.setDeltaMovement(new Vec3(entity1.getLookAngle().x * -0.2D, 0.0D, entity1.getLookAngle().z * -0.2D));
//                        if (entity instanceof Player) {
//                            if (!entity.level().isClientSide() && entity.getServer() != null) {
//                                try {
//                                    entity.getServer().getCommands().getDispatcher().execute(
//                                            "effect give @s cgm:blinded 1 1 true",
//                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                    entity.getServer().getCommands().getDispatcher().execute(
//                                            "impactful @s shake 15 5 6",
//                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                } catch (CommandSyntaxException e) {
//
//                                }
//                            }
//                        } else if (!entity.level().isClientSide() && entity.getServer() != null) {
//                            try {
//                                entity.getServer().getCommands().getDispatcher().execute(
//                                        "execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100",
//                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                            } catch (CommandSyntaxException e) {
//
//                            }
//                        }
//
//                        if (entity1 instanceof Player) {
//                            if (!entity1.level().isClientSide() && entity1.getServer() != null) {
//                                try {
//                                    entity1.getServer().getCommands().getDispatcher().execute(
//                                            "impactful @s shake 15 5 6",
//                                            entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                    entity1.getServer().getCommands().getDispatcher().execute(
//                                            "effect give @s cgm:blinded 1 1 true",
//                                            entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                } catch (CommandSyntaxException e) {
//
//                                }
//                            }
//                        }
//
//                        if (entity.isAlive()) {
//                            new DelayedTask(1) {
//                                @Override
//                                public void run() {
//                                    Entity entity2 = entity;
//
//                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
//                                        try {
//                                            entity2.getServer().getCommands().getDispatcher().execute(
//                                                    "indestructible @s play \"annoyingvillagers:biped/combat/guard_break_attack\" 0 10",
//                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                        } catch (CommandSyntaxException e) {
//
//                                        }
//                                    }
//
//                                    entity2 = entity1;
//                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
//                                        try {
//                                            entity2.getServer().getCommands().getDispatcher().execute(
//                                                    "indestructible @s play \"annoyingvillagers:biped/combat/guard_break_attack\" 0 10",
//                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
//                                        } catch (CommandSyntaxException e) {
//
//                                        }
//                                    }
//                                }
//                            };
//                        }
//
//                        if (entity.level() instanceof ServerLevel serverLevel) {
//                            ((HitParticleType) EpicFightParticles.AIR_BURST.get()).spawnParticleWithArgument(serverLevel, entity, entity1);
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//}
