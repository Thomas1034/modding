package com.thomas.quantumcircuit.item.custom;

import com.thomas.quantumcircuit.util.CircuitBuilder;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DebugItem extends Item {

	public DebugItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (!level.isClientSide()) {
			if (!player.isShiftKeyDown()) {

			} else {

			}
		}
		return super.use(level, player, hand);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		System.out.println(context.getClickedPos() + " goes back to " + CircuitBuilder.findDirectBeginPos(context.getLevel(), context.getClickedPos()));
		return super.useOn(context);
	}

}
