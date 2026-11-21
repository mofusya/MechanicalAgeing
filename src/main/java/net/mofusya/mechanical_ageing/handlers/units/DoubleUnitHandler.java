package net.mofusya.mechanical_ageing.handlers.units;

import com.google.common.collect.ImmutableList;
import net.kairayk.yklibrary.util.VectorColor;
import net.kairayk.yklibrary.util.scrpos.IScrPos;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.handlers.IUnitHandler;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import net.mofusya.ornatelib.util.function.Modification;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Capability handler for simple, multi layered double units.
 */
public interface DoubleUnitHandler extends IUnitHandler<Double> {

    /**
     * A helper method for converting the lists for the {@code capacity}, {@code maxReceive}, {@code maxExtract} to a {@code Collection} of {@code TripleEntry}.
     *
     * @param capacity   The settings for the max capacity.
     * @param maxReceive The settings for the max receiving limit.
     * @param maxExtract The settings for the max extracting limit,
     * @return The converted {@code Collection<TripleEntry<Double>>}
     */
    static Collection<TripleEntry<Double>> convertListsToSettings(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract) {
        List<TripleEntry<Double>> settings = new ArrayList<>();

        for (int i = 0; i < capacity.size(); i++) {
            settings.add(new TripleEntry<>(capacity.get(i), maxReceive.get(i), maxExtract.get(i)));
        }
        return settings;
    }

    /**
     * A getter for the {@code allowedSlots}.
     */
    @Nullable
    ImmutableList<Integer> getAllowedSlots();

    /**
     * A abstract method for setting/getting the color for the rendering bar.
     */
    VectorColor getColor(int slot);

    /**
     * A method to check if the {@code slot}'s index is allowed to access.
     *
     * @param slot The index to check.
     * @return If the {@code slot}'s index is allowed.
     */
    default boolean isAllowed(int slot) {
        if (this.getAllowedSlots() == null) return true;
        return this.getAllowedSlots().contains(slot);
    }

    /**
     * A method to check if the {@code slot}'s index is denied to access.
     *
     * @param slot The index to check.
     * @return If the {@code slot}'s index is denied.
     */
    default boolean isDenied(int slot) {
        return !this.isAllowed(slot);
    }

    /**
     * An indexed modifier for the {@code stored}.
     *
     * @param modification The modification func to apply.
     * @param slot         The index to modify.
     */
    @Override
    @Deprecated
    default void modStored(Modification<Double> modification, int slot) {
        this.setStored(modification.apply(this.getStored(slot)), slot);
    }

    /**
     * A method to get the leftover space of this tank/storage from the index {@code slot}.
     *
     * @param slot The index to get the leftover space from.
     * @return The leftover space from index {@code slot}.
     */
    @Override
    default Double getSpace(int slot) {
        if (this.isDenied(slot)) return 0d;
        return this.getCapacity(slot) - this.getStored(slot);
    }

    /**
     * The method to get whether this can be received or not.
     *
     * @param slot The index to check the max receive limit.
     * @return The boolean if this can be received or not from the {@code getMaxReceive(int)}'s amount.
     */
    @Override
    default boolean canReceive(int slot) {
        return this.getMaxReceive(slot) > 0f;
    }

    /**
     * The method to get whether this can be extracted or not.
     *
     * @param slot The index to check the max extract limit.
     * @return The boolean if this can be extracted or not from the {@code getMaxExtract(int)}'s amount.
     */
    @Override
    default boolean canExtract(int slot) {
        return this.getMaxExtract(slot) > 0f;
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
    default Double receive(Double amount, boolean simulated, boolean forceLightReceive, int slot) {
        if (this.isDenied(slot)) return 0d;
        if (!this.canReceive(slot) && !forceLightReceive) return 0d;

        if (this.getMaxReceive(slot) < amount && !forceLightReceive) amount = this.getMaxReceive(slot);
        if (this.getSpace(slot) < amount) amount = this.getSpace(slot);

        double finalAmount = amount;
        if (!simulated) {
            this.modStored(s -> s + finalAmount, slot);
        }
        return finalAmount;
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
    default Double extract(Double amount, boolean simulated, boolean forceLightExtract, int slot) {
        if (this.isDenied(slot)) return 0d;
        if (!this.canExtract(slot) && !forceLightExtract) return 0d;

        if (this.getMaxExtract(slot) < amount && !forceLightExtract) amount = this.getMaxExtract(slot);
        if (this.getStored(slot) < amount) amount = this.getStored(slot);

        double finalAmount = amount;
        if (!simulated) {
            this.modStored(s -> s + finalAmount, slot);
        }
        return finalAmount;
    }

    /**
     * A method for rendering this to the GUI.
     *
     * @param guiGraphics The gui rendering graphic.
     * @param pos         The position to render the bar.
     * @param slot        The index of {@code getMaxExtract} to render the bar.
     */
    @Override
    default void render(GuiGraphics guiGraphics, IScrPos<Integer> pos, int slot) {
    }

    /**
     * A method for converting this to a NBT tag.
     *
     * @return The NBT serialized {@code CompoundTag}.
     */
    @Override
    default CompoundTag serializeNBT() {
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
     *
     * @param tag The NBT tag to reconstruct this from.
     */
    @Override
    default void deserializeNBT(CompoundTag tag) {
        ListTag storedTags = tag.getList(C.STORED, Tag.TAG_DOUBLE);
        for (int slot = 0; slot < this.getSlotCount(); slot++) {
            this.setStored(storedTags.getDouble(slot), slot);
        }
    }
}
