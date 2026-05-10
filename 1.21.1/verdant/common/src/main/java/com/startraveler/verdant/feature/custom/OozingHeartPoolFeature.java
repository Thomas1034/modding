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
package com.startraveler.verdant.feature.custom;

import com.mojang.serialization.Codec;
import com.startraveler.verdant.block.custom.OozingHeartBlock;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class OozingHeartPoolFeature extends Feature<@NotNull NoneFeatureConfiguration> {

    public OozingHeartPoolFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @SafeVarargs
    public static boolean tryPlaceBlock(ServerLevelAccessor level, BlockPos pos, BlockState state, TagKey<Block>... canReplace) {
        BlockState original = level.getBlockState(pos);
        for (TagKey<Block> replaceable : canReplace) {
            if (original.is(replaceable)) {
                level.setBlock(pos, state, Block.UPDATE_ALL);
                return true;
            }
        }
        return false;
    }

    protected static boolean tryPlaceBlockTerrainSafe(ServerLevelAccessor level, BlockPos pos, BlockState state) {
        return tryPlaceBlock(level, pos, state, VerdantTags.Blocks.TERRAIN_ALTERING_FEATURES_AFFECT);
    }

    @Override
    public boolean place(FeaturePlaceContext featurePlaceContext) {

        BlockPos origin = featurePlaceContext.origin().below();
        WorldGenLevel level = featurePlaceContext.level();

        // Place the wooden frame.
        for (int i = -2; i <= 2; i++) {
            for (int k = -2; k <= 2; k++) {
                // Skip corners.
                if (Math.abs(i) == 2 && Math.abs(k) == 2) {
                    continue;
                }
                for (int j = -1; j <= 0; j++) {
                    tryPlaceBlockTerrainSafe(
                            level,
                            origin.offset(i, j, k),
                            WoodSets.HEARTWOOD.getWood().get().defaultBlockState()
                    );
                }
                for (int j = 1; j <= 2; j++) {
                    tryPlaceBlockTerrainSafe(level, origin.offset(i, j, k), Blocks.AIR.defaultBlockState());
                }
            }
        }

        // Place the sap.
        for (int i = -1; i <= 1; i++) {
            for (int k = -1; k <= 1; k++) {
                tryPlaceBlockTerrainSafe(
                        level,
                        origin.offset(i, 0, k),
                        BlockRegistry.SAP_BLOCK.get().defaultBlockState()
                );
            }
        }

        // Place the plus, below the origin.
        boolean anySucceeded = false;
        boolean allSucceeded = true;
        for (Direction d : Direction.values()) {
            if (d.getStepY() == 0) {
                boolean result = tryPlaceBlockTerrainSafe(
                        level,
                        origin.relative(d).below(1),
                        WoodSets.HEARTWOOD.getLog()
                                .get()
                                .defaultBlockState()
                                .trySetValue(BlockStateProperties.AXIS, d.getAxis())
                );
                anySucceeded |= result;
                allSucceeded &= result;
            }
        }

        // Place the ooze fissure.
        if (allSucceeded) {
            tryPlaceBlockTerrainSafe(
                    level,
                    origin.below(1),
                    BlockRegistry.OOZING_HEART.get()
                            .defaultBlockState()
                            .setValue(OozingHeartBlock.AXIS, Direction.Axis.Y)
                            .setValue(OozingHeartBlock.NATURAL, true)
            );
        } else {
            tryPlaceBlockTerrainSafe(level, origin.below(1), BlockRegistry.ROTTEN_WOOD.get().defaultBlockState());
        }

        return anySucceeded;
    }
}

