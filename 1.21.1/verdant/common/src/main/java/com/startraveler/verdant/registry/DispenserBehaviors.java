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
import com.startraveler.verdant.item.custom.ThrowableBombItem;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DispenserBehaviors {

    public static void init() {

        DispenserBlock.registerBehavior(
                ItemRegistry.ROPE_COIL.get(),
                new ProjectileDispenseBehavior(ItemRegistry.ROPE_COIL.get())
        );


        DispenserBlock.registerBehavior(
                ItemRegistry.BLASTING_BLOOM.get(), new ThrowableBombItemDispenseItemsBehavior()
        );
        DispenserBlock.registerBehavior(
                ItemRegistry.TERRACOTTA_GRENADE.get(),
                new ThrowableBombItemDispenseItemsBehavior(
                        ThrowableBombItemDispenseItemsBehavior.PROJECTILE_SHOOT_POWER * 1.5f,
                        ThrowableBombItemDispenseItemsBehavior.PROJECTILE_FUSE
                )
        );
        DispenserBlock.registerBehavior(
                ItemRegistry.METAL_GRENADE.get(),
                new ThrowableBombItemDispenseItemsBehavior(
                        ThrowableBombItemDispenseItemsBehavior.PROJECTILE_SHOOT_POWER * 2,
                        ThrowableBombItemDispenseItemsBehavior.PROJECTILE_FUSE / 2
                )
        );
    }

    public static class ThrowableBombItemDispenseItemsBehavior extends DefaultDispenseItemBehavior {

        public static final float PROJECTILE_SHOOT_POWER = 0.3f;
        public static final int PROJECTILE_FUSE = 80;

        public final float shootPower;
        public final int fuse;


        public ThrowableBombItemDispenseItemsBehavior() {
            this(PROJECTILE_SHOOT_POWER, PROJECTILE_FUSE);
        }

        public ThrowableBombItemDispenseItemsBehavior(float shootPower, int fuse) {
            this.shootPower = shootPower;
            this.fuse = fuse;
        }

        public @NotNull ItemStack execute(@NotNull BlockSource pseudoLevel, @NotNull ItemStack stack) {
            Direction direction = pseudoLevel.state().getValue(DispenserBlock.FACING);
            Vec3 dispensePos = pseudoLevel.center().add(direction.getUnitVec3().scale(0.65));
            Vec3 shootAngle = direction.getUnitVec3();
            ThrowableBombItem throwableBombItem = stack.getItem() instanceof ThrowableBombItem tbi ? tbi : ItemRegistry.BLASTING_BLOOM.get();
            try {
                throwableBombItem.createAndLaunchBomb(
                        pseudoLevel.level(),
                        dispensePos.x,
                        dispensePos.y,
                        dispensePos.z,
                        null,
                        shootAngle
                );
            } catch (Exception exception) {
                Constants.LOG.error(
                        "Error while dispensing throwable bomb item {} from dispenser at {}",
                        pseudoLevel.pos(),
                        stack,
                        exception
                );
                return ItemStack.EMPTY;
            }

            stack.shrink(1);
            pseudoLevel.level().gameEvent(null, GameEvent.PRIME_FUSE, pseudoLevel.pos());
            return stack;
        }
    }

    ;

}

