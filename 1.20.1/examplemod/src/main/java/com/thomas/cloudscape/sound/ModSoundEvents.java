package com.thomas.cloudscape.sound;

import com.thomas.cloudscape.ZirconMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
			.create(ForgeRegistries.SOUND_EVENTS, ZirconMod.MOD_ID);

	/*
	 * wind.wav by gorkylmz1 -- https://freesound.org/s/518151/ -- License: Creative
	 * Commons 0
	 */
	public static final RegistryObject<SoundEvent> GUST_AMBIENT = registerSoundEvents("gust_ambient");
	/*
	 * whoosh_short_mid.wav by DJT4NN3R -- https://freesound.org/s/449996/ --
	 * License: Creative Commons 0
	 */
	public static final RegistryObject<SoundEvent> GUST_DEATH = registerSoundEvents("gust_death");
	/* Default Minecraft explosion. */
	public static final RegistryObject<SoundEvent> GUST_HURT = registerSoundEvents("gust_hurt");
	// public static final RegistryObject<SoundEvent> SOUND_BLOCK_FALL =
	// registerSoundEvents("sound_block_fall");
	// public static final RegistryObject<SoundEvent> SOUND_BLOCK_PLACE =
	// registerSoundEvents("sound_block_place");
	// public static final RegistryObject<SoundEvent> SOUND_BLOCK_HIT =
	// registerSoundEvents("sound_block_hit");
	// public static final ForgeSoundType SOUND_BLOCK_SOUNDS = new
	// ForgeSoundType(1f, 1f,
	// ModSounds.SOUND_BLOCK_BREAK, ModSounds.SOUND_BLOCK_STEP,
	// ModSounds.SOUND_BLOCK_PLACE,
	// ModSounds.SOUND_BLOCK_HIT, ModSounds.SOUND_BLOCK_FALL);

	private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
		return SOUND_EVENTS.register(name,
				() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZirconMod.MOD_ID, name)));
	}

	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}
}
