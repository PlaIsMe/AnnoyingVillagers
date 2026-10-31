package com.pla.annoyinglib.init;

import com.pla.annoyinglib.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingLibModEntityRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(RegisterRenderers registerrenderers) {
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.DIAMOND_BOLT_PROJECTILE.get(), DiamondBoltProjectileRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.GOLEM_ARMS.get(), GolemArmsRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.BLOCK_PROJECTILE.get(), BlockProjectileRenderer::new);
        registerrenderers.registerBlockEntityRenderer(AnnoyingLibModBlockEntities.FRACTURE_BLOCK.get(), FractureBlockRenderer::new);

        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.BLUE_DEMON.get(), BlueDemonRenderer::new);


        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.VILLAGER_SCOUT.get(), VillagerScoutRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.VILLAGER_SCOUT_CAPTAIN.get(), VillagerScoutCaptainRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.BLUE_VILLAGER_KNIGHT.get(), BlueVillagerKnightRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.GREEN_VILLAGER_KNIGHT.get(), GreenVillagerKnightRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.RED_VILLAGER_KNIGHT.get(), RedVillagerKnightRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.PURPLE_VILLAGER_KNIGHT.get(), PurpleVillagerKnightRenderer::new);

        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.THROWN_POISON_EGG.get(), ThrownItemRenderer::new);


        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.HOOK_GUN_HOOK.get(), HookGunHookRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.ALEX.get(), AlexRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.JEV.get(), JevRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.BBQ.get(), BbqRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.CHRIS.get(), ChrisRenderer::new);



        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.STEVE.get(), SteveRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.ANGRY_STEVE.get(), AngrySteveRenderer::new);























        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.IRON_GOLEM_WARRIOR.get(), AvGolemRenderer::new);



        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.SHOCKWAVE_BLOCK.get(), ShockWaveBlockRenderer::new);


        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.BLUE_DEMON_THUNDER_BEAM.get(), BlueDemonThunderBeamRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.TRIDENT_LIGHTNING_BOLT.get(), LightningBoltRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.BLUE_DEMON_THROWN_TRIDENT.get(), ThrownTridentRenderer::new);

        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.ELECTRIC_AREA.get(), ElectricAreaRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.BLACK_FIRE.get(), BlackFireRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.ENCHANTED_ARROW.get(), SpriteArrowRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.ITEM_PROJECTILE.get(), ItemProjectileRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.FLYING_SHOCKWAVE.get(), FlyingShockwaveRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.ELECTRIC_PHASE.get(), ElectricPhaseRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.RISING_WALL_BLOCK.get(), RisingWallBlockRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.FLOATING_LOOK_BLOCK.get(), FloatingLookBlockRenderer::new);
    }
}
