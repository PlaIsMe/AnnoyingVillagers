package com.pla.annoyingvillagers.blockentity;

import com.pla.annoyingvillagers.block.FractureBlockState;
import com.pla.annoyingvillagers.client.engine.FractureBlockEntityClient;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
        FractureBlockState.Data data = FractureBlockState.take(blockPos);
        if (data != null) {
            this.originalBlockState = data.originalState();
            this.bouncing = data.bouncing();
            this.translate = new Vector3f(data.translate());
            this.rotation = new Quaternionf(data.rotation());
            this.maxLifeTime = data.maxLifeTime();
        }
    }

    public BlockState getOriginalBlockState() { return this.originalBlockState; }
    public Vector3f getTranslate() { return this.translate; }
    public Quaternionf getRotation() { return this.rotation; }
    public double getBouncing() { return this.bouncing; }
    public int getMaxLifeTime() { return this.maxLifeTime; }
    public int getLifeTime() { return this.lifeTime; }

    public static void lifeTimeTick(Level level, BlockPos blockPos, BlockState blockState, FractureBlockEntity blockEntity) {
        if (blockEntity.originalBlockState == null) {
            level.removeBlockEntity(blockPos);
            FractureBlockState.remove(blockPos);
            return;
        }

        if (!blockEntity.originalBlockState.isAir() && blockEntity.maxLifeTime - blockEntity.lifeTime < 10) {
            FractureBlockEntityClient.spawnTerrainParticle(level, blockPos, blockEntity.originalBlockState);
        }

        if (blockEntity.lifeTime++ > blockEntity.maxLifeTime) {
            level.removeBlockEntity(blockPos);
            FractureBlockState.remove(blockPos);
            level.setBlock(blockPos, blockEntity.originalBlockState, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
        }
    }
}
