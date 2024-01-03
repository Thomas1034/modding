package com.thomas.zirconmod.worldgen.tree;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.worldgen.tree.custom.PalmTrunkPlacer;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, ZirconMod.MOD_ID);

    public static final RegistryObject<TrunkPlacerType<PalmTrunkPlacer>> PALM_TRUNK_PLACER =
            TRUNK_PLACER.register("palm_trunk_placer", () -> new TrunkPlacerType<>(PalmTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACER.register(eventBus);
    }
}
