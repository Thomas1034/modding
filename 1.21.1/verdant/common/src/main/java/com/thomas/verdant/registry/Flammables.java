package com.thomas.verdant.registry;

import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.function.Supplier;

public class Flammables {

    private final TriConsumer<Block, Integer, Integer> registrar;

    private Flammables(TriConsumer<Block, Integer, Integer> registrar) {
        this.registrar = registrar;
    }

    public static void init(TriConsumer<Block, Integer, Integer> registrar) {
        // FireBlock
        Flammables flammables = new Flammables(registrar);


        flammables.registerFlammability(BlockRegistry.STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.STRANGLER_VINE, 60, 60);
        flammables.registerFlammability(BlockRegistry.LEAFY_STRANGLER_VINE, 60, 60);
    }

    public void registerFlammability(Block block, int flammability, int spreadSpeed) {
        // Constants.LOG.warn("Setting block {} to be flammable with {} and {}", block, flammability, spreadSpeed);
        this.registrar.accept(block, flammability, spreadSpeed);
    }

    public void registerFlammability(Supplier<Block> block, int flammability, int spreadSpeed) {
        this.registerFlammability(block.get(), flammability, spreadSpeed);
    }

}
