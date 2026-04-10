package net.mofusya.mechanical_ageing.machinetiles.baseclass;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineMenu extends AbstractContainerMenu {
    public final MachineBlockEntity blockEntity;
    private final Level level;
    @Nullable
    private final ContainerData data;
    private final Block block;

    private final MachineTile machineTile;
    private final int ownSlotCount;

    public MachineMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData, MachineTile machineTile, Block block) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), machineTile.getDataSlotCount() >= 1 ? new SimpleContainerData(machineTile.getDataSlotCount()) : null, machineTile, block);
    }

    public MachineMenu(int containerId, Inventory inventory, BlockEntity blockEntity, @Nullable ContainerData data, MachineTile machineTile, Block block) {
        super(machineTile.getMenu().get(), containerId);
        checkContainerSize(inventory, machineTile.getSlots().size());
        this.blockEntity = (MachineBlockEntity) blockEntity;
        this.level = inventory.player.level();
        this.data = data;
        this.block = block;

        this.machineTile = machineTile;
        this.ownSlotCount = machineTile.getSlots().size();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            SlotList slotList = machineTile.getSlots();
            for (int i = 0; i < slotList.size(); i++) {
                this.addSlot(new SlotItemHandler(iItemHandler, i, slotList.get(i).x(), slotList.get(i).y()));
            }
        });

        this.addPlayerInventory(inventory);
        this.addPlayerHotbar(inventory);

        if (this.data != null) {
            this.addDataSlots(this.data);
        }
    }

    /*
     * BluSunrize
     * Copyright (c) 2023
     *
     * These codes, quickMoveStack, moveItemStackToWithMayPlace, moveItemStackToWithMayPlace, MoveItemsFunc is licensed under "Blu's License of Common Sense"
     * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.20.1/LICENSE
     */
    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int slot) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slotObject = this.slots.get(slot);
        if (slotObject.hasItem()) {
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

    public static boolean moveItemStackToWithMayPlace(List<Slot> slots, MoveItemsFunc move, ItemStack pStack, int pStartIndex, int pEndIndex) {
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

    public interface MoveItemsFunc {
        boolean moveItemStackTo(ItemStack var1, int var2, int var3, boolean var4);
    }
    /**----**/

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, this.block);
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
