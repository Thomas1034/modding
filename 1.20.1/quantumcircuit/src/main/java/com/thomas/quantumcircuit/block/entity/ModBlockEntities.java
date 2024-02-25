package com.thomas.quantumcircuit.block.entity;

import com.thomas.quantumcircuit.QuantumCircuit;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITY_TYPES, QuantumCircuit.MOD_ID);

//    public static final RegistryObject<BlockEntityType<ResonatorBlockEntity>> RESONATOR =
//            BLOCK_ENTITIES.register("resonator", () ->
//                    BlockEntityType.Builder.of(ResonatorBlockEntity::new,
//                            ModBlocks.RESONATOR_BLOCK.get()).build(null));

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITIES.register(eventBus);
	}
}
