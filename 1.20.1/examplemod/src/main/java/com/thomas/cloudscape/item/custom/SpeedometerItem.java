package com.thomas.cloudscape.item.custom;

import java.util.Stack;

import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.util.MotionHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class SpeedometerItem extends Item {
	
	public SpeedometerItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
			Vec3 vel = player.getDeltaMovement();
			player.sendSystemMessage(Component.literal("Speed: " + vel.length()));
			player.sendSystemMessage(Component.literal("Velocity: " + vel.x + " " + vel.y + " " + vel.z));
		}

		else if (!level.isClientSide() && player instanceof ServerPlayer sp) {
			Vec3 vel = MotionHelper.getVelocity(sp);
			player.sendSystemMessage(Component.literal("Server Velocity: " + vel.x + " " + vel.y + " " + vel.z));
		}

		return super.use(level, player, hand);
	}

	private static void eraseCloudAt(Level level, BlockPos pos) {

		Stack<BlockPos> posStack = new Stack<>();

		posStack.push(pos);

		while (!posStack.isEmpty()) {
			pos = posStack.pop();
			if (level.getBlockState(pos).is(ModBlocks.CLOUD.get())) {
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				posStack.push(pos.above());
				posStack.push(pos.below());
				posStack.push(pos.north());
				posStack.push(pos.east());
				posStack.push(pos.south());
				posStack.push(pos.west());
			}
		}

	}

}
