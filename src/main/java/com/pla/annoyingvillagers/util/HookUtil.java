package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.util.EnchantmentUtil;
import com.mojang.datafixers.util.Pair;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.entity.ArmoredHerobrineEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import com.pla.annoyingvillagers.item.LegacyArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ThrowablePotionItem;
import com.pla.annoyingvillagers.util.PotionUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.IShearable;

import javax.annotation.Nullable;
import java.util.List;

public final class HookUtil {
    public enum HitResult {
        PASS,
        HANDLED
    }

    public record ItemInteractionResult(HitResult hitResult, ItemStack itemStack) {
        public boolean handled() {
            return this.hitResult == HitResult.HANDLED;
        }
    }

    private HookUtil() {
    }

    public static boolean isPickaxe(ItemStack stack) {
        return !stack.isEmpty()
                && stack.is(ItemTags.PICKAXES);
    }

    public static boolean shouldUseShieldFacing(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof ShieldItem;
    }

    public static boolean shouldAlignSharpEdge(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return com.pla.annoyingvillagers.item.LegacySwordItem.isSword(stack)
                || stack.getItem() instanceof AxeItem
                || stack.getItem() instanceof HoeItem
                || stack.getItem() instanceof ShovelItem
                || stack.is(ItemTags.SWORDS)
                || stack.is(ItemTags.AXES)
                || stack.is(ItemTags.HOES)
                || stack.is(ItemTags.SHOVELS)
                || stack.is(ItemTags.PICKAXES);
    }

    public static boolean shouldRenderWithoutProjectileSpin(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof BlockItem;
    }

    public static HitResult handleEntityHit(Level level, ItemStack boundStack, Entity projectile, @Nullable LivingEntity owner, LivingEntity target) {
        return handleEntityHitWithResult(level, boundStack, projectile, owner, target).hitResult();
    }

    public static ItemInteractionResult handleEntityHitWithResult(Level level, ItemStack boundStack, Entity projectile, @Nullable LivingEntity owner, LivingEntity target) {
        if (boundStack.isEmpty() || !target.isAlive() || target.isSpectator()) {
            return pass(boundStack);
        }

        if (owner != null && target == owner) {
            return handled(boundStack);
        }

        if (owner != null && target.isAlliedTo(owner) && !canUseBoundItemOnAlly(boundStack, target)) {
            return handled(boundStack);
        }

        if (boundStack.getItem() instanceof SpawnEggItem) {
            return result(spawnFromSpawnEgg(level, boundStack, owner, target.blockPosition(), false, false), boundStack);
        }

        if (boundStack.getItem() instanceof EggItem) {
            return result(hatchChickenEgg(level, boundStack, hitPosition(target)), boundStack);
        }

        if (isShears(boundStack)) {
            HitResult shearResult = shearEntity(level, boundStack, owner, target);
            if (shearResult == HitResult.HANDLED) {
                return handled(boundStack);
            }
        }

        if (boundStack.is(Items.BUCKET)) {
            ItemInteractionResult bucketResult = fillBucketFromEntity(level, boundStack, target);
            if (bucketResult.handled()) {
                return bucketResult;
            }
        }

        if (boundStack.is(Items.WATER_BUCKET) && target.isOnFire() && !EndFireUtil.isEndFireBurning(target)) {
            target.clearFire();
            level.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.8F, 1.0F);
            return handled(new ItemStack(Items.BUCKET));
        }

        if (boundStack.is(Items.SNOWBALL)) {
            return result(hitWithSnowball(level, boundStack, target), boundStack);
        }

        if (boundStack.getItem() instanceof ShieldItem) {
            return result(hitWithShield(level, boundStack, projectile, owner, target), boundStack);
        }

        if (isWeaponLike(boundStack)) {
            return result(hitWithWeapon(level, boundStack, projectile, owner, target), boundStack);
        }

        if (boundStack.getItem() instanceof LegacyArmorItem armorItem) {
            return result(equipArmor(boundStack, target, armorItem), boundStack);
        }

        if (isPotion(boundStack)) {
            return result(applyPotion(level, boundStack, projectile, owner, target), boundStack);
        }

        FoodProperties food = boundStack.get(net.minecraft.core.component.DataComponents.FOOD);
        if (food != null) {
            return result(feedTarget(level, boundStack, target, food), boundStack);
        }

        if (boundStack.getItem() instanceof FireChargeItem) {
            target.igniteForSeconds(8);
            boundStack.shrink(1);
            level.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            return handled(boundStack);
        }

        if (boundStack.getItem() instanceof FlintAndSteelItem) {
            target.igniteForSeconds(8);
            damageTool(boundStack, owner);
            level.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            return handled(boundStack);
        }

        return pass(boundStack);
    }

    public static HitResult handleBlockHit(Level level, ItemStack boundStack, Entity projectile, @Nullable LivingEntity owner, BlockHitResult hitResult) {
        return handleBlockHitWithResult(level, boundStack, projectile, owner, hitResult).hitResult();
    }

    public static ItemInteractionResult handleBlockHitWithResult(Level level, ItemStack boundStack, Entity projectile, @Nullable LivingEntity owner, BlockHitResult hitResult) {
        if (boundStack.isEmpty()) {
            return pass(boundStack);
        }

        if (boundStack.getItem() instanceof SpawnEggItem) {
            BlockPos blockPos = getSpawnEggBlockPos(level, hitResult);
            boolean offsetForFace = hitResult.getDirection() == Direction.UP
                    && !blockPos.equals(hitResult.getBlockPos());
            return result(spawnFromSpawnEgg(level, boundStack, owner, blockPos, true, offsetForFace), boundStack);
        }

        if (boundStack.getItem() instanceof EggItem) {
            return result(hatchChickenEgg(level, boundStack, hitResult.getLocation()), boundStack);
        }

        if (isShears(boundStack)) {
            HitResult shearResult = shearBlock(level, boundStack, owner, hitResult);
            if (shearResult == HitResult.HANDLED) {
                return handled(boundStack);
            }
        }

        if (boundStack.getItem() instanceof FireChargeItem) {
            ItemInteractionResult fireChargeResult = useFireCharge(level, boundStack, owner, hitResult);
            if (fireChargeResult.handled()) {
                return fireChargeResult;
            }
        }

        if (boundStack.getItem() instanceof BucketItem bucketItem) {
            ItemInteractionResult bucketResult = useBucket(level, boundStack, owner, bucketItem, hitResult);
            if (bucketResult.handled()) {
                return bucketResult;
            }
        }

        if (boundStack.getItem() instanceof FlintAndSteelItem) {
            if (igniteTntBlock(level, hitResult.getBlockPos(), owner) || placeFire(level, hitResult)) {
                damageTool(boundStack, owner);
                level.playSound(null, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z,
                        SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                return handled(boundStack);
            }

            return pass(boundStack);
        }

        if (boundStack.getItem() instanceof BoneMealItem) {
            return result(applyBoneMeal(level, boundStack, hitResult), boundStack);
        }

        if (boundStack.getItem() instanceof BlockItem blockItem) {
            return result(placeBoundBlock(level, boundStack, owner, blockItem, hitResult), boundStack);
        }

        return pass(boundStack);
    }

    private static ItemInteractionResult handled(ItemStack itemStack) {
        return new ItemInteractionResult(HitResult.HANDLED, itemStack);
    }

    private static ItemInteractionResult pass(ItemStack itemStack) {
        return new ItemInteractionResult(HitResult.PASS, itemStack);
    }

    private static ItemInteractionResult result(HitResult hitResult, ItemStack itemStack) {
        return new ItemInteractionResult(hitResult, itemStack);
    }

    private static boolean isShears(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof ShearsItem
               
               );
    }

    private static boolean canUseBoundItemOnAlly(ItemStack stack, LivingEntity target) {
        return stack.getItem() instanceof LegacyArmorItem
                || stack.is(Items.WATER_BUCKET)
                || stack.is(Items.SNOWBALL)
                || isPotion(stack)
                || stack.get(net.minecraft.core.component.DataComponents.FOOD) != null;
    }

    private static HitResult hitWithSnowball(Level level, ItemStack boundStack, LivingEntity target) {
        target.clearFire();
        target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1));
        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.8F, 0.75F);
        boundStack.shrink(1);
        return HitResult.HANDLED;
    }

    private static HitResult shearEntity(Level level, ItemStack boundStack, @Nullable LivingEntity owner, LivingEntity target) {
        if (!(target instanceof IShearable shearable)) {
            return HitResult.PASS;
        }

        BlockPos pos = target.blockPosition();
        Player player = owner instanceof Player ownerPlayer ? ownerPlayer : null;
        if (!shearable.isShearable(player, boundStack, level, pos)) {
            return HitResult.PASS;
        }

        List<ItemStack> drops = shearable.onSheared(player, boundStack, level, pos);
        RandomSource random = target.getRandom();

        for (ItemStack drop : drops) {
            ItemEntity itemEntity = com.pla.annoyingvillagers.util.LegacyEntityOps.spawnAtLocation(target, drop, 1.0F);
            if (itemEntity != null) {
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(
                        (random.nextFloat() - random.nextFloat()) * 0.1F,
                        random.nextFloat() * 0.05F,
                        (random.nextFloat() - random.nextFloat()) * 0.1F
                ));
            }
        }

        damageTool(boundStack, owner);
        return HitResult.HANDLED;
    }

    private static HitResult shearBlock(Level level, ItemStack boundStack, @Nullable LivingEntity owner, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(BlockTags.LEAVES)) {
            return HitResult.PASS;
        }

        Block.dropResources(state, level, pos, level.getBlockEntity(pos), owner, boundStack);
        level.levelEvent(2001, pos, Block.getId(state));
        level.removeBlock(pos, false);
        level.gameEvent(owner, GameEvent.BLOCK_DESTROY, pos);
        damageTool(boundStack, owner);
        return HitResult.HANDLED;
    }

    private static ItemInteractionResult useFireCharge(Level level, ItemStack boundStack, @Nullable LivingEntity owner, BlockHitResult hitResult) {
        if (!igniteTntBlock(level, hitResult.getBlockPos(), owner) && !placeFireChargeFire(level, owner, hitResult)) {
            return pass(boundStack);
        }

        boundStack.shrink(1);
        RandomSource random = level.getRandom();
        level.playSound(null, hitResult.getBlockPos(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS,
                1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        return handled(boundStack);
    }

    private static boolean placeFireChargeFire(Level level, @Nullable LivingEntity owner, BlockHitResult hitResult) {
        BlockPos firePos = hitResult.getBlockPos().relative(hitResult.getDirection());
        Direction direction = owner != null ? owner.getDirection() : Direction.NORTH;
        if (!BaseFireBlock.canBePlacedAt(level, firePos, direction)) {
            return false;
        }

        level.setBlockAndUpdate(firePos, BaseFireBlock.getState(level, firePos));
        level.gameEvent(owner, GameEvent.BLOCK_PLACE, firePos);
        return true;
    }

    private static ItemInteractionResult useBucket(Level level, ItemStack boundStack, @Nullable LivingEntity owner, BucketItem bucketItem, BlockHitResult hitResult) {
        if (boundStack.is(Items.BUCKET)) {
            return fillBucketFromBlock(level, boundStack, owner, hitResult);
        }

        if (bucketItem.content == Fluids.EMPTY) {
            return pass(boundStack);
        }

        return emptyBucket(level, boundStack, owner, bucketItem, hitResult);
    }

    private static ItemInteractionResult emptyBucket(Level level, ItemStack boundStack, @Nullable LivingEntity owner, BucketItem bucketItem, BlockHitResult hitResult) {
        BlockPos hitPos = hitResult.getBlockPos();
        BlockState hitState = level.getBlockState(hitPos);
        Fluid fluid = bucketItem.content;
        BlockPos placePos = canPlaceBucketFluidInBlock(level, hitPos, hitState, fluid)
                ? hitPos
                : hitPos.relative(hitResult.getDirection());

        Player player = owner instanceof Player ownerPlayer ? ownerPlayer : null;
        if (!bucketItem.emptyContents(player, level, placePos, hitResult, boundStack)) {
            return pass(boundStack);
        }

        bucketItem.checkExtraContent(player, level, boundStack, placePos);
        return handled(new ItemStack(Items.BUCKET));
    }

    private static boolean canPlaceBucketFluidInBlock(Level level, BlockPos pos, BlockState state, Fluid fluid) {
        return state.getBlock() instanceof LiquidBlockContainer liquidBlockContainer
                && liquidBlockContainer.canPlaceLiquid(ownerAsPlayer(null), level, pos, state, fluid);
    }

    private static ItemInteractionResult fillBucketFromBlock(Level level, ItemStack boundStack, @Nullable LivingEntity owner, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        boolean pickedUpWater = state.getFluidState().is(FluidTags.WATER);

        if (!(state.getBlock() instanceof BucketPickup bucketPickup)) {
            return pass(boundStack);
        }

        Player player = owner instanceof Player ownerPlayer ? ownerPlayer : null;
        ItemStack filledBucket = bucketPickup.pickupBlock(player, level, pos, state);
        if (filledBucket.isEmpty()) {
            return pass(boundStack);
        }

        bucketPickup.getPickupSound().ifPresent(soundEvent ->
                level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F));
        level.gameEvent(owner, GameEvent.FLUID_PICKUP, pos);

        if (pickedUpWater) {
            ItemInteractionResult bucketableResult = fillBucketFromNearbyBucketable(level, boundStack, pos);
            if (bucketableResult.handled()) {
                return bucketableResult;
            }
        }

        return handled(filledBucket);
    }

    private static ItemInteractionResult fillBucketFromNearbyBucketable(Level level, ItemStack boundStack, BlockPos pos) {
        List<LivingEntity> bucketableTargets = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(pos).inflate(0.75D),
                entity -> entity.isAlive() && entity instanceof Bucketable
        );

        if (bucketableTargets.isEmpty()) {
            return pass(boundStack);
        }

        return bucketEntity(level, bucketableTargets.get(0));
    }

    private static ItemInteractionResult fillBucketFromEntity(Level level, ItemStack boundStack, LivingEntity target) {
        if (!(target instanceof Bucketable bucketable)) {
            return pass(boundStack);
        }

        if (!pickupNearbyWaterSource(level, target)) {
            return pass(boundStack);
        }

        return bucketEntity(level, target);
    }

    private static boolean pickupNearbyWaterSource(Level level, LivingEntity target) {
        BlockPos center = target.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-1, -1, -1), center.offset(1, 1, 1))) {
            if (level.getFluidState(pos).is(FluidTags.WATER) && level.getFluidState(pos).isSource()
                    && pickupFluidBlock(level, target, pos.immutable())) {
                return true;
            }
        }

        return false;
    }

    private static boolean pickupFluidBlock(Level level, @Nullable LivingEntity owner, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof BucketPickup bucketPickup)) {
            return false;
        }

        Player player = owner instanceof Player ownerPlayer ? ownerPlayer : null;
        ItemStack pickedBucket = bucketPickup.pickupBlock(player, level, pos, state);
        if (pickedBucket.isEmpty()) {
            return false;
        }

        bucketPickup.getPickupSound().ifPresent(soundEvent ->
                level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F));
        level.gameEvent(owner, GameEvent.FLUID_PICKUP, pos);
        return true;
    }

    private static ItemInteractionResult bucketEntity(Level level, LivingEntity target) {
        if (!(target instanceof Bucketable bucketable)) {
            return pass(ItemStack.EMPTY);
        }

        ItemStack filledBucket = bucketable.getBucketItemStack();
        bucketable.saveToBucketTag(filledBucket);
        target.playSound(bucketable.getPickupSound(), 1.0F, 1.0F);
        if (!level.isClientSide()) {
            target.discard();
        }
        return handled(filledBucket);
    }

    public static float calculateWeaponDamage(ItemStack stack, LivingEntity target) {
        final double[] damage = {1.0D};
        stack.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
            if (!attribute.equals(Attributes.ATTACK_DAMAGE)) return;
            if (modifier.operation() == AttributeModifier.Operation.ADD_VALUE) {
                damage[0] += modifier.amount();
            } else if (modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE) {
                damage[0] += damage[0] * modifier.amount();
            } else if (modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                damage[0] *= 1.0D + modifier.amount();
            }
        });

        damage[0] += EnchantmentUtil.getDamageBonus(stack, target);
        return (float) Math.max(1.0D, damage[0]);
    }

    private static boolean isWeaponLike(ItemStack stack) {
        return com.pla.annoyingvillagers.item.LegacySwordItem.isSword(stack)
                || stack.getItem() instanceof AxeItem
                || stack.getItem() instanceof HoeItem
                || stack.getItem() instanceof ShovelItem
                || shouldAlignSharpEdge(stack);
    }

    private static boolean isPotion(ItemStack stack) {
        return !PotionUtil.getMobEffects(stack).isEmpty()
                || stack.getItem() instanceof ThrowablePotionItem;
    }

    private static BlockPos getSpawnEggBlockPos(Level level, BlockHitResult hitResult) {
        BlockPos hitPos = hitResult.getBlockPos();
        BlockState hitState = level.getBlockState(hitPos);
        return hitState.getCollisionShape(level, hitPos).isEmpty()
                ? hitPos
                : hitPos.relative(hitResult.getDirection());
    }

    private static HitResult spawnFromSpawnEgg(
            Level level,
            ItemStack boundStack,
            @Nullable LivingEntity owner,
            BlockPos spawnPos,
            boolean shouldOffsetY,
            boolean shouldOffsetYMore
    ) {
        if (!(level instanceof ServerLevel serverLevel)
                || !(boundStack.getItem() instanceof SpawnEggItem spawnEggItem)) {
            return HitResult.HANDLED;
        }

        EntityType<?> entityType = spawnEggItem.getType(boundStack);
        Player player = owner instanceof Player ownerPlayer ? ownerPlayer : null;
        Entity spawned = entityType.spawn(
                serverLevel,
                boundStack,
                player,
                spawnPos,
                EntitySpawnReason.SPAWN_ITEM_USE,
                shouldOffsetY,
                shouldOffsetYMore
        );

        if (spawned != null) {
            boundStack.shrink(1);
        }

        return HitResult.HANDLED;
    }

    private static HitResult hatchChickenEgg(Level level, ItemStack boundStack, Vec3 hitPos) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return HitResult.HANDLED;
        }

        serverLevel.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F,
                0.4F / (serverLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        if (serverLevel.getRandom().nextInt(8) == 0) {
            int count = serverLevel.getRandom().nextInt(32) == 0 ? 4 : 1;
            for (int i = 0; i < count; i++) {
                Chicken chicken = EntityType.CHICKEN.create(serverLevel, net.minecraft.world.entity.EntitySpawnReason.TRIGGERED);
                if (chicken != null) {
                    chicken.setAge(-24000);
                    chicken.snapTo(hitPos.x, hitPos.y, hitPos.z, 0.0F, 0.0F);
                    serverLevel.addFreshEntity(chicken);
                }
            }
        }

        boundStack.shrink(1);
        return HitResult.HANDLED;
    }

    private static Vec3 hitPosition(LivingEntity target) {
        return new Vec3(target.getX(), target.getY() + target.getBbHeight() * 0.5D, target.getZ());
    }

    private static HitResult hitWithShield(Level level, ItemStack boundStack, Entity projectile, @Nullable LivingEntity owner, LivingEntity target) {
        DamageSource source = level.damageSources().thrown(projectile, owner);
        if (!target.hurtOrSimulate(source, 15.0F)) {
            return HitResult.PASS;
        }

        damageTool(boundStack, owner);
        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                AnnoyingVillagersModSounds.HEAVY_HIT.get(), SoundSource.PLAYERS, 0.8F, 0.95F);
        return HitResult.HANDLED;
    }

    private static HitResult hitWithWeapon(Level level, ItemStack boundStack, Entity projectile, @Nullable LivingEntity owner, LivingEntity target) {
        DamageSource source = level.damageSources().thrown(projectile, owner);
        if (!target.hurtOrSimulate(source, calculateWeaponDamage(boundStack, target))) {
            return HitResult.PASS;
        }

        if (owner != null) {
            applyWeaponEnchantEffects(level, boundStack, owner, target, source);
        }

        damageTool(boundStack, owner);
        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                AnnoyingVillagersModSounds.CLASH.get(), SoundSource.PLAYERS, 0.8F, 1.0F);
        return HitResult.HANDLED;
    }

    private static void applyWeaponEnchantEffects(Level level, ItemStack stack, LivingEntity owner, LivingEntity target, DamageSource source) {
        int fireAspect = EnchantmentUtil.getLevel(Enchantments.FIRE_ASPECT, stack);
        if (fireAspect > 0) {
            target.igniteForSeconds(fireAspect * 4);
        }

        if (level instanceof ServerLevel serverLevel) {
            EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, target, source, stack);
        }
    }

    private static HitResult equipArmor(ItemStack boundStack, LivingEntity target, LegacyArmorItem armorItem) {
        if (isArmorTargetBlacklisted(target)) {
            return HitResult.HANDLED;
        }

        EquipmentSlot slot = armorItem.getEquipmentSlot();
        if (!target.getItemBySlot(slot).isEmpty()) {
            return HitResult.HANDLED;
        }

        ItemStack equipped = boundStack.copy();
        equipped.setCount(1);
        target.setItemSlot(slot, equipped);
        if (target instanceof Mob mob) {
            mob.setDropChance(slot, 1.0F);
        }
        boundStack.shrink(1);
        return HitResult.HANDLED;
    }

    private static boolean isArmorTargetBlacklisted(LivingEntity target) {
        return target instanceof NullWeapon
                || target instanceof BlueDemonEntity
                || target instanceof ArmoredHerobrineEntity;
    }

    private static HitResult applyPotion(Level level, ItemStack boundStack, Entity projectile, @Nullable LivingEntity owner, LivingEntity target) {
        for (MobEffectInstance effect : PotionUtil.getMobEffects(boundStack)) {
            if (effect.getEffect().value().isInstantenous() && level instanceof ServerLevel serverLevel) {
                effect.getEffect().value().applyInstantenousEffect(serverLevel, projectile, owner, target, effect.getAmplifier(), 1.0D);
            } else {
                target.addEffect(new MobEffectInstance(effect));
            }
        }

        if (isFlashPotion(boundStack) && level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ColorParticleOption.create(ParticleTypes.FLASH, 0xFFFFFFFF), target.getX(), target.getEyeY(), target.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }

        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.SPLASH_POTION_BREAK, SoundSource.PLAYERS, 0.8F, 1.0F);
        boundStack.shrink(1);
        return HitResult.HANDLED;
    }

    private static boolean isFlashPotion(ItemStack stack) {
        String descriptionId = stack.getItem().getDescriptionId().toLowerCase();
        return descriptionId.contains("flash");
    }

    private static HitResult feedTarget(Level level, ItemStack boundStack, LivingEntity target, FoodProperties food) {
        if (target.isInvertedHealAndHarm()) {
            float damage = Math.max(1.0F, food.nutrition());
            target.hurtOrSimulate(level.damageSources().magic(), damage);
        } else {
            target.heal(Math.max(1.0F, food.nutrition()));
            net.minecraft.world.item.component.Consumable consumable = boundStack.get(net.minecraft.core.component.DataComponents.CONSUMABLE);
            if (consumable != null) consumable.onConsumeEffects().forEach(effect -> effect.apply(level, boundStack, target));
        }

        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 1.0F);
        boundStack.shrink(1);
        return HitResult.HANDLED;
    }

    private static HitResult placeBoundBlock(Level level, ItemStack boundStack, @Nullable LivingEntity owner, BlockItem blockItem, BlockHitResult hitResult) {
        BlockPos hitPos = hitResult.getBlockPos();
        BlockState hitState = level.getBlockState(hitPos);
        BlockPos placePos = hitState.canBeReplaced() ? hitPos : hitPos.relative(hitResult.getDirection());
        BlockState existingState = level.getBlockState(placePos);

        if (!existingState.canBeReplaced() || !level.getFluidState(placePos).isEmpty()) {
            return HitResult.PASS;
        }

        BlockState placeState = blockItem.getBlock().defaultBlockState();
        if (!placeState.canSurvive(level, placePos)) {
            return HitResult.PASS;
        }

        if (!level.setBlock(placePos, placeState, Block.UPDATE_ALL)) {
            return HitResult.PASS;
        }

        blockItem.getBlock().setPlacedBy(level, placePos, placeState, owner, boundStack);
        level.playSound(null, placePos, placeState.getSoundType(level, placePos, owner).getPlaceSound(),
                SoundSource.BLOCKS, 1.0F, 1.0F);
        boundStack.shrink(1);
        return HitResult.HANDLED;
    }

    private static HitResult applyBoneMeal(Level level, ItemStack boundStack, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof BonemealableBlock bonemealableBlock)) {
            return HitResult.PASS;
        }

        if (level instanceof ServerLevel serverLevel
                && bonemealableBlock.isValidBonemealTarget(level, pos, state)
                && bonemealableBlock.isBonemealSuccess(level, level.getRandom(), pos, state)) {
            bonemealableBlock.performBonemeal(serverLevel, level.getRandom(), pos, state);
            level.levelEvent(1505, pos, 0);
            boundStack.shrink(1);
            return HitResult.HANDLED;
        }

        return HitResult.PASS;
    }

    private static boolean igniteTntBlock(Level level, BlockPos pos, @Nullable LivingEntity owner) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(Blocks.TNT)) {
            return false;
        }

        if (!level.isClientSide()) {
            PrimedTnt primedTnt = new PrimedTnt(level, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, owner);
            level.addFreshEntity(primedTnt);
            level.removeBlock(pos, false);
            level.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return true;
    }

    private static boolean placeFire(Level level, BlockHitResult hitResult) {
        BlockPos firePos = hitResult.getBlockPos().relative(hitResult.getDirection());
        BlockState fireState = Blocks.FIRE.defaultBlockState();
        if (!level.getBlockState(firePos).canBeReplaced() || !fireState.canSurvive(level, firePos)) {
            return false;
        }

        level.setBlock(firePos, fireState, Block.UPDATE_ALL);
        return true;
    }

    private static void damageTool(ItemStack stack, @Nullable LivingEntity owner) {
        if (!stack.isDamageableItem()) {
            return;
        }

        if (owner != null && owner.level() instanceof ServerLevel serverLevel) {
            stack.hurtAndBreak(1, serverLevel, owner, item -> {});
        } else if (stack.getDamageValue() + 1 >= stack.getMaxDamage()) {
            stack.shrink(1);
            stack.setDamageValue(0);
        } else {
            stack.setDamageValue(stack.getDamageValue() + 1);
        }
    }

    private static Player ownerAsPlayer(@Nullable LivingEntity owner) {
        return owner instanceof Player player ? player : null;
    }
}
