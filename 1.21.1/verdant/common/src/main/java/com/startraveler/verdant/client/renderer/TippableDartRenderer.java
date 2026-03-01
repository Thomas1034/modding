package com.startraveler.verdant.client.renderer;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.entity.custom.DartEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class TippableDartRenderer extends DartRenderer<@NotNull DartEntity, @NotNull DartRenderState> {
    public static final Identifier NORMAL_DART_LOCATION = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/projectiles/dart.png"
    );

    public TippableDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected @NotNull Identifier getTextureLocation(DartRenderState state) {
        return NORMAL_DART_LOCATION;
    }

    @Override
    public void extractRenderState(DartEntity dart, DartRenderState state, float partialTick) {
        super.extractRenderState(dart, state, partialTick);
        state.isTipped = dart.getColor() != -1;
        state.color = dart.getColor();
    }

    @Override
    public DartRenderState createRenderState() {
        return new DartRenderState();
    }
}
