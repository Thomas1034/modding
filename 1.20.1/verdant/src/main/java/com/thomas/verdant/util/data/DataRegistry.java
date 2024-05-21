package com.thomas.verdant.util.data;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;

public class DataRegistry<T extends DataParseable<T>> extends AbstractCollection<T> {

	private final ResourceLocation name;
	private final Map<ResourceLocation, T> contents;
	private final Function<ResourceLocation, T> factory;

	public DataRegistry(ResourceLocation name, Function<ResourceLocation, T> factory) {
		this.name = name;
		this.contents = new HashMap<>();
		this.factory = factory;
		//System.out.println("Creating a new data register " + name);
		MinecraftForge.EVENT_BUS.addListener(this::onReload);
	}

	public DataRegistry(String modid, String name, Function<ResourceLocation, T> factory) {
		this(new ResourceLocation(modid, name), factory);
	}

	@Override
	public Iterator<T> iterator() {
		return this.contents.values().iterator();
	}

	public Collection<T> values() {
		return this.contents.values();
	}

	public Collection<ResourceLocation> keys() {
		return this.contents.keySet();
	}

	@Override
	public int size() {
		return contents.size();
	}

	public ResourceLocation getKey(T value) {
		for (Entry<ResourceLocation, T> entry : this.contents.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public T getValue(ResourceLocation key) {
		return this.contents.get(key);
	}

	public DataAccessor<T> register(ResourceLocation key, T value) {
		//System.out.println("In " + this.name + ": registering new key " + key);
		this.contents.put(key, value);
		return new DataAccessor<T>(this, key);
	}

	public DataAccessor<T> register(ResourceLocation key) {
		return new DataAccessor<T>(this, key);
	}

	public DataAccessor<T> register(String namespace, String path) {
		return new DataAccessor<T>(this, new ResourceLocation(namespace, path));
	}

	public void clear() {
		this.contents.clear();
	}

	public ResourceLocation getName() {
		return name;
	}

	//@SubscribeEvent
	public void onReload(AddReloadListenerEvent event) {
		//System.out.println("Adding listener here!");
		event.addListener(new DataParserListener<T>(this, this.name.getPath(), this.factory));
	}

}
