package net.mofusya.mechanical_ageing.machinetiles.watt;

import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.UnLong;

public interface IWattEnergyStorage {
    UnLong receive(UnLong maxReceive, boolean simulate);

    UnLong extract(UnLong maxExtract, boolean simulate);

    boolean canReceive();

    boolean canExtract();

    UnLong getStored();

    UnLong getCapacity();
}
