package com.thomas.verdant.block.entity;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class VerdantHeartBlockEntity extends BlockEntity {

	private List<BeaconBeamSection> beamSections = Lists.newArrayList();
	private boolean isActive = false;
	private static final float[] BASE_TINT = new float[] { 0.25f, 1.0f, 0.15f };
	private static final float BASE_LUMINANCE = luminance(BASE_TINT);

	public VerdantHeartBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.VERDANT_HEART_BLOCK_ENTITY.get(), pos, state);
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
		VerdantHeartBlockEntity verdantHeart = (VerdantHeartBlockEntity) be;
		// System.out.println("Ticking at " + pos);
		if (level.canSeeSky(pos.above())) {
			verdantHeart.setActive();
		} else {
			verdantHeart.setInactive();
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
