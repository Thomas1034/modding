package com.thomas.verdant.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.custom.ModFlammableRotatedPillarBlock;
import com.thomas.verdant.block.custom.ModHangingSignBlock;
import com.thomas.verdant.block.custom.ModStandingSignBlock;
import com.thomas.verdant.block.custom.ModWallHangingSignBlock;
import com.thomas.verdant.block.custom.ModWallSignBlock;
import com.thomas.verdant.block.custom.VerdantGrassBlock;
import com.thomas.verdant.block.custom.VerdantRootedDirtBlock;
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
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Verdant.MOD_ID);

	// Verdant grass
	public static final RegistryObject<Block> VERDANT_GRASS_BLOCK = registerBlock("verdant_grass_block",
			() -> new VerdantGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()));

	public static final RegistryObject<Block> VERDANT_ROOTED_DIRT = registerBlock("verdant_rooted_dirt",
			() -> new VerdantRootedDirtBlock(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT).randomTicks()));

	// Verdant logs
	public static final RegistryObject<Block> VERDANT_LOG = registerFuelBlock("verdant_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), BurnTimes.LOG);

	public static final RegistryObject<Block> VERDANT_WOOD = registerFuelBlock("verdant_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), BurnTimes.LOG);

	public static final RegistryObject<Block> STRIPPED_VERDANT_LOG = registerFuelBlock("stripped_verdant_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
			BurnTimes.LOG);

	public static final RegistryObject<Block> STRIPPED_VERDANT_WOOD = registerFuelBlock("stripped_verdant_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)),
			BurnTimes.LOG);

	// Verdant planks
	public static final RegistryObject<Block> VERDANT_PLANKS = registerFuelBlock("verdant_planks",
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

	// Verdant plank items
	public static final RegistryObject<Block> VERDANT_STAIRS = registerFuelBlock("verdant_stairs",
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

	public static final RegistryObject<Block> VERDANT_SLAB = registerFuelBlock("verdant_slab",
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

	public static final RegistryObject<Block> VERDANT_BUTTON = registerFuelBlock("verdant_button",
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

	public static final RegistryObject<Block> VERDANT_PRESSURE_PLATE = registerFuelBlock("verdant_pressure_plate",
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
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

	public static final RegistryObject<Block> VERDANT_FENCE = registerFuelBlock("verdant_fence",
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

	public static final RegistryObject<Block> VERDANT_FENCE_GATE = registerFuelBlock("verdant_fence_gate",
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

	public static final RegistryObject<Block> VERDANT_DOOR = registerFuelBlock("verdant_door",
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

	public static final RegistryObject<Block> VERDANT_TRAPDOOR = registerFuelBlock("verdant_trapdoor",
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

	public static final RegistryObject<Block> VERDANT_SIGN = BLOCKS.register("verdant_sign",
			() -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.VERDANT));
	public static final RegistryObject<Block> VERDANT_WALL_SIGN = BLOCKS.register("verdant_wall_sign",
			() -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.VERDANT));

	public static final RegistryObject<Block> VERDANT_HANGING_SIGN = BLOCKS.register("verdant_hanging_sign",
			() -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN),
					ModWoodTypes.VERDANT));
	public static final RegistryObject<Block> VERDANT_WALL_HANGING_SIGN = BLOCKS.register("verdant_wall_hanging_sign",
			() -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN),
					ModWoodTypes.VERDANT));

	// Boilerplate from here on.
	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<T> registerFuelBlock(String name, Supplier<T> block, int burnTime) {
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