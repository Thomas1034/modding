package com.thomas.verdant.modfeature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import com.thomas.verdant.util.function.PentaPredicate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FeatureSet implements Iterable<FeatureType> {

	private final List<FeatureType> types = new ArrayList<>();
	private final String name;

	public FeatureSet() {
		this.name = "feature_set";
	}

	public FeatureSet(String name) {
		this.name = name;
	}

	public FeatureType create(BiPredicate<Level, BlockPos> condition, String name) {
		FeatureType type = new FeatureType(condition, (level, pos, above, at, below) -> condition.test(level, pos),
				name);
		this.types.add(type);
		return type;
	}

	public FeatureType create(BiPredicate<Level, BlockPos> condition,
			PentaPredicate<Level, BlockPos, BlockState, BlockState, BlockState> extendedCondition, String name) {
		FeatureType type = new FeatureType(condition, extendedCondition, name);
		this.types.add(type);
		return type;
	}

	public Iterable<FeatureType> iterate() {
		return this.types;
	}

	@Override
	public Iterator<FeatureType> iterator() {
		return this.types.iterator();
	}

	public Stream<FeatureType> stream() {
		return this.types.stream();
	}

	@Override
	public String toString() {
		return this.name + ":" + this.types.size();
	}

}
