package com.thomas.verdant.block.custom;

import java.util.function.Supplier;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PoisonVerdantTendrilPlantBlock extends GrowingPlantBodyBlock {

	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final Supplier<MobEffectInstance> POISON = () -> new MobEffectInstance(MobEffects.POISON, 100, 0);

	public PoisonVerdantTendrilPlantBlock(Properties properties) {
		super(properties, Direction.DOWN, SHAPE, true);
	}

	// Inflicts poison on anything inside.
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && livingEntity.getType() != EntityType.FOX
				&& livingEntity.getMobType() != MobType.ARTHROPOD) {
			livingEntity.makeStuckInBlock(state, new Vec3((double) 0.9F, 0.95D, (double) 0.9F));
			if (!level.isClientSide) {
				livingEntity.addEffect(POISON.get());
			}
		}
	}

	@Override
	protected GrowingPlantHeadBlock getHeadBlock() {
		// TODO Auto-generated method stub
		return (GrowingPlantHeadBlock) ModBlocks.POISON_IVY.get();
	}

	@Override
	protected boolean canAttachTo(BlockState state) {
		return true;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos growingFrom = pos.relative(this.growthDirection.getOpposite());
		BlockState growingOn = level.getBlockState(growingFrom);
		if (!this.canAttachTo(growingOn)) {
			return false;
		} else {
			return growingOn.is(this.getHeadBlock()) || growingOn.is(this.getBodyBlock())
					|| growingOn.isFaceSturdy(level, growingFrom, this.growthDirection)
					|| growingOn.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS);
		}
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.randomTick(state, level, pos, rand);
		this.tick(state, level, pos, rand);

	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
	}
}
