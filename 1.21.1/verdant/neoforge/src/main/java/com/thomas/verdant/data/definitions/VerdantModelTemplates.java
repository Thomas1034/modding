package com.thomas.verdant.data.definitions;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;

public class VerdantModelTemplates {

    public static final ModelTemplate FISH_TRAP = ModelTemplates.create(
            "verdant:fish_trap_base",
            TextureSlot.PARTICLE,
            TextureSlot.SIDE,
            TextureSlot.FRONT,
            TextureSlot.TOP,
            VerdantTextureSlot.INSET_HIGH,
            VerdantTextureSlot.INSET_LOW
    );

    public static final ModelTemplate OVERLAID_CUBE = ModelTemplates.create(
            "verdant:overlaid_cube",
            TextureSlot.PARTICLE,
            VerdantTextureSlot.BASE,
            VerdantTextureSlot.OVERLAY
    );

    public static final ModelTemplate TOP_OVERLAID_CUBE = ModelTemplates.create(
            "verdant:top_overlaid_cube",
            TextureSlot.PARTICLE,
            VerdantTextureSlot.BASE,
            VerdantTextureSlot.OVERLAY,
            TextureSlot.TOP,
            TextureSlot.SIDE,
            TextureSlot.BOTTOM
    );
}
