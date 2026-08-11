package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkillDataKeys;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.item.NullWeaponItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.SkillCastEvent;

import java.util.*;

public class NullWeaponSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("08b6bf0d-2fbe-4b7a-87da-ad4c4ebb9597");
    private static final int MAX_NULL_WEAPON_STACKS = 5;
    public static final List<String> NULL_WEAPON_KEYS = List.of(
            "NullPickaxeUUID",
            "NullAxeUUID",
            "NullHoeUUID",
            "NullShovelUUID",
            "NullSwordUUID"
    );

    public static NullWeapon pickRandomNullWeapon(ServerLevel serverLevel, CompoundTag data, RandomSource rand) {
        List<NullWeapon> candidates = new ArrayList<>();

        for (String key : NULL_WEAPON_KEYS) {
            if (!data.hasUUID(key)) continue;

            Entity entity = serverLevel.getEntity(data.getUUID(key));
            if (entity instanceof NullWeapon nullWeapon && nullWeapon.isAlive() && !nullWeapon.isRemoved()) {
                candidates.add(nullWeapon);
            }
        }

        if (candidates.isEmpty()) return null;
        return candidates.get(rand.nextInt(candidates.size()));
    }

    public NullWeaponSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!skillContainer.isActivated()) {
            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F);
            Player player = skillContainer.getExecutor().getOriginal();

            if (player.level() instanceof ServerLevel serverLevel) {
                CompoundTag data = player.getPersistentData();
                int releaseStacks = getReleaseStackCount(skillContainer);
                if (releaseStacks > 0) {
                    releaseAvailableWeapons(serverLevel, data, releaseStacks);
                } else {
                    releaseTrackedWeapons(serverLevel, data);
                }
                skillContainer.getDataManager().setDataSync(AVSkillDataKeys.NULL_WEAPON_RELEASE_STACKS.get(), 0);
            }
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
        }
    }

    @Override
    public boolean resourcePredicate(PlayerPatch<?> playerPatch, SkillCastEvent event) {
        if (!(playerPatch instanceof ServerPlayerPatch serverPatch)) {
            return true;
        }

        SkillContainer container = serverPatch.getSkill(AVSkills.NULL_WEAPON);
        if (container == null || container.getSkill() != this) {
            return false;
        }

        Player player = serverPatch.getOriginal();
        int available = getClampedStack(container.getStack());
        container.getDataManager().setDataSync(AVSkillDataKeys.NULL_WEAPON_RELEASE_STACKS.get(), available);

        if (player.level() instanceof ServerLevel serverLevel) {
            syncWeaponsForStack(serverLevel, player, player.getPersistentData(), available);
        }

        if (player.isCreative()) {
            return true;
        }

        if (available <= 0) {
            return false;
        }

        Skill.setSkillStackSynchronize(container, 0);
        Skill.setSkillConsumptionSynchronize(container, 0.0F);
        return true;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().setDataSync(AVSkillDataKeys.NULL_WEAPON_RELEASE_STACKS.get(), 0);
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID, (pre) -> {
            DamageSource damageSource = pre.getDamageSource();
            if (!damageSource.is(DamageTypes.MAGIC)
                    && !damageSource.is(DamageTypes.EXPLOSION)
                    && !damageSource.is(DamageTypes.ON_FIRE)
                    && !damageSource.is(DamageTypes.IN_FIRE)
                    && !damageSource.is(DamageTypes.FALL)
                    && !damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)
                    && !damageSource.is(DamageTypes.DROWN)) {
                Player player = pre.getPlayerPatch().getOriginal();

                if (player.level() instanceof ServerLevel serverLevel) {
                    CompoundTag data = player.getPersistentData();
                    if (new Random().nextFloat() > (container.isActivated() ? 0.5F : 0.25F)) return;
                    NullWeapon nullWeapon = pickRandomNullWeapon(serverLevel, data, player.getRandom());

                    if (nullWeapon != null) {
                        nullWeapon.moveTo(player.getX(), player.getY(), player.getZ(), nullWeapon.getYRot(), nullWeapon.getXRot());
                        pre.setCanceled(true);
                        pre.setResult(AttackResult.ResultType.BLOCKED);

                        EpicfightUtil.damageBlocked(pre.getDamageSource(), player, serverLevel);
                        nullWeapon.spinfor5seconds();

                        EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(
                                serverLevel,
                                HitParticleType.FRONT_OF_EYES,
                                HitParticleType.ZERO,
                                player,
                                pre.getDamageSource().getEntity()
                        );
                    }
                }
            }
        });
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID, (event) -> {
                    Player player = container.getExecutor().getOriginal();
                    Skill skill = event.getSkillContainer().getSkill();

                    if (skill.getCategory() == SkillCategories.GUARD){
                        for (String key : NULL_WEAPON_KEYS) {
                            if (player.getPersistentData().hasUUID(key) && player.level() instanceof ServerLevel serverLevel) {
                                UUID uuid = player.getPersistentData().getUUID(key);
                                Entity entity = serverLevel.getEntity(uuid);

                                if (entity instanceof NullWeapon nullWeapon
                                        && !nullWeapon.isReleased()) {
                                    nullWeapon.setSpinning(true);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        if (!container.getExecutor().isLogicalClient()
                && container.getExecutor().getOriginal().level() instanceof ServerLevel serverLevel) {
            killOwnedNullSkeletons(serverLevel, container.getExecutor().getOriginal());
        }
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID);
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        Player player = container.getExecutor().getOriginal();
        container.getDataManager().setDataSync(AVSkillDataKeys.NULL_WEAPON_RELEASE_STACKS.get(), 0);

        if (player.level() instanceof ServerLevel serverLevel) {
            killOwnedNullSkeletons(serverLevel, player);
        }

        for (String key : NULL_WEAPON_KEYS) {
            if (player.getPersistentData().hasUUID(key) && player.level() instanceof ServerLevel serverLevel) {
                UUID uuid = player.getPersistentData().getUUID(key);
                Entity entity = serverLevel.getEntity(uuid);

                if (entity instanceof NullWeapon nullWeapon) {
                    nullWeapon.setReleased(false);
                }
            }
        }
        super.cancelOnServer(container, args);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    private static void removeTrackedEntityIfWrongType(ServerLevel serverLevel, CompoundTag persistentData, String uuidKey, Class<? extends Entity> expectedClass) {
        if (!persistentData.hasUUID(uuidKey)) {
            return;
        }

        Entity trackedEntity = serverLevel.getEntity(persistentData.getUUID(uuidKey));
        if (trackedEntity == null || !expectedClass.isInstance(trackedEntity) || !trackedEntity.isAlive() || trackedEntity.isRemoved()) {
            persistentData.remove(uuidKey);
        }
    }

    private static int getClampedStack(int stack) {
        return Math.max(0, Math.min(MAX_NULL_WEAPON_STACKS, stack));
    }

    private static int getReleaseStackCount(SkillContainer container) {
        Integer releaseStacks = container.getDataManager().getDataValue(AVSkillDataKeys.NULL_WEAPON_RELEASE_STACKS.get());
        return getClampedStack(releaseStacks == null ? 0 : releaseStacks);
    }

    private static NullWeapon getTrackedWeapon(ServerLevel serverLevel, CompoundTag data, String key) {
        if (!data.hasUUID(key)) {
            return null;
        }

        Entity entity = serverLevel.getEntity(data.getUUID(key));
        if (entity instanceof NullWeapon nullWeapon && nullWeapon.isAlive() && !nullWeapon.isRemoved()) {
            return nullWeapon;
        }

        data.remove(key);
        return null;
    }

    private static void releaseAvailableWeapons(ServerLevel serverLevel, CompoundTag data, int stack) {
        int available = getClampedStack(stack);
        for (int i = 0; i < available; i++) {
            NullWeapon nullWeapon = getTrackedWeapon(serverLevel, data, NULL_WEAPON_KEYS.get(i));
            if (nullWeapon != null) {
                nullWeapon.setReleased(true);
            }
        }
    }

    private static void releaseTrackedWeapons(ServerLevel serverLevel, CompoundTag data) {
        for (String key : NULL_WEAPON_KEYS) {
            NullWeapon nullWeapon = getTrackedWeapon(serverLevel, data, key);
            if (nullWeapon != null) {
                nullWeapon.setReleased(true);
            }
        }
    }

    private static void removeTrackedWeaponIfIdle(ServerLevel serverLevel, CompoundTag data, String key) {
        if (!data.hasUUID(key)) {
            return;
        }

        Entity entity = serverLevel.getEntity(data.getUUID(key));
        if (entity instanceof NullWeapon nullWeapon && nullWeapon.isAlive() && !nullWeapon.isRemoved()) {
            if (!nullWeapon.isReleased()) {
                nullWeapon.remove(Entity.RemovalReason.KILLED);
                data.remove(key);
            }
            return;
        }

        data.remove(key);
    }

    private static void syncWeaponForStack(ServerLevel serverLevel, Player player, CompoundTag data, int available,
                                           int requiredStack, String key,
                                           EntityType<? extends NullWeapon> type,
                                           Class<? extends Entity> expectedClass) {
        if (available >= requiredStack) {
            ensureWeapon(serverLevel, player, data, key, type, expectedClass);
        } else {
            removeTrackedWeaponIfIdle(serverLevel, data, key);
        }
    }

    private static void syncWeaponsForStack(ServerLevel serverLevel, Player player, CompoundTag data, int stack) {
        int available = getClampedStack(stack);
        syncWeaponForStack(serverLevel, player, data, available, 1, "NullPickaxeUUID", AnnoyingVillagersModEntities.NULL_PICKAXE.get(), NullPickaxeEntity.class);
        syncWeaponForStack(serverLevel, player, data, available, 2, "NullAxeUUID", AnnoyingVillagersModEntities.NULL_AXE.get(), NullAxeEntity.class);
        syncWeaponForStack(serverLevel, player, data, available, 3, "NullHoeUUID", AnnoyingVillagersModEntities.NULL_HOE.get(), NullHoeEntity.class);
        syncWeaponForStack(serverLevel, player, data, available, 4, "NullShovelUUID", AnnoyingVillagersModEntities.NULL_SHOVEL.get(), NullShovelEntity.class);
        syncWeaponForStack(serverLevel, player, data, available, 5, "NullSwordUUID", AnnoyingVillagersModEntities.NULL_SWORD.get(), NullSwordEntity.class);
    }

    private static void killOwnedNullSkeletons(ServerLevel serverLevel, Player player) {
        serverLevel.getEntitiesOfClass(
                NullSkeletonEntity.class,
                player.getBoundingBox().inflate(128.0D),
                nullSkeleton -> nullSkeleton.isOwnedBy(player)
        ).forEach(NullSkeletonEntity::killForDeathAnimation);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (container.getExecutor().getValidItemInHand(InteractionHand.MAIN_HAND) != null) {
            LivingEntityPatch<?> livingEntityPatch = container.getExecutor();
            if (livingEntityPatch == null) return;
            if (livingEntityPatch.getAnimator() == null) return;
            if (livingEntityPatch.getArmature() == null) return;
            if (Armatures.BIPED.get() == null || Armatures.BIPED.get().toolL == null) return;
            if (livingEntityPatch.getOriginal() == null) return;

            byte poseSampleCount = 3;
            float poseStep = 1.0F / (float) (poseSampleCount - 1);
            float poseProgress = 0.0F;

            OpenMatrix4f toolLeftTransform;
            int particleIndex;
            int poseSampleIndex;
            OpenMatrix4f jointTransform;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                toolLeftTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().toolL
                );
                toolLeftTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        toolLeftTransform,
                        toolLeftTransform
                );

                for (particleIndex = 0; particleIndex < 1; ++particleIndex) {
                    livingEntityPatch.getOriginal().level().addParticle(
                            AnnoyingVillagersModParticleTypes.NULL.get(),
                            (double) toolLeftTransform.m30 + livingEntityPatch.getOriginal().getX(),
                            (double) toolLeftTransform.m31 + ((Player) livingEntityPatch.getOriginal()).getY(),
                            (double) toolLeftTransform.m32 + ((Player) livingEntityPatch.getOriginal()).getZ(),
                            ((new Random()).nextFloat() - 0.5F) * 0.15F,
                            ((new Random()).nextFloat() - 0.5F) * 0.15F,
                            ((new Random()).nextFloat() - 0.5F) * 0.15F
                    );
                }

                for (particleIndex = 0; particleIndex < 1; ++particleIndex) {
                    livingEntityPatch.getOriginal().level().addParticle(
                            AnnoyingVillagersModParticleTypes.NULL.get(),
                            (double) toolLeftTransform.m30 + livingEntityPatch.getOriginal().getX(),
                            (double) toolLeftTransform.m31 + ((Player) livingEntityPatch.getOriginal()).getY(),
                            (double) toolLeftTransform.m32 + ((Player) livingEntityPatch.getOriginal()).getZ(),
                            0.0D, 0.0D, 0.0D
                    );
                }

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().toolR
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 1.8F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, -((new Random()).nextFloat() * 4.0F)));

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX(),
                        (double) jointTransform.m31 + ((Player) livingEntityPatch.getOriginal()).getY(),
                        (double) jointTransform.m32 + ((Player) livingEntityPatch.getOriginal()).getZ(),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );
                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX(),
                        (double) jointTransform.m31 + ((Player) livingEntityPatch.getOriginal()).getY(),
                        (double) jointTransform.m32 + ((Player) livingEntityPatch.getOriginal()).getZ(),
                        0.0D, 0.0D, 0.0D
                );

                poseProgress += poseStep;
            }

            for (particleIndex = 0; particleIndex < 14; ++particleIndex) {
                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        livingEntityPatch.getOriginal().getX(),
                        livingEntityPatch.getOriginal().getY() + 0.029999999329447746D,
                        livingEntityPatch.getOriginal().getZ(),
                        ((new Random()).nextFloat() - 0.5F) * 0.65F,
                        ((new Random()).nextFloat() - 0.5F) * 0.05F,
                        ((new Random()).nextFloat() - 0.5F) * 0.65F
                );
            }

            poseStep = 1.0F;
            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().head
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() + 0.1F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().chest
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().armL
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().armR
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().torso
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().thighL
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().thighR
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().legL
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = livingEntityPatch.getArmature().getBoundTransformFor(
                        livingEntityPatch.getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().legR
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                livingEntityPatch.getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + livingEntityPatch.getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + livingEntityPatch.getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + livingEntityPatch.getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }
        }

        if (container.getExecutor().isLogicalClient()) return;

        ServerPlayerPatch serverPlayerPatch = container.getServerExecutor();
        Player player = serverPlayerPatch.getOriginal();
        ServerLevel serverLevel = (ServerLevel) player.level();
        CompoundTag data = player.getPersistentData();

        if (container.isActivated() && container.getRemainDuration() <= 0) {
            this.cancelOnServer(container, null);
            return;
        }

        if (player.tickCount >= 40 && player.tickCount % 10 == 0) {
            if (!container.isActivated()) {
                killOwnedNullSkeletons(serverLevel, player);
            }

            if (!container.isActivated()
                    && container.getExecutor().getOriginal().getMainHandItem().getItem() instanceof NullWeaponItem) {
                syncWeaponsForStack(serverLevel, player, data, getClampedStack(container.getStack()));
            }

            for (String key : NULL_WEAPON_KEYS) {
                teleportWeapon(key, serverLevel, data);
            }
        }
    }

    private static void ensureWeapon(ServerLevel level, Player player, CompoundTag data,
                                     String key,
                                     EntityType<? extends NullWeapon> type,
                                     Class<? extends Entity> expectedClass) {
        removeTrackedEntityIfWrongType(level, data, key, expectedClass);
        if (data.hasUUID(key)) return;
        NullWeapon nullWeapon = type.create(level);
        if (nullWeapon == null) return;

        nullWeapon.summonNullWeaponForPlayer(key, level, player);
    }

    private void teleportWeapon(String uuidNbt, ServerLevel serverLevel, CompoundTag compoundTag) {
        if (compoundTag.hasUUID(uuidNbt)) {
            Entity entity = serverLevel.getEntity(compoundTag.getUUID(uuidNbt));
            if (entity instanceof NullWeapon nullWeapon && nullWeapon.isAlive() && !nullWeapon.isRemoved()) {
                nullWeapon.processTeleportByPlayer();
            } else {
                compoundTag.remove(uuidNbt);
            }
        }
    }
}
