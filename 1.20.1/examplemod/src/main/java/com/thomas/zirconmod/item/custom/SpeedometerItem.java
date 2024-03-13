package com.thomas.zirconmod.item.custom;

import java.util.Stack;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.util.Utilities;
import com.thomas.zirconmod.worldgen.custom.CloudFloorFeature;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpeedometerItem extends Item {
	
	public static int skin = 0;

	public SpeedometerItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
			Vec3 vel = Utilities.deltaMotion(player);
			player.sendSystemMessage(Component.literal("Velocity: " + vel.x + " " + vel.y + " " + vel.z));
			player.sendSystemMessage(Component.literal("Speed: " + vel.length()));
		}

		else if (!level.isClientSide()) {
			/*if (!player.isShiftKeyDown()) {
				CloudFloorFeature.placeMultiLayer(level, player.blockPosition(), 3, ModBlocks.CLOUD.get().defaultBlockState(), Biomes.RIVER);
			} else {
				HitResult result = player.pick(5.0, 1.0f, false);
				if (result instanceof BlockHitResult bres) {
					// Erase cloud.
					eraseCloudAt(level, bres.getBlockPos());
				}
			}*/
			
			if (!player.isShiftKeyDown()) {
				skin = 0;
			} else {
				skin = 1;
			}
			System.out.println(skin);
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
