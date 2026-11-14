package net.mofusya.mechanical_ageing.matter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.ornatelib.util.annotation.FieldsAreNonNullByDefault;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.util.annotation.ParametersAreNonNullByDefault;

import javax.annotation.Nonnull;

/**
 * A class for storing a {@code IMatterType}, a {@code Double} amount, and a few {@code JsonObject} tags.
 */
@FieldsAreNonNullByDefault
@MethodsReturnNonNullByDefault
@ParametersAreNonNullByDefault
@Nonnull
public final class MatterStack implements IMatterStack {
    /**
     * A field for storing the {@code IMatterType} type for this.
     */
    private final IMatterType type;
    /**
     * A field for storing the {@code JsonObject} tags for this.
     */
    private JsonObject tags;
    /**
     * A field for storing the {@code Double} amount of this.
     */
    private double amount;

    /**
     * An overloaded constructor
     *
     * @param type   The {@code IMatterType} type for this.
     * @param amount The initial {@code Double} amount for this.
     */
    public MatterStack(IMatterType type, double amount) {
        this(type, amount, new JsonObject());
    }

    /**
     * The root constructor.
     *
     * @param type   The {@code IMatterType} type for this.
     * @param amount The initial {@code Double} amount for this.
     * @param tags   The initial {@code JsonObject} tags for this.
     */
    public MatterStack(IMatterType type, double amount, JsonObject tags) {
        this.type = type;
        this.amount = amount;
        this.tags = tags;
    }

    /**
     * A static method for reconstructing a {@code MatterStack} from a {@code CompoundTag}/NBT.
     *
     * @param tag The NBT tag to reconstruct from.
     */
    public static MatterStack deserializeNBT(CompoundTag tag) {
        return new MatterStack(
                MAgMatters.REGISTRY.getEntries().get(new ResourceLocation(tag.getString(C.TYPE))).get(),
                tag.getDouble(C.AMOUNT),
                JsonParser.parseString(tag.getString(C.TAGS)).getAsJsonObject()
        );
    }

    /**
     * A getter for this {@code  type}.
     *
     * @return This {@code type}.
     */
    @Override
    public IMatterType getType() {
        return this.type;
    }

    /**
     * A getter for this {@code  amount}.
     *
     * @return This {@code amount}
     */
    @Override
    public Double getAmount() {
        return this.amount;
    }

    /**
     * A setter for this {@code amount}.
     *
     * @param amount The amount to set to.
     */
    @Override
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * A getter for this Json tags.
     *
     * @return This {@code tags}
     */
    @Override
    public JsonObject getTags() {
        return this.tags;
    }

    /**
     * A setter for this Json tags.
     *
     * @param newTags The new Tags to set to.
     */
    @Override
    public void setTags(JsonObject newTags) {
        this.tags = newTags;
    }

    /**
     * A simple copy method.
     *
     * @return The copied this.
     */
    @Override
    public IMatterStack copy() {
        return new MatterStack(this.getType(), this.getAmount(), this.getTags());
    }

    /**
     * A simple copy method, with a overdrive to the matterType.
     *
     * @param matterType The {@code IMatterType} to override to.
     * @return The copied this.
     */
    @Override
    public IMatterStack copy(IMatterType matterType) {
        return new MatterStack(matterType, this.getAmount(), this.getTags());
    }

    /**
     * A method for converting this to a NBT tag.
     *
     * @return The NBT serialized {@code CompoundTag}.
     */
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putString(C.TYPE, this.getType().getId().toString());
        tag.putDouble(C.AMOUNT, this.getAmount());
        tag.putString(C.TAGS, this.getTags().toString());

        return tag;
    }

    /**
     * A helper method to get a new empty {@code MatterStack}.
     * @return A new empty {@code MatterStack}.
     */
    public static MatterStack empty(){
        return new MatterStack(MAgMatters.EMPTY.get(), 0d);
    }
}
