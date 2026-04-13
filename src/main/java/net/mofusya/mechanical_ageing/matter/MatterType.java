package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public final class MatterType {
    private final int color;

    private ResourceLocation id;

    private MatterType(Builder builder) {
        this.color = builder.color;
    }

    public int getColor() {
        return this.color;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getTranslationId(){
        return "matter." + this.id.getNamespace() + "." + this.id.getPath();
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public boolean is(@Nullable MatterType type){
        if (type == null) return false;
        return this == type;
    }

    public static class Builder{

        private final int color;

        public Builder(int color) {
            this.color = color;
        }

        public MatterType build(){
            return new MatterType(this);
        }
    }
}
