package com.thomas.verdant.item.custom;

import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.growth.VerdantGrower;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

			// Repeat based on the number of items in the stack.
			for (int i = 0; i < stack.getCount(); i++) {
				// System.out.println("Heart is ticking");
				// The range to convert blocks in; radius of 3.
				BlockPos posToTry = VerdantGrower.withinSphereDist(holder.getOnPos(), 3, level.random);

				// Try to erode the block, then try to convert it.
				VerdantGrower.erodeStatic(level, posToTry, holder.isInWaterOrRain());
				// Try to convert the nearby block.
				VerdantGrower.convertGround(level, posToTry, holder.isInWaterOrRain());
			}

//			// Grow vines on the holder, if the holder is living.
//			if (holder instanceof LivingEntity livingHolder
//					&& !(holder instanceof ServerPlayer sp && sp.getAbilities().instabuild)) {
//				EntityOvergrowth.addLevel(livingHolder, 3);
//				// System.out.println("Added infection: " + EntityInfection.getLevel(holder));
//			}

		}
	}

}
