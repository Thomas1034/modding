package com.thomas.verdant.entity;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.custom.ModBoatEntity;
import com.thomas.verdant.entity.custom.ModChestBoatEntity;
import com.thomas.verdant.entity.custom.OvergrownSkeletonEntity;
import com.thomas.verdant.entity.custom.OvergrownZombieEntity;
import com.thomas.verdant.entity.custom.PoisonIvyArrowEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityType {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, Verdant.MOD_ID);

	public static final RegistryObject<EntityType<ModBoatEntity>> VERDANT_BOAT = ENTITY_TYPES.register("verdant_boat",
			() -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f)
					.build("verdant_boat"));
	public static final RegistryObject<EntityType<ModChestBoatEntity>> VERDANT_CHEST_BOAT = ENTITY_TYPES.register(
			"verdant_chest_boat",
			() -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
					.sized(1.375f, 0.5625f).build("verdant_chest_boat"));
	public static final RegistryObject<EntityType<PoisonIvyArrowEntity>> POISON_IVY_ARROW = ENTITY_TYPES.register(
			"poison_ivy_arrow",
			() -> EntityType.Builder.<PoisonIvyArrowEntity>of(PoisonIvyArrowEntity::new, MobCategory.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("poison_ivy_arrow"));
	public static final RegistryObject<EntityType<OvergrownZombieEntity>> OVERGROWN_ZOMBIE = ENTITY_TYPES.register(
			"overgrown_zombie",
			() -> EntityType.Builder.<OvergrownZombieEntity>of(OvergrownZombieEntity::new, MobCategory.MISC)
					.sized(0.9f, 1.95f).build("overgrown_zombie"));
	
	public static final RegistryObject<EntityType<OvergrownSkeletonEntity>> OVERGROWN_SKELETON = ENTITY_TYPES.register(
			"overgrown_skeleton",
			() -> EntityType.Builder.<OvergrownSkeletonEntity>of(OvergrownSkeletonEntity::new, MobCategory.MISC)
					.sized(0.9f, 1.95f).build("overgrown_skeleton"));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}