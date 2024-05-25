package com.thomas.verdant.event;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.ModEntityType;
import com.thomas.verdant.entity.custom.VerdantZombieEntity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Verdant.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntityType.VERDANT_ZOMBIE.get(), VerdantZombieEntity.createAttributes().build());


	}
}