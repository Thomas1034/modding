package com.thomas.cloudscape.block.custom;

import javax.annotation.Nullable;

import com.thomas.cloudscape.block.entity.menu.NetheriteAnvilMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class NetheriteAnvilBlock extends AnvilBlock {

	private static final Component CONTAINER_TITLE = Component.translatable("container.repair");

	public NetheriteAnvilBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult result) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			player.openMenu(state.getMenuProvider(level, pos));
			player.awardStat(Stats.INTERACT_WITH_ANVIL);
			return InteractionResult.CONSUME;
		}
	}

	@Override
	@Nullable
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((integer, inventory, containerLevelAccess) -> {
			return new NetheriteAnvilMenu(integer, inventory, ContainerLevelAccess.create(level, pos));
		}, CONTAINER_TITLE);
	}
}
