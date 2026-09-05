package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.blockentity.*;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.clazz.ProjectileBreakableBlocks;
import com.pla.annoyingvillagers.compat.SmartNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.network.ClientboundEliteHerobrineFx;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.clazz.DangerousReaction;
import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class HerobrineUtil {
    private static final int HEROBRINE_ASSISTANCE_FALLBACK_TICKS = 34;
    private static final double HEROBRINE_ASSISTANCE_FALLBACK_HEIGHT = 3.25D;
    private static final double HEROBRINE_ASSISTANCE_FALLBACK_RADIUS = 1.15D;
    private static final List<AssistanceSpiralFx> ACTIVE_ASSISTANCE_SPIRALS = new ArrayList<>();
    private static Level assistanceSpiralLevel;

    public static void startHerobrineAssistanceFallback(Level level, Vec3 origin) {
        if (level == null || !level.isClientSide() || origin == null) {
            return;
        }

        resetAssistanceSpirals(level);
        RandomSource rand = level.getRandom();
        AssistanceSpiralFx fx = new AssistanceSpiralFx(origin, rand.nextDouble() * Math.PI * 2.0D);
        ACTIVE_ASSISTANCE_SPIRALS.add(fx);
        fx.spawnBaseBurst(level);
    }

    public static void tickHerobrineAssistanceFallbacks(Level level) {
        if (level == null) {
            ACTIVE_ASSISTANCE_SPIRALS.clear();
            assistanceSpiralLevel = null;
            return;
        }

        resetAssistanceSpirals(level);
        Iterator<AssistanceSpiralFx> iterator = ACTIVE_ASSISTANCE_SPIRALS.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().tick(level)) {
                iterator.remove();
            }
        }
    }

    private static void resetAssistanceSpirals(Level level) {
        if (assistanceSpiralLevel != level) {
            ACTIVE_ASSISTANCE_SPIRALS.clear();
            assistanceSpiralLevel = level;
        }
    }

    public static boolean isHerobrineFaction(Entity e) {
        return e instanceof HerobrineMob
                || e instanceof HerobrineGregEntity
                || e instanceof LowHerobrineCloneEntity
                || e instanceof LowShadowHerobrineCloneEntity
                || e instanceof InfectedPlayerNpcEntity
                || e instanceof InfectedTheMostMoistBurrit0Entity
                || e instanceof InfectedChrisEntity
                || e instanceof NullSwordEntity
                || e instanceof NullAxeEntity
                || e instanceof NullPickaxeEntity
                || e instanceof NullShovelEntity
                || e instanceof NullHoeEntity
                || e instanceof BlockProjectileEntity
                || e instanceof EliteHerobrineKnockedEntity;
    }

    private static Vec3 randomUnit(RandomSource rand) {
        double z = rand.nextDouble() * 2.0D - 1.0D;
        double angle = rand.nextDouble() * Math.PI * 2.0D;
        double radius = Math.sqrt(Math.max(0.0D, 1.0D - z * z));
        return new Vec3(radius * Math.cos(angle), z, radius * Math.sin(angle));
    }

    private static void spawnParticle(Level level, ParticleOptions particle, Vec3 pos, Vec3 velocity) {
        level.addParticle(particle, true, pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z);
    }

    private static final class AssistanceSpiralFx {
        private final Vec3 origin;
        private final double seedAngle;
        private int age;

        private AssistanceSpiralFx(Vec3 origin, double seedAngle) {
            this.origin = origin;
            this.seedAngle = seedAngle;
        }

        private boolean tick(Level level) {
            if (age >= HEROBRINE_ASSISTANCE_FALLBACK_TICKS) {
                spawnTopBurst(level);
                return false;
            }

            RandomSource rand = level.getRandom();
            double progress = age / (double) HEROBRINE_ASSISTANCE_FALLBACK_TICKS;
            double baseHeight = 0.12D + progress * HEROBRINE_ASSISTANCE_FALLBACK_HEIGHT;
            double turnAngle = seedAngle + progress * Math.PI * 7.0D;

            for (int arm = 0; arm < 2; arm++) {
                double armAngle = turnAngle + arm * Math.PI;

                for (int trail = 0; trail < 4; trail++) {
                    double trailProgress = Math.max(0.0D, progress - trail * 0.022D);
                    double angle = armAngle - trail * 0.42D;
                    double radius = HEROBRINE_ASSISTANCE_FALLBACK_RADIUS * (1.0D - trailProgress * 0.48D)
                            + (rand.nextDouble() - 0.5D) * 0.08D;
                    double cos = Math.cos(angle);
                    double sin = Math.sin(angle);

                    Vec3 radial = new Vec3(cos, 0.0D, sin);
                    Vec3 tangent = new Vec3(-sin, 0.0D, cos);
                    Vec3 pos = origin
                            .add(radial.scale(radius))
                            .add(0.0D, baseHeight - trail * 0.055D + (rand.nextDouble() - 0.5D) * 0.07D, 0.0D);
                    Vec3 velocity = tangent.scale(0.045D + rand.nextDouble() * 0.025D)
                            .add(radial.scale(-0.012D))
                            .add(0.0D, 0.045D + rand.nextDouble() * 0.035D, 0.0D);

                    spawnParticle(level, ParticleTypes.ENCHANT, pos, velocity);

                    if ((age + trail + arm) % 6 == 0) {
                        spawnParticle(level, ParticleTypes.END_ROD, pos, velocity.scale(0.45D));
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                double angle = seedAngle - progress * Math.PI * 4.0D + rand.nextDouble() * Math.PI * 2.0D;
                double radius = 0.22D + rand.nextDouble() * 0.55D;
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);
                Vec3 pos = origin.add(cos * radius, 0.08D + rand.nextDouble() * 0.22D, sin * radius);
                Vec3 velocity = new Vec3(-sin, 0.0D, cos).scale(0.018D)
                        .add(-cos * 0.01D, 0.025D + rand.nextDouble() * 0.035D, -sin * 0.01D);

                spawnParticle(level, ParticleTypes.ENCHANT, pos, velocity);
            }

            age++;
            return true;
        }

        private void spawnBaseBurst(Level level) {
            RandomSource rand = level.getRandom();
            for (int i = 0; i < 36; i++) {
                double angle = seedAngle + i / 36.0D * Math.PI * 2.0D;
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);
                double radius = 0.35D + rand.nextDouble() * 0.85D;
                Vec3 pos = origin.add(cos * radius, 0.08D + rand.nextDouble() * 0.18D, sin * radius);
                Vec3 velocity = new Vec3(-sin, 0.0D, cos).scale(0.055D + rand.nextDouble() * 0.035D)
                        .add(cos * 0.015D, 0.04D + rand.nextDouble() * 0.045D, sin * 0.015D);

                spawnParticle(level, ParticleTypes.ENCHANT, pos, velocity);
                if ((i & 3) == 0) {
                    spawnParticle(level, ParticleTypes.END_ROD, pos, velocity.scale(0.35D));
                }
            }
        }

        private void spawnTopBurst(Level level) {
            RandomSource rand = level.getRandom();
            Vec3 top = origin.add(0.0D, HEROBRINE_ASSISTANCE_FALLBACK_HEIGHT + 0.35D, 0.0D);

            for (int i = 0; i < 28; i++) {
                Vec3 offset = randomUnit(rand).scale(0.18D + rand.nextDouble() * 0.62D);
                Vec3 pos = top.add(offset);
                Vec3 velocity = offset.normalize().scale(0.025D + rand.nextDouble() * 0.045D)
                        .add(0.0D, 0.015D + rand.nextDouble() * 0.045D, 0.0D);

                spawnParticle(level, ParticleTypes.ENCHANT, pos, velocity);
                if ((i & 2) == 0) {
                    spawnParticle(level, ParticleTypes.END_ROD, pos, velocity.scale(0.55D));
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
    public static final class ClientEvents {
        private ClientEvents() {
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                tickHerobrineAssistanceFallbacks(Minecraft.getInstance().level);
            }
        }
    }

    public static void placeIfReplaceable(ServerLevel level, BlockPos pos, BlockState state, Entity ownerEntity) {
        if (!level.isLoaded(pos)) return;

        BlockState existingState = level.getBlockState(pos);
        if (!existingState.canBeReplaced()) {
            ProjectileBreakableBlocks rule = ProjectileBreakableBlocks.find(existingState);
            if (rule == null) return;
            boolean requiresTool = existingState.requiresCorrectToolForDrops();
            boolean destroyed = level.destroyBlock(pos, true, ownerEntity);
            if (!destroyed) return;
            if (requiresTool) {
                Item item = existingState.getBlock().asItem();
                if (item != Items.AIR) {
                    Block.popResource(level, pos, new ItemStack(item));
                }
            }
        }
        if (!level.getBlockState(pos).canBeReplaced()) return;

        level.setBlockAndUpdate(pos, state);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return;

        if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
            obsidianBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
            shadowObsidianBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof CryingObsidianBlockEntity cryingObsidianBlockEntity) {
            cryingObsidianBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof ShadowObsidianShortPillarBlockEntity shadowObsidianShortPillarBlockEntity) {
            shadowObsidianShortPillarBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof ShadowObsidianMiddlePillarBlockEntity shadowObsidianMiddlePillarBlockEntity) {
            shadowObsidianMiddlePillarBlockEntity.setOwner(ownerEntity.getUUID());
        } else if (blockEntity instanceof ShadowObsidianLongPillarBlockEntity shadowObsidianLongPillarBlockEntity) {
            shadowObsidianLongPillarBlockEntity.setOwner(ownerEntity.getUUID());
        }

        blockEntity.setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
    }

    private static Basis basisFromEntity(Entity e) {
        Vec3 forward = e.getLookAngle().normalize();

        Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0e-6) {
            right = new Vec3(1.0, 0.0, 0.0);
        } else {
            right = right.normalize();
        }
        Vec3 up = right.cross(forward).normalize();
        return new Basis(forward, right, up);
    }

    public static void transformHerobrine(LevelAccessor world, double x, double y, double z, Entity entity, Entity herobrineEntity) {
        if (entity == null) return;
        Random random = new Random();
        if (random.nextFloat() >= AnnoyingVillagersConfig.HEROBRINE_POSSESS_RATE.get().floatValue()) {
            return;
        }

        if (ModList.get().isLoaded("smart_npc") && SmartNpc.isSmartNpc(entity)) {
            if (!(world instanceof ServerLevel serverLevel)) return;
            entity.getPersistentData().putBoolean("die_by_possess", true);
            Entity possessed;
            if (herobrineEntity instanceof HerobrineCloneEntity || herobrineEntity instanceof NullEntity
                    || herobrineEntity instanceof NullSwordEntity || herobrineEntity instanceof NullAxeEntity
                    || herobrineEntity instanceof NullPickaxeEntity || herobrineEntity instanceof NullShovelEntity
                    || herobrineEntity instanceof NullHoeEntity || herobrineEntity instanceof GlaiveHerobrineEntity
                    || herobrineEntity instanceof AegisHerobrineEntity || herobrineEntity instanceof ReaperHerobrineEntity
                    || herobrineEntity instanceof SwordsmanHerobrineEntity || herobrineEntity instanceof SledgehammerHerobrineEntity) {
                possessed = new LowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), serverLevel);
            } else {
                possessed = new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel);
            }
            possessed.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
            LivingEntity victim = (LivingEntity) entity;
            victim.getCustomName();
            possessed.getPersistentData().putString("killed_name", victim.getCustomName().getString());

            if (!victim.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(Items.PLAYER_HEAD)) {
                possessed.setItemSlot(EquipmentSlot.HEAD, victim.getItemBySlot(EquipmentSlot.HEAD).copy());
            }
            possessed.setItemSlot(EquipmentSlot.CHEST, victim.getItemBySlot(EquipmentSlot.CHEST).copy());
            possessed.setItemSlot(EquipmentSlot.LEGS, victim.getItemBySlot(EquipmentSlot.LEGS).copy());
            possessed.setItemSlot(EquipmentSlot.FEET, victim.getItemBySlot(EquipmentSlot.FEET).copy());
            possessed.setItemSlot(EquipmentSlot.MAINHAND, victim.getItemBySlot(EquipmentSlot.MAINHAND).copy());
            possessed.setItemSlot(EquipmentSlot.OFFHAND, victim.getItemBySlot(EquipmentSlot.OFFHAND).copy());
            Mob mob = (Mob) possessed;
            if (mob instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                lowHerobrineCloneEntity.setUsername(((FakePlayer) entity).getUsername());
                lowHerobrineCloneEntity.setProfile(((FakePlayer) entity).getProfile());
                if (herobrineEntity instanceof HerobrineMob herobrineMob) {
                    lowHerobrineCloneEntity.setPossessedByEntity(herobrineMob);
                    lowHerobrineCloneEntity.setPossessedByUuid(herobrineMob.getUUID());
                } else if (herobrineEntity instanceof NullSwordEntity nullSwordEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullSwordEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullSwordEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullAxeEntity nullAxeEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullAxeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullAxeEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullPickaxeEntity nullPickaxeEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullPickaxeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullPickaxeEntity.getNullUUID());
                } else if (herobrineEntity instanceof NullShovelEntity nullShovelEntity) {
                    lowHerobrineCloneEntity.setPossessedByEntity(nullShovelEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullShovelEntity.getNullUUID());
                } else {
                    NullHoeEntity nullHoeEntity = (NullHoeEntity) herobrineEntity;
                    lowHerobrineCloneEntity.setPossessedByEntity(nullHoeEntity.getNullEntity());
                    lowHerobrineCloneEntity.setPossessedByUuid(nullHoeEntity.getNullUUID());
                }
            }
            if (mob instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                if (herobrineEntity instanceof HerobrineMob herobrineMob) {
                    lowShadowHerobrineCloneEntity.setPossessedByEntity(herobrineMob);
                    lowShadowHerobrineCloneEntity.setPossessedByUuid(herobrineMob.getUUID());
                }
            }
            mob.finalizeSpawn(serverLevel, world.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            serverLevel.addFreshEntity(possessed);
        }
    }

    public static void initialSpawn(LevelAccessor levelaccessor, final Entity entity, int recallTicks, MobSpawnType mobSpawnType) {
        int min = AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME.get();
        int max = AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME.get();
        int randomMin = Math.min(min, max);
        int randomMax = Math.max(min, max);

        if (entity != null) {
            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                String killedName = entity.getPersistentData().getString("killed_name");
                if (!killedName.isEmpty()) { // Low Herobrine Clone
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(killedName + " " + Component.translatable("subtitles.possessed_npc").getString()), false);
                } else {
                    if ((entity instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity && !lowHerobrineCloneEntity.isSummoned()) || (entity instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity && !lowShadowHerobrineCloneEntity.isSummoned())) {
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(Component.translatable("subtitles.possessed_random").getString()), false);
                    } else {
                        if (recallTicks == 0) {
                            recallTicks = (randomMin + new Random().nextInt(randomMax - randomMin + 1)) * 60 * 20;
                            if (entity instanceof HerobrineMob herobrineMob) {
                                herobrineMob.setRecallTicks(recallTicks);
                            }
                        }
                        if (mobSpawnType.equals(MobSpawnType.NATURAL) || mobSpawnType.equals(MobSpawnType.CHUNK_GENERATION)) { // For natural spawn
                            if (Math.random() <= 0.5D) { // Natural possessed
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(Component.translatable("subtitles.possessed_random").getString()), false);
                            } else { // Portal animation
                                if (entity instanceof HerobrineMob herobrineMob) {
                                    herobrineMob.setRenderPortal(true);
                                    HerobrinePortalUtil.spawnHerobrine(herobrineMob);
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(herobrineMob.getChatName() + " " + Component.translatable("subtitles.herobrine_arrive").getString()), false);
                                } else if (entity instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                                    lowShadowHerobrineCloneEntity.setRenderPortal(true);
                                    HerobrinePortalUtil.spawnHerobrine(lowShadowHerobrineCloneEntity);
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Netherite Herobrine§r " + Component.translatable("subtitles.herobrine_arrive").getString()), false);
                                }
                            }
                        } else {
                            if (entity instanceof HerobrineMob herobrineMob) {
                                if (mobSpawnType.equals(MobSpawnType.SPAWN_EGG) || mobSpawnType.equals(MobSpawnType.COMMAND)) {
                                    herobrineMob.setRenderPortal(true);
                                }
                                HerobrinePortalUtil.spawnHerobrine(herobrineMob);
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(herobrineMob.getChatName() + " " + Component.translatable("subtitles.herobrine_arrive").getString()), false);
                            } else if (entity instanceof LivingEntity livingEntity) {
                                // This logic is for #5 and #6 ground spawn
                                HerobrinePortalUtil.spawnHerobrine(livingEntity);
                            }
                        }
                    }
                }
            }

            if (entity.level() instanceof ServerLevel
                    && (entity instanceof HerobrineCloneEntity
                    || entity instanceof ShadowHerobrineCloneEntity
                    || entity instanceof Herobrine7Entity
                    || entity instanceof ArmoredHerobrineEntity)) {
                entity.playSound(AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY_ON_SPAWN.get(), 0.5F, 1.0F);
            }

            if (entity.level() instanceof ServerLevel
                    && entity instanceof ShadowHerobrineEntity) {
                entity.playSound(AnnoyingVillagersModSounds.SHADOW_HEROBRINE_SAY_ON_SPAWN.get(), 0.5F, 1.0F);
            }

            TeamUtil.addOrJoinTeam(entity, "herobrine");
        }
    }

    public static void spawnEliteEffect(Level level, double x, double y, double z, Entity entity) {
        if (entity != null && level instanceof ServerLevel serverLevel) {
            if (Math.random() <= 0.3D) {
                boolean extraParticle = Math.random() <= 0.87D;
                AnnoyingVillagers.PACKET_HANDLER.send(
                        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
                        new ClientboundEliteHerobrineFx(entity.getId(), entity.tickCount, new Vec3(x, y, z), extraParticle)
                );

                if (extraParticle) {
                    serverLevel.playSound(
                                null
                            , x, y, z, AnnoyingVillagersModSounds.ELECTRIFY.get(),
                            SoundSource.NEUTRAL,
                            new Random().nextFloat(0.05F, 0.4F),
                            new Random().nextFloat(0.5F, 1.2F));
                }
            }

        }
    }

    private record Basis(Vec3 fwd, Vec3 right, Vec3 up) {}

    public static void spawnObsidianEyeLineStaggered(ServerLevel level, Entity entity, BlockState state, int tickGap) {
        if (level == null || entity == null) return;

        Basis b = basisFromEntity(entity);
        Vec3 eye = entity.getEyePosition(1.0F);

        BlockPos[] sequence = new BlockPos[1 + 6];
        sequence[0] = BlockPos.containing(eye.add(b.fwd().scale(1.0)).add(b.up().scale(-1.0)));
        for (int i = 1; i <= 6; i++) {
            sequence[i] = BlockPos.containing(eye.add(b.fwd().scale(i)));
        }

        for (int i = 0; i < sequence.length; i++) {
            final BlockPos pos = sequence[i];
            new DelayedTask(i * Math.max(1, tickGap)) {
                @Override public void run() {
                    placeIfReplaceable(level, pos, state, entity);
                }
            };
        }
    }

    private static final class Pattern2D {
        final int w, h;
        final int[][] cells;
        Pattern2D(int w, int h, int[][] cells) { this.w = w; this.h = h; this.cells = cells; }
        int centerX() { return w / 2; }
    }

    private static final Pattern2D[] OBSIDIAN_PATTERNS = new Pattern2D[] {
            new Pattern2D(1, 3, new int[][] { {0,0},{0,1},{0,2} }),
            new Pattern2D(2, 3, new int[][] { {0,0},{0,1},{0,2},{1,2} }),
            new Pattern2D(2, 3, new int[][] { {1,0},{1,1},{1,2},{0,1} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{1,0},{2,0},{1,1},{1,2} }),
            new Pattern2D(3, 3, new int[][] { {0,2},{1,2},{2,2},{1,1},{1,0} }),
            new Pattern2D(3, 3, new int[][] { {1,1},{1,2},{1,0},{0,1},{2,1} }),
            new Pattern2D(3, 4, new int[][] { {1,0},{1,1},{1,2},{1,3},{0,2},{2,2} }),
            new Pattern2D(2, 2, new int[][] { {0,0},{1,0},{0,1},{1,1} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{0,1},{1,1},{2,1},{0,2},{1,2} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{1,0},{1,1},{1,2},{2,2} }),
            new Pattern2D(3, 2, new int[][] { {0,0},{1,0},{2,0},{0,1} }),
    };

    private static boolean hasGroundWithin(ServerLevel level, Entity e, int maxDown) {
        Vec3 start = new Vec3(e.getX(), e.getBoundingBox().minY + 1.0E-3D, e.getZ());
        Vec3 end = start.add(0.0D, -maxDown, 0.0D);

        BlockHitResult hit = level.clip(new ClipContext(
                start, end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                e
        ));
        return hit.getType() != HitResult.Type.MISS;
    }

    public static void spawnObsidianPatternAtBody(ServerLevel level, Entity entity, BlockState state) {
        if (level == null || entity == null) return;
        if (!hasGroundWithin(level, entity, 3)) return;

        int minY = level.getMinBuildHeight();
        int maxY = level.getMaxBuildHeight() - 1;

        BlockPos feet = BlockPos.containing(entity.getX(), entity.getBoundingBox().minY, entity.getZ());

        var rand = level.getRandom();
        Pattern2D pat = OBSIDIAN_PATTERNS[rand.nextInt(OBSIDIAN_PATTERNS.length)];

        Direction face = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
        boolean mirror = rand.nextBoolean();
        BlockPos origin = feet.relative(face);

        Direction side = mirror ? face.getCounterClockWise() : face.getClockWise();
        int cx = pat.centerX();

        for (int[] c : pat.cells) {
            int localX = c[0] - cx;
            int localY = c[1];

            int y = origin.getY() + localY;
            if (y < minY || y > maxY) continue;

            BlockPos p = origin.offset(
                    side.getStepX() * localX,
                    localY,
                    side.getStepZ() * localX
            );

            placeIfReplaceable(level, p, state, entity);
        }
    }

    private static Vec3 getJointOrVanillaActionPosition(Entity entity, Object joint, float partialTick) {
//        Add this is AV_EFM
//        if (joint != null) {
//            try {
//                Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
//                        entity, new Vec3f(0, 0, 0),
//                        (Joint) joint, partialTick, 0.0F
//                );
//                if (jointVec != null) {
//                    return jointVec;
//                }
//            } catch (Exception ignored) {
//            }
//        }

        float fallbackPartialTick = entity.level().isClientSide ? partialTick : 1.0F;
        return CommonUtil.getVanillaSwordOrBodyPosition(entity, fallbackPartialTick);
    }

    public static void summonObsidianBlocksInfrontOf(ServerLevel level,
                                                     LivingEntity caster,
                                                     BlockState obsidianState,
                                                     int amount,
                                                     Object joint) {
        if (level == null || caster == null) return;

        final Vec3[] lockedEye = { null };
        final Vec3[] lockedDir = { null };
        final int[] anchorY = { Integer.MIN_VALUE };

        for (int i = 1; i <= amount; i++) {
            final int forwardBlock = i + 1;

            new DelayedTask(i) {
                @Override public void run() {
                    if (!caster.isAlive()) return;
                    if (caster.level() != level) return;

                    if (lockedDir[0] == null) {
                        lockedEye[0] = caster.getEyePosition(1.0F);
                        lockedDir[0] = caster.getLookAngle().normalize();
                    }

                    Vec3 placeVec;

                    if (forwardBlock == 2) {
                        Vec3 jointVec = getJointOrVanillaActionPosition(caster, joint, 0.0F);
                        if (jointVec == null) return;

                        placeVec = jointVec.add(lockedDir[0].scale(1.0D));
                        anchorY[0] = BlockPos.containing(placeVec).getY();
                    } else {
                        if (anchorY[0] == Integer.MIN_VALUE) return;

                        Vec3 target = lockedEye[0].add(lockedDir[0].scale(forwardBlock));
                        placeVec = new Vec3(target.x, anchorY[0] + 0.5D, target.z);
                    }

                    placeIfReplaceable(level, BlockPos.containing(placeVec), obsidianState, caster);
                }
            };
        }
    }

    public static void summonObsidianBlocksFromPosition(ServerLevel level, LivingEntity caster, BlockState obsidianState, int amount, Vec3 startPosition) {
        if (level == null || caster == null || obsidianState == null || startPosition == null || amount <= 0) return;

        Vec3 look = caster.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            Direction direction = caster.getDirection();
            forward = new Vec3(direction.getStepX(), 0.0D, direction.getStepZ());
        } else {
            forward = forward.normalize();
        }

        final Vec3 lockedForward = forward;
        final int anchorY = BlockPos.containing(startPosition).getY();
        for (int i = 1; i <= amount; i++) {
            final int step = i;
            new DelayedTask(i) {
                @Override public void run() {
                    if (!caster.isAlive() || caster.level() != level) return;
                    Vec3 raw = startPosition.add(lockedForward.scale(step));
                    BlockPos pos = BlockPos.containing(raw.x, anchorY + 0.5D, raw.z);
                    placeIfReplaceable(level, pos, obsidianState, caster);
                }
            };
        }
    }

    public static void summonObsidianVerticalColumnInFront(ServerLevel level, LivingEntity caster, BlockState obsidianState, int height) {
        if (level == null || caster == null || obsidianState == null || height <= 0) return;

        Vec3 look = caster.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            Direction direction = caster.getDirection();
            forward = new Vec3(direction.getStepX(), 0.0D, direction.getStepZ());
        } else {
            forward = forward.normalize();
        }

        Vec3 ahead = new Vec3(caster.getX(), caster.getY(), caster.getZ()).add(forward.scale(2.5D));
        final BlockPos base = BlockPos.containing(ahead.x, caster.getY() + 0.01D, ahead.z);
        for (int y = 0; y < height; y++) {
            final int yOffset = y;
            new DelayedTask(y + 1) {
                @Override public void run() {
                    if (!caster.isAlive() || caster.level() != level) return;
                    placeIfReplaceable(level, base.above(yOffset), obsidianState, caster);
                }
            };
        }
    }

    public static void summonObsidianArcInFront(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null || obsidianState == null) return;

        Vec3 look = caster.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            Direction direction = caster.getDirection();
            forward = new Vec3(direction.getStepX(), 0.0D, direction.getStepZ());
        } else {
            forward = forward.normalize();
        }

        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x).normalize();
        final Vec3 lockedForward = forward;
        final Vec3 lockedRight = right;
        final Vec3 origin = new Vec3(caster.getX(), caster.getY(), caster.getZ());

        final double[][] offsets = {
                {-2.0D, 2.0D},
                {-1.0D, 3.0D},
                {0.0D, 3.5D},
                {1.0D, 3.0D},
                {2.0D, 2.0D}
        };

        for (int i = 0; i < offsets.length; i++) {
            final int index = i;
            new DelayedTask(i + 1) {
                @Override public void run() {
                    if (!caster.isAlive() || caster.level() != level) return;

                    Vec3 target = origin.add(lockedRight.scale(offsets[index][0])).add(lockedForward.scale(offsets[index][1]));
                    BlockPos pos = BlockPos.containing(target.x, caster.getY() + 1.01D, target.z);
                    placeIfReplaceable(level, pos, obsidianState, caster);
                }
            };
        }
    }

    public static void summonObsidianPillarAtTarget(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null || obsidianState == null) return;

        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : null;
        Vec3 basePosition;
        if (target != null && target.isAlive()) {
            basePosition = new Vec3(target.getX(), target.getY(), target.getZ());
        } else {
            Vec3 look = caster.getLookAngle();
            Vec3 forward = new Vec3(look.x, 0.0D, look.z);
            if (forward.lengthSqr() < 1.0E-6D) {
                Direction direction = caster.getDirection();
                forward = new Vec3(direction.getStepX(), 0.0D, direction.getStepZ());
            } else {
                forward = forward.normalize();
            }
            basePosition = new Vec3(caster.getX(), caster.getY(), caster.getZ()).add(forward.scale(4.0D));
        }

        final BlockPos base = BlockPos.containing(basePosition.x, basePosition.y + 0.01D, basePosition.z);
        for (int delay = 1; delay <= 12; delay++) {
            final int yOffset = delay - 1;
            new DelayedTask(delay) {
                @Override public void run() {
                    if (!caster.isAlive() || caster.level() != level) return;
                    placeIfReplaceable(level, base.above(yOffset), obsidianState, caster);
                }
            };
        }
    }

    public static void summonObsidianCube3x3x3(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null || obsidianState == null) return;

        Vec3 look = caster.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);

        if (forward.lengthSqr() < 1.0E-6D) {
            Direction direction = caster.getDirection();
            forward = new Vec3(direction.getStepX(), 0.0D, direction.getStepZ());
        } else {
            forward = forward.normalize();
        }

        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x).normalize();
        Vec3 origin = new Vec3(caster.getX(), caster.getY(), caster.getZ()).add(forward.scale(2.0D));

        for (int depth = 0; depth < 3; depth++) {
            for (int y = 0; y < 3; y++) {
                for (int side = -1; side <= 1; side++) {
                    Vec3 target = origin
                            .add(right.scale(side))
                            .add(forward.scale(depth));

                    BlockPos pos = BlockPos.containing(
                            target.x,
                            caster.getY() + y + 0.01D,
                            target.z
                    );

                    placeIfReplaceable(level, pos, obsidianState, caster);
                }
            }
        }
    }

    public static void summonObsidianWall3x3(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null || obsidianState == null) return;

        Vec3 look = caster.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            Direction direction = caster.getDirection();
            forward = new Vec3(direction.getStepX(), 0.0D, direction.getStepZ());
        } else {
            forward = forward.normalize();
        }

        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x).normalize();
        Vec3 center = new Vec3(caster.getX(), caster.getY(), caster.getZ()).add(forward.scale(3.0D));
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 2; y++) {
                Vec3 world = center.add(right.scale(x)).add(0.0D, y, 0.0D);
                placeIfReplaceable(level, BlockPos.containing(world), obsidianState, caster);
            }
        }
    }

    public static void summonObsidianWall(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null) return;

        final Vec3 eye = caster.getEyePosition(1.0F);
        final Vec3 fwd = caster.getLookAngle().normalize();

        Vec3 left = new Vec3(fwd.z, 0.0D, -fwd.x);
        if (left.lengthSqr() < 1.0E-6D) left = new Vec3(1.0D, 0.0D, 0.0D);
        else left = left.normalize();

        final Vec3 up = fwd.cross(left).normalize();
        final BlockPos p1 = BlockPos.containing(
                eye.add(left.scale(-2)).add(up.scale(-1)).add(fwd.scale(3))
        );
        final BlockPos p2 = BlockPos.containing(
                eye.add(left.scale( 2)).add(up.scale( 2)).add(fwd.scale(3))
        );

        if (!caster.isAlive()) return;

        int minX = Math.min(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int minZ = Math.min(p1.getZ(), p2.getZ());
        int maxX = Math.max(p1.getX(), p2.getX());
        int maxY = Math.max(p1.getY(), p2.getY());
        int maxZ = Math.max(p1.getZ(), p2.getZ());

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    pos.set(x, y, z);
                    placeIfReplaceable(level, pos, obsidianState, caster);
                }
            }
        }
    }

    private static void placePillarWorldOffsets(ServerLevel level, Vec3 eye, int dx, int dz, BlockState state, LivingEntity caster) {
        for (int dy = -1; dy <= 1; dy++) {
            BlockPos pos = BlockPos.containing(eye.x + dx, eye.y + dy, eye.z + dz);
            placeIfReplaceable(level, pos, state, caster);
        }
    }

    private static void placeSingleWorldOffset(ServerLevel level, Vec3 eye, int dx, int dy, int dz, BlockState state, LivingEntity caster) {
        BlockPos pos = BlockPos.containing(eye.x + dx, eye.y + dy, eye.z + dz);
        placeIfReplaceable(level, pos, state, caster);
    }

    public static void summonObsidianCross(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null) return;

        new DelayedTask(2) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                Vec3 eye = caster.getEyePosition(1.0F);

                placePillarWorldOffsets(level, eye, 0,  3, obsidianState, caster);
                placePillarWorldOffsets(level, eye, 0, -3, obsidianState, caster);

                placePillarWorldOffsets(level, eye,  3, 0, obsidianState, caster);
                placePillarWorldOffsets(level, eye, -3, 0, obsidianState, caster);
            }
        };

        new DelayedTask(4) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                Vec3 eye = caster.getEyePosition(1.0F);

                placeSingleWorldOffset(level, eye, 0, 2,  3, obsidianState, caster);
                placeSingleWorldOffset(level, eye, 0, 2, -3, obsidianState, caster);
                placeSingleWorldOffset(level, eye, 3, 2,  0, obsidianState, caster);
                placeSingleWorldOffset(level, eye,-3, 2,  0, obsidianState, caster);
            }
        };

        new DelayedTask(6) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                Vec3 eye = caster.getEyePosition(1.0F);

                int[] dist = {5, 7};
                for (int d : dist) {
                    placePillarWorldOffsets(level, eye, 0,  d, obsidianState, caster);
                    placePillarWorldOffsets(level, eye, 0, -d, obsidianState, caster);
                    placePillarWorldOffsets(level, eye,  d, 0, obsidianState, caster);
                    placePillarWorldOffsets(level, eye, -d, 0, obsidianState, caster);
                }
            }
        };

        new DelayedTask(8) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                Vec3 eye = caster.getEyePosition(1.0F);

                int[] dists = {5, 7};
                for (int d : dists) {
                    placeSingleWorldOffset(level, eye, 0, 2,  d, obsidianState, caster);
                    placeSingleWorldOffset(level, eye, 0, 2, -d, obsidianState, caster);
                    placeSingleWorldOffset(level, eye,  d, 2, 0, obsidianState, caster);
                    placeSingleWorldOffset(level, eye, -d, 2, 0, obsidianState, caster);
                }
            }
        };
    }

    private static void placePillarWorldOffsetsHeight(ServerLevel level, Vec3 eye,
                                                      int dx, int dz,
                                                      int minDy, int maxDy,
                                                      BlockState state, LivingEntity caster) {
        for (int dy = minDy; dy <= maxDy; dy++) {
            BlockPos pos = BlockPos.containing(eye.x + dx, eye.y + dy, eye.z + dz);
            placeIfReplaceable(level, pos, state, caster);
        }
    }

    public static void summonObsidianSmallCross(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null) return;

        new DelayedTask(2) {
            @Override public void run() {
                if (!caster.isAlive()) return;
                if (caster.level() != level) return;

                Vec3 eye = caster.getEyePosition(1.0F);

                boolean isLongPillar = obsidianState.is(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get());
                int minDy = -1;
                int maxDy = isLongPillar ? -1 : 0;
                int d = 3;

                placePillarWorldOffsetsHeight(level, eye, 0,  d, minDy, maxDy, obsidianState, caster);
                placePillarWorldOffsetsHeight(level, eye, 0, -d, minDy, maxDy, obsidianState, caster);

                placePillarWorldOffsetsHeight(level, eye,  d, 0, minDy, maxDy, obsidianState, caster);
                placePillarWorldOffsetsHeight(level, eye, -d, 0, minDy, maxDy, obsidianState, caster);
            }
        };
    }

    public static void summonObsidianPillar(ServerLevel level, LivingEntity caster, BlockState obsidianState) {
        if (level == null || caster == null) return;

        final Vec3 eye = caster.getEyePosition(1.0F);
        final Vec3 fwd = caster.getLookAngle().normalize();

        Vec3 ahead = eye.add(fwd.scale(2.0D));
        Vec3 bodyLevelAhead = new Vec3(ahead.x, caster.getY(), ahead.z);

        final BlockPos base = BlockPos.containing(bodyLevelAhead).below(1);

        for (int delay = 1; delay <= 12; delay++) {
            final int yOffset = delay - 1;

            new DelayedTask(delay) {
                @Override public void run() {
                    if (!caster.isAlive()) return;

                    BlockPos pos = base.above(yOffset);
                    placeIfReplaceable(level, pos, obsidianState, caster);
                }
            };
        }
    }

    public static void summonShadowObsidianShortPillarShootToward(ServerLevel level, Entity ownerEntity, int maxDistance, Object joint) {
        if (level == null || ownerEntity == null) return;

        BlockState baseState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());

        summonPillarsTowardJoint(level, ownerEntity, baseState, Math.max(2, maxDistance), joint);
    }

    public static void summonShadowObsidianMiddlePillarShootToward(ServerLevel level, Entity ownerEntity, int maxDistance, Object joint) {
        if (level == null || ownerEntity == null) return;

        BlockState baseState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());
        summonPillarsTowardJoint(level, ownerEntity, baseState, maxDistance, joint);
    }

    private static void summonPillarsTowardJoint(ServerLevel level,
                                                 Entity ownerEntity,
                                                 BlockState blockState,
                                                 int maxDistance,
                                                 Object joint) {
        final Vec3[] lockedDir = { null };
        final Vec3[] lockedJoint = { null };
        final Direction[] lockedFacing = { null };
        final int[] anchorY = { Integer.MIN_VALUE };

        for (int dist = 2; dist <= maxDistance + 1; dist++) {
            final int d = dist;

            new DelayedTask(d) {
                @Override public void run() {
                    if (!ownerEntity.isAlive()) return;
                    if (ownerEntity.level() != level) return;
                    if (lockedDir[0] == null) {
                        lockedDir[0] = ownerEntity.getLookAngle().normalize();
                        lockedFacing[0] = ownerEntity.getDirection();

                        lockedJoint[0] = getJointOrVanillaActionPosition(ownerEntity, joint, 0.0F);
                        if (lockedJoint[0] == null) return;
                    }

                    BlockState stateNow = blockState;
                    if (stateNow.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && lockedFacing[0] != null) {
                        stateNow = stateNow.setValue(BlockStateProperties.HORIZONTAL_FACING, lockedFacing[0]);
                    }

                    Vec3 raw = lockedJoint[0].add(lockedDir[0].scale(d));

                    if (d == 2) {
                        anchorY[0] = BlockPos.containing(raw).getY();
                    } else if (anchorY[0] == Integer.MIN_VALUE) {
                        return;
                    }

                    Vec3 placeVec = (d == 2)
                            ? raw
                            : new Vec3(raw.x, anchorY[0] + 0.5D, raw.z);

                    placeIfReplaceable(level, BlockPos.containing(placeVec), stateNow, ownerEntity);
                }
            };
        }
    }

    public static void summonShadowObsidianLongPillarDefense(ServerLevel level, Entity ownerEntity) {
        if (level == null || ownerEntity == null) return;

        if (!ownerEntity.isAlive()) return;
        if (ownerEntity.level() != level) return;

        BlockState longPillarState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());
        Vec3 origin = ownerEntity.getEyePosition(1.0F);
        Vec3 forward = ownerEntity.getLookAngle().normalize();
        Vec3 worldUp = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 left = forward.cross(worldUp);
        if (left.lengthSqr() < 1.0E-6D) {
            Direction facing = ownerEntity.getDirection();
            Direction leftDir = facing.getCounterClockWise();
            left = new Vec3(leftDir.getStepX(), 0.0D, leftDir.getStepZ());
        } else {
            left = left.normalize();
        }

        Vec3 up = left.cross(forward).normalize();
        int[][] localOffsets = {
                { 0, -1, 2},
                {-1, -1, 2},
                { 1, -1, 2},
                {-2, -1, 2},
                { 2, -1, 2},

                { 0, -1, 3},
                {-1, -1, 3},
                { 1, -1, 3},
        };

        for (int[] o : localOffsets) {
            int dx = o[0];
            int dy = o[1];
            int dz = o[2];

            Vec3 target = origin
                    .add(left.scale(dx))
                    .add(up.scale(dy))
                    .add(forward.scale(dz));

            BlockPos pos = BlockPos.containing(target);
            if (level.getBlockState(pos).isAir()) {
                placeIfReplaceable(level, pos, longPillarState, ownerEntity);
            }
        }
    }

    public static void summonShadowObsidianLongPillarDefenseWide(ServerLevel level, Entity ownerEntity) {
        int startDistance = 2;
        int depth = 5;
        int maxHalfWidth = 4;
        int dy = -1;
        int startDelay = 2;
        if (level == null || ownerEntity == null) return;
        if (!ownerEntity.isAlive()) return;
        if (ownerEntity.level() != level) return;
        BlockState longPillarState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());

        final Vec3 origin = ownerEntity.getEyePosition(1.0F);
        Vec3 look = ownerEntity.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            Direction dir = ownerEntity.getDirection();
            forward = new Vec3(dir.getStepX(), 0.0D, dir.getStepZ());
        } else {
            forward = forward.normalize();
        }

        final Vec3 worldUp = new Vec3(0.0D, 1.0D, 0.0D);
        final Vec3 left = forward.cross(worldUp).normalize();
        for (int dz = startDistance; dz < startDistance + depth; dz++) {
            final int fdz = dz;
            final int halfWidth = Math.max(0, maxHalfWidth - (fdz - startDistance));

            final int rowDelay = startDelay + (fdz - startDistance);

            for (int dx = -halfWidth; dx <= halfWidth; dx++) {
                final int fdx = dx;

                Vec3 finalForward = forward;
                new DelayedTask(rowDelay) {
                    @Override
                    public void run() {
                        if (!ownerEntity.isAlive()) return;
                        if (ownerEntity.level() != level) return;

                        Vec3 target = origin
                                .add(left.scale(fdx))
                                .add(worldUp.scale(dy))
                                .add(finalForward.scale(fdz));

                        BlockPos pos = BlockPos.containing(target);
                        placeIfReplaceable(level, pos, longPillarState, ownerEntity);
                    }
                };
            }
        }
    }

    public static void summonShadowObsidianLongPillarShootToward(ServerLevel level, Entity ownerEntity) {
        if (level == null || ownerEntity == null) return;

        BlockState baseState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());

        final Vec3[] lockedEye = { null };
        final Basis[] lockedBasis = { null };
        final Direction[] lockedFacing = { null };

        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 2, 1,  1,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 3, 2,  3,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 4, 4,  5,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 5, 6,  7,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 6, 8,  9,  lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 7, 10, 11, lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 8, 12, 13, lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 9, 14, 15, lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 10, 16, 17, lockedEye, lockedBasis, lockedFacing);
        scheduleLocalEyesForwardLine(level, ownerEntity, baseState, 11, 18, 25, lockedEye, lockedBasis, lockedFacing);
    }

    private static void scheduleLocalEyesForwardLine(ServerLevel level,
                                                     Entity ownerEntity,
                                                     BlockState baseState,
                                                     int delayTicks,
                                                     int zStart,
                                                     int zEnd,
                                                     Vec3[] lockedEye,
                                                     Basis[] lockedBasis,
                                                     Direction[] lockedFacing) {
        new DelayedTask(delayTicks) {
            @Override public void run() {
                if (!ownerEntity.isAlive()) return;
                if (ownerEntity.level() != level) return;
                if (lockedEye[0] == null) {
                    lockedEye[0] = ownerEntity.getEyePosition(1.0F);
                    lockedBasis[0] = basisFromEntity(ownerEntity);
                    lockedFacing[0] = ownerEntity.getDirection();
                }

                BlockState stateNow = baseState;
                if (stateNow.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && lockedFacing[0] != null) {
                    stateNow = stateNow.setValue(BlockStateProperties.HORIZONTAL_FACING, lockedFacing[0]);
                }

                Basis basis = lockedBasis[0];
                Vec3 eye = lockedEye[0];

                int from = Math.min(zStart, zEnd);
                int to = Math.max(zStart, zEnd);

                for (int z = from; z <= to; z++) {
                    Vec3 world = eye
                            .add(basis.up().scale(-1.0))
                            .add(basis.fwd().scale(z));

                    placeIfReplaceable(level, BlockPos.containing(world), stateNow, ownerEntity);
                }
            }
        };
    }

    public static void summonShadowObsidianLongPillarCircle(ServerLevel level, Entity ownerEntity, BlockPos centerPos) {
        if (level == null || ownerEntity == null || centerPos == null) return;
        if (!ownerEntity.isAlive()) return;
        if (ownerEntity.level() != level) return;

        BlockState longPillarState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                .defaultBlockState()
                .setValue(HerobrineObsidianBlock.FROM_PLAYER, ownerEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ownerEntity.getDirection());
        
        scheduleRing(level, ownerEntity, centerPos, longPillarState,
                0, 6,  2.5D, (float) (Math.PI * 2F / 5F));
        scheduleRing(level, ownerEntity, centerPos, longPillarState,
                2, 11, 3.5D, (float) (Math.PI * 2F / 10F));
        scheduleRing(level, ownerEntity, centerPos, longPillarState,
                4, 14, 4.5D, (float) (Math.PI * 2F / 20F));
        scheduleRing(level, ownerEntity, centerPos, longPillarState,
                6, 19, 5.5D, (float) (Math.PI * 2F / 25F));
    }

    private static void scheduleRing(ServerLevel level,
                                     Entity ownerEntity,
                                     BlockPos centerPos,
                                     BlockState blockState,
                                     int delayTicks,
                                     int points,
                                     double radius,
                                     float angleOffset) {

        new DelayedTask(delayTicks) {
            @Override
            public void run() {
                if (!ownerEntity.isAlive()) return;
                if (ownerEntity.level() != level) return;

                int centerX = centerPos.getX();
                int centerZ = centerPos.getZ();

                for (int k = 0; k < points; k++) {
                    float angle = (float) k * ((float) Math.PI * 2.0F / (float) points) + angleOffset;

                    double worldX = centerX + (double) Mth.cos(angle) * radius;
                    double worldZ = centerZ + (double) Mth.sin(angle) * radius;

                    int x = Mth.floor(worldX);
                    int z = Mth.floor(worldZ);

                    int groundY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;

                    BlockPos placePos = new BlockPos(x, groundY, z);
                    if (!level.getBlockState(placePos).canBeReplaced()) {
                        placePos = placePos.above();
                    }

                    placeIfReplaceable(level, placePos, blockState, ownerEntity);
                }
            }
        };
    }

    private static ItemStack createRandomModdedEnchantedBook() {
        List<Enchantment> pool = BuiltInRegistries.ENCHANTMENT.stream()
                .filter(enchantment -> !enchantment.isCurse())
                .toList();

        if (pool.isEmpty()) {
            return new ItemStack(Items.ENCHANTED_BOOK);
        }

        Enchantment enchantment = pool.get(new Random().nextInt(pool.size()));
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(book, new EnchantmentInstance(enchantment, new Random().nextInt(5, 10)));
        return book;
    }

    public static void dropNullLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] drops = new Item[]{
                Items.DIAMOND, Items.DIAMOND,
                Items.ENDER_PEARL, Items.COMPASS,
                Items.ENDER_PEARL, Items.ENDER_PEARL, Items.EMERALD,
                Items.ENCHANTED_BOOK, Items.ENCHANTED_BOOK, Items.ENCHANTED_BOOK,
                Items.ENCHANTED_GOLDEN_APPLE, Items.NETHERITE_INGOT,
                Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.MUSIC_DISC_11
        };

        for (Item item : drops) {
            ItemStack stack = (item == Items.ENCHANTED_BOOK)
                    ? createRandomModdedEnchantedBook()
                    : new ItemStack(item);

            ItemEntity entity = new ItemEntity(level, x, y, z, stack);
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }

    public static void dropEliteHerobrineLoot(LevelAccessor world, double x, double y, double z, String fromElite) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(),
                Items.ENDER_EYE,
                Items.ENDER_EYE,
                Items.ENDER_EYE,
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                Items.ENCHANTED_BOOK, Items.ENCHANTED_BOOK, Items.ENCHANTED_BOOK,
                Items.COAL
        };

        for (Item item : items) {
            ItemStack stack = (item == Items.ENCHANTED_BOOK)
                    ? createRandomModdedEnchantedBook()
                    : new ItemStack(item);

            ItemEntity entity = new ItemEntity(level, x, y, z, stack);
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }

        ItemStack eliteDrop = ItemStack.EMPTY;
        switch (fromElite) {
            case "EnderGlaive" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.ENDER_GLAIVE.get());
            case "ObsidianSledgehammer" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get());
            case "EnderSlayerScythe" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get());
            case "EnderAegis" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.ENDER_AEGIS.get());
            case "DemoniacVoltageReaver" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_HILT.get());
        }

        if (!eliteDrop.isEmpty()) {
            ItemEntity drop = new ItemEntity(level, x, y, z, eliteDrop);
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }
    }

    public static void dropShadowHerobrineLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;
        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(
                    Component.translatable("subtitles.shadow_herobrine_die"),
                    false
            );
        }
        Item[] items = new Item[] {
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),

                Items.ENDER_EYE,
                Items.ENDER_EYE,
                Items.ENDER_EYE,

                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),

                Items.ENCHANTED_BOOK, Items.ENCHANTED_BOOK, Items.ENCHANTED_BOOK,
                Items.COAL,
                AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get(),
                AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()
        };

        for (Item item : items) {
            ItemStack stack = (item == Items.ENCHANTED_BOOK)
                    ? createRandomModdedEnchantedBook()
                    : new ItemStack(item);

            ItemEntity entity = new ItemEntity(level, x, y, z, stack);
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }

    public static void dropHerobrine7Loot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;
        if (level.getServer() != null) {
            level.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("subtitles.herobrine_clone_die"), false);
        }
        Item[] items = new Item[] {
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                Items.ENDER_EYE, Items.ENDER_EYE, Items.ENDER_EYE,
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                Items.ENCHANTED_BOOK,
                Items.COAL,
                AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get(),
                AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()
        };

        for (Item item : items) {
            ItemStack stack = (item == Items.ENCHANTED_BOOK)
                    ? createRandomModdedEnchantedBook()
                    : new ItemStack(item);

            ItemEntity entity = new ItemEntity(level, x, y, z, stack);
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }

    public static void dropLowHerobrineCloneLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                Items.OBSIDIAN, Items.OBSIDIAN, Items.IRON_INGOT, Items.DIAMOND, Items.DIAMOND, Items.CRYING_OBSIDIAN, Items.CRYING_OBSIDIAN,
                Items.NETHERITE_SCRAP, Items.ENDER_PEARL, Items.GOLDEN_APPLE
        };

        for (Item item : items) {
            ItemEntity drop = new ItemEntity(level, x, y, z, new ItemStack(item));
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }
    }

    public static void dropHerobrineCloneLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;
        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("subtitles.herobrine_clone_die"), false);
        }
        Item[] items = new Item[] {
                Items.DIAMOND, Items.DIAMOND, Items.MUSIC_DISC_11, Items.IRON_INGOT,
                Items.EMERALD, Items.EMERALD, Items.ENCHANTED_GOLDEN_APPLE,
                Items.NETHERITE_INGOT, Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.TNT, Items.TNT, Items.ENCHANTED_BOOK, AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get(),
                AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()
        };

        for (Item item : items) {
            ItemStack stack = (item == Items.ENCHANTED_BOOK)
                    ? createRandomModdedEnchantedBook()
                    : new ItemStack(item);

            ItemEntity entity = new ItemEntity(level, x, y, z, stack);
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }

    public static void dropHerobrineChrisLoot(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel serverLevel) {
            Item[] items = new Item[] {
                    AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(), AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModItems.BEDROCK_WEAPON.get(), AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(), Blocks.OAK_SIGN.asItem(), Blocks.OBSIDIAN.asItem(), Blocks.OBSIDIAN.asItem(), Items.NETHERITE_INGOT, Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE, Items.ENDER_EYE, Items.ENDER_EYE, AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get(), Items.ENCHANTED_BOOK
            };

            for (Item item : items) {
                ItemStack stack = (item == Items.ENCHANTED_BOOK)
                        ? createRandomModdedEnchantedBook()
                        : new ItemStack(item);

                ItemEntity entity = new ItemEntity(serverLevel, x, y, z, stack);
                entity.setPickUpDelay(10);
                serverLevel.addFreshEntity(entity);
            }
        }
    }

    public static void dropArmoredHerobrineLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                    Component.translatable("subtitles.herobrine_clone_die"),
                    false
            );

            Item[] items = new Item[] {
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                    Items.ENDER_EYE,
                    Items.ENDER_EYE,
                    Items.SPLASH_POTION,
                    Items.ENCHANTED_BOOK,
                    Blocks.DIAMOND_BLOCK.asItem(),
                    Items.IRON_SWORD,
                    AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get(),
                    AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get(),
                    AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()
            };

            for (Item item : items) {
                ItemStack stack = (item == Items.ENCHANTED_BOOK)
                        ? createRandomModdedEnchantedBook()
                        : new ItemStack(item);

                ItemEntity entity = new ItemEntity(level, x, y, z, stack);
                entity.setPickUpDelay(10);
                serverLevel.addFreshEntity(entity);
            }
        }
    }

    public static void dropShadowHerobrineCloneLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;
        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("subtitles.herobrine_clone_die"), false);
        }
        Item[] drops = new Item[]{
                Items.DIAMOND, Items.DIAMOND,
                Items.MUSIC_DISC_11, Items.IRON_INGOT, Items.EMERALD, Items.EMERALD,
                Items.ENCHANTED_GOLDEN_APPLE, Items.NETHERITE_INGOT,
                Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.TNT, Items.TNT, Items.ENCHANTED_BOOK,
                AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get(),
                AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()
        };

        for (Item item : drops) {
            ItemStack stack = (item == Items.ENCHANTED_BOOK)
                    ? createRandomModdedEnchantedBook()
                    : new ItemStack(item);

            ItemEntity entity = new ItemEntity(level, x, y, z, stack);
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }

    // Portal routing helpers moved into HerobrineUtil from the legacy portal combat utility.
    private static final double WALK_ENTRANCE_RADIUS = 32.0D;
    private static final double WALK_EXIT_TARGET_RADIUS = 14.0D;
    private static final double PROJECTILE_ENTRANCE_RADIUS = 24.0D;
    private static final double PROJECTILE_EXIT_TARGET_RADIUS = 18.0D;
    private static final double COUNTER_BOW_MIN_TARGET_DISTANCE_SQR = 4.0D * 4.0D;
    private static final double COUNTER_BOW_AIM_DOT_THRESHOLD = 0.9D;

    public record PortalRoute(PortalEntity entrance, PortalEntity exit) {
    }

    private record BowCounterThreat(LivingEntity attacker, LivingEntity target) {
    }

    public static boolean isHerobrineSide(Entity entity) {
        return entity instanceof HerobrineMob
                || entity instanceof HerobrineGregEntity
                || entity instanceof LowHerobrineCloneEntity
                || entity instanceof LowShadowHerobrineCloneEntity
                || entity instanceof NullWeapon;
    }

    public static boolean isEnemyOf(LivingEntity caster, LivingEntity entity) {
        return entity != caster
                && entity.isAlive()
                && !entity.isSpectator()
                && !(entity instanceof Player player && player.isCreative())
                && !entity.isAlliedTo(caster)
                && !caster.isAlliedTo(entity)
                && !isHerobrineSide(entity);
    }

    public static boolean canUsePortalApproach(Mob mob) {
        if (!isHerobrineSide(mob)) {
            return false;
        }
        if (mob instanceof HerobrineDragonEntity) {
            return false;
        }
        if (mob.isPassenger() && mob.getVehicle() instanceof HerobrineDragonEntity) {
            return false;
        }
        return !(mob instanceof NullWeapon nullWeapon) || nullWeapon.isReleased();
    }

    public static boolean canUsePortalOwnedBy(LivingEntity user, @Nullable UUID ownerUuid) {
        if (ownerUuid == null || ownerUuid.equals(user.getUUID())) {
            return true;
        }
        if (!(user.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        Entity owner = serverLevel.getEntity(ownerUuid);
        return owner != null && isHerobrineSide(user) && isHerobrineSide(owner);
    }

    @Nullable
    public static PortalRoute findRouteToTarget(Mob mob, LivingEntity target) {
        if (!canUsePortalApproach(mob)) {
            return null;
        }
        return findRouteNearEntity(mob, target, WALK_ENTRANCE_RADIUS, WALK_EXIT_TARGET_RADIUS, true);
    }

    @Nullable
    public static Vec3 getProjectilePortalAim(Entity shooter, LivingEntity target) {
        PortalRoute route = findRouteNearEntity(shooter, target, PROJECTILE_ENTRANCE_RADIUS, PROJECTILE_EXIT_TARGET_RADIUS, false);
        return route == null ? null : route.entrance().getPortalCenter();
    }

    @Nullable
    private static PortalRoute findRouteNearEntity(Entity source, LivingEntity target, double entranceRadius, double exitRadius, boolean walkingRoute) {
        if (!(source.level() instanceof ServerLevel) || target == null || !target.isAlive()) {
            return null;
        }
        if (walkingRoute && source instanceof Mob mob && !canUsePortalApproach(mob)) {
            return null;
        }

        AABB searchBox = source.getBoundingBox().inflate(entranceRadius);
        Vec3 sourceCenter = source.position().add(0.0D, source.getBbHeight() * 0.5D, 0.0D);
        Vec3 targetCenter = entityCenter(target);
        double directTargetDistance = sourceCenter.distanceToSqr(targetCenter);
        PortalRoute bestRoute = null;
        double bestScore = Double.MAX_VALUE;

        for (PortalEntity portal : source.level().getEntitiesOfClass(PortalEntity.class, searchBox)) {
            if (!isUsablePortalFor(source, portal)) {
                continue;
            }

            PortalEntity linkedPortal = portal.getLinkedPortal();
            if (linkedPortal == null || !isUsablePortalFor(source, linkedPortal)) {
                continue;
            }

            double exitDistance = linkedPortal.getPortalCenter().distanceToSqr(targetCenter);
            if (exitDistance > exitRadius * exitRadius) {
                continue;
            }

            double entranceDistance = portal.getPortalCenter().distanceToSqr(sourceCenter);
            if (walkingRoute && entranceDistance >= directTargetDistance) {
                continue;
            }

            double score = walkingRoute ? exitDistance + entranceDistance * 0.35D : entranceDistance + exitDistance * 0.35D;
            if (score < bestScore) {
                bestScore = score;
                bestRoute = new PortalRoute(portal, linkedPortal);
            }
        }

        return bestRoute;
    }

    private static boolean isUsablePortalFor(Entity user, PortalEntity portal) {
        if (portal == null || portal.isRemoved() || !portal.isAlive()) {
            return false;
        }

        UUID ownerUuid = portal.getOwnerUUID();
        if (ownerUuid == null || ownerUuid.equals(user.getUUID())) {
            return true;
        }
        if (!(user.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        Entity owner = serverLevel.getEntity(ownerUuid);
        if (owner == null) {
            return false;
        }
        if (user instanceof HerobrineDragonEntity) {
            return isHerobrineSide(owner);
        }
        return isHerobrineSide(user) && isHerobrineSide(owner);
    }

    public static List<LivingEntity> findSupportHerobrines(LivingEntity caster, double radius) {
        AABB searchBox = caster.getBoundingBox().inflate(radius);
        List<LivingEntity> candidates = caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity ->
                entity != caster
                        && entity.isAlive()
                        && isHerobrineSide(entity)
                        && !(entity instanceof HerobrineGregEntity)
        );

        candidates.sort(Comparator.comparingDouble(caster::distanceToSqr));
        return candidates;
    }

    public static boolean hasNearbyPortalGroup(LivingEntity anchor, @Nullable UUID ownerUuid, int requiredCount, double radius) {
        if (requiredCount <= 0) return true;
        return findNearbyPortalGroup(anchor, ownerUuid, requiredCount, radius) != null;
    }

    @Nullable
    public static UUID findNearbyPortalGroup(LivingEntity anchor, @Nullable UUID ownerUuid, int requiredCount, double radius) {
        if (anchor == null || requiredCount <= 0) return null;

        Map<UUID, Integer> portalGroupCounts = new HashMap<>();
        for (PortalEntity portal : anchor.level().getEntitiesOfClass(PortalEntity.class, anchor.getBoundingBox().inflate(radius))) {
            if (portal.isRemoved() || !portal.isAlive() || portal.tickCount >= PortalEntity.LIFETIME_TICKS) continue;

            UUID portalGroupUuid = portal.getPortalGroupUUID();
            if (portalGroupUuid == null) continue;
            if (ownerUuid != null && !ownerUuid.equals(portal.getOwnerUUID())) continue;

            int count = portalGroupCounts.merge(portalGroupUuid, 1, Integer::sum);
            if (count >= requiredCount) return portalGroupUuid;
        }
        return null;
    }

    @Nullable
    public static LivingEntity findEnemyForSupport(LivingEntity support, @Nullable LivingEntity fallback, double radius) {
        if (support instanceof Mob mob && mob.getTarget() != null && isEnemyOf(support, mob.getTarget())) {
            return mob.getTarget();
        }
        if (fallback != null && isEnemyOf(support, fallback)) {
            return fallback;
        }
        return findNearestEnemy(support, radius);
    }

    @Nullable
    public static LivingEntity findThreateningEnemy(LivingEntity caster, @Nullable LivingEntity support, double radius) {
        LivingEntity recentThreat = chooseNearestThreat(caster, support, radius,
                caster.getLastHurtByMob(),
                support != null ? support.getLastHurtByMob() : null);
        if (recentThreat != null) {
            return recentThreat;
        }

        LivingEntity targetedThreat = chooseNearestThreat(caster, support, radius,
                caster instanceof Mob mob ? mob.getTarget() : null,
                support instanceof Mob mob ? mob.getTarget() : null);
        if (targetedThreat != null) {
            return targetedThreat;
        }

        BowCounterThreat rangedThreat = findBowCounterThreat(caster, support, radius);
        if (rangedThreat != null) {
            return rangedThreat.attacker();
        }

        AABB searchBox = support == null
                ? caster.getBoundingBox().inflate(radius)
                : caster.getBoundingBox().minmax(support.getBoundingBox()).inflate(radius);
        return caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isThreateningEnemy(caster, support, entity, radius))
                .stream()
                .min(Comparator.comparingDouble(entity -> threatDistanceSqr(caster, support, entity)))
                .orElse(null);
    }

    @Nullable
    private static LivingEntity findNearestEnemy(LivingEntity caster, double radius) {
        if (caster instanceof Mob mob && mob.getTarget() != null && isEnemyOf(caster, mob.getTarget())) {
            return mob.getTarget();
        }

        AABB searchBox = caster.getBoundingBox().inflate(radius);
        return caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isEnemyOf(caster, entity))
                .stream()
                .min(Comparator.comparingDouble(caster::distanceToSqr))
                .orElse(null);
    }

    @Nullable
    private static LivingEntity chooseNearestThreat(LivingEntity caster, @Nullable LivingEntity support, double radius, @Nullable LivingEntity first, @Nullable LivingEntity second) {
        LivingEntity best = null;
        double bestDistance = Double.MAX_VALUE;
        for (LivingEntity candidate : new LivingEntity[]{first, second}) {
            if (candidate == null || !isThreatCandidate(caster, support, candidate, radius)) {
                continue;
            }
            double distance = threatDistanceSqr(caster, support, candidate);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }
        return best;
    }

    private static boolean isThreatCandidate(LivingEntity caster, @Nullable LivingEntity support, LivingEntity candidate, double radius) {
        if (!(isEnemyOf(caster, candidate) || support != null && isEnemyOf(support, candidate))) {
            return false;
        }
        double radiusSqr = radius * radius;
        return threatDistanceSqr(caster, support, candidate) <= radiusSqr;
    }

    private static boolean isThreateningEnemy(LivingEntity caster, @Nullable LivingEntity support, LivingEntity candidate, double radius) {
        if (!isThreatCandidate(caster, support, candidate, radius)) {
            return false;
        }
        if (isBowCounterThreat(caster, support, candidate, radius)) {
            return true;
        }
        if (candidate instanceof Mob mob) {
            return mob.getTarget() == caster || support != null && mob.getTarget() == support;
        }
        return false;
    }

    private static double threatDistanceSqr(LivingEntity caster, @Nullable LivingEntity support, LivingEntity entity) {
        double distance = caster.distanceToSqr(entity);
        if (support != null) {
            distance = Math.min(distance, support.distanceToSqr(entity));
        }
        return distance;
    }

    private static Vec3 entityCenter(Entity entity) {
        return new Vec3(entity.getX(), entity.getY() + entity.getBbHeight() * 0.5D, entity.getZ());
    }

    @Nullable
    private static BowCounterThreat findBowCounterThreat(LivingEntity caster, @Nullable LivingEntity support, double radius) {
        AABB searchBox = caster.getBoundingBox().inflate(radius);
        BowCounterThreat bestThreat = null;
        double bestCasterDistance = Double.MAX_VALUE;
        double bestTargetDistance = Double.MAX_VALUE;

        for (LivingEntity attacker : caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isPotentialBowCounterAttacker(caster, entity))) {
            LivingEntity target = resolveBowCounterTarget(caster, support, attacker, radius);
            if (target == null) {
                continue;
            }

            double casterDistance = caster.distanceToSqr(attacker);
            double targetDistance = attacker.distanceToSqr(target);
            if (bestThreat == null
                    || casterDistance < bestCasterDistance
                    || casterDistance == bestCasterDistance && targetDistance < bestTargetDistance) {
                bestThreat = new BowCounterThreat(attacker, target);
                bestCasterDistance = casterDistance;
                bestTargetDistance = targetDistance;
            }
        }

        return bestThreat;
    }

    private static boolean isPotentialBowCounterAttacker(LivingEntity caster, LivingEntity attacker) {
        return attacker != caster
                && attacker.isAlive()
                && isEnemyOf(caster, attacker)
                && hasBowReady(attacker);
    }

    private static boolean hasBowReady(LivingEntity attacker) {
        return attacker.getMainHandItem().getItem() instanceof BowItem
                || attacker.getOffhandItem().getItem() instanceof BowItem
                || attacker.getUseItem().getItem() instanceof BowItem;
    }

    private static boolean isBowCounterThreat(LivingEntity caster, @Nullable LivingEntity support, LivingEntity attacker, double radius) {
        return resolveBowCounterTarget(caster, support, attacker, radius) != null;
    }

    @Nullable
    private static LivingEntity resolveBowCounterTarget(LivingEntity caster, @Nullable LivingEntity support, LivingEntity attacker, double radius) {
        if (!isPotentialBowCounterAttacker(caster, attacker)) {
            return null;
        }

        if (attacker instanceof Mob mob) {
            LivingEntity mobTarget = mob.getTarget();
            if (isValidBowCounterTarget(caster, support, attacker, mobTarget)) {
                return mobTarget;
            }
        }

        AABB searchBox = attacker.getBoundingBox().inflate(radius);
        LivingEntity bestTarget = null;
        double bestAim = COUNTER_BOW_AIM_DOT_THRESHOLD;
        double bestDistance = Double.MAX_VALUE;
        Vec3 look = attacker.getLookAngle();
        if (look.lengthSqr() < 1.0E-4D) {
            return null;
        }
        look = look.normalize();

        for (LivingEntity candidate : attacker.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isValidBowCounterTarget(caster, support, attacker, entity))) {
            Vec3 direction = entityCenter(candidate).subtract(attacker.getEyePosition());
            if (direction.lengthSqr() < 1.0E-4D) {
                continue;
            }

            double aimDot = look.dot(direction.normalize());
            double distance = attacker.distanceToSqr(candidate);
            if (aimDot > bestAim || aimDot == bestAim && distance < bestDistance) {
                bestTarget = candidate;
                bestAim = aimDot;
                bestDistance = distance;
            }
        }

        return bestTarget;
    }

    private static boolean isValidBowCounterTarget(LivingEntity caster, @Nullable LivingEntity support, LivingEntity attacker, @Nullable LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (!(target == caster
                || target == support
                || isSupportedPortalDefenseTarget(caster, target))) {
            return false;
        }
        return attacker.hasLineOfSight(target)
                && attacker.distanceToSqr(target) >= COUNTER_BOW_MIN_TARGET_DISTANCE_SQR;
    }

    private static boolean isSupportedPortalDefenseTarget(LivingEntity caster, LivingEntity target) {
        return target == caster
                || target instanceof HerobrineMob
                || target instanceof LowHerobrineCloneEntity
                || target instanceof LowShadowHerobrineCloneEntity;
    }

    // Greg/Transporter support helpers moved into HerobrineUtil from the legacy support portal utility.
    public static final double SUPPORT_SEARCH_RADIUS = 48.0D;
    public static final double APPROACH_MIN_DISTANCE = 12.0D;
    private static final double COUNTER_SEARCH_RADIUS = SUPPORT_SEARCH_RADIUS + 24.0D;
    private static final double COUNTER_MIN_ATTACK_DISTANCE = 6.0D;
    private static final double COUNTER_AIM_DOT = 0.94D;

    public static boolean canSpawnPortalPair(HerobrinePortalSupportCaster supportCaster) {
        Mob caster = supportCaster.getPortalSupportMob();
        return caster.level() instanceof ServerLevel serverLevel && TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2);
    }

    public static List<LivingEntity> findSupportedHerobrines(HerobrinePortalSupportCaster supportCaster, double radius) {
        Mob caster = supportCaster.getPortalSupportMob();
        AABB searchBox = caster.getBoundingBox().inflate(radius);
        return caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, ally -> ally != caster && ally.isAlive() && !ally.isRemoved() && supportCaster.canSupportPortalAlly(ally) && !isRidingHerobrineDragon(ally)).stream().sorted(Comparator.comparingDouble(caster::distanceToSqr)).toList();
    }

    @Nullable
    public static LivingEntity findDangerousReactionSupport(HerobrinePortalSupportCaster supportCaster) {
        for (LivingEntity support : findSupportedHerobrines(supportCaster, SUPPORT_SEARCH_RADIUS)) {
            if (support instanceof Mob mob && DangerousReaction.isPerformingDangerousReaction(mob)) return support;
        }
        return null;
    }

    public static boolean spawnDangerousReactionSupportPortal(HerobrinePortalSupportCaster supportCaster, LivingEntity support) {
        Mob caster = supportCaster.getPortalSupportMob();
        if (!(caster.level() instanceof ServerLevel serverLevel) || support == null || !support.isAlive() || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)) return false;

        Vec3 entrance = getMovementEntrancePosition(support, support instanceof Mob mob ? mob.getTarget() : null, 1.35D, true);
        Vec3 exit = findSafeSurfaceAround(serverLevel, caster, caster, 2.5D, 5.0D, null);
        if (entrance == null || exit == null) return false;
        return TransporterFragmentItem.spawnLinkedPortalPair(serverLevel, caster, entrance, exit) > 0;
    }

    @Nullable
    public static ApproachPortalPlan findApproachPortalPlan(HerobrinePortalSupportCaster supportCaster) {
        Mob caster = supportCaster.getPortalSupportMob();
        if (!(caster.level() instanceof ServerLevel serverLevel)) return null;

        ApproachPortalPlan best = null;
        double bestDistance = APPROACH_MIN_DISTANCE * APPROACH_MIN_DISTANCE;
        for (LivingEntity support : findSupportedHerobrines(supportCaster, SUPPORT_SEARCH_RADIUS)) {
            if (!(support instanceof Mob supportMob) || DangerousReaction.isPerformingDangerousReaction(supportMob)) continue;
            LivingEntity target = supportMob.getTarget();
            if (target == null || !target.isAlive() || target.isRemoved() || !isEnemyOf(caster, target)) continue;

            double distance = support.distanceToSqr(target);
            if (distance < bestDistance) continue;

            Vec3 entrance = getMovementEntrancePosition(support, target, 1.75D, false);
            Vec3 exit = findSafeTargetPortalPosition(serverLevel, support, target);
            if (entrance == null || exit == null) continue;

            bestDistance = distance;
            best = new ApproachPortalPlan(support, target, entrance, exit);
        }
        return best;
    }

    public static boolean spawnApproachPortal(HerobrinePortalSupportCaster supportCaster, ApproachPortalPlan plan) {
        Mob caster = supportCaster.getPortalSupportMob();
        if (!(caster.level() instanceof ServerLevel serverLevel) || plan == null || !plan.support().isAlive() || !plan.target().isAlive() || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)) return false;
        Vec3 entrance = getMovementEntrancePosition(plan.support(), plan.target(), 1.75D, false);
        Vec3 exit = findSafeTargetPortalPosition(serverLevel, plan.support(), plan.target());
        if (entrance == null || exit == null) return false;
        return TransporterFragmentItem.spawnLinkedPortalPair(serverLevel, caster, entrance, exit) > 0;
    }

    @Nullable
    public static ProjectileCounterPlan findProjectileCounterPlan(HerobrinePortalSupportCaster supportCaster) {
        Mob caster = supportCaster.getPortalSupportMob();
        List<LivingEntity> supports = findSupportedHerobrines(supportCaster, SUPPORT_SEARCH_RADIUS);
        if (supports.isEmpty()) return null;

        ProjectileCounterPlan arrowPlan = findFlyingArrowCounterPlan(caster, supports);
        if (arrowPlan != null) return arrowPlan;
        return findBowAimCounterPlan(caster, supports);
    }

    public static boolean spawnProjectileCounterPortal(HerobrinePortalSupportCaster supportCaster, ProjectileCounterPlan plan) {
        Mob caster = supportCaster.getPortalSupportMob();
        if (!(caster.level() instanceof ServerLevel serverLevel) || plan == null || !plan.attacker().isAlive() || !plan.support().isAlive() || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)) return false;

        Vec3 entrance = plan.arrow() != null && plan.arrow().isAlive() ? getArrowEntrance(plan.arrow(), plan.support()) : getCounterEntrance(plan.attacker(), plan.support());
        Vec3 exit = getCounterExitBehindAttacker(plan.attacker(), plan.support());
        if (entrance == null || exit == null) return false;
        return TransporterFragmentItem.spawnLinkedPortalPair(serverLevel, caster, entrance, exit) > 0;
    }

    public static boolean spawnSelfDangerousReactionPortal(HerobrinePortalSupportCaster supportCaster) {
        Mob caster = supportCaster.getPortalSupportMob();
        if (!(caster.level() instanceof ServerLevel serverLevel) || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)) return false;

        LivingEntity target = caster.getTarget();
        Vec3 away = target == null ? backwardFromYaw(caster) : horizontalDirection(caster.position().subtract(target.position()));
        if (away.lengthSqr() < 1.0E-6D) away = backwardFromYaw(caster);
        Vec3 entrance = caster.position().add(away.scale(1.55D));
        Vec3 exit = findSafeSurfaceAround(serverLevel, caster, caster, 14.0D, 24.0D, target);
        if (exit == null) return false;
        return TransporterFragmentItem.spawnLinkedPortalPair(serverLevel, caster, entrance, exit) > 0;
    }

    public static boolean canSummonLowCloneSupport(HerobrinePortalSupportCaster supportCaster) {
        Mob caster = supportCaster.getPortalSupportMob();
        return supportCaster.canUseSupportPortalAction() && caster.onGround() && supportCaster.getLowCloneSupportCooldown() <= 0 && supportCaster.hasAvailableCombatLowCloneSupportSlot() && findLowCloneSupportPlan(supportCaster) != null;
    }

    public static boolean summonLowCloneSupport(HerobrinePortalSupportCaster supportCaster) {
        Mob caster = supportCaster.getPortalSupportMob();
        if (!(caster.level() instanceof ServerLevel serverLevel)) return false;
        LowCloneSupportPlan plan = findLowCloneSupportPlan(supportCaster);
        if (plan == null) return false;

        int available = supportCaster.getAvailableCombatLowCloneSupportSlotCount();
        int count = Math.min(1 + caster.getRandom().nextInt(3), available);
        int spawned = 0;
        for (int i = 0; i < count && supportCaster.hasAvailableCombatLowCloneSupportSlot(); i++) {
            if (spawnCombatLowClone(serverLevel, supportCaster, plan.anchor(), plan.enemy())) spawned++;
        }
        if (spawned <= 0) return false;

        supportCaster.markPortalSupport();
        supportCaster.setLowCloneSupportCooldown();
        return true;
    }



    @Nullable
    private static LowCloneSupportPlan findLowCloneSupportPlan(HerobrinePortalSupportCaster supportCaster) {
        Mob caster = supportCaster.getPortalSupportMob();
        for (LivingEntity support : findSupportedHerobrines(supportCaster, SUPPORT_SEARCH_RADIUS)) {
            if (!(support instanceof Mob supportMob)) continue;
            LivingEntity enemy = supportMob.getTarget();
            if (enemy != null && enemy.isAlive() && !enemy.isRemoved() && isEnemyOf(caster, enemy)) return new LowCloneSupportPlan(support, enemy);
        }

        if (caster instanceof TransporterHerobrineCloneEntity) {
            LivingEntity ownTarget = caster.getTarget();
            if (ownTarget != null && ownTarget.isAlive() && !ownTarget.isRemoved() && isEnemyOf(caster, ownTarget)) return new LowCloneSupportPlan(caster, ownTarget);

            LivingEntity nearbyEnemy = findEnemyForSupport(caster, null, SUPPORT_SEARCH_RADIUS);
            if (nearbyEnemy != null && nearbyEnemy.isAlive() && !nearbyEnemy.isRemoved() && isEnemyOf(caster, nearbyEnemy)) return new LowCloneSupportPlan(caster, nearbyEnemy);
        }
        return null;
    }

    private static boolean spawnCombatLowClone(ServerLevel serverLevel, HerobrinePortalSupportCaster supportCaster, LivingEntity anchor, LivingEntity enemy) {
        Mob caster = supportCaster.getPortalSupportMob();
        Vec3 spawn = findSafeSurfaceAround(serverLevel, caster, anchor, 2.5D, 8.0D, enemy);
        if (spawn == null) return false;

        Mob clone = caster.getRandom().nextBoolean() ? new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel) : new LowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), serverLevel);
        clone.moveTo(spawn.x, spawn.y, spawn.z, caster.getYRot(), caster.getXRot());
        if (!serverLevel.noCollision(clone)) return false;

        configureCombatLowClone(clone);
        equipLowCloneGear(clone, caster.getRandom());
        clone.setTarget(enemy);
        clone.lookAt(EntityAnchorArgument.Anchor.EYES, enemy.getEyePosition());
        clone.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(clone.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        if (!serverLevel.addFreshEntity(clone)) return false;
        if (!supportCaster.claimCombatLowCloneSupportSlot(clone)) {
            clone.discard();
            return false;
        }

        TeamUtil.addOrJoinTeam(clone, "herobrine");
        AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY.with(() -> clone), new ClientboundHerobrinePortalFx(spawn));
        return true;
    }

    private static void configureCombatLowClone(Mob clone) {
        if (clone instanceof LowHerobrineCloneEntity lowClone) {
            lowClone.setSummoned(true);
            lowClone.setRenderPortal(false);
        } else if (clone instanceof LowShadowHerobrineCloneEntity lowShadowClone) {
            lowShadowClone.setSummoned(true);
            lowShadowClone.setRenderPortal(false);
        }
    }

    private static void equipLowCloneGear(Mob clone, RandomSource random) {
        if (random.nextFloat() < 0.3F) clone.setItemSlot(EquipmentSlot.HEAD, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_HELMET.get()), random));
        if (random.nextFloat() < 0.3F) clone.setItemSlot(EquipmentSlot.CHEST, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get()), random));
        if (random.nextFloat() < 0.3F) clone.setItemSlot(EquipmentSlot.LEGS, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_LEGGINGS.get()), random));
        if (random.nextFloat() < 0.3F) clone.setItemSlot(EquipmentSlot.FEET, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_BOOTS.get()), random));
        clone.setItemSlot(EquipmentSlot.MAINHAND, damageRandomly(new ItemStack(HerobrineGregEntity.listWeapons.get(random.nextInt(HerobrineGregEntity.listWeapons.size()))), random));
    }

    private static ItemStack damageRandomly(ItemStack stack, RandomSource random) {
        if (!stack.isDamageableItem()) return stack;
        int maxDamage = stack.getMaxDamage();
        stack.setDamageValue(random.nextInt(Math.max(1, maxDamage / 3), Math.max(2, maxDamage * 3 / 4)));
        return stack;
    }

    @Nullable
    private static ProjectileCounterPlan findFlyingArrowCounterPlan(Mob caster, List<LivingEntity> supports) {
        AABB searchBox = caster.getBoundingBox().inflate(COUNTER_SEARCH_RADIUS);
        for (AbstractArrow arrow : caster.level().getEntitiesOfClass(AbstractArrow.class, searchBox, arrow -> arrow.isAlive() && arrow.getDeltaMovement().lengthSqr() > 1.0E-5D)) {
            if (!(arrow.getOwner() instanceof LivingEntity attacker) || !isEnemyOf(caster, attacker)) continue;
            Vec3 velocity = arrow.getDeltaMovement();
            if (velocity.lengthSqr() < 1.0E-5D) continue;
            Vec3 direction = velocity.normalize();
            for (LivingEntity support : supports) {
                Vec3 toSupport = supportEntityCenter(support).subtract(arrow.position());
                double along = toSupport.dot(direction);
                if (along <= 1.0D || along > 24.0D) continue;
                Vec3 closest = arrow.position().add(direction.scale(along));
                if (closest.distanceToSqr(supportEntityCenter(support)) <= 2.25D) return new ProjectileCounterPlan(attacker, support, arrow);
            }
        }
        return null;
    }

    @Nullable
    private static ProjectileCounterPlan findBowAimCounterPlan(Mob caster, List<LivingEntity> supports) {
        AABB searchBox = caster.getBoundingBox().inflate(COUNTER_SEARCH_RADIUS);
        ProjectileCounterPlan best = null;
        double bestDistance = Double.MAX_VALUE;

        for (LivingEntity attacker : caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> entity != caster && entity.isAlive() && isEnemyOf(caster, entity) && hasSupportBowReady(entity))) {
            Vec3 look = attacker.getLookAngle();
            if (look.lengthSqr() < 1.0E-5D) continue;
            look = look.normalize();

            for (LivingEntity support : supports) {
                if (!attacker.hasLineOfSight(support) || attacker.distanceToSqr(support) < COUNTER_MIN_ATTACK_DISTANCE * COUNTER_MIN_ATTACK_DISTANCE) continue;
                Vec3 direction = supportEntityCenter(support).subtract(attacker.getEyePosition());
                if (direction.lengthSqr() < 1.0E-5D || look.dot(direction.normalize()) < COUNTER_AIM_DOT) continue;
                double distance = attacker.distanceToSqr(support);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    best = new ProjectileCounterPlan(attacker, support, null);
                }
            }
        }
        return best;
    }

    private static boolean hasSupportBowReady(LivingEntity entity) {
        return entity.getMainHandItem().getItem() instanceof BowItem || entity.getOffhandItem().getItem() instanceof BowItem || entity.getUseItem().getItem() instanceof BowItem;
    }

    @Nullable
    private static Vec3 getArrowEntrance(AbstractArrow arrow, LivingEntity support) {
        Vec3 velocity = arrow.getDeltaMovement();
        if (velocity.lengthSqr() < 1.0E-5D) return null;
        Vec3 direction = velocity.normalize();
        Vec3 toSupport = supportEntityCenter(support).subtract(arrow.position());
        double along = Math.max(1.0D, Math.min(4.0D, toSupport.dot(direction) * 0.35D));
        return arrow.position().add(direction.scale(along));
    }

    @Nullable
    private static Vec3 getCounterEntrance(LivingEntity attacker, LivingEntity support) {
        Vec3 from = supportEntityCenter(attacker);
        Vec3 to = supportEntityCenter(support);
        Vec3 direction = to.subtract(from);
        if (direction.lengthSqr() < 1.0E-5D) return null;
        Vec3 pos = from.add(direction.scale(0.62D));
        return new Vec3(pos.x, Math.max(attacker.getY(), support.getY()), pos.z);
    }

    @Nullable
    private static Vec3 getCounterExitBehindAttacker(LivingEntity attacker, LivingEntity support) {
        Vec3 direction = horizontalDirection(support.position().subtract(attacker.position()));
        if (direction.lengthSqr() < 1.0E-5D) return null;
        Vec3 behind = attacker.position().subtract(direction.scale(2.5D));
        return new Vec3(behind.x, attacker.getY(), behind.z);
    }

    @Nullable
    private static Vec3 getMovementEntrancePosition(LivingEntity support, @Nullable LivingEntity target, double distance, boolean allowAwayMovement) {
        Vec3 targetDirection = target == null ? Vec3.ZERO : horizontalDirection(target.position().subtract(support.position()));
        Vec3 movement = horizontalDirection(support.getDeltaMovement());
        Vec3 direction = movement;
        if (direction.lengthSqr() < 1.0E-5D || !allowAwayMovement && targetDirection.lengthSqr() > 1.0E-5D && direction.dot(targetDirection) < 0.15D) direction = targetDirection;
        if (direction.lengthSqr() < 1.0E-5D && target != null) direction = targetDirection;
        if (direction.lengthSqr() < 1.0E-5D) direction = forwardFromYaw(support);
        if (direction.lengthSqr() < 1.0E-5D) return null;
        Vec3 pos = support.position().add(direction.scale(distance));
        return new Vec3(pos.x, support.getY(), pos.z);
    }

    @Nullable
    private static Vec3 findSafeTargetPortalPosition(ServerLevel serverLevel, LivingEntity portalUser, LivingEntity target) {
        double offset = 2.0D;
        double referenceX = target.getX();
        double referenceZ = target.getZ();
        float referenceYaw = target.yHeadRot;
        double sin = Math.sin(Math.toRadians(referenceYaw));
        double cos = Math.cos(Math.toRadians(referenceYaw));
        int minY = serverLevel.getMinBuildHeight() + 1;
        int maxY = serverLevel.getMaxBuildHeight() - 2;
        int baseY = Mth.clamp(target.blockPosition().getY(), minY, maxY);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int tries = 0; tries < 10 && offset > 0.25D; tries++, offset -= 0.25D) {
            double x = referenceX + offset * sin;
            double z = referenceZ - offset * cos;
            mutable.set(Mth.floor(x), baseY, Mth.floor(z));
            if (!serverLevel.isLoaded(mutable)) continue;

            int scan = 0;
            while (scan++ < 12 && mutable.getY() > minY) {
                BlockPos belowPos = mutable.below();
                BlockState below = serverLevel.getBlockState(belowPos);
                if (below.isFaceSturdy(serverLevel, belowPos, Direction.UP) && !below.is(Blocks.VOID_AIR)) break;
                mutable.move(0, -1, 0);
            }

            BlockPos belowPos = mutable.below();
            BlockState below = serverLevel.getBlockState(belowPos);
            BlockState feet = serverLevel.getBlockState(mutable);
            BlockState head = serverLevel.getBlockState(mutable.above());
            boolean solidBelow = below.isFaceSturdy(serverLevel, belowPos, Direction.UP) && !below.is(Blocks.VOID_AIR);
            boolean freeFeet = feet.isAir() || feet.getBlock() instanceof BushBlock;
            boolean freeHead = head.isAir() || head.getBlock() instanceof BushBlock;
            if (!solidBelow || !freeFeet || !freeHead) continue;

            Vec3 candidate = new Vec3(mutable.getX() + 0.5D, mutable.getY(), mutable.getZ() + 0.5D);
            if (serverLevel.noCollision(portalUser, portalUser.getBoundingBox().move(candidate.subtract(portalUser.position())).deflate(1.0E-4D))) return candidate;
        }
        return null;
    }

    @Nullable
    private static Vec3 findSafeSurfaceAround(ServerLevel serverLevel, LivingEntity portalUser, LivingEntity anchor, double minDistance, double maxDistance, @Nullable LivingEntity avoid) {
        RandomSource random = portalUser.getRandom();
        Vec3 best = null;
        double bestAvoidDistance = -1.0D;

        for (int attempt = 0; attempt < 24; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double distance = minDistance + random.nextDouble() * Math.max(0.01D, maxDistance - minDistance);
            double x = anchor.getX() + Math.cos(angle) * distance;
            double z = anchor.getZ() + Math.sin(angle) * distance;
            int y = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mth.floor(x), Mth.floor(z));
            BlockPos pos = BlockPos.containing(x, y, z);
            if (!isSafeSurface(serverLevel, portalUser, pos)) continue;

            Vec3 candidate = new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            if (avoid == null) return candidate;
            double avoidDistance = candidate.distanceToSqr(avoid.position());
            if (avoidDistance > bestAvoidDistance) {
                bestAvoidDistance = avoidDistance;
                best = candidate;
            }
        }
        return best;
    }

    private static boolean isSafeSurface(ServerLevel serverLevel, LivingEntity portalUser, BlockPos pos) {
        if (!serverLevel.isLoaded(pos) || !serverLevel.getWorldBorder().isWithinBounds(pos)) return false;
        BlockState below = serverLevel.getBlockState(pos.below());
        BlockState feet = serverLevel.getBlockState(pos);
        BlockState head = serverLevel.getBlockState(pos.above());
        if (!below.isFaceSturdy(serverLevel, pos.below(), Direction.UP) || below.is(Blocks.VOID_AIR)) return false;
        if (!(feet.isAir() || feet.getBlock() instanceof BushBlock) || !(head.isAir() || head.getBlock() instanceof BushBlock)) return false;
        Vec3 candidate = new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        return serverLevel.noCollision(portalUser, portalUser.getBoundingBox().move(candidate.subtract(portalUser.position())).deflate(1.0E-4D));
    }

    private static boolean isRidingHerobrineDragon(Entity entity) {
        return entity.isPassenger() && entity.getVehicle() instanceof HerobrineDragonEntity;
    }

    private static Vec3 supportEntityCenter(Entity entity) {
        return new Vec3(entity.getX(), entity.getY() + entity.getBbHeight() * 0.5D, entity.getZ());
    }

    private static Vec3 horizontalDirection(Vec3 vector) {
        Vec3 flattened = new Vec3(vector.x, 0.0D, vector.z);
        return flattened.lengthSqr() < 1.0E-6D ? Vec3.ZERO : flattened.normalize();
    }

    private static Vec3 forwardFromYaw(LivingEntity entity) {
        double yaw = Math.toRadians(entity.getYRot());
        return new Vec3(-Math.sin(yaw), 0.0D, Math.cos(yaw));
    }

    private static Vec3 backwardFromYaw(LivingEntity entity) {
        return forwardFromYaw(entity).scale(-1.0D);
    }

    public record ApproachPortalPlan(LivingEntity support, LivingEntity target, Vec3 entrance, Vec3 exit) {
    }

    public record ProjectileCounterPlan(LivingEntity attacker, LivingEntity support, @Nullable AbstractArrow arrow) {
    }

    private record LowCloneSupportPlan(LivingEntity anchor, LivingEntity enemy) {
    }

}
