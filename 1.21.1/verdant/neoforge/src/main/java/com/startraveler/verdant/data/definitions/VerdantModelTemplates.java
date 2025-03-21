package com.startraveler.verdant.data.definitions;

import com.startraveler.verdant.block.custom.BombFlowerCropBlock;
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

    public static final ModelTemplate ASTERISK = ModelTemplates.create(
            "verdant:asterisk",
            TextureSlot.CROSS,
            VerdantTextureSlot.PLUS
    );

    public static final ModelTemplate HUGE_ASTERISK = ModelTemplates.create(
            "verdant:huge_asterisk",
            TextureSlot.CROSS,
            VerdantTextureSlot.PLUS
    );

    public static final ModelTemplate DOUBLE_SIDED_CUBE_COLUMN = ModelTemplates.create("verdant:double_sided_cube_column",
            TextureSlot.END,
            TextureSlot.SIDE
    );

    public static final ModelTemplate TRAP_STAGE0 = ModelTemplates.create(
            "verdant:trap_stage0",
            VerdantTextureSlot.PARTICLE_BASE,
            VerdantTextureSlot.BASE,
            VerdantTextureSlot.BAR,
            VerdantTextureSlot.SPIKES
    );
    public static final ModelTemplate TRAP_HIDDEN_STAGE0 = ModelTemplates.create(
            "verdant:trap_hidden_stage0",
            VerdantTextureSlot.PARTICLE_BASE,
            VerdantTextureSlot.BASE,
            VerdantTextureSlot.BAR,
            VerdantTextureSlot.SPIKES
    );
    public static final ModelTemplate TRAP_STAGE1 = ModelTemplates.create(
            "verdant:trap_stage1",
            VerdantTextureSlot.PARTICLE_BASE,
            VerdantTextureSlot.BASE,
            VerdantTextureSlot.BAR,
            VerdantTextureSlot.SPIKES
    );
    public static final ModelTemplate TRAP_STAGE2 = ModelTemplates.create(
            "verdant:trap_stage2",
            VerdantTextureSlot.PARTICLE_BASE,
            VerdantTextureSlot.BASE,
            VerdantTextureSlot.BAR,
            VerdantTextureSlot.SPIKES
    );
    public static final ModelTemplate TRAP_STAGE3 = ModelTemplates.create(
            "verdant:trap_stage3",
            VerdantTextureSlot.PARTICLE_BASE,
            VerdantTextureSlot.BASE,
            VerdantTextureSlot.BAR,
            VerdantTextureSlot.SPIKES
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

    public static ModelTemplate bombFlower(Integer age) {
        return age < BombFlowerCropBlock.MAX_AGE ? ModelTemplates.create(
                "verdant:bomb_flower_stage" + age,
                TextureSlot.PARTICLE,
                VerdantTextureSlot.BASE,
                VerdantTextureSlot.FLOWER
        ) : ModelTemplates.create(
                "verdant:bomb_flower_stage" + age,
                TextureSlot.PARTICLE,
                VerdantTextureSlot.BASE,
                VerdantTextureSlot.FLOWER,
                TextureSlot.SIDE
        );
    }

    public static ModelTemplate bombPile(Integer bombs) {
        return ModelTemplates.create(
                "verdant:bomb_pile_stack" + bombs,
                TextureSlot.PARTICLE,
                VerdantTextureSlot.FLOWER,
                TextureSlot.SIDE
        );
    }
}
