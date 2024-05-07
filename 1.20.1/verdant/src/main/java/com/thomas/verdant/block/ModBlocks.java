package com.thomas.verdant.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.item.ModItems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Verdant.MOD_ID);

	
	// Boilerplate from here on.
	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<T> registerFuelBlock(String name, Supplier<T> block, int burnTime) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerFuelBlockItem(name, toReturn, burnTime);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<T> registerFireproofBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerFireproofBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	private static <T extends Block> RegistryObject<Item> registerFireproofBlockItem(String name,
			RegistryObject<T> block) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().fireResistant()));
	}

	private static <T extends Block> RegistryObject<Item> registerFuelBlockItem(String name, RegistryObject<T> block,
			int burnTime) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {

			@Override
			public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
				return burnTime;
			}

		});
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}