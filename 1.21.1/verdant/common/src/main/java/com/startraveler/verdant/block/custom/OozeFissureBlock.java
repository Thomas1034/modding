package com.startraveler.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.block.custom.entity.OozeFissureBlockEntity;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.CreakingHeartState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class OozeFissureBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction.@NotNull Axis> AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<@NotNull CreakingHeartState> STATE = BlockStateProperties.CREAKING_HEART_STATE;
    public static final BooleanProperty NATURAL = BlockStateProperties.NATURAL;
    protected final TagKey<Block> requiredLogs;

    public OozeFissureBlock(Properties properties, TagKey<Block> requiredLogs) {
        super(properties);
        this.requiredLogs = requiredLogs;
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(AXIS, Direction.Axis.Y)
                .setValue(STATE, CreakingHeartState.UPROOTED)
                .setValue(NATURAL, false));

    }

    @SuppressWarnings("unused")
    public static boolean timeAgreeing(Level level) {
        // Confederate season else no creature seeing
        return true; // TODO refine when they spawn?
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(STATE) != CreakingHeartState.UPROOTED && random.nextInt(16) == 0) {
            level.playLocalSound(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    SoundEvents.SLIME_SQUISH,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F,
                    false
            );
        }

    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateState(
                this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()),
                context.getLevel(),
                context.getClickedPos()
        );
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof OozeFissureBlockEntity) {
            this.tryAwardExperience(player, state, level, pos);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS, STATE, NATURAL);
    }

    protected BlockState updateState(BlockState state, Level level, BlockPos pos) {
        boolean hasRequiredLogs = this.hasRequiredLogs(state, level, pos);
        boolean isUprooted = state.getValue(STATE) == CreakingHeartState.UPROOTED;
        return hasRequiredLogs && isUprooted ? state.setValue(
                STATE,
                OozeFissureBlock.timeAgreeing(level) ? CreakingHeartState.AWAKE : CreakingHeartState.DORMANT
        ) : state;
    }

    public boolean hasRequiredLogs(BlockState state, LevelReader level, BlockPos pos) {
        Direction.Axis axis = state.getValue(AXIS);
        for (Direction direction : axis.getDirections()) {
            BlockState blockstate = level.getBlockState(pos.relative(direction));
            if (!blockstate.is(this.requiredLogs) || blockstate.getValue(AXIS) != axis) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull MapCodec<OozeFissureBlock> codec() {
        throw new IllegalStateException("Block codecs are not yet implemented.");
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return BlockEntityTypeRegistry.OOZE_FISSURE_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                BlockEntityTypeRegistry.OOZE_FISSURE_BLOCK_ENTITY.get(),
                level.isClientSide() ? OozeFissureBlockEntity::clientTick : OozeFissureBlockEntity::serverTick
        );
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, ScheduledTickAccess scheduledTickAccess, @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPos neighborPos, @NotNull BlockState neighborState, @NotNull RandomSource random) {
        scheduledTickAccess.scheduleTick(pos, this, 1);
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void affectNeighborsAfterRemoval(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    protected void onExplosionHit(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull Explosion explosion, @NotNull BiConsumer<ItemStack, BlockPos> dropConsumer) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof OozeFissureBlockEntity) {
            if (explosion instanceof ServerExplosion) {
                if (explosion.getBlockInteraction().shouldAffectBlocklikeEntities()) {
                    LivingEntity explosionIndirectSourceEntity = explosion.getIndirectSourceEntity();
                    if (explosionIndirectSourceEntity instanceof Player player) {
                        if (explosion.getBlockInteraction().shouldAffectBlocklikeEntities()) {
                            this.tryAwardExperience(player, state, level, pos);
                        }
                    }
                }
            }
        }

        super.onExplosionHit(state, level, pos, explosion, dropConsumer);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected @NotNull BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
        return RotatedPillarBlock.rotatePillar(state, rotation);
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Direction direction) {
        if (state.getValue(STATE) == CreakingHeartState.UPROOTED) {
            return 0;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            int signal;
            if (blockEntity instanceof OozeFissureBlockEntity oozeFissureBlockEntity) {
                signal = oozeFissureBlockEntity.getAnalogOutputSignal();
            } else {
                signal = 0;
            }

            return signal;
        }
    }

    @Override
    protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        BlockState blockState = this.updateState(state, level, pos);
        if (blockState != state) {
            level.setBlock(pos, blockState, 3);
        }

    }

    private void tryAwardExperience(Player player, BlockState state, Level level, BlockPos pos) {
        if (!player.preventsBlockDrops() && !player.isSpectator() && state.getValue(NATURAL) && level instanceof ServerLevel serverlevel) {
            this.popExperience(serverlevel, pos, level.random.nextIntBetweenInclusive(20, 24));
            Block.popResource(level, pos, new ItemStack(ItemRegistry.BALSAM.get()));
        }

    }

}
