package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.ArrayMap;
import net.mofusya.ornatelib.lang.SeptiLong;
import org.jetbrains.annotations.Nullable;

public record LazyMatterType(@Nullable ResourceLocation type, ArrayMap<String, String> tags) {

    public LazyMatterType(@Nullable ResourceLocation type) {
        this(type, new ArrayMap<>());
    }

    public LazyMatterType(@Nullable ResourceLocation type, ArrayMap<String, String> tags) {
        this.type = type;
        this.tags = tags;
    }

    public MatterStack get(){
        if (this.type == null) return new MatterStack(null, UnLong.zero());

        return new MatterStack(MatterManager.get().get(this.type), new UnLong(1), this.tags);
    }
}
