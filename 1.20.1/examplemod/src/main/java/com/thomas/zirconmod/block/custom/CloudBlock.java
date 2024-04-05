package com.thomas.zirconmod.block.custom;

import java.util.List;
import java.util.OptionalInt;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.entity.ModEntityType;
import com.thomas.zirconmod.entity.custom.GustEntity;
import com.thomas.zirconmod.util.ModTags;
import com.thomas.zirconmod.util.Utilities;
import com.thomas.zirconmod.worldgen.dimension.ModDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CloudBlock extends Block {

	public static final int MAX_DISTANCE = 15;
	public static final int MIN_DISTANCE = 0;

	public static final IntegerProperty SOLIDIFIER_DISTANCE = IntegerProperty.create("distance", MIN_DISTANCE,
			MAX_DISTANCE);

	// private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D,
	// 0.0D, 0.0D, 1.0D, (double) 0.9F, 1.0D);

	// private static final int TICK_DELAY = 1;

	public CloudBlock(Properties properties) {
		super(properties);
		
		this.registerDefaultState(this.stateDefinition.any().setValue(SOLIDIFIER_DISTANCE, MAX_DISTANCE));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean skipRendering(BlockState here, BlockState there, Direction p_53974_) {
		return (there.is(this) && ((here.getValue(SOLIDIFIER_DISTANCE) < MAX_DISTANCE
				&& (there.getValue(SOLIDIFIER_DISTANCE) < MAX_DISTANCE))
				|| (here.getValue(SOLIDIFIER_DISTANCE) == MAX_DISTANCE
						&& (there.getValue(SOLIDIFIER_DISTANCE) == MAX_DISTANCE))) ? true
								: super.skipRendering(here, there, p_53974_));
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathType) {
		/*
		 * boolean isSolid = (state.getValue(SOLIDIFIER_DISTANCE) < MAX_DISTANCE);
		 * switch (pathType) { case LAND: return !isSolid; case WATER: return !isSolid;
		 * case AIR: return !isSolid; default: return false; }
		 */
		return false;// pathType == PathComputationType.AIR && !(state.getValue(SOLIDIFIER_DISTANCE)
						// < MAX_DISTANCE);
	}

	// Causes no fall damage when landed on.
	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float distance) {
		// Creates particles instead of doing fall damage.
		Utilities.addParticlesAroundPosition(level, entity.position(), ParticleTypes.CLOUD, 1.0f);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext entitycollisioncontext) {
			Entity entity = entitycollisioncontext.getEntity();
			// if (entity.fallDistance > 2.5F) {
			// return FALLING_COLLISION_SHAPE;
			// }

			boolean isSolid = state.getValue(SOLIDIFIER_DISTANCE) < MAX_DISTANCE;

			if (isSolid || canEntityWalkOnCloud(entity)) {
				return Shapes.block();
			}
		}

		return Shapes.empty();
	}

	public static boolean canEntityWalkOnCloud(Entity entity) {
		if (entity == null) {
			return false;
		}

		if (entity.getType().is(ModTags.EntityTypes.CLOUD_WALKABLE_MOBS) || entity instanceof ItemEntity) {
			return true;

		} else {
			return entity instanceof LivingEntity
					? ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).is(ModTags.Items.CLOUD_WALKABLE_ITEMS)
							|| ((LivingEntity) entity).hasEffect(ModEffects.CITRINE_GLOW.get())
					: false;
		}
	}

	@Override
	public void tick(BlockState p_221369_, ServerLevel p_221370_, BlockPos p_221371_, RandomSource p_221372_) {
		p_221370_.setBlock(p_221371_, updateDistance(p_221369_, p_221370_, p_221371_), 3);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		int i = getDistanceAt(level, otherPos, otherState) + 1;
		if (i != 1 || state.getValue(SOLIDIFIER_DISTANCE) != i) {
			level.scheduleTick(pos, this, 1);
		}

		return state;
	}

	@Override
	public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
		ItemStack stack = player.getMainHandItem();
		boolean hasSilkTouchEnchantment = stack.getEnchantmentLevel(Enchantments.SILK_TOUCH) > 0;
		return (player.getMainHandItem().is(ModTags.Items.CLOUD_HARVEST_ITEMS) || hasSilkTouchEnchantment)
				&& super.canHarvestBlock(state, level, pos, player);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
		return p_49928_.getFluidState().isEmpty();
	}

	public static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int dist = MAX_DISTANCE;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		// Should be less laggy, but doesn't count corners.
		for (Direction direction : Direction.values()) {
			blockpos$mutableblockpos.setWithOffset(pos, direction);
			dist = Math.min(dist,
					getDistanceAt(level, blockpos$mutableblockpos, level.getBlockState(blockpos$mutableblockpos)) + 1);
			if (dist == 1) {
				break;
			}
		}

		return state.setValue(SOLIDIFIER_DISTANCE, Integer.valueOf(dist));
	}

	protected static int getDistanceAt(LevelAccessor level, BlockPos pos, BlockState state) {
		return getOptionalDistanceAt(state).orElse(MAX_DISTANCE);
	}

	private static OptionalInt getOptionalDistanceAt(BlockState state) {
		if (state.is(ModTags.Blocks.CLOUD_SOLIDIFYING_BLOCKS)) {
			int s = Math.max(MAX_DISTANCE - getSolidifyingStrength(state), 0);
			return OptionalInt.of(s);
		} else {
			return state.hasProperty(SOLIDIFIER_DISTANCE) ? OptionalInt.of(state.getValue(SOLIDIFIER_DISTANCE))
					: OptionalInt.empty();
		}
	}

	// Gets the projection distance that a block will solidify clouds for.
	// Checks the tags for solidifying strength.
	// If the block is not recognized, returns the maximum amount.
	public static int getSolidifyingStrength(BlockState state) {
		if (state.is(ModTags.Blocks.STRONG_CLOUD_SOLIDIFYING_BLOCKS)) {
			return MAX_DISTANCE;
		} else if (state.is(ModTags.Blocks.MEDIUM_CLOUD_SOLIDIFYING_BLOCKS)) {
			return (MAX_DISTANCE + 1) / 2;
		} else if (state.is(ModTags.Blocks.WEAK_CLOUD_SOLIDIFYING_BLOCKS)) {
			return (MAX_DISTANCE + 1) / 4;
		}
		return MAX_DISTANCE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_54424_) {
		BlockState blockstate = this.defaultBlockState().setValue(SOLIDIFIER_DISTANCE, MAX_DISTANCE);
		return updateDistance(blockstate, p_54424_.getLevel(), p_54424_.getClickedPos());
	}

	@SuppressWarnings("deprecation")
	public static boolean placeCloud(LevelAccessor level, BlockPos pos) {
		if (!level.getBlockState(pos).isAir() || !level.isAreaLoaded(pos, 4))
			return false;
		BlockState blockstate = ModBlocks.CLOUD.get().defaultBlockState().setValue(SOLIDIFIER_DISTANCE, MAX_DISTANCE);
		blockstate = updateDistance(blockstate, level, pos);
		level.setBlock(pos, blockstate, 3);
		return true;
	}

	@Override
	public boolean isValidSpawn(BlockState state, BlockGetter level, BlockPos pos, SpawnPlacements.Type type,
			EntityType<?> entityType) {
		return (state.getValue(SOLIDIFIER_DISTANCE) < MAX_DISTANCE)
				|| entityType.is(ModTags.EntityTypes.CLOUD_SPAWNABLE_MOBS)
				|| entityType.is(ModTags.EntityTypes.CLOUD_WALKABLE_MOBS);
	}

	// If the space above it is air, spawns a gust with a very small probability.
	// Assuming the game rules don't specify otherwise.
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		Difficulty difficulty = level.getDifficulty();
		boolean doSpawning = level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING);
		if (doSpawning && difficulty != Difficulty.PEACEFUL && level.isRaining()
				&& level.getBlockState(pos.above()).isAir() && level.dimension() == ModDimensions.SKY_DIM_LEVEL_KEY) {
			float chance = difficulty.getId() * 0.001f;
			if (rand.nextFloat() < chance) {
				List<GustEntity> entities = level.getEntitiesOfClass(GustEntity.class,
						AABB.ofSize(pos.getCenter(), 16, 64, 16));
				if (entities.size() < 1) {
					GustEntity gust = new GustEntity(ModEntityType.GUST_ENTITY.get(), level);
					gust.moveTo(pos.above().getCenter());
					gust.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 1, 1));
					level.addFreshEntity(gust);
				}
			}
		}
	}

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing,
			net.minecraftforge.common.IPlantable plantable) {
		return (facing == Direction.UP && plantable.getPlant(world, pos).is(ModTags.Blocks.CLOUD_PLANTS));
	}

	// Very important!
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54447_) {
		p_54447_.add(SOLIDIFIER_DISTANCE);
	}

}
