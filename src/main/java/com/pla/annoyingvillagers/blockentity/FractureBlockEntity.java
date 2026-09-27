package com.pla.annoyingvillagers.blockentity;

import com.pla.annoyingvillagers.block.FractureBlock;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (originalBlockState != null) tag.put("OriginalBlockState", NbtUtils.writeBlockState(originalBlockState));
        tag.putFloat("TranslateX", translate.x());
        tag.putFloat("TranslateY", translate.y());
        tag.putFloat("TranslateZ", translate.z());
        tag.putFloat("RotationX", rotation.x());
        tag.putFloat("RotationY", rotation.y());
        tag.putFloat("RotationZ", rotation.z());
        tag.putFloat("RotationW", rotation.w());
        tag.putDouble("Bouncing", bouncing);
        tag.putInt("MaxLifeTime", maxLifeTime);
        tag.putInt("LifeTime", lifeTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        BlockState original = tag.contains("OriginalBlockState", Tag.TAG_COMPOUND)
                ? NbtUtils.readBlockState(registries.lookupOrThrow(Registries.BLOCK), tag.getCompound("OriginalBlockState")) : null;
        originalBlockState = original != null && !(original.getBlock() instanceof FractureBlock) ? original : null;
        translate = new Vector3f(tag.getFloat("TranslateX"), tag.getFloat("TranslateY"), tag.getFloat("TranslateZ"));
        rotation = tag.contains("RotationW", Tag.TAG_ANY_NUMERIC)
                ? new Quaternionf(tag.getFloat("RotationX"), tag.getFloat("RotationY"), tag.getFloat("RotationZ"), tag.getFloat("RotationW"))
                : new Quaternionf();
        bouncing = tag.getDouble("Bouncing");
        maxLifeTime = tag.getInt("MaxLifeTime");
        lifeTime = tag.getInt("LifeTime");
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

    @OnlyIn(Dist.CLIENT)
    public static void lifeTimeTick(Level level, BlockPos blockPos, BlockState blockState, FractureBlockEntity blockEntity) {
        if (blockEntity.originalBlockState == null) {
            // A chunk's block entity can tick before its update tag arrives.
            return;
        }

        if (!blockEntity.originalBlockState.isAir() && blockEntity.maxLifeTime - blockEntity.lifeTime < 10) {
            Particle blockParticle = new TerrainParticle((ClientLevel) level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.0D, 0.0D, 0.0D, blockEntity.originalBlockState, blockPos);
            blockParticle.setParticleSpeed((Math.random() - 0.5D) * 0.3D, Math.random() * 0.5D, (Math.random() - 0.5D) * 0.3D);
            blockParticle.setLifetime(10 + level.random.nextInt(60));
            Minecraft.getInstance().particleEngine.add(blockParticle);
        }

        if (blockEntity.lifeTime++ > blockEntity.maxLifeTime) {
            level.setBlock(blockPos, blockEntity.originalBlockState, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
        }
    }
}
