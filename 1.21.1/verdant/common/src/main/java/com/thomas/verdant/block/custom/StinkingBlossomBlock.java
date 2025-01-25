package com.thomas.verdant.block.custom;

import com.thomas.verdant.VerdantIFF;
import com.thomas.verdant.registry.TriggerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class StinkingBlossomBlock extends SporeBlossomBlock {

    public static final EnumProperty<Direction> VERTICAL_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    protected static final Supplier<MobEffectInstance> NAUSEA = () -> new MobEffectInstance(
            MobEffects.CONFUSION,
            100,
            0
    );
    private static final VoxelShape FLOOR_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
    private static final VoxelShape CEILING_SHAPE = Block.box(2.0D, 13.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public StinkingBlossomBlock(Properties properties) {
        super(properties);
    }

    // Inflicts nausea on anything inside.
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && !VerdantIFF.isFriend(entity)) {
            if (!level.isClientSide) {
                if (livingEntity instanceof ServerPlayer player) {
                    TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get().trigger(player);
                }
                livingEntity.addEffect(NAUSEA.get());
            }
        }
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(VERTICAL_DIRECTION);
        return Block.canSupportCenter(level, pos.relative(direction.getOpposite()), direction) && !level.isWaterAt(pos);
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
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

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int l = 0; l < 14; ++l) {
            mutablePos.set(i + Mth.nextInt(rand, -10, 10), j - rand.nextInt(10), k + Mth.nextInt(rand, -10, 10));
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

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(VERTICAL_DIRECTION) == Direction.UP ? FLOOR_SHAPE : CEILING_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(VERTICAL_DIRECTION, context.getNearestLookingVerticalDirection().getOpposite());
    }

    // Very important!
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(VERTICAL_DIRECTION);
    }
}