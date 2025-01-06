package com.thomas.verdant.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CompostablesRegistry {

    private final BiConsumer<ItemLike, Float> registrar;

    private CompostablesRegistry(BiConsumer<ItemLike, Float> registrar) {
        this.registrar = registrar;
    }

    public static void init(BiConsumer<ItemLike, Float> registrar) {
        CompostablesRegistry compostables = new CompostablesRegistry(registrar);

        compostables.registerCompostability(BlockRegistry.WILD_COFFEE.get(), 0.65f);
        compostables.registerCompostability(BlockRegistry.BLEEDING_HEART.get(), 0.65f);
        compostables.registerCompostability(BlockRegistry.TIGER_LILY.get(), 0.65f);
        compostables.registerCompostability(BlockRegistry.STINKING_BLOSSOM.get(), 0.65f);
        compostables.registerCompostability(BlockRegistry.BUSH.get(), 0.65f);
        compostables.registerCompostability(BlockRegistry.THORN_BUSH.get(), 0.65f);
        compostables.registerCompostability(ItemRegistry.COFFEE_BERRIES, 0.65f);
        compostables.registerCompostability(ItemRegistry.ROASTED_COFFEE, 0.65f);
        compostables.registerCompostability(ItemRegistry.ROTTEN_COMPOST, 1.0f);
        compostables.registerCompostability(BlockRegistry.THORNY_STRANGLER_LEAVES.get(), 0.3f);
        compostables.registerCompostability(BlockRegistry.POISON_STRANGLER_LEAVES.get(), 0.3f);
        compostables.registerCompostability(BlockRegistry.STRANGLER_LEAVES.get(), 0.3f);
        compostables.registerCompostability(BlockRegistry.WILTED_STRANGLER_LEAVES.get(), 0.3f);
        compostables.registerCompostability(BlockRegistry.STRANGLER_TENDRIL.get(), 0.3f);
        compostables.registerCompostability(BlockRegistry.ROTTEN_WOOD.get(), 0.9f);
    }

    public void registerCompostability(ItemLike item, float compostability) {
        // Constants.LOG.warn("Setting item-like {} to be compostable with {}", item, compostability);
        this.registrar.accept(item, compostability);
    }

    public void registerCompostability(Supplier<Item> item, float compostability) {
        // Constants.LOG.warn("Setting item {} to be compostable with {}", item.get(), compostability);
        this.registerCompostability(item.get(), compostability);
    }
}
