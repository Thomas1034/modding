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
package com.startraveler.verdant.block.custom.entity;

import com.startraveler.verdant.block.custom.FishTrapBlock;
import com.startraveler.verdant.menu.FishTrapMenu;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import com.startraveler.verdant.util.baitdata.BaitData;
import com.startraveler.verdant.util.baitdata.BaitDataAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.stream.IntStream;

public class FishTrapBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    /**
     * The number of container data slots that this block entity has.
     * Stored statically so it can be updated once and changed everywhere
     * as needed.
     */
    public static final int DATA_COUNT = 5;
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
     * The index in the data in which the catch chance is stored.
     */
    public static final int CATCH_PERCENT_INDEX = 4;
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
     */
    private final List<ItemStack> bait;
    /**
     * A restricted view of the items placed in the slots of the fish trap. Only has access to the output items.
     */
    private final List<ItemStack> output;
    private final int[] baitSlots;
    private final int[] outputSlots;
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
     * The current percent chance of catching on the next attempt.
     */
    private int catchPercent;
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
                case CATCH_PERCENT_INDEX -> FishTrapBlockEntity.this.catchPercent;
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
                case 4: {
                    FishTrapBlockEntity.this.catchPercent = value;
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
        this.numOutputSlots = 3;
        this.baitSlots = IntStream.range(0, this.numBaitSlots).map(this::absoluteIndexForBaitSlot).toArray();
        this.outputSlots = IntStream.range(0, this.numOutputSlots).map(this::absoluteIndexForOutputSlot).toArray();
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

    // Create an update tag here, like above.
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
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

    public int getCatchPercent() {
        return this.dataAccess.get(CATCH_PERCENT_INDEX);
    }

    public int getCatchProgress() {
        return this.dataAccess.get(CATCH_PROGRESS_INDEX);
    }

    public int getCycleTime() {
        return this.dataAccess.get(CYCLE_TIME_INDEX);
    }

    private boolean hasRecipe() {
        return true;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {

        if (this.hasRecipe() && state.getValue(FishTrapBlock.ENABLED)) {
            this.increaseCraftingProgress();

            if (this.hasProgressFinished()) {
                this.craftItem();
                this.resetProgress();
            }
            // This is probably important?
            BlockEntity.setChanged(level, pos, state);
            // This actually marks it as updated.
            level.sendBlockUpdated(pos, state, state, 3);
        } else {
            this.resetProgress();
        }
    }

    private void resetProgress() {
        this.catchProgress = 0;
    }

    private boolean hasProgressFinished() {
        return this.catchProgress >= this.cycleTime;
    }

    private void increaseCraftingProgress() {
        this.catchProgress = Math.min(this.catchProgress + 1, this.cycleTime);
    }

    private Pair<BaitData.InnerData, ItemStack> getHighestCatchChanceBait() {
        BaitData.InnerData best = null;
        ItemStack bestStack = null;
        // System.out.println("Getting best bait out of " +
        // Arrays.toString(this.getBait()));
        for (ItemStack stack : this.bait) {
            if (null == best) {
                best = BaitDataAccess.lookupOrCache(this.level.registryAccess(), stack.getItem());
                bestStack = stack;
            } else {
                BaitData.InnerData other = BaitDataAccess.lookupOrCache(this.level.registryAccess(), stack.getItem());
                if (null != other && best.catchChance() < other.catchChance()) {
                    best = other;
                    bestStack = stack;
                }
            }
        }
        return new Pair<>(best, bestStack);
    }


    private void craftItem() {

        if (!(this.level instanceof ServerLevel serverLevel)) {
            return;
        }

        // System.out.println("This level is " + this.level.getClass());
        // System.out.println("This is " + this);

        // First, get the highest bait strength pair.
        Pair<BaitData.InnerData, ItemStack> bestBait = this.getHighestCatchChanceBait();
        BaitData.InnerData data = bestBait.getA();
        ItemStack stack = bestBait.getB();
        // System.out.println("The data is " + data);
        // System.out.println("The stack is " + stack);

        // Get the catch chance
        double catchChance = 0;
        double consumeChance = 0;
        boolean hasBait = null != data && null != stack;
        if (hasBait) {
            catchChance = data.catchChance();
            consumeChance = data.consumeChance();
        }
        // Base assumed water count
        int waterCount = 5;
        // Check for surrounding water.
        for (int i = -1; i <= 1; i++) {
            for (int k = -1; k <= 1; k++) {
                if ((i == 0 && k == 0) || serverLevel.getBlockState(this.worldPosition.offset(i, 0, k))
                        .is(Blocks.WATER)) {
                    waterCount += 2;
                    if (serverLevel.getBlockState(this.worldPosition.offset(i, -1, k)).is(Blocks.WATER)) {
                        waterCount++;
                    }
                    for (int j = 1; j <= 2; j++) {

                        if (serverLevel.getBlockState(this.worldPosition.offset(i, j, k)).is(Blocks.WATER)) {
                            waterCount++;
                        }
                    }
                }
            }
        }
        int maxWater = 50;

        // Scale catch and consume chance by water ratio
        double waterRatio = ((double) waterCount) / ((double) maxWater);
        // System.out.println("The raw catch chance is " + catchChance);
        // System.out.println("The water ratio is " + waterRatio);
        catchChance *= waterRatio;
        consumeChance *= 0.5 + 0.5 * waterRatio;
        // Set the catch percent
        this.catchPercent = (int) (catchChance * 100);
        // System.out.println("Setting the catch percent to " + this.catchPercent);

        // Check if a fish should be caught.
        double catchTarget = this.level.random.nextFloat();
        boolean anySucceeded = catchTarget > catchChance;
        while (catchChance > catchTarget) {
            catchChance--;
            // System.out.println("Catching!");
            float luck = 0;
            // Get the loot table.
            LootParams lootparams = (new LootParams.Builder(serverLevel)).withParameter(
                            LootContextParams.ORIGIN,
                            this.getBlockPos().getCenter())
                    .withParameter(
                            LootContextParams.TOOL,
                            this.getBlockState().getBlock().asItem().getDefaultInstance())
                    .withLuck(luck)
                    .create(LootContextParamSets.FISHING);
            LootTable loottable = serverLevel.getServer()
                    .reloadableRegistries()
                    .getLootTable(BuiltInLootTables.FISHING);
            List<ItemStack> list = loottable.getRandomItems(lootparams);

            for (ItemStack item : list) {
                anySucceeded |= this.tryAddCatch(item);
            }
        }

        // Check if a bait should be consumed.
        if (consumeChance > this.level.random.nextFloat() && null != stack && anySucceeded) {
            // System.out.println("Consuming!");
            stack.shrink(1);
        }
    }

    private int openSpaceInSlot(int slot, ItemStack contents, Item item, int count) {
        boolean isBaitOrOutput = true;
        if (this.isBaitSlot(slot)) {
            isBaitOrOutput = BaitDataAccess.lookupOrCache(this.level.registryAccess(), item) != null;
        }
        return isBaitOrOutput ? contents.isEmpty() ? item.getDefaultInstance()
                .getMaxStackSize() : contents.is(item) ? contents.getMaxStackSize() - contents.getCount() : 0 : 0;
    }

    private boolean tryAddCatch(ItemStack item) {
        boolean atLeastPartialSuccess = false;
        for (int i = 0; i < this.numOutputSlots; i++) {
            int slot = this.absoluteIndexForOutputSlot(i);
            ItemStack contents = this.getItem(slot);
            int openSpace = openSpaceInSlot(slot, contents, item.getItem(), item.getCount());
            if (openSpace > 0) {

                if (openSpace >= item.getCount()) {
                    ItemStack copy = item.copy();
                    copy.setCount(copy.getCount() + contents.getCount());
                    this.setItem(slot, copy);
                    item.setCount(0);

                } else {
                    ItemStack copy = item.copy();
                    copy.setCount(copy.getMaxStackSize());
                    this.setItem(slot, copy);
                    item.setCount(item.getCount() - openSpace);
                }
                atLeastPartialSuccess = true;
            }
        }

        return atLeastPartialSuccess;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return side == Direction.DOWN ? this.outputSlots : this.baitSlots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return direction != Direction.DOWN && this.isBaitSlot(index);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN && this.isOutputSlot(index);
    }
}

