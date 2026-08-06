package com.pla.annoyingvillagers.client.particle.airflow;

import com.pla.annoyingvillagers.client.particle.AwakenJointFollowBaseParticle;
import com.pla.annoyingvillagers.client.particle.AwakenJointFollowParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AwakenAirflowParticle extends AwakenJointFollowBaseParticle {
    public AwakenAirflowParticle(ClientLevel level, double x, double y, double z,
                                 double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        this.lifetime = 16;
        this.quadSize = 2.0F;
        this.alpha = 0.4F;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<AwakenJointFollowParticleOptions> {
        @Override
        public Particle createParticle(@NotNull AwakenJointFollowParticleOptions options, @NotNull ClientLevel level,
                                       double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AwakenAirflowParticle particle = new AwakenAirflowParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
            particle.entityId = options.getEntityId();
            particle.jointId = options.getJointId();
            particle.offset = options.getOffset();
            return particle;
        }
    }
}
