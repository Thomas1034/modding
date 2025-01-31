package com.thomas.verdant.item.custom;

import com.thomas.verdant.block.Converter;
import com.thomas.verdant.registry.BlockTransformerRegistry;
import com.thomas.verdant.registry.DamageSourceRegistry;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.*;
import java.util.function.Supplier;

public class ToxicAshItem extends Item implements Converter {
    public static final int MAX_PERMEABILITY_RANGE = 4;
    protected final Supplier<ItemStack> residual;
    protected final int range;
    protected final int randomRange;

    public ToxicAshItem(Properties properties, Supplier<ItemStack> residual, int range, int randomRange) {
        super(properties);
        this.residual = residual;
        this.range = range;
        this.randomRange = randomRange;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (level instanceof ServerLevel serverLevel && (state.is(VerdantTags.Blocks.ALLOWS_ASH_SPREAD) || this.canConvert(state,
                serverLevel
        ))) {

            this.convertInRadius(serverLevel, pos, this.getRadius(serverLevel.random));

            ItemStack stack = context.getItemInHand();
            Player player = context.getPlayer();
            if (player != null) {
                player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
            }
            if (stack.getMaxStackSize() != 1) {
                stack.shrink(1);
                return InteractionResult.SUCCESS_SERVER;
            } else {
                return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(this.getEmptySuccessItem(stack, player));
            }
        }

        return InteractionResult.FAIL;
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        } else {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!level.mayInteract(player, blockPos)) {
                    return InteractionResult.PASS;
                }

                if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                    if (level instanceof ServerLevel serverLevel) {

                        this.convertInRadius(serverLevel, blockPos, this.getRadius(serverLevel.random));

                        if (player != null) {
                            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                        }
                        if (itemStack.getMaxStackSize() != 1) {
                            itemStack.shrink(1);
                            return InteractionResult.SUCCESS_SERVER;
                        } else {
                            return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(this.getEmptySuccessItem(itemStack,
                                    player
                            ));
                        }
                    }
                }
            }

            return InteractionResult.PASS;
        }
    }

    protected void convertInRadius(final ServerLevel level, final BlockPos original, final int radius) {
        final int radiusSqr = radius * radius;
        Deque<BlockPos> stack = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();//new ConcurrentSkipListSet<>(Comparator.comparingInt(Vec3i::getY));
        stack.push(original);

        while (!stack.isEmpty()) {
            BlockPos current = stack.pop();

            // Skip if already visited or out of radius
            if (!visited.add(current) || current.distSqr(original) > radiusSqr) {
                continue;
            }

            BlockState state = level.getBlockState(current);

            // Attempt to convert the block
            if (state.is(VerdantTags.Blocks.BLOCKS_ASH_SPREAD) || this.canConvert(
                    state,
                    level
            ) || state.is(VerdantTags.Blocks.ALLOWS_ASH_SPREAD)) {
                // Add neighboring positions to stack
                stack.push(current.above());
                stack.push(current.below());
                stack.push(current.north());
                stack.push(current.south());
                stack.push(current.east());
                stack.push(current.west());

                // Spawn particles for visual feedback
                // addDeathParticles(level, current, 0);
            }
        }

        for (BlockPos pos : visited) {
            this.convert(level, pos);
        }

        // Handle entity damage within the affected radius
        AABB bounds = new AABB(
                original.offset(-radius - 1, -radius - 1, -radius - 1).getCenter(),
                original.offset(radius + 1, radius + 1, radius + 1).getCenter()
        );
        List<LivingEntity> affectedEntities = level.getEntitiesOfClass(LivingEntity.class, bounds);
        for (LivingEntity entity : affectedEntities) {
            if (visited.contains(entity.blockPosition())) {
                this.damageEntity(level, entity);
            }
        }
    }

    protected void damageEntity(ServerLevel level, LivingEntity entity) {
        if (entity.getType().is(VerdantTags.EntityTypes.TOXIC_ASH_DAMAGES)) {
            Holder<DamageType> type = DamageSourceRegistry.get(level.registryAccess(), DamageSourceRegistry.TOXIC_ASH);
            DamageSource source = new DamageSource(type, (Entity) null);
            entity.hurtServer(level, source, 20);
        }
    }

    protected int getRadius(RandomSource random) {
        return this.range + random.nextInt(this.randomRange);
    }


    public ItemStack getEmptySuccessItem(ItemStack stack, Player player) {
        return (player != null && !player.hasInfiniteMaterials()) ? this.residual == null ? ItemStack.EMPTY : this.residual.get() : stack;
    }

    @Override
    public ResourceLocation getTransformer() {
        return BlockTransformerRegistry.TOXIC_ASH;
    }
}
