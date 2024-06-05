package com.thomas.verdant.util.modfeature.checkers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.thomas.verdant.util.data.DataParseable;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class AbstractCheckerType implements DataParseable<AbstractCheckerType> {

	public abstract boolean check(Level level, BlockPos pos);

	public static Block getBlock(JsonElement parameters) {

		if (!(parameters instanceof JsonObject)) {
			throw new JsonParseException(
					"Expected parameters to be a JsonObject, was instead: " + parameters.getClass());
		}
		JsonObject jsonObject = (JsonObject) parameters;

		// Get the block field.
		JsonElement blockElement = jsonObject.get("block");

		// Ensure it's a string.
		if (blockElement == null) {
			throw new JsonParseException("Missing required field block.");
		} else if (blockElement instanceof JsonPrimitive blockPrimitive && blockPrimitive.isString()) {
			// Get the block as a string.
			String blockName = blockPrimitive.getAsString();
			// Look up the block
			ResourceLocation blockLocation = new ResourceLocation(blockName);
			Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
			// Return the block.
			return block;
		} else {
			throw new JsonParseException("Field block must be a String.");
		}
	}

	public static Map<Property<?>, Object> getBlockState(JsonElement parameters, Block block) {
		Map<Property<?>, Object> properties = new HashMap<>();

		// First off, ensure it's an object.
		if (!(parameters instanceof JsonObject)) {
			throw new JsonParseException(
					"Expected parameters to be a JsonObject, was instead: " + parameters.getClass() + ".");
		}
		JsonObject jsonObject = (JsonObject) parameters;

		// Get the state field.
		// Get the block field.
		JsonElement stateElement = jsonObject.get("state");

		// Ensure it's a string.
		if (stateElement == null) {
			throw new JsonParseException("Missing required field state.");
		} else if (stateElement instanceof JsonObject stateObject) {

			// Now, read each element of the array and construct the state.
			Map<String, JsonElement> stateMap = stateObject.asMap();

			// Get all the properties this block has.
			Collection<Property<?>> blockProperties = block.defaultBlockState().getProperties();
			// Get their types by their names.
			Map<String, Property<?>> propertyTypes = new HashMap<>();
			// Fill the map.
			for (Property<?> prop : blockProperties) {
				propertyTypes.put(prop.getName(), prop);
			}

			for (Entry<String, JsonElement> entry : stateMap.entrySet()) {
				JsonElement jsonValue = entry.getValue();
				String propertyName = entry.getKey();
				// Ensure the block has this property.
				if (!(propertyTypes.containsKey(propertyName))) {
					throw new JsonParseException(
							"Block " + block + " does not have the property " + propertyName + ".");
				}

				// Ensure it's a single value.
				if (jsonValue instanceof JsonPrimitive primitive) {

					// Special-case all of the valid types.
					if (primitive.isString()) {
						// Ensure that this matches.
						String value = primitive.getAsString();
						Property<?> property = propertyTypes.get(propertyName);
						// Get the enum value of this property.
						if (property instanceof EnumProperty<?> enumProperty) {
							Enum<?> enumValue = enumProperty.getValue(value).orElseThrow();
							// Now finally add it.
							properties.put(property, enumValue);
						} else {
							throw new JsonParseException(
									"Block " + block + " does not have the enum property " + propertyName + ".");
						}

					} else if (primitive.isNumber()) {
						Number numberValue = primitive.getAsNumber();
						// Ensure the number is an integer.
						if (!(numberValue.doubleValue() == numberValue.intValue())) {
							throw new JsonParseException("Numerical block state property must be an integer.");
						}
						// Convert the number to an integer.
						Integer value = numberValue.intValue();

						// Ensure that this property is indeed an integer property.
						Property<?> property = propertyTypes.get(propertyName);

						if (property instanceof IntegerProperty intProperty) {

							// Check that the value is in range.
							if (!intProperty.getPossibleValues().contains(value)) {
								throw new JsonParseException("Value " + value
										+ " is not within range for integer property " + propertyName + ".");
							}
							// Add the value now.
							properties.put(property, value);

						} else {
							throw new JsonParseException(
									"Block " + block + " does not have the integer property " + propertyName + ".");
						}

					} else if (primitive.isBoolean()) {

						// Ensure that this property is indeed an integer property.
						Property<?> property = propertyTypes.get(propertyName);

						if (property instanceof BooleanProperty) {
							properties.put(property, primitive.getAsBoolean());
						} else {
							throw new JsonParseException(
									"Block " + block + " does not have the boolean property " + propertyName + ".");
						}
					} else {
						throw new JsonParseException(
								"Block state property must be either a String, an integer, or a boolean.");
					}

				} else {
					throw new JsonParseException("Block state property is not a primitive.");
				}

			}

		} else {
			throw new JsonParseException("Field state must be an object.");
		}

		return properties;
	}

	public static int[] getOffset(JsonElement parameters) {

		int[] offset = new int[3];

		if (!(parameters instanceof JsonObject)) {
			throw new JsonParseException(
					"Expected parameters to be a JsonObject, was instead: " + parameters.getClass());
		}
		JsonObject jsonObject = (JsonObject) parameters;

		// Get the x field.
		JsonElement xElement = jsonObject.get("x");

		// Ensure it's a number or not present.
		if (xElement == null) {
			// Do nothing if not present.
			offset[0] = 0;
		} else if (xElement instanceof JsonPrimitive xPrimitive && xPrimitive.isNumber()) {
			Number numberValue = xPrimitive.getAsNumber();
			// Ensure the number is an integer.
			if (!(numberValue.doubleValue() == numberValue.intValue())) {
				throw new JsonParseException("Offset must be an integer.");
			}
			// Set the offset array.
			offset[0] = numberValue.intValue();
		} else {
			throw new JsonParseException("Offset must be an integer.");
		}

		// Get the y field.
		JsonElement yElement = jsonObject.get("y");

		// Ensure it's a number or not present.
		if (yElement == null) {
			// Do nothing if not present.
			offset[0] = 0;
		} else if (yElement instanceof JsonPrimitive yPrimitive && yPrimitive.isNumber()) {
			Number numberValue = yPrimitive.getAsNumber();
			// Ensure the number is an integer.
			if (!(numberValue.doubleValue() == numberValue.intValue())) {
				throw new JsonParseException("Offset must be an integer.");
			}
			// Set the offset array.
			offset[0] = numberValue.intValue();
		} else {
			throw new JsonParseException("Offset must be an integer.");
		}

		// Get the z field.
		JsonElement zElement = jsonObject.get("z");

		// Ensure it's a number or not present.
		if (yElement == null) {
			// Do nothing if not present.
			offset[0] = 0;
		} else if (zElement instanceof JsonPrimitive zPrimitive && zPrimitive.isNumber()) {
			Number numberValue = zPrimitive.getAsNumber();
			// Ensure the number is an integer.
			if (!(numberValue.doubleValue() == numberValue.intValue())) {
				throw new JsonParseException("Offset must be an integer.");
			}
			// Set the offset array.
			offset[0] = numberValue.intValue();
		} else {
			throw new JsonParseException("Offset must be an integer.");
		}

		return offset;
	}
}
