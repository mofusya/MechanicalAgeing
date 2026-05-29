package net.mofusya.mechanical_ageing.tiles.energy;

import net.minecraft.nbt.CompoundTag;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import org.jetbrains.annotations.Nullable;

public abstract class WattEnergyStorage {
    protected final SeptiLong stored;
    protected final SeptiLong capacity;
    protected final @Nullable SeptiLong maxReceive;
    protected final @Nullable SeptiLong maxExtract;

    public WattEnergyStorage(SeptiLong stored, SeptiLong capacity) {
        this(stored, capacity, capacity, capacity);
    }

    public WattEnergyStorage(SeptiLong stored, SeptiLong capacity, @Nullable SeptiLong maxTransfer) {
        this(stored, capacity, maxTransfer, maxTransfer);
    }

    public WattEnergyStorage(SeptiLong stored, SeptiLong capacity, @Nullable SeptiLong maxReceive, @Nullable SeptiLong maxExtract) {
        this.stored = stored.copy();
        this.capacity = capacity.copy();
        this.maxReceive = maxReceive == null ? null : maxReceive.copy();
        this.maxExtract = maxExtract == null ? null : maxExtract.copy();
    }

    public SeptiLong receive(SeptiLong maxReceive, boolean simulate) {
        if (maxReceive.isSmallerThan(0) || !this.canReceive()) return new SeptiLong();

        SeptiLong receive = maxReceive.copy();
        if (receive.isGreaterThan(this.getSpace())) receive.set(this.getSpace());
        if (receive.isGreaterThan(this.getMaxReceive())) receive.set(this.getMaxReceive());

        if (!simulate) {
            this.stored.add(receive);
            this.onChanged();
        }

        return receive;
    }

    public SeptiLong extract(SeptiLong maxExtract, boolean simulate) {
        if (maxExtract.isSmallerThan(0) || !this.canExtract()) return new SeptiLong();

        SeptiLong extract = maxExtract.copy();
        if (extract.isGreaterThan(this.getStored())) extract.set(this.getStored());
        if (extract.isGreaterThan(this.getMaxExtract())) extract.set(this.getMaxExtract());

        if (!simulate) {
            this.stored.add(extract);
            this.onChanged();
        }

        return extract;
    }

    public SeptiLong receiveFromInside(SeptiLong maxReceive, boolean simulate) {
        if (maxReceive.isSmallerThan(0)) return new SeptiLong();

        SeptiLong receive = maxReceive.copy();
        if (receive.isGreaterThan(this.getSpace())) receive.set(this.getSpace());

        if (!simulate) {
            this.stored.add(receive);
            this.onChanged();
        }

        return receive;
    }

    public SeptiLong extractFromInside(SeptiLong maxExtract, boolean simulate) {
        if (maxExtract.isSmallerThan(0)) return new SeptiLong();

        SeptiLong extract = maxExtract.copy();
        if (extract.isGreaterThan(this.getStored())) extract.set(this.getStored());

        if (!simulate) {
            this.stored.add(extract);
            this.onChanged();
        }

        return extract;
    }

    public SeptiLong getMaxReceive() {
        if (this.maxReceive == null) return new SeptiLong();
        return this.maxReceive.copy();
    }

    public SeptiLong getMaxExtract() {
        if (this.maxExtract == null) return new SeptiLong();
        return this.maxExtract.copy();
    }

    public boolean canReceive() {
        return this.getMaxReceive().isGreaterThan(0);
    }

    public boolean canExtract() {
        return this.getMaxExtract().isGreaterThan(0);
    }

    public boolean canReceive(SeptiLong receive) {
        return receive.is(SeptiLongValue.ZERO.get()) || (!receive.isGreaterThan(this.getSpace()) && !receive.isGreaterThan(this.getMaxReceive()));
    }

    public boolean canExtract(SeptiLong extract) {
        return extract.is(SeptiLongValue.ZERO.get()) || (!extract.isGreaterThan(this.getStored()) && !extract.isGreaterThan(this.getMaxExtract()));
    }

    public boolean canReceiveFromInside(SeptiLong receive) {
        return receive.is(SeptiLongValue.ZERO.get()) || !receive.isGreaterThan(this.getSpace());
    }

    public boolean canExtractFromInside(SeptiLong extract) {
        return extract.is(SeptiLongValue.ZERO.get()) || !extract.isGreaterThan(this.getStored());
    }

    public void setStored(SeptiLong stored) {
        this.stored.set(stored);
        this.onChanged();
    }

    public SeptiLong getStored() {
        return this.stored.copy();
    }

    public SeptiLong getCapacity() {
        return this.capacity.copy();
    }

    public SeptiLong getSpace() {
        return this.getCapacity().remove(this.getStored());
    }

    public abstract void onChanged();
}