package com.thomas.verdant.util.itemtonumber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thomas.verdant.util.data.DataParseable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class ItemToNumberMap implements DataParseable<ItemToNumberMap> {

	public Map<Item, Double> map;
	public Map<TagKey<Item>, Double> tagMap;
	private final ResourceLocation name;
	private Set<TagKey<Item>> inputTags;
	private Set<Item> inputs;
	private Set<Double> outputs;

	// Creates a transformer with the given name and namespace.
	// The file would be located at: data/namespace/Item_transformers/filename
	public ItemToNumberMap(ResourceLocation name) {
		this.reset();
		this.name = name;
	}

	// Creates a transformer with the given name and namespace.
	// The file would be located at: data/namespace/Item_transformers/filename
	protected ItemToNumberMap(String namespace, String filename) {
		this.reset();
		this.name = new ResourceLocation(namespace, filename);
	}

	public ResourceLocation getLocation() {
		return this.name;
	}

	// Wipes the transformer, so new Items can be added.
	public void reset() {
		this.map = new HashMap<>();
		this.tagMap = new HashMap<>();
		this.inputs = new HashSet<>();
		this.inputTags = new HashSet<>();
		this.outputs = new HashSet<>();
	}

	// Adds a Item to the transformer.
	public ItemToNumberMap register(Item a, Double b) {
		this.inputs.add(a);
		this.outputs.add(b);
		this.map.put(a, b);

		return this;
	}

	// Adds a Item tag to the transformer.
	public ItemToNumberMap register(TagKey<Item> a, Double b) {
		this.inputTags.add(a);
		this.outputs.add(b);
		this.tagMap.put(a, b);

		return this;
	}

	public boolean hasInput(Item a) {
		return this.inputs.contains(a);
	}

	public boolean hasInput(TagKey<Item> a) {
		return this.inputTags.contains(a);
	}

	public boolean hasOutput(Double a) {
		return this.outputs.contains(a);
	}

	// Returns the converted Item from the map, or null.
	// If the Item was not found directly, checks for tags that might contain it.
	public Double get(Item a) {
		Double retval = this.map.get(a);

		if (retval != null) {
			return retval;
		}

		for (TagKey<Item> tag : this.inputTags) {
			if (a.getDefaultInstance().is(tag)) {
				return this.tagMap.get(tag);
			}
		}

		return null;
	}

	// Parses the transformer from a JSON.
	@SuppressWarnings("unchecked")
	@Override
	public ItemToNumberMap parse(Gson gson, JsonElement element) {
		// Get the contents of the JSON.
		Map<String, Number> contents = gson.fromJson(element, Map.class);

		// For each element, find it in the Item registry and load it into the
		// transformer.
		for (Entry<String, Number> itemPair : contents.entrySet()) {
			// Get the strings.
			String key = itemPair.getKey();
			Double value = itemPair.getValue().doubleValue();

			// Convert to namespaces.
			String[] keyParts = key.split(":");

			// Check if it is a tag
			boolean isTag = keyParts[0].startsWith("#");
			if (isTag) {
				keyParts[0] = keyParts[0].substring(1);
			}

			ResourceLocation keyLocation = new ResourceLocation(keyParts[0], keyParts[1]);

			// Check if the start is a Item or a tag.
			if (isTag) {
				// It is a tag.
				// Get the tag it corresponds to.
				@SuppressWarnings("deprecation")
				ITag<Item> keyTag = ForgeRegistries.ITEMS.tags()
						.getTag(new TagKey<Item>(ForgeRegistries.ITEMS.getRegistryKey(), keyLocation));

				// Add the Items to the transformer.
				this.register(keyTag.getKey(), value);
			} else {
				// It is a Item.
				// Now, get the Items
				Item keyItem = ForgeRegistries.ITEMS.getValue(keyLocation);
				// Add the Items to the transformer.
				this.register(keyItem, value);
			}

		}
		return this;
	}

	// @Override
	public JsonObject write(JsonObject element) {
		for (Entry<Item, Double> entry : this.map.entrySet()) {
			element.addProperty(name(entry.getKey()).toString(), entry.getValue().toString());
		}
		for (Entry<TagKey<Item>, Double> entry : this.tagMap.entrySet()) {
			element.addProperty("#" + entry.getKey().location(), entry.getValue().toString());
		}

		return element;
	}

	private final ResourceLocation name(Item Item) {
		return ForgeRegistries.ITEMS.getKey(Item);
	}

}
