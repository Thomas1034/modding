package com.startraveler.verdant.registry;

import com.startraveler.verdant.woodset.WoodSet;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FuelsRegistry {

    private final BiConsumer<ItemLike, Integer> registrar;

    private FuelsRegistry(BiConsumer<ItemLike, Integer> registrar) {
        this.registrar = registrar;
    }

    public static void init(BiConsumer<ItemLike, Integer> registrar) {
        // FireBlock
        FuelsRegistry fuels = new FuelsRegistry(registrar);

        fuels.registerFuel(BlockRegistry.FISH_TRAP_BLOCK, WoodSet.BurnTimes.PLANKS);
        fuels.registerFuel(BlockRegistry.BUSH, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.THORN_BUSH, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.STRANGLER_LEAVES, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.WILTED_STRANGLER_LEAVES, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.THORNY_STRANGLER_LEAVES, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.STRANGLER_VINE, WoodSet.BurnTimes.SIGN);
        fuels.registerFuel(BlockRegistry.LEAFY_STRANGLER_VINE, WoodSet.BurnTimes.SIGN);
        fuels.registerFuel(BlockRegistry.FRAME_BLOCK, WoodSet.BurnTimes.DOOR);

    }

    public void registerFuel(ItemLike block, int burnTime) {
        // Constants.LOG.warn("Setting itemLike {} to be fuel with {}", block, burnTime);
        this.registrar.accept(block, burnTime);
    }

    public void registerFuel(Supplier<Block> block, int burnTime) {
        this.registerFuel(block.get(), burnTime);
    }

}
