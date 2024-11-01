package com.thomas.verdant.event;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.ModEntityTypes;
import com.thomas.verdant.entity.custom.HallucinatedCreeperEntity;
import com.thomas.verdant.entity.custom.OvergrownSkeletonEntity;
import com.thomas.verdant.entity.custom.OvergrownZombieEntity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Verdant.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntityTypes.OVERGROWN_ZOMBIE.get(), OvergrownZombieEntity.createAttributes().build());
		event.put(ModEntityTypes.OVERGROWN_SKELETON.get(), OvergrownSkeletonEntity.createAttributes().build());
		event.put(ModEntityTypes.HALLUCINATED_CREEPER.get(), HallucinatedCreeperEntity.createAttributes().build());

	}

}