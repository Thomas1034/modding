package com.thomas.verdant.block.custom.entity;

import com.thomas.verdant.registry.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FishTrapBlockEntity extends BaseContainerBlockEntity {

    /**
     * The items currently placed in the slots of the fish trap.
     */
    private final NonNullList<ItemStack> items;
    /**
     * A restricted view of the items placed in the slots of the fish trap. Only has access to the bait items.
     */
    private final List<ItemStack> bait;
    /**
     * A restricted view of the items placed in the slots of the fish trap. Only has access to the
     */
    private final List<ItemStack> output;
    /**
     * The number of bait slots this fish trap has.
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
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> FishTrapBlockEntity.this.numBaitSlots;
                case 1 -> FishTrapBlockEntity.this.numOutputSlots;
                case 2 -> FishTrapBlockEntity.this.cycleTime;
                case 3 -> FishTrapBlockEntity.this.catchProgress;
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
            return 4;
        }
    };
    /**
     * The current bait the fish trap is using.
     */
    private ItemStack currentBait;


    public FishTrapBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityTypeRegistry.FISH_TRAP_BLOCK_ENTITY.get(), pos, blockState);
        this.numBaitSlots = 3;
        this.numOutputSlots = 3;
        this.cycleTime = 30;
        this.items = NonNullList.withSize(5, ItemStack.EMPTY);
        this.bait = items.subList(0, numBaitSlots);
        this.output = items.subList(numBaitSlots, numOutputSlots);
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
        return null;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    public void drops() {
    }
}
