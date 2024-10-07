package com.thomas.verdant.util.data;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.util.baitdata.BaitData;
import com.thomas.verdant.util.blocktransformers.BlockTransformer;

public class DataRegistries {

	public static final DataRegistry<BlockTransformer> BLOCK_TRANSFORMERS = new DataRegistry<>(Verdant.MOD_ID,
			"block_transformers", BlockTransformer::new);
	public static final DataRegistry<BaitData> BAIT_DATA = new DataRegistry<>(Verdant.MOD_ID,
			"bait_data", BaitData::new);

	// public static final DataRegistry<AbstractPlacementType> PLACEMENT_TYPES = new
	// DataRegistry<>(Verdant.MOD_ID,
	// "mod_feature/placement_types", ModPlacementTypeLookup::lookup);

	// Accessed to ensure that the class is loaded early in the mod's lifecycle.
	public static void ensureAlive() {

	}
}
