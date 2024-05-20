package com.thomas.verdant.util.block_transformers;

import com.thomas.verdant.Verdant;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Verdant.MOD_ID)
public class BlockTransformLoader {
	@SubscribeEvent
	public static void onServerAboutToStart(AddReloadListenerEvent event) {
		//System.out.println("Adding listener here!");
		event.addListener(new BlockTransformerLoaderListener());
	}
}
