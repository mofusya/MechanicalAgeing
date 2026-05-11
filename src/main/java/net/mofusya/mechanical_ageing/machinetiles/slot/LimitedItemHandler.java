package net.mofusya.mechanical_ageing.machinetiles.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LimitedItemHandler implements IItemHandlerModifiable {
    private final ItemStackHandler handler;
    private final List<Integer> allowedSlots;

    public LimitedItemHandler(ItemStackHandler handler, Integer... allowedSlots) {
        this(handler, List.of(allowedSlots));
    }

    public LimitedItemHandler(ItemStackHandler handler, List<Integer> allowedSlots) {
        this.handler = handler;
        this.allowedSlots = allowedSlots;
    }

    public void setSize(int size) {
        this.handler.setSize(size);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        if (!allowedSlots.contains(slot)) return;
        this.handler.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return this.handler.getSlots();
    }

    @Override
    @NotNull
    public ItemStack getStackInSlot(int slot) {
        if (!allowedSlots.contains(slot)) return ItemStack.EMPTY;
        return this.handler.getStackInSlot(slot);
    }

    @Override
    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (!allowedSlots.contains(slot)) return stack;
        return this.handler.insertItem(slot, stack, simulate);
    }

    @Override
    @NotNull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!allowedSlots.contains(slot)) return ItemStack.EMPTY;
        return this.handler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        if (!allowedSlots.contains(slot)) return 0;
        return this.handler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (!allowedSlots.contains(slot)) return false;
        return this.handler.isItemValid(slot, stack);
    }
}
