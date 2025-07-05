package com.startraveler.verdant.block.custom;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;
import com.startraveler.verdant.mixin.PrimedTntAccessors;
import com.startraveler.verdant.util.CommonTags;
import com.startraveler.verdant.util.XFactHDShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class BombPileBlock extends Block {

    public static final int MIN_BOMBS = 1;
    public static final int MAX_BOMBS = 8;
    public static final IntegerProperty BOMBS = IntegerProperty.create("bombs", MIN_BOMBS, MAX_BOMBS);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final Table<Integer, Direction, VoxelShape> SHAPES = ArrayTable.create(
            BOMBS.getPossibleValues(),
            FACING.getPossibleValues()
    );
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;
    public static final int NORMAL_FUSE = 20;
    protected final Predicate<ItemStack> canIncreaseSize;

    public BombPileBlock(Properties properties, Predicate<ItemStack> canIncreaseSize) {
        super(properties);
        this.canIncreaseSize = canIncreaseSize;
        this.registerDefaultState(this.getStateDefinition().any().setValue(BOMBS, MIN_BOMBS).setValue(UNSTABLE, false));
    }


    public static boolean prime(BlockState state, Level level, BlockPos pos) {
        return prime(state, level, pos, null);
    }

    private static boolean prime(BlockState state, Level level, BlockPos pos, @Nullable LivingEntity entity) {
        if (level instanceof ServerLevel serverlevel) {
            if (serverlevel.getGameRules().getBoolean(GameRules.RULE_TNT_EXPLODES)) {
                PrimedTnt bomb = new PrimedTnt(
                        level,
                        (double) pos.getX() + (double) 0.5F,
                        (double) pos.getY(),
                        (double) pos.getZ() + (double) 0.5F,
                        entity
                );
                ((PrimedTntAccessors) bomb).setExplosionPower(state.getValue(BOMBS) * 2);
                bomb.setBlockState(state);
                bomb.setFuse(NORMAL_FUSE);
                level.addFreshEntity(bomb);
                level.playSound(
                        (Entity) null,
                        bomb.getX(),
                        bomb.getY(),
                        bomb.getZ(),
                        SoundEvents.TNT_PRIMED,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                );
                level.gameEvent(entity, GameEvent.PRIME_FUSE, pos);
                return true;
            }
        }

        return false;
    }

    // Make NeoForge happy TODO replace with mixin to FireBlock?
    public boolean onCaughtFire(BlockState state, Level level, BlockPos pos, @org.jetbrains.annotations.Nullable Direction direction, @Nullable LivingEntity igniter) {
        return prime(state, level, pos, igniter);
    }

    @Override
    protected void neighborChanged(BlockState p_57457_, Level p_57458_, BlockPos p_57459_, Block p_57460_, @Nullable Orientation p_364510_, boolean p_57462_) {
        if (p_57458_.hasNeighborSignal(p_57459_) && prime(p_57457_, p_57458_, p_57459_)) {
            p_57458_.removeBlock(p_57459_, false);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock())) {
            if (level.hasNeighborSignal(pos) && prime(state, level, pos)) {
                level.removeBlock(pos, false);
            }
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        int numBombs = state.getValue(BOMBS);
        Item item = stack.getItem();
        if (numBombs < MAX_BOMBS && this.canIncreaseSize.test(stack)) {
            level.setBlockAndUpdate(pos, state.setValue(BOMBS, numBombs + 1));
            player.awardStat(Stats.ITEM_USED.get(item));
            stack.consume(1, player);
            return InteractionResult.SUCCESS;
        } else if (!stack.is(Items.FLINT_AND_STEEL) && !stack.is(Items.FIRE_CHARGE) && !stack.is(CommonTags.Items.TOOLS_IGNITER)) {
            return super.useItemOn(stack, state, level, pos, player, hand, result);
        } else {
            if (prime(state, level, pos, player)) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
                if (stack.is(Items.FLINT_AND_STEEL)) {
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                } else {
                    stack.consume(1, player);
                }

                player.awardStat(Stats.ITEM_USED.get(item));
            } else if (level instanceof ServerLevel serverLevel) {
                if (!serverLevel.getGameRules().getBoolean(GameRules.RULE_TNT_EXPLODES)) {
                    player.displayClientMessage(Component.translatable("block.minecraft.tnt.disabled"), true);
                    return InteractionResult.PASS;
                }
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState attachedTo = level.getBlockState(pos.below());
        return attachedTo.isFaceSturdy(level, pos, Direction.UP) && super.canSurvive(state, level, pos);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int count = state.getValue(BOMBS);
        Direction facing = state.getValue(FACING);
        VoxelShape shape = SHAPES.get(count, facing);
        if (shape == null) {
            shape = makeShape(count);
            shape = XFactHDShapeUtils.rotateShapeAroundY(Direction.NORTH, facing, shape);
            SHAPES.put(count, facing, shape);
        }
        return shape;
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (level instanceof ServerLevel serverlevel) {
            BlockPos blockpos = hit.getBlockPos();
            Entity entity = projectile.getOwner();
            if (projectile.isOnFire()
                    && projectile.mayInteract(serverlevel, blockpos)
                    && prime(state, level, blockpos, entity instanceof LivingEntity ? (LivingEntity) entity : null)) {
                level.removeBlock(blockpos, false);
            }
        }
    }

    @Override
    public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
        if (level.getGameRules().getBoolean(GameRules.RULE_TNT_EXPLODES)) {
            PrimedTnt bomb = new PrimedTnt(
                    level,
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    explosion.getIndirectSourceEntity()
            );
            bomb.setFuse(1);
            level.addFreshEntity(bomb);
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        return this.canSurvive(state, context.getLevel(), context.getClickedPos()) ? state : null;
    }

    @Override
    public BlockState playerWillDestroy(Level p_57445_, BlockPos p_57446_, BlockState p_57447_, Player p_57448_) {
        if (!p_57445_.isClientSide() && !p_57448_.getAbilities().instabuild && p_57447_.getValue(UNSTABLE)) {
            prime(p_57447_, p_57445_, p_57446_);
        }

        return super.playerWillDestroy(p_57445_, p_57446_, p_57447_, p_57448_);
    }

    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BOMBS, UNSTABLE);
    }

    public VoxelShape makeShape(int number) {

        VoxelShape shape = Shapes.empty();
        switch (number) {
            case 1:
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.5, 0.75), BooleanOp.OR);
                break;
            case 2:
                shape = Shapes.join(shape, Shapes.box(0, 0, 0.125, 0.5, 0.5, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.375, 1, 0.5, 0.875), BooleanOp.OR);
                break;
            case 3:
                shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.5, 1, 0.5, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 0.5, 0.5, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0, 0.75, 0.5, 0.5), BooleanOp.OR);
                break;
            case 4:
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.5, 1), BooleanOp.OR);
                break;
            case 5:
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.5, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.5, 0.25, 0.75, 1, 0.75), BooleanOp.OR);
                break;
            case 6:
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.5, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5, 0, 0.9375, 1, 0.5), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.5, 0.5, 0.5625, 1, 1), BooleanOp.OR);
                break;
            case 7:
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.5, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.5, 0.5, 0.75, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5, 0.5, 0, 1, 1, 0.5), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.5, 0, 0.5, 1, 0.5), BooleanOp.OR);
                break;
            case 8:
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 1, 1), BooleanOp.OR);
                break;
        }

        return shape;
    }

}
