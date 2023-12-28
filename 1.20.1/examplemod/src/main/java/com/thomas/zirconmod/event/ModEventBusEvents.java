package com.thomas.zirconmod.event;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.ModEntities;
import com.thomas.zirconmod.entity.custom.MoleEntity;
import com.thomas.zirconmod.entity.custom.NimbulaEntity;
import com.thomas.zirconmod.entity.custom.WispEntity;
import com.thomas.zirconmod.entity.custom.WoodGolemEntity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZirconMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
	
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MOLE_ENTITY.get(), MoleEntity.createAttributes().build());
        event.put(ModEntities.WOOD_GOLEM_ENTITY.get(), WoodGolemEntity.createAttributes().build());
        event.put(ModEntities.NIMBULA_ENTITY.get(), NimbulaEntity.createAttributes().build());
        event.put(ModEntities.WISP_ENTITY.get(), WispEntity.createAttributes().build());

    }
}