package com.thomas.verdant.command;

import java.util.List;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.thomas.verdant.growth.SpreadAmount;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.overgrowth.EntityOvergrowth;
import com.thomas.verdant.overgrowth.OvergrowthProgression;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/*
verdant
        overgrowth
                  set: sets an entity's overgrowth level
                       entities: sets for all the selected entities
                  add: adds to an entity's overgrowth level
                       entities: adds for all the selected entities
                  get: gets this player's overgrowth level
                       entity: gets for the specified single entity
                  stop: sets the growth speed of the overgrowth to zero
                  speed: sets the growth speed of the overgrowth for all stages
        spread
               get
                   pos1 pos2: counts the number of verdant ground blocks in the given area and prints the result
               speed
                     set float: the number of blocks to spread to per attempt
                     get: prints the current base spread speed
 */

public class VerdantCommandHandler {
	// FillCommand

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("verdant").requires((stack) -> {
			return stack.hasPermission(Commands.LEVEL_GAMEMASTERS);
		});

		// Infection command.
		LiteralArgumentBuilder<CommandSourceStack> overgrowth = Commands.literal("overgrowth");

		// Infection set
		LiteralArgumentBuilder<CommandSourceStack> overgrowthSet = Commands.literal("set")
				.then(Commands.argument("targets", EntityArgument.entities())
						.then(Commands.argument("level", IntegerArgumentType.integer())
								.executes(VerdantCommandHandler::setInfection)));

		// Infection get
		LiteralArgumentBuilder<CommandSourceStack> overgrowthGet = Commands.literal("get").then(
				Commands.argument("target", EntityArgument.entity()).executes(VerdantCommandHandler::getInfection));

		// Infection add
		LiteralArgumentBuilder<CommandSourceStack> overgrowthAdd = Commands.literal("add")
				.then(Commands.argument("targets", EntityArgument.entities())
						.then(Commands.argument("level", IntegerArgumentType.integer())
								.executes(VerdantCommandHandler::addInfection)));

		// Infection stop
		LiteralArgumentBuilder<CommandSourceStack> overgrowthStop = Commands.literal("stop")
				.executes(VerdantCommandHandler::stopInfection);

		// Infection reset
		LiteralArgumentBuilder<CommandSourceStack> overgrowthReset = Commands.literal("reset")
				.executes(VerdantCommandHandler::resetInfection);

		// Infection speed
		LiteralArgumentBuilder<CommandSourceStack> overgrowthSpeed = Commands.literal("speed")
				.then(Commands.argument("stage", IntegerArgumentType.integer(0, EntityOvergrowth.STAGES - 1))
						.then(Commands.argument("speed", IntegerArgumentType.integer())
								.executes(VerdantCommandHandler::speedInfection)));

		// Assemble overgrowth commands
		overgrowth.then(overgrowthSet).then(overgrowthGet).then(overgrowthAdd).then(overgrowthStop).then(overgrowthSpeed)
				.then(overgrowthReset);

		// Spread command
		LiteralArgumentBuilder<CommandSourceStack> spread = Commands.literal("spread");

		// Spread check
		LiteralArgumentBuilder<CommandSourceStack> spreadCheck = Commands.literal("check")
				.then(Commands.argument("from", BlockPosArgument.blockPos()).then(Commands
						.argument("to", BlockPosArgument.blockPos()).executes(VerdantCommandHandler::checkSpread)));

		// Spread speed
		LiteralArgumentBuilder<CommandSourceStack> spreadSpeed = Commands.literal("speed");

		// Spread speed set
		LiteralArgumentBuilder<CommandSourceStack> spreadSpeedSet = Commands.literal("set").then(Commands
				.argument("speed", FloatArgumentType.floatArg(0.0f)).executes(VerdantCommandHandler::setSpread));

		// Spread speed get
		LiteralArgumentBuilder<CommandSourceStack> spreadSpeedGet = Commands.literal("get")
				.executes(VerdantCommandHandler::getSpread);

		// Assemble speed subcommand.
		spreadSpeed.then(spreadSpeedGet).then(spreadSpeedSet);

		// Assemble spread command.
		spread.then(spreadSpeed).then(spreadCheck);

		// Assemble full command
		command.then(spread).then(overgrowth);

		// command.then(Commands.argument("command",
		// EnumArgument.enumArgument(InitialCommandOptions.class)));

		dispatcher.register(command);
	}

	private static int checkSpread(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		BlockPos from = BlockPosArgument.getLoadedBlockPos(context, "from");
		BlockPos to = BlockPosArgument.getLoadedBlockPos(context, "to");
		int ctr = 0;
		ServerLevel level = context.getSource().getLevel();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		// Iterate over the range.
		int xmin = Math.min(from.getX(), to.getX());
		int xmax = Math.max(from.getX(), to.getX());
		int ymin = Math.min(from.getY(), to.getY());
		int ymax = Math.max(from.getY(), to.getY());
		int zmin = Math.min(from.getZ(), to.getZ());
		int zmax = Math.max(from.getZ(), to.getZ());
		for (int x = xmin; x <= xmax; x++) {
			for (int y = ymin; y <= ymax; y++) {
				for (int z = zmin; z <= zmax; z++) {
					if (level.getBlockState(pos.set(x, y, z)).getBlock() instanceof VerdantGrower) {
						ctr++;
					}
				}
			}
		}
		if (context.getSource().getEntity() instanceof Player player) {
			player.sendSystemMessage(Component.translatable("command.verdant.spread_check", ctr));
		}
		return ctr;
	}

	private static int getSpread(CommandContext<CommandSourceStack> context) {

		if (context.getSource().getEntity() instanceof Player player) {
			float speed = SpreadAmount.getAmount(context.getSource().getLevel());
			float ipart = (float) Math.floor(speed);
			float fpart = speed - ipart;
			if (fpart != 0) {
				player.sendSystemMessage(Component.translatable("command.verdant.spread_speed_random", ipart, fpart));
			} else {
				player.sendSystemMessage(Component.translatable("command.verdant.spread_speed", ipart));
			}
		}
		return Command.SINGLE_SUCCESS;
	}

	private static int setSpread(CommandContext<CommandSourceStack> context) {

		float amount = context.getArgument("speed", Float.class);
		SpreadAmount.setAmount(context.getSource().getLevel(), amount);
		if (context.getSource().getEntity() instanceof Player player) {
			float speed = SpreadAmount.getAmount(context.getSource().getLevel());
			float ipart = (float) Math.floor(speed);
			float fpart = speed - ipart;
			if (fpart != 0) {
				player.sendSystemMessage(
						Component.translatable("command.verdant.set_spread_speed_random", ipart, fpart));
			} else {
				player.sendSystemMessage(Component.translatable("command.verdant.set_spread_speed", ipart));
			}
		}
		return Command.SINGLE_SUCCESS;
	}

	private static int setInfection(CommandContext<CommandSourceStack> context) {

		EntitySelector targets = context.getArgument("targets", EntitySelector.class);
		int level = context.getArgument("level", Integer.class);
		int counter = 0;
		try {
			List<? extends Entity> entities = targets.findEntities(context.getSource());

			for (Entity entity : entities) {
				if (entity instanceof LivingEntity le) {
					EntityOvergrowth.setLevel(le, level);
					counter++;
				}
			}

			if (context.getSource().getEntity() instanceof Player player) {
				player.sendSystemMessage(Component.translatable("command.verdant.set_overgrowth", counter));
			}
		} catch (CommandSyntaxException e) {
			if (context.getSource().getEntity() instanceof Player player) {
				player.sendSystemMessage(Component.translatable("command.verdant.exception"));
			}
			e.printStackTrace();
		}

		return counter;

	}

	private static int getInfection(CommandContext<CommandSourceStack> context) {

		EntitySelector target = context.getArgument("target", EntitySelector.class);
		int level = 0;
		try {
			List<? extends Entity> entities = target.findEntities(context.getSource());
			LivingEntity targetEntity = null;
			for (Entity entity : entities) {
				if (entity instanceof LivingEntity le) {
					targetEntity = le;
				}
			}
			if (targetEntity == null) {
				if (context.getSource().getEntity() instanceof Player player) {
					player.sendSystemMessage(Component.translatable("command.verdant.not_found"));
				}
				return 0;
			}

			level = EntityOvergrowth.getLevel(targetEntity);

			if (context.getSource().getEntity() instanceof Player player) {
				player.sendSystemMessage(Component.translatable("command.verdant.get_overgrowth",
						targetEntity.getDisplayName().getString(), level));
			}
		} catch (CommandSyntaxException e) {
			if (context.getSource().getEntity() instanceof Player player) {
				player.sendSystemMessage(Component.translatable("command.verdant.exception"));
			}
			// e.printStackTrace();
		}

		return level;
	}

	private static int stopInfection(CommandContext<CommandSourceStack> context) {
		// System.out.println("Stopping overgrowth.");
		OvergrowthProgression.setAllStages(context.getSource().getLevel(), 0);
		// System.out.println("Stages set.");
		if (context.getSource().getEntity() instanceof Player player) {
			player.sendSystemMessage(Component.translatable("command.verdant.stop_overgrowth"));
		}
		// System.out.println("Message sent.");
		return Command.SINGLE_SUCCESS;
	}

	private static int resetInfection(CommandContext<CommandSourceStack> context) {
		// System.out.println("Resetting overgrowth.");
		OvergrowthProgression.resetAllStages(context.getSource().getLevel());
		// System.out.println("Stages set.");
		if (context.getSource().getEntity() instanceof Player player) {
			player.sendSystemMessage(Component.translatable("command.verdant.reset_overgrowth_speed"));
		}
		// System.out.println("Message sent.");
		return Command.SINGLE_SUCCESS;
	}

	private static int speedInfection(CommandContext<CommandSourceStack> context) {

		int stage = context.getArgument("stage", Integer.class);
		int speed = context.getArgument("speed", Integer.class);

		OvergrowthProgression.setStage(context.getSource().getLevel(), stage, speed);

		if (context.getSource().getEntity() instanceof Player player) {
			player.sendSystemMessage(Component.translatable("command.verdant.set_overgrowth_speed", stage, speed));
		}

		return Command.SINGLE_SUCCESS;
	}

	private static int addInfection(CommandContext<CommandSourceStack> context) {

		EntitySelector targets = context.getArgument("targets", EntitySelector.class);
		int level = context.getArgument("level", Integer.class);
		int counter = 0;
		try {
			List<? extends Entity> entities = targets.findEntities(context.getSource());

			for (Entity entity : entities) {
				if (entity instanceof LivingEntity le) {
					EntityOvergrowth.addLevel(le, level);
					counter++;
				}
			}

			if (context.getSource().getEntity() instanceof Player player) {
				player.sendSystemMessage(Component.translatable("command.verdant.add_overgrowth", level, counter));
			}
		} catch (CommandSyntaxException e) {
			if (context.getSource().getEntity() instanceof Player player) {
				player.sendSystemMessage(Component.translatable("command.verdant.exception"));
			}
			e.printStackTrace();
		}

		return counter;

	}

}
