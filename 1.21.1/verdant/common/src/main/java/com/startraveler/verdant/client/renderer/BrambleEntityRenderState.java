package com.startraveler.verdant.client.renderer;

import net.minecraft.client.renderer.entity.state.TntRenderState;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BrambleEntityRenderState extends TntRenderState {
    public boolean isInvulnerable;
    @Nullable
    public BlockState shellBlockState;
    @Nullable
    public BlockState headBlockState;
    public float age;
    public float shellBlockScale;
    public float yRot;
    public float xRot;
    public float partialTick;
}
