package net.mofusya.mechanical_ageing.handler;

import net.kairayk.yklibrary.util.scrpos.IScrPos;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import net.mofusya.ornatelib.util.function.Modification;

/**
 * A Capability handler for a multi layered units.
 */
@AutoRegisterCapability
public interface IUnitHandler<T> extends INBTSerializable<CompoundTag> {

    /**
     * A getter for the {@code slotCount}.
     *
     * @return This {@code slotCount}.
     */
    int getSlotCount();

    /**
     * An indexed getter for the {@code stored}.
     *
     * @param slot The index to get the stored amount.
     * @return The stored amount from {@code stored} in index {@code slot}.
     */
    T getStored(int slot);

    /**
     * An indexed setter for the {@code stored}.
     *
     * @param amount The amount to set to.
     * @param slot   The index to set.
     */
    void setStored(T amount, int slot);

    /**
     * An indexed modifier for the {@code stored}.
     *
     * @param modification The modification func to apply.
     * @param slot         The index to modify.
     */
    void modStored(Modification<T> modification, int slot);

    /**
     * An indexed getter for the {@code capacity}.
     *
     * @param slot The index to get from.
     * @return The max capacity from {@code capacity} in index {@code slot}.
     */
    T getCapacity(int slot);

    /**
     * A method to get the leftover space of this tank/storage from the index {@code slot}.
     *
     * @param slot The index to get the leftover space from.
     * @return The leftover space from index {@code slot}.
     */
    T getSpace(int slot);

    /**
     * An indexed getter for the {@code maxReceive}.
     *
     * @param slot The index to get from.
     * @return The max receiving limit from {@code maxReceive} in index {@code slot}.
     */
    T getMaxReceive(int slot);

    /**
     * An indexed getter for the {@code maxExtract}.
     *
     * @param slot The index to get from.
     * @return The max extracting limit from {@code maxExtract} in index {@code slot}.
     */
    T getMaxExtract(int slot);

    /**
     * The method to get whether this can be received or not.
     *
     * @param slot The index to check the max receive limit.
     * @return The boolean if this can be received or not from the {@code getMaxReceive(int)}'s amount.
     */
    boolean canReceive(int slot);

    /**
     * The method to get whether this can be extracted or not.
     *
     * @param slot The index to check the max extract limit.
     * @return The boolean if this can be extracted or not from the {@code getMaxExtract(int)}'s amount.
     */
    boolean canExtract(int slot);

    /**
     * A method for receiving/adding amount from the outside. (Or the inside)
     *
     * @param amount            The amount you want this to receive.
     * @param simulated         When true, the method does not "really" receive any amounts, but it simulates the receiving.
     * @param forceLightReceive When true, the method ignores the max receiving limit.
     * @param slot              The slot that you want this to receive.
     * @return The amount that has been successfully received. (Or can be received when simulated)
     */
    T receive(T amount, boolean simulated, boolean forceLightReceive, int slot);

    /**
     * A method for extracting/deleting amount from the outside. (Or the inside)
     *
     * @param amount            The amount you want this to extract.
     * @param simulated         When true, the method does not "really" extract any amounts, but it simulates the extracting.
     * @param forceLightExtract When true, the method ignores the max extracting limit.
     * @param slot              The slot that you want this to extract.
     * @return The amount that has been successfully extracted. (Or can be extracted when simulated)
     */
    T extract(T amount, boolean simulated, boolean forceLightExtract, int slot);

    /**
     * A method for rendering this to the GUI.
     *
     * @param guiGraphics The gui rendering graphic.
     * @param pos         The position to render the bar.
     * @param slot        The index of {@code getMaxExtract} to render the bar.
     */
    void render(GuiGraphics guiGraphics, IScrPos<Integer> pos, int slot);

    /**
     * A method for converting this to a NBT tag.
     *
     * @return The NBT serialized {@code CompoundTag}.
     */
    @Override
    CompoundTag serializeNBT();

    /**
     * A method for reconstructing this from a {@code CompoundTag}/NBT.
     *
     * @param tag The NBT tag to reconstruct this from.
     */
    @Override
    void deserializeNBT(CompoundTag tag);

    /**
     * A method fired when this was changed.
     */
    void onChanged();
}
