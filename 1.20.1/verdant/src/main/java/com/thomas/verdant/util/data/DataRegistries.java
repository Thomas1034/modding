package com.thomas.verdant.util.data;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.util.blocktransformers.BlockTransformer;

public class DataRegistries {

	
	public static final DataRegistry<BlockTransformer> BLOCK_TRANSFORMERS = new DataRegistry<BlockTransformer>(
			Verdant.MOD_ID, "block_transformers", BlockTransformer::new);
	
	
	
	// Accessed to ensure that the class is loaded early in the mod's lifecycle.
	public static void ensureAlive() {
		
	}
}
