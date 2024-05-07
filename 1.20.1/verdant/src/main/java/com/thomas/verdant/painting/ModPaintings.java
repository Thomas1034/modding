package com.thomas.verdant.painting;

import com.thomas.verdant.Verdant;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModPaintings {

	public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister
			.create(ForgeRegistries.PAINTING_VARIANTS, Verdant.MOD_ID);

	public static void register(IEventBus eventBus) {
		PAINTING_VARIANTS.register(eventBus);
	}
}