package com.thomas.verdant.block.entity;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.entity.custom.FishTrapBlockEntity;
import com.thomas.verdant.block.entity.custom.VerdantConduitBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Verdant.MOD_ID);

	public static final RegistryObject<BlockEntityType<VerdantConduitBlockEntity>> VERDANT_HEART_BLOCK_ENTITY = BLOCK_ENTITIES
			.register("jungle_heart", () -> BlockEntityType.Builder
					.of(VerdantConduitBlockEntity::new, ModBlocks.VERDANT_CONDUIT.get()).build(null));

	public static final RegistryObject<BlockEntityType<FishTrapBlockEntity>> FISH_TRAP_BLOCK_ENTITY = BLOCK_ENTITIES
			.register("fish_trap", () -> BlockEntityType.Builder
					.of(FishTrapBlockEntity::new, ModBlocks.FISH_TRAP_BLOCK.get()).build(null));

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITIES.register(eventBus);
	}
}
