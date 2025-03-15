package com.startraveler.verdant.block.custom.extensible;

import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;
import java.util.function.Supplier;

public class HugeAloeCropBlock extends Block {
    public static int MAX_COORD = 2;
    public static int MIN_COORD = 0;
    public static int CENTER_COORD = (MAX_COORD - MIN_COORD) / 2;
    public static IntegerProperty X_PROPERTY = IntegerProperty.create("x", MIN_COORD, MAX_COORD);
    public static IntegerProperty Y_PROPERTY = IntegerProperty.create("y", MIN_COORD, MAX_COORD);
    public static IntegerProperty Z_PROPERTY = IntegerProperty.create("z", MIN_COORD, MAX_COORD);
    public static IntegerProperty AGE = BlockStateProperties.AGE_7;
    public static int MAX_AGE = 7;
    protected final Function<RandomSource, ItemStack> harvest;
    protected final Supplier<Item> baseSeed;


    public HugeAloeCropBlock(Properties properties, Function<RandomSource, ItemStack> harvest, Supplier<Item> baseSeed) {
        super(properties);
        this.harvest = harvest;
        this.baseSeed = baseSeed;
        this.registerDefaultState(this.getStateDefinition()
                .any()
                .setValue(X_PROPERTY, CENTER_COORD)
                .setValue(Y_PROPERTY, MIN_COORD)
                .setValue(Z_PROPERTY, CENTER_COORD));
    }

    // Returns true if the given center position is supported and on a valid block for aloes.
    protected boolean isCenterSafe(LevelReader level, BlockPos center) {
        BlockPos below = center.below();
        BlockState belowState = level.getBlockState(below);
        return belowState.isFaceSturdy(level, below, Direction.UP) && belowState.is(BlockTags.DIRT);
    }

    // Returns true if this is the center of a full bush.
    protected boolean isFullBush(LevelReader level, BlockPos center) {
        int age = -1;
        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j <= 2; j++) {
                for (int k = -1; k <= 1; k++) {
                    BlockState state = level.getBlockState(center.offset(i, j, k));
                    if (!state.is(this)) {
                        return false;
                    }
                    if (age == -1) {
                        age = state.getValue(AGE);
                    }
                    if (this.getXPos(state) != i || state.getValue(Y_PROPERTY) != j || this.getZPos(state) != k || (state.getValue(
                            AGE) != age && age != -1)) {
                        System.out.println(this.getXPos(state) != i);
                        System.out.println(state.getValue(Y_PROPERTY) != j);
                        System.out.println(this.getZPos(state) != k);
                        System.out.println(state.getValue(AGE) != age);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected int getZPos(BlockState state) {
        return state.getValue(Z_PROPERTY) - 1;
    }

    protected int getXPos(BlockState state) {
        return state.getValue(X_PROPERTY) - 1;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, Orientation orientation, boolean movedByPiston) {
        if (!this.canSurvive(state, level, pos)) {
            this.destroyFullBush(level, this.getCenterPos(state, pos));
        }
        super.neighborChanged(state, level, pos, neighborBlock, orientation, movedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int currentAge = state.getValue(AGE);
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
            BlockPos center = this.getCenterPos(state, pos);
            BlockState centerState = this.placeFullBush(level, center, currentAge - 1);
            level.gameEvent(GameEvent.BLOCK_CHANGE, center, GameEvent.Context.of(player, centerState));
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return this.isFullBush(level, this.getCenterPos(state, pos)) && this.isCenterSafe(
                level,
                this.getCenterPos(state, pos)
        ) && super.canSurvive(state, level, pos);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (this.getCenterPos(state, BlockPos.ZERO).equals(BlockPos.ZERO)) {
            Services.CROP_EVENT_HELPER.fireEvent(
                    level, pos, state, true, () -> {
                        this.placeFullBush(level, pos, state.getValue(AGE) + 1);
                    }
            );
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.getType() != EntityType.BEE && livingEntity.getType() != EntityType.RABBIT && VerdantIFF.isEnemy(
                livingEntity)) {
            float slowdownFactor = ((float) ((1 - Math.abs(this.getXPos(state))) + (1 - Math.abs(this.getZPos(state))) + (2 - state.getValue(
                    Y_PROPERTY)))) / (8);
            slowdownFactor = 1 - slowdownFactor;
            // Handle floating point imprecision.
            if (slowdownFactor < 0.99999f) {
                entity.makeStuckInBlock(state, new Vec3(slowdownFactor, 0.75, slowdownFactor));
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return super.isRandomlyTicking(state) && this.getCenterPos(state, BlockPos.ZERO)
                .equals(BlockPos.ZERO) && state.getValue(AGE) < MAX_AGE;
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean maybeSimulate) {
        return new ItemStack(this.baseSeed.get());
    }

    // Breaks a full bush, given the center.
    protected void destroyFullBush(Level level, BlockPos center) {
        int age = -1;
        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j <= 2; j++) {
                for (int k = -1; k <= 1; k++) {
                    BlockState state = level.getBlockState(center.offset(i, j, k));
                    if (!state.is(this)) {
                        continue;
                    }
                    if (age == -1) {
                        age = state.getValueOrElse(AGE, -1);
                    }
                    // If it's invalid, skip. This should decrease glitches.
                    if (this.getXPos(state) != i || state.getValue(Y_PROPERTY) != j || this.getZPos(state) != k || state.getValue(
                            AGE) != age) {
                        continue;
                    }
                    level.setBlockAndUpdate(center.offset(i, j, k), Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    // Places a bush of the specified age at the given position.
    // Returns the block state of the center stem.
    public BlockState placeFullBush(Level level, BlockPos center, int age) {

        BlockState stem = null;
        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j <= 2; j++) {
                for (int k = -1; k <= 1; k++) {

                    BlockPos offset = center.offset(i, j, k);

                    BlockState state = this.defaultBlockState()
                            .setValue(X_PROPERTY, i + 1)
                            .setValue(Y_PROPERTY, j)
                            .setValue(Z_PROPERTY, k + 1)
                            .setValue(AGE, age);
                    if (i == j && j == 1 && k == 0) {
                        stem = state;
                    }

                    level.setBlock(offset, state, Block.UPDATE_CLIENTS);
                }
            }
        }
        return stem;
    }

    // If allowStem is true, then the center base block is ignored.
    public boolean canPlace(Level level, BlockPos center, boolean allowStem) {
        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j <= 2; j++) {
                for (int k = -1; k <= 1; k++) {
                    BlockState state = level.getBlockState(center.offset(i, j, k));
                    if (!(state.is(BlockTags.REPLACEABLE_BY_TREES) || state.isAir()) && !(i == j && j == k && k == 0 && allowStem)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected BlockPos getCenterPos(BlockState state, BlockPos pos) {
        return pos.offset(-this.getXPos(state), -state.getValue(Y_PROPERTY), -this.getZPos(state));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.canPlace(
                context.getLevel(),
                context.getClickedPos(),
                false
        ) ? super.getStateForPlacement(context) : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, X_PROPERTY, Y_PROPERTY, Z_PROPERTY);
    }

}
