package net.mofusya.mechanical_ageing.handler;

import com.google.common.collect.ImmutableList;
import net.kairayk.yklibrary.util.scrpos.IScrPos;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Capability handler for simple, multi layered double units.
 */
@EverythingAreNonNullByDefault
public class SimpleUnitHandler implements IUnitHandler<Double> {
    /**
     * A field for storing the slot count this haves.
     */
    private final int slotCount;

    /**
     * The fields for storing the max capacity, receiving limit, and extract limit.
     */
    private final ImmutableList<Double> capacity;
    private final ImmutableList<Double> maxReceive;
    private final ImmutableList<Double> maxExtract;

    /**
     * A field for storing the actual amounts.
     */
    private List<Double> stored;

    /**
     * The root constructor,
     *
     * @param settings The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     */
    public SimpleUnitHandler(Collection<TripleEntry<Double>> settings) {
        this.slotCount = settings.size();

        var capacityBuilder = ImmutableList.<Double>builder();
        var maxReceiveBuilder = ImmutableList.<Double>builder();
        var maxExtractBuilder = ImmutableList.<Double>builder();
        for (TripleEntry<Double> setting : settings) {
            capacityBuilder.add(setting.first());
            maxReceiveBuilder.add(setting.second());
            maxExtractBuilder.add(setting.third());
        }

        this.capacity = capacityBuilder.build();
        this.maxReceive = maxReceiveBuilder.build();
        this.maxExtract = maxExtractBuilder.build();

        this.stored = new ArrayList<>(this.getSlotCount());
        for (int i = 0; i < this.getSlotCount(); i++) {
            this.stored.set(i, 0d);
        }
    }

    /**
     * A getter for the {@code slotCount}.
     * @return This {@code slotCount}.
     */
    @Override
    public int getSlotCount() {
        return slotCount;
    }

    /**
     * An indexed getter for the {@code stored}.
     * @param slot The index to get the stored amount.
     * @return The stored amount from {@code stored} in index {@code slot}.
     */
    @Override
    public Double getStored(int slot) {
        return this.stored.get(slot);
    }

    /**
     * An indexed setter for the {@code stored}.
     * @param amount The amount to set to.
     * @param slot The index to set.
     */
    @Override
    public void setStored(Double amount, int slot) {
        this.stored.set(slot, amount);
    }


    /**
     * An indexed getter for the {@code capacity}.
     * @param slot The index to get from.
     * @return The max capacity from {@code capacity} in index {@code slot}.
     */
    @Override
    public Double getCapacity(int slot) {
        return this.capacity.get(slot);
    }


    /**
     * A method to get the leftover space of this tank/storage from the index {@code slot}.
     * @param slot The index to get the leftover space from.
     * @return The leftover space from index {@code slot}.
     */
    @Override
    public Double getSpace(int slot) {
        return this.getCapacity(slot) - this.getStored(slot);
    }

    /**
     * An indexed getter for the {@code maxReceive}.
     * @param slot The index to get from.
     * @return The max receiving limit from {@code maxReceive} in index {@code slot}.
     */
    @Override
    public Double getMaxReceive(int slot) {
        return this.maxReceive.get(slot);
    }

    /**
     * An indexed getter for the {@code maxExtract}.
     * @param slot The index to get from.
     * @return The max extracting limit from {@code maxExtract} in index {@code slot}.
     */
    @Override
    public Double getMaxExtract(int slot) {
        return this.maxExtract.get(slot);
    }

    /**
     * The method to get whether this can be received or not.
     * @param slot The index to check the max receive limit.
     * @return The boolean if this can be received or not from the {@code getMaxReceive(int)}'s amount.
     */
    @Override
    public boolean canReceive(int slot) {
        return this.getMaxReceive(slot) > 0;
    }

    /**
     * The method to get whether this can be extracted or not.
     * @param slot The index to check the max extract limit.
     * @return The boolean if this can be extracted or not from the {@code getMaxExtract(int)}'s amount.
     */
    @Override
    public boolean canExtract(int slot) {
        return this.getMaxExtract(slot) > 0;
    }

    /**
     * A method for receiving/adding amount from the outside. (Or the inside)
     *
     * @param amount            The amount you want this to receive.
     * @param simulated         When true, the method does not "really" receive any amounts, but it simulates the receiving.
     * @param forceLightReceive When true, the method ignores the max receiving limit.
     * @param slot              The slot that you want this to receive.
     * @return The amount that has been successfully received. (Or can be received when simulated)
     */
    @Override
    public Double receive(Double amount, boolean simulated, boolean forceLightReceive, int slot) {
        if (!this.canReceive(slot)) return 0d;
        return 0d;
    }


    /**
     * A method for extracting/deleting amount from the outside. (Or the inside)
     *
     * @param amount            The amount you want this to extract.
     * @param simulated         When true, the method does not "really" extract any amounts, but it simulates the extracting.
     * @param forceLightExtract When true, the method ignores the max extracting limit.
     * @param slot              The slot that you want this to extract.
     * @return The amount that has been successfully extracted. (Or can be extracted when simulated)
     */
    @Override
    public Double extract(Double amount, boolean simulated, boolean forceLightExtract, int slot) {
        return 0.0;
    }

    /**
     * A method for rendering this to the GUI.
     * @param guiGraphics   The gui rendering graphic.
     * @param pos           The position to render the bar.
     * @param slot          The index of {@code getMaxExtract} to render the bar.
     */
    @Override
    public void render(GuiGraphics guiGraphics, IScrPos<Integer> pos, int slot) {

    }

    /**
     * A method for converting this to a NBT tag.
     * @return The NBT serialized {@code CompoundTag}.
     */
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        ListTag storedTags = new ListTag();
        for (int slot = 0; slot < this.getSlotCount(); slot++) {
            storedTags.add(DoubleTag.valueOf(this.getStored(slot)));
        }
        tag.put(C.STORED, storedTags);

        return tag;
    }

    /**
     * A method for reconstructing this from a {@code CompoundTag}/NBT.
     * @param nbt The NBT tag to reconstruct this from.
     */
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag storedTags = nbt.getList(C.STORED, Tag.TAG_DOUBLE);
        for (int slot = 0; slot < this.getSlotCount(); slot++) {
            this.setStored(storedTags.getDouble(slot), slot);
        }
    }
}
