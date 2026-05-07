package net.mofusya.mechanical_ageing.machinetiles.matter;

import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.List;

public class LimitedMatterHandler implements IMatterHandler {
    private final MatterHandler handler;
    private final List<Integer> allowedSlots;

    public LimitedMatterHandler(MatterHandler handler, Integer... allowedSlots) {
        this(handler, List.of(allowedSlots));
    }

    public LimitedMatterHandler(MatterHandler handler, List<Integer> allowedSlots) {
        this.handler = handler;
        this.allowedSlots = allowedSlots;
    }

    @Override
    public MatterStack receive(MatterStack amount, int slot, boolean simulate) {
        if (!this.allowedSlots.contains(slot)) return MatterStack.empty();
        return this.handler.receive(amount, slot, simulate);
    }

    @Override
    public MatterStack extract(MatterStack amount, int slot, boolean simulate) {
        if (!this.allowedSlots.contains(slot)) return MatterStack.empty();
        return this.handler.extract(amount, slot, simulate);
    }

    public MatterStack receiveFromInside(MatterStack amount, int slot) {
        if (!this.allowedSlots.contains(slot)) return MatterStack.empty();
        return this.handler.receiveFromInside(amount, slot);
    }

    public MatterStack extractFromInside(MatterStack amount, int slot) {
        if (!this.allowedSlots.contains(slot)) return MatterStack.empty();
        return this.handler.extractFromInside(amount, slot);
    }

    @Override
    public MatterStack getStored(int slot) {
        if (!this.allowedSlots.contains(slot)) return MatterStack.empty();
        return this.handler.getStored(slot);
    }

    @Override
    public SeptiLong getMaxStored(int slot) {
        if (!this.allowedSlots.contains(slot)) return new SeptiLong();
        return this.handler.getMaxStored(slot);
    }

    @Override
    public boolean canReceive(int slot) {
        if (!this.allowedSlots.contains(slot)) return false;
        return this.handler.canReceive(slot);
    }

    @Override
    public boolean canExtract(int slot) {
        if (!this.allowedSlots.contains(slot)) return false;
        return this.handler.canExtract(slot);
    }

    public boolean canReceive(MatterStack amount, int slot) {
        if (!this.allowedSlots.contains(slot)) return false;
        return this.handler.canReceive(amount, slot);
    }

    public boolean canExtract(MatterStack amount, int slot) {
        if (!this.allowedSlots.contains(slot)) return false;
        return this.handler.canExtract(amount, slot);
    }

    public boolean canReceiveFromInside(MatterStack amount, int slot) {
        if (!this.allowedSlots.contains(slot)) return false;
        return this.handler.canReceiveFromInside(amount, slot);
    }

    public boolean canExtractFromInside(MatterStack amount, int slot) {
        if (!this.allowedSlots.contains(slot)) return false;
        return this.handler.canExtractFromInside(amount, slot);
    }

    public boolean setStored(MatterStack matterStack, int slot) {
        if (!this.allowedSlots.contains(slot)) return false;
        return this.handler.setStored(matterStack, slot);
    }

    public SeptiLong getMaxReceive(int slot) {
        if (!this.allowedSlots.contains(slot)) return new SeptiLong();
        return this.handler.getMaxReceive(slot);
    }

    public SeptiLong getMaxExtract(int slot) {
        if (!this.allowedSlots.contains(slot)) return new SeptiLong();
        return this.handler.getMaxExtract(slot);
    }

    public int size() {
        return this.handler.size();
    }

    protected MatterSlotList getSlots() {
        return this.handler.getSlots();
    }
}
