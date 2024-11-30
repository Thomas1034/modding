package com.thomas.verdant.registry.properties;

import com.thomas.verdant.Constants;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class WoodSets {

    public static final WoodSet STRANGLER = new WoodSet(Constants.MOD_ID, "strangler", () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());


    public static void init() {

    }
}
