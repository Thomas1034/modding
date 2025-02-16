/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.stream.Stream;

public class FertilizerRotatedPillarBlock extends RotatedPillarBlock {

    public FertilizerRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    public static boolean growCrop(Level level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        Block var5 = blockstate.getBlock();
        if (var5 instanceof BonemealableBlock bonemealableBlock) {
            if (bonemealableBlock.isValidBonemealTarget(level, pos, blockstate)) {
                if (level instanceof ServerLevel) {
                    if (bonemealableBlock.isBonemealSuccess(level, level.random, pos, blockstate)) {
                        bonemealableBlock.performBonemeal((ServerLevel) level, level.random, pos, blockstate);
                    }
                }

                return true;
            }
        }

        return false;
    }

    public static boolean growWaterPlant(Level level, BlockPos pos, Direction clickedSide) {
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
            if (level instanceof ServerLevel) {
                RandomSource randomsource = level.getRandom();

                label79:
                for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.offset(
                                randomsource.nextInt(3) - 1,
                                (randomsource.nextInt(3) - 1) * randomsource.nextInt(3) / 2,
                                randomsource.nextInt(3) - 1
                        );
                        if (level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                            continue label79;
                        }
                    }

                    Holder<Biome> holder = level.getBiome(blockpos);
                    if (holder.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                        if (i == 0 && clickedSide != null && clickedSide.getAxis().isHorizontal()) {
                            blockstate = BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.WALL_CORALS, level.random)
                                    .map((p_204100_) -> ((Block) p_204100_.value()).defaultBlockState())
                                    .orElse(blockstate);
                            if (blockstate.hasProperty(BaseCoralWallFanBlock.FACING)) {
                                blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, clickedSide);
                            }
                        } else if (randomsource.nextInt(4) == 0) {
                            blockstate = BuiltInRegistries.BLOCK.getRandomElementOf(
                                            BlockTags.UNDERWATER_BONEMEALS,
                                            level.random
                                    )
                                    .map((p_204095_) -> ((Block) p_204095_.value()).defaultBlockState())
                                    .orElse(blockstate);
                        }
                    }

                    if (blockstate.is(
                            BlockTags.WALL_CORALS,
                            (p_373910_) -> p_373910_.hasProperty(BaseCoralWallFanBlock.FACING)
                    )) {
                        for (int k = 0; !blockstate.canSurvive(level, blockpos) && k < 4; ++k) {
                            blockstate = blockstate.setValue(
                                    BaseCoralWallFanBlock.FACING,
                                    Direction.Plane.HORIZONTAL.getRandomDirection(randomsource)
                            );
                        }
                    }

                    if (blockstate.canSurvive(level, blockpos)) {
                        BlockState blockstate1 = level.getBlockState(blockpos);
                        if (blockstate1.is(Blocks.WATER) && level.getFluidState(blockpos).getAmount() == 8) {
                            level.setBlock(blockpos, blockstate, 3);
                        } else if (blockstate1.is(Blocks.SEAGRASS) && ((BonemealableBlock) Blocks.SEAGRASS).isValidBonemealTarget(level,
                                blockpos,
                                blockstate1
                        ) && randomsource.nextInt(10) == 0) {
                            ((BonemealableBlock) Blocks.SEAGRASS).performBonemeal(
                                    (ServerLevel) level,
                                    randomsource,
                                    blockpos,
                                    blockstate1
                            );
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static void fertilize(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        Direction[] shuffled = Stream.concat(
                        Stream.of(Direction.UP, Direction.DOWN),
                        Direction.allShuffled(random).stream()
                )
                .toArray(Direction[]::new);
        for (Direction direction : shuffled) {
            if (boneMeal(level, pos, direction)) {
                break;
            }
        }
    }

    public static boolean boneMeal(Level level, BlockPos pos, Direction relative) {
        BlockPos offset = pos.relative(relative);
        if (growCrop(level, pos)) {
            if (!level.isClientSide) {
                level.levelEvent(1505, pos, 15);
            }
            return true;
        } else {
            BlockState blockstate = level.getBlockState(pos);
            boolean flag = blockstate.isFaceSturdy(level, pos, relative);
            if (flag && growWaterPlant(level, offset, relative)) {
                if (!level.isClientSide) {
                    level.levelEvent(1505, offset, 15);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = -2; k <= 2; k++) {
                    fertilize(state, level, pos.offset(i, j, k), random);
                }
            }
        }
    }

}

