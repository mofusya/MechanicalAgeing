package net.mofusya.mechanical_ageing.machinetiles.energy;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class EnergyType<T extends IEnergyStorage> {
    public abstract EnergyTypeFunction<T> getStorage();

    public abstract Capability<? extends IEnergyStorage> getCapability();

    public abstract String suffix();

    public abstract int getColor();

    public int getGradientColor(){
        return -404;
    }
}
