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

import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.function.Supplier;

public class ToxicDirtBlock extends Block {


    public static final Supplier<MobEffectInstance> POISON = () -> new MobEffectInstance(MobEffects.POISON, 100, 0);
    public static final Supplier<MobEffectInstance> WITHER = () -> new MobEffectInstance(MobEffects.WITHER, 100, 2);
    public static final BooleanProperty IS_SURFACE = BlockStateProperties.UP;

    public ToxicDirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (state.getValue(IS_SURFACE)) {
            // Constants.LOG.warn("Creating particle!");
            if (random.nextInt(10) == 0) {
                level.addParticle(
                        ParticleTypes.SMOKE,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 1.1,
                        pos.getZ() + random.nextDouble(),
                        0.0F,
                        0.0F,
                        0.0F
                );
            }
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack boots = livingEntity.getItemBySlot(EquipmentSlot.FEET);
            ItemStack body = livingEntity.getItemBySlot(EquipmentSlot.BODY);

            boolean isPoisoned = (boots == null || boots.isEmpty()) && (body == null || body.isEmpty());
            boolean isMoving = entity.oldPosition().subtract(entity.position()).length() > 0.01f;

            if (level instanceof ServerLevel) {
                if (isPoisoned) {
                    if (livingEntity.getType().is(VerdantTags.EntityTypes.TOXIC_ASH_DAMAGES)) {

                        livingEntity.addEffect(WITHER.get());
                    } else {

                        livingEntity.addEffect(POISON.get());
                    }
                }
            } else {
                RandomSource random = level.random;
                if (isPoisoned) {
                    level.addParticle(
                            ParticleTypes.SMOKE,
                            pos.getX() + random.nextDouble(),
                            pos.getY() + 1.1,
                            pos.getZ() + random.nextDouble(),
                            0.0F,
                            0.0F,
                            0.0F
                    );
                } else if (isMoving) {
                    level.addParticle(
                            new BlockParticleOption(ParticleTypes.BLOCK, state),
                            pos.getX() + random.nextDouble(),
                            pos.getY() + 1.1,
                            pos.getZ() + random.nextDouble(),
                            0.0F,
                            0.0F,
                            0.0F
                    );

                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.updateDistance(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    // Very important!
    // Defines the properties for the block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_SURFACE);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
        tickAccess.scheduleTick(currentPos, this, 1);
        return state;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlockAndUpdate(pos, this.updateDistance(state, level, pos));
    }

    protected BlockState updateDistance(BlockState state, Level level, BlockPos pos) {
        return state.setValue(IS_SURFACE, !level.getBlockState(pos.above()).isCollisionShapeFullBlock(level, pos));
    }

}

