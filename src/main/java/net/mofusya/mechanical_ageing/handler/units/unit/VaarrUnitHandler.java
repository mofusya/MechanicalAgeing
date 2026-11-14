package net.mofusya.mechanical_ageing.handler.units.unit;

import net.kairayk.yklibrary.util.VectorColor;
import net.mofusya.mechanical_ageing.handler.units.SimpleUnitHandler;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import org.jetbrains.annotations.Range;

import java.util.Collection;
import java.util.List;

/**
 * A Capability handler for the Vaarr unit.
 */
public class VaarrUnitHandler extends SimpleUnitHandler {
    /**
     * An overdriven constructor,
     *
     * @param capacity     The max capacity of this.
     * @param maxReceive   The max receiving limit of this.
     * @param maxExtract   The max extracting limit of this.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public VaarrUnitHandler(Double capacity, Double maxReceive, Double maxExtract, Runnable onChangeFunc) {
        super(capacity, maxReceive, maxExtract, onChangeFunc);
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
    public VaarrUnitHandler(Double capacity, Double maxReceive, Double maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        super(capacity, maxReceive, maxExtract, allowedSlots, onChangeFunc);
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
    public VaarrUnitHandler(@Range(from = 1, to = Integer.MAX_VALUE) int slotCount, Double capacity, Double maxReceive, Double maxExtract, Runnable onChangeFunc) {
        super(slotCount, capacity, maxReceive, maxExtract, onChangeFunc);
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
    public VaarrUnitHandler(@Range(from = 1, to = Integer.MAX_VALUE) int slotCount, Double capacity, Double maxReceive, Double maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        super(slotCount, capacity, maxReceive, maxExtract, allowedSlots, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param capacity     The max capacity per slot of this.
     * @param maxReceive   The max receiving limit per slot of this.
     * @param maxExtract   The max extracting limit per slot of this.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public VaarrUnitHandler(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract, Runnable onChangeFunc) {
        super(capacity, maxReceive, maxExtract, onChangeFunc);
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
    public VaarrUnitHandler(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        super(capacity, maxReceive, maxExtract, allowedSlots, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param settings     The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     * @param onChangeFunc A function, fired when this was changed.
     */
    public VaarrUnitHandler(Collection<TripleEntry<Double>> settings, Runnable onChangeFunc) {
        super(settings, onChangeFunc);
    }

    /**
     * The root constructor,
     *
     * @param settings     The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     * @param allowedSlots The list of slot indexes that can be accessed or changed.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public VaarrUnitHandler(Collection<TripleEntry<Double>> settings, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        super(settings, allowedSlots, onChangeFunc);
    }

    /**
     * A method for setting/getting the color for the rendering bar.
     * @param slot The slot to get the color.
     * @return A {@code VectorColor} of the color for rendering the bar.
     */
    @Override
    public VectorColor getColor(int slot) {
        return VectorColor.fromARGB(0xFF000000);
    }
}
