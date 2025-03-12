package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.CommonTags;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class VerdantBlockTagProvider extends BlockTagsProvider {
    public VerdantBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // Mineables
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                BlockRegistry.LEAFY_STRANGLER_VINE.get(),
                BlockRegistry.STRANGLER_VINE.get(),
                BlockRegistry.WOODEN_SPIKES.get(),
                BlockRegistry.WOODEN_TRAP.get(),
                BlockRegistry.FISH_TRAP.get(),
                BlockRegistry.VERDANT_CONDUIT.get(),
                BlockRegistry.ROPE_LADDER.get(),
                BlockRegistry.IMBUED_HEARTWOOD_LOG.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.TOXIC_DIRT.get(),
                BlockRegistry.PACKED_GRAVEL.get(),
                BlockRegistry.SCREE.get(),
                BlockRegistry.DIRT_COAL_ORE.get(),
                BlockRegistry.DIRT_COPPER_ORE.get(),
                BlockRegistry.DIRT_DIAMOND_ORE.get(),
                BlockRegistry.DIRT_EMERALD_ORE.get(),
                BlockRegistry.DIRT_GOLD_ORE.get(),
                BlockRegistry.DIRT_IRON_ORE.get(),
                BlockRegistry.DIRT_LAPIS_ORE.get(),
                BlockRegistry.DIRT_REDSTONE_ORE.get(),
                BlockRegistry.CASSAVA_ROOTED_DIRT.get(),
                BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_HOE).addTag(VerdantTags.Blocks.STRANGLER_LEAVES);
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                BlockRegistry.POISON_IVY_BLOCK.get(),
                BlockRegistry.THORN_BUSH.get(),
                BlockRegistry.BUSH.get(),
                BlockRegistry.STRANGLER_TENDRIL.get(),
                BlockRegistry.STRANGLER_TENDRIL_PLANT.get(),
                BlockRegistry.POISON_IVY.get(),
                BlockRegistry.POISON_IVY_PLANT.get()
        );

        this.tag(BlockTags.SWORD_EFFICIENT).addTag(VerdantTags.Blocks.STRANGLER_LEAVES);
        this.tag(BlockTags.SWORD_EFFICIENT).addTag(VerdantTags.Blocks.STRANGLER_VINES);
        this.tag(BlockTags.SWORD_EFFICIENT).add(
                BlockRegistry.STRANGLER_LEAVES.get(),
                BlockRegistry.THORNY_STRANGLER_LEAVES.get(),
                BlockRegistry.POISON_STRANGLER_LEAVES.get(),
                BlockRegistry.STRANGLER_TENDRIL.get(),
                BlockRegistry.STRANGLER_TENDRIL_PLANT.get(),
                BlockRegistry.POISON_IVY.get(),
                BlockRegistry.POISON_IVY_PLANT.get(),
                BlockRegistry.THORN_BUSH.get(),
                BlockRegistry.BUSH.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockRegistry.IRON_SPIKES.get(), BlockRegistry.IRON_TRAP.get());

        this.tag(BlockTags.LEAVES).addTag(VerdantTags.Blocks.STRANGLER_LEAVES);


        // Mechanical.
        this.tag(BlockTags.CROPS).add(
                BlockRegistry.CASSAVA_CROP.get(),
                BlockRegistry.BITTER_CASSAVA_CROP.get(),
                BlockRegistry.COFFEE_CROP.get()
        );
        this.tag(BlockTags.DIRT).add(
                BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get(),
                BlockRegistry.CASSAVA_ROOTED_DIRT.get(),
                BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get()
        );
        this.tag(Tags.Blocks.GRAVELS).add(BlockRegistry.SCREE.get());
        this.tag(BlockTags.MUSHROOM_GROW_BLOCK).add(
                BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get(),
                BlockRegistry.TOXIC_DIRT.get()
        );
        this.tag(BlockTags.DEAD_BUSH_MAY_PLACE_ON).add(BlockRegistry.TOXIC_DIRT.get());

        this.tag(BlockTags.CLIMBABLE).add(
                BlockRegistry.STRANGLER_VINE.get(),
                BlockRegistry.STRANGLER_TENDRIL_PLANT.get(),
                BlockRegistry.STRANGLER_TENDRIL.get(),
                BlockRegistry.POISON_IVY.get(),
                BlockRegistry.POISON_IVY_PLANT.get(),
                BlockRegistry.ROPE.get(),
                BlockRegistry.ROPE_HOOK.get(),
                BlockRegistry.ROPE_LADDER.get()
        );

        this.tag(BlockTags.REPLACEABLE).add(BlockRegistry.POISON_IVY.get(), BlockRegistry.POISON_IVY_PLANT.get());
        this.tag(BlockTags.REPLACEABLE_BY_TREES).add(
                BlockRegistry.POISON_IVY.get(),
                BlockRegistry.POISON_IVY_PLANT.get(),
                BlockRegistry.STRANGLER_TENDRIL_PLANT.get(),
                BlockRegistry.STRANGLER_TENDRIL.get()
        );

        this.tag(BlockTags.BIG_DRIPLEAF_PLACEABLE).add(
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get()
        );
        this.tag(BlockTags.SMALL_DRIPLEAF_PLACEABLE).add(
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get()
        );

        this.tag(VerdantTags.Blocks.ROPES_EXTEND)
                .add(BlockRegistry.ROPE.get(), BlockRegistry.ROPE_HOOK.get(), Blocks.TRIPWIRE_HOOK);

        this.tag(VerdantTags.Blocks.ROPE_HOOKS).add(Blocks.TRIPWIRE_HOOK, BlockRegistry.ROPE_HOOK.get());

        this.tag(BlockTags.LOGS_THAT_BURN).add(BlockRegistry.ROTTEN_WOOD.get());


        this.tag(BlockTags.CANDLE_CAKES).add(
                BlockRegistry.CANDLE_UBE_CAKE.get(),
                BlockRegistry.WHITE_CANDLE_UBE_CAKE.get(),
                BlockRegistry.ORANGE_CANDLE_UBE_CAKE.get(),
                BlockRegistry.MAGENTA_CANDLE_UBE_CAKE.get(),
                BlockRegistry.LIGHT_BLUE_CANDLE_UBE_CAKE.get(),
                BlockRegistry.YELLOW_CANDLE_UBE_CAKE.get(),
                BlockRegistry.LIME_CANDLE_UBE_CAKE.get(),
                BlockRegistry.PINK_CANDLE_UBE_CAKE.get(),
                BlockRegistry.GRAY_CANDLE_UBE_CAKE.get(),
                BlockRegistry.LIGHT_GRAY_CANDLE_UBE_CAKE.get(),
                BlockRegistry.CYAN_CANDLE_UBE_CAKE.get(),
                BlockRegistry.PURPLE_CANDLE_UBE_CAKE.get(),
                BlockRegistry.BLUE_CANDLE_UBE_CAKE.get(),
                BlockRegistry.BROWN_CANDLE_UBE_CAKE.get(),
                BlockRegistry.GREEN_CANDLE_UBE_CAKE.get(),
                BlockRegistry.RED_CANDLE_UBE_CAKE.get(),
                BlockRegistry.BLACK_CANDLE_UBE_CAKE.get()
        );

        // All vine replacable blocks.
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(VerdantTags.Blocks.TENDRILS);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.REPLACEABLE);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.LEAVES);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.REPLACEABLE_BY_TREES);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.FLOWERS);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.SNOW);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.SIGNS);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.WOOL_CARPETS);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.STONE_BUTTONS);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.WOODEN_BUTTONS);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.STONE_PRESSURE_PLATES);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).addTag(BlockTags.WOODEN_PRESSURE_PLATES);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).add(Blocks.VINE);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).add(Blocks.CAVE_VINES);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).add(Blocks.CAVE_VINES_PLANT);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).add(Blocks.AIR);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).add(Blocks.CAVE_AIR);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).add(Blocks.VOID_AIR);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).add(Blocks.WATER);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES)
                .add(BlockRegistry.THORN_BUSH.get(), BlockRegistry.BUSH.get());
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).remove(BlockTags.FIRE);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).remove(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES);

        this.tag(VerdantTags.Blocks.SUPPORTS_STRANGLER_VINES).addTag(BlockTags.LOGS);

        // Leafy blocks
        this.tag(VerdantTags.Blocks.STRANGLER_LEAVES).add(
                BlockRegistry.WILTED_STRANGLER_LEAVES.get(),
                BlockRegistry.STRANGLER_LEAVES.get(),
                BlockRegistry.THORNY_STRANGLER_LEAVES.get(),
                BlockRegistry.POISON_STRANGLER_LEAVES.get(),
                BlockRegistry.LEAFY_STRANGLER_VINE.get()
        );
        this.tag(BlockTags.LEAVES).addTag(VerdantTags.Blocks.STRANGLER_LEAVES);

        // Vines
        this.tag(VerdantTags.Blocks.STRANGLER_VINES)
                .add(BlockRegistry.STRANGLER_VINE.get(), BlockRegistry.LEAFY_STRANGLER_VINE.get());

        this.tag(VerdantTags.Blocks.TENDRILS).add(
                BlockRegistry.STRANGLER_TENDRIL.get(),
                BlockRegistry.STRANGLER_TENDRIL_PLANT.get(),
                BlockRegistry.POISON_IVY.get(),
                BlockRegistry.POISON_IVY_PLANT.get(),
                Blocks.VINE,
                Blocks.CAVE_VINES,
                Blocks.CAVE_VINES_PLANT
        );

        // Verdant ground
        this.tag(VerdantTags.Blocks.VERDANT_GROUND).add(
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get()
        );


        // Verdant logs
        this.tag(VerdantTags.Blocks.SUSTAINS_STRANGLER_LEAVES).addTag(BlockTags.LOGS_THAT_BURN);
        this.tag(VerdantTags.Blocks.SUSTAINS_STRANGLER_LEAVES).addTag(VerdantTags.Blocks.STRANGLER_VINES);

        // Flowers
        this.tag(BlockTags.SMALL_FLOWERS).add(
                BlockRegistry.BLEEDING_HEART.get(),
                BlockRegistry.RUE.get(),
                BlockRegistry.WILD_COFFEE.get(),
                BlockRegistry.TIGER_LILY.get(),
                BlockRegistry.WILD_CASSAVA.get()
        );

        this.tag(BlockTags.BEE_ATTRACTIVE).add(
                BlockRegistry.BLEEDING_HEART.get(),
                BlockRegistry.RUE.get(),
                BlockRegistry.WILD_COFFEE.get(),
                BlockRegistry.TIGER_LILY.get(),
                BlockRegistry.WILD_CASSAVA.get()
        );
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_BLEEDING_HEART.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_WILD_COFFEE.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_COFFEE_CROP.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_THORN_BUSH.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_BUSH.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_TIGER_LILY.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_RUE.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_WILD_CASSAVA.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_WILD_UBE.get());

        // REMOVE vines from tree replaceables.
        this.tag(BlockTags.REPLACEABLE_BY_TREES).remove(VerdantTags.Blocks.STRANGLER_VINES);

        // Intangibility
        this.tag(VerdantTags.Blocks.BLOCKS_INTANGIBLE)
                .add(Blocks.BEDROCK, Blocks.NETHER_PORTAL, Blocks.BARRIER, Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN);

        //
        this.tag(BlockTags.LOGS).add(BlockRegistry.IMBUED_HEARTWOOD_LOG.get());
        this.tag(BlockTags.LOGS_THAT_BURN).add(BlockRegistry.IMBUED_HEARTWOOD_LOG.get());

        this.tag(BlockTags.CROPS).add(
                BlockRegistry.UBE_CROP.get(),
                BlockRegistry.CASSAVA_CROP.get(),
                BlockRegistry.BITTER_CASSAVA_CROP.get(),
                BlockRegistry.COFFEE_CROP.get()
        );
        this.tag(BlockTags.BEE_GROWABLES).add(
                BlockRegistry.UBE_CROP.get(),
                BlockRegistry.CASSAVA_CROP.get(),
                BlockRegistry.BITTER_CASSAVA_CROP.get(),
                BlockRegistry.COFFEE_CROP.get()
        );

        this.tag(VerdantTags.Blocks.BLOCKS_ASH_SPREAD).add(
                Blocks.BEDROCK,
                Blocks.NETHER_PORTAL,
                Blocks.BARRIER,
                Blocks.OBSIDIAN,
                Blocks.CRYING_OBSIDIAN,
                Blocks.SPONGE,
                Blocks.WET_SPONGE,
                Blocks.AMETHYST_BLOCK,
                Blocks.BUDDING_AMETHYST
        );

        this.tag(VerdantTags.Blocks.ALLOWS_ASH_SPREAD)
                .add(Blocks.WATER, BlockRegistry.TOXIC_DIRT.get(), BlockRegistry.TOXIC_ASH_BLOCK.get());
        this.tag(VerdantTags.Blocks.ALLOWS_ASH_SPREAD).addTag(WoodSets.DEAD.getLogs());
        this.tag(VerdantTags.Blocks.ALLOWS_ASH_SPREAD).addTag(BlockTags.MOSS_REPLACEABLE);
        this.tag(VerdantTags.Blocks.ALLOWS_ASH_SPREAD).addTag(BlockTags.LUSH_GROUND_REPLACEABLE);
        this.tag(VerdantTags.Blocks.ALLOWS_ASH_SPREAD).addTag(BlockTags.BAMBOO_PLANTABLE_ON);

        // Dead logs
        this.tag(VerdantTags.Blocks.DOES_NOT_SUPPORT_STRANGLER_VINES).addTag(WoodSets.DEAD.getLogs());

        addDirtOres();
    }

    private void addDirtOres() {
        this.tag(Tags.Blocks.ORES).addTag(CommonTags.Blocks.ORES_IN_GROUND_DIRT);
        this.tag(CommonTags.Blocks.ORES_IN_GROUND_DIRT).add(
                BlockRegistry.DIRT_COAL_ORE.get(),
                BlockRegistry.DIRT_COPPER_ORE.get(),
                BlockRegistry.DIRT_DIAMOND_ORE.get(),
                BlockRegistry.DIRT_EMERALD_ORE.get(),
                BlockRegistry.DIRT_GOLD_ORE.get(),
                BlockRegistry.DIRT_IRON_ORE.get(),
                BlockRegistry.DIRT_LAPIS_ORE.get(),
                BlockRegistry.DIRT_REDSTONE_ORE.get()
        );
        this.tag(Tags.Blocks.ORES_COAL).add(BlockRegistry.DIRT_COAL_ORE.get());
        this.tag(Tags.Blocks.ORES_COPPER).add(BlockRegistry.DIRT_COPPER_ORE.get());
        this.tag(Tags.Blocks.ORES_DIAMOND).add(BlockRegistry.DIRT_DIAMOND_ORE.get());
        this.tag(Tags.Blocks.ORES_EMERALD).add(BlockRegistry.DIRT_EMERALD_ORE.get());
        this.tag(Tags.Blocks.ORES_GOLD).add(BlockRegistry.DIRT_GOLD_ORE.get());
        this.tag(Tags.Blocks.ORES_IRON).add(BlockRegistry.DIRT_IRON_ORE.get());
        this.tag(Tags.Blocks.ORES_LAPIS).add(BlockRegistry.DIRT_LAPIS_ORE.get());
        this.tag(Tags.Blocks.ORES_REDSTONE).add(BlockRegistry.DIRT_REDSTONE_ORE.get());
        this.tag(CommonTags.Blocks.ORE_BEARING_GROUND_DIRT).add(Blocks.DIRT);
    }

}
