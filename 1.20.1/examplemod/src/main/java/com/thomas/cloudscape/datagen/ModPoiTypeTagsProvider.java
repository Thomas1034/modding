package com.thomas.cloudscape.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.thomas.cloudscape.Cloudscape;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {
	public ModPoiTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(pOutput, pProvider, Cloudscape.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {
		tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
				.addOptional(new ResourceLocation(Cloudscape.MOD_ID, "forester_poi"))
				.addOptional(new ResourceLocation(Cloudscape.MOD_ID, "architect_poi"))
				.addOptional(new ResourceLocation(Cloudscape.MOD_ID, "botanist_poi"))
				.addOptional(new ResourceLocation(Cloudscape.MOD_ID, "chief_poi"))
				.addOptional(new ResourceLocation(Cloudscape.MOD_ID, "gemsmith_poi"))
				.addOptional(new ResourceLocation(Cloudscape.MOD_ID, "scholar_poi"))
				.addOptional(new ResourceLocation(Cloudscape.MOD_ID, "tinkerer_poi"));
	}
}
