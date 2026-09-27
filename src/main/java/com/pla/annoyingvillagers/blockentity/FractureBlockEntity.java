package com.pla.annoyingvillagers.blockentity;

import com.pla.annoyingvillagers.block.FractureBlock;
import com.pla.annoyingvillagers.client.engine.FractureBlockEntityClient;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

    public void setFractureInfo(BlockState originalState, Vector3f translate, Quaternionf rotation, double bouncing, int maxLifeTime) {
        this.originalBlockState = originalState.getBlock() instanceof FractureBlock ? null : originalState;
        this.bouncing = bouncing;
        this.translate = new Vector3f(translate);
        this.rotation = new Quaternionf(rotation);
        this.maxLifeTime = maxLifeTime;
        this.lifeTime = 0;
        setChanged();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (originalBlockState != null) output.store("OriginalBlockState", BlockState.CODEC, originalBlockState);
        output.putFloat("TranslateX", translate.x());
        output.putFloat("TranslateY", translate.y());
        output.putFloat("TranslateZ", translate.z());
        output.putFloat("RotationX", rotation.x());
        output.putFloat("RotationY", rotation.y());
        output.putFloat("RotationZ", rotation.z());
        output.putFloat("RotationW", rotation.w());
        output.putDouble("Bouncing", bouncing);
        output.putInt("MaxLifeTime", maxLifeTime);
        output.putInt("LifeTime", lifeTime);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        BlockState original = input.read("OriginalBlockState", BlockState.CODEC).orElse(null);
        originalBlockState = original != null && !(original.getBlock() instanceof FractureBlock) ? original : null;
        translate = new Vector3f(input.getFloatOr("TranslateX", 0.0F), input.getFloatOr("TranslateY", 0.0F), input.getFloatOr("TranslateZ", 0.0F));
        rotation = new Quaternionf(input.getFloatOr("RotationX", 0.0F), input.getFloatOr("RotationY", 0.0F),
                input.getFloatOr("RotationZ", 0.0F), input.getFloatOr("RotationW", 1.0F));
        bouncing = input.getDoubleOr("Bouncing", 0.0D);
        maxLifeTime = input.getIntOr("MaxLifeTime", 0);
        lifeTime = input.getIntOr("LifeTime", 0);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        // Replay snapshots use chunk block-entity update tags, not just disk saves.
        return saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public BlockState getOriginalBlockState() { return this.originalBlockState; }
    public Vector3f getTranslate() { return this.translate; }
    public Quaternionf getRotation() { return this.rotation; }
    public double getBouncing() { return this.bouncing; }
    public int getMaxLifeTime() { return this.maxLifeTime; }
    public int getLifeTime() { return this.lifeTime; }

    public static void lifeTimeTick(Level level, BlockPos blockPos, BlockState blockState, FractureBlockEntity blockEntity) {
        if (blockEntity.originalBlockState == null) {
            // A chunk's block entity can tick before its update tag arrives.
            return;
        }

        if (!blockEntity.originalBlockState.isAir() && blockEntity.maxLifeTime - blockEntity.lifeTime < 10) {
            FractureBlockEntityClient.spawnTerrainParticle(level, blockPos, blockEntity.originalBlockState);
        }

        if (blockEntity.lifeTime++ > blockEntity.maxLifeTime) {
            level.setBlock(blockPos, blockEntity.originalBlockState, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
        }
    }
}
