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
import com.startraveler.verdant.block.custom.OozeFissureBlock;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class OozeFissureSpikeFeature extends Feature<NoneFeatureConfiguration> {

    public OozeFissureSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
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

    @Override
    public boolean place(FeaturePlaceContext featurePlaceContext) {

        BlockPos origin = featurePlaceContext.origin();
        WorldGenLevel level = featurePlaceContext.level();
        boolean anySucceeded = false;
        boolean allSucceeded = true;

        for (Direction d : Direction.values()) {
            boolean result = tryPlaceBlock(
                    level,
                    origin.relative(d),
                    WoodSets.HEARTWOOD.getWood()
                            .get()
                            .defaultBlockState()
                            .trySetValue(BlockStateProperties.AXIS, d.getAxis()),
                    VerdantTags.Blocks.TERRAIN_ALTERING_FEATURES_AFFECT
            );
            anySucceeded |= result;
            allSucceeded &= result;
        }

        if (allSucceeded) {
            tryPlaceBlock(
                    level,
                    origin,
                    BlockRegistry.OOZE_FISSURE_BLOCK.get()
                            .defaultBlockState()
                            .setValue(OozeFissureBlock.AXIS, Direction.Axis.Y).setValue(OozeFissureBlock.NATURAL, true),
                    VerdantTags.Blocks.TERRAIN_ALTERING_FEATURES_AFFECT
            );
        } else {
            tryPlaceBlock(
                    level,
                    origin,
                    BlockRegistry.ROTTEN_WOOD.get().defaultBlockState(),
                    VerdantTags.Blocks.TERRAIN_ALTERING_FEATURES_AFFECT
            );
        }

        return anySucceeded;
    }
}

