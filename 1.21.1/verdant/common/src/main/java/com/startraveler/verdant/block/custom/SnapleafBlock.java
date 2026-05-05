package com.startraveler.verdant.block.custom;

import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SnapleafBlock extends TrapBlock {

    public SnapleafBlock(Properties properties, int cooldownTime, int responseTime, float attackDamage) {
        super(properties, cooldownTime, responseTime, attackDamage, VerdantIFF::isEnemy, false, false, true);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        return level.getBlockState(belowPos).is(VerdantTags.Blocks.SUPPORTS_SNAPLEAVES) && super.canSurvive(
                state,
                level,
                pos
        );
    }
}
