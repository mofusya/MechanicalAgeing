package net.mofusya.mechanical_ageing.matter;

import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class MatterStack {
    @Nullable
    private MatterType type;
    @NotNull
    private SeptiLong amount;

    public MatterStack(@Nullable MatterType type) {
        this(type, 0);
    }

    public MatterStack(@Nullable MatterType type, long amount) {
        this(type, new SeptiLong(amount));
    }

    public MatterStack(@Nullable MatterType type, @NotNull SeptiLong amount) {
        this.type = type;
        this.amount = amount;
    }

    public boolean receive(MatterStack matterStack, boolean simulate) {
        if (matterStack == null) return false;
        if (this.type == null) {
            if (!simulate) {
                this.modify(matterType -> matterStack.getType(), matterAmount -> matterAmount.add(matterStack.getAmount()));
            }
            return true;
        } else if (matterStack.getType() == null || this.type.is(matterStack.getType())) {
            if (!simulate) {
                this.modifyAmount(matterAmount -> matterAmount.add(matterStack.getAmount()));
            }
            return true;
        }
        return false;
    }

    public boolean extract(MatterStack matterStack, boolean simulate) {
        if (matterStack == null || this.type == null || this.amount.isSmallerOrSameThan(0)) return false;
        if (matterStack.getType() == null || this.type.is(matterStack.getType())) {
            if (!simulate) {
                this.modifyAmount(matterAmount -> matterAmount.remove(matterStack.getAmount()));
            }
            return true;
        }
        return false;
    }

    public MatterStack copy() {
        return new MatterStack(this.type, this.amount);
    }

    public static MatterStack empty() {
        return new MatterStack(null, 0);
    }

    //Getter setter modifiers
    public void setType(@Nullable MatterType type) {
        this.type = type;
        if (type == null){
            this.amount = SeptiLongValue.ZERO.get();
        }
    }

    public void setAmount(long amount) {
        this.setAmount(new SeptiLong(amount));
    }

    public void setAmount(@NotNull SeptiLong amount) {
        this.amount = amount;
    }

    public void set(@Nullable MatterType type, long amount) {
        this.set(type, new SeptiLong(amount));
    }

    public void set(@Nullable MatterType type, @NotNull SeptiLong amount) {
        this.type = type;
        if (type == null) {
            this.amount = SeptiLongValue.ZERO.get();
        } else {
            this.amount = amount;
        }
    }

    public void modifyType(@NotNull Function<MatterType, MatterType> matterFunc) {
        this.modify(matterFunc, null);
    }

    public void modifyAmount(@NotNull Function<SeptiLong, @NotNull SeptiLong> amountFunc) {
        this.modify(null, amountFunc);
    }

    public void modify(@Nullable Function<MatterType, MatterType> matterFunc, @Nullable Function<SeptiLong, @NotNull SeptiLong> amountFunc) {
        if (matterFunc != null) this.type = matterFunc.apply(this.type);
        if (amountFunc != null) this.amount = amountFunc.apply(this.amount);
    }

    public @Nullable MatterType getType() {
        return this.amount.isGreaterThan(0) ? this.type : null;
    }

    public @NotNull SeptiLong getAmount() {
        return this.type != null ? this.amount.copy() : SeptiLongValue.ZERO.get();
    }
}
