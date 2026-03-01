/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class InfestedRotatedPillarBlock extends RotatedPillarBlock {
    protected final Supplier<EntityType<? extends Monster>> infestor;

    public InfestedRotatedPillarBlock(Properties properties, Supplier<EntityType<? extends Monster>> infestor) {
        super(properties);
        this.infestor = infestor;
    }


    private void spawnInfestation(ServerLevel level, BlockPos pos) {
        Monster infestor = this.infestor.get().create(level, EntitySpawnReason.TRIGGERED);
        if (infestor != null) {

            infestor.snapTo(
                    pos.getBottomCenter(),
                    level.random.nextFloat() * Mth.TWO_PI,
                    0.0F
            );
            level.addFreshEntity(infestor);
            infestor.spawnAnim();
        }
    }

    @Override
    protected void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean simulate) {
        super.spawnAfterBreak(state, level, pos, stack, simulate);
        if (level.getGameRules().get(GameRules.BLOCK_DROPS) && !EnchantmentHelper.hasTag(
                stack,
                EnchantmentTags.PREVENTS_INFESTED_SPAWNS
        )) {
            this.spawnInfestation(level, pos);
        }
    }
}

