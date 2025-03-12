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
import com.startraveler.verdant.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.function.Function;
import java.util.function.Supplier;

public class AloeCropBlock extends CropBlock {

    private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    protected final TriConsumer<BlockState, Level, BlockPos> mature;
    protected final Function<RandomSource, ItemStack> harvest;
    protected final Supplier<Item> baseSeed;
    protected final Function<Integer, Float> slowdown;

    public AloeCropBlock(TriConsumer<BlockState, Level, BlockPos> mature, Function<RandomSource, ItemStack> harvest, Supplier<Item> baseSeed, Function<Integer, Float> slowdown, Properties properties) {
        super(properties);
        this.mature = mature;
        this.harvest = harvest;
        this.baseSeed = baseSeed;
        this.slowdown = slowdown;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return BlockStateProperties.AGE_2;
    }

    @Override
    public int getMaxAge() {
        return 2;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(this.getAgeProperty()) <= this.getMaxAge();
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(this.getAgeProperty()) == this.getMaxAge()) {
            Services.CROP_EVENT_HELPER.fireEvent(
                    level, pos, state, true, () -> {
                        this.mature.accept(state, level, pos);
                    }
            );
        } else {
            super.randomTick(state, level, pos, random);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.getType() != EntityType.BEE && livingEntity.getType() != EntityType.RABBIT && VerdantIFF.isEnemy(
                livingEntity)) {
            float slowdownFactor = slowdown.apply(state.getValue(this.getAgeProperty()));
            slowdownFactor = 1 - slowdownFactor;
            // Handle floating point imprecision.
            if (slowdownFactor < 0.99999f) {
                entity.makeStuckInBlock(state, new Vec3(slowdownFactor, 0.75, slowdownFactor));
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.baseSeed.get();
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(this.baseSeed.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(this.getAgeProperty());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int currentAge = state.getValue(this.getAgeProperty());
        if (currentAge > 0) {
            popResource(level, pos, this.harvest.apply(level.random));
            level.playSound(
                    null,
                    pos,
                    SoundEvents.BOGGED_SHEAR,
                    SoundSource.BLOCKS,
                    1.0F,
                    0.8F + level.random.nextFloat() * 0.4F
            );
            BlockState blockstate = state.setValue(this.getAgeProperty(), currentAge - 1);
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int currentAge = state.getValue(this.getAgeProperty());
        boolean isMaxAge = currentAge == this.getMaxAge();
        return (!isMaxAge && stack.is(Items.BONE_MEAL) ? InteractionResult.PASS : super.useItemOn(
                stack,
                state,
                level,
                pos,
                player,
                hand,
                hitResult
        ));
    }
}
