package net.mofusya.mechanical_ageing.handler.units;

import com.google.common.collect.ImmutableList;
import net.kairayk.yklibrary.util.VectorColor;
import net.kairayk.yklibrary.util.scrpos.IScrPos;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.handler.IUnitHandler;
import net.mofusya.mechanical_ageing.util.ListUtil;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;
import net.mofusya.ornatelib.util.function.Modification;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Capability handler for simple, multi layered double units.
 */
@EverythingAreNonNullByDefault
public abstract class SimpleUnitHandler implements IUnitHandler<Double> {
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
     * The list of slot indexes that can be accessed or changed.
     * When null, every slot is treated as allowed.
     */
    @Nullable
    private final ImmutableList<Integer> allowedSlots;

    /**
     * The field for storing a function, fired when this was changed.
     */
    private final Runnable onChangeFunc;

    /**
     * A field for storing the actual amounts.
     */
    private final List<Double> stored;

    //region Constructors

    /**
     * An overdriven constructor,
     *
     * @param capacity     The max capacity of this.
     * @param maxReceive   The max receiving limit of this.
     * @param maxExtract   The max extracting limit of this.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public SimpleUnitHandler(Double capacity, Double maxReceive, Double maxExtract, Runnable onChangeFunc) {
        this(capacity, maxReceive, maxExtract, null, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param capacity     The max capacity of this.
     * @param maxReceive   The max receiving limit of this.
     * @param maxExtract   The max extracting limit of this.
     * @param allowedSlots The list of slot indexes that can be accessed or changed.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public SimpleUnitHandler(Double capacity, Double maxReceive, Double maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        this(1, capacity, maxReceive, maxExtract, allowedSlots, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param slotCount    The slot count of this.
     * @param capacity     The max capacity of this.
     * @param maxReceive   The max receiving limit of this.
     * @param maxExtract   The max extracting limit of this.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public SimpleUnitHandler(@Range(from = 1, to = Integer.MAX_VALUE) int slotCount, Double capacity, Double maxReceive, Double maxExtract, Runnable onChangeFunc) {
        this(slotCount, capacity, maxReceive, maxExtract, null, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param slotCount    The slot count of this.
     * @param capacity     The max capacity of this.
     * @param maxReceive   The max receiving limit of this.
     * @param maxExtract   The max extracting limit of this.
     * @param allowedSlots The list of slot indexes that can be accessed or changed.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public SimpleUnitHandler(@Range(from = 1, to = Integer.MAX_VALUE) int slotCount, Double capacity, Double maxReceive, Double maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        this(ListUtil.createFilledListFor(slotCount, () -> capacity), ListUtil.createFilledListFor(slotCount, () -> maxReceive), ListUtil.createFilledListFor(slotCount, () -> maxExtract), allowedSlots, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param capacity     The max capacity per slot of this.
     * @param maxReceive   The max receiving limit per slot of this.
     * @param maxExtract   The max extracting limit per slot of this.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public SimpleUnitHandler(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract, Runnable onChangeFunc) {
        this(capacity, maxReceive, maxExtract, null, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param capacity     The max capacity per slot of this.
     * @param maxReceive   The max receiving limit per slot of this.
     * @param maxExtract   The max extracting limit per slot of this.
     * @param allowedSlots The list of slot indexes that can be accessed or changed.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public SimpleUnitHandler(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        this(convertListsToSettings(capacity, maxReceive, maxExtract), allowedSlots, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param settings     The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     * @param onChangeFunc A function, fired when this was changed.
     */
    public SimpleUnitHandler(Collection<TripleEntry<Double>> settings, Runnable onChangeFunc) {
        this(settings, null, onChangeFunc);
    }

    /**
     * The root constructor,
     *
     * @param settings     The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     * @param allowedSlots The list of slot indexes that can be accessed or changed.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public SimpleUnitHandler(Collection<TripleEntry<Double>> settings, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        this.slotCount = settings.size();
        this.onChangeFunc = onChangeFunc;

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

        this.allowedSlots = ImmutableList.copyOf(allowedSlots);

        this.stored = ListUtil.createFilledListFor(this.getSlotCount(), () -> 0d);
    }
    //endregion

    /**
     * A helper method for converting the lists for the {@code capacity}, {@code maxReceive}, {@code maxExtract} to a {@code Collection} of {@code TripleEntry}.
     *
     * @param capacity   The settings for the max capacity.
     * @param maxReceive The settings for the max receiving limit.
     * @param maxExtract The settings for the max extracting limit,
     * @return The converted {@code Collection<TripleEntry<Double>>}
     */
    public static Collection<TripleEntry<Double>> convertListsToSettings(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract) {
        List<TripleEntry<Double>> settings = new ArrayList<>();

        for (int i = 0; i < capacity.size(); i++) {
            settings.add(new TripleEntry<>(capacity.get(i), maxReceive.get(i), maxExtract.get(i)));
        }
        return settings;
    }

    /**
     * A getter for the {@code allowedSlots}.
     *
     * @return This {@code allowedSlots}.
     */
    @Nullable
    public ImmutableList<Integer> getAllowedSlots() {
        return allowedSlots;
    }

    /**
     * A method to check if the {@code slot}'s index is allowed to access.
     *
     * @param slot The index to check.
     * @return If the {@code slot}'s index is allowed.
     */
    public boolean isAllowed(int slot) {
        if (this.getAllowedSlots() == null) return true;
        return this.getAllowedSlots().contains(slot);
    }

    /**
     * A method to check if the {@code slot}'s index is denied to access.
     *
     * @param slot The index to check.
     * @return If the {@code slot}'s index is denied.
     */
    public boolean isDenied(int slot) {
        return !this.isAllowed(slot);
    }

    /**
     * A getter for the {@code slotCount}.
     *
     * @return This {@code slotCount}.
     */
    @Override
    public int getSlotCount() {
        return slotCount;
    }


    /**
     * An indexed getter for the {@code stored}.
     *
     * @param slot The index to get the stored amount.
     * @return The stored amount from {@code stored} in index {@code slot}.
     */
    @Override
    public Double getStored(int slot) {
        if (this.isDenied(slot)) return 0d;
        return this.stored.get(slot);
    }

    /**
     * An indexed setter for the {@code stored}.
     *
     * @param amount The amount to set to.
     * @param slot   The index to set.
     */
    @Override
    @Deprecated
    public void setStored(Double amount, int slot) {
        this.stored.set(slot, amount);
        this.onChanged();
    }

    /**
     * An indexed modifier for the {@code stored}.
     *
     * @param modification The modification func to apply.
     * @param slot         The index to modify.
     */
    @Override
    @Deprecated
    public void modStored(Modification<Double> modification, int slot) {
        this.setStored(modification.apply(this.getStored(slot)), slot);
    }

    /**
     * An indexed getter for the {@code capacity}.
     *
     * @param slot The index to get from.
     * @return The max capacity from {@code capacity} in index {@code slot}.
     */
    @Override
    public Double getCapacity(int slot) {
        if (this.isDenied(slot)) return 0d;
        return this.capacity.get(slot);
    }

    /**
     * A method to get the leftover space of this tank/storage from the index {@code slot}.
     *
     * @param slot The index to get the leftover space from.
     * @return The leftover space from index {@code slot}.
     */
    @Override
    public Double getSpace(int slot) {
        if (this.isDenied(slot)) return 0d;
        return this.getCapacity(slot) - this.getStored(slot);
    }

    /**
     * An indexed getter for the {@code maxReceive}.
     *
     * @param slot The index to get from.
     * @return The max receiving limit from {@code maxReceive} in index {@code slot}.
     */
    @Override
    public Double getMaxReceive(int slot) {
        if (this.isDenied(slot)) return 0d;
        return this.maxReceive.get(slot);
    }

    /**
     * An indexed getter for the {@code maxExtract}.
     *
     * @param slot The index to get from.
     * @return The max extracting limit from {@code maxExtract} in index {@code slot}.
     */
    @Override
    public Double getMaxExtract(int slot) {
        if (this.isDenied(slot)) return 0d;
        return this.maxExtract.get(slot);
    }

    /**
     * The method to get whether this can be received or not.
     *
     * @param slot The index to check the max receive limit.
     * @return The boolean if this can be received or not from the {@code getMaxReceive(int)}'s amount.
     */
    @Override
    public boolean canReceive(int slot) {
        return this.getMaxReceive(slot) > 0f;
    }

    /**
     * The method to get whether this can be extracted or not.
     *
     * @param slot The index to check the max extract limit.
     * @return The boolean if this can be extracted or not from the {@code getMaxExtract(int)}'s amount.
     */
    @Override
    public boolean canExtract(int slot) {
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
    public Double receive(Double amount, boolean simulated, boolean forceLightReceive, int slot) {
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
    public Double extract(Double amount, boolean simulated, boolean forceLightExtract, int slot) {
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
     * A abstract method for setting/getting the color for the rendering bar.
     *
     * @param slot The slot to get the color.
     * @return A {@code VectorColor} of the color for rendering the bar.
     */
    public abstract VectorColor getColor(int slot);

    /**
     * A method for rendering this to the GUI.
     *
     * @param guiGraphics The gui rendering graphic.
     * @param pos         The position to render the bar.
     * @param slot        The index of {@code getMaxExtract} to render the bar.
     */
    @Override
    public void render(GuiGraphics guiGraphics, IScrPos<Integer> pos, int slot) {

    }

    /**
     * A method for converting this to a NBT tag.
     *
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
     *
     * @param tag The NBT tag to reconstruct this from.
     */
    @Override
    public void deserializeNBT(CompoundTag tag) {
        ListTag storedTags = tag.getList(C.STORED, Tag.TAG_DOUBLE);
        for (int slot = 0; slot < this.getSlotCount(); slot++) {
            this.setStored(storedTags.getDouble(slot), slot);
        }
    }

    /**
     * A method fired when this was changed.
     */
    @Override
    public void onChanged() {
        this.onChangeFunc.run();
    }

    /**
     * A method to make a new clone instance of this, and adjust the allowedSlots from the {@code deniedSlots}.
     *
     * @param deniedSlots The slot indexes to deny.
     * @return The cloned {@code SimpleUnitHandler}.
     */
    public SimpleUnitHandler copyAndLimitFromDeniedSlots(List<Integer> deniedSlots) {
        List<Integer> allowedSlots = new ArrayList<>();
        for (int i = 0; i < this.getSlotCount(); i++) {
            if (deniedSlots.contains(i)) continue;
            allowedSlots.add(i);
        }
        return copyAndLimit(allowedSlots);
    }

    /**
     * A method to make a new clone instance of this, and adjust tje allowed slots.
     *
     * @param allowedSlots The slot indexes to allow.
     * @return The cloned {@code SimpleUnitHandler}
     */
    public SimpleUnitHandler copyAndLimit(List<Integer> allowedSlots) {
        return new SimpleUnitHandler(this.capacity, this.maxReceive, this.maxExtract, allowedSlots, this.onChangeFunc) {
            @Override
            public VectorColor getColor(int slot) {
                return SimpleUnitHandler.this.getColor(slot);
            }
        };
    }

    /**
     * A method to make a new clone instance of this.
     *
     * @return THe cloned {@code SimpleUnitHandler}
     */
    public SimpleUnitHandler copy() {
        return new SimpleUnitHandler(this.capacity, this.maxReceive, this.maxExtract, this.allowedSlots, this.onChangeFunc) {
            @Override
            public VectorColor getColor(int slot) {
                return SimpleUnitHandler.this.getColor(slot);
            }
        };
    }
}
