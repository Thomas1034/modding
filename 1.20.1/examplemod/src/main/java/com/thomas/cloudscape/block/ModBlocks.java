package com.thomas.cloudscape.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.block.custom.BlueberryCropBlock;
import com.thomas.cloudscape.block.custom.BubblefruitCropBlock;
import com.thomas.cloudscape.block.custom.BuddingCitrineBlock;
import com.thomas.cloudscape.block.custom.CloudBlock;
import com.thomas.cloudscape.block.custom.CloudConverterBlock;
import com.thomas.cloudscape.block.custom.CloudDetectorBlock;
import com.thomas.cloudscape.block.custom.CloudInverterBlock;
import com.thomas.cloudscape.block.custom.DirectionalPassageBlock;
import com.thomas.cloudscape.block.custom.FloorFrondBlock;
import com.thomas.cloudscape.block.custom.FrondBlock;
import com.thomas.cloudscape.block.custom.GemBracketBlock;
import com.thomas.cloudscape.block.custom.LightningBlock;
import com.thomas.cloudscape.block.custom.ModFlammableRotatedPillarBlock;
import com.thomas.cloudscape.block.custom.ModHangingSignBlock;
import com.thomas.cloudscape.block.custom.ModStandingSignBlock;
import com.thomas.cloudscape.block.custom.ModWallHangingSignBlock;
import com.thomas.cloudscape.block.custom.ModWallSignBlock;
import com.thomas.cloudscape.block.custom.NetheriteAnvilBlock;
import com.thomas.cloudscape.block.custom.NimbulaPolypBlock;
import com.thomas.cloudscape.block.custom.PalmFruitBlock;
import com.thomas.cloudscape.block.custom.PalmTrunkBlock;
import com.thomas.cloudscape.block.custom.QuicksandBlock;
import com.thomas.cloudscape.block.custom.RainCloudBlock;
import com.thomas.cloudscape.block.custom.ResonatorBlock;
import com.thomas.cloudscape.block.custom.SculkJawBlock;
import com.thomas.cloudscape.block.custom.SculkRootBlock;
import com.thomas.cloudscape.block.custom.ThirstyBlock;
import com.thomas.cloudscape.block.custom.ThunderCloudBlock;
import com.thomas.cloudscape.block.custom.UnstableLightningBlock;
import com.thomas.cloudscape.block.custom.WallGemBracketBlock;
import com.thomas.cloudscape.block.custom.WeatheringCopperButtonBlock;
import com.thomas.cloudscape.block.custom.WispBedBlock;
import com.thomas.cloudscape.block.custom.ZirconLampBlock;
import com.thomas.cloudscape.item.ModItems;
import com.thomas.cloudscape.util.BurnTimes;
import com.thomas.cloudscape.util.ModTags;
import com.thomas.cloudscape.util.ModWoodTypes;
import com.thomas.cloudscape.worldgen.tree.PalmTreeGrower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
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
			Cloudscape.MOD_ID);

	// Villager workstation blocks
	public static final RegistryObject<Block> CARPENTRY_TABLE = registerBlock("carpentry_table",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

	// Unobtainable blocks, to be used for villager workstations that shouldn't
	// exist.
	public static final RegistryObject<Block> GEMSMITH_WORKSITE = registerBlock("gemsmith_worksite",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> BOTANIST_WORKSITE = registerBlock("botanist_worksite",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> ARCHITECT_WORKSITE = registerBlock("architect_worksite",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> TINKERER_WORKSITE = registerBlock("tinkerer_worksite",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> SCHOLAR_WORKSITE = registerBlock("scholar_worksite",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> CHIEF_WORKSITE = registerBlock("chief_worksite",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)));

	// Blueberry crop
	public static final RegistryObject<Block> BLUEBERRY_CROP = BLOCKS.register("blueberry_crop",
			() -> new BlueberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

	// Cloudberry block
	public static final RegistryObject<Block> BUBBLEFRUIT_CROP = BLOCKS.register("bubblefruit_crop",
			() -> new BubblefruitCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

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
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)
					.emissiveRendering((state, getter, position) -> true).lightLevel(state -> 5)));

	// Zircon ore
	public static final RegistryObject<Block> ZIRCON_ORE = registerBlock("zircon_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.STONE)));

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
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).sound(SoundType.DEEPSLATE)));

	// Echo block
	public static final RegistryObject<Block> ECHO_BLOCK = registerBlock("echo_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).sound(SoundType.METAL)));

	// Resonator block
	public static final RegistryObject<Block> RESONATOR_BLOCK = registerBlock("resonator",
			() -> new ResonatorBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).sound(SoundType.METAL)));

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
			() -> new CloudBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.1F).destroyTime(0.5F)
					.pushReaction(PushReaction.DESTROY).isViewBlocking((state, level, pos) -> true)
					.isSuffocating((state, level, pos) -> false)
					.isValidSpawn((state, level, pos,
							type) -> (state.getValue(CloudBlock.SOLIDIFIER_DISTANCE) == CloudBlock.MAX_DISTANCE
									|| type.is(ModTags.EntityTypes.CLOUD_SPAWNABLE_MOBS)))
					.randomTicks().sound(SoundType.EMPTY)));

	// Transparent cloud block
	public static final RegistryObject<Block> MIST = registerBlock("mist",
			() -> new CloudBlock(
					BlockBehaviour.Properties.of().strength(0.1F).destroyTime(0.5F).pushReaction(PushReaction.DESTROY)
							.isViewBlocking((state, level, pos) -> false).isSuffocating((state, level, pos) -> false)
							.isValidSpawn((state, level, pos,
									type) -> (state.getValue(CloudBlock.SOLIDIFIER_DISTANCE) == CloudBlock.MAX_DISTANCE
											|| type.is(ModTags.EntityTypes.CLOUD_SPAWNABLE_MOBS)))
							.randomTicks().sound(SoundType.EMPTY).noOcclusion()));

	// Transparent cloud block
	public static final RegistryObject<Block> DENSE_MIST = registerBlock("dense_mist",
			() -> new CloudBlock(
					BlockBehaviour.Properties.of().strength(0.1F).destroyTime(0.5F).pushReaction(PushReaction.DESTROY)
							.isViewBlocking((state, level, pos) -> false).isSuffocating((state, level, pos) -> false)
							.isValidSpawn((state, level, pos,
									type) -> (state.getValue(CloudBlock.SOLIDIFIER_DISTANCE) == CloudBlock.MAX_DISTANCE
											|| type.is(ModTags.EntityTypes.CLOUD_SPAWNABLE_MOBS)))
							.randomTicks().sound(SoundType.EMPTY).noOcclusion()));

	// Grey cloud block
	public static final RegistryObject<Block> RAIN_CLOUD = registerBlock("rain_cloud",
			() -> new RainCloudBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.1F).destroyTime(0.5F)
					.pushReaction(PushReaction.DESTROY).isViewBlocking((state, level, pos) -> true)
					.isSuffocating((state, level, pos) -> false)
					.isValidSpawn((state, level, pos,
							type) -> (state.getValue(CloudBlock.SOLIDIFIER_DISTANCE) == CloudBlock.MAX_DISTANCE
									|| type.is(ModTags.EntityTypes.CLOUD_SPAWNABLE_MOBS)))
					.randomTicks().sound(SoundType.EMPTY)));

	// Indestructible cloud block
	public static final RegistryObject<Block> SEALED_MIST = registerBlock("sealed_mist",
			() -> new CloudBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)
					.isViewBlocking((state, level, pos) -> false).isSuffocating((state, level, pos) -> false)
					.isValidSpawn((state, level, pos,
							type) -> (state.getValue(CloudBlock.SOLIDIFIER_DISTANCE) == CloudBlock.MAX_DISTANCE
									|| type.is(ModTags.EntityTypes.CLOUD_SPAWNABLE_MOBS)))
					.randomTicks().sound(SoundType.EMPTY).noOcclusion()));

	// Sealed cloud block
	public static final RegistryObject<Block> SEALED_CLOUD = registerBlock("sealed_cloud",
			() -> new CloudBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).randomTicks().sound(SoundType.EMPTY)
					.emissiveRendering((state, level, pos) -> true).isViewBlocking((state, level, pos) -> true)
					.lightLevel(state -> 3)));

	// Basic thundercloud block
	public static final RegistryObject<Block> THUNDER_CLOUD = registerBlock("thunder_cloud",
			() -> new ThunderCloudBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD.get()).randomTicks()
					.isViewBlocking((state, level, pos) -> true)));

	// Lighting block
	public static final RegistryObject<Block> LIGHTNING_BLOCK = registerBlock("lightning_block",
			() -> new LightningBlock(BlockBehaviour.Properties.of().strength(0.1F).pushReaction(PushReaction.DESTROY)
					.noOcclusion().isViewBlocking((state, level, pos) -> true).noCollission().lightLevel((state) -> 3)
					.instabreak().emissiveRendering((state, level, pos) -> true).sound(SoundType.EMPTY)));
	public static final RegistryObject<Block> UNSTABLE_LIGHTNING_BLOCK = registerBlock("unstable_lightning_block",
			() -> new UnstableLightningBlock(
					BlockBehaviour.Properties.copy(ModBlocks.LIGHTNING_BLOCK.get()).randomTicks()));

	// Cloud bricks
	public static final RegistryObject<Block> CLOUD_BRICKS = registerBlock("cloud_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).sound(SoundType.EMPTY)));

	public static final RegistryObject<Block> RAIN_CLOUD_BRICKS = registerBlock("rain_cloud_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).sound(SoundType.EMPTY)));

	public static final RegistryObject<Block> THUNDER_CLOUD_BRICKS = registerBlock("thunder_cloud_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).sound(SoundType.EMPTY)) {
				// Damage all entities that step on the block
				public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
					if (!entity.isSteppingCarefully() && entity instanceof LivingEntity) {
						entity.hurt(entity.damageSources().lightningBolt(), 1.0F);
					}
					super.stepOn(level, pos, state, entity);
				}
			});

	public static final RegistryObject<Block> CLOUD_BRICK_SLAB = registerBlock("cloud_brick_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get())));

	public static final RegistryObject<Block> RAIN_CLOUD_BRICK_SLAB = registerBlock("rain_cloud_brick_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get())));

	public static final RegistryObject<Block> THUNDER_CLOUD_BRICK_SLAB = registerBlock("thunder_cloud_brick_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.THUNDER_CLOUD_BRICKS.get())) {
				// Damage all entities that step on the block
				public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
					if (level.isThundering() && !entity.isSteppingCarefully() && entity instanceof LivingEntity) {
						entity.hurt(entity.damageSources().lightningBolt(), 1.0F);
					}
					super.stepOn(level, pos, state, entity);
				}
			});

	public static final RegistryObject<Block> CLOUD_BRICK_STAIRS = registerBlock("cloud_brick_stairs",
			() -> new StairBlock(() -> ModBlocks.CLOUD_BRICKS.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get())));

	public static final RegistryObject<Block> RAIN_CLOUD_BRICK_STAIRS = registerBlock("rain_cloud_brick_stairs",
			() -> new StairBlock(() -> ModBlocks.CLOUD_BRICKS.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get())));

	public static final RegistryObject<Block> THUNDER_CLOUD_BRICK_STAIRS = registerBlock("thunder_cloud_brick_stairs",
			() -> new StairBlock(() -> ModBlocks.THUNDER_CLOUD_BRICKS.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.THUNDER_CLOUD_BRICKS.get())) {
				// Damage all entities that step on the block
				public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
					if (level.isThundering() && !entity.isSteppingCarefully() && entity instanceof LivingEntity) {
						entity.hurt(entity.damageSources().lightningBolt(), 1.0F);
					}
					super.stepOn(level, pos, state, entity);
				}
			});

	public static final RegistryObject<Block> CLOUD_BRICK_WALL = registerBlock("cloud_brick_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get())));

	public static final RegistryObject<Block> RAIN_CLOUD_BRICK_WALL = registerBlock("rain_cloud_brick_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get())));

	public static final RegistryObject<Block> THUNDER_CLOUD_BRICK_WALL = registerBlock("thunder_cloud_brick_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(ModBlocks.THUNDER_CLOUD_BRICKS.get())) {
				// Damage all entities that step on the block
				public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
					if (level.isThundering() && !entity.isSteppingCarefully() && entity instanceof LivingEntity) {
						entity.hurt(entity.damageSources().lightningBolt(), 1.0F);
					}
					super.stepOn(level, pos, state, entity);
				}
			});

	public static final RegistryObject<Block> CLOUD_BRICK_PILLAR = registerBlock("cloud_brick_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get())));

	public static final RegistryObject<Block> RAIN_CLOUD_BRICK_PILLAR = registerBlock("rain_cloud_brick_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get())));

	public static final RegistryObject<Block> THUNDER_CLOUD_BRICK_PILLAR = registerBlock("thunder_cloud_brick_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(ModBlocks.THUNDER_CLOUD_BRICKS.get())) {
				// Damage all entities that step on the block
				public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
					if (!entity.isSteppingCarefully() && entity instanceof LivingEntity) {
						entity.hurt(entity.damageSources().lightningBolt(), 1.0F);
					}
					super.stepOn(level, pos, state, entity);
				}
			});

	// Cloud bricks
	public static final RegistryObject<Block> CHISELED_CLOUD_BRICKS = registerBlock("chiseled_cloud_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get()).sound(SoundType.EMPTY)));

	public static final RegistryObject<Block> CHISELED_RAIN_CLOUD_BRICKS = registerBlock("chiseled_rain_cloud_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get()).sound(SoundType.EMPTY)));

	public static final RegistryObject<Block> CHISELED_THUNDER_CLOUD_BRICKS = registerBlock(
			"chiseled_thunder_cloud_bricks", () -> new Block(
					BlockBehaviour.Properties.copy(ModBlocks.THUNDER_CLOUD_BRICKS.get()).sound(SoundType.EMPTY)) {
				// Damage all entities that step on the block
				public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
					if (!entity.isSteppingCarefully() && entity instanceof LivingEntity) {
						entity.hurt(entity.damageSources().lightningBolt(), 1.0F);
					}
					super.stepOn(level, pos, state, entity);
				}
			});

	// Cloud converter block, converts redstone signal to cloud strength
	public static final RegistryObject<Block> CLOUD_CONVERTER = registerBlock("cloud_converter",
			() -> new CloudConverterBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD.get())));

	// Cloud inverter block, converts redstone signal to cloud strength in reverse
	// order.
	public static final RegistryObject<Block> CLOUD_INVERTER = registerBlock("cloud_inverter",
			() -> new CloudInverterBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD.get())));

	// Cloud detector block, converts cloud strength to redstone signal
	public static final RegistryObject<Block> CLOUD_DETECTOR = registerBlock("cloud_detector",
			() -> new CloudDetectorBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD.get())));

	// Petrified log
	public static final RegistryObject<Block> PETRIFIED_LOG = registerBlock("petrified_log",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

	// Palm components
	public static final RegistryObject<Block> PALM_SAPLING = registerBlock("palm_sapling",
			() -> new SaplingBlock(new PalmTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)) {
				public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
					return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND) || state.is(BlockTags.SAND);
				}
			});

	public static final RegistryObject<Block> POTTED_PALM_SAPLING = BLOCKS.register("potted_palm_sapling",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.PALM_SAPLING,
					BlockBehaviour.Properties.copy(Blocks.POTTED_OAK_SAPLING).noOcclusion()));

	public static final RegistryObject<Block> PALM_FROND = registerFuelBlock("palm_frond",
			() -> new FrondBlock(
					BlockBehaviour.Properties.of().noOcclusion().randomTicks().sound(SoundType.GRASS).destroyTime(1f)),
			BurnTimes.BUTTON);

	public static final RegistryObject<Block> PALM_FLOOR_FROND = BLOCKS.register("palm_floor_frond",
			() -> new FloorFrondBlock(
					BlockBehaviour.Properties.of().noOcclusion().randomTicks().sound(SoundType.GRASS).destroyTime(1f)));

	public static final RegistryObject<Block> PALM_TRUNK = registerFuelBlock("palm_trunk",
			() -> new PalmTrunkBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)), BurnTimes.LOG);

	public static final RegistryObject<Block> PALM_FRUIT = registerBlock("palm_fruit",
			() -> new PalmFruitBlock(BlockBehaviour.Properties.copy(Blocks.COCOA).randomTicks().strength(0.2F, 3.0F)
					.sound(SoundType.WOOD).noOcclusion().noCollission()));

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

	public static final RegistryObject<Block> PALM_SIGN = BLOCKS.register("palm_sign",
			() -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.PALM));
	public static final RegistryObject<Block> PALM_WALL_SIGN = BLOCKS.register("palm_wall_sign",
			() -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.PALM));

	public static final RegistryObject<Block> PALM_HANGING_SIGN = BLOCKS.register("palm_hanging_sign",
			() -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), ModWoodTypes.PALM));
	public static final RegistryObject<Block> PALM_WALL_HANGING_SIGN = BLOCKS.register("palm_wall_hanging_sign",
			() -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN),
					ModWoodTypes.PALM));

	// Modded Torchflower
	public static final RegistryObject<Block> ILLUMINATED_TORCHFLOWER = registerBlock("illuminated_torchflower",
			() -> new FlowerBlock(() -> MobEffects.GLOWING, 16,
					BlockBehaviour.Properties.copy(Blocks.TORCHFLOWER).noOcclusion().noCollission()
							.emissiveRendering((state, getter, position) -> true).lightLevel(state -> 14)));

	public static final RegistryObject<Block> POTTED_ILLUMINATED_TORCHFLOWER = BLOCKS.register(
			"potted_illuminated_torchflower",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.ILLUMINATED_TORCHFLOWER,
					BlockBehaviour.Properties.copy(Blocks.POTTED_TORCHFLOWER).noOcclusion()
							.emissiveRendering((state, getter, position) -> true).lightLevel(state -> 12)));

	// Modded orchid
	public static final RegistryObject<Block> WHITE_ORCHID = registerBlock("white_orchid",
			() -> new FlowerBlock(() -> MobEffects.LEVITATION, 8,
					BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noOcclusion().noCollission()
							.emissiveRendering((state, getter, position) -> true).lightLevel(state -> 1)));

	public static final RegistryObject<Block> POTTED_WHITE_ORCHID = BLOCKS.register("potted_white_orchid",
			() -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.WHITE_ORCHID,
					BlockBehaviour.Properties.copy(Blocks.POTTED_BLUE_ORCHID).noOcclusion()
							.emissiveRendering((state, getter, position) -> true).lightLevel(state -> 2)));

	// Citrine lantern
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

	// Nimbula Polyp
	public static final RegistryObject<Block> NIMBULA_POLYP = registerBlock("nimbula_polyp",
			() -> new NimbulaPolypBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.POWDER_SNOW)
					.randomTicks().noOcclusion()));

	// Sealed door block
	public static final RegistryObject<Block> WEATHER_PASSAGE_BLOCK = registerBlock("weather_passage_block",
			() -> new DirectionalPassageBlock(
					BlockBehaviour.Properties.copy(Blocks.GLASS).strength(-1.0F, 3600000.0F)
							.pushReaction(PushReaction.BLOCK).isViewBlocking((state, level, pos) -> false)
							.isSuffocating((state, level, pos) -> false).randomTicks().lightLevel(state -> 12),
					(level, pos) -> !level.isRaining()));

	// Sealed bricks
	public static final RegistryObject<Block> SEALED_CLOUD_BRICKS = registerBlock("sealed_cloud_bricks",
			() -> new Block(
					BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS).lightLevel(state -> 2)));

	public static final RegistryObject<Block> SEALED_THUNDER_CLOUD_BRICKS = registerBlock("sealed_thunder_cloud_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)));

	// Wisp bed block
	public static final RegistryObject<Block> WISP_BED = registerBlock("wisp_bed",
			() -> new WispBedBlock(BlockBehaviour.Properties.copy(ModBlocks.CLOUD_BRICKS.get()).sound(SoundType.WOOL)));

	// Sculk jaw block
	public static final RegistryObject<Block> SCULK_JAW = registerBlock("sculk_jaw",
			() -> new SculkJawBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).speedFactor(0.75f)
					.jumpFactor(0.5f).explosionResistance(6f).sound(SoundType.SCULK)));

	public static final RegistryObject<Block> SCULK_ROOTS = registerBlock("sculk_roots",
			() -> new SculkRootBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_VEIN).speedFactor(0.5f)
					.jumpFactor(0.5f).lightLevel(state -> state.getValue(SculkRootBlock.STAGE) * 3)
					.explosionResistance(0.1f).sound(SoundType.SCULK).randomTicks()));

	// Netherite Anvil
	public static final RegistryObject<Block> NETHERITE_ANVIL = registerBlock("netherite_anvil",
			() -> new NetheriteAnvilBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)));

	// Charcoal block
	public static final RegistryObject<Block> CHARCOAL_BLOCK = registerFuelBlock("charcoal_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.COAL * 9);

	// Ore and material stairs
	public static final RegistryObject<Block> CHARCOAL_STAIRS = registerFuelBlock("charcoal_stairs",
			() -> new StairBlock(() -> ModBlocks.CHARCOAL_BLOCK.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.CHARCOAL_BLOCK.get())) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.COAL * 6);
	public static final RegistryObject<Block> COAL_STAIRS = registerFuelBlock("coal_stairs",
			() -> new StairBlock(() -> Blocks.COAL_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.COAL * 6);
	public static final RegistryObject<Block> RAW_IRON_STAIRS = registerBlock("raw_iron_stairs",
			() -> new StairBlock(() -> Blocks.RAW_IRON_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
	public static final RegistryObject<Block> IRON_STAIRS = registerBlock("iron_stairs",
			() -> new StairBlock(() -> Blocks.IRON_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
	public static final RegistryObject<Block> RAW_COPPER_STAIRS = registerBlock("raw_copper_stairs",
			() -> new StairBlock(() -> Blocks.RAW_COPPER_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK)));
	public static final RegistryObject<Block> RAW_GOLD_STAIRS = registerBlock("raw_gold_stairs",
			() -> new StairBlock(() -> Blocks.RAW_GOLD_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)));
	public static final RegistryObject<Block> GOLD_STAIRS = registerBlock("gold_stairs",
			() -> new StairBlock(() -> Blocks.GOLD_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
	public static final RegistryObject<Block> REDSTONE_STAIRS = registerBlock("redstone_stairs",
			() -> new StairBlock(() -> Blocks.REDSTONE_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.REDSTONE_BLOCK)) {

				@Override
				public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 11;
				}

				@Override
				public boolean isSignalSource(BlockState p_60571_) {
					return true;
				}

			});
	public static final RegistryObject<Block> LAPIS_STAIRS = registerBlock("lapis_stairs",
			() -> new StairBlock(() -> Blocks.LAPIS_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK)));
	public static final RegistryObject<Block> EMERALD_STAIRS = registerBlock("emerald_stairs",
			() -> new StairBlock(() -> Blocks.EMERALD_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK)));
	public static final RegistryObject<Block> DIAMOND_STAIRS = registerBlock("diamond_stairs",
			() -> new StairBlock(() -> Blocks.DIAMOND_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
	public static final RegistryObject<Block> NETHERITE_STAIRS = registerBlock("netherite_stairs",
			() -> new StairBlock(() -> Blocks.NETHERITE_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> OBSIDIAN_STAIRS = registerBlock("obsidian_stairs",
			() -> new StairBlock(() -> Blocks.OBSIDIAN.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)));
	public static final RegistryObject<Block> CRYING_OBSIDIAN_STAIRS = registerBlock("crying_obsidian_stairs",
			() -> new StairBlock(() -> Blocks.CRYING_OBSIDIAN.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.CRYING_OBSIDIAN)));
	public static final RegistryObject<Block> AMETHYST_STAIRS = registerBlock("amethyst_stairs",
			() -> new StairBlock(() -> Blocks.AMETHYST_BLOCK.defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
	public static final RegistryObject<Block> ZIRCON_STAIRS = registerBlock("zircon_stairs",
			() -> new StairBlock(() -> ModBlocks.ZIRCON_BLOCK.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.ZIRCON_BLOCK.get())));
	public static final RegistryObject<Block> RAW_ZIRCONIUM_STAIRS = registerBlock("raw_zirconium_stairs",
			() -> new StairBlock(() -> ModBlocks.RAW_ZIRCONIUM_BLOCK.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.RAW_ZIRCONIUM_BLOCK.get())));
	public static final RegistryObject<Block> ZIRCONIUM_STAIRS = registerBlock("zirconium_stairs",
			() -> new StairBlock(() -> ModBlocks.ZIRCONIUM_BLOCK.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.ZIRCONIUM_BLOCK.get())));
	public static final RegistryObject<Block> CITRINE_STAIRS = registerBlock("citrine_stairs",
			() -> new StairBlock(() -> ModBlocks.CITRINE_BLOCK.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(ModBlocks.CITRINE_BLOCK.get())));

	// Ore and material slabs
	public static final RegistryObject<Block> CHARCOAL_SLAB = registerFuelBlock("charcoal_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.CHARCOAL_BLOCK.get())) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.COAL * 3);
	public static final RegistryObject<Block> COAL_SLAB = registerFuelBlock("coal_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}

				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			}, BurnTimes.COAL * 3);
	public static final RegistryObject<Block> RAW_IRON_SLAB = registerBlock("raw_iron_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
	public static final RegistryObject<Block> IRON_SLAB = registerBlock("iron_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
	public static final RegistryObject<Block> RAW_COPPER_SLAB = registerBlock("raw_copper_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK)));
	public static final RegistryObject<Block> RAW_GOLD_SLAB = registerBlock("raw_gold_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)));
	public static final RegistryObject<Block> GOLD_SLAB = registerBlock("gold_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
	public static final RegistryObject<Block> REDSTONE_SLAB = registerBlock("redstone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_BLOCK)) {

				@Override
				public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 7;
				}

				@Override
				public boolean isSignalSource(BlockState p_60571_) {
					return true;
				}

			});
	public static final RegistryObject<Block> LAPIS_SLAB = registerBlock("lapis_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK)));
	public static final RegistryObject<Block> EMERALD_SLAB = registerBlock("emerald_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK)));
	public static final RegistryObject<Block> DIAMOND_SLAB = registerBlock("diamond_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
	public static final RegistryObject<Block> NETHERITE_SLAB = registerBlock("netherite_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> OBSIDIAN_SLAB = registerBlock("obsidian_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)));
	public static final RegistryObject<Block> CRYING_OBSIDIAN_SLAB = registerBlock("crying_obsidian_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CRYING_OBSIDIAN)));
	public static final RegistryObject<Block> AMETHYST_SLAB = registerBlock("amethyst_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
	public static final RegistryObject<Block> ZIRCON_SLAB = registerBlock("zircon_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.ZIRCON_BLOCK.get())));
	public static final RegistryObject<Block> RAW_ZIRCONIUM_SLAB = registerBlock("raw_zirconium_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.RAW_ZIRCONIUM_BLOCK.get())));
	public static final RegistryObject<Block> ZIRCONIUM_SLAB = registerBlock("zirconium_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.ZIRCONIUM_BLOCK.get())));
	public static final RegistryObject<Block> CITRINE_SLAB = registerBlock("citrine_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.CITRINE_BLOCK.get())));

	// Copper buttons
	public static final RegistryObject<Block> COPPER_BUTTON = registerBlock("copper_button",
			() -> new WeatheringCopperButtonBlock(WeatherState.UNAFFECTED, 80,
					BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).randomTicks()));
	public static final RegistryObject<Block> EXPOSED_COPPER_BUTTON = registerBlock("exposed_copper_button",
			() -> new WeatheringCopperButtonBlock(WeatherState.EXPOSED, 40,
					BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).randomTicks()));
	public static final RegistryObject<Block> WEATHERED_COPPER_BUTTON = registerBlock("weathered_copper_button",
			() -> new WeatheringCopperButtonBlock(WeatherState.WEATHERED, 20,
					BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).randomTicks()));
	public static final RegistryObject<Block> OXIDIZED_COPPER_BUTTON = registerBlock("oxidized_copper_button",
			() -> new WeatheringCopperButtonBlock(WeatherState.OXIDIZED, 10,
					BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).randomTicks()));
	public static final RegistryObject<Block> WAXED_COPPER_BUTTON = registerBlock("waxed_copper_button",
			() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.IRON, 80, false));
	public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_BUTTON = registerBlock("waxed_exposed_copper_button",
			() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.IRON, 40, false));
	public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_BUTTON = registerBlock(
			"waxed_weathered_copper_button",
			() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.IRON, 20, false));
	public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_BUTTON = registerBlock(
			"waxed_oxidized_copper_button",
			() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.IRON, 10, false));

	// Soul sandstone
	public static final RegistryObject<Block> SOUL_SANDSTONE = registerBlock("soul_sandstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.SANDSTONE)));
	public static final RegistryObject<Block> CHISELED_SOUL_SANDSTONE = registerBlock("chiseled_soul_sandstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_SANDSTONE)));
	public static final RegistryObject<Block> SOUL_SANDSTONE_STAIRS = registerBlock("soul_sandstone_stairs",
			() -> new StairBlock(() -> ModBlocks.SOUL_SANDSTONE.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.SANDSTONE_STAIRS)));
	public static final RegistryObject<Block> SOUL_SANDSTONE_SLAB = registerBlock("soul_sandstone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE_SLAB)));
	public static final RegistryObject<Block> SOUL_SANDSTONE_WALL = registerBlock("soul_sandstone_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE_WALL)));
	public static final RegistryObject<Block> SMOOTH_SOUL_SANDSTONE = registerBlock("smooth_soul_sandstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE)));
	public static final RegistryObject<Block> SMOOTH_SOUL_SANDSTONE_STAIRS = registerBlock("smooth_soul_sandstone_stairs",
			() -> new StairBlock(() -> ModBlocks.SOUL_SANDSTONE.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE_STAIRS)));
	public static final RegistryObject<Block> SMOOTH_SOUL_SANDSTONE_SLAB = registerBlock("smooth_soul_sandstone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE_SLAB)));
	public static final RegistryObject<Block> CUT_SOUL_SANDSTONE = registerBlock("cut_soul_sandstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE)));
	public static final RegistryObject<Block> CUT_SOUL_SANDSTONE_SLAB = registerBlock("cut_soul_sandstone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE_SLAB)));

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