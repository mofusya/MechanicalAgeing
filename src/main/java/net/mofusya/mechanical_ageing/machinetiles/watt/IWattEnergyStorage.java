package net.mofusya.mechanical_ageing.machinetiles.watt;

import net.mofusya.ornatelib.lang.SeptiLong;

public interface IWattEnergyStorage {
    SeptiLong receive(SeptiLong maxReceive, boolean simulate);

    SeptiLong extract(SeptiLong maxExtract, boolean simulate);

    boolean canReceive();

    boolean canExtract();

    SeptiLong getStored();

    SeptiLong getCapacity();
}
