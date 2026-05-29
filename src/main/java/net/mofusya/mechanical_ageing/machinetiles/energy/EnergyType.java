package net.mofusya.mechanical_ageing.machinetiles.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;

public abstract class EnergyType<T extends IEnergyStorage> {
    public abstract EnergyTypeFunction<T> getStorage();

    public abstract Capability<? extends IEnergyStorage> getCapability();

    public abstract String suffix();

    public abstract int getColor();

    public int getGradientColor(){
        return -404;
    }

    public void serializeNBT(IEnergyStorage iEnergyStorage, CompoundTag tag, String nbtId){
        T storage = (T) iEnergyStorage;
        tag.putInt(nbtId, storage.getEnergyStored());
    }

     public abstract void deserializeNBT(IEnergyStorage iEnergyStorage, CompoundTag tag, String nbtId);
}
