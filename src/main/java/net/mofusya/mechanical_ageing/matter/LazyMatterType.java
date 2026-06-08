package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.ornatelib.util.ArrayMap;
import net.mofusya.ornatelib.lang.SeptiLong;

public record LazyMatterType(ResourceLocation type, ArrayMap<String, String> tags) {
    public MatterStack get(){
        return new MatterStack(MatterManager.get().get(this.type), new SeptiLong(1), this.tags);
    }
}
