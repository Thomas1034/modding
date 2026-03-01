package com.startraveler.verdant.client.renderer;

import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class OozeRenderState extends SlimeRenderState {
    public ItemStackRenderState item;
    public MovingBlockRenderState block;
}
