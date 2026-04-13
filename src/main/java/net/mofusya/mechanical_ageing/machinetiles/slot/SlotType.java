package net.mofusya.mechanical_ageing.machinetiles.slot;

import org.stringtemplate.v4.ST;

public enum SlotType {
    SYSTEM, EXTRACT_ONLY, NORMAL;

    public boolean is(SlotType type){
        return this == type;
    }
}
