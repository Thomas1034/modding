package com.startraveler.verdant.feature.custom;

import com.mojang.serialization.Codec;
import com.startraveler.verdant.block.custom.StranglerVineBlock;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StranglerVineFeature extends Feature<NoneFeatureConfiguration> {

    public StranglerVineFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public static boolean canGrowToFace(WorldGenLevel level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos.relative(direction));
        if (!state.is(VerdantTags.Blocks.SUPPORTS_STRANGLER_VINES)) {
            return false;
        }
        return state.isFaceSturdy(level, pos, direction.getOpposite());
    }

    @Override
    public boolean place(FeaturePlaceContext featurePlaceContext) {

        BlockPos origin = featurePlaceContext.origin();
        WorldGenLevel level = featurePlaceContext.level();
        BlockState originState = level.getBlockState(origin);
        if (originState.is(VerdantTags.Blocks.SUPPORTS_STRANGLER_VINES)) {
            for (Direction offset : Direction.values()) {
                BlockPos neighbor = origin.relative(offset);
                BlockState neighborState = level.getBlockState(neighbor);
                if (neighborState.is(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES)) {
                    level.setBlock(neighbor, getStateForPlacement(level, neighbor), Block.UPDATE_ALL);
                }
            }
        }


        return false;
    }

    // Places a Verdant Vine at that block.
    public BlockState getStateForPlacement(WorldGenLevel level, BlockPos pos) {
        // Store the previous block there.
        BlockState replaced = level.getBlockState(pos);
        // Place the vine block there. Leafy if it is replacing leaves.
        BlockState placed = BlockRegistry.STRANGLER_VINE.get().defaultBlockState();

        // Find every direction it can grow there.
        boolean canGrowToAnyFace = false;
        for (Direction d : Direction.values()) {
            // Place it there.
            if (canGrowToFace(level, pos, d)) {
                placed = placed.setValue(StranglerVineBlock.PROPERTY_FOR_FACE.get(d), 1);
                canGrowToAnyFace = true;
            }
        }
        // Water-log if possible
        placed = placed.setValue(
                BlockStateProperties.WATERLOGGED,
                replaced.getOptionalValue(BlockStateProperties.WATERLOGGED).orElse(false)
        );

        // Update the block.
        return canGrowToAnyFace ? placed : null;
    }
}
