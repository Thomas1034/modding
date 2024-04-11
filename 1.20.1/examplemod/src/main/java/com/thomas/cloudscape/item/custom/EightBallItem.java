package com.thomas.cloudscape.item.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EightBallItem extends Item {
	
	public EightBallItem(Properties properties) {
		super(properties);
	}
	
	// Makes the 8-ball fire resistant
	@Override
	public boolean isFireResistant() {
		return true;
	}
	
	// Implements custom behavior.
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
	    
		if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND)
		{
			player.respawn();
			
			// Output a random number
			int value = getRandomNumber(1, 10);
			outputNumber(player, value);

			// Checks if the player wins or loses
			if (value > 6)
			{
				announceWin(player);
				// Grants the player a luck effect
				player.addEffect(new MobEffectInstance(MobEffects.LUCK, 20*60*((value - 5) * 2), value - 5));
				// play sound
				Vec3 eyepos = player.getEyePosition();
				level.playSound(null, new BlockPos((int)eyepos.x, (int)eyepos.y, (int)eyepos.z), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0f, 1.0f);
			}
			else
			{
				announceLose(player);
				// Grants the player a bad luck effect
				player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 20*60*((6 - value) * 2), 6 - value));
				// play sound
				Vec3 eyepos = player.getEyePosition();
				level.playSound(null, new BlockPos((int)eyepos.x, (int)eyepos.y, (int)eyepos.z), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0f, 1.0f);
			}
			
			
			// Set a cooldown
			player.getCooldowns().addCooldown(this, 60);
		}
		
		return super.use(level,  player, hand);
	}
	
	
	// Displays a custom tooltip
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
		
		if (Screen.hasShiftDown())
		{
			components.add(Component.literal("Right click to play").withStyle(ChatFormatting.AQUA));
		}
		else
		{
			components.add(Component.literal("Press SHIFT for more information").withStyle(ChatFormatting.YELLOW));
		}
		
	}
	
	
	
	
	private void outputNumber(Player player, int number)
	{
		player.sendSystemMessage(Component.literal("Your Number is " + number));
		
	}
	
	private void announceWin(Player player)
	{
		player.sendSystemMessage(Component.literal("You win!"));
	}
	
	private void announceLose(Player player)
	{
		player.sendSystemMessage(Component.literal("You lose."));
	}
	
	
	private int getRandomNumber(int a, int b)
	{
		return RandomSource.createNewThreadLocalInstance().nextInt(b - a) + a;
	}
	
}
