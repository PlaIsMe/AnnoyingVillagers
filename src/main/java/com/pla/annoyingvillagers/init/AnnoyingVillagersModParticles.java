package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.particle.*;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AnnoyingVillagersModParticles {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(), ElectricSparkParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.SPARK.get(), SparkParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.PE.get(), PeParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.GLOWINGEYES.get(), GlowingEyesParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.LIGHT.get(), LightParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.ENDER.get(), EnderParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.NULL.get(), SmokeParticle.Provider::new);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.FULL_COWL.get(), FullCowlParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.METEORITE_TRAIL.get(), MeteoriteTrailParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.BIG_SPLASH.get(), BigSplashParticle::provider);
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.HIT_BLUNT.get(), HitBluntParticle::provider);
        event.registerSpecial(AnnoyingVillagersModParticleTypes.GROUND_SLAM.get(), new GroundSlamParticle.Provider());
        event.registerSpecial(AnnoyingVillagersModParticleTypes.WHITE_AFTERIMAGE.get(), new WhiteAfterimageParticle.Provider());
    }
}