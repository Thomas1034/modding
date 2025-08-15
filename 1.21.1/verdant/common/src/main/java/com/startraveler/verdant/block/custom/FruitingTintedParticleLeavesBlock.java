package com.startraveler.verdant.block.custom;

import com.startraveler.verdant.platform.Services;
import com.startraveler.verdant.util.CommonTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class FruitingTintedParticleLeavesBlock extends TintedParticleLeavesBlock implements BonemealableBlock {

    public static final IntegerProperty STAGES = BlockStateProperties.AGE_2;
    public static final int MAX_STAGES = 2;
    private static final double GROWTH_CHANCE = 0.0125;
    protected final Function<RandomSource, ItemStack> harvest;

    public FruitingTintedParticleLeavesBlock(float particleChance, Properties properties, Function<RandomSource, ItemStack> harvest) {
        super(particleChance, properties);
        this.harvest = harvest;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return !(Boolean) state.getValue(PERSISTENT);
    }

    @Override
    protected void randomTick(BlockState state, @NotNull ServerLevel serverLevel, @NotNull BlockPos pos, RandomSource random) {
        int currentStage = state.getValue(STAGES);
        boolean randomRollSucceeds = random.nextDouble() < GROWTH_CHANCE;
        if (!this.decaying(state)) {
            Services.CROP_EVENT_HELPER.fireEvent(
                    serverLevel, pos, state, (currentStage < MAX_STAGES) && randomRollSucceeds, () -> {
                        serverLevel.setBlockAndUpdate(
                                pos,
                                state.setValue(STAGES, currentStage + 1)
                        );
                    }
            );
        }
        super.randomTick(
                randomRollSucceeds && !this.decaying(state) ? serverLevel.getBlockState(pos) : state,
                serverLevel,
                pos,
                random
        );

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STAGES);
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        int currentAge = state.getValue(STAGES);
        if (currentAge == MAX_STAGES) {
            if (stack.is(CommonTags.Items.TOOLS_SHEAR)) {
                state = state.setValue(STAGES, 0);
                level.setBlockAndUpdate(pos, state);
                popResourceFromFace(level, pos, hitResult.getDirection(), this.harvest.apply(level.random));
                stack.hurtAndBreak(1, player, hand);
                level.playSound(
                        null,
                        pos,
                        SoundEvents.SHEARS_SNIP,
                        SoundSource.BLOCKS,
                        1.0F,
                        0.8F + level.random.nextFloat() * 0.4F
                );
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(STAGES) != MAX_STAGES;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, BlockState blockState) {
        serverLevel.setBlockAndUpdate(
                blockPos,
                blockState.setValue(STAGES, Math.min(blockState.getValue(STAGES) + 1, MAX_STAGES))
        );
    }
}
