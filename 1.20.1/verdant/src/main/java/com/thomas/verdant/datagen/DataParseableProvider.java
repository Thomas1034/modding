package com.thomas.verdant.datagen;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import com.google.gson.JsonObject;
import com.thomas.verdant.util.data.DataParseable;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public abstract class DataParseableProvider<T extends DataParseable<T>> implements DataProvider {

	private final List<T> parseables = new ArrayList<>();

	private final PackOutput output;
	private final String modid;
	private final String folder;
	private final String name;
	private final Function<ResourceLocation, T> supplier;

	public DataParseableProvider(PackOutput output, String modid, String folder, String name,
			Function<ResourceLocation, T> supplier) {
		this.output = output;
		this.modid = modid;
		this.folder = folder;
		this.name = name;
		this.supplier = supplier;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {

		this.addParseables();

		if (!this.parseables.isEmpty()) {
			return this.save(cache,
					this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(this.modid).resolve(this.folder));
		}

		return CompletableFuture.runAsync(() -> {
		});
	}

	private CompletableFuture<?> save(CachedOutput cache, Path target) {
		CompletableFuture<?>[] futures = new CompletableFuture<?>[this.parseables.size()];
		int i = 0;
		// For each parseable.
		for (T parseable : this.parseables) {
			// Create the json object.
			JsonObject json = new JsonObject();
			// Add all the properties.
			parseable.write(json);

			System.out.println("Saving parseable " + i + ": " + parseable.getLocation());
			futures[i++] = DataProvider.saveStable(cache, json,
					target.resolve(parseable.getLocation().getPath() + ".json"));
		}

		return CompletableFuture.allOf(futures);
	}

	public T add(String name) {
		T parseable = supplier.apply(new ResourceLocation(this.modid, name));
		this.parseables.add(parseable);
		return parseable;
	}

	protected abstract void addParseables();

	@Override
	public String getName() {
		return this.name;
	}
}