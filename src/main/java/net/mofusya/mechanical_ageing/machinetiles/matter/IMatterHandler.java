package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.SeptiLong;

@AutoRegisterCapability
public interface IMatterHandler {
    SeptiLong receive(MatterStack amount, int slot);

    SeptiLong extract(MatterStack amount, int slot);

    MatterStack getStored(int slot);

    SeptiLong getMaxStored(int slot);

    boolean canReceive(int slot);

    boolean canExtract(int slot);

    default SeptiLong getSpace(int slot) {
        return this.getMaxStored(slot).copy().remove(this.getStored(slot).getAmount());
    }
}