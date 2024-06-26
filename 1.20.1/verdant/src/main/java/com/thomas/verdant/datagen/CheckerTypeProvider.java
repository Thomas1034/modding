package com.thomas.verdant.datagen;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;
import com.thomas.verdant.util.modfeature.checkers.AbstractCheckerType;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

public abstract class CheckerTypeProvider implements DataProvider {

	private final List<AbstractCheckerType> checker_types = new ArrayList<>();

	private final PackOutput output;
	private final String modid;

	public CheckerTypeProvider(PackOutput output, String modid) {
		this.output = output;
		this.modid = modid;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {

		this.addPlacementTypes();

		if (!this.checker_types.isEmpty())
			return save(cache, this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(this.modid)
					.resolve("placement_types"));

		return null;
	}

	private CompletableFuture<?> save(CachedOutput cache, Path target) {
		// TODO: see LanguageProvider's save function

		CompletableFuture<?>[] futures = new CompletableFuture<?>[this.checker_types.size()];
		int i = 0;
		// For each transformer.
		for (AbstractCheckerType checker : this.checker_types) {
			// Create the json object.
			JsonObject json = new JsonObject();
			// Add all the properties.
			// checker.write(json);

			// System.out.println("Saving transformer " + i + ": " + checker.getLocation());
			// futures[i++] = DataProvider.saveStable(cache, json,
			// target.resolve(checker.getLocation().getPath() + ".json"));
		}

		return CompletableFuture.allOf(futures);
	}

	protected abstract void addPlacementTypes();

	@Override
	public String getName() {
		return "Placement Types";
	}

}
