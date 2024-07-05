package com.thomas.verdant.modfeature;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class FeatureType implements Iterable<WeightedFeature> {
	private final HashSet<WeightedFeature> holders = new HashSet<>();
	private final BiPredicate<Level, BlockPos> condition;
	private final String name;
	private int totalWeight;

	public FeatureType(BiPredicate<Level, BlockPos> condition, String name) {
		this.condition = condition;
		this.name = name;
		this.totalWeight = 0;
	}

	public boolean checkForPlacement(Level level, BlockPos pos) {
		return this.condition.test(level, pos);
	}

	public WeightedFeature addFeature(WeightedFeature holder) {
		this.holders.add(holder);
		this.totalWeight += holder.weight();
		return holder;
	}

	public WeightedFeature addFeature(Feature feature, int weight) {
		WeightedFeature holder = new WeightedFeature(feature, weight);
		this.addFeature(holder);
		return holder;
	}

	public Iterable<WeightedFeature> features() {
		return this.holders;
	}

	public int size() {
		return this.holders.size();
	}

	public String getName() {
		return this.name;
	}

	public int weight() {
		return this.totalWeight;
	}

	public Stream<WeightedFeature> stream() {
		return this.holders.stream();
	}

	@Override
	public Iterator<WeightedFeature> iterator() {
		return this.holders.iterator();
	}

	@Override
	public String toString() {
		return this.name + ":" + this.holders.size();
	}
}