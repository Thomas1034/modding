package com.thomas.verdant.registry;

import com.thomas.verdant.util.VerdantTags;
import net.minecraft.world.item.ToolMaterial;

public class ToolMaterialRegistry {

    public static ToolMaterial HEARTWOOD = new ToolMaterial(
            VerdantTags.Blocks.INCORRECT_FOR_HEARTWOOD_TOOL,
            59,
            6.0F,
            2.0F,
            5,
            VerdantTags.Items.HEARTWOOD_TOOL_MATERIALS
    );

    public static ToolMaterial IMBUED_HEARTWOOD = new ToolMaterial(
            VerdantTags.Blocks.INCORRECT_FOR_HEARTWOOD_TOOL,
            131,
            7.0F,
            2.5F,
            3,
            VerdantTags.Items.HEARTWOOD_TOOL_MATERIALS
    );

}
