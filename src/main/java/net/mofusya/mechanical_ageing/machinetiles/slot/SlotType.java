package net.mofusya.mechanical_ageing.machinetiles.slot;

public enum SlotType {
    SYSTEM, OUTPUT, NEUTRAL, INPUT;

    public boolean is(SlotType type){
        return this == type;
    }
}
