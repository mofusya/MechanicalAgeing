package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.util.ArrayMap;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.ArrayList;
import java.util.function.Function;

public class MatterSlotList extends ArrayList<MatterSlotProperties> {

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, SeptiLong capability) {
        return this.create(x, y, isTypeValidFunc, matterTag -> matterTag.matches(new ArrayMap<>()), capability, capability, capability);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc,
                                 SeptiLong capability, SeptiLong maxTransfer) {
        return this.create(x, y, isTypeValidFunc, matterTag -> matterTag.matches(new ArrayMap<>()), capability, maxTransfer, maxTransfer);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc,
                                 SeptiLong capability, SeptiLong maxReceive, SeptiLong maxExtract) {
        return this.create(x, y, isTypeValidFunc, matterTag -> matterTag.matches(new ArrayMap<>()), capability, maxReceive, maxExtract);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, Function<ArrayMap<String, String>, Boolean> isTagValidFunc, SeptiLong capability) {
        return this.create(x, y, isTypeValidFunc, isTagValidFunc, capability, capability, capability);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, Function<ArrayMap<String, String>, Boolean> isTagValidFunc,
                                 SeptiLong capability, SeptiLong maxTransfer) {
        return this.create(x, y, isTypeValidFunc, isTagValidFunc, capability, maxTransfer, maxTransfer);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, Function<ArrayMap<String, String>, Boolean> isTagValidFunc,
                                 SeptiLong capability, SeptiLong maxReceive, SeptiLong maxExtract) {
        this.add(new MatterSlotProperties(x, y, isTypeValidFunc, isTagValidFunc, capability, maxReceive, maxExtract));
        return this;
    }
}
