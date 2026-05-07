package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.matter.MatterManager;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.SeptiLong;

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
    public MatterStack receive(MatterStack amount, int slot, boolean simulate) {
        MatterSlotProperties matterSlot = this.slots.get(slot);

        if (amount.getType() == null && this.getStored(slot).getType() == null) return MatterStack.empty();

        if (amount.getType() != null && !matterSlot.isValidFunc().apply(amount.getType()))
            return MatterStack.empty();

        if (amount.getNoneTypeAmount().isGreaterThan(this.getMaxReceive(slot)))
            amount.setAmount(this.getMaxReceive(slot));
        if (amount.getNoneTypeAmount().isGreaterThan(this.getSpace(slot))) amount.setAmount(this.getSpace(slot));
        if (amount.getType() == null) amount.setType(this.getStored(slot).getType());

        if (simulate) return amount.copy();

        if (this.storage[slot].receive(amount, false)) {
            this.onChanged();
            return amount.copy();
        }
        return MatterStack.empty();
    }

    @Override
    public MatterStack extract(MatterStack amount, int slot, boolean simulate) {
        MatterSlotProperties matterSlot = this.slots.get(slot);

        if (amount.getType() == null && this.getStored(slot).getType() == null) return MatterStack.empty();

        if (amount.getNoneTypeAmount().isGreaterThan(this.getMaxExtract(slot)))
            amount.setAmount(this.getMaxExtract(slot));
        if (amount.getNoneTypeAmount().isGreaterThan(this.getStored(slot).getAmount()))
            amount.setAmount(this.getStored(slot).getAmount());
        if (amount.getType() == null) amount.setType(this.getStored(slot).getType());

        if (simulate) return amount.copy();

        if (this.storage[slot].extract(amount, false)) {
            this.onChanged();
            return amount.copy();
        }
        return MatterStack.empty();
    }

    public MatterStack receiveFromInside(MatterStack amount, int slot) {
        return this.receiveFromInside(amount, slot, false);
    }

    public MatterStack receiveFromInside(MatterStack amount, int slot, boolean simulate) {
        MatterSlotProperties matterSlot = this.slots.get(slot);

        if (amount.getType() != null && !matterSlot.isValidFunc().apply(amount.getType()))
            return MatterStack.empty();

        if (amount.getNoneTypeAmount().isGreaterThan(this.getSpace(slot))) amount.setAmount(this.getSpace(slot));

        if (simulate) return amount.copy();

        if (this.storage[slot].receive(amount, false)) {
            this.onChanged();
            return amount.copy();
        }
        return MatterStack.empty();
    }

    public MatterStack extractFromInside(MatterStack amount, int slot) {
        return this.extractFromInside(amount, slot, false);
    }

    public MatterStack extractFromInside(MatterStack amount, int slot, boolean simulate) {
        if (amount.getNoneTypeAmount().isGreaterThan(this.getStored(slot).getAmount()))
            amount.setAmount(this.getStored(slot).getAmount());

        if (simulate) return amount.copy();

        if (this.storage[slot].extract(amount, false)) {
            this.onChanged();
            return amount.copy();
        }
        return MatterStack.empty();
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

    protected MatterSlotList getSlots() {
        return this.slots;
    }

    public abstract void onChanged();
}
