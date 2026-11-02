package com.pla.annoyinglib.util;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.init.AnnoyingLibModItems;
import com.pla.annoyinglib.init.AnnoyingLibModSounds;
import com.pla.annoyinglib.item.DiamondAttractorSwordItem;
import com.pla.annoyinglib.item.DiamondBlasterSwordItem;
import com.pla.annoyinglib.item.HookedDiamondSwordItem;
import com.pla.annoyinglib.item.HookedGoldenSwordItem;
import com.pla.annoyinglib.item.HookedIronSwordItem;
import com.pla.annoyinglib.network.ClientboundDiamondAttractorFx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

/** Shared normal-weapon integration for the library rig and combat systems. */
public final class SharedWeaponHooks {
    private SharedWeaponHooks() {}


    public static boolean isHookedDiamondSwordItem(Object value) {
        return value instanceof HookedDiamondSwordItem;
    }

    public static boolean isBlackFireSwordItem(Object value) {
        return ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isBlackFireSwordItem(value);
    }

    public static boolean isDiamondAttractorSwordItem(Object value) {
        return value instanceof DiamondAttractorSwordItem;
    }

    public static boolean isDiamondBlasterSwordItem(Object value) {
        return value instanceof DiamondBlasterSwordItem;
    }

    public static boolean isHookedGoldenSwordItem(Object value) {
        return value instanceof HookedGoldenSwordItem;
    }

    public static boolean isHookedIronSwordItem(Object value) {
        return value instanceof HookedIronSwordItem;
    }

    public static void sendBlackFire(Level level, Entity entity) {
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.sendBlackFire(level, entity);
    }

    public static void sendDiamondAttractor(Level level, Entity entity) {
        com.pla.annoyinglib.util.AAAParticlesUtil.sendDiamondAttractor(level, entity);
    }

    public static void rigAction5(Mob mob) {
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.rigAction5(mob);
    }

    public static void rigAction6(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), AnnoyingLibModSounds.DIAMOND_ATTRACTOR.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
            com.pla.annoyinglib.AnnoyingLib.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundDiamondAttractorFx(mob));
            DiamondAttractorSwordItem.pullWeapons(mob);
        }
    }

    public static Vec3 blackFireSwordItem_getBlackFireFallbackPosition(Entity entity) {
        return ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.blackFireSwordItem_getBlackFireFallbackPosition(entity) : CommonUtil.getVanillaSwordOrBodyPosition(entity);
    }

    public static void blackFireSwordItem_startBlackFireFallback(Level level, int entityId) {
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.blackFireSwordItem_startBlackFireFallback(level, entityId);
    }

    public static Vec3 blackFireSwordItem_getSwordOrBodyPosition(Entity entity) {
        return CommonUtil.getVanillaSwordOrBodyPosition(entity);
    }

    public static Item getItems_JESSICA_THE_DARK_SHIELD() {
        return AnnoyingLibModItems.JESSICA_THE_DARK_SHIELD.get();
    }

    public static Item getItems_BLACK_FIRE_SWORD() {
        return ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.getItems_BLACK_FIRE_SWORD() : Items.AIR;
    }

    public static void handleBlackFire(Object packet) {
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.handleBlackFire(packet);
    }

    public static void handleDiamondAttractor(Object packet) { com.pla.annoyinglib.client.engine.ClientPacketHandlers.handleDiamondAttractor((ClientboundDiamondAttractorFx) packet); }
}
