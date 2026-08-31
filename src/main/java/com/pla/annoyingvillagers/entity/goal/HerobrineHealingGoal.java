package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class HerobrineHealingGoal extends Goal {
    private static final float HEALING_HEALTH_THRESHOLD = 0.5F;

    private final HerobrineMob mob;

    public HerobrineHealingGoal(HerobrineMob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (this.mob.level().isClientSide || !this.mob.isAlive() || this.mob.isRemoved() || this.mob.isDeadOrDying()) return false;
        if (this.mob.isSacrificing() || this.mob.isHealing() || this.mob.getHealingCooldown() > 0) return false;

        // Only start healing when health is below 50%.
        if (this.mob.getHealth() >= this.mob.getMaxHealth() * HEALING_HEALTH_THRESHOLD) return false;

        List<Entity> healingClones = getAvailableHealingClones();

        // Any Herobrine may use an existing available healer.
        if (!healingClones.isEmpty()) return true;

        // Only these elite/special Herobrines may create a new healer.
        return canSummonHealingClone(this.mob);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        if (!(this.mob.level() instanceof ServerLevel serverLevel)) return;
        if (this.mob.getHealth() >= this.mob.getMaxHealth() * HEALING_HEALTH_THRESHOLD) return;

        List<Entity> healingClones = getAvailableHealingClones();

        Entity chosen;
        if (!healingClones.isEmpty()) {
            chosen = healingClones.get(this.mob.getRandom().nextInt(healingClones.size()));
        } else {
            if (!canSummonHealingClone(this.mob)) return;
            chosen = spawnHealingClone(serverLevel);
        }

        if (!startHealingWith(chosen)) {
            this.mob.setHealing(false);
            return;
        }

        this.mob.setHealing(true);
        chosen.playSound(AnnoyingVillagersModSounds.HEROBRINE_UNDERSTOOD.get(),1.0F,1.0F);
    }

    private List<Entity> getAvailableHealingClones() {
        List<Entity> available = new ArrayList<>();

        for (Entity entity : this.mob.getAliveBoundPossessedHerobrines()) {
            if (entity instanceof LowShadowHerobrineCloneEntity lowShadow) {
                if (!lowShadow.isHealing()) available.add(lowShadow);
                continue;
            }

            if (entity instanceof LowHerobrineCloneEntity low) {
                if (!low.isHealing()) available.add(low);
            }
        }

        return available;
    }

    private Entity spawnHealingClone(ServerLevel serverLevel) {
        double radius = 3.0D + this.mob.getRandom().nextDouble() * 3.0D;
        double angle = this.mob.getRandom().nextDouble() * Math.PI * 2.0D;
        double x = this.mob.getX() + Math.cos(angle) * radius;
        double z = this.mob.getZ() + Math.sin(angle) * radius;
        BlockPos xz = BlockPos.containing(x,0.0D,z);
        int y = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,xz).getY();
        Vec3 spawnPos = new Vec3(x,y,z);

        Entity spawned;

        if (this.mob.getRandom().nextBoolean()) {
            LowHerobrineCloneEntity low = new LowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(),serverLevel);
            low.moveTo(spawnPos.x,spawnPos.y,spawnPos.z,this.mob.getYRot(),this.mob.getXRot());
            low.setPossessedByEntity(this.mob);
            low.setRenderPortal(false);
            low.setPossessedByUuid(this.mob.getUUID());
            low.setNoAi(true);
            TeamUtil.addOrJoinTeam(low,"herobrine");
            spawned = low;
        } else {
            LowShadowHerobrineCloneEntity low = new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(),serverLevel);
            low.moveTo(spawnPos.x,spawnPos.y,spawnPos.z,this.mob.getYRot(),this.mob.getXRot());
            low.setPossessedByEntity(this.mob);
            low.setRenderPortal(false);
            low.setPossessedByUuid(this.mob.getUUID());
            low.setNoAi(true);
            TeamUtil.addOrJoinTeam(low,"herobrine");
            spawned = low;
        }

        if (!serverLevel.addFreshEntity(spawned)) return null;

        if (!this.mob.boundPossessed(spawned)) {
            spawned.discard();
            return null;
        }

        return spawned;
    }

    private boolean startHealingWith(Entity chosen) {
        if (chosen instanceof LowShadowHerobrineCloneEntity lowShadow) {
            if (lowShadow.isHealing()) return false;

            lowShadow.setPossessedByEntity(this.mob);
            lowShadow.setPossessedByUuid(this.mob.getUUID());
            lowShadow.setSacrificing(false);
            lowShadow.setHealing(true);
            lowShadow.setNoAi(true);
            return true;
        }

        if (chosen instanceof LowHerobrineCloneEntity low) {
            if (low.isHealing()) return false;

            low.setPossessedByEntity(this.mob);
            low.setPossessedByUuid(this.mob.getUUID());
            low.setHealing(true);
            low.setNoAi(true);
            return true;
        }

        return false;
    }

    private static boolean canSummonHealingClone(HerobrineMob mob) {
        return mob instanceof AegisHerobrineEntity
                || mob instanceof SwordsmanHerobrineEntity
                || mob instanceof SledgehammerHerobrineEntity
                || mob instanceof ReaperHerobrineEntity
                || mob instanceof GlaiveHerobrineEntity
                || mob instanceof NullEntity
                || mob instanceof ShadowHerobrineEntity;
    }
}