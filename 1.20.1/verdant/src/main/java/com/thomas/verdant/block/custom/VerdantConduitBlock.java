package com.thomas.verdant.block.custom;

import javax.annotation.Nullable;

import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.block.entity.custom.VerdantConduitBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerdantConduitBlock extends BaseEntityBlock {

	private static final VoxelShape SHAPE = Shapes.or(Block.box(2, 0, 2, 14, 3, 14), Block.box(3, 3, 3, 13, 13, 13));

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	public VerdantConduitBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false));
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

//	public void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectil) {
//		BlockPos pos = hitResult.getBlockPos();
//
//		if (!level.isClientSide()) {
//			return;
//		}
//
//		BlockEntity be = level.getBlockEntity(pos);
//
//		if (be instanceof VerdantHeartBlockEntity heart) {
//
//			System.out.println("Found a heart. ");
//			System.out.println("Is it active? " + heart.isActive());
//			System.out.println("Sections:");
//			List<BeaconBeamSection> sections = heart.getBeamSections();
//			for (BeaconBeamSection section : sections) {
//				System.out.println(
//						"Color: " + section.getColor()[0] + " " + section.getColor()[1] + " " + section.getColor()[2]);
//				System.out.println("Height: " + section.getHeight());
//			}
//
//		} else {
//			System.out.println("Did not find heart!");
//		}
//
//	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return ModBlockEntities.VERDANT_HEART_BLOCK_ENTITY.get().create(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
			BlockEntityType<T> type) {
		return type == ModBlockEntities.VERDANT_HEART_BLOCK_ENTITY.get() ? VerdantConduitBlockEntity::tick : null;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE);
	}

}
