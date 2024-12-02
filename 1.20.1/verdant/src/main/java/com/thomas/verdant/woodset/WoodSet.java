package com.thomas.verdant.woodset;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;
import com.thomas.verdant.datagen.DataParseableProvider;
import com.thomas.verdant.util.blocktransformers.BlockTransformer;
import com.thomas.verdant.util.data.DataAccessor;
import com.thomas.verdant.util.data.DataRegistries;
import com.thomas.verdant.util.function.Reflection;
import com.thomas.verdant.util.function.Reflection.CallableWithArgs;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// WIP TODO datagen
public class WoodSet {

	// The registries to link to
	protected final DeferredRegister<Block> blockRegister;
	protected final DeferredRegister<Item> itemRegister;
	protected final DeferredRegister<BlockEntityType<?>> blockEntityRegister;
	protected final DeferredRegister<EntityType<?>> entityRegister;

	// The name of the wood set
	public final String baseName;

	public final String modid;

	// The wood type of the wood set
	protected final WoodType woodType;
	// The block type of the wood set
	protected final BlockSetType blockSetType;

	// The base properties
	protected final Supplier<BlockBehaviour.Properties> baseProperties;
	protected final float burnTimeMultiplier;
	protected final int flammability;
	protected final int fireSpreadSpeed;
	protected final int buttonTime = 20;

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
	protected RegistryObject<Block> hangingSign;
	protected RegistryObject<Block> wallHangingSign;
	protected RegistryObject<Block> button;
	protected RegistryObject<Block> pressurePlate;
	protected RegistryObject<Block> door;
	protected RegistryObject<Block> trapdoor;

	// The items created
	protected RegistryObject<Item> signItem;
	protected RegistryObject<Item> hangingSignItem;
	protected RegistryObject<Item> boatItem;
	protected RegistryObject<Item> chestBoatItem;

	// The block entities created for the signs
	protected RegistryObject<BlockEntityType<WoodSet.WoodSetSignBlockEntity>> signBlockEntity;
	protected RegistryObject<BlockEntityType<WoodSet.WoodSetHangingSignBlockEntity>> hangingSignBlockEntity;

	// The block transformer to handle stripping.
	public final DataAccessor<BlockTransformer> stripping;

	// The entities for boats
	protected RegistryObject<EntityType<WoodSet.WoodSetBoatEntity>> boatEntity;
	protected RegistryObject<EntityType<WoodSet.WoodSetChestBoatEntity>> chestBoatEntity;

	// Supplier must supply a new instance per call.
	public WoodSet(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister,
			DeferredRegister<BlockEntityType<?>> blockEntityRegister, DeferredRegister<EntityType<?>> entityRegister,
			String modid, String baseName, Supplier<BlockBehaviour.Properties> baseProperties, float burnTimeMultiplier,
			int flammability, int fireSpreadSpeed) {
		this.blockRegister = blockRegister;
		this.itemRegister = itemRegister;
		this.blockEntityRegister = blockEntityRegister;
		this.entityRegister = entityRegister;

		this.baseName = baseName;
		this.modid = modid;
		this.blockSetType = BlockSetType.register(new BlockSetType(baseName));
		this.woodType = WoodType.register(new WoodType(modid + ":" + baseName, this.blockSetType));

		this.baseProperties = baseProperties;
		this.burnTimeMultiplier = burnTimeMultiplier;
		this.flammability = flammability;
		this.fireSpreadSpeed = fireSpreadSpeed;

		this.stripping = DataRegistries.BLOCK_TRANSFORMERS.register(this.modid, this.baseName + "_stripping");

		// Generate blocks and items.
		this.registerBlocks();
		this.registerItems();
		this.registerBlockEntities();
		this.registerEntities();

		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	protected void registerBER(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(this.signBlockEntity.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(this.hangingSignBlockEntity.get(), HangingSignRenderer::new);
	}

	@SubscribeEvent
	protected void registerDispenserBehaviors(FMLCommonSetupEvent event) {
		DispenserBlock.registerBehavior(this.boatItem.get(), new WoodSetBoatDispenseItemBehavior());
		DispenserBlock.registerBehavior(this.chestBoatItem.get(), new WoodSetBoatDispenseItemBehavior(true));
	}

	@SubscribeEvent
	protected void registerER(FMLClientSetupEvent event) {
		EntityRenderers.register(this.boatEntity.get(), context -> this.new WoodSetBoatRenderer(context, false));
		EntityRenderers.register(this.chestBoatEntity.get(), context -> this.new WoodSetBoatRenderer(context, true));
	}

	protected void registerBlocks() {

		this.log = this.registerConditionalFuelBlockWithItem(logName(this.baseName),
				() -> this.new Log(logProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.LOG));

		this.wood = this.registerConditionalFuelBlockWithItem(woodName(this.baseName),
				() -> this.new Log(logProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.LOG));

		this.strippedLog = this.registerConditionalFuelBlockWithItem(strippedLogName(this.baseName),
				() -> this.new Log(logProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.LOG));

		this.strippedWood = this.registerConditionalFuelBlockWithItem(strippedWoodName(this.baseName),
				() -> this.new Log(logProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.LOG));

		this.planks = this.registerConditionalFuelBlockWithItem(planksName(this.baseName),
				() -> this.new Planks(planksProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.PLANKS));

		this.slab = registerConditionalFuelBlockWithItem(slabName(this.baseName),
				() -> this.new Slab(slabProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.SLAB));

		this.stairs = this.registerConditionalFuelBlockWithItem(stairsName(this.baseName),
				() -> this.new Stair(() -> this.planks.get().defaultBlockState(),
						stairsProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.STAIRS));

		this.fence = this.registerConditionalFuelBlockWithItem(fenceName(this.baseName),
				() -> this.new Fence(fenceProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.FENCE));

		this.fenceGate = this.registerConditionalFuelBlockWithItem(fenceGateName(this.baseName),
				() -> this.new FenceGate(fenceGateProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.FENCE_GATE));

		this.sign = this.registerBlockOnly(signName(this.baseName),
				() -> this.new StandingSign(signProperties(this.baseProperties.get()), this.woodType));

		this.wallSign = this.registerBlockOnly(wallSignName(this.baseName),
				() -> this.new WallSign(wallSignProperties(this.baseProperties.get()), this.woodType));

		this.hangingSign = this.registerBlockOnly(hangingSignName(this.baseName),
				() -> this.new HangingSign(hangingSignProperties(this.baseProperties.get()), this.woodType));

		this.wallHangingSign = this.registerBlockOnly(wallHangingSignName(this.baseName),
				() -> this.new WallHangingSign(wallHangingSignProperties(this.baseProperties.get()), this.woodType));

		this.button = registerConditionalFuelBlockWithItem(buttonName(this.baseName),
				() -> this.new Button(buttonProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.BUTTON));

		this.pressurePlate = registerConditionalFuelBlockWithItem(pressurePlateName(this.baseName),
				() -> this.new PressurePlate(PressurePlateBlock.Sensitivity.MOBS,
						pressurePlateProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.PRESSURE_PLATE));

		this.trapdoor = registerConditionalFuelBlockWithItem(trapdoorName(this.baseName),
				() -> this.new TrapDoor(trapdoorProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.TRAPDOOR));

		this.door = registerConditionalFuelBlockWithItem(doorName(this.baseName),
				() -> this.new Door(doorProperties(this.baseProperties.get())),
				(int) (this.burnTimeMultiplier * BurnTimes.TRAPDOOR));
	}

	protected void registerItems() {

		this.signItem = this.registerItem(signName(this.baseName),
				() -> new SignItem(new Item.Properties().stacksTo(16), this.sign.get(), this.wallSign.get()));

		this.hangingSignItem = this.registerItem(hangingSignName(this.baseName),
				() -> new HangingSignItem(this.hangingSign.get(), this.wallHangingSign.get(),
						new Item.Properties().stacksTo(16)));

		this.boatItem = registerItem(boatName(this.baseName), () -> new WoodSetBoatItem(false, new Item.Properties()));

		this.chestBoatItem = registerItem(chestBoatName(this.baseName),
				() -> new WoodSetBoatItem(true, new Item.Properties()));

	}

	protected void registerBlockEntities() {

		this.hangingSignBlockEntity = this.blockEntityRegister.register(hangingSignName(this.baseName),
				() -> BlockEntityType.Builder.of(WoodSet.WoodSetHangingSignBlockEntity::new, this.wallHangingSign.get(),
						this.hangingSign.get()).build(null));
		this.signBlockEntity = this.blockEntityRegister.register(signName(this.baseName), () -> BlockEntityType.Builder
				.of(WoodSet.WoodSetSignBlockEntity::new, this.wallSign.get(), this.sign.get()).build(null));

	}

	protected void registerEntities() {

		this.boatEntity = this.entityRegister.register(boatName(this.getBaseName()),
				() -> EntityType.Builder.<WoodSetBoatEntity>of(WoodSetBoatEntity::new, MobCategory.MISC)
						.sized(1.375f, 0.5625f).build(boatName(this.getBaseName())));
		this.chestBoatEntity = this.entityRegister.register(chestBoatName(this.getBaseName()),
				() -> EntityType.Builder.<WoodSetChestBoatEntity>of(WoodSetChestBoatEntity::new, MobCategory.MISC)
						.sized(1.375f, 0.5625f).build(chestBoatName(this.getBaseName())));
	}

	public String getBaseName() {
		return baseName;
	}

	public String getModid() {
		return modid;
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

	public RegistryObject<Item> getBoatItem() {
		return boatItem;
	}

	public RegistryObject<Item> getChestBoatItem() {
		return chestBoatItem;
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
		RegistryObject<T> toReturn = this.registerBlockOnly(name, block);
		this.registerFuelBlockItem(name, toReturn, burnTime);
		return toReturn;
	}

	protected <T extends Block> RegistryObject<T> registerConditionalFuelBlockWithItem(String name, Supplier<T> block,
			int burnTime) {

		return this.flammability > 0 ? registerFuelBlockWithItem(name, block, burnTime)
				: registerBlockWithItem(name, block);
	}

	protected <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = this.registerBlockOnly(name, block);
		this.registerBlockItem(name, toReturn);
		return toReturn;
	}

	protected <T extends Block> RegistryObject<Item> registerFuelBlockItem(String name, RegistryObject<T> block,
			int burnTime) {
		return this.registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()) {
			@Override
			public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
				return burnTime;
			}
		});
	}

	protected <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return this.registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	protected <T extends Block> RegistryObject<T> registerBlockOnly(String name, Supplier<T> block) {
		return this.blockRegister.register(name, block);
	}

	protected RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
		return this.itemRegister.register(name, item);
	}

	public void addRecipes(Consumer<FinishedRecipe> recipeWriter) {
		WoodSet.Recipes recipes = new WoodSet.Recipes();

		recipes.charcoalSmelting(recipeWriter, this.log.get());
		recipes.charcoalSmelting(recipeWriter, this.strippedLog.get());
		recipes.charcoalSmelting(recipeWriter, this.wood.get());
		recipes.charcoalSmelting(recipeWriter, this.strippedWood.get());
		recipes.shapeless(recipeWriter, List.of(this.log.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				this.planks.get(), 4);
		recipes.shapeless(recipeWriter, List.of(this.strippedLog.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				this.planks.get(), 4);
		recipes.shapeless(recipeWriter, List.of(this.wood.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				this.planks.get(), 4);
		recipes.shapeless(recipeWriter, List.of(this.strippedWood.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				this.planks.get(), 4);
		recipes.shaped(recipeWriter, List.of("PP", "PP"), List.of('P'), List.of(this.log.get()),
				RecipeCategory.BUILDING_BLOCKS, this.wood.get(), 3);
		recipes.shaped(recipeWriter, List.of("PP", "PP", "PP"), List.of('P'), List.of(this.planks.get()),
				RecipeCategory.BUILDING_BLOCKS, this.door.get(), 3);
		recipes.shaped(recipeWriter, List.of("PPP", "PPP"), List.of('P'), List.of(this.planks.get()),
				RecipeCategory.BUILDING_BLOCKS, this.trapdoor.get(), 2);
		recipes.shaped(recipeWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(this.planks.get()),
				RecipeCategory.BUILDING_BLOCKS, this.stairs.get(), 4);
		recipes.shaped(recipeWriter, List.of("PPP"), List.of('P'), List.of(this.planks.get()),
				RecipeCategory.BUILDING_BLOCKS, this.slab.get(), 6);
		recipes.shapeless(recipeWriter, List.of(this.planks.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				this.button.get(), 1);
		recipes.shaped(recipeWriter, List.of("PSP", "PSP"), List.of('P', 'S'), List.of(this.planks.get(), Items.STICK),
				RecipeCategory.BUILDING_BLOCKS, this.fence.get(), 3);
		recipes.shaped(recipeWriter, List.of("SPS", "SPS"), List.of('P', 'S'), List.of(this.planks.get(), Items.STICK),
				RecipeCategory.BUILDING_BLOCKS, this.fenceGate.get(), 1);
		recipes.shaped(recipeWriter, List.of("PP"), List.of('P'), List.of(this.planks.get()),
				RecipeCategory.BUILDING_BLOCKS, this.pressurePlate.get(), 1);
		recipes.shaped(recipeWriter, List.of("PPP", "PPP", " S "), List.of('P', 'S'),
				List.of(this.planks.get(), Items.STICK), RecipeCategory.BUILDING_BLOCKS, this.signItem.get(), 3);
		recipes.shaped(recipeWriter, List.of("C C", "PPP", "PPP"), List.of('P', 'C'),
				List.of(this.strippedLog.get(), Items.CHAIN), RecipeCategory.BUILDING_BLOCKS,
				this.hangingSignItem.get(), 2);
		recipes.shaped(recipeWriter, List.of("P P", "PPP"), List.of('P'), List.of(this.planks.get()),
				RecipeCategory.TRANSPORTATION, this.boatItem.get(), 1);
		recipes.shaped(recipeWriter, List.of("P", "C"), List.of('P', 'C'), List.of(this.boatItem.get(), Items.CHEST),
				RecipeCategory.TRANSPORTATION, this.chestBoatItem.get(), 1);

	}

	private class Recipes {

		// Shapeless recipe. Item at i must correspond to item count at i.
		@SuppressWarnings("unchecked")
		public void shapeless(Consumer<FinishedRecipe> finishedRecipeConsumer, List<Object> ingredients,
				List<Integer> counts, RecipeCategory recipeCategory, ItemLike result, int count) {

			ShapelessRecipeBuilder recipe = ShapelessRecipeBuilder.shapeless(recipeCategory, result, count);
			String recipeName = WoodSet.this.modid + ":" + getItemPath(result) + "_from";

			// Check if the ingredients match the count length.
			if (counts.size() != ingredients.size()) {
				throw new IllegalArgumentException("Token count does not match ingredient count.");
			}

			// Adds in the ingredients.
			for (int i = 0; i < counts.size(); i++) {
				if (ingredients.get(i) instanceof ItemLike) {
					recipeName += "_" + getItemPath((ItemLike) ingredients.get(i));
					recipe = recipe.requires((ItemLike) ingredients.get(i), counts.get(i));
				} else if (ingredients.get(i) instanceof TagKey) {
					recipeName += "_tag_" + dePath(((TagKey<Item>) ingredients.get(i)).location());
					recipe = recipe.requires(Ingredient.of((TagKey<Item>) ingredients.get(i)), counts.get(i));
				} else {
					throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
				}
			}

			// Adds in the unlock triggers for the ingredients.
			for (int i = 0; i < ingredients.size(); i++) {
				if (ingredients.get(i) instanceof ItemLike)
					recipe = recipe.unlockedBy(getHasName((ItemLike) ingredients.get(i)),
							has((ItemLike) ingredients.get(i)));
				else if (ingredients.get(i) instanceof TagKey) {
					String name = "has" + ((TagKey<Item>) ingredients.get(i)).registry().registry().toDebugFileName();
					recipe = recipe.unlockedBy(name, has((TagKey<Item>) ingredients.get(i)));
				} else {
					throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
				}
			}
			// Adds in the unlock trigger for the result.
			recipe = recipe.unlockedBy(getHasName(result), has(result));

			recipe.group(getItemNamespace(result) + ":" + getItemPath(result));

			// Saves the recipe.
			System.out.println("Finished shapeless recipe, saving with name: " + recipeName);
			recipe.save(finishedRecipeConsumer, recipeName);
		}

		// Shaped recipe. Token at i must correspond to ingredient at i.
		@SuppressWarnings("unchecked")
		protected void shaped(Consumer<FinishedRecipe> finishedRecipeConsumer, List<String> pattern,
				List<Character> tokens, List<Object> ingredients, RecipeCategory recipeCategory, ItemLike result,
				int count) {

			ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(recipeCategory, result, count);
			String recipeName = WoodSet.this.modid + ":" + getItemPath(result) + "_from";
			// System.out.println(recipeName);

			// Adds in the pattern.
			for (String row : pattern) {
				recipe = recipe.pattern(row);
			}

			// Check if the ingredients match the tokens.
			if (tokens.size() != ingredients.size()) {
				throw new IllegalArgumentException("Token count does not match ingredient count.");
			}
			// System.out.println("There are " + tokens.size() + " tokens. They are: ");
			// Defines the tokens.
			for (int i = 0; i < tokens.size(); i++) {
				Object obj = ingredients.get(i);
				Character token = tokens.get(i);
				if (obj instanceof ItemLike item) {
					recipeName += "_" + getItemPath(item);
					recipe = recipe.define(token, item);
				} else if (obj instanceof TagKey tag) {
					recipeName += "_tag_" + dePath(tag.location());
					recipe = recipe.define(token, Ingredient.of(tag));
				} else {
					throw new IllegalArgumentException("Unrecognized item or tag type: " + obj);
				}
				// System.out.println(recipeName);

			}

			// Adds in the unlock trigger for the ingredients.
			for (int i = 0; i < ingredients.size(); i++) {
				if (ingredients.get(i) instanceof ItemLike) {
					String name = getHasName((ItemLike) ingredients.get(i));
					// System.out.println("Adding criterion with name: " + name);
					recipe = recipe.unlockedBy(name, has((ItemLike) ingredients.get(i)));
				} else if (ingredients.get(i) instanceof TagKey) {
					String name = "has_" + ((TagKey<Item>) ingredients.get(i)).registry().registry().toDebugFileName();
					// System.out.println("Adding criterion with name: " + name);
					recipe = recipe.unlockedBy(name, has((TagKey<Item>) ingredients.get(i)));
				} else {
					throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
				}
			}
			// Adds in the unlock trigger for the result.

			// System.out.println("Adding criterion with name: " + getHasName(result));
			recipe = recipe.unlockedBy(getHasName(result), has(result));

			recipe.group(getItemNamespace(result) + ":" + getItemPath(result));

			// Saves the recipe.
			System.out.println("Finished shaped recipe, saving with name: " + recipeName);
			recipe.save(finishedRecipeConsumer, recipeName);
		}

		protected void charcoalSmelting(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike pIngredient) {
			cooking(finishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, List.of(pIngredient), RecipeCategory.MISC,
					Items.CHARCOAL, 0.1f, 200, "charcoal", "_from_smelting_" + getItemPath(pIngredient));
		}

		protected void cooking(Consumer<FinishedRecipe> finishedRecipeConsumer,
				RecipeSerializer<? extends AbstractCookingRecipe> cookingSerializer, List<ItemLike> ingredients,
				RecipeCategory category, ItemLike result, float experience, int cookingTime, String group,
				String recipeName) {
			for (ItemLike itemlike : ingredients) {
				SimpleCookingRecipeBuilder
						.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, cookingSerializer)
						.group(group).unlockedBy(getHasName(itemlike), has(itemlike))
						.save(finishedRecipeConsumer, WoodSet.this.modid + ":" + getItemPath(result) + recipeName + "_"
								+ getItemPath(itemlike));
			}
		}

		protected static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tag) {
			return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build());
		}

		public static String getItemPath(ItemLike item) {
			return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
		}

		public static String getItemNamespace(ItemLike item) {
			return ForgeRegistries.ITEMS.getKey(item.asItem()).getNamespace();
		}

		public static String getHasName(ItemLike item) {
			return "has_" + getItemPath(item);
		}

		protected static InventoryChangeTrigger.TriggerInstance has(ItemLike item) {
			return inventoryTrigger(ItemPredicate.Builder.item().of(item).build());
		}

		protected static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... preds) {
			return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY,
					MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, preds);
		}

		public static String dePath(ResourceLocation location) {
			char[] chars = location.toString().toCharArray();

			for (int i = 0; i < chars.length; i++) {
				if (chars[i] == '/' || chars[i] == '\\' || chars[i] == ':') {
					chars[i] = '_';
				}
			}

			return null;
		}
	}

	public void addTransforms(DataParseableProvider<BlockTransformer> provider) {
		BlockTransformer stripping = provider.add(this.baseName + "_stripping");
		stripping.register(this.log.get(), this.strippedLog.get());
		stripping.register(this.wood.get(), this.strippedWood.get());
	}

	public void addItemTags(ItemTagsProvider tagProvider) {

		@SuppressWarnings("unchecked")
		Reflection.CallableWithArgs<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item>> tag = (Reflection.CallableWithArgs<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item>>) Reflection
				.getMethodCallable(tagProvider, "tag", TagKey.class);

		tag.call(ItemTags.WOODEN_TRAPDOORS).add(this.trapdoor.get().asItem());
		tag.call(ItemTags.DOORS).add(this.door.get().asItem());
		tag.call(ItemTags.TRAPDOORS).add(this.trapdoor.get().asItem());
		tag.call(ItemTags.WOODEN_DOORS).add(this.door.get().asItem());
		tag.call(ItemTags.DOORS).add(this.door.get().asItem());
		tag.call(ItemTags.WOODEN_SLABS).add(this.slab.get().asItem());
		tag.call(ItemTags.SLABS).add(this.slab.get().asItem());
		tag.call(ItemTags.WOODEN_STAIRS).add(this.stairs.get().asItem());
		tag.call(ItemTags.STAIRS).add(this.stairs.get().asItem());
		tag.call(ItemTags.WOODEN_BUTTONS).add(this.button.get().asItem());
		tag.call(ItemTags.BUTTONS).add(this.button.get().asItem());
		tag.call(ItemTags.WOODEN_PRESSURE_PLATES).add(this.pressurePlate.get().asItem());

		tag.call(ItemTags.WOODEN_FENCES).add(this.fence.get().asItem().asItem());
		tag.call(ItemTags.FENCES).add(this.fence.get().asItem());
		tag.call(ItemTags.FENCE_GATES).add(this.fenceGate.get().asItem());
		tag.call(ItemTags.PLANKS).add(this.planks.get().asItem());
		tag.call(ItemTags.LOGS).add(this.log.get().asItem(), this.wood.get().asItem(), this.strippedLog.get().asItem(),
				this.strippedWood.get().asItem());
		if (this.flammability > 0) {
			tag.call(ItemTags.LOGS_THAT_BURN).add(this.log.get().asItem(), this.wood.get().asItem(),
					this.strippedLog.get().asItem(), this.strippedWood.get().asItem());
		}

		tag.call(Tags.Items.FENCE_GATES).add(this.fenceGate.get().asItem());
		tag.call(Tags.Items.FENCE_GATES_WOODEN).add(this.fenceGate.get().asItem());
		tag.call(Tags.Items.FENCES).add(this.fence.get().asItem());
		tag.call(Tags.Items.FENCES_WOODEN).add(this.fence.get().asItem());

	}

	public void addBlockTags(BlockTagsProvider tagProvider) {

		@SuppressWarnings("unchecked")
		Reflection.CallableWithArgs<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>> tag = (Reflection.CallableWithArgs<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>>) Reflection
				.getMethodCallable(tagProvider, "tag", TagKey.class);

		tag.call(BlockTags.MINEABLE_WITH_AXE).add(this.log.get(), this.wood.get(), this.strippedLog.get(),
				this.strippedWood.get(), this.planks.get(), this.slab.get(), this.stairs.get(), this.fence.get(),
				this.fenceGate.get(), this.sign.get(), this.wallSign.get(), this.hangingSign.get(),
				this.wallHangingSign.get(), this.button.get(), this.pressurePlate.get(), this.door.get(),
				this.trapdoor.get());
		tag.call(BlockTags.WOODEN_TRAPDOORS).add(this.trapdoor.get());
		tag.call(BlockTags.DOORS).add(this.door.get());
		tag.call(BlockTags.TRAPDOORS).add(this.trapdoor.get());
		tag.call(BlockTags.WOODEN_DOORS).add(this.door.get());
		tag.call(BlockTags.DOORS).add(this.door.get());
		tag.call(BlockTags.WOODEN_SLABS).add(this.slab.get());
		tag.call(BlockTags.SLABS).add(this.slab.get());
		tag.call(BlockTags.WOODEN_STAIRS).add(this.stairs.get());
		tag.call(BlockTags.STAIRS).add(this.stairs.get());
		tag.call(BlockTags.WOODEN_BUTTONS).add(this.button.get());
		tag.call(BlockTags.BUTTONS).add(this.button.get());
		tag.call(BlockTags.WOODEN_PRESSURE_PLATES).add(this.pressurePlate.get());
		tag.call(BlockTags.PRESSURE_PLATES).add(this.pressurePlate.get());

		tag.call(BlockTags.WOODEN_FENCES).add(this.fence.get());
		tag.call(BlockTags.FENCES).add(this.fence.get());
		tag.call(BlockTags.FENCE_GATES).add(this.fenceGate.get());
		tag.call(BlockTags.PLANKS).add(this.planks.get());
		tag.call(BlockTags.LOGS).add(this.log.get(), this.wood.get(), this.strippedLog.get(), this.strippedWood.get());
		if (this.flammability > 0) {
			tag.call(BlockTags.LOGS_THAT_BURN).add(this.log.get(), this.wood.get(), this.strippedLog.get(),
					this.strippedWood.get());
		}

		tag.call(Tags.Blocks.FENCE_GATES).add(this.fenceGate.get());
		tag.call(Tags.Blocks.FENCE_GATES_WOODEN).add(this.fenceGate.get());
		tag.call(Tags.Blocks.FENCES).add(this.fence.get());
		tag.call(Tags.Blocks.FENCES_WOODEN).add(this.fence.get());

	}

	public void addItemModels(ItemModelProvider modelProvider) {

		BiFunction<ItemModelProvider, RegistryObject<Item>, ItemModelBuilder> simpleItem = (provider, item) -> provider
				.withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
				.texture("layer0", new ResourceLocation(this.modid, "item/" + item.getId().getPath()));

		BiFunction<ItemModelProvider, RegistryObject<Block>, ItemModelBuilder> evenSimplerBlockItem = (provider,
				block) -> provider.withExistingParent(
						WoodSet.this.modid + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
						new ResourceLocation(this.modid,
								"block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));

		BiFunction<ItemModelProvider, RegistryObject<Block>, ItemModelBuilder> simpleBlockItem = (provider,
				item) -> provider.withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
						.texture("layer0", new ResourceLocation(this.modid, "item/" + item.getId().getPath()));

		BiFunction<ItemModelProvider, RegistryObject<Block>, ItemModelBuilder> simpleLogModel = (provider,
				block) -> provider
						.withExistingParent(block.getId().getPath(), new ResourceLocation("block/cube_column"))
						.texture("side", new ResourceLocation(this.modid, "block/" + block.getId().getPath()))
						.texture("end", new ResourceLocation(this.modid, "block/" + block.getId().getPath() + "_top"));

		TriFunction<ItemModelProvider, RegistryObject<Block>, RegistryObject<Block>, ItemModelBuilder> fenceItem = (
				provider, block, baseBlock) -> provider
						.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
								new ResourceLocation("block/fence_inventory"))
						.texture("texture", new ResourceLocation(this.modid,
								"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));

		TriFunction<ItemModelProvider, RegistryObject<Block>, RegistryObject<Block>, ItemModelBuilder> buttonItem = (
				provider, block, baseBlock) -> provider
						.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
								new ResourceLocation("block/button_inventory"))
						.texture("texture", new ResourceLocation(this.modid,
								"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));

		BiFunction<ItemModelProvider, RegistryObject<Block>, ItemModelBuilder> trapdoorItem = (provider,
				block) -> provider.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
						new ResourceLocation(this.modid,
								"block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));

		simpleItem.apply(modelProvider, this.boatItem);
		simpleItem.apply(modelProvider, this.chestBoatItem);
		simpleItem.apply(modelProvider, this.signItem);
		simpleItem.apply(modelProvider, this.hangingSignItem);
		simpleBlockItem.apply(modelProvider, this.door);
		simpleLogModel.apply(modelProvider, this.log);
		simpleLogModel.apply(modelProvider, this.wood);
		simpleLogModel.apply(modelProvider, this.strippedLog);
		simpleLogModel.apply(modelProvider, this.strippedWood);

		fenceItem.apply(modelProvider, this.fence, this.planks);
		buttonItem.apply(modelProvider, this.button, this.planks);

		evenSimplerBlockItem.apply(modelProvider, this.stairs);
		evenSimplerBlockItem.apply(modelProvider, this.slab);
		evenSimplerBlockItem.apply(modelProvider, this.pressurePlate);
		evenSimplerBlockItem.apply(modelProvider, this.fenceGate);

		trapdoorItem.apply(modelProvider, this.trapdoor);

	}

	public void addBlockModels(BlockStateProvider provider) {
		provider.logBlock((RotatedPillarBlock) this.log.get());
		provider.logBlock((RotatedPillarBlock) this.strippedLog.get());
		provider.logBlock((RotatedPillarBlock) this.wood.get());
		provider.logBlock((RotatedPillarBlock) this.strippedWood.get());

		provider.simpleBlockWithItem(this.planks.get(), provider.cubeAll(this.planks.get()));

		provider.stairsBlock(((StairBlock) this.stairs.get()), provider.blockTexture(this.planks.get()));
		provider.slabBlock(((SlabBlock) this.slab.get()), provider.blockTexture(this.planks.get()),
				provider.blockTexture(this.planks.get()));

		provider.buttonBlock(((ButtonBlock) this.button.get()), provider.blockTexture(this.planks.get()));
		provider.pressurePlateBlock(((PressurePlateBlock) this.pressurePlate.get()),
				provider.blockTexture(this.planks.get()));

		provider.fenceBlock(((FenceBlock) this.fence.get()), provider.blockTexture(this.planks.get()));
		provider.fenceGateBlock(((FenceGateBlock) this.fenceGate.get()), provider.blockTexture(this.planks.get()));

		provider.doorBlockWithRenderType(((DoorBlock) this.door.get()),
				provider.modLoc("block/" + this.baseName + "_door_bottom"),
				provider.modLoc("block/" + this.baseName + "_door_top"), "cutout");
		provider.trapdoorBlockWithRenderType(((TrapDoorBlock) this.trapdoor.get()),
				provider.modLoc("block/" + this.baseName + "_trapdoor"), true, "cutout");

		provider.signBlock(((StandingSignBlock) this.sign.get()), ((WallSignBlock) this.wallSign.get()),
				provider.blockTexture(this.planks.get()));

		provider.simpleBlock(this.hangingSign.get(),
				provider.models().sign(ForgeRegistries.BLOCKS.getKey(this.hangingSign.get()).getPath(),
						provider.blockTexture(this.planks.get())));
		provider.simpleBlock(this.wallHangingSign.get(),
				provider.models().sign(ForgeRegistries.BLOCKS.getKey(this.hangingSign.get()).getPath(),
						provider.blockTexture(this.planks.get())));
	}

	public void addLootTables(BlockLootSubProvider provider) {

		Reflection.RunnableWithArgs dropSelf;

		Reflection.RunnableWithArgs add = Reflection.getMethodRunnable(provider, "add", Block.class,
				LootTable.Builder.class);
		@SuppressWarnings("unchecked")
		Reflection.CallableWithArgs<LootTable.Builder> createSlabItemTable = (CallableWithArgs<Builder>) Reflection
				.getMethodCallable(provider, "createSlabItemTable", Block.class);
		@SuppressWarnings("unchecked")
		Reflection.CallableWithArgs<LootTable.Builder> createDoorTable = (CallableWithArgs<Builder>) Reflection
				.getMethodCallable(provider, "createDoorTable", Block.class);

		dropSelf = Reflection.getMethodRunnable(provider, "dropSelf", Block.class);

		dropSelf.run(this.button.get());
		dropSelf.run(this.fence.get());
		dropSelf.run(this.fenceGate.get());
		dropSelf.run(this.planks.get());
		dropSelf.run(this.log.get());
		dropSelf.run(this.wood.get());
		dropSelf.run(this.strippedLog.get());
		dropSelf.run(this.strippedWood.get());
		dropSelf.run(this.pressurePlate.get());
		dropSelf.run(this.stairs.get());
		dropSelf.run(this.trapdoor.get());
		add.run(this.slab.get(), createSlabItemTable.call(WoodSet.this.slab.get()));
		add.run(this.door.get(), createDoorTable.call(this.door.get()));
		add.run(this.sign.get(), provider.createSingleItemTable(this.signItem.get()));
		add.run(this.sign.get(), provider.createSingleItemTable(this.signItem.get()));
		add.run(this.wallSign.get(), provider.createSingleItemTable(this.signItem.get()));
		add.run(this.hangingSign.get(), provider.createSingleItemTable(this.hangingSignItem.get()));
		add.run(this.wallHangingSign.get(), provider.createSingleItemTable(this.hangingSignItem.get()));

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

	protected static final String boatName(String base) {
		return base + "_boat";
	}

	protected static final String chestBoatName(String base) {
		return base + "_chest_boat";
	}

	public class WoodSetBoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
		private final boolean isChestBoat;

		public WoodSetBoatDispenseItemBehavior() {
			this(false);
		}

		public WoodSetBoatDispenseItemBehavior(boolean isChestBoat) {
			this.isChestBoat = isChestBoat;
		}

		public ItemStack execute(BlockSource blockSource, ItemStack stack) {
			Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
			Level level = blockSource.getLevel();
			double d0 = 0.5625D + (double) EntityType.BOAT.getWidth() / 2.0D;
			double d1 = blockSource.x() + (double) direction.getStepX() * d0;
			double d2 = blockSource.y() + (double) ((float) direction.getStepY() * 1.125F);
			double d3 = blockSource.z() + (double) direction.getStepZ() * d0;
			BlockPos blockpos = blockSource.getPos().relative(direction);
			Boat boat = (Boat) (this.isChestBoat ? new WoodSetChestBoatEntity(level, d0, d1, d2)
					: new WoodSetBoatEntity(level, d0, d1, d2));
			boat.setYRot(direction.toYRot());
			double d4;
			if (boat.canBoatInFluid(level.getFluidState(blockpos))) {
				d4 = 1.0D;
			} else {
				if (!level.getBlockState(blockpos).isAir()
						|| !boat.canBoatInFluid(level.getFluidState(blockpos.below()))) {
					return this.defaultDispenseItemBehavior.dispense(blockSource, stack);
				}

				d4 = 0.0D;
			}

			boat.setPos(d1, d2 + d4, d3);
			level.addFreshEntity(boat);
			stack.shrink(1);
			return stack;
		}

		protected void playSound(BlockSource blockSource) {
			blockSource.getLevel().levelEvent(1000, blockSource.getPos(), 0);
		}
	}

	public class Door extends DoorBlock {

		public Door(Properties properties) {
			super(properties, WoodSet.this.blockSetType);
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.fireSpreadSpeed;
		}

	}

	public class TrapDoor extends TrapDoorBlock {

		public TrapDoor(Properties properties) {
			super(properties, WoodSet.this.blockSetType);
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.fireSpreadSpeed;
		}

	}

	public class PressurePlate extends PressurePlateBlock {

		public PressurePlate(Sensitivity sensitivity, Properties properties) {
			super(sensitivity, properties, WoodSet.this.blockSetType);
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.fireSpreadSpeed;
		}

	}

	public class Button extends ButtonBlock {
		public Button(Properties properties) {
			super(properties, WoodSet.this.blockSetType, WoodSet.this.buttonTime, true);
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.fireSpreadSpeed;
		}
	}

	public class WallHangingSign extends WallHangingSignBlock {
		public WallHangingSign(Properties properties, WoodType type) {
			super(properties, type);
		}

		@Override
		public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return WoodSet.this.new WoodSetHangingSignBlockEntity(pos, state);
		}

		@Override
		public void openTextEdit(Player player, SignBlockEntity blockEntity, boolean bool) {
			super.openTextEdit(player, blockEntity, bool);
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.fireSpreadSpeed;
		}
	}

	public class HangingSign extends CeilingHangingSignBlock {
		public HangingSign(Properties properties, WoodType type) {
			super(properties, type);
		}

		@Override
		public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return WoodSet.this.new WoodSetHangingSignBlockEntity(pos, state);
		}

		@Override
		public void openTextEdit(Player player, SignBlockEntity blockEntity, boolean bool) {
			System.out.println(blockEntity.getClass());
			super.openTextEdit(player, blockEntity, bool);
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.fireSpreadSpeed;
		}
	}

	public class WallSign extends WallSignBlock {
		public WallSign(Properties properties, WoodType type) {
			super(properties, type);
		}

		@Override
		public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return WoodSet.this.new WoodSetSignBlockEntity(pos, state);
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.fireSpreadSpeed;
		}
	}

	public class StandingSign extends StandingSignBlock {
		public StandingSign(Properties properties, WoodType type) {
			super(properties, type);
		}

		@Override
		public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return WoodSet.this.new WoodSetSignBlockEntity(pos, state);
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return WoodSet.this.fireSpreadSpeed;
		}
	}

	public class FenceGate extends FenceGateBlock {

		private final int flammability;
		private final int fireSpreadSpeed;

		public FenceGate(Properties properties) {
			this(properties, WoodSet.this.flammability, WoodSet.this.fireSpreadSpeed);
		}

		public FenceGate(Properties properties, int flammability, int fireSpreadSpeed) {
			super(properties, WoodSet.this.woodType);
			this.flammability = flammability;
			this.fireSpreadSpeed = fireSpreadSpeed;
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.fireSpreadSpeed;
		}
	}

	public class Fence extends FenceBlock {

		private final int flammability;
		private final int fireSpreadSpeed;

		public Fence(Properties properties) {
			this(properties, WoodSet.this.flammability, WoodSet.this.fireSpreadSpeed);
		}

		public Fence(Properties properties, int flammability, int fireSpreadSpeed) {
			super(properties);
			this.flammability = flammability;
			this.fireSpreadSpeed = fireSpreadSpeed;
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.fireSpreadSpeed;
		}
	}

	public class Stair extends StairBlock {

		private final int flammability;
		private final int fireSpreadSpeed;

		public Stair(Supplier<BlockState> state, Properties properties) {
			this(state, properties, WoodSet.this.flammability, WoodSet.this.fireSpreadSpeed);
		}

		public Stair(Supplier<BlockState> state, Properties properties, int flammability, int fireSpreadSpeed) {
			super(state, properties);
			this.flammability = flammability;
			this.fireSpreadSpeed = fireSpreadSpeed;
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.fireSpreadSpeed;
		}
	}

	public class Slab extends SlabBlock {

		private final int flammability;
		private final int fireSpreadSpeed;

		public Slab(Properties properties) {
			this(properties, WoodSet.this.flammability, WoodSet.this.fireSpreadSpeed);
		}

		public Slab(Properties properties, int flammability, int fireSpreadSpeed) {
			super(properties);
			this.flammability = flammability;
			this.fireSpreadSpeed = fireSpreadSpeed;
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.fireSpreadSpeed;
		}
	}

	public class Planks extends Block {

		private final int flammability;
		private final int fireSpreadSpeed;

		public Planks(Properties properties) {
			this(properties, WoodSet.this.flammability, WoodSet.this.fireSpreadSpeed);
		}

		public Planks(Properties properties, int flammability, int fireSpreadSpeed) {
			super(properties);
			this.flammability = flammability;
			this.fireSpreadSpeed = fireSpreadSpeed;
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.fireSpreadSpeed;
		}
	}

	public class Log extends RotatedPillarBlock {

		private final int flammability;
		private final int fireSpreadSpeed;

		public Log(Properties properties) {
			this(properties, WoodSet.this.flammability, WoodSet.this.fireSpreadSpeed);
		}

		public Log(Properties properties, int flammability, int fireSpreadSpeed) {
			super(properties);
			this.flammability = flammability;
			this.fireSpreadSpeed = fireSpreadSpeed;
		}

		@Override
		public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability > 0;
		}

		@Override
		public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.flammability;
		}

		@Override
		public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
			return this.fireSpreadSpeed;
		}

		@Override
		public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction,
				boolean simulate) {
			if (toolAction == ToolActions.AXE_STRIP) {
				if (WoodSet.this.stripping.get().hasInput(state.getBlock())) {
					return WoodSet.this.stripping.get().next(state);
				}
			}
			return super.getToolModifiedState(state, context, toolAction, simulate);
		}
	}

	/*
	 * SignBlockEntity has
	 * 
	 * @@ -278,4 +_,9 @@
	 * 
	 * Syntax? First number is the starting line number of the injection. Second
	 * number is the number of lines to remove Third number is the total number of
	 * lines to add
	 */

	public class WoodSetHangingSignBlockEntity extends HangingSignBlockEntity {
		private static final int MAX_TEXT_LINE_WIDTH = 60;
		private static final int TEXT_LINE_HEIGHT = 9;

		public WoodSetHangingSignBlockEntity(BlockPos pos, BlockState state) {
			super(pos, state);
		}

		@Override
		public BlockEntityType<?> getType() {
			return WoodSet.this.hangingSignBlockEntity.get();
		}

		@Override
		public int getMaxTextLineWidth() {
			return MAX_TEXT_LINE_WIDTH;
		}

		@Override
		public int getTextLineHeight() {
			return TEXT_LINE_HEIGHT;
		}
	}

	public class WoodSetSignBlockEntity extends SignBlockEntity {
		public WoodSetSignBlockEntity(BlockPos pos, BlockState state) {
			super(WoodSet.this.signBlockEntity.get(), pos, state);
		}

		@Override
		public BlockEntityType<?> getType() {
			return WoodSet.this.signBlockEntity.get();
		}
	}

	public class WoodSetBoatEntity extends Boat {
		public WoodSetBoatEntity(EntityType<? extends Boat> entityType, Level level) {
			super(entityType, level);
		}

		public WoodSetBoatEntity(Level level, double x, double y, double z) {
			this(WoodSet.this.boatEntity.get(), level);
			this.setPos(x, y, z);
			this.xo = x;
			this.yo = y;
			this.zo = z;
		}

		@Override
		public Item getDropItem() {
			return WoodSet.this.boatItem.get();
		}
	}

	public class WoodSetChestBoatEntity extends ChestBoat {

		public WoodSetChestBoatEntity(EntityType<? extends ChestBoat> entityType, Level level) {
			super(entityType, level);
		}

		public WoodSetChestBoatEntity(Level level, double x, double y, double z) {
			this(WoodSet.this.chestBoatEntity.get(), level);
			this.setPos(x, y, z);
			this.xo = x;
			this.yo = y;
			this.zo = z;
		}

		@Override
		public Item getDropItem() {

			return WoodSet.this.chestBoatItem.get();
		}
	}

	public class WoodSetBoatRenderer extends BoatRenderer {

		private final Pair<ResourceLocation, ListModel<Boat>> modelWithLocation;

		public WoodSetBoatRenderer(EntityRendererProvider.Context context, boolean isChestBoat) {
			super(context, isChestBoat);
			this.modelWithLocation = Pair.of(this.getTextureLocation(isChestBoat),
					this.createBoatModel(context, isChestBoat));
		}

		private ResourceLocation getTextureLocation(boolean chestBoat) {
			return new ResourceLocation(WoodSet.this.modid,
					chestBoat ? "textures/entity/chest_boat/" + WoodSet.this.baseName + ".png"
							: "textures/entity/boat/" + WoodSet.this.baseName + ".png");
		}

		private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, boolean isChestBoat) {
			ModelLayerLocation modellayerlocation = isChestBoat ? this.createChestBoatModelName()
					: this.createBoatModelName();
			ModelPart modelpart = context.bakeLayer(modellayerlocation);
			return isChestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
		}

		public ModelLayerLocation createBoatModelName() {
			return createLocation("boat/" + WoodSet.this.baseName, "main");
		}

		public ModelLayerLocation createChestBoatModelName() {
			return createLocation("chest_boat/" + WoodSet.this.baseName, "main");
		}

		private ModelLayerLocation createLocation(String path, String model) {
			return new ModelLayerLocation(new ResourceLocation(WoodSet.this.modid, path), model);
		}

		public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
			return this.modelWithLocation;
		}
	}

	public class WoodSetBoatItem extends Item {
		private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
		private final boolean hasChest;

		public WoodSetBoatItem(boolean hasChest, Item.Properties properties) {
			super(properties);
			this.hasChest = hasChest;
		}

		public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
			ItemStack itemstack = player.getItemInHand(useHand);
			HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
			if (hitresult.getType() == HitResult.Type.MISS) {
				return InteractionResultHolder.pass(itemstack);
			} else {
				Vec3 vec3 = player.getViewVector(1.0F);
				List<Entity> list = level.getEntities(player,
						player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
				if (!list.isEmpty()) {
					Vec3 vec31 = player.getEyePosition();

					for (Entity entity : list) {
						AABB aabb = entity.getBoundingBox().inflate((double) entity.getPickRadius());
						if (aabb.contains(vec31)) {
							return InteractionResultHolder.pass(itemstack);
						}
					}
				}
				if (hitresult.getType() == HitResult.Type.BLOCK) {
					Boat boat = this.getBoat(level, hitresult);
					boat.setYRot(player.getYRot());
					if (!level.noCollision(boat, boat.getBoundingBox())) {
						return InteractionResultHolder.fail(itemstack);
					} else {
						if (!level.isClientSide) {
							level.addFreshEntity(boat);
							level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
							if (!player.getAbilities().instabuild) {
								itemstack.shrink(1);
							}
						}

						player.awardStat(Stats.ITEM_USED.get(this));
						return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
					}
				} else {
					return InteractionResultHolder.pass(itemstack);
				}
			}
		}

		private Boat getBoat(Level level, HitResult result) {
			return (Boat) (this.hasChest
					? WoodSet.this.new WoodSetChestBoatEntity(level, result.getLocation().x, result.getLocation().y,
							result.getLocation().z)
					: WoodSet.this.new WoodSetBoatEntity(level, result.getLocation().x, result.getLocation().y,
							result.getLocation().z));
		}
	}

	@FunctionalInterface
	public interface TriFunction<R, S, T, U> {
		public U apply(R r, S s, T t);
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
