package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.util.ArrayMap;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.function.Function;

public record MatterSlotProperties(int x, int y, Function<MatterType, Boolean> isTypeValidFunc, Function<ArrayMap<String, String>, Boolean> isTagValidFunc,
                                   SeptiLong capacity, SeptiLong maxReceive, SeptiLong maxExtract) {

    public boolean isValid(MatterType matterType, ArrayMap<String, String> matterTags){
        return this.isTypeValidFunc().apply(matterType) && this.isTagValidFunc().apply(matterTags);
    }
}
