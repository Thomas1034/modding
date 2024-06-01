package com.thomas.verdant.overgrowth;

import java.util.HashMap;
import java.util.Map;

import com.google.common.primitives.Ints;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class OvergrowthProgression {
	private static final String NAME = "overgrowth_stages";
	private Map<Integer, Integer> stages = OvergrowthProgression.newMap();

	public static final OvergrowthProgression DEFAULT = new OvergrowthProgression();

	public void saveNBTData(CompoundTag nbt) {
		nbt.putIntArray(NAME, Ints.toArray(this.stages.values()));
	}

	private static Map<Integer, Integer> newMap() {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		map.put(1, 0);
		map.put(2, 1);
		map.put(3, 1);
		map.put(4, 1);
		map.put(5, 1);
		map.put(6, 1);
		map.put(7, 1);
		map.put(8, 1);

		return map;
	}

	public void loadNBTData(CompoundTag nbt) {
		// System.out.println("Looking for " + NAME);
		int[] values = nbt.getIntArray(NAME);
		// System.out.println("Loaded " + values);
		// System.out.println("Length is " + values.length);
		for (int i = 0; i < Math.min(EntityOvergrowth.STAGES, values.length); i++) {
			// System.out.println("Value " + i + " is " + values[i]);
			// System.out.println("Stages are " + this.stages);
			// System.out.println("This stage is " + this.stages.get(i));
			this.stages.put(i, values[i]);
		}
		// System.out.println("Loaded successfully.");
	}

	public int getStage(int stage) {
		// System.out.println("Stage is: " + stage);
		// System.out.println("Result of getting: " + this.stages.get(stage));
		return this.stages.getOrDefault(stage, 0);
	}

	public void setStage(int stage, int speed) {
		// System.out.println("Setting stage for " + stage + " to " + speed);
		this.stages.put(stage, speed);
	}

	public void setAllStages(int speed) {
		// System.out.println("In setter.");
		for (Integer key : this.stages.keySet()) {
			// System.out.println("Setting stage for " + key);
			this.setStage(key, speed);
		}
	}

	public void resetAllStages() {
		this.stages = OvergrowthProgression.newMap();
	}

	public static int getStage(Level level, int stage) {
		return level.getCapability(OvergrowthProgressionProvider.OVERGROWTH_PROGRESSION).orElseGet(() -> DEFAULT)
				.getStage(stage);
	}

	public static void setStage(Level level, int stage, int speed) {
		level.getCapability(OvergrowthProgressionProvider.OVERGROWTH_PROGRESSION)
				.ifPresent(stages -> stages.setStage(stage, speed));
	}

	public static void setAllStages(Level level, int speed) {
		// System.out.println("Setting all stages.");
		level.getCapability(OvergrowthProgressionProvider.OVERGROWTH_PROGRESSION).ifPresent(stages -> {
			// System.out.println("In lambda.");
			stages.setAllStages(speed);
		});
	}

	public static void resetAllStages(Level level) {
		// System.out.println("Setting all stages.");
		level.getCapability(OvergrowthProgressionProvider.OVERGROWTH_PROGRESSION).ifPresent(stages -> {
			// System.out.println("In lambda.");
			stages.resetAllStages();
		});
	}

}
