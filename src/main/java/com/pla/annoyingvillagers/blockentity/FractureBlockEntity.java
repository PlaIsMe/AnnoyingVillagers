package com.pla.annoyingvillagers.blockentity;

import com.pla.annoyingvillagers.block.FractureBlockState;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class FractureBlockEntity extends BlockEntity {
    private Vector3f translate = new Vector3f();
    private Quaternionf rotation = new Quaternionf();
    private BlockState originalBlockState;
    private double bouncing;
    private int maxLifeTime;
    private int lifeTime;

    public FractureBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AnnoyingVillagersModBlockEntities.FRACTURE_BLOCK.get(), blockPos, blockState);
    }

    public FractureBlockEntity(BlockPos blockPos, BlockState blockState, FractureBlockState fractureBlockState) {
        super(AnnoyingVillagersModBlockEntities.FRACTURE_BLOCK.get(), blockPos, blockState);
        this.originalBlockState = fractureBlockState.getOriginalBlockState(blockPos);
        this.bouncing = fractureBlockState.getBouncing();
        this.translate = new Vector3f(fractureBlockState.getTranslate());
        this.rotation = new Quaternionf(fractureBlockState.getRotation());
        this.maxLifeTime = fractureBlockState.getLifeTime();
    }

    public BlockState getOriginalBlockState() { return this.originalBlockState; }
    public Vector3f getTranslate() { return this.translate; }
    public Quaternionf getRotation() { return this.rotation; }
    public double getBouncing() { return this.bouncing; }
    public int getMaxLifeTime() { return this.maxLifeTime; }
    public int getLifeTime() { return this.lifeTime; }

    @OnlyIn(Dist.CLIENT)
    public static void lifeTimeTick(Level level, BlockPos blockPos, BlockState blockState, FractureBlockEntity blockEntity) {
        if (blockEntity.originalBlockState == null) {
            level.removeBlockEntity(blockPos);
            FractureBlockState.remove(blockPos);
            return;
        }

        if (blockEntity.originalBlockState.shouldSpawnParticlesOnBreak() && blockEntity.maxLifeTime - blockEntity.lifeTime < 10) {
            Particle blockParticle = new TerrainParticle((ClientLevel) level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.0D, 0.0D, 0.0D, blockEntity.originalBlockState, blockPos);
            blockParticle.setParticleSpeed((Math.random() - 0.5D) * 0.3D, Math.random() * 0.5D, (Math.random() - 0.5D) * 0.3D);
            blockParticle.setLifetime(10 + level.random.nextInt(60));
            Minecraft.getInstance().particleEngine.add(blockParticle);
        }

        if (blockEntity.lifeTime++ > blockEntity.maxLifeTime) {
            level.removeBlockEntity(blockPos);
            FractureBlockState.remove(blockPos);
            level.setBlock(blockPos, blockEntity.originalBlockState, 0);
        }
    }
}
