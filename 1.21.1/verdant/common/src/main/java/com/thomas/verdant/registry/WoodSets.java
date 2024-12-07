package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.HashSet;
import java.util.Set;

public class WoodSets {

    public static Set<WoodSet> WOOD_SETS = new HashSet<>();

    public static final WoodSet STRANGLER = register(new WoodSet(Constants.MOD_ID, "strangler", () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava(), 1, true));

    public static final WoodSet HEARTWOOD = register(new WoodSet(Constants.MOD_ID, "heartwood", () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(4.0F, 6.0F).sound(SoundType.WOOD).ignitedByLava(), 2, true));

    public static WoodSet register(WoodSet woodSet) {
        WOOD_SETS.add(woodSet);
        return woodSet;
    }

    public static void init() {

    }
}
