package net.mofusya.mechanical_ageing.matter;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.mofusya.ornatelib.util.annotation.FieldsAreNonNullByDefault;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.util.annotation.ParametersAreNonNullByDefault;
import net.mofusya.ornatelib.util.function.Modification;

import javax.annotation.Nonnull;

/**
 * A class for storing a {@code IMatterType}, a {@code Double} amount, and a few {@code JsonObject} tags.
 */
@FieldsAreNonNullByDefault
@MethodsReturnNonNullByDefault
@ParametersAreNonNullByDefault
@Nonnull
public interface IMatterStack {
    /**
     * A getter for this matterType.
     */
    IMatterType getType();

    /**
     * A getter for this Double amount.
     */
    Double getAmount();

    /**
     * A setter for this Double amount.
     *
     * @param amount The amount to set to.
     */
    void setAmount(Double amount);

    /**
     * A getter for this Json tags.
     */
    JsonObject getTags();

    /**
     * A setter for this Json tags.
     *
     * @param newTags The new Tags to set to.
     */
    void setTags(JsonObject newTags);

    /**
     * A simple copy method.
     *
     * @return The copied this.
     */
    IMatterStack copy();

    /**
     * A simple copy method, with a overdrive to the matterType.
     *
     * @param matterType The {@code IMatterType} to override to.
     * @return The copied this.
     */
    IMatterStack copy(IMatterType matterType);

    /**
     * A method for converting this to a NBT tag.
     *
     * @return The NBT serialized {@code CompoundTag}.
     */
    CompoundTag serializeNBT();

    /**
     * A modifier for this Double amount.
     *
     * @param modificationFunc The modification function for this Double amount.
     */
    default void modAmount(Modification<Double> modificationFunc) {
        this.setAmount(modificationFunc.apply(this.getAmount()));
    }

    /**
     * An adder for this Double amount.
     *
     * @param amount The amount to add.
     */
    default void addAmount(Double amount) {
        this.modAmount(d -> d + amount);
    }

    /**
     * A remover for this Double amount.
     *
     * @param amount The amount to remove.
     */
    default void removeAmount(Double amount) {
        this.modAmount(d -> d - amount);
    }

    /**
     * A method to add a amount of an {@code IMatterStack}.
     * If the {@code IMatterStack}'s matterType and this {@code matterType} is different, cancel the adding.
     *
     * @param matterStack The {@code IMatterStack} to add.
     * @return Whether the adding was successful.
     */
    default boolean add(IMatterStack matterStack, boolean simulated) {
        if (!this.is(matterStack.getType()) && !matterStack.isEmpty()) return false;
        if (!this.testTags(matterStack)) return false;

        if (!simulated) {
            this.addAmount(matterStack.getAmount());
            if (this.isEmpty() && !matterStack.isEmpty()){
                this.setTags(matterStack.getTags().deepCopy());
            }
        }
        return true;
    }

    /**
     * A method to remove a amount of an {@code IMatterStack}.
     * If the {@code IMatterStack}'s matterType and this {@code matterType} is different, cancel the removing.
     *
     * @param matterStack The {@code ImatterStack} to remove.
     * @return Whether the removing was successful.
     */
    default boolean remove(IMatterStack matterStack, boolean simulated) {
        if (!this.is(matterStack.getType()) && !matterStack.isEmpty()) return false;
        if (!this.testTags(matterStack)) return false;

        if (!simulated) {
            this.removeAmount(matterStack.getAmount());
        }
        return true;
    }

    /**
     * A method to set this amount to an another {@code IMatterStack}.
     * If the {@code IMatterStack}'s matterType and this {@code matterType} is different, cancel this method.
     *
     * @param matterStack The {@code IMatterStack} to get the setting amount.
     */
    default void setTo(IMatterStack matterStack) {
        if (!this.is(matterStack.getType()) && !matterStack.isEmpty()) return;
        this.setAmount(matterStack.getAmount());
    }

    /**
     * A helper method to determine if this matterType is the same as {@code matterType}.
     *
     * @param matterType The {@code IMatterType} to compare.
     * @return If the {@code matterType} is the same as this matterType.
     */
    default boolean is(IMatterType matterType) {
        return this.getType().is(matterType);
    }

    /**
     * A helper method to determine if this matters tag components is the same as {@code matterStack}.
     *
     * @param matterStack The {@code IMatterStack} to compare.
     * @return If the {@code matterStack} has the same tags.
     */
    default boolean testTags(IMatterStack matterStack) {
        return this.getTags().toString().equals(matterStack.toString());
    }

    /**
     * A method to determine if this {@code IMatterStack} is empty.
     *
     * @return Whether if this is empty.
     */
    default boolean isEmpty() {
        return this.is(MAgMatters.EMPTY.get());
    }
}
