package net.mofusya.mechanical_ageing.machinetiles.energy;

import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.mofusya.mechanical_ageing.tiles.energy.ForgeEnergyStorage;

@FunctionalInterface
public interface EnergyTypeFunction<T extends IEnergyStorage> {
    T apply(Runnable runnable, int capacity, int maxReceive, int maxExtract, int energy);
}
