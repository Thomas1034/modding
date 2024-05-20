package com.thomas.verdant.util.block_transformers;

import java.util.Map.Entry;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public class BlockTransformerAccessor implements Supplier<BlockTransformer> {

	private final ResourceLocation location;

	public BlockTransformerAccessor(ResourceLocation location) {
		this.location = location;
		//System.out.println("Requesting block transformer: " + location);
		//System.out.println("Block transformers are: ");
		//for (Entry<ResourceLocation, BlockTransformer> entry : BlockTransformer.TRANSFORMERS.entrySet()) {
		//	System.out.println(entry.getKey());
		//}
	}

	@Override
	public BlockTransformer get() {
		return BlockTransformer.get(this.location);
	}

}
