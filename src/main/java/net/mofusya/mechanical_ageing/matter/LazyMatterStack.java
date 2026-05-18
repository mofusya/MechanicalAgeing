package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.util.ArrayMap;
import net.mofusya.ornatelib.lang.SeptiLong;

public record LazyMatterStack(ResourceLocation type, SeptiLong amount, ArrayMap<String, String> tags) {
    public MatterStack get(){
        return new MatterStack(MatterManager.get().get(this.type), this.amount.copy(), this.tags);
    }
}
