package net.mofusya.mechanical_ageing.machinetiles.energy;

import net.mofusya.ornatelib.lang.SeptiLong;

public interface ISeptiLongEnergyHandler {
    SeptiLong receiveEnergy(SeptiLong maxReceive, boolean simulate);

    SeptiLong extractEnergy(SeptiLong maxExtract, boolean simulate);

    SeptiLong getEnergyStored();

    SeptiLong getMaxEnergyStored();
}
