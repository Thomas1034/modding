package com.thomas.verdant.block.custom;

import net.minecraft.world.level.block.Block;

public class HoeRemovableItemBlock extends Block {

	// For cassava roots.
	// They'll grow under the plant, if it's in dirt.
	// You'll need to collect the plant and hoe the ground underneath it again
	// to collect the roots.
	public HoeRemovableItemBlock(Properties properties) {
		super(properties);
	}

}
