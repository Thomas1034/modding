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
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;
import java.util.function.Function;

public class HoeRemovableItemBlock extends Block {

    private final ResourceKey<LootTable> itemLootTable;
    private final Function<UseOnContext, BlockState> stateProvider;

    // For cassava roots.
    // They'll grow under the plant, if it's in dirt.
    // You'll need to collect the plant and hoe the ground underneath it again
    // to collect the roots.
    public HoeRemovableItemBlock(BlockBehaviour.Properties properties, ResourceKey<LootTable> itemLootTable, Function<UseOnContext, BlockState> stateProvider) {
        super(properties);
        this.itemLootTable = itemLootTable;
        this.stateProvider = stateProvider;
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (stack.is(ItemTags.HOES) && (player.getAbilities().mayBuild || stack.canBreakBlockInAdventureMode(new BlockInWorld(level,
                pos,
                false
        )))) {
            UseOnContext context = new UseOnContext(player, hand, hitResult);
            BlockState newState = this.stateProvider.apply(context);


            if (level instanceof ServerLevel serverLevel) {

                LootTable table = level.getServer().reloadableRegistries().getLootTable(this.itemLootTable);

                LootParams lootparams = (new LootParams.Builder(serverLevel)).withParameter(
                                LootContextParams.ORIGIN,
                                pos.getCenter()
                        )
                        .withParameter(LootContextParams.TOOL, stack)
                        .withParameter(LootContextParams.BLOCK_STATE, state)
                        .withParameter(LootContextParams.THIS_ENTITY, player)
                        .create(LootContextParamSets.BLOCK);

                List<ItemStack> loot = table.getRandomItems(lootparams);

                level.setBlockAndUpdate(pos, newState);
                for (ItemStack poppedStack : loot) {
                    Block.popResourceFromFace(level, pos, hitResult.getDirection(), poppedStack);
                }

            }
            return InteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}

