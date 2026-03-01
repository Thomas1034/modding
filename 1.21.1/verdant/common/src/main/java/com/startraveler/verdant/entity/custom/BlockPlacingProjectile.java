package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.platform.Services;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class BlockPlacingProjectile extends ThrowableItemProjectile {

    @SuppressWarnings("unused")
    public BlockPlacingProjectile(Level level, LivingEntity thrower, ItemStack stack) {
        super(EntityTypeRegistry.BLOCK_PLACING_PROJECTILE.get(), thrower, level, stack);
    }

    public BlockPlacingProjectile(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    @SuppressWarnings("unused")
    public BlockPlacingProjectile(Level level) {
        super(EntityTypeRegistry.BLOCK_PLACING_PROJECTILE.get(), level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.COBWEB;
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);

        if (this.level() instanceof ServerLevel serverLevel) {
            Entity entity = this.getOwner();

            Direction hitDirection = switch (hitResult) {
                case BlockHitResult blockHitResult -> blockHitResult.getDirection();

                case EntityHitResult entityHitResult -> Direction.getApproximateNearest(hitResult.getLocation()
                        .subtract(entityHitResult.getEntity().position()));

                default -> Direction.getApproximateNearest(this.getDeltaMovement());
            };
            Constants.LOG.warn("Hit direction is: {}", hitDirection);
            BlockPos blockPos = switch (hitResult) {
                case BlockHitResult blockHitResult -> blockHitResult.getBlockPos().relative(hitDirection);
                case EntityHitResult entityHitResult -> entityHitResult.getEntity().blockPosition();
                default -> BlockPos.containing(hitResult.getLocation());
            };
            ItemStack stackToPlace = this.getItem();
            boolean placementFailed = true;
            if (!(entity instanceof Mob) || Services.ENTITY_GRIEFING_CHECKER.canEntityGrief(serverLevel, entity)) {
                BlockState previousState = serverLevel.getBlockState(blockPos);
                if (previousState.isAir() || previousState.is(BlockTags.REPLACEABLE)) {
                    BlockItemStateProperties stateProperties = stackToPlace.getOrDefault(
                            DataComponents.BLOCK_STATE,
                            BlockItemStateProperties.EMPTY
                    );
                    if (stackToPlace.getItem() instanceof BlockItem blockItem) {
                        BlockState state = stateProperties.apply(blockItem.getBlock().defaultBlockState());
                        this.level().setBlockAndUpdate(blockPos, state);
                        placementFailed = false;
                    }
                }
            }
            if (placementFailed) {
                Block.popResourceFromFace(serverLevel, blockPos, hitDirection.getOpposite(), stackToPlace);
            }

            this.discard();
        }
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity entity) {
        return true;
    }


}
