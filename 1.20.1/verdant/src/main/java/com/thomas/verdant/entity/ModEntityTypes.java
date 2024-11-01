package com.thomas.verdant.entity;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.custom.HallucinatedCreeperEntity;
import com.thomas.verdant.entity.custom.OvergrownSkeletonEntity;
import com.thomas.verdant.entity.custom.OvergrownZombieEntity;
import com.thomas.verdant.entity.custom.PoisonIvyArrowEntity;
import com.thomas.verdant.entity.custom.ThrownRopeEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, Verdant.MOD_ID);

	public static final RegistryObject<EntityType<PoisonIvyArrowEntity>> POISON_IVY_ARROW = ENTITY_TYPES.register(
			"poison_ivy_arrow",
			() -> EntityType.Builder.<PoisonIvyArrowEntity>of(PoisonIvyArrowEntity::new, MobCategory.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("poison_ivy_arrow"));
	public static final RegistryObject<EntityType<OvergrownZombieEntity>> OVERGROWN_ZOMBIE = ENTITY_TYPES.register(
			"overgrown_zombie",
			() -> EntityType.Builder.<OvergrownZombieEntity>of(OvergrownZombieEntity::new, MobCategory.MONSTER)
					.sized(0.9f, 1.95f).build("overgrown_zombie"));
	public static final RegistryObject<EntityType<OvergrownSkeletonEntity>> OVERGROWN_SKELETON = ENTITY_TYPES
			.register("overgrown_skeleton",
					() -> EntityType.Builder
							.<OvergrownSkeletonEntity>of(OvergrownSkeletonEntity::new, MobCategory.MONSTER)
							.sized(0.9f, 1.95f).build("overgrown_skeleton"));
	public static final RegistryObject<EntityType<ThrownRopeEntity>> THROWN_ROPE = ENTITY_TYPES.register("thrown_rope",
			() -> EntityType.Builder.<ThrownRopeEntity>of(ThrownRopeEntity::new, MobCategory.MISC).sized(0.8f, 0.8f)
					.build("thrown_rope"));
	public static final RegistryObject<EntityType<HallucinatedCreeperEntity>> HALLUCINATED_CREEPER = ENTITY_TYPES
			.register("hallucinated_creeper",
					() -> EntityType.Builder
							.<HallucinatedCreeperEntity>of(HallucinatedCreeperEntity::new, MobCategory.MISC)
							.sized(0.9f, 1.95f).build("hallucinated_creeper"));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}