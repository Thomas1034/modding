package com.thomas.cloudscape.painting;

import com.thomas.cloudscape.ZirconMod;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {

	
	public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = 
			DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, ZirconMod.MOD_ID);
	
	public static final RegistryObject<PaintingVariant> VULTURE = PAINTING_VARIANTS.register("vulture",
			() -> new PaintingVariant(16, 16));
	public static final RegistryObject<PaintingVariant> OSPREY = PAINTING_VARIANTS.register("osprey",
			() -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> GULL = PAINTING_VARIANTS.register("gull",
			() -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> MOCKINGBIRD = PAINTING_VARIANTS.register("mockingbird",
			() -> new PaintingVariant(32, 16));
	public static final RegistryObject<PaintingVariant> BUTTERFLY = PAINTING_VARIANTS.register("butterfly",
			() -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> FLOWERS = PAINTING_VARIANTS.register("flowers",
			() -> new PaintingVariant(16, 16));
	public static final RegistryObject<PaintingVariant> YELLOW_FLOWERS = PAINTING_VARIANTS.register("yellow_flowers",
			() -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> PURPLE_FLOWERS = PAINTING_VARIANTS.register("purple_flowers",
			() -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> GUAM = PAINTING_VARIANTS.register("guam",
			() -> new PaintingVariant(64, 48));
	public static final RegistryObject<PaintingVariant> HIBISCUS = PAINTING_VARIANTS.register("hibiscus",
			() -> new PaintingVariant(48, 48));
	public static final RegistryObject<PaintingVariant> EGRET = PAINTING_VARIANTS.register("egret",
			() -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> DEER = PAINTING_VARIANTS.register("deer",
			() -> new PaintingVariant(16, 16));
	public static final RegistryObject<PaintingVariant> BUCK = PAINTING_VARIANTS.register("buck",
			() -> new PaintingVariant(64, 64));
	public static final RegistryObject<PaintingVariant> BOBCAT = PAINTING_VARIANTS.register("bobcat",
			() -> new PaintingVariant(32, 16));
	public static final RegistryObject<PaintingVariant> LIGHTHOUSE = PAINTING_VARIANTS.register("lighthouse",
			() -> new PaintingVariant(32, 16));
	public static final RegistryObject<PaintingVariant> PASTURE = PAINTING_VARIANTS.register("pasture",
			() -> new PaintingVariant(32, 16));
	public static final RegistryObject<PaintingVariant> SPIDER = PAINTING_VARIANTS.register("spider",
			() -> new PaintingVariant(16, 32));
	public static final RegistryObject<PaintingVariant> TOMBSTONE = PAINTING_VARIANTS.register("tombstone",
			() -> new PaintingVariant(16, 32));
	public static final RegistryObject<PaintingVariant> HERON = PAINTING_VARIANTS.register("heron",
			() -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> GATOR = PAINTING_VARIANTS.register("gator",
			() -> new PaintingVariant(64, 64));
	public static final RegistryObject<PaintingVariant> SUNSET = PAINTING_VARIANTS.register("sunset",
			() -> new PaintingVariant(32, 16));
	public static final RegistryObject<PaintingVariant> ORANGE_BUTTERFLY = PAINTING_VARIANTS.register("orange_butterfly",
			() -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> POUNCER = PAINTING_VARIANTS.register("pouncer",
			() -> new PaintingVariant(64, 64));
	public static final RegistryObject<PaintingVariant> BLOOM = PAINTING_VARIANTS.register("bloom",
			() -> new PaintingVariant(64, 48));
	public static final RegistryObject<PaintingVariant> MEADOW = PAINTING_VARIANTS.register("meadow",
			() -> new PaintingVariant(32, 32));
	
	
	
	
	public static void register(IEventBus eventBus)
	{
		PAINTING_VARIANTS.register(eventBus);
	}
}