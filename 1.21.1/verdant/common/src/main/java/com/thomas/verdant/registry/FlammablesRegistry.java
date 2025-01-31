package com.thomas.verdant.registry;

import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.function.Supplier;

public class FlammablesRegistry {

    private final TriConsumer<Block, Integer, Integer> registrar;

    private FlammablesRegistry(TriConsumer<Block, Integer, Integer> registrar) {
        this.registrar = registrar;
    }

    public static void init(TriConsumer<Block, Integer, Integer> registrar) {
        // FireBlock
        FlammablesRegistry flammables = new FlammablesRegistry(registrar);

        flammables.registerFlammability(BlockRegistry.WILTED_STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.THORNY_STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.POISON_STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.STRANGLER_VINE, 60, 60);
        flammables.registerFlammability(BlockRegistry.LEAFY_STRANGLER_VINE, 60, 60);
        flammables.registerFlammability(BlockRegistry.STRANGLER_TENDRIL, 60, 60);
        flammables.registerFlammability(BlockRegistry.ROPE, 60, 60);
        flammables.registerFlammability(BlockRegistry.ROTTEN_WOOD, 60, 60);
        flammables.registerFlammability(BlockRegistry.STINKING_BLOSSOM, 60, 100);
        flammables.registerFlammability(BlockRegistry.BLEEDING_HEART, 60, 100);
        flammables.registerFlammability(BlockRegistry.TIGER_LILY, 60, 100);
        flammables.registerFlammability(BlockRegistry.THORN_BUSH, 60, 100);
        flammables.registerFlammability(BlockRegistry.BUSH, 60, 100);
        flammables.registerFlammability(BlockRegistry.WILD_COFFEE, 60, 100);
        flammables.registerFlammability(BlockRegistry.COFFEE_CROP, 60, 100);
        flammables.registerFlammability(BlockRegistry.PAPER_FRAME, 60, 100);
    }

    public void registerFlammability(Block block, int flammability, int spreadSpeed) {
        // Constants.LOG.warn("Setting block {} to be flammable with {} and {}", block, flammability, spreadSpeed);
        this.registrar.accept(block, flammability, spreadSpeed);
    }

    public void registerFlammability(Supplier<Block> block, int flammability, int spreadSpeed) {
        this.registerFlammability(block.get(), flammability, spreadSpeed);
    }

}
