package net.mofusya.mechanical_ageing.machinetiles.baseclass;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotList;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MachineBlockEntity extends BlockEntity implements MenuProvider {

    private final MachineTile machineTile;

    protected final ContainerData containerData;
    protected final int[] data;

    private final ItemStackHandler itemHandler;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final List<IEnergyStorage> energyStorages;
    private final List<LazyOptional<IEnergyStorage>> lazyEnergyHandler;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, MachineTile machineTile) {
        super(type, pos, state);
        this.machineTile = machineTile;
        SlotList slotList = machineTile.getSlots(new SlotList());
        this.itemHandler = new ItemStackHandler(slotList.size()) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack itemStack) {
                return slotList.get(slot).itemValidFunc().apply(itemStack);
            }
        };
        if (machineTile.getDataSlotCount() > 0) {
            this.data = new int[machineTile.getDataSlotCount()];
            this.containerData = new ContainerData() {
                @Override
                public int get(int index) {
                    return MachineBlockEntity.this.data[index];
                }

                @Override
                public void set(int index, int value) {
                    MachineBlockEntity.this.data[index] = value;
                }

                @Override
                public int getCount() {
                    return MachineBlockEntity.this.machineTile.getDataSlotCount();
                }
            };
        } else {
            this.data = null;
            this.containerData = null;
        }

        this.energyStorages = new ArrayList<>();
        this.lazyEnergyHandler = new ArrayList<>();
        for (EnergySlotProperties energy : machineTile.getEnergySlots()) {
            this.energyStorages.add(energy.energyType().getStorage().apply(() -> {
                MachineBlockEntity.this.setChanged();
                MachineBlockEntity.this.getLevel().sendBlockUpdated(MachineBlockEntity.this.getBlockPos(), MachineBlockEntity.this.getBlockState(), MachineBlockEntity.this.getBlockState(), 3);
            }, energy.capacity(), energy.maxReceive(), energy.maxExtract(), energy.energy()));
            this.lazyEnergyHandler.add(LazyOptional.empty());
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        this.machineTile.tick(level, pos, state, this);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return this.machineTile.getDisplayName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new MachineMenu(containerId, inventory, this, this.containerData, this.machineTile, this.getBlockState().getBlock());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        EnergySlotList energySlots = this.machineTile.getEnergySlots();
        for (int i = 0; i < energySlots.size(); i++) {
            if (cap == energySlots.get(i).energyType().getCapability()) {
                return this.lazyEnergyHandler.get(i).cast();
            }
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyItemHandler = LazyOptional.of(() -> this.itemHandler);

        EnergySlotList energySlots = this.machineTile.getEnergySlots();
        for (int i = 0; i < energySlots.size(); i++) {
            int finalI = i;
            this.lazyEnergyHandler.set(i, LazyOptional.of(() -> this.energyStorages.get(finalI)));
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.lazyItemHandler.invalidate();

        EnergySlotList energySlots = this.machineTile.getEnergySlots();
        for (int i = 0; i < energySlots.size(); i++) {
            var energyHandler = this.lazyEnergyHandler.get(i);
            energyHandler.invalidate();
            this.lazyEnergyHandler.set(i, energyHandler);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", this.itemHandler.serializeNBT());

        EnergySlotList energySlots = this.machineTile.getEnergySlots();
        for (int i = 0; i < energySlots.size(); i++) {
            tag.putInt("energy_storage_" + (i + 1), this.energyStorages.get(i).getEnergyStored());
        }

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.itemHandler.deserializeNBT(tag.getCompound("inventory"));

        EnergySlotList energySlots = this.machineTile.getEnergySlots();
        for (int i = 0; i < energySlots.size(); i++) {
            EnergySlotProperties energy = energySlots.get(i);
            var energyStorage = energySlots.get(i).energyType().getStorage().apply(() -> {
                MachineBlockEntity.this.setChanged();
                MachineBlockEntity.this.getLevel().sendBlockUpdated(MachineBlockEntity.this.getBlockPos(), MachineBlockEntity.this.getBlockState(), MachineBlockEntity.this.getBlockState(), 3);
            }, energy.capacity(), energy.maxReceive(), energy.maxExtract(), tag.getInt("energy_storage_" + (i + 1)));
            this.energyStorages.set(i, energyStorage);
        }
    }


    /*Getter setters*/
    public MachineTile getMachineTile() {
        return this.machineTile;
    }

    public ItemStackHandler getItemHandler() {
        return this.itemHandler;
    }

    public int getData(int index) {
        return this.data[index];
    }

    public void setData(int index, int value) {
        this.data[index] = value;
    }

    public void modifyData(int index, Function<Integer, Integer> modify) {
        this.setData(index, modify.apply(this.getData(index)));
    }

    public void addData(int index) {
        this.addData(index, 1);
    }

    public void addData(int index, int add) {
        this.modifyData(index, value -> value + add);
    }
}