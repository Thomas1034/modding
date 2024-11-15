package com.thomas.verdant.util.data;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public class MappedDataParserListener<S, T extends DataParseable<T>> extends DataParserListener<T> {

	private final Function<T, S> mapKeyGetter;

	public MappedDataParserListener(MappedDataRegistry<S, T> registry, String folder,
			Function<ResourceLocation, T> factory, Function<T, S> getMapKey) {
		super(registry, folder, factory);
		this.mapKeyGetter = getMapKey;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void apply(Map<ResourceLocation, JsonElement> inputMap, ResourceManager resourceManager,
			ProfilerFiller profiler) {

		// Erase the existing data
		this.registry.clear();

		// Look over every element, and load it in.
		for (Entry<ResourceLocation, JsonElement> entry : inputMap.entrySet()) {
			// Get the location; this will be the name of the transformer.
			ResourceLocation location = entry.getKey();
			// Get the element; this will be the contents of the transformer.
			JsonElement element = entry.getValue();
			// System.out.println("Loading a new block transformer: " + location);

			if (location.getPath().startsWith("_"))
				continue; // Forge: filter anything beginning with "_" as it's used for metadata.

			try {
				// Register the new registry object.
				T t = factory.apply(location).parse(GSON, element);
				if (null == t) {
					throw new NullPointerException("Unable to register " + location + ", element is null.");
				}
				// Get the map key.
				S s = mapKeyGetter.apply(t);
				if (null == s) {
					throw new NullPointerException("Unable to register " + location + ", key is null.");
				}
				if (this.registry instanceof MappedDataRegistry mappedRegistry) {
					mappedRegistry.registerMapped(location, s, t);
				} else {
					throw new NullPointerException("Registry is not a mapped registry: " + this.registry.getName());
				}

			} catch (IllegalArgumentException | JsonParseException jpe) {
				System.out.println("Parsing error loading json " + location);
				jpe.printStackTrace();
			}
		}

	}

}
