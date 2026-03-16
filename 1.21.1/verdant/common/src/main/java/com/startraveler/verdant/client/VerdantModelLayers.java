package com.startraveler.verdant.client;

import com.google.common.collect.Sets;
import com.startraveler.verdant.Constants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class VerdantModelLayers {
    public static final ModelLayerLocation SKULL_SPIDER = new ModelLayerLocation(Constants.id("skull_spider"), "main");
    private static final Set<ModelLayerLocation> VERDANT_MODELS = Sets.newHashSet();

    public static final ArmorModelSet<ModelLayerLocation> ARMOR_SPIKES = registerArmorSet("spikes");


    private static ModelLayerLocation register(String path, String model) {
        ModelLayerLocation modellayerlocation = createLocation(path, model);
        if (!VERDANT_MODELS.add(modellayerlocation)) {
            throw new IllegalStateException("Duplicate registration for " + String.valueOf(modellayerlocation));
        } else {
            return modellayerlocation;
        }
    }

    private static ModelLayerLocation createLocation(String path, String model) {
        return new ModelLayerLocation(Constants.id(path), model);
    }

    @SuppressWarnings("SameParameterValue")
    private static ArmorModelSet<ModelLayerLocation> registerArmorSet(String path) {
        return new ArmorModelSet<>(
                register(path, "helmet"),
                register(path, "chestplate"),
                register(path, "leggings"),
                register(path, "boots")
        );
    }

    public static <T> void putArmorLayersFrom(ArmorModelSet<T> t, ArmorModelSet<LayerDefinition> other, BiConsumer<T, Supplier<LayerDefinition>> builder) {
        builder.accept(t.head(), other::head);
        builder.accept(t.chest(), other::chest);
        builder.accept(t.legs(), other::legs);
        builder.accept(t.feet(), other::feet);
    }
}
