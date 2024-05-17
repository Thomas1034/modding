package com.thomas.verdant.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.custom.CoffeeCropBlock;
import com.thomas.verdant.block.custom.FragileFlammableRotatedPillarBlock;
import com.thomas.verdant.block.custom.VerdantConduitBlock;
import com.thomas.verdant.block.custom.ModFlammableRotatedPillarBlock;
import com.thomas.verdant.block.custom.ModHangingSignBlock;
import com.thomas.verdant.block.custom.ModStandingSignBlock;
import com.thomas.verdant.block.custom.ModWallHangingSignBlock;
import com.thomas.verdant.block.custom.ModWallSignBlock;
import com.thomas.verdant.block.custom.PoisonVerdantLeavesBlock;
import com.thomas.verdant.block.custom.PoisonVerdantTendrilBlock;
import com.thomas.verdant.block.custom.PoisonVerdantTendrilPlantBlock;
import com.thomas.verdant.block.custom.StinkingBlossomBlock;
import com.thomas.verdant.block.custom.ThornBushBlock;
import com.thomas.verdant.block.custom.ThornyVerdantLeavesBlock;
import com.thomas.verdant.block.custom.VerdantLeafyVineBlock;
import com.thomas.verdant.block.custom.VerdantLeavesBlock;
import com.thomas.verdant.block.custom.VerdantRootedDirtBlock;
import com.thomas.verdant.block.custom.VerdantTendrilBlock;
import com.thomas.verdant.block.custom.VerdantTendrilPlantBlock;
import com.thomas.verdant.block.custom.VerdantVineBlock;
import com.thomas.verdant.block.custom.WildCoffeeBlock;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.item.ModItems;
import com.thomas.verdant.util.BurnTimes;
import com.thomas.verdant.util.ModBlockSetType;
import com.thomas.verdant.util.ModWoodTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Verdant.MOD_ID);

	// Verdant planks
	public static final RegistryObject<Block> VERDANT_PLANKS = registerFuelBlockWithItem("verdant_planks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.PLANKS);

	// Verdant heartwood planks
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_PLANKS = registerFuelBlockWithItem(
			"verdant_heartwood_planks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(3.0F, 8.0F)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.PLANKS);

	// Rotten wood.
	public static final RegistryObject<Block> ROTTEN_WOOD = registerFuelBlockWithItem("rotten_wood",
			() -> new FragileFlammableRotatedPillarBlock(
					BlockBehaviour.Properties.copy(Blocks.OAK_LOG).instabreak()
							.isViewBlocking((state, level, pos) -> false).noOcclusion().explosionResistance(0.015625f),
					35, 1, 2.0f),
			BurnTimes.STICK);

	// Verdant grass
	public static final RegistryObject<Block> VERDANT_ROOTED_DIRT = registerBlockWithItem("verdant_rooted_dirt",
			() -> new VerdantRootedDirtBlock(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT).randomTicks()));

	public static final RegistryObject<Block> VERDANT_GRASS_BLOCK = registerBlockWithItem("verdant_grass_block",
			() -> new VerdantRootedDirtBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()));

	public static final RegistryObject<Block> VERDANT_ROOTED_MUD = registerBlockWithItem("verdant_rooted_mud",
			() -> new VerdantRootedDirtBlock(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT).randomTicks()));

	public static final RegistryObject<Block> VERDANT_MUD_GRASS_BLOCK = registerBlockWithItem("verdant_mud_grass_block",
			() -> new VerdantRootedDirtBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()));

	public static final RegistryObject<Block> VERDANT_ROOTED_CLAY = registerBlockWithItem("verdant_rooted_clay",
			() -> new VerdantRootedDirtBlock(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT).randomTicks()));

	public static final RegistryObject<Block> VERDANT_CLAY_GRASS_BLOCK = registerBlockWithItem(
			"verdant_clay_grass_block",
			() -> new VerdantRootedDirtBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()));

	// Verdant vine
	public static final RegistryObject<Block> VERDANT_VINE = registerBlockWithItem("verdant_vine",
			() -> new VerdantVineBlock(
					BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).pushReaction(PushReaction.DESTROY).randomTicks()
							.isViewBlocking((state, level, pos) -> false).noOcclusion()));
	public static final RegistryObject<Block> LEAFY_VERDANT_VINE = registerBlockWithItem("leafy_verdant_vine",
			() -> new VerdantLeafyVineBlock(
					BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).pushReaction(PushReaction.DESTROY).randomTicks()
							.isViewBlocking((state, level, pos) -> false).noOcclusion()));

	// Tendril
	public static final RegistryObject<Block> VERDANT_TENDRIL = registerBlockWithItem("verdant_tendril",
			() -> new VerdantTendrilBlock(BlockBehaviour.Properties.copy(Blocks.KELP)));
	public static final RegistryObject<Block> VERDANT_TENDRIL_PLANT = registerBlockOnly("verdant_tendril_plant",
			() -> new VerdantTendrilPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT)));

	// Poison ivy
	public static final RegistryObject<Block> POISON_IVY = registerBlockWithItem("poison_ivy",
			() -> new PoisonVerdantTendrilBlock(BlockBehaviour.Properties.copy(Blocks.KELP)));
	public static final RegistryObject<Block> POISON_IVY_PLANT = registerBlockOnly("poison_ivy_plant",
			() -> new PoisonVerdantTendrilPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT)));

	// Verdant logs
	public static final RegistryObject<Block> VERDANT_LEAVES = registerBlockWithItem("verdant_leaves",
			() -> new VerdantLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks()));

	public static final RegistryObject<Block> THORNY_VERDANT_LEAVES = registerBlockWithItem("thorny_verdant_leaves",
			() -> new ThornyVerdantLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks()));

	public static final RegistryObject<Block> POISON_IVY_VERDANT_LEAVES = registerBlockWithItem(
			"poison_ivy_verdant_leaves",
			() -> new PoisonVerdantLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks()));

	public static final RegistryObject<Block> VERDANT_LOG = registerFuelBlockWithItem("verdant_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), BurnTimes.LOG);

	public static final RegistryObject<Block> VERDANT_WOOD = registerFuelBlockWithItem("verdant_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), BurnTimes.LOG);

	public static final RegistryObject<Block> STRIPPED_VERDANT_LOG = registerFuelBlockWithItem("stripped_verdant_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
			BurnTimes.LOG);

	public static final RegistryObject<Block> STRIPPED_VERDANT_WOOD = registerFuelBlockWithItem("stripped_verdant_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)),
			BurnTimes.LOG);

	// Verdant plank items
	public static final RegistryObject<Block> VERDANT_STAIRS = registerFuelBlockWithItem("verdant_stairs",
			() -> new StairBlock(() -> ModBlocks.VERDANT_PLANKS.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.VERDANT_PLANKS.get())) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.STAIRS);

	public static final RegistryObject<Block> VERDANT_SLAB = registerFuelBlockWithItem("verdant_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_PLANKS.get())) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.SLAB);

	public static final RegistryObject<Block> VERDANT_BUTTON = registerFuelBlockWithItem("verdant_button",
			() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), ModBlockSetType.VERDANT, 10,
					true) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.BUTTON);

	public static final RegistryObject<Block> VERDANT_PRESSURE_PLATE = registerFuelBlockWithItem(
			"verdant_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
					BlockBehaviour.Properties.copy(ModBlocks.VERDANT_PLANKS.get()), ModBlockSetType.VERDANT) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.PRESSURE_PLATE);

	public static final RegistryObject<Block> VERDANT_FENCE = registerFuelBlockWithItem("verdant_fence",
			() -> new FenceBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_PLANKS.get())) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.FENCE);

	public static final RegistryObject<Block> VERDANT_FENCE_GATE = registerFuelBlockWithItem("verdant_fence_gate",
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_PLANKS.get()),
					SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.FENCE_GATE);

	public static final RegistryObject<Block> VERDANT_DOOR = registerFuelBlockWithItem("verdant_door",
			() -> new DoorBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_PLANKS.get()).noOcclusion(),
					ModBlockSetType.VERDANT) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.DOOR);

	public static final RegistryObject<Block> VERDANT_TRAPDOOR = registerFuelBlockWithItem("verdant_trapdoor",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_PLANKS.get()).noOcclusion(),
					ModBlockSetType.VERDANT) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
			}, BurnTimes.TRAPDOOR);

	// Verdant signs
	public static final RegistryObject<Block> VERDANT_SIGN = registerBlockOnly("verdant_sign",
			() -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.VERDANT));
	public static final RegistryObject<Block> VERDANT_WALL_SIGN = registerBlockOnly("verdant_wall_sign",
			() -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.VERDANT));

	public static final RegistryObject<Block> VERDANT_HANGING_SIGN = registerBlockOnly("verdant_hanging_sign",
			() -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN),
					ModWoodTypes.VERDANT));
	public static final RegistryObject<Block> VERDANT_WALL_HANGING_SIGN = registerBlockOnly("verdant_wall_hanging_sign",
			() -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN),
					ModWoodTypes.VERDANT));

	// Heartwood variants
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_LOG = registerFuelBlockWithItem("verdant_heartwood_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG), 2, 5),
			BurnTimes.LOG * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_WOOD = registerFuelBlockWithItem(
			"verdant_heartwood_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD), 2, 5),
			BurnTimes.LOG * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> STRIPPED_VERDANT_HEARTWOOD_LOG = registerFuelBlockWithItem(
			"stripped_verdant_heartwood_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG), 2, 5),
			BurnTimes.LOG * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> STRIPPED_VERDANT_HEARTWOOD_WOOD = registerFuelBlockWithItem(
			"stripped_verdant_heartwood_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD), 2, 5),
			BurnTimes.LOG * BurnTimes.HEARTWOOD_BONUS);

	// Verdant plank items
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_STAIRS = registerFuelBlockWithItem(
			"verdant_heartwood_stairs",
			() -> new StairBlock(() -> ModBlocks.VERDANT_HEARTWOOD_PLANKS.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get())) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.STAIRS * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_SLAB = registerFuelBlockWithItem(
			"verdant_heartwood_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get())) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.SLAB * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_BUTTON = registerFuelBlockWithItem(
			"verdant_heartwood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON),
					ModBlockSetType.VERDANT_HEARTWOOD, 20, true) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.BUTTON * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_PRESSURE_PLATE = registerFuelBlockWithItem(
			"verdant_heartwood_pressure_plate",
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS,
					BlockBehaviour.Properties.copy(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()),
					ModBlockSetType.VERDANT_HEARTWOOD) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.PRESSURE_PLATE * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_FENCE = registerFuelBlockWithItem(
			"verdant_heartwood_fence",
			() -> new FenceBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get())) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.FENCE * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_FENCE_GATE = registerFuelBlockWithItem(
			"verdant_heartwood_fence_gate",
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()),
					SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.FENCE_GATE * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_DOOR = registerFuelBlockWithItem(
			"verdant_heartwood_door",
			() -> new DoorBlock(BlockBehaviour.Properties.copy(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()).noOcclusion(),
					ModBlockSetType.VERDANT) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.DOOR * BurnTimes.HEARTWOOD_BONUS);

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_TRAPDOOR = registerFuelBlockWithItem(
			"verdant_heartwood_trapdoor",
			() -> new TrapDoorBlock(
					BlockBehaviour.Properties.copy(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()).noOcclusion(),
					ModBlockSetType.VERDANT) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 2;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.TRAPDOOR * BurnTimes.HEARTWOOD_BONUS);

	// Verdant signs
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_SIGN = registerBlockOnly("verdant_heartwood_sign",
			() -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN),
					ModWoodTypes.VERDANT_HEARTWOOD));
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_WALL_SIGN = registerBlockOnly(
			"verdant_heartwood_wall_sign",
			() -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN),
					ModWoodTypes.VERDANT_HEARTWOOD));

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_HANGING_SIGN = registerBlockOnly(
			"verdant_heartwood_hanging_sign",
			() -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN),
					ModWoodTypes.VERDANT_HEARTWOOD));
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_WALL_HANGING_SIGN = registerBlockOnly(
			"verdant_heartwood_wall_hanging_sign",
			() -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN),
					ModWoodTypes.VERDANT_HEARTWOOD));

	// Decorative flowers
	public static final RegistryObject<Block> BLEEDING_HEART = registerBlockWithItem("bleeding_heart",
			() -> new FlowerBlock(() -> ModMobEffects.FOOD_POISONING.get(), 40,
					BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noOcclusion().noCollission()));

	public static final RegistryObject<Block> POTTED_BLEEDING_HEART = registerBlockOnly("potted_bleeding_heart",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.BLEEDING_HEART,
					BlockBehaviour.Properties.copy(Blocks.POTTED_BLUE_ORCHID).noOcclusion()));

	public static final RegistryObject<Block> WILD_COFFEE = registerBlockWithItem("wild_coffee",
			() -> new WildCoffeeBlock(() -> ModMobEffects.CAFFEINATED.get(), 25,
					BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noOcclusion().noCollission()));

	public static final RegistryObject<Block> POTTED_WILD_COFFEE = registerBlockOnly("potted_wild_coffee",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.WILD_COFFEE,
					BlockBehaviour.Properties.copy(Blocks.POTTED_BLUE_ORCHID).noOcclusion()));

	public static final RegistryObject<Block> STINKING_BLOSSOM = registerBlockWithItem("stinking_blossom",
			() -> new StinkingBlossomBlock(BlockBehaviour.Properties.copy(Blocks.SPORE_BLOSSOM).noOcclusion()));

	// Thorn bush
	public static final RegistryObject<Block> THORN_BUSH = registerBlockWithItem("thorn_bush", () -> new ThornBushBlock(
			BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion().strength(0.5F)));

	public static final RegistryObject<Block> POTTED_THORN_BUSH = registerBlockOnly("potted_thorn_bush",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.THORN_BUSH,
					BlockBehaviour.Properties.copy(Blocks.POTTED_BLUE_ORCHID).noOcclusion()));

	// Coffee crop
	public static final RegistryObject<Block> COFFEE_CROP = BLOCKS.register("coffee_crop", () -> new CoffeeCropBlock(
			BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion().noCollission()));

	// Dirt ores
	public static final RegistryObject<Block> DIRT_COAL_ORE = registerBlockWithItem("dirt_coal_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> DIRT_COPPER_ORE = registerBlockWithItem("dirt_copper_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> DIRT_IRON_ORE = registerBlockWithItem("dirt_iron_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> DIRT_GOLD_ORE = registerBlockWithItem("dirt_gold_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> DIRT_REDSTONE_ORE = registerBlockWithItem("dirt_redstone_ore",
			() -> new RedStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> DIRT_LAPIS_ORE = registerBlockWithItem("dirt_lapis_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> DIRT_EMERALD_ORE = registerBlockWithItem("dirt_emerald_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> DIRT_DIAMOND_ORE = registerBlockWithItem("dirt_diamond_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));

	// Jungle heart block
	public static final RegistryObject<Block> VERDANT_CONDUIT = registerBlockWithItem("verdant_conduit",
			() -> new VerdantConduitBlock(BlockBehaviour.Properties.copy(Blocks.BEACON)));

	// Boilerplate from here on.
	private static <T extends Block> RegistryObject<T> registerBlockOnly(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		// registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<T> registerFuelBlockWithItem(String name, Supplier<T> block,
			int burnTime) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerFuelBlockItem(name, toReturn, burnTime);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<T> registerFireproofBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerFireproofBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	private static <T extends Block> RegistryObject<Item> registerFireproofBlockItem(String name,
			RegistryObject<T> block) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().fireResistant()));
	}

	private static <T extends Block> RegistryObject<Item> registerFuelBlockItem(String name, RegistryObject<T> block,
			int burnTime) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {

			@Override
			public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
				return burnTime;
			}

		});
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}