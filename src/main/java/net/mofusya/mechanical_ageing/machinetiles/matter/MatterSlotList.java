package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.ArrayList;
import java.util.function.Function;

public class MatterSlotList extends ArrayList<MatterSlotProperties> {

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isValidFunc, SeptiLong capability) {
        return this.create(x, y, isValidFunc, capability, capability, capability);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isValidFunc,
                                 SeptiLong capability, SeptiLong maxTransfer) {
        return this.create(x, y, isValidFunc, capability, maxTransfer, maxTransfer);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isValidFunc,
                                 SeptiLong capability, SeptiLong maxReceive, SeptiLong maxExtract) {
        this.add(new MatterSlotProperties(x, y, isValidFunc, capability, maxReceive, maxExtract));
        return this;
    }
}
