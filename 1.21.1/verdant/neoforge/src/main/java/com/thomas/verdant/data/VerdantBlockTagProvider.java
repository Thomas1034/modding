package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.util.CommonTags;
import com.thomas.verdant.util.VerdantTags;
import com.thomas.verdant.woodset.WoodSet;
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
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            generateFor(woodSet);
        }

        // Mineables
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                BlockRegistry.LEAFY_STRANGLER_VINE.get(),
                BlockRegistry.STRANGLER_VINE.get(),
                BlockRegistry.THORN_SPIKES.get(),
                BlockRegistry.THORN_TRAP.get()/*, BlockRegistry.IMBUED_VERDANT_HEARTWOOD_LOG.get(), BlockRegistry.FISH_TRAP_BLOCK.get()*/
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.DIRT_COAL_ORE.get(),
                BlockRegistry.DIRT_COPPER_ORE.get(),
                BlockRegistry.DIRT_DIAMOND_ORE.get(),
                BlockRegistry.DIRT_EMERALD_ORE.get(),
                BlockRegistry.DIRT_GOLD_ORE.get(),
                BlockRegistry.DIRT_IRON_ORE.get(),
                BlockRegistry.DIRT_LAPIS_ORE.get(),
                BlockRegistry.DIRT_REDSTONE_ORE.get()/*, BlockRegistry.CASSAVA_ROOTED_DIRT.get(),
                BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get(), BlockRegistry.VERDANT_CONDUIT.get()*/
        );
        this.tag(BlockTags.MINEABLE_WITH_HOE).addTag(VerdantTags.Blocks.STRANGLER_LEAVES);
        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(/*BlockRegistry.THORN_BUSH.get(), BlockRegistry.BUSH.get(),*/BlockRegistry.STRANGLER_TENDRIL.get(),
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
                BlockRegistry.POISON_IVY_PLANT.get()/*, BlockRegistry.THORN_BUSH.get(), BlockRegistry.BUSH.get()*/
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockRegistry.IRON_SPIKES.get(), BlockRegistry.IRON_TRAP.get());

        this.tag(BlockTags.LEAVES).addTag(VerdantTags.Blocks.STRANGLER_LEAVES);


        //        // Mechanical.
        this.tag(BlockTags.CROPS).add(
                /*
                BlockRegistry.CASSAVA_CROP.get(),
                BlockRegistry.BITTER_CASSAVA_CROP.get(),
                 */
                BlockRegistry.COFFEE_CROP.get());
        this.tag(BlockTags.DIRT).add(
                BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get()
                /*, BlockRegistry.CASSAVA_ROOTED_DIRT.get(), BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get()*/
        );
        this.tag(BlockTags.MUSHROOM_GROW_BLOCK).add(
                BlockRegistry.VERDANT_ROOTED_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_ROOTED_MUD.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get()
        );

        this.tag(BlockTags.CLIMBABLE).add(
                BlockRegistry.STRANGLER_VINE.get(),
                BlockRegistry.STRANGLER_TENDRIL_PLANT.get(),
                BlockRegistry.STRANGLER_TENDRIL.get(),
                BlockRegistry.POISON_IVY.get(),
                BlockRegistry.POISON_IVY_PLANT.get(),
                BlockRegistry.ROPE.get(),
                BlockRegistry.ROPE_HOOK.get()/*, BlockRegistry.ROPE_LADDER.get()*/
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


        //        this.tag(BlockTags.CANDLE_CAKES).add(BlockRegistry.CANDLE_UBE_CAKE.get(), BlockRegistry.WHITE_CANDLE_UBE_CAKE.get(),
        //                BlockRegistry.ORANGE_CANDLE_UBE_CAKE.get(), BlockRegistry.MAGENTA_CANDLE_UBE_CAKE.get(),
        //                BlockRegistry.LIGHT_BLUE_CANDLE_UBE_CAKE.get(), BlockRegistry.YELLOW_CANDLE_UBE_CAKE.get(),
        //                BlockRegistry.LIME_CANDLE_UBE_CAKE.get(), BlockRegistry.PINK_CANDLE_UBE_CAKE.get(),
        //                BlockRegistry.GRAY_CANDLE_UBE_CAKE.get(), BlockRegistry.LIGHT_GRAY_CANDLE_UBE_CAKE.get(),
        //                BlockRegistry.CYAN_CANDLE_UBE_CAKE.get(), BlockRegistry.PURPLE_CANDLE_UBE_CAKE.get(),
        //                BlockRegistry.BLUE_CANDLE_UBE_CAKE.get(), BlockRegistry.BROWN_CANDLE_UBE_CAKE.get(),
        //                BlockRegistry.GREEN_CANDLE_UBE_CAKE.get(), BlockRegistry.RED_CANDLE_UBE_CAKE.get(),
        //                BlockRegistry.BLACK_CANDLE_UBE_CAKE.get());

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
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).remove(BlockTags.FIRE);
        this.tag(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES).remove(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES);

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
        this.tag(BlockTags.SMALL_FLOWERS).add(BlockRegistry.BLEEDING_HEART.get());
        this.tag(BlockTags.SMALL_FLOWERS).add(BlockRegistry.WILD_COFFEE.get());
        this.tag(BlockTags.SMALL_FLOWERS).add(BlockRegistry.TIGER_LILY.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_BLEEDING_HEART.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_WILD_COFFEE.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_COFFEE_CROP.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_THORN_BUSH.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_BUSH.get());
        this.tag(BlockTags.FLOWER_POTS).add(BlockRegistry.POTTED_TIGER_LILY.get());

        // REMOVE vines from tree replaceables.
        this.tag(BlockTags.REPLACEABLE_BY_TREES).remove(VerdantTags.Blocks.STRANGLER_VINES);

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


    public void generateFor(WoodSet woodSet) {
        this.tag(woodSet.getLogs()).add(
                woodSet.getLog().get(),
                woodSet.getWood().get(),
                woodSet.getStrippedLog().get(),
                woodSet.getStrippedWood().get()
        );
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                woodSet.getLog().get(),
                woodSet.getWood().get(),
                woodSet.getStrippedLog().get(),
                woodSet.getStrippedWood().get(),
                woodSet.getPlanks().get(),
                woodSet.getSlab().get(),
                woodSet.getStairs().get(),
                woodSet.getFence().get(),
                woodSet.getFenceGate().get(),
                woodSet.getSign().get(),
                woodSet.getWallSign().get(),
                woodSet.getHangingSign().get(),
                woodSet.getWallHangingSign().get(),
                woodSet.getButton().get(),
                woodSet.getPressurePlate().get(),
                woodSet.getDoor().get(),
                woodSet.getTrapdoor().get()
        );
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(woodSet.getTrapdoor().get());
        this.tag(BlockTags.WOODEN_DOORS).add(woodSet.getDoor().get());
        this.tag(BlockTags.WOODEN_SLABS).add(woodSet.getSlab().get());
        this.tag(BlockTags.WOODEN_STAIRS).add(woodSet.getStairs().get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(woodSet.getButton().get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(woodSet.getPressurePlate().get());
        this.tag(BlockTags.WOODEN_FENCES).add(woodSet.getFence().get());
        this.tag(BlockTags.PLANKS).add(woodSet.getPlanks().get());
        this.tag(BlockTags.LOGS).add(
                woodSet.getLog().get(),
                woodSet.getWood().get(),
                woodSet.getStrippedLog().get(),
                woodSet.getStrippedWood().get()
        );
        if (woodSet.isFlammable()) {
            this.tag(BlockTags.LOGS_THAT_BURN).add(
                    woodSet.getLog().get(),
                    woodSet.getWood().get(),
                    woodSet.getStrippedLog().get(),
                    woodSet.getStrippedWood().get()
            );
        }
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(woodSet.getFenceGate().get());
        this.tag(Tags.Blocks.FENCES_WOODEN).add(woodSet.getFence().get());
    }
}
