package com.startraveler.verdant.client.renderer;

import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;

public class ThrownSpearRenderState extends ThrownTridentRenderState {
    public boolean isStuck;
    public float xRotRelative;
    public float yRotRelative;
    public double targetX;
    public double targetY;
    public double targetZ;
    public float xRotTarget;
    public float yRotTarget;
    public double relativeX;
    public double relativeY;
    public double relativeZ;

    public ThrownSpearRenderState() {
    }
}
