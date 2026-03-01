package com.startraveler.verdant.block.custom;

import net.minecraft.core.Direction;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public class ColoredFallingPillarBlock extends ColoredFallingBlock {

    public static final EnumProperty<Direction.@NotNull Axis> AXIS = BlockStateProperties.AXIS;

    public ColoredFallingPillarBlock(ColorRGBA dustColor, Properties properties) {
        super(dustColor, properties);
    }

    protected @NotNull BlockState rotate(@NotNull BlockState state, @NotNull Rotation rot) {
        return RotatedPillarBlock.rotatePillar(state, rot);
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(AXIS, context.getClickedFace().getAxis());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, @NotNull BlockState> builder) {
        builder.add(AXIS);
    }

}
