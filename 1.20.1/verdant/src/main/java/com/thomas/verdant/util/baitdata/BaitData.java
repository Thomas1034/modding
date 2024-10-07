package com.thomas.verdant.util.baitdata;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.thomas.verdant.util.data.DataParseable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class BaitData implements DataParseable<BaitData> {

	private Item item;
	private double catchChance;
	private double consumeChance;
	private final ResourceLocation name;
	private boolean readOnly;

	public static final String ITEM_STRING = "item";
	public static final String CATCH_STRING = "catchChance";
	public static final String CONSUME_STRING = "consumeChance";

	public BaitData(ResourceLocation name) {
		this.name = name;
		this.readOnly = false;
	}
	
	public Item getItem() {
		return this.item;
	}

	public double getCatchChance() {
		return this.catchChance;
	}

	public double getConsumeChance() {
		return this.consumeChance;
	}
	
	
	@Override
	public BaitData parse(Gson gson, JsonElement element) {
		if (element.isJsonObject()) {
			JsonObject jsonObject = element.getAsJsonObject();
			Item item = this.fromResourceString(jsonObject.get(ITEM_STRING).getAsString());
			double catchChance = jsonObject.get(CATCH_STRING).getAsNumber().doubleValue();
			double consumeChance = jsonObject.get(CONSUME_STRING).getAsNumber().doubleValue();

			if (null == item) {
				return null;
			}

			return this.setData(item, catchChance, consumeChance);
		}

		return null;
	}

	public BaitData setData(Item item, double catchChance, double consumeChance) {

		if (this.readOnly) {
			throw new UnsupportedOperationException("This object is in read-only mode.");
		}

		this.readOnly = true;
		this.item = item;
		this.catchChance = catchChance;
		this.consumeChance = consumeChance;

		return this;
	}

	private Item fromResourceString(String resourceString) {
		return ForgeRegistries.ITEMS.getValue(new ResourceLocation(resourceString));
	}

	@Override
	public JsonObject write(JsonObject element) {
		element.add(ITEM_STRING, new JsonPrimitive(ForgeRegistries.ITEMS.getKey(this.item).toString()));
		element.add(CATCH_STRING, new JsonPrimitive(this.catchChance));
		element.add(CONSUME_STRING, new JsonPrimitive(this.consumeChance));

		return element;
	}

	public ResourceLocation getLocation() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.write(new JsonObject()).toString();
	}
}
