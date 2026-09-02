package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModEntityRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(RegisterRenderers registerrenderers) {
        registerrenderers.registerBlockEntityRenderer(AnnoyingVillagersModBlockEntities.FRACTURE_BLOCK.get(), FractureBlockRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(), HerobrineCloneRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON.get(), BlueDemonRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get(), ShadowHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get(), TransporterHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(), VillagerScoutRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), VillagerScoutCaptainRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(), BlueVillagerKnightRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(), GreenVillagerKnightRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(), RedVillagerKnightRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get(), PurpleVillagerKnightRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), ThrownItemRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.THROWN_POISON_EGG.get(), ThrownItemRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ENDER_AEGIS_PROJECTILE.get(), ThrownItemRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.VACUUM_SLICE.get(),VacuumSliceRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HOOK_GUN_HOOK.get(), HookGunHookRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ALEX.get(), AlexRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.JEV.get(), JevRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BBQ.get(), BbqRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.CHRIS.get(), ChrisRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.INFECTED_CHRIS.get(), InfectedChrisRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_CHRIS.get(), HerobrineChrisRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_7.get(), ShadowHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(), ArmoredHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.STEVE.get(), SteveRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ANGRY_STEVE.get(), AngrySteveRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.INFECTED_PLAYER_NPC.get(), InfectedPlayerNpcRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(), InfectedTheMostMoistBurrit0Renderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), ShadowHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(), EliteHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), EliteHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.PORTAL.get(), PortalEntityRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SNAKE_BLADE.get(), SnakeBladeRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(), EliteHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(), EliteHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), EliteHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.DRAGON_BEAM.get(), DragonBeamRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), EliteHerobrineKnockedRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), LowHerobrineCloneRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), LowShadowHerobrineCloneRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL.get(), NullRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_SWORD.get(), NullWeaponRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_AXE.get(), NullWeaponRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_PICKAXE.get(), NullWeaponRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_SHOVEL.get(), NullWeaponRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_HOE.get(), NullWeaponRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLOCK_PROJECTILE.get(), BlockProjectileRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_GREG.get(), HerobrineGregRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_WARDEN.get(), HerobrineWardenRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get(), HerobrineDragonRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.DRAGON_METEORITE.get(), DragonMeteoriteRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.OBSIDIAN_SLEDGEHAMMER_PROJECTILE.get(), ObsidianSledgehammerProjectileRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SHOCKWAVE_BLOCK.get(), ShockWaveBlockRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_SKELETON.get(), WitherSkeletonRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON_THUNDER_BEAM.get(), BlueDemonThunderBeamRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.TRIDENT_LIGHTNING_BOLT.get(), LightningBoltRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON_THROWN_TRIDENT.get(), ThrownTridentRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.DIAMOND_BOLT_PROJECTILE.get(), DiamondBoltProjectileRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ELECTRIC_AREA.get(), ElectricAreaRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLACK_FIRE.get(), BlackFireRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ENCHANTED_ARROW.get(), SpriteArrowRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ITEM_PROJECTILE.get(), ItemProjectileRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.FLYING_SHOCKWAVE.get(), FlyingShockwaveRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ELECTRIC_PHASE.get(), ElectricPhaseRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.RISING_WALL_BLOCK.get(), RisingWallBlockRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.FLOATING_LOOK_BLOCK.get(), FloatingLookBlockRenderer::new);
    }
}
