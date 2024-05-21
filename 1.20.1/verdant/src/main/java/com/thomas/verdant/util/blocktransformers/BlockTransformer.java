package com.thomas.verdant.util.blocktransformers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.thomas.verdant.util.data.DataParseable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

// Represents an abstract way of transforming one block into another, preserving as much of the state as possible.
public class BlockTransformer implements DataParseable<BlockTransformer> {

	protected static final Map<ResourceLocation, BlockTransformer> TRANSFORMERS = new HashMap<>();

	public Map<Block, Block> map;
	private final ResourceLocation name;
	private Set<Block> inputs;
	private Set<Block> outputs;

	// Gets a transformer with the given name and namespace from a loaded JSON file
	protected static BlockTransformer get(ResourceLocation name) {
		return TRANSFORMERS.get(name);
	}

	// Gets a transformer with the given name and namespace from a loaded JSON file
	protected static BlockTransformer get(String namespace, String filename) {
		return TRANSFORMERS.get(new ResourceLocation(namespace, filename));
	}

	// Creates a transformer with the given name and namespace.
	// The file would be located at: data/namespace/block_transformers/filename
	public BlockTransformer(ResourceLocation name) {
		this.reset();
		this.name = name;
	}

	// Creates a transformer with the given name and namespace.
	// The file would be located at: data/namespace/block_transformers/filename
	protected BlockTransformer(String namespace, String filename) {
		this.reset();
		this.name = new ResourceLocation(namespace, filename);
	}

	public ResourceLocation getLocation() {
		return this.name;
	}

	// Sets the transformer's fields to null, indicating it is not in use.
	public void nullify() {
		this.map = new HashMap<>();
		this.inputs = new HashSet<>();
		this.outputs = new HashSet<>();
	}

	// Wipes the transformer, so new blocks can be added.
	public void reset() {
		this.map = new HashMap<>();
		this.inputs = new HashSet<>();
		this.outputs = new HashSet<>();
	}

	// Adds a block to the transformer.
	public BlockTransformer register(Block a, Block b) {
		System.out.println("Registering " + this.name + ": " + ForgeRegistries.BLOCKS.getKey(a) + " -> "
				+ ForgeRegistries.BLOCKS.getKey(b));
		this.inputs.add(a);
		this.outputs.add(b);
		this.map.put(a, b);

		return this;
	}

	public boolean hasInput(Block a) {
		return this.inputs.contains(a);
	}

	public boolean hasOutput(Block a) {
		return this.outputs.contains(a);
	}

	// Returns the converted block from the map, or null.
	public Block next(Block a) {
		return this.map.get(a);
	}

	// Returns the converted block state, with as many properties as possible copied
	// over. If the block is not present in the map, the old state will be returned.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BlockState next(BlockState oldState) {

		Block next = this.next(oldState.getBlock());

		if (next != null) {
			BlockState newState = next.defaultBlockState();
			for (Property prop : oldState.getProperties()) {
				if (newState.hasProperty(prop)) {
					newState = newState.trySetValue(prop, oldState.getValue(prop));
				}
			}
			return newState;
		} else {
			return oldState;
		}
	}

	// Parses the transformer from a JSON.
	@SuppressWarnings("unchecked")
	@Override
	public BlockTransformer parse(Gson gson, JsonElement element) {
		// Get the contents of the JSON.
		Map<String, String> contents = gson.fromJson(element, Map.class);

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
			this.register(startBlock, finishBlock);
		}
		return this;
	}

}
