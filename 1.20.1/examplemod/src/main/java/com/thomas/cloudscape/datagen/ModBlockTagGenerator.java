package com.thomas.cloudscape.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.util.ModTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagGenerator extends BlockTagsProvider {
	public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, ZirconMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {
		// this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES).add(ModBlocks.SAPPHIRE_ORE.get()).addTag(Tags.Blocks.ORES);

		// Mineables
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ZIRCON_BLOCK.get(), ModBlocks.ZIRCON_ORE.get(),
				ModBlocks.DEEPSLATE_ZIRCON_ORE.get(), ModBlocks.ECHO_BLOCK.get(), ModBlocks.RAW_ZIRCONIUM_BLOCK.get(),
				ModBlocks.THIRSTY_MUD_BRICKS.get(), ModBlocks.THIRSTY_PACKED_MUD.get(), ModBlocks.ZIRCONIUM_BLOCK.get(),
				ModBlocks.CITRINE_BLOCK.get(), ModBlocks.CITRINE_LANTERN.get(), ModBlocks.CITRINE_CLUSTER.get(),
				ModBlocks.LARGE_CITRINE_BUD.get(), ModBlocks.MEDIUM_CITRINE_BUD.get(),
				ModBlocks.SMALL_CITRINE_BUD.get(), ModBlocks.CLOUD_BRICKS.get(), ModBlocks.RAIN_CLOUD_BRICKS.get(),
				ModBlocks.THUNDER_CLOUD_BRICKS.get(), ModBlocks.CLOUD_BRICK_WALL.get(),
				ModBlocks.RAIN_CLOUD_BRICK_WALL.get(), ModBlocks.THUNDER_CLOUD_BRICK_WALL.get(),
				ModBlocks.CLOUD_BRICK_STAIRS.get(), ModBlocks.RAIN_CLOUD_BRICK_STAIRS.get(),
				ModBlocks.THUNDER_CLOUD_BRICK_STAIRS.get(), ModBlocks.CLOUD_BRICK_SLAB.get(),
				ModBlocks.RAIN_CLOUD_BRICK_SLAB.get(), ModBlocks.THUNDER_CLOUD_BRICK_SLAB.get(),
				ModBlocks.CLOUD_BRICK_PILLAR.get(), ModBlocks.RAIN_CLOUD_BRICK_PILLAR.get(),
				ModBlocks.THUNDER_CLOUD_BRICK_PILLAR.get(), ModBlocks.CHISELED_CLOUD_BRICKS.get(),
				ModBlocks.CHISELED_RAIN_CLOUD_BRICKS.get(), ModBlocks.CHISELED_THUNDER_CLOUD_BRICKS.get(),
				ModBlocks.PETRIFIED_LOG.get(), ModBlocks.RESONATOR_BLOCK.get(), ModBlocks.NETHERITE_ANVIL.get(),
				ModBlocks.GOLD_SLAB.get(), ModBlocks.GOLD_STAIRS.get(), ModBlocks.RAW_GOLD_SLAB.get(),
				ModBlocks.RAW_GOLD_STAIRS.get(), ModBlocks.EMERALD_SLAB.get(), ModBlocks.EMERALD_STAIRS.get(),
				ModBlocks.REDSTONE_SLAB.get(), ModBlocks.REDSTONE_STAIRS.get(), ModBlocks.LAPIS_SLAB.get(),
				ModBlocks.LAPIS_STAIRS.get(), ModBlocks.DIAMOND_SLAB.get(), ModBlocks.DIAMOND_STAIRS.get(),
				ModBlocks.ZIRCONIUM_SLAB.get(), ModBlocks.ZIRCONIUM_STAIRS.get(), ModBlocks.RAW_ZIRCONIUM_SLAB.get(),
				ModBlocks.RAW_ZIRCONIUM_STAIRS.get(), ModBlocks.OBSIDIAN_SLAB.get(), ModBlocks.OBSIDIAN_STAIRS.get(),
				ModBlocks.CRYING_OBSIDIAN_SLAB.get(), ModBlocks.CRYING_OBSIDIAN_STAIRS.get(),
				ModBlocks.NETHERITE_SLAB.get(), ModBlocks.NETHERITE_STAIRS.get(), ModBlocks.RAW_IRON_SLAB.get(),
				ModBlocks.RAW_IRON_STAIRS.get(), ModBlocks.IRON_SLAB.get(), ModBlocks.IRON_STAIRS.get(),
				ModBlocks.ZIRCON_SLAB.get(), ModBlocks.ZIRCON_STAIRS.get(), ModBlocks.CITRINE_SLAB.get(),
				ModBlocks.CITRINE_STAIRS.get(), ModBlocks.AMETHYST_SLAB.get(), ModBlocks.AMETHYST_STAIRS.get(),
				ModBlocks.COAL_SLAB.get(), ModBlocks.COAL_STAIRS.get(), ModBlocks.COPPER_BUTTON.get(),
				ModBlocks.EXPOSED_COPPER_BUTTON.get(), ModBlocks.WEATHERED_COPPER_BUTTON.get(),
				ModBlocks.OXIDIZED_COPPER_BUTTON.get());

		this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.PALM_LOG.get(), ModBlocks.PALM_WOOD.get(),
				ModBlocks.STRIPPED_PALM_LOG.get(), ModBlocks.STRIPPED_PALM_WOOD.get(), ModBlocks.PALM_BUTTON.get(),
				ModBlocks.PALM_DOOR.get(), ModBlocks.PALM_FENCE.get(), ModBlocks.PALM_FENCE_GATE.get(),
				ModBlocks.PALM_PLANKS.get(), ModBlocks.PALM_PRESSURE_PLATE.get(), ModBlocks.PALM_SLAB.get(),
				ModBlocks.PALM_TRAPDOOR.get(), ModBlocks.CARPENTRY_TABLE.get(), ModBlocks.PALM_TRUNK.get());

		// Tool tiers
		this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.ECHO_BLOCK.get(), ModBlocks.ZIRCONIUM_BLOCK.get(),
				ModBlocks.RAW_ZIRCONIUM_BLOCK.get(), ModBlocks.GOLD_SLAB.get(), ModBlocks.GOLD_STAIRS.get(),
				ModBlocks.RAW_GOLD_SLAB.get(), ModBlocks.RAW_GOLD_STAIRS.get(), ModBlocks.EMERALD_SLAB.get(),
				ModBlocks.EMERALD_STAIRS.get(), ModBlocks.REDSTONE_SLAB.get(), ModBlocks.REDSTONE_STAIRS.get(),
				ModBlocks.LAPIS_SLAB.get(), ModBlocks.LAPIS_STAIRS.get(), ModBlocks.DIAMOND_SLAB.get(),
				ModBlocks.DIAMOND_STAIRS.get(), ModBlocks.ZIRCONIUM_SLAB.get(), ModBlocks.ZIRCONIUM_STAIRS.get(),
				ModBlocks.RAW_ZIRCONIUM_SLAB.get(), ModBlocks.RAW_ZIRCONIUM_STAIRS.get());

		this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.NETHERITE_ANVIL.get(), ModBlocks.OBSIDIAN_SLAB.get(),
				ModBlocks.OBSIDIAN_STAIRS.get(), ModBlocks.CRYING_OBSIDIAN_SLAB.get(),
				ModBlocks.CRYING_OBSIDIAN_STAIRS.get(), ModBlocks.NETHERITE_SLAB.get(),
				ModBlocks.NETHERITE_STAIRS.get());

		this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.ZIRCON_BLOCK.get(), ModBlocks.ZIRCON_ORE.get(),
				ModBlocks.DEEPSLATE_ZIRCON_ORE.get(), ModBlocks.CITRINE_BLOCK.get(), ModBlocks.CITRINE_CLUSTER.get(),
				ModBlocks.LARGE_CITRINE_BUD.get(), ModBlocks.MEDIUM_CITRINE_BUD.get(),
				ModBlocks.SMALL_CITRINE_BUD.get(), ModBlocks.PETRIFIED_LOG.get(), ModBlocks.RAW_IRON_SLAB.get(),
				ModBlocks.RAW_IRON_STAIRS.get(), ModBlocks.IRON_SLAB.get(), ModBlocks.IRON_STAIRS.get(),
				ModBlocks.ZIRCON_SLAB.get(), ModBlocks.ZIRCON_STAIRS.get(), ModBlocks.CITRINE_SLAB.get(),
				ModBlocks.CITRINE_STAIRS.get(), ModBlocks.AMETHYST_SLAB.get(), ModBlocks.AMETHYST_STAIRS.get());

		this.tag(BlockTags.STONE_ORE_REPLACEABLES).add(ModBlocks.PETRIFIED_LOG.get());

		// this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(ModBlocks.END_STONE_SAPPHIRE_ORE.get());

		// this.tag(ModTags.Blocks.NEEDS_SAPPHIRE_TOOL).add(ModBlocks.SOUND_BLOCK.get());

		// Other block tags

		this.tag(BlockTags.BUTTONS).add(ModBlocks.COPPER_BUTTON.get(), ModBlocks.EXPOSED_COPPER_BUTTON.get(),
				ModBlocks.WEATHERED_COPPER_BUTTON.get(), ModBlocks.OXIDIZED_COPPER_BUTTON.get());

		// Palm furniture
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.PALM_TRAPDOOR.get());
		this.tag(BlockTags.TRAPDOORS).add(ModBlocks.PALM_TRAPDOOR.get());
		this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.PALM_DOOR.get());
		this.tag(BlockTags.DOORS).add(ModBlocks.PALM_DOOR.get());
		this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.PALM_SLAB.get());
		this.tag(BlockTags.SLABS).add(ModBlocks.PALM_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.PALM_STAIRS.get());
		this.tag(BlockTags.STAIRS).add(ModBlocks.PALM_STAIRS.get());
		this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.PALM_BUTTON.get());
		this.tag(BlockTags.BUTTONS).add(ModBlocks.PALM_BUTTON.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.PALM_PRESSURE_PLATE.get());
		this.tag(BlockTags.PRESSURE_PLATES).add(ModBlocks.PALM_PRESSURE_PLATE.get());
		this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.PALM_FENCE.get());
		this.tag(BlockTags.FENCES).add(ModBlocks.PALM_FENCE.get());
		this.tag(BlockTags.FENCE_GATES).add(ModBlocks.PALM_FENCE_GATE.get());
		this.tag(BlockTags.PLANKS).add(ModBlocks.PALM_PLANKS.get());
		this.tag(ModTags.Blocks.PALM_LOGS).add(ModBlocks.PALM_TRUNK.get(), ModBlocks.PALM_LOG.get(),
				ModBlocks.PALM_WOOD.get(), ModBlocks.STRIPPED_PALM_LOG.get(), ModBlocks.STRIPPED_PALM_WOOD.get());
		this.tag(BlockTags.LOGS).add(ModBlocks.PALM_TRUNK.get(), ModBlocks.PALM_LOG.get(), ModBlocks.PALM_WOOD.get(),
				ModBlocks.STRIPPED_PALM_LOG.get(), ModBlocks.STRIPPED_PALM_WOOD.get());

		this.tag(BlockTags.FLOWER_POTS).add(ModBlocks.POTTED_ILLUMINATED_TORCHFLOWER.get(),
				ModBlocks.POTTED_PALM_SAPLING.get(), ModBlocks.POTTED_WHITE_ORCHID.get());

		this.tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.ILLUMINATED_TORCHFLOWER.get(), ModBlocks.WHITE_ORCHID.get());

		// Cloud furniture
		this.tag(BlockTags.MOSS_REPLACEABLE).add(ModBlocks.CLOUD.get());
		this.tag(BlockTags.WALLS).add(ModBlocks.CLOUD_BRICK_WALL.get());
		this.tag(BlockTags.WALLS).add(ModBlocks.RAIN_CLOUD_BRICK_WALL.get());
		this.tag(BlockTags.WALLS).add(ModBlocks.THUNDER_CLOUD_BRICK_WALL.get());
		this.tag(BlockTags.STAIRS).add(ModBlocks.CLOUD_BRICK_STAIRS.get());
		this.tag(BlockTags.STAIRS).add(ModBlocks.RAIN_CLOUD_BRICK_STAIRS.get());
		this.tag(BlockTags.STAIRS).add(ModBlocks.THUNDER_CLOUD_BRICK_STAIRS.get());
		this.tag(BlockTags.SLABS).add(ModBlocks.CLOUD_BRICK_SLAB.get());
		this.tag(BlockTags.SLABS).add(ModBlocks.RAIN_CLOUD_BRICK_SLAB.get());
		this.tag(BlockTags.SLABS).add(ModBlocks.THUNDER_CLOUD_BRICK_SLAB.get());

		// Anvil
		this.tag(BlockTags.ANVIL).add(ModBlocks.NETHERITE_ANVIL.get());

		// Forge
		this.tag(Tags.Blocks.FENCE_GATES).add(ModBlocks.PALM_FENCE_GATE.get());
		this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(ModBlocks.PALM_FENCE_GATE.get());
		this.tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.ZIRCON_ORE.get());
		this.tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get());
		this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.ZIRCON_BLOCK.get());
		this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.ZIRCONIUM_BLOCK.get());
		this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.RAW_ZIRCONIUM_BLOCK.get());
		this.tag(Tags.Blocks.ORES).add(ModBlocks.ZIRCON_ORE.get());
		this.tag(Tags.Blocks.ORES).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get());
		this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.ZIRCON_ORE.get());
		this.tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get());

		// Functions
		this.tag(ModTags.Blocks.BUBBLEFRUIT_CAN_GROW_ON).add(ModBlocks.CLOUD.get(), Blocks.MOSS_BLOCK);
		addStrongCloudSolidifier(Blocks.BUDDING_AMETHYST);
		addStrongCloudSolidifier(Blocks.AMETHYST_CLUSTER);
		addStrongCloudSolidifier(Blocks.LARGE_AMETHYST_BUD);
		addMediumCloudSolidifier(Blocks.MEDIUM_AMETHYST_BUD);
		addWeakCloudSolidifier(Blocks.SMALL_AMETHYST_BUD);
		addWeakCloudSolidifier(Blocks.AMETHYST_BLOCK);
		addStrongCloudSolidifier(ModBlocks.CITRINE_LANTERN.get());
		addStrongCloudSolidifier(ModBlocks.CITRINE_BRACKET.get());
		addStrongCloudSolidifier(ModBlocks.CITRINE_WALL_BRACKET.get());
		addStrongCloudSolidifier(ModBlocks.BUDDING_CITRINE.get());
		addStrongCloudSolidifier(ModBlocks.CITRINE_CLUSTER.get());
		addStrongCloudSolidifier(ModBlocks.LARGE_CITRINE_BUD.get());
		addMediumCloudSolidifier(ModBlocks.MEDIUM_CITRINE_BUD.get());
		addWeakCloudSolidifier(ModBlocks.SMALL_CITRINE_BUD.get());
		addWeakCloudSolidifier(ModBlocks.CITRINE_BLOCK.get());
		addWeakCloudSolidifier(ModBlocks.CITRINE_STAIRS.get());
		addWeakCloudSolidifier(ModBlocks.CITRINE_SLAB.get());
		addCloudPlant(ModBlocks.BUBBLEFRUIT_CROP.get());
		addCloudPlant(ModBlocks.WHITE_ORCHID.get());
		addCloudPlant(Blocks.BLUE_ORCHID);

	}

	private void addStrongCloudSolidifier(Block b) {
		this.tag(ModTags.Blocks.CLOUD_SOLIDIFYING_BLOCKS).add(b);
		this.tag(ModTags.Blocks.STRONG_CLOUD_SOLIDIFYING_BLOCKS).add(b);
	}

	private void addMediumCloudSolidifier(Block b) {
		this.tag(ModTags.Blocks.CLOUD_SOLIDIFYING_BLOCKS).add(b);
		this.tag(ModTags.Blocks.MEDIUM_CLOUD_SOLIDIFYING_BLOCKS).add(b);
	}

	private void addWeakCloudSolidifier(Block b) {
		this.tag(ModTags.Blocks.CLOUD_SOLIDIFYING_BLOCKS).add(b);
		this.tag(ModTags.Blocks.WEAK_CLOUD_SOLIDIFYING_BLOCKS).add(b);
	}

	private void addCloudPlant(Block b) {
		this.tag(ModTags.Blocks.CLOUD_PLANTS).add(b);
	}
}
