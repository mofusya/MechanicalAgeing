package net.mofusya.mechanical_ageing.matter;

import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.ArrayMap;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import net.mofusya.ornatelib.util.function.Modification;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class MatterStack {
    @Nullable
    private MatterType type;
    @NotNull
    private UnLong amount;
    @NotNull
    private ArrayMap<String, String> tags;

    public MatterStack(@Nullable MatterType type) {
        this(type, 0);
    }

    public MatterStack(@Nullable MatterType type, long amount) {
        this(type, new UnLong(amount));
    }

    public MatterStack(@Nullable MatterType type, @NotNull UnLong amount) {
        this(type, amount, null);
    }

    public MatterStack(@Nullable MatterType type, @NotNull UnLong amount, @Nullable ArrayMap<String, String> tags) {
        this.type = type;
        this.amount = amount.copy();
        this.tags = tags == null ? new ArrayMap<>() : tags;
    }

    public boolean receive(MatterStack matterStack, boolean simulate) {
        if (matterStack == null) return false;
        if (!checkTags(this, matterStack)) return false;

        if (matterStack.getType() != null && matterStack.getType().is(MAgMatterTypes.WATER_VAPOR) && !matterStack.getTags().hasContent()){
            return false;
        }

        if (this.type == null) {
            if (!simulate) {
                this.modify(matterType -> matterStack.getType(), matterAmount -> matterAmount.add(matterStack.getAmount()));
                this.getTags().putAll(matterStack.getTags());
            }
            return true;
        } else if (matterStack.getType() == null || (this.type.is(matterStack.getType()) && this.tags.matches(matterStack.getTags()))) {
            if (!simulate) {
                this.modifyAmount(matterAmount -> matterAmount.add(matterStack.getAmount()));
            }
            return true;
        }
        return false;
    }

    public boolean extract(MatterStack matterStack, boolean simulate) {
        if (matterStack == null || this.type == null || this.amount.isSmallerOrSameAs(0)) return false;
        if (!checkTags(this, matterStack)) return false;

        if (matterStack.getType() == null || (this.type.is(matterStack.getType()) && this.tags.matches(matterStack.getTags()))) {
            if (!simulate) {
                this.modifyAmount(matterAmount -> matterAmount.min(matterStack.getAmount()));
                if (this.getNoneTypeAmount().isSmallerOrSameAs(0)){
                    this.type = null;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        return this.type == null || this.amount.equals(UnLong.zero());
    }

    public MatterStack copy() {
        return new MatterStack(this.type, this.amount, this.tags);
    }

    public static MatterStack empty() {
        return new MatterStack(null, 0);
    }

    //Getter setter modifiers
    public void setType(@Nullable MatterType type) {
        this.type = type;
        if (type == null) {
            this.amount = UnLong.zero();
        }
    }

    public void setAmount(long amount) {
        this.setAmount(new UnLong(amount));
    }

    public void setAmount(@NotNull UnLong amount) {
        this.amount = amount;
    }

    public void set(@Nullable MatterType type, long amount) {
        this.set(type, new UnLong(amount));
    }

    public void set(@Nullable MatterType type, @NotNull UnLong amount) {
        this.type = type;
        if (type == null) {
            this.amount = UnLong.zero();
        } else {
            this.amount = amount;
        }
    }

    public void modifyType(@NotNull Function<MatterType, MatterType> matterFunc) {
        this.modify(matterFunc, null);
    }

    public void modifyAmount(@NotNull Modification<UnLong> amountFunc) {
        this.modify(null, amountFunc);
    }

    public void modify(@Nullable Function<MatterType, MatterType> matterFunc, @Nullable Modification<UnLong> amountFunc) {
        if (matterFunc != null) this.type = matterFunc.apply(this.type);
        if (amountFunc != null) this.amount = amountFunc.apply(this.amount);
    }

    public MatterStack tag(String key, String value) {
        this.getTags().put(key, value);
        return this;
    }

    public @Nullable MatterType getType() {
        return this.amount.isGreaterThan(0) ? this.type : null;
    }

    public @NotNull UnLong getAmount() {
        return this.type != null ? this.amount.copy() : UnLong.zero();
    }

    public @NotNull UnLong getNoneTypeAmount() {
        return this.amount.copy();
    }

    public @NotNull ArrayMap<String, String> getTags() {
        if (!this.amount.isGreaterThan(0)) this.tags = new ArrayMap<>();

        return this.tags;
    }

    public static boolean checkTags(MatterStack matterStackA, MatterStack matterStackB) {
        if (matterStackA == null || matterStackB == null) return true;
        if (matterStackA.getType() == null || matterStackB.getType() == null) return true;

        return matterStackA.getTags().matches(matterStackB.getTags());
    }

    @Override
    public String toString() {
        return (this.type == null? "Air" : this.type.getId()) + ": " + this.amount;
    }
}
