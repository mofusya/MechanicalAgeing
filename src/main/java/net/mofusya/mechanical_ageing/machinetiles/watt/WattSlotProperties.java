package net.mofusya.mechanical_ageing.machinetiles.watt;

import net.mofusya.ornatelib.lang.SeptiLong;
import org.jetbrains.annotations.Nullable;

public record WattSlotProperties(int x, SeptiLong stored, SeptiLong capacity, @Nullable SeptiLong maxReceive, @Nullable SeptiLong maxExtract) {
}
