package com.thomas.cloudscape.entity;

import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.entity.custom.BallLightningEntity;
import com.thomas.cloudscape.entity.custom.GustEntity;
import com.thomas.cloudscape.entity.custom.HailstoneEntity;
import com.thomas.cloudscape.entity.custom.ModBoatEntity;
import com.thomas.cloudscape.entity.custom.ModChestBoatEntity;
import com.thomas.cloudscape.entity.custom.MoleEntity;
import com.thomas.cloudscape.entity.custom.NimbulaEntity;
import com.thomas.cloudscape.entity.custom.TempestEntity;
import com.thomas.cloudscape.entity.custom.WispEntity;
import com.thomas.cloudscape.entity.custom.WoodGolemEntity;
import com.thomas.cloudscape.entity.custom.WraithEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityType {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, Cloudscape.MOD_ID);

	public static final RegistryObject<EntityType<MoleEntity>> MOLE_ENTITY = ENTITY_TYPES.register("mole",
			() -> EntityType.Builder.of(MoleEntity::new, MobCategory.MONSTER).sized(0.9f, 0.9f).fireImmune()
					.canSpawnFarFromPlayer().build(new ResourceLocation(Cloudscape.MOD_ID, "mole").toString()));

	public static final RegistryObject<EntityType<WoodGolemEntity>> WOOD_GOLEM_ENTITY = ENTITY_TYPES
			.register("wood_golem", () -> EntityType.Builder.of(WoodGolemEntity::new, MobCategory.MISC)
					.sized(1.0f, 2.0f).build(new ResourceLocation(Cloudscape.MOD_ID, "wood_golem").toString()));

	public static final RegistryObject<EntityType<NimbulaEntity>> NIMBULA_ENTITY = ENTITY_TYPES.register("nimbula",
			() -> EntityType.Builder.of(NimbulaEntity::new, MobCategory.CREATURE).sized(1.0f, 1.5f).build("nimbula"));

	public static final RegistryObject<EntityType<TempestEntity>> TEMPEST_ENTITY = ENTITY_TYPES.register("tempest",
			() -> EntityType.Builder.of(TempestEntity::new, MobCategory.CREATURE).sized(4.0f, 6.0f).build("tempest"));

	public static final RegistryObject<EntityType<WispEntity>> WISP_ENTITY = ENTITY_TYPES.register("wisp",
			() -> EntityType.Builder.of(WispEntity::new, MobCategory.CREATURE).sized(0.9f, 0.9f).build("wisp"));

	public static final RegistryObject<EntityType<GustEntity>> GUST_ENTITY = ENTITY_TYPES.register("gust",
			() -> EntityType.Builder.of(GustEntity::new, MobCategory.MONSTER).sized(1.5f, 1.5f).build("gust"));;

	public static final RegistryObject<EntityType<WraithEntity>> WRAITH_ENTITY = ENTITY_TYPES.register("wraith",
			() -> EntityType.Builder.of(WraithEntity::new, MobCategory.MONSTER).sized(0.9F, 0.5F).clientTrackingRange(8).build("wraith"));;

	public static final RegistryObject<EntityType<ModBoatEntity>> MOD_BOAT = ENTITY_TYPES.register("mod_boat",
			() -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f)
					.build("mod_boat"));
	public static final RegistryObject<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT = ENTITY_TYPES.register(
			"mod_chest_boat", () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
					.sized(1.375f, 0.5625f).build("mod_chest_boat"));

	public static final RegistryObject<EntityType<HailstoneEntity>> HAILSTONE_ENTITY = ENTITY_TYPES
			.register("hailstone", () -> EntityType.Builder.<HailstoneEntity>of(HailstoneEntity::new, MobCategory.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("hailstone"));

	public static final RegistryObject<EntityType<BallLightningEntity>> BALL_LIGHTNING_ENTITY = ENTITY_TYPES.register(
			"ball_lightning",
			() -> EntityType.Builder.<BallLightningEntity>of(BallLightningEntity::new, MobCategory.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("ball_lightning"));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}