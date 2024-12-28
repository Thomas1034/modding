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
        // FireBlock
        CompostablesRegistry compostables = new CompostablesRegistry(registrar);

        compostables.registerCompostability(ItemRegistry.COFFEE_BERRIES, 0.2f);
        compostables.registerCompostability(ItemRegistry.ROASTED_COFFEE, 0.5f);
        compostables.registerCompostability(ItemRegistry.ROTTEN_COMPOST, 1.0f);
        compostables.registerCompostability(BlockRegistry.THORNY_STRANGLER_LEAVES.get().asItem(), 0.3f);
        compostables.registerCompostability(BlockRegistry.POISON_STRANGLER_LEAVES.get().asItem(), 0.3f);
        compostables.registerCompostability(BlockRegistry.STRANGLER_LEAVES.get().asItem(), 0.3f);
        compostables.registerCompostability(BlockRegistry.ROTTEN_WOOD.get().asItem(), 0.9f);
    }

    public void registerCompostability(ItemLike item, float compostability) {
        // Constants.LOG.warn("Setting block {} to be flammable with {} and {}", block, flammability, spreadSpeed);
        this.registrar.accept(item, compostability);
    }

    public void registerCompostability(Supplier<Item> item, float compostability) {
        this.registerCompostability(item.get(), compostability);
    }
}
