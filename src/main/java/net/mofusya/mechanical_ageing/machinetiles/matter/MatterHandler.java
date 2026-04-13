package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.matter.MatterManager;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;

public abstract class MatterHandler implements IMatterHandler {
    private final MatterStack[] storage;
    private final MatterSlotList slots;

    public MatterHandler(MatterSlotList slots) {
        this.storage = new MatterStack[slots.size()];
        for (int i = 0; i < slots.size(); i++) {
            this.storage[i] = new MatterStack(null);
        }
        this.slots = slots;
    }

    @Override
    public SeptiLong receive(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        SeptiLong receiveAmount = amount.getAmount();

        if (amount.getType() != null && !matterSlot.isValidFunc().apply(amount.getType()))
            return SeptiLongValue.ZERO.get();

        if (receiveAmount.isGreaterThan(this.getMaxReceive(slot))) receiveAmount.set(this.getMaxReceive(slot));
        if (receiveAmount.isGreaterThan(this.getSpace(slot))) receiveAmount.set(this.getSpace(slot));

        if (this.storage[slot].receive(new MatterStack(amount.getType(), receiveAmount), false)) {
            this.onChanged();
            return receiveAmount;
        }
        return SeptiLongValue.ZERO.get();
    }

    @Override
    public SeptiLong extract(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        SeptiLong extractAmount = amount.getAmount();

        if (amount.getType() != null && matterSlot.isValidFunc().apply(amount.getType()))
            return SeptiLongValue.ZERO.get();

        if (extractAmount.isGreaterThan(this.getMaxExtract(slot))) extractAmount.set(this.getMaxExtract(slot));
        if (extractAmount.isGreaterThan(this.getStored(slot).getAmount()))
            extractAmount.set(this.getStored(slot).getAmount());

        if (this.storage[slot].extract(new MatterStack(amount.getType(), extractAmount), false)) {
            this.onChanged();
            return extractAmount;
        }
        return SeptiLongValue.ZERO.get();
    }

    public SeptiLong receiveFromInside(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        SeptiLong receiveAmount = amount.getAmount();

        if (amount.getType() != null && !matterSlot.isValidFunc().apply(amount.getType()))
            return SeptiLongValue.ZERO.get();

        if (receiveAmount.isGreaterThan(this.getSpace(slot))) receiveAmount.set(this.getSpace(slot));

        if (this.storage[slot].receive(new MatterStack(amount.getType(), receiveAmount), false)) {
            this.onChanged();
            return receiveAmount;
        }
        return SeptiLongValue.ZERO.get();
    }

    public SeptiLong extractFromInside(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        SeptiLong extractAmount = amount.getAmount();

        if (amount.getType() != null && matterSlot.isValidFunc().apply(amount.getType()))
            return SeptiLongValue.ZERO.get();

        if (extractAmount.isGreaterThan(this.getStored(slot).getAmount()))
            extractAmount.set(this.getStored(slot).getAmount());

        if (this.storage[slot].extract(new MatterStack(amount.getType(), extractAmount), false)) {
            this.onChanged();
            return extractAmount;
        }
        return SeptiLongValue.ZERO.get();
    }

    @Override
    public MatterStack getStored(int slot) {
        return this.storage[slot].copy();
    }

    @Override
    public SeptiLong getMaxStored(int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        return matterSlot.capacity().copy();
    }

    @Override
    public boolean canReceive(int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        return matterSlot.maxReceive().isGreaterThan(0);
    }

    @Override
    public boolean canExtract(int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        return matterSlot.maxExtract().isGreaterThan(0);
    }

    public boolean canReceive(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        SeptiLong receiveAmount = amount.getAmount();

        if (!this.canReceive(slot)) return false;
        if (amount.getType() != null && !matterSlot.isValidFunc().apply(amount.getType())) return false;
        if (!receiveAmount.isGreaterThan(this.getMaxReceive(slot))) return false;
        if (receiveAmount.isGreaterThan(this.getSpace(slot))) return false;

        return true;
    }

    public boolean canExtract(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        SeptiLong extractAmount = amount.getAmount();

        if (!this.canExtract(slot)) return false;
        if (amount.getType() != null && !matterSlot.isValidFunc().apply(amount.getType())) return false;
        if (!extractAmount.isGreaterThan(this.getMaxExtract(slot))) return false;
        if (extractAmount.isGreaterThan(this.getStored(slot).getAmount())) return false;

        return true;
    }

    public boolean canReceiveFromInside(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        SeptiLong receiveAmount = amount.getAmount();

        if (amount.getType() != null && !matterSlot.isValidFunc().apply(amount.getType())) return false;
        if (receiveAmount.isGreaterThan(this.getSpace(slot))) return false;

        return true;
    }

    public boolean canExtractFromInside(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        SeptiLong extractAmount = amount.getAmount();

        if (amount.getType() != null && !matterSlot.isValidFunc().apply(amount.getType())) return false;
        if (extractAmount.isGreaterThan(this.getStored(slot).getAmount())) return false;

        return true;
    }

    public boolean setStored(MatterStack matterStack, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        MatterStack matterStorage = this.storage[slot];
        if (matterStack.getType() != null && matterSlot.isValidFunc().apply(matterStack.getType())) return false;
        SeptiLong amount = matterStack.getAmount();

        if (amount.isGreaterThan(this.getSpace(slot))) amount.set(this.getSpace(slot));

        matterStorage.set(matterStack.getType(), amount);
        this.onChanged();
        return true;
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        for (int i = 0; i < this.storage.length; i++) {
            MatterStack storage = this.storage[i];
            tag.putString("matterStorageType_" + i, storage.getType() == null ? "404" : storage.getType().getId().toString());
            tag.putLongArray("matterStorageAmount_" + i, storage.getAmount().getLayer());
        }
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        for (int i = 0; i < this.storage.length; i++) {
            this.storage[i] = new MatterStack(tag.getString("matterStorageType_" + i).equals("404") ? null : MatterManager.get().get(new ResourceLocation(tag.getString("matterStorageType_" + i))), SeptiLong.createFromList(tag.getLongArray("matterStorageAmount_" + i)));
        }
    }

    public SeptiLong getMaxReceive(int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        return matterSlot.maxReceive().copy();
    }

    public SeptiLong getMaxExtract(int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        return matterSlot.maxExtract().copy();
    }

    public int size() {
        return this.storage.length;
    }

    public abstract void onChanged();
}
