package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.entity.PortalEntity;
import com.pla.annoyingvillagers.entity.SwordsmanHerobrineEntity;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HerobrineGregSixPortalSupportGoal extends AbstractHerobrinePortalActionGoal {
    private final HerobrineGregEntity greg;

    @Nullable
    private SwordsmanHerobrineEntity swordsman;

    public HerobrineGregSixPortalSupportGoal(HerobrineGregEntity greg) {
        super(greg, RigAnimationId.PORTAL_SUMMON);
        this.greg = greg;
    }

    @Override
    protected boolean canStartAction() {
        if (this.greg.getPortalActionCooldown() > 0) return false;

        LivingEntity support = this.greg.findGregFollowSupportHerobrine();
        if (!(support instanceof SwordsmanHerobrineEntity swordsman)) return false;
        if (swordsman.getState() != 2) return false;

        this.swordsman = swordsman;
        return true;
    }

    @Override
    protected void performAction() {
        if (this.swordsman == null || !this.swordsman.isAlive() || this.swordsman.isRemoved()) return;
        if (this.swordsman.getState() != 2) return;
        if (!(this.greg.level() instanceof ServerLevel serverLevel)) return;

        this.swordsman.setGregUUID(this.greg.getUUID());
        this.discardGregOwnedPortals(serverLevel);
        TransporterFragmentItem.PortalSpawnBatch portalBatch = TransporterFragmentItem.spawnPortalPairsBatch(serverLevel, this.greg, this.swordsman);
        if (portalBatch.spawned() < 6 || portalBatch.portalGroup() == null) {
            if (portalBatch.portalGroup() != null) this.discardPortalGroup(serverLevel, portalBatch.portalGroup());
            return;
        }

        DemoniacVoltageReaverItem.setPreferredPortalTarget(this.swordsman.getMainHandItem(), portalBatch.portalGroup(), this.greg.getUUID());
        this.greg.markSupportingHerobrine();
        this.greg.setPortalActionCooldown();
    }

    @Override
    protected int getActionTick() {
        return 20;
    }

    @Override
    protected LivingEntity getLookTarget() {
        return this.swordsman;
    }

    @Override
    protected void clearActionState() {
        this.swordsman = null;
    }

    private void discardGregOwnedPortals(ServerLevel serverLevel) {
        UUID gregUuid = this.greg.getUUID();
        for (Entity entity : serverLevel.getAllEntities()) {
            if (entity instanceof PortalEntity portal && gregUuid.equals(portal.getOwnerUUID()) && !portal.isRemoved()) portal.discard();
        }
    }

    private void discardPortalGroup(ServerLevel serverLevel, UUID portalGroupUuid) {
        UUID gregUuid = this.greg.getUUID();
        for (Entity entity : serverLevel.getAllEntities()) {
            if (entity instanceof PortalEntity portal && gregUuid.equals(portal.getOwnerUUID()) && portalGroupUuid.equals(portal.getPortalGroupUUID()) && !portal.isRemoved()) portal.discard();
        }
    }
}
