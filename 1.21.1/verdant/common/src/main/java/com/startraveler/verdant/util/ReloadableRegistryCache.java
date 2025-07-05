package com.startraveler.verdant.util;

import com.startraveler.rootbound.blocktransformer.BlockTransformer;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class ReloadableRegistryCache<T> extends SimplePreparableReloadListener<Integer> {

    private final Map<ResourceLocation, T> cachedValues;
    private final ResourceKey<? extends Registry<T>> key;
    private Registry<T> registry;

    public ReloadableRegistryCache(ResourceKey<? extends Registry<T>> key) {
        this.cachedValues = new HashMap<>();
        this.key = key;
    }

    public T get(RegistryAccess access, ResourceLocation name) {
        if (this.cachedValues.containsKey(name)) {
            return this.cachedValues.get(name);
        }
        if (this.registry == null) {
            this.registry = access.lookupOrThrow(this.key);
        }
        T result = this.registry.getValue(name);
        this.cachedValues.put(name, result);
        return result;
    }

    public void clear() {
        this.cachedValues.clear();
        this.registry = null;
    }

    @Override
    protected Integer prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return 0;
    }

    @Override
    protected void apply(Integer i, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.clear();
    }

    public static class Transformers extends ReloadableRegistryCache<BlockTransformer> {
        public Transformers() {
            super(BlockTransformer.KEY);
        }
    }
}
