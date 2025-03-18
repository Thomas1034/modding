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
package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.entity.custom.BlockIgnoringPrimedTnt;
import com.startraveler.verdant.mixin.PrimedTntAccessors;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class DispenserBehaviors {

    public static void init() {

        DispenserBlock.registerBehavior(
                ItemRegistry.ROPE_COIL.get(),
                new ProjectileDispenseBehavior(ItemRegistry.ROPE_COIL.get())
        );
        DispenserBlock.registerBehavior(
                ItemRegistry.BLASTING_BLOOM.get(), new DefaultDispenseItemBehavior() {

                    public static final float PROJECTILE_SHOOT_POWER = 0.3f;

                    public ItemStack execute(BlockSource pseudoLevel, ItemStack stack) {
                        Direction direction = pseudoLevel.state().getValue(DispenserBlock.FACING);

                        Vec3 dispensePos = pseudoLevel.center().add(direction.getUnitVec3().scale(0.65));
                        try {
                            PrimedTnt bomb = new BlockIgnoringPrimedTnt(
                                    pseudoLevel.level(),
                                    dispensePos.x,
                                    dispensePos.y,
                                    dispensePos.z,
                                    null
                            );
                            bomb.setBlockState(Objects.requireNonNull(stack.get(DataComponents.BLOCK_STATE))
                                    .apply(BlockRegistry.BLASTING_BUNCH.get().defaultBlockState()));
                            ((PrimedTntAccessors) bomb).setExplosionPower(2.0f);
                            bomb.setDeltaMovement(direction.getUnitVec3()
                                    .scale(PROJECTILE_SHOOT_POWER)
                                    .add(0, PROJECTILE_SHOOT_POWER / 5, 0));
                            bomb.setFuse(40);
                            bomb.setBoundingBox(bomb.getBoundingBox().deflate(0.25, 0.25, 0.25));
                            pseudoLevel.level().addFreshEntity(bomb);
                            bomb.refreshDimensions();
                        } catch (Exception exception) {
                            Constants.LOG.error(
                                    "Error while dispensing blasting bloom from dispenser at {}",
                                    pseudoLevel.pos(),
                                    exception
                            );
                            return ItemStack.EMPTY;
                        }

                        stack.shrink(1);
                        pseudoLevel.level().gameEvent(null, GameEvent.PRIME_FUSE, pseudoLevel.pos());
                        return stack;
                    }
                }
        );
    }

}

