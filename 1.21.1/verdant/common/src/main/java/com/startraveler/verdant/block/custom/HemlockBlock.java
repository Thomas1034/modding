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
        if (entity instanceof LivingEntity livingEntity && VerdantIFF.isEnemy(livingEntity)) {
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

