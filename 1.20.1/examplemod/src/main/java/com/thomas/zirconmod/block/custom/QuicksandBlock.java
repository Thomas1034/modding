package com.thomas.zirconmod.block.custom;

import com.thomas.zirconmod.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class QuicksandBlock extends PowderSnowBlock {

	public QuicksandBlock(Properties properties) {
		super(properties);
	}

	public boolean skipRendering(BlockState p_154268_, BlockState p_154269_, Direction p_154270_) {
		return false;
	}
	
	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathType)
	{
		return false;
	}

	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
			entity.makeStuckInBlock(state, new Vec3((double) 0.9F, 1.5D, (double) 0.9F));
			if (level.isClientSide) {
				RandomSource randomsource = level.getRandom();
				boolean flag = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
				// Spawn in destroy particles if the entity is moving.
				if (flag)
					this.spawnDestroyParticles(level, null, pos, state);
			}
			entity.setSharedFlagOnFire(false);
		}

		if (!level.isClientSide) {
			if (entity instanceof LivingEntity && entity.getEyeY() > pos.getY() && entity.getEyeY() < pos.getY() + 1)
				entity.hurt(level.damageSources().drown(), 1);
			entity.setSharedFlagOnFire(false);
		}
	}

	public ItemStack pickupBlock(LevelAccessor p_154281_, BlockPos p_154282_, BlockState p_154283_) {
		p_154281_.setBlock(p_154282_, Blocks.AIR.defaultBlockState(), 11);
		if (!p_154281_.isClientSide()) {
			p_154281_.levelEvent(2001, p_154282_, Block.getId(p_154283_));
		}

		return new ItemStack(ModItems.QUICKSAND_BUCKET.get());
	}

}
