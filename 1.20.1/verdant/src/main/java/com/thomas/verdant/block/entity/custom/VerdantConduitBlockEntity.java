package com.thomas.verdant.block.entity.custom;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.thomas.verdant.block.custom.VerdantConduitBlock;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.util.ModTags;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags.Blocks;

public class VerdantConduitBlockEntity extends BlockEntity {

	private List<BeaconBeamSection> beamSections = Lists.newArrayList();
	private boolean isActive = false;
	private static final float[] BASE_TINT = new float[] { 0.25f, 1.0f, 0.15f };
	private static final float BASE_LUMINANCE = luminance(BASE_TINT);
	private static final int BASE_GROWTH_RANGE = 3;
	private static final int MAX_GROWTH_BONUS = 15;
	private int bonusGrowthRange = 0;

	public VerdantConduitBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.VERDANT_HEART_BLOCK_ENTITY.get(), pos, state);
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
		VerdantConduitBlockEntity verdantHeart = (VerdantConduitBlockEntity) be;
		int bonusGrowth = verdantHeart.bonusGrowthRange;
		state = updateActivity(level, pos, state, verdantHeart);
		if (level instanceof ClientLevel clientLevel) {
			updateBeamSections(clientLevel, pos, state, verdantHeart);
		} else if (level instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 2 * bonusGrowth * bonusGrowth + 8; i++) {
				grow(serverLevel, pos, state, verdantHeart, BASE_GROWTH_RANGE);
				doRandomTick(serverLevel, pos, state, verdantHeart, BASE_GROWTH_RANGE);
			}
			applyEffect(serverLevel, pos, state, verdantHeart, BASE_GROWTH_RANGE + MAX_GROWTH_BONUS,
					() -> new MobEffectInstance(ModMobEffects.VERDANT_ENERGY.get(), 600, 0));
		}
	}

	private static BlockState updateActivity(Level level, BlockPos pos, BlockState state,
			VerdantConduitBlockEntity verdantHeart) {
		if (level.canSeeSky(pos.above()) && hasValidBase(level, pos)) {
			verdantHeart.setActive();
			if (level instanceof ServerLevel && !state.getValue(VerdantConduitBlock.ACTIVE)) {
				state = state.setValue(VerdantConduitBlock.ACTIVE, true);
				level.setBlockAndUpdate(pos, state);
			}
		} else {
			verdantHeart.setInactive();
			if (level instanceof ServerLevel && state.getValue(VerdantConduitBlock.ACTIVE)) {
				state = state.setValue(VerdantConduitBlock.ACTIVE, false);
				level.setBlockAndUpdate(pos, state);
			}
		}

		return state;
	}

	@SuppressWarnings("deprecation")
	private static void doRandomTick(ServerLevel level, BlockPos pos, BlockState state,
			VerdantConduitBlockEntity verdantHeart, int radius) {
		if (!verdantHeart.isActive) {
			return;
		}
		// The range to tick blocks in.
		BlockPos posToTry = VerdantGrower.withinSphereDist(pos, radius + verdantHeart.bonusGrowthRange, level.random);
		// Give the block a random tick.
		// First get the new state
		BlockState newState = level.getBlockState(posToTry);
		Block newBlock = newState.getBlock();
		// Run the tick.
		newBlock.randomTick(newState, level, posToTry, level.random);
	}

	private static void applyEffect(ServerLevel level, BlockPos pos, BlockState state,
			VerdantConduitBlockEntity verdantHeart, int radius, Supplier<MobEffectInstance> effect) {

		if (!verdantHeart.isActive) {
			return;
		}

		AABB boxToCheck = AABB.ofSize(Vec3.atLowerCornerOf(pos), 1 + 2 * radius, 1 + 2 * radius, 1 + 2 * radius);
		List<Entity> toCheck = level.getEntities(null, boxToCheck);
		int radiusSqr = radius * radius;
		Vec3 center = Vec3.atCenterOf(pos);

		// Iterate and check
		for (Entity e : toCheck) {
			if (e instanceof LivingEntity le) {
				// Distance check.
				if (le.distanceToSqr(center.x, center.y, center.z) < radiusSqr) {
					// Add the effect.
					le.addEffect(effect.get());
				}
			}
		}

	}

	private static void grow(ServerLevel level, BlockPos pos, BlockState state, VerdantConduitBlockEntity verdantHeart,
			int radius) {

		if (!verdantHeart.isActive) {
			return;
		}
		// The range to convert blocks in.
		BlockPos posToTry = VerdantGrower.withinSphereDist(pos, radius + verdantHeart.bonusGrowthRange, level.random);

		// Try to erode the block, then try to convert it.
		// Try to convert the nearby block.
		// On failure, increase the range to try.
		boolean didErode = VerdantGrower.erodeStatic(level, posToTry, false);
		boolean didConvert = VerdantGrower.convertGround(level, posToTry, false);
		if (!didErode && !didConvert) {
			if (verdantHeart.bonusGrowthRange < MAX_GROWTH_BONUS) {
				verdantHeart.bonusGrowthRange++;
			}
		}
		// On success, decrease it.
		else {
			verdantHeart.bonusGrowthRange = 0;
		}

	};

	private static void updateBeamSections(ClientLevel level, BlockPos pos, BlockState state,
			VerdantConduitBlockEntity verdantHeart) {
		// System.out.println("Updating beam sections at " + pos);
		if (!verdantHeart.isActive()) {
			// System.out.println("Resetting sections");
			verdantHeart.beamSections = Lists.newArrayList();
			return;
		}

		// Get the maximum build height.
		int maxHeight = level.getMaxBuildHeight();

		// Iterate up from the heart, setting the tint as each block is passed.
		int currentHeight = pos.above().getY();
		BlockPos.MutableBlockPos currentPos = new BlockPos.MutableBlockPos().set(pos.above());
		float[] previousTint = BASE_TINT;
		float[] currentTint;
		BeaconBeamSection section = new BeaconBeamSection(BASE_TINT);
		// System.out.println("Resetting sections");
		verdantHeart.beamSections = Lists.newArrayList();

		while (currentHeight < maxHeight) {
			BlockState currentState = level.getBlockState(currentPos);
			// Get the current block's color multiplier.
			currentTint = currentState.getBeaconColorMultiplier(level, currentPos, pos);

			if (currentTint == null || compareTints(previousTint, currentTint)) {
				section.increaseHeight();
				currentHeight++;
				currentPos.move(Direction.UP);
				continue;
			}

			// Calculate the new tint.
			float[] newTint = new float[] { previousTint[0] * currentTint[0], previousTint[1] * currentTint[1],
					previousTint[2] * currentTint[2] };

			// Get the luminance of the new tint
			float newLuminance = luminance(newTint);

			// Scale up the new tint;
			newTint[0] = Math.min(1.0f, newTint[0] * BASE_LUMINANCE / newLuminance);
			newTint[1] = Math.min(1.0f, newTint[1] * BASE_LUMINANCE / newLuminance);
			newTint[2] = Math.min(1.0f, newTint[2] * BASE_LUMINANCE / newLuminance);

			// Add the old section to the list.
			// System.out.println("Adding section: " + section);
			verdantHeart.beamSections.add(section);

			// Make a new section, with the new tint.
			section = new BeaconBeamSection(newTint);

			// Update the previous tint.
			previousTint = newTint;
			// Update the coordinates.
			currentHeight++;
			currentPos.move(Direction.UP);
		}
		// System.out.println("Adding section: " + section);
		verdantHeart.beamSections.add(section);
	}

	private static boolean hasValidBase(Level level, BlockPos pos) {

		// First, check to see if it has a square of verdant heartwood logs underneath
		// it.
		BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();

		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				checkPos.set(pos.getX() + i, pos.getY() - 1, pos.getZ() + k);
				if (!level.getBlockState(checkPos).is(ModTags.Blocks.MATURE_VERDANT_LOGS)) {
					// System.out.println("Failing due to lack of logs at " + checkPos);
					// System.out.println("Relative " + i + " " + k);
					return false;
				}
			}
		}

		// Now, check for the emerald blocks underneath.
		if (!level.getBlockState(pos.offset(1, -2, 0)).is(Blocks.STORAGE_BLOCKS_EMERALD)) {
			// System.out.println("Failing due to lack of emerald in positive x direction");
			return false;
		}
		if (!level.getBlockState(pos.offset(-1, -2, 0)).is(Blocks.STORAGE_BLOCKS_EMERALD)) {
			// System.out.println("Failing due to lack of emerald in negative x direction");
			return false;
		}
		if (!level.getBlockState(pos.offset(0, -2, 1)).is(Blocks.STORAGE_BLOCKS_EMERALD)) {
			// System.out.println("Failing due to lack of emerald in positive z direction");
			return false;
		}
		if (!level.getBlockState(pos.offset(0, -2, -1)).is(Blocks.STORAGE_BLOCKS_EMERALD)) {
			// System.out.println("Failing due to lack of emerald in negative z direction");
			return false;
		}

		return true;
	}

	private void setInactive() {
		this.isActive = false;
	}

	private void setActive() {
		this.isActive = true;
	}

	public boolean isActive() {
		return this.isActive;
	}

	// To make it render when offscreen
	@Override
	public AABB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@SuppressWarnings("unchecked")
	public List<BeaconBeamSection> getBeamSections() {
		return (List<BeaconBeamSection>) (!this.isActive ? ImmutableList.of() : this.beamSections);
	}

	private static boolean compareTints(float[] previous, float[] current) {
		// If lengths are not equal, arrays cannot be identical
		if (previous.length != current.length) {
			return false;
		}

		// Compare each element
		for (int i = 0; i < previous.length; i++) {
			if (previous[i] != current[i]) {
				return false;
			}
		}

		return true;
	}

	private static float luminance(float[] tint) {
		return tint[0] * 0.2126f + tint[1] * 0.7152f + tint[2] * 0.0722f;
	}

	public static class BeaconBeamSection {
		final float[] color;
		private int height;

		public BeaconBeamSection(float[] tint) {
			this.color = tint;
			this.height = 1;
		}

		protected void increaseHeight() {
			++this.height;
		}

		public float[] getColor() {
			return this.color;
		}

		public int getHeight() {
			return this.height;
		}
	}

}
