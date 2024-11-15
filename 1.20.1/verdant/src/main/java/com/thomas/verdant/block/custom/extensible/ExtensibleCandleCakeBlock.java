package com.thomas.verdant.block.custom.extensible;

import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExtensibleCandleCakeBlock extends AbstractCandleBlock {
	public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
	protected static final float AABB_OFFSET = 1.0F;
	protected static final VoxelShape CAKE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
	protected static final VoxelShape CANDLE_SHAPE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
	protected static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);
	private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

	private final Supplier<Block> baseCake;

	public ExtensibleCandleCakeBlock(Block candle, Supplier<Block> baseCake, Properties properties) {
		// TODO This still overwrites the existing candle cake. Fix by reimplementing...
		// everything.
		super(properties);
		this.baseCake = baseCake;
	}

	protected Iterable<Vec3> getParticleOffsets(BlockState p_152868_) {
		return PARTICLE_OFFSETS;
	}

	public VoxelShape getShape(BlockState p_152875_, BlockGetter p_152876_, BlockPos p_152877_,
			CollisionContext p_152878_) {
		return SHAPE;
	}

	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult result) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE)) {
			if (this.candleHit(result) && player.getItemInHand(hand).isEmpty() && state.getValue(LIT)) {
				extinguish(player, state, level, pos);
				return InteractionResult.sidedSuccess(level.isClientSide);
			} else {
				InteractionResult interactionresult = ((ExtensibleCakeBlock) this.baseCake.get()).eatCustom(level, pos,
						this.baseCake.get().defaultBlockState(), player);
				if (interactionresult.consumesAction()) {
					dropResources(state, level, pos);
				}

				return interactionresult;
			}
		} else {
			return InteractionResult.PASS;
		}
	}

	private boolean candleHit(BlockHitResult hitResult) {
		return hitResult.getLocation().y - (double) hitResult.getBlockPos().getY() > 0.5D;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return new ItemStack(this.baseCake.get());
	}

	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState thisState, Direction relative, BlockState otherState, LevelAccessor level,
			BlockPos thisPos, BlockPos otherPos) {
		return relative == Direction.DOWN && !thisState.canSurvive(level, thisPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(thisState, relative, otherState, level, thisPos, otherPos);
	}

	@SuppressWarnings("deprecation")
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).isSolid();
	}

	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return CakeBlock.FULL_CAKE_SIGNAL;
	}

	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathType) {
		return false;
	}

	public static boolean canLight(BlockState state) {
		return state.is(BlockTags.CANDLE_CAKES, (p_152896_) -> {
			return p_152896_.hasProperty(LIT) && !state.getValue(LIT);
		});
	}
}
