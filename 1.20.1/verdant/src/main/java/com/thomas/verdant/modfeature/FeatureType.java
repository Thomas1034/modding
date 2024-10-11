package com.thomas.verdant.modfeature;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import com.thomas.verdant.util.function.PentaPredicate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FeatureType implements Iterable<WeightedFeature> {
	private final HashSet<WeightedFeature> holders = new HashSet<>();
	private final BiPredicate<Level, BlockPos> condition;
	private final PentaPredicate<Level, BlockPos, BlockState, BlockState, BlockState> extendedCondition;
	private final String name;
	private int totalWeight;

	public FeatureType(BiPredicate<Level, BlockPos> condition,
			PentaPredicate<Level, BlockPos, BlockState, BlockState, BlockState> extendedCondition, String name) {
		this.condition = condition;
		this.extendedCondition = extendedCondition;
		this.name = name;
		this.totalWeight = 0;
	}

	public boolean checkForPlacement(Level level, BlockPos pos) {
		return this.condition.test(level, pos);
	}

	public boolean checkForPlacement(Level level, BlockPos pos, BlockState above, BlockState at, BlockState below) {
		return this.extendedCondition.test(level, pos, above, at, below);
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