package com.thomas.verdant.block.custom;


import com.thomas.verdant.registry.DamageSourceRegistry;
import com.thomas.verdant.registry.MobEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.function.Supplier;

public class TrapBlock extends Block {

    public static final int MIN_STAGE = 0;
    public static final int MAX_STAGE = 3;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", MIN_STAGE, MAX_STAGE);
    public static final BooleanProperty SHRINKING = BooleanProperty.create("shrinking");
    public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape[] SHAPE = {Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 5.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 10.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D)};
    private static final double BOX_INSET = 5D;
    protected static final AABB[] TOUCH_SHAPE = {new AABB(
            BOX_INSET / 16D,
            0.0D,
            BOX_INSET / 16D,
            (16D - BOX_INSET) / 16D,
            1D / 16D,
            (16D - BOX_INSET) / 16D
    ),
            new AABB(BOX_INSET / 16D, 0.0D, (16D - BOX_INSET) / 16D, 9D / 16D, 4D / 16D, (16D - BOX_INSET) / 16D),
            new AABB(BOX_INSET / 16D, 0.0D, (16D - BOX_INSET) / 16D, 9D / 16D, 8D / 16D, (16D - BOX_INSET) / 16D),
            new AABB(BOX_INSET / 16D, 0.0D, (16D - BOX_INSET) / 16D, 9D / 16D, 15D / 16D, (16D - BOX_INSET) / 16D)};
    private static final Supplier<MobEffectInstance> TRAPPED_EFFECT_GETTER = () -> new MobEffectInstance(MobEffectRegistry.TRAPPED.asHolder(),
            10,
            0
    );
    private final int cooldownTime;
    private final int responseTime;
    private final float attackDamage;

    public TrapBlock(Properties properties, int cooldownTime, int responseTime, float attackDamage) {
        super(properties);
        this.cooldownTime = cooldownTime;
        this.responseTime = responseTime;
        this.attackDamage = attackDamage;

    }

    @SuppressWarnings("deprecation")
    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return direction == Direction.DOWN && !state.canSurvive(
                level,
                pos
        ) ? Blocks.AIR.defaultBlockState() : super.updateShape(
                state,
                level,
                scheduledTickAccess,
                pos,
                direction,
                neighborPos,
                neighborState,
                random
        );
    }

    // This and updateNeighbours are used to make sure that neighboring traps are
    // updated when this one is broken
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState p_49319_, Level p_49320_, BlockPos p_49321_, BlockState p_49322_, boolean p_49323_) {
        if (!p_49323_ && !p_49319_.is(p_49322_.getBlock())) {
            this.updateNeighbours(p_49320_, p_49321_);
            super.onRemove(p_49319_, p_49320_, p_49321_, p_49322_, p_49323_);
        }
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockState belowState = level.getBlockState(pos.below());
        float destroySpeedRaw = belowState.getDestroySpeed(level, pos);
        boolean isCorrectToolForBelow = false; // TODO

        // If it's the right tool and the trap is fully open
        if (isCorrectToolForBelow && state.getValue(STAGE) == MIN_STAGE) {
            // Hide/unhide the trap.
            if (level instanceof ServerLevel serverLevel) {
                // ModPacketHandler.sendToAllClients(new DestroyEffectsPacket(pos, belowState,
                // 2));
                level.levelEvent(null, LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(belowState));
                // Utilities.addParticlesAroundPositionServer(serverLevel, pos.getCenter(), new
                // BlockParticleOption(ParticleTypes.BLOCK, state), 1.0, 20);
                level.setBlockAndUpdate(pos, state.setValue(HIDDEN, !state.getValue(HIDDEN)));
            }
            return InteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean canSurvive(BlockState p_49325_, LevelReader p_49326_, BlockPos p_49327_) {
        BlockPos blockpos = p_49327_.below();
        return canSupportRigidBlock(p_49326_, blockpos) || canSupportCenter(p_49326_, blockpos, Direction.UP);
    }

    @Override
    public VoxelShape getShape(BlockState p_56620_, BlockGetter p_56621_, BlockPos p_56622_, CollisionContext p_56623_) {
        return SHAPE[p_56620_.getValue(STAGE)];
    }

    // Schedule a tick on random ticks.
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.scheduleTick(new BlockPos(pos), this, this.getCooldownTime());
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        // Check if there are any entities in the block. If so, trigger the trap.

        // First, get the stage of the trap.
        int stage = state.getValue(STAGE);

        // Get the collision shape.
        AABB shape = TOUCH_SHAPE[stage].move(pos);

        // Get all the entities inside the collision shape.
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, shape);

        // See if there are any entities there at all.
        if (entities == null || entities.size() == 0) {
            // If there are no entities, shrink if powered by redstone.
            if (level.hasNeighborSignal(pos)) {
                int newStage = Math.max(stage - 1, MIN_STAGE);
                state = state.setValue(STAGE, newStage);
                stage = newStage;
            }

        } else {
            // Otherwise, trigger.
            state = state.setValue(STAGE, MAX_STAGE);
            stage = MAX_STAGE;

            // Now damage all the entities if you haven't triggered before.
            if (!state.getValue(SHRINKING)) {
                // If it is hidden, display burst particles.
                if (state.getValue(HIDDEN)) {
                    level.addDestroyBlockEffect(pos, level.getBlockState(pos.below()));
                    level.addDestroyBlockEffect(pos, level.getBlockState(pos.below()));
                }

                // Play the snap sound
                level.playSound(null, pos, SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.BLOCKS);
                state = state.setValue(SHRINKING, true);
                for (Entity entity : entities) {
                    Holder<DamageType> type = DamageSourceRegistry.get(
                            level.registryAccess(),
                            DamageSourceRegistry.BRIAR
                    );
                    DamageSource source = new DamageSource(type, (Entity) null);
                    entity.hurtServer(level, source, this.attackDamage);
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addEffect(TRAPPED_EFFECT_GETTER.get());
                    }
                }
            }

        }

        // Schedule another tick if it's not at minimum growth.
        if (stage > MIN_STAGE) {
            level.scheduleTick(new BlockPos(pos), this, this.getCooldownTime());
        } else {

            // If it is at minimum growth, stop shrinking.
            state = state.setValue(SHRINKING, false);
        }

        // Set the block.
        level.setBlock(pos, state, 3);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        // If there is an entity inside, schedule a tick.
        if (!level.isClientSide) {
            // Only schedule a tick if the block is not currently shrinking.
            if (!state.getValue(SHRINKING)) {
                level.scheduleTick(new BlockPos(pos), this, this.getResponseTime());
            } else if (state.getValue(STAGE) == MIN_STAGE) {
                level.setBlock(pos, state.setValue(SHRINKING, false), 3);
            }
        }
        // If the trap is fully closed, trap the entities.
        if (state.getValue(STAGE) == MAX_STAGE) {
            if (entity instanceof LivingEntity le && le.hasEffect(MobEffectRegistry.TRAPPED.asHolder())) {
                le.makeStuckInBlock(state, new Vec3(0.01f, 0.01f, 0.01f));
                // Only on mod-5 ticks to reduce flickering.
                if (!level.isClientSide && 0 == entity.tickCount % 5) {
                    le.addEffect(TRAPPED_EFFECT_GETTER.get());
                }
            }
        }

    }

    protected int getCooldownTime() {
        return this.cooldownTime;
    }

    protected int getResponseTime() {
        return this.responseTime;
    }

    protected void updateNeighbours(Level p_49292_, BlockPos p_49293_) {
        p_49292_.updateNeighborsAt(p_49293_, this);
        p_49292_.updateNeighborsAt(p_49293_.below(), this);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(HIDDEN, false);
    }

    // Respawning in a trap is bad.
    @Override
    public boolean isPossibleToRespawnInThis(BlockState p_279155_) {
        return false;
    }

    // Very important!
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54447_) {
        p_54447_.add(STAGE, SHRINKING, FACING, HIDDEN);
    }

}
