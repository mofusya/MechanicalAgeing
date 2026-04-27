package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public final class MatterType {
    private final int color;
    private final String suffix;

    private ResourceLocation id;

    private MatterType(Builder builder) {
        this.color = builder.color;
        this.suffix = builder.suffix;
    }


    /*===Getters====*/

    public int getColor() {
        return this.color;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getSuffix() {
        return this.suffix;
    }

    /*========*/

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
        private String suffix = "mB";

        public Builder(int color) {
            this.color = color;
        }

        public Builder suffix(String suffix){
            this.suffix = suffix;
            return this;
        }

        public MatterType build(){
            return new MatterType(this);
        }
    }
}