package com.thomas.verdant.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.thomas.verdant.Verdant;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {
	public ModPoiTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(pOutput, pProvider, Verdant.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {

	}
}
