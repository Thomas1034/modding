package com.thomas.verdant.util.block_transformers.copy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

// Represents an abstract way of transforming one block into another, preserving as much of the state as possible.
public class BlockTransformer {

	protected static final Map<ResourceLocation, BlockTransformer> TRANSFORMERS = new HashMap<>();

	protected Map<Block, Block> map;
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
	protected BlockTransformer(ResourceLocation name) {
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
}
