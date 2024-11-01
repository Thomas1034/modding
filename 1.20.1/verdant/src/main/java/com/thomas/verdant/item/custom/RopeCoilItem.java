package com.thomas.verdant.item.custom;

import com.thomas.verdant.entity.custom.ThrownRopeEntity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RopeCoilItem extends Item {

	private final int length;

	public RopeCoilItem(Item.Properties properties, int length) {
		super(properties);
		this.length = length;
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW,
				SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (level instanceof ServerLevel serverLevel) {
			ThrownRopeEntity rope = this.createThrownRope(serverLevel, player, itemstack);
			rope.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			serverLevel.addFreshEntity(rope);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.getAbilities().instabuild) {
			itemstack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}

	public int getLength() {
		return this.getLength();
	}

	public ThrownRopeEntity createThrownRope(Level level, Player player, ItemStack itemstack) {
		ThrownRopeEntity rope = player == null ? new ThrownRopeEntity(level) : new ThrownRopeEntity(level, player);
		rope.setItem(itemstack);
		rope.setLength(this.length);
		return rope;
	}
}
