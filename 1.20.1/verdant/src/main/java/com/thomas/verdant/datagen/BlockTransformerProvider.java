package com.thomas.verdant.datagen;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;
import com.thomas.verdant.util.blocktransformers.BlockTransformer;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class BlockTransformerProvider implements DataProvider {

	private final List<BlockTransformer> transformers = new ArrayList<>();

	private final PackOutput output;
	private final String modid;

	public BlockTransformerProvider(PackOutput output, String modid) {
		this.output = output;
		this.modid = modid;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {

		this.addTransformers();

		if (!this.transformers.isEmpty())
			return save(cache, this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(this.modid)
					.resolve("block_transformers"));

		return null;
	}

	private CompletableFuture<?> save(CachedOutput cache, Path target) {
		// TODO: see LanguageProvider's save function

		CompletableFuture<?>[] futures = new CompletableFuture<?>[this.transformers.size()];
		int i = 0;
		// For each transformer.
		for (BlockTransformer transformer : this.transformers) {
			// Create the json object.
			JsonObject json = new JsonObject();
			// Add all the properties.
			transformer.write(json);

			System.out.println("Saving transformer " + i + ": " + transformer.getLocation());
			futures[i++] = DataProvider.saveStable(cache, json,
					target.resolve(transformer.getLocation().getPath() + ".json"));
		}

		return CompletableFuture.allOf(futures);
	}

	public BlockTransformer add(String name) {
		BlockTransformer transformer = new BlockTransformer(new ResourceLocation(this.modid, name));
		this.transformers.add(transformer);
		return transformer;
	}

	private final ResourceLocation name(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	protected abstract void addTransformers();

	@Override
	public String getName() {
		return "Block Transformers";
	}
}