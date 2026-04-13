package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.function.Function;

public record MatterSlotProperties(int x, int y, Function<MatterType, Boolean> isValidFunc,
                                   SeptiLong capacity, SeptiLong maxReceive, SeptiLong maxExtract) {
}
