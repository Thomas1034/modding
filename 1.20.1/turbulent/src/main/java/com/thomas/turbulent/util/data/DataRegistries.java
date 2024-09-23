package com.thomas.turbulent.util.data;

import com.thomas.turbulent.Turbulent;
import com.thomas.turbulent.util.blocktransformers.BlockTransformer;

public class DataRegistries {

	public static final DataRegistry<BlockTransformer> BLOCK_TRANSFORMERS = new DataRegistry<>(Turbulent.MOD_ID,
			"block_transformers", BlockTransformer::new);

	// Accessed to ensure that the class is loaded early in the mod's lifecycle.
	public static void ensureAlive() {

	}
}
