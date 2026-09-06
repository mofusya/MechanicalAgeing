package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.matter.MatterManager;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.UnLong;

import java.util.Arrays;
import java.util.Map;

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
        MatterStack receiveAmount = amount.copy();

        if (receiveAmount.getType() == null && this.getStored(slot).getType() == null) return MatterStack.empty();

        if (receiveAmount.getType() != null && !matterSlot.isValid(receiveAmount.getType(), receiveAmount.getTags()))
            return MatterStack.empty();
        if (!MatterStack.checkTags(this.getStored(slot), receiveAmount)) return MatterStack.empty();

        if (receiveAmount.getNoneTypeAmount().isGreaterThan(this.getMaxReceive(slot)))
            receiveAmount.setAmount(this.getMaxReceive(slot));
        if (receiveAmount.getNoneTypeAmount().isGreaterThan(this.getSpace(slot)))
            receiveAmount.setAmount(this.getSpace(slot));
        if (receiveAmount.getType() == null) receiveAmount.setType(this.getStored(slot).getType());

        if (simulate) return receiveAmount.copy();

        if (this.storage[slot].receive(receiveAmount, false)) {
            this.onChanged();
            return receiveAmount.copy();
        }
        return MatterStack.empty();
    }

    @Override
    public MatterStack extract(MatterStack amount, int slot, boolean simulate) {
        MatterStack extractAmount = amount.copy();

        if (extractAmount.getType() == null && this.getStored(slot).getType() == null) return MatterStack.empty();
        if (!MatterStack.checkTags(this.getStored(slot), extractAmount)) return MatterStack.empty();

        if (extractAmount.getNoneTypeAmount().isGreaterThan(this.getMaxExtract(slot)))
            extractAmount.setAmount(this.getMaxExtract(slot));
        if (extractAmount.getNoneTypeAmount().isGreaterThan(this.getStored(slot).getAmount()))
            extractAmount.setAmount(this.getStored(slot).getAmount());
        if (extractAmount.getType() == null) extractAmount.setType(this.getStored(slot).getType());

        if (simulate) return extractAmount.copy();

        if (this.storage[slot].extract(extractAmount, false)) {
            this.onChanged();
            return extractAmount.copy();
        }
        return MatterStack.empty();
    }

    public MatterStack receiveFromInside(MatterStack amount, int slot) {
        return this.receiveFromInside(amount, slot, false);
    }

    public MatterStack receiveFromInside(MatterStack amount, int slot, boolean simulate) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        MatterStack receiveAmount = amount.copy();

        if (receiveAmount.getType() != null && !matterSlot.isValid(receiveAmount.getType(), receiveAmount.getTags()))
            return MatterStack.empty();
        if (!MatterStack.checkTags(this.getStored(slot), receiveAmount)) return MatterStack.empty();

        if (receiveAmount.getNoneTypeAmount().isGreaterThan(this.getSpace(slot)))
            receiveAmount.setAmount(this.getSpace(slot));

        if (simulate) return receiveAmount.copy();

        if (this.storage[slot].receive(receiveAmount, false)) {
            this.onChanged();
            return receiveAmount.copy();
        }
        return MatterStack.empty();
    }

    public MatterStack extractFromInside(MatterStack amount, int slot) {
        return this.extractFromInside(amount, slot, false);
    }

    public MatterStack extractFromInside(MatterStack amount, int slot, boolean simulate) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        MatterStack extractAmount = amount.copy();

        if (extractAmount.getType() != null && !matterSlot.isValid(extractAmount.getType(), extractAmount.getTags()))
            return MatterStack.empty();
        if (!MatterStack.checkTags(this.getStored(slot), extractAmount)) return MatterStack.empty();

        if (extractAmount.getNoneTypeAmount().isGreaterThan(this.getStored(slot).getAmount()))
            extractAmount.setAmount(this.getStored(slot).getAmount());

        if (simulate) return extractAmount.copy();

        if (this.storage[slot].extract(extractAmount, false)) {
            this.onChanged();
            return extractAmount.copy();
        }
        return MatterStack.empty();
    }

    @Override
    public MatterStack getStored(int slot) {
        return this.storage[slot].copy();
    }

    @Override
    public UnLong getMaxStored(int slot) {
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
        UnLong receiveAmount = amount.getAmount();

        if (!this.canReceive(slot)) return false;
        if (!MatterStack.checkTags(this.storage[slot], amount)) return false;

        if (amount.getType() != null && !matterSlot.isValid(amount.getType(), amount.getTags())) return false;
        if (!receiveAmount.isGreaterThan(this.getMaxReceive(slot))) return false;
        if (receiveAmount.isGreaterThan(this.getSpace(slot))) return false;

        return true;
    }

    public boolean canExtract(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        UnLong extractAmount = amount.getAmount();

        if (!this.canExtract(slot)) return false;
        if (!MatterStack.checkTags(this.storage[slot], amount)) return false;

        if (amount.getType() != null && !matterSlot.isValid(amount.getType(), amount.getTags())) return false;
        if (!extractAmount.isGreaterThan(this.getMaxExtract(slot))) return false;
        if (extractAmount.isGreaterThan(this.getStored(slot).getAmount())) return false;

        return true;
    }

    public boolean canReceiveFromInside(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        UnLong receiveAmount = amount.getAmount();

        if (!MatterStack.checkTags(this.storage[slot], amount)) return false;
        if (amount.getType() != null && !matterSlot.isValid(amount.getType(), amount.getTags())) return false;
        if (receiveAmount.isGreaterThan(this.getSpace(slot))) return false;

        return true;
    }

    public boolean canExtractFromInside(MatterStack amount, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        UnLong extractAmount = amount.getAmount();

        if (!MatterStack.checkTags(this.storage[slot], amount)) return false;
        if (amount.getType() != null && !matterSlot.isValid(amount.getType(), amount.getTags())) return false;
        if (extractAmount.isGreaterThan(this.getStored(slot).getAmount())) return false;

        return true;
    }

    public boolean setStored(MatterStack matterStack, int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        MatterStack matterStorage = this.storage[slot];
        if (matterStack.getType() != null && matterSlot.isValid(matterStack.getType(), matterStack.getTags()))
            return false;
        UnLong amount = matterStack.getAmount();

        if (amount.isGreaterThan(this.getSpace(slot))) amount.setTo(this.getSpace(slot));

        matterStorage.set(matterStack.getType(), amount);
        this.onChanged();
        return true;
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        for (int i = 0; i < this.storage.length; i++) {
            MatterStack storage = this.storage[i];
            if (storage.isEmpty()) continue;

            tag.putString("matterStorageType_" + i, storage.getType().getId().toString());
            tag.putLongArray("matterStorageAmount_" + i, storage.getAmount().getValues());
            CompoundTag matterStorageTags = new CompoundTag();
            for (int j = 0; j < storage.getTags().size(); j++) {
                matterStorageTags.putString("key_" + j, storage.getTags().getKeys().get(j));
                matterStorageTags.putString("value_" + j, storage.getTags().getValues().get(j));
            }
            tag.put("matterStorageTags_" + i, matterStorageTags);
        }
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        for (int i = 0; i < this.storage.length; i++) {
            if (!tag.contains("matterStorageType_" + i) || !tag.contains("matterStorageAmount_" + i) || !tag.contains("matterStorageTags_" + i))
                continue;

            Map<ResourceLocation, MatterType> matterManager = MatterManager.get();

            ResourceLocation typeId = new ResourceLocation(tag.getString("matterStorageType_" + i));
            long[] amount = tag.getLongArray("matterStorageAmount_" + i);
            MatterType type = matterManager.get(typeId);
            if (type == null) {
                continue;
            }

            MatterStack matterStack = new MatterStack(type, UnLong.createWithoutReverse(Arrays.stream(amount).boxed().toList()));
            CompoundTag matterStorageTags = tag.getCompound("matterStorageTags_" + i);
            for (int j = 0; matterStorageTags.contains("key_" + j) && matterStorageTags.contains("value_" + j); j++) {
                matterStack.getTags().put(matterStorageTags.getString("key_" + j), matterStorageTags.getString("value_" + j));
            }
            this.storage[i] = matterStack;
        }
    }

    public UnLong getMaxReceive(int slot) {
        MatterSlotProperties matterSlot = this.slots.get(slot);
        return matterSlot.maxReceive().copy();
    }

    public UnLong getMaxExtract(int slot) {
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
