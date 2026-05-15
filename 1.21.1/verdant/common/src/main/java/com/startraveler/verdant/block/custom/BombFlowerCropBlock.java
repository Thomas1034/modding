package com.startraveler.verdant.block.custom;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.entity.custom.BlockIgnoringPrimedTnt;
import com.startraveler.verdant.mixin.PrimedTntAccessors;
import com.startraveler.verdant.platform.Services;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.util.CommonTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;

public class BombFlowerCropBlock extends Block implements BonemealableBlock {

    public static final EnumProperty<@NotNull Direction> FACING = BlockStateProperties.FACING;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final int MAX_AGE = 3;
    public static final int MIN_AGE = 0;

    private static final VoxelShape SHAPE_BUD_DOWN = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 2.0D, 10.0D);
    private static final VoxelShape SHAPE_SMALL_FLOWER_DOWN = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D);
    private static final VoxelShape SHAPE_MATURE_DOWN = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

    private static final VoxelShape SHAPE_BUD_UP = Block.box(6.0D, 14.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape SHAPE_SMALL_FLOWER_UP = Block.box(5.0D, 11.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_MATURE_UP = Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    private static final VoxelShape SHAPE_BUD_NORTH = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 2.0D);
    private static final VoxelShape SHAPE_SMALL_FLOWER_NORTH = Block.box(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 5.0D);
    private static final VoxelShape SHAPE_MATURE_NORTH = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D);

    private static final VoxelShape SHAPE_BUD_SOUTH = Block.box(6.0D, 6.0D, 14.0D, 10.0D, 10.0D, 16.0D);
    private static final VoxelShape SHAPE_SMALL_FLOWER_SOUTH = Block.box(5.0D, 5.0D, 11.0D, 11.0D, 11.0D, 16.0D);
    private static final VoxelShape SHAPE_MATURE_SOUTH = Block.box(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D);

    private static final VoxelShape SHAPE_BUD_EAST = Block.box(14.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    private static final VoxelShape SHAPE_SMALL_FLOWER_EAST = Block.box(11.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D);
    private static final VoxelShape SHAPE_MATURE_EAST = Block.box(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);

    private static final VoxelShape SHAPE_BUD_WEST = Block.box(0.0D, 6.0D, 6.0D, 2.0D, 10.0D, 10.0D);
    private static final VoxelShape SHAPE_SMALL_FLOWER_WEST = Block.box(0.0D, 5.0D, 5.0D, 5.0D, 11.0D, 11.0D);
    private static final VoxelShape SHAPE_MATURE_WEST = Block.box(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D);

    private static final Map<Direction, VoxelShape> SHAPE_NO_FLOWER = Map.of(
            Direction.DOWN,
            SHAPE_BUD_DOWN,
            Direction.UP,
            SHAPE_BUD_UP,
            Direction.NORTH,
            SHAPE_BUD_NORTH,
            Direction.SOUTH,
            SHAPE_BUD_SOUTH,
            Direction.EAST,
            SHAPE_BUD_EAST,
            Direction.WEST,
            SHAPE_BUD_WEST
    );
    private static final Map<Direction, VoxelShape> SHAPE_BUD = Map.of(
            Direction.DOWN,
            SHAPE_BUD_DOWN,
            Direction.UP,
            SHAPE_BUD_UP,
            Direction.NORTH,
            SHAPE_BUD_NORTH,
            Direction.SOUTH,
            SHAPE_BUD_SOUTH,
            Direction.EAST,
            SHAPE_BUD_EAST,
            Direction.WEST,
            SHAPE_BUD_WEST
    );
    private static final Map<Direction, VoxelShape> SHAPE_SMALL_FLOWER = Map.of(
            Direction.DOWN,
            SHAPE_SMALL_FLOWER_DOWN,
            Direction.UP,
            SHAPE_SMALL_FLOWER_UP,
            Direction.NORTH,
            SHAPE_SMALL_FLOWER_NORTH,
            Direction.SOUTH,
            SHAPE_SMALL_FLOWER_SOUTH,
            Direction.EAST,
            SHAPE_SMALL_FLOWER_EAST,
            Direction.WEST,
            SHAPE_SMALL_FLOWER_WEST
    );
    private static final Map<Direction, VoxelShape> SHAPE_MATURE = Map.of(
            Direction.DOWN,
            SHAPE_MATURE_DOWN,
            Direction.UP,
            SHAPE_MATURE_UP,
            Direction.NORTH,
            SHAPE_MATURE_NORTH,
            Direction.SOUTH,
            SHAPE_MATURE_SOUTH,
            Direction.EAST,
            SHAPE_MATURE_EAST,
            Direction.WEST,
            SHAPE_MATURE_WEST
    );
    private static final int TICKS_PER_STAGE = 4;
    protected final Function<RandomSource, ItemStack> harvest;

    public BombFlowerCropBlock(Properties properties, Function<RandomSource, ItemStack> harvest) {
        super(properties);
        this.harvest = harvest;
    }

    protected static void explode(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            Vec3 center = pos.getCenter();
            PrimedTnt bomb = new BlockIgnoringPrimedTnt(level, center.x, center.y, center.z, null);
            bomb.setBlockState(BlockRegistry.BLASTING_BUNCH.get().defaultBlockState());
            ((PrimedTntAccessors) bomb).setExplosionPower(3);
            bomb.setDeltaMovement(Vec3.ZERO);
            bomb.setFuse(20);
            bomb.setBoundingBox(bomb.getBoundingBox().deflate(0.25, 0.25, 0.25));
            bomb.refreshDimensions();
            serverLevel.addFreshEntity(bomb);
        }

    }

    @Override
    protected void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, Orientation orientation, boolean movedByPiston) {
        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, false);
        }
        super.neighborChanged(state, level, pos, neighborBlock, orientation, movedByPiston);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, boolean movedByPiston) {
        if (state.getValue(AGE) == MAX_AGE) {
            explode(level, pos);
        }
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        int currentAge = state.getValue(AGE);
        if (currentAge == MAX_AGE) {
            state = state.setValue(AGE, MIN_AGE);
            level.setBlockAndUpdate(pos, state);
            if (stack.is(CommonTags.Items.TOOLS_SHEAR) || player.hasEffect(MobEffects.STRENGTH)) {
                popResource(level, pos, this.harvest.apply(level.getRandom()));
                stack.hurtAndBreak(1, player, hand);
                level.playSound(
                        null,
                        pos,
                        SoundEvents.SHEARS_SNIP,
                        SoundSource.BLOCKS,
                        1.0F,
                        0.8F + level.getRandom().nextFloat() * 0.4F
                );
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));

            } else {
                explode(level, pos);
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    @Override
    protected boolean canSurvive(@Nullable BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        if (null == state) {
            return false;
        }

        Direction facing = state.getValue(FACING);
        BlockState attachedTo = level.getBlockState(pos.relative(facing.getOpposite()));
        return attachedTo.isFaceSturdy(level, pos, facing) && super.canSurvive(state, level, pos);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int age = state.getValue(AGE);
        Direction facing = state.getValue(FACING).getOpposite();
        return switch (age) {
            case MIN_AGE -> SHAPE_NO_FLOWER.get(facing);
            case MIN_AGE + 1 -> SHAPE_BUD.get(facing);
            case MAX_AGE - 1 -> SHAPE_SMALL_FLOWER.get(facing);
            case MAX_AGE -> SHAPE_MATURE.get(facing);
            default -> {
                Constants.LOG.error("Unexpected value: {} in BombFlowerCropBlock#getShape", age);
                yield SHAPE_SMALL_FLOWER.get(facing);
            }
        };
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(AGE) > MIN_AGE + 1 ? state.getShape(level, pos, context) : Shapes.empty();
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.randomTick(state, level, pos, random);
        Services.CROP_EVENT_HELPER.fireEvent(
                level,
                pos,
                state,
                random.nextDouble() < (1.0 / TICKS_PER_STAGE),
                () -> level.setBlockAndUpdate(pos, state.setValue(AGE, state.getValue(AGE) + 1))
        );
    }

    @Override
    protected void onProjectileHit(@NotNull Level level, @NotNull BlockState state, @NotNull BlockHitResult hit, @NotNull Projectile projectile) {
        super.onProjectileHit(level, state, hit, projectile);
        BlockPos pos = hit.getBlockPos();
        if (state.getValue(AGE) > MIN_AGE) {
            level.setBlockAndUpdate(pos, state.setValue(AGE, MIN_AGE));
            explode(level, pos);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) != MAX_AGE;
    }

    @Override
    public void wasExploded(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        super.wasExploded(level, pos, explosion);
        explode(level, pos);
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!entity.is(EntityTypeTags.FALL_DAMAGE_IMMUNE) && VerdantIFF.isEnemy(entity) && state.getValue(AGE) == MAX_AGE) {
            level.setBlockAndUpdate(pos, state.setValue(AGE, MIN_AGE));
            explode(level, pos);
        }
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            state = state.setValue(FACING, context.getClickedFace());
        }
        return this.canSurvive(state, context.getLevel(), context.getClickedPos()) ? state : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, @NotNull BlockState> builder) {
        builder.add(FACING, AGE);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(AGE) != MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return isValidBonemealTarget(level, blockPos, blockState);
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, BlockState blockState) {
        serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, blockState.getValue(AGE) + 1));
    }
}
