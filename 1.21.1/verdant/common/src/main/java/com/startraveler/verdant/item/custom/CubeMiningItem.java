package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.platform.Services;
import com.startraveler.verdant.registry.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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

        if (result && level instanceof ServerLevel serverLevel) {
            int miningRadius = stack.getOrDefault(DataComponentRegistry.MINING_CUBE_RADIUS.get(), 0);
            Tool thisTool = stack.get(DataComponents.TOOL);

            if (thisTool != null && miningRadius > 0) {
                float centerMiningSpeed = thisTool.getMiningSpeed(state);
                float centerDestroySpeed = state.getDestroySpeed(serverLevel, pos);
                boolean centerIsCorrectForDrops = thisTool.isCorrectForDrops(state);

                if ((centerMiningSpeed > thisTool.defaultMiningSpeed() && centerIsCorrectForDrops) || Mth.equal(
                        centerDestroySpeed,
                        0f
                ) || centerDestroySpeed <= 0) {

                    Iterable<BlockPos> positionsToMine = BlockPos.betweenClosed(
                            pos.offset(
                                    -miningRadius,
                                    -miningRadius,
                                    -miningRadius
                            ),
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
                        float otherDestroySpeed = otherState.getDestroySpeed(serverLevel, positionToMine);
                        boolean otherIsCorrectForDrops = thisTool.isCorrectForDrops(otherState);


                        if (((otherMiningSpeed >= centerMiningSpeed || this.allowBreakingHarderBlocks) && otherIsCorrectForDrops) || Mth.equal(
                                otherDestroySpeed,
                                0f
                        ) || otherDestroySpeed <= 0) {
                            if (miningEntity instanceof ServerPlayer player) {
                                // TODO Causes infinite recursion since it calls this method again.
                                Services.BLOCK_EVENT_HELPER.fire(
                                        serverLevel,
                                        player.gameMode(),
                                        player,
                                        positionToMine,
                                        otherState,
                                        (lambdaServerLevel, lambdaGameMode, lambdaPlayer, lambdaPos, lambdaState) -> level.destroyBlock(
                                                lambdaPos,
                                                true,
                                                lambdaPlayer,
                                                512
                                        )
                                );
                            } else {
                                level.destroyBlock(positionToMine, true, miningEntity, 512);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}
