package com.thomas.verdant.modfeature;

public class WeightedFeature {
	private final Feature feature;
	private final int weight;
	private final String name;

	public WeightedFeature(Feature feature, int weight) {
		this.feature = feature;
		this.weight = weight;
		this.name = null;
	}
	
	public WeightedFeature(Feature feature, int weight, String name) {
		this.feature = feature;
		this.weight = weight;
		this.name = name;
	}

	public Feature feature() {
		return this.feature;
	}

	public int weight() {
		return this.weight;
	}
	
	@Override
	public String toString() {
		return (this.name == null ? this.feature : this.name) + ":" + this.weight;
	}
}