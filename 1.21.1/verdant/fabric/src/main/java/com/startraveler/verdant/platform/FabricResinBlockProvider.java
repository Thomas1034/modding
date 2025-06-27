package com.startraveler.verdant.platform;

import com.startraveler.verdant.platform.services.IResinBlockProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class FabricResinBlockProvider implements IResinBlockProvider {
    @Override
    public Block getResinBlock(BlockBehaviour.Properties properties) {
        return new Block(properties);
    }

    @Override
    public SlabBlock getResinSlab(BlockBehaviour.Properties properties) {
        return new SlabBlock(properties);
    }

    @Override
    public StairBlock getResinStair(Supplier<Block> base, BlockBehaviour.Properties properties) {
        return new StairBlock(base.get().defaultBlockState(), properties);
    }

    @Override
    public WallBlock getResinWall(BlockBehaviour.Properties properties) {
        return new WallBlock(properties);
    }

}
