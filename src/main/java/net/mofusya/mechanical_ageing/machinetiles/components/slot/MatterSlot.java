package net.mofusya.mechanical_ageing.machinetiles.components.slot;

import net.kairayk.yklibrary.util.scrpos.ScrPosI;
import net.mofusya.mechanical_ageing.matter.IMatterStack;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

import java.util.function.Predicate;

@EverythingAreNonNullByDefault
public record MatterSlot(ScrPosI pos, Predicate<IMatterStack> isValidFunc, TripleEntry<Double> settings,
                         SlotType type) {

    public static MatterSlot of(ScrPosI pos, Double capacity, Double maxReceive, Double maxExtract) {
        return of(pos, capacity, maxReceive, maxExtract, SlotType.NORMAL);
    }

    public static MatterSlot of(ScrPosI pos, Double capacity, Double maxReceive, Double maxExtract, SlotType type) {
        return of(pos, capacity, maxReceive, maxExtract, matterStack -> true, type);
    }

    public static MatterSlot of(ScrPosI pos, Double capacity, Double maxReceive, Double maxExtract, Predicate<IMatterStack> isValidFunc) {
        return of(pos, capacity, maxReceive, maxExtract, isValidFunc, SlotType.NORMAL);
    }

    public static MatterSlot of(ScrPosI pos, Double capacity, Double maxReceive, Double maxExtract, Predicate<IMatterStack> isValidFunc, SlotType type) {
        return of(pos, isValidFunc, new TripleEntry<>(capacity, maxReceive, maxExtract), type);
    }

    public static MatterSlot of(ScrPosI pos, TripleEntry<Double> settings) {
        return of(pos, settings, SlotType.NORMAL);
    }

    public static MatterSlot of(ScrPosI pos, TripleEntry<Double> settings, SlotType type) {
        return of(pos, matterStack -> true, settings, type);
    }

    public static MatterSlot of(ScrPosI pos, Predicate<IMatterStack> isValidFunc, TripleEntry<Double> settings) {
        return of(pos, isValidFunc, settings, SlotType.NORMAL);
    }

    public static MatterSlot of(ScrPosI pos, Predicate<IMatterStack> isValidFunc, TripleEntry<Double> settings, SlotType type) {
        return new MatterSlot(pos, isValidFunc, settings, type);
    }
}
