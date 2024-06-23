package com.thomas.verdant.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.custom.ModFlammableRotatedPillarBlock;
import com.thomas.verdant.block.custom.ModStandingSignBlock;
import com.thomas.verdant.block.custom.ModWallHangingSignBlock;
import com.thomas.verdant.block.custom.ModWallSignBlock;
import com.thomas.verdant.util.ModBlockSetType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
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
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

// WIP TODO

// TODO fix burn times.
public class WoodSet {

	// The registries to link to
	protected final DeferredRegister<Block> blockRegister;
	protected final DeferredRegister<Item> itemRegister;

	// The name of the wood set
	protected final String baseName;

	// The wood type of the wood set
	protected final WoodType woodType;
	// The block type of the wood set
	protected final BlockSetType blockSetType;

	// The base properties
	protected final Supplier<BlockBehaviour.Properties> baseProperties;
	protected final float burnTimeMultiplier;
	protected final int flammability;
	protected final int fireSpreadSpeed;

	// The blocks created
	protected RegistryObject<Block> log;
	protected RegistryObject<Block> wood;
	protected RegistryObject<Block> strippedLog;
	protected RegistryObject<Block> strippedWood;
	protected RegistryObject<Block> planks;
	protected RegistryObject<Block> slab;
	protected RegistryObject<Block> stairs;
	protected RegistryObject<Block> fence;
	protected RegistryObject<Block> fenceGate;
	protected RegistryObject<Block> sign;
	protected RegistryObject<Block> wallSign;
	protected RegistryObject<Item> signItem;
	protected RegistryObject<Block> hangingSign;
	protected RegistryObject<Block> wallHangingSign;
	protected RegistryObject<Item> hangingSignItem;
	protected RegistryObject<Block> button;
	protected RegistryObject<Block> pressurePlate;
	protected RegistryObject<Block> door;
	protected RegistryObject<Block> trapdoor;

	// Supplier must supply a new instance per call.
	public WoodSet(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, String modid,
			String baseName, WoodType woodType, Supplier<BlockBehaviour.Properties> baseProperties,
			float burnTimeMultiplier, int flammability, int fireSpreadSpeed) {
		this.blockRegister = blockRegister;
		this.itemRegister = itemRegister;

		this.baseName = baseName;
		this.blockSetType = BlockSetType.register(new BlockSetType(baseName));
		this.woodType = WoodType.register(new WoodType(modid + ":" + baseName, this.blockSetType));

		this.baseProperties = baseProperties;
		this.burnTimeMultiplier = burnTimeMultiplier;
		this.flammability = flammability;
		this.fireSpreadSpeed = fireSpreadSpeed;
	}

	protected void registerBlocks() {
		int flammability = this.flammability;
		int fireSpreadSpeed = this.fireSpreadSpeed;

		this.log = this
				.registerFuelBlockWithItem(logName(this.baseName),
						() -> new ModFlammableRotatedPillarBlock(logProperties(this.baseProperties.get()),
								this.flammability, this.fireSpreadSpeed),
						(int) (this.burnTimeMultiplier * BurnTimes.LOG));

		this.wood = this
				.registerFuelBlockWithItem(woodName(this.baseName),
						() -> new ModFlammableRotatedPillarBlock(logProperties(this.baseProperties.get()),
								this.flammability, this.fireSpreadSpeed),
						(int) (this.burnTimeMultiplier * BurnTimes.LOG));

		this.strippedLog = this
				.registerFuelBlockWithItem(strippedLogName(this.baseName),
						() -> new ModFlammableRotatedPillarBlock(logProperties(this.baseProperties.get()),
								this.flammability, this.fireSpreadSpeed),
						(int) (this.burnTimeMultiplier * BurnTimes.LOG));

		this.strippedWood = this
				.registerFuelBlockWithItem(strippedWoodName(this.baseName),
						() -> new ModFlammableRotatedPillarBlock(logProperties(this.baseProperties.get()),
								this.flammability, this.fireSpreadSpeed),
						(int) (this.burnTimeMultiplier * BurnTimes.LOG));

		this.planks = this.registerFuelBlockWithItem(planksName(this.baseName),
				() -> new Block(planksProperties(this.baseProperties.get())) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return fireSpreadSpeed;

					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability > 0;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.PLANKS));

		this.slab = registerFuelBlockWithItem(slabName(this.baseName),
				() -> new SlabBlock(slabProperties(this.baseProperties.get())) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return fireSpreadSpeed;
					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability > 0;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.SLAB));

		this.stairs = this.registerFuelBlockWithItem(stairsName(this.baseName),
				() -> new StairBlock(() -> this.planks.get().defaultBlockState(),
						stairsProperties(this.baseProperties.get())) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return fireSpreadSpeed;
					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability > 0;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.STAIRS));

		this.fence = this.registerFuelBlockWithItem(fenceName(this.baseName),
				() -> new FenceBlock(fenceProperties(this.baseProperties.get())) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return fireSpreadSpeed;
					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability > 0;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.FENCE));

		this.fenceGate = this.registerFuelBlockWithItem(fenceGateName(this.baseName),
				() -> new FenceGateBlock(fenceGateProperties(this.baseProperties.get()), SoundEvents.FENCE_GATE_OPEN,
						SoundEvents.FENCE_GATE_CLOSE) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return fireSpreadSpeed;
					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability > 0;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.FENCE_GATE));

		this.sign = this.registerBlockOnly(signName(this.baseName),
				() -> new ModStandingSignBlock(signProperties(this.baseProperties.get()), this.woodType));

		this.wallSign = this.registerBlockOnly(wallSignName(this.baseName),
				() -> new ModWallSignBlock(wallSignProperties(this.baseProperties.get()), this.woodType));

		this.hangingSign = this.registerBlockOnly(hangingSignName(this.baseName),
				() -> new ModWallHangingSignBlock(hangingSignProperties(this.baseProperties.get()), this.woodType));

		this.wallHangingSign = this.registerBlockOnly(wallHangingSignName(this.baseName),
				() -> new ModWallHangingSignBlock(wallHangingSignProperties(this.baseProperties.get()), this.woodType));

		this.button = registerFuelBlockWithItem(buttonName(this.baseName),
				() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON),
						ModBlockSetType.VERDANT_HEARTWOOD, 20, true) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return 5;
					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return 2;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return true;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.BUTTON));

		this.pressurePlate = registerFuelBlockWithItem(pressurePlateName(this.baseName),
				() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS,
						pressurePlateProperties(this.baseProperties.get()), this.blockSetType) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return fireSpreadSpeed;
					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability > 0;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.PRESSURE_PLATE));

		this.trapdoor = registerFuelBlockWithItem(trapdoorName(this.baseName),
				() -> new TrapDoorBlock(trapdoorProperties(this.baseProperties.get()), this.blockSetType) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return fireSpreadSpeed;
					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability > 0;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.TRAPDOOR));

		this.door = registerFuelBlockWithItem(doorName(this.baseName),
				() -> new DoorBlock(doorProperties(this.baseProperties.get()), this.blockSetType) {
					@Override
					public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos,
							Direction direction) {
						return fireSpreadSpeed;
					}

					@Override
					public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability;
					}

					@Override
					public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
						return flammability > 0;
					}
				}, (int) (this.burnTimeMultiplier * BurnTimes.TRAPDOOR));
	}

	protected void registerItems() {

		this.signItem = this.registerItem(signName(this.baseName),
				() -> new SignItem(new Item.Properties().stacksTo(16), this.sign.get(), this.wallSign.get()));

		this.hangingSignItem = this.registerItem(hangingSignName(this.baseName),
				() -> new HangingSignItem(this.hangingSign.get(), this.wallHangingSign.get(),
						new Item.Properties().stacksTo(16)));
	}

	protected void generateData() {

//		logBlock((RotatedPillarBlock) ModBlocks.VERDANT_HEARTWOOD_LOG.get());
//		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get());
//		logBlock((RotatedPillarBlock) ModBlocks.VERDANT_HEARTWOOD_WOOD.get());
//		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get());
//		blockWithItem(ModBlocks.VERDANT_HEARTWOOD_PLANKS);
//		stairsBlock(((StairBlock) ModBlocks.VERDANT_HEARTWOOD_STAIRS.get()),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
//		slabBlock(((SlabBlock) ModBlocks.VERDANT_HEARTWOOD_SLAB.get()),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
//
//		buttonBlock(((ButtonBlock) ModBlocks.VERDANT_HEARTWOOD_BUTTON.get()),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
//		pressurePlateBlock(((PressurePlateBlock) ModBlocks.VERDANT_HEARTWOOD_PRESSURE_PLATE.get()),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
//
//		fenceBlock(((FenceBlock) ModBlocks.VERDANT_HEARTWOOD_FENCE.get()),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
//		fenceGateBlock(((FenceGateBlock) ModBlocks.VERDANT_HEARTWOOD_FENCE_GATE.get()),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
//
//		doorBlockWithRenderType(((DoorBlock) ModBlocks.VERDANT_HEARTWOOD_DOOR.get()),
//				modLoc("block/verdant_heartwood_door_bottom"), modLoc("block/verdant_heartwood_door_top"), "cutout");
//		trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.VERDANT_HEARTWOOD_TRAPDOOR.get()),
//				modLoc("block/verdant_heartwood_trapdoor"), true, "cutout");
//
//		signBlock(((StandingSignBlock) ModBlocks.VERDANT_HEARTWOOD_SIGN.get()),
//				((WallSignBlock) ModBlocks.VERDANT_HEARTWOOD_WALL_SIGN.get()),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
//
//		hangingSignBlock(ModBlocks.VERDANT_HEARTWOOD_HANGING_SIGN.get(),
//				ModBlocks.VERDANT_HEARTWOOD_WALL_HANGING_SIGN.get(),
//				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));

	}

	public String getBaseName() {
		return baseName;
	}

	public WoodType getWoodType() {
		return woodType;
	}

	public Supplier<BlockBehaviour.Properties> getBaseProperties() {
		return baseProperties;
	}

	public float getBurnTimeMultiplier() {
		return burnTimeMultiplier;
	}

	public int getFlammability() {
		return flammability;
	}

	public int getFireSpreadSpeed() {
		return fireSpreadSpeed;
	}

	public RegistryObject<Block> getLog() {
		return log;
	}

	public RegistryObject<Block> getWood() {
		return wood;
	}

	public RegistryObject<Block> getStrippedLog() {
		return strippedLog;
	}

	public RegistryObject<Block> getStrippedWood() {
		return strippedWood;
	}

	public RegistryObject<Block> getPlanks() {
		return planks;
	}

	public RegistryObject<Block> getSlab() {
		return slab;
	}

	public RegistryObject<Block> getStairs() {
		return stairs;
	}

	public RegistryObject<Block> getFence() {
		return fence;
	}

	public RegistryObject<Block> getFenceGate() {
		return fenceGate;
	}

	public RegistryObject<Block> getSign() {
		return sign;
	}

	public RegistryObject<Block> getWallSign() {
		return wallSign;
	}

	public RegistryObject<Item> getSignItem() {
		return signItem;
	}

	public RegistryObject<Block> getHangingSign() {
		return hangingSign;
	}

	public RegistryObject<Block> getWallHangingSign() {
		return wallHangingSign;
	}

	public RegistryObject<Item> getHangingSignItem() {
		return hangingSignItem;
	}

	public RegistryObject<Block> getButton() {
		return button;
	}

	public RegistryObject<Block> getPressurePlate() {
		return pressurePlate;
	}

	public RegistryObject<Block> getDoor() {
		return door;
	}

	public RegistryObject<Block> getTrapdoor() {
		return trapdoor;
	}

	protected static Properties planksProperties(BlockBehaviour.Properties base) {
		return base;
	}

	protected static Properties logProperties(BlockBehaviour.Properties base) {
		return base;
	}

	protected static Properties slabProperties(BlockBehaviour.Properties base) {
		return base;
	}

	protected static Properties stairsProperties(BlockBehaviour.Properties base) {
		return base;
	}

	protected static Properties fenceProperties(BlockBehaviour.Properties base) {
		return base.forceSolidOn();
	}

	protected static Properties fenceGateProperties(BlockBehaviour.Properties base) {
		return base.forceSolidOn();
	}

	protected static Properties signProperties(BlockBehaviour.Properties base) {
		return base.forceSolidOn().noCollission().strength(1.0F);
	}

	protected static Properties wallSignProperties(BlockBehaviour.Properties base) {
		return base.forceSolidOn().noCollission().strength(1.0F);
	}

	protected static Properties hangingSignProperties(BlockBehaviour.Properties base) {
		return base.forceSolidOn().noCollission().strength(1.0F);
	}

	protected static Properties wallHangingSignProperties(BlockBehaviour.Properties base) {
		return base.forceSolidOn().noCollission().strength(1.0F);
	}

	protected static Properties buttonProperties(BlockBehaviour.Properties base) {
		return base.noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
	}

	protected static Properties pressurePlateProperties(BlockBehaviour.Properties base) {
		return base.forceSolidOn().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
	}

	protected static Properties trapdoorProperties(BlockBehaviour.Properties base) {
		return base.noOcclusion();
	}

	protected static Properties doorProperties(BlockBehaviour.Properties base) {
		return base.noOcclusion();
	}

	protected <T extends Block> RegistryObject<T> registerFuelBlockWithItem(String name, Supplier<T> block,
			int burnTime) {
		RegistryObject<T> toReturn = this.blockRegister.register(name, block);
		this.registerFuelBlockItem(name, toReturn, burnTime);
		return toReturn;
	}

	protected RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
		return this.itemRegister.register(name, item);
	}

	protected <T extends Block> RegistryObject<Item> registerFuelBlockItem(String name, RegistryObject<T> block,
			int burnTime) {
		return this.itemRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {
			@Override
			public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
				return burnTime;
			}
		});
	}

	protected <T extends Block> RegistryObject<T> registerBlockOnly(String name, Supplier<T> block) {
		return this.blockRegister.register(name, block);
	}

	protected static final String logName(String base) {
		return base + "_log";
	}

	protected static final String woodName(String base) {
		return base + "_wood";
	}

	protected static final String strippedLogName(String base) {
		return "stripped_" + base + "_log";
	}

	protected static final String strippedWoodName(String base) {
		return "stripped_" + base + "_wood";
	}

	protected static final String planksName(String base) {
		return base + "_planks";
	}

	protected static final String slabName(String base) {
		return base + "_slab";
	}

	protected static final String stairsName(String base) {
		return base + "_stairs";
	}

	protected static final String fenceName(String base) {
		return base + "_fence";
	}

	protected static final String fenceGateName(String base) {
		return base + "_fence_gate";
	}

	protected static final String signName(String base) {
		return base + "_sign";
	}

	protected static final String wallSignName(String base) {
		return base + "_wall_sign";
	}

	protected static final String hangingSignName(String base) {
		return base + "_hanging_sign";
	}

	protected static final String wallHangingSignName(String base) {
		return base + "_wall_hanging_sign";
	}

	protected static final String buttonName(String base) {
		return base + "_button";
	}

	protected static final String pressurePlateName(String base) {
		return base + "_pressure_plate";
	}

	protected static final String trapdoorName(String base) {
		return base + "_trapdoor";
	}

	protected static final String doorName(String base) {
		return base + "_door";
	}

	public static class BurnTimes {
		// The burn times of common items, in ticks.
		public static final int LOG = 300;
		public static final int PLANKS = 300;
		public static final int BUTTON = 100;
		public static final int STICK = 100;
		public static final int FENCE = 300;
		public static final int FENCE_GATE = 300;
		public static final int SLAB = 150;
		public static final int DOOR = 200;
		public static final int TRAPDOOR = 300;
		public static final int STAIRS = 300;
		public static final int PRESSURE_PLATE = 300;
		public static final int COAL = 1600;

	}
}
