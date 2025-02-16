/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.block.custom.entity.VerdantConduitBlockEntity;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ConduitBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class VerdantConduitBlock extends ConduitBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final int PARTICLES_PER_TICK = 3;
    private static final int PARTICLE_DELAY = 4;

    public VerdantConduitBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<ConduitBlock> codec() {
        throw new IllegalStateException("Codecs aren't implemented yet!");
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityTypeRegistry.VERDANT_CONDUIT_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                BlockEntityTypeRegistry.VERDANT_CONDUIT_BLOCK_ENTITY.get(),
                level.isClientSide ? VerdantConduitBlockEntity::clientTick : VerdantConduitBlockEntity::serverTick
        );
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof VerdantConduitBlockEntity verdantConduitBlockEntity)) {
            return;
        }
        if (verdantConduitBlockEntity.isActive() && random.nextInt(PARTICLE_DELAY) == 0) {
            for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
                double d0 = -0.5 + pos.getX() + random.nextDouble() * 2;
                double d1 = -0.5 + pos.getY() + random.nextDouble() * 2;
                double d2 = -0.5 + pos.getZ() + random.nextDouble() * 2;
                level.addParticle(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 0.0F, random.nextDouble() * 0.1, 0.0F);
            }
        }

    }
}

