package com.thomas.zirconmod.block.custom;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.thomas.zirconmod.block.entity.ModBlockEntities;
import com.thomas.zirconmod.block.entity.ResonatorBlockEntity;
import com.thomas.zirconmod.util.ModTags;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ResonatorBlock extends BaseEntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty CHARGE = BlockStateProperties.RESPAWN_ANCHOR_CHARGES;
	public static final int RANGE = 15;

	public ResonatorBlock(Properties properties) {
		super(properties);
	}

	// Render with a standard model.
	@Override
	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.MODEL;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> blockEntityType) {
		return (blockEntityType == ModBlockEntities.RESONATOR.get()) ? this::tick : null;
	}

	private void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (blockEntity instanceof ResonatorBlockEntity resonator) {
			int signalStrength = level.getBestNeighborSignal(pos);
			int charge = state.getValue(CHARGE);
			// Checking if the signal strength is nonzero.
			if (signalStrength > 0) {
				// Check if can fire.
				if (!resonator.isPowered() && charge > 0 && resonator.getCooldown() <= 0) {
					if (level instanceof ServerLevel sl) {
						// Fire!
						// First get the box to damage entities in.
						AABB firingBox = getFiringBox(sl, pos, state.getValue(FACING), RANGE);
						// Then get all the entities in that box.
						List<LivingEntity> entities = sl.getEntitiesOfClass(LivingEntity.class, firingBox);

						// Damage and knockback each one.
						for (LivingEntity entity : entities) {
							entity.hurt(entity.damageSources().sonicBoom(null), 5);
							entity.addDeltaMovement(new Vec3(state.getValue(FACING).step()).scale(0.5).add(0, 0.05, 0));
						}

						// Decrease the charge.
						level.setBlockAndUpdate(pos, state.setValue(CHARGE, charge - 1));
						// Set the cooldown.
						resonator.updateCooldown(20);
						resonator.setPowered(true);
						// Spawn particles.
						spawnFiringParticles(sl, pos, state.getValue(FACING), RANGE);
						sl.playSound(null, pos, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 1.0f, 1.0f);
					}
				}
			} else {
				// Decrease the cooldown, when it isn't powered.
				resonator.setPowered(false);
			}
			resonator.decreaseCooldown();

		}
	}

	private static AABB getFiringBox(Level level, BlockPos pos, Direction dir, int maxRange) {

		BlockPos inFront = pos.relative(dir);
		int distance = 0;
		// Iterate until non-air, non-water block is reached.
		while (distance < maxRange && (level.isWaterAt(inFront) || level.getBlockState(inFront).isAir())) {
			inFront = inFront.relative(dir);
			distance++;
		}

		return new AABB(pos.relative(dir)).inflate(dir.getStepX() * distance, dir.getStepY() * distance,
				dir.getStepZ() * distance);
	}

	private static void spawnFiringParticles(ServerLevel level, BlockPos pos, Direction dir, int maxRange) {

		BlockPos inFront = pos.relative(dir);
		int distance = 0;
		// Iterate until non-air, non-water block is reached.
		while (distance < maxRange && (level.isWaterAt(inFront) || level.getBlockState(inFront).isAir())) {
			level.sendParticles(ParticleTypes.SONIC_BOOM, inFront.getX() + 0.5, inFront.getY() + 0.5,
					inFront.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.0);
			inFront = inFront.relative(dir);
			distance++;
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		// TODO Auto-generated method stub
		return new ResonatorBlockEntity(pos, state);

	}

	@Override
	public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos,
			@Nullable Direction direction) {
		return true;
	}

	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction playerFacing = context.getHorizontalDirection().getOpposite();
		return this.defaultBlockState().setValue(FACING, playerFacing);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult result) {
		if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && !player.isCrouching()) {

			// Checks if the player is holding echo powder.
			if (player.getItemInHand(hand).is(ModTags.Items.SCULK_AWAKENING_ITEMS)) {

				// Gets the current charge.
				int currentCharge = state.getValue(CHARGE);

				// If the block is currently charged to the maximum, does nothing.
				if (currentCharge == 4) {
					return super.use(state, level, pos, player, hand, result);
				}

				// Sets the new charge level to full.
				level.setBlock(pos, state.setValue(CHARGE, 4), 2);

				// Takes an item from the player if not in creative.
				if(!player.getAbilities().instabuild) player.getMainHandItem().shrink(1);

				return InteractionResult.CONSUME;
			}
		}
		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54447_) {
		p_54447_.add(FACING);
		p_54447_.add(CHARGE);
	}
}
