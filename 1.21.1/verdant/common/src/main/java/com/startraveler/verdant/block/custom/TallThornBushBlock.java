package com.startraveler.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;

public class TallThornBushBlock extends TallFlowerBlock {
    private final float damage;

    public TallThornBushBlock(Properties properties, float damage) {
        super(properties);
        this.damage = damage;
    }

    @Override
    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity, @NotNull InsideBlockEffectApplier applier, boolean intersects) {
        super.entityInside(state, level, pos, entity, applier, intersects);
        ThornBushBlock.entityInsideThorns(state, level, pos, entity, this.damage);
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return this.damage == 0;
    }
}
