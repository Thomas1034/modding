package com.thomas.verdant.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.custom.CassavaCropBlock;
import com.thomas.verdant.block.custom.CoffeeCropBlock;
import com.thomas.verdant.block.custom.FragileFlammableRotatedPillarBlock;
import com.thomas.verdant.block.custom.FrameBlock;
import com.thomas.verdant.block.custom.HoeRemovableItemBlock;
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
import com.thomas.verdant.block.custom.WaterHemlockBlock;
import com.thomas.verdant.block.custom.WildCoffeeBlock;
import com.thomas.verdant.block.custom.WiltedVerdantLeavesBlock;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.item.ModItems;
import com.thomas.verdant.util.BurnTimes;
import com.thomas.verdant.woodset.WoodSet;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
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
	public static final WoodSet VERDANT_HEARTWOOD = new WoodSet(ModBlocks.BLOCKS, ModItems.ITEMS,
			ModBlockEntities.BLOCK_ENTITIES, Verdant.MOD_ID, "verdant_heartwood",
			() -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS)
					.strength(4.0F, 6.0F).sound(SoundType.WOOD).ignitedByLava(),
			2.0f, 5, 2);

	public static final WoodSet VERDANT = new WoodSet(ModBlocks.BLOCKS, ModItems.ITEMS, ModBlockEntities.BLOCK_ENTITIES,
			Verdant.MOD_ID, "verdant",
			() -> BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE)
					.instrument(NoteBlockInstrument.BASS).strength(2.0f).sound(SoundType.WOOD).ignitedByLava(),
			2.0f, 15, 50);
	// Decorative flowers
	public static final RegistryObject<Block> BLEEDING_HEART = registerBlockWithItem("bleeding_heart",
			() -> new FlowerBlock(() -> ModMobEffects.FOOD_POISONING.get(), 40,
					BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noOcclusion().noCollission()));

	// Cassava
	public static final RegistryObject<Block> CASSAVA_CROP = registerBlockOnly("cassava_crop",
			() -> new CassavaCropBlock(BlockBehaviour.Properties.copy(Blocks.POTATOES),
					(state) -> (state.is(ModBlocks.CASSAVA_ROOTED_DIRT.get())
							|| state.is(ModBlocks.BITTER_CASSAVA_ROOTED_DIRT.get())),
					() -> ModBlocks.CASSAVA_ROOTED_DIRT.get().defaultBlockState(), ModItems.CASSAVA_CUTTINGS));

	public static final RegistryObject<Block> BITTER_CASSAVA_CROP = registerBlockOnly("bitter_cassava_crop",
			() -> new CassavaCropBlock(BlockBehaviour.Properties.copy(Blocks.POTATOES),
					(state) -> (state.is(ModBlocks.CASSAVA_ROOTED_DIRT.get())
							|| state.is(ModBlocks.BITTER_CASSAVA_ROOTED_DIRT.get())),
					() -> ModBlocks.BITTER_CASSAVA_ROOTED_DIRT.get().defaultBlockState(),
					ModItems.BITTER_CASSAVA_CUTTINGS));

	@SuppressWarnings("resource")
	public static final RegistryObject<Block> CASSAVA_ROOTED_DIRT = registerBlockWithItem("cassava_rooted_dirt",
			() -> new HoeRemovableItemBlock(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT), (context) -> {
				int base = 4;
				int bonus = 3 * context.getItemInHand().getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
				return new ItemStack(ModItems.CASSAVA.get(),
						context.getLevel().random.nextIntBetweenInclusive(base, 2 * base + bonus));
			}, (context) -> {
				return Blocks.DIRT.defaultBlockState();
			}));

	@SuppressWarnings("resource")
	public static final RegistryObject<Block> BITTER_CASSAVA_ROOTED_DIRT = registerBlockWithItem(
			"bitter_cassava_rooted_dirt",
			() -> new HoeRemovableItemBlock(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT), (context) -> {

				int bonus = 3 * context.getItemInHand().getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
				int base = 6;

				if (context.getLevel().getRandom().nextFloat() < (1f / 32f)) {
					return new ItemStack(ModItems.CASSAVA.get(),
							context.getLevel().random.nextIntBetweenInclusive(base / 2, base + bonus / 2));
				} else {

					return new ItemStack(ModItems.BITTER_CASSAVA.get(),
							context.getLevel().random.nextIntBetweenInclusive(base, 2 * base + bonus));
				}
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

	public static final RegistryObject<Block> STRIPPED_VERDANT_LOG = VERDANT.getStrippedLog();

	public static final RegistryObject<Block> STRIPPED_VERDANT_WOOD = VERDANT.getStrippedWood();

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

	public static final RegistryObject<Block> VERDANT_BUTTON = VERDANT.getButton();

	public static final RegistryObject<Block> VERDANT_CLAY_GRASS_BLOCK = registerBlockWithItem(
			"verdant_clay_grass_block", () -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	// Jungle heart block
	public static final RegistryObject<Block> VERDANT_CONDUIT = registerBlockWithItem("verdant_conduit",
			() -> new VerdantConduitBlock(BlockBehaviour.Properties.copy(Blocks.BEACON)));

	public static final RegistryObject<Block> VERDANT_DOOR = VERDANT.getDoor();

	public static final RegistryObject<Block> VERDANT_FENCE = VERDANT.getFence();

	public static final RegistryObject<Block> VERDANT_FENCE_GATE = VERDANT.getFenceGate();

	public static final RegistryObject<Block> VERDANT_GRASS_BLOCK = registerBlockWithItem("verdant_grass_block",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	public static final RegistryObject<Block> VERDANT_HANGING_SIGN = VERDANT.getHangingSign();

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

	public static final RegistryObject<Block> VERDANT_LOG = VERDANT.getLog();

	public static final RegistryObject<Block> VERDANT_MUD_GRASS_BLOCK = registerBlockWithItem("verdant_mud_grass_block",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	// Verdant planks
	public static final RegistryObject<Block> VERDANT_PLANKS = VERDANT.getPlanks();

	public static final RegistryObject<Block> VERDANT_PRESSURE_PLATE = VERDANT.getPressurePlate();

	public static final RegistryObject<Block> VERDANT_ROOTED_CLAY = registerBlockWithItem("verdant_rooted_clay",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	// Verdant grass
	public static final RegistryObject<Block> VERDANT_ROOTED_DIRT = registerBlockWithItem("verdant_rooted_dirt",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	public static final RegistryObject<Block> VERDANT_ROOTED_MUD = registerBlockWithItem("verdant_rooted_mud",
			() -> new VerdantRootedDirtBlock(ModBlockProperties.VERDANT_ROOTS));

	// Verdant signs
	public static final RegistryObject<Block> VERDANT_SIGN = VERDANT.getSign();

	public static final RegistryObject<Block> VERDANT_SLAB = VERDANT.getSlab();

	// Verdant plank items
	public static final RegistryObject<Block> VERDANT_STAIRS = VERDANT.getStairs();

	// Tendril
	public static final RegistryObject<Block> VERDANT_TENDRIL = registerBlockWithItem("verdant_tendril",
			() -> new VerdantTendrilBlock(BlockBehaviour.Properties.copy(Blocks.KELP)));

	public static final RegistryObject<Block> VERDANT_TENDRIL_PLANT = registerBlockOnly("verdant_tendril_plant",
			() -> new VerdantTendrilPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT)));

	public static final RegistryObject<Block> VERDANT_TRAPDOOR = VERDANT.getTrapdoor();

	// Verdant vine
	public static final RegistryObject<Block> VERDANT_VINE = registerBlockWithItem("verdant_vine",
			() -> new VerdantVineBlock(
					BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).pushReaction(PushReaction.DESTROY).randomTicks()
							.isViewBlocking((state, level, pos) -> false).noOcclusion()));

	public static final RegistryObject<Block> VERDANT_WALL_HANGING_SIGN = VERDANT.getWallHangingSign();

	public static final RegistryObject<Block> VERDANT_WALL_SIGN = VERDANT.getWallSign();

	public static final RegistryObject<Block> VERDANT_WOOD = VERDANT.getWood();

	public static final RegistryObject<Block> WILD_COFFEE = registerBlockWithItem("wild_coffee",
			() -> new WildCoffeeBlock(() -> ModMobEffects.CAFFEINATED.get(), 25,
					BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noOcclusion().noCollission()));

	public static final RegistryObject<Block> WILTED_VERDANT_LEAVES = registerBlockWithItem("wilted_verdant_leaves",
			() -> new WiltedVerdantLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks()));

	public static final RegistryObject<Block> WATER_HEMLOCK = registerBlockOnly("water_hemlock",
			() -> new WaterHemlockBlock(BlockBehaviour.Properties.copy(Blocks.SMALL_DRIPLEAF)));

	public static final RegistryObject<Block> WILD_CASSAVA = registerBlockWithItem("wild_cassava",
			() -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion().instabreak()
					.offsetType(OffsetType.XZ)));

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

	@SuppressWarnings("unused")
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