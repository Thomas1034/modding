package com.thomas.verdant.util.data;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public class DataAccessor<T extends DataParseable<T>> implements Supplier<T> {

	private final DataRegistry<T> registry;
	private final ResourceLocation resource;

	protected DataAccessor(DataRegistry<T> registry, ResourceLocation resource) {
		this.registry = registry;
		this.resource = resource;
	}

	@Override
	public T get() {
		return this.registry.getValue(this.resource);
	}

}
