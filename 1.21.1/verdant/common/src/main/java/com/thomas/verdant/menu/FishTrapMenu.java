package com.thomas.verdant.menu;

import com.thomas.verdant.Constants;
import com.thomas.verdant.block.custom.FishTrapBlock;
import com.thomas.verdant.block.custom.entity.FishTrapBlockEntity;
import com.thomas.verdant.client.screen.FishTrapScreen;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.MenuRegistry;
import com.thomas.verdant.util.baitdata.BaitDataAccess;
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

        Constants.LOG.warn(
                "Printing container data on the {}:",
                playerInventory.player.level().isClientSide ? "client" : "server");
        for (int i = 0; i < this.data.getCount(); i++) {
            Constants.LOG.warn("{}: {}", i, this.data.get(i));
        }

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
        return AbstractContainerMenu.stillValid(this.access, player, BlockRegistry.FISH_TRAP_BLOCK.get());
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