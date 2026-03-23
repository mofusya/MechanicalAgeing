package net.mofusya.mechanical_ageing.machinetiles;

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
import org.jetbrains.annotations.NotNull;

public class MachineMenu extends AbstractContainerMenu {
    public final MachineBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private final Block block;

    private final MachineTile machineTile;

    public MachineMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData, MachineTile machineTile, Block block) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2), machineTile, block);
    }

    public MachineMenu(int containerId, Inventory inventory, BlockEntity blockEntity, ContainerData data, MachineTile machineTile, Block block) {
        super(machineTile.getMenu().get(), containerId);
        checkContainerSize(inventory, machineTile.getSlots().size());
        this.blockEntity = (MachineBlockEntity) blockEntity;
        this.level = inventory.player.level();
        this.data = data;
        this.block = block;

        this.machineTile = machineTile;

        this.addPlayerInventory(inventory);
        this.addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            SlotList slotList = machineTile.getSlots();
            for (int i = 0; i < slotList.size(); i++) {
                this.addSlot(new SlotItemHandler(iItemHandler, i, slotList.get(i).x(), slotList.get(i).y()));
            }
        });

        this.addDataSlots(this.data);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack fItemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack = slot.getItem();
            fItemstack = itemstack.copy();

            int slotCount = this.machineTile.getSlots().size();

            if (index < slotCount) {
                if (!this.moveItemStackTo(itemstack, slotCount, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack, 0, slotCount, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return fItemstack;
    }

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
