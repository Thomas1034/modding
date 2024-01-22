package com.thomas.zirconmod.item.custom;

import com.thomas.zirconmod.util.Utilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpeedometerItem extends Item {

	public SpeedometerItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		System.out.println(player.getClass());
		if (level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
			Vec3 vel = Utilities.deltaMotion(player);
			player.sendSystemMessage(Component.literal("Velocity: " + vel.x + " " + vel.y + " " + vel.z));
			player.sendSystemMessage(Component.literal("Speed: " + vel.length()));

		}
		else if (!level.isClientSide()) {
			//PetrifiedTreeFeature.placePetrifiedTree(level, player.blockPosition());
		}

		return super.use(level, player, hand);
	}

}
