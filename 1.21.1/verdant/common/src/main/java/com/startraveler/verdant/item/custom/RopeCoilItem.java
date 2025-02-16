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
package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.entity.custom.ThrownRopeEntity;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.registry.DataComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RopeCoilItem extends Item implements ProjectileItem {

    public static final RopeCoilData DEFAULT_DATA_COMPONENT = new RopeCoilData(
            4,
            false,
            0,
            RopeCoilData.LanternOptions.NONE
    );

    public RopeCoilItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        RopeCoilData data = stack.get(DataComponentRegistry.ROPE_COIL.get());
        if (data != null) {
            String baseKey = this.descriptionId;
            if (data.length() > 0) {
                tooltipComponents.add(Component.translatable(baseKey + ".length", data.length())
                        .withStyle(ChatFormatting.GRAY));
            }
            if (data.hasHook()) {
                tooltipComponents.add(Component.translatable(baseKey + ".hook").withStyle(ChatFormatting.GRAY));
            }
            if (data.lightLevel() > 0) {
                tooltipComponents.add(Component.translatable(baseKey + ".glow", data.lightLevel())
                        .withStyle(ChatFormatting.GRAY));
            }
            if (data.lantern() != RopeCoilData.LanternOptions.NONE) {
                tooltipComponents.add(Component.translatable(baseKey + "." + data.lantern().typeName).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ITEM_FRAME_ADD_ITEM,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (level instanceof ServerLevel serverlevel) {
            Projectile.spawnProjectileFromRotation(
                    ThrownRopeEntity::new,
                    serverlevel,
                    itemstack,
                    player,
                    0.0F,
                    1.5F,
                    0.1F
            );
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return new ThrownRopeEntity(level, pos.x(), pos.y(), pos.z(), stack);
    }
}

