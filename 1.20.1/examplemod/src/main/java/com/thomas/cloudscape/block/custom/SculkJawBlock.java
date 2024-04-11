package com.thomas.cloudscape.block.custom;

import com.thomas.cloudscape.entity.custom.MoleEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkJawBlock extends Block {

	protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 16.0D, 15.0D, 16.0D);
	//protected static final VoxelShape OUTLINE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public SculkJawBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (!level.isClientSide()) {
			// Check if the entity is an instance of LivingEntity or a subclass if you want
			// to damage only living entities
			if (entity instanceof LivingEntity) {
				if (!(entity instanceof Warden) && !(entity instanceof MoleEntity)) {
					// Apply the desired damage to the entity
					((LivingEntity) entity).hurt(entity.damageSources().magic(), 3.0f);
					// Plays a sound
					level.playSound(null, pos, SoundEvents.SCULK_SHRIEKER_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);

				}
			}
		}
	}

	// These are from the cactus class, used to enable contact damage.
	public VoxelShape getCollisionShape(BlockState p_51176_, BlockGetter p_51177_, BlockPos p_51178_,
			CollisionContext p_51179_) {
		return COLLISION_SHAPE;
	}

//	public VoxelShape getShape(BlockState p_51171_, BlockGetter p_51172_, BlockPos p_51173_,
//			CollisionContext p_51174_) {
//		return OUTLINE_SHAPE;
//	}

	@Override
	// This may cause problems due to the *1.5F
	public void fallOn(Level p_152426_, BlockState p_152427_, BlockPos p_152428_, Entity p_152429_, float p_152430_) {
		super.fallOn(p_152426_, p_152427_, p_152428_, p_152429_, p_152430_ * 1.5f);
	}

}