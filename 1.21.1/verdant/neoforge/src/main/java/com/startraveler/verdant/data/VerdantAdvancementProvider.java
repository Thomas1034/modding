package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.advancement.VerdantPlantAttackTriggerInstance;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.MobEffectRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class VerdantAdvancementProvider {

    public static void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {

        // With wiki comments for future reference.

        // Create an advancement builder using the static #advancement() method.
        // Using #advancement() automatically enables telemetry events. If you do not want this,
        // #recipeAdvancement() can be used instead, there are no other functional differences.
        Advancement.Builder builder = Advancement.Builder.advancement();
        // Sets the display properties of the advancement. This can either be a DisplayInfo object,
        // or pass in the values directly. If values are passed in directly, a DisplayInfo object will be created for you.
        builder.display(
                // The advancement icon. Can be an ItemStack or an ItemLike.
                new ItemStack(BlockRegistry.VERDANT_ROOTED_DIRT.get()),
                // The advancement title and description. Don't forget to add translations for these!
                Component.translatable("advancements.verdant.root.title"),
                Component.translatable("advancements.verdant.root.description"),
                // The background texture. Use null if you don't want a background texture (for non-root advancements).
                ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID,
                        "textures/gui/advancements/backgrounds/verdant.png"
                ),
                // The frame type. Valid values are AdvancementType.TASK, CHALLENGE, or GOAL.
                AdvancementType.TASK,
                // Whether to show the advancement toast or not.
                false,
                // Whether to announce the advancement into chat or not.
                false,
                // Whether the advancement should be hidden or not.
                false
        );
        // Always triggers.
        builder.addCriterion(
                "always",
                PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location()
                        .setY(MinMaxBounds.Doubles.ANY))
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("always")));
        AdvancementHolder root = builder.save(writer, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "root"));


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(Blocks.MOSSY_STONE_BRICKS),
                Component.translatable("advancements.verdant.petrichor.title"),
                Component.translatable("advancements.verdant.petrichor.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(root);
        builder.addCriterion(
                "pyramid",
                PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location()
                        .setStructures(registries.lookupOrThrow(Registries.STRUCTURE)
                                .getOrThrow(VerdantTags.Structures.CONTAINS_VERDANT)))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("pyramid")));
        AdvancementHolder petrichor = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "petrichor")
        );


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.HEART_OF_THE_FOREST.get()),
                Component.translatable("advancements.verdant.overgrowth.title"),
                Component.translatable("advancements.verdant.overgrowth.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(petrichor);
        builder.addCriterion(
                "heart",
                InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.HEART_OF_THE_FOREST.get())
        );
        builder.addCriterion(
                "conduit",
                EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects()
                        .and(MobEffectRegistry.VERDANT_ENERGY.asHolder()))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("heart", "conduit")));
        AdvancementHolder overgrowth = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "overgrowth")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.VERDANT_GRASS_DIRT.get()),
                Component.translatable("advancements.verdant.stand_on_verdant_ground.title"),
                Component.translatable("advancements.verdant.stand_on_verdant_ground.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(overgrowth);
        builder.addCriterion(
                "on_verdant_ground",
                PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity()
                        .steppingOn(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(
                                                registries.lookupOrThrow(Registries.BLOCK),
                                                VerdantTags.Blocks.VERDANT_GROUND
                                        ))))
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("on_verdant_ground")));
        AdvancementHolder stand_on_verdant_ground = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "stand_on_verdant_ground")
        );


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(WoodSets.STRANGLER.getLog().get()),
                Component.translatable("advancements.verdant.inside_tree.title"),
                Component.translatable("advancements.verdant.inside_tree.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(stand_on_verdant_ground);
        builder.addCriterion(
                "inside_tree",
                PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity()
                        .located(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), WoodSets.STRANGLER.getLogs()))))
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("inside_tree")));
        AdvancementHolder inside_tree = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "inside_tree")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.SNAPLEAF.get()),
                Component.translatable("advancements.verdant.trap_plant.title"),
                Component.translatable("advancements.verdant.trap_plant.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(stand_on_verdant_ground);
        builder.addCriterion(
                "trap_plant", VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), BlockRegistry.SNAPLEAF.get()))
                                .build()), new BlockPos(0, 0, 0)
                )))
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("trap_plant")));
        AdvancementHolder trap_plant = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "trap_plant")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.STINKING_BLOSSOM.get()),
                Component.translatable("advancements.verdant.stinking_blossom.title"),
                Component.translatable("advancements.verdant.stinking_blossom.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(stand_on_verdant_ground);
        builder.addCriterion(
                "stinking_blossom",
                VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(
                                                registries.lookupOrThrow(Registries.BLOCK),
                                                BlockRegistry.STINKING_BLOSSOM.get()
                                        ))
                                .build()), new BlockPos(0, 0, 0)
                )))
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("stinking_blossom")));
        AdvancementHolder stinking_blossom = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "stinking_blossom")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.THORN_BUSH.get()),
                Component.translatable("advancements.verdant.thorn_bush.title"),
                Component.translatable("advancements.verdant.thorn_bush.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(stand_on_verdant_ground);
        builder.addCriterion(
                "thorn_bush", VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), BlockRegistry.THORN_BUSH.get()))
                                .build()), new BlockPos(0, 0, 0)
                )))
        );
        builder.addCriterion(
                "thorny_leaves",
                VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(
                                registries.lookupOrThrow(Registries.BLOCK),
                                BlockRegistry.THORNY_STRANGLER_LEAVES.get()
                        )).build()), new BlockPos(0, -1, 0)
                )))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("thorn_bush", "thorny_leaves")));
        AdvancementHolder thorn_bush = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "thorn_bush")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.POISON_IVY.get()),
                Component.translatable("advancements.verdant.poison_ivy.title"),
                Component.translatable("advancements.verdant.poison_ivy.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(stand_on_verdant_ground);
        builder.addCriterion(
                "poison_ivy", VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), BlockRegistry.POISON_IVY.get()))
                                .build()), new BlockPos(0, 0, 0)
                )))
        );
        builder.addCriterion(
                "poison_ivy_plant",
                VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(
                                                registries.lookupOrThrow(Registries.BLOCK),
                                                BlockRegistry.POISON_IVY_PLANT.get()
                                        ))
                                .build()), new BlockPos(0, 0, 0)
                )))
        );
        builder.addCriterion(
                "poison_ivy_above",
                VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), BlockRegistry.POISON_IVY.get()))
                                .build()), new BlockPos(0, 1, 0)
                )))
        );
        builder.addCriterion(
                "poison_ivy_plant_above",
                VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(
                                                registries.lookupOrThrow(Registries.BLOCK),
                                                BlockRegistry.POISON_IVY_PLANT.get()
                                        ))
                                .build()), new BlockPos(0, 1, 0)
                )))
        );
        builder.addCriterion(
                "poison_leaves",
                VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(
                                registries.lookupOrThrow(Registries.BLOCK),
                                BlockRegistry.POISON_STRANGLER_LEAVES.get()
                        )).build()), new BlockPos(0, -1, 0)
                )))
        );
        builder.requirements(new AdvancementRequirements(List.of(List.of(
                "poison_ivy",
                "poison_ivy_plant",
                "poison_ivy_above",
                "poison_ivy_plant_above",
                "poison_leaves"
        ))));
        AdvancementHolder poison_ivy = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "poison_ivy")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(WoodSets.HEARTWOOD.getLog().get()),
                Component.translatable("advancements.verdant.strong_trees.title"),
                Component.translatable("advancements.verdant.strong_trees.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(stand_on_verdant_ground);
        builder.addCriterion("log", InventoryChangeTrigger.TriggerInstance.hasItems(WoodSets.HEARTWOOD.getLog().get()));
        builder.requirements(AdvancementRequirements.allOf(List.of("log")));
        AdvancementHolder strong_trees = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "strong_trees")
        );


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.DIRT_DIAMOND_ORE.get()),
                Component.translatable("advancements.verdant.deep_roots.title"),
                Component.translatable("advancements.verdant.deep_roots.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(strong_trees);
        builder.addCriterion(
                "deep_roots",
                PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity()
                        .steppingOn(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(
                                                registries.lookupOrThrow(Registries.BLOCK),
                                                VerdantTags.Blocks.VERDANT_GROUND
                                        )))
                        .located(LocationPredicate.Builder.location().setY(MinMaxBounds.Doubles.atMost(0))))
        );

        builder.requirements(AdvancementRequirements.allOf(List.of("deep_roots")));
        AdvancementHolder deep_roots = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "deep_roots")
        );


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(Items.DIAMOND_AXE),
                Component.translatable("advancements.verdant.rip_them_all_down.title"),
                Component.translatable("advancements.verdant.rip_them_all_down.description"),
                null,
                AdvancementType.CHALLENGE,
                true,
                true,
                true
        );
        builder.parent(deep_roots);
        builder.addCriterion(
                "rip_them_all_down",
                InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
                        .of(registries.lookupOrThrow(Registries.ITEM), WoodSets.HEARTWOOD.getLog().get())
                        .withCount(MinMaxBounds.Ints.atLeast(128)))
        );
        builder.rewards(AdvancementRewards.Builder.experience(50));
        builder.requirements(AdvancementRequirements.allOf(List.of("rip_them_all_down")));
        AdvancementHolder rip_them_all_down = builder.save(
                writer,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rip_them_all_down")
        );
    }


}
