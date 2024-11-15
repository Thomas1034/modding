package com.thomas.verdant.screen.menu;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.entity.custom.FishTrapBlockEntity;
import com.thomas.verdant.screen.ModMenuTypes;
import com.thomas.verdant.screen.screen.FishTrapScreen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class FishTrapMenu extends AbstractContainerMenu {

	public final FishTrapBlockEntity blockEntity;
	private final Level level;
	private final ContainerData data;

	public FishTrapMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
		this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
				new SimpleContainerData(FishTrapBlockEntity.SYNCHED_DATA_SIZE));
	}

	public FishTrapMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
		super(ModMenuTypes.FISH_TRAP_MENU.get(), containerId);

		checkContainerSize(inv, FishTrapBlockEntity.TOTAL_SLOTS);
		checkContainerDataCount(data, FishTrapBlockEntity.SYNCHED_DATA_SIZE);

		this.blockEntity = ((FishTrapBlockEntity) entity);
		this.level = inv.player.level();
		this.data = data;

		this.addPlayerInventory(inv);

		this.addPlayerHotbar(inv);

		this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
			int outputOffsetIfEven = FishTrapScreen.SLOT_TEXTURE_OFFSET_X * ((FishTrapBlockEntity.OUTPUT_SLOTS + 1) % 2)
					/ 2;
			for (int i = 0; i < FishTrapBlockEntity.OUTPUT_SLOTS; i++) {
				this.addSlot(new SlotItemHandler(iItemHandler, FishTrapBlockEntity.OUTPUT_SLOTS_ARRAY[i],
						FishTrapScreen.OUTPUT_SLOT_BLIT_OFFSET_X + 1
								+ FishTrapScreen.SLOT_WIDTH * (i - FishTrapBlockEntity.OUTPUT_SLOTS / 2)
								+ outputOffsetIfEven,
						FishTrapScreen.OUTPUT_SLOT_BLIT_OFFSET_Y + 1));
			}

			int inputOffsetIfEven = FishTrapScreen.SLOT_TEXTURE_OFFSET_X * ((FishTrapBlockEntity.BAIT_SLOTS + 1) % 2)
					/ 2;
			for (int i = 0; i < FishTrapBlockEntity.BAIT_SLOTS; i++) {
				this.addSlot(new SlotItemHandler(iItemHandler, FishTrapBlockEntity.BAIT_SLOTS_ARRAY[i],
						FishTrapScreen.INPUT_SLOT_BLIT_OFFSET_X + 1
								+ FishTrapScreen.SLOT_WIDTH * (i - FishTrapBlockEntity.BAIT_SLOTS / 2)
								+ inputOffsetIfEven,
						FishTrapScreen.INPUT_SLOT_BLIT_OFFSET_Y + 1));
			}
		});

		this.addDataSlots(data);
	}

	public boolean isCrafting() {
		return this.data.get(0) > 0;
	}
	
	public int getCatchPercent() {
		return this.data.get(2);
	}

	public int getScaledProgress() {
		int progress = this.data.get(0);
		int maxProgress = this.data.get(1); // Max Progress
		int progressArrowSize = FishTrapScreen.ARROW_HEIGHT; // This is the height in pixels of your arrow

		return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
	}

	// CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
	// must assign a slot number to each of the slots used by the GUI.
	// For this container, we can see both the tile inventory's slots as well as the
	// player inventory slots and the hotbar.
	// Each time we add a Slot to the container, it automatically increases the
	// slotIndex, which means
	// 0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 -
	// 8)
	// 9 - 35 = player inventory slots (which map to the InventoryPlayer slot
	// numbers 9 - 35)
	// 36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 -
	// 8)
	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int BLOCK_ENTITY_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

	// THIS YOU HAVE TO DEFINE!
	private static final int BLOCK_ENTITY_INVENTORY_SLOT_COUNT = FishTrapBlockEntity.TOTAL_SLOTS; // must be the number
																									// of slots you
																									// have!

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		Slot sourceSlot = slots.get(index);
		if (sourceSlot == null || !sourceSlot.hasItem())
			return ItemStack.EMPTY; // EMPTY_ITEM
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		// Check if the slot clicked is one of the vanilla container slots
		if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			// This is a vanilla container slot so merge the stack into the tile inventory
			if (!moveItemStackTo(sourceStack, BLOCK_ENTITY_INVENTORY_FIRST_SLOT_INDEX,
					BLOCK_ENTITY_INVENTORY_FIRST_SLOT_INDEX + BLOCK_ENTITY_INVENTORY_SLOT_COUNT, false)) {
				return ItemStack.EMPTY; // EMPTY_ITEM
			}
		} else if (index < BLOCK_ENTITY_INVENTORY_FIRST_SLOT_INDEX + BLOCK_ENTITY_INVENTORY_SLOT_COUNT) {
			// This is a block entity slot so merge the stack into the players inventory
			if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT,
					false)) {
				return ItemStack.EMPTY;
			}
		} else {
			System.out.println("Invalid slotIndex:" + index);
			return ItemStack.EMPTY;
		}
		// If stack size == 0 (the entire stack was moved) set slot contents to null
		if (sourceStack.getCount() == 0) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(playerIn, sourceStack);
		return copyOfSourceStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player,
				ModBlocks.FISH_TRAP_BLOCK.get());
	}

	private void addPlayerInventory(Inventory playerInventory) {
		for (int i = 0; i < 3; ++i) {
			for (int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
			}
		}
	}

	private void addPlayerHotbar(Inventory playerInventory) {
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

}
