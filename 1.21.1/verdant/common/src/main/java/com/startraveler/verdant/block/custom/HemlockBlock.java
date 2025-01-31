package com.startraveler.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class HemlockBlock extends KelpBlock {
    public static final MapCodec<HemlockBlock> CODEC = simpleCodec(HemlockBlock::new);
    protected static final Supplier<MobEffectInstance> ASPHYXIATION = () -> new MobEffectInstance(
            MobEffectRegistry.ASPHYXIATING.asHolder(),
            100,
            0
    );

    public HemlockBlock(Properties properties) {
        super(properties);
    }

    // Inflicts asphyxiation on anything inside.
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && !VerdantIFF.isFriend(livingEntity)) {
            if (!level.isClientSide) {
                livingEntity.addEffect(ASPHYXIATION.get());
            }
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    protected Block getBodyBlock() {
        return BlockRegistry.DROWNED_HEMLOCK_PLANT.get();
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return random.nextBoolean() && random.nextBoolean() ? 1 : 0;
    }
}
