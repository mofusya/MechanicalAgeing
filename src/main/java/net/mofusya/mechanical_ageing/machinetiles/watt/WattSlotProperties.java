package net.mofusya.mechanical_ageing.machinetiles.watt;

import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.UnLong;
import org.jetbrains.annotations.Nullable;

public record WattSlotProperties(int x, UnLong capacity, @Nullable UnLong maxReceive, @Nullable UnLong maxExtract, UnLong stored) {

    public WattSlotProperties(int x, UnLong capacity) {
        this(x, capacity, capacity, capacity, UnLong.zero());
    }

    public WattSlotProperties(int x, UnLong capacity, @Nullable UnLong maxTransfer) {
        this(x, capacity, maxTransfer, maxTransfer, UnLong.zero());
    }

    public WattSlotProperties(int x, UnLong capacity, @Nullable UnLong maxReceive, @Nullable UnLong maxExtract) {
        this(x, capacity, maxReceive, maxExtract, UnLong.zero());
    }

    public WattSlotProperties(int x, UnLong capacity, @Nullable UnLong maxReceive, @Nullable UnLong maxExtract, UnLong stored) {
        this.x = x;
        this.capacity = capacity.copy();
        this.maxReceive = maxReceive == null ? null : maxReceive.copy();
        this.maxExtract = maxExtract == null ? null : maxExtract.copy();
        this.stored = stored.copy();
    }
}