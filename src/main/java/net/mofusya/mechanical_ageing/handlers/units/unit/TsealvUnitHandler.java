package net.mofusya.mechanical_ageing.handlers.units.unit;

import com.google.common.collect.ImmutableList;
import net.mofusya.mechanical_ageing.handlers.units.DoubleUnitHandler;
import net.mofusya.mechanical_ageing.util.ListUtil;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Capability handler for the Tsealv unit.
 */
public class TsealvUnitHandler implements ITsealvUnitHandler {
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
    public TsealvUnitHandler(Double capacity, Double maxReceive, Double maxExtract, Runnable onChangeFunc) {
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
    public TsealvUnitHandler(Double capacity, Double maxReceive, Double maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
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
    public TsealvUnitHandler(@Range(from = 1, to = Integer.MAX_VALUE) int slotCount, Double capacity, Double maxReceive, Double maxExtract, Runnable onChangeFunc) {
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
    public TsealvUnitHandler(@Range(from = 1, to = Integer.MAX_VALUE) int slotCount, Double capacity, Double maxReceive, Double maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
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
    public TsealvUnitHandler(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract, Runnable onChangeFunc) {
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
    public TsealvUnitHandler(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        this(DoubleUnitHandler.convertListsToSettings(capacity, maxReceive, maxExtract), allowedSlots, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param settings     The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     * @param onChangeFunc A function, fired when this was changed.
     */
    public TsealvUnitHandler(Collection<TripleEntry<Double>> settings, Runnable onChangeFunc) {
        this(settings, null, onChangeFunc);
    }

    /**
     * The root constructor,
     *
     * @param settings     The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     * @param allowedSlots The list of slot indexes that can be accessed or changed.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public TsealvUnitHandler(Collection<TripleEntry<Double>> settings, @Nullable Collection<Integer> allowedSlots, Runnable onChangeFunc) {
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

        this.allowedSlots = allowedSlots == null ? null : ImmutableList.copyOf(allowedSlots);

        this.stored = ListUtil.createFilledListFor(this.getSlotCount(), () -> 0d);
    }
    //endregion


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
        if (this.isDenied(slot)) return;
        this.stored.set(slot, amount);
        this.onChanged();
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
    public TsealvUnitHandler copyAndLimitFromDeniedSlots(List<Integer> deniedSlots) {
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
    public TsealvUnitHandler copyAndLimit(List<Integer> allowedSlots) {
        return new TsealvUnitHandler(this.capacity, this.maxReceive, this.maxExtract, allowedSlots, this.onChangeFunc);
    }

    /**
     * A method to make a new clone instance of this.
     *
     * @return THe cloned {@code SimpleUnitHandler}
     */
    public TsealvUnitHandler copy() {
        return new TsealvUnitHandler(this.capacity, this.maxReceive, this.maxExtract, this.allowedSlots, this.onChangeFunc);
    }
}
