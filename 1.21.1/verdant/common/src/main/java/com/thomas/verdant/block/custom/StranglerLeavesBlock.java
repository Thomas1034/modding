package com.thomas.verdant.block.custom;

import com.thomas.verdant.block.VerdantGrower;
import com.thomas.verdant.util.OptionalDirection;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class StranglerLeavesBlock extends Block implements VerdantGrower {

    public static final EnumProperty<OptionalDirection> NABLA = EnumProperty.create("nabla", OptionalDirection.class);
    public static final int MIN_DISTANCE = 0;
    public static final int MAX_DISTANCE = 8;
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", MIN_DISTANCE, MAX_DISTANCE);
    public static final int MIN_LEAVES_BENEATH = 0;
    public static final int MAX_LEAVES_BENEATH = 3;
    public static final IntegerProperty LEAVES_BENEATH = IntegerProperty.create("leaves_beneath", MIN_LEAVES_BENEATH, MAX_LEAVES_BENEATH);

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    public StranglerLeavesBlock(Properties properties) {
        super(properties);
    }


    // Very important!
    // Defines the properties for the block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT, NABLA, DISTANCE);
    }

}
