package com.thomas.verdant.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FragileFrameBlock extends FrameBlock {


    public FragileFrameBlock(Properties properties) {
        super(properties);
    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {

        // Prevents fall damage.
        // entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
        if (fallDistance >= 1.5) {
            level.destroyBlock(pos, true);
        }
    }

}