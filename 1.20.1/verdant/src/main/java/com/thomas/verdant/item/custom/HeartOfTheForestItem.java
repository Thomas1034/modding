package com.thomas.verdant.item.custom;

import com.thomas.verdant.block.VerdantGrower;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeartOfTheForestItem extends Item {

	public HeartOfTheForestItem(Properties properties) {
		super(properties);
	}

	// Spreads the verdant around the player that holds it.
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity holder, int iInt, boolean isHeld) {
		// Ensure it is on the server.
		if (level instanceof ServerLevel) {
			// System.out.println("Heart is ticking");
			// The range to convert blocks in; radius of 3.
			BlockPos posToTry = VerdantGrower.withinSphereDist(holder.getOnPos(), 3, level.random);

			// Try to erode the block, then try to convert it.
			VerdantGrower.erodeStatic(level, posToTry, holder.isInWaterOrRain());
			// Try to convert the nearby block.
			VerdantGrower.convertGround(level, posToTry, holder.isInWaterOrRain());
		}
	}

}
