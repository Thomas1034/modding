package com.thomas.zirconmod.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.custom.BlueberryCropBlock;
import com.thomas.zirconmod.block.custom.BuddingCitrineBlock;
import com.thomas.zirconmod.block.custom.CloudBlock;
import com.thomas.zirconmod.block.custom.GemBracketBlock;
import com.thomas.zirconmod.block.custom.ModFlammableRotatedPillarBlock;
import com.thomas.zirconmod.block.custom.NimbulaPolypBlock;
import com.thomas.zirconmod.block.custom.QuicksandBlock;
import com.thomas.zirconmod.block.custom.ThirstyBlock;
import com.thomas.zirconmod.block.custom.ThunderCloudBlock;
import com.thomas.zirconmod.block.custom.WallGemBracketBlock;
import com.thomas.zirconmod.block.custom.ZirconLampBlock;
import com.thomas.zirconmod.item.ModItems;
import com.thomas.zirconmod.util.BurnTimes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			ZirconMod.MOD_ID);

	// Citrine blocks
	public static final RegistryObject<Block> CITRINE_BLOCK = registerBlock("citrine_block",
			() -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
	public static final RegistryObject<Block> BUDDING_CITRINE = registerBlock("budding_citrine",
			() -> new BuddingCitrineBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)));
	public static final RegistryObject<Block> CITRINE_CLUSTER = registerBlock("citrine_cluster",
			() -> new AmethystClusterBlock(7, 3,
					BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).forceSolidOn().noOcclusion()
							.randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152632_) -> {
								return 5;
							}).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> LARGE_CITRINE_BUD = registerBlock("large_citrine_bud",
			() -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.copy(CITRINE_CLUSTER.get())
					.sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn().lightLevel((p_152629_) -> {
						return 4;
					}).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> MEDIUM_CITRINE_BUD = registerBlock("medium_citrine_bud",
			() -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.copy(CITRINE_CLUSTER.get())
					.sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn().lightLevel((p_152617_) -> {
						return 2;
					}).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> SMALL_CITRINE_BUD = registerBlock("small_citrine_bud",
			() -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.copy(CITRINE_CLUSTER.get())
					.sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn().lightLevel((p_187409_) -> {
						return 1;
					}).pushReaction(PushReaction.DESTROY)));

	// Zircon block
	public static final RegistryObject<Block> ZIRCON_BLOCK = registerBlock("zircon_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)
					.emissiveRendering((state, getter, position) -> true).lightLevel(state -> 5)));

	// Zircon ore
	public static final RegistryObject<Block> ZIRCON_ORE = registerBlock("zircon_ore", () -> new Block(
			BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.STONE).lightLevel(state -> 1)));

	// Zirconium block
	public static final RegistryObject<Block> ZIRCONIUM_BLOCK = registerFireproofBlock("zirconium_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).strength(8f)
					.requiresCorrectToolForDrops().explosionResistance(100).sound(SoundType.METAL)));

	// Raw zirconium block
	public static final RegistryObject<Block> RAW_ZIRCONIUM_BLOCK = registerFireproofBlock("raw_zirconium_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(8f).requiresCorrectToolForDrops()
					.explosionResistance(100).sound(SoundType.METAL)));

	// Deepslate zircon ore
	public static final RegistryObject<Block> DEEPSLATE_ZIRCON_ORE = registerBlock("deepslate_zircon_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).sound(SoundType.DEEPSLATE)
					.lightLevel(state -> 1)));

	// Echo block
	public static final RegistryObject<Block> ECHO_BLOCK = registerBlock("echo_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).sound(SoundType.METAL)));

	// Zircon lamp block
	public static final RegistryObject<Block> ZIRCON_LAMP = registerBlock("zircon_lamp",
			() -> new ZirconLampBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.3f)
					.lightLevel(state -> state.getValue(ZirconLampBlock.LIT) ? 12 : 0).sound(SoundType.GLASS)));

	// Thirsty packed mud block (for structures)
	public static final RegistryObject<Block> THIRSTY_PACKED_MUD = registerBlock("thirsty_packed_mud",
			() -> new ThirstyBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_MUD), Blocks.PACKED_MUD));
	// Thirsty mud bricks block
	public static final RegistryObject<Block> THIRSTY_MUD_BRICKS = registerBlock("thirsty_mud_bricks",
			() -> new ThirstyBlock(BlockBehaviour.Properties.copy(Blocks.MUD_BRICKS), Blocks.MUD_BRICKS));

	// Quicksand block
	public static final RegistryObject<Block> QUICKSAND = registerBlock("quicksand",
			() -> new QuicksandBlock(BlockBehaviour.Properties.copy(Blocks.SAND)));

	// Basic cloud block
	public static final RegistryObject<Block> CLOUD = registerBlock("cloud",
			() -> new CloudBlock(BlockBehaviour.Properties.of().strength(0.1F).destroyTime(0.5F)
					.pushReaction(PushReaction.DESTROY).randomTicks().noOcclusion()));

	// Basic thundercloud block
	public static final RegistryObject<Block> THUNDER_CLOUD = registerBlock("thunder_cloud",
			() -> new ThunderCloudBlock(BlockBehaviour.Properties.of().strength(0.1F).destroyTime(0.5F)
					.pushReaction(PushReaction.DESTROY).randomTicks().noOcclusion()));

	// Cloud bricks
	public static final RegistryObject<Block> CLOUD_BRICKS = registerBlock("cloud_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).sound(SoundType.EMPTY)));

	public static final RegistryObject<Block> THUNDER_CLOUD_BRICKS = registerBlock("thunder_cloud_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).sound(SoundType.EMPTY)));

	// Palm logs
	public static final RegistryObject<Block> PALM_LOG = registerFuelBlock("palm_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), BurnTimes.LOG);

	public static final RegistryObject<Block> PALM_WOOD = registerFuelBlock("palm_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), BurnTimes.LOG);

	public static final RegistryObject<Block> STRIPPED_PALM_LOG = registerFuelBlock("stripped_palm_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
			BurnTimes.LOG);

	public static final RegistryObject<Block> STRIPPED_PALM_WOOD = registerFuelBlock("stripped_palm_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)),
			BurnTimes.LOG);

	// Palm planks
	public static final RegistryObject<Block> PALM_PLANKS = registerFuelBlock("palm_planks",
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

	// Palm planked items
	public static final RegistryObject<Block> PALM_STAIRS = registerFuelBlock("palm_stairs",
			() -> new StairBlock(() -> ModBlocks.PALM_PLANKS.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.PALM_PLANKS.get())) {
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

	public static final RegistryObject<Block> PALM_SLAB = registerFuelBlock("palm_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.PALM_PLANKS.get())) {
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

	public static final RegistryObject<Block> PALM_BUTTON = registerFuelBlock("palm_button",
			() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetType.OAK, 10, true) {
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

	public static final RegistryObject<Block> PALM_PRESSURE_PLATE = registerFuelBlock("palm_pressure_plate",
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
					BlockBehaviour.Properties.copy(ModBlocks.PALM_PLANKS.get()), BlockSetType.OAK) {
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

	public static final RegistryObject<Block> PALM_FENCE = registerFuelBlock("palm_fence",
			() -> new FenceBlock(BlockBehaviour.Properties.copy(ModBlocks.PALM_PLANKS.get())) {
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

	public static final RegistryObject<Block> PALM_FENCE_GATE = registerFuelBlock("palm_fence_gate",
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(ModBlocks.PALM_PLANKS.get()),
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

	public static final RegistryObject<Block> PALM_DOOR = registerFuelBlock("palm_door",
			() -> new DoorBlock(BlockBehaviour.Properties.copy(ModBlocks.PALM_PLANKS.get()).noOcclusion(),
					BlockSetType.OAK) {
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

	public static final RegistryObject<Block> PALM_TRAPDOOR = registerFuelBlock("palm_trapdoor",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(ModBlocks.PALM_PLANKS.get()).noOcclusion(),
					BlockSetType.OAK) {
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

	// Blueberry crop
	public static final RegistryObject<Block> BLUEBERRY_CROP = BLOCKS.register("blueberry_crop",
			() -> new BlueberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

	// Modded Torchflower
	public static final RegistryObject<Block> ILLUMINATED_TORCHFLOWER = registerBlock("illuminated_torchflower",
			() -> new FlowerBlock(() -> MobEffects.GLOWING, 5,
					BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER).noOcclusion().noCollission()
							.emissiveRendering((state, getter, position) -> true).lightLevel(state -> 14)));

	public static final RegistryObject<Block> POTTED_ILLUMINATED_TORCHFLOWER = BLOCKS.register(
			"potted_illuminated_torchflower",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.ILLUMINATED_TORCHFLOWER,
					BlockBehaviour.Properties.copy(Blocks.POTTED_TORCHFLOWER).noOcclusion()
							.emissiveRendering((state, getter, position) -> true).lightLevel(state -> 12)));

	// Amethyst lantern
	public static final RegistryObject<Block> CITRINE_LANTERN = registerBlock("citrine_lantern",
			() -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)));

	// Citrine bracket
	public static final RegistryObject<Block> CITRINE_BRACKET = BLOCKS.register("citrine_bracket",
			() -> new GemBracketBlock(
					BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> {
						return 14;
					}).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY),
					new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.AIR.defaultBlockState())));
	public static final RegistryObject<Block> CITRINE_WALL_BRACKET = BLOCKS.register("amethyst_wall_bracket",
			() -> new WallGemBracketBlock(
					BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50886_) -> {
						return 14;
					}).sound(SoundType.WOOD).lootFrom(() -> CITRINE_BRACKET.get()).pushReaction(PushReaction.DESTROY),
					new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.AIR.defaultBlockState())));

	// Villager workstation blocks
	public static final RegistryObject<Block> CARPENTRY_TABLE = registerBlock("carpentry_table",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

	// Nimbula Polyp
	public static final RegistryObject<Block> NIMBULA_POLYP = registerBlock("nimbula_polyp",
			() -> new NimbulaPolypBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.POWDER_SNOW)
					.randomTicks().noOcclusion()));

	// Unobtainable blocks, to be used for villager workstations that shouldn't
	// exist.
	// For the gemsmith
	public static final RegistryObject<Block> UNOBTAINIUM_GEM = registerBlock("unobtainium_gem",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)));

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