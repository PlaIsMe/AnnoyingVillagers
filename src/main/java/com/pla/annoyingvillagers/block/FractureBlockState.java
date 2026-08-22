package com.pla.annoyingvillagers.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class FractureBlockState extends BlockState {
    private static final Int2ObjectMap<BlockState> ORIGINAL_BLOCK_STATE_CACHE = new Int2ObjectOpenHashMap<>();
    private Vector3f translate = new Vector3f();
    private Quaternionf rotation = new Quaternionf();
    private double bouncing;
    private int maxLifeTime;

    public FractureBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyMap, MapCodec<BlockState> mapCodec) {
        super(block, propertyMap, mapCodec);
    }

    public static void remove(BlockPos blockPos) {
        ORIGINAL_BLOCK_STATE_CACHE.remove(blockPos.hashCode());
    }

    public static void reset() {
        ORIGINAL_BLOCK_STATE_CACHE.clear();
    }

    public void setFractureInfo(BlockPos blockPos, BlockState originalState, Vector3f translate, Quaternionf rotation, double bouncing, int maxLifeTime) {
        ORIGINAL_BLOCK_STATE_CACHE.put(blockPos.hashCode(), originalState);
        this.translate = new Vector3f(translate);
        this.rotation = new Quaternionf(rotation);
        this.bouncing = bouncing;
        this.maxLifeTime = maxLifeTime;
    }

    public Vector3f getTranslate() { return this.translate; }
    public Quaternionf getRotation() { return this.rotation; }
    public BlockState getOriginalBlockState(BlockPos blockPos) { return ORIGINAL_BLOCK_STATE_CACHE.get(blockPos.hashCode()); }
    public double getBouncing() { return this.bouncing; }
    public int getLifeTime() { return this.maxLifeTime; }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState neighborState, Direction direction) {
        return false;
    }

    @Override
    public boolean supportsExternalFaceHiding() {
        return false;
    }

    @Override
    public int getLightEmission(BlockGetter level, BlockPos blockPos) {
        BlockState originalState = this.getOriginalBlockState(blockPos);
        return originalState == null ? this.owner.getLightEmission(this, level, blockPos) : originalState.getLightEmission(level, blockPos);
    }

    @Override
    public VoxelShape getShape(BlockGetter level, BlockPos blockPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockGetter level, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockGetter level, BlockPos blockPos) {
        BlockState originalState = this.getOriginalBlockState(blockPos);
        return originalState == null ? Shapes.empty() : originalState.getCollisionShape(level, blockPos, CollisionContext.empty());
    }

    @Override
    public VoxelShape getCollisionShape(BlockGetter level, BlockPos blockPos, CollisionContext collisionContext) {
        BlockState originalState = this.getOriginalBlockState(blockPos);
        return originalState == null ? Shapes.empty() : originalState.getCollisionShape(level, blockPos, collisionContext);
    }

    @Override
    public VoxelShape getVisualShape(BlockGetter level, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape() {
        return RenderShape.INVISIBLE;
    }
}
