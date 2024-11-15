package com.thomas.verdant.block.custom;

import java.util.function.Supplier;

import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.overgrowth.EntityOvergrowthEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SmallDripleafBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

public class WaterHemlockBlock extends SmallDripleafBlock {

	protected static final Supplier<MobEffectInstance> CHOKING = () -> new MobEffectInstance(
			ModMobEffects.ASPHYXIATING.get(), 30, 1);
	protected static final Supplier<MobEffectInstance> WITHER = () -> new MobEffectInstance(MobEffects.WITHER, 40, 0);

	public WaterHemlockBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		popResource(level, pos, new ItemStack(this));
	}

	// Inflicts wither on anything inside.
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && !EntityOvergrowthEffects.isFriend(livingEntity)) {
			if (!level.isClientSide) {
				if (level.getDifficulty() == Difficulty.HARD
						&& livingEntity.canDrownInFluidType(ForgeMod.WATER_TYPE.get())) {
					livingEntity.addEffect(CHOKING.get());
				} else {
					// Apply wither on easier game modes or to non-water-breathing entities.
					livingEntity.addEffect(WITHER.get());
				}
			}
		}
	}
}
