package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.ArrayMap;

public record LazyMatterStack(ResourceLocation type, UnLong amount, ArrayMap<String, String> tags) {
    public MatterStack get() {
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
