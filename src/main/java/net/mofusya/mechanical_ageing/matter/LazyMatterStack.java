package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.ornatelib.lang.SeptiLong;

public record LazyMatterStack(ResourceLocation type, SeptiLong amount) {
    public MatterStack get(){
        return new MatterStack(MatterManager.get().get(this.type), this.amount);
    }
}
