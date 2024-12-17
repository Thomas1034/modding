package com.thomas.verdant.block.custom.entity;

import com.thomas.verdant.menu.FishTrapMenu;
import com.thomas.verdant.registry.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FishTrapBlockEntity extends BaseContainerBlockEntity {
    /**
     * The number of container data slots that this block entity has.
     * Stored statically so it can be updated once and changed everywhere
     * as needed.
     */
    public static final int DATA_COUNT = 4;
    /**
     * The index in the data in which the number of bait slots is stored.
     */
    public static final int NUM_BAIT_SLOTS_INDEX = 0;
    /**
     * The index in the data in which the number of output slots is stored.
     */
    public static final int NUM_OUTPUT_SLOTS_INDEX = 1;
    /**
     * The index in the data in which the maximum cycle time is stored.
     */
    public static final int CYCLE_TIME_INDEX = 2;
    /**
     * The index in the data in which the catch progress is stored.
     */
    public static final int CATCH_PROGRESS_INDEX = 3;
    /**
     * The tag name for this block entity's stored data.
     */
    public static final String SAVED_DATA_ACCESS_TAG = "SavedData";
    /**
     * The items currently placed in the slots of the fish trap.
     */
    private final NonNullList<ItemStack> items;
    /**
     * A restricted view of the items placed in the slots of the fish trap. Only has access to the bait items.
     * TODO will be used later.
     */
    private final List<ItemStack> bait;
    /**
     * A restricted view of the items placed in the slots of the fish trap. Only has access to the output items.
     * TODO will be used later.
     */
    private final List<ItemStack> output;
    /**
     * The number of bait slots this fish trap has. Bait slots come before output slots.
     */
    private int numBaitSlots;
    /**
     * The number of output slots this fish trap has; a higher number means it can store more fish.
     */
    private int numOutputSlots;
    /**
     * The time it should take for the fish trap to try one fishing attempt. Shorter times mean fish are caught more often.
     */
    private int cycleTime;
    /**
     * The progress in ticks till the next catch attempt.
     * Catch attempts are made when catchProgress equals cycleTime.
     */
    private int catchProgress;
    /**
     * Stored data for the block; should be automatically synced to the client (I think?)
     */
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case NUM_BAIT_SLOTS_INDEX -> FishTrapBlockEntity.this.numBaitSlots;
                case NUM_OUTPUT_SLOTS_INDEX -> FishTrapBlockEntity.this.numOutputSlots;
                case CYCLE_TIME_INDEX -> FishTrapBlockEntity.this.cycleTime;
                case CATCH_PROGRESS_INDEX -> FishTrapBlockEntity.this.catchProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {

                case 0: {
                    FishTrapBlockEntity.this.numBaitSlots = value;
                    return;
                }
                case 1: {
                    FishTrapBlockEntity.this.numOutputSlots = value;
                    return;
                }
                case 2: {
                    FishTrapBlockEntity.this.cycleTime = value;
                    return;
                }
                case 3: {
                    FishTrapBlockEntity.this.catchProgress = value;
                    return;
                }
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("[");
            for (int i = 0; i < this.getCount(); i++) {
                builder.append(this.get(i));
                builder.append(", ");
            }
            builder.append("]");
            return builder.toString();
        }
    };
    /**
     * The current bait the fish trap is using.
     */
    private ItemStack currentBait;


    public FishTrapBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityTypeRegistry.FISH_TRAP_BLOCK_ENTITY.get(), pos, blockState);
        this.numBaitSlots = 3;
        this.numOutputSlots = 4;
        this.cycleTime = 30;
        this.items = NonNullList.withSize(this.numBaitSlots + this.numOutputSlots, ItemStack.EMPTY);
        this.bait = items.subList(0, numBaitSlots);
        this.output = items.subList(numBaitSlots, numBaitSlots + numOutputSlots);

        this.dataAccess.set(CATCH_PROGRESS_INDEX, 10);
        this.dataAccess.set(CYCLE_TIME_INDEX, 20);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        int[] array = tag.getIntArray(SAVED_DATA_ACCESS_TAG);
        int dataCount = this.getDataCount();
        for (int i = 0; i < dataCount && i < array.length; i++) {
            this.dataAccess.set(i, array[i]);
        }
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        int dataCount = this.getDataCount();
        int[] array = new int[dataCount];
        for (int i = 0; i < dataCount; i++) {
            array[i] = this.dataAccess.get(i);
        }
        tag.putIntArray(SAVED_DATA_ACCESS_TAG, array);

        ContainerHelper.saveAllItems(tag, this.items, registries);

    }

    public int getNumBaitSlots() {
        return this.numBaitSlots;
    }

    public int getNumOutputSlots() {
        return this.numOutputSlots;
    }

    public int getDataCount() {
        return this.dataAccess.getCount();
    }

    public int absoluteIndexForBaitSlot(int slot) {
        if (slot < 0 || slot >= this.numBaitSlots) {
            throw new IllegalArgumentException("Not a bait slot: " + slot);
        }
        return slot;
    }

    public int absoluteIndexForOutputSlot(int slot) {
        if (slot < 0 || slot >= this.numOutputSlots) {
            throw new IllegalArgumentException("Not an output slot: " + slot);
        }
        return slot + this.numBaitSlots;
    }


    public boolean isBaitSlot(int i) {
        return i >= 0 && i < this.numBaitSlots;
    }

    public boolean isOutputSlot(int i) {
        return i >= this.numBaitSlots && i < this.numOutputSlots;
    }

    @Override
    protected Component getDefaultName() {
        return this.getBlockState().getBlock().getName();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            this.items.set(i, items.get(i));
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new FishTrapMenu(
                containerId,
                inventory,
                new FishTrapMenu.SyncedFishTrapMenuData(this.getBlockPos(), this.numBaitSlots, this.numOutputSlots),
                this,
                this.dataAccess,
                ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, this.items);
    }

    public int getCatchProgress() {
        return this.dataAccess.get(CATCH_PROGRESS_INDEX);
    }

    public int getCycleTime() {
        return this.dataAccess.get(CYCLE_TIME_INDEX);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        // TODO
        // Everything
    }
}
