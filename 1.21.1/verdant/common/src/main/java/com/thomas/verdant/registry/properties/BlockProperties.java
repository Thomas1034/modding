package com.thomas.verdant.registry.properties;


import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockProperties {
    public static final BlockBehaviour.Properties VERDANT_ROOTS = BlockBehaviour.Properties.of()
            .mapColor(MapColor.GRASS).randomTicks().sound(SoundType.GRASS).strength(0.75F);
    public static final BlockBehaviour.Properties VERDANT_GRASS = BlockBehaviour.Properties.of()
            .mapColor(MapColor.GRASS).randomTicks().sound(SoundType.GRASS).strength(0.8F);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_PLANKS = verdantHeartwoodBase();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_SLAB = verdantHeartwoodBase();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_SIGN = verdantHeartwoodBase().forceSolidOn()
            .noCollission().strength(2.0F);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_LOGS = verdantHeartwoodBase();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_PRESSURE_PLATE = verdantHeartwoodBase()
            .forceSolidOn().noCollission().strength(2.0F).pushReaction(PushReaction.DESTROY);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_STAIRS = verdantHeartwoodBase();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_FENCE = verdantHeartwoodBase().forceSolidOn();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_FENCE_GATE = verdantHeartwoodBase().forceSolidOn();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_TRAPDOOR = verdantHeartwoodBase().noOcclusion();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_DOOR = verdantHeartwoodBase().strength(4.5F)
            .noOcclusion().pushReaction(PushReaction.DESTROY);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_WALL_HANGING_SIGN = verdantHeartwoodBase()
            .forceSolidOn().noCollission().strength(1.0F);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_WALL_SIGN = verdantHeartwoodBase().forceSolidOn()
            .noCollission().strength(1.0F);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_HANGING_SIGN = verdantHeartwoodBase().forceSolidOn()
            .noCollission().strength(1.0F);

    private static final BlockBehaviour.Properties verdantHeartwoodBase() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS)
                .strength(4.0F, 6.0F).sound(SoundType.WOOD).ignitedByLava();
    }
}