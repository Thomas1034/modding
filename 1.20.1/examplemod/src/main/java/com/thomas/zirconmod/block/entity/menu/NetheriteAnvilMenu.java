package com.thomas.zirconmod.block.entity.menu;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;

public class NetheriteAnvilMenu extends AnvilMenu {

	public NetheriteAnvilMenu(int integer, Inventory inventory) {
		this(integer, inventory, ContainerLevelAccess.NULL);
	}

	public NetheriteAnvilMenu(int integer, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
		super(integer, inventory, containerLevelAccess);
	}

	@Override
	protected boolean isValidBlock(BlockState state) {
		return state.is(BlockTags.ANVIL);
	}

}
