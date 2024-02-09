package com.thomas.quantumcircuit.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.thomas.quantumcircuit.QuantumCircuit;
import com.thomas.quantumcircuit.block.ModBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagGenerator extends BlockTagsProvider {
	public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, QuantumCircuit.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {
		// this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES).add(ModBlocks.SAPPHIRE_ORE.get()).addTag(Tags.Blocks.ORES);

		// Mineables
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ZIRCON_BLOCK.get());
		
	}
}
