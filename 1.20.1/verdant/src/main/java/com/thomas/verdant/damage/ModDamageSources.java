package com.thomas.verdant.damage;

import com.thomas.verdant.Verdant;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class ModDamageSources {
	
	public static final ResourceKey<DamageType> THORN_BUSH = register("thorn_bush");
    public static final ResourceKey<DamageType> OVERGROWTH = register("overgrowth");
    public static final ResourceKey<DamageType> SUNLIGHT = register("sunlight");
    
    private static ResourceKey<DamageType> register(String name)
    {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Verdant.MOD_ID, name));
    }
	
    public static DamageSource get(Level level, ResourceKey<DamageType> type) {
    	DamageSource source = new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type));
    	return source;
    }
    
}
