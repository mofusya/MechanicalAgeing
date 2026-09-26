package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.ArrayMap;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.ArrayList;
import java.util.function.Function;

public class MatterSlotList extends ArrayList<MatterSlotProperties> {

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, UnLong capability) {
        return this.create(x, y, isTypeValidFunc, matterTag -> true, capability, capability, capability);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc,
                                 UnLong capability, UnLong maxTransfer) {
        return this.create(x, y, isTypeValidFunc, matterTag -> true, capability, maxTransfer, maxTransfer);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc,
                                 UnLong capability, UnLong maxReceive, UnLong maxExtract) {
        return this.create(x, y, isTypeValidFunc, matterTag -> true, capability, maxReceive, maxExtract);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, Function<ArrayMap<String, String>, Boolean> isTagValidFunc, UnLong capability) {
        return this.create(x, y, isTypeValidFunc, isTagValidFunc, capability, capability, capability);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, Function<ArrayMap<String, String>, Boolean> isTagValidFunc,
                                 UnLong capability, UnLong maxTransfer) {
        return this.create(x, y, isTypeValidFunc, isTagValidFunc, capability, maxTransfer, maxTransfer);
    }

    public MatterSlotList create(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, Function<ArrayMap<String, String>, Boolean> isTagValidFunc,
                                 UnLong capability, UnLong maxReceive, UnLong maxExtract) {
        this.add(new MatterSlotProperties(x, y, isTypeValidFunc, isTagValidFunc, capability, maxReceive, maxExtract));
        return this;
    }
}
