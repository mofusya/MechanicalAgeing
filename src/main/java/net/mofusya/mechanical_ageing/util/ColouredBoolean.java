package net.mofusya.mechanical_ageing.util;

import net.kairayk.yklibrary.util.VectorColor;

import javax.annotation.Nullable;

/**
 * A class who stores both a {@code boolean} and a {@code @Nullable} {@code VectorColor} colour.
 */
public class ColouredBoolean {
    private boolean bool;
    @Nullable
    private VectorColor color;

    public ColouredBoolean() {
        this(false, null);
    }

    public ColouredBoolean(boolean bool) {
        this(bool, null);
    }

    public ColouredBoolean(@Nullable VectorColor color) {
        this(false, color);
    }

    public ColouredBoolean(boolean bool, @Nullable VectorColor color) {
        this.bool = bool;
        this.color = color;
    }

    public void activate(){
        this.setBool(true);
    }

    public void disable(){
        this.setBool(false);
    }

    public void trigger(){
        this.setBool(!this.get());
    }

    public boolean get() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    @Nullable
    public VectorColor getColour() {
        return color;
    }

    public void setColour(@Nullable VectorColor color) {
        this.color = color;
    }
}
