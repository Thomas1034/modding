package com.thomas.turbulent.painting;

import com.thomas.turbulent.Turbulent;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModPaintings {

	public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister
			.create(ForgeRegistries.PAINTING_VARIANTS, Turbulent.MOD_ID);

	public static void register(IEventBus eventBus) {
		PAINTING_VARIANTS.register(eventBus);
	}
}