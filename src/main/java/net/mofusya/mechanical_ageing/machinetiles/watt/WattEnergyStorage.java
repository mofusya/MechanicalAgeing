package net.mofusya.mechanical_ageing.machinetiles.watt;

import net.minecraft.nbt.CompoundTag;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import net.mofusya.ornatelib.lang.UnLong;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WattEnergyStorage implements IWattEnergyStorage {
    protected final UnLong stored;
    protected final UnLong capacity;
    protected final @Nullable UnLong maxReceive;
    protected final @Nullable UnLong maxExtract;

    protected final Runnable changeFunc;

    public WattEnergyStorage(UnLong capacity, Runnable changeFunc) {
        this(capacity, UnLong.zero(), capacity, capacity, changeFunc);
    }

    public WattEnergyStorage(UnLong capacity, UnLong stored, Runnable changeFunc) {
        this(capacity, stored, capacity, capacity, changeFunc);
    }

    public WattEnergyStorage(UnLong capacity, UnLong stored, @Nullable UnLong maxTransfer, Runnable changeFunc) {
        this(capacity, stored, maxTransfer, maxTransfer, changeFunc);
    }

    public WattEnergyStorage(UnLong capacity, UnLong stored, @Nullable UnLong maxReceive, @Nullable UnLong maxExtract, Runnable changeFunc) {
        this.capacity = capacity.copy();
        this.stored = stored.copy();
        this.maxReceive = maxReceive == null ? null : maxReceive.copy();
        this.maxExtract = maxExtract == null ? null : maxExtract.copy();
        this.changeFunc = changeFunc;
    }

    public UnLong receive(UnLong maxReceive, boolean simulate) {
        if (maxReceive.isSmallerThan(0) || !this.canReceive()) return UnLong.zero();

        UnLong receive = maxReceive.copy();
        if (receive.isGreaterThan(this.getSpace())) receive.setTo(this.getSpace());
        if (receive.isGreaterThan(this.getMaxReceive())) receive.setTo(this.getMaxReceive());

        if (!simulate) {
            this.stored.add(receive);
            this.onChanged();
        }

        return receive;
    }

    public UnLong extract(UnLong maxExtract, boolean simulate) {
        if (maxExtract.isSmallerThan(0) || !this.canExtract()) return UnLong.zero();

        UnLong extract = maxExtract.copy();
        if (extract.isGreaterThan(this.getStored())) extract.setTo(this.getStored());
        if (extract.isGreaterThan(this.getMaxExtract())) extract.setTo(this.getMaxExtract());

        if (!simulate) {
            this.stored.add(extract);
            this.onChanged();
        }

        return extract;
    }

    public UnLong receiveFromInside(UnLong maxReceive, boolean simulate) {
        if (maxReceive.isSmallerThan(0)) return UnLong.zero();

        UnLong receive = maxReceive.copy();
        if (receive.isGreaterThan(this.getSpace())) receive.setTo(this.getSpace());

        if (!simulate) {
            this.stored.add(receive);
            this.onChanged();
        }

        return receive;
    }

    public UnLong extractFromInside(UnLong maxExtract, boolean simulate) {
        if (maxExtract.isSmallerThan(0)) return UnLong.zero();

        UnLong extract = maxExtract.copy();
        if (extract.isGreaterThan(this.getStored())) extract.setTo(this.getStored());

        if (!simulate) {
            this.stored.add(extract);
            this.onChanged();
        }

        return extract;
    }

    public UnLong getMaxReceive() {
        if (this.maxReceive == null) return UnLong.zero();
        return this.maxReceive.copy();
    }

    public UnLong getMaxExtract() {
        if (this.maxExtract == null) return UnLong.zero();
        return this.maxExtract.copy();
    }

    public boolean canReceive() {
        return this.getMaxReceive().isGreaterThan(0);
    }

    public boolean canExtract() {
        return this.getMaxExtract().isGreaterThan(0);
    }

    public boolean canReceive(UnLong receive) {
        return receive.equals(UnLong.zero()) || (!receive.isGreaterThan(this.getSpace()) && !receive.isGreaterThan(this.getMaxReceive()));
    }

    public boolean canExtract(UnLong extract) {
        return extract.equals(UnLong.zero()) || (!extract.isGreaterThan(this.getStored()) && !extract.isGreaterThan(this.getMaxExtract()));
    }

    public boolean canReceiveFromInside(UnLong receive) {
        return receive.equals(UnLong.zero()) || !receive.isGreaterThan(this.getSpace());
    }

    public boolean canExtractFromInside(UnLong extract) {
        return extract.equals(UnLong.zero()) || !extract.isGreaterThan(this.getStored());
    }

    public void setStored(UnLong stored) {
        this.stored.setTo(stored);
        this.onChanged();
    }

    public UnLong getStored() {
        return this.stored.copy();
    }

    public UnLong getCapacity() {
        return this.capacity.copy();
    }

    public UnLong getSpace() {
        return this.getCapacity().min(this.getStored()).copy();
    }

    public void serializeNBT(CompoundTag tag){
        tag.putLongArray("wattEnergyStorage", this.getStored().getValues());
    }

    public void deSerializeNBT(CompoundTag tag){
        this.stored.setTo(UnLong.createWithoutReverse(Arrays.stream(tag.getLongArray("wattEnergyStorage")).boxed().toList()));
    }

    public void onChanged(){
        this.changeFunc.run();
    }
}