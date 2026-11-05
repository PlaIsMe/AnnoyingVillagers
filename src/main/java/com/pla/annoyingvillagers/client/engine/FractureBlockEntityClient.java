package com.pla.annoyingvillagers.client.engine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Client-only effects for the fracture block entity. */
public final class FractureBlockEntityClient {
    private FractureBlockEntityClient() {
    }

    public static void spawnTerrainParticle(Level level, BlockPos blockPos, BlockState originalBlockState) {
        Particle blockParticle = new TerrainParticle((ClientLevel) level, blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                0.0D, 0.0D, 0.0D, originalBlockState, blockPos);
        blockParticle.setParticleSpeed((Math.random() - 0.5D) * 0.3D, Math.random() * 0.5D,
                (Math.random() - 0.5D) * 0.3D);
        blockParticle.setLifetime(10 + level.getRandom().nextInt(60));
        Minecraft.getInstance().particleEngine.add(blockParticle);
    }
}
