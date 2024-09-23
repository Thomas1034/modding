package com.thomas.turbulent.util.blocktransformers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thomas.turbulent.util.data.DataParseable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

// Represents an abstract way of transforming one block into another, preserving as much of the state as possible.
public class BlockTransformer implements DataParseable<BlockTransformer> {

	public Map<Block, Block> map;
	public Map<TagKey<Block>, Block> tagMap;
	private final ResourceLocation name;
	private Set<TagKey<Block>> inputTags;
	private Set<Block> inputs;
	private Set<Block> outputs;

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

	// Wipes the transformer, so new blocks can be added.
	public void reset() {
		this.map = new HashMap<>();
		this.tagMap = new HashMap<>();
		this.inputs = new HashSet<>();
		this.inputTags = new HashSet<>();
		this.outputs = new HashSet<>();
	}

	// Adds a block to the transformer.
	public BlockTransformer register(Block a, Block b) {
		// System.out.println("Registering " + this.name + ": " +
		// ForgeRegistries.BLOCKS.getKey(a) + " -> "
		// + ForgeRegistries.BLOCKS.getKey(b));
		this.inputs.add(a);
		this.outputs.add(b);
		this.map.put(a, b);

		return this;
	}

	// Adds a block tag to the transformer.
	public BlockTransformer register(TagKey<Block> a, Block b) {
		// System.out
		// .println("Registering " + this.name + ": " + a.location() + " -> " +
		// ForgeRegistries.BLOCKS.getKey(b));
		this.inputTags.add(a);
		this.outputs.add(b);
		this.tagMap.put(a, b);

		return this;
	}

	public boolean hasInput(Block a) {
		return this.inputs.contains(a);
	}

	public boolean hasInput(TagKey<Block> a) {
		return this.inputTags.contains(a);
	}

	public boolean hasOutput(Block a) {
		return this.outputs.contains(a);
	}

	// Returns the converted block from the map, or null.
	// If the block was not found directly, checks for tags that might contain it.
	public Block next(Block a) {
		Block retval = this.map.get(a);

		if (retval != null) {
			return retval;
		}

		for (TagKey<Block> tag : this.inputTags) {
			if (a.defaultBlockState().is(tag)) {
				return this.tagMap.get(tag);
			}
		}

		return null;
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

			// Check if it is a tag
			boolean isTag = startParts[0].startsWith("#");
			if (isTag) {
				startParts[0] = startParts[0].substring(1);
			}

			ResourceLocation startLocation = new ResourceLocation(startParts[0], startParts[1]);
			String[] finishParts = finish.split(":");
			ResourceLocation finishLocation = new ResourceLocation(finishParts[0], finishParts[1]);

			// The finish block should be a block no matter what.
			Block finishBlock = ForgeRegistries.BLOCKS.getValue(finishLocation);

			// Check if the start is a block or a tag.
			if (isTag) {
				// It is a tag.
				// Get the tag it corresponds to.
				ITag<Block> startTag = ForgeRegistries.BLOCKS.tags()
						.getTag(new TagKey<Block>(ForgeRegistries.BLOCKS.getRegistryKey(), startLocation));

				// Add the blocks to the transformer.
				this.register(startTag.getKey(), finishBlock);
			} else {
				// It is a block.
				// Now, get the blocks
				Block startBlock = ForgeRegistries.BLOCKS.getValue(startLocation);
				// Add the blocks to the transformer.
				this.register(startBlock, finishBlock);
			}

		}
		return this;
	}

	// @Override
	public void write(JsonObject element) {
		for (Entry<Block, Block> entry : this.map.entrySet()) {
			element.addProperty(name(entry.getKey()).toString(), name(entry.getValue()).toString());
		}
		for (Entry<TagKey<Block>, Block> entry : this.tagMap.entrySet()) {
			element.addProperty("#" + entry.getKey().location(), name(entry.getValue()).toString());
		}
	}

	private final ResourceLocation name(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

}
