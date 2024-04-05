package com.thomas.zirconmod.event;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.ModEntityType;
import com.thomas.zirconmod.entity.custom.GustEntity;
import com.thomas.zirconmod.entity.custom.MoleEntity;
import com.thomas.zirconmod.entity.custom.NimbulaEntity;
import com.thomas.zirconmod.entity.custom.TempestEntity;
import com.thomas.zirconmod.entity.custom.WispEntity;
import com.thomas.zirconmod.entity.custom.WoodGolemEntity;
import com.thomas.zirconmod.entity.custom.WraithEntity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZirconMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntityType.MOLE_ENTITY.get(), MoleEntity.createAttributes().build());
		event.put(ModEntityType.WOOD_GOLEM_ENTITY.get(), WoodGolemEntity.createAttributes().build());
		event.put(ModEntityType.NIMBULA_ENTITY.get(), NimbulaEntity.createAttributes().build());
		event.put(ModEntityType.WISP_ENTITY.get(), WispEntity.createAttributes().build());
		event.put(ModEntityType.GUST_ENTITY.get(), GustEntity.createAttributes().build());
		event.put(ModEntityType.TEMPEST_ENTITY.get(), TempestEntity.createAttributes().build());
		event.put(ModEntityType.WRAITH_ENTITY.get(), WraithEntity.createAttributes().build());

	}
}