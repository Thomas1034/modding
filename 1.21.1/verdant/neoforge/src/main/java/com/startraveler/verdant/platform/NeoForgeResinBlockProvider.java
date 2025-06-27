package com.startraveler.verdant.platform;

import com.startraveler.verdant.platform.services.IResinBlockProvider;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class NeoForgeResinBlockProvider implements IResinBlockProvider {

    @Override
    public Block getResinBlock(BlockBehaviour.Properties properties) {
        return new Block(properties) {
            @Override
            public boolean isStickyBlock(BlockState state) {
                return state.is(VerdantTags.Blocks.VERDANT_RESIN_BLOCKS);
            }

            @Override
            public boolean canStickTo(BlockState state, BlockState other) {
                return state.isStickyBlock() && other.isStickyBlock();
            }
        };
    }

    @Override
    public SlabBlock getResinSlab(BlockBehaviour.Properties properties) {
        return new SlabBlock(properties) {
            @Override
            public boolean isStickyBlock(BlockState state) {
                return state.is(VerdantTags.Blocks.VERDANT_RESIN_BLOCKS);
            }

            @Override
            public boolean canStickTo(BlockState state, BlockState other) {
                return state.isStickyBlock() && other.isStickyBlock();
            }
        };
    }

    @Override
    public StairBlock getResinStair(Supplier<Block> base, BlockBehaviour.Properties properties) {
        return new StairBlock(base.get().defaultBlockState(), properties) {
            @Override
            public boolean isStickyBlock(BlockState state) {
                return state.is(VerdantTags.Blocks.VERDANT_RESIN_BLOCKS);
            }

            @Override
            public boolean canStickTo(BlockState state, BlockState other) {
                return state.isStickyBlock() && other.isStickyBlock();
            }
        };
    }

    @Override
    public WallBlock getResinWall(BlockBehaviour.Properties properties) {
        return new WallBlock(properties) {
            @Override
            public boolean isStickyBlock(BlockState state) {
                return true;
            }

            @Override
            public boolean canStickTo(BlockState state, BlockState other) {
                return state.isStickyBlock() && other.isStickyBlock();
            }
        };
    }
}
