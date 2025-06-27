package com.startraveler.verdant.platform.services;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public interface IResinBlockProvider {
    Block getResinBlock(BlockBehaviour.Properties properties);

    SlabBlock getResinSlab(BlockBehaviour.Properties properties);

    StairBlock getResinStair(Supplier<Block> base, BlockBehaviour.Properties properties);

    WallBlock getResinWall(BlockBehaviour.Properties properties);
}
