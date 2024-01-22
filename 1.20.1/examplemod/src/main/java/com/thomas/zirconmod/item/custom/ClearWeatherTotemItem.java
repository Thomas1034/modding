package com.thomas.zirconmod.item.custom;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClearWeatherTotemItem extends Item {

	public ClearWeatherTotemItem(Properties properties) {
		super(properties);
	}

	// Implements custom behavior.
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
			// Sets the level's weather to clear if it's raining.
			ServerLevel sl = (ServerLevel) level;
			sl.setWeatherParameters(ServerLevel.RAIN_DELAY.sample(sl.getRandom()), 0, false, false);
			player.getCooldowns().addCooldown(this, 60);
            return InteractionResultHolder.consume(player.getItemInHand(hand));
		}

		return super.use(level, player, hand);
	}

	@SuppressWarnings("unused")
	private static int getDuration(CommandSourceStack p_265382_, int p_265171_, IntProvider p_265122_) {
		return p_265171_ == -1 ? p_265122_.sample(p_265382_.getLevel().getRandom()) : p_265171_;
	}

}