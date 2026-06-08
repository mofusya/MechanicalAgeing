package net.mofusya.mechanical_ageing.machinetiles.watt;

import net.mofusya.ornatelib.lang.SeptiLong;
import org.jetbrains.annotations.Nullable;

public record WattSlotProperties(int x, SeptiLong capacity, @Nullable SeptiLong maxReceive, @Nullable SeptiLong maxExtract, SeptiLong stored) {

    public WattSlotProperties(int x, SeptiLong capacity) {
        this(x, capacity, capacity, capacity, new SeptiLong());
    }

    public WattSlotProperties(int x, SeptiLong capacity, @Nullable SeptiLong maxTransfer) {
        this(x, capacity, maxTransfer, maxTransfer, new SeptiLong());
    }

    public WattSlotProperties(int x, SeptiLong capacity, @Nullable SeptiLong maxReceive, @Nullable SeptiLong maxExtract) {
        this(x, capacity, maxReceive, maxExtract, new SeptiLong());
    }

    public WattSlotProperties(int x, SeptiLong capacity, @Nullable SeptiLong maxReceive, @Nullable SeptiLong maxExtract, SeptiLong stored) {
        this.x = x;
        this.capacity = capacity.copy();
        this.maxReceive = maxReceive == null ? null : maxReceive.copy();
        this.maxExtract = maxExtract == null ? null : maxExtract.copy();
        this.stored = stored.copy();
    }
}