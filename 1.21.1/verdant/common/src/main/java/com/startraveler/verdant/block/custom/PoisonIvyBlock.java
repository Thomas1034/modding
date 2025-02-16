﻿/*
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
import com.startraveler.verdant.registry.TriggerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class PoisonIvyBlock extends StranglerTendrilBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public static final MapCodec<PoisonIvyBlock> CODEC = simpleCodec(PoisonIvyBlock::new);
    protected static final Supplier<MobEffectInstance> POISON = () -> new MobEffectInstance(MobEffects.POISON, 100, 0);

    public PoisonIvyBlock(Properties properties) {
        super(properties, SHAPE);
        this.maxAge = 8;
    }

    // Inflicts poison on anything inside.
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && VerdantIFF.isEnemy(livingEntity)) {
            if (!level.isClientSide) {
                if (livingEntity instanceof ServerPlayer player) {
                    TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get().trigger(player);
                }
                livingEntity.addEffect(POISON.get());
            }
        }
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    @Override
    protected Block getBodyBlock() {
        return BlockRegistry.POISON_IVY_PLANT.get();
    }
}

