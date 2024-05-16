package com.thomas.verdant.block.custom;

import java.util.List;

import javax.annotation.Nullable;

import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.block.entity.VerdantHeartBlockEntity;
import com.thomas.verdant.block.entity.VerdantHeartBlockEntity.BeaconBeamSection;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class VerdantHeartBlock extends BaseEntityBlock {

	public VerdantHeartBlock(Properties properties) {
		super(properties);

	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
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
		return type == ModBlockEntities.VERDANT_HEART_BLOCK_ENTITY.get() ? VerdantHeartBlockEntity::tick : null;
	}

}
