package com.thomas.verdant.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.custom.CassavaCropBlock;
import com.thomas.verdant.block.custom.CoffeeCropBlock;
import com.thomas.verdant.block.custom.FragileFlammableRotatedPillarBlock;
import com.thomas.verdant.block.custom.FrameBlock;
import com.thomas.verdant.block.custom.HoeRemovableItemBlock;
import com.thomas.verdant.block.custom.ModFlammableRotatedPillarBlock;
import com.thomas.verdant.block.custom.ModHangingSignBlock;
import com.thomas.verdant.block.custom.ModStandingSignBlock;
import com.thomas.verdant.block.custom.ModWallHangingSignBlock;
import com.thomas.verdant.block.custom.ModWallSignBlock;
import com.thomas.verdant.block.custom.PoisonIvyBlock;
import com.thomas.verdant.block.custom.PoisonVerdantLeavesBlock;
import com.thomas.verdant.block.custom.PoisonVerdantTendrilBlock;
import com.thomas.verdant.block.custom.PoisonVerdantTendrilPlantBlock;
import com.thomas.verdant.block.custom.RopeBlock;
import com.thomas.verdant.block.custom.SpikesBlock;
import com.thomas.verdant.block.custom.StinkingBlossomBlock;
import com.thomas.verdant.block.custom.ThornBushBlock;
import com.thomas.verdant.block.custom.ThornyVerdantLeavesBlock;
import com.thomas.verdant.block.custom.VerdantConduitBlock;
import com.thomas.verdant.block.custom.VerdantLeafyVineBlock;
import com.thomas.verdant.block.custom.VerdantLeavesBlock;
import com.thomas.verdant.block.custom.VerdantRootedDirtBlock;
import com.thomas.verdant.block.custom.VerdantTendrilBlock;
import com.thomas.verdant.block.custom.VerdantTendrilPlantBlock;
import com.thomas.verdant.block.custom.VerdantVineBlock;
import com.thomas.verdant.block.custom.WildCoffeeBlock;
import com.thomas.verdant.block.custom.WiltedVerdantLeavesBlock;
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
import net.minecraft.world.item.enchantment.Enchantments;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Verdant.MOD_ID);

	// Register the Verdant heartwood set.
	public static final WoodSet VERDANT_HEARTWOOD = new WoodSet(ModBlocks.BLOCKS, ModItems.ITEMS, Verdant.MOD_ID,
			"verdant_heartwood", () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
					.instrument(NoteBlockInstrument.BASS).strength(4.0F, 6.0F).sound(SoundType.WOOD).ignitedByLava(),
			2.0f, 5, 2);

	// Decorative flowers
	public static final RegistryObject<Block> BLEEDING_HEART = registerBlockWithItem("bleeding_heart",
			() -> new FlowerBlock(() -> ModMobEffects.FOOD_POISONING.get(), 40,
					BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noOcclusion().noCollission()));

	// Cassava
	public static final RegistryObject<Block> CASSAVA_CROP = registerBlockWithItem("cassava_crop",
			() -> new CassavaCropBlock(BlockBehaviour.Properties.copy(Blocks.POTATOES)));

	@SuppressWarnings("resource")
	public static final RegistryObject<Block> CASSAVA_ROOTED_DIRT = registerBlockWithItem("cassava_rooted_dirt",
			() -> new HoeRemovableItemBlock(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT), (context) -> {
				int bonus = 2 * context.getItemInHand().getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
				int base = 6;
				return new ItemStack(ModItems.CASSAVA.get(),
						context.getLevel().random.nextIntBetweenInclusive(base, 2 * base + bonus));
			}, (context) -> {
				return Blocks.DIRT.defaultBlockState();
			}));

	// Coffee crop
	public static final RegistryObject<Block> COFFEE_CROP = BLOCKS.register("coffee_crop", () -> new CoffeeCropBlock(
			BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion().noCollission()));

	// Dirt ores
	public static final RegistryObject<Block> DIRT_COAL_ORE = registerBlockWithItem("dirt_coal_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));

	public static final RegistryObject<Block> DIRT_COPPER_ORE = registerBlockWithItem("dirt_copper_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));

	public static final RegistryObject<Block> DIRT_DIAMOND_ORE = registerBlockWithItem("dirt_diamond_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));

	public static final RegistryObject<Block> DIRT_EMERALD_ORE = registerBlockWithItem("dirt_emerald_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));

	public static final RegistryObject<Block> DIRT_GOLD_ORE = registerBlockWithItem("dirt_gold_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));

	public static final RegistryObject<Block> DIRT_IRON_ORE = registerBlockWithItem("dirt_iron_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> DIRT_LAPIS_ORE = registerBlockWithItem("dirt_lapis_ore",
			() -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));

	public static final RegistryObject<Block> DIRT_REDSTONE_ORE = registerBlockWithItem("dirt_redstone_ore",
			() -> new RedStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.75f)));
	public static final RegistryObject<Block> FRAME_BLOCK = registerFuelBlockWithItem("frame_block",
			() -> new FrameBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)
					.isViewBlocking((state, level, pos) -> false).noOcclusion().instabreak()),
			BurnTimes.PLANKS);

	public static final RegistryObject<Block> LEAFY_VERDANT_VINE = registerBlockWithItem("leafy_verdant_vine",
			() -> new VerdantLeafyVineBlock(
					BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).pushReaction(PushReaction.DESTROY).randomTicks()
							.isViewBlocking((state, level, pos) -> false).noOcclusion()));
	// Poison ivy
	public static final RegistryObject<Block> POISON_IVY = registerBlockWithItem("poison_ivy",
			() -> new PoisonVerdantTendrilBlock(BlockBehaviour.Properties.copy(Blocks.KELP)));

	// Poison ivy block
	public static final RegistryObject<Block> POISON_IVY_BLOCK = registerBlockWithItem("poison_ivy_block",
			() -> new PoisonIvyBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));

	public static final RegistryObject<Block> POISON_IVY_PLANT = registerBlockOnly("poison_ivy_plant",
			() -> new PoisonVerdantTendrilPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT)));

	public static final RegistryObject<Block> POISON_IVY_VERDANT_LEAVES = registerBlockWithItem(
			"poison_ivy_verdant_leaves",
			() -> new PoisonVerdantLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks()));

	public static final RegistryObject<Block> POTTED_BLEEDING_HEART = registerBlockOnly("potted_bleeding_heart",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.BLEEDING_HEART,
					BlockBehaviour.Properties.copy(Blocks.POTTED_BLUE_ORCHID).noOcclusion()));

	public static final RegistryObject<Block> POTTED_THORN_BUSH = registerBlockOnly("potted_thorn_bush",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.THORN_BUSH,
					BlockBehaviour.Properties.copy(Blocks.POTTED_BLUE_ORCHID).noOcclusion()));

	public static final RegistryObject<Block> POTTED_WILD_COFFEE = registerBlockOnly("potted_wild_coffee",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.WILD_COFFEE,
					BlockBehaviour.Properties.copy(Blocks.POTTED_BLUE_ORCHID).noOcclusion()));

	// Rope block
	public static final RegistryObject<Block> ROPE = registerBlockWithItem("rope",
			() -> new RopeBlock(BlockBehaviour.Properties.copy(Blocks.KELP).sound(SoundType.WOOL)));

	// Rotten wood.
	public static final RegistryObject<Block> ROTTEN_WOOD = registerFuelBlockWithItem("rotten_wood",
			() -> new FragileFlammableRotatedPillarBlock(
					BlockBehaviour.Properties.copy(Blocks.OAK_LOG).instabreak()
							.isViewBlocking((state, level, pos) -> false).noOcclusion().explosionResistance(0.015625f),
					35, 1, 2.0f),
			BurnTimes.STICK);

	public static final RegistryObject<Block> STINKING_BLOSSOM = registerBlockWithItem("stinking_blossom",
			() -> new StinkingBlossomBlock(BlockBehaviour.Properties.copy(Blocks.SPORE_BLOSSOM).noOcclusion()));

	public static final RegistryObject<Block> STRIPPED_VERDANT_HEARTWOOD_LOG = VERDANT_HEARTWOOD.getStrippedLog();

	public static final RegistryObject<Block> STRIPPED_VERDANT_HEARTWOOD_WOOD = VERDANT_HEARTWOOD.getStrippedWood();

	public static final RegistryObject<Block> STRIPPED_VERDANT_LOG = registerFuelBlockWithItem("stripped_verdant_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
			BurnTimes.LOG);

	public static final RegistryObject<Block> STRIPPED_VERDANT_WOOD = registerFuelBlockWithItem("stripped_verdant_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)),
			BurnTimes.LOG);

	// Thorn bush
	public static final RegistryObject<Block> THORN_BUSH = registerBlockWithItem("thorn_bush", () -> new ThornBushBlock(
			BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion().strength(0.5F), 2.0f));

	// Thorns
	public static final RegistryObject<Block> THORN_SPIKES = registerBlockWithItem("thorn_spikes",
			() -> new SpikesBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH).noOcclusion().strength(0.95F),
					3.0f));

	public static final RegistryObject<Block> THORNY_VERDANT_LEAVES = registerBlockWithItem("thorny_verdant_leaves",
			() -> new ThornyVerdantLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks()));

	public static final RegistryObject<Block> TOXIC_ASH_BLOCK = registerBlockWithItem("toxic_ash_block",
			() -> new FragileFlammableRotatedPillarBlock(
					BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK).sound(SoundType.SAND).strength(0.1f), 4.0f));

	public static final RegistryObject<Block> VERDANT_BUTTON = registerFuelBlockWithItem("verdant_button",
			() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), ModBlockSetType.VERDANT, 10,
					true) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.BUTTON);
	public static final RegistryObject<Block> VERDANT_CLAY_GRASS_BLOCK = registerBlockWithItem(
			"verdant_clay_grass_block", () -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	// Jungle heart block
	public static final RegistryObject<Block> VERDANT_CONDUIT = registerBlockWithItem("verdant_conduit",
			() -> new VerdantConduitBlock(BlockBehaviour.Properties.copy(Blocks.BEACON)));
	public static final RegistryObject<Block> VERDANT_DOOR = registerFuelBlockWithItem("verdant_door",
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), ModBlockSetType.VERDANT) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.DOOR);

	public static final RegistryObject<Block> VERDANT_FENCE = registerFuelBlockWithItem("verdant_fence",
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.FENCE);

	public static final RegistryObject<Block> VERDANT_FENCE_GATE = registerFuelBlockWithItem("verdant_fence_gate",
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), SoundEvents.FENCE_GATE_OPEN,
					SoundEvents.FENCE_GATE_CLOSE) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.FENCE_GATE);

	public static final RegistryObject<Block> VERDANT_GRASS_BLOCK = registerBlockWithItem("verdant_grass_block",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	public static final RegistryObject<Block> VERDANT_HANGING_SIGN = registerBlockOnly("verdant_hanging_sign",
			() -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN),
					ModWoodTypes.VERDANT));

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_BUTTON = VERDANT_HEARTWOOD.getButton();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_DOOR = VERDANT_HEARTWOOD.getDoor();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_FENCE = VERDANT_HEARTWOOD.getFence();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_FENCE_GATE = VERDANT_HEARTWOOD.getFenceGate();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_HANGING_SIGN = VERDANT_HEARTWOOD.getHangingSign();

	// Heartwood variants
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_LOG = VERDANT_HEARTWOOD.getLog();

	// Verdant heartwood planks
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_PLANKS = VERDANT_HEARTWOOD.getPlanks();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_PRESSURE_PLATE = VERDANT_HEARTWOOD.getPressurePlate();

	// Verdant signs
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_SIGN = VERDANT_HEARTWOOD.getSign();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_SLAB = VERDANT_HEARTWOOD.getSlab();

	// Verdant plank items
	public static final RegistryObject<Block> VERDANT_HEARTWOOD_STAIRS = VERDANT_HEARTWOOD.getStairs();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_TRAPDOOR = VERDANT_HEARTWOOD.getTrapdoor();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_WALL_HANGING_SIGN = VERDANT_HEARTWOOD
			.getWallHangingSign();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_WALL_SIGN = VERDANT_HEARTWOOD.getWallSign();

	public static final RegistryObject<Block> VERDANT_HEARTWOOD_WOOD = VERDANT_HEARTWOOD.getWood();

	public static final RegistryObject<Block> VERDANT_LEAVES = registerBlockWithItem("verdant_leaves",
			() -> new VerdantLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks()));

	public static final RegistryObject<Block> VERDANT_LOG = registerFuelBlockWithItem("verdant_log",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), BurnTimes.LOG);

	public static final RegistryObject<Block> VERDANT_MUD_GRASS_BLOCK = registerBlockWithItem("verdant_mud_grass_block",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	// Verdant planks
	public static final RegistryObject<Block> VERDANT_PLANKS = registerFuelBlockWithItem("verdant_planks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.PLANKS);

	public static final RegistryObject<Block> VERDANT_PRESSURE_PLATE = registerFuelBlockWithItem(
			"verdant_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
					BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), ModBlockSetType.VERDANT) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.PRESSURE_PLATE);

	public static final RegistryObject<Block> VERDANT_ROOTED_CLAY = registerBlockWithItem("verdant_rooted_clay",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	// Verdant grass
	public static final RegistryObject<Block> VERDANT_ROOTED_DIRT = registerBlockWithItem("verdant_rooted_dirt",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	public static final RegistryObject<Block> VERDANT_ROOTED_MUD = registerBlockWithItem("verdant_rooted_mud",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	// Verdant signs
	public static final RegistryObject<Block> VERDANT_SIGN = registerBlockOnly("verdant_sign",
			() -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.VERDANT));

	public static final RegistryObject<Block> VERDANT_SLAB = registerFuelBlockWithItem("verdant_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.SLAB);

	// Verdant plank items
	public static final RegistryObject<Block> VERDANT_STAIRS = registerFuelBlockWithItem("verdant_stairs",
			() -> new StairBlock(() -> ModBlocks.VERDANT_PLANKS.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.STAIRS);

	// Tendril
	public static final RegistryObject<Block> VERDANT_TENDRIL = registerBlockWithItem("verdant_tendril",
			() -> new VerdantTendrilBlock(BlockBehaviour.Properties.copy(Blocks.KELP)));

	public static final RegistryObject<Block> VERDANT_TENDRIL_PLANT = registerBlockOnly("verdant_tendril_plant",
			() -> new VerdantTendrilPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT)));

	public static final RegistryObject<Block> VERDANT_TRAPDOOR = registerFuelBlockWithItem("verdant_trapdoor",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), ModBlockSetType.VERDANT) {
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 10;
				}

				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
			}, BurnTimes.TRAPDOOR);

	// Verdant vine
	public static final RegistryObject<Block> VERDANT_VINE = registerBlockWithItem("verdant_vine",
			() -> new VerdantVineBlock(
					BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).pushReaction(PushReaction.DESTROY).randomTicks()
							.isViewBlocking((state, level, pos) -> false).noOcclusion()));

	public static final RegistryObject<Block> VERDANT_WALL_HANGING_SIGN = registerBlockOnly("verdant_wall_hanging_sign",
			() -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN),
					ModWoodTypes.VERDANT));

	public static final RegistryObject<Block> VERDANT_WALL_SIGN = registerBlockOnly("verdant_wall_sign",
			() -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.VERDANT));

	public static final RegistryObject<Block> VERDANT_WOOD = registerFuelBlockWithItem("verdant_wood",
			() -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), BurnTimes.LOG);

	public static final RegistryObject<Block> WILD_COFFEE = registerBlockWithItem("wild_coffee",
			() -> new WildCoffeeBlock(() -> ModMobEffects.CAFFEINATED.get(), 25,
					BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noOcclusion().noCollission()));
	// Verdant logs
	public static final RegistryObject<Block> WILTED_VERDANT_LEAVES = registerBlockWithItem("wilted_verdant_leaves",
			() -> new WiltedVerdantLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks()));

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

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

	private static <T extends Block> RegistryObject<T> registerFireproofBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerFireproofBlockItem(name, toReturn);
		return toReturn;
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

	private static <T extends Block> RegistryObject<T> registerFuelBlockWithItem(String name, Supplier<T> block,
			int burnTime) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerFuelBlockItem(name, toReturn, burnTime);
		return toReturn;
	}
}