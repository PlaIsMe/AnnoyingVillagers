package com.pla.annoyingvillagers.client.engine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.util.*;

public class ThunderRender {

    private static final float REFRESH_TIME = 3F;
    private static final double MAX_OWNER_TRACK_TIME = 100;

    private Timestamp refreshTimestamp = new Timestamp();

    private final Random random = new Random();
    private final Minecraft minecraft = Minecraft.getInstance();

    private final Map<Object, BoltOwnerData> boltOwners = new Object2ObjectOpenHashMap<>();

    public void render(float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn) {
        VertexConsumer buffer = bufferIn.getBuffer(RenderType.lightning());
        Matrix4f matrix = matrixStackIn.last().pose();
        assert minecraft.level != null;
        Timestamp timestamp = new Timestamp(minecraft.level.getGameTime(), partialTicks);
        boolean refresh = timestamp.isPassed(refreshTimestamp, (1 / REFRESH_TIME));
        if (refresh) {
            refreshTimestamp = timestamp;
        }
        for (Iterator<Map.Entry<Object, BoltOwnerData>> iter = boltOwners.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<Object, BoltOwnerData> entry = iter.next();
            BoltOwnerData data = entry.getValue();
            if (refresh) {
                data.bolts.removeIf(bolt -> bolt.tick(timestamp));
            }
            if (data.bolts.isEmpty() && data.lastBolt != null && data.lastBolt.getSpawnFunction().isConsecutive()) {
                data.addBolt(new ThunderInstance(data.lastBolt, timestamp), timestamp);
            }
            data.bolts.forEach(bolt -> bolt.render(matrix, buffer, timestamp));

            if (data.bolts.isEmpty() && timestamp.isPassed(data.lastUpdateTimestamp, MAX_OWNER_TRACK_TIME)) {
                iter.remove();
            }
        }
    }

    public void update(Object owner, ThunderData newBoltData, float partialTicks) {
        if (minecraft.level == null) {
            return;
        }
        BoltOwnerData data = boltOwners.computeIfAbsent(owner, o -> new BoltOwnerData());
        data.lastBolt = newBoltData;
        Timestamp timestamp = new Timestamp(minecraft.level.getGameTime(), partialTicks);
        if ((!data.lastBolt.getSpawnFunction().isConsecutive() || data.bolts.isEmpty()) && timestamp.isPassed(data.lastBoltTimestamp, data.lastBoltDelay)) {
            data.addBolt(new ThunderInstance(newBoltData, timestamp), timestamp);
        }
        data.lastUpdateTimestamp = timestamp;
    }

    public class BoltOwnerData {

        private final Set<ThunderInstance> bolts = new ObjectOpenHashSet<>();
        private ThunderData lastBolt;
        private Timestamp lastBoltTimestamp = new Timestamp();
        private Timestamp lastUpdateTimestamp = new Timestamp();
        private double lastBoltDelay;

        private void addBolt(ThunderInstance instance, Timestamp timestamp) {
            bolts.add(instance);
            lastBoltDelay = instance.bolt.getSpawnFunction().getSpawnDelay(random);
            lastBoltTimestamp = timestamp;
        }
    }

    public static class ThunderInstance {
        private final ThunderData bolt;
        private final List<ThunderData.BoltQuads> renderQuads;
        private final Timestamp createdTimestamp;

        public ThunderInstance(ThunderData bolt, Timestamp timestamp) {
            this.bolt = bolt;
            this.renderQuads = bolt.generate();
            this.createdTimestamp = timestamp;
        }

        public void render(Matrix4f matrix, VertexConsumer buffer, Timestamp timestamp) {
            float lifeScale = timestamp.subtract(createdTimestamp).value() / bolt.getLifespan();
            Pair<Integer, Integer> bounds = bolt.getFadeFunction().getRenderBounds(renderQuads.size(), lifeScale);
            for (int i = bounds.getLeft(); i < bounds.getRight(); i++) {
                renderQuads.get(i).getVecs().forEach(v -> buffer.addVertex(matrix, (float) v.x, (float) v.y, (float) v.z)
                        .setColor(bolt.getColor().x(), bolt.getColor().y(), bolt.getColor().z(), bolt.getColor().w()));
            }
        }

        public boolean tick(Timestamp timestamp) {
            return timestamp.isPassed(createdTimestamp, bolt.getLifespan());
        }
    }

    public static class Timestamp {

        private final long ticks;
        private final float partial;

        public Timestamp() {
            this(0, 0);
        }

        public Timestamp(long ticks, float partial) {
            this.ticks = ticks;
            this.partial = partial;
        }

        public Timestamp subtract(Timestamp other) {
            long newTicks = ticks - other.ticks;
            float newPartial = partial - other.partial;
            if (newPartial < 0) {
                newPartial += 1;
                newTicks -= 1;
            }
            return new Timestamp(newTicks, newPartial);
        }

        public float value() {
            return ticks + partial;
        }

        public boolean isPassed(Timestamp prev, double duration) {
            long ticksPassed = ticks - prev.ticks;
            if (ticksPassed > duration)
                return true;
            duration -= ticksPassed;
            if (duration >= 1)
                return false;
            return (partial - prev.partial) >= duration;
        }
    }

    public static class ThunderData {

        private final Random random = new Random();

        private final ThunderRenderInfo renderInfo;

        private final Vec3 start;
        private final Vec3 end;

        private final int segments;

        private int count = 1;
        private float size = 0.1F;
        private int lifespan = 30;

        private SpawnFunction spawnFunction = SpawnFunction.delay(60);
        private FadeFunction fadeFunction = FadeFunction.fade(0.5F);

        public ThunderData(Vec3 start, Vec3 end) {
            this(ThunderRenderInfo.DEFAULT, start, end, (int) (Math.sqrt(start.distanceTo(end) * 100)));
        }

        public ThunderData(ThunderRenderInfo info, Vec3 start, Vec3 end, int segments) {
            this.renderInfo = info;
            this.start = start;
            this.end = end;
            this.segments = segments;
        }

        public ThunderData count(int count) {
            this.count = count;
            return this;
        }

        public ThunderData size(float size) {
            this.size = size;
            return this;
        }

        public ThunderData spawn(SpawnFunction spawnFunction) {
            this.spawnFunction = spawnFunction;
            return this;
        }

        public ThunderData fade(FadeFunction fadeFunction) {
            this.fadeFunction = fadeFunction;
            return this;
        }

        public ThunderData lifespan(int lifespan) {
            this.lifespan = lifespan;
            return this;
        }

        public int getLifespan() {
            return lifespan;
        }

        public SpawnFunction getSpawnFunction() {
            return spawnFunction;
        }

        public FadeFunction getFadeFunction() {
            return fadeFunction;
        }

        public Vector4f getColor() {
            return renderInfo.color;
        }

        public List<BoltQuads> generate() {
            List<BoltQuads> quads = new ArrayList<>();
            Vec3 diff = end.subtract(start);
            float totalDistance = (float) diff.length();
            for (int i = 0; i < count; i++) {
                LinkedList<BoltInstructions> drawQueue = new LinkedList<>();
                drawQueue.add(new BoltInstructions(start, 0, new Vec3(0, 0, 0), null, false));
                while (!drawQueue.isEmpty()) {
                    BoltInstructions data = drawQueue.poll();
                    Vec3 perpendicularDist = data.perpendicularDist;
                    float progress = data.progress + (1F / segments) * (1 - renderInfo.parallelNoise + random.nextFloat() * renderInfo.parallelNoise * 2);
                    Vec3 segmentEnd;
                    if (progress >= 1) {
                        segmentEnd = end;
                    } else {
                        float segmentDiffScale = renderInfo.spreadFunction.getMaxSpread(progress);
                        float maxDiff = renderInfo.spreadFactor * segmentDiffScale * totalDistance * renderInfo.randomFunction.getRandom(random);
                        Vec3 randVec = findRandomOrthogonalVector(diff, random);
                        perpendicularDist = renderInfo.segmentSpreader.getSegmentAdd(perpendicularDist, randVec, maxDiff, segmentDiffScale, progress);
                        if (renderInfo.spreadFactor <= 0.0001F) {
                            perpendicularDist = Vec3.ZERO;
                        }
                        segmentEnd = start.add(diff.scale(progress)).add(perpendicularDist);
                    }
                    float boltSize = size * (0.5F + (1 - progress) * 0.5F);
                    Pair<BoltQuads, QuadCache> quadData = createQuads(data.cache, data.start, segmentEnd, boltSize);
                    quads.add(quadData.getLeft());

                    if (segmentEnd == end) {
                        break;
                    } else if (!data.isBranch) {
                        drawQueue.add(new BoltInstructions(segmentEnd, progress, perpendicularDist, quadData.getRight(), false));
                    } else if (random.nextFloat() < renderInfo.branchContinuationFactor) {
                        drawQueue.add(new BoltInstructions(segmentEnd, progress, perpendicularDist, quadData.getRight(), true));
                    }

                    while (random.nextFloat() < renderInfo.branchInitiationFactor * (1 - progress)) {
                        drawQueue.add(new BoltInstructions(segmentEnd, progress, perpendicularDist, quadData.getRight(), true));
                    }
                }
            }
            return quads;
        }

        private static Vec3 findRandomOrthogonalVector(Vec3 vec, Random rand) {
            Vec3 newVec = new Vec3(-0.5 + rand.nextDouble(), -0.5 + rand.nextDouble(), -0.5 + rand.nextDouble());
            return vec.cross(newVec).normalize();
        }

        private Pair<BoltQuads, QuadCache> createQuads(QuadCache cache, Vec3 startPos, Vec3 end, float size) {
            Vec3 diff = end.subtract(startPos);
            Vec3 rightAdd = diff.cross(new Vec3(0.5, 0.5, 0.5)).normalize().scale(size);
            Vec3 backAdd = diff.cross(rightAdd).normalize().scale(size), rightAddSplit = rightAdd.scale(0.5F);

            Vec3 start = cache != null ? cache.prevEnd : startPos;
            Vec3 startRight = cache != null ? cache.prevEndRight : start.add(rightAdd);
            Vec3 startBack = cache != null ? cache.prevEndBack : start.add(rightAddSplit).add(backAdd);
            Vec3 endRight = end.add(rightAdd), endBack = end.add(rightAddSplit).add(backAdd);

            BoltQuads quads = new BoltQuads();
            quads.addQuad(start, end, endRight, startRight);
            quads.addQuad(startRight, endRight, end, start);

            quads.addQuad(startRight, endRight, endBack, startBack);
            quads.addQuad(startBack, endBack, endRight, startRight);

            return Pair.of(quads, new QuadCache(end, endRight, endBack));
        }

        private record QuadCache(Vec3 prevEnd, Vec3 prevEndRight, Vec3 prevEndBack) {

        }

        protected static class BoltInstructions {

            private final Vec3 start;
            private final Vec3 perpendicularDist;
            private final QuadCache cache;
            private final float progress;
            private final boolean isBranch;

            private BoltInstructions(Vec3 start, float progress, Vec3 perpendicularDist, QuadCache cache, boolean isBranch) {
                this.start = start;
                this.perpendicularDist = perpendicularDist;
                this.progress = progress;
                this.cache = cache;
                this.isBranch = isBranch;
            }
        }

        public static class BoltQuads {

            private final List<Vec3> vecs = new ArrayList<>();

            protected void addQuad(Vec3... quadVecs) {
                vecs.addAll(Arrays.asList(quadVecs));
            }

            public List<Vec3> getVecs() {
                return vecs;
            }
        }

        public interface SpreadFunction {
            SpreadFunction DEFAULT = (progress) -> 1.0F;

            float getMaxSpread(float progress);
        }

        public interface RandomFunction {
            RandomFunction DEFAULT = rand -> (float) rand.nextGaussian();

            float getRandom(Random rand);
        }

        public interface SegmentSpreader {
            SegmentSpreader DEFAULT = (perpendicularDist, randVec, maxDiff, scale, progress) -> randVec.scale(maxDiff);
            static SegmentSpreader memory(float memoryFactor) {
                return (perpendicularDist, randVec, maxDiff, spreadScale, progress) -> {
                    float nextDiff = maxDiff * (1 - memoryFactor);
                    Vec3 cur = randVec.scale(nextDiff);
                    if (progress > 0.5F) {
                        float pull = (1F - spreadScale) * (1F - memoryFactor) * 0.35F; // tune 0.2..0.6
                        cur = cur.add(perpendicularDist.scale(-pull));
                    }

                    return perpendicularDist.add(cur);
                };
            }

            Vec3 getSegmentAdd(Vec3 perpendicularDist, Vec3 randVec, float maxDiff, float scale, float progress);
        }

        public interface SpawnFunction {
            SpawnFunction DEFAULT = (rand) -> Pair.of(0F, 0F);

            static SpawnFunction delay(float delay) {
                return (rand) -> Pair.of(delay, delay);
            }

            Pair<Float, Float> getSpawnDelayBounds(Random rand);

            default float getSpawnDelay(Random rand) {
                Pair<Float, Float> bounds = getSpawnDelayBounds(rand);
                return bounds.getLeft() + (bounds.getRight() - bounds.getLeft()) * rand.nextFloat();
            }

            default boolean isConsecutive() {
                return false;
            }
        }

        public interface FadeFunction {
            FadeFunction NONE = (totalBolts, lifeScale) -> Pair.of(0, totalBolts);
            static FadeFunction fade(float fade) {
                return (totalBolts, lifeScale) -> {
                    int start = lifeScale > (1 - fade) ? (int) (totalBolts * (lifeScale - (1 - fade)) / fade) : 0;
                    int end = lifeScale < fade ? (int) (totalBolts * (lifeScale / fade)) : totalBolts;
                    return Pair.of(start, end);
                };
            }

            Pair<Integer, Integer> getRenderBounds(int totalBolts, float lifeScale);
        }

        public static class ThunderRenderInfo {

            public static final ThunderRenderInfo DEFAULT = new ThunderRenderInfo();
            public static final ThunderRenderInfo DRAGON_THUNDER = dragonThunder();
            public static final ThunderRenderInfo BLUE_DEMON_THUNDER = blueDemonThunder();
            private float parallelNoise = 0.1F;
            private float spreadFactor = 0.0F;
            private float branchInitiationFactor = 0.0F;
            private float branchContinuationFactor = 0.0F;

            private Vector4f color = new Vector4f(0.45F, 0.45F, 0.5F, 0.8F);

            private final RandomFunction randomFunction = RandomFunction.DEFAULT;
            private final SpreadFunction spreadFunction = SpreadFunction.DEFAULT;
            private SegmentSpreader segmentSpreader = SegmentSpreader.DEFAULT;

            public static ThunderRenderInfo dragonThunder() {
                return new ThunderRenderInfo(
                            0.15F,
                            0.025F,
                            0.0F,
                            0.0F,
                            new Vector4f(0.85F, 0.55F, 1.0F, 0.85F),
                            0.8F
                    );
            }

            public static ThunderRenderInfo blueDemonThunder() {
                return new ThunderRenderInfo(
                        0.15F,
                        0.025F,
                        0.0F,
                        0.0F,
                        new Vector4f(0.65F, 1.0F, 1.0F, 0.9F),
                        0.8F
                );
            }

            public ThunderRenderInfo() {
            }

            public ThunderRenderInfo(float parallelNoise, float spreadFactor, float branchInitiationFactor, float branchContinuationFactor, Vector4f color, float closeness) {
                this.parallelNoise = parallelNoise;
                this.spreadFactor = spreadFactor;
                this.branchInitiationFactor = branchInitiationFactor;
                this.branchContinuationFactor = branchContinuationFactor;
                this.color = color;
                this.segmentSpreader = SegmentSpreader.memory(closeness);
            }
        }
    }
}
