package com.thomas.zirconmod.entity;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.custom.ModBoatEntity;
import com.thomas.zirconmod.entity.custom.ModChestBoatEntity;
import com.thomas.zirconmod.entity.custom.MoleEntity;
import com.thomas.zirconmod.entity.custom.NimbulaEntity;
import com.thomas.zirconmod.entity.custom.WispEntity;
import com.thomas.zirconmod.entity.custom.WoodGolemEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, ZirconMod.MOD_ID);

	public static final RegistryObject<EntityType<MoleEntity>> MOLE_ENTITY = ENTITY_TYPES.register("mole",
			() -> EntityType.Builder.of(MoleEntity::new, MobCategory.MONSTER).sized(0.9f, 0.9f).fireImmune()
					.canSpawnFarFromPlayer().build(new ResourceLocation(ZirconMod.MOD_ID, "mole").toString()));

	public static final RegistryObject<EntityType<WoodGolemEntity>> WOOD_GOLEM_ENTITY = ENTITY_TYPES
			.register("wood_golem", () -> EntityType.Builder.of(WoodGolemEntity::new, MobCategory.MISC)
					.sized(1.0f, 2.0f).build(new ResourceLocation(ZirconMod.MOD_ID, "wood_golem").toString()));

	public static final RegistryObject<EntityType<NimbulaEntity>> NIMBULA_ENTITY = ENTITY_TYPES.register("nimbula",
			() -> EntityType.Builder.of(NimbulaEntity::new, MobCategory.CREATURE).sized(1.0f, 1.5f).build("nimbula"));

	public static final RegistryObject<EntityType<WispEntity>> WISP_ENTITY = ENTITY_TYPES.register("wisp",
			() -> EntityType.Builder.of(WispEntity::new, MobCategory.CREATURE).sized(0.9f, 0.9f).build("wisp"));

	public static final RegistryObject<EntityType<ModBoatEntity>> MOD_BOAT = ENTITY_TYPES.register("mod_boat",
			() -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f)
					.build("mod_boat"));
	public static final RegistryObject<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT = ENTITY_TYPES.register(
			"mod_chest_boat", () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
					.sized(1.375f, 0.5625f).build("mod_chest_boat"));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}