package com.thomas.verdant.util.block_transformers;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockTransformerLoaderListener extends SimpleJsonResourceReloadListener {
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

	public BlockTransformerLoaderListener() {
		super(GSON, "block_transformers");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void apply(Map<ResourceLocation, JsonElement> inputMap, ResourceManager resourceManager,
			ProfilerFiller profiler) {
		// System.out.println("There are " + inputMap.size() + " elements to check.");
		// Look over every transformer.
		for (Entry<ResourceLocation, JsonElement> entry : inputMap.entrySet()) {
			// Get the location; this will be the name of the transformer.
			ResourceLocation location = entry.getKey();
			// Get the element; this will be the contents of the transformer.
			JsonElement element = entry.getValue();
			// System.out.println("Loading a new block transformer: " + location);

			if (location.getPath().startsWith("_"))
				continue; // Forge: filter anything beginning with "_" as it's used for metadata.

			try {
				// Get the contents of the JSON.
				Map<String, String> contents = GSON.fromJson(element, Map.class);

				// Create a new BlockTransformer
				BlockTransformer transformer = new BlockTransformer(location);

				// For each element, find it in the block registry and load it into the
				// transformer.
				for (Entry<String, String> blockPair : contents.entrySet()) {
					// Get the strings.
					String start = blockPair.getKey();
					String finish = blockPair.getValue();

					// Convert to namespaces.
					String[] startParts = start.split(":");
					ResourceLocation startLocation = new ResourceLocation(startParts[0], startParts[1]);
					String[] finishParts = finish.split(":");
					ResourceLocation finishLocation = new ResourceLocation(finishParts[0], finishParts[1]);

					// Now, get the blocks
					Block startBlock = ForgeRegistries.BLOCKS.getValue(startLocation);
					Block finishBlock = ForgeRegistries.BLOCKS.getValue(finishLocation);

					// Add the blocks to the transformer.
					transformer.register(startBlock, finishBlock);
				}

				// Register the transformer.
				BlockTransformer.TRANSFORMERS.put(location, transformer);

			} catch (IllegalArgumentException | JsonParseException jpe) {
				System.out.println("Parsing error loading json " + location);
				jpe.printStackTrace();
			}
		}

	}

}
