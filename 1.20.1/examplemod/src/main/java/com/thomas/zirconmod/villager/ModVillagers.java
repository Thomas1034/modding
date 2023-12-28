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
	
	public static final RegistryObject<PoiType> GEMSMITH_POI = POI_TYPES.register("gemsmith_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(ModBlocks.UNOBTAINIUM_GEM.get().getStateDefinition().getPossibleStates()), 1,
					1));

	public static final RegistryObject<VillagerProfession> FORESTER = VILLAGER_PROFESSIONS.register("forester",
			() -> new VillagerProfession("forester", holder -> holder.get() == CARPENTER_POI.get(),
					holder -> holder.get() == CARPENTER_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.VILLAGER_WORK_FLETCHER));
	
	public static final RegistryObject<VillagerProfession> GEMSMITH = VILLAGER_PROFESSIONS.register("gemsmith",
			() -> new VillagerProfession("gemsmith", holder -> holder.get() == GEMSMITH_POI.get(),
					holder -> holder.get() == GEMSMITH_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.AMETHYST_BLOCK_RESONATE));


	public static void register(IEventBus eventBus) {
		POI_TYPES.register(eventBus);
		VILLAGER_PROFESSIONS.register(eventBus);
	}
}