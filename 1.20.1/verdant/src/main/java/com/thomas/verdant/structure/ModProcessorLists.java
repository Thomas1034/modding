package com.thomas.verdant.structure;

import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class ModProcessorLists {

	// public static final ResourceKey<StructureProcessorList> VERDANT_PYRAMID =
	// createKey("verdant_pyramid");

	private static ResourceKey<StructureProcessorList> createKey(String name) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation(name));
	}

	private static void register(BootstapContext<StructureProcessorList> bootstapContext,
			ResourceKey<StructureProcessorList> resourceKey, List<StructureProcessor> processors) {
		bootstapContext.register(resourceKey, new StructureProcessorList(processors));
	}

	public static void bootstrap(BootstapContext<StructureProcessorList> bootstapContext) {
	}
}
