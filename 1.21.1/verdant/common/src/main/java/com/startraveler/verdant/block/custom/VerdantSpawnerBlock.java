package com.startraveler.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.block.custom.entity.OvergrownSpawnerBlockEntity;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VerdantSpawnerBlock extends BaseEntityBlock {
    public static final MapCodec<VerdantSpawnerBlock> CODEC = simpleCodec(VerdantSpawnerBlock::new);

    public VerdantSpawnerBlock(Properties properties) {
        super(properties);
    }

    public @NotNull MapCodec<VerdantSpawnerBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new OvergrownSpawnerBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                BlockEntityTypeRegistry.OVERGROWN_SPAWNER.get(),
                level.isClientSide() ? OvergrownSpawnerBlockEntity::clientTick : OvergrownSpawnerBlockEntity::serverTick
        );
    }


    @Override
    protected void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (dropExperience) {
            int i = 15 + level.getRandom().nextInt(15) + level.getRandom().nextInt(15);
            this.popExperience(level, pos, i);
        }
    }
}
