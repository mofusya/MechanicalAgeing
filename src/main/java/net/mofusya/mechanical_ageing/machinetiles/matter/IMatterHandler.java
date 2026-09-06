package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.UnLong;

@AutoRegisterCapability
public interface IMatterHandler {
    MatterStack receive(MatterStack amount, int slot, boolean simulate);

    MatterStack extract(MatterStack amount, int slot, boolean simulate);

    default MatterStack receive(MatterStack amount, int slot){
        return this.receive(amount, slot, false);
    }

    default MatterStack extract(MatterStack amount, int slot){
        return this.extract(amount, slot, false);
    }

    MatterStack getStored(int slot);

    UnLong getMaxStored(int slot);

    boolean canReceive(int slot);

    boolean canExtract(int slot);

    default UnLong getSpace(int slot) {
        return this.getMaxStored(slot).copy().min(this.getStored(slot).getAmount());
    }
}