package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.util.baitdata.BaitData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class VerdantBaitDataProvider extends DatapackBuiltinEntriesProvider {

    public VerdantBaitDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, getRegistrySetBuilder(), Set.of(Constants.MOD_ID));
    }

    private static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder().add(BaitData.KEY, VerdantBaitDataProvider::register);
    }

    private static void register(BootstrapContext<BaitData> bootstrap) {

    }

    private static void add(BootstrapContext<BaitData> bootstrap, Item item, float catchChance, float consumeChance) {

    }

    private static ResourceKey<BaitData> key(ResourceLocation location) {
        return ResourceKey.create(BaitData.KEY, location);
    }

    private static ResourceKey<BaitData> key(TagKey<Item> tag) {
        return key(tag.location());
    }

    private static ResourceKey<BaitData> key(Item item) {
        return key(BuiltInRegistries.ITEM.getKey(item));
    }

    @Override
    @NotNull
    public String getName() {
        return "Bait Data";
    }
}
