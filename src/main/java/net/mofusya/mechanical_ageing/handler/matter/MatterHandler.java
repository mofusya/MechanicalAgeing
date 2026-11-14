package net.mofusya.mechanical_ageing.handler.matter;

import com.google.common.collect.ImmutableList;
import net.kairayk.yklibrary.util.scrpos.IScrPos;
import net.minecraft.client.gui.GuiGraphics;
import net.mofusya.mechanical_ageing.matter.IMatterStack;
import net.mofusya.mechanical_ageing.matter.MAgMatters;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.util.ListUtil;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import net.mofusya.ornatelib.util.function.Modification;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A multi layered Capability handler for {@code IMatterStack}.
 */
public abstract class MatterHandler implements IMatterHandler {
    /**
     * A field for storing the slot count this has.
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
    private final List<IMatterStack> stored;

    //region Constructors

    /**
     * An overdriven constructor,
     *
     * @param capacity     The max capacity of this.
     * @param maxReceive   The max receiving limit of this.
     * @param maxExtract   The max extracting limit of this.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public MatterHandler(Double capacity, Double maxReceive, Double maxExtract, Runnable onChangeFunc) {
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
    public MatterHandler(Double capacity, Double maxReceive, Double maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
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
    public MatterHandler(@Range(from = 1, to = Integer.MAX_VALUE) int slotCount, Double capacity, Double maxReceive, Double maxExtract, Runnable onChangeFunc) {
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
    public MatterHandler(@Range(from = 1, to = Integer.MAX_VALUE) int slotCount, Double capacity, Double maxReceive, Double maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
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
    public MatterHandler(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract, Runnable onChangeFunc) {
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
    public MatterHandler(List<Double> capacity, List<Double> maxReceive, List<Double> maxExtract, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
        this(convertListsToSettings(capacity, maxReceive, maxExtract), allowedSlots, onChangeFunc);
    }

    /**
     * An overdriven constructor,
     *
     * @param settings     The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     * @param onChangeFunc A function, fired when this was changed.
     */
    public MatterHandler(Collection<TripleEntry<Double>> settings, Runnable onChangeFunc) {
        this(settings, null, onChangeFunc);
    }

    /**
     * The root constructor,
     *
     * @param settings     The setting entry for initializing the {@code capacity}, the {@code maxReceive}, and the {@code maxExtract},
     * @param allowedSlots The list of slot indexes that can be accessed or changed.
     * @param onChangeFunc A function, fired when this was changed.
     */
    public MatterHandler(Collection<TripleEntry<Double>> settings, Collection<Integer> allowedSlots, Runnable onChangeFunc) {
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

        this.stored = ListUtil.createFilledListFor(this.getSlotCount(), MatterStack::empty);
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
        return this.slotCount;
    }

    /**
     * An indexed getter for the {@code stored}.
     *
     * @param slot The index to get the stored amount.
     * @return The stored amount from {@code stored} in index {@code slot}.
     */
    @Override
    public IMatterStack getStored(int slot) {
        return this.stored.get(slot);
    }

    /**
     * An indexed setter for the {@code stored}.
     *
     * @param amount The amount to set to.
     * @param slot   The index to set.
     */
    @Override
    public void setStored(IMatterStack amount, int slot) {
        if (amount.isEmpty()) return;
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
    public void modStored(Modification<IMatterStack> modification, int slot) {
        this.setStored(modification.apply(this.getStored(slot)), slot);
    }

    /**
     * An indexed getter for the {@code capacity}.
     *
     * @param slot The index to get from.
     * @return The max capacity from {@code capacity} in index {@code slot}.
     */
    @Override
    public IMatterStack getCapacity(int slot) {
        return new MatterStack(MAgMatters.EMPTY.get(), this.capacity.get(slot));
    }

    /**
     * A method to get the leftover space of this tank/storage from the index {@code slot}.
     *
     * @param slot The index to get the leftover space from.
     * @return The leftover space from index {@code slot}.
     */
    @Override
    public IMatterStack getSpace(int slot) {
        var capacity = this.getCapacity(slot);
        capacity.remove(this.getStored(slot), false);
        return capacity;
    }

    /**
     * An indexed getter for the {@code maxReceive}.
     *
     * @param slot The index to get from.
     * @return The max receiving limit from {@code maxReceive} in index {@code slot}.
     */
    @Override
    public IMatterStack getMaxReceive(int slot) {
        return new MatterStack(MAgMatters.EMPTY.get(), this.maxReceive.get(slot));
    }

    /**
     * An indexed getter for the {@code maxExtract}.
     *
     * @param slot The index to get from.
     * @return The max extracting limit from {@code maxExtract} in index {@code slot}.
     */
    @Override
    public IMatterStack getMaxExtract(int slot) {
        return new MatterStack(MAgMatters.EMPTY.get(), this.maxExtract.get(slot));
    }

    /**
     * The method to get whether this can be received or not.
     *
     * @param slot The index to check the max receive limit.
     * @return The boolean if this can be received or not from the {@code getMaxReceive(int)}'s amount.
     */
    @Override
    public boolean canReceive(int slot) {
        return this.getMaxReceive(slot).getAmount() > 0d;
    }

    /**
     * The method to get whether this can be extracted or not.
     *
     * @param slot The index to check the max extract limit.
     * @return The boolean if this can be extracted or not from the {@code getMaxExtract(int)}'s amount.
     */
    @Override
    public boolean canExtract(int slot) {
        return this.getMaxExtract(slot).getAmount() > 0d;
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
    public IMatterStack receive(IMatterStack amount, boolean simulated, boolean forceLightReceive, int slot) {
        if (this.isDenied(slot)) return MatterStack.empty();
        if (!this.isValid(amount, slot)) return MatterStack.empty();
        if (!this.canReceive(slot) && !forceLightReceive) return MatterStack.empty();

        IMatterStack stored = this.getStored(slot);
        IMatterStack receiveAmount = amount.isEmpty() ? amount.copy(stored.getType()) : amount.copy();
        if (this.getMaxReceive(slot).getAmount() < receiveAmount.getAmount() && !forceLightReceive)
            receiveAmount.setTo(this.getMaxReceive(slot));
        if (this.getSpace(slot).getAmount() < receiveAmount.getAmount()) receiveAmount.setTo(this.getSpace(slot));

        if (!stored.add(receiveAmount, true)) return MatterStack.empty();

        if (!simulated) {
            if (stored.isEmpty()){
                this.setStored(receiveAmount, slot);
            } else {
                stored.add(receiveAmount, false);
                this.onChanged();
            }
        }
        return receiveAmount;
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
    public IMatterStack extract(IMatterStack amount, boolean simulated, boolean forceLightExtract, int slot) {
        if (this.isDenied(slot)) return MatterStack.empty();
        if (!this.canExtract(slot) && !forceLightExtract) return MatterStack.empty();
        if (this.getStored(slot).isEmpty()) return MatterStack.empty();

        IMatterStack stored = this.getStored(slot);
        IMatterStack extractAmount = amount.isEmpty() ? amount.copy(stored.getType()) : amount.copy();
        if (this.getMaxExtract(slot).getAmount() < extractAmount.getAmount() && !forceLightExtract)
            extractAmount.setTo(this.getMaxExtract(slot));
        if (stored.getAmount() < extractAmount.getAmount()) extractAmount.setTo(stored);

        if (!stored.remove(extractAmount, true)) return MatterStack.empty();

        if (!simulated) {
            stored.remove(extractAmount, false);
        }
        return extractAmount;
    }

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
     * A method fired when this was changed.
     */
    @Override
    public void onChanged() {
        this.onChangeFunc.run();
    }

    /**
     * A abstract method to determine if the slot can store the {@code IMatterStack} matterStack.
     *
     * @param matterStack The {@code IMatterStack} to test.
     * @param slot        The slot index to test.
     * @return If the {@code matterStack} is valid.
     */
    abstract boolean isValid(IMatterStack matterStack, int slot);
}