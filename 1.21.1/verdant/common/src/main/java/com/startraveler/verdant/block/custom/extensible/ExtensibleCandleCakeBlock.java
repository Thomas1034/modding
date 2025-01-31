package com.startraveler.verdant.block.custom.extensible;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class ExtensibleCandleCakeBlock extends AbstractCandleBlock {
    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
    protected static final float AABB_OFFSET = 1.0F;
    protected static final VoxelShape CAKE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape CANDLE_SHAPE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
    protected static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);
    private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

    private final Supplier<Block> baseCake;

    public ExtensibleCandleCakeBlock(Block candle, Supplier<Block> baseCake, Properties properties) {
        // TODO This still overwrites the existing candle cake. Fix by reimplementing...
        // everything.
        super(properties);
        this.baseCake = baseCake;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(false)));
    }

    private static boolean candleHit(BlockHitResult hit) {
        return hit.getLocation().y - (double) hit.getBlockPos().getY() > 0.5;
    }

    @Override
    protected MapCodec<? extends AbstractCandleBlock> codec() {
        throw new IllegalStateException("Codecs aren't implementing yet.");
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState p_152868_) {
        return PARTICLE_OFFSETS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152905_) {
        p_152905_.add(LIT);
    }

    @Override
    protected boolean isPathfindable(BlockState p_152870_, PathComputationType p_152873_) {
        return false;
    }

    @Override
    protected BlockState updateShape(BlockState p_152898_, LevelReader p_374136_, ScheduledTickAccess p_374358_, BlockPos p_152902_, Direction p_152899_, BlockPos p_152903_, BlockState p_152900_, RandomSource p_374518_) {
        return p_152899_ == Direction.DOWN && !p_152898_.canSurvive(
                p_374136_,
                p_152902_
        ) ? Blocks.AIR.defaultBlockState() : super.updateShape(
                p_152898_,
                p_374136_,
                p_374358_,
                p_152902_,
                p_152899_,
                p_152903_,
                p_152900_,
                p_374518_
        );
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState p_316519_, Level p_316226_, BlockPos p_316122_, Player p_316438_, BlockHitResult p_316849_) {
        InteractionResult interactionresult = ((ExtensibleCakeBlock) this.baseCake.get()).eatCustom(
                p_316226_,
                p_316122_,
                this.baseCake.get().defaultBlockState(),
                p_316438_
        );
        if (interactionresult.consumesAction()) {
            dropResources(p_316519_, p_316226_, p_316122_);
        }

        return interactionresult;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack p_316571_, BlockState p_316514_, Level p_316171_, BlockPos p_316112_, Player p_316172_, InteractionHand p_316257_, BlockHitResult p_316286_) {
        if (p_316571_.is(Items.FLINT_AND_STEEL) || p_316571_.is(Items.FIRE_CHARGE)) {
            return InteractionResult.PASS;
        } else if (candleHit(p_316286_) && p_316571_.isEmpty() && p_316514_.getValue(LIT)) {
            extinguish(p_316172_, p_316514_, p_316171_, p_316112_);
            return InteractionResult.SUCCESS;
        } else {
            return super.useItemOn(p_316571_, p_316514_, p_316171_, p_316112_, p_316172_, p_316257_, p_316286_);
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState p_152909_) {
        return true;
    }

    @Override
    protected boolean canSurvive(BlockState p_152891_, LevelReader p_152892_, BlockPos p_152893_) {
        return p_152892_.getBlockState(p_152893_.below()).isSolid();
    }

    @Override
    protected int getAnalogOutputSignal(BlockState p_152880_, Level p_152881_, BlockPos p_152882_) {
        return CakeBlock.FULL_CAKE_SIGNAL;
    }

    @Override
    protected VoxelShape getShape(BlockState p_152875_, BlockGetter p_152876_, BlockPos p_152877_, CollisionContext p_152878_) {
        return SHAPE;
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader p_304662_, BlockPos p_152863_, BlockState p_152864_, boolean p_387122_) {
        return new ItemStack(this.baseCake.get());
    }

}
