package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class DamageSourceRegistry {

    public static ResourceKey<DamageType> BRIAR = create("briar");
    public static ResourceKey<DamageType> PHOTOSENSITIVE = create("photosensitive");
    public static ResourceKey<DamageType> TOXIC_ASH = create("toxic_ash");

    private static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }

    public static Holder<DamageType> get(RegistryAccess access, ResourceKey<DamageType> key) {
        return access.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key);
    }
}
