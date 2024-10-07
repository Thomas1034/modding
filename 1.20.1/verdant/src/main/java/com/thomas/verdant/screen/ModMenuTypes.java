package com.thomas.verdant.screen;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.screen.menu.FishTrapMenu;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES,
			Verdant.MOD_ID);

	public static final RegistryObject<MenuType<FishTrapMenu>> FISH_TRAP_MENU = registerMenuType(
			"fish_trap_menu", FishTrapMenu::new);

	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name,
			IContainerFactory<T> factory) {
		return MENUS.register(name, () -> IForgeMenuType.create(factory));
	}

	public static void register(IEventBus eventBus) {
		MENUS.register(eventBus);
	}
}
