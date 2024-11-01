package com.thomas.verdant.block.entity.custom;

import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.thomas.verdant.block.custom.FishTrapBlock;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.screen.menu.FishTrapMenu;
import com.thomas.verdant.util.baitdata.BaitData;
import com.thomas.verdant.util.data.DataRegistries;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class FishTrapBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

	// OUTPUT SLOTS ARE LOW
	public static final Map<Item, BaitData> BAIT_MAP = DataRegistries.BAIT_DATA.stream()
			.collect(Collectors.toUnmodifiableMap((data) -> data.getItem(), (data) -> data));

	public static final int BAIT_SLOTS = 3;
	public static final int OUTPUT_SLOTS = 3;
	public static final int TOTAL_SLOTS = OUTPUT_SLOTS + BAIT_SLOTS;
	public static final IntPredicate IS_BAIT_SLOT = (i) -> i < BAIT_SLOTS;
	public static final IntPredicate IS_OUTPUT_SLOT = (i) -> i >= BAIT_SLOTS && i < TOTAL_SLOTS;
	public static final int[] BAIT_SLOTS_ARRAY = new int[] { 0, 1, 2 };
	public static final int[] OUTPUT_SLOTS_ARRAY = new int[] { 3, 4, 5 };

	private final ItemStackHandler itemHandler = new ItemStackHandler(TOTAL_SLOTS) {

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return (!IS_BAIT_SLOT.test(slot) || BAIT_MAP.containsKey(stack.getItem()))
					&& super.isItemValid(slot, stack);
		}

	};

	public static final String INVENTORY_TAG_NAME = "inventory";
	public static final String PROGRESS_TAG_NAME = "fish_trap_progress";

	private static final double BASE_CATCH_CHANCE = 3 / (60 * 30);

	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

	net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers = net.minecraftforge.items.wrapper.SidedInvWrapper
			.create(this, Direction.values());

	protected final ContainerData data;
	private int progress = 0;
	private int maxProgress = 60;

	public FishTrapBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.FISH_TRAP_BLOCK_ENTITY.get(), pos, state);

		this.data = new ContainerData() {

			@Override
			public int get(int index) {
				return switch (index) {
				case 0 -> FishTrapBlockEntity.this.progress;
				case 1 -> FishTrapBlockEntity.this.maxProgress;
				case 2 -> 0;
				default -> 0;
				};

			}

			@Override
			public void set(int index, int value) {
				switch (index) {
				case 0: {
					FishTrapBlockEntity.this.progress = value;
					break;
				}
				case 1: {
					FishTrapBlockEntity.this.maxProgress = value;
					break;
				}
				}

			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			if (side == null) {
				return this.lazyItemHandler.cast();
			}
			return this.handlers[side.ordinal()].cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		this.lazyItemHandler = LazyOptional.of(() -> this.itemHandler);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		this.lazyItemHandler.invalidate();
		for (int x = 0; x < handlers.length; x++)
			handlers[x].invalidate();
	}

	@Override
	public void reviveCaps() {
		super.reviveCaps();
		this.lazyItemHandler = LazyOptional.of(() -> this.itemHandler);
		this.handlers = SidedInvWrapper.create(this, Direction.values());
	}

	@Override
	public AbstractContainerMenu createMenu(int containerID, Inventory playerInventory, Player player) {
		return new FishTrapMenu(containerID, playerInventory, this, this.data);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.verdant.fish_trap_block");
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put(INVENTORY_TAG_NAME, itemHandler.serializeNBT());
		tag.putInt(PROGRESS_TAG_NAME, this.progress);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		itemHandler.deserializeNBT(tag.getCompound(INVENTORY_TAG_NAME));
		this.progress = tag.getInt(PROGRESS_TAG_NAME);
	}

	public void drops() {
		SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
		for (int i = 0; i < this.itemHandler.getSlots(); i++) {
			inventory.setItem(i, this.itemHandler.getStackInSlot(i));
		}
		Containers.dropContents(this.level, this.worldPosition, inventory);
	}

	public void tick(Level level, BlockPos pos, BlockState state) {

		if (this.hasRecipe() && state.getValue(FishTrapBlock.ENABLED)) {
			this.increaseCraftingProgress();
			BlockEntity.setChanged(level, pos, state);

			if (this.hasProgressFinished()) {
				this.craftItem();
				this.resetProgress();
			}
		} else {
			this.resetProgress();
		}
	}

	private boolean hasRecipe() {
		return true;
	}

	// Returns whether the item can be added to this slot.
	private boolean canInsertItemIntoSlot(int slot, ItemStack contents, Item item, int count) {
		boolean isBaitOrOutput = true;
		if (IS_BAIT_SLOT.test(slot)) {
			isBaitOrOutput = BAIT_MAP.containsKey(item);
		}
		return isBaitOrOutput && (contents.isEmpty()
				|| (contents.is(item) && contents.getCount() + count <= contents.getMaxStackSize()));
	}

	// Returns whether the item can be added to this slot.
	private int openSpaceInSlot(int slot, ItemStack contents, Item item, int count) {
		boolean isBaitOrOutput = true;
		if (IS_BAIT_SLOT.test(slot)) {
			isBaitOrOutput = BAIT_MAP.containsKey(item);
		}
		return isBaitOrOutput ? contents.isEmpty() ? item.getDefaultInstance().getMaxStackSize()
				: contents.is(item) ? contents.getMaxStackSize() - contents.getCount() : 0 : 0;
	}

	private void resetProgress() {
		this.progress = 0;

	}

	private boolean hasProgressFinished() {
		return this.progress >= this.maxProgress;
	}

	private void increaseCraftingProgress() {
		this.progress = Math.min(this.progress + 1, this.maxProgress);
	}

	private void craftItem() {

		// First, get the highest bait strength pair.
		Pair<BaitData, ItemStack> bestBait = this.getHighestCatchChanceBait();
		BaitData data = bestBait.getLeft();
		ItemStack stack = bestBait.getRight();

		// Get the catch chance
		double catchChance = BASE_CATCH_CHANCE;
		double consumeChance = 0;
		boolean hasBait = null != data && null != stack;
		if (hasBait) {
			catchChance = data.getCatchChance();
			consumeChance = data.getConsumeChance();
		}

		// Server only!
		if (this.level instanceof ServerLevel serverLevel) {
			// Base assumed water count
			int waterCount = 5;
			// Check for surrounding water.
			for (int i = -1; i <= 1; i++) {
				for (int k = -1; k <= 1; k++) {
					if ((i == 0 && k == 0)
							|| serverLevel.getBlockState(this.worldPosition.offset(i, 0, k)).is(Blocks.WATER)) {
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
			catchChance *= waterRatio;
			consumeChance *= 0.5 + 0.5 * waterRatio;

			// Check if a fish should be caught.
			double catchTarget = this.level.random.nextFloat();
			boolean anySucceeded = catchTarget > catchChance;
			while (catchChance > catchTarget) {
				catchChance--;
				// System.out.println("Catching!");
				float luck = 0;
				// Get the loot table.
				LootParams lootparams = (new LootParams.Builder(serverLevel))
						.withParameter(LootContextParams.ORIGIN, this.getBlockPos().getCenter())
						.withParameter(LootContextParams.BLOCK_STATE, this.getBlockState())
						.withParameter(LootContextParams.TOOL,
								this.getBlockState().getBlock().asItem().getDefaultInstance())
						.withLuck(luck).create(LootContextParamSets.FISHING);
				LootTable loottable = serverLevel.getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
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
	}

	private boolean tryAddCatch(ItemStack item) {
		boolean atLeastPartialSuccess = false;
		for (int i = 0; i < OUTPUT_SLOTS; i++) {
			int slot = OUTPUT_SLOTS_ARRAY[i];
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

	@SuppressWarnings("unused")
	private ItemStack[] getOutput() {
		ItemStack[] output = new ItemStack[OUTPUT_SLOTS];
		for (int i = 0; i < OUTPUT_SLOTS; i++) {
			output[i] = this.itemHandler.getStackInSlot(OUTPUT_SLOTS_ARRAY[i]);
		}
		return output;
	}

	private ItemStack[] getBait() {
		ItemStack[] bait = new ItemStack[BAIT_SLOTS];
		for (int i = 0; i < BAIT_SLOTS; i++) {
			bait[i] = this.itemHandler.getStackInSlot(BAIT_SLOTS_ARRAY[i]);
		}
		return bait;
	}

	private Pair<BaitData, ItemStack> getHighestCatchChanceBait() {
		BaitData best = null;
		ItemStack bestStack = null;
		for (ItemStack stack : this.getBait()) {
			if (null == best) {
				best = BAIT_MAP.get(stack.getItem());
				bestStack = stack;
			} else {
				BaitData other = BAIT_MAP.get(stack.getItem());
				if (null != other && best.getCatchChance() < other.getCatchChance()) {
					best = other;
					bestStack = stack;
				}
			}
		}
		return Pair.of(best, bestStack);
	}

	@Override
	public int getContainerSize() {
		return FishTrapBlockEntity.TOTAL_SLOTS;
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < FishTrapBlockEntity.TOTAL_SLOTS; i++) {
			if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return this.itemHandler.getStackInSlot(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int count) {
		return this.itemHandler.extractItem(slot, count, false);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		ItemStack stack = this.itemHandler.getStackInSlot(slot);
		this.itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		if (stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}
		this.itemHandler.setStackInSlot(slot, stack);
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

	@Override
	public void clearContent() {
		for (int i = 0; i < FishTrapBlockEntity.TOTAL_SLOTS; i++) {
			this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
		}
	}

	@Override
	public int[] getSlotsForFace(Direction face) {
		if (Direction.DOWN == face) {

			return OUTPUT_SLOTS_ARRAY;
		} else {

			return BAIT_SLOTS_ARRAY;
		}
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return this.canInsertItemIntoSlot(slot, this.getItem(slot), stack.getItem(), stack.getCount());
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction face) {
		return (Direction.DOWN != face) && IS_BAIT_SLOT.test(slot) && this.canPlaceItem(slot, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction face) {
		return IS_OUTPUT_SLOT.test(slot) && (Direction.DOWN == face);
	}

}
