package com.startraveler.verdant.entity.goal;


import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class BoundedDistanceRangedAttackGoal extends RangedAttackGoal {
    private final float[] distanceBounds;

    @SuppressWarnings("unused")
    public BoundedDistanceRangedAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackInterval, float attackRadius, FloatList distanceBounds) {
        super(rangedAttackMob, speedModifier, attackInterval, attackRadius);
        this.distanceBounds = distanceBounds.toFloatArray();
    }

    public BoundedDistanceRangedAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius, FloatList distanceBounds) {
        super(rangedAttackMob, speedModifier, attackIntervalMin, attackIntervalMax, attackRadius);
        this.distanceBounds = distanceBounds.toFloatArray();
    }

    @Override
    public boolean canUse() {
        return this.isValidDistance(this.mob, this.mob.getTarget()) && super.canUse();
    }

    public boolean canContinueToUse() {
        return this.isValidDistance(this.mob, this.mob.getTarget()) && super.canContinueToUse();
    }

    private boolean isValidDistance(Mob mob, LivingEntity target) {
        if (target == null || mob == null) {
            return false;
        }
        float distanceToTarget = mob.distanceTo(target);
        return isValidDistance(distanceToTarget);
    }

    private boolean isValidDistance(float distanceToTarget) {
        return isValidDistance(distanceToTarget, this.distanceBounds);
    }

    public boolean isValidDistance(float distanceToTarget, float[] bounds) {
        int length = bounds.length;

        if (length == 0) {
            return true;
        }

        for (int i = 0; i + 1 < length; i += 2) {
            float lower = bounds[i];
            float upper = bounds[i + 1];
            if (distanceToTarget > lower && distanceToTarget < upper) {
                return true;
            }
        }

        if (length % 2 == 1) {
            return distanceToTarget > bounds[length - 1];
        }

        return false;
    }

}

