package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.PlayerNpcOnHurtProcedure;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.EquipmentDataLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class PlayerNpcEntity extends PlayerMobEntity {
    private final SimpleContainer inventory = new SimpleContainer(27);
    private int gapCooldown;
    private int enderPearlCooldown;

    public int getGapCooldown() {
        return gapCooldown;
    }

    public int getEnderPearlCooldown() {
        return enderPearlCooldown;
    }

    public void setGapCooldown() {
        this.gapCooldown = random.nextInt(60, 200);
    }

    public void setEnderPearlCooldown() {
        this.enderPearlCooldown = random.nextInt(60, 200);
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public PlayerNpcEntity(PlayMessages.SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.PLAYER_NPC.get(), level);
    }

    public PlayerNpcEntity(EntityType<? extends PlayerNpcEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.6F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Inventory", this.inventory.createTag());
        tag.putInt("GapCooldown", this.gapCooldown);
        tag.putInt("EnderPearlCooldown", this.enderPearlCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)) {
            this.inventory.fromTag(tag.getList("Inventory", Tag.TAG_COMPOUND));
        }
        this.gapCooldown = tag.getInt("GapCooldown");
        this.enderPearlCooldown = tag.getInt("EnderPearlCooldown");
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
            }
        }
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void hostileHunterPlayerMob() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerMobEntity.class, true));
        CommonGoals.attackAllMonstersGoals(this);
        CommonGoals.attackAllNpcGoals(this);
    }
    private void villagerHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerMobEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.2D, 1.8D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, JevEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        CommonGoals.attackAllVillagerArmyGoal(this);
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));
    }
    private void monsterHunterPlayerMob() {
        CommonGoals.attackAllMonstersGoals(this);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
    }
    private void playerHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerNpcEntity.class, true));
        CommonGoals.attackAllNpcGoals(this);
    }
    private void animalHunterPlayerMob() {
        CommonGoals.runAwayFromHerobrineGoals(this, 20.0F);
        CommonGoals.runAwayFromVillagerArmyGoals(this);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerNpcEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.2D, 1.8D));
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);

        CompoundTag data = this.getPersistentData();
        String role;

        if (!data.contains("behavior")) {
            List<String> roles = List.of(
                    "monster_hunter",
                    "player_hunter",
                    "hostile_hunt",
                    "passive_hunt",
                    "animal_hunter"
            );
            role = roles.get(this.getRandom().nextInt(roles.size()));
            data.putString("behavior", role);
        } else {
            role = data.getString("behavior");
        }

        switch (role) {
            case "hostile_hunter" -> {
                hostileHunterPlayerMob();
            }
            case "village_hunter" -> {
                villagerHunterPlayerMob();
            }
            case "monster_hunter" -> {
                monsterHunterPlayerMob();
            }
            case "player_hunter" -> {
                playerHunterPlayerMob();
            }
            case "animal_hunter" -> {
                animalHunterPlayerMob();
            }
            default -> {
                Random random = new Random();
                if (random.nextFloat() < 0.2) {
                    data.putString("behavior", "hostile_hunter");
                    hostileHunterPlayerMob();
                } else if (random.nextFloat() < 0.4) {
                    data.putString("behavior", "village_hunter");
                    villagerHunterPlayerMob();
                } else if (random.nextFloat() < 0.6) {
                    data.putString("behavior", "monster_hunter");
                    monsterHunterPlayerMob();
                } else if (random.nextFloat() < 0.8) {
                    data.putString("behavior", "player_hunter");
                    playerHunterPlayerMob();
                } else {
                    data.putString("behavior", "animal_hunter");
                    animalHunterPlayerMob();
                }
            }
        }
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        PlayerNpcOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity(), f);
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel levelaccessor) {
            if (this.getPersistentData().getBoolean("die_by_possess")) {
                this.remove(Entity.RemovalReason.KILLED);
            } else if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                PlayerMobDeadEntity corpse = new PlayerMobDeadEntity(AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), levelaccessor);
                corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                corpse.setUsername(this.getUsername());
                corpse.setProfile(this.getProfile());
                corpse.setInvisible(true);
                corpse.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                levelaccessor.addFreshEntity(corpse);
                this.remove(Entity.RemovalReason.KILLED);
                new DelayedTask(3) {
                    @Override
                    public void run() {
                        try {
                            corpse.getServer().getCommands().getDispatcher().execute(
                                    "kill @s",
                                    corpse.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }
                };
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount % 20 == 0) {
            if (!this.isAlive() || this.isRemoved() || this.level() == null) return;
            List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(2));
            for (ItemEntity item : items) {
                if (!item.hasPickUpDelay() && !item.isRemoved()) {
                    final ItemEntity itemEntity = item;
                    if (!this.isAlive() || this.isRemoved() || this.level() == null) return;

                    ItemStack stack = itemEntity.getItem();
                    ItemStack remaining = stack.copy();

                    for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                        if (remaining.isEmpty()) break;
                        ItemStack slotStack = this.inventory.getItem(i);

                        if (slotStack.isEmpty()) {
                            this.inventory.setItem(i, remaining);
                            remaining = ItemStack.EMPTY;
                            break;
                        } else if (ItemStack.isSameItemSameTags(slotStack, remaining) &&
                                slotStack.getCount() < slotStack.getMaxStackSize()) {
                            int transferable = Math.min(
                                    remaining.getCount(),
                                    slotStack.getMaxStackSize() - slotStack.getCount()
                            );
                            slotStack.grow(transferable);
                            remaining.shrink(transferable);
                        }
                    }

                    if (remaining.isEmpty()) {
                        itemEntity.setDeltaMovement(
                                (this.getX() - itemEntity.getX()) * 0.25,
                                (this.getY() + 1.0 - itemEntity.getY()) * 0.25,
                                (this.getZ() - itemEntity.getZ()) * 0.25
                        );
                        itemEntity.setPickUpDelay(0);
                        Entity entity = this;
                        new DelayedTask(5) {
                            @Override
                            public void run() {
                                if (!entity.isAlive() || entity.isRemoved() || entity.level() == null) return;
                                itemEntity.discard();
                                entity.level().playSound(null, entity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.HOSTILE, 0.2F, 1.0F);
                            }
                        };
                    } else {
                        if (this.getHealth() <= 0.0F) return;
                        itemEntity.setItem(remaining);
                    }
                }
            }
        }
        if (!this.level().isClientSide) {
            if (this.gapCooldown > 0) {
                this.gapCooldown = this.gapCooldown - 1;
            }
            if (this.enderPearlCooldown > 0) {
                this.enderPearlCooldown = this.enderPearlCooldown - 1;
            }
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        ServerLevel serverLevel = serverlevelaccessor.getLevel();

        if ((mobspawntype == MobSpawnType.CHUNK_GENERATION || mobspawntype == MobSpawnType.NATURAL) && serverLevel.isDay() && Math.random() <= 0.8D) {
            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            if (serverLevel.getFluidState(spawnPos).isEmpty()) {
                this.moveTo(spawnPos, this.getYRot(), this.getXRot());
            }
        }

        List<String> commands = EquipmentDataLoader.getEquipCommands(0.85f, this);
        for (String cmd : commands) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        cmd,
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException e) {

            }
        }

        try {
            this.getServer().getCommands().getDispatcher().execute(
                    "tellraw @a {\"text\":\"" + this.getDisplayName().getString() + " has joined the game\",\"color\":\"yellow\"}",
                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
        } catch (CommandSyntaxException e) {
        }

        try {
            this.getServer().getCommands().getDispatcher().execute(
                    "data merge entity @s {CanPickUpLoot: 1b}",
                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
        } catch (CommandSyntaxException e) {
        }

        if (Math.random() <= 0.05D) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team add player",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team join player @s",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }

        if (Math.random() <= 0.15D) {
            this.getPersistentData().putDouble("npc_level", 3.0D);
        }

        return returnSpawnGroupData;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
    }

    public void baseTick() {
        super.baseTick();
    }

    public static boolean canSpawn(EntityType<PlayerNpcEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        if (serverLevel.isNight()) {
            // Nerft Player NPC spawn at night
            return false;
        }
        return Monster.checkAnyLightMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 30.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}