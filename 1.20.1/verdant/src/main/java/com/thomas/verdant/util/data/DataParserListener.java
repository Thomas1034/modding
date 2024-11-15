package com.thomas.verdant.util.data;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class DataParserListener<T extends DataParseable<T>> extends SimpleJsonResourceReloadListener {
	protected static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	protected final DataRegistry<T> registry;
	protected final Function<ResourceLocation, T> factory;

	public DataParserListener(DataRegistry<T> registry, String folder, Function<ResourceLocation, T> factory) {
		super(GSON, folder);
		this.registry = registry;
		this.factory = factory;
	}

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
					System.out.println("Parsing error loading json " + location);
				}
				this.registry.register(location, t);

			} catch (IllegalArgumentException | JsonParseException jpe) {
				System.out.println("Parsing error loading json " + location);
				jpe.printStackTrace();
			}
		}

	}

}
