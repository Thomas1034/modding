package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.advancement.InventoryChangeItemCountTrigger;
import com.startraveler.verdant.advancement.VerdantPlantAttackTrigger;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.MobEffectRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("unused")
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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "gui/advancements/backgrounds/verdant"),
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
        AdvancementHolder root = builder.save(writer, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "root"));


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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "petrichor")
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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "overgrowth")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.ROPE.get()),
                Component.translatable("advancements.verdant.craft_rope.title"),
                Component.translatable("advancements.verdant.craft_rope.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(overgrowth);
        builder.addCriterion(
                "craft_rope", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "rope_from_strangler_tendril")
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("craft_rope")));
        AdvancementHolder craft_rope = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "craft_rope")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.ROPE_COIL.get()),
                Component.translatable("advancements.verdant.craft_rope_coil.title"),
                Component.translatable("advancements.verdant.craft_rope_coil.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(craft_rope);
        builder.addCriterion(
                "craft_rope_coil",
                RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "rope_coil_from_rope")
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("craft_rope_coil")));
        AdvancementHolder craft_rope_coil = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "craft_rope_coil")
        );


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.SACK.get()),
                Component.translatable("advancements.verdant.craft_sack.title"),
                Component.translatable("advancements.verdant.craft_sack.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(craft_rope);
        builder.addCriterion(
                "craft_sack", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "sack_from_vine_rope")
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("craft_sack")));
        AdvancementHolder craft_sack = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "craft_sack")
        );


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.SACK.get()),
                Component.translatable("advancements.verdant.many_sacks.title"),
                Component.translatable("advancements.verdant.many_sacks.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(craft_sack);
        builder.addCriterion(
                "carry_sacks", InventoryChangeItemCountTrigger.TriggerInstance.hasItems(new ItemPredicate(
                        Optional.of(HolderSet.direct(ItemRegistry.SACK.asHolder())),
                        MinMaxBounds.Ints.atLeast(10),
                        DataComponentMatchers.ANY
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("carry_sacks")));
        AdvancementHolder many_sacks = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "many_sacks")
        );


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.SACK.get()),
                Component.translatable("advancements.verdant.too_many_sacks.title"),
                Component.translatable("advancements.verdant.too_many_sacks.description"),
                null,
                AdvancementType.CHALLENGE,
                true,
                true,
                true
        );
        builder.rewards(AdvancementRewards.Builder.experience(50));
        builder.parent(many_sacks);
        builder.addCriterion(
                "carry_sacks", InventoryChangeItemCountTrigger.TriggerInstance.hasItems(new ItemPredicate(
                        Optional.of(HolderSet.direct(ItemRegistry.SACK.asHolder())),
                        MinMaxBounds.Ints.atLeast(28),
                        DataComponentMatchers.ANY
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("carry_sacks")));
        AdvancementHolder too_many_sacks = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "too_many_sacks")
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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "stand_on_verdant_ground")
        );


        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(WoodSets.STRANGLER.getLog().get()),
                Component.translatable("advancements.verdant.inside_tree.title"),
                Component.translatable("advancements.verdant.inside_tree.description"),
                null,
                AdvancementType.CHALLENGE,
                true,
                true,
                true
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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "inside_tree")
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
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "stinking_blossom")
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
                "thorn_bush",
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), BlockRegistry.THORN_BUSH.get()))
                                .build()), new BlockPos(0, 0, 0)
                )))
        );
        builder.addCriterion(
                "thorny_leaves",
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(
                                registries.lookupOrThrow(Registries.BLOCK),
                                BlockRegistry.THORNY_STRANGLER_LEAVES.get()
                        )).build()), new BlockPos(0, -1, 0)
                )))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("thorn_bush", "thorny_leaves")));
        AdvancementHolder thorn_bush = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "thorn_bush")
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
        builder.parent(thorn_bush);
        builder.addCriterion(
                "trap_plant",
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), BlockRegistry.SNAPLEAF.get()))
                                .build()), new BlockPos(0, 0, 0)
                )))
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("trap_plant")));
        AdvancementHolder trap_plant = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "trap_plant")
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
                "poison_ivy",
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), BlockRegistry.POISON_IVY.get()))
                                .build()), new BlockPos(0, 0, 0)
                )))
        );
        builder.addCriterion(
                "poison_ivy_plant",
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
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
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
                        Optional.of(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block()
                                        .of(registries.lookupOrThrow(Registries.BLOCK), BlockRegistry.POISON_IVY.get()))
                                .build()), new BlockPos(0, 1, 0)
                )))
        );
        builder.addCriterion(
                "poison_ivy_plant_above",
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
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
                VerdantPlantAttackTrigger.VerdantPlantAttackTriggerInstance.instance(ContextAwarePredicate.create(new LocationCheck(
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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "poison_ivy")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.TOXIC_ASH.get()),
                Component.translatable("advancements.verdant.toxic_ash.title"),
                Component.translatable("advancements.verdant.toxic_ash.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(poison_ivy);
        builder.addCriterion(
                "toxic_ash",
                InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TOXIC_ASH.get())
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("toxic_ash")));
        AdvancementHolder toxic_ash = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "toxic_ash")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.TOXIC_ASH_BLOCK.get()),
                Component.translatable("advancements.verdant.toxic_fishing.title"),
                Component.translatable("advancements.verdant.toxic_fishing.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(toxic_ash);
        builder.addCriterion(
                "toxic_fishing", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
                        Optional.of(DamagePredicate.Builder.damageInstance()
                                .type(DamageSourcePredicate.Builder.damageType()
                                        .tag(TagPredicate.is(VerdantTags.DamageSources.TOXIC_ASH))
                                        .build()).build()),
                        Optional.of(EntityPredicate.Builder.entity()
                                .of(registries.lookupOrThrow(Registries.ENTITY_TYPE), EntityTypeTags.AQUATIC)
                                .build())
                )
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("toxic_fishing")));
        AdvancementHolder toxic_fishing = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "toxic_fishing")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.TOXIC_SOLUTION_BUCKET.get()),
                Component.translatable("advancements.verdant.toxic_solution.title"),
                Component.translatable("advancements.verdant.toxic_solution.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                true
        );
        builder.parent(toxic_ash);
        builder.addCriterion(
                "toxic_solution",
                InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TOXIC_SOLUTION_BUCKET.get())
        );
        builder.requirements(AdvancementRequirements.allOf(List.of("toxic_solution")));
        AdvancementHolder toxic_solution = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "toxic_solution")
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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "strong_trees")
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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "deep_roots")
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
        builder.rewards(AdvancementRewards.Builder.experience(85));
        builder.parent(deep_roots);
        builder.addCriterion(
                "rip_them_all_down",
                InventoryChangeItemCountTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
                        .of(registries.lookupOrThrow(Registries.ITEM), WoodSets.HEARTWOOD.getLog().get())
                        .withCount(MinMaxBounds.Ints.atLeast(128)))
        );
        builder.rewards(AdvancementRewards.Builder.experience(50));
        builder.requirements(AdvancementRequirements.allOf(List.of("rip_them_all_down")));
        AdvancementHolder rip_them_all_down = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "rip_them_all_down")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.RANCID_SLIME.get()),
                Component.translatable("advancements.verdant.inedible.title"),
                Component.translatable("advancements.verdant.inedible.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(poison_ivy);
        builder.addCriterion(
                "rotten",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                        registries.lookupOrThrow(Registries.ITEM),
                        ItemRegistry.ROTTEN_COMPOST.get()
                )
        );
        builder.addCriterion(
                "rancid",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                        registries.lookupOrThrow(Registries.ITEM),
                        ItemRegistry.RANCID_SLIME.get()
                )

        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("rotten", "rancid")));
        AdvancementHolder inedible = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "inedible")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(ItemRegistry.ALOE_LEAF.get()),
                Component.translatable("advancements.verdant.aloe.title"),
                Component.translatable("advancements.verdant.aloe.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(poison_ivy);
        builder.addCriterion(
                "young",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                        registries.lookupOrThrow(Registries.ITEM),
                        ItemRegistry.YOUNG_ALOE_LEAF.get()
                )
        );
        builder.addCriterion(
                "normal",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                        registries.lookupOrThrow(Registries.ITEM),
                        ItemRegistry.ALOE_LEAF.get()
                )

        );
        builder.addCriterion(
                "old",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                        registries.lookupOrThrow(Registries.ITEM),
                        ItemRegistry.OLD_ALOE_LEAF.get()
                )

        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("young", "normal", "old")));
        AdvancementHolder aloe = builder.save(writer, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "aloe"));

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.WOODEN_SPIKES.get()),
                Component.translatable("advancements.verdant.wooden_spikes.title"),
                Component.translatable("advancements.verdant.wooden_spikes.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(thorn_bush);
        builder.addCriterion(
                "craft_spikes_rope",
                RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "wooden_spikes_from_thorn_stick_rope")
                ))
        );
        builder.addCriterion(
                "craft_spikes_string",
                RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "wooden_spikes_from_thorn_stick_string")
                ))
        );

        builder.requirements(AdvancementRequirements.anyOf(List.of("craft_spikes_rope", "craft_spikes_string")));
        AdvancementHolder wooden_spikes = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "wooden_spikes")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.IRON_SPIKES.get()),
                Component.translatable("advancements.verdant.iron_spikes.title"),
                Component.translatable("advancements.verdant.iron_spikes.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(wooden_spikes);
        builder.addCriterion(
                "craft_spikes", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(
                                Constants.MOD_ID,
                                "iron_spikes_from_iron_nugget_wooden_spikes_iron_bars"
                        )
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("craft_spikes")));
        AdvancementHolder iron_spikes = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "iron_spikes")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.WOODEN_TRAP.get()),
                Component.translatable("advancements.verdant.wooden_trap.title"),
                Component.translatable("advancements.verdant.wooden_trap.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(wooden_spikes);
        builder.addCriterion(
                "craft_trap", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE, Identifier.fromNamespaceAndPath(
                                Constants.MOD_ID,
                                "wooden_trap_from_wooden_spikes_copper_ingot_stick_tag_minecraft_wooden_pressure_plates"
                        )
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("craft_trap")));
        AdvancementHolder wooden_trap = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "wooden_trap")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.IRON_TRAP.get()),
                Component.translatable("advancements.verdant.iron_trap.title"),
                Component.translatable("advancements.verdant.iron_trap.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(wooden_trap);
        builder.addCriterion(
                "craft_trap", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(
                                Constants.MOD_ID,
                                "iron_trap_from_iron_spikes_iron_ingot_stick_stone_pressure_plate"
                        )
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("craft_trap")));
        AdvancementHolder iron_trap = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "iron_trap")
        );

        builder = Advancement.Builder.advancement();
        builder.display(
                new ItemStack(BlockRegistry.GOLDEN_TRAP.get()),
                Component.translatable("advancements.verdant.golden_trap.title"),
                Component.translatable("advancements.verdant.golden_trap.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );
        builder.parent(wooden_trap);
        builder.addCriterion(
                "craft_trap", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(
                        Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(
                                Constants.MOD_ID,
                                "golden_trap_from_golden_spikes_gold_ingot_stick_gold_pressure_plate"
                        )
                ))
        );
        builder.requirements(AdvancementRequirements.anyOf(List.of("craft_trap")));
        AdvancementHolder golden_trap = builder.save(
                writer,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "golden_trap")
        );
    }


}
