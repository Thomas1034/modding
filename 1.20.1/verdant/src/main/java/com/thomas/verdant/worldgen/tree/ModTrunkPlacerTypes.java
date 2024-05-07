package com.thomas.verdant.worldgen.tree;

import com.thomas.verdant.Verdant;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ModTrunkPlacerTypes {
	public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER = DeferredRegister
			.create(Registries.TRUNK_PLACER_TYPE, Verdant.MOD_ID);

	public static void register(IEventBus eventBus) {
		TRUNK_PLACER.register(eventBus);
	}
}
