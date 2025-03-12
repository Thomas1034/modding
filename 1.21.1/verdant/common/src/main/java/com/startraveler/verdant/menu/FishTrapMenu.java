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
package com.startraveler.verdant.menu;

import com.startraveler.verdant.block.custom.FishTrapBlock;
import com.startraveler.verdant.block.custom.entity.FishTrapBlockEntity;
import com.startraveler.verdant.client.screen.FishTrapScreen;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.MenuRegistry;
import com.startraveler.verdant.util.baitdata.BaitDataAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class FishTrapMenu extends AbstractContainerMenu {

    protected final ContainerLevelAccess access;
    protected final ContainerData data;
    protected final Container container;
    protected final Inventory playerInventory;
    protected final int fishTrapSlotCount;
    protected final BlockPos pos;
    protected final FishTrapBlockEntity blockEntity;

    // Client menu constructor
    public FishTrapMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, SyncedFishTrapMenuData.from(extraData));
    }

    // Alternate client menu constructor
    public FishTrapMenu(int containerId, Inventory inventory, SyncedFishTrapMenuData extraData) {
        this(
                containerId,
                inventory,
                extraData,
                new SimpleContainer(inventory.getContainerSize()),
                new SimpleContainerData(FishTrapBlockEntity.DATA_COUNT),
                ContainerLevelAccess.NULL);
    }

    // Server menu constructor
    public FishTrapMenu(int containerId, Inventory playerInventory, SyncedFishTrapMenuData extraData, Container fishTrapContainer, ContainerData fishTrapData, ContainerLevelAccess access) {
        super(MenuRegistry.FISH_TRAP_MENU.get(), containerId);

        this.container = fishTrapContainer;
        this.data = fishTrapData;
        this.data.set(FishTrapBlockEntity.NUM_BAIT_SLOTS_INDEX, extraData.numBaitSlots());
        this.data.set(FishTrapBlockEntity.NUM_OUTPUT_SLOTS_INDEX, extraData.numOutputSlots());
        this.pos = extraData.pos();
        this.blockEntity = (FishTrapBlockEntity) playerInventory.player.level().getBlockEntity(this.pos);

        this.access = access;
        this.playerInventory = playerInventory;
        this.fishTrapSlotCount = this.data.get(0) + this.data.get(1);

        this.addThisInventorySlots();
        this.addStandardInventorySlots(this.playerInventory, 8, 84);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (invSlot < this.container.getContainerSize()) {
                // If the item is in the player's inventory,
                if (!this.moveItemStackTo(originalStack, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    protected void addThisInventorySlots() {
        int inputOffsetIfEven = FishTrapScreen.SLOT_WIDTH * ((this.getNumBaitSlots() + 1) % 2) / 2;
        for (int i = 0; i < this.getNumBaitSlots(); i++) {
            this.addSlot(new BaitSlot(
                    this.blockEntity.getLevel().registryAccess(),
                    this.container,
                    this.blockEntity.absoluteIndexForBaitSlot(i),
                    FishTrapScreen.BAIT_SLOT_BLIT_OFFSET_X + 1 + FishTrapScreen.SLOT_WIDTH * (i - this.getNumBaitSlots() / 2) + inputOffsetIfEven,
                    FishTrapScreen.BAIT_SLOT_BLIT_OFFSET_Y + 1));
        }

        int outputOffsetIfEven = FishTrapScreen.SLOT_WIDTH * ((this.getNumOutputSlots() + 1) % 2) / 2;
        for (int i = 0; i < this.getNumOutputSlots(); i++) {
            this.addSlot(new Slot(
                    this.container,
                    this.blockEntity.absoluteIndexForOutputSlot(i),
                    FishTrapScreen.OUTPUT_SLOT_BLIT_OFFSET_X + 1 + FishTrapScreen.SLOT_WIDTH * (i - this.getNumOutputSlots() / 2) + outputOffsetIfEven,
                    FishTrapScreen.OUTPUT_SLOT_BLIT_OFFSET_Y + 1));
        }


    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, BlockRegistry.FISH_TRAP.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public float getProgress() {
        return ((float) this.blockEntity.getCatchProgress()) / ((float) this.blockEntity.getCycleTime());
    }

    public boolean isCrafting() {
        return this.blockEntity.getBlockState().getValue(FishTrapBlock.ENABLED);
    }

    public int getCatchPercent() {
        return this.blockEntity.getCatchPercent();
    }

    public int getNumOutputSlots() {
        return this.data.get(FishTrapBlockEntity.NUM_OUTPUT_SLOTS_INDEX);
    }

    public int getNumBaitSlots() {
        return this.data.get(FishTrapBlockEntity.NUM_BAIT_SLOTS_INDEX);
    }

    public record SyncedFishTrapMenuData(BlockPos pos, int numBaitSlots, int numOutputSlots) {

        public static SyncedFishTrapMenuData from(FriendlyByteBuf buf) {
            return new SyncedFishTrapMenuData(buf.readBlockPos(), buf.readInt(), buf.readInt());
        }

        public void to(FriendlyByteBuf buf) {
            buf.writeBlockPos(pos);
            buf.writeInt(numBaitSlots);
            buf.writeInt(numOutputSlots);
        }
    }

    public static class BaitSlot extends Slot {
        private final RegistryAccess access;

        public BaitSlot(RegistryAccess access, Container container, int slot, int x, int y) {
            super(container, slot, x, y);
            this.access = access;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return BaitDataAccess.lookupOrCache(access, stack.getItem()) != null;
        }
    }
}
