package net.mofusya.mechanical_ageing.machinetiles.baseclass;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

import java.util.List;
import java.util.Optional;

/**
 * A class for handling the Menu of a machine.
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "resource"})
@EverythingAreNonNullByDefault
public class MachineMenu extends AbstractContainerMenu {
    /**
     * The field for storing this machine tile.
     */
    private final MachineTile machineTile;
    /**
     * The field for storing this blockEntity.
     */
    private final MachineBlockEntity blockEntity;
    /**
     * A field for storing this level.
     */
    private final Level level;
    /**
     * A {@code Optional} field for storing this Container data.
     */
    private final Optional<ContainerData> data;
    /**
     * A field for storing this slot count.
     */
    private final int ownSlotCount;

    /**
     * An overloaded constructor.
     *
     * @param windowId    A {@code Integer} menu id for this.
     * @param inventory   The inventory from the player opening this.
     * @param buf         A FriendlyByteBuf that stores the block position of the blockEntity that the player is opening this from.
     * @param machineTile The {@code MachineTile} settings for this.
     */
    public MachineMenu(int windowId, Inventory inventory, FriendlyByteBuf buf, MachineTile machineTile) {
        this(windowId, inventory, (MachineBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()), Optional.empty(), machineTile);
    }

    /**
     * The root constructor.
     *
     * @param windowId    A {@code Integer} menu id for this.
     * @param inventory   The inventory from the player opening this.
     * @param blockEntity The blockEntity the player is opening this from.
     * @param data        A {@code Optional}
     * @param machineTile The {@code MachineTile} settings for this.
     */
    public MachineMenu(int windowId, Inventory inventory, MachineBlockEntity blockEntity, Optional<ContainerData> data, MachineTile machineTile) {
        super(machineTile.getMenuType().get(), windowId);
        checkContainerSize(inventory, 0);
        this.machineTile = machineTile;
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();
        this.data = data;
        this.ownSlotCount = 0;

        this.addPlayerInventory(inventory);
        this.addPlayerHotbar(inventory);

        this.getData().ifPresent(this::addDataSlots);
    }

    /*
     * BluSunrize
     * Copyright (c) 2023
     *
     * These codes, quickMoveStack, moveItemStackToWithMayPlace, moveItemStackToWithMayPlace, MoveItemsFunc is licensed under "Blu's License of Common Sense"
     * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.20.1/LICENSE
     */
    //region quickMoveStack() from BluSunrize
    public static boolean moveItemStackToWithMayPlace(
            List<Slot> slots, MoveItemsFunc move, ItemStack pStack, int pStartIndex, int pEndIndex
    ) {
        boolean inAllowedRange = true;
        int allowedStart = pStartIndex;
        for (int i = pStartIndex; i < pEndIndex; i++) {
            boolean mayplace = slots.get(i).mayPlace(pStack);
            if (inAllowedRange && !mayplace) {
                if (move.moveItemStackTo(pStack, allowedStart, i, false))
                    return true;
                inAllowedRange = false;
            } else if (!inAllowedRange && mayplace) {
                allowedStart = i;
                inAllowedRange = true;
            }
        }
        return inAllowedRange && move.moveItemStackTo(pStack, allowedStart, pEndIndex, false);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slotObject = this.slots.get(slot);
        if (slotObject != null && slotObject.hasItem()) {
            ItemStack itemstack1 = slotObject.getItem();
            itemstack = itemstack1.copy();
            if (slot < ownSlotCount) {
                if (!this.moveItemStackTo(itemstack1, ownSlotCount, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.moveItemStackToWithMayPlace(itemstack1, 0, ownSlotCount))
                return ItemStack.EMPTY;

            if (itemstack1.isEmpty())
                slotObject.set(ItemStack.EMPTY);
            else
                slotObject.setChanged();
        }

        return itemstack;
    }

    protected boolean moveItemStackToWithMayPlace(ItemStack pStack, int pStartIndex, int pEndIndex) {
        return moveItemStackToWithMayPlace(slots, this::moveItemStackTo, pStack, pStartIndex, pEndIndex);
    }
    //endregion

    /**
     * A method for testing if the menu, block, blockEntity of still valid.
     *
     * @param player The {@code Player} opening this.
     * @return The tested result if this is still valid.
     */
    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(this.getLevel(), blockEntity.getBlockPos()), player, this.getMachineTile().getBlock().get());
    }

    //region Helpers
    /**
     * A helper method for adding the inventory slots from the player opening this.
     *
     * @param playerInventory The inventory from the player opening this.
     */
    protected void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    /**
     * A helper method for adding the hotbar slots from the player opening this.
     *
     * @param playerInventory The inventory from the player opening this.
     */
    protected void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    //endregion
    //region Getters
    /**
     * A getter for this {@code machineTile}.
     *
     * @return This {@code machineTile}.
     */
    public MachineTile getMachineTile() {
        return machineTile;
    }

    /**
     * A getter for this {@code blockEntity}.
     *
     * @return This {@code blockEntity}.
     */
    public MachineBlockEntity getBlockEntity() {
        return blockEntity;
    }

    /**
     * A getter for this {@code level}.
     *
     * @return This {@code level}.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * A getter for this. {@code data}.
     *
     * @return This {@code data}.
     */
    public Optional<ContainerData> getData() {
        return data;
    }
    //endregion

    /*
     * BluSunrize
     * Copyright (c) 2023
     *
     * These codes, quickMoveStack, moveItemStackToWithMayPlace, moveItemStackToWithMayPlace, MoveItemsFunc is licensed under "Blu's License of Common Sense"
     * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.20.1/LICENSE
     */
    public interface MoveItemsFunc {
        boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
    }
}
