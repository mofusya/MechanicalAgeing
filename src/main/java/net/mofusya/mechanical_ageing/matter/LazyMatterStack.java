package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.ArrayMap;
import org.jetbrains.annotations.Nullable;

public record LazyMatterStack(@Nullable ResourceLocation type, UnLong amount, ArrayMap<String, String> tags) {

    public LazyMatterStack(@Nullable ResourceLocation type, UnLong amount) {
        this(type, amount, new ArrayMap<>());
    }

    public LazyMatterStack(@Nullable ResourceLocation type, UnLong amount, ArrayMap<String, String> tags) {
        this.type = type;
        this.amount = amount;
        this.tags = tags;
    }

    public MatterStack get() {
        if (this.type == null){
            return new MatterStack(null, UnLong.zero());
        }

        return new MatterStack(MatterManager.get().get(this.type), this.amount.copy(), new ArrayMap<>(this.tags));
    }

    @Override
    public String toString() {
        return "LazyMatterStack{" +
                "type=" + type +
                ", amount=" + amount +
                '}';
    }
}
