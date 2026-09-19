package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.particle.*;
import com.pla.annoyingvillagers.client.particle.smoke_wave.SmokeWaveParticle;
import net.minecraft.client.particle.SmokeParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(value = Dist.CLIENT)
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
        event.registerSpriteSet(AnnoyingVillagersModParticleTypes.SMOKE_WAVE.get(), SmokeWaveParticle.Provider::new);
        event.registerSpecial(AnnoyingVillagersModParticleTypes.GROUND_SLAM.get(), new GroundSlamParticle.Provider());
        event.registerSpecial(AnnoyingVillagersModParticleTypes.WHITE_AFTERIMAGE.get(), new WhiteAfterimageParticle.Provider());
    }
}
