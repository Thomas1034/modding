package com.thomas.verdant.block.custom;

import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.growth.VerdantGrower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;

public class VerdantLeafyVineBlock extends VerdantVineBlock implements IForgeShearable {

	public static final Supplier<BlockState> LEAVES = () -> ModBlocks.VERDANT_LEAVES.get().defaultBlockState();

	public VerdantLeafyVineBlock(Properties properties) {
		super(properties);
		// LeavesBlock
		this.registerDefaultState(this.stateDefinition.any().setValue(UP, 0).setValue(DOWN, 0).setValue(NORTH, 0)
				.setValue(SOUTH, 0).setValue(EAST, 0).setValue(WEST, 0).setValue(WATERLOGGED, false));
	}

	@Override
	public BlockState updateShape(BlockState thisState, Direction p_54441_, BlockState p_54442_, LevelAccessor level,
			BlockPos thisPos, BlockPos p_54445_) {
		thisState = super.updateShape(thisState, p_54441_, p_54442_, level, thisPos, p_54445_);
		// System.out.println("Updating shape of a Verdant Leafy Vine Block.");
		if (thisState.getValue(WATERLOGGED)) {
			level.scheduleTick(thisPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		level.scheduleTick(thisPos, this, 1);

		return thisState;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		// System.out.println("Verdant Leafy Vines are randomly ticking.");
		super.randomTick(state, level, pos, rand);
		// Grow leaves, too.
		float growthChance = this.growthChance(level);
		float randomChance = rand.nextFloat();
		while (randomChance < growthChance) {
			// System.out.println("Trying to spread.");
			VerdantLeavesBlock.spreadLeaves(level, pos);
			growthChance--;
		}
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest,
			FluidState fluid) {
		// Call to run pre-destroy function.
		this.playerWillDestroy(level, pos, state, player);

		// The state to set.
		BlockState toSet = LEAVES.get().setValue(WATERLOGGED, fluid.is(FluidTags.WATER))
				.setValue(BlockStateProperties.DISTANCE, 1);

		// Set the block.
		return level.setBlock(pos, toSet, level.isClientSide ? 11 : 3);
	}

	// Override to ensure the leafy vines turn to leaves when destroyed.
	/*
	 * @Override public void onRemove(BlockState state, Level level, BlockPos pos,
	 * BlockState otherState, boolean maybeMeansDropResources) {
	 * System.out.println("Removing leafy vines. The unknown boolean is " +
	 * maybeMeansDropResources); //
	 * System.out.println("Replacing destroyed vines with leaves."); //
	 * super.onRemove(state, level, pos, otherState, maybeMeansDropResources); //
	 * Replace with leaves. BlockState placed =
	 * LEAVES.get().setValue(BlockStateProperties.DISTANCE, 1); if
	 * (state.hasProperty(BlockStateProperties.WATERLOGGED)) { if
	 * (state.getValue(BlockStateProperties.WATERLOGGED)) { placed =
	 * placed.setValue(BlockStateProperties.WATERLOGGED, true); } }
	 * System.out.println("Leafy vines were broken, replacing with leaves.");
	 * level.setBlockAndUpdate(pos, placed); }
	 */

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.block();
	}

	@Override
	@NotNull
	public List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos,
			int fortune) {
		VerdantGrower.replaceLeafyVineWithVine(level, pos);
		return List.of(new ItemStack(ModBlocks.VERDANT_LEAVES.get(), 1));
	}
}
