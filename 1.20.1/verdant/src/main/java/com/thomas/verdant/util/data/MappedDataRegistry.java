package com.thomas.verdant.util.data;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Function;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;

public class MappedDataRegistry<S, T extends DataParseable<T>> extends DataRegistry<T> {

	private final Map<S, T> extraMap;
	private final Function<T, S> keyGetter;

	public MappedDataRegistry(ResourceLocation name, Function<ResourceLocation, T> factory, Function<T, S> keyGetter) {
		super(name, factory);
		this.extraMap = new HashMap<>();
		this.keyGetter = keyGetter;
	}

	public MappedDataRegistry(String modid, String name, Function<ResourceLocation, T> factory,
			Function<T, S> keyGetter) {
		this(new ResourceLocation(modid, name), factory, keyGetter);
	}
	


	public boolean hasMappedKey(S key) {
		return this.extraMap.containsKey(key);
	}


	public T getMappedValue(S key) {
		return this.extraMap.get(key);
	}

	public DataAccessor<T> registerMapped(ResourceLocation key, S s, T value) {
		// System.out.println("In " + this.name + ": registering new key " + key);
		this.extraMap.put(s, value);
		return super.register(key, value);
	}

	public void clear() {
		this.extraMap.clear();
	}

	@Override
	public void onReload(AddReloadListenerEvent event) {
		// System.out.println("Adding listener here!");
		event.addListener(new MappedDataParserListener<S, T>(this, this.name.getPath(), this.factory, this.keyGetter));
	}

}
