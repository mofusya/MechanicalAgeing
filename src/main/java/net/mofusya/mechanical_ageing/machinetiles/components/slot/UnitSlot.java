package net.mofusya.mechanical_ageing.machinetiles.components.slot;

import net.kairayk.yklibrary.util.scrpos.ScrPosI;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

@EverythingAreNonNullByDefault
public record UnitSlot(ScrPosI pos, TripleEntry<Double> settings, SlotType type) {

    public static UnitSlot of(ScrPosI pos, Double capacity, Double maxReceive, Double maxExtract) {
        return of(pos, capacity, maxReceive, maxExtract, SlotType.NORMAL);
    }

    public static UnitSlot of(ScrPosI pos, Double capacity, Double maxReceive, Double maxExtract, SlotType type) {
        return of(pos, new TripleEntry<>(capacity, maxReceive, maxExtract), type);
    }

    public static UnitSlot of(ScrPosI pos, TripleEntry<Double> settings) {
        return of(pos, settings, SlotType.NORMAL);
    }

    public static UnitSlot of(ScrPosI pos, TripleEntry<Double> settings, SlotType type){
        return new UnitSlot(pos, settings, type);
    }
}
