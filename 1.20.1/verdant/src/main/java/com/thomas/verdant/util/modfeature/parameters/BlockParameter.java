package com.thomas.verdant.util.modfeature.parameters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockParameter extends Parameter<Block> {

	public BlockParameter(String field) {
		super(field);
	}

	@Override
	public Block readFrom(JsonElement parameters) throws MissingFieldException, FieldMismatchException {
		if (!(parameters instanceof JsonObject)) {
			throw this.missingField();
		}
		JsonObject jsonObject = (JsonObject) parameters;

		// Get the block field.
		JsonElement blockElement = jsonObject.get("block");

		// Ensure it's a string.
		if (blockElement == null) {
			throw this.missingField();
		} else if (blockElement instanceof JsonPrimitive blockPrimitive && blockPrimitive.isString()) {
			// Get the block as a string.
			String blockName = blockPrimitive.getAsString();
			// Look up the block
			ResourceLocation blockLocation = new ResourceLocation(blockName);
			Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
			// Return the block.
			return block;
		} else {
			throw this.fieldMismatch();
		}
	}

}
