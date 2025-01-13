package com.thomas.verdant.item.custom;

import com.thomas.verdant.block.VerdantGrower;
import com.thomas.verdant.block.custom.SpreadingRootsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeartOfTheForestItem extends Item implements VerdantGrower {

    public HeartOfTheForestItem(Properties properties) {
        super(properties);
    }

    // Spreads the verdant around the player that holds it.
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity holder, int iInt, boolean isHeld) {
        // Ensure it is on the server.
        if (level instanceof ServerLevel serverLevel) {

            // Repeat based on the number of items in the stack.
            for (int i = 0; i < stack.getCount(); i++) {
                // System.out.println("Heart is ticking");
                // The range to convert blocks in; radius of 3.
                BlockPos posToTry = SpreadingRootsBlock.withinDist(holder.getOnPos(), 3, level.random);

                // Try to erode the block, then try to convert it.
                this.erodeOrGrow(serverLevel, posToTry, holder.isInWaterOrBubble());
            }
        }
    }

}
