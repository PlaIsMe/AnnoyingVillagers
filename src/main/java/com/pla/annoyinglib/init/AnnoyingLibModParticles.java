package com.pla.annoyinglib.init;

import com.pla.annoyinglib.client.particle.*;
import com.pla.annoyinglib.client.particle.smoke_wave.SmokeWaveParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AnnoyingLibModParticles {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AnnoyingLibModParticleTypes.SPARK.get(), SparkParticle::provider);

        event.registerSpriteSet(AnnoyingLibModParticleTypes.BIG_SPLASH.get(), BigSplashParticle::provider);
        event.registerSpriteSet(AnnoyingLibModParticleTypes.HIT_BLUNT.get(), HitBluntParticle::provider);
        event.registerSpriteSet(AnnoyingLibModParticleTypes.SMOKE_WAVE.get(), SmokeWaveParticle.Provider::new);
        event.registerSpecial(AnnoyingLibModParticleTypes.GROUND_SLAM.get(), new GroundSlamParticle.Provider());
        event.registerSpecial(AnnoyingLibModParticleTypes.WHITE_AFTERIMAGE.get(), new WhiteAfterimageParticle.Provider());
    }
}
