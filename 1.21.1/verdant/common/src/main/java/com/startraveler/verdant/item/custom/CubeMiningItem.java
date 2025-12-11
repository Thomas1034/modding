package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.registry.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CubeMiningItem extends Item {
    protected final boolean allowBreakingHarderBlocks;

    public CubeMiningItem(Properties properties) {
        this(properties, false);
    }

    public CubeMiningItem(Properties properties, boolean allowBreakingHarderBlocks) {
        super(properties);
        this.allowBreakingHarderBlocks = allowBreakingHarderBlocks;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity miningEntity) {

        boolean result = super.mineBlock(stack, level, state, pos, miningEntity);

        if (result) {
            int miningRadius = stack.getOrDefault(DataComponentRegistry.MINING_CUBE_RADIUS.get(), 0);
            Tool thisTool = stack.get(DataComponents.TOOL);

            if (thisTool != null) {
                float centerMiningSpeed = thisTool.getMiningSpeed(state);
                float centerDestroySpeed = state.getDestroySpeed(level, pos);
                boolean centerIsCorrectForDrops = thisTool.isCorrectForDrops(state);

                if ((centerMiningSpeed > thisTool.defaultMiningSpeed() && centerIsCorrectForDrops) || Mth.equal(
                        centerDestroySpeed,
                        0f
                ) || centerDestroySpeed <= 0) {

                    Iterable<BlockPos> positionsToMine = BlockPos.betweenClosed(
                            pos.offset(-miningRadius, -miningRadius, -miningRadius),
                            pos.offset(miningRadius, miningRadius, miningRadius)
                    );

                    for (BlockPos positionToMine : positionsToMine) {

                        if (positionToMine.equals(pos)) {
                            continue;
                        }

                        BlockState otherState = level.getBlockState(positionToMine);

                        if (otherState.isAir()) {
                            continue;
                        }

                        float otherMiningSpeed = thisTool.getMiningSpeed(otherState);
                        float otherDestroySpeed = otherState.getDestroySpeed(level, positionToMine);
                        boolean otherIsCorrectForDrops = thisTool.isCorrectForDrops(otherState);


                        if (((otherMiningSpeed >= centerMiningSpeed || this.allowBreakingHarderBlocks) && otherIsCorrectForDrops) || Mth.equal(
                                otherDestroySpeed,
                                0f
                        ) || otherDestroySpeed <= 0) {
                            level.destroyBlock(positionToMine, true);
                        }
                    }
                }
            }
        }

        return result;
    }
}
