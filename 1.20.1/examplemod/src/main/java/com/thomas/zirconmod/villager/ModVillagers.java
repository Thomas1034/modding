package com.thomas.zirconmod.villager;

import com.google.common.collect.ImmutableSet;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
	public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES,
			ZirconMod.MOD_ID);
	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister
			.create(ForgeRegistries.VILLAGER_PROFESSIONS, ZirconMod.MOD_ID);

	public static final RegistryObject<PoiType> CARPENTER_POI = POI_TYPES.register("forester_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(ModBlocks.CARPENTRY_TABLE.get().getStateDefinition().getPossibleStates()), 1,
					1));
	public static final RegistryObject<PoiType> ARCHITECT_POI = POI_TYPES.register("architect_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(ModBlocks.ARCHITECT_WORKSITE.get().getStateDefinition().getPossibleStates()), 1,
					1));
	public static final RegistryObject<PoiType> BOTANIST_POI = POI_TYPES.register("botanist_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(ModBlocks.BOTANIST_WORKSITE.get().getStateDefinition().getPossibleStates()), 1,
					1));
	public static final RegistryObject<PoiType> CHIEF_POI = POI_TYPES.register("chief_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(ModBlocks.CHIEF_WORKSITE.get().getStateDefinition().getPossibleStates()), 1,
					1));
	public static final RegistryObject<PoiType> GEMSMITH_POI = POI_TYPES.register("gemsmith_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(ModBlocks.GEMSMITH_WORKSITE.get().getStateDefinition().getPossibleStates()), 1,
					1));
	public static final RegistryObject<PoiType> SCHOLAR_POI = POI_TYPES.register("scholar_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(ModBlocks.SCHOLAR_WORKSITE.get().getStateDefinition().getPossibleStates()), 1,
					1));
	public static final RegistryObject<PoiType> TINKERER_POI = POI_TYPES.register("tinkerer_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(ModBlocks.TINKERER_WORKSITE.get().getStateDefinition().getPossibleStates()), 1,
					1));
	
	public static final RegistryObject<VillagerProfession> FORESTER = VILLAGER_PROFESSIONS.register("forester",
			() -> new VillagerProfession("forester", holder -> holder.get() == CARPENTER_POI.get(),
					holder -> holder.get() == CARPENTER_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.VILLAGER_WORK_FLETCHER));
	public static final RegistryObject<VillagerProfession> ARCHITECT = VILLAGER_PROFESSIONS.register("architect",
			() -> new VillagerProfession("architect", holder -> holder.get() == ARCHITECT_POI.get(),
					holder -> holder.get() == ARCHITECT_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.NETHER_BRICKS_BREAK));
	public static final RegistryObject<VillagerProfession> BOTANIST = VILLAGER_PROFESSIONS.register("botanist",
			() -> new VillagerProfession("botanist", holder -> holder.get() == BOTANIST_POI.get(),
					holder -> holder.get() == BOTANIST_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.AZALEA_LEAVES_BREAK));
	public static final RegistryObject<VillagerProfession> CHIEF = VILLAGER_PROFESSIONS.register("chief",
			() -> new VillagerProfession("chief", holder -> holder.get() == CHIEF_POI.get(),
					holder -> holder.get() == CHIEF_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.VILLAGER_AMBIENT));
	public static final RegistryObject<VillagerProfession> GEMSMITH = VILLAGER_PROFESSIONS.register("gemsmith",
			() -> new VillagerProfession("gemsmith", holder -> holder.get() == GEMSMITH_POI.get(),
					holder -> holder.get() == GEMSMITH_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.AMETHYST_BLOCK_RESONATE));
	public static final RegistryObject<VillagerProfession> SCHOLAR = VILLAGER_PROFESSIONS.register("scholar",
			() -> new VillagerProfession("scholar", holder -> holder.get() == SCHOLAR_POI.get(),
					holder -> holder.get() == SCHOLAR_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.BOOK_PAGE_TURN));
	public static final RegistryObject<VillagerProfession> TINKERER = VILLAGER_PROFESSIONS.register("tinkerer",
			() -> new VillagerProfession("tinkerer", holder -> holder.get() == TINKERER_POI.get(),
					holder -> holder.get() == TINKERER_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.ANVIL_USE));

	public static void register(IEventBus eventBus) {
		POI_TYPES.register(eventBus);
		VILLAGER_PROFESSIONS.register(eventBus);
	}
}