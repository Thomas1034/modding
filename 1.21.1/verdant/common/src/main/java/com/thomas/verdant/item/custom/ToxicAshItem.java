package com.thomas.verdant.item.custom;

import com.thomas.verdant.block.Converter;
import com.thomas.verdant.registry.BlockTransformerRegistry;
import com.thomas.verdant.registry.DamageSourceRegistry;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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

    public static void addDeathParticles(ServerLevel level, BlockPos pos, int data) {
        BlockState blockstate = level.getBlockState(pos);
        Vec3 center = pos.getCenter();
        Vec3 box = new Vec3(0.75, 0.75, 0.75);
        if (blockstate.isCollisionShapeFullBlock(level, pos)) {
            center = center.add(0, 0.25, 0);
            box = box.multiply(1, 0.25, 1);
        }
        level.sendParticles(ParticleTypes.SMOKE, center.x, center.y, center.z, 5, box.x, box.y, box.z, 0);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);

        if (level instanceof ServerLevel serverLevel) {
            boolean convertedClicked = state.is(VerdantTags.Blocks.ALLOWS_ASH_SPREAD) || this.convert(
                    state,
                    serverLevel,
                    pos
            );
            if (convertedClicked) {

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
                    return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(this.getEmptySuccessItem(
                            stack,
                            player
                    ));
                }
            }

        }

        return InteractionResult.FAIL;
    }

    protected void convertInRadius(final ServerLevel level, final BlockPos original, final int radius) {
        final int radiusSqr = radius * radius;
        int xLowerBound = original.getX();
        int xUpperBound = original.getX();
        int yLowerBound = original.getY();
        int yUpperBound = original.getY();
        int zLowerBound = original.getZ();
        int zUpperBound = original.getZ();
        // Using a stack-based method.
        Deque<BlockPos> stack = new ArrayDeque<>(radius * radius * radius / 6);
        Deque<Integer> dripDistance = new ArrayDeque<>(radius * radius * radius / 6);
        stack.push(original);
        dripDistance.push(-1);
        Set<BlockPos> visited = new HashSet<>(radius * radius * radius / 6);

        while (!stack.isEmpty()) {
            BlockPos current = stack.pop();
            int currentDrip = dripDistance.pop();
            if (current.distSqr(original) > radiusSqr) {
                continue;
            }

            boolean shouldSpread = false;
            BlockState state = level.getBlockState(current);
            if (!state.is(VerdantTags.Blocks.BLOCKS_ASH_SPREAD) && (this.convert(state, level, current) || state.is(
                    VerdantTags.Blocks.ALLOWS_ASH_SPREAD))) {
                shouldSpread = true;
            } else if (currentDrip < MAX_PERMEABILITY_RANGE) {
                shouldSpread = true;
                currentDrip++;
            }

            if (shouldSpread) {
                int xCurrent = current.getX();
                int yCurrent = current.getY();
                int zCurrent = current.getZ();

                if (xCurrent < xLowerBound) {
                    xLowerBound = xCurrent;
                }
                if (xCurrent > xLowerBound) {
                    xLowerBound = xCurrent;
                }
                if (yCurrent < yLowerBound) {
                    yLowerBound = yCurrent;
                }
                if (yCurrent > yLowerBound) {
                    yLowerBound = yCurrent;
                }
                if (zCurrent < zLowerBound) {
                    zLowerBound = zCurrent;
                }
                if (zCurrent > zLowerBound) {
                    zLowerBound = zCurrent;
                }
                stack.push(current.above());
                stack.push(current.below());
                stack.push(current.north());
                stack.push(current.south());
                stack.push(current.east());
                stack.push(current.west());
                dripDistance.push(currentDrip);
                dripDistance.push(currentDrip);
                dripDistance.push(currentDrip);
                dripDistance.push(currentDrip);
                dripDistance.push(currentDrip);
                dripDistance.push(currentDrip);
                visited.add(current);
            }
        }

        xLowerBound--;
        xUpperBound++;
        yLowerBound--;
        yUpperBound++;
        zLowerBound--;
        zUpperBound++;
        AABB bounds = AABB.encapsulatingFullBlocks(
                new BlockPos(xLowerBound, yLowerBound, zLowerBound),
                new BlockPos(xUpperBound, yUpperBound, zUpperBound)
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
