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

import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.registry.MobEffectRegistry;
import com.startraveler.verdant.registry.TriggerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SporeBlossomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class StinkingBlossomBlock extends SporeBlossomBlock {

    public static final EnumProperty<@NotNull Direction> VERTICAL_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    protected static final Supplier<MobEffectInstance> NAUSEA = () -> new MobEffectInstance(
            MobEffects.NAUSEA,
            100,
            0
    );
    private static final VoxelShape FLOOR_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
    private static final VoxelShape CEILING_SHAPE = Block.box(2.0D, 13.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public StinkingBlossomBlock(Properties properties) {
        super(properties);
    }

    public boolean canSurvive(BlockState state, @NotNull LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(VERTICAL_DIRECTION);
        return Block.canSupportCenter(level, pos.relative(direction.getOpposite()), direction) && !level.isWaterAt(pos);
    }

    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess tickAccess, @NotNull BlockPos currentPos, @NotNull Direction facing, @NotNull BlockPos facingPos, @NotNull BlockState facingState, @NotNull RandomSource random) {
        return (facing == Direction.DOWN || facing == Direction.UP) && !this.canSurvive(
                state,
                level,
                currentPos
        ) ? Blocks.AIR.defaultBlockState() : super.updateShape(
                state,
                level,
                tickAccess,
                currentPos,
                facing,
                facingPos,
                facingState,
                random
        );
    }

    public void animateTick(@NotNull BlockState state, @NotNull Level level, BlockPos pos, @NotNull RandomSource rand) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int verticalFactor = (state.getValue(VERTICAL_DIRECTION) == Direction.UP ? 1 : -1);
        for (int l = 0; l < 14; ++l) {
            mutablePos.set(
                    x + Mth.nextInt(rand, -10, 10),
                    y + verticalFactor * rand.nextInt(10),
                    z + Mth.nextInt(rand, -10, 10)
            );
            BlockState blockstate = level.getBlockState(mutablePos);
            if (!blockstate.isCollisionShapeFullBlock(level, mutablePos)) {
                level.addParticle(
                        ParticleTypes.SPORE_BLOSSOM_AIR,
                        (double) mutablePos.getX() + rand.nextDouble(),
                        (double) mutablePos.getY() + rand.nextDouble(),
                        (double) mutablePos.getZ() + rand.nextDouble(),
                        0.0D,
                        0.0D,
                        0.0D
                );
            }
        }

    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(VERTICAL_DIRECTION) == Direction.UP ? FLOOR_SHAPE : CEILING_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(VERTICAL_DIRECTION, context.getNearestLookingVerticalDirection().getOpposite());
    }

    // Very important!
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VERTICAL_DIRECTION);
    }

    @Override
    protected void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        // Create a cloud that gives nausea and stench when the flower is broken.
        if (level.getGameRules().get(GameRules.BLOCK_DROPS)) {
            Vec3 center = pos.getCenter();
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(level, center.x, center.y, center.z);
            areaEffectCloud.setRadius(2.0F);
            areaEffectCloud.setDuration(80);
            areaEffectCloud.setRadiusPerTick(0.0F);
            areaEffectCloud.setPotionContents(new PotionContents(
                    Optional.empty(),
                    Optional.of(0x798f35),
                    List.of(
                            new MobEffectInstance(MobEffects.NAUSEA, 240, 0),
                            new MobEffectInstance(MobEffectRegistry.STENCH.asHolder(), 800, 0)
                    ),
                    Optional.empty()
            ));
            level.addFreshEntity(areaEffectCloud);
        }
    }

    // Inflicts nausea on anything inside.
    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity, @NotNull InsideBlockEffectApplier applier, boolean intersects) {
        super.entityInside(state, level, pos, entity, applier, intersects);
        if (entity instanceof LivingEntity livingEntity && VerdantIFF.isEnemy(entity)) {
            if (!level.isClientSide()) {
                if (livingEntity instanceof ServerPlayer player) {
                    TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get().trigger(player);
                }
                livingEntity.addEffect(NAUSEA.get());
            }
        }
    }
}
