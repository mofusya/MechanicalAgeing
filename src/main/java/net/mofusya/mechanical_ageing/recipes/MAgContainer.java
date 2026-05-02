package net.mofusya.mechanical_ageing.recipes;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class MAgContainer extends SimpleContainer {
    private final int @Nullable [] energies;
    private final MatterStack @Nullable [] matters;
    @Nullable
    private final FluidStack fluid;

    public MAgContainer(ItemStack[] items, int @Nullable [] energies, MatterStack @Nullable [] matters, @Nullable FluidStack fluid) {
        super(items);
        this.energies = energies;
        this.matters = matters;
        this.fluid = fluid;
    }

    public int @Nullable [] getEnergies() {
        return this.energies;
    }

    public MatterStack @Nullable [] getMatters() {
        return this.matters;
    }

    @Nullable
    public FluidStack getFluid() {
        return this.fluid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int @Nullable [] itemSlots = null;
        private int @Nullable [] energySlots = null;
        private int @Nullable [] matterSlots = null;
        private boolean fluidSlot = false;

        private Builder() {}

        public Builder itemSlots(int end) {
            return this.itemSlots(0, end);
        }

        public Builder itemSlots(int start, int end) {
            ArrayList<Integer> slots = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                slots.add(i);
            }
            int[] slotList = new int[slots.size()];
            for (int i = 0; i < slots.size(); i++) {
                slotList[i] = slots.get(i);
            }
            return this.itemSlotsList(slotList);
        }

        public Builder itemSlotsList(int @Nullable ... itemSlots) {
            if (itemSlots == null || itemSlots.length == 0) {
                this.itemSlots = null;
            } else {
                this.itemSlots = itemSlots;
            }
            return this;
        }

        public Builder energySlots(int end) {
            return this.energySlots(0, end);
        }

        public Builder energySlots(int start, int end) {
            ArrayList<Integer> slots = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                slots.add(i);
            }
            int[] slotList = new int[slots.size()];
            for (int i = 0; i < slots.size(); i++) {
                slotList[i] = slots.get(i);
            }
            return this.energySlotList(slotList);
        }

        public Builder energySlotList(int @Nullable ... energySlots) {
            if (energySlots == null || energySlots.length == 0) {
                this.energySlots = null;
            } else {
                this.energySlots = energySlots;
            }
            return this;
        }

        public Builder matterSlots(int end) {
            return this.matterSlots(0, end);
        }

        public Builder matterSlots(int start, int end) {
            ArrayList<Integer> slots = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                slots.add(i);
            }
            int[] slotList = new int[slots.size()];
            for (int i = 0; i < slots.size(); i++) {
                slotList[i] = slots.get(i);
            }
            return this.matterSlotList(slotList);
        }

        public Builder matterSlotList(int @Nullable ... matterSlots) {
            if (matterSlots == null || matterSlots.length == 0) {
                this.matterSlots = null;
            } else {
                this.matterSlots = matterSlots;
            }
            return this;
        }

        public Builder fluidSlot(){
            this.fluidSlot = true;
            return this;
        }

        public MAgContainer build(MachineBlockEntity blockEntity) {
            ItemStack[] items = new ItemStack[this.itemSlots == null ? 0 : this.itemSlots.length];
            int @Nullable [] energies = this.energySlots == null ? null : new int[this.energySlots.length];
            MatterStack @Nullable [] matters = this.matterSlots == null ? null : new MatterStack[this.matterSlots.length];
            @Nullable
            FluidStack fluid = null;

            for (int i = 0; i < items.length; i++) {
                items[i] = blockEntity.getItemHandler().getStackInSlot(this.itemSlots[i]);
            }
            if (energies != null) {
                for (int i = 0; i < energies.length; i++) {
                    energies[i] = blockEntity.getEnergyStorage(i).getEnergyStored();
                }
            }
            var matterHandler = blockEntity.getMatterHandler();
            if (matters != null && matterHandler != null){
                for (int i = 0; i < matters.length; i++) {
                    matters[i] = matterHandler.getStored(i);
                }
            }
            var fluidHandler = blockEntity.getFluidTank();
            if (this.fluidSlot && fluidHandler != null){
                fluid = fluidHandler.getFluid();
            }

            return new MAgContainer(items, energies, matters, fluid);
        }
    }
}
